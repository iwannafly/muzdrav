package ru.nkz.ivcgzo.Infomat;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ReservedTalonTableModel implements TableModel { 
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Talon> reservedTalons;
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy");
    private static final String[] COLUMN_NAMES = {
        "Дата приёма", "Время приёма", "ЛПУ" ,"Специальность" , "Ф.И.О"
    };
    
    public ReservedTalonTableModel(final int patientId) {
        reservedTalons = getReservedTalonsFromDb(patientId);
    }

    private List<Talon> getReservedTalonsFromDb(int patientId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final String sqlQuery = "SELECT id, ntalon, vidp, timep, datap, npasp, pcod_sp, dataz, prv, "
            + "n_s00.name as spec, (fam || ' ' || im || ' ' || ot) as fio, n_n00.name_s as lpu "
            + "FROM e_talon "
            + "INNER JOIN n_s00 ON n_s00.pcod = e_talon.cdol "
            + "INNER JOIN s_vrach ON s_vrach.pcod = e_talon.pcod_sp "
            + "INNER JOIN n_n00 ON n_n00.pcod = e_talon.cpol "
            + "WHERE datap >= ? "
            + "AND npasp = ? AND npasp <> 0 ORDER BY datap, timep;";
        DbManager dbManager = DbManager.getInstance();
        List<Talon> talons = new ArrayList<Talon>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setInt(2, patientId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                talons.add(
                    new Talon(
                        resultSet.getInt("id"),
                        resultSet.getInt("vidp"),
                        resultSet.getTime("timep"),
                        resultSet.getDate("datap"),
                        resultSet.getInt("npasp"),
                        resultSet.getInt("pcod_sp"),
                        resultSet.getString("lpu"),
                        resultSet.getString("spec"),
                        resultSet.getString("fio")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return talons;
    }

    @Override
    public final int getRowCount() {
        return reservedTalons.size();
    }
    
    @Override
    public final int getColumnCount() {
        return 5;
    }
    
    @Override
    public final String getColumnName(final int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }
    
    @Override
    public final Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }
    
    @Override
    public final boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }
    
    @Override
    public final Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return DEFAULT_DATE_FORMAT.format(reservedTalons.get(rowIndex).getDatap());
            case 1:
                return DEFAULT_TIME_FORMAT.format(reservedTalons.get(rowIndex).getTimep());
            case 2:
                return reservedTalons.get(rowIndex).getLpuName();
            case 3:
                return reservedTalons.get(rowIndex).getDoctorSpec();
            case 4:
                return reservedTalons.get(rowIndex).getDoctorFio();
            default:
                return null;
        }
    }
    
    public final List<Talon> getReservedTalonList() {
        return reservedTalons;
    }
    
    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    }
    
    @Override
    public final void addTableModelListener(final TableModelListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public final void removeTableModelListener(final TableModelListener listener) {
        listeners.remove(listener);
    }
}

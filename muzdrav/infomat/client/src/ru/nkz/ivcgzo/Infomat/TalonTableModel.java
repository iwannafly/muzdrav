package ru.nkz.ivcgzo.Infomat;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public final class TalonTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy");
    private static final String[] DAY_NAMES = {
        "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вск"
    };
    private TalonList talonList;

    public TalonTableModel(final int cpol, final String cdol, final int pcod) {
        setTalonList(cpol, cdol, pcod);
    }

    private void setTalonList(final int cpol, final String cdol, final int pcod) {
        talonList = getTalonFromDb(cpol, cdol, pcod);
    }

    private TalonList getTalonFromDb(int cpol, String cdol, int pcod) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT id, vidp, timep, datap, npasp, pcod_sp "
                + "FROM e_talon WHERE cpol = ? AND cdol = ? AND pcod_sp = ? "
                + "AND ((datap > ?) OR (datap = ? AND timep >= ?)) AND prv = ? "
                + "ORDER BY datap, timep;";
        DbManager dbManager = DbManager.getInstance();
        List<Talon> talons = new ArrayList<Talon>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, cpol);
            statement.setString(2, cdol);
            statement.setInt(3, pcod);
            statement.setDate(4, new Date(System.currentTimeMillis()));
            statement.setDate(5, new Date(System.currentTimeMillis()));
            statement.setTime(6, new Time(System.currentTimeMillis()));
            statement.setInt(7, 0);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                talons.add(
                    new Talon(
                        resultSet.getInt("id"),
                        resultSet.getInt("vidp"),
                        resultSet.getTime("timep"),
                        resultSet.getDate("datap"),
                        resultSet.getInt("npasp"),
                        resultSet.getInt("pcod_sp")
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
        return new TalonList(talons);
    }

    public TalonList getTalonList() {
        return talonList;
    }

    @Override
    public int getRowCount() {
        return talonList.getMaximumListSize();
    }

    @Override
    public int getColumnCount() {
        return talonList.getWeekDays().length;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        String displayedTime = "";
        if ((talonList.getTimeOfAppointmentByDay(rowIndex, columnIndex)) != null) {
            displayedTime = DEFAULT_TIME_FORMAT.format(
                talonList.getTimeOfAppointmentByDay(rowIndex, columnIndex));
        }
        return displayedTime; //talonList.getTimeOfAppointmentByDay(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return String.format("%s %s", DAY_NAMES[columnIndex],
            DEFAULT_DATE_FORMAT.format(talonList.getWeekDays()[columnIndex]));
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }

    @Override 
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    }

    public void setPrevWeek() {
        talonList.setPrevWeek();
    }

    public void setNextWeek() {
        talonList.setNextWeek();
    }

    @Override
    public void addTableModelListener(final TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(final TableModelListener listener) {
        listeners.remove(listener);
    }
}

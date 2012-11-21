package ru.nkz.ivcgzo.Infomat;

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

public class SheduleTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<SheduleDay> sheduleList;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("HH-mm");
    private static final String[] COLUMN_NAMES = {
        "Понедельник", "Вторник", "Среда" , "Четверг" , "Пятница", "Суббота", "Воскресенье"
    };
    
    public SheduleTableModel(final int pcod, final int cpol, final String cdol) {
        sheduleList = getReservedTalonsFromDb(pcod, cpol, cdol);
    }
    
    private List<SheduleDay> getReservedTalonsFromDb(
            final int pcod, final int cpol, final String cdol) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final String sqlQuery = "SELECT time_n, time_k, vidp, denn "
            + "FROM e_nrasp WHERE pcod =? AND cpol = ? AND cdol = ? ORDER BY time_n";
        DbManager dbManager = DbManager.getInstance();
        List<SheduleDay> shedule = new ArrayList<SheduleDay>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, pcod);
            statement.setInt(2, cpol);
            statement.setString(3, cdol);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                shedule.add(
                    new SheduleDay(
                        resultSet.getTime("time_n"),
                        resultSet.getTime("time_k"),
                        resultSet.getInt("vidp"),
                        resultSet.getInt("denn")
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
        return shedule;
    }
    
    @Override
    public final int getRowCount() {
        return 4;
    }
    
    @Override
    public final int getColumnCount() {
        return 7;
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

    public int getVidp(final int rowIndex, final int columnIndex) {
        if (findSheduleDay(columnIndex-1, rowIndex+1) != null) {
            return findSheduleDay(columnIndex-1, rowIndex+1).getVidp();
        } else {
            return 0;
        }
    }
    private SheduleDay findSheduleDay(int denn, int vidP) {
        for (SheduleDay sheduleDay : sheduleList) {
            if ((sheduleDay.getWeekDay() == denn) && (sheduleDay.getVidp() == vidP)) {
                return sheduleDay;
            }
        }
        return null;
    }

    private String createCellLabel(SheduleDay sheduleDay) {
        if (sheduleDay == null) {
            return "";
        }
        String timeStart = " ";
        String timeEnd = " ";
        if ((sheduleDay.getTimeStart() != null) && (sheduleDay.getTimeStart().getTime() != 0)) {
            timeStart = DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeStart());
        }
        if ((sheduleDay.getTimeEnd() != null) && (sheduleDay.getTimeEnd().getTime() != 0)) {
            timeEnd = DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeEnd());
        }
        return String.format("%s - %s", timeStart, timeEnd); 
    }

    @Override
    public final Object getValueAt(final int rowIndex, final int columnIndex) {
        return createCellLabel(findSheduleDay(columnIndex-1, rowIndex+1));
    }
    
    public final List<SheduleDay> getSheduleTalonList() {
        return sheduleList;
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

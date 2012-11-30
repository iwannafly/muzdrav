package ru.nkz.ivcgzo.clientInfomat;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;

public class SheduleTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<TSheduleDay> sheduleList;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("HH-mm");
    private static final String[] COLUMN_NAMES = {
        "Понедельник", "Вторник", "Среда" , "Четверг" , "Пятница", "Суббота", "Воскресенье"
    };
    
    public SheduleTableModel(final int pcod, final int cpol, final String cdol) {
        try {
            sheduleList = ClientInfomat.tcl.getShedule(pcod, cpol, cdol);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
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
    private TSheduleDay findSheduleDay(int denn, int vidP) {
        for (TSheduleDay sheduleDay : sheduleList) {
            if ((sheduleDay.getWeekDay() == denn) && (sheduleDay.getVidp() == vidP)) {
                return sheduleDay;
            }
        }
        return null;
    }

    private String createCellLabel(TSheduleDay sheduleDay) {
        if (sheduleDay == null) {
            return "";
        }
        String timeStart = " ";
        String timeEnd = " ";
        if ((sheduleDay.isSetTimeStart()) && (sheduleDay.getTimeStart() != 0)) {
            timeStart = DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeStart());
        }
        if ((sheduleDay.isSetTimeEnd()) && (sheduleDay.getTimeEnd() != 0)) {
            timeEnd = DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeEnd());
        }
        return String.format("%s - %s", timeStart, timeEnd); 
    }

    @Override
    public final Object getValueAt(final int rowIndex, final int columnIndex) {
        return createCellLabel(findSheduleDay(columnIndex-1, rowIndex+1));
    }
    
    public final List<TSheduleDay> getSheduleTalonList() {
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

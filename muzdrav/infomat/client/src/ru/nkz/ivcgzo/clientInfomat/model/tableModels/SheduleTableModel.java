package ru.nkz.ivcgzo.clientInfomat.model.tableModels;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.ClientInfomat;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;

public class SheduleTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<TSheduleDay> sheduleList;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private static final int ROW_COUNT = 1;
    private static final String[] COLUMN_NAMES = {
        "Понедельник", "Вторник", "Среда" , "Четверг" , "Пятница", "Суббота", "Воскресенье"
    };

    public SheduleTableModel(final int pcod, final int cpol, final String cdol) throws TException {
        sheduleList = ClientInfomat.tcl.getShedule(pcod, cpol, cdol);
    }

    @Override
    public final int getRowCount() {
        return ROW_COUNT;
    }

    @Override
    public final int getColumnCount() {
        return COLUMN_NAMES.length;
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

    private TSheduleDay findSheduleDay(final int denn) {
        for (TSheduleDay sheduleDay : sheduleList) {
            if (sheduleDay.getWeekDay() == denn) {
                return sheduleDay;
            }
        }
        return null;
    }

    private String createCellLabel(final TSheduleDay sheduleDay) {
        if (sheduleDay == null) {
            return "";
        }
        if (!isTimeStartEndSet(sheduleDay)) {
        	return "";
        } else {
        	return String.format("%s - %s", DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeStart()), DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeEnd()));
        }
    }
    
    private boolean isTimeStartEndSet(final TSheduleDay sheduleDay) {
    	final String zeroTime = "00:00";
    	return sheduleDay.isSetTimeStart() &&
    			!DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeStart()).equals(zeroTime) &&
    			sheduleDay.isSetTimeEnd() &&
    			!DEFAULT_DATE_FORMAT.format(sheduleDay.getTimeEnd()).equals(zeroTime);
    }

    @Override
    public final Object getValueAt(final int rowIndex, final int columnIndex) {
        return createCellLabel(findSheduleDay(columnIndex + 1));
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

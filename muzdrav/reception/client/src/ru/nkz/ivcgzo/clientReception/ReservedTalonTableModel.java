package ru.nkz.ivcgzo.clientReception;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.TalonNotFoundException;

public class ReservedTalonTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Talon> reservedTalons;
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy");
    private static final String[] COLUMN_NAMES = {
        "Дата приёма", "Время приёма", " Специальность" , "Ф.И.О"
    };

    public ReservedTalonTableModel(final int cpol, final String cdol, final int doctorId,
            final int patientId) {
        try {
            reservedTalons = MainForm.tcl.getReservedTalons(cpol, cdol, doctorId, patientId);
        } catch (TalonNotFoundException e) {
            reservedTalons = Collections.emptyList();
        } catch (KmiacServerException | TException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final int getRowCount() {
        return reservedTalons.size();
    }

    @Override
    public final int getColumnCount() {
        return 4;
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
                return reservedTalons.get(rowIndex).getDoctorSpec();
            case 3:
                return reservedTalons.get(rowIndex).getDoctorFio();
            case 4:
                return reservedTalons.get(rowIndex).getVidp();
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

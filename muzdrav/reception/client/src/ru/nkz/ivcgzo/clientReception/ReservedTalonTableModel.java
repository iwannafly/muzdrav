package ru.nkz.ivcgzo.clientReception;

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
    public int getRowCount() {
        return reservedTalons.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        
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

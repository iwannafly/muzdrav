package ru.nkz.ivcgzo.clientHospital;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

public class AllPatientTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<TSimplePatient> patients;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final String[] COLUMN_HEADERS = {
        "№ истории болезни", "Фамилия", "Имя", "Отчество", "Дата рождения", "Дата прибытия",
        "Номер палаты"
    };

    public AllPatientTableModel(final int pcod, final int cpodr) {
        try {
            patients = ClientHospital.tcl.getAllPatientForDoctor(pcod, cpodr);
        } catch (PatientNotFoundException e) {
            patients = Collections.<TSimplePatient>emptyList();
        } catch (TException e) {
            patients = Collections.<TSimplePatient>emptyList();
            e.printStackTrace();
        }
    }

    public AllPatientTableModel(final int cpodr) {
        try {
            patients = ClientHospital.tcl.getAllPatientFromOtd(cpodr);
        } catch (PatientNotFoundException e) {
            patients = Collections.<TSimplePatient>emptyList();
        } catch (TException e) {
            patients = Collections.<TSimplePatient>emptyList();
            e.printStackTrace();
        }
    }

    public final List<TSimplePatient> getPatientList() {
        return patients;
    }

    @Override
    public final int getRowCount() {
        return patients.size();
    }

    @Override
    public final int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public final String getColumnName(final int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public final Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return Integer.class;
            default:
                return null;
        }
    }

    @Override
    public final boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }

    @Override
    public final Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return patients.get(rowIndex).getNist();
            case 1:
                return patients.get(rowIndex).getSurname();
            case 2:
                return patients.get(rowIndex).getName();
            case 3:
                return patients.get(rowIndex).getMiddlename();
            case 4:
                return DEFAULT_DATE_FORMAT.format(new Date(patients.get(rowIndex).getBirthDate()));
            case 5:
                return DEFAULT_DATE_FORMAT.format(
                        new Date(patients.get(rowIndex).getArrivalDate()));
            case 6:
                return patients.get(rowIndex).getNpal();
            default:
                return null;
        }
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

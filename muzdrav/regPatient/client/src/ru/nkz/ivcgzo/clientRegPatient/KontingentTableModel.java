package ru.nkz.ivcgzo.clientRegPatient;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KontingentTableModel implements TableModel{
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	// ������ ��������� ���������
	private List<Kontingent> PatientKontList;
	
	public KontingentTableModel(List<Kontingent> inputPatientKontList){
		this.PatientKontList = inputPatientKontList;
	}

	public int getRowCount() {		
		return PatientKontList.size();
	}

	public int getColumnCount() {
		return 3;
	}
	// ����� ������������ �������� ������� �� ������
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		    case 0:
		        return "����";
		    case 1:
		        return "���������";
		    case 2:
		        return "������������";
	    }
	    return "��� ������� � ����� �������";
	}

	public Class<?> getColumnClass(int columnIndex) {
			return String.class;		
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0 || columnIndex == 1)
			return true;
		else	return false;
	}
	// ����� ������������ �������� ����������� �������� 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kontingent tempKont = PatientKontList.get(rowIndex);
		
		switch (columnIndex){
			case 0:
		        return DATE_FORMAT.format(tempKont.getDatau());
		    case 1:
		        return tempKont.getKateg();
		    case 2:
		        return tempKont.getName();
		}
		return "";
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

}

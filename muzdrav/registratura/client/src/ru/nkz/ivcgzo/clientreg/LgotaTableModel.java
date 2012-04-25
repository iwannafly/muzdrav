package ru.nkz.ivcgzo.clientreg;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.nkz.ivcgzo.thriftreg.PatientLgotaInfoStruct;

public class LgotaTableModel implements TableModel{
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	// ������ ��������� ���������
	private List<PatientLgotaInfoStruct> PatientLgotaList;
	
	public LgotaTableModel(List<PatientLgotaInfoStruct> inputPatientLgotaList){
		this.PatientLgotaList = inputPatientLgotaList;
	}

	public int getRowCount() {		
		return PatientLgotaList.size();
	}

	public int getColumnCount() {
		return 5;
	}
	
	// ����� ������������ �������� ������� �� ������
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		    case 0:
		        return "����";
		    case 1:
		        return "������";
		    case 2:
		        return "������������";
			case 3:
		        return "���";
		    case 4:
		        return "��/��";
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
		PatientLgotaInfoStruct tempLgota = PatientLgotaList.get(rowIndex);
		
		switch (columnIndex){
			case 0:
		        return DATE_FORMAT.format(tempLgota.getDatau());
		    case 1:
		        return tempLgota.getLgota();
		    case 2:
		        return tempLgota.getName();
			case 3:
				return tempLgota.getKov();
		    case 4:
		        return tempLgota.getPf();
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

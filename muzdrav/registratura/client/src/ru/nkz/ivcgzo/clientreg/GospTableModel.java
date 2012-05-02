package ru.nkz.ivcgzo.clientreg;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.nkz.ivcgzo.thriftreg.PatientAllGospInfoStruct;

public class GospTableModel  implements TableModel{
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	// список пациентов отделения
	private List<PatientAllGospInfoStruct> PatientGospList;
	
	public GospTableModel(List<PatientAllGospInfoStruct> inputPatientGospList){
		this.PatientGospList = inputPatientGospList;
	}

	public int getRowCount() {		
		return PatientGospList.size();
	}

	public int getColumnCount() {
		return 8;
	}
	
	// ����� ������������ �������� ������� �� ������
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		    case 0:
		        return "N ист.";
		    case 1:
		        return "Дата обращения";
		    case 2:
		        return "Отделение";
			case 3:
		        return "Диагноз";
		    case 4:
		        return "";
		    case 5:
		        return "Npasp";
		    case 6:
		        return "Ngosp";
		    case 7:
		        return "Id";
	    }
	    return "Нет столбца с таким номером";
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		PatientAllGospInfoStruct tempGosp = PatientGospList.get(rowIndex);
		
		switch (columnIndex){
			case 0:
				return tempGosp.getNist();
			case 1:
		        return DATE_FORMAT.format(tempGosp.getDatap());
		    case 2:
		        return tempGosp.getCotd();
		    case 3:
		        return tempGosp.getDiag_p();
			case 4:
				return tempGosp.getNamed_p();
		    case 5:
		        return tempGosp.getNpasp();
		    case 6:
		        return tempGosp.getNgosp();
		    case 7:
		        return tempGosp.getId();
		}
		return "";
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

}

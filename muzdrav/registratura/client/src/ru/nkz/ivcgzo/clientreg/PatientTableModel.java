package ru.nkz.ivcgzo.clientreg;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import ru.nkz.ivcgzo.thriftreg.PatientAllStruct;


public class PatientTableModel implements TableModel{

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	private List<PatientAllStruct> PatientsList;
	
	public PatientTableModel(List<PatientAllStruct> inputPatientsList){
		this.PatientsList = inputPatientsList;
	}

//	public PatientTableModel(List<PatientAllStruct> pat) {
//		// TODO Auto-generated constructor stub
//		this.PatientsList = pat;
//	}

	public int getRowCount() {		
		return PatientsList.size();
	}

	public int getColumnCount() {
		return 4;
	}

	// Метод возвращающий название столбца по номеру
	public String getColumnName(int columnIndex) {
		TableColumn column = null;
		switch (columnIndex) {
		    case 0:
		        return "Фамилия";
	//	        column.setWidth(100);
		    case 1:
		        return "Имя";
		    case 2:
		        return "Отчество";
			case 3:
		        return "ВН";
//		    case 4:
//		        return "Дата рождения";
//		    case 5:
//		        return "Полис ОМС";
//		    case 6:
//		        return "Адрес";
		    //case 8:
		      //  return "Адрес";
	    }
	    return "Нет столбца с таким номером";
	}

	// Метод возвращающий значение
	public Object getValueAt(int rowIndex, int columnIndex) {
		PatientAllStruct tempPatient = PatientsList.get(rowIndex);
		
		switch (columnIndex){
			case 0:
		        return tempPatient.getFam();
		    case 1:
		        return tempPatient.getIm();
		    case 2:
		        return tempPatient.getOt();
			case 3:
				return tempPatient.getNpasp();
//		    case 4:
//		        return DATE_FORMAT.format(tempPatient.getDatar());
//		    case 5:
//		        return tempPatient.getSpolis()+' '+tempPatient.getNpolis();
//		    case 6:
//		        return tempPatient.getAdpAddress().city+' '+tempPatient.getAdpAddress().street+' '+tempPatient.getAdpAddress().house+'-'+tempPatient.getAdpAddress().flat;				
		    //case 8:
		      //  return tempPatient.getAdmAddress().city+' '+tempPatient.getAdmAddress().street+' '+tempPatient.getAdmAddress().house;				
		}
		return "";
	}

	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 3){
			return Integer.class;
		} else {
			return String.class;		
		}
	}
	public void initColumnSizes(JTable tbl) {
		TableColumn column = null;
		for (int i = 0; i < 5; i++) {
		    column = tbl.getColumnModel().getColumn(i); 
		    if (i == 3) {
		        column.setPreferredWidth(50); 
		    } else {
		        column.setPreferredWidth(100);
		    }
		}		
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
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

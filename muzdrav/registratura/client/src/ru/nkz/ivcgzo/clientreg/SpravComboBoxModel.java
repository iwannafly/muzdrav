package ru.nkz.ivcgzo.clientreg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import ru.nkz.ivcgzo.thriftreg.SpravStruct;

public class SpravComboBoxModel implements ComboBoxModel<String>{
	private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
	// ������ �������� �� ��������������
	private List<SpravStruct> SpravList;
	private String selected=null;
	
	public SpravComboBoxModel(List<SpravStruct> inSpravList){
		this.SpravList =inSpravList; 
	}

	@Override
	public int getSize() {
		return SpravList.size();
	}

	@Override
	public String getElementAt(int index) {
		return convertSpravThriftToString(SpravList.get(index));
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);		
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected = (String) anItem;		
	}

	@Override
	public String getSelectedItem() {
		if (selected == null) {
			return  "�� ������ �� ������� �� ��������������";
		} else {
			return selected;
		}
	}
	
	private String convertSpravThriftToString(SpravStruct spr){
		String tempString;
		tempString = "["+spr.pcod+"] "+spr.name;
		return tempString;		
	}
}

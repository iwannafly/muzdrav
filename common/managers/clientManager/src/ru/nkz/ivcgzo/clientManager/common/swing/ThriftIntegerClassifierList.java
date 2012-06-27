package ru.nkz.ivcgzo.clientManager.common.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

/**
 * Параметризированный класс для работы со списками swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link IntegerClassifier}
 */
public class ThriftIntegerClassifierList extends JList<IntegerClassifier> {
	private static final long serialVersionUID = -873049270461876460L;
	private List<IntegerClassifier> items;
	private StringListModel model;
	
	/**
	 * Конструктор списка.
	 */
	public ThriftIntegerClassifierList() {
		this(new ArrayList<IntegerClassifier>());
	}
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftIntegerClassifierList(List<IntegerClassifier> list) {
		setModel();
		setData(list);
	}
	
	private void setModel() {
		model = new StringListModel();
		this.setModel(model);
	}
	
	/**
	 * Устанавливает список для отображения. 
	 */
	public void setData(List<IntegerClassifier> list) {
		items = new ArrayList<>(list.size());
		for (IntegerClassifier item : list) {
			items.add(new IntegerClassifierItem(item));
		}
		model.fireContentsChanged();
	}
	
	/**
	 * Получает выбранный объект.
	 */
	@Override
	public IntegerClassifier getSelectedValue() {
		Object selItem = super.getSelectedValue();
		
		if (selItem instanceof IntegerClassifier)
			return (IntegerClassifier) selItem;
		else
			return null;
	}
	
	/**
	 * Получает выбранный пользовательский код. 
	 */
	public int getSelectedPcod() {
		return getSelectedValue().pcod;
	}
	
	/**
	 * Устанавливает объект на основе пользовательского кода. Выбрасывает {@link RuntimeException},
	 * если такой код не найден.  
	 */
	public void setSelectedPcod(int pcod) {
		IntegerClassifier selItem = null;
		
		for (IntegerClassifier item : items) {
			if (item.pcod == pcod) {
				selItem = item;
				break;
			}
		}
		
		if (selItem == null)
			throw new RuntimeException(String.format("Unknown pcod '%s'.", pcod));
		else
			setSelectedValue(selItem, true);
	}
	
	class StringListModel extends DefaultListModel<IntegerClassifier> {
		private static final long serialVersionUID = -5142318941761484444L;

		@Override
		public IntegerClassifier getElementAt(int index) {
			return items.get(index);
		}
		
		@Override
		public int getSize() {
			return items.size();
		}
		
		public void fireContentsChanged() {
			super.fireContentsChanged(ThriftIntegerClassifierList.this, 0, getSize() - 1);
		}
	}
	
	class IntegerClassifierItem extends IntegerClassifier {
		private static final long serialVersionUID = 8395296885089622783L;

		private IntegerClassifierItem(IntegerClassifier item) {
			super(item);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;

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
	private IntegerClassifiers classifier;
	private boolean classifierLoaded;
	
	/**
	 * Конструктор списка.
	 */
	public ThriftIntegerClassifierList() {
		setData(null);
	}
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftIntegerClassifierList(List<IntegerClassifier> list) {
		this();
		setData(list);
	}
	
	/**
	 * Конструктор списка.
	 * @param classifierName - название классификатора для загрузки
	 */
	public ThriftIntegerClassifierList(IntegerClassifiers classifierName) {
		this();
		this.classifier = classifierName;
	}
	
	/**
	 * Устанавливает список для отображения. 
	 */
	public void setData(List<IntegerClassifier> list) {
		classifierLoaded = list != null;
		if (list == null)
			list = new ArrayList<>();
			
		items = new ArrayList<>(list.size());
		for (IntegerClassifier item : list) {
			items.add(new IntegerClassifierItem(item));
		}
		setModel(new StringListModel());
		selectFirstItem();
	}
	
	/**
	 * Получает список данных. 
	 */
	public List<IntegerClassifier> getData() {
		return items;
	}

	private void selectFirstItem() {
		if (items.size() > 0)
			setSelectedIndex(0);
		scrollRectToVisible(new Rectangle());
	}
	
	/**
	 * Загрузка классификатора, указанного в конструкторе
	 */
	public void loadClassifier() {
		if (!classifierLoaded)
			reloadClassifier();
	}
	
	/**
	 * Перезагрузка классификатора, указанного в конструкторе
	 */
	public void reloadClassifier() {
		if (classifier != null)
			setData(ConnectionManager.instance.getIntegerClassifier(classifier));
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
		
		@Override
		public IntegerClassifier remove(int index) {
			IntegerClassifier element = items.get(index);
			
			items.remove(index);
	        fireIntervalRemoved(this, index, index);
			
			return element;
		}
		
		@Override
		public void add(int index, IntegerClassifier element) {
			items.add(new IntegerClassifierItem(element));
			fireIntervalAdded(this, index, index);
		}
		
		@Override
		public void addElement(IntegerClassifier element) {
			add(getSize(), element);
			
		}
		
		@Override
		public IntegerClassifier set(int index, IntegerClassifier element) {
			IntegerClassifier prevElement = items.get(index);
			
			items.set(index, new IntegerClassifierItem(element));
			fireContentsChanged(this, index, index);
			
			return prevElement;
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
package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;

/**
 * Параметризированный класс для работы со списками swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link StringClassifier}
 */
public class ThriftStringClassifierList<T extends StringClassifier> extends JList<StringClassifier> {
	private static final long serialVersionUID = 677069909690689690L;
	private List<StringClassifier> items;
	private StringListModel model;
	private StringClassifiers classifier;
	private boolean classifierLoaded;
	
	/**
	 * Конструктор списка.
	 */
	public ThriftStringClassifierList() {
		setModel();
		setData(null);
	}
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftStringClassifierList(List<StringClassifier> list) {
		this();
		setData(list);
	}
	
	/**
	 * Конструктор списка.
	 * @param classifierName - название классификатора для загрузки
	 */
	public ThriftStringClassifierList(StringClassifiers classifierName) {
		this();
		this.classifier = classifierName;
	}
	
	private void setModel() {
		model = new StringListModel();
		this.setModel(model);
	}
	
	/**
	 * Устанавливает список для отображения. 
	 */
	public void setData(List<StringClassifier> list) {
		classifierLoaded = list != null;
		if (list == null)
			list = new ArrayList<>();
			
		items = new ArrayList<>(list.size());
		for (StringClassifier item : list) {
			items.add(new StringClassifierItem(item));
		}
		selectFirstItem();
		model.fireContentsChanged();
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
			setData(ConnectionManager.instance.getStringClassifier(classifier));
	}
	
	/**
	 * Получает выбранный объект.
	 */
	@Override
	public StringClassifier getSelectedValue() {
		Object selItem = super.getSelectedValue();
		
		if (selItem instanceof StringClassifier)
			return (StringClassifier) selItem;
		else
			return null;
	}
	
	/**
	 * Получает выбранный пользовательский код. 
	 */
	public String getSelectedPcod() {
		return getSelectedValue().pcod;
	}
	
	/**
	 * Устанавливает объект на основе пользовательского кода. Выбрасывает {@link RuntimeException},
	 * если такой код не найден.  
	 */
	public void setSelectedPcod(String pcod) {
		StringClassifier selItem = null;
		
		for (StringClassifier item : items) {
			if (item.pcod.equals(pcod)) {
				selItem = item;
				break;
			}
		}
		
		if (selItem == null)
			throw new RuntimeException(String.format("Unknown pcod '%s'.", pcod));
		else
			setSelectedValue(selItem, true);
	}
	
	class StringListModel extends DefaultListModel<StringClassifier> {
		private static final long serialVersionUID = -2344352512126576791L;

		@Override
		public StringClassifier getElementAt(int index) {
			return items.get(index);
		}
		
		@Override
		public int getSize() {
			return items.size();
		}
		
		public void fireContentsChanged() {
			super.fireContentsChanged(ThriftStringClassifierList.this, 0, getSize() - 1);
		}
	}
	
	class StringClassifierItem extends StringClassifier {
		private static final long serialVersionUID = -7379841009003710937L;

		private StringClassifierItem(StringClassifier item) {
			super(item);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
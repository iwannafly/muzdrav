package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

/**
 * Параметризированный класс для работы с комбобоксами swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link StringClassifier}
 */
public class ThriftStringClassifierCombobox<T extends StringClassifier> extends JComboBox<StringClassifier> {
	private static final long serialVersionUID = -8720200365221036747L;
	private List<StringClassifier> items;
	private Searcher searcher;
	private StringComboBoxModel model;
	
	/**
	 * Конструктор комбобокса.
	 * @param searcheable - включать ли поиск по первым буквам
	 */
	public ThriftStringClassifierCombobox(boolean searcheable) {
		this(searcheable, new ArrayList<StringClassifier>());
	}
	
	/**
	 * Конструктор комбобокса.
	 * @param searcheable - включать ли поиск по первым буквам
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftStringClassifierCombobox(boolean searcheable, List<StringClassifier> list) {
		if (searcheable) {
			setEditable(true);
			searcher = new Searcher();
		}
		setModel();
		setData(list);
	}
	
	private void setModel() {
		model = new StringComboBoxModel();
		this.setModel(model);
	}
	
	/**
	 * Устанавливает список для отображения. 
	 */
	public void setData(List<StringClassifier> list) {
		items = new ArrayList<>(list.size());
		for (StringClassifier item : list) {
			items.add(new StringClassifierItem(item));
		}
		model.fireContentsChanged();
	}
	
	/**
	 * Получает выбранный объект.
	 */
	@Override
	public StringClassifier getSelectedItem() {
		Object selItem = super.getSelectedItem();
		
		if (selItem instanceof StringClassifier)
			return (StringClassifier) selItem;
		else
			return null;
	}
	
	/**
	 * Получает выбранный пользовательский код. 
	 */
	public String getSelectedPcod() {
		if (getSelectedItem() != null)
			return getSelectedItem().getPcod();
		else
			return null;
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
		
		if (searcher == null)
			setSelectedItem(selItem);
		else
			try {
				searcher.setSearchEnabled(false);
				setSelectedItem(selItem);
			} finally {
				searcher.setSearchEnabled(true);
			}
		
		if (selItem == null)
			throw new RuntimeException(String.format("Unknown pcod '%s'.", pcod));
	}
	
	class StringComboBoxModel extends DefaultComboBoxModel<StringClassifier> {
		private static final long serialVersionUID = 3897339719447445357L;

		@Override
		public StringClassifier getElementAt(int index) {
			return items.get(index);
		}
		
		@Override
		public int getSize() {
			return items.size();
		}
		
		public void fireContentsChanged() {
			super.fireContentsChanged(ThriftStringClassifierCombobox.this, 0, getSize() - 1);
		}
	}
	
	class StringClassifierItem extends StringClassifier {
		private static final long serialVersionUID = -2948760587239238424L;

		private StringClassifierItem(StringClassifier item) {
			super(item);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	class Searcher implements DocumentListener {
		private ThriftStringClassifierCombobox<T> cmb = ThriftStringClassifierCombobox.this;
		private CustomComboBoxEditor editor;
		private boolean searching = false;
		private boolean enabled = false;
		private StringClassifier lastSelected;
		
		public Searcher() {
			editor = new CustomComboBoxEditor();
			ThriftStringClassifierCombobox.this.setEditor(editor);
			editor.putClientProperty("doNotCancelPopup", null);

			cmb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					editor.selectAll();
				}
			});
			setSearchEnabled(true);
		}
		
		public void setSearchEnabled(boolean searchEnabled) {
			if (searchEnabled) {
				if (!enabled)
					editor.getDocument().addDocumentListener(this);
			} else {
				editor.getDocument().removeDocumentListener(this);
			}
			enabled = searchEnabled;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			search();
		}
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			search();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
		
		private void search() {
			if (searching)
				return;
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						lastSelected = cmb.getSelectedItem();
						String editText = editor.getText();
						String editTextLow = editText.toLowerCase();
						String lastSelTextLow = (lastSelected != null) ? lastSelected.name.toLowerCase() : "";
						if (editText.length() == 0) {
							searching = true;
							cmb.setSelectedItem(null);
							return;
						} else if ((lastSelected != null) && (lastSelTextLow.indexOf(editTextLow) == 0)) {
							searching = true;
							if (!lastSelTextLow.equals(editTextLow)) {
								editor.setText(lastSelected.name);
								editor.setCaretPosition(lastSelTextLow.length());
								editor.moveCaretPosition(editTextLow.length());
							}
						} else							
							for (StringClassifier item : cmb.items) {
								if (item.name.toLowerCase().indexOf(editTextLow) == 0) {
									searching = true;
									lastSelected = item;
									lastSelTextLow = lastSelected.name.toLowerCase();
									cmb.setSelectedItem(lastSelected);
									editor.setText(lastSelected.name);
									editor.setCaretPosition(lastSelTextLow.length());
									editor.moveCaretPosition(editTextLow.length());
									break;
								}
							}
						if (!searching) {
							searching = true;
							cmb.setSelectedItem(null);
							editor.setText(editText);
							cmb.showPopup();
						} else if ((lastSelected == null))
							cmb.showPopup();
						else if (!lastSelTextLow.equals(editTextLow))
							cmb.showPopup();
					} finally {
						searching = false;
					}
				}
			});
		}
	}
	
	class CustomComboBoxEditor extends CustomTextField implements ComboBoxEditor{
		private static final long serialVersionUID = -1173671126585172816L;
		
		public CustomComboBoxEditor() {
			super(true, true, false);
			
			setBorder(new EmptyBorder(1, 2, 1, 1));
		}
		
		@Override
		public Component getEditorComponent() {
			return this;
		}

		@Override
		public Object getItem() {
			StringClassifier selItem = ThriftStringClassifierCombobox.this.getSelectedItem();
			
			if (selItem == null)
				setText(null);
			
			return selItem;
		}

		@Override
		public void setItem(Object anObject) {
			if (anObject != null)
				setText(anObject.toString());
			else
				setText(null);
		}
	}
}
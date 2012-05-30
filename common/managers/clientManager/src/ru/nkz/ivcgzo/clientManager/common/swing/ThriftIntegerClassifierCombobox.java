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

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

/**
 * Параметризированный класс для работы с комбобоксами swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link IntegerClassifier}
 */
public class ThriftIntegerClassifierCombobox<T extends IntegerClassifier> extends JComboBox<IntegerClassifier> {
	private static final long serialVersionUID = 8540397050277967316L;
	private List<IntegerClassifier> items;
	private Searcher searcher;
	
	/**
	 * Конструктор комбобокса.
	 * @param searcheable - включать ли поиск по первым буквам
	 */
	public ThriftIntegerClassifierCombobox(boolean searcheable) {
		this(searcheable, new ArrayList<IntegerClassifier>());
	}
	
	/**
	 * Конструктор комбобокса.
	 * @param searcheable - включать ли поиск по первым буквам
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftIntegerClassifierCombobox(boolean searcheable, List<IntegerClassifier> list) {
		if (searcheable) {
			setEditable(true);
			searcher = new Searcher();
		}
		setModel();
		setData(list);
	}
	
	private void setModel() {
		DefaultComboBoxModel<IntegerClassifier> model = new DefaultComboBoxModel<IntegerClassifier>() {
			private static final long serialVersionUID = 8684385138292155382L;
			
			@Override
			public IntegerClassifier getElementAt(int index) {
				return items.get(index);
			}
			
			@Override
			public int getSize() {
				return items.size();
			}
		};
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
	}
	
	/**
	 * Получает выбранный объект.
	 */
	@Override
	public IntegerClassifier getSelectedItem() {
		Object selItem = super.getSelectedItem();
		
		if (selItem instanceof IntegerClassifier)
			return (IntegerClassifier) selItem;
		else
			return null;
	}
	
	/**
	 * Получает выбранный пользовательский код. 
	 */
	public int getSelectedPcod() {
		return getSelectedItem().pcod;
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
			throw new RuntimeException(String.format("Unknown pcod '%d'.", pcod));
	}
	
	class IntegerClassifierItem extends IntegerClassifier {
		private static final long serialVersionUID = 5955074073218292470L;
		
		private IntegerClassifierItem(IntegerClassifier item) {
			super(item);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	class Searcher implements DocumentListener {
		private ThriftIntegerClassifierCombobox<T> cmb = ThriftIntegerClassifierCombobox.this;
		private CustomComboBoxEditor editor;
		private boolean searching = false;
		private boolean enabled = false;
		private IntegerClassifier lastSelected;
		
		public Searcher() {
			editor = new CustomComboBoxEditor();
			ThriftIntegerClassifierCombobox.this.setEditor(editor);
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
						if ((lastSelected != null) && (lastSelTextLow.indexOf(editTextLow) == 0)) {
							searching = true;
							if (!lastSelTextLow.equals(editTextLow)) {
								editor.setText(lastSelected.name);
								editor.setCaretPosition(lastSelTextLow.length());
								editor.moveCaretPosition(editTextLow.length());
							}
						} else							
							for (IntegerClassifier item : cmb.items) {
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
		
		@Override
		public void removeUpdate(DocumentEvent e) {
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}
	
	class CustomComboBoxEditor extends CustomTextField implements ComboBoxEditor{
		private static final long serialVersionUID = -1173671126585172816L;
		
		public CustomComboBoxEditor() {
			super();
			
			setBorder(new EmptyBorder(1, 2, 1, 1));
		}
		
		@Override
		public Component getEditorComponent() {
			return this;
		}

		@Override
		public Object getItem() {
			IntegerClassifier selItem = ThriftIntegerClassifierCombobox.this.getSelectedItem();
			
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
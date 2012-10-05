package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;

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
	private IntegerClassifiers classifier;
	private boolean classifierLoaded;
	private Searcher searcher;
	private StringComboBoxModel model;
	private boolean strict = true;
	
	/**
	 * Конструктор комбобокса с неотсортированным классификатором.
	 * @param classifierName - название классификатора для автоматической
	 * загрузки
	 */
	public ThriftIntegerClassifierCombobox(IntegerClassifiers classifierName) {
		this(classifierName, true, null);
	}
	
	/**
	 * Конструктор комбобокса.
	 * @param searcheable - включать ли поиск по первым буквам
	 */
	public ThriftIntegerClassifierCombobox(boolean searcheable) {
		this(null, searcheable, null);
	}
	
	/**
	 * Конструктор комбобокса.
	 * @param classifierName - название классификатора для автоматической
	 * загрузки
	 * @param searcheable - включать ли поиск по первым буквам
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftIntegerClassifierCombobox(IntegerClassifiers classifierName, boolean searcheable, List<IntegerClassifier> list) {
		classifier = classifierName;
		if (searcheable) {
			setEditable(true);
			searcher = new Searcher();
		}
		setModel();
		setData(list);
		if (classifier != null)
			setFormListeners();
	}
	
	private void setModel() {
		model = new StringComboBoxModel();
		this.setModel(model);
	}
	
	/**
	 * Устанавливает список для отображения. 
	 */
	public void setData(List<IntegerClassifier> list) {
		if (classifier == null)
			classifierLoaded = true;
		else
			classifierLoaded = list != null;
		if (list == null)
			list = new ArrayList<>();
			
		items = new ArrayList<>(list.size());
		for (IntegerClassifier item : list) {
			items.add(new IntegerClassifierItem(item));
		}
		setSelectedItem(null);
		model.fireContentsChanged();
	}
	
	/**
	 * Получает список данных. 
	 */
	public List<IntegerClassifier> getData() {
		return items;
	}
	
	private void setFormListeners() {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					loadClassifier();
					if (isEnabled() && items.size() > 0) {
						IntegerClassifier res = ConnectionManager.instance.showIntegerClassifierSelector(classifier);
						
						if (res != null)
							setSelectedPcod(res.pcod);
					}
				}
			}
		};
		
		if (searcher == null)
			addMouseListener(ma);
		else
			((CustomTextField) getEditor()).addMouseListener(ma);
		
		addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				loadClassifier();
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
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
		setData(ConnectionManager.instance.getIntegerClassifier(classifier));
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
	public Integer getSelectedPcod() {
		if (getSelectedItem() != null)
			return getSelectedItem().getPcod();
		else
			return null;
	}
	
	/**
	 * Устанавливает объект на основе пользовательского кода. Выбрасывает {@link RuntimeException},
	 * если такой код не найден.  
	 */
	public void setSelectedPcod(int pcod) {
		IntegerClassifier selItem = null;
		
		loadClassifier();
		
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
	
	/**
	 * Устанавливает наличие строгой проверки вводимых данных. 
	 */
	public void setStrictCheck(boolean value) {
		if (isEditable()) {
			strict = value;
		}
	}
	
	/**
	 * Получает текст из поля ввода.
	 */
	public String getText() {
		if (getSelectedItem() != null)
			return getSelectedItem().name.toUpperCase();
		else if (isEditable())
			return ((JTextComponent) getEditor().getEditorComponent()).getText();
		
		return null;
	}
	
	/**
	 * Устанавливает текст в поле ввода.
	 */
	public void setText(String text) {
		if (isEditable())
			((JTextComponent) getEditor().getEditorComponent()).setText(text);
	}
	
	class StringComboBoxModel extends DefaultComboBoxModel<IntegerClassifier> {
		private static final long serialVersionUID = 1612989647638575239L;

		@Override
		public IntegerClassifier getElementAt(int index) {
			return items.get(index);
		}
		
		@Override
		public int getSize() {
			return items.size();
		}
		
		public void fireContentsChanged() {
			super.fireContentsChanged(ThriftIntegerClassifierCombobox.this, 0, getSize() - 1);
		}
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
			search();
		}
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			search();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			search();
		}
		
		private void search() {
			if (searching)
				return;
			searching = true;
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					boolean found = false;
					try {
						lastSelected = cmb.getSelectedItem();
						String editText = editor.getText();
						String editTextLow = editText.toLowerCase();
						String lastSelTextLow = (lastSelected != null) ? lastSelected.name.toLowerCase() : "";
						loadClassifier();
						if (editText.length() == 0) {
							found = true;
							cmb.setSelectedItem(null);
							return;
						} else if ((lastSelected != null) && (lastSelTextLow.indexOf(editTextLow) == 0)) {
							found = true;
							if (!lastSelTextLow.equals(editTextLow)) {
								editor.setText(lastSelected.name);
								setCaretPosition(lastSelTextLow.length(), editTextLow.length());
							}
						} else							
							for (IntegerClassifier item : cmb.items) {
								if (item.name.toLowerCase().indexOf(editTextLow) == 0) {
									found = true;
									lastSelected = item;
									lastSelTextLow = lastSelected.name.toLowerCase();
									cmb.setSelectedItem(lastSelected);
									editor.setText(lastSelected.name);
									setCaretPosition(lastSelTextLow.length(), editTextLow.length());
									break;
								}
							}
						if (!found) {
							cmb.setSelectedItem(null);
							editor.setText(editText);
							if (cmb.items.size() > 0)
								cmb.showPopup();
						} else if ((lastSelected == null) && (cmb.items.size() > 0))
							cmb.showPopup();
						else if (!lastSelTextLow.equals(editTextLow) && (cmb.items.size() > 0))
							cmb.showPopup();
					} finally {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								searching = false;
							}
						});
					}
				}
				
				private void setCaretPosition(final int pos, final int len) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							editor.setCaretPosition(pos);
							editor.moveCaretPosition(len);
						}
					});
				}
			});
		}
	}
	
	class CustomComboBoxEditor extends CustomTextField implements ComboBoxEditor{
		private static final long serialVersionUID = -1173671126585172816L;
		
		public CustomComboBoxEditor() {
			super(true, true, true);
			
			setBorder(new EmptyBorder(1, 1, 1, 1));
		}
		
		@Override
		public Component getEditorComponent() {
			return this;
		}
		
		@Override
		public Object getItem() {
			IntegerClassifier selItem = ThriftIntegerClassifierCombobox.this.getSelectedItem();
			
			if (selItem == null && strict)
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
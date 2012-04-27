package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

public class ThriftIntegerClassifierCombobox<T extends IntegerClassifier> extends JComboBox<IntegerClassifier> {
	private static final long serialVersionUID = 8540397050277967316L;
	private List<IntegerClassifier> items;
	
	public ThriftIntegerClassifierCombobox(boolean searcheable) {
		this(searcheable, new ArrayList<IntegerClassifier>());
	}
	
	public ThriftIntegerClassifierCombobox(boolean searcheable, List<IntegerClassifier> list) {
		if (searcheable) {
			setEditable(true);
			new Searcher();
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
	
	public void setData(List<IntegerClassifier> list) {
		items = new ArrayList<>(list.size());
		for (IntegerClassifier item : list) {
			items.add(new IntegerClassifierItem(item));
		}
	}
	
	@Override
	public IntegerClassifier getSelectedItem() {
		Object selItem = super.getSelectedItem();
		
		if (selItem instanceof IntegerClassifier)
			return (IntegerClassifier) selItem;
		else
			return null;
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
		private JTextField editor = (JTextField) getEditor().getEditorComponent();
		private boolean searching = false;
		private IntegerClassifier lastSelected;
		
		public Searcher() {
			cmb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					editor.selectAll();
				}
			});
			editor.getDocument().addDocumentListener(this);
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			if (searching)
				return;
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						if (!cmb.isPopupVisible())
							cmb.showPopup();
						String str = editor.getText().toLowerCase();
						if ((lastSelected != null) && (lastSelected.name.toLowerCase().indexOf(str) == 0)) {
							searching = true;
							editor.setText(lastSelected.name);
							editor.select(str.length(), lastSelected.name.length());
						} else							
							for (IntegerClassifier item : cmb.items) {
								if (item.name.toLowerCase().indexOf(str) == 0) {
									searching = true;
									lastSelected = item;
									cmb.setSelectedItem(lastSelected);
									editor.setText(lastSelected.name);
									editor.select(str.length(), lastSelected.name.length());
									break;
								}
							}
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
}
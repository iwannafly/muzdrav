package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;

public class TableComboBoxStringEditor extends ThriftStringClassifierCombobox<StringClassifier> implements TableCellEditor {
	private static final long serialVersionUID = 7798392803203166908L;
	private TableComboBoxIntegerRender rnd;
	private AbstractCellEditor dce;
	private CustomTable<?, ?> ctb;
	private CustomTextField txt;

	public TableComboBoxStringEditor(StringClassifiers classifierName, boolean searcheable, List<StringClassifier> list) {
		super(classifierName, searcheable, list);
		
		rnd = new TableComboBoxIntegerRender();
		dce = new AbstractCellEditor() {
			private static final long serialVersionUID = -871983044315064563L;

			@Override
			public Object getCellEditorValue() {
				if (getSelectedIndex() < 0)
					return null;
				else
					return itemsBcp.get(getIdx(getSelectedItem().pcod)).pcod;
			}
		};
		
		txt = (CustomTextField) getEditor().getEditorComponent();
		txt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctb.dispatchEvent(new KeyEvent(ctb, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
			}
		});
		txt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (!ctb.hasFocus())
							stopCellEditing();
					}
				});
			}
		});
	}
	
	@Override
	public Object getCellEditorValue() {
		return dce.getCellEditorValue();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent)anEvent).getClickCount() > 1;
		}
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return dce.shouldSelectCell(anEvent);
	}

	@Override
	public boolean stopCellEditing() {
		return dce.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		dce.cancelCellEditing();
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		dce.addCellEditorListener(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		dce.removeCellEditorListener(l);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			setSelectedIndex(-1);
		} else {
			items = itemsBcp;
			setSelectedIndex(getIdx((String) value));
			if (getSelectedItem() != null)
				setText(getSelectedItem().name);
			else
				setText(null);
		}
		
		return this;
	}
	
	public Integer getIdx(String pcod) {
		for (int i = 0; i < itemsBcp.size(); i++)
			if (itemsBcp.get(i).pcod.equals(pcod))
				return i;
		
		return -1;
	}
	
	public TableComboBoxIntegerRender getRender() {
		return rnd;
	}
	
	public class TableComboBoxIntegerRender extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 9082746977401450306L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			ctb = (CustomTable<?, ?>) table;
			
			JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if (value == null)
				lbl.setText("");
			else {
				int idx = getIdx((String) value);
				if (idx < 0)
					lbl.setText(value.toString());
				else
					lbl.setText(itemsBcp.get(idx).name);
			}
			
			return lbl;
		}
	}
}

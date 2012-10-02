package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

/**
 * Класс, представляющий собой компонент для редактирования классификаторов,
 * заменяющий стандартное поле в <code>CustomTable<code>.
 * @author bsv798
 * @see CustomTable
 */
public class TableComboBoxIntegerEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = -1007012035130398318L;
	private final JComboBox<String> cmb;
	private List<IntegerClassifier> lst;
	private TableComboBoxIntegerRender rnd;
	private Map<Integer, Integer> pcd;
	
	public TableComboBoxIntegerEditor(List<IntegerClassifier> list) {
		cmb = new JComboBox<>();
		cmb.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        cmb.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 8883313793725297972L;

			public void actionPerformed(ActionEvent e) {
				cmb.transferFocus();
            }
        });
		rnd = new TableComboBoxIntegerRender();
		setModel(list);
	}
	
	public TableComboBoxIntegerRender getRender() {
		return rnd;
	}
	
	public Integer getIdx(int pcod) {
		if (pcd.containsKey(pcod))
			return pcd.get(pcod);
		else
			return -1;
	}
	
	private void idx2pcod() {
		pcd = new HashMap<>();
		for (int i = 0; i < lst.size(); i++)
			pcd.put(lst.get(i).pcod, i);
	}
	
	private void setModel(List<IntegerClassifier> list) {
		lst = list;
		idx2pcod();
		cmb.setModel(new DefaultComboBoxModel<String>() {
			private static final long serialVersionUID = 5904161020166672433L;
			
			@Override
			public String getElementAt(int arg0) {
				return lst.get(arg0).name;
			}
			
			@Override
			public int getSize() {
				return lst.size();
			}
		});
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		if (arg1 == null)
			cmb.setSelectedIndex(-1);
		else
			cmb.setSelectedIndex(getIdx((int) arg1));
		
		return cmb;
	}
	
	@Override
	public Integer getCellEditorValue() {
		if (cmb.getSelectedIndex() < 0)
			return null;
		else
			return lst.get(cmb.getSelectedIndex()).pcod;
	}
	
	public Component getEditor() {
		return cmb;
	}
	
	public class TableComboBoxIntegerRender extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -2915705885392742240L;
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if (value == null)
				lbl.setText("");
			else {
				int idx = getIdx((int) value);
				if (idx < 0)
					lbl.setText(value.toString());
				else
					lbl.setText(cmb.getItemAt(idx));
			}
			
			return lbl;
		}
	}
}

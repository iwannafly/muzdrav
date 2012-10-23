package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Класс, представляющий собой компонент для редактирования дат, заменяющий
 * стандартное поле в {@link CustomTable}.
 * @author bsv798
 */
public class TableDateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6671230063586063616L;
	private CustomDateEditor txt;
	private CustomTable<?, ?> ctb;
	private TableDateRenderer renderer;
	private static Border blackBorder = new LineBorder(Color.black);
	
	public TableDateEditor() {
		super(new CustomDateEditor());
		
		txt = (CustomDateEditor) this.getComponent();
		
		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ctb.dispatchEvent(new KeyEvent(ctb, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
				} else {
					super.keyPressed(e);
				}
			}
		});
		txt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				stopCellEditing();
			}
		});
		renderer = new TableDateRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getDate();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		ctb = (CustomTable<?, ?>) arg0;
		
		txt.setBorder(blackBorder);
		txt.setValue((arg1 == null) ? null : txt.dateFormatter.format(arg1));
		return txt;
	}
	
	public TableDateRenderer getRenderer() {
		return renderer;
	}
	
	class TableDateRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -6219585518858657636L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			lbl.setText((value == null) ? null : txt.dateFormatter.format(value));
			
			return lbl;
		}
	}
}

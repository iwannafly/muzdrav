package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Класс, представляющий собой компонент для редактирования времени, заменяющий
 * стандартное поле в {@link CustomTable}.
 * @author bsv798
 */
public class TableTimeEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 2570879157508581937L;
	private CustomTimeEditor txt;
	private CustomTable<?, ?> ctb;
	private TableTimeRenderer renderer;
	private static Border blackBorder = new LineBorder(Color.black);
	
	public TableTimeEditor() {
		super(new CustomTimeEditor());
		
		txt = (CustomTimeEditor) this.getComponent();
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
		renderer = new TableTimeRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getTime();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		ctb = (CustomTable<?, ?>) arg0;
		txt.setBorder(blackBorder);
		txt.setValue((arg1 == null) ? null : txt.timeFormatter.format(arg1));
		return txt;
	}
	
	public TableTimeRenderer getRenderer() {
		return renderer;
	}
	
	class TableTimeRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 4605047196383309621L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			lbl.setText((value == null) ? null : txt.timeFormatter.format(value));
			
			return lbl;
		}
	}
}

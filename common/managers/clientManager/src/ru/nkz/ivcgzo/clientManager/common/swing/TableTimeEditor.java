package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;

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
	private TableTimeRenderer renderer;
	private static Border blackBorder = new LineBorder(Color.black);
	private static Border redBorder = new LineBorder(Color.red);
	
	public TableTimeEditor() {
		super(new CustomTimeEditor());
		
		txt = (CustomTimeEditor) this.getComponent();
		renderer = new TableTimeRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getTime();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		txt.setBorder(blackBorder);
		txt.setValue((arg1 == null) ? null : txt.timeFormatter.format(arg1));
		return txt;
	}
	
	@Override
	public boolean stopCellEditing() {
			try {
				if (txt.getText().equals(txt.placeHolder))
					txt.setValue(null);	
				else if (txt.getTime() == null)
					throw new ParseException(null, 0);
				txt.setBorder(blackBorder);
				return super.stopCellEditing();
			} catch (ParseException e) {
			}
		txt.setBorder(redBorder);
		return false;
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

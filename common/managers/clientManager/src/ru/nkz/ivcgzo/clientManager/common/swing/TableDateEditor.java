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
 * Класс, представляющий собой компонент для редактирования дат, заменяющий
 * стандартное поле в {@link CustomTable}.
 * @author bsv798
 */
public class TableDateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6671230063586063616L;
	private CustomDateEditor txt;
	private TableDateRenderer renderer;
	private static Border blackBorder = new LineBorder(Color.black);
	private static Border redBorder = new LineBorder(Color.red);
	
	public TableDateEditor() {
		super(new CustomDateEditor());
		
		txt = (CustomDateEditor) this.getComponent();
		renderer = new TableDateRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getDate();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		txt.setBorder(blackBorder);
		txt.setValue((arg1 == null) ? null : txt.dateFormatter.format(arg1));
		return txt;
	}
	
	@Override
	public boolean stopCellEditing() {
			try {
				if (txt.getText().equals(txt.placeHolder))
					txt.setValue(null);	
				else if (txt.getDate() == null)
					throw new ParseException(null, 0);
				txt.setBorder(blackBorder);
				return super.stopCellEditing();
			} catch (ParseException e) {
			}
		txt.setBorder(redBorder);
		return false;
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

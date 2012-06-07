package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Класс, представляющий собой компонент для редактирования чисел, заменяющий
 * стандартное поле в {@link CustomTable}.
 * @author bsv798
 */
public class TableNumberEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6671230063586063616L;
	private CustomNumberEditor txt;
	private TableNumberRenderer renderer;
	private Class<?> editorClass;
	private static Border blackBorder = new LineBorder(Color.black);
	private static Border redBorder = new LineBorder(Color.red);
	
	public TableNumberEditor() {
		super(new CustomNumberEditor());
		
		txt = (CustomNumberEditor) this.getComponent();
		txt.setHorizontalAlignment(JTextField.RIGHT);
		renderer = new TableNumberRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getNumber(editorClass);
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		editorClass = arg0.getColumnClass(arg4);
		
		txt.setBorder(blackBorder);
		txt.setText((arg1 == null) ? null : arg1.toString());
		
		return txt;
	}
	
	@Override
	public boolean stopCellEditing() {
			try {
				if (txt.getText().length() > 0)
					if (txt.getNumber(editorClass) == null)
						throw new ParseException(null, 0);
				txt.setBorder(blackBorder);
				return super.stopCellEditing();
			} catch (ParseException e) {
			}
		txt.setBorder(redBorder);
		return false;
	}
	
	public TableNumberRenderer getRenderer() {
		return renderer;
	}
	
	class TableNumberRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 4372440185602130078L;
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			lbl.setHorizontalAlignment(JLabel.RIGHT);
			lbl.setText((value == null) ? null : value.toString().replace(',', '.'));
			
			return lbl;
		}
	}
}

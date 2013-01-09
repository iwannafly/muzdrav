package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
	private CustomTable<?, ?> ctb;
	private static Border blackBorder = new LineBorder(Color.black);
	
	public TableNumberEditor() {
		super(new CustomNumberEditor());
		
		txt = (CustomNumberEditor) this.getComponent();
		txt.setHorizontalAlignment(JTextField.RIGHT);
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
		renderer = new TableNumberRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		return txt.getNumber(editorClass);
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		ctb = (CustomTable<?, ?>) arg0;
		editorClass = arg0.getColumnClass(arg4);
		
		txt.setBorder(blackBorder);
		txt.setText((arg1 == null) ? null : arg1.toString());
		
		return txt;
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

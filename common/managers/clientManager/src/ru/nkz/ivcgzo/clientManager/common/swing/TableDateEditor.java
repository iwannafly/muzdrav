package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.text.DateFormatter;

/**
 * Класс, представляющий собой компонент для редактирования дат, заменяющий
 * стандартное поле в <code>CustomTable<code>.
 * @author bsv798
 * @see CustomTable
 */
public class TableDateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6671230063586063616L;
	private JFormattedTextField txt;
	
	public TableDateEditor() {
		super(new JFormattedTextField(DateFormat.getDateInstance(DateFormat.MEDIUM)));
		
		txt = (JFormattedTextField) this.getComponent();
		((DateFormat) ((DateFormatter) txt.getFormatter()).getFormat()).setLenient(false);
        txt.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        txt.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 8883313793725297972L;

			public void actionPerformed(ActionEvent e) {
                    txt.postActionEvent();
            }
        });
	}
	
	@Override
	public Object getCellEditorValue() {
		if (txt.getText().length() == 0)
			return null;
		else
			return txt.getValue();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		txt.setBorder(new LineBorder(Color.black));
		txt.setValue(arg1);
		return txt;
	}
	
	@Override
	public boolean stopCellEditing() {
		if (txt.isEditValid() || txt.getText().length() == 0) {
			try {
				txt.commitEdit();
			} catch (ParseException e) {
			}
			return super.stopCellEditing();
		} else {
			txt.setBorder(new LineBorder(Color.red));
			return false;
		}
	}
}

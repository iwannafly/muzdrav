package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

/**
 * Класс, представляющий собой компонент для редактирования дат, заменяющий
 * стандартное поле в <code>CustomTable<code>.
 * @author bsv798
 * @see CustomTable
 */
public class TableDateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6671230063586063616L;
	private JFormattedTextField txt;
	private TableDateRenderer renderer;
	private SimpleDateFormat dateFormatter;
	private char dateSeparator;
	private char placeHolderChar = '_';
	private String placeHolder = "";
	
	public TableDateEditor() {
		super(new JFormattedTextField(new MaskFormatter()));
		
		txt = (JFormattedTextField) this.getComponent();
		renderer = new TableDateRenderer();
		dateFormatter = new SimpleDateFormat(convertDatePattern(((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT)).toPattern()));
		try {
			MaskFormatter maskFormatter = (MaskFormatter) txt.getFormatter();
			String mask = dateFormatter.toPattern();
			mask = mask.replace('d', '#').replace('M', '#').replace('y', '#');
			maskFormatter.setMask(mask);
			maskFormatter.setPlaceholderCharacter(placeHolderChar);
			placeHolder = mask.replace('#', placeHolderChar);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		dateFormatter.setLenient(false);
		
        txt.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        txt.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 8883313793725297972L;

			public void actionPerformed(ActionEvent e) {
                    txt.postActionEvent();
            }
        });
        txt.addCaretListener(new TableDateSelector());
	}
	
	private String convertDatePattern(String pattern)
	{
		if (pattern.indexOf('/') > -1)
			dateSeparator = '/';
		else if (pattern.indexOf('-') > -1)
			dateSeparator = '-';
		else
			dateSeparator = '.';

		pattern = pattern.replaceAll("dd", "d");
		pattern = pattern.replaceAll("MM", "M");
		pattern = pattern.replaceAll("yy", "y");
		pattern = pattern.replaceAll("yy", "y");
		pattern = pattern.replaceAll("d", "dd");
		pattern = pattern.replaceAll("M", "MM");
		pattern = pattern.replaceAll("y", "yyyy");
		
		return pattern;
	}
	
	@Override
	public Object getCellEditorValue() {
		try {
			if (!txt.getText().equals(placeHolder))
				return dateFormatter.parse(txt.getText());
		} catch (ParseException e) {
		}
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		txt.setBorder(new LineBorder(Color.black));
		txt.setValue((arg1 == null) ? null : dateFormatter.format(arg1));
		return txt;
	}
	
	@Override
	public boolean stopCellEditing() {
			try {
				if (txt.getText().equals(placeHolder))
					txt.setValue(null);	
				else if (txt.getText().indexOf(placeHolderChar) < 0)
					dateFormatter.parse(txt.getText());
				else
					throw new ParseException(null, 0);
				txt.setBorder(new LineBorder(Color.black));
				return super.stopCellEditing();
			} catch (ParseException e) {
			}
		txt.setBorder(new LineBorder(Color.red));
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
			
			lbl.setText((value == null) ? null : dateFormatter.format(value));
			
			return lbl;
		}
	}
	
	class TableDateSelector implements CaretListener {
		private boolean updating = false;
		
		public TableDateSelector() {
			txt.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						int pos = txt.getCaretPosition();
						
						if (pos == txt.getText().length()) {
							return;
						}
						
						while (pos > 0) {
							if (txt.getText().charAt(pos) != dateSeparator)
								break;
							pos--;
						}
						
						if (pos > 0)
							txt.setCaretPosition(pos);
						else if (txt.getText() != null)
							txt.setCaretPosition(0);
					}
					super.keyPressed(e);
				}
			});
		}
		
		@Override
		public synchronized void caretUpdate(CaretEvent e) {
			if (updating)
				return;
			
			if (e.getDot() == e.getMark())
				if (e.getDot() != txt.getText().length()) {
					updating = true;
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							int dot = txt.getCaretPosition();
							if (dot < txt.getText().length()) {
								txt.setCaretPosition(dot + 1);
								txt.moveCaretPosition(dot);
							}
							updating = false;
						}
					});
				}
		}
	}
}

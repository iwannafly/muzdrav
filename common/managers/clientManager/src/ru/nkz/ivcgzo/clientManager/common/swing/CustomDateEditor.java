package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * Текстовое поле для ввода дат, обернутое в {@link CustomTextComponentWrapper}.
 * @author bsv798
 */
public class CustomDateEditor extends JFormattedTextField {
	private static final long serialVersionUID = -817488987418629780L;
	private CustomTextComponentWrapper ctcWrapper;
	private JFormattedTextField txt;
	protected SimpleDateFormat dateFormatter;
	protected char dateSeparator;
	protected char placeHolderChar = '_';
	protected String placeHolder = "";
	
	public CustomDateEditor() {
		this(true, true);
		
		dateFormatter = new SimpleDateFormat(convertDatePattern(((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT)).toPattern()));
		try {
			txt = this;
			String mask = dateFormatter.toPattern();
			mask = mask.replace('d', '#').replace('M', '#').replace('y', '#');
			MaskFormatter maskFormatter = new MaskFormatter(mask);
			maskFormatter.setPlaceholderCharacter(placeHolderChar);
			txt.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
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
		this.addCaretListener(new TableDateSelector());
	}
	
	public CustomDateEditor(boolean selectOnFocus, boolean popupMenu) {
		super();
		
		ctcWrapper = new CustomTextComponentWrapper(this);
		
		if (selectOnFocus)
			ctcWrapper.setTextSelectionOnFocus();
		if (popupMenu)
			ctcWrapper.setPopupMenu();
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
								if (dot == 0) {
									txt.setCaretPosition(0);
									txt.moveCaretPosition(1);
								} else {
									txt.setCaretPosition(dot + 1);
									txt.moveCaretPosition(dot);
								}
							}
							updating = false;
						}
					});
				}
		}
	}
	
	public Date getDate() {
		try {
			if (txt.getText().indexOf(placeHolderChar) == -1)
				return dateFormatter.parse(txt.getText());
		} catch (ParseException e) {
		}
		return null;
	}
}

package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * Текстовое поле для ввода времени, обернутое в {@link CustomTextComponentWrapper}.
 * @author bsv798
 */
public class CustomTimeEditor extends JFormattedTextField {
	private static final long serialVersionUID = 834656272032185057L;
	private CustomTextComponentWrapper ctcWrapper;
	private JFormattedTextField txt;
	protected SimpleDateFormat timeFormatter;
	protected char timeSeparator;
	protected char placeHolderChar = '_';
	protected String placeHolder = "";
	
	public CustomTimeEditor() {
		this(true, true);
	}
	
	public CustomTimeEditor(boolean selectOnFocus, boolean popupMenu) {
		super();
		
		ctcWrapper = new CustomTextComponentWrapper(this);
		
		if (selectOnFocus)
			ctcWrapper.setTextSelectionOnFocus();
		if (popupMenu)
			ctcWrapper.setPopupMenu();
		
		timeFormatter = new SimpleDateFormat(convertTimePattern(((SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT)).toPattern()));
		try {
			txt = this;
			String mask = timeFormatter.toPattern();
			mask = mask.replace('H', '#').replace('m', '#');
			MaskFormatter maskFormatter = new MaskFormatter(mask);
			maskFormatter.setPlaceholderCharacter(placeHolderChar);
			txt.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
			placeHolder = mask.replace('#', placeHolderChar);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		timeFormatter.setLenient(false);
		
		this.addCaretListener(new TableTimeSelector());
	}
	
	public CustomTimeEditor(Date time) {
		this();
		
		setTime(time);
	}
	
	public CustomTimeEditor(long mills) {
		this();
		
		setTime(mills);
	}

	public CustomTimeEditor(String time) {
		this();
		
		setTime(time);
	}
	
	private String convertTimePattern(String pattern)
	{
		if (pattern.indexOf(':') > -1)
			timeSeparator = ':';
		else
			timeSeparator = '.';

		pattern = "HH" + timeSeparator + "mm";
		
		return pattern;
	}
	
	@Override
	public void commitEdit() {
		Date time = getTime();
		
		if (time != null)
			setValue(timeFormatter.format(time));
		else
			setValue(null);
	}
	
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		if (pressed && (condition == JComponent.WHEN_FOCUSED) && (e.getKeyCode() == KeyEvent.VK_ENTER) && (e.getModifiers() == 0))
			return processKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_TAB, KeyEvent.CHAR_UNDEFINED), condition, pressed);
		else
			return super.processKeyBinding(ks, e, condition, pressed);
	}
	
	class TableTimeSelector implements CaretListener {
		private boolean updating = false;
		
		public TableTimeSelector() {
			txt.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						int pos = txt.getCaretPosition();
						
						if (pos == txt.getText().length()) {
							return;
						}
						
						while (pos > 0) {
							if (txt.getText().charAt(pos) != timeSeparator)
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
	
	public Date getTime() {
		try {
			if (txt.getText().indexOf(placeHolderChar) == -1)
				return timeFormatter.parse(txt.getText());
		} catch (ParseException e) {
		}
		
		return null;
	}
	
	public void setTime(Date time) {
		if (time != null)
			setValue(timeFormatter.format(time));
		else
			setValue(null);
	}
	
	public void setTime(long mills) {
		setTime(new Date(mills));
	}
	
	public void setTime(String time) {
		try {
			setTime(timeFormatter.parse(time));
		} catch (Exception e) {
			setValue(null);
		}
	}
}

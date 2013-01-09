package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JCalendar;

/**
 * Текстовое поле для ввода дат, обернутое в {@link CustomTextComponentWrapper}.
 * @author bsv798
 */
public class CustomDateEditor extends JFormattedTextField {
	private static final long serialVersionUID = -817488987418629780L;
	private CustomTextComponentWrapper ctcWrapper;
	private JFormattedTextField txt;
	private static JCalendar calendar;
	private static JPopupMenu popCal;
	private static JComboBox<?> cmbCal;
	private static boolean dateSelected;
	private static CustomDateEditor calCaller;
	protected JButton btnCal;
	protected SimpleDateFormat dateFormatter;
	protected char dateSeparator;
	protected char placeHolderChar = '_';
	protected String placeHolder = "";
	
	public CustomDateEditor() {
		this(true, true);
	}
	
	public CustomDateEditor(boolean selectOnFocus, boolean popupMenu) {
		super();
		
		initCalendar();
		insertCalendarButton();
		
		ctcWrapper = new CustomTextComponentWrapper(this);
		
		if (selectOnFocus)
			ctcWrapper.setTextSelectionOnFocus();
		if (popupMenu)
			ctcWrapper.setPopupMenu();
		
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
		
		this.addCaretListener(new TableDateSelector());
	}
	
	private void insertCalendarButton() {
		btnCal = new JButton(new ImageIcon(CustomDateEditor.class.getResource("/com/toedter/calendar/images/JCalendarColor16.gif")));
		btnCal.setCursor(Cursor.getDefaultCursor());
		btnCal.setFocusable(false);
		btnCal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calCaller = CustomDateEditor.this;
				if (CustomDateEditor.this.getDate() != null)
					calendar.setDate(CustomDateEditor.this.getDate());
				popCal.show(CustomDateEditor.this, CustomDateEditor.this.getWidth() - (int) popCal.getPreferredSize().getWidth(), CustomDateEditor.this.getHeight());
				dateSelected = false;
			}
		});
		add(btnCal);
		
		setLayout(new CustomDateEditorLayoutManager());
	}
	
	private static void initCalendar() {
		if (calendar != null)
			return;
		
		calendar = new JCalendar();
		calendar.setTodayButtonVisible(true);
		calendar.getDayChooser().setDayBordersVisible(true);
		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		cmbCal = (JComboBox<?>) calendar.getMonthChooser().getComboBox();
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				 if (evt.getPropertyName().equals("day")) {
					 calCaller.setDate(calendar.getDate());
					dateSelected = true;
					popCal.setVisible(false);
					calCaller.requestFocusInWindow();
				 }
			}
		});
		
		popCal = new JPopupMenu() {
			private static final long serialVersionUID = -6078272560337577761L;

			public void setVisible(boolean b) {
				Boolean isCanceled = (Boolean) getClientProperty("JPopupMenu.firePopupMenuCanceled");
				if (b || (!b && dateSelected) || ((isCanceled != null) && !b && isCanceled.booleanValue())) {
					super.setVisible(b);
				}
			}
		};
		popCal.add(calendar);
		
		MenuSelectionManager.defaultManager().addChangeListener(new ChangeListener() {
			boolean hasListened = false;

			public void stateChanged(ChangeEvent e) {
				if (hasListened) {
					hasListened = false;
					return;
				}
				if (popCal.isVisible() && cmbCal.hasFocus()) {
					MenuElement[] me = MenuSelectionManager.defaultManager().getSelectedPath();
					MenuElement[] newMe = new MenuElement[me.length + 1];
					newMe[0] = popCal;
					for (int i = 0; i < me.length; i++) {
						newMe[i + 1] = me[i];
					}
					hasListened = true;
					MenuSelectionManager.defaultManager().setSelectedPath(newMe);
				}
			}
		});
	}
	
	public CustomDateEditor(Date date) {
		this();
		
		setDate(date);
	}
	
	public CustomDateEditor(long mills) {
		this();
		
		setDate(mills);
	}

	public CustomDateEditor(String date) {
		this();
		
		setDate(date);
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
	public void commitEdit() throws ParseException {
		Date date = getDate();
		
		if (date != null)
			setValue(dateFormatter.format(date));
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
	
	class CustomDateEditorLayoutManager implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return null;
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return null;
		}

		@Override
		public void layoutContainer(Container parent) {
			btnCal.setBounds(parent.getWidth() - parent.getHeight(), 0, parent.getHeight(), parent.getHeight());
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
	
	public Long getTime() {
		Date d = getDate();
		
		if (d != null)
			return d.getTime();
		
		return null;
	}
	
	public void setDate(Date date) {
		if (date != null)
			setValue(dateFormatter.format(date));
		else
			setValue(null);
	}
	
	public void setDate(long mills) {
		setDate(new Date(mills));
	}
	
	public void setDate(String date) {
		try {
			setDate(dateFormatter.parse(date));
		} catch (Exception e) {
			setValue(null);
		}
	}
	
	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}
}

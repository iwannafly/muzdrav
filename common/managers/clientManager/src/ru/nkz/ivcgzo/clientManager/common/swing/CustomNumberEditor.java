package ru.nkz.ivcgzo.clientManager.common.swing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Текстовое поле для ввода чисел, обернутое в {@link CustomTextComponentWrapper}.
 * @author bsv798
 */
public class CustomNumberEditor extends CustomTextField {
	private static final long serialVersionUID = -2201929356310916795L;
	
	public CustomNumberEditor() {
		this(true, true);
	}
	
	public CustomNumberEditor(boolean selectOnFocus, boolean popupMenu) {
		this.setDocument(new NumberDocument());
	}
	
	public Number getNumber() {
		try {
			return Short.parseShort(this.getText());
		} catch (NumberFormatException e) {
		}
		
		try {
			return Integer.parseInt(this.getText());
		} catch (NumberFormatException e) {
		}
		
		return null;
	}
	
	public Number getNumber(Class<?> cl) {
		try {
			if (Short.class == cl)
				return Short.parseShort(this.getText());
			else if (Integer.class == cl)
				return Integer.parseInt(this.getText());
			else if (Long.class == cl)
				return Long.parseLong(this.getText());
			else if (Float.class == cl)
				return Float.parseFloat(this.getText());
			else if (Double.class == cl)
				return Double.parseDouble(this.getText());
			else
				return null;

		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	class NumberDocument extends PlainDocument  {
		private static final long serialVersionUID = 2424359267532412904L;
		
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			str = str.replace(',', '.');
			String fullStr = getText(0, offs) + str + getText(offs, getLength() - offs);
			
			try {
				Short.parseShort(fullStr);
				super.insertString(offs, str, a);
				return;
			} catch (NumberFormatException e) {
			}
			
			try {
				Float.parseFloat(fullStr);
				super.insertString(offs, str, a);
				return;
			} catch (NumberFormatException e) {
			}
		}
	}
}

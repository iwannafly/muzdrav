package ru.nkz.ivcgzo.clientManager.common.swing;

import javax.swing.JTextField;

/**
 * Текстовое поле, обернутое в {@link CustomTextComponentWrapper}.
 * @author bsv798
 */
public class CustomTextField extends JTextField {
	private static final long serialVersionUID = 1861478369203689770L;
	private CustomTextComponentWrapper ctcWrapper;
	
	public CustomTextField() {
		this(true, true);
	}
	
	public CustomTextField(boolean selectOnFocus, boolean popupMenu) {
		super();
		
		ctcWrapper = new CustomTextComponentWrapper(this);
		
		if (selectOnFocus)
			ctcWrapper.setTextSelectionOnFocus();
		if (popupMenu)
			ctcWrapper.setPopupMenu();
	}
}

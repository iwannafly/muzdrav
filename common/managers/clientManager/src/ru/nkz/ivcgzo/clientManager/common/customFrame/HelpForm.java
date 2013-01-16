package ru.nkz.ivcgzo.clientManager.common.customFrame;

import javax.swing.JDialog;

public class HelpForm extends JDialog {
	private static final long serialVersionUID = -3095501803314345336L;
	
	public HelpForm() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Просмотр справки");
		
	}
}

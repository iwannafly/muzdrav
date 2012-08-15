package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Toolkit;

import javax.swing.JFrame;

import sun.awt.ModalityEvent;
import sun.awt.ModalityListener;
import sun.awt.SunToolkit;

public abstract class ModalForm extends JFrame {
	private static final long serialVersionUID = -1348144141516475969L;
	
	protected ModalityListener modListener;
	protected boolean resultsAccepted;
	protected Object results;
	
	public void setModalityListener() {
		if (modListener == null)
			if (Toolkit.getDefaultToolkit() instanceof SunToolkit) {
				modListener = new ModalityListener() {
					
					@Override
					public void modalityPushed(ModalityEvent arg0) {
					}
					
					@Override
					public void modalityPopped(ModalityEvent arg0) {
						if (!resultsAccepted)
							results = null;
					
						resultsAccepted = false;
					}
				};
				
				((SunToolkit) Toolkit.getDefaultToolkit()).addModalityListener(modListener);
			}
	}
	
	public void removeModalityListener() {
		if (modListener != null)
			if (Toolkit.getDefaultToolkit() instanceof SunToolkit) {
				((SunToolkit)Toolkit.getDefaultToolkit()).removeModalityListener(modListener);
				modListener = null;
			}
	}
	
	public void acceptResults() {
		resultsAccepted = true;
		
		setVisible(false);
	}
	
	public abstract Object getResults();
	
	public void declineResults() {
		setVisible(false);
	}
}

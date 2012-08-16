package ru.nkz.ivcgzo.clientManager.common;

import java.awt.Insets;
import java.awt.Rectangle;
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
	private boolean modalLocationRelativeToParent;
	
	public ModalForm(boolean setModalLocationRelativeToParent) {
		modalLocationRelativeToParent = setModalLocationRelativeToParent;
	}
	
	public boolean getModalLocationRelativeToParent() {
		return modalLocationRelativeToParent;
	}
	
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
	
	public void rightSnapWindow() {
		Rectangle scrBounds = getMainScreenBounds();
		int wdt = (int) scrBounds.getWidth();
		int wdt3 = wdt / 3;
		int hgt = (int) scrBounds.getHeight();
		
		setBounds(scrBounds.x + (wdt - wdt3), scrBounds.y, wdt3, hgt);
	}
	
	public Rectangle getMainScreenBounds() {
		Rectangle scrBounds = getGraphicsConfiguration().getBounds();
		Insets scrInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		
		return new Rectangle(scrBounds.x + scrInsets.left, scrBounds.y + scrInsets.top, scrBounds.width - (scrInsets.right + scrInsets.left), scrBounds.height - (scrInsets.bottom + scrInsets.top));
	}
}

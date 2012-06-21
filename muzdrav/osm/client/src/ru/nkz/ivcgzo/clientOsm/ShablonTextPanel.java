package ru.nkz.ivcgzo.clientOsm;

import javax.swing.JPanel;

public class ShablonTextPanel extends JPanel {
	private static final long serialVersionUID = -6109313361249646157L;
	private final int id_razd;
	
	public ShablonTextPanel(int id_razd) {
		super();
		
		this.id_razd = id_razd;
	}
	
	public int getRazdId() {
		return id_razd;
	}
}

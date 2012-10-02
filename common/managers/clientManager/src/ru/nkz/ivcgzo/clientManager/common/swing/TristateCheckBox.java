package ru.nkz.ivcgzo.clientManager.common.swing;

import javax.swing.JCheckBox;

public class TristateCheckBox extends JCheckBox {
	private static final long serialVersionUID = 5868673540905382670L;
	private TristateCheckBoxModel model;
	
	public TristateCheckBox(String text) {
		this(text, false);
	}
	
	public TristateCheckBox(String text, boolean checked) {
		super(text, checked);
		
		model = new TristateCheckBoxModel();
		setModel(model);
	}
	
	public void setIntermediateState() {
		model.setIntermediateState();
	}
	
	class TristateCheckBoxModel extends ToggleButtonModel {
		private static final long serialVersionUID = -2403533499052034286L;
		
		public void setIntermediateState() {
			super.setSelected(true);
			super.setArmed(true);
			super.setPressed(true);
		}
		
//		@Override
//		public void setSelected(boolean b) {
//			super.setSelected(b);
//			super.setArmed(false);
//			super.setPressed(false);
//		}
	}
}

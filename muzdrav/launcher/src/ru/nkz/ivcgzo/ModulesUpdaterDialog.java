package ru.nkz.ivcgzo;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ModulesUpdaterDialog extends JDialog {
	private static final long serialVersionUID = -1113743171062628535L;
	private JLabel lblCurrent;
	private JProgressBar pbCurrent;
	private JLabel lblOverall;
	private JProgressBar pbOverall;
	private Exception exc;
	
	public ModulesUpdaterDialog() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setMinimumSize(new Dimension(0, 162));
		setMaximumSize(new Dimension(0, 162));
		setBounds(100, 100, 680, 162);
		
		lblCurrent = new JLabel("New label");
		
		pbCurrent = new JProgressBar();
		
		lblOverall = new JLabel("New label");
		
		pbOverall = new JProgressBar();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblOverall, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
						.addComponent(pbOverall, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
						.addComponent(pbCurrent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
						.addComponent(lblCurrent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCurrent)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pbCurrent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblOverall, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pbOverall, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void resetProgress() {
		pbCurrent.setValue(pbCurrent.getMinimum());
		pbOverall.setValue(pbOverall.getMinimum());
	}
	
	public void setCurrentMessage(String msg) {
		lblCurrent.setText(msg);
	}
	
	public void setOverallMessage(String msg) {
		lblOverall.setText(msg);
	}
	
	public void setCurrentMaximum(int max) {
		pbCurrent.setMaximum(max);
	}
	
	public void setOverallMaximum(int max) {
		pbOverall.setMaximum(max);
	}
	
	public void setCurrentValue(int val) {
		pbCurrent.setValue(val);
	}
	
	public void setOverallValue(int val) {
		pbOverall.setValue(val);
	}
	
	public void addToCurrentValue(int val) {
		setCurrentValue(pbCurrent.getValue() + val);
	}
	
	public void addToOverallValue(int val) {
		setOverallValue(pbOverall.getValue() + val);
	}
	
	public void addToProgress(int val) {
		addToCurrentValue(val);
		addToOverallValue(val);
	}
	
	public void setException(Exception e) {
		exc = e;
	}
	
	public Exception getException() {
		return exc;
	}
}

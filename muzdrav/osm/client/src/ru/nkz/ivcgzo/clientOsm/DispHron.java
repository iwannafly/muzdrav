package ru.nkz.ivcgzo.clientOsm;


import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Pmer;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DispHron extends JFrame{
	private CustomTable<Pmer,Pmer._Fields> tblDispHron;


	/**
	 * Create the application.
	 */
	public DispHron() {
		setTitle("Диспансеризация");
		setBounds(100, 100, 1011, 726);
		JScrollPane spDispHron = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spDispHron, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spDispHron, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		
		JPanel pnlDispHron = new JPanel();
		spDispHron.setViewportView(pnlDispHron);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton button = new JButton("+");
		
		JButton btnV = new JButton("v");
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pmer pmer = new Pmer();
				pmer.setPmer(tblDispHron.getSelectedItem().getPmer());
				pmer.setId(2);
				try {
					MainForm.tcl.UpdatePmer(pmer);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				}
		});
		
		JButton button_1 = new JButton("-");
		GroupLayout gl_pnlDispHron = new GroupLayout(pnlDispHron);
		gl_pnlDispHron.setHorizontalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnV)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1)))
					.addContainerGap())
		);
		gl_pnlDispHron.setVerticalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(btnV)
						.addComponent(button_1))
					.addContainerGap(344, Short.MAX_VALUE))
		);
		
		tblDispHron = new CustomTable<>(true,true,Pmer.class,3,"Диагноз",11,"Врач.должность",4,"Мероприятие",7,"Специалист",5,"Дата план.",6,"Дата факт.",10,"Результат");
		tblDispHron.setDateField(4);
		tblDispHron.setDateField(5);
		tblDispHron.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblDispHron);
		pnlDispHron.setLayout(gl_pnlDispHron);
		getContentPane().setLayout(groupLayout);
	}
	
	public void ShowDispHron()
	{	try {
		tblDispHron.setData(MainForm.tcl.getPmer(377));
		tblDispHron.setIntegerClassifierSelector(2, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abd));
	} catch (KmiacServerException e) {
		e.printStackTrace();
	} catch (TException e) {
		e.printStackTrace();
		MainForm.conMan.reconnect(e);
	}
		setVisible(true);	
	}
}

package ru.nkz.ivcgzo.clientOsm;


import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.Pmer;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class DispHron extends JFrame{
	private CustomTable<Pmer,Pmer._Fields> tblDispHron;
	private Pmer pmer;


	/**
	 * Create the application.
	 */
	public DispHron() {
		setTitle("Диспансеризация");
		setBounds(100, 100, 1011, 398);
		JScrollPane spDispHron = new JScrollPane();
		spDispHron.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		spDispHron.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pmer = new Pmer();
		  		pmer.setNpasp(Vvod.zapVr.getNpasp());
		  		pmer.setId_pdiag(Vvod.pdisp.getId_diag());
		  		pmer.setDiag(Vvod.pdisp.getDiag());
		  		pmer.setCod_sp(MainForm.authInfo.getPcod());
		  		pmer.setId_pvizit(Vvod.pvizit.getId());
		  		pmer.setId_pos(Vvod.pvizitAmb.getId());
				try {
					pmer.setId(MainForm.tcl.AddPmer(pmer));
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 			tblDispHron.addItem(pmer);
			}
		});
		
		JButton btnV = new JButton("v");
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pmer pmer = new Pmer();
				pmer.setId(tblDispHron.getSelectedItem().getId());
				pmer.setPmer(tblDispHron.getSelectedItem().getPmer());
				pmer.setPdat(tblDispHron.getSelectedItem().getPdat());
				pmer.setFdat(tblDispHron.getSelectedItem().getFdat());
				pmer.setDataz(System.currentTimeMillis());
				pmer.setRez(tblDispHron.getSelectedItem().getRez());
				pmer.setCdol(tblDispHron.getSelectedItem().getCdol());
				try {
					MainForm.tcl.UpdatePmer(pmer);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				}
		});
		
		final JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
						if (tblDispHron.getSelectedItem()!= null)
						if (JOptionPane.showConfirmDialog(button_1, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			  			MainForm.tcl.DeletePmer(tblDispHron.getSelectedItem().getId());
						tblDispHron.setData(MainForm.tcl.getPmer(Vvod.zapVr.getNpasp()));}
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}
			}
		});
		GroupLayout gl_pnlDispHron = new GroupLayout(pnlDispHron);
		gl_pnlDispHron.setHorizontalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 969, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnV)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_pnlDispHron.setVerticalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(btnV)
						.addComponent(button_1))
					.addContainerGap(434, Short.MAX_VALUE))
		);
		
		tblDispHron = new CustomTable<>(true,true,Pmer.class,3,"Диагноз",4,"Мероприятие",11,"Специалист",5,"Дата план.",6,"Дата факт.",10,"Результат");
		tblDispHron.setDateField(3);
		tblDispHron.setDateField(4);
		tblDispHron.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblDispHron);
		pnlDispHron.setLayout(gl_pnlDispHron);
		getContentPane().setLayout(groupLayout);
	}
	
	public void ShowDispHron()
	{	try {
		tblDispHron.setData(MainForm.tcl.getPmer(16164));
		tblDispHron.setStringClassifierSelector(0, MainForm.tcl.get_n_c00(16164));
		tblDispHron.setIntegerClassifierSelector(1, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abd));
		tblDispHron.setStringClassifierSelector(2, MainForm.tcl.get_n_s00(MainForm.authInfo.getClpu()));
		tblDispHron.setIntegerClassifierSelector(5, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_arez));
		
	} catch (KmiacServerException e) {
		e.printStackTrace();
	} catch (TException e) {
		e.printStackTrace();
		MainForm.conMan.reconnect(e);
	}
		setVisible(true);	
	}
}

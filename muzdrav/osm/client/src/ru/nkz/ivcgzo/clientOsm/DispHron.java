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
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Pmer;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;

public class DispHron extends JFrame{
	private static final long serialVersionUID = -2929416282414434095L;
	private CustomTable<Pmer,Pmer._Fields> tblDispHron;
	private Pmer pmer;
	private ThriftStringClassifierCombobox<StringClassifier> cmbDiag;


	public DispHron() {
		setTitle("Диспансеризация");
		setBounds(100, 100, 1011, 655);
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
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
	 			tblDispHron.addItem(pmer);
			}
		});
		
		JButton btnV = new JButton("v");
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tblDispHron.updateSelectedItem();
				try {
					Pmer pmer = new Pmer();
					pmer.setId(tblDispHron.getSelectedItem().getId());
					pmer.setDiag(tblDispHron.getSelectedItem().getDiag());
					pmer.setPmer(tblDispHron.getSelectedItem().getPmer());
					pmer.setPdat(tblDispHron.getSelectedItem().getPdat());
					pmer.setFdat(tblDispHron.getSelectedItem().getFdat());
					pmer.setDataz(System.currentTimeMillis());
					pmer.setRez(tblDispHron.getSelectedItem().getRez());
					pmer.setCdol(tblDispHron.getSelectedItem().getCdol());
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
						tblDispHron.setData(MainForm.tcl.getPmer(Vvod.zapVr.getNpasp(), "D50"));}
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}
			}
		});
		
		 cmbDiag = new ThriftStringClassifierCombobox<>(true);
		
		JLabel lblDiag = new JLabel("Диагноз");
		GroupLayout gl_pnlDispHron = new GroupLayout(pnlDispHron);
		gl_pnlDispHron.setHorizontalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addComponent(button, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnV, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(840)))
					.addGap(22))
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addGap(5)
					.addComponent(lblDiag)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(882, Short.MAX_VALUE))
		);
		gl_pnlDispHron.setVerticalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiag))
					.addGap(33)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(btnV)
						.addComponent(button_1))
					.addGap(380))
		);
		
		tblDispHron = new CustomTable<>(true,true,Pmer.class,4,"Мероприятие",11,"Специалист",5,"Дата план.",6,"Дата факт.",10,"Результат");
		tblDispHron.setDateField(2);
		tblDispHron.setDateField(3);
		tblDispHron.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
//			if (!arg0.getValueIsAdjusting()){
//				if (tblDispHron.getSelectedItem()!= null) {
//					
//				}
//			}
		}
				});
	
		tblDispHron.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblDispHron);
		pnlDispHron.setLayout(gl_pnlDispHron);
		getContentPane().setLayout(groupLayout);
	}
	
	public void ShowDispHron()
	{	try {
		tblDispHron.setData(MainForm.tcl.getPmer(16164, "D50"));
		cmbDiag.setData(MainForm.tcl.get_n_c00(16164));
		tblDispHron.setIntegerClassifierSelector(0, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abd));
		tblDispHron.setStringClassifierSelector(1, MainForm.tcl.get_n_s00(MainForm.authInfo.getClpu()));
		tblDispHron.setIntegerClassifierSelector(4, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_arez));
		
	} catch (KmiacServerException e) {
		e.printStackTrace();
	} catch (TException e) {
		e.printStackTrace();
		MainForm.conMan.reconnect(e);
	}
		setVisible(true);	
	}
}

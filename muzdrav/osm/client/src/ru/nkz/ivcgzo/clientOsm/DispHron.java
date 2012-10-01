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
import ru.nkz.ivcgzo.thriftOsm.Pobost;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class DispHron extends JFrame{
	private static final long serialVersionUID = -2929416282414434095L;
	private CustomTable<Pmer,Pmer._Fields> tblDispHron;
	private Pmer pmer;
	private ThriftStringClassifierCombobox<StringClassifier> cmbDiag;
	private CustomTable<Pobost,Pobost._Fields> tabObost;
	private Pobost obostr;


	public DispHron() {
		setTitle("Диспансеризация");
		setBounds(100, 100, 1011, 739);
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
		
		JButton bAddDispHron = new JButton("");
		bAddDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		bAddDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pmer = new Pmer();
		  		pmer.setNpasp(Vvod.zapVr.getNpasp());
		  		pmer.setId_pdiag(Vvod.pdisp.getId_diag());
		  		pmer.setDiag(cmbDiag.getSelectedPcod());
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
		
		JButton bSaveDispHron = new JButton("");
		bSaveDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		bSaveDispHron.addActionListener(new ActionListener() {
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
		
		final JButton bDelDispHron = new JButton("");
		bDelDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		bDelDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
						if (tblDispHron.getSelectedItem()!= null)
						if (JOptionPane.showConfirmDialog(bDelDispHron, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
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
		
		JScrollPane spObost = new JScrollPane();
		
		JLabel lblObost = new JLabel("Случаи обострения");
		
		JButton bAddObost = new JButton("");
		bAddObost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(DispHron.this, Vvod.pdisp.id_diag);
			}
		});
		bAddObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		JButton bSaveObost = new JButton("");
		bSaveObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		
		JButton bDelObost = new JButton("");
		bDelObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		GroupLayout gl_pnlDispHron = new GroupLayout(pnlDispHron);
		gl_pnlDispHron.setHorizontalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addGap(5)
							.addComponent(lblDiag)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(bAddDispHron)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bSaveDispHron)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bDelDispHron))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 983, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(bAddObost)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bSaveObost)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(bDelObost))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(spObost, GroupLayout.PREFERRED_SIZE, 969, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblObost)))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_pnlDispHron.setVerticalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiag))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(bAddDispHron)
						.addComponent(bSaveDispHron)
						.addComponent(bDelDispHron))
					.addGap(26)
					.addComponent(lblObost)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spObost, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addComponent(bAddObost)
						.addComponent(bSaveObost)
						.addComponent(bDelObost))
					.addGap(872))
		);
		
		tabObost = new CustomTable<>(true, true, Pobost.class,4,"Случай обострения",5,"Случай хронического очага",8,"Дата обострения");
		tabObost.setDateField(2);
		tabObost.setFillsViewportHeight(true);
		spObost.setViewportView(tabObost);
		
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
		cmbDiag.setData(MainForm.tcl.get_n_c00(16164));
		tblDispHron.setData(MainForm.tcl.getPmer(16164, "D50.0"));
		tabObost.setData(MainForm.tcl.getPobost(16164, "D50.0"));
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

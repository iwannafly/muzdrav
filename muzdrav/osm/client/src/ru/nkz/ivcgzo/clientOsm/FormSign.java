package ru.nkz.ivcgzo.clientOsm;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;

public class FormSign extends JFrame {
	private static final long serialVersionUID = -5267798845014525253L;
	private JEditorPane tpfarm;
	private JEditorPane tpanamnz;
	private Psign psign;
	private JCheckBox cbk;
	private JCheckBox cba;
	private JCheckBox cbn;
	private ButtonGroup BGgrk;
	private JRadioButton rb1g;
	private JRadioButton rb2g;
	private JRadioButton rb3g;
	private JRadioButton rb4g;
	private ButtonGroup BGRez;
	private JRadioButton rbpol;
	private JRadioButton rbotr;
	private String vrp;
	private JEditorPane tpallerg;
	private JPanel pallerg;
	public static List<IntegerClassifier> pokNames;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	
	
	public FormSign() {
		try {
			pokNames = MainForm.tcl.getPokNames();
		} catch (Exception e3) {
			e3.printStackTrace();
			pokNames = new ArrayList<>();
		}


		setBounds(100, 100, 1011, 726);
		
		final JScrollPane spAnamn = new JScrollPane();
		spAnamn.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		final ThriftIntegerClassifierList listShablon = new ThriftIntegerClassifierList();
		listShablon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ShablonTextField.instance.setText(listShablon.getSelectedValue().getName());
				}
			}
		});
		    
			final JLabel lblFarm1 = new JLabel("Фармакологический анамнез");
		      
		      JScrollPane spSh = new JScrollPane();
		      
		      spSh.setViewportView(listShablon);
		
		JLabel lblVr = new JLabel("Вредные привычки");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		cbk = new JCheckBox("Курение");
		
		cba = new JCheckBox("Алкоголь");
		
		cbn = new JCheckBox("Наркотики");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbk)
					.addGap(18)
					.addComponent(cba)
					.addGap(18)
					.addComponent(cbn)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbk)
						.addComponent(cba)
						.addComponent(cbn))
					.addContainerGap(8, Short.MAX_VALUE))
		);
		
		final JLabel lblGrkr = new JLabel("Группа крови");
		
		JPanel pgrk = new JPanel();
		pgrk.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		rb1g = new JRadioButton("I", true);
		pgrk.add(rb1g);
		rb2g = new JRadioButton("II", true);
		pgrk.add(rb2g);
		rb3g = new JRadioButton("III", true);
		pgrk.add(rb3g);
		rb4g = new JRadioButton("IV", true);
		pgrk.add(rb4g);
		BGgrk = new ButtonGroup();
		BGgrk.add(rb1g);
		BGgrk.add(rb2g);
		BGgrk.add(rb3g);
		BGgrk.add(rb4g);
		
		final JLabel lblRezus = new JLabel("Резус-фактор");
		
		JPanel prezus = new JPanel();
		prezus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		rbpol = new JRadioButton("+", true);
		prezus.add(rbpol);
		rbotr = new JRadioButton("-", true);
		prezus.add(rbotr);
		BGRez = new ButtonGroup();		
		BGRez.add(rbpol);
		BGRez.add(rbotr);
		
		JButton button = new JButton("Сохранить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rb1g.isSelected()) {
					psign.setGrup("1");
				}
				if (rb2g.isSelected()) {
					psign.setGrup("2");
				}
				if (rb3g.isSelected()) {
					psign.setGrup("3");
				}
				if (rb4g.isSelected()) {
					psign.setGrup("4");
				}
				if (rbpol.isSelected()) {
					psign.setPh("+");
				}
				if (rbotr.isSelected()) {
					psign.setPh("-");
				}
				
				psign.setVitae(tpanamnz.getText());
				psign.setAllerg(tpallerg.getText());
				psign.setFarmkol(tpfarm.getText());
				psign.setVitae(tpanamnz.getText());
				psign.setVred(getVrPr());
				
				try {
					MainForm.tcl.setPsign(psign);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblGrkr)
							.addGap(5)
							.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblRezus)
							.addGap(4)
							.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblVr)
							.addGap(18)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(spAnamn, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spSh, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(lblGrkr))
						.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(13)
							.addComponent(lblRezus))
						.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(lblVr))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(spSh, GroupLayout.PREFERRED_SIZE, 606, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(spAnamn, 0, 0, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)))
					.addGap(11))
		);
		
		JPanel pAnamn = new JPanel();
		spAnamn.setViewportView(pAnamn);
		getContentPane().setLayout(groupLayout);
		
		
		final JLabel lblFarm = new JLabel("Фармакологический анамнез");
		
		final JLabel lblAnamnz = new JLabel("Анамнез жизни");
		
		tpfarm = new JEditorPane();
		tpfarm.setBorder(UIManager.getBorder("TextField.border"));
		
		tpanamnz = new JEditorPane();
		tpanamnz.setBorder(UIManager.getBorder("TextField.border"));
		
		 pallerg = new JPanel();
		GroupLayout gl_pAnamn = new GroupLayout(pAnamn);
		gl_pAnamn.setHorizontalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tpfarm, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
							.addComponent(lblFarm, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpanamnz, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
							.addComponent(lblAnamnz, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFarm1, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
						.addComponent(pallerg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(1557))
		);
		gl_pAnamn.setVerticalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addGap(18)
					.addComponent(lblAnamnz)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(pallerg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFarm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(607)
					.addComponent(lblFarm1)
					.addGap(184))
		);
		
		JLabel label = new JLabel("Аллергоанамнез");
		
		 tpallerg = new JEditorPane();
		tpallerg.setBorder(UIManager.getBorder("TextField.border"));
		GroupLayout gl_pallerg = new GroupLayout(pallerg);
		gl_pallerg.setHorizontalGroup(
			gl_pallerg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pallerg.createSequentialGroup()
					.addGroup(gl_pallerg.createParallelGroup(Alignment.LEADING)
						.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_pallerg.setVerticalGroup(
			gl_pallerg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pallerg.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pallerg.setLayout(gl_pallerg);
		pAnamn.setLayout(gl_pAnamn);
//		pAnamn.setLayout(gl_panel_1);
//		pAnamn.setLayout(gl_panel);
		//GroupLayout groupLayout = new GroupLayout(getContentPane());
//		groupLayout.setHorizontalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
//		);
//		groupLayout.setVerticalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
//		);
		getContentPane().setLayout(groupLayout);
		
		addWindowListener(new WindowAdapter() {
			List<IntegerClassifier> lstIdRazd;
			
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					lstIdRazd = MainForm.tcl.getShablonCdol(3, MainForm.authInfo.getCdol());
					lstIdRazd.addAll(MainForm.tcl.getShablonCdol(4, MainForm.authInfo.getCdol()));
					lstIdRazd.addAll(MainForm.tcl.getShablonCdol(5, MainForm.authInfo.getCdol()));
					disableTextFields(spAnamn);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				
				super.windowActivated(e);
			}
			
			private void disableTextFields(Container c) {
				if (c.getComponentCount() > 0)
					lbl: for (int i = 0; i < c.getComponentCount(); i++) {
						if (c.getComponent(i) instanceof ShablonTextField) {
							ShablonTextField txt = (ShablonTextField)c.getComponent(i);
							for (IntegerClassifier razd : lstIdRazd) {
								if (razd.getPcod() == txt.getIdPok()) {
									txt.setVisible(true);
									continue lbl;
								}
							}
							txt.setVisible(false);
						} else if (c.getComponent(i) instanceof Container)
							disableTextFields((Container) c.getComponent(i));
					}
			}
		});
	}
	
		private String getVrPr() {
			String prv,s1,s2,s3;
			if (cbk.isSelected()){
				s1 = "1";	
			}else {
				s1 = "0";
			}
	
			if (cba.isSelected()){
				s2 = "1";	
				}else {
					s2 = "0";
				}
			if (cbn.isSelected()){
				s3 = "1";	
				}else {
					s3 = "0";
				}
			
			prv = s1+s2+s3;

			return prv;
		}
		
		public void showPsign() {
		try {
			psign = MainForm.tcl.getPsign(Vvod.zapVr.npasp);
			if (psign!=null){
			BGgrk.clearSelection();
			rb1g.setSelected(psign.grup.charAt(0) == '1');
			rb2g.setSelected(psign.grup.charAt(0) == '2');
			rb3g.setSelected(psign.grup.charAt(0) == '3');
			rb4g.setSelected(psign.grup.charAt(0) == '4');
			
			BGRez.clearSelection();
			rbpol.setSelected(psign.ph.charAt(0) == '+');
			rbotr.setSelected(psign.ph.charAt(0) == '-');
			
			tpallerg.setText(psign.allerg);
			tpanamnz.setText(psign.vitae);
			tpfarm.setText(psign.farmkol);
			tpanamnz.setText(psign.vitae);
			
			vrp = psign.getVred();
			cbk.setSelected(vrp.charAt(0) == '1');
			cba.setSelected(vrp.charAt(1) == '1');
			cbn.setSelected(vrp.charAt(2) == '1');}
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (PsignNotFoundException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
		
		setVisible(true);
	}
}


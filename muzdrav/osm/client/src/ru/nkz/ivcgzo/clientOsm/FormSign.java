package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class FormSign extends JFrame {
	private static final long serialVersionUID = -5267798845014525253L;
	private JEditorPane tpallerg; 
	private JEditorPane tpfarm;
	private JEditorPane tpanamnz;
	private Psign psign;
	private JCheckBox cbk;
	private JCheckBox cba;
	private JCheckBox cbn;
	private JRadioButton rb1g;
	private JRadioButton rb2g;
	private JRadioButton rb3g;
	private JRadioButton rb4g;
	private JRadioButton rbpol;
	private JRadioButton rbotr;
	private String vrp;
	private ShablonTextField tprazv;
	private ShablonTextField tpuslov;
	private ShablonTextField tpper_zab;
	private ShablonTextField tpper_oper;
	private ShablonTextField tpgemotrans;
	private ShablonTextField tpnasl;
	private ShablonTextField tpginek;
	private ShablonTextField tppriem_lek;
	private ShablonTextField tpprim_gorm;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	
	
	public FormSign() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
			try {
				psign = MainForm.tcl.getPsign(Vvod.zapVr.npasp);
				if (psign.getGrup().trim() != null){
					rb1g.setSelected(psign.grup.charAt(0) == '1');
					rb2g.setSelected(psign.grup.charAt(0) == '2');
					rb3g.setSelected(psign.grup.charAt(0) == '3');
					rb4g.setSelected(psign.grup.charAt(0) == '4');
				}
				if (psign.getPh().trim() != null){
					rbpol.setSelected(psign.grup.charAt(0) == '+');
					rbotr.setSelected(psign.grup.charAt(0) == '-');
				}
				tpallerg.setText(psign.allerg);
				tpanamnz.setText(psign.vitae);
				tpfarm.setText(psign.farmkol);
				tpgemotrans.setText(psign.gemotrans);
				tpginek.setText(psign.ginek);
				tpnasl.setText(psign.nasl);
				tpper_oper.setText(psign.per_oper);
				tpper_zab.setText(psign.per_zab);
				tppriem_lek.setText(psign.priem_lek);
				tpprim_gorm.setText(psign.prim_gorm);
				tprazv.setText(psign.razv);
				tpuslov.setText(psign.uslov);
				
				vrp = psign.getVred();
				cbk.setSelected(vrp.charAt(0) == '1');
				cba.setSelected(vrp.charAt(1) == '1');
				cbn.setSelected(vrp.charAt(2) == '1');
			} catch (KmiacServerException e1) {
				JOptionPane.showMessageDialog(FormSign.this, "Неизвестная ошибка");
			} catch (PsignNotFoundException e1) {
				e1.printStackTrace();
			} catch (TException e1) {
//				MainForm.conMan.reconnect(e1);
			}	
			}
		});
		setBounds(100, 100, 1011, 726);
		
		ThriftIntegerClassifierList listShablon = new ThriftIntegerClassifierList();
		
		JScrollPane spAnamn = new JScrollPane();
		spAnamn.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnamn, GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnamn, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
		);
		
		JPanel pAnamn = new JPanel();
		spAnamn.setViewportView(pAnamn);
		getContentPane().setLayout(groupLayout);
		
		
		final JLabel lblFarm = new JLabel("Фармакологический анамнез");
		
		final JLabel lblGrkr = new JLabel("Группа крови");
		
		final JLabel lblRezus = new JLabel("Резус-фактор");
		
		final JLabel lblAllerg = new JLabel("Аллергоанамнез");
		
		final JLabel lblAnamnz = new JLabel("Анамнез жизни");
		
		
		tprazv = new ShablonTextField(4, 15, listShablon);
		tprazv.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpuslov = new ShablonTextField(4, 16, listShablon);
		tpuslov.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpper_zab =  new ShablonTextField(4, 17, listShablon);
		tpper_zab.setBorder(UIManager.getBorder("TextField.border"));
		
		tpper_oper = new ShablonTextField(4, 18, listShablon);
		tpper_oper.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpgemotrans = new ShablonTextField(4, 19, listShablon);
		tpgemotrans.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpnasl = new ShablonTextField(4, 20, listShablon);
		tpnasl.setBorder(UIManager.getBorder("TextField.border"));
		
		tpginek = new ShablonTextField(4, 21, listShablon);
		tpginek.setBorder(UIManager.getBorder("TextField.border"));
		
		 tppriem_lek = new ShablonTextField(5, 22, listShablon);
		tppriem_lek.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpprim_gorm = new ShablonTextField(5, 23, listShablon);
		tpprim_gorm.setBorder(UIManager.getBorder("TextField.border"));
		
		JButton bSave = new JButton("Сохранить");
		bSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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

				psign.setAllerg(tpallerg.getText());
				psign.setFarmkol(tpfarm.getText());
				psign.setVitae(tpanamnz.getText());
				psign.setGemotrans(tpgemotrans.getText());
				psign.setGinek(tpginek.getText());
				psign.setNasl(tpnasl.getText());
				psign.setPer_oper(tpper_oper.getText());
				psign.setPer_zab(tpper_zab.getText());
				psign.setPriem_lek(tppriem_lek.getText());
				psign.setPrim_gorm(tpprim_gorm.getText());
				psign.setRazv(tprazv.getText());
				psign.setUslov(tpuslov.getText());
				psign.setVred(getVrPr());
				
				try {
					MainForm.tcl.setPsign(psign);
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		tpallerg = new JEditorPane();
		tpallerg.setBorder(UIManager.getBorder("TextField.border"));
		
		tpfarm = new JEditorPane();
		tpfarm.setBorder(UIManager.getBorder("TextField.border"));
		
		tpanamnz = new JEditorPane();
		tpanamnz.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel lblVr = new JLabel("Вредные привычки");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel pgrk = new JPanel();
		pgrk.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ButtonGroup BGgrk = new ButtonGroup();
		 rb1g = new JRadioButton("I", true);
		pgrk.add(rb1g);
		 rb2g = new JRadioButton("II", true);
		pgrk.add(rb2g);
		 rb3g = new JRadioButton("III", true);
		pgrk.add(rb3g);
		 rb4g = new JRadioButton("IV", true);
		pgrk.add(rb4g);
		BGgrk.add(rb1g);
		BGgrk.add(rb2g);
		BGgrk.add(rb3g);
		BGgrk.add(rb4g);
		
		JPanel prezus = new JPanel();
		prezus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ButtonGroup BGRez = new ButtonGroup();		
		 rbpol = new JRadioButton("+", true);
		prezus.add(rbpol);
		 rbotr = new JRadioButton("-", true);
		prezus.add(rbotr);
		BGRez.add(rbpol);
		BGRez.add(rbotr);
		
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
		
		JScrollPane spSh = new JScrollPane();
		GroupLayout gl_pAnamn = new GroupLayout(pAnamn);
		gl_pAnamn.setHorizontalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblGrkr)
							.addGap(5)
							.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblRezus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(lblVr)
							.addGap(18)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addGap(18)
							.addComponent(bSave))
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addContainerGap()
							.addComponent(tpper_oper.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
								.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
									.addComponent(lblAllerg, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblFarm, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblAnamnz, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
									.addComponent(tprazv.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tprazv, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpuslov.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpuslov, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpper_zab.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpper_zab, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpper_oper, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpgemotrans.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpgemotrans, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpnasl.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpnasl, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpginek.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpginek, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tppriem_lek.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tppriem_lek, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpprim_gorm.getLabel(), GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
									.addComponent(tpprim_gorm, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addComponent(spSh, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(1096, Short.MAX_VALUE))
		);
		gl_pAnamn.setVerticalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pAnamn.createSequentialGroup()
									.addGap(16)
									.addGroup(gl_pAnamn.createParallelGroup(Alignment.TRAILING)
										.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_pAnamn.createSequentialGroup()
											.addComponent(lblRezus)
											.addGap(10))
										.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_pAnamn.createSequentialGroup()
												.addGap(11)
												.addComponent(lblGrkr))
											.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addComponent(panel_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addGap(27)
							.addComponent(lblVr)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnamnz)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pAnamn.createSequentialGroup()
							.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAllerg)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFarm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tprazv.getLabel())
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tprazv, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpuslov.getLabel())
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpuslov, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpper_zab.getLabel())
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpper_zab, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addComponent(spSh))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpper_oper.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpper_oper, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpgemotrans.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpgemotrans, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpnasl.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpnasl, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addComponent(tpginek.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpginek, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tppriem_lek.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tppriem_lek, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpprim_gorm.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpprim_gorm, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(bSave)
					.addGap(15))
		);
		
		spSh.setViewportView(listShablon);
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
		getContentPane().setLayout(groupLayout);}
	
		private String getVrPr() {
			String prv,s1,s2,s3;
			if (cbk.isSelected()){
				s1 = "1";	
			}else {
				s1 = "0";
			}
			System.out.println(s1);
			
			if (cba.isSelected()){
				s2 = "1";	
				}else {
					s2 = "0";
				}
			System.out.println(s2);
			if (cbn.isSelected()){
				s3 = "1";	
				}else {
					s3 = "0";
				}
			System.out.println(s3);
			
			prv = s1+s2+s3;

			return prv;
		}
}


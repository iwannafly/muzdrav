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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;

public class FormSign extends JFrame {
	private static final long serialVersionUID = -5267798845014525253L;
	private JTextField tfgrup;
	private JTextField tfrezus;
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
				psign = MainForm.tcl.getPsign(Vvod.zapVrSave.npasp);
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
				
				vrp = psign.getVred();
				cbk.setSelected(vrp.charAt(0) == '1');
				cba.setSelected(vrp.charAt(1) == '1');
				cbn.setSelected(vrp.charAt(2) == '1');
			} catch (KmiacServerException e1) {
				JOptionPane.showMessageDialog(FormSign.this, "Неизвестная ошибка");
			} catch (PsignNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TException e1) {
//				MainForm.conMan.reconnect(e1);
			}	
			}
		});
		setBounds(100, 100, 522, 489);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("<html>Фармакологический<br>\r\nанамнез</html>");
		
		JLabel label_1 = new JLabel("Группа крови");
		
		tfgrup = new JTextField();
		tfgrup.setColumns(10);
		
		JLabel label_2 = new JLabel("Резус-фактор");
		
		tfrezus = new JTextField();
		tfrezus.setColumns(10);
		
		JLabel label_3 = new JLabel("Аллергоанамнез");
		
		JLabel label_4 = new JLabel("Анамнез жизни");
		
		JButton button = new JButton("Сохранить");
		button.addActionListener(new ActionListener() {
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
				psign.setVred(getVrPr());
				
				try {
					MainForm.tcl.setPsign(psign);
				} catch (KmiacServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
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
		
		JLabel label_5 = new JLabel("Вредные привычки");
		
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
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_3)
							.addGap(45)
							.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label_4)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(tpfarm)
								.addComponent(tpanamnz, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(button)
								.addComponent(label_5))
							.addGap(30)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
					.addGap(420)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tfrezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfgrup, 183, 183, 183))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(13)
							.addComponent(label_1))
						.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addComponent(tfgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(tfrezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_2)
								.addComponent(prezus, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(49)
									.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(40)
									.addComponent(label_3)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(23)
									.addComponent(label)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addComponent(label_4)
									.addGap(66)
									.addComponent(label_5)
									.addGap(14))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(8)
									.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))))
					.addGap(15)
					.addComponent(button)
					.addGap(115))
		);
		
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
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
		);
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

////			
			return prv;
		}
}

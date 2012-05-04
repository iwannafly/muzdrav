package ru.nkz.ivcgzo.clientOsm;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;

public class FormSign extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfgrup;
	private JTextField tfrezus;
	private JTextPane tpallerg;
	private JTextPane tpfarm;
	private JTextPane tpanamnz;
	private Psign psign;
	private JCheckBox cbk;
	private JCheckBox cba;
	private JCheckBox cbn;
	private String vrp;
	private JTextField textField;

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
				tfgrup.setText(psign.grup);
				tfrezus.setText(psign.ph);
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
				MainForm.conMan.reconnect(e1);
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
				psign.setGrup(tfgrup.getText());
				psign.setPh(tfrezus.getText());
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
		
		tpallerg = new JTextPane();
		tpallerg.setBorder(UIManager.getBorder("TextField.border"));
		
		tpfarm = new JTextPane();
		tpfarm.setBorder(UIManager.getBorder("TextField.border"));
		
		tpanamnz = new JTextPane();
		tpanamnz.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel label_5 = new JLabel("Вредные привычки");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				
		textField = new JTextField();
		textField.setVisible(false);
		textField.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_4)
										.addComponent(label_2)
										.addComponent(label_1))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(tpfarm, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
										.addComponent(tpanamnz, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(tfrezus, Alignment.LEADING)
											.addComponent(tfgrup, Alignment.LEADING))))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_3)
									.addGap(45)
									.addComponent(tpallerg, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(label_5)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(30)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))))
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(button)
							.addGap(22))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(tfgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(tfrezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label_5)
							.addGap(20))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(6)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(button))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(61))
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

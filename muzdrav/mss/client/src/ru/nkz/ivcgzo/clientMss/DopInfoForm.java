package ru.nkz.ivcgzo.clientMss;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMss.Psmertdop;
import ru.nkz.ivcgzo.thriftMss.MssdopNotFoundException;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;

public class DopInfoForm extends JDialog {
	private CustomTextField tfFam;
	private CustomTextField tfIm;
	private CustomTextField tfOt;
	private JTextField tfNomer_n;
	private JTextField tfNomer_k;
	private JTextField tfNomer_t;
	private JCheckBox ckbPrizn;
	private int cuserCpodr = 0;
	private int cuserCslu = 0;
	private int cuserClpu = 0;
	private Psmertdop dopInfo;
	
	public DopInfoForm() {
		setModalityType(ModalityType.TOOLKIT_MODAL);
		setBounds(100,100,800,300);
		setTitle("Дополнительная информация для формирования свидетельства");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Руководитель подразделения");
		
		JLabel label = new JLabel("фамилия");
		
		tfFam = new CustomTextField();
		tfFam.setColumns(10);
		
		JLabel label_1 = new JLabel("имя");
		
		tfIm = new CustomTextField();
		tfIm.setColumns(10);
		
		JLabel label_2 = new JLabel("отчество");
		
		tfOt = new CustomTextField();
		tfOt.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Номера свидетельств: начальный");
		
		tfNomer_n = new JTextField();
		tfNomer_n.setColumns(10);
		
		JLabel label_3 = new JLabel("конечный");
		
		tfNomer_k = new JTextField();
		tfNomer_k.setColumns(10);
		
		JLabel label_4 = new JLabel("текущий номер");
		
		tfNomer_t = new JTextField();
		tfNomer_t.setColumns(10);
		
		ckbPrizn = new JCheckBox("автоматически проставлять номер свидетельства");
		cuserCpodr = MainForm.authInfo.cpodr;
		cuserCslu = MainForm.authInfo.cslu;
		cuserClpu = MainForm.authInfo.clpu;
		try {
		dopInfo = MainForm.tcl.getPsmertdop(cuserCpodr,cuserCslu,cuserClpu);
		tfNomer_n.setText(String.valueOf(dopInfo.nomer_n));
		tfNomer_k.setText(String.valueOf(dopInfo.nomer_k));
		tfNomer_t.setText(String.valueOf(dopInfo.nomer_t));
		tfFam.setText(dopInfo.fam.trim());
		tfIm.setText(dopInfo.im.trim());
		tfOt.setText(dopInfo.ot.trim());
		if (dopInfo.prizn == true) ckbPrizn.setSelected(true);
		else ckbPrizn.setSelected(false);
		} catch (TException  e1){
			tfNomer_n.setText(null);
			tfNomer_k.setText(null);
			tfNomer_t.setText(null);
			tfFam.setText(null);
			tfIm.setText(null);
			tfOt.setText(null);
			ckbPrizn.setSelected(false);
		}

		//addWindowListener(new WindowAdapter() {
		//	public void WindowOpened(WindowEvent arg0) {
				
		//	}
		//});
		JButton btnSaveInfo = new JButton("сохранить");
		btnSaveInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dopInfo = new Psmertdop();
					dopInfo.setCpodr(cuserCpodr);
					dopInfo.setCslu(cuserCslu);
					dopInfo.setClpu(cuserClpu);
					if (ckbPrizn.isSelected()) dopInfo.setPrizn(true);
					else dopInfo.setPriznIsSet(false);
					if (tfNomer_n.getText().isEmpty()) dopInfo.setNomer_nIsSet(false);
					else dopInfo.setNomer_n(Integer.valueOf(tfNomer_n.getText().trim()));
					if (tfNomer_k.getText().isEmpty()) dopInfo.setNomer_kIsSet(false);
					else dopInfo.setNomer_k(Integer.valueOf(tfNomer_k.getText().trim()));
					if (tfNomer_t.getText().isEmpty()) dopInfo.setNomer_tIsSet(false);
					else dopInfo.setNomer_t(Integer.valueOf(tfNomer_t.getText().trim()));
					dopInfo.setFam(tfFam.getText().trim());
					dopInfo.setIm(tfIm.getText().trim());
					dopInfo.setOt(tfOt.getText().trim());
					//System.out.println(tfOt.getText().trim());
					MainForm.tcl.setPsmertdop(dopInfo);
					dispose();
				} catch (Exception e1){
					e1.printStackTrace();
				}
			}
			
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(ckbPrizn)
						.addComponent(lblNewLabel)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfFam, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNomer_t, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSaveInfo)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfNomer_n, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(label_3)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNomer_k, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tfNomer_n, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(tfNomer_k, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(tfNomer_t, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(ckbPrizn)
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(btnSaveInfo)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}
}

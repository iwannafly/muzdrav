package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputFacZd;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import ru.nkz.ivcgzo.thriftOsm.InputSvodVed;
import java.io.File;
import java.text.SimpleDateFormat;

public class FacZd extends JPanel {
	private CustomDateEditor tfDateB;
	private CustomDateEditor tfDateF;
	private JLabel lblNewLabel;
	//public InputInfo inputInfo;
	private final ButtonGroup buttonGroupVoz = new ButtonGroup();
	private final ButtonGroup buttonGroupPer = new ButtonGroup();
	public FacZd() {
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat sdfo = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfa = new SimpleDateFormat("yyyy.MM.dd");
		// Текущая дата в формате 12.12.2012
		final String curDat = sdfo.format(java.util.Calendar.getInstance().getTime());
		final String curYear = sdfy.format(java.util.Calendar.getInstance().getTime());
		final String curDatAg = sdfa.format(java.util.Calendar.getInstance().getTime());
		final String finI = "31.03"+curYear;
		final String finIi = "30.06"+curYear;
		final String finIii = "30.09"+curYear;
		final String finIv = "31.12"+curYear;
		
		
		JLabel label = new JLabel("с ");
		tfDateB = new CustomDateEditor();
		tfDateB.setText("01012012");
		tfDateF = new CustomDateEditor();
		tfDateF.setText("25122012");
		
		lblNewLabel = new JLabel("по");
		final JLabel lblKodForm = new JLabel("BIPG64J");
		
		final JRadioButton rdbtnDet = new JRadioButton("Дети до 14 лет");
		rdbtnDet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblKodForm.setText("BIPG74J");
			}
		});
		buttonGroupVoz.add(rdbtnDet);
		final JRadioButton rdbtnPod = new JRadioButton("Подростки 15-18 лет");
		rdbtnPod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblKodForm.setText("BIPG64J");
			}
		});
		buttonGroupVoz.add(rdbtnPod);
		final JRadioButton rdbtnVzr = new JRadioButton("Взрослые");
		rdbtnVzr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblKodForm.setText("BIPG54J");
			}
		});
		buttonGroupVoz.add(rdbtnVzr);
		rdbtnPod.setSelected(true);
		
		final JRadioButton rdbtnI = new JRadioButton("I КВАРТАЛ");
		rdbtnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ffj
				tfDateF.setText(finI);
			}
		});
		buttonGroupPer.add(rdbtnI);
		final JRadioButton rdbtnIi = new JRadioButton("ПОЛУГОДИЕ");
		rdbtnIi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ffj
				tfDateF.setText(finIi);
			}
		});
		buttonGroupPer.add(rdbtnIi);
		final JRadioButton rdbtnIii = new JRadioButton("III КВАРТАЛ");
		rdbtnIii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ffj
				tfDateF.setText(finIii);
			}
		});
		buttonGroupPer.add(rdbtnIii);
		final JRadioButton rdbtnIv = new JRadioButton("IV КВАРТАЛ");
		rdbtnIv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ffj
				tfDateF.setText(finIv);
			}
		});
		buttonGroupPer.add(rdbtnIv);
		
		
		
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				//InputSvodVed inputSvodVed = new InputSvodVed();
				//inputSvodVed.setDateb(tfDateB.toString());
				//inputSvodVed.setDatef(tfDateF.toString());
				//inputSvodVed.setKpolik(MainForm.authInfo.getCpodr());
				//inputSvodVed.setNamepol(MainForm.authInfo.getCpodr_name());
					
				InputFacZd ifz = new InputFacZd();	
				if (rdbtnDet.isSelected()) ifz.setVozcat(1);
				else if (rdbtnPod.isSelected()) ifz.setVozcat(2);
				else if (rdbtnVzr.isSelected()) ifz.setVozcat(3);
				
				if (rdbtnI.isSelected()) ifz.setKvar(1);
				else if (rdbtnIi.isSelected()) ifz.setKvar(2);
				else if (rdbtnIii.isSelected()) ifz.setKvar(3);
				else if (rdbtnIv.isSelected()) ifz.setKvar(4);

				
				ifz.setDateb(sdf.format(tfDateB.getDate()));
				ifz.setDatef(sdf.format(tfDateF.getDate()));
				
				InputAuthInfo iaf = new InputAuthInfo();
				iaf.setUserId(MainForm.authInfo.getUser_id());
				iaf.setCpodr_name(MainForm.authInfo.getCpodr_name());
				iaf.setClpu_name(MainForm.authInfo.getClpu_name());
								
				
				//OutputTest ot = new OutputTest();
				String servPath = MainForm.tcl.printFacZd(iaf,ifz);
				String cliPath = File.createTempFile("test", ".htm").getAbsolutePath();
				MainForm.conMan.transferFileFromServer(servPath, cliPath);
				MainForm.conMan.openFileInEditor(cliPath, false);
					//catch (TException e1) {
					//	MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			}
		});
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Возрастная категория");
		
		JLabel label_2 = new JLabel("Период формирования:");
		
		
		if (curDatAg.compareTo(curYear+".09.30")>=0) {
			rdbtnIii.setSelected(true);
			tfDateF.setText(finIii);
		} else if (curDatAg.compareTo(curYear+".06.30")>=0) {
			rdbtnIi.setSelected(true);
			tfDateF.setText(finIi);
		} else {
			rdbtnIi.setSelected(true);
			tfDateF.setText(finI);
		}
		
		
		//rdbtnI.setSelected(true);
		
		JLabel label_3 = new JLabel("Код формы:");
		
		//JLabel lblKodForm = new JLabel("BIPG34J");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(rdbtnDet)
						.addComponent(rdbtnPod)
						.addComponent(rdbtnVzr)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblKodForm)))
					.addGap(70)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnIi, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnI, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(rdbtnIii, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnIv, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(57, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(85)
					.addComponent(label)
					.addGap(4)
					.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(128, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(label_2))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnDet)
						.addComponent(rdbtnI))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnIi)
						.addComponent(rdbtnPod))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnIii)
						.addComponent(rdbtnVzr))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnIv)
						.addComponent(label_3)
						.addComponent(lblKodForm))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(this);
		//addTab("New tab", null, scrollPane, null);
	
	}
}

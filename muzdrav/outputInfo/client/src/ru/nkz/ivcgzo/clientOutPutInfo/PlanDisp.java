package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPlanDisp;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;

public class PlanDisp extends JPanel {
	private CustomDateEditor tfDateB;
	private CustomDateEditor tfDateF;
	private JTextField tfUch;
	public PlanDisp() {
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		tfDateB = new CustomDateEditor();
		tfDateB.setText("01012012");
		tfDateF = new CustomDateEditor();
		tfDateF.setText("25122012");
		
		JButton btnNewButton = new JButton("Вывод");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MainForm.disp ==1){
					// план дисп
					try{
						InputPlanDisp ipd = new InputPlanDisp();	
						/*	if (rdbtnDet.isSelected()) isv.setVozcat(1);
						else if (rdbtnPod.isSelected()) isv.setVozcat(2);
						else if (rdbtnVzr.isSelected()) isv.setVozcat(3);
						 */				
						ipd.setDaten(sdf.format(tfDateB.getDate()));
						ipd.setDatek(sdf.format(tfDateF.getDate()));
						ipd.setUchas(tfUch.getText());
						ipd.setKpolik(MainForm.authInfo.getCpodr());
						ipd.setNamepol(MainForm.authInfo.getCpodr_name());
						//ipd.setClpu(MainForm.authInfo.clpu);					
					
							//OutputTest ot = new OutputTest();
						String servPath = MainForm.tcl.printPlanDisp(ipd);
						String cliPath = File.createTempFile("test", ".htm").getAbsolutePath();
						MainForm.conMan.transferFileFromServer(servPath, cliPath);
						MainForm.conMan.openFileInEditor(cliPath, true);
							//catch (TException e1) {
							//	MainForm.conMan.reconnect(e1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
				if(MainForm.disp ==2){
				//не выполнененые планы дисп	
				}
				if(MainForm.disp ==3){
					//Сведения о дисп обслуж
					try{
						InputPlanDisp ipd = new InputPlanDisp();	
							
						ipd.setDaten(sdf.format(tfDateB.getDate()));
						ipd.setDatek(sdf.format(tfDateF.getDate()));
						ipd.setUchas(tfUch.getText());
						ipd.setKpolik(MainForm.authInfo.getCpodr());
						ipd.setNamepol(MainForm.authInfo.getCpodr_name());
						String servPath = MainForm.tcl.printSvedDispObs(ipd);
						String cliPath = File.createTempFile("test", ".htm").getAbsolutePath();
						MainForm.conMan.transferFileFromServer(servPath, cliPath);
						MainForm.conMan.openFileInEditor(cliPath, true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}	
				
				
		});
		
		JLabel label = new JLabel("Период формирования: с ");
		
		
		JLabel label_1 = new JLabel("по");
		
		
		JLabel lblNewLabel = new JLabel("Участок:");
		
		tfUch = new JTextField();
		tfUch.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfUch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(63, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label_1))
						.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(91)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(tfUch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		setLayout(groupLayout);
		JScrollPane scrollPane = new JScrollPane();
	}
}


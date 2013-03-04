package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPasUch;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import ru.nkz.ivcgzo.thriftOsm.InputSvodVed;
import ru.nkz.ivcgzo.clientManager.common.DocumentPrinter;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;

public class PasUch extends JPanel {
	private CustomDateEditor tfDateB;
	private CustomDateEditor tfDateF;
	private JLabel lblNewLabel;
	//public InputInfo inputInfo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public PasUch() {
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JLabel label = new JLabel("Период формирования: с ");
		tfDateB = new CustomDateEditor();
		tfDateB.setText("01012012");
		tfDateF = new CustomDateEditor();
		tfDateF.setText("25122012");
		
		lblNewLabel = new JLabel("по");
		
		JLabel label_1 = new JLabel("Паспорт врачебного участка");
		
		String[] items = {
				"1",
				"2",
				"3"
			};
		
		
		final JComboBox comboBox = new JComboBox(items);
		comboBox.setEditable(true);
		
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				//InputSvodVed inputSvodVed = new InputSvodVed();
				//inputSvodVed.setDateb(tfDateB.toString());
				//inputSvodVed.setDatef(tfDateF.toString());
				//inputSvodVed.setKpolik(MainForm.authInfo.getCpodr());
				//inputSvodVed.setNamepol(MainForm.authInfo.getCpodr_name());
					
				InputPasUch ipu = new InputPasUch();	
				
				int uchnum = Integer.parseInt(comboBox.getSelectedItem().toString());
				
				ipu.setDateb(sdf.format(tfDateB.getDate()));
				ipu.setDatef(sdf.format(tfDateF.getDate()));
				ipu.setUchnum(uchnum);
				InputAuthInfo iaf = new InputAuthInfo();
				iaf.setUserId(MainForm.authInfo.getUser_id());
				iaf.setCpodr_name(MainForm.authInfo.getCpodr_name());
				iaf.setClpu_name(MainForm.authInfo.getClpu_name());
				iaf.setCpodr(MainForm.authInfo.getCpodr());
								
				//DocumentPrinter dp = new DocumentPrinter;
				//OutputTest ot = new OutputTest();
				String servPath = MainForm.tcl.printPasUch(iaf,ipu);
				//String cliPath = new File("C://Outputtest", ".htm").getAbsolutePath();
				//String cliPath = new DocumentPrinter.createReportFile("paspu");
				String cliPath = DocumentPrinter.createReportFile("paspu");
				MainForm.conMan.transferFileFromServer(servPath, cliPath);
				MainForm.conMan.openFileInEditor(cliPath, false);
				//catch (TException e1) {
				//	MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			}
		});
		
		
		
		JLabel label_2 = new JLabel("Номер участка:  ");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(138)
					.addComponent(label_1)
					.addContainerGap(150, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))
					.addGap(53))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(this);
		//addTab("New tab", null, scrollPane, null);
	
	}
}

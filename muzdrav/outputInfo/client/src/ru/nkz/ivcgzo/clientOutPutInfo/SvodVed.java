package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import ru.nkz.ivcgzo.thriftOsm.InputSvodVed;

public class SvodVed extends JPanel {
	private CustomDateEditor tfDateB;
	private CustomDateEditor tfDateF;
	private JLabel lblNewLabel;
	//public InputInfo inputInfo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public SvodVed() {
		
		JLabel label = new JLabel("Период формирования: с ");
		tfDateB = new CustomDateEditor();
		tfDateF = new CustomDateEditor();
		
		lblNewLabel = new JLabel("по");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Дети до 14 лет");
		buttonGroup.add(rdbtnNewRadioButton);
		
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputSvodVed inputSvodVed = new InputSvodVed();
				inputSvodVed.setDateb(tfDateB.toString());
				inputSvodVed.setDatef(tfDateF.toString());
				inputSvodVed.setKpolik(MainForm.authInfo.getCpodr());
				inputSvodVed.setNamepol(MainForm.authInfo.getCpodr_name());
				//if (rdbtnNewRadioButton.isSelected()) 
			}
		});
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Подростки 15-18 лет");
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Взрослые");
		buttonGroup.add(rdbtnNewRadioButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("Возрастная категория");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(label)
					.addGap(4)
					.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(84, Short.MAX_VALUE)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(lblNewLabel_1)
						.addPreferredGap(ComponentPlacement.UNRELATED))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_2)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(rdbtnNewRadioButton))
					.addGap(81))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(44)
					.addComponent(rdbtnNewRadioButton)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addComponent(rdbtnNewRadioButton_2)
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(this);
		//addTab("New tab", null, scrollPane, null);
	
	}
}

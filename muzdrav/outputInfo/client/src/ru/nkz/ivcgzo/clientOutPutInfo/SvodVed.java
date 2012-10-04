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
		
		final JRadioButton rdbtnDet = new JRadioButton("Дети до 14 лет");
		final JRadioButton rdbtnPod = new JRadioButton("Подростки 15-18 лет");
		final JRadioButton rdbtnVzr = new JRadioButton("Взрослые");
		buttonGroup.add(rdbtnVzr);
		buttonGroup.add(rdbtnDet);
		buttonGroup.add(rdbtnPod);
		
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputSvodVed inputSvodVed = new InputSvodVed();
				inputSvodVed.setDateb(tfDateB.toString());
				inputSvodVed.setDatef(tfDateF.toString());
				inputSvodVed.setKpolik(MainForm.authInfo.getCpodr());
				inputSvodVed.setNamepol(MainForm.authInfo.getCpodr_name());
				if (rdbtnDet.isSelected()) inputSvodVed.setVozcat(1);
				else if (rdbtnPod.isSelected()) inputSvodVed.setVozcat(2);
				else if (rdbtnVzr.isSelected()) inputSvodVed.setVozcat(3);
			}
		});
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Возрастная категория");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(lblNewLabel_1))
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tfDateB, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfDateF, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addComponent(rdbtnVzr)
						.addComponent(rdbtnPod)
						.addComponent(rdbtnDet))
					.addGap(53))
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
					.addGap(59)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(87))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnDet)
							.addGap(18)
							.addComponent(rdbtnPod)
							.addGap(18)
							.addComponent(rdbtnVzr)
							.addGap(40)))
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(this);
		//addTab("New tab", null, scrollPane, null);
	
	}
}

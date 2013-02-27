package ru.nkz.ivcgzo.clientOutPutInfo;

import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPos;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPosAuth;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;

public class StructPos extends JPanel {
	
	private CustomDateEditor Date1;
	private CustomDateEditor Date2;
	private final ButtonGroup butGroup = new ButtonGroup();
	private int cpol = 0;
	
	public StructPos() {
		cpol = MainForm.authInfo.cpodr;
		
		JLabel label = new JLabel("Сведения о структуре посещений и использования рабочего времени");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
				
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JPanel panel = new JPanel();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(143)
					.addComponent(label)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(231)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(130)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(179, Short.MAX_VALUE))
		);
		
		JLabel label_1 = new JLabel("Период формирования:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		Date1 = new CustomDateEditor();
		Date1.setText("19092012");
		JLabel label_3 = new JLabel("По");
		Date2 = new CustomDateEditor();
		Date2.setText("11102012");
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(StructPos.class.getResource("/ru/nkz/ivcgzo/clientOutPutInfo/resources/1341981970_Accept.png")));
				
				JLabel label_2 = new JLabel("С");
		
				final JRadioButton rbutP = new JRadioButton("По дате посещения");
				butGroup.add(rbutP);
				rbutP.setSelected(true);
				final JRadioButton rbutB = new JRadioButton("По дате записи в базу");
				butGroup.add(rbutB);
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(rbutP)
										.addComponent(rbutB))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(24)
											.addComponent(label_2))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(18)
											.addComponent(label_3)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(Date2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(Date1, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
									.addGap(18)
									.addComponent(button, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_1)
									.addGap(99))))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_1)
									.addGap(47)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(rbutP)
										.addComponent(label_2)
										.addComponent(Date1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(13)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(rbutB)
											.addComponent(label_3))
										.addComponent(Date2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel.setLayout(gl_panel);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				InputStructPos isp = new InputStructPos();	
				if (rbutP.isSelected()) isp.setDateChange("a.datap");
				else if (rbutB.isSelected()) isp.setDateChange("a.dataz");
				
				isp.setDate1(sdf.format(Date1.getDate()));
				isp.setDate2(sdf.format(Date2.getDate()));
							
					InputStructPosAuth ispa = new InputStructPosAuth();
					ispa.setUserId(MainForm.authInfo.getUser_id());
					ispa.setCpodr_name(MainForm.authInfo.getCpodr_name());
					ispa.setClpu_name(MainForm.authInfo.getClpu_name());
					ispa.setCpodr(MainForm.authInfo.getCpodr());
				
					String servPath = MainForm.tcl.printStructPos(ispa,isp);
					String cliPath = File.createTempFile("test", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInEditor(cliPath, false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			}
		});
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
	}
}

package ru.nkz.ivcgzo.clientVgr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.apache.thrift.TException;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftVgr.KovNotFoundException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Period {


	public int Cslu = 0;
	private JFrame frame;

	private CustomDateEditor tfDn;
	private CustomDateEditor tfDk;
//	private ServerVgr sfrm;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Period window = new Period();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Period() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param tfDn 
	 * @param tfDk 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
		//		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				String servPath = null;
				String cliPath = null;
				if (Cslu == 1){
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
					
					try {
					
			        try {
						servPath = MainForm.tcl.getKovInfoPol(MainForm.authInfo.cpodr, SimpleDateFormat.getDateInstance().parse("01.01.2012").getTime(), SimpleDateFormat.getDateInstance().parse("31.12.2012").getTime());
					} catch (ParseException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					cliPath = "C:\\Kov"+MainForm.authInfo.getKdate()+MainForm.authInfo.cpodr+sdf.format(new Date())+".rar";
							
						
						try {
							MainForm.tcl.getKovInfoPol(MainForm.authInfo.cpodr,SimpleDateFormat.getDateInstance().parse("01.01.2012").getTime(), SimpleDateFormat.getDateInstance().parse("31.12.2012").getTime());
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					    if (servPath.endsWith("zip")){
	   						try {
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Файл : "+cliPath, null, JOptionPane.INFORMATION_MESSAGE); 
						}
					
					} catch (KmiacServerException e1) {
						JOptionPane.showMessageDialog(frame, "Какая-то ошибка.", "error", JOptionPane.ERROR_MESSAGE);
					} /*catch (KovNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "Что-то не найдено.", "error", JOptionPane.ERROR_MESSAGE);
					} */
					catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
				}
				
				if (Cslu == 2){
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
					
					try {
					
			        try {
						servPath = MainForm.tcl.getDetInfoPol(MainForm.authInfo.cpodr, SimpleDateFormat.getDateInstance().parse("01.01.2012").getTime(), SimpleDateFormat.getDateInstance().parse("31.12.2012").getTime());
					} catch (ParseException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					cliPath = "C:\\dd_"+MainForm.authInfo.getKdate()+MainForm.authInfo.cpodr+"_"+sdf.format(new Date())+".rar";
							
						
						try {
							MainForm.tcl.getDetInfoPol(MainForm.authInfo.cpodr,SimpleDateFormat.getDateInstance().parse("01.01.2012").getTime(), SimpleDateFormat.getDateInstance().parse("31.12.2012").getTime());
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					    if (servPath.endsWith("zip")){
	   						try {
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Файл : "+cliPath, null, JOptionPane.INFORMATION_MESSAGE); 
						}
					
					} catch (KmiacServerException e1) {
						JOptionPane.showMessageDialog(frame, "Какая-то ошибка.", "error", JOptionPane.ERROR_MESSAGE);
					} /*catch (KovNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "Что-то не найдено.", "error", JOptionPane.ERROR_MESSAGE);
					} */
					catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));

		JButton btnNewButton_1 = new JButton("Выход");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
			   setVisible(false);
		    }
		});

		JLabel lblNewLabel = new JLabel("Задайте период");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNewLabel_1 = new JLabel("с");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel_2 = new JLabel("по");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));

		tfDn  = new  CustomDateEditor();
		tfDn .setColumns(10);

		tfDk  = new CustomDateEditor();
		tfDk .setColumns(10);
	//	tfDk = new CustomDateEditor();
	//	tfDn = new CustomDateEditor();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(180)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(80)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(37)
									.addComponent(lblNewLabel_2)
									.addGap(18)
									.addComponent(tfDk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton)
									.addGap(83)
									.addComponent(btnNewButton_1)))))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(lblNewLabel)
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(tfDn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addGap(36))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	

	public void setVisible(boolean value) {
		frame.setVisible(value);

	}

	public void showPeriod() {
		if (tfDn.getDate() == null) tfDn.setDate(System.currentTimeMillis());
		if (tfDk.getDate() == null) tfDk.setDate(System.currentTimeMillis());
		setVisible(true);
	}

}

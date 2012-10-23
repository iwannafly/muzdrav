package ru.nkz.ivcgzo.clientVgr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Period {


	private JFrame frame;
	private CustomDateEditor textField;
	private CustomDateEditor textField_1;
	private CustomDateEditor tfDn;
	private CustomDateEditor tfDk;
	private VgrKov sfrm;
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
		initialize(tfDk, tfDn);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param tfDk 
	 * @param tfDn 
	 */
	private void initialize(CustomDateEditor tfDk, CustomDateEditor tfDn) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JButton btnNewButton = new JButton("Выполнить");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		//		sfrm = new ServerVgr();
		//		sfrm.ServerVgr();
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

		textField = new  CustomDateEditor();
		textField.setColumns(10);

		textField_1 = new CustomDateEditor();
		textField_1.setColumns(10);
		tfDk = new CustomDateEditor();
		tfDn = new CustomDateEditor();
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
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(37)
									.addComponent(lblNewLabel_2)
									.addGap(18)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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

	public void showPeriod(CustomDateEditor tfDn, CustomDateEditor tfDk) {
	//	CustomDateEditor tfDn = null;
		// TODO Auto-generated method stub
	      if (tfDn.getDate() == null) tfDn.setDate(System.currentTimeMillis());
	//        CustomDateEditor tfDk = null;
			if (tfDk.getDate() == null) tfDk.setDate(System.currentTimeMillis());
	  	
	}

}

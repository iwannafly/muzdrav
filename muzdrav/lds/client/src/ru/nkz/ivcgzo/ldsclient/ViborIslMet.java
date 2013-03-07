package ru.nkz.ivcgzo.ldsclient;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.ldsThrift.LIslExistsException;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.N_ldi;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.ldsThrift.S_ot01IsMet;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViborIslMet extends JDialog {
	
	PIslForm winPat;

	private JPanel contentPanel = new JPanel();
	public CustomTable<S_ot01IsMet, S_ot01IsMet._Fields> tLabMet;
	public Integer npasp;
	public Integer nisl;
	private JButton okButton;
	private JButton cancelButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViborIslMet dialog = new ViborIslMet();
					//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ViborIslMet() {
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 799, 568);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		//tLabMet = new CustomTable<>(true, true, S_ot01IsMet.class, 0, "Pcod", 1, "Исследование", 2, "Pcod_m", 3, "Метод исследования", 4, "Стоимость", 5, "Выбор");
		tLabMet = new CustomTable<>(true, true, S_ot01IsMet.class, 1, "Исследование", 3, "Метод исследования", 4, "Стоимость", 5, "Выбор");
		tLabMet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tLabMet.getSelectedColumn() ==3){
					
					LabIsl nLI = new LabIsl();
					
					if (tLabMet.getSelectedItem().vibor){
						
						nLI.setCpok(tLabMet.getSelectedItem().pcod);
						nLI.setPcod_m(tLabMet.getSelectedItem().pcod_m);
						nLI.setStoim(tLabMet.getSelectedItem().stoim);
						nLI.setNisl(nisl);
						nLI.setNpasp(npasp);

						
						try {
							MainForm.ltc.AddLIsl(nLI);
						} catch (TException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else{
						try {
							MainForm.ltc.DelLIsl2(nisl, tLabMet.getSelectedItem().pcod, tLabMet.getSelectedItem().pcod_m);
						} catch (TException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
				
				
			}
		});
		scrollPane.setViewportView(tLabMet);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setVisible(false);
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_buttonPane.createSequentialGroup()
						.addGap(294)
						.addComponent(okButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
						.addGap(306)
						.addComponent(cancelButton)
						.addGap(5))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addComponent(cancelButton)
						.addContainerGap())
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(10)
						.addComponent(okButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(9))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}

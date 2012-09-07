package ru.nkz.ivcgzo.clientGenReestr;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ReestrForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private CustomDateEditor Datan;
	private CustomDateEditor Datak;

	public ReestrForm() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(configuration.appName);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu RunMenu = new JMenu("Выполнить");
		menuBar.add(RunMenu);
		JMenuItem menu1 = new JMenuItem ("Формирование базы реестров");
		menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JMenuItem menu3 = new JMenuItem ("Загрузка файла контроля/оплаты реестров");
		menu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JMenuItem menu4 = new JMenuItem ("Контроль данных реестра");
		menu4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JMenuItem menu5 = new JMenuItem ("Создание пустой базы реестра");
		menu5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JMenu menu2 = new JMenu("Выгрузка файлов для контроля в ТФ ОМС");
		RunMenu.add(menu2);
		JMenuItem ReestrPolMenu = new JMenuItem ("Реестры посещений");
		JMenuItem ReestrStMenu = new JMenuItem ("Реестры стационара");
		JMenuItem ReestrLDSMenu = new JMenuItem ("Реестры ЛДС");
		menu2.add (ReestrPolMenu);
		menu2.add (ReestrStMenu);
		menu2.add (ReestrLDSMenu);
		RunMenu.add(menu3);
		RunMenu.addSeparator(); 
		RunMenu.add(menu1);
		RunMenu.add(menu4);
		RunMenu.add(menu5);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTabbedPane tbMain = new JTabbedPane();
		tbMain.add("Реестры", panel1);
		GroupLayout gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 933, Short.MAX_VALUE)
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 719, Short.MAX_VALUE)
		);
		panel1.setLayout(gl_panel1);
		tbMain.add("Протокол проверки", panel2);
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 933, Short.MAX_VALUE)
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 719, Short.MAX_VALUE)
		);
		panel2.setLayout(gl_panel2);
		getContentPane().add(tbMain);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		Datan = new CustomDateEditor();
		Datak = new CustomDateEditor();
		
		JLabel lbl1 = new JLabel("Отчетный период");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(Datan, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(Datak, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(653, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl1)
						.addComponent(Datan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Datak, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
	}

	public void onConnect() {
//		try {
//		
//		} catch (TException e) {
//			e.printStackTrace();
//			MainForm.conMan.reconnect(e);
//		}
	}
}

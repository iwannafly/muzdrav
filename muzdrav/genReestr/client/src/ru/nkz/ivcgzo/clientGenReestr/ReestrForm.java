package ru.nkz.ivcgzo.clientGenReestr;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;


public class ReestrForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private SettingsForm sfrm;

	public ReestrForm() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(50, 50, 432, 311); //ширина, высота
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(configuration.appName+" за отчетный период");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu RunMenu = new JMenu("Выполнить");
		menuBar.add(RunMenu);
		JMenuItem menu3 = new JMenuItem ("Загрузка файла контроля/оплаты реестров");
		menu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JMenu menu2 = new JMenu("Выгрузка файлов для контроля в ТФ ОМС");
		RunMenu.add(menu2);
		JMenuItem ReestrPolMenu = new JMenuItem ("Реестры посещений");
		ReestrPolMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.showSettingsForm();
			}
		});
		JMenuItem ReestrStMenu = new JMenuItem ("Реестры стационара");
		JMenuItem ReestrLDSMenu = new JMenuItem ("Реестры ЛДС");
		menu2.add (ReestrPolMenu);
		menu2.add (ReestrStMenu);
		menu2.add (ReestrLDSMenu);
		RunMenu.add(menu3);
//		RunMenu.addSeparator(); 

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
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 938, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 36, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
	}

	public void onConnect() {
//		try {
//			System.out.println(MainForm.authInfo.getCslu());
//			System.out.println(Integer.toString(MainForm.authInfo.getCpodr()).length());
//			System.out.println(MainForm.tcl.getPolForCurrentLpu(MainForm.authInfo.getCpodr()));
//			if (Integer.toString(MainForm.authInfo.getCpodr()).length() == 3) MainForm.tcl.getPolForCurrentLpu(MainForm.authInfo.getCpodr());
//			if (Integer.toString(MainForm.authInfo.getCpodr()).length() == 2) MainForm.tcl.getAllPolForCurrentLpu(MainForm.authInfo.getCpodr());
//		} catch (TException e) {
//			e.printStackTrace();
//			MainForm.conMan.reconnect(e);
//		} catch (KmiacServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

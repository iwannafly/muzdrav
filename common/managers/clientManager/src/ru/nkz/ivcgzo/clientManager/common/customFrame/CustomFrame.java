package ru.nkz.ivcgzo.clientManager.common.customFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class CustomFrame extends JFrame {
	private static final long serialVersionUID = -3739385239668692352L;
	public static UserAuthInfo authInfo;
	private RedmineForm redmineFrm;
	private HelpForm helpFrm;
	
	public CustomFrame() {
		redmineFrm = new RedmineForm();
		helpFrm = new HelpForm();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mmFile = new JMenu("Файл");
		menuBar.add(mmFile);
		
		JMenuItem mmFileExtiModule = new JMenuItem("Выход из модуля");
		mmFileExtiModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame cliFrm = ConnectionManager.instance.getMainForm();
				cliFrm.dispatchEvent(new WindowEvent(cliFrm, WindowEvent.WINDOW_CLOSING));
			}
		});
		mmFile.add(mmFileExtiModule);
		
		JMenuItem mmFileExitSystem = new JMenuItem("Выход из системы");
		mmFileExitSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mmFile.add(mmFileExitSystem);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JMenu mmErrors = new JMenu("Ошибки");
		mmErrors.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				redmineFrm.setVisible("auth", true);
			}
		});
		menuBar.add(mmErrors);
		
		JMenu mmHelp = new JMenu("Справка");
		mmHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				helpFrm.setVisible(true);
			}
		});
		menuBar.add(mmHelp);
	}
	
	public static void setAuthInfo(UserAuthInfo authInfo) {
		CustomFrame.authInfo = authInfo;
	}
}

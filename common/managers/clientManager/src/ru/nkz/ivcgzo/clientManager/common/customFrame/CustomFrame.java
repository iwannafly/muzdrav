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
import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class CustomFrame extends JFrame {
	private static final long serialVersionUID = -3739385239668692352L;
	public static UserAuthInfo authInfo;
	public static String redmineServerAddr;
	protected JMenuBar menuBar;
	protected JMenu mmIssues;
	protected JMenu mmHelp;
	private MouseAdapter issuesListener;
	private MouseAdapter helpListener;
	private RedmineForm redmineFrm;
	private HelpForm helpFrm;
	
	public CustomFrame() {
		redmineFrm = new RedmineForm(redmineServerAddr);
		helpFrm = new HelpForm(redmineServerAddr);
		
		menuBar = new JMenuBar();
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
		
		mmIssues = new JMenu("Задачи");
		setMmIssuesListener(null);
		mmIssues.setEnabled(redmineServerAddr != null);
		menuBar.add(mmIssues);
		
		mmHelp = new JMenu("Справка");
		setMmHelpListener(null);
		mmHelp.setEnabled(redmineServerAddr != null);
		menuBar.add(mmHelp);
	}
	
	public static void setAuthInfo(UserAuthInfo authInfo) {
		CustomFrame.authInfo = authInfo;
	}
	
	public void setMmIssuesListener(final String projectId) {
		if (issuesListener != null)
			mmIssues.removeMouseListener(issuesListener);
		
		if (mmIssues.isEnabled()) {
			issuesListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if ((redmineServerAddr != null) && (projectId != null) && (projectId.length() > 0))
						redmineFrm.setVisible(projectId, true);
					else
						JOptionPane.showMessageDialog(CustomFrame.this, "Не указан путь к серверу задач.");
				}
			};
			mmIssues.addMouseListener(issuesListener);
		}
	}
	
	public void setMmHelpListener(final String helpUrl) {
		if (helpListener != null)
			mmHelp.removeMouseListener(helpListener);
		
		if (mmHelp.isEnabled()) {
			helpListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if ((redmineServerAddr != null) && (helpUrl != null) && (helpUrl.length() > 0))
						helpFrm.showHelp(helpUrl);
					else
						JOptionPane.showMessageDialog(CustomFrame.this, "Не указан путь к справке.");
				}
			};
			mmHelp.addMouseListener(helpListener);
		}
	}
}

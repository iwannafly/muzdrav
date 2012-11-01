package ru.nkz.ivcgzo.clientVgr;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftVgr.ThriftVgr;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends Client<ThriftVgr.Client>  {

//	protected static final JFrame Per = null;
	private JFrame frame;
	private Period per;
	public static MainForm instance;
	public static ThriftVgr.Client tcl;
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftVgr.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		setFrame(frame);

		instance = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 504, 444);
		
		JPanel panel = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 442, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 10, Short.MAX_VALUE)
		);
		
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
		);
		frame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Выгрузка данных");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem = new JMenuItem("Диспансеризация КОВ");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				per = new Period();
				per.Cslu = 1;
				per.showPeriod();
			}
		});
		mnNewMenu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Диспансеризация подростков");
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Диспансеризация детей-сирот");
		mnNewMenu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("Родовой сертификат");
		mnNewMenu.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("Регистр женщин фертильного возраста");
		mnNewMenu.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("Регистр больных с орфанными заб-ями");
		mnNewMenu.add(menuItem_5);
		
		JMenuItem menuItem_6 = new JMenuItem("Экспорт карт детей-инвалидов");
		mnNewMenu.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("Экспорт данных о флюоороосмотрах");
		mnNewMenu.add(menuItem_7);
		
		JMenuItem menuItem_8 = new JMenuItem("Экспорт данных по диспансеризации беременных");
		mnNewMenu.add(menuItem_8);
		
		JMenu menu = new JMenu("Подгрузка данных");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		menuBar.add(menu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Подгрузка информации из ПФ");
		menu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Подгрузка информации об областных льготниках");
		menu.add(mntmNewMenuItem_1);
		
//		JMenuBar menuBar_1 = new JMenuBar();
//		menuBar.add(menuBar_1);

	}

	
	public void setVisible(boolean value) {
		frame.setVisible(value);		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
//		component.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			public void mouseReleased(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			private void showMenu(MouseEvent e) {
//				popup.show(e.getComponent(), e.getX(), e.getY());
//			}
//		});
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftVgr.Client) {
			tcl = thrClient;
		}
	}
}

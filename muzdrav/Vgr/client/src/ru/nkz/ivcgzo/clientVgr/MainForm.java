package ru.nkz.ivcgzo.clientVgr;

import java.awt.EventQueue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
//import ru.nkz.ivcgzo.clientOsm.MainForm;
//import ru.nkz.ivcgzo.clientOsm.Vvod;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
//import ru.nkz.ivcgzo.thriftOsm.KartaBer;
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

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends Client<ThriftVgr.Client>  {

//	protected static final JFrame Per = null;
	private JFrame frame;
	private Period per;
	private DopClas dopCl;
	public static MainForm instance;
	public static ThriftVgr.Client tcl;
	String titleString ="", stForm = "";
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
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				per = new Period();
				per.Cslu = 2;
				per.showPeriod();
			}
		});
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
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String servPath = MainForm.tcl.formfilecsv();
					String cliPath;
					String oslname = "kartl";
					cliPath = File.createTempFile(oslname, ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInEditor(cliPath, false);

			}
			catch (TException e1) {
				e1.printStackTrace();
				MainForm.conMan.reconnect(e1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		mnNewMenu.add(menuItem_8);
		
		JMenu menu = new JMenu("Выгрузка информации в АСУ \"Горздрав\"");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		menuBar.add(menu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("1. Статистический талон (форма 025)");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
			titleString = "1. Статистический талон (форма 025)";
			stForm = "F25";
			dopCl = new DopClas();	
			dopCl.setTitle(titleString);
			dopCl.DopClas(titleString,stForm); 
			}
		});
		menu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("2. Учет работы врача (форма 039)");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1){
			titleString = "2. Учет работы врача (форма 039)";
			stForm = "F39";
			dopCl = new DopClas();
			dopCl.setTitle(titleString);
			dopCl.DopClas(titleString, stForm);
			}
		});
		menu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("3. Диспансеризация (форма 030)");
		menu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("4. Дневной стационар (форма 003)");
		menu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("5. Стационар круглосуточного пребывания (форма 066)");
		menu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("6. Свидетельство о смерти (форма 028)");
		menu.add(mntmNewMenuItem_5);
		
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

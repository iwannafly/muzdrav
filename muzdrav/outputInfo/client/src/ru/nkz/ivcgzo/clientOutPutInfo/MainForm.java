package ru.nkz.ivcgzo.clientOutPutInfo;

import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainForm extends Client<ThriftOutputInfo.Client> {

	private JFrame frame;
	
	public static ThriftOutputInfo.Client tcl;
	//public Input_info inputInfo;
	public SvodVed pSvodVed;
	public FacZd pFacZd;
	public tableVrach pTableVrach;
	public PlanDisp pPlanDisp;
	public Ot039 pOt039;

	private JMenuItem menuItemSvodSP;


	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftOutputInfo.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		setFrame(frame);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		final JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		//scrollPane.add(pSvodVed);
		
		final JPanel panel = new JPanel();
		
		panel.removeAll();
		panel.revalidate();
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu mnNewMenu = new JMenu("Поликлиника");
		menuBar.add(mnNewMenu);
		
		JMenu menu_2 = new JMenu("Регламентные сводки");
		mnNewMenu.add(menu_2);
		
		JMenu menu_3 = new JMenu("Сводки по форме 025");
		menu_2.add(menu_3);
		
		JMenuItem menuItem = new JMenuItem("Сводная ведомость учета зарегистрированных заболеваний");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pSvodVed = new SvodVed();
				panel.removeAll();
				panel.add(pSvodVed);
				panel.revalidate();
			}
		});
		menu_3.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Факторы, влияющие на состояние здоровья");
		menu_3.add(menuItem_1);
		
		JMenu menu_4 = new JMenu("Сводки по форме 039");
		menu_2.add(menu_4);
		
		JMenuItem menuItemSvedSP = new JMenuItem("Сведения о структуре посещений");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pOt039 = new Ot039();
				panel.removeAll();
				panel.add(pOt039);
				panel.revalidate();
			}
		});
		menu_4.add(menuItemSvedSP);
		
		JMenu menu_6 = new JMenu("Сводки по диспансеризации");
		menu_2.add(menu_6);
		
		JMenu menu_7 = new JMenu("Отчет по прививкам");
		menu_2.add(menu_7);
		
		JMenu menu_8 = new JMenu("КОВ");
		menu_2.add(menu_8);
		
		JMenu menu_9 = new JMenu("Годовые отчеты");
		menu_2.add(menu_9);
		
		JMenuItem menuItem_2 = new JMenuItem("Табель врача");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pTableVrach = new tableVrach();
				panel.removeAll();
				panel.add(pTableVrach);
				panel.revalidate();
			}
		});
		mnNewMenu.add(menuItem_2);
		
		JMenu menu = new JMenu("Стационар");
		menuBar.add(menu);
		
		JMenu menu_1 = new JMenu("Параотделение");
		menuBar.add(menu_1);
		
		
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		pSvodVed = new SvodVed();
		panel.add(pSvodVed);
		panel.revalidate();
		
		/**
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		pOt039 = new Ot039();
		panel.add(pOt039);
		panel.revalidate();
		*/
	
		//tpReg.addTab("Паспорт участка", pFacZd);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Статистика");
		frame.setBounds(100, 100, 821, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
		});
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftOutputInfo.Client) {
			tcl = thrClient;
			try {

				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}

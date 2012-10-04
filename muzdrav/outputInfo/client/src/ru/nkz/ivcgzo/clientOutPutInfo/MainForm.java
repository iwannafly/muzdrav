package ru.nkz.ivcgzo.clientOutPutInfo;

import java.awt.EventQueue;

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
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

public class MainForm extends Client<ThriftOutputInfo.Client> {

	private JFrame frame;
	
	public static ThriftOutputInfo.Client tcl;
	//public Input_info inputInfo;
	public SvodVed pSvodVed;
	public FacZd pFacZd;
	public tableVrach pTableVrach;


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
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		//inputInfo.setKpolik(authInfo.clpu);
		//inputInfo.setNamepol(authInfo.clpu_name);
		
		/**
		 * Создание панелей с табами (категорий)
		 */
		JTabbedPane tpMain = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpPol = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpStac = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpPar = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpReg = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpZap = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpRees = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpVrTabel = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tp039 = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tp025 = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpDisp = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tp615 = new JTabbedPane(JTabbedPane.TOP);
		JTabbedPane tpVzaim = new JTabbedPane(JTabbedPane.TOP);
		//JTabbedPane tpSvodVed = new JTabbedPane(JTabbedPane.TOP);
		//JTabbedPane tpFacZd = new JTabbedPane(JTabbedPane.TOP);
		
		/**
		 * Создание панелей (классов) во вкладках
		 */
		tableVrach pTableVrach = new tableVrach();
		
		scrollPane.setViewportView(tpMain);

		/**
		 * Создание и привязка вкладок
		 */
		tpMain.addTab("Поликлиника", tpPol);
		tpMain.addTab("Стационар", tpStac);
		tpMain.addTab("Параотделение", tpPar);
		
		tpPol.addTab("Регламентные сводки", tpReg);
		tpPol.addTab("Запросы", tpZap);
		tpPol.addTab("Реестры", tpRees);

		//tp025.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		SvodVed pSvodVed = new SvodVed();
		FacZd pFacZd = new FacZd();
		
		tpReg.addTab("Отчеты по форме 025", tp025);
		tpReg.addTab("Отчеты по форме 039", tp039);
		tpReg.addTab("Сводки по диспансеризации", tpDisp);
		tpReg.addTab("Сводки по пр.№615 и положению 16 ТС", tp615);
		tpReg.addTab("Реестр на взаиморасчеты по параклинике", tpVzaim);
		
		/**
		 * Создание и привязка панелей (классов)
		 */
		
		tpPol.addTab("Табель врача", pTableVrach);
		tp025.addTab("Сводная ведомость учета зарегистрированных заболеваний", pSvodVed);
		tp025.addTab("Факторы, влияющие на состояние здоровья", pFacZd);
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

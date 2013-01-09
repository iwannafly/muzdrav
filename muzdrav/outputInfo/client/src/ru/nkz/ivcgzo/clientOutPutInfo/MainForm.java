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
import java.io.File;
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

import org.apache.thrift.TException;

public class MainForm extends Client<ThriftOutputInfo.Client> {

	private JFrame frame;
	
	public static ThriftOutputInfo.Client tcl;
	//public Input_info inputInfo;
	public SvodVed pSvodVed;
	public StructPos pStructPos;
	public FacZd pFacZd;
	public tableVrach pTableVrach;
	public PlanDisp pPlanDisp;
	public Uchastok pUchastok;


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
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu mnNewMenu = new JMenu("Поликлиника");
		menuBar.add(mnNewMenu);
		
		JMenu menu_2 = new JMenu("Регламентные сводки");
		mnNewMenu.add(menu_2);
		
		JMenu menu_3 = new JMenu("Сводки по форме 025");
		menu_2.add(menu_3);
		
		final JMenuItem menuItem = new JMenuItem("Сводная ведомость учета зарегистрированных заболеваний");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pSvodVed = new SvodVed();
				panel.removeAll();
				panel.add(pSvodVed);
				panel.revalidate();
				frame.setTitle("Статистическая отчетность: "+menuItem.getText());
			}
		});
		menu_3.add(menuItem);
		
		final JMenuItem menuItem_1 = new JMenuItem("Факторы, влияющие на состояние здоровья");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pFacZd = new FacZd();
				panel.removeAll();
				panel.add(pFacZd);
				panel.revalidate();
				frame.setTitle("Статистическая отчетность: "+menuItem_1.getText());
			}
		});
		menu_3.add(menuItem_1);
		
		JMenu menu_4 = new JMenu("Сводки по форме 039");
		menu_2.add(menu_4);
		menu_3.add(menuItem);
		
		final JMenuItem StructPos = new JMenuItem("Сведения о структуре посещений");
		StructPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pStructPos = new StructPos();
				panel.removeAll();
				panel.add(pStructPos);
				panel.revalidate();
			}
		});
		menu_4.add(StructPos);
		
		JMenuItem OtDPol = new JMenuItem("Отчет о деятельности поликлиники");
		menu_4.add(OtDPol);
		
		JMenuItem RaspVr = new JMenuItem("Распределение рабочего времени");
		menu_4.add(RaspVr);
		
		JMenuItem menuItem_4 = new JMenuItem("Посещения врачей поликлиники");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String servPath = MainForm.tcl.printDnevVr();
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
		menu_4.add(menuItem_4);
		
		JMenu menu_6 = new JMenu("Сводки по диспансеризации");
		menu_2.add(menu_6);
		
		JMenuItem menuItem_3 = new JMenuItem("Плановая диспансеризация");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pPlanDisp = new PlanDisp();
				panel.removeAll();
				panel.add(pPlanDisp);
				panel.revalidate();
			}
		});
		menu_6.add(menuItem_3);
		
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
		
		JMenuItem menuUch = new JMenuItem("Справочник участков");
		menuUch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pUchastok = new Uchastok();
				panel.removeAll();
				panel.add(pUchastok);
				panel.revalidate();
			}
		});
		mnNewMenu.add(menuUch);
		
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
		pStructPos = new StructPos();
		panel.add(pStructPos);
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
		frame.setTitle("Сводная ведомость");
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

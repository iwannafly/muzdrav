package ru.nkz.ivcgzo.clientViewSelect;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class NSForm extends Client<ThriftViewSelect.Client> {

	private JFrame frame;
	private JTextField tfSearch;
	public static ThriftViewSelect.Client tcl;
	private static CustomTable<StringClassifier, StringClassifier._Fields> table;

	/**
	 * Launch the application.
	 */
	public NSForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftViewSelect.Client.class, configuration.appId, configuration.thrPort, lncPrm);

		initialize();
		//setFrame(new ViewTablePcodStringForm());
		//setFrame(new ViewTablePcodIntForm());
	}


	/**
	 * Create the application.
	 */
	//public NSForm() {
	//	initialize();
	//}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setTitle("Нормативно-")
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Показать");
		menuBar.add(mnNewMenu);
		
		JMenuItem cbMenuItemStat = new JCheckBoxMenuItem("Статические");
		//cbMenuItem.setMnemonic(KeyEvent.VK_C);
		mnNewMenu.add(cbMenuItemStat);
		cbMenuItemStat.setSelected(true);

		JMenuItem cbMenuItemDyn = new JCheckBoxMenuItem("Редактируемые");
		//cbMenuItem.setMnemonic(KeyEvent.VK_H);
		mnNewMenu.add(cbMenuItemDyn);
		cbMenuItemDyn.setSelected(true);
		
		//JMenuItem mntmNewMenuItem = new JMenuItem("Данные");
		//mntmNewMenuItem.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent arg0) {				
		//		winPat.frame.setVisible(true);
		//		winPat.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//	}
		//});
		//mnNewMenu.add(mntmNewMenuItem);
		
		tfSearch = new JTextField();
		frame.getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
		
		JScrollPane spClassifier = new JScrollPane();
		frame.getContentPane().add(spClassifier, BorderLayout.CENTER);
		table = new CustomTable<>(false, true, StringClassifier.class, 0, "Код", 1, "Наименование");
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		spClassifier.setViewportView(table);
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftViewSelect.Client) {
			tcl = thrClient;
			try { 
				table.setData(MainForm.tcl.getVSStringClassifierView("n_spr"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
	}
	
}

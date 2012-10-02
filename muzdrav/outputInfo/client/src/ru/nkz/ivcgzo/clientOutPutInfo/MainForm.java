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
import ru.nkz.ivcgzo.thriftOutputInfo.Input_info;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends Client<ThriftOutputInfo.Client> {

	private JFrame frame;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public static ThriftOutputInfo.Client tcl;
	public Input_info inputInfo;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftOutputInfo.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		inputInfo.setKpolik(authInfo.clpu);
		inputInfo.setNamepol(authInfo.clpu_name);
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

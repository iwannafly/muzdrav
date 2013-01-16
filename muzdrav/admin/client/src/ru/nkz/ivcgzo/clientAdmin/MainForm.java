package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.customFrame.CustomFrame;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerAdmin.ThriftServerAdmin;
import java.awt.Dimension;

public class MainForm extends Client<ThriftServerAdmin.Client> {
	public static ThriftServerAdmin.Client tcl;
	private CustomFrame frame;
	private JTabbedPane tabbedPane;
	private UserPanel tpUser;
	private ShablonOsmPanel tpShablonOsm;
	private ShablonDopPanel tpShablonDop;
	private ShablonLdsPanel tpShablonLds;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftServerAdmin.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		setFrame(frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new CustomFrame();
		frame.setMinimumSize(new Dimension(896, 128));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/icon_2_32x32.png")));
		frame.setTitle(configuration.appName);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0)
					tpUser.updateVrachTable();
				else if (tabbedPane.getSelectedIndex() == 1)
					tpShablonOsm.prepareShTextFields();
				else if (tabbedPane.getSelectedIndex() == 2)
					tpShablonDop.prepareShTextFields();
				else if (tabbedPane.getSelectedIndex() == 3)
					tpShablonLds.prepareShTextFields();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tpUser = new UserPanel();
		tabbedPane.addTab("Пользователи", null, tpUser, null);
		
		tpShablonOsm = new ShablonOsmPanel();
		tabbedPane.addTab("Шаблоны осмотра", null, tpShablonOsm, null);
		
		tpShablonDop = new ShablonDopPanel();
		tabbedPane.addTab("Дополнительные шаблоны", null, tpShablonDop, null);
		
		tpShablonLds = new ShablonLdsPanel();
		tabbedPane.addTab("Шаблоны параотделения", null, tpShablonLds, null);
		
		frame.getContentPane().setLayout(groupLayout);
		
		frame.setLocationRelativeTo(null);
	}

	public void onConnect() throws TException {
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftServerAdmin.Client) {
			tcl = thrClient;
			try {
				tpUser.onConnect();
			} catch (TException e) {
				e.printStackTrace();
				conMan.reconnect(e);
			}
		}
	}
}

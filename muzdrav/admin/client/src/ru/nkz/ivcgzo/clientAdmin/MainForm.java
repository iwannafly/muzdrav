package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerAdmin.ThriftServerAdmin;

public class MainForm extends Client<ThriftServerAdmin.Client> {
	private final boolean adminMode;
	public static ThriftServerAdmin.Client tcl;
	private JFrame frame;
	private UserPanel tpUser;
	private ShablonPanel tpShablon;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftServerAdmin.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		adminMode = lncPrm == 2;
		
		initialize();
		
		setFrame(frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/icon_2_32x32.png")));
		frame.setTitle(configuration.appName);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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
		
		tpUser = new UserPanel(adminMode);
		tabbedPane.addTab("Пользователи", null, tpUser, null);
		
		tpShablon = new ShablonPanel();
		tabbedPane.addTab("Шаблоны", null, tpShablon, null);
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
				tpShablon.setCdolList(tcl.get_n_s00());
			} catch (TException e) {
				e.printStackTrace();
				conMan.reconnect(e);
			}
		}
	}
}

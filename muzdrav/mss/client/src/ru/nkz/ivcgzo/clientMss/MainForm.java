package ru.nkz.ivcgzo.clientMss;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class MainForm extends Client {
	private JFrame frame;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) {
		super(conMan, authInfo, lncPrm);
		// TODO Auto-generated constructor stub
		
		initialize();
		
		frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(configuration.appName);
	}
	
	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public int getId() {
		return configuration.appId;
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		// TODO Auto-generated method stub

	}
}

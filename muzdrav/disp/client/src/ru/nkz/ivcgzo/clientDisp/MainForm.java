package ru.nkz.ivcgzo.clientDisp;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp;

public class MainForm extends Client<ThriftDisp.Client>{
	public static ThriftDisp.Client tcl;
	public static MainForm instance;
	private JFrame frame;


	public MainForm (ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftDisp.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		initialize();
		
		setFrame(frame);
		
		instance = this;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

}

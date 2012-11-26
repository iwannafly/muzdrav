package ru.nkz.ivcgzo.clientPbol;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;

public class MainForm extends Client<ThriftPbol.Client>{
	public static ThriftPbol.Client tcl;
	public static MainForm instance;
	private JFrame frame;

	public MainForm (ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftPbol.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		initialize();
		
		setFrame(frame);
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Диспансеризация детей");
		frame.setBounds(100, 100, 995, 737);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftPbol.Client) {
			tcl = thrClient;
			onTclConnect();
		}
		
	}
	
	public void onTclConnect() {
//		try {
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}

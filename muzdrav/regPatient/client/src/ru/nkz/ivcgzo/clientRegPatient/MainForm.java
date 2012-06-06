package ru.nkz.ivcgzo.clientRegPatient;

import java.lang.reflect.InvocationTargetException;

import javax.swing.UnsupportedLookAndFeelException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient;

public class MainForm extends Client<ThriftRegPatient.Client> {
    public static ThriftRegPatient.Client tcl;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, UnsupportedLookAndFeelException {
		super(conMan, authInfo, ThriftRegPatient.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		PacientMainFrame pacientMainFrame = new PacientMainFrame();
		pacientMainFrame.pack();
		setFrame(pacientMainFrame);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftRegPatient.Client) {
			tcl = thrClient;
		}
	}

}

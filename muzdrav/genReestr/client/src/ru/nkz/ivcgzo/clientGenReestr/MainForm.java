package ru.nkz.ivcgzo.clientGenReestr;

import java.lang.reflect.InvocationTargetException;

import javax.swing.UnsupportedLookAndFeelException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr;

public class MainForm extends Client<ThriftGenReestr.Client> {
    public static ThriftGenReestr.Client tcl;
    public static Client<ThriftGenReestr.Client> instance;
    private ReestrForm reestrform;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, UnsupportedLookAndFeelException {
		super(conMan, authInfo, ThriftGenReestr.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		instance = this;
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		reestrform = new ReestrForm();
		reestrform.pack();
		setFrame(reestrform);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftGenReestr.Client) {
			tcl = thrClient;
			reestrform.onConnect();
		}
	}

}

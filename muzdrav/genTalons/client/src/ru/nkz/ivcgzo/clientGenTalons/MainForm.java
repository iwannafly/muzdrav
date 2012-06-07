package ru.nkz.ivcgzo.clientGenTalons;

import java.lang.reflect.InvocationTargetException;

import javax.swing.UnsupportedLookAndFeelException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;

public class MainForm extends Client<ThriftGenTalons.Client> {
    public static ThriftGenTalons.Client tcl;

    public MainForm(ConnectionManager conMan, UserAuthInfo authInfo,
            int accessParam) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ClassNotFoundException, UnsupportedLookAndFeelException {
        super(conMan, authInfo, ThriftGenTalons.Client.class, configuration.appId,
                configuration.thrPort, accessParam);
        
        initialize();
    }

    private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

    }

    @Override
    public String getName() {
        return configuration.appName;
    }

    @Override
    public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftGenTalons.Client) {
            tcl = thrClient;
        }
    }

}

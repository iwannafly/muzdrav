package ru.nkz.ivcgzo.clientHospital;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.thrift.TException;
import ru.nkz.ivcgzo.configuration;

import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital;

public class ClientHospital extends Client<ThriftHospital.Client> {

    public static ThriftHospital.Client tcl;
    private MainFrame mainFrame;
    public static Client<ThriftHospital.Client> instance;

    public ClientHospital(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException,
            MalformedURLException, ClassNotFoundException, UnsupportedLookAndFeelException,
            TException {
        super(conMan, authInfo, ThriftHospital.Client.class, configuration.appId,
                configuration.thrPort, accessParam);
        initialize(authInfo);
        instance = this;
    }

    private void initialize(final UserAuthInfo authInfo) throws MalformedURLException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, TException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        mainFrame = new MainFrame(authInfo);
        mainFrame.pack();
        setFrame(mainFrame);
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftHospital.Client) {
            tcl = thrClient;
            mainFrame.onConnect();
        }
    }
}

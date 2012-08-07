package ru.ivcgzo.nkz.clientReception;

import java.lang.reflect.InvocationTargetException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftReception.ThriftReception;

public class MainForm extends Client<ThriftReception.Client> {
    public static ThriftReception.Client tcl;
    public static Client<ThriftReception.Client> instance;

    public MainForm(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException {
        super(conMan, authInfo, ThriftReception.Client.class, configuration.appId,
                configuration.thrPort, accessParam);

        initialize();
        instance = this;
    }

    private void initialize() {
        TalonSelectFrame talonSelectFrame = new TalonSelectFrame();
        talonSelectFrame.pack();
        setFrame(talonSelectFrame);
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftReception.Client) {
            tcl = thrClient;
        }
    }

}

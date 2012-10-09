package ru.nkz.ivczo.clientLab;

import java.lang.reflect.InvocationTargetException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftLab.ThriftLab;

public class ClientLab extends Client<ThriftLab.Client>{
    public static ThriftLab.Client tcl;
    public static Client<ThriftLab.Client> instance;

    public ClientLab(ConnectionManager conMan, UserAuthInfo authInfo,
            int accessParam) throws IllegalAccessException, NoSuchMethodException,
            SecurityException, InstantiationException,
            IllegalArgumentException, InvocationTargetException {
        super(conMan, authInfo, ThriftLab.Client.class, configuration.appId,
                configuration.thrPort, accessParam);
    }

    @Override
    public String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftLab.Client) {
            tcl = thrClient;
        }
    }

}

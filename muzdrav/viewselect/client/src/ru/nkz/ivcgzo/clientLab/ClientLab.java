package ru.nkz.ivcgzo.clientLab;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftLab.ThriftLab;

public class ClientLab extends Client<ThriftLab.Client> {
    public static ThriftLab.Client tcl;
    public static Client<ThriftLab.Client> instance;
    private MainFrame labFrame;

    public ClientLab(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException {
        super(conMan, authInfo, ThriftLab.Client.class, configuration.labAppId,
                configuration.labThrPort, accessParam);
        initialize(authInfo);
        instance = this;
    }

    private void initialize(final UserAuthInfo authInfo) {
        labFrame = new MainFrame(authInfo);
        labFrame.pack();
        setFrame(labFrame);
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftLab.Client) {
            tcl = thrClient;
            labFrame.onConnect();
        }
    }

    @Override
    public final Object showModal(final IClient parent, final Object... params) {
//        labFrame.setTitle(String.format("%s %s %s",
//            (String) params[1], (String) params[2], (String) params[3]));
        labFrame.setTitle("Лабораторные и диагностические исследования");
        JDialog dialog = prepareModal(parent);
        labFrame.fillPatient((int) params[0], (String) params[1],
            (String) params[2], (String) params[3], (int) params[4],
            (String) params[5]);
        dialog.setVisible(true);
        disposeModal();
        return null;
    }

}

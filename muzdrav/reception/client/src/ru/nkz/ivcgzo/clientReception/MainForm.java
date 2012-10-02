package ru.nkz.ivcgzo.clientReception;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftReception.ThriftReception;

public class MainForm extends Client<ThriftReception.Client> {
    public static ThriftReception.Client tcl;
    public static Client<ThriftReception.Client> instance;
    private TalonSelectFrame talonSelectFrame;

    public MainForm(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException {
        super(conMan, authInfo, ThriftReception.Client.class, configuration.appId,
                configuration.thrPort, accessParam);

        initialize(authInfo);
        instance = this;
    }

    private void initialize(final UserAuthInfo authInfo) {
        talonSelectFrame = new TalonSelectFrame(authInfo);
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
            talonSelectFrame.onConnect();
        }
    }

    @Override
    public final Object showModal(final IClient parent, final Object... params) {
        JDialog dialog = prepareModal(parent);
        talonSelectFrame.fillPatientInfoLabels((int) params[0], (String) params[1],
                (String) params[2], (String) params[3], (long) params[4], (int) params[5]);
        dialog.setVisible(true);
        disposeModal();
        return null;
    }

}

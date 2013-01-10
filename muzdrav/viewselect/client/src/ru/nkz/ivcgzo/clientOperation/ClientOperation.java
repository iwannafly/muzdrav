package ru.nkz.ivcgzo.clientOperation;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOperation.ThriftOperation;

public class ClientOperation extends Client<ThriftOperation.Client> {
    public static ThriftOperation.Client tcl;
    public static Client<ThriftOperation.Client> instance;
    private MainFrame operationFrame;

    public ClientOperation(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException {
        super(conMan, authInfo, ThriftOperation.Client.class, configuration.opAppId,
                configuration.opThrPort, accessParam);
        initialize();
        instance = this;
    }

    private void initialize() {
        operationFrame = new MainFrame();
        operationFrame.pack();
        setFrame(operationFrame);
    }

    @Override
    public String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftOperation.Client) {
            tcl = thrClient;
            operationFrame.onConnect();
        }
    }

     @Override
    public final Object showModal(final IClient parent, final Object... params) {
        operationFrame.setTitle("Операции");
        JDialog dialog = prepareModal(parent);
        dialog.setVisible(true);
        disposeModal();
        return null;
    }
}

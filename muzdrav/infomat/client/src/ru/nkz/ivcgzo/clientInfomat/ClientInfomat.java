package ru.nkz.ivcgzo.clientInfomat;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientInfomat.model.Model;
import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftInfomat.ThriftInfomat;

public class ClientInfomat extends Client<ThriftInfomat.Client>{
    public static ThriftInfomat.Client tcl;
    public static Client<ThriftInfomat.Client> instance;

    public ClientInfomat(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException,
            MalformedURLException, ClassNotFoundException, UnsupportedLookAndFeelException,
            TException {
        super(conMan, authInfo, ThriftInfomat.Client.class, configuration.appId,
                configuration.thrPort, accessParam);
        initialize();
        instance = this;
    }

    private void initialize() throws MalformedURLException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, TException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        IModel model = new Model();
        IController controller = new Controller(model);

        // FIXME костыль, в контроллере должны быть только войд методы
        setFrame(controller.getMainFrame());
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftInfomat.Client) {
            tcl = thrClient;
        }
    }
}

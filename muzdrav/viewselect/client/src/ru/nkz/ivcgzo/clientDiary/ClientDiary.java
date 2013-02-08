package ru.nkz.ivcgzo.clientDiary;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftDiary.ThriftDiary;

public class ClientDiary  extends Client<ThriftDiary.Client> {
    public static ThriftDiary.Client tcl;
    public static Client<ThriftDiary.Client> instance;
    private MainFrame diaryFrame;

    public ClientDiary(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException {
        super(conMan, authInfo, ThriftDiary.Client.class, configuration.labAppId,
                configuration.diaryThrPort, accessParam);
        initialize(authInfo);
        instance = this;
    }

    private void initialize(final UserAuthInfo authInfo) {
        diaryFrame = new MainFrame();
        diaryFrame.pack();
        setFrame(diaryFrame);
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftDiary.Client) {
            tcl = thrClient;
            diaryFrame.onConnect();
        }
    }

    @Override
    public final Object showModal(final IClient parent, final Object... params) {
//        labFrame.setTitle(String.format("%s %s %s",
//            (String) params[1], (String) params[2], (String) params[3]));
        diaryFrame.setTitle("Дневник осмотров");
        JDialog dialog = prepareModal(parent);
        diaryFrame.fillPatient((int) params[0], (String) params[1],
            (String) params[2], (String) params[3], (int) params[4]);
        dialog.setVisible(true);
        disposeModal();
        return null;
    }

}

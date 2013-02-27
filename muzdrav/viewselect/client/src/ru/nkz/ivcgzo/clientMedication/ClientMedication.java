package ru.nkz.ivcgzo.clientMedication;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication;

public class ClientMedication extends Client<ThriftMedication.Client> {
    public static ThriftMedication.Client tcl;
    public static Client<ThriftMedication.Client> instance;
    private MainFrame medicationFrame;

    public ClientMedication(final ConnectionManager conMan, final UserAuthInfo authInfo,
            final int accessParam) throws IllegalAccessException, NoSuchMethodException,
            InstantiationException, InvocationTargetException {
        super(conMan, authInfo, ThriftMedication.Client.class, configuration.medAppId,
                configuration.medThrPort, accessParam);
        initialize(authInfo);
        instance = this;
    }

    private void initialize(final UserAuthInfo authInfo) {
        medicationFrame = new MainFrame(authInfo);
        medicationFrame.pack();
        setFrame(medicationFrame);
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public final void onConnect(
            final ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
        super.onConnect(conn);
        if (conn instanceof ThriftMedication.Client) {
            tcl = thrClient;
            medicationFrame.onConnect();
        }
    }

    @Override
    public final Object showModal(final IClient parent, final Object... params) {
//        medicationFrame.setTitle(String.format("%s %s %s",
//            (String) params[1], (String) params[2], (String) params[3]));
        medicationFrame.setTitle("Медицинские назначения");
        final JDialog dialog = prepareModal(parent);
        medicationFrame.fillPatient((int) params[0], (String) params[1],
            (String) params[2], (String) params[3], (int) params[4]);
        medicationFrame.prepareFrame();
        dialog.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowOpened(WindowEvent e) {
        		((MainFrame)getFrame()).btnAddClick();
        	}
		});
        ((MainFrame)getFrame()).frmMedicationCatalog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            }
        });
        dialog.setVisible(true);
        disposeModal();
        return null;
    }

}

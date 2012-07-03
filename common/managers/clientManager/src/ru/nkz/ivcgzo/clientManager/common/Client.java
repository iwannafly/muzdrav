package ru.nkz.ivcgzo.clientManager.common;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

/**
 * Главный класс плагин-клиента должен наследовать от этого класса.
 * @author bsv798
 */
public abstract class Client <T extends KmiacServer.Client> implements IClient {
	public static ConnectionManager conMan;
	public static UserAuthInfo authInfo;
	public static int accessParam;
	private Class<T> thrClass;
	private static int appId;
	private static int thrPort;
	public T thrClient;
	private JFrame frame;
	
	public Client(ConnectionManager conMan, UserAuthInfo authInfo, Class<T> thrClass, int appId, int thrPort, int accessParam, String... params) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Client.conMan = conMan;
		Client.authInfo = authInfo;
		Client.accessParam = accessParam;
		this.thrClass = thrClass;
		Client.appId = appId;
		Client.thrPort = thrPort;
		
		conMan.add(thrClass, thrPort);
	}
	
	public Client(ConnectionManager conMan, UserAuthInfo authInfo, int accessParam) throws IllegalAccessException {
		throw new IllegalAccessException("This constructor shouldn't be called directly. Use extended form.");
	}
	
	@Override
	public void setFrame(JFrame frame) {
		this.frame = frame;
		setDisconnectOnFrameClose();
		
		//FIXME uncomment to enable return to plugin selection form
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public JFrame getFrame() {
		if (frame != null)
			return frame;
		else
			throw new IllegalArgumentException("No displayable frame set.");
	}
	
	@Override
	public void showNormal() {
		getFrame().setVisible(true);
	}
	
	@Override
	public String[] showModal(IClient parent) {
		ModalExclusionType parentModalType = parent.getFrame().getModalExclusionType();
		
		parent.getFrame().setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		
		try {
			JDialog dialog = new JDialog(parent.getFrame());
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setContentPane(getFrame().getContentPane());
			dialog.pack();
			dialog.setLocationRelativeTo(parent.getFrame());
			conMan.setClient(this);
			conMan.connect();
			dialog.setVisible(true);
			conMan.remove(getPort());
		} catch (TException e) {
			e.printStackTrace();
			conMan.reconnect(e);
		} finally {
			conMan.setClient(parent);
			parent.getFrame().setModalExclusionType(parentModalType);
		}
		
		return null;
	}
	
	private void setDisconnectOnFrameClose() {
		getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				conMan.remove();
				
				super.windowClosed(e);
			}
		});
	}
	
	@Override
	public void onConnect(KmiacServer.Client conn) {
		if (conn.getClass() == thrClass)
			thrClient = thrClass.cast(conn);
	}
	
	@Override
	public int getId() {
		return appId;
	}
	
	@Override
	public int getPort() {
		return thrPort;
	}
}

package ru.nkz.ivcgzo.clientManager.common;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
	private int appId;
	private int thrPort;
	public T thrClient;
	private JFrame frame;
	private IClient parent;
	private JDialog dialog;
	private ModalExclusionType prevModalType;
	private List<Window> childList;
	
	public Client(ConnectionManager conMan, UserAuthInfo authInfo, Class<T> thrClass, int appId, int thrPort, int accessParam, String... params) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Client.conMan = conMan;
		Client.authInfo = authInfo;
		Client.accessParam = accessParam;
		this.thrClass = thrClass;
		this.appId = appId;
		this.thrPort = thrPort;
		childList = new ArrayList<>();
		
		conMan.add(thrClass, thrPort);
	}
	
	public Client(ConnectionManager conMan, UserAuthInfo authInfo, int accessParam) throws IllegalAccessException {
		throw new IllegalAccessException("This constructor shouldn't be called directly. Use extended form.");
	}
	
	@Override
	public void setFrame(JFrame frame) {
		this.frame = frame;
		setDisconnectOnFrameClose();
		
//FIXME	uncomment to enable return to plugin selection form
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public JFrame getFrame() {
		if (frame != null)
			return frame;
		else
			throw new IllegalArgumentException("No displayable frame set.");
	}
	
	/**
	 * Добавление дочернего окна в список. Окна из списка закрываются при
	 * закрытии модальной формы. 
	 */
	public void addChildFrame(Window child) {
		child.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		childList.add(child);
	}
	
	@Override
	public void showNormal() {
		getFrame().setVisible(true);
	}
	
	@Override
	public Object showModal(IClient parent, Object... params) {
		return null;
	}
	
	/**
	 * Преобразует форму, установленную методом {@link #setFrame(JFrame)} в
	 * модальный вид и устанавливает подключение к плагин-серверам.
	 * @param parent - родительский плагин-клиент
	 */
	public JDialog prepareModal(IClient parent) {
		if (dialog != null)
			throw new RuntimeException("Modal dialog already prepared.");
		
		dialog = new JDialog(getFrame());
		
		this.parent = parent;
		prevModalType = getFrame().getModalExclusionType();
		
		this.getFrame().setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		
		try {
			dialog.setMinimumSize(getFrame().getMinimumSize());
			dialog.setBounds(getFrame().getBounds());
			dialog.setTitle(getFrame().getTitle());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setContentPane(getFrame().getContentPane());
			dialog.revalidate();
			if (!(getFrame() instanceof ModalForm) || (((ModalForm) getFrame()).getModalLocationRelativeToParent()))
					dialog.setLocationRelativeTo(parent.getFrame());
			conMan.setClient(this);
			conMan.connect();
		} catch (TException e) {
			e.printStackTrace();
			conMan.setClient(parent);
			conMan.reconnect(e);
		}
		
		return dialog;
	}
	
	/**
	 * Закрывает все отрытые дочерние окна модальной формы и отключает ее от
	 * плагин-серверов.
	 */
	public void disposeModal() {
		conMan.setClient(parent);
		parent.getFrame().setModalExclusionType(prevModalType);
		disposeChildren();
		if (this != parent)
			conMan.disconnect(getPort());
		dialog.dispose();
		dialog = null;
	}
	
	private void setDisconnectOnFrameClose() {
		getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				conMan.remove();
				disposeChildren();
				
				super.windowClosed(e);
			}
		});
	}
	
	private void disposeChildren() {
		for (Window child : childList)
			child.dispose();
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

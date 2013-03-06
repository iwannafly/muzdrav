package ru.nkz.ivcgzo.clientManager.common;

import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.FileNotFoundException;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.FileTransfer;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.OpenFileException;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.fileTransferConstants;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

/**
 * Класс, подключающий клиентов к трифт-серверам и следящий за актуальностью этих
 * подключений. Так же, осуществляет автоматическое обновление модулей и
 * предоставляет клиентам возможность принимать/передавать файлы на сервер
 * приложений.
 * @author bsv798
 */
public class ConnectionManager {
	public static ConnectionManager instance;
	private final String appServerIp;
	private Map<Integer, TTransport> transports;
	private Map<Integer, KmiacServer.Client> connections;
	private Map<Integer, TTransport> commonTransports;
	private Map<Integer, KmiacServer.Client> commonConnections;
	private FileTransfer.Client filTrans;
	private PluginLoader pLdr;
	
	private IClient client;
	private JFrame mainForm;
	private boolean connecting;
	private JDialog reconnectForm;
	private Thread reconnectThread;
	private IClient viewClient;
	
	/**
	 * Конструктор класса.
	 * @param mainForm - родительская форма, над которой будут высвечиваться формы
	 * с сообщениями о переподключении к серверам или наличии обновления.
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public <T extends FileTransfer.Client> ConnectionManager(String appServerIp, JFrame mainForm, Class<T> filTransCls, int filTransPort) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, ClassNotFoundException, IOException {
		instance = this;
		this.appServerIp = appServerIp;
		
		transports = new HashMap<>();
		connections = new HashMap<>();
		commonTransports = new HashMap<>();
		commonConnections = new HashMap<>();
		
		this.mainForm = mainForm;
		initReconnectForm();
		
		filTrans = add(filTransCls, filTransPort);
		
		addToCommon(filTransPort);
	}
	
	/**
	 * Установка модуля, для которого будет проверяться актуальность подключений и
	 * наличие обновлений.
	 */
	public void setClient(IClient client) {
		this.client = client;
		this.mainForm = client.getFrame();
	}
	
	/**
	 * Получение модуля, для которого будет проверяться актуальность подключений и
	 * наличие обновлений.
	 */
	public IClient getClient() {
		return client;
	}
	
	/**
	 * Получение главной формы клиента.
	 */
	public JFrame getMainForm() {
		return mainForm;
	}
	
	private void addToCommon(int port) {
		commonTransports.put(port, transports.get(port));
		commonConnections.put(port, connections.get(port));
	}
	
	private void restoreCommonConnections() {
		for (Integer key : commonTransports.keySet()) {
			transports.put(key, commonTransports.get(key));
			connections.put(key, commonConnections.get(key));
		}
	}
	
	public PluginLoader createPluginLoader(UserAuthInfo authInfo) {
		pLdr = new PluginLoader(this, authInfo);
		
		return getPluginLoader();
	}
	
	public PluginLoader getPluginLoader() {
		return pLdr;
	}
	
	public void loadViewClient() throws Exception {
		viewClient = getPluginLoader().loadPluginByAppId(7);
		client = viewClient;
		connect(client.getPort());
		
		addToCommon(client.getPort());
	}
	
	public IClient getViewClient() {
		return viewClient;
	}
	
	/**
	 * Добавление подключений, за которыми будет вестись наблюдение.
	 * @param cls - клиентская часть трифтового сервиса
	 * @param port - порт, на котором работает соответствующий клиенту сервер
	 * @return
	 * экземпляр клиентской части
	 */
	@SuppressWarnings("unchecked")
	public <T extends KmiacServer.Client> T add(Class<T> cls, int port) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T connection;
		
		if (!connections.containsKey(port)) {
			Constructor<T> constructor = cls.getConstructor(TProtocol.class);
			TTransport transport = new TFramedTransport(new TSocket(appServerIp, port));
			connection = constructor.newInstance(new TBinaryProtocol(transport));
			transports.put(port, transport);
			connections.put(port, connection);
		} else {
			connection = (T) connections.get(port);
		}
		
		return connection;
	}
	
	/**
	 * Закрывает подключение и удаляет его из списка наблюдаемых.
	 */
	public void remove(int port) {
		disconnect(port);
		transports.remove(port);
		connections.remove(port);
	}
	
	/**
	 * Удаляет все подключения.
	 */
	public void remove() {
		Integer[] ports = transports.keySet().toArray(new Integer[0]);
		for (int i = 0; i < ports.length; i++) {
			remove(ports[i]);
		}
	}
	
	/**
	 * Получает экземпляр подключения.
	 */
	public KmiacServer.Client get(int port) {
		return connections.get(port);
	}
	
	/**
	 * Подключение ко всем трифт-серверам.
	 */
	public void connect() throws TException {
		restoreCommonConnections();
		
		for (Integer key : transports.keySet())
			connect(key);
	}
	
	/**
	 * Подключение к трифт-серверу.
	 */
	public void connect(int port) throws TException {
		try {
			TTransport transport = transports.get(port);
			KmiacServer.Client connection = connections.get(port);
			
			if (transport.isOpen())
				transport.close();
			transport.open();
			if (client != null)
				client.onConnect(connection);
		} catch (TTransportException e) {
			throw new ConnectionException(e);
		}
	}

	/**
	 * Отключение от всех трифт-серверов.
	 */
	public void disconnect() {
		for (Integer key : transports.keySet()) {
			disconnect(key);
		}
	}
	
	/**
	 * Отключение от трифт-сервера.
	 */
	public void disconnect(int port) {
		transports.get(port).close();
	}
	
	/**
	 * Переподключение к трифт-серверам.
	 * @param e - исключение, приведшее к необходимости переподключения.
	 */
	public void reconnect(TException e) {
		try {
			throw new ConnectionException(e);
		} catch (ConnectionException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Проверка актуальности подключений к трифт-серверам.
	 * @return
	 */
	private boolean checkAll() {
		for (Integer key : transports.keySet()) {
			try {
					TTransport transport = transports.get(key);
					KmiacServer.Client connection = connections.get(key);
					
					if (!transport.isOpen()) {
							transport.open();
							connection.testConnection();
							if (client != null)
								client.onConnect(connection);
					} else
						connection.testConnection();
			} catch (TException e) {
				disconnect(key);
				return false;
			}
		}
		
		return true;
	}
	
	public class ConnectionException extends TException {
		private static final long serialVersionUID = 477521460024129062L;
		
		public ConnectionException(TException e) {
			super(e);
			reconnect();
		}

		private void reconnect() {
			if (!connecting)
				if (!checkAll()) {
					reconnectThread = new Thread(new Runnable() {
						@Override
						public synchronized void run() {
							connecting = true;
							while (!checkAll()) {
								try {
									if (!connecting) {
										System.exit(3);
									}
									Thread.sleep(500);
								} catch (InterruptedException e) {
								}
							}
							connecting = false;
							closeReconnectForm();
						}
					});
					
					reconnectThread.start();
					showReconnectForm();
				}
		}
		
	}
	
	private void initReconnectForm() {
		reconnectForm = new JDialog();
		reconnectForm.setTitle("Переподключение");
		reconnectForm.setModalityType(ModalityType.APPLICATION_MODAL);
		reconnectForm.setResizable(false);
		reconnectForm.setBounds(100, 100, 450, 300);
		
		JLabel lblClose = new JLabel("<html>Идет переподключение к серверам. Вы можете подождать или закрыть программу</html>");
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnClose = new JButton("Закрыть");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeReconnectForm();
			}
		});
		GroupLayout groupLayout = new GroupLayout(reconnectForm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblClose, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addComponent(btnClose, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblClose, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClose)
					.addContainerGap())
		);
		reconnectForm.getContentPane().setLayout(groupLayout);
		
		reconnectForm.addWindowListener(new WindowAdapter() {
			@Override
			public synchronized void windowClosing(WindowEvent e) {
				if (connecting) {
					connecting = false;
				}
			}
		});
	}
	
	private void showReconnectForm() {
		reconnectForm.setLocationRelativeTo(mainForm);
		reconnectForm.setVisible(true);
	}
	
	private void closeReconnectForm() {
		reconnectForm.dispatchEvent(new WindowEvent(reconnectForm, WindowEvent.WINDOW_CLOSING));
	}
	
	
	public int openReadServerSocket(String path) throws FileNotFoundException, OpenFileException, TException {
		return filTrans.openReadServerSocket(path);
	}

	public int openWriteServerSocket(String path) throws OpenFileException, TException {
		return filTrans.openWriteServerSocket(path);
	}
	
	public void closeServerSocket(int port, boolean delFile) throws TException {
		filTrans.closeServerSocket(port, delFile);
	}
	
	public void transferFileToServer(String srcPath, String dstPath) throws java.io.FileNotFoundException, IOException, OpenFileException, TException {
		int port = openWriteServerSocket(dstPath);
		try (FileInputStream fis = new FileInputStream(srcPath);
				Socket socket = new Socket(appServerIp, port);) {
			byte[] buf = new byte[fileTransferConstants.bufSize];
			int read = fileTransferConstants.bufSize;
			while (read == fileTransferConstants.bufSize) {
				read = fis.read(buf);
				socket.getOutputStream().write(buf, 0, read);
			}
			socket.getOutputStream().close();
		} finally {
			closeServerSocket(port, false);
		}
	}

	public void transferFileFromServer(String srcPath, String dstPath) throws java.io.FileNotFoundException, IOException, FileNotFoundException, OpenFileException, TException {
		int port = openReadServerSocket(srcPath);
		try (FileOutputStream fos = new FileOutputStream(dstPath);
				Socket socket = new Socket(appServerIp, port);) {
			byte[] buf = new byte[fileTransferConstants.bufSize];
			int read = socket.getInputStream().read(buf);
			while (read > -1) {
				fos.write(buf, 0, read);
				read = socket.getInputStream().read(buf);
			}
		} finally {
			closeServerSocket(port, false);
		}
	}
	
	public void saveConfig(UserAuthInfo authInfo) throws TException {
		filTrans.saveUserConfig(authInfo.user_id, authInfo.config);
	}
	
	/**
	 * Вызов общей формы поиска пациентов.
	 * @param title - заголовок формы
	 * @param clearFields - очистка полей и прошлых результатов поиска
	 * @param disableOptionalParams - выключение опциональных параметров поиска
	 * @return массив кодов пациентов или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public int[] showPatientSearchForm(String title, boolean clearFields, boolean disableOptionalParams) {
		Object srcRes = viewClient.showModal(client, 1, title, clearFields, disableOptionalParams);
		
		if (srcRes != null)
			return (int[]) srcRes;
		
		return null;
	}
	
	/**
	 * Вызов формы с древовидным отображением диагнозов.
	 * @param title - заголовок формы
	 * @param pcod - текущее значение кода диагноза
	 * @return выбранный диагноз или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public StringClassifier showMkbTreeForm(String title, String pcod) {
		Object srcRes = viewClient.showModal(client, 2, title, pcod);
		
		if (srcRes != null)
			return (StringClassifier) srcRes;
		
		return null;
	}
	
	/**
	 * Показ формы с отсортированным классификатором, в котором код - число.
	 * @param cls - название классификатора
	 * @param ord - порядок сортировки
	 * @param fld - поля для сортировки
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public IntegerClassifier showIntegerClassifierSelector(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		Object res = viewClient.showModal(client, 7, cls, ord, fld);
		
		if (res != null)
			return (IntegerClassifier) res;
		
		return null;
	}
	
	/**
	 * Показ формы с неотсортированным классификатором, в котором код - число.
	 * @param cls - название классификатора
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public IntegerClassifier showIntegerClassifierSelector(IntegerClassifiers cls) {
		Object res = viewClient.showModal(client, 8, cls);
		
		if (res != null)
			return (IntegerClassifier) res;
		
		return null;
	}
	
	/**
	 * Показ формы с произвольным классификатором, в котором код - число.
	 * @param lst - классификатор
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public IntegerClassifier showIntegerClassifierSelector(List<IntegerClassifier> lst) {
		Object res = viewClient.showModal(client, 29, lst);
		
		if (res != null)
			return (IntegerClassifier) res;
		
		return null;
	}
	
	/**
	 * Показ формы с отсортированным классификатором, в котором код - строка.
	 * @param cls - название классификатора
	 * @param ord - порядок сортировки
	 * @param fld - поля для сортировки
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public StringClassifier showStringClassifierSelector(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		Object res = viewClient.showModal(client, 9, cls, ord, fld);
		
		if (res != null)
			return (StringClassifier) res;
		
		return null;
	}
	
	/**
	 * Показ формы с неотсортированным классификатором, в котором код - строка.
	 * @param cls - название классификатора
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public StringClassifier showStringClassifierSelector(StringClassifiers cls) {
		Object res = viewClient.showModal(client, 10, cls);
		
		if (res != null)
			return (StringClassifier) res;
		
		return null;
	}
	
	/**
	 * Показ формы с произвольным классификатором, в котором код - строка.
	 * @param lst - классификатор
	 * @return выбранное значение или <code>null</code>, если
	 * пользователь закрыл форму
	 */
	public StringClassifier showStringClassifierSelector(List<StringClassifier> lst) {
		Object res = viewClient.showModal(client, 30, lst);
		
		if (res != null)
			return (StringClassifier) res;
		
		return null;
	}
	
	/**
	 * Загрузка отсортированного классификатора, в котором код - число.
	 * @param cls - название классификатора
	 * @param ord - порядок сортировки
	 * @param fld - поля для сортировки
	 */
	@SuppressWarnings("unchecked")
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		Object res = viewClient.showModal(client, 3, cls, ord, fld);
		
		if (res != null)
			return (List<IntegerClassifier>) res;
		
		return null;
	}
	
	/**
	 * Загрузка неотсортированного классификатора, в котором код - число.
	 * @param cls - название классификатора
	 */
	@SuppressWarnings("unchecked")
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls) {
		Object res = viewClient.showModal(client, 4, cls);
		
		if (res != null)
			return (List<IntegerClassifier>) res;
		
		return null;
	}
	
	/**
	 * Загрузка отсортированного классификатора, в котором код - строка.
	 * @param cls - название классификатора
	 * @param ord - порядок сортировки
	 * @param fld - поля для сортировки
	 */
	@SuppressWarnings("unchecked")
	public List<StringClassifier> getStringClassifier(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		Object res = viewClient.showModal(client, 5, cls, ord, fld);
		
		if (res != null)
			return (List<StringClassifier>) res;
		
		return null;
	}
	
	/**
	 * Загрузка неотсортированного классификатора, в котором код - строка.
	 * @param cls - название классификатора
	 */
	@SuppressWarnings("unchecked")
	public List<StringClassifier> getStringClassifier(StringClassifiers cls) {
		Object res = viewClient.showModal(client, 6, cls);
		
		if (res != null)
			return (List<StringClassifier>) res;
		
		return null;
	}
	
	/**
	 * Вызов формы с древовидным отображением поликлиник прикрепления.
	 * @param title - заголовок формы
	 * @param kdAte - текущий код территории
	 * @param kdLpu - текущий код ЛПУ
	 * @param kdPol - текущий код поликлиники
	 * @return массив из трех значений: код территории прикрепления, код ЛПУ и
	 * код поликлиники или <code>null</code>, если пользователь закрыл форму
	 */
	public int[] showPolpTreeForm(String title, int kdAte, int kdLpu, int kdPol) {
		Object res = viewClient.showModal(client, 11, title, kdAte, kdLpu, kdPol);
		
		if (res != null)
			return (int[]) res;
		
		return null;
	}
	
	/**
	 * Вызов формы с древовидным отображением мест работы.
	 * @param title - заголовок формы
	 * @param pMrab - текущий код места работы
	 * @return массив из двух значений: код группы и
	 * код места работы или <code>null</code>, если пользователь закрыл форму
	 */
	public IntegerClassifier showMrabTreeForm(String title, int pMrab) {
		Object res = viewClient.showModal(client, 12, title, pMrab);
		
		if (res != null)
			return (IntegerClassifier) res;
		
		return null;
	}
	
	/**
	 * Получение из классификатора имени по его коду.
	 * @param cls - название классификатора
	 * @param pcod - код для поиска имени
	 */
	public String getNameFromPcodInteger(IntegerClassifiers cls, int pcod) {
		Object res = viewClient.showModal(client, 13, cls, pcod);
		
		if (res != null)
			return (String) res;
		
		return null;
	}
	
	/**
	 * Получение из классификатора имени по его коду.
	 * @param cls - название классификатора
	 * @param pcod - код для поиска имени
	 */
	public String getNameFromPcodString(StringClassifiers cls, String pcod) {
		Object res = viewClient.showModal(client, 14, cls, pcod);
		
		if (res != null)
			return (String) res;
		
		return null;
	}
	
	/**
	 * Получение из классификатора кода по его имени.
	 * @param cls - название классификатора
	 * @param name - имя для поиска кода
	 */
	public Integer getPcodFromNameInteger(IntegerClassifiers cls, String name) {
		Object res = viewClient.showModal(client, 15, cls, name);
		
		if (res != null)
			return (Integer) res;
		
		return null;
	}
	
	/**
	 * Получение из классификатора кода по его имени.
	 * @param cls - название классификатора
	 * @param name - имя для поиска кода
	 */
	public String getPcodFromNameString(StringClassifiers cls, String name) {
		Object res = viewClient.showModal(client, 16, cls, name);
		
		if (res != null)
			return (String) res;
		
		return null;
	}
	
	/**
	 * Вызов формы с информацией на пациента.
	 * @param title - заголовок формы
	 * @param npasp - уникальный номер пациента
	 */
	public void showPatientInfoForm(String title, int npasp) {
		viewClient.showModal(client, 17, title, npasp);
	}
	
	/**
	 * Вызов формы записи пациента на исследование.
	 * @param npasp - уникальный номер пациента
	 * @param fam - фамилия
	 * @param im - имя
	 * @param ot - отчество
	 * @param idGosp - идентификатор госпитализации
	 * @param labPattern - шаблон отображения только
	 * определённого вида исследований ("Т" - терапевтических,
	 * "Д" - диагностических, "Л" - лабораторных);
	 * также возможно комбинировать виды исследований, разделяя их
	 * вертикальной чертой "|". Для отображения всех видов исследований,
	 * следует передать символ "%". В случае передачи некорректного шаблона
	 * Будут выведены все виды исследований
	 */
	public void showLabRecordForm(int npasp, String fam, String im, String ot,
			int idGosp, String labPattern) {
		viewClient.showModal(client, 18, npasp, fam, im, ot, idGosp, labPattern);
	}

	/**
     * Вызов формы медицинских назначений
     * @param npasp - уникальный номер пациента
     * @param fam - фамилия
     * @param im - имя
     * @param ot - отчество
     * @param idGosp - идентификатор госпитализации
     */
    public void showMedicationForm(int npasp, String fam, String im, String ot, int idGosp) {
        viewClient.showModal(client, 20, npasp, fam, im, ot, idGosp);
    }

    /**
     * Вызов формы записи пациента на исследование.
     * @param npasp - уникальный номер пациента
     * @param fam - фамилия
     * @param im - имя
     * @param ot - отчество
     * @param idGosp - идентификатор госпитализации
     */
    public void showOperationForm(int npasp, String fam, String im, String ot, int idGosp) {
        viewClient.showModal(client, 25, npasp, fam, im, ot, idGosp);
    }

    /**
     * Вызов формы дневника осмотров стационара
     * @param npasp - уникальный номер пациента
     * @param fam - фамилия
     * @param im - имя
     * @param ot - отчество
     * @param idGosp - идентификатор госпитализации
     * @param is_po - признак приёмного отделения
     */
    public void showDiaryForm(int npasp, String fam, String im, String ot, int idGosp, boolean is_po) {
        viewClient.showModal(client, 28, npasp, fam, im, ot, idGosp, is_po);
    }

	/**
	 * Вызов формы записи пациента на прием к врачу.
	 * @param npasp - уникальный номер пациента.
	 * @param fam - фамилия
	 * @param im - имя
	 * @param ot - отчество
	 * @param idPvizit - идентификатор случая заболевания
	 */
	public void showReceptionRecordForm(int npasp, String fam, String im, String ot, int idPvizit) {
		viewClient.showModal(client, 19, npasp, fam, im, ot, idPvizit);
	}
	
	/**
	 * Вызов формы со списком ошибок в паспортной части пациентов.
	 * @return код выбранного пациента или <code>null</code>, 
	 * если пользователь закрыл форму
	 */
	public Integer showPaspErrorsForm() {
		return (Integer) viewClient.showModal(client, 21);
	}
	
	/**
	 * Вызов формы со списком ошибок в медицинской части поликлиники.
	 * @return код выбранного пациента, код обращения и код посещения 
	 * или <code>null</code>, если пользователь закрыл форму
	 */
	public int[] showMedPolErrorsForm() {
		return (int[]) viewClient.showModal(client, 22);
	}
	
	/**
	 * Вызов формы со списком ошибок в медицинской части приемного отделения.
	 * @return код выбранного посещения 
	 * или <code>null</code>, если пользователь закрыл форму
	 */
	public Integer showMedPriemErrorsForm() {
		return (Integer) viewClient.showModal(client, 27, true);
	}
	
	/**
	 * Вызов формы со списком ошибок в медицинской части стационара.
	 * @return код выбранной госпитализации 
	 * или <code>null</code>, если пользователь закрыл форму
	 */
	public Integer showMedStaErrorsForm() {
		return (Integer) viewClient.showModal(client, 27, false);
	}
	
	/**
	 * Вызов формы с редактором анамнеза пациента
	 * @param npasp - уникальный номер пациента.
	 */
	public void showPatientAnamnezForm(int npasp) {
		viewClient.showModal(client, 24, npasp);
	}
	
	/**
	 * Вызов формы с редактором больничного листа пациента.
	 * @param npasp - уникальный номер пациента.
	 * @param idObr - уникальный номер обращения.
	 * @param idGosp - уникальный номер госпитализации.
	 */
	public void showBolListForm(int npasp, int idObr, int idGosp) {
		viewClient.showModal(client, 26, npasp, idObr, idGosp);
	}
	
	/**
	 * Открывает сгенерированный отчет в офисном редакторе.
	 * @param path - путь к отчету
	 * @param print - печатать ли файл на <b>принтере по умолчанию</b>
     * @deprecated Процедура скоро будет удалена. Вместо нее следует использовать {@link #openFileInTextProcessor(String, boolean)}.
	 */
	public void openFileInEditor(String path, boolean print) {
		throw new RuntimeException("Not implemented anymore. Use openFileInTextProcessor instead.");
	}
	
	/**
	 * Открывает сгенерированный отчет в офисном текстовом процессоре.
	 * @param path - путь к отчету
	 * @param print - печатать ли файл на <b>принтере по умолчанию</b>
	 */
	public void openFileInTextProcessor(String path, boolean print) {
		if (print)
			DocumentPrinter.printViaTextProcessor(path);
		else
			DocumentPrinter.openInTextProcessor(path);
	}
	
	/**
	 * Открывает сгенерированный отчет в офисном табличном процессоре.
	 * @param path - путь к отчету
	 * @param print - печатать ли файл на <b>принтере по умолчанию</b>
	 */
	public void openFileInTableProcessor(String path, boolean print) {
		if (print)
			DocumentPrinter.printViaTableProcessor(path);
		else
			DocumentPrinter.openInTableProcessor(path);
	}
	
	/**
	 * Открывает сгенерированный отчет в средстве просмотра по
	 * умолчанию. Обычно им является браузер.
	 * @param path - путь к отчету
	 */
	public void openFileInDefaultViewer(String path) {
		DocumentPrinter.openDefault(path);
	}
	
	/**
	 * Создает пустой файл для отчета с путем по умолчанию.
	 * @param fileName - имя файла без расширения
	 */
	public String createReport(String fileName) throws IOException {
		return DocumentPrinter.createReportFile(fileName);
	}
	
	/**
	 * Создает пустой файл для реестра с путем по умолчанию.
	 * @param fileName - имя файла без расширения
	 */
	public String createReestr(String fileName) throws IOException {
		return DocumentPrinter.createReestrFile(fileName);
	}
}

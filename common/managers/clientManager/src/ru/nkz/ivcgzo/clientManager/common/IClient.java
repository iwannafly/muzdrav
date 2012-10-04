package ru.nkz.ivcgzo.clientManager.common;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;

/**
 * Определяет главные методы, которые должен реализовывать плагин-клиент.
 * @author bsv798
 */
public interface IClient {
	/**
	 * Установка главной формы клиента. При ее закрытии должно происходить отключение
	 * от всех плагин-серверов.
	 */
	void setFrame(JFrame frame);
	
	/**
	 * Получение формы, установленной методом {@link #setFrame(JFrame)}. 
	 */
	JFrame getFrame();
	
	/**
	 * Показ главной формы. Подключение к плагин-серверам должно происходить после
	 * вызова этого метода.
	 */
	void showNormal();
	
	/**
	 * Показ главной формы в модальном режиме. Подключение к плагин-серверам и
	 * отключение от них должно происходить внутри метода.
	 * @param parent - родительский плагин-клиент
	 * @param params - параметры, передаваемые в модальную форму
	 * @return
	 * результаты вызова формы
	 */
	Object showModal(IClient parent, Object... params);
	
	/**
	 * Метод должен вызываться при [пере]подключении к плагин-серверам.
	 */
	void onConnect(KmiacServer.Client conn);
	
	/**
	 * Возвращает уникальный идентификатор плагин-клиента.
	 */
	int getId();
	
	/**
	 * Возвращает уникальный номер порта, к которому подключен плагин-клиент.
	 */
	int getPort();
	
	/**
	 * Возвращает имя плагин-клиента.
	 */
	String getName();
}

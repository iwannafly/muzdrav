package ru.nkz.ivcgzo.serverManager.common;

/**
 * Определяет главные методы, которые должен реализовывать плагин-сервер.
 * @author bsv798
 */
public interface IServer {
	/**
	 * Вызывается при запуске/инициализации плагина-сервера.
	 * @throws Exception
	 */
	void start() throws Exception;
	
	/**
	 * Вызывается при остановке/выгрузке плагина-сервера.
	 */
	void stop();
	
}

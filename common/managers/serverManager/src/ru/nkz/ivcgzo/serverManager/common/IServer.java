package ru.nkz.ivcgzo.serverManager.common;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

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

	String dataSelection(long dbegin, long dend, int porc, String cform,
			int cpodr, long dclose) throws KmiacServerException, TException;
	
}

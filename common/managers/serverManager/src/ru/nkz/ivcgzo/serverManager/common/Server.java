package ru.nkz.ivcgzo.serverManager.common;

/**
 * Главный класс плагина-сервера должен наследовать от этого класса.
 * @author bsv798
 */
public abstract class Server implements IServer {
	protected ISqlSelectExecutor sse;
	protected ITransactedSqlExecutor tse;
	
	public Server(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
	}
	
	/**
	 * Выполняет указанный метод плагина-сервера.
	 * @param id - идентификатор метода
	 * @param params - параметры для передачи в метод
	 */
	public Object executeServerMethod(int id, Object... params) throws Exception {
		throw new Exception("Not implemented. You must override this method in child class.");
	}
	
}

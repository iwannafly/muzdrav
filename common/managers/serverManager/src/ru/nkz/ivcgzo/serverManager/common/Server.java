package ru.nkz.ivcgzo.serverManager.common;

/**
 * Главный класс плагина-сервера должен наследовать от этого класса.
 * @author bsv798
 */
public abstract class Server implements IServer {
	public ISqlSelectExecutor sse;
	public ITransactedSqlExecutor tse;

	public Server(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
	}
	
	@Override
	public abstract void start() throws Exception;

	@Override
	public abstract void stop();
	
}

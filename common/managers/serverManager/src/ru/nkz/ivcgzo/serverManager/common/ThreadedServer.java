package ru.nkz.ivcgzo.serverManager.common;

/**
 * Класс для запуска плагинов-серверов отдельными потоками.
 * Если в плагине-сервере возникнет необработанное исключение,
 * он остановится.
 * @author bsv798
 */
public class ThreadedServer implements Runnable {
	private Thread thread;
	private IServer server;
	private boolean isRunning;
	
	public ThreadedServer(IServer server) {
		this.server = server;
		CreateThread();
	}
	
	/**
	 * Функция получения состояния сервера
	 * @return Возвращает <code>true</code>, если сервер запущен;
	 * иначе - <code>false</code>
     * @author Балабаев Никита Дмитриевич
	 */
	public boolean isRunning() {
		return (this.isRunning);
	}
	
	@Override
	public void run() {
		try {
			isRunning = true;
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	public void start() throws Exception {
		if (!isRunning)
			thread.start();
		else
			throw new Exception("Server is already running.");
	}

	public void stop() {
		synchronized (thread) {
			server.stop();
			CreateThread();
		}
	}
	
	private void CreateThread() {
		thread = new Thread(this);
		isRunning = false;
	}
	
	public IServer getServer() {
		return server;
	}
}

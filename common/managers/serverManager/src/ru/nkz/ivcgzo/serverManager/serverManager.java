//http://ru.wikipedia.org/wiki/OSGi
//http://voituk.kiev.ua/2008/01/14/java-plugins/

package ru.nkz.ivcgzo.serverManager;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import ru.nkz.ivcgzo.adminManager.AdminController;
import ru.nkz.ivcgzo.serverManager.common.IServer;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.ThreadedServer;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;

public class serverManager extends AdminController {
	public static serverManager instance;
	public enum DatabaseDriver {
		firebird,
		postgre,
		mysql,
		oracle
	}
	public static final int ERR_LOAD_DRIVER = 1;
	public static final int ERR_CONN_TO_DB = 2;
	public static final int ERR_LOAD_SERVER = 3;
	public static DatabaseDriver driver = DatabaseDriver.postgre;
	private static final String pluginsDirectory = "plugin";
	
	private static Map<String, ThreadedServer> pluginsStr;
	private static Map<Integer, ThreadedServer> pluginsId;
	private static ISqlSelectExecutor sse;
	private static ITransactedSqlExecutor tse;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception  {
		serverManager servMan = new serverManager();
		
		try {
			servMan.ConnectToDatabase(DatabaseDriver.postgre, "10.0.0.242", "5432", "zabol", null, 5, "postgres", "postgres");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		} 
		try {
			servMan.loadPlugins();
			servMan.startServers();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			synchronized (servMan) {
				servMan.listenToAdminManager();
			}
		}

	}
	
	public serverManager() {
		instance = this;
	}
	
	public void ConnectToDatabase(DatabaseDriver type, String host, String port, String name, String params, int count, String user, String pass) throws Exception {
		String conn;
		
		driver = type;
		switch (type) {
		case firebird:
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			conn = String.format("jdbc:firebirdsql://%s:%s/%s", host, port, name);
			break;
		case postgre:
			Class.forName("org.postgresql.Driver");
			conn = String.format("jdbc:postgresql://%s:%s/%s", host, port, name);
			break;
		case mysql:
			Class.forName("com.mysql.jdbc.Driver");
			conn = String.format("jdbc:mysql://%s:%s/%s", host, port, name);
			break;
		default:
			throw new Exception("Unknown database driver");
		}
		if (params != null)
			if (params.length() > 0)
				conn += '?' + params;
		
		sse = new SqlSelectExecutor(conn, user, pass);
		if (count < 1)
			tse = new SqlModifyExecutor(conn, user, pass);
		else
			tse = new TransactedSqlManager(conn, user, pass, count);
	}
	
	/**
	 * Загружает все плагины из директории <code>pluginsDirectory</code>.
	 */
	public void loadPlugins(){
		pluginsStr = new HashMap<>();
		pluginsId = new HashMap<>();
		String corePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();
		File[] files = new File(corePath, pluginsDirectory).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".jar");
			}
		});
		
		for (File file : files) {
			try {
				loadPlugin(file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Загружает отдельно взятый плагин из директории <code>pluginsDirectory</code>.
	 * Валидность плагина определяется наличием в нем файла
	 * <code>propertiesFileName</code>, а в нем - свойства <code>propertyClassName</code>.
	 */
	private void loadPlugin(String fileName) throws Exception {
		File plugDir = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath(), pluginsDirectory);
		File file = new File(plugDir, fileName);
		try (JarFile jar = new JarFile(file);
				URLClassLoader confLoader = new URLClassLoader(new URL[] {file.toURI().toURL()}, null);
			) {
			Class<?> confClass = confLoader.loadClass("ru.nkz.ivcgzo.configuration");
			loadPluginClass(file, (String) confClass.getDeclaredField("serverClassName").get(null));
		}
	}
	
	/**
	 * Загружает основной класс из плагина.
	 */
	private void loadPluginClass(File file, String clName) throws Exception {
		try {
			String fileName = file.getName();
			@SuppressWarnings("resource") //class loader must not be closed
			URLClassLoader clLdr = new URLClassLoader(new URL[] {file.toURI().toURL()});
			Class<?> plug = clLdr.loadClass(clName);
			Constructor<?> cntr = plug.getConstructor(ISqlSelectExecutor.class, ITransactedSqlExecutor.class);
			IServer isrv = (IServer) cntr.newInstance(sse, tse);
			ThreadedServer tsrv = new ThreadedServer(isrv);
			pluginsStr.put(fileName.substring(0, fileName.length() - 4), tsrv);
			pluginsId.put(isrv.getId(), tsrv);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Server getServerById(int id) {
		return (Server) pluginsId.get(id).getServer();
	}
	
	@Override
	public void startServers() throws Exception {
		try {
			if (pluginsStr.isEmpty())
				loadPlugins();
			doServerAction(this.getClass().getMethod("startServer" , String.class));
		} catch (Exception e) {
			throw new Exception(String.format("Error starting servers: %s", e.getMessage()), e);
		}
	}
	
	@Override
	public void startServer(String name) throws Exception {
		ThreadedServer plug;
		
		try {
			plug = pluginsStr.get(name);
			if (plug != null)
				plug.start();
			else {
				loadPlugin(name.concat(".jar"));
				pluginsStr.get(name).start();
			}
		} catch (Exception e) {
			throw new Exception(String.format("Error starting server '%s': %s", name, e.getMessage()), e);
		}
	}
	
	@Override
	public void pauseServers() throws Exception {
		try {
			doServerAction(this.getClass().getMethod("pauseServer" , String.class));
		} catch (Exception e) {
			throw new Exception(String.format("Error pausing servers: %s", e.getMessage()), e);
		}
	}
	
	@Override
	public void pauseServer(String name) throws Exception {
		ThreadedServer plug;
		
		try {
			plug = pluginsStr.get(name);
			if (plug != null)
				plug.stop();
			else
				throw new Exception("Unknown server");
		} catch (Exception e) {
			throw new Exception(String.format("Error pausing server '%s': %s", name, e.getMessage()), e);
		}
	}

	@Override
	public void stopServers() throws Exception {
		try {
			doServerAction(this.getClass().getMethod("stopServer" , String.class));
		} catch (Exception e) {
			throw new Exception(String.format("Error stopping servers: %s", e.getMessage()), e);
		}
	}
	
	@Override
	public void stopServer(String name) throws Exception {
		try {
			pauseServer(name);
			pluginsStr.remove(name);
		} catch (Exception e) {
			throw new Exception(String.format("Error stopping server '%s': %s", name, e.getMessage()), e);
		}
	}
	
	private void doServerAction(Method act) throws Exception {
		String[] servNames = pluginsStr.keySet().toArray(new String[] {});
		try {
			for (String name : servNames) {
				act.invoke(this, name);
			}
		} catch (Exception e) {
			throw new Exception(e.getCause());
		}
	}

	@Override
	public String getManagerName() {
		return "server";
	}

	@Override
	public List<String> getServerList() throws Exception {
		return new ArrayList<>(pluginsStr.keySet());
	}
	
}

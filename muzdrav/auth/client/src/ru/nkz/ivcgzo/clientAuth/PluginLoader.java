package ru.nkz.ivcgzo.clientAuth;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;

/**
 * Класс для загрузки модулей системы.
 * @author bsv798
 */
public class PluginLoader {
	ConnectionManager conMan;
	String pdost;
	List<Plugin> plst;
	
	/**
	 * Конструктор класса.
	 * @param conMan - экземпляр менеджера подключений
	 * @param pdost - строка с правами доступа, на основе которой будет строиться
	 * список доступных данному пользователю модулей
	 */
	public PluginLoader(ConnectionManager conMan, String pdost) throws FileNotFoundException {
		this.conMan = conMan;
		this.pdost = pdost;
		
		loadPluginList();
	}
	
	/**
	 * Построение списка доступных пользователю модулей.
	 */
	private void loadPluginList() throws FileNotFoundException {
		plst = new ArrayList<>();
		
		String exePath = new File(MainForm.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();
		File plgDir = new File(exePath, "plugin");
		if (!plgDir.exists())
			throw new FileNotFoundException("Не найдена папка с модулями. Обратитесь в отдел по работе с клиентами КМИАЦ.");
		File[] files = plgDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".jar");
			}
		});
		
		for (File file : files) {
			try {
				Plugin plg = new Plugin(file.getAbsolutePath(), pdost);
				if (plg.getLaunchParam() != 0)
					plst.add(plg);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/**
	 * Получение списка доступных пользователю модулей.
	 */
	public List<Plugin> getPluginList() {
		return plst;
	}
	
	/**
	 * Загрузка модуля.
	 * @param index - индекс модуля в списке
	 */
	public IClient loadPlugin(int index) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return plst.get(index).load();
	}
	
	
	class Plugin {
		private String path;
		private String className;
		private String name;
		private int id;
		private int launchParam;
		
		protected Plugin(String path, String pdost) throws Exception {
			this.path = path;
			
			loadParams();
			
			if (pdost != null)
				if (id < pdost.length())
					launchParam = Integer.parseInt(String.valueOf(pdost.charAt(id)));
		}
		
		private void loadParams() throws Exception {
			try (JarFile jar = new JarFile(path)) {
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if (entry.getName().equalsIgnoreCase(".prop")) {
						try (InputStream stream = jar.getInputStream(entry)) {
							Properties prop = new Properties();
							prop.load(stream);
							if (!prop.containsKey("className") || !prop.containsKey("appName") || !prop.containsKey("appId"))
								throw new Exception("The '.prop' file doesn't contain needed properties");
							className = prop.getProperty("className");
							name = new String(prop.getProperty("appName").getBytes("ISO-8859-1"), "utf-8");
							id = Integer.parseInt(prop.getProperty("appId"));
							break;
						}
					}
				}
			}
		}
		
		protected IClient load() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			URL fileUrl = new File(path).toURI().toURL();
			URLClassLoader clLdr = new URLClassLoader(new URL[] {fileUrl});
			Class<?> plug = clLdr.loadClass(className);
			Constructor<?> cntr = plug.getConstructor(ConnectionManager.class, int.class);
			return (IClient) cntr.newInstance(conMan, launchParam);
		}
		
		public String getName() {
			return name;
		}
		
		public int getId() {
			return id;
		}
		
		protected int getLaunchParam() {
			return launchParam;
		}
	}
}

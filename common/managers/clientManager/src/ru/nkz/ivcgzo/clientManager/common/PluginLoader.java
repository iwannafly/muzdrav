package ru.nkz.ivcgzo.clientManager.common;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarFile;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

/**
 * Класс для загрузки модулей системы.
 * @author bsv798
 */
public class PluginLoader {
	private ConnectionManager conMan;
	private UserAuthInfo authInfo;
	private List<Plugin> pList;
	private PluginComparator pComp;
	
	/**
	 * Конструктор класса.
	 * @param conMan - экземпляр менеджера подключений
	 * @param pdost - строка с правами доступа, на основе которой будет строиться
	 * список доступных данному пользователю модулей
	 */
	protected PluginLoader(ConnectionManager conMan, UserAuthInfo authInfo) {
		this.conMan = conMan;
		this.authInfo = authInfo;
		
		pList = new ArrayList<>();
		pComp = new PluginComparator();
	}
	
	/**
	 * Построение списка доступных пользователю модулей.
	 */
	public void loadPluginList(String path) throws FileNotFoundException {
		String exePath = new File(path).getParentFile().getAbsolutePath();
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
				Plugin plg = new Plugin(file.getAbsolutePath());
				if (plg.getLaunchParam() != 0)
					pList.add(plg);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		Collections.sort(pList, pComp);
	}
	
	/**
	 * Получение списка доступных пользователю модулей.
	 */
	public List<Plugin> getPluginList() {
		return pList;
	}
	
	public boolean hasPlugin(int id) {
		for (Plugin plugin : pList) {
			if (plugin.getId() == id)
				return true;
		}
		return false;
	}
	
	/**
	 * Загрузка модуля по индексу.
	 * @param index - индекс модуля в списке
	 */
	public IClient loadPluginByIndex(int index) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		return pList.get(index).load();
	}
	
	/**
	 * Загрузка модуля по уникальному идентификатору.
	 * @param appId - идентификатор модуля
	 */
	public IClient loadPluginByAppId(int appId) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		for (Plugin plugin : pList) {
			if (plugin.getId() == appId)
				return plugin.load();
		}
		
		throw new IllegalArgumentException(String.format("Module with id %d not found.", appId));
	}
	
	public class Plugin {
		private String path;
		private String className;
		private String name;
		private int id;
		private int launchParam;
		
		protected Plugin(String path) throws Exception {
			this.path = path;
			
			loadParams();
			
			if (authInfo.pdost != null)
				if (id < authInfo.pdost.length())
					launchParam = Integer.parseInt(String.valueOf(authInfo.pdost.charAt(id)));
		}
		
		private void loadParams() throws Exception {
			File file = new File(path);
			try (JarFile jar = new JarFile(file);
					URLClassLoader confLoader = new URLClassLoader(new URL[] {file.toURI().toURL()}, null);) {
				Class<?> confClass = confLoader.loadClass("ru.nkz.ivcgzo.configuration");
				className = (String) confClass.getDeclaredField("clientClassName").get(null);
				name = (String) confClass.getDeclaredField("appName").get(null);
				id = confClass.getDeclaredField("appId").getInt(null);
			}
		}
		
		protected IClient load() throws MalformedURLException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			@SuppressWarnings("resource") //class loader must not be closed
			URLClassLoader clLdr = new URLClassLoader(new URL[] {new File(path).toURI().toURL()});
			Class<?> plug = clLdr.loadClass(className);
			Constructor<?> cntr = plug.getConstructor(ConnectionManager.class, UserAuthInfo.class, int.class);
			return (IClient) cntr.newInstance(conMan, authInfo, launchParam);
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
	
	class PluginComparator implements Comparator<Plugin> {

		@Override
		public int compare(Plugin arg0, Plugin arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
		
	}
}

package ru.nkz.ivcgzo.clientAuth;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
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

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
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
	public PluginLoader(ConnectionManager conMan, UserAuthInfo authInfo) throws FileNotFoundException {
		this.conMan = conMan;
		this.authInfo = authInfo;
		
		pList = new ArrayList<>();
		pComp = new PluginComparator();
	}
	
	/**
	 * Построение списка доступных пользователю модулей.
	 */
	public void loadPluginList() throws FileNotFoundException {
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
	 * Загрузка модуля.
	 * @param index - индекс модуля в списке
	 */
	public IClient loadPlugin(int index) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return pList.get(index).load();
	}
	
	
	class Plugin {
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
					URLClassLoader confLoader = new URLClassLoader(new URL[] {file.toURI().toURL()}, null);
				) {
				Class<?> confClass = confLoader.loadClass("ru.nkz.ivcgzo.configuration");
				className = (String) confClass.getDeclaredField("clientClassName").get(null);
				name = (String) confClass.getDeclaredField("appName").get(null);
				id = confClass.getDeclaredField("appId").getInt(null);
			}
		}
		
		protected IClient load() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			URL fileUrl = new File(path).toURI().toURL();
			URLClassLoader clLdr = new URLClassLoader(new URL[] {fileUrl});
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

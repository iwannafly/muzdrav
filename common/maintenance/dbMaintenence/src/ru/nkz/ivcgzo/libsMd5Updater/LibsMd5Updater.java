package ru.nkz.ivcgzo.libsMd5Updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

import ru.nkz.ivcgzo.dbConnection.DBConnection;

public class LibsMd5Updater {
	private Configuration conf;
	private DBConnection conn;
	
	public LibsMd5Updater(String configFilePath) throws Exception {
		conf = new Configuration(configFilePath);
		conn = new DBConnection(conf.baseParams);
		conn.connect();
	}
	
	public void updateLibsMd5() {
		List<Library> localList = getLocalLibsList();
		List<Library> remoteList = getRemoteLibsList();
		List<Library> updList = getUpdateList(localList, remoteList);
		
		try {
			if (updList.size() > 0) {
				update(updList, getMinLibIdx(remoteList));
				System.out.println("All libs are sucsessfully updated.");
			} else
				System.out.println("All libs are up to date.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Library> getLocalLibsList() {
		List<Library> list = new ArrayList<>();
		List<File> jarList = new ArrayList<>();
		
		jarList.addAll(getJarList(conf.libsParams.libPath));
		jarList.addAll(getJarList(conf.libsParams.pluginPath));
		jarList.add(new File(new File(conf.libsParams.pluginPath).getAbsoluteFile().getParentFile(), "auth.jar"));
		
		for (File jar : jarList)
			list.add(new Library(jar));
		
		return list;
	}
	
	private List<File> getJarList(String path) {
		File dir = new File(path);
		File[] arr = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("jar");
			}
		});
		
		if (arr != null)
			return Arrays.asList(arr);
		else
			return new ArrayList<File>();
	}
	
	private List<Library> getRemoteLibsList() {
		List<Library> list = new ArrayList<>();
		
		try (Statement stm = conn.createStatement()) {
			ResultSet rs = conn.executeQuery(stm, "SELECT id, name, md5, size FROM s_libs ");
			
			while (rs.next())
				if (rs.getString(2) != null)
					list.add(new Library(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private List<Library> getUpdateList(List<Library> localList, List<Library> remoteList) {
		List<Library> list = new ArrayList<>();
		
		loop: for (Library local : localList) {
			for (Library remote : remoteList) {
				if (remote.name.equals(local.name)) {
					if (!remote.md5.equals(local.md5) || remote.size != local.size)
						list.add(local);
					local.id = remote.id;
					continue loop;
				}
			}
			
			local.isNew = true;
			list.add(local);
		}
		
		return list;
	}
	
	private void update(List<Library> updList, int minLibIdx) throws Exception {
		for (Library upd : updList) {
			if (upd.isNew) {
				try (PreparedStatement stm = conn.createPreparedStatement("INSERT INTO s_libs VALUES (?, ?, ?, ?) ")) {
					if (upd.id < 0)
						stm.setInt(1, --minLibIdx);
					else
						stm.setInt(1, upd.id);
					stm.setString(2, upd.name);
					stm.setString(3, upd.md5);
					stm.setInt(4, upd.size);
					stm.execute();
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
					throw new Exception(String.format("Error inserting library %s.", upd.name), e);
				}
			} else {
				try (PreparedStatement stm = conn.createPreparedStatement("UPDATE s_libs SET md5 = ?, size = ? WHERE id = ?")) {
					stm.setString(1, upd.md5);
					stm.setInt(2, upd.size);
					stm.setInt(3, upd.id);
					stm.execute();
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
					throw new Exception(String.format("Error updating library %s.", upd.name), e);
				}
			}
		}
	}
	
	private int getMinLibIdx(List<Library> remoteList) {
		int min = 0;
		
		for (Library remote : remoteList) {
			if (remote.id < min)
				min = remote.id;
		}
		
		return min;
	}
	
	class Library {
		public File file;
		public int id;
		public String name;
		public String md5;
		public int size;
		public boolean isNew;
		
		public Library(File jarFile) {
			file = jarFile;
			
			id = getId();
			name = file.getName();
			md5 = getMd5();
			size = (int) file.length();
			isNew = false;
		}
		
		private int getId() {
			try (JarFile jar = new JarFile(file);
					URLClassLoader confLoader = new URLClassLoader(new URL[] {file.toURI().toURL()}, null);) {
				Class<?> confClass = confLoader.loadClass("ru.nkz.ivcgzo.configuration");
				return confClass.getDeclaredField("appId").getInt(null);
			} catch (ClassNotFoundException e) {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return -1;
		}
		
		private String getMd5() {
			String md5 = "";
				
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buf = new byte[(int) fis.getChannel().size()];
				fis.read(buf);
				byte[] md5Bin = MessageDigest.getInstance("md5").digest(buf);
				
				for (int i = 0; i < md5Bin.length; i++)
					md5 += String.format("%02x", md5Bin[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return md5;
		}
		
		public Library(int id, String name, String md5, int size) {
			this.id = id;
			this.name = name;
			this.md5 = md5;
			this.size = size;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}

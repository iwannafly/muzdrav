package ru.nkz.ivcgzo.clientAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.libraryUpdater.LibraryInfo;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;

public class ModulesUpdater {
	private static final String libDir = "plugin";
	private ThriftServerAuth.Client client;
	
	public ModulesUpdater(ConnectionManager conMan) {
		client = (ThriftServerAuth.Client) conMan.get(configuration.thrPort);
	}
	
	public void checkAndUpdate(String pdost) throws Exception {
		List<LibraryInfo> servModList = client.getModulesList();
		List<Integer> availModList = getModuleIdList(pdost);
		List<LibraryInfo> updList = getUpdateList(servModList, availModList);
		if (updList.size() > 0) {
			updateLibs(updList);
			checkUpdate(servModList, availModList);
		}
	}
	
	private List<Integer> getModuleIdList(String pdost) {
		List<Integer> availModList = new ArrayList<>();
		
		for (int i = 1; i < pdost.length(); i++)
			if (Integer.parseInt(pdost.substring(i, i + 1)) > 0)
			availModList.add(i);
		
		return availModList;
	}
	
	private List<LibraryInfo> getUpdateList(List<LibraryInfo> servModList, List<Integer> availModList) {
		String path = checkAndCreateLibFolder();
		List<LibraryInfo> updList = new ArrayList<>();
		for (Integer id : availModList) {
			try {
				LibraryInfo module = getModule(servModList, id);
				if (!checkLibExistenceAndMd5(path, module))
					updList.add(module);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return updList;
	}
	
	private String checkAndCreateLibFolder() {
		File libFolder = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile(), libDir);
		if (!libFolder.exists())
			libFolder.mkdir();
		return libFolder.getAbsolutePath();
	}
	
	private LibraryInfo getModule(List<LibraryInfo> servModList, int id) throws Exception {
		for (LibraryInfo libInf : servModList) {
			if (libInf.id == id)
				return libInf;
		}
		throw new Exception(String.format("Module %d not found in server's list", id));
	}
	
	private boolean checkLibExistenceAndMd5(String path, LibraryInfo libInfo) {
		File libFile = new File(path, libInfo.name);
		
		if (!libFile.exists())
			return false;
		
		if (libInfo.size != libFile.length())
			return false;
		
		try (FileInputStream fis = new FileInputStream(libFile)) {
			byte[] buf = new byte[(int) fis.getChannel().size()];
			fis.read(buf);
			return compareMd5(MessageDigest.getInstance("md5").digest(buf), libInfo.md5);
		} catch (NoSuchAlgorithmException | IOException e) {
			return false;
		}
	}
	
	private boolean compareMd5(byte[] bin, String str) {
		try {
			for (int i = 0; i < bin.length; i++) {
				if (bin[i] != (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16))
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private void updateLibs(List<LibraryInfo> updList) throws Exception {
		String path = checkAndCreateLibFolder();
		byte[] buf = new byte[65536];
		for (LibraryInfo libInfo : updList) {
			try (Socket servSct = new Socket(MainForm.appServerIp, client.openModuleReadSocket(libInfo.id))) {
				try (FileOutputStream fos = new FileOutputStream(new File(path, libInfo.name))) {
					fos.getChannel().lock();
					int read = servSct.getInputStream().read(buf);
					int readFile = read;
					while (readFile < libInfo.size) {
						fos.write(buf, 0, read);
						read = servSct.getInputStream().read(buf);
						readFile += read;
					}
					fos.write(buf, 0, read);
					client.closeReadSocket(servSct.getPort());
				} catch (Exception e) {
					throw new Exception(String.format("Transferring '%s' failed.", libInfo.name), e);
				}
			} catch (Exception e) {
				throw new Exception("Error transferring files from server.", e);
			}
		}
	}
	
	private void checkUpdate(List<LibraryInfo> servModList, List<Integer> availModList) throws Exception {
		if (getUpdateList(servModList, availModList).size() > 0)
			throw new Exception("Transferred files check failed.");
	}
}

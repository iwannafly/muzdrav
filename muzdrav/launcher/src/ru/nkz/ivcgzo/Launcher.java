package ru.nkz.ivcgzo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Launcher {
	private static final String libDir = "launcher_lib";
	private class LibraryInfo {
		int id;
		String name;
		String md5;
		int size;
	}
	
	public static void main(String[] args) {
		Launcher lnc = new Launcher();
		lnc.checkAndUpdate();
		try {
			Runtime.getRuntime().exec("java -jar auth.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkAndUpdate() {
		try (Socket servSct = new Socket("localhost", 55201)) {
			handShake(servSct);
			Document domLibList = getLibrariesList(servSct);
			List<LibraryInfo> updList = getUpdateList(domLibList);
			updateLibs(servSct, updList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handShake(Socket servSct) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(servSct.getInputStream()));
			PrintWriter writer = new PrintWriter(servSct.getOutputStream());
			String str = reader.readLine();
			if (str.equals("Кто это?")) {
				writer.println("Клиент муздрава.");
				writer.flush();
				if (reader.readLine().equals("Приветствую."))
					return;
			}
			throw new Exception("Talking to stranger.");
		} catch (Exception e) {
			throw new Exception("Handshake failed.", e);
		}
	}
	
	private Document getLibrariesList(Socket servSct) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(servSct.getInputStream()));
			StringReader sr = new StringReader(reader.readLine());
			StreamSource src = new StreamSource(sr);
			DOMResult res = new DOMResult();
			TransformerFactory.newInstance().newTransformer().transform(src, res);
			return  (Document) res.getNode();
		} catch (Exception e) {
			throw new Exception("Failed getting libraries list.", e);
		}
	}
	
	private List<LibraryInfo> getUpdateList(Document domLibList) {
		String path = checkAndCreateLibFolder();
		List<LibraryInfo> updList = new ArrayList<>();
		NodeList libList = domLibList.getElementsByTagName("libInfo");
		for (int i = 0; i < libList.getLength(); i++) {
			Element libElement = (Element) libList.item(i);
			LibraryInfo libInf = new LibraryInfo();
			libInf.id = Integer.parseInt(libElement.getAttribute("id"));
			libInf.name = getTagValue(libElement, "name");
			libInf.md5 = getTagValue(libElement, "md5");
			libInf.size = Integer.parseInt(getTagValue(libElement, "size"));
			if (!checkLibExistenceAndMd5(path, libInf))
				updList.add(libInf);
		}
		return updList;
	}
	
	private String checkAndCreateLibFolder() {
		File libFolder = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile(), libDir);
		if (!libFolder.exists())
			libFolder.mkdir();
		return libFolder.getAbsolutePath();
	}
	
	private String getTagValue(Element parentTag, String valueName) {
		return parentTag.getElementsByTagName(valueName).item(0).getChildNodes().item(0).getNodeValue();
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
	
	private void updateLibs(Socket servSct, List<LibraryInfo> updList) throws Exception {
		String path = checkAndCreateLibFolder();
		byte[] buf = new byte[65536];
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(servSct.getInputStream()));
				PrintWriter writer = new PrintWriter(servSct.getOutputStream())) {
			for (LibraryInfo libInfo : updList) {
				writer.println(libInfo.name);
				writer.flush();
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
					writer.println("Получил.");
					writer.flush();
				} catch (Exception e) {
					throw new Exception(String.format("Transferring '%s' failed.", libInfo.name), e);
				}
			}
			writer.println("");
			writer.flush();
			if (!reader.readLine().equals("Всего хорошего."))
				throw new Exception("Farewell failed.");
		} catch (Exception e) {
			throw new Exception("Error transferring files from server.", e);
		}
	}
}

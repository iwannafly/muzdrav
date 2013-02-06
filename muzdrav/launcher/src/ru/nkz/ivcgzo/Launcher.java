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
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Launcher {
	private static String clientAuthType = "unspecified";
	private static String appServerIp = "localhost";
	private static boolean hasLoginParams;
	private static String paramLogin;
	private static String paramPassword;
	private static int paramModuleIndex;
	private static final String libDir = "lib";
	private final String rootPath;
	private class LibraryInfo {
		int id;
		String name;
		String md5;
		int size;
	}
	
	public static void main(String[] args) {
		Pattern ipPattern = Pattern.compile("(^[2][5][0-5].|^[2][0-4][0-9].|^[1][0-9][0-9].|^[0-9][0-9].|^[0-9].)([2][0-5][0-5].|[2][0-4][0-9].|[1][0-9][0-9].|[0-9][0-9].|[0-9].)([2][0-5][0-5].|[2][0-4][0-9].|[1][0-9][0-9].|[0-9][0-9].|[0-9].)([2][0-5][0-5]|[2][0-4][0-9]|[1][0-9][0-9]|[0-9][0-9]|[0-9])$");
		int prmStart;
		
		if (clientAuthType.equals("unspecified")) {
			prmStart = 1;
			if (args.length == 0) {
				System.out.println("No application server alias (tst, int, ext) specified. Using dev server.");
				appServerIp = "localhost";
			} else {
				if (args[0].equals("tst")) {
					System.out.println("Using test application server.");
					clientAuthType = "unspecified";
					appServerIp = "10.0.0.248";
				} else if (args[0].equals("int")) {
					System.out.println("Using internal application server.");
					clientAuthType = "unspecified";
					appServerIp = "10.0.0.243";
				} else if (args[0].equals("ext")) {
					System.out.println("Using external application server.");
					clientAuthType = "unspecified";
					appServerIp = "10.1.1.8";
				} else if (ipPattern.matcher(args[0]).matches()) {
					appServerIp = args[0];
					clientAuthType = appServerIp;
					System.out.println(String.format("Using %s application server.", appServerIp));
				} else {
					System.out.println("No valid application server alias (tst, int, ext) or ip address specified. Using dev server.");
					appServerIp = "localhost";
				}
			}
		} else {
			prmStart = 0;
		}
		if (args.length > prmStart)
			if (args.length > prmStart + 2) {
				paramLogin = args[prmStart];
				paramPassword = args[prmStart + 1];
				try {
					paramModuleIndex = Integer.parseInt(args[prmStart + 2]);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Неверно задан индекс запускаемого модуля. Программа будет закрыта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
					System.exit(2);
				}
				hasLoginParams = true;
			} else {
				JOptionPane.showMessageDialog(null, "Недостаточное количество параметров для задания логина, пароля и индекса запускаемого модуля. Программа будет закрыта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				System.exit(2);
			}

		
		Launcher lnc;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			lnc = new Launcher();
			lnc.checkAndUpdate();
			
			Runtime.getRuntime().exec("java -jar auth.jar " + clientAuthType + (hasLoginParams ? String.format(" \"%s\" \"%s\" \"%d\"", paramLogin, paramPassword, paramModuleIndex) : ""));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не удалось обновить системные модули. Программа будет закрыта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	public Launcher() {
		rootPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();
	}
	
	public void checkAndUpdate() throws Exception {
		switch (clientAuthType) {
		case "tst":
			appServerIp = "10.0.0.248";
			break;
		case "int":
			appServerIp = "10.0.0.243";
			break;
		case "ext":
			appServerIp = "10.1.1.8";
			break;
		}
//		checkForJavaFx();
		try (Socket servSct = new Socket(appServerIp, 55201)) {
			handShake(servSct);
			Document domLibList = getLibrariesList(servSct);
			List<LibraryInfo> updList = getUpdateList(domLibList);
			updateLibs(servSct, updList);
		}
	}
	
//	private void checkForJavaFx() throws Exception {
//		String javaHome;
//		Path jfxLibFile, jfxMuzFile;
//		
//		javaHome = System.getProperty("java.home");
//		if (javaHome == null) {
//			JOptionPane.showMessageDialog(null, "Исполняемая среда Java установлена некорректно.\nПереустановите ее, скачав последний дистрибутив с официального сайта http://www.oracle.com/technetwork/java/javase/downloads/index.html.", "Ошибка", JOptionPane.ERROR_MESSAGE);
//			throw new Exception("Jre not set up correctly.");
//		}
//		
//		jfxLibFile = Paths.get(javaHome, "lib", "jfxrt.jar");
//		if (!jfxLibFile.toFile().exists()) {
//			JOptionPane.showMessageDialog(null, "Библиотека JavaFX не установлена.\nУстановите ее, скачав последний дистрибутив с официального сайта http://www.oracle.com/technetwork/java/javase/downloads/index.html.", "Ошибка", JOptionPane.ERROR_MESSAGE);
//			throw new Exception("Jfx not set up.");
//		}
//		
//		jfxMuzFile = Paths.get(new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath(), "lib", "jfxrt.jar");
//		if (!jfxMuzFile.toFile().exists()) {
//			Files.copy(jfxLibFile, jfxMuzFile);
//		} else if (jfxLibFile.toFile().length() != jfxMuzFile.toFile().length()) {
//			Files.copy(jfxLibFile, jfxMuzFile, StandardCopyOption.REPLACE_EXISTING);
//		}
//	}

	private void handShake(Socket servSct) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(servSct.getInputStream()));
			PrintWriter writer = new PrintWriter(servSct.getOutputStream());
			String str = reader.readLine();
			if (str.equals("Who's this?")) {
				writer.println("Muzdrav client.");
				writer.flush();
				if (reader.readLine().equals("Ohayo."))
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
		String path = checkAndCreateLibFolder(rootPath);
		List<LibraryInfo> updList = new ArrayList<>();
		NodeList libList = domLibList.getElementsByTagName("libInfo");
		for (int i = 0; i < libList.getLength(); i++) {
			Element libElement = (Element) libList.item(i);
			LibraryInfo libInf = new LibraryInfo();
			libInf.id = Integer.parseInt(libElement.getAttribute("id"));
			libInf.name = getTagValue(libElement, "name");
			libInf.md5 = getTagValue(libElement, "md5");
			libInf.size = Integer.parseInt(getTagValue(libElement, "size"));
			if (!checkLibExistenceAndMd5(libInf.id == 0 ? rootPath : path, libInf))
				updList.add(libInf);
		}
		return updList;
	}
	
	private String checkAndCreateLibFolder(String rootPath) {
		File libFolder = new File(rootPath, libDir);
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
	
	private void updateLibs(final Socket servSct, final List<LibraryInfo> updList) throws Exception {
		int overallSize = 0;
		final ModulesUpdaterDialog mud = new ModulesUpdaterDialog();
		for (LibraryInfo libraryInfo : updList) {
			overallSize += libraryInfo.size;
		}
		mud.setLocationRelativeTo(null);
		mud.setTitle("МИС \"Инфо МуЗдрав\" - Обновление системных модулей");
		mud.setOverallMessage("Всего загружено");
		mud.setOverallMaximum(overallSize);
		
		new Thread(new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {
				String path = checkAndCreateLibFolder(rootPath);
				byte[] buf = new byte[65536];
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(servSct.getInputStream()));
						PrintWriter writer = new PrintWriter(servSct.getOutputStream())) {
					for (LibraryInfo libInfo : updList) {
						writer.println(libInfo.id);
						writer.flush();
						try (FileOutputStream fos = new FileOutputStream(new File(libInfo.id == 0 ? rootPath : path, libInfo.name))) {
							mud.setCurrentMessage(String.format("Загрузка модуля %s", libInfo.name));
							mud.setCurrentMaximum(libInfo.size);
							mud.setCurrentValue(0);
							
							fos.getChannel().lock();
							int read = servSct.getInputStream().read(buf);
							int readFile = read;
							while (readFile < libInfo.size) {
								fos.write(buf, 0, read);
								mud.addToProgress(read);
								read = servSct.getInputStream().read(buf);
								readFile += read;
							}
							fos.write(buf, 0, read);
							mud.addToProgress(read);
							writer.println("Got it.");
							writer.flush();
						} catch (Exception e) {
							throw new Exception(String.format("Transferring '%s' failed.", libInfo.name), e);
						}
					}
					writer.println("");
					writer.flush();
					if (!reader.readLine().equals("Good bye."))
						throw new Exception("Farewell failed.");
				} catch (Exception e) {
					e = new Exception("Error transferring files from server.", e);
					mud.setException(e);
				} finally {
					mud.dispose();
				}
			}
		}).start();
		if (updList.size() > 0)
			mud.setVisible(true);
		if (mud.getException() != null)
			throw mud.getException();
	}
}

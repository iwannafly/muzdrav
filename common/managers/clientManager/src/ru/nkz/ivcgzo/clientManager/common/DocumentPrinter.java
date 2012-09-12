package ru.nkz.ivcgzo.clientManager.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DocumentPrinter {
	private static OparatingSystem os = checkForOS();
	private static String libreOfficePath = checkForLibreOfficePath();
	private static String msOfficePath = checkForMsOfficePath();
	private static String openOfficePath = checkForOpenOfficePath();
	
	public enum OparatingSystem {
		Unknown,
		Windows,
		Linux
	}
	
	private static OparatingSystem checkForOS() {
		String osName = System.getProperty("os.name").toLowerCase().substring(0, 3);
		
		switch (osName) {
		case "win":
			return OparatingSystem.Windows;
		}
		
		return OparatingSystem.Linux;
	}
	
	private static String checkForLibreOfficePath() {
		String path = "";
		
		switch (os) {
		case Windows:
			path = readWindowsRegistryString("HKEY_CLASSES_ROOT\\Software\\OpenOffice.org\\LibreOffice", "Path");
			if (path.length() > 0) {
				path = new File(path, "program\\soffice.exe").getAbsolutePath();
				if (isPathExists(path))
					return path;
			}
			break;
		case Linux:
			path = "/usr/bin/soffice";
			if (isPathExists(path))
				return path;
			break;
		default:
			break;
		}
		
		return path;
	}
	
	private static String checkForMsOfficePath() {
		switch (os) {
		case Windows:
			String path = readWindowsRegistryString("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\Winword.exe", "Path");
			if (path.length() > 0) {
				path = new File(path, "WINWORD.EXE").getAbsolutePath();
				if (isPathExists(path))
					return path;
			}
			break;
		default:
			break;
		}
		
		return "";
	}
	
	private static String checkForOpenOfficePath() {
		return "";
	}
	
	private static String readWindowsRegistryString(String path, String key) {
		InputStreamReader reader = null;
			
		try {
			Process process = Runtime.getRuntime().exec(String.format("reg query \"%s\" /v \"%s\"", path, key));
			reader = new InputStreamReader(process.getInputStream());
			
			process.waitFor();
			char[] buf = new char[1000];
			reader.read(buf);
			reader.close();
			String output = new String(buf);
			
			if(!output.contains("\t"))
				return "";
			String[] parsed = output.split("\t");
			int crlfIdx = parsed[parsed.length - 1].indexOf("\r\n");
		
			return parsed[parsed.length - 1].substring(0, crlfIdx);
		}
		catch (Exception e) {
			return "";
		}
	}
	
	private static boolean isPathExists(String path) {
		return new File(path).exists();
	}
	
	public static void editFile(String path) {
		String execCmd = "";
		
		if (msOfficePath.length() > 0)
			execCmd = String.format("\"%s\" \"%s\"", msOfficePath, path);
		if (libreOfficePath.length() > 0)
			execCmd = String.format("\"%s\" \"%s\"", libreOfficePath, path);
		
		try {
			Runtime.getRuntime().exec(execCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printFile(String path) {
		String execCmd = "";
		
		if (msOfficePath.length() > 0)
			execCmd = String.format("\"%s\" \"%s\" /mFilePrintDefault /mFileExit", msOfficePath, path);
		if (libreOfficePath.length() > 0)
			execCmd = String.format("\"%s\" -p \"%s\"", libreOfficePath, path);
		
		try {
			Runtime.getRuntime().exec(execCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

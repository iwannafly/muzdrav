package ru.nkz.ivcgzo.clientManager.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DocumentPrinter {
	private static OparatingSystem os = checkForOS();
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
	
	private static String checkForOpenOfficePath() {
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
		
		return "";
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
	
	private static String readWindowsRegistryString(String path, String key) {
		final String regSz = "REG_SZ";
		InputStreamReader reader = null;
			
		try {
			Process process = Runtime.getRuntime().exec(String.format("reg query \"%s\" /v \"%s\"", path, key));
			
			if (process.waitFor() == 0) {
				reader = new InputStreamReader(process.getInputStream());
				char[] buf = new char[1000];
				reader.read(buf);
				reader.close();
				String output = new String(buf);
				
				String[] outputArray = output.split(System.lineSeparator());
				String outputLine = outputArray[outputArray.length - 3];
				int crlfIdx = outputLine.indexOf(regSz);
				
				if (crlfIdx > -1)
					return outputLine.substring(crlfIdx + regSz.length(), outputLine.length()).trim();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static boolean isPathExists(String path) {
		return new File(path).exists();
	}
	
	public static void editFile(String path) {
		String execCmd = "";
		
		if (msOfficePath.length() > 0)
			execCmd = String.format("\"%s\" \"%s\"", msOfficePath, path);
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = String.format("\"%s\" \"%s\"", openOfficePath, path);
				break;
			default:
				execCmd = String.format("%s %s", openOfficePath, path.replaceAll(" ", "\\\\ "));
				break;
			}
		
		if (execCmd.length() > 0)
			try {
				Runtime.getRuntime().exec(execCmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			openDefault(path);
	}
	
	public static void printFile(String path) {
		String execCmd = "";
		
		if (msOfficePath.length() > 0)
			execCmd = String.format("\"%s\" \"%s\" /mFilePrintDefault /mFileExit", msOfficePath, path);
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = String.format("\"%s\" -p \"%s\"", openOfficePath, path);
				break;
			default:
				execCmd = String.format("%s -p %s", openOfficePath, path.replaceAll(" ", "\\\\ "));
				break;
			}
		
		if (execCmd.length() > 0)
			try {
				Runtime.getRuntime().exec(execCmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			openDefault(path);
	}
	
	private static void openDefault(String path) {
		String execCmd = "";
		
		switch (os) {
		case Windows:
			execCmd = String.format("cmd /c \"%s\"", path);
			break;
		default:
			execCmd = String.format("xdg-open %s", path.replaceAll(" ", "\\\\ "));
			break;
		}
		
		try {
			Runtime.getRuntime().exec(execCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

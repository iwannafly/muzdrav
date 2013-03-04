package ru.nkz.ivcgzo.clientManager.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientManager.common.customFrame.CustomFrame;

public class DocumentPrinter {
	private static OparatingSystem os = checkForOS();
	private static String msOfficePath = checkForMsOfficePath();
	private static String openOfficePath = checkForOpenOfficePath();
	private static String libreOfficePath = checkForLibreOfficePath();
	private static String rootOutPath = getRootOutputPath();
	
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
					return new File(path).getParentFile().getAbsolutePath();
			}
			break;
		case Linux:
			path = "/usr/bin/soffice";
			if (isPathExists(path))
				return new File(path).getParentFile().getAbsolutePath();
			break;
		default:
			break;
		}
		
		return "";
	}
	
	private static String checkForLibreOfficePath() {
		String path = "";
		
		switch (os) {
		case Windows:
			path = readWindowsRegistryString("HKEY_CLASSES_ROOT\\Software\\LibreOffice\\LibreOffice", "Path");
			if (path.length() > 0) {
				path = new File(path, "program\\soffice.exe").getAbsolutePath();
				if (isPathExists(path))
					return path;
			}
			break;
		case Linux:
			path = "/usr/bin/libreoffice4.0";
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
					return new File(path).getParentFile().getAbsolutePath();
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
	
	public static void openInTextProcessor(String path) {
		String[] execCmd = null;
		
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {new File(openOfficePath, "swriter.exe").getAbsolutePath(), path};
				break;
			default:
				execCmd = new String[] {new File(openOfficePath, "swriter").getAbsolutePath(), path};
				break;
			}
		if (msOfficePath.length() > 0)
			execCmd = new String[] {new File(msOfficePath, "WINWORD.EXE").getAbsolutePath(), path};
		if (libreOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {libreOfficePath, "--writer", path};
				break;
			default:
				execCmd = new String[] {libreOfficePath, "--writer", path};
				break;
			}
			
		
		if (execCmd != null)
			new ExecThread(execCmd, "Невозможно открыть файл в текстовом процессоре.").start();
		else
			openDefault(path);
	}
	
	public static void openInTableProcessor(String path) {
		String[] execCmd = null;
		
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {new File(openOfficePath, "scalc.exe").getAbsolutePath(), path};
				break;
			default:
				execCmd = new String[] {new File(openOfficePath, "scalc").getAbsolutePath(), path};
				break;
			}
		if (msOfficePath.length() > 0)
			execCmd = new String[] {new File(msOfficePath, "EXCEL.EXE").getAbsolutePath(), path};
		if (libreOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {libreOfficePath, "--calc", path};
				break;
			default:
				execCmd = new String[] {libreOfficePath, "--calc", path};
				break;
			}
		
		if (execCmd != null)
			new ExecThread(execCmd, "Невозможно открыть файл в табличном процессоре.").start();
		else
			openDefault(path);
	}

	public static void printViaTextProcessor(String path) {
		String[] execCmd = null;
		
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {new File(openOfficePath, "swriter.exe").getAbsolutePath(), "-p", path};
				break;
			default:
				execCmd = new String[] {new File(openOfficePath, "swriter").getAbsolutePath(), "-p", path};
				break;
			}
		if (msOfficePath.length() > 0)
			execCmd = new String[] {new File(msOfficePath, "WINWORD.EXE").getAbsolutePath(), path, "/mFilePrintDefault", "/mFileExit"};
		if (libreOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {libreOfficePath, "--writer", "-p", path};
				break;
			default:
				execCmd = new String[] {libreOfficePath, "--writer", "-p", path};
				break;
			}
		
		if (execCmd != null)
			new ExecThread(execCmd, "Невозможно напечатать файл через текстовый процессор.").start();
		else
			openDefault(path);
	}
	
	public static void printViaTableProcessor(String path) {
		String[] execCmd = null;
		
		if (openOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {new File(openOfficePath, "scalc.exe").getAbsolutePath(), "-p", path};
				break;
			default:
				execCmd = new String[] {new File(openOfficePath, "scalc").getAbsolutePath(), "-p", path};
				break;
			}
		if (msOfficePath.length() > 0)
			execCmd = new String[] {new File(msOfficePath, "EXCEL.EXE").getAbsolutePath(), path, "/mFilePrintDefault", "/mFileExit"};
		if (libreOfficePath.length() > 0)
			switch (os) {
			case Windows:
				execCmd = new String[] {libreOfficePath, "--calc", "-p", path};
				break;
			default:
				execCmd = new String[] {libreOfficePath, "--calc", "-p", path};
				break;
			}
		
		if (execCmd != null)
			new ExecThread(execCmd, "Невозможно напечатать файл через табличный процессор.").start();
		else
			openDefault(path);
	}

	public static void openDefault(String path) {
		String[] execCmd;
		
		switch (os) {
		case Windows:
			execCmd = new String[] {"cmd", "/c", path};
			break;
		default:
			execCmd = new String[] {"xdg-open", path};
			break;
		}
		
		new ExecThread(execCmd, "Невозможно открыть файл.").start();
	}
	
	private static String getRootOutputPath() {
		String res = System.getenv("muz_out");
		
		if (res == null) {
			switch (os) {
			case Windows:
				res = System.getenv("SYSTEMDRIVE");
				break;
			default:
				res = System.getenv("HOME");
				break;
			}
			res = Paths.get(res, "muz_out").toAbsolutePath().toString();
		}
		
		return res;
	}
	
	private static String createReportFile(boolean isReestr, String fileNameWithoutExtension) throws IOException {
		File file = Paths.get(rootOutPath, Integer.toString(CustomFrame.authInfo.cpodr), (isReestr) ? "reestr" : "report", String.format("%s_%td%2$tm%2$tY.%s", fileNameWithoutExtension, new Date(), (isReestr) ? "rar" : "htm")).toAbsolutePath().toFile();
		file.getParentFile().mkdirs();
		file.createNewFile();
		
		return file.getAbsolutePath();
	}
	
	public static String createReportFile(String fileNameWithoutExtension) throws IOException {
		return createReportFile(false, fileNameWithoutExtension);
	}
	
	public static String createReestrFile(String fileNameWithoutExtension) throws IOException {
		return createReportFile(true, fileNameWithoutExtension);
	}
}

class ExecThread extends Thread {
	private String[] cmd;
	private String errMsg;
	
	public ExecThread(String[] cmd, String errMsg) {
		this.cmd = cmd;
		this.errMsg = errMsg;
	}
	
	@Override
	public void run() {
		try {
			Process pr = Runtime.getRuntime().exec(cmd);
			if (pr.waitFor() != 0)
				throw new IOException(streamToString(pr.getErrorStream(), false), new Exception(String.format("Command '%s' exited with value %d.", Arrays.toString(cmd), pr.exitValue())));
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, errMsg, "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private String streamToString(InputStream inpStr, boolean close) throws IOException {
		try (Scanner parentScan = new Scanner(inpStr);
				Scanner childScan = parentScan.useDelimiter("\\A");) {
			return childScan.hasNext() ? childScan.next() : "";
		} finally {
			if (close)
				inpStr.close();
		}
	}

}

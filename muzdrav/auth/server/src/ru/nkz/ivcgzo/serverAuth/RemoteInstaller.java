package ru.nkz.ivcgzo.serverAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;

public class RemoteInstaller extends Thread implements Runnable {
	private ISqlSelectExecutor sse;
	private ServerSocket servSct;
	private boolean stopping;
	private Map<Integer, LibraryInfo> libs;
	
	public RemoteInstaller(ISqlSelectExecutor sse) {
		this.sse = sse;
	}
	
	public void startListen() throws IOException {
		servSct = new ServerSocket(55201);
		start();
	}
	
	public void stopListen() {
		try {
			stopping = true;
			servSct.close();
		} catch (IOException e) {
		}
	}

	@Override
	public void run() {
		while (!stopping) {
			try (Socket clientSct = listenForClient()) {
				checkAndTransferLibs(clientSct);
			} catch (Exception e) {
				if (!stopping)
					e.printStackTrace();
			}
		}
	}
	
	private Socket listenForClient() throws Exception {
		Socket clientSct = null;
		while (!stopping) {
			try {
//				servSct.setSoTimeout(1000);
				clientSct = servSct.accept();
				handShake(clientSct);
				break;
			} catch (SocketTimeoutException e) {
				continue;
			} catch (Exception e) {
				throw new Exception("Handshake failed.", e);
			}
		}
		return clientSct;
	}
	
	private void handShake(Socket clientSct) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSct.getInputStream()));
			PrintWriter writer = new PrintWriter(clientSct.getOutputStream());
			writer.println("Who's this?");
			writer.flush();
			if (reader.readLine().equals("Muzdrav client.")) {
				writer.println("Ohayo.");
				writer.flush();
			} else {
				writer.println("Get off me.");
				writer.flush();
				throw new Exception("Talked to stranger.");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private synchronized void checkAndTransferLibs(Socket clientSct) throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSct.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSct.getOutputStream())) {
			writer.println(getSharedLibrariesAndMainModuleInfo());
			writer.flush();
			
			String mainModulePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
			String libPath = new File(new File(mainModulePath), "lib").getAbsolutePath();
			String idStr;
			byte[] buf = new byte[65536];
//			clientSct.setSoTimeout(2000);
			while ((idStr = reader.readLine()) != null) {
				if (idStr.length() == 0)
					break;
				int id = Integer.parseInt(idStr);
				try (FileInputStream fis = new FileInputStream(new File(libs.get(id).id == 0 ? mainModulePath : libPath, libs.get(id).name))) {
					int read = fis.read(buf);
					while (read == buf.length) {
						clientSct.getOutputStream().write(buf, 0, read);
						read = fis.read(buf);
					}
					clientSct.getOutputStream().write(buf, 0, read);
				}
				if (!reader.readLine().equals("Got it."))
					throw new Exception("Did not get success transfer message from client.");
			}
			
			writer.println("Good bye.");
			writer.flush();
		} catch (Exception e) {
			throw new Exception("Failed checking or transfering libraries.", e);
		}
	}
	
	private String getSharedLibrariesAndMainModuleInfo() throws Exception {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id, name, md5, size FROM s_libs WHERE (id < 1) AND (req = TRUE) ")) {
			libs = new HashMap<>();
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = doc.createElement("libInfoList");
			doc.appendChild(root);
			ResultSet rs = acrs.getResultSet();
			while (rs.next()) {
				LibraryInfo lib = new LibraryInfo();
				
				Element libInfo = doc.createElement("libInfo");
				libInfo.setAttribute("id", rs.getString(1));
				root.appendChild(libInfo);
				lib.id = rs.getInt(1);
				
				Element libInfoValue;
				libInfoValue = doc.createElement("name");
				libInfoValue.appendChild(doc.createTextNode(rs.getString(2)));
				libInfo.appendChild(libInfoValue);
				lib.name = rs.getString(2);
				
				libInfoValue = doc.createElement("md5");
				libInfoValue.appendChild(doc.createTextNode(rs.getString(3)));
				libInfo.appendChild(libInfoValue);
				
				libInfoValue = doc.createElement("size");
				libInfoValue.appendChild(doc.createTextNode(rs.getString(4)));
				libInfo.appendChild(libInfoValue);
				
				libs.put(lib.id, lib);
			}
			return xmlDocToString(doc);
		} catch (Exception e) {
			throw new Exception("Failed getting libraries list.", e);
		}
	}
	
	private String xmlDocToString(Document doc) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), sr);
		return sw.toString();
	}
	
	private class LibraryInfo {
		int id;
		String name;
	}
}


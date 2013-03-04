package ru.nkz.ivcgzo.serverAutoProc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Scanner;

public class JsonHttpTransport {
	private Authenticator proxyAuth;
	private final String charsetName = "utf-8";
	
	public JsonHttpTransport() {
		requestAuthentication();
	}
	
	public synchronized String sendPostRequest(String addr, String text) throws MalformedURLException, IOException {
		String response = "";
		HttpURLConnection conn;
		conn = (HttpURLConnection) new URL(addr).openConnection();
		conn.setDoOutput(true);
		conn.addRequestProperty("Content-Type", String.format("application/json; charset=%s", charsetName));
		conn.addRequestProperty("Accept", String.format("application/json; charset=%s", charsetName));
		
		try (OutputStream os = conn.getOutputStream()) {
			os.write(((String) text).getBytes(charsetName));
			os.flush();
			
			try (InputStream is = conn.getInputStream()) {
				response = streamToString(is, true);
			}
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new IOException(response);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		
		return response;
	}
	
	private void requestAuthentication() {
		if (proxyAuth == null) {
			if (Boolean.valueOf(System.getProperty("http.proxySet", "false"))) {
				proxyAuth = new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(System.getProperty("http.proxyUser"), System.getProperty("http.proxyPassword").toCharArray());
					}
				};
				Authenticator.setDefault(proxyAuth);
			}
		}
	}

	private String streamToString(InputStream inpStr, boolean close) throws IOException {
		try (Scanner parentScan = new Scanner(inpStr, charsetName);
				Scanner childScan = parentScan.useDelimiter("\\A");) {
			return childScan.hasNext() ? childScan.next() : "";
		} finally {
			if (close)
				inpStr.close();
		}
	}
	

}

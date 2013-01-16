package ru.nkz.ivcgzo.clientManager.common.redmineManager;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Transport {
	private static final String charsetName = "utf-8";
	private String baseAddress;
	private String apiKey;
	private HttpURLConnection conn;
	
	public Transport(String baseAddress, String apiKey) {
		if (!baseAddress.endsWith("/"))
			baseAddress += "/";
		this.baseAddress = baseAddress;
		this.apiKey = apiKey;
	}
	
	public String get(String supplementaryAddress, String... keyValuePairs) throws Exception {
		String keyValueString = getKeyValueString(keyValuePairs);
		conn = (HttpURLConnection) new URL(baseAddress + supplementaryAddress + ((keyValueString.length() > 0) ? "?" + keyValueString : "")).openConnection();
		String response = "";
		
		try (InputStream inpStr = conn.getInputStream()) {
			response = streamToString(inpStr, false);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new Exception(response);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		
		return response;
	}
	
	private String getKeyValueString(String... keyValuePairs) throws Exception {
		String res = "";
		
		if (keyValuePairs.length % 2 != 0)
			throw new Exception("Keys and values must go in pairs.");
		
		for (int i = 0; i < keyValuePairs.length; i += 2)
			res += keyValuePairs[i] + "=" + URLEncoder.encode(keyValuePairs[i + 1], charsetName) + "&";
		res += "key=" + apiKey;
		
		return res;
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
	
	public String post(String contentType, Object data, String supplementaryAddress, String... keyValuePairs) throws Exception {
		String keyValueString = getKeyValueString(keyValuePairs);
		conn = (HttpURLConnection) new URL(baseAddress + supplementaryAddress + ((keyValueString.length() > 0) ? "?" + keyValueString : "")).openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept-Encoding", "gzip");
		conn.addRequestProperty("Content-Length", "113");
		conn.addRequestProperty("Content-Type", String.format("%s; charset=%s", contentType, charsetName));
//		conn.setRequestProperty("Content-Type", contentType);
		String response = "";
//		conn.getPermission();
//		conn.getHeaderFields();
//		conn.getRequestProperties();
		
		try {
			OutputStream os = conn.getOutputStream();
			if (contentType.equals("application/json")) {
			} else {
				
			}
			os.write(((String) data).getBytes(charsetName));
//			os.write(new byte [] {0,1,2,3});
//			os.close();
			
			InputStream is = conn.getInputStream();
			response = streamToString(is, true);
			is.close();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED)
				throw new Exception(response);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		
		return response;
	}
}

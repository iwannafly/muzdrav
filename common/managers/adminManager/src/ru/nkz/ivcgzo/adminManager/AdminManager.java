package ru.nkz.ivcgzo.adminManager;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class AdminManager {
	static final Console cons = System.console();
	static Map<String, Integer> managers;
	
	/**
	 * @param args
	 * @throws Exception 
	 */ 
	public static void main(String[] args) throws Exception {
		listManagers();
		while (true) {
			parseInput(cons.readLine());
		}
	}
	
	public static void listManagers() {
		ServerSocket ssc;
		Socket sct;
		PrintWriter pwr;
		BufferedReader brd;
		int port;
		String name;
		
		managers = new HashMap<>();
		try {
			ssc = new ServerSocket(9090);
			Thread.sleep(2000);
			synchronized (ssc) {
				while (true) {
					try {
						ssc.setSoTimeout(100);
						sct = ssc.accept();
						pwr = new PrintWriter(sct.getOutputStream());
						brd = new BufferedReader(new InputStreamReader(sct.getInputStream()));
						pwr.println("LA-LI-LU-LE-LOOOOOO!!!");
						pwr.flush();
						System.out.println(brd.readLine());
						port = Integer.parseInt(brd.readLine());
						pwr.println("See you soon.");
						pwr.flush();
						System.out.println(brd.readLine());
						sct.close();
						
						Thread.sleep(500);
						name = sendCommandToManager(port, "getManagerName", null);
						managers.put(name, port);
					} catch (SocketTimeoutException e) {
						if (!managers.isEmpty()) {
							cons.writer().println("Available server managers:");
							for (String man : managers.keySet().toArray(new String[] {})) {
								cons.writer().println(man);
							}
						}
						else {
							cons.writer().println("None of server managers available");
						}
						ssc.close();
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void parseInput(String input) {
		String cmd;
		String[] params;
		int port;
		String resp;
		
		try {
			switch (input.toLowerCase()) {
			case "quit":
				System.exit(0);
				break;
			case "list":
				listManagers();
				break;
			default:
				params = input.split(" ");
				if (params.length > 1) {
					if (managers.containsKey(params[0])) {
						port = managers.get(params[0]);
						cmd = params[1];
						if (params.length > 2)
							System.arraycopy(params, 2, params, 0, params.length - 2);
						else
							params = null;
						resp = sendCommandToManager(port, cmd, params);
						cons.writer().println(resp);
					}
					else {
						throw new Exception(String.format("unknown server '%s'", params[0]));
					}
				}
				else if (params.length > 0) {
					throw new Exception("command not specified");
				}
				else {
					throw new Exception("empty command");
				}
				break;
			}
		} catch (Exception e) {
			cons.writer().println(String.format("Error: %s", e.getMessage()));
		}
	}
	
	public static String sendCommandToManager(int port, String cmd, String[] params) throws Exception{
		Socket sct = null;
		PrintWriter pwr = null;
		BufferedReader brd = null;
		String res;

		try {
			sct = new Socket("localhost", port);
			pwr = new PrintWriter(sct.getOutputStream());
			brd = new BufferedReader(new InputStreamReader(sct.getInputStream()));
			
			pwr.println("ru.nkz.ivcgzo.adminManager");
			pwr.println(cmd);
			if (params != null)
				for (String param : params) {
					pwr.println(param);
				}
			pwr.flush();
			res = brd.readLine();
			if (res.startsWith("Error"))
				throw new Exception(res);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (brd != null)
				brd.close();
			if (pwr != null)
				pwr.close();
			if (sct != null)
				sct.close();
		}
		return res;
	}
}

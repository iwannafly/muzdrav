package ru.nkz.ivcgzo.adminManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public abstract class AdminController {
	public abstract void startServers() throws Exception;
	public abstract void startServer(String name) throws Exception;
	public abstract void pauseServers() throws Exception;
	public abstract void pauseServer(String name) throws Exception;
	public abstract void stopServers() throws Exception;
	public abstract void stopServer(String name) throws Exception;
	public abstract String getManagerName();

	public static AdminListener listener;
	
	private class AdminListener implements Runnable {
		Thread thread;
		int port;
		ServerSocket srv;

		public AdminListener(int portNum) {
			port = portNum;
			thread = new Thread(this);
		}
		
		@Override
		public void run() {
			try {
				listenAdminCommands();
			} catch (Exception e) {
				deaf();
			}
		}
		
		public void listen() {
			thread.start();	
		}
		
		public void deaf() {
			thread.interrupt();
			try {
				srv.close();
			} catch (IOException e) {
			}
		}
		
		private void listenAdminCommands() throws Exception {
			Socket sct = null;
			PrintWriter pwr = null;
			BufferedReader brd = null;
			String errMsg = null;
			
			boolean acc = false;
			try {
				String funcName;
				srv = new ServerSocket(port);
				while (true) {
					try {
						Thread.sleep(100);
						sct = srv.accept();
						pwr = new PrintWriter(sct.getOutputStream());
						brd = new BufferedReader(new InputStreamReader(sct.getInputStream()));
						
						if (brd.readLine().equals("ru.nkz.ivcgzo.adminManager")) {
							acc = true;
							funcName = brd.readLine();
							switch (funcName.toLowerCase()) {
							case "getmanagername":
								pwr.println(getManagerName());
								pwr.flush();
								break;
							case "starts":
								startServers();
								pwr.println("Done.");
								pwr.flush();
								break;
							case "start":
								errMsg = "Server name not specified";
								sct.setSoTimeout(500);
								startServer(brd.readLine());
								pwr.println("Done.");
								pwr.flush();
								break;
							case "pauses":
								pauseServers();
								pwr.println("Done.");
								pwr.flush();
								break;
							case "pause":
								errMsg = "Server name not specified";
								sct.setSoTimeout(500);
								pauseServer(brd.readLine());
								pwr.println("Done.");
								pwr.flush();
								break;
							case "stops":
								stopServers();
								pwr.println("Done.");
								pwr.flush();
								break;
							case "stop":
								errMsg = "Server name not specified";
								sct.setSoTimeout(500);
								stopServer(brd.readLine());
								pwr.println("Done.");
								pwr.flush();
								break;
							default:
								throw new Exception(String.format("Unknown function '%s'.", funcName));
							}
						} else
							throw new Exception("Who are you?");
						acc = false;
						brd.close();
						pwr.close();
						sct.close();
					} catch (SocketTimeoutException e) {
						if (pwr != null) {
							pwr.println("Error: " + errMsg);
							pwr.flush();
						}
					} catch (Exception e) {
						if (!acc)
							throw new Exception(String.format("I can't hear you. %s ", e.getMessage()), e);
						else if (pwr != null) {
							pwr.println("Error: " + e.getMessage());
							pwr.flush();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			} finally {
				if (brd != null)
					brd.close();
				if (pwr != null)
					pwr.close();
				if (sct != null)
					sct.close();
				if (srv != null)
					srv.close();
			}
		}
		
	}

	public void listenToAdminManager () {
		Socket sct;
		PrintWriter pwr;
		BufferedReader brd;
		
		while (true) {
			try {
				while (true) {
					Thread.sleep(1000);
					sct = new Socket("localhost", 9090);
					pwr = new PrintWriter(sct.getOutputStream());
					brd = new BufferedReader(new InputStreamReader(sct.getInputStream()));
					//				if (brd.readLine() == "This is ru.nkz.ivcgzo.adminManager. Anybody here?") {
					//					pwr.println("Hi, this is ru.nkz.ivcgzo.serverManager. How are you?");
					//					pwr.flush();
					//					if (brd.readLine() == "I'm fine thank you. Wow, you are pretty! Can I have your phone number?") {
					//						pwr.println("Hmm. I don't know. May be if you would say the magic word...");
					//						pwr.flush();
					//				System.out.println(brd.readLine());
					if (brd.readLine().equals("LA-LI-LU-LE-LOOOOOO!!!")) {
						pwr.println("Oooh, I'm all yours! Here's my number.");
						pwr.println(sct.getLocalPort());
						pwr.flush();
						if (brd.readLine().equals("See you soon.")) {
							if (listener != null)
								listener.deaf();
							listener = new AdminListener(sct.getLocalPort());
							pwr.println("Oooh, I'm so glad you called! Wait for me I'll be ready in a couple of seconds.");
							pwr.flush();
							brd.close();
							pwr.close();
							sct.close();
							listener.listen();
							Thread.sleep(5000);
							break;
						}
					}
					//					}
					//				}
					pwr.println("Go fuck youself stranger!");
					pwr.flush();
					sct.close();
				}
			} catch (Exception e) {
				continue;
			}
		}
	}
	
}


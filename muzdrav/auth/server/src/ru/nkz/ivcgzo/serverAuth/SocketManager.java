package ru.nkz.ivcgzo.serverAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class SocketManager {
	private Map<Integer, ScmSocket> sockets;
	private final int socketCount;
	private final int bufSize;
	
	public SocketManager(int socketCount, int bufSize) {
		this.socketCount = socketCount;
		this.bufSize = bufSize;
		
		sockets = new HashMap<>(socketCount);
	}
	
	public synchronized int openRead(String path) throws IOException, InterruptedException {
		return open(path, false);
	}
	
	public synchronized int openWrite(String path) throws IOException, InterruptedException {
		return open(path, true);
	}
	
	private synchronized int open(String path, boolean writeMode) throws IOException, InterruptedException {
		int tries = 0;
		
		loop: while (true) {
			if (sockets.size() < socketCount) {
				ScmSocket socket = new ScmSocket(path, writeMode);
				sockets.put(socket.getPort(), socket);
				socket.start();
				return socket.getPort();
			} else {
				for (Integer port : sockets.keySet()) {
					if (!sockets.get(port).isRunning()) {
						close(port, false);
						continue loop;
					}
				}
				if (tries++ < 5)
					wait(1000);
				else
					throw new IOException("No available sockets for file transfer.");
			}
		}
	}
	
	public void close(int port, boolean delFile) {
		if (sockets.containsKey(port)) {
			ScmSocket socket = sockets.get(port);
			sockets.remove(port);
			socket.close(delFile);
		}
	}
	
	private class ScmSocket extends Thread implements Runnable {
		private ServerSocket ssc;
		private Socket client;
		private String path;
		private boolean writeMode;
		private FileInputStream fis;
		private FileOutputStream fos;
		private boolean running;
		private boolean closed;
		
		public ScmSocket(String path, boolean writeMode) throws IOException  {
			this.path = path;
			this.writeMode = writeMode;
			
			if (!writeMode)
				fis = new FileInputStream(path);
			else {
				fos = new FileOutputStream(path);
				fos.getChannel().lock();
			}
			ssc = new ServerSocket();
			ssc.bind(null);
			this.setName(String.format("filTrans-%d", getPort()));
			
			running = true;
		}
		
		public int getPort() {
			return ssc.getLocalPort();
		}
		
		public void close(boolean delFile) {
			closeFilesAndSocket();
			try {
				if (delFile) {
					File file = new File(path);
					if (file.exists())
						file.delete();
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		private synchronized void closeFilesAndSocket() {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
					if (client != null) {
						client.getOutputStream().close();
						client = null;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				if (fos != null) {
					fos.close();
					fos = null;
					if (client != null) {
						client.getInputStream().close();
						client = null;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				ssc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (!closed) {
				closed = true;
				synchronized (SocketManager.this) {
					SocketManager.this.notify();
				}
			}
		}
		
		@Override
		public void run() {
			byte[] buf = new byte[bufSize];
			
			try {
				ssc.setSoTimeout(30000);
				client = ssc.accept();
				ssc.setSoTimeout(5000);

				if (!writeMode) {
					try {
						int read = bufSize;
						while (read == bufSize) {
							read = fis.read(buf);
							client.getOutputStream().write(buf, 0, read);
						}
					} catch (SocketTimeoutException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						int read = client.getInputStream().read(buf);
						while (read > -1) {
							fos.write(buf, 0, read);
							read = client.getInputStream().read(buf);
						}
					} catch (SocketTimeoutException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			running = false;
			closeFilesAndSocket();
		}
		
		public boolean isRunning() {
			return running;
		}
	}
}

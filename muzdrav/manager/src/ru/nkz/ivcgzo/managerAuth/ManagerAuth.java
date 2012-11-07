package ru.nkz.ivcgzo.managerAuth;

import ru.nkz.ivcgzo.serverManager.serverManager;

public class ManagerAuth extends serverManager {

	public static void main(String[] args) {
		ManagerAuth manAuth = null;
		String ip = null;
		
		try {
			if (args.length > 0) {
				if (args[0].equals("tst")) {
					System.out.println("Using test database server.");
					ip = "10.0.0.248";
				} else if (args[0].equals("wrk")) {
					System.out.println("Using work database server.");
					ip = "10.0.0.242";
				} else {
					usage();
				}
			} else {
				usage();
			}
			
			manAuth = new ManagerAuth();

			manAuth.ConnectToDatabase(DatabaseDriver.postgre, ip, "5432", "zabol", null, 5, "postgres", "postgres");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			manAuth.loadPlugins();
			manAuth.startServers();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			synchronized (manAuth) {
				manAuth.listenToAdminManager();
			}
		}
	}
	
	private static void usage() {
		System.out.println("Database server (tst, wrk) must be specified explicitly.");
		
		System.exit(2);
	}
	
	@Override
	public String getManagerName() {
		return "muzdrav";
	}
}

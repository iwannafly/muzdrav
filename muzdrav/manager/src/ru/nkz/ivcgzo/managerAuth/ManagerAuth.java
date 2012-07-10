package ru.nkz.ivcgzo.managerAuth;

import ru.nkz.ivcgzo.serverManager.serverManager;

public class ManagerAuth extends serverManager {

	public static void main(String[] args) {
		ManagerAuth manAuth = new ManagerAuth();
		
		try {
			manAuth.ConnectToDatabase(DatabaseDriver.postgre, "10.0.0.242", "5432", "zabol", null, 5, "postgres", "postgres");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
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
	
	@Override
	public String getManagerName() {
		return "muzdrav";
	}
}

package ru.nkz.ivcgzo.Infomat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    private static volatile DbManager dbManagerInstance;
    private static Connection fbConnection;
    private String host;
    private String port;
    private String name;
    private String user;
    private String password;

    private DbManager() {        
    }

    //double-checked locking
    public static DbManager getInstance() {
        DbManager localInstance = dbManagerInstance;
        if (localInstance == null) {
            synchronized (DbManager.class) {
                localInstance = dbManagerInstance;
                if (localInstance == null) {
                    dbManagerInstance = localInstance = new DbManager();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() {
        Connection localInstance = fbConnection;
        if (localInstance == null) {
            synchronized (DbManager.class) {
                localInstance = fbConnection;
                if (localInstance == null) {
//                    fbConnection = localInstance = createConnection();
                }
            }
        }
        return localInstance;
    }

//    private createConnection() {
//        setDefaults();
//        connectingToDb();
//    }
    private Connection createConnection(String host, String port, String name, String user, String pass) {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String connString = String.format("jdbc:firebirdsql://%s:%s/%s", host, port, name);
            fbConnection = DriverManager.getConnection(connString, user, pass);
            return fbConnection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

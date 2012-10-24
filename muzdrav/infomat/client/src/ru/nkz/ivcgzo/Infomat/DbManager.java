package ru.nkz.ivcgzo.Infomat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    private static volatile DbManager dbManagerInstance;
    private Connection fbConnection;
    private String host;
    private String port;
    private String name;
    private String user;
    private String password;

    private DbManager() {
        setDefaults();
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

    private void setDefaults() {
        host = "localhost";
        port = "3050";
        name = "C:\\Asupol\\DB\\zabol.gdb";
        user = "sysdba";
        password = "masterkey";
    }

    public Connection getConnection() throws SQLException {
        if ((fbConnection == null) || (fbConnection.isClosed())) {
            fbConnection = createConnection();
        }
        return fbConnection;
    }

    private Connection createConnection() {
        try {
//            Class.forName("org.firebirdsql.jdbc.FBDriver");
            DriverManager.registerDriver(new org.firebirdsql.jdbc.FBDriver());
            String connString = String.format("jdbc:firebirdsql://%s:%s/%s?characterEncoding=cp1251", host, port, name);
            fbConnection = DriverManager.getConnection(connString, user, password);
            fbConnection.setAutoCommit(false);
            return fbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

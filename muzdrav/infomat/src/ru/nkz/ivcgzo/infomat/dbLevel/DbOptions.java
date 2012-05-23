package ru.nkz.ivcgzo.infomat.dbLevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbOptions {
    private String driverClassName;
    private String host;
    private int port;
    private String name;
    private String login;
    private String password;
    private String url;

    public DbOptions() {
        Properties properties = new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream("dbOption.properties");
            properties.load(in);
            in.close();
            driverClassName = properties.getProperty("driverClassName");
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            name = properties.getProperty("name");
            login = properties.getProperty("login");
            password = properties.getProperty("password");
            url = generateUrl();
        } catch (IOException e) {
            System.out.println("Нет доступа к файлу");
            //e.printStackTrace();
        }
    }

    public final String getDriverClassName() {
        return driverClassName;
    }

    public final String getHost() {
        return host;
    }

    public final int getPort() {
        return port;
    }

    public final String getName() {
        return name;
    }

    public final String getLogin() {
        return login;
    }

    public final String getPassword() {
        return password;
    }

    public final String getUrl() {
        return url;
    }

    private String generateUrl() {
        String tmpUrl = String.format("jdbc:firebirdsql://%s:%s/%s", host, port, name);
        return tmpUrl;
    }
}

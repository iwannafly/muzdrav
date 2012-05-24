package ru.nkz.ivcgzo.infomat.dbLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionManager {

    // static variable
    private static DbConnectionManager instance = null;

    // instance variables
    private Connection connection = null;

    private DbOptions dbOptions = null;

    // флаг занятости соединения
    private boolean isConnectionBusy = false;

    /**
     * Приватный конструктор Singleton'а.
     * При создании считываются настройки соединения с БД из properties файла.
     *
     * <p><b>ВАЖНО:</b> При инициализации устанавливается соединение.
     */
    //TODO подумать над необходимостью установки соединения при инициализации
    private DbConnectionManager() {
        dbOptions = new DbOptions();
        loadDbDriver(dbOptions.getDriverClassName());

        //always new the pool because pool is an instance variable
        this.connection = newConnection();
    }

    /**
     * Возвращает единственный экземпляр объекта DbConnectionManager,
     * созданный при первом вызове метода.
     *
     * @return DBConnectionManager - instance.
     */
    public static synchronized DbConnectionManager getInstance() {
        if (instance == null) {
            instance = new DbConnectionManager();
        }
        return instance;
    }

    /**
     * Загружает драйвер БД
     */
    private void loadDbDriver(final String driverClassname) {
        try {
            Class.forName(dbOptions.getDriverClassName()).newInstance();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки драйвера БД");
        }
    }

    /**
     * Создаёт новое соединение
     */
    private Connection newConnection() {
        Connection tmpConnection = null;
        try {
            tmpConnection = DriverManager.getConnection(
                    dbOptions.getUrl(), dbOptions.getLogin(), dbOptions.getPassword());
            isConnectionBusy = false;
            // con.setAutoCommit(true);//thread 804 by trulore
        } catch (SQLException e) {
            return null;
        }
        return tmpConnection;
    }

    /**
     * Возвращает соединение.
     * <p><b>ВАЖНО:</b> Если соединение закрыто или равно null - создаёт новое соединение
     */
    public Connection getConnection() {
        if (isConnectionBusy) {
            System.out.println("Соединение занято");
        }
        try {
            if (connection.isClosed()) {
                connection = null;
            }
        } catch (SQLException e) {
            connection = null;
        }
        if (connection == null) {
            connection = newConnection();
        }
        isConnectionBusy = true;
        return connection;
    }

    /**
     * Устанавливает флаг освобождения соединения
     */
    public void freeConnection() {
        isConnectionBusy = false;
    }

    /**
     * Уничтожает соединение
     */
    public void releaseConnection() {
        isConnectionBusy = false;
        try {
            connection.close();
        } catch (SQLException e) {
            connection = null;
        }
        connection = null;
    }
}

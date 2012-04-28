package ru.nkz.ivcgzo.dbConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.nkz.ivcgzo.misc.Misc;


public class DBConnection {
	public final DBParams databaseParams;
	private Connection connection;
	public final String binPath;
	
	public DBConnection(DBParams dbParams) throws Exception {
		databaseParams = dbParams;
		
		binPath = getBinPath();
		loadDriver();
	}
	
	private String getBinPath() throws Exception {
		String exeName = getExecutableName();
		for (String path : getPaths()) {
			File exeFile = new File(path, exeName);
			if (exeFile.exists())
				return exeFile.getParentFile().getAbsolutePath();
		}
		throw new Exception(String.format("The 'bin' folder not specified for %s database in PATH variable.", databaseParams.type));
	}
	
	private String getExecutableName() throws Exception {
		String name;
		
		switch (databaseParams.type) {
		case postgre:
			name =  "psql";
			break;
		case oracle:
			name = "SQLPLUS";
			break;
		default:
			throw new Exception(String.format("Unsupported database type '%s'.", databaseParams.type));
		}
		
		if (Misc.isWindows())
			return name + ".exe";
		else
			return name;
	}
	
	private String[] getPaths() throws Exception {
		String[] paths = System.getenv("PATH").split(Misc.isWindows() ? ";" : ":");
		
		if (paths == null)
			throw new Exception("How are you living without a PATH variable?");
		
		return paths;
	}
	
	private void loadDriver() throws ClassNotFoundException {
		switch (databaseParams.type) {
		case postgre:
			Class.forName("org.postgresql.Driver");
			break;
		case oracle:
			Class.forName("oracle.jdbc.OracleDriver");
			break;
		default:
			throw new ClassNotFoundException(String.format("Unsupported database type '%s'.", databaseParams.type));
		}
	}
	
	public void connect() throws SQLException {
		connection = DriverManager.getConnection(getConnectionString(), databaseParams.login, databaseParams.password);
		connection.setAutoCommit(false);
	}
	
	private String getConnectionString() throws SQLException {
		String connStr;
		
		switch (databaseParams.type) {
		case postgre:
			connStr = "jdbc:postgresql:";
			if (databaseParams.host != null) {
				connStr += String.format("//%s", databaseParams.host);
				if (databaseParams.port != null)
					connStr += String.format(":%d/", databaseParams.port);
				else
					connStr += '/';
			}
			connStr += String.format("%s", databaseParams.name);
			return connStr;
		case oracle:
			connStr = "jdbc:oracle:thin:@";
			if (databaseParams.host != null) {
				connStr += String.format("%s", databaseParams.host);
				if (databaseParams.port != null)
					connStr += String.format(":%d", databaseParams.port);
			}
			connStr += String.format(":%s", databaseParams.schema);
			return connStr;
		default:
			throw new SQLException(String.format("Unsupported database type '%s'.", databaseParams.type));
		}
	}
	
	public void disconnect() throws SQLException {
		if (connection != null)
			connection.close();
	}
	
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
	
	public ResultSet executeQuery(Statement stm, String sql) throws SQLException {
		return stm.executeQuery(sql);
	}
}

package ru.nkz.ivcgzo.serverManager.common;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

/**
 * Класс, представляющий собой одно подключене к базе данных и
 * предоставляющий методы для выполнения запросов на чтение данных.
 * Создается менеджером плагинов-серверов.
 * Не предоставляет методов для управления транзакциями.
 * Уровень изоляции транзакций - read committed.
 * @author bsv798
 */
public class SqlSelectExecutor implements ISqlSelectExecutor {
	protected Connection conn;
	private String connString, user, pass;
	private boolean _connected;
	private boolean _closed;
	private static boolean reconnecting = false;
	
	/**
	 * Устанавливает подключение к базе данных и настраивает его свойства.
	 * @param connString - строка подключеня к базе, в которой
	 * указываются драйвер подключения, хост, порт, путь к базе
	 * или имя схемы и дополнительные параметры.
	 * @param user - логин.
	 * @param pass - пароль.
	 * @throws SQLException
	 */
	public SqlSelectExecutor(String connString, String user, String pass) throws SQLException {
		conn = DriverManager.getConnection(connString, user, pass);
		conn.setAutoCommit(false);
		conn.setReadOnly(true);
		
		this.connString = connString;
		this.user = user;
		this.pass = pass;
		
		_connected = true;
		_closed = false;
	}
	
	protected final boolean isConnected() {
		return _connected;
	}
	
	@Override
	public final AutoCloseableResultSet execQuery(String sql) throws SqlExecutorException {
		 try {
			return new AutoCloseableResultSet(this, conn.createStatement().executeQuery(sql));
		} catch (SQLException e) {
			throw new SqlExecutorException(e.getMessage(), e);
		}
	}

	@Override
	public AutoCloseableResultSet execPreparedQuery(String sql, Object... params) throws SqlExecutorException {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			prepareStatement(ps, params);		
			return new AutoCloseableResultSet(this, ps.executeQuery());
		} catch (SQLException e) {
			throw new SqlExecutorException(e.getMessage(), e);
		}
	}

	/**
	 * Устанавливает параметры параметризированного запроса.
	 * @param ps - экземпляр запроса;
	 * @param params - значения параметров в порядке, указанном в запросе;
	 * @throws SQLException
	 */
	protected void prepareStatement(PreparedStatement ps, Object... params) throws SQLException {
		Class<?> cls;
		
		for (int i = 0; i < params.length; i++)
			if (params[i] == null) {
				ps.setNull(i + 1, java.sql.Types.NULL);
			} else {
				cls = params[i].getClass();
				if (cls == Integer.class)
					ps.setInt(i + 1, (Integer)params[i]);
				else if (cls == String.class)
					ps.setString(i + 1, (String)params[i]);
				else if (cls == Short.class)
					ps.setShort(i + 1, (Short)params[i]);
				else if (cls == java.sql.Date.class)
					ps.setDate(i + 1, (java.sql.Date)params[i]);
				else if (cls == java.sql.Time.class)
					ps.setTime(i + 1, (java.sql.Time)params[i]);
				else if (cls == java.sql.Timestamp.class)
					ps.setTimestamp(i + 1, (java.sql.Timestamp)params[i]);
				else if (cls == Float.class)
					ps.setFloat(i + 1, (Float)params[i]);
				else if (cls == Long.class)
					ps.setLong(i + 1, (Long)params[i]);
				else if (cls == Double.class)
					ps.setDouble(i + 1, (Double)params[i]);
			}
	}

	@Override
	public <T extends TBase<?, F>, F extends TFieldIdEnum> AutoCloseableResultSet execPreparedQueryT(String sql, T obj, Class<?>[] types, int... indexes) throws SqlExecutorException {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			prepareStatementT(ps, obj, types, indexes);		
			return new AutoCloseableResultSet(this, ps.executeQuery());
		} catch (SQLException e) {
			throw new SqlExecutorException(e.getMessage(), e);
		}
	}

	/**
	 * Устанавливает параметры параметризированного запроса.
	 * @param ps - экземпляр запроса;
	 * @param obj - объект thrift-структуры;
	 * @param fields - поля, содержащиеся в этом объекте;
	 * @param types - типы данных этих полей;
	 * @param indexes - индексы полей в порядке, указанном в запросе;
	 * @throws SQLException
	 */
	protected <T extends TBase<?, F>, F extends TFieldIdEnum> void prepareStatementT(PreparedStatement ps, T obj, Class<?>[] types, int... indexes) throws SQLException {
		for (int i = 0; i < indexes.length; i++) {
			F fld = obj.fieldForId(indexes[i] + 1);
			if (!obj.isSet(fld)) {
				ps.setNull(i + 1, java.sql.Types.NULL);
			} else {
				Class<?> cls = types[indexes[i]];
				if (cls == Integer.class)
					ps.setInt(i + 1, (Integer) obj.getFieldValue(fld));
				else if (cls == String.class)
					ps.setString(i + 1, (String) obj.getFieldValue(fld));
				else if (cls == Short.class)
					ps.setShort(i + 1, (Short) obj.getFieldValue(fld));
				else if (cls == java.sql.Date.class)
					ps.setDate(i + 1, new java.sql.Date((Long) obj.getFieldValue(fld)));
				else if (cls == java.sql.Time.class)
					ps.setTime(i + 1, new java.sql.Time((Long) obj.getFieldValue(fld)));
				else if (cls == java.sql.Timestamp.class)
					ps.setTimestamp(i + 1, new java.sql.Timestamp((Long) obj.getFieldValue(fld)));
				else if (cls == Float.class)
					ps.setFloat(i + 1, (Float) obj.getFieldValue(fld));
				else if (cls == Long.class)
					ps.setLong(i + 1, (Long) obj.getFieldValue(fld));
				else if (cls == Double.class)
					ps.setDouble(i + 1, (Double) obj.getFieldValue(fld));
			}
		}
	}

	@Override
	public final void closeStatement(Statement stm) {
		try {
			if (stm != null)
				stm.close();
		} catch (SQLException e) {
		}
	}
		
	@Override
	public final void closeStatement(ResultSet rs){
		try {
			if (rs != null)
				closeStatement(rs.getStatement());
		} catch (SQLException e) {
		}

	}

	@Override
	public void closeConnection() throws SQLException {
		_closed = true;
		conn.close();
	}

	/**
	 * Это исключение перебрасывается при ошибке выполненя
	 * плагином-сервером любого запроса. Переброска запускает проверку
	 * валидности подключения к базе данных.
	 * @author bsv798
	 */
	public final class SqlExecutorException extends SQLException {
		private static final int sleepMils = 500;
		private static final long serialVersionUID = -2379733341661086868L;
		
		public SqlExecutorException(String reason, Throwable cause) {
			super(reason, cause);
			
			reconnect();
		}

	    /**
	     * Определяет наличие подключения к базе и, если оно отсутствует,
	     * пытается подключиться до тех пор, пока это не удатся или не
	     * поступит запрос на остановку плагина. Так же, откатывает текущую
	     * транзакцию.
	     */
		public void reconnect() {
			Thread recThr;
			
			if (reconnecting)
				return;
			
			try {
				conn.rollback();
//				conn.createStatement().executeQuery("SELECT CAST(1 AS INTEGER) "); //FROM rdb$database
				_connected = true;
			} catch (SQLException e1) {
				_connected = false;
			}

			if (!_connected) {
				recThr = new Thread() {
					@Override
					public void run() {
						reconnecting = true;
						while (!_connected && !_closed) {
							try {
								sleep(sleepMils);
								conn = DriverManager.getConnection(connString, user, pass);
								_connected = true;
							} catch (Exception e) {
							}
						}
						reconnecting = false;
					}
				};
				recThr.start();
			}
		}
}

}

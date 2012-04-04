package ru.nkz.ivcgzo.serverManager.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

/**
 * Класс, представляющий собой одно подключене к базе данных и
 * предоставляющий методы для выполнения запросов на модификацию данных
 * и управления транзакциями.
 * @author bsv798
 */
public class SqlModifyExecutor extends SqlSelectExecutor implements ISqlModifyExecutor, ITransactedSqlExecutor, AutoCloseable {
	private TransactedSqlManager man;
	private boolean _inTransaction;
	private boolean _commit;
	private boolean _manIsSet;
	private ResultSet genKeys;

	/**
	 * Устанавливает подключение к базе данных и настраивает его свойства.
	 * @param man - экземпляр менеджера транзакций.
	 * @throws SQLException
	 */
	public SqlModifyExecutor(String connString, String user, String pass, TransactedSqlManager man)
			throws SQLException {
		super(connString, user, pass);
		
		conn.setReadOnly(false);
		
		this.man = man;
		_manIsSet = man != null;
		
		_inTransaction = false;
	}

	public SqlModifyExecutor(String connString, String user, String pass)
			throws SQLException {
		this(connString, user, pass, null);
	}

	@Override
	public SqlModifyExecutor startTransaction() throws InterruptedException {
		if (isInTransaction())
			throw new InterruptedException("Another transaction is in progress");
		_inTransaction = true;
		_commit = false;
		return this;
	}

	protected boolean isInTransaction() {
		return _inTransaction;
	}
	
	/**
	 * Запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL). Сразу
	 * после выполнения запрос закрывается.
	 * @throws SqlExecutorException
	 */
	@Override
	public int execUpdate(String sql, boolean keys) throws SqlExecutorException  {
		Statement stm = null;
		try {
			stm = conn.createStatement();
			if (keys) {
				int res = stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				setGeneratedKeys(stm.getGeneratedKeys());
				return res;
			} else
				return stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute update sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(stm);
		}	
	}

	/**
	 * Запрос на любую манипуляцию с данными. Сразу после выполнения
	 * запрос закрывается. <b>Внимание:</b> результаты выполнения
	 * запроса получить будет невозможно.
	 * @throws SqlExecutorException
	 */
	@Override
	public boolean exec(String sql, boolean keys) throws SqlExecutorException  {
		Statement stm = null;
		try {
			stm = conn.createStatement();
			if (keys) {
				boolean res = stm.execute(sql, Statement.RETURN_GENERATED_KEYS);
				setGeneratedKeys(stm.getGeneratedKeys());
				return res;
			} else
				return stm.execute(sql);
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(stm);
		}
	}

	/**
	 * Параметризированный запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * Сразу после выполнения запрос закрывается.
	 * @throws SqlExecutorException
	 */
	@Override
	public int execPreparedUpdate(String sql, boolean keys, Object... params) throws SqlExecutorException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, (keys) ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			prepareStatement(ps, params);
			if (keys) {
				int res = ps.executeUpdate();
				setGeneratedKeys(ps.getGeneratedKeys());
				return res;
			} else
				return ps.executeUpdate();
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute update sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(ps);
		}	
	}

	/**
	 * Параметризированный запрос на любую манипуляцию с данными. Сразу после
	 * выполнения запрос закрывается. <b>Внимание:</b> результаты выполнения
	 * запроса получить будет невозможно.
	 * @throws SqlExecutorException
	 */
	@Override
	public boolean execPrepared(String sql, boolean keys, Object... params) throws SqlExecutorException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, (keys) ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			prepareStatement(ps, params);
			if (keys) {
				boolean res = ps.execute();
				setGeneratedKeys(ps.getGeneratedKeys());
				return res;
			} else
				return ps.execute();
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(ps);
		}
	}

	/**
	 * Параметризированный запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * Сразу после выполнения запрос закрывается.
	 * @throws SqlExecutorException
	 */
	@Override
	public <T extends TBase<?, F>, F extends TFieldIdEnum> int execPreparedUpdateT(String sql, boolean keys, T obj, Class<?>[] types, int... indexes) throws SqlExecutorException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, (keys) ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			prepareStatementT(ps, obj, types, indexes);
			if (keys) {
				int res = ps.executeUpdate();
				setGeneratedKeys(ps.getGeneratedKeys());
				return res;
			} else
				return ps.executeUpdate();
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute update sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(ps);
		}	
	}

	/**
	 * Параметризированный запрос на любую манипуляцию с данными. Сразу после
	 * выполнения запрос закрывается. <b>Внимание:</b> результаты выполнения
	 * запроса получить будет невозможно.
	 * @throws SqlExecutorException
	 */
	@Override
	public <T extends TBase<?, F>, F extends TFieldIdEnum> boolean execPreparedT(String sql, boolean keys, T obj, Class<?>[] types, int... indexes) throws SqlExecutorException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, (keys) ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			prepareStatementT(ps, obj, types, indexes);		
			if (keys) {
				boolean res = ps.execute();
				setGeneratedKeys(ps.getGeneratedKeys());
				return res;
			} else
				return ps.execute();
		} catch (SQLException e) {
			throw new SqlExecutorException(String.format("Could not execute sql statement '%s'", sql), e);
		} finally {
			if (!keys)
				closeStatement(ps);
		}
	}

	private void setGeneratedKeys(ResultSet rs) throws SqlExecutorException {
		closeStatement(genKeys);
		genKeys = rs;
		try {
			genKeys.next();
		} catch (SQLException e) {
			throw new SqlExecutorException("Could not get generated keys", e);
		}
	}
	
	@Override
	public ResultSet getGeneratedKeys() {
		return genKeys;
	}

	/**
	 * Подтверждает транзакцию и сообщает об этом менеджеру транзакций.	
	 * @throws SqlExecutorException
	 */
	@Override
	public synchronized void commitTransaction() throws SqlExecutorException {
		try {
			closeStatement(genKeys);
			conn.commit();
		} catch (SQLException e) {
			throw new SqlExecutorException("Could not commit transaction", e);
		} finally {
			_inTransaction = false;
			if (_manIsSet)
				synchronized (man) {
					man.notify();
				}
		}
	}

	public void setCommit() {
		_commit = true;
	}
	
	/**
	 * Откатывает транзакцию и сообщает об этом менеджеру транзакций.	
	 * @throws SqlExecutorException
	 */
	@Override
	public synchronized void rollbackTransaction() {
		try {
			closeStatement(genKeys);
			conn.rollback();
		} catch (SQLException e) {
			new SqlExecutorException("Could not rollback transaction", e);
		} finally {
			_inTransaction = false;
			if (_manIsSet)
				synchronized (man) {
					man.notify();
				}
		}
	}

	/**
	 * Закрывает подключение и сообщает об этом менеджеру транзакций.	
	 * @throws SqlExecutorException
	 */
	@Override
	public void closeConnection() throws SQLException {
		try {
			super.closeConnection();
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			if (_manIsSet)
				synchronized (man) {
					man.notify();
				}
		}
	}

	/**
	 * Автоматически подтверждает или откатывает транзакцию в зависимости от
	 * успешности предыдущих запросов.<br>
	 * <b>Внимание:</b> метод вызывается при выходе из блока <code>try</code>. Интерфейс
	 * <code>AutoCloseable</code> позволяет сделать это без использования блока
	 * <code>finally</code>. Важно помнить, что объекты, реализующие этот интерфейс,
	 * требуют особого объявления в блоке <code>try</code>.
	 */
	@Override
	public synchronized void close() throws SqlExecutorException {
		if (_commit)
			commitTransaction();
		else
			rollbackTransaction();
	}

}

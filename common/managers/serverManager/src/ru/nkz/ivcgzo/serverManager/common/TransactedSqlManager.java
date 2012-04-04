package ru.nkz.ivcgzo.serverManager.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер транзакций.
 * @author bsv798
 */
public class TransactedSqlManager implements ITransactedSqlExecutor {
	private List<SqlModifyExecutor> trans;
	
	public TransactedSqlManager(String connString, String user, String pass, int count) throws SQLException {
		if (count < 1)
			throw new SQLException("Transaction manager requires more than zero connections to work");
		trans = new ArrayList<>(count);
		while (count-- > 0)
			trans.add(new SqlModifyExecutor(connString, user, pass, this));
	}
	
	@Override
	/**
	 * Начинает транзакцию в асинхронном режиме. Каждому потоку предоставляется
	 * одно подключение. Если свободных подключений нет, поток ожидает их
	 * появления. 
	 */
	public synchronized SqlModifyExecutor startTransaction() throws InterruptedException {
		while (true) {
			for (SqlModifyExecutor teq : trans) {
				if (!teq.isInTransaction())
					if (teq.isConnected())
						return teq.startTransaction();
					else
						throw new InterruptedException("Invalid database connection");
			}
			wait();
		}
	}

}

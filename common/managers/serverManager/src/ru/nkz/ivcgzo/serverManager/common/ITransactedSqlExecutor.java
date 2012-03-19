package ru.nkz.ivcgzo.serverManager.common;

/**
 * Определяет методы для управления транзакциями.
 * @author bsv798
 */
public interface ITransactedSqlExecutor {
	/**
	 * Начинает транзакцию.
	 * @return
	 * @throws InterruptedException
	 */
	SqlModifyExecutor startTransaction() throws InterruptedException;
	
}

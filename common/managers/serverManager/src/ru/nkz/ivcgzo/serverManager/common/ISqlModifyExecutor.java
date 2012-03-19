package ru.nkz.ivcgzo.serverManager.common;

import java.sql.ResultSet;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;

/**
 * Определяет методы для выполнения запросов на модификацию данных и
 * управления транзакциями.
 * @author bsv798
 */
public interface ISqlModifyExecutor extends ISqlSelectExecutor {
	/**
	 * Запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * @return
	 * Количество модифицированных записей. Если количество не важно,
	 * следует использовать метод {@link #exec(String, boolean)}, чтобы избежать
	 * лишнего запроса к базе данных.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @throws InterruptedException
	 */
	int execUpdate(String sql, boolean keys) throws SqlExecutorException;
	
	/**
	 * Запрос на любую манипуляцию с данными.
	 * @return
	 * <code>true</code>, если результат запроса представляет собой
	 * набор данных; <code>false</code> - если количество модифицированных
	 * записей.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @throws SqlExecutorException
	 */
	boolean exec(String sql, boolean keys) throws SqlExecutorException;
	
	/**
	 * Параметризированный запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * @return
	 * Количество модифицированных записей. Если количество не важно,
	 * следует использовать метод {@link #execPrepared(String, boolean, Object[])},
	 * чтобы избежать лишнего запроса к базе данных.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @param params - значения параметров в порядке, указанном в запросе;
	 * @throws InterruptedException
	 */
	int execPreparedUpdate(String sql, boolean keys, Object... params) throws SqlExecutorException;
	
	/**
	 * Параметризированный запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * @return
	 * Количество модифицированных записей. Если количество не важно,
	 * следует использовать метод {@link #execPrepared(String, boolean, TBase, TFieldIdEnum[], Class[], int[])},
	 * чтобы избежать лишнего запроса к базе данных.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @param obj - объект thrift-структуры;
	 * @param fields - поля, содержащиеся в этом объекте;
	 * @param types - типы данных этих полей;
	 * @param indexes - индексы полей в порядке, указанном в запросе;
	 * @throws SqlExecutorException
	 */
	<T extends TBase<?, F>, F extends TFieldIdEnum> int execPreparedUpdate(String sql, boolean keys, T obj, F[] fields, Class<?>[] types, int... indexes) throws SqlExecutorException;
	
	/**
	 * Параметризированный запрос на любую манипуляцию с данными.
	 * @return
	 * <code>true</code>, если результат запроса представляет собой
	 * набор данных; <code>false</code> - если количество модифицированных
	 * записей.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @param params - значения параметров в порядке, указанном в запросе;
	 * @throws SqlExecutorException
	 */
	boolean execPrepared(String sql, boolean keys, Object... params) throws SqlExecutorException;
	
	/**
	 * Параметризированный запрос на модификацию данных (INSERT, DELETE, UPDATE или DDL).
	 * @return
	 * <code>true</code>, если результат запроса представляет собой
	 * набор данных; <code>false</code> - если количество модифицированных
	 * записей.
	 * @param sql - текст sql-запроса;
	 * @param keys - указывает, нужно ли возвращать набор сгенерированных ключей;
	 * @param obj - объект thrift-структуры;
	 * @param fields - поля, содержащиеся в этом объекте;
	 * @param types - типы данных этих полей;
	 * @param indexes - индексы полей в порядке, указанном в запросе;
	 * @throws SqlExecutorException
	 */
	<T extends TBase<?, F>, F extends TFieldIdEnum> boolean execPrepared(String sql, boolean keys, T obj, F[] fields, Class<?>[] types, int... indexes) throws SqlExecutorException;
	
	/**
	 * Возвращает набор данных, содержащий сгенерированные в результате выполнения
	 * последнего запроса ключи. Полезно использовать, например, для получения
	 * автоматически сгенерированного индекса при вставке записи.
	 * @return
	 * @throws SqlExecutorException
	 */
	ResultSet getGeneratedKeys() throws SqlExecutorException;
	
	/**
	 * Подтверждает транзакцию.
	 * @throws SqlExecutorException 
	 */
	void commitTransaction() throws SqlExecutorException;
	
	/**
	 * Откатывает транзакцию.
	 * @throws SqlExecutorException 
	 */
	void rollbackTransaction();
	
}

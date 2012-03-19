package ru.nkz.ivcgzo.serverManager.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;

/**
 * Определяет методы для выполнения запросов на чтение данных
 * и работы с подключениями.
 * @author bsv798
 */
public interface ISqlSelectExecutor {
	/**
	 * Выполняет запрос на чтение и возвращает набор данных. Набор
	 * данных следует закрывать сразу после использования методом
	 * <code>CloseStatement</code>. 
	 * @param sql - текст sql-запроса;
	 * @throws SqlExecutorException
	 */
	AutoCloseableResultSet execQuery(String sql) throws SqlExecutorException;
	
	/**
	 * Выполняет параметризированный запрос на чтение и возвращает набор данных.
	 * Набор данных следует закрывать сразу после использования методом
	 * <code>CloseStatement</code>. 
	 * @param sql - текст sql-запроса;
	 * @param params - значения параметров в порядке, указанном в запросе;
	 * @throws SqlExecutorException
	 */
	AutoCloseableResultSet execPreparedQuery(String sql, Object... params) throws SqlExecutorException;
	
	/**
	 * Выполняет параметризированный запрос на чтение и возвращает набор данных.
	 * Набор данных следует закрывать сразу после использования методом
	 * <code>CloseStatement</code>. 
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
	<T extends TBase<?, F>, F extends TFieldIdEnum> AutoCloseableResultSet execPreparedQuery(String sql, T obj, F[] fields, Class<?>[] types, int... indexes) throws SqlExecutorException;
	
	/**
	 * Закрывает запрос и его набор данных.
	 */
	void closeStatement(Statement stm);
	
	/**
	 * Закрывает набор данных и его запрос.
	 */
	void closeStatement(ResultSet rs);
	
	/**
	 * Закрывает подключение к базе данных. Вызывается при закрытии
	 * менеджера плагинов-серверов.
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException;
	
}

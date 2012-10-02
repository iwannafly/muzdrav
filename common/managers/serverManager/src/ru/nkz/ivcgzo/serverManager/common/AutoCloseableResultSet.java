package ru.nkz.ivcgzo.serverManager.common;

import java.sql.ResultSet;

/**
 * Класс, реализующий автоматическое закрытие набора данных.
 * @author bsv798
 */
public class AutoCloseableResultSet implements AutoCloseable {
	ISqlSelectExecutor sse;
	ResultSet rs;
	
	protected AutoCloseableResultSet(ISqlSelectExecutor sse, ResultSet rs) {
		this.sse = sse;
		this.rs = rs;
	}
	
	public ResultSet getResultSet() {
		return rs;
	}
	
	public ResultSet setResultSet(ResultSet rs) {
		this.rs = rs;
		return rs;
	}
	
	@Override
	public void close() {
		sse.closeStatement(rs);
	}

}

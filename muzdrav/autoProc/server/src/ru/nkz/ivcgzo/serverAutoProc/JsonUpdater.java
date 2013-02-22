package ru.nkz.ivcgzo.serverAutoProc;

import java.io.IOException;

import org.json.JSONObject;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;

public abstract class JsonUpdater extends Thread implements Runnable {
	protected JsonHttpTransport jtr;
	protected ISqlSelectExecutor sse;
	protected ITransactedSqlExecutor tse;
	
	public JsonUpdater(JsonHttpTransport jtr, ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.jtr = jtr;
		this.sse = sse;
		this.tse = tse;
	}
	
	public void scheduleOperation(int addrType, String text, int npasp, String comm) throws Exception {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("INSERT INTO scheduled (type, int, text, comm) VALUES (?, ?, ?, ?)", false, addrType, npasp, text, comm);
			sme.setCommit();
		}
	}
	
	public void unscheduleOperation(int operId) {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM scheduled WHERE id = ?", false, operId);
			sme.setCommit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO Запись в таблицу отложенных операций происходит только в случае ошибок http. Писать ли туда ошибки, возвращаемые с сервиса?
	public JSONObject sendJson(int addrType, String addr, String text, int i) throws Exception {
		try {
			JSONObject resp = new JSONObject(jtr.sendPostRequest(addr, text));
			if (resp.getBoolean("IsSuccessful"))
				return resp;
			new Exception(String.format("Responce from server: %s", resp.getString("Error"))).printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				scheduleOperation(addrType, text, i, e.getMessage());
			} catch (Exception e1) {
				throw new Exception(String.format("Can't schedule operation %d.", addrType), e);
			}
		}
		return null;
	}
}

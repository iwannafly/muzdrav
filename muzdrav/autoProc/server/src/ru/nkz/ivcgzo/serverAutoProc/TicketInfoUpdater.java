package ru.nkz.ivcgzo.serverAutoProc;

import java.sql.SQLException;

import org.json.JSONException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;

public class TicketInfoUpdater extends JsonUpdater {
	private static final String vr42erWorkAddr = "http://vrach42.ru/Services/er/mis";
	private static final String vr42erTestAddr = "http://vrach42.ru/DemoServices/er/mis";
	
	public TicketInfoUpdater(JsonHttpTransport jtr, ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(jtr, sse, tse);
	}
	
	@Override
	public void run() {
		while (true) {
			try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id, text, int FROM scheduled WHERE type = 1 LIMIT 100 ")) {
				if (acrs.getResultSet().next()) {
					do {
						sendJson(acrs.getResultSet().getString(2), acrs.getResultSet().getInt(3));
						unscheduleOperation(acrs.getResultSet().getInt(1));
						sleep(1000);
					} while (acrs.getResultSet().next());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	private void sendJson(String text, int etalonId) throws Exception {
		sendJson(1, (ServerAutoProc.DEBUG) ? vr42erTestAddr : vr42erWorkAddr, text, etalonId);
	}
	
	public synchronized void sendReceptionTicketToVr42(int etalonId, int operId) throws Exception {
		if (ServerAutoProc.DEBUG)
			return;
		
		String text;
		
		try {
			text = new JsonTicketInfo(sse, etalonId, operId).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception("Can't make json for add reception ticket.", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Can't query db to make json for add reception ticket.", e);
		}
		
		sendJson(text, etalonId);
	}
	
}

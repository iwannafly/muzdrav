package ru.nkz.ivcgzo.serverAutoProc;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;

public class PatientInfoUpdater extends JsonUpdater {
	private static final String vr42iemkWorkAddr = "http://test.kuzdrav.ru/iemk/api/patient/register";
	private static final String vr42iemkTestAddr = "http://test.kuzdrav.ru/iemk/api/patient/register";
	
	public PatientInfoUpdater(JsonHttpTransport jtr, ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(jtr, sse, tse);
	}
	
	@Override
	public void run() {
		while (true) {
			try (AutoCloseableResultSet acrs = sse.execQuery("WITH t AS (SELECT int FROM scheduled WHERE type = 2) SELECT npasp FROM patient WHERE npasp NOT IN (SELECT * FROM t) AND vr42_id IS NULL LIMIT 100 ")) {
				if (acrs.getResultSet().next()) {
					do {
						sendPatientInfoToVr42(acrs.getResultSet().getInt(1));
						sleep(1000);
					} while (acrs.getResultSet().next());
				} else {
					try (AutoCloseableResultSet acrs1 = sse.execQuery("SELECT id, text, int FROM scheduled WHERE type = 2 LIMIT 100 ")) {
						while (acrs.getResultSet().next()) {
							sendJson(acrs.getResultSet().getString(2), acrs.getResultSet().getInt(3));
							unscheduleOperation(acrs.getResultSet().getInt(1));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sleep(60 * 60 * 1000);
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
	
	private void sendJson(String text, int npasp) throws Exception {
		JSONObject resp = sendJson(2, (ServerAutoProc.DEBUG) ? vr42iemkTestAddr : vr42iemkWorkAddr, text, npasp);
		
		if (resp != null) {
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("UPDATE patient SET vr42_id = ?::uuid WHERE npasp = ? ", false, resp.getString("PatientId"), npasp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendPatientInfoToVr42(int npasp) throws Exception {
		if (ServerAutoProc.DEBUG)
			return;
		
		String text;
		
		try {
			text = new JsonPatientInfo(sse, npasp).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception("Can't make json for patient info.", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Can't query db to make json for patient info.", e);
		}
		
		sendJson(text, npasp);
	}
	
}

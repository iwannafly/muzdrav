package ru.nkz.ivcgzo.serverAutoProc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;

public class JsonPatientInfo extends JSONObject {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public JsonPatientInfo(ISqlSelectExecutor sse, int npasp) throws JSONException, SQLException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT datar AS birth_date, ot AS father_name, im AS first_name, (CASE WHEN pol = 1 THEN 0 WHEN pol = 2 THEN 1 ELSE NULL END) AS gender, fam AS last_name, docser || docnum AS passport, poms_ser || poms_nom AS polis, snils AS snils FROM patient WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				fillFields(acrs.getResultSet());
			else
				throw new SQLException(String.format("Patient with id %d not found.", npasp));
		}
	}
	
	private void fillFields(ResultSet rs) throws JSONException, SQLException {
		this.put("BirthDate", sdf.format(rs.getDate("birth_date")));
		this.put("FatherName", rs.getString("father_name"));
		this.put("FirstName", rs.getString("first_name"));
		this.put("Gender", rs.getInt("gender"));
		this.put("LastName", rs.getString("last_name"));
		this.put("Passport", rs.getString("passport"));
		this.put("Polis", rs.getString("polis"));
		this.put("Snils", rs.getString("snils"));
	}
}

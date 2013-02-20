package ru.nkz.ivcgzo.serverAutoProc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;

public class JsonTicketInfo extends JSONObject {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat stf = new SimpleDateFormat("'PT'HH'H'mm'M'");
	
	public JsonTicketInfo(ISqlSelectExecutor sse, int etalonId, int operId) throws JSONException, SQLException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT n.kem_token AS auth_token, pcod_sp AS doctor_id, v.fam || ' ' || v.im || ' ' || v.ot AS doctor_name, p.docser || p.docnum AS document_number14, p.poms_ser || p.poms_nom AS document_number19, p.snils AS document_number20, p.datar AS patient_birth_date, p.ot AS patient_father_name, p.im AS patient_first_name, p.npasp AS patient_id, p.fam AS patient_last_name, t.cdol AS speciality_id, s.name AS speciality_name, t.datap AS ticket_date, t.id AS ticket_id, t.timep AS ticket_time FROM e_talon t JOIN n_n00 n ON (n.pcod = t.cpol) JOIN s_vrach v ON (v.pcod = t.pcod_sp) JOIN patient p ON (p.npasp = t.npasp) JOIN n_s00 s ON (s.pcod = t.cdol) WHERE t.id = ? ", etalonId)) {
			if (acrs.getResultSet().next())
				fillFields(acrs.getResultSet(), operId);
			else
				throw new SQLException(String.format("Talon with id %d not found.", etalonId));
		}
	}
	
	private void fillFields(ResultSet rs, int operId) throws JSONException, SQLException {
		this.put("AuthToken", rs.getString("auth_token"));
		this.put("DoctorId", rs.getInt("doctor_id"));
		this.put("DoctorName", rs.getString("doctor_name"));
		this.put("OperationCode", operId);
		this.put("PatientBirthDate", sdf.format(rs.getDate("patient_birth_date")));
		this.put("PatientFatherName", rs.getString("patient_father_name"));
		this.put("PatientFirstName", rs.getString("patient_first_name"));
		this.put("PatientId", rs.getInt("patient_id"));
		this.put("PatientLastName", rs.getString("patient_last_name"));
		this.put("SpecialityId", rs.getString("speciality_id"));
		this.put("SpecialityName", rs.getString("speciality_name"));
		this.put("TicketDate", sdf.format(rs.getDate("ticket_date")));
		this.put("TicketId", rs.getInt("ticket_id"));
		this.put("TicketTime", stf.format(rs.getTime("ticket_time")));
		if (rs.getString("document_number14") != null) {
			this.put("DocumentType", 14);
			this.put("DocumentNumber", rs.getString("document_number14"));
		} else if (rs.getString("document_number19") != null) {
			this.put("DocumentType", 19);
			this.put("DocumentNumber", rs.getString("document_number19"));
		} else if (rs.getString("document_number20") != null) {
			this.put("DocumentType", 20);
			this.put("DocumentNumber", rs.getString("document_number20"));
		}
	}
}

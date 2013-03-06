package ru.nkz.ivcgzo.serverHospital;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.TDiagnostic;
import ru.nkz.ivcgzo.thriftHospital.TMedication;

/**
 * Серверная часть учёта медицинских назначений: препаратов, 
 * исследований, режимов и лечебных процедур
 * @author Балабаев Никита Дмитриевич
 */
public final class ServerAssignments {

	protected ISqlSelectExecutor sse;
	protected ITransactedSqlExecutor tse;
	private TResultSetMapper<TMedication, TMedication._Fields> rsmMedication;
	private TResultSetMapper<TDiagnostic, TDiagnostic._Fields> rsmDiagnostic;
    private static final String[] MEDICATION_FIELD_NAMES = {
        "name", "nlek", "id_gosp", "vrach", "datan", "klek", "flek", "doza", "ed",
        "sposv", "spriem", "pereod", "datae", "komm", "datao", "vracho", "dataz",
        "id_kap", "id_inj", "opl", "diag", "ed_name", "sposv_name", "vrach_name",
        "vracho_name", "opl_name", "diag_name"
    };
    private static final String[] DIAGNOSTIC_FIELD_NAMES = {
        "nisl", "cpok", "cpok_name", "result", "datan", "datav", "op_name", "rez_name"
    };
	
	public ServerAssignments(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
		
        rsmMedication = new TResultSetMapper<>(TMedication.class, MEDICATION_FIELD_NAMES);
        rsmDiagnostic = new TResultSetMapper<>(TDiagnostic.class, DIAGNOSTIC_FIELD_NAMES);
	}
	
	public List<TMedication> getMedications(final int idGosp)
			throws KmiacServerException {
        String sqlQuery =
        		"SELECT n_med.name AS name, l.nlek, l.id_gosp, " +
	        		"l.vrach, l.datan, l.klek, l.flek, l.doza, l.ed, l.sposv, " +
	        		"l.spriem, l.pereod, l.datae, l.komm, l.datao, l.vracho, " +
	        		"l.dataz, l.id_kap, l.id_inj, l.opl, l.diag, " +
	        		"n_edd.name AS ed_name, n_svl.name AS sposv_name, " +
	        		"(v.fam || ' ' || v.im || ' ' || v.ot) AS vrach_name, " +
	        		"(vo.fam || ' ' || vo.im || ' ' || vo.ot) AS vracho_name, " +
	        		"n_opl1.name AS opl_name, n_c00.name AS diag_name " +
                "FROM c_lek l " +
	                "JOIN n_med ON (l.klek = n_med.pcod) " +
	                "JOIN s_vrach v ON (l.vrach = v.pcod) " +
	                "LEFT JOIN s_vrach vo ON (l.vracho = vo.pcod) " +
	                "LEFT JOIN n_edd ON (l.ed = n_edd.pcod) " +
	                "LEFT JOIN n_svl ON (l.sposv = n_svl.pcod) " +
	                "LEFT JOIN n_opl1 ON (l.opl = n_opl1.pcod) " +
	                "LEFT JOIN n_c00 ON (l.diag = n_c00.pcod) " +
                "WHERE (l.id_gosp = ?) AND (l.klek IS NOT NULL) " +
                "UNION " +
                "SELECT l.lek_name AS name, l.nlek, l.id_gosp, " +
	        		"l.vrach, l.datan, l.klek, l.flek, l.doza, l.ed, l.sposv, " +
	        		"l.spriem, l.pereod, l.datae, l.komm, l.datao, l.vracho, " +
	        		"l.dataz, l.id_kap, l.id_inj, l.opl, l.diag, " +
	                "n_edd.name AS ed_name, " +
	        		"n_svl.name AS sposv_name, " +
	        		"(v.fam || ' ' || v.im || ' ' || v.ot) AS vrach_name, " +
	        		"(vo.fam || ' ' || vo.im || ' ' || vo.ot) AS vracho_name, " +
	        		"n_opl1.name AS opl_name, n_c00.name AS diag_name " +
                "FROM c_lek l " +
	                "JOIN s_vrach v ON (l.vrach = v.pcod) " +
	                "LEFT JOIN s_vrach vo ON (l.vracho = vo.pcod) " +
	                "LEFT JOIN n_edd ON (l.ed = n_edd.pcod) " +
	                "LEFT JOIN n_svl ON (l.sposv = n_svl.pcod) " +
	                "LEFT JOIN n_opl1 ON (l.opl = n_opl1.pcod) " +
	                "LEFT JOIN n_c00 ON (l.diag = n_c00.pcod) " +
                "WHERE (l.id_gosp = ?) AND (l.klek IS NULL);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp, idGosp)) {
            return rsmMedication.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
        	ServerHospital.log.log(Level.ERROR, "Exception (getMedications): ", e);
            throw new KmiacServerException();
        }
	}

	public void updateMedication(final TMedication med) throws KmiacServerException {
        final String sqlQuery =
        		"UPDATE c_lek " +
        		"SET doza = ?, spriem = ?, pereod = ?, datao = ?, vracho = ? " +
        		"WHERE (nlek = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false,
            		(med.isSetDoza()) ? med.getDoza() : null,
            		(med.isSetSpriem()) ? med.getSpriem() : null,
            		(med.isSetPereod()) ? med.getPereod() : null,
            		(med.isSetDatao()) ? new Date(med.getDatao()) : null,
            		(med.isSetVracho()) ? med.getVracho() : null,
            		med.getNlek());
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
        	ServerHospital.log.log(Level.ERROR,
        			"SQLException | InterruptedException (updateMedication): ", e);
            throw new KmiacServerException();
        }
		
	}

	public void deleteMedication(final int nlek) throws KmiacServerException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM c_lek WHERE (nlek = ?);", false, nlek);
			sme.setCommit();
        } catch (SQLException | InterruptedException e) {
        	ServerHospital.log.log(Level.ERROR,
        			"SQLException | InterruptedException (deleteMedication): ", e);
            throw new KmiacServerException();
        }
	}

	public List<TDiagnostic> getDiagnostics(final int idGosp) throws KmiacServerException {
        String sqlQuery = 
		"SELECT DISTINCT isl.nisl, p_rez_l.cpok AS cpok, " +
			"n_ldi.name_n AS cpok_name, " +
			"p_rez_l.zpok AS result, isl.datan, isl.datav, " +
			"NULL AS op_name, NULL AS rez_name " +
		"FROM p_isl_ld isl " +
			"JOIN p_rez_l ON (p_rez_l.nisl = isl.nisl) " +
			"JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) " +
			"JOIN n_lds ON (n_lds.pcod = isl.kodotd) " +
		"WHERE (isl.id_gosp = ?) AND (n_lds.tip SIMILAR TO 'Д|Л') " +
		"UNION " +
		"SELECT DISTINCT isl.nisl, p_rez_d.kodisl AS cpok, " +
			"n_ldi.name_n AS cpok_name, " +
			"n_arez.name AS result, isl.datan, isl.datav, " +
			"p_rez_d.op_name, p_rez_d.rez_name " +
		"FROM p_isl_ld isl " +
			"JOIN p_rez_d ON (p_rez_d.nisl = isl.nisl) " +
			"JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) " +
			"JOIN n_lds ON (n_lds.pcod = isl.kodotd) " +
			"LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) " +
		"WHERE (isl.id_gosp = ?) AND (n_lds.tip SIMILAR TO 'Д|Л');";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp, idGosp)) {
        	List<TDiagnostic> list2 = rsmDiagnostic.mapToList(acrs.getResultSet());
            return list2;
        } catch (SQLException e) {
        	ServerHospital.log.log(Level.ERROR, "Exception (getDiagnostics): ", e);
            throw new KmiacServerException();
        }
	}
}

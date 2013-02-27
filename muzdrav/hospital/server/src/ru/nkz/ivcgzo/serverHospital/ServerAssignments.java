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
        "ed_name", "sposv_name", "vrach_name", "vracho_name"
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
        String sqlQuery = "SELECT n_med.name AS name, c_lek.*, n_edd.name AS ed_name, " +
        		"n_svl.name AS sposv_name, (v.fam || ' ' || v.im || ' ' || v.ot) AS vrach_name, " +
        		"(vo.fam || ' ' || vo.im || ' ' || vo.ot) AS vracho_name " +
                "FROM c_lek " +
                "JOIN n_med ON (c_lek.klek = n_med.pcod) " +
                "JOIN s_vrach v ON (c_lek.vrach = v.pcod) " +
                "LEFT JOIN s_vrach vo ON (c_lek.vracho = vo.pcod) " +
                "LEFT JOIN n_edd ON (c_lek.ed = n_edd.pcod) " +
                "LEFT JOIN n_svl ON (c_lek.sposv = n_svl.pcod) " +
                "WHERE (c_lek.id_gosp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            return rsmMedication.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
        	ServerHospital.log.log(Level.ERROR, "Exception (getMedications): ", e);
            throw new KmiacServerException();
        }
	}

	public void updateMedication(final TMedication med) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_lek " +
        						"SET doza = ?, spriem = ?, pereod = ?, datao = ?, " +
        						"vracho = ? " +
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
        	ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (updateMedication): ", e);
            throw new KmiacServerException();
        }
		
	}

	public void deleteMedication(final int nlek) throws KmiacServerException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM c_lek WHERE (nlek = ?);", false, nlek);
			sme.setCommit();
        } catch (SQLException | InterruptedException e) {
        	ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (deleteMedication): ", e);
            throw new KmiacServerException();
        }
	}

	public List<TDiagnostic> getDiagnostics(int idGosp) throws KmiacServerException {
        String sqlQuery = 
		"SELECT DISTINCT p_isl_ld.nisl, p_rez_l.cpok AS cpok, n_ldi.name_n AS cpok_name, " +
			"p_rez_l.zpok AS result, p_isl_ld.datan, p_isl_ld.datav, '' AS op_name, '' AS rez_name " +
		"FROM p_isl_ld " +
		"JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) " +
		"JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) " +
		"WHERE (p_isl_ld.id_gosp = ?) " +
		"UNION " +
		"SELECT DISTINCT p_isl_ld.nisl, p_rez_d.kodisl AS cpok, n_ldi.name_n AS cpok_name, " +
			"n_arez.name AS result, p_isl_ld.datan, p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name " +
		"FROM p_isl_ld " +
		"JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) " +
		"JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) " +
		"LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) " +
		"WHERE (p_isl_ld.id_gosp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp, idGosp)) {
            return rsmDiagnostic.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
        	ServerHospital.log.log(Level.ERROR, "Exception (getDiagnostics): ", e);
            throw new KmiacServerException();
        }
	}
}

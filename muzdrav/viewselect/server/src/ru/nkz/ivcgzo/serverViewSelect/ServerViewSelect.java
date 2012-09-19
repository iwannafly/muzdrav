package ru.nkz.ivcgzo.serverViewSelect;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.PatientAnamZabInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientDiagAmbInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientDiagZInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientIsslInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientNaprInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientPriemInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSearchParams;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSignInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientVizitAmbInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientVizitInfo;
import ru.nkz.ivcgzo.thriftViewSelect.RdSlInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect.Iface;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mrab_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_0;

public class ServerViewSelect extends Server implements Iface {
	private TServer thrServ;
	private final ClassifierManager ccm;
	private final TResultSetMapper<PatientBriefInfo, PatientBriefInfo._Fields> rsmPatBrief;
	private final TResultSetMapper<PatientCommonInfo, PatientCommonInfo._Fields> rsmPatComInfo;
	private final TResultSetMapper<PatientSignInfo, PatientSignInfo._Fields> rsmPsign;
	private final TResultSetMapper<PatientVizitInfo, PatientVizitInfo._Fields> rsmPvizit;
	private final TResultSetMapper<RdSlInfo, RdSlInfo._Fields> rsmRdSl;
	private final TResultSetMapper<PatientDiagZInfo, PatientDiagZInfo._Fields> rsmPdiagZ;
	private final TResultSetMapper<PatientVizitAmbInfo, PatientVizitAmbInfo._Fields> rsmPvizitAmb;
	private final TResultSetMapper<PatientPriemInfo, PatientPriemInfo._Fields> rsmPriem;
	private final TResultSetMapper<PatientNaprInfo, PatientNaprInfo._Fields> rsmPnapr;
	private final TResultSetMapper<PatientDiagAmbInfo, PatientDiagAmbInfo._Fields> rsmPdiagAmb;
	private final TResultSetMapper<PatientIsslInfo, PatientIsslInfo._Fields> rsmIsslInfo;
	private final TResultSetMapper<PatientAnamZabInfo, PatientAnamZabInfo._Fields> rsmAnamZab;

	public ServerViewSelect(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmPatBrief = new TResultSetMapper<>(PatientBriefInfo.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom");
		rsmPatComInfo = new TResultSetMapper<>(PatientCommonInfo.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom", "pol", "jitel", "sgrp", "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_korp", "adp_kv", "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_korp", "adm_kv", "name_mr", "ncex", "poms_strg", "poms_tdoc", "poms_ndog", "pdms_strg", "pdms_ser", "pdms_nom", "pdms_ndog", "cpol_pr", "terp", "datapr", "tdoc", "docser", "docnum", "datadoc", "odoc", "snils", "dataz", "prof", "tel", "dsv", "prizn", "ter_liv", "region_liv", "mrab");
		rsmPsign = new TResultSetMapper<>(PatientSignInfo.class, "npasp", "grup", "ph", "allerg", "farmkol", "vitae", "vred");
		rsmPvizit = new TResultSetMapper<>(PatientVizitInfo.class, "id", "npasp", "cpol", "cobr", "datao", "ishod", "rezult", "talon", "cod_sp", "cdol", "cuser", "zakl", "dataz", "recomend","lech");
		rsmRdSl = new TResultSetMapper<>(RdSlInfo.class, "id", "npasp", "datay", "dataosl", "abort", "shet", "datam", "yavka1", "ishod", "datasn", "datazs", "kolrod", "deti", "kont", "vesd", "dsp", "dsr", "dtroch", "cext", "indsol", "prmen", "dataz", "datasert", "nsert", "ssert", "oslab", "plrod", "prrod", "vozmen", "oslrod", "polj", "dataab", "srokab", "cdiagt", "cvera", "id_pvizit", "rost");
		rsmPdiagZ = new TResultSetMapper<>(PatientDiagZInfo.class, "id", "id_diag_amb", "npasp", "diag", "cpodr", "d_vz", "d_grup", "ishod", "dataish", "datag", "datad", "diag_s", "d_grup_s", "cod_sp", "cdol_ot", "nmvd", "xzab", "stady", "disp", "pat", "prizb", "prizi", "named", "fio_vr");
		rsmPvizitAmb = new TResultSetMapper<>(PatientVizitAmbInfo.class, "id", "id_obr", "npasp", "datap", "cod_sp", "cdol", "diag", "mobs", "rezult", "opl", "stoim", "uet", "datak", "kod_rez", "k_lr", "n_sp", "pr_opl", "pl_extr", "vpom", "fio_vr", "dataz");
		rsmPriem = new TResultSetMapper<>(PatientPriemInfo.class, "id_obr","npasp", "id_pos", "sl_ob", "n_is", "n_kons", "n_proc", "n_lek", "t_chss", "t_temp", "t_ad", "t_rost", "t_ves", "t_st_localis", "t_ocenka", "t_jalob", "t_status_praesense", "t_fiz_obsl");
		rsmPnapr = new TResultSetMapper<>(PatientNaprInfo.class, "id", "idpvizit", "vid_doc", "text", "preds", "zaved", "name");
		rsmPdiagAmb = new TResultSetMapper<>(PatientDiagAmbInfo.class, "id", "id_obr", "npasp", "diag", "named", "diag_stat", "predv", "datad", "obstreg", "cod_sp", "cdol", "datap", "dataot", "obstot", "cod_spot", "cdol_ot", "vid_tr");
		rsmIsslInfo = new TResultSetMapper<>(PatientIsslInfo.class, "nisl", "cp0e1", "np0e1", "cldi", "nldi", "zpok", "datav");
		rsmAnamZab = new TResultSetMapper<>(PatientAnamZabInfo.class, "id_pvizit", "npasp", "t_ist_zab");
		
		ccm = new ClassifierManager(sse);
	}

	@Override
	public void testConnection() throws TException {
		
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		
	}

    @Override
    public void start() throws Exception {
        ThriftViewSelect.Processor<Iface> proc = new ThriftViewSelect.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        thrServ.serve();
    }

    @Override
    public void stop() {
        if (thrServ != null)
            thrServ.stop();
    }

	@Override
	public List<IntegerClassifier> getVSIntegerClassifierView(String className) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM " + className;
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIVS =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmIVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}
	
	@Override
	public List<StringClassifier> getVSStringClassifierView(String className) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM " + className;
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmSVS =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmSVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}
	
	@Override
	public boolean isClassifierEditable(String className) throws TException {
		if (className.charAt(0)=='c')
			return true;
		else return false;
	}

	@Override
	public boolean isClassifierPcodInteger(String className) throws TException {
		String sqlQueryGetType = "SELECT data_type FROM information_schema.columns where table_name = ? AND column_name = ?";
		try (AutoCloseableResultSet arcs = sse.execPreparedQuery(sqlQueryGetType, className, "pcod")) {
			if (arcs.getResultSet().next()){
				if (arcs.getResultSet().getString(1).equals("integer"))
					return true;
				else return false;
			} else {
				throw new TException();
			}				
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	///////////////////////// patient search ///////////////////////////////
	
	@Override
	public List<PatientBriefInfo> searchPatient(PatientSearchParams prms) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom FROM patient " + getSearchPatientWhereClause(prms), getSearchPatientParamArray(prms))) {
			return rsmPatBrief.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}
	
	private String getSearchPatientWhereClause(PatientSearchParams prms) {
		String clause = "WHERE ";
		String condStringFormat = ((prms.illegibleSearch) ? "(%s SIMILAR TO ?) " : "(%s = ?) ") + "AND ";
		String condDateFormat = ((prms.illegibleSearch) ? "(%s BETWEEN ? AND ?) " : "(%s = ?) ") + "AND ";
		final int andLen = 4;
		
		if (prms.isSetFam())
			clause += String.format(condStringFormat, "fam");
		if (prms.isSetIm())
			clause += String.format(condStringFormat, "im");
		if (prms.isSetOt())
			clause += String.format(condStringFormat, "ot", prms.getOt());
		if ((!prms.illegibleSearch && prms.isSetDatar()) || (prms.isSetDatar() && prms.isSetDatar2()))
			clause += String.format(condDateFormat, "datar");
		if (prms.isSetSpolis())
			clause += String.format(condStringFormat, "poms_ser");
		if (prms.isSetNpolis())
			clause += String.format(condStringFormat, "poms_nom");
		
		return clause.substring(0, clause.length() - andLen);
	}
	
	private Object[] getSearchPatientParamArray(PatientSearchParams prms) {
		List<Object> list = new ArrayList<>(PatientSearchParams.metaDataMap.size());
		
		if (prms.isSetFam())
			list.add(prms.getFam());
		if (prms.isSetIm())
			list.add(prms.getIm());
		if (prms.isSetOt())
			list.add(prms.getOt());
		if (!prms.illegibleSearch && prms.isSetDatar()) {
			list.add(new Date(prms.getDatar()));
		} else if (prms.isSetDatar() && prms.isSetDatar2()) {
			list.add(new Date(prms.getDatar()));
			list.add(new Date(prms.getDatar2()));
		}
		if (prms.isSetSpolis())
			list.add(prms.getSpolis());
		if (prms.isSetNpolis())
			list.add(prms.getNpolis());
		
		return list.toArray();
	}

	///////////////////////// classifiers ///////////////////////////////
	
	@Override
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls) throws KmiacServerException {
		return ccm.getIntegerClassifier(cls);
	}

	@Override
	public List<IntegerClassifier> getIntegerClassifierSorted(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		return ccm.getIntegerClassifier(cls, ord, fld);
	}

	@Override
	public List<StringClassifier> getStringClassifier(StringClassifiers cls) throws KmiacServerException {
		return ccm.getStringClassifier(cls);
	}

	@Override
	public List<StringClassifier> getStringClassifierSorted(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		return ccm.getStringClassifier(cls, ord, fld);
	}

	@Override
	public List<mkb_0> getMkb_0() throws KmiacServerException {
		return ccm.getMkbTreeClassifier();
	}

	@Override
	public List<polp_0> getPolp_0() throws KmiacServerException {
		return ccm.getPolpTreeClassifier();
	}
	
	@Override
	public List<mrab_0> getMrab_0() throws KmiacServerException {
		return ccm.getMrabTreeClassifier();
	}
	
	///////////////////////// patient info ///////////////////////////////
	
	@Override
	public PatientCommonInfo getPatientCommonInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM patient WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPatComInfo.map(acrs.getResultSet());
			else
				throw new SQLException("Patient common info not found");
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public PatientSignInfo getPatientSignInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_sign WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPsign.map(acrs.getResultSet());
			else
				throw new SQLException("Patient sign info not found");
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientVizitInfo> getPatientVizitInfoList(int npasp, long datan, long datak) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_vizit WHERE npasp = ? AND datao BETWEEN ? AND ? ", npasp, new Date(datan), new Date(datak))) {
			return rsmPvizit.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<RdSlInfo> getRdSlInfoList(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where npasp = ? ", npasp)) {
			return rsmRdSl.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientDiagZInfo> getPatientDiagZInfoList(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT p_diag.*,s_vrach.fam||' '||s_vrach.im||' '||s_vrach.ot as fio_vr FROM p_diag JOIN s_vrach ON (p_diag.cod_sp=s_vrach.pcod) WHERE npasp = ? ", npasp)) {
			return rsmPdiagZ.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientVizitAmbInfo> getPatientVizitAmbInfoList(int pvizitId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT pva.*, get_short_fio(svr.fam, svr.im, svr.ot) AS fio_vr FROM p_vizit_amb pva JOIN s_vrach svr ON (svr.pcod = pva.cod_sp) WHERE id_obr = ? ORDER BY pva.datap", pvizitId)) {
			return rsmPvizitAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public PatientPriemInfo getPatientPriemInfo(int npasp, int pvizitAmbId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_priem WHERE npasp = ? AND id_pos = ? ", npasp, pvizitAmbId)) {
			if (acrs.getResultSet().next())
				return rsmPriem.map(acrs.getResultSet());
			else
				throw new SQLException("Patient priem info not found");
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientNaprInfo> getPatientNaprInfoList(int pvizitId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_napr join n_vr_doc on(p_napr.vid_doc=n_vr_doc.pcod) where p_napr.id_pvizit = ?", pvizitId)) {
			return rsmPnapr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientDiagAmbInfo> getPatientDiagAmbInfoList(int pvizitId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_obr = ? ", pvizitId)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientIsslInfo> getPatientIsslInfoList(int pvizitId) throws KmiacServerException, TException {
		String sql = "SELECT p_isl_ld.nisl, n_ldi.pcod AS cldi, n_ldi.name_n AS nldi, p_rez_l.zpok, p_isl_ld.datav " +
			     "FROM p_isl_ld JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) " + 
				 "WHERE p_isl_ld.pvizit_id = ? " +
				 "UNION " +
				 "SELECT p_isl_ld.nisl, n_ldi.pcod AS cldi, n_ldi.name_n AS nldi, n_arez.name, p_isl_ld.datav " + 
				 "FROM p_isl_ld JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) " + 
				 "LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) " +
				 "WHERE p_isl_ld.pvizit_id = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, pvizitId, pvizitId)) {
			return rsmIsslInfo.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public PatientAnamZabInfo getPatientAnamZabInfo(int pvizitId, int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_anam_zab WHERE id_pvizit = ? AND npasp = ? ", pvizitId, npasp)) {
			if (acrs.getResultSet().next())
				return rsmAnamZab.map(acrs.getResultSet());
			else
				throw new SQLException("Patient anamnesis not found");
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}
}

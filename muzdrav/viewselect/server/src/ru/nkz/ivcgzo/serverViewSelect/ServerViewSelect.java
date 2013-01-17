package ru.nkz.ivcgzo.serverViewSelect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverLab.ServerLab;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.serverMedication.ServerMedication;
import ru.nkz.ivcgzo.serverOperation.ServerOperation;
import ru.nkz.ivcgzo.serverReception.ServerReception;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.C_etapInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CdepicrisInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CdiagInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CgospInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CizmerInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ClekInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CosmotrInfo;
import ru.nkz.ivcgzo.thriftViewSelect.CotdInfo;
import ru.nkz.ivcgzo.thriftViewSelect.MedPolErrorInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PaspErrorInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientAnamZabInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientAnamnez;
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
import ru.nkz.ivcgzo.thriftViewSelect.Pbol;
import ru.nkz.ivcgzo.thriftViewSelect.RdSlInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect.Iface;
import ru.nkz.ivcgzo.thriftViewSelect.TipPodrNotFoundException;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mrab_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_0;

public class ServerViewSelect extends Server implements Iface {
	private TServer thrServ;
	private Server labServ;
	private Server recServ;
	private Server medServ;
	private Server operationServ;
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
	private final TResultSetMapper<CgospInfo, CgospInfo._Fields> rsmCgosp;
	private final TResultSetMapper<CdepicrisInfo, CdepicrisInfo._Fields> rsmCdepicris;
	private final TResultSetMapper<CdiagInfo, CdiagInfo._Fields> rsmCdiag;
	private final TResultSetMapper<C_etapInfo, C_etapInfo._Fields> rsmC_etap;
	private final TResultSetMapper<CizmerInfo, CizmerInfo._Fields> rsmCizmer;
	private final TResultSetMapper<ClekInfo, ClekInfo._Fields> rsmClek;
	private final TResultSetMapper<CosmotrInfo, CosmotrInfo._Fields> rsmCosmotr;
	private final TResultSetMapper<CotdInfo, CotdInfo._Fields> rsmCotd;
	private final TResultSetMapper<PaspErrorInfo, PaspErrorInfo._Fields> rsmPaspError;
	private final TResultSetMapper<MedPolErrorInfo, MedPolErrorInfo._Fields> rsmMedPolError;
    private final TResultSetMapper<PatientAnamnez, PatientAnamnez._Fields> rsmAnam;
	private final TResultSetMapper<Pbol, Pbol._Fields> rsmPbol;
	private final Class<?>[] pbolTypes; 

	public ServerViewSelect(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmPatBrief = new TResultSetMapper<>(PatientBriefInfo.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom");
		rsmPatComInfo = new TResultSetMapper<>(PatientCommonInfo.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom", "pol", "jitel", "sgrp", "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_korp", "adp_kv", "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_korp", "adm_kv", "name_mr", "ncex", "poms_strg", "poms_tdoc", "poms_ndog", "pdms_strg", "pdms_ser", "pdms_nom", "pdms_ndog", "cpol_pr", "terp", "datapr", "tdoc", "docser", "docnum", "datadoc", "odoc", "snils", "dataz", "prof", "tel", "dsv", "prizn", "ter_liv", "region_liv", "mrab");
		rsmPsign = new TResultSetMapper<>(PatientSignInfo.class, "npasp", "datap", "numstr", "name", "vybor", "comment", "yn");
		rsmPvizit = new TResultSetMapper<>(PatientVizitInfo.class, "id", "npasp", "cpol", "datao", "ishod", "rezult", "talon", "cod_sp", "cdol", "cuser", "zakl", "dataz", "recomend", "lech", "cobr");
		rsmRdSl = new TResultSetMapper<>(RdSlInfo.class, "id", "npasp", "datay", "dataosl", "abort", "shet", "datam", "yavka1", "ishod", "datasn", "datazs", "kolrod", "deti", "kont", "vesd", "dsp", "dsr", "dtroch", "cext", "indsol", "prmen", "dataz", "datasert", "nsert", "ssert", "oslab", "plrod", "prrod", "vozmen", "oslrod", "polj", "dataab", "srokab", "cdiagt", "cvera", "id_pvizit", "rost");
		rsmPdiagZ = new TResultSetMapper<>(PatientDiagZInfo.class, "id", "id_diag_amb", "npasp", "diag", "cpodr", "d_vz", "d_grup", "ishod", "dataish", "datag", "datad", "cod_sp", "cdol_ot", "nmvd", "xzab", "stady", "disp", "pat", "prizb", "prizi", "named", "fio_vr");
		rsmPvizitAmb = new TResultSetMapper<>(PatientVizitAmbInfo.class, "id", "id_obr", "npasp", "datap", "cod_sp", "cdol", "diag", "mobs", "rezult", "opl", "stoim", "uet", "datak", "kod_rez", "k_lr", "n_sp", "pr_opl", "pl_extr", "vpom", "fio_vr", "dataz", "cpos");
		rsmPriem = new TResultSetMapper<>(PatientPriemInfo.class, "id_obr","npasp", "id_pos", "sl_ob", "n_is", "n_kons", "n_proc", "n_lek", "t_chss", "t_temp", "t_ad", "t_rost", "t_ves", "t_st_localis", "t_ocenka", "t_jalob", "t_status_praesense", "t_fiz_obsl", "t_recom");
		rsmPnapr = new TResultSetMapper<>(PatientNaprInfo.class, "id", "idpvizit", "vid_doc", "text", "preds", "zaved", "name");
		rsmPdiagAmb = new TResultSetMapper<>(PatientDiagAmbInfo.class, "id", "id_obr", "npasp", "diag", "named", "diag_stat", "predv", "datad", "obstreg", "cod_sp", "cdol", "dataot", "obstot", "cod_spot", "cdol_ot", "vid_tr");
		rsmIsslInfo = new TResultSetMapper<>(PatientIsslInfo.class, "nisl", "cp0e1", "np0e1", "cldi", "nldi", "zpok", "datav", "op_name", "rez_name", "gruppa");
		rsmAnamZab = new TResultSetMapper<>(PatientAnamZabInfo.class, "id_pvizit", "npasp", "t_ist_zab");
		rsmCgosp = new TResultSetMapper<>(CgospInfo.class, "id", "ngosp", "npasp", "nist", "datap", "vremp", "pl_extr", "naprav", "n_org", "cotd", "sv_time", "sv_day", "ntalon", "vidtr", "pr_out", "alkg", "meesr", "vid_tran", "diag_n", "diag_p", "named_n", "named_p", "nal_z", "nal_p", "t0c", "ad", "smp_data", "smp_time", "smp_num", "cotd_p", "datagos", "vremgos", "cuser", "dataosm", "vremosm", "kod_rez", "dataz", "jalob", "vid_st", "d_rez", "pr_ber");
		rsmCdepicris = new TResultSetMapper<>(CdepicrisInfo.class, "id", "id_gosp", "datas", "times", "dspat", "prizn", "named", "dataz");
		rsmCdiag = new TResultSetMapper<>(CdiagInfo.class, "id", "id_gosp", "cod", "med_op", "date_ustan", "prizn", "vrach");
		rsmC_etap = new TResultSetMapper<>(C_etapInfo.class, "id", "id_gosp", "stl", "mes", "date_start", "date_end");
		rsmCizmer= new TResultSetMapper<>(CizmerInfo.class, "id", "id_gosp", "temp", "ad", "chss", "rost", "ves", "dataz", "timez");
		rsmClek = new TResultSetMapper<>(ClekInfo.class, "id", "id_gosp", "vrach", "datan", "klek", "flek", "doza", "ed", "sposv", "spriem", "pereod", "dlitkl", "komm", "datao", "vracho", "dataz");
		rsmCosmotr = new TResultSetMapper<>(CosmotrInfo.class, "id", "id_gosp", "jalob", "morbi", "status_praesense", "status_localis", "fisical_obs", "pcod_vrach", "dataz", "timez");
		rsmCotd = new TResultSetMapper<>(CotdInfo.class, "id", "id_gosp", "nist", "sign", "cotd", "cprof", "stt", "dataol", "datazl", "vozrlbl", "pollbl", "ishod", "result", "ukl", "vrach", "npal", "datav", "vremv", "sostv", "recom", "mes", "dataz", "stat_type");
		rsmPaspError = new TResultSetMapper<>(PaspErrorInfo.class);
		rsmMedPolError = new TResultSetMapper<>(MedPolErrorInfo.class);
		rsmAnam = new TResultSetMapper<>(PatientAnamnez.class, "npasp", "datap", "numstr", "vybor", "comment", "name", "prof_anz");
		rsmPbol = new TResultSetMapper<>(Pbol.class, "id",          "id_obr",      "id_gosp",     "npasp",       "bol_l",       "s_bl", 	"po_bl",    "pol",         "vozr",        "nombl", 	    "cod_sp",      "cdol",       "pcod",        "dataz");
		pbolTypes = new Class<?>[] {                 Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class, Date.class};
		
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
    	new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					labServ = new ServerLab(sse, tse);
					labServ.start();
				} catch (Exception e) {
					System.err.println("Error starting lab server.");
					e.printStackTrace();
				}
			}
		}).start();
    	
    	new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recServ = new ServerReception(sse, tse);
					recServ.start();
				} catch (Exception e) {
					System.err.println("Error starting reception server.");
					e.printStackTrace();
				}
			}
		}).start();

    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    medServ = new ServerMedication(sse, tse);
                    medServ.start();
                } catch (Exception e) {
                    System.err.println("Error starting medication server.");
                    e.printStackTrace();
                }
            }
        }).start();

    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    operationServ = new ServerOperation(sse, tse);
                    operationServ.start();
                } catch (Exception e) {
                    System.err.println("Error starting operation server.");
                    e.printStackTrace();
                }
            }
        }).start();
    	
        ThriftViewSelect.Processor<Iface> proc = new ThriftViewSelect.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        thrServ.serve();
    }

    @Override
    public void stop() {
        if (thrServ != null)
            thrServ.stop();
        if (labServ != null)
        	labServ.stop();
        if (recServ != null)
        	recServ.stop();
        if (operationServ != null)
            operationServ.stop();
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
		String condStringFormat = ((prms.illegibleSearch) ? "(%s SIMILAR TO ?) " : "(%s LIKE ?) ") + "AND ";
		String condDateFormat = ((prms.illegibleSearch) ? "(%s BETWEEN ? AND ?) " : "(%s = ?) ") + "AND ";
		final int andLen = 4;
		
		if (prms.isSetNpasp()) {
			clause += "npasp = ? AND ";
		} else {
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
		}
		
		return clause.substring(0, clause.length() - andLen);
	}
	
	private Object[] getSearchPatientParamArray(PatientSearchParams prms) {
		List<Object> list = new ArrayList<>(PatientSearchParams.metaDataMap.size());
		
		if (prms.isSetNpasp()) {
			list.add(prms.getNpasp());
		} else {
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
		}
		
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
	public List<PatientSignInfo> getPatientSignInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_anamnez.npasp, p_anamnez.datap,p_anamnez.numstr,n_anz.name,p_anamnez.vybor,p_anamnez.comment,n_anz.yn from p_anamnez inner join n_anz on (n_anz.nstr=p_anamnez.numstr) where npasp=? order by numstr", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPsign.mapToList(acrs.getResultSet());
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_diag WHERE npasp = ? ", npasp)) {
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
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_pos = ? ", pvizitId)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PatientIsslInfo> getPatientIsslInfoList(int pvizitId) throws KmiacServerException, TException {
		String sql = "SELECT p_isl_ld.nisl, n_ldi.pcod AS cldi, n_ldi.name_n AS nldi, p_rez_l.zpok, p_isl_ld.datav, p_rez_l.pcod_m as op_name,n_nsikodrez.name as rez_name, n_p0e1.gruppa as gruppa " +
			     "FROM p_isl_ld JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) " + 
			     "JOIN n_nsikodrez ON (n_nsikodrez.kod_rez=p_rez_l.kod_rez) "+
				 "JOIN n_p0e1 on (n_ldi.c_p0e1=n_p0e1.pcod) "+
			     "WHERE p_isl_ld.pvizit_id = ? " +
				 "UNION " +
				 "SELECT p_isl_ld.nisl, n_ldi.pcod AS cldi, n_ldi.name_n AS nldi, n_arez.name, p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name, n_p0e1.gruppa as gruppa " + 
				 "FROM p_isl_ld JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) " + 
				 "LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) " +
				 "JOIN n_p0e1 on (n_ldi.c_p0e1=n_p0e1.pcod) "+
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

	@Override
	public List<CgospInfo> getCgospinfo(int npasp) throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_gosp where npasp = ? ", npasp)) {
			return rsmCgosp.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public CdepicrisInfo getCdepicrisInfo(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from c_depicris where id_gosp = ? ", id_gosp)) {
			if (acrs.getResultSet().next())
				return rsmCdepicris.map(acrs.getResultSet());
			else
				throw new SQLException("Depicris not found");
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<CdiagInfo> getCdiagInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_diag where id_gosp = ? ", id_gosp)) {
			return rsmCdiag.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}

	}

	@Override
	public List<C_etapInfo> getCEtapInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_etap where id_gosp = ? ", id_gosp)) {
			return rsmC_etap.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<CizmerInfo> getCizmerInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_izmer where id_gosp = ? ", id_gosp)) {
			return rsmCizmer.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<ClekInfo> getClekInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_lek where id_gosp = ? ", id_gosp)) {
			return rsmClek.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<CosmotrInfo> getCosmotrInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_osmotr where id_gosp = ? ", id_gosp)) {
			return rsmCosmotr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<CotdInfo> getCotdInfoList(int id_gosp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from c_otd where id_gosp = ? ", id_gosp)) {
			return rsmCotd.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException(e.getMessage());
		}
	}

	@Override
	public List<PaspErrorInfo> getPaspErrors(int cpodrz, long datazf, long datazt) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_reestr_pasp_errors(?, ?, ?) ", cpodrz, new Date(datazf), new Date(datazt));
				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.id, e.npasp, p.fam, p.im, p.ot, p.datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN patient p ON (p.npasp = e.npasp) WHERE (n.pasp_med = 1) AND (e.cpodr = ?) AND (e.dataz BETWEEN ? AND ?) ORDER BY p.fam, p.im, p.ot, n.kderr ", cpodrz, new Date(datazf), new Date(datazt))) {
			sme.setCommit();
			return rsmPaspError.mapToList(acrsq.getResultSet());
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not get pasp errors.");
		}
//		try (SqlModifyExecutor sme = tse.startTransaction();
//				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT r.*, e.name_err AS err_name, e.comm AS err_comm FROM get_reestr_pasp_errors(?, ?, ?) r JOIN n_kderr e ON (e.kderr = r.kderr) ORDER BY r.fam, r.im, r.ot, r.kderr ", cpodrz, new Date(datazf), new Date(datazt))) {
//			return rsmPaspError.mapToList(acrs.getResultSet());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new KmiacServerException("Could not get pasp errors.");
//		}

	}

	@Override
	public List<MedPolErrorInfo> getMedPolErrors(int cpodrz, long datazf, long datazt) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_reestr_med_pol_errors(?, ?, ?) ", cpodrz, new Date(datazf), new Date(datazt));
				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.id, e.sl_id AS id_obr, e.id_med AS id_pos, v.datao AS dat_obr, a.datap AS dat_pos, a.cod_sp AS vr_pcod, get_short_fio(r.fam, r.im, r.ot) AS vr_fio, a.cdol AS vr_cdol, s.name AS vr_cdol_name, e.npasp, get_short_fio(p.fam, p.im, p.ot) AS pat_fio, p.datar AS pat_datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN p_vizit v ON (v.id = e.sl_id) JOIN p_vizit_amb a ON (a.id = e.id_med AND a.id_obr = e.sl_id) JOIN s_vrach r ON (r.pcod = a.cod_sp) JOIN n_s00 s ON (a.cdol = s.pcod) JOIN patient p ON (p.npasp = e.npasp) WHERE (n.pasp_med = 2) AND (e.cpodr = ?) AND (a.datap BETWEEN ? AND ?) ORDER BY v.datao DESC, a.datap DESC, p.fam, p.im, p.ot, n.kderr ", cpodrz, new Date(datazf), new Date(datazt))) {
			sme.setCommit();
			return rsmMedPolError.mapToList(acrsq.getResultSet());
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not get med pol errors.");
		}
	}
	
	@Override
	public List<PatientAnamnez> getAnamnez(int npasp, int cslu, int cpodr) throws TipPodrNotFoundException, KmiacServerException, TException {
	    String sqlQuery = null;

	    if (cslu == 1){
			sqlQuery = "SELECT p.npasp, p.datap, n.nstr "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON (p.numstr = n.nstr and p.npasp = ?) " +
	                   "INNER JOIN n_o00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "ORDER BY n.nstr;";
		}
		if (cslu == 2){
			sqlQuery = "SELECT p.npasp, p.datap, n.nstr "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON (p.numstr = n.nstr and p.npasp = ?) " +
	                   "INNER JOIN n_n00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "ORDER BY n.nstr;";
		}
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp, cpodr)) {
            ResultSet rs = acrs.getResultSet();
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				while (rs.next()){
					if (rs.getInt("npasp") == 0){
						sme.execPrepared("INSERT INTO p_anamnez (npasp, numstr, datap) "+
	                    "VALUES (?, ?, ?);",  false, npasp, rs.getInt("nstr"), new Date(System.currentTimeMillis()));
					}
                }
				sme.setCommit();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new KmiacServerException();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				throw new KmiacServerException();
			}
        } catch (SQLException e) {
			e.printStackTrace();
            throw new KmiacServerException();
        }

		if (cslu == 1){
			sqlQuery = "SELECT p.npasp, p.datap, p.numstr, p.vybor, p.comment, n.name, ot.prof_anz "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON p.numstr = n.nstr " +
	                   "INNER JOIN n_o00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "WHERE p.npasp = ?"+
	                   "ORDER BY n.nstr;";
		}
		if (cslu == 2){
			sqlQuery = "SELECT p.npasp, p.datap, p.numstr, p.vybor, p.comment, n.name, ot.prof_anz "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON p.numstr = n.nstr " +
	                   "INNER JOIN n_n00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "WHERE p.npasp = ?"+
	                   "ORDER BY n.nstr;";
		}
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, npasp)) {
            ResultSet rs = acrs.getResultSet();
            List<PatientAnamnez> anamList = rsmAnam.mapToList(rs);
            if (anamList.size() > 0) {
                return anamList;
            } else {
                throw new TipPodrNotFoundException();
            }
        } catch (SQLException e) {
			e.printStackTrace();
           throw new KmiacServerException();
        }
	}
	
	@Override
	public void updateAnam(List<PatientAnamnez> patAnam) throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (PatientAnamnez elemAnam : patAnam) {
            	sme.execPrepared("UPDATE p_anamnez SET vybor = ?, comment = ? WHERE npasp = ? and numstr = ?;",
            			false, elemAnam.vybor, elemAnam.getComment(), elemAnam.getNpasp(), elemAnam.getNumstr());
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
			e.printStackTrace();
            throw new KmiacServerException("Could not update anam.");
        }
	}
	
	@Override
	public void deleteAnam(int npasp, int cslu, int cpodr) throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_anamnez p " +
            		"WHERE p.npasp=?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
			e.printStackTrace();
            throw new KmiacServerException("Could not delete anam.");
        }
	}

	@Override
	public String printAnamnez(int npasp, int cpodr, int cslu)
			throws KmiacServerException, TException {
		final String path;
		String sqlQuery = null;
		int numline = 0;
		int prlpu = 0;
			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("anam", ".htm").getAbsolutePath()), "utf-8")) {
   				StringBuilder sb = new StringBuilder(0x10000);
	    			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
	    			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	    			sb.append("<head>");
	       			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
	   				sb.append("<title>Эпидемиологический анамнез</title>");
	   				sb.append("</head>");
	   				sb.append("<body>");
					try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name from n_m00 where pcod = ?", cpodr)) {
						if (acr.getResultSet().next())
		      				sb.append(String.format("<h4 align=center> %s </h4>", acr.getResultSet().getString("name")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (cslu == 1){
   						sqlQuery = "SELECT o.prlpu FROM n_o00 o WHERE o.pcod = ?";
					}
					if (cslu == 2){
   						sqlQuery = "SELECT o.prlpu FROM n_n00 o WHERE o.pcod = ?";
					}
	            	try (AutoCloseableResultSet acr = sse.execPreparedQuery(sqlQuery, cpodr)) {
						if (acr.getResultSet().next()){
		      				prlpu = acr.getResultSet().getInt("prlpu");
							if (acr.getResultSet().getInt("prlpu") == 5){
		          				sb.append("<h4 align=center> <b>Эпидемиологический анамнез</b> </h4>");
		      				}else{
		          				sb.append("<h4 align=center> <b>ПЕРВИЧНЫЙ ОСМОТР В ПРИЕМНО-ДИАГНОСТИЧЕСКОМ ОТДЕЛЕНИИ</b> </h4>");
		      				}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
   				    

   					try {
  		            	AutoCloseableResultSet acr = sse.execPreparedQuery("select p_anamnez.npasp, p_anamnez.datap,p_anamnez.numstr,n_anz.name,p_anamnez.vybor,p_anamnez.comment,n_anz.yn,n_ot_str.prlpu,n_ot_str.numline from p_anamnez inner join n_anz on (n_anz.nstr=p_anamnez.numstr) inner join n_ot_str on (n_ot_str.nstr=n_anz.nstr) where n_ot_str.prlpu=? and npasp=? " , cslu, npasp); 
   							
								if (acr.getResultSet().next()){
									do {
										if (acr.getResultSet().getBoolean(7)==true){
												if ((acr.getResultSet().getBoolean(5)==true) && (acr.getResultSet().getString(6) == null)){
													sb.append(String.format("%s да",acr.getResultSet().getString(4)));
													sb.append("<br>");
												}
												if ((acr.getResultSet().getBoolean(5)==true) && (acr.getResultSet().getString(6) != null)){
													sb.append(String.format("%s да, %s",acr.getResultSet().getString(4), acr.getResultSet().getString(6)));
													sb.append("<br>");
												}
												if (acr.getResultSet().getBoolean(5)==false){
													sb.append(String.format("%s нет",acr.getResultSet().getString(4)));
													sb.append("<br>");
												}
												
										}
										if (acr.getResultSet().getBoolean(7)==false){
											if (acr.getResultSet().getString(6) != null)
												sb.append(String.format("%s %s",acr.getResultSet().getString(4), acr.getResultSet().getString(6)));
											sb.append("<br>");
									}
										

									}
									while (acr.getResultSet().next());
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
   						
   						
 

   	   		            sb.append(String.format("<br><br>Подпись  _______________________________________________    %1$td.%1$tm.%1$tY г. <br><br>", new Date(System.currentTimeMillis())));
   	   					sb.append(String.format("Подпись врача __________________________________________    %1$td.%1$tm.%1$tY г.<br>", new Date(System.currentTimeMillis())));
 

					osw.write(sb.toString());
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
			return path;
	}
			
	
	@Override
	public int AddPbol(Pbol pbol) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_bol (id_obr, id_gosp, npasp, cod_sp, cdol, pcod, dataz) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, pbol, pbolTypes, 1, 2, 3, 10, 11, 12, 13);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not add pbol.");
		}
	}

	@Override
	public void UpdatePbol(Pbol pbol) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_bol SET bol_l = ?, s_bl = ?, po_bl = ?, pol = ?, vozr = ?, nombl = ? WHERE id = ? ", false, pbol, pbolTypes, 4, 5, 6, 7, 8, 9, 0);
			sme.setCommit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not update pbol.");
		}
	}

	@Override
	public void DeletePbol(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_bol WHERE id = ? ", false, id);
			sme.setCommit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not delete pbol.");
		}
	}

	@Override
	public List<Pbol> getPbol(int id_obr, int id_gosp, int id_bol)
			throws KmiacServerException, TException {
		String sqlQuery = null;
		if (id_gosp == 0){
			id_bol = id_obr;
			sqlQuery = "select * from p_bol where id_obr = ?";
		}
		if (id_obr == 0){
			id_bol = id_gosp;
			sqlQuery = "select * from p_bol where id_gosp = ?";
		}
		
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery(sqlQuery, id_bol)) {
					return rsmPbol.mapToList(acrs.getResultSet());
				} catch (Exception e) {
				e.printStackTrace();
			throw new KmiacServerException("Could not get pbol list.");
		}
	}
}

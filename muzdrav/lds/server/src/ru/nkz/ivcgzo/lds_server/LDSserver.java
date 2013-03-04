	package ru.nkz.ivcgzo.lds_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.ldsThrift.DIslNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.DiagIsl;
import ru.nkz.ivcgzo.ldsThrift.InputLG;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift.Iface;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.LdiNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.Metod;
import ru.nkz.ivcgzo.ldsThrift.MetodNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.N_ldi;
import ru.nkz.ivcgzo.ldsThrift.N_lds;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.ldsThrift.PatientNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.SLdiNotFoundException;
//import ru.nkz.ivcgzo.ldsThrift.S_ldi;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.ldsThrift.S_ot01ExistsException;
import ru.nkz.ivcgzo.ldsThrift.S_ot01NotFoundException;
import ru.nkz.ivcgzo.ldsThrift.Sh_lds;
import ru.nkz.ivcgzo.ldsThrift.Sh_ldsNotFoundException;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;





public class LDSserver extends Server implements Iface { 
	private TServer	thrServ;
	private TResultSetMapper<ObInfIsl, ObInfIsl._Fields> rsmObInIs;
	private static final Class<?>[] islTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class};	
	private TResultSetMapper<DiagIsl, DiagIsl._Fields> rsmDiIs;
	private static final Class<?>[] dislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class, String.class, String.class, Double.class, String.class, Integer.class};
	private TResultSetMapper<LabIsl, LabIsl._Fields> rsmLabIs;	
	private static final Class<?>[] lislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, String.class, String.class, String.class, Double.class, String.class, String.class};
	private TResultSetMapper<S_ot01, S_ot01._Fields> rsmS_ot01;	
	private static final Class<?>[] s_ot01Types = new Class<?>[] {Integer.class, String.class, String.class, String.class, Date.class, String.class, String.class, Double.class};
	private TResultSetMapper<Patient, Patient._Fields> rmsPatient;
	private TResultSetMapper<N_lds, N_lds._Fields> rmsN_lds;
	private TResultSetMapper<Metod, Metod._Fields> rmsMetod;
	private TResultSetMapper<N_ldi, N_ldi._Fields> rmsnldi;
	//private TResultSetMapper<S_ldi, S_ldi._Fields> rmssldi;
//	private static final Class<?>[] s_ldiTypes = new Class<?>[] {Integer.class, String.class};
	private TResultSetMapper<Sh_lds, Sh_lds._Fields> rmsSh_lds;
	private static final Class<?>[] n_ldiTypes = new Class<?>[] {String.class, String.class, String.class, String.class, String.class, Integer.class};
	private final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	private QueryGenerator<Patient> qgPatient;
	@SuppressWarnings("unused")
	private final Class<?>[] intClasTypes; 
	private final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
	@SuppressWarnings("unused")
	private final Class<?>[] strClasTypes; 
	public LDSserver(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
				
		rsmObInIs = new TResultSetMapper<>(ObInfIsl.class, "npasp", "nisl", "kodotd", "nprob", "pcisl", "cisl", "datap", "datav", "prichina", "popl", "napravl", "naprotd", "vrach", "vopl", "diag", "kodvr", "dataz", "cuser", "id_gosp", "id_pos", "talon", "kod_ter");
		rsmDiIs = new TResultSetMapper<>(DiagIsl.class, "npasp", "nisl", "kodisl", "rez", "anamnez", "anastezi", "model", "kol", "op_name", "rez_name", "stoim", "pcod_m", "id");
		rsmLabIs = new TResultSetMapper<>(LabIsl.class, "npasp", "nisl", "cpok", "name", "zpok", "norma", "stoim", "pcod_m", "nameobst");	
		rmsPatient = new TResultSetMapper<>(Patient.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom", "adp_gorod", "adp_ul", "adp_dom", "adp_kv", "adm_gorod", "adm_ul", "adm_dom", "adm_kv", "ter_liv");
		rsmS_ot01 = new TResultSetMapper<>(S_ot01.class, "cotd", "pcod", "c_obst", "c_nz1", "stoim");
		rmsnldi = new TResultSetMapper<>(N_ldi.class, "pcod", "c_nz1", "name_n", "name", "norma", "c_p0e1", "vibor", "id");
		//rmssldi = new TResultSetMapper<>(S_ldi.class, "id", "name");
		rmsMetod = new TResultSetMapper<>(Metod.class, "c_p0e1", "pcod", "c_obst", "name", "stoim", "vibor");
		rmsN_lds = new TResultSetMapper<>(N_lds.class, "clpu", "pcod", "name", "tip");
		rmsSh_lds = new TResultSetMapper<>(Sh_lds.class, "c_p0e1", "c_ldi", "name", "opis", "zakl");
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
	}
	
	@Override
	public int getId() {
		return configuration.appId;
	}
	
	@Override
	public int getPort() {
		return configuration.thrPort;
	}
	
	@Override
	public String getName() {
		return configuration.appName;
	}
	
	private static final Class<?>[] PATIENT_BRIEF_TYPES = new Class<?>[] {
	    //  npasp          fam           im            ot
	        Integer.class, String.class, String.class, String.class,
	    //  datar       poms_ser        poms_nom      
	        Date.class, String.class, String.class
	    };

	@Override
	public void testConnection() throws TException {
	}

	@Override
	public List<ObInfIsl> GetObInfIslt(int npasp, int kodotd) throws TException {
		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT npasp, nisl, kodotd, nprob, pcisl, cisl, datap, datav, prichina, popl, napravl, naprotd, vrach, vopl, diag, kodvr, dataz, cuser, id_gosp, id_pos, talon, kod_ter FROM p_isl_ld where (npasp = ?) and (kodotd = ?) ", npasp, kodotd)) {
			return rsmObInIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}		
		
		
	}

	@Override
	public ObInfIsl GetIsl(int npasp) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT npasp, nisl, kodotd, nprob, pcisl, cisl, datap, datav, prichina, popl, napravl, naprotd, vrach, vopl, diag, kodvr, dataz, cuser, id_gosp, id_pos, talon, kod_ter FROM p_isl_ld WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmObInIs.map(acrs.getResultSet());
			else
				return new ObInfIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int AddIsl(ObInfIsl info) throws TException {

		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_isl_ld (npasp, kodotd, nprob, pcisl, cisl, datap, datav, prichina, popl, napravl, naprotd, vrach, vopl, diag, kodvr, dataz, cuser, talon, kod_ter) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, info, islTypes, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21);
				int nisl = sme.getGeneratedKeys().getInt("nisl");
				sme.setCommit();
				return nisl;
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void UpdIsl(ObInfIsl info) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_isl_ld SET nprob = ?, pcisl = ?, datap = ?, datav = ?, prichina = ?, popl = ?, napravl = ?, naprotd = ?, vrach = ?, vopl = ?, diag = ?, kodvr = ?, cuser = ?, talon = ?, kod_ter = ? WHERE nisl = ? ", false, info, islTypes, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 20, 21, 1 );
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelIsl(int nisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_isl_ld WHERE nisl = ? ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public List<DiagIsl> GetDiagIsl(int nisl) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d where nisl = ? order by kodisl", nisl)) {
			return rsmDiIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public DiagIsl GetDIsl(int nisl) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d WHERE nisl = ? order by kodisl", nisl)) {
			if (acrs.getResultSet().next())
				return rsmDiIs.map(acrs.getResultSet());
			else
				return new DiagIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	

	@Override
	public DiagIsl GetDIslPos(String kodisl, long datav, int npasp, int kodotd)
			throws DIslNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select Distinct prd.kodisl FROM p_isl_ld pld join p_rez_d prd on (pld.nisl = prd.nisl) WHERE (prd.kodisl = ?) and (pld.datav =?) and (pld.npasp = ?) and (pld.kodotd = ?) ORDER BY prd.kodisl", kodisl, new Date (datav), npasp, kodotd)) {
			if (acrs.getResultSet().next())
				return rsmDiIs.map(acrs.getResultSet());
			else
				return new DiagIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	
	
	
	@Override
	public void AddDIsl(DiagIsl di) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl, rez, anamnez, anestezi, model, kol, op_name, rez_name, stoim, pcod_m) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, di, dislTypes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void UpdDIsl(DiagIsl di) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_rez_d SET kodisl = ?, rez = ?, anamnez = ?, anestezi = ?, model = ?, kol = ?, op_name = ?, rez_name = ?, stoim = ?, pcod_m = ? WHERE (id = ?) ", false, di, dislTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void DelDIsl(int nisl, String kodisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_d WHERE (nisl = ?) and (kodisl = ?) ", false, nisl, kodisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public void DelDIslP(int nisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_d WHERE (nisl = ?) ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}
	
	
	
	
	@Override
	public List<LabIsl> GetLabIsl(int nisl, String c_nz1) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select p_rez_l.cpok, n_ldi.name, p_rez_l.zpok, n_ldi.norma, p_rez_l.stoim, p_rez_l.pcod_m, n_nsi_obst.nameobst from p_rez_l JOIN n_ldi ON (p_rez_l.cpok = n_ldi.pcod) Left Join n_nsi_obst ON (p_rez_l.pcod_m = n_nsi_obst.obst) where (nisl = ?)and(c_nz1 = ?) ", nisl, c_nz1)) {
			return rsmLabIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public LabIsl GetLIsl(int nisl, String c_nz1) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_rez_l.cpok, n_ldi.name, p_rez_l.zpok, n_ldi.norma, p_rez_l.stoim, p_rez_l.pcod_m, n_nsi_obst.nameobst from p_rez_l JOIN n_ldi ON (p_rez_l.cpok = n_ldi.pcod) Left Join n_nsi_obst ON(p_rez_l.pcod_m = n_nsi_obst.obst) WHERE (nisl = ?)and(c_nz1 = ?) ", nisl, c_nz1)) {
			if (acrs.getResultSet().next())
				return rsmLabIs.map(acrs.getResultSet());
			else
				return new LabIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void AddLIsl(LabIsl li) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok, zpok, stoim, pcod_m) VALUES (?, ?, ?, ?, ?, ?) ", true, li, lislTypes, 0, 1, 2, 4, 6, 7);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void UpdLIsl(LabIsl li) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_rez_l SET zpok = ?, stoim = ?, pcod_m = ? WHERE (nisl = ?) and (cpok = ?) ", false, li, lislTypes, 4, 6, 7, 1, 2);
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void DelLIsl(int nisl, String cpok) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_l WHERE (nisl = ?) and (cpok = ?) ", false, nisl, cpok);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void DelLIslD(int nisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_l WHERE (nisl = ?) ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}	
	
	
	@Override
	public void start() throws Exception {
		LDSThrift.Processor<Iface> proc = new LDSThrift.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public List<IntegerClassifier> GetKlasM00() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod,name FROM n_m00 where pr = 'Л' ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<IntegerClassifier> GetKlasPrvM00(int pcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod,name FROM n_m00 where (pr = 'Л') and(pcod = ?) ", pcod)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

		
	
	
	@Override
	public List<IntegerClassifier> GetKlasCpos2() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0c ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasPopl() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_abt ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasNapr() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0s ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasO00(int clpu) throws TException {
		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT n_o00.pcod, n_o00.name FROM n_o00 JOIN n_ot9 ON (n_ot9.cotd = n_o00.pcod) where n_ot9.clpu = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}


	@Override
	public List<IntegerClassifier> GetKlasPrvO00(int clpu, int pcod)
			throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT n_o00.pcod, n_o00.name FROM n_o00 JOIN n_ot9 ON (n_ot9.cotd = n_o00.pcod) where (n_ot9.clpu = ?)and(n_o00.pcod = ?) ", clpu, pcod)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public List<IntegerClassifier> GetKlasN00() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_n00 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	

	@Override
	public List<IntegerClassifier> GetKlasPrvN00(int pcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_n00 where pcod = ?", pcod)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<IntegerClassifier> GetKlasOpl() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_opl ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasArez() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_arez ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	public void saveUserConfig(int id, String config) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new TException();
		}
	}

	@Override
	public List<Patient> getPatient(String npasp)
			throws PatientNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT npasp, fam, im, ot, datar, adp_gorod, adp_ul, adp_dom, adp_kv, adm_gorod, adm_ul, adm_dom, adm_kv, ter_liv FROM patient where npasp in " + npasp)) {
			return rmsPatient.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	
	@Override
	public List<Patient> getPatDat(int kodotd, long datap)
			throws PatientNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT distinct Patient.npasp, Patient.fam, Patient.im, Patient.ot, Patient.datar, Patient.adp_gorod, Patient.adp_ul, Patient.adp_dom, Patient.adp_kv, Patient.adm_gorod, Patient.adm_ul, Patient.adm_dom, Patient.adm_kv, Patient.ter_liv "+
																 "FROM patient JOIN p_isl_ld ON (patient.npasp = p_isl_ld.npasp) "+
																 "		Join p_rez_l on (p_isl_ld.nisl = p_rez_l.nisl) "+
																 "where (p_isl_ld.datan is not null) and (p_isl_ld.kodotd = ?) and (p_rez_l.zpok is null) and(p_isl_ld.datap <= ?)", kodotd, new Date(datap))) {
																 //"order by p_isl_ld.naprotd, p_isl_ld.datan, Patient.fam, Patient.im, Patient.ot", new Date(datan), kodotd)) {
			return rmsPatient.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	
	

	@Override
	public List<Metod> getMetod(int c_p0e1, String pcod, String pcod_m)
			throws MetodNotFoundException, TException {
		//try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT n_stoim.c_obst, n_nsi_obst.nameobst, n_stoim.stoim FROM n_stoim left JOIN n_nsi_obst ON (n_stoim.c_obst = n_nsi_obst.obst) where (n_stoim.c_p0e1 = ?) and (n_stoim.pcod = ?) and((n_stoim.c_obst like ?)or(n_stoim.c_obst is null))", c_p0e1, pcod, pcod_m)) {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT n_stoim.c_obst, n_nsi_obst.nameobst as name, n_stoim.stoim FROM n_stoim left JOIN n_nsi_obst ON (n_stoim.c_obst = n_nsi_obst.obst) where (n_stoim.c_p0e1 = ?) and (n_stoim.pcod = ?)", c_p0e1, pcod)) {
		return rmsMetod.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<Metod> GetStoim(String pcod, String c_obst, int kodotd)
			throws TException {
		try (AutoCloseableResultSet	acrs =sse. execPreparedQuery("select n_stoim.stoim from s_ot01 JOIN n_stoim on(s_ot01.pcod = n_stoim.pcod) where (s_ot01.c_obst = n_stoim.c_obst) and (s_ot01.pcod = ?) and (s_ot01.c_obst = ?) and (s_ot01.cotd = ?)", pcod, c_obst, kodotd)){
		return rmsMetod.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}


	@Override
	public List<Metod> GetLabStoim(String pcisl, int kodotd) throws TException {
		try (AutoCloseableResultSet	acrs =sse. execPreparedQuery("select n_stoim.pcod, n_stoim.c_obst, n_stoim.stoim from s_ot01 JOIN n_stoim on(s_ot01.pcod = n_stoim.pcod) where (s_ot01.c_obst = n_stoim.c_obst) and (s_ot01.c_nz1 = ?) and (s_ot01.cotd = ?) order by s_ot01.pcod", pcisl, kodotd)){
		return rmsMetod.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}	
	
	
	
	@Override
	public List<StringClassifier> GetKlasNz1() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_nz1 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasP0e1(int grupp) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0e1 where gruppa = ?", grupp)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<IntegerClassifier> GetKlasNoLabP0e1(int grupp)
			throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0e1 where gruppa <> ?", grupp)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}	
	
	
	
	@Override
	public List<N_ldi> getN_ldi(String c_nz1, int c_p0e1)
			throws LdiNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT nl.pcod, nl.c_nz1, nl.name_n, nl.name, nl.norma, nl.c_p0e1, nl.id FROM n_ldi nl where (nl.c_nz1 = ?) and (nl.c_p0e1 = ?) ", c_nz1, c_p0e1)) {
			return rmsnldi.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	
	@Override
	public List<N_ldi> getAllN_ldi() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM n_ldi ")) {
			return rmsnldi.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public void UpdN_ldi(N_ldi nldi)
			throws LdiNotFoundException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE n_ldi SET name = ? WHERE (c_p0e1 = ?) and (c_nz1 = ?) and (pcod = ?)", false, nldi, n_ldiTypes, 3, 5, 1, 0);
			sme.setCommit();
	} catch (SQLException | InterruptedException e) {
		throw new TException(e);
	}
	}
	
	
/*	@Override
	public List<S_ldi> getAllS_ldi() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_ldi ")) {
			return rmssldi.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void InsS_ldi(S_ldi s_ldi) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO s_ldi (id,name) VALUES (?, ?) ", true, s_ldi, s_ldiTypes, 0, 1);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void UpdS_ldi(S_ldi s_ldi) throws SLdiNotFoundException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE s_ldi SET name = ? WHERE (id = ?) ", false, s_ldi, s_ldiTypes, 1, 0);
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}	
	*/
	
	
	
	
	@Override
	public List<S_ot01> GetS_ot01(int cotd, String c_nz1)
			throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_ot01 where (cotd = ?) and (c_nz1 = ?) order by c_obst", cotd, c_nz1)) {
			return rsmS_ot01.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	
	@Override
	public List<S_ot01> GetMinS_ot01(int cotd) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_ot01 where (cotd = ?) ", cotd)) {
			return rsmS_ot01.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	
	@Override
	public List<StringClassifier> GetKlasS_ot01(int cotd) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT (s_ot01.c_nz1) as pcod, (n_nz1.name) as name FROM s_ot01 JOIN n_nz1 ON (s_ot01.c_nz1 = n_nz1.pcod) where (cotd = ?) order by s_ot01.c_nz1, n_nz1.name", cotd)){
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	 
	@Override
	public List<StringClassifier> GetKlasIsS_ot01(int cotd, String organ)
		throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT (s_ot01.pcod) as pcod, (n_ldi.name_n) as name FROM s_ot01 JOIN n_ldi ON (s_ot01.pcod = n_ldi.pcod) where (s_ot01.cotd = ?)and(s_ot01.c_nz1 = ?) order by s_ot01.pcod, n_ldi.name_n", cotd, organ)){
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}	
		}

	@Override
	public List<StringClassifier> GetKlasMetS_ot01(int cotd, String organ,
			String isl, int priznak) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select (n_stoim.c_obst) as pcod, (n_nsi_obst.nameobst) as name from s_ot01 JOIN n_stoim ON (s_ot01.pcod = n_stoim.pcod) JOIN n_nsi_obst ON (s_ot01.c_obst = n_nsi_obst.obst) where (s_ot01.c_obst = n_stoim.c_obst) and (s_ot01.cotd = ?) and (s_ot01.c_nz1 = ?) and (s_ot01.pcod = ?)and(n_stoim.priznak = ?)", cotd, organ, isl, priznak)){
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
		
	}
	
	
	
	@Override
	public S_ot01 GetSot01(int cotd, String c_nz1)
			throws S_ot01NotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM s_ot01 WHERE (cotd = ?) and (c_nz1 = ?) ", cotd, c_nz1)) {
			if (acrs.getResultSet().next())
				return rsmS_ot01.map(acrs.getResultSet());
			else
				return new S_ot01();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void AddS_ot01(S_ot01 so) throws S_ot01ExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO s_ot01 (cotd, pcod, c_obst, c_nz1) VALUES (?, ?, ?, ?) ", true, so, s_ot01Types, 0, 1, 2, 3);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void UpdS_ot01(S_ot01 so) throws S_ot01ExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//		if (!isIslExists(info)) {
			sme.execPreparedT("UPDATE s_ot01 SET  c_obst = ? WHERE (cotd = ?) and (pcod = ?) and (c_nz1 = ?)", false, so, s_ot01Types, 2, 0, 1, 3);
			sme.setCommit();
//		} else
//			throw new IslExistsException();
	} catch (SQLException | InterruptedException e) {
		throw new TException(e);
	}
		
	}

	@Override
	public void DelS_ot01(int cotd, String pcod, String c_nz1)
			throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_ot01 WHERE (cotd = ?) and (pcod = ?) and (c_nz1 = ?)", false, cotd, pcod, c_nz1);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void DelS_ot01D(int cotd, String pcod, String c_obst, String c_nz1)
			throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_ot01 WHERE (cotd = ?) and (pcod = ?) and (c_obst = ?) and (c_nz1 = ?)", false, cotd, pcod, c_obst, c_nz1);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public List<IntegerClassifier> GetKlasSvrach(int cpodr) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select s_vrach.pcod, (s_vrach.fam ||' '|| s_vrach.im ||' '|| s_vrach.ot) AS name FROM s_vrach JOIN s_mrab ON (s_vrach.pcod = s_mrab.pcod) WHERE (s_mrab.cpodr = ?)and(priznd = 1) ", cpodr)){
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<IntegerClassifier> GetKlasAllSvrach(int cpodr) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select s_vrach.pcod, (s_vrach.fam ||' '|| s_vrach.im ||' '|| s_vrach.ot) AS name FROM s_vrach JOIN s_mrab ON (s_vrach.pcod = s_mrab.pcod) WHERE s_mrab.cpodr = ? ", cpodr)){
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}	
	
	
	@Override
	public List<N_lds> getN_lds(int pcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT clpu, pcod, name, tip FROM n_lds where (pcod = ?)", pcod)) {
		return rmsN_lds.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<Sh_lds> getSh_lds(String c_ldi) throws Sh_ldsNotFoundException,
			TException {
		//try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT c_p0e1, c_ldi, name, opis, zakl FROM sh_lds where (c_ldi = ?)", c_ldi)) {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT  name FROM sh_lds where (c_ldi = ?)", c_ldi)) {
		return rmsSh_lds.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<Sh_lds> getDSh_lds(String c_ldi, String name)
			throws Sh_ldsNotFoundException, TException {
		//try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT c_p0e1, c_ldi, name, opis, zakl FROM sh_lds where (c_ldi = ?)and(name = ?)", c_ldi, name)) {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT  opis, zakl FROM sh_lds where (c_ldi = ?)and(name = ?)", c_ldi, name)) {
		return rmsSh_lds.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<StringClassifier> GetShab_lds(String c_lds) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT (c_ldi) as pcod, name FROM sh_lds where (c_ldi = ?) order by name", c_lds)) {
		return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public String printLabGur(InputLG ilg) throws KmiacServerException,
			TException {
		String svod = null;
		
		// Дата от ...
		Date dn;
		// Дата до ...
		Date dk;
		/*try {*/
		//dn = Date.valueOf(String.valueOf(ilg.getDaten()));
		//dk = Date.valueOf(String.valueOf(ilg.getDatek()));

		dn = new Date(ilg.getDaten());
		dk = new Date(ilg.getDatek());

			// Код полеклиники
			int kotd = ilg.getKotd();
			
			// Код ЛПУ
			String cnz1 = ilg.getC_nz1();
			// Вид больницы (Д/В)
			
			int kpol = 0;
			int kzap = 0;
			String [][] labgur = null;  
			final String sqlKolPol ="select count(distinct so.PCOD) as kol "+
								"from p_isl_ld pil join p_rez_l prl on(pil.nisl = prl.nisl) "+ 
									"join s_ot01 so on(prl.cpok = so.pcod) "+
									"join n_ldi nl on(nl.pcod= so.pcod) " +
									"where (prl.pcod_m = so.c_obst)and(so.c_nz1=nl.c_nz1)and(pil.kodotd= "+kotd+")and(pil.pcisl = '"+ cnz1 +"') and "+
									"(pil.datav between '"+new Date(ilg.getDaten())+"' and '"+new Date(ilg.getDatek())+"')";
					
					
			final String sqlNamPol="select distinct so.PCOD,nl.NAME_n "+
										"from p_isl_ld pil join p_rez_l prl on(pil.nisl = prl.nisl) "+
										"join s_ot01 so on(prl.cpok = so.pcod) "+
										"join n_ldi nl on(nl.pcod= so.pcod) "+
										"where (prl.pcod_m = so.c_obst)and(so.c_nz1=nl.c_nz1)and(pil.kodotd= "+kotd+")and(pil.pcisl = '"+ cnz1 +"') and "+
										"(pil.datav between '"+new Date(ilg.getDaten())+"' and '"+new Date(ilg.getDatek())+"')"+
										" group by so.PCOD,nl.NAME_n";	
			
			final String sqlKolZap = "select count(distinct pil.nisl) as kol "+
										"from patient p join p_isl_ld pil on (p.npasp = pil.npasp) "+
										"join p_rez_l prl on (pil.nisl = prl.nisl) "+
										"join s_ot01 so on(prl.cpok = so.pcod) "+
										"join n_ldi nl on (so.pcod = nl.pcod) "+
										"where (prl.pcod_m = so.c_obst)and(so.c_nz1=nl.c_nz1)and(pil.kodotd= "+kotd+")and(pil.pcisl = '"+ cnz1 +"') and "+
										"(pil.datav between '"+dn+"' and '"+dk+"')";

			/*System.out.println(sqlKolZap);
			System.out.println(sqlKolPol);*/
			
			try (AutoCloseableResultSet bisl = sse.execPreparedQuery(sqlKolZap)) {
				
				bisl.getResultSet().next();
				
				//System.out.println(sqlKolZap);
				
				kzap = bisl.getResultSet().getInt("kol")+1; // кол-во пациентов
				//System.out.println(kzap);
						
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			try (AutoCloseableResultSet bisl = sse.execPreparedQuery(sqlKolPol)) {
				
				
				
					//System.out.println(sqlKolPol);
					
					bisl.getResultSet().next();
					
					kpol = bisl.getResultSet().getInt("kol")+3; // кол-во исследований
					//System.out.println(sqlKolPol);
					//System.out.println(kpol);
					//bisl.getResultSet().first();
				
					//labgur = new String [kzap][kpol];
					/*int i = 3;
					labgur[0][i] = bisl.getResultSet().getString("name");
				
					while(bisl.getResultSet().next()){
						i++;
						labgur[0][i] = bisl.getResultSet().getString("name");
				
					}*/
										
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			labgur = new String [kzap][kpol];
			
			try (AutoCloseableResultSet bisl = sse.execPreparedQuery(sqlNamPol)) {
				
				
				
				System.out.println(sqlNamPol);
				//kpol = bisl.getResultSet().getRow()+3; // кол-во исследований
				/*bisl.getResultSet().first();
			
				//labgur = new String [kzap][kpol];
				
				labgur[0][i] = bisl.getResultSet().getString("name");*/
				
				int i = 2;
				while(bisl.getResultSet().next()){
					i++;
					labgur[0][i] = bisl.getResultSet().getString("name_n");
			
				}
									
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
			
			final String sqlPatLG = "select distinct pil.nprob, pil.datav, pil.nisl, (p.fam || ' ' || p.im || ' ' || p.ot) fio, nl.NAME_n Pokaz, prl.ZPOK, p.NPASP "+
					"from patient p join p_isl_ld pil on (p.npasp = pil.npasp) "+
					"join p_rez_l prl on (pil.nisl = prl.nisl) "+
					"join s_ot01 so on(prl.cpok = so.pcod) "+
					"join n_ldi nl on (so.pcod = nl.pcod) "+
					"where (prl.pcod_m = so.c_obst)and(so.c_nz1=nl.c_nz1)and(pil.pcisl = so.c_nz1)and(pil.kodotd= "+kotd+")and(pil.pcisl = '"+ cnz1 +"') and "+
					"(pil.datav between '"+dn+"' and '"+dk+"') "+
					"order by pil.nisl, nl.name_n";

			
			try (AutoCloseableResultSet bisl = sse.execPreparedQuery(sqlPatLG)) {
				
				int i = 1; 
				
				while (bisl.getResultSet().next()){
					
					if(bisl.getResultSet().isFirst()){
						labgur[0][0] = "№ п/п";
						labgur[0][1] = "№ пробы";
						labgur[0][2] = "Ф.И.О.";
						
						labgur[i][0] = String.valueOf(i);
						labgur[i][1] = bisl.getResultSet().getString("nprob");
						labgur[i][2] = bisl.getResultSet().getString("fio");
								
					}

					// Сравнение равна ли текущая запись таблицы записи в массиве
					if (labgur[i][2].equals(bisl.getResultSet().getString("fio"))){
						//поиск поля в массиве соответствующее записи таблицы
						for (int j = 3; j<kpol-4; j++){
							
							if(labgur[0][j].equals(bisl.getResultSet().getString("Pokaz"))){
								labgur[i][j] = bisl.getResultSet().getString("zpok");
								break;
							}						
							
						}
						
						
					}else{
						
						i++;
						labgur[i][0] = String.valueOf(i);
						labgur[i][1] = bisl.getResultSet().getString("nprob");
						labgur[i][2] = bisl.getResultSet().getString("fio");	
						for (int j = 3; j<kpol-4; j++){
							
							if(labgur[0][j].equals(bisl.getResultSet().getString("Pokaz"))){
								labgur[i][j] = bisl.getResultSet().getString("zpok");
								break;
							}						
							
						}
						
					}
				
				}
				
				try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(svod = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
					
					
					StringBuilder sb = new StringBuilder(0x10000);

				  //sb.append(String.format("!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
				  sb.append(String.format("<HTML>"));
				  sb.append(String.format("<HEAD>"));
				  sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1251\">"));
				  sb.append(String.format("	<TITLE>Лабораторный журнал</TITLE>"));
				  sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Windows)\">"));
				  sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121017;13540000\">"));
				  sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121102;14361071\">"));

				  sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
				  sb.append(String.format("	<!--"));
				  sb.append(String.format("		@page { size: 29.7cm 21cm; margin-right: 0.35cm; margin-top: 3cm; margin-bottom: 1.5cm }"));
				  sb.append(String.format("		P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }"));
				  sb.append(String.format("		P.western { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ru-RU }"));
				  sb.append(String.format("		P.cjk { font-family: \"Times New Roman\", serif; font-size: 12pt }"));
				  sb.append(String.format("		P.ctl { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ar-SA }"));
				  sb.append(String.format("	-->"));
				  sb.append(String.format("	</STYLE>"));
				  sb.append(String.format("</HEAD>"));
				  sb.append(String.format("<BODY LANG=\"ru-RU\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
				  sb.append(String.format("<P STYLE=\"margin-bottom: 0cm\"><BR></P>"));
				  sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>Лабораторный журнал</B></FONT></P>"));
				  sb.append(String.format("<P></P>"));
				  sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>за период с %s по %s</B></FONT></P>",dn,dk));
				  //sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>Поликлиника прикрепления:  %s</B></FONT></P>",namepol));
				  sb.append(String.format("<P></P>"));
				  sb.append(String.format("<P></P>"));
				  sb.append(String.format("<TABLE WIDTH=1120 CELLPADDING=7 CELLSPACING=0>"));
			
				 for (int n = 0; n<kzap-1; n++ ){
					 sb.append(String.format("	<TR VALIGN=TOP>"));
					 for (int m = 0; m<kpol-1; m++){
							sb.append(String.format("		<TD WIDTH=52 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
							sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",labgur[n][m]));
							sb.append(String.format("		</TD>"));
						 
					 }
					 sb.append(String.format("	</TR>"));
				 }
				 
					sb.append(String.format("</TABLE>"));
					sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
					sb.append(String.format("</P>"));
					sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
					sb.append(String.format("</P>"));
					sb.append(String.format("</BODY>"));
					sb.append(String.format("</HTML>"));
					
					osw.write(sb.toString());

				 svod = sb.toString();
				 
				} catch (IOException e) {
					e.printStackTrace();
					throw new KmiacServerException();
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
		 return svod;
			
			
	}

	@Override
	public List<IntegerClassifier> GetKlasKod_ter() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select distinct kdate as pcod, g_name AS name FROM n_nsipol where g_name is not null order by kdate")){
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasNsipol(int kdate)
			throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select kdpodr as pcod, namepodr AS name FROM n_nsipol where kdate = ? and kdoms = 1", kdate)){
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	

}

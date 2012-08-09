	package ru.nkz.ivcgzo.lds_server;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.ldsThrift.DiagIsl;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift.Iface;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.LdiNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.Metod;
import ru.nkz.ivcgzo.ldsThrift.MetodNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.N_ldi;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.ldsThrift.PatientNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.ldsThrift.S_ot01ExistsException;
import ru.nkz.ivcgzo.ldsThrift.S_ot01NotFoundException;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;






public class LDSserver extends Server implements Iface { 
	private TServer	thrServ;
	private TResultSetMapper<ObInfIsl, ObInfIsl._Fields> rsmObInIs;
	private static final Class<?>[] islTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, String.class, Short.class, Date.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Data.class};	
	private TResultSetMapper<DiagIsl, DiagIsl._Fields> rsmDiIs;
	private static final Class<?>[] dislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, Integer.class, String.class, String.class, String.class, Short.class, String.class, String.class, Double.class, String.class};
	private TResultSetMapper<LabIsl, LabIsl._Fields> rsmLabIs;	
	private static final Class<?>[] lislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, String.class, Double.class, String.class, Integer.class};
	private TResultSetMapper<S_ot01, S_ot01._Fields> rsmS_ot01;	
	private static final Class<?>[] s_ot01Types = new Class<?>[] {Integer.class, String.class, String.class, String.class};
	private TResultSetMapper<Patient, Patient._Fields> rmsPatient;
	private TResultSetMapper<Metod, Metod._Fields> rmsMetod;
	private TResultSetMapper<N_ldi, N_ldi._Fields> rmsnldi;
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
				
		rsmObInIs = new TResultSetMapper<>(ObInfIsl.class, "npasp", "nisl", "kodotd", "nprob", "pcisl", "cisl", "datap", "datav", "prichina", "popl", "napravl", "naprotd", "fio", "vopl", "diag", "kodvr", "kodm", "kods", "dataz");
		rsmDiIs = new TResultSetMapper<>(DiagIsl.class, "npasp", "nisl", "kodisl", "rez", "anamnez", "anastezi", "model", "kol", "op_name", "rez_name", "stoim", "pcod_m");
		rsmLabIs = new TResultSetMapper<>(LabIsl.class, "npasp", "nisl", "cpok", "zpok", "stoim", "pcod_m", "pvibor");	
		rsmS_ot01 = new TResultSetMapper<>(S_ot01.class, "cotd", "pcod", "c_obst", "c_nz1");
		rmsnldi = new TResultSetMapper<>(N_ldi.class, "pcod", "c_nz1", "name_n", "name", "norma", "c_p0e1", "vibor");
		rmsMetod = new TResultSetMapper<>(Metod.class, "c_p0e1", "pcod", "c_obst", "name", "stoim", "vibor");
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
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
	public List<ObInfIsl> GetObInfIslt(int npasp) throws TException {
		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_isl_ld where npasp = ? ", npasp)) {
			return rsmObInIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}		
		
		
	}

	@Override
	public ObInfIsl GetIsl(int npasp) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_isl_ld WHERE npasp = ? ", npasp)) {
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
				sme.execPreparedT("INSERT INTO p_isl_ld (npasp, kodotd, nprob, pcisl, cisl, datap, datav, prichina, popl, napravl, naprotd, fio, vopl, diag, kodvr, kodm, kods, dataz) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, info, islTypes, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
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
				sme.execPreparedT("UPDATE p_isl_ld SET nprob = ?, pcisl = ?, cisl = ?, datap = ?, datav = ?, prichina = ?, popl = ?, napravl = ?, naprotd = ?, fio = ?, vopl = ?, diag = ?, kodvr = ?, kodm = ?, kods = ? WHERE nisl = ? ", false, info, islTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 1 );
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d where nisl = ? ", nisl)) {
			return rsmDiIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public DiagIsl GetDIsl(int nisl) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d WHERE nisl = ? ", nisl)) {
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
				sme.execPreparedT("UPDATE p_rez_d SET kodisl = ?, rez = ?, anamnez = ?, anestezi = ?, model = ?, kol = ?, op_name = ?, rez_name = ?, stoim = ?, pcod_m = ? WHERE (nisl = ?) ", false, di, dislTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1);
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
			sme.execPrepared("DELETE FROM p_rez_d WHERE (nisl = ?) and (kodisl = ?) ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<LabIsl> GetLabIsl(int nisl) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_l where nisl = ? ", nisl)) {
			return rsmLabIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public LabIsl GetLIsl(int nisl) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_rez_l WHERE nisl = ? ", nisl)) {
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
				sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok, zpok, stoim, pcod_m, pvibor) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, li, lislTypes, 0, 1, 2, 3, 4, 5, 6);
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
				sme.execPreparedT("UPDATE p_rez_l SET zpok = ?, stoim = ?, pcod_m = ?, pvibor = ? WHERE (nisl = ?) and (cpok = ?) ", false, li, lislTypes, 3, 4, 5, 6, 1, 2);
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
	public List<IntegerClassifier> GetKlasN00() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_n00 ")) {
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
	public List<Patient> getPatient(Patient pat)
			throws PatientNotFoundException, TException {
/*        String  sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom"
                + "FROM patient";
        InputData inData = qgPatient.genSelect(pat, sqlQuery);
        try {
            sqlQuery = inData.getQueryText();
            int[] indexes = inData.getIndexes();
           try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(sqlQuery, pat,
                    PATIENT_BRIEF_TYPES, indexes)){
            ResultSet rs = acrs.getResultSet();
            List<Patient> patientsInfo = new ArrayList<Patient>();
            if (rs.next()) {
                do {
                    Patient curPatient = rmsPatient.map(rs);
                    curPatient.setAdpAddress(new Address(rsmAdpAdress.map(rs)));
                    curPatient.setAdmAddress(new Address(rsmAdmAdress.map(rs)));
                    patientsInfo.add(curPatient);
                } while (rs.next());
                return patientsInfo;
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }*/
		return null;
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
	public List<StringClassifier> GetKlasNz1() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_nz1 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> GetKlasP0e1() throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0e1 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<N_ldi> getN_ldi(String c_nz1, int c_p0e1)
			throws LdiNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM n_ldi where (c_nz1 = ?) and (c_p0e1 = ?) ", c_nz1, c_p0e1)) {
			return rmsnldi.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<S_ot01> GetS_ot01(int cotd, String c_nz1)
			throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_ot01 where (cotd = ?) and (c_nz1 = ?) ", cotd, c_nz1)) {
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
	public void UpdN_ldi(N_ldi nldi)
			throws LdiNotFoundException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE n_ldi SET name = ? WHERE (c_p0e1 = ?) and (c_nz1 = ?) and (pcod = ?)", false, nldi, n_ldiTypes, 3, 5, 1, 0);
			sme.setCommit();
	} catch (SQLException | InterruptedException e) {
		throw new TException(e);
	}
	}

}

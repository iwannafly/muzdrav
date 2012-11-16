package ru.nkz.ivcgzo.serverDisp;

import java.sql.Date;
import java.sql.SQLException;
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
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftDisp.PatientInfo;
import ru.nkz.ivcgzo.thriftDisp.Pdisp_ds_do;
import ru.nkz.ivcgzo.thriftDisp.Pdisp_ds_po;
import ru.nkz.ivcgzo.thriftDisp.Pfiz;
import ru.nkz.ivcgzo.thriftDisp.PfizNotFoundException;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp.Iface;

public class ServerDisp extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<Pfiz, Pfiz._Fields> rsmPfiz;
	private final Class<?>[] pfizTypes; 
	private final TResultSetMapper<Pdisp_ds_do, Pdisp_ds_do._Fields> rsmPdisp_ds_do;
	private final Class<?>[] pdispds_doTypes;
	private final TResultSetMapper<Pdisp_ds_po, Pdisp_ds_po._Fields> rsmPdisp_ds_po;
	private final Class<?>[] pdispds_poTypes;
	private final TResultSetMapper<PatientInfo, PatientInfo._Fields> rsmPat;
	private final Class<?>[] patTypes;
	private final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	@SuppressWarnings("unused")
	private final Class<?>[] intClasTypes; 


	public ServerDisp(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmPfiz = new TResultSetMapper<>(Pfiz.class, "npasp",       "pe",          "pp",          "pi",           "fv",          "fr",         "grzd",        "prb",          "prk",
				"ves",         "rost",      "dataz",    "vrk",       "pfm1",        "pfm2",        "pfm3",       "pfd1",       "pfd2",       "pfd3",      "pfd4",        "pfd5", 
				"pfd6",       "prs",         "priv",       "priv_pr",      "priv_n",       "bcg",        "polio",        "akds",        "adsm",       "adm",           "kor", 
				"parotit",      "krasn",       "gepatit",    "bcg_vr",      "polio_vr",     "akds_vr",   "kor_vr",      "parotit_vr",   "krasn_vr", 
				"gepatit_vr",  "dat_p",     "profil",      "vedom",       "vib1",        "vib2",        "ipr",        "dat_ipr", "menses",      "menses1", 
				"okr",          "pf1",        "mf1",         "ef1",        "rf1",          "grzd_s");
		pfizTypes = new Class<?>[] {                 Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Double.class, Double.class, Date.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class,Integer.class, Integer.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmPdisp_ds_do = new TResultSetMapper<>(Pdisp_ds_do.class, "npasp",       "id",          "d_do",        "diag_do",    "obs_n",       "obs_v", 	"lech_n",      "lech_v",       "dataz",   "nameds");
		pdispds_doTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class,Integer.class, Integer.class, Date.class, String.class};
		
		rsmPdisp_ds_po = new TResultSetMapper<>(Pdisp_ds_po.class, "npasp",        "id",          "d_po",        "diag_po",    "xzab",        "pu",          "disp",       "vmp1",        
				"vmp2",        "vrec1",       "vrec2",		 "vrec3",         "vrec4",     "vrec5",       "vrec6",	    "vrec7",       "vrec8",       "vrec9",        "vrec10",     
				"nrec1",       "nrec2",       "nrec3",        "nrec4",      "nrec5",       "nrec6",       "nrec7",       "nrec8",       "nrec9",        "nrec10",     "dataz",    "recdop1", 
				"recdop2",      "recdop3",    "nameds");
		pdispds_poTypes = new Class<?>[] {                         Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, 
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class,
				Integer.class, Integer.class, String.class};
		
		rsmPat = new TResultSetMapper<>(PatientInfo.class, "npasp",       "fam",        "im",         "ot",         "datar",    "poms_ser",   "poms_nom",   "pol"); 
		patTypes = new Class<?>[] {                        Integer.class, String.class, String.class, String.class, Date.class, String.class, String.class, Integer.class};
	
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};

	}

	@Override
	public void testConnection() throws TException {

	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {

	}

	@Override
	public void start() throws Exception {
		ThriftDisp.Processor<Iface> proc = new ThriftDisp.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();


	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public void setPfiz(Pfiz fiz) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPfiz(fiz.npasp);
				sme.execPreparedT("update p_fiz set pe=?, pp=?, pi=?, fv=?, fr=?, grzd=?, prb=?, prk=?, ves=?, rost=?, dataz=?, vrk=?, pfm1=?, pfm2=?, pfm3=?, pfd1=?, pfd2=?, pfd3=?, pfd4=?, pfd5=?, pfd6=?, prs=?, priv=?, priv_pr=?, priv_n=?, bcg=?, polio=?, akds=?, adsm=?, adm=?, kor=?, parotit=?, krasn=?, gepatit=?, bcg_vr=?, polio_vr=?, akds_vr=?, kor_vr=?, parotit_vr=?, krasn_vr=?, gepatit_vr=?, dat_p=?, profil=?, vedom=?, vib1=?, vib2=?, ipr=?, dat_ipr=?, menses=?, menses1=?, okr=?, pf1=?, mf1=?, ef1=?, rf1=?, grzd_s=? where npasp = ? ", false, fiz, pfizTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 0);
				sme.setCommit();
			} catch (PfizNotFoundException e) {
				sme.execPreparedT("insert into p_fiz (npasp, pe, pp, pi, fv, fr, grzd, prb, prk, ves, rost, dataz, vrk, pfm1, pfm2, pfm3, pfd1, pfd2, pfd3, pfd4, pfd5, pfd6, prs, priv, priv_pr, priv_n, bcg, polio, akds, adsm, adm, kor, parotit, krasn, gepatit, bcg_vr, polio_vr, akds_vr, kor_vr, parotit_vr, krasn_vr, gepatit_vr, dat_p, profil, vedom, vib1, vib2, ipr, dat_ipr, menses, menses1, okr, pf1, mf1, ef1, rf1, grzd_s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ", false, fiz, pfizTypes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56);
				sme.setCommit();
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Pfiz getPfiz(int npasp) throws KmiacServerException,
			PfizNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_fiz where npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPfiz.map(acrs.getResultSet());
			else
				throw new PfizNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public PatientInfo getPatientInfo(int npasp) throws KmiacServerException,
			TException {
		String sql = "select npasp,fam,im,ot,datar,poms_ser,poms_nom,pol from patient where npasp = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, npasp)) {
			acrs.getResultSet().next();
			return rsmPat.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
}

	@Override
	public int AddPdispds_do(Pdisp_ds_do pds_do) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_disp_ds_do (npasp, dataz, diag_do) VALUES (?, ?, ?) ", true, pds_do, pdispds_doTypes, 0, 8, 3);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePdispds_do(Pdisp_ds_do pds_do)
			throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("update p_disp_ds_do set d_do = ?, diag_do = ?, obs_n = ?, obs_v = ?, lech_n = ?, lech_v = ? where id = ? ", false, pds_do, pdispds_doTypes, 2, 3, 4, 5, 6, 7, 1);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<Pdisp_ds_do> getTblDispds_do(int npasp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_disp_ds_do.*, n_c00.name as nameds from p_disp_ds_do join n_c00 on (p_disp_ds_do.diag_do=n_c00.pcod) where npasp = ? ", npasp)) 
		{
			return rsmPdisp_ds_do.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	}
	}

	@Override
	public Pdisp_ds_do getDispds_do(int id) throws KmiacServerException,
			TException {
		String sql = "select * from p_disp_ds_do where id = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, id)) {
			acrs.getResultSet().next();
			return rsmPdisp_ds_do.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPdispds_po(Pdisp_ds_po pds_po) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_disp_ds_po (npasp, dataz, diag_po) VALUES (?, ?, ?) ", true, pds_po, pdispds_poTypes, 0, 29, 3);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePdispds_po(Pdisp_ds_po pds_po)
			throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("update p_disp_ds_po set d_po = ?, diag_po = ?, xzab = ?, pu = ?, disp = ?, vmp1 = ?, vmp2 = ?, vrec1 = ?, vrec2 = ?, vrec3 = ?, vrec4 = ?, vrec5 = ?, vrec6 = ?, vrec7 = ?, vrec8 = ?, vrec9 = ?, vrec10 = ?, nrec1 = ?, nrec2 = ?, nrec3 = ?, nrec4 = ?, nrec5 = ?, nrec6 = ?, nrec7 = ?, nrec8 = ?, nrec9 = ?, nrec10 = ?, recdop1 = ?, recdop2 = ?, recdop3 = ? where id = ? ", false, pds_po, pdispds_poTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 30, 31, 32, 1);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<Pdisp_ds_po> getTblDispds_po(int npasp)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_disp_ds_po.*, n_c00.name as nameds from p_disp_ds_po join n_c00 on (p_disp_ds_po.diag_po=n_c00.pcod) where npasp = ? ", npasp)) 
		{
			return rsmPdisp_ds_po.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	}
	}

	@Override
	public Pdisp_ds_po getDispds_po(int id) throws KmiacServerException,
			TException {
		String sql = "select * from p_disp_ds_po where id = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, id)) {
			acrs.getResultSet().next();
			return rsmPdisp_ds_po.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeleteDispds_do(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("delete from p_disp_ds_do where id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void DeleteDispds_po(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("delete from p_disp_ds_po where id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<IntegerClassifier> get_n_prf() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name from n_prf ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}	}

}

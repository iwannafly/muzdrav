package ru.nkz.ivcgzo.serverMss;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMss.MestnNotFoundException;
import ru.nkz.ivcgzo.thriftMss.MssNotFoundException;
import ru.nkz.ivcgzo.thriftMss.MssdopNotFoundException;
import ru.nkz.ivcgzo.thriftMss.P_smert;
import ru.nkz.ivcgzo.thriftMss.PatientMestn;
import ru.nkz.ivcgzo.thriftMss.Psmertdop;
import ru.nkz.ivcgzo.thriftMss.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftMss.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftMss.ThriftMss;
import ru.nkz.ivcgzo.thriftMss.ThriftMss.Iface;
import ru.nkz.ivcgzo.thriftMss.UserFio;
import ru.nkz.ivcgzo.thriftMss.UserNotFoundException;

public class serverMss extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<P_smert, P_smert._Fields> mssDoc;
	private static Class<?>[] mssTypes; 
	private TResultSetMapper<PatientCommonInfo, PatientCommonInfo._Fields> mssPatient;
	private static Class<?>[] patTypes;
	private TResultSetMapper<Psmertdop,Psmertdop._Fields> mssDop;
	private static Class<?>[] dopTypes;
	private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> mssClass;
	private TResultSetMapper<StringClassifier, StringClassifier._Fields> mssStrClas;
	private static Class<?>[] intcTypes;
	private static Class<?>[] strcTypes;
	private TResultSetMapper<PatientMestn,PatientMestn._Fields> mssMestn;
	private static Class<?>[] mestnTypes;
	private TResultSetMapper<UserFio,UserFio._Fields> mssUser;
	private static Class<?>[] userTypes;
	
	
	public serverMss(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		mssDoc = new TResultSetMapper<>(P_smert.class, "id", "npasp", "ser", "nomer", "vid", "datav", "datas", "vrems", 
				"ads_obl", "ads_raion", "ads_gorod", "ads_ul", "ads_dom", "ads_korp", "ads_kv", "ads_mestn", 
				"nastupila", "semp", "obraz", "zan", "proiz", "datatr", "vid_tr", "vrem_tr", "obst", "ustan", "cvrach",
				"cdol", "osn", "psm_a", "psm_an", "psm_ak", "psm_ad", "psm_b", "psm_bn", "psm_bk", "psm_bd",
				"psm_v", "psm_vn", "psm_vk", "psm_vd", "psm_g", "psm_gn", "psm_gk", "psm_gd", "psm_p", "psm_pn",
				"psm_pk", "psm_pd", "psm_p1", "psm_p1n", "psm_p1k", "psm_p1d", "psm_p2", "psm_p2n", "psm_p2k",
				"psm_p2d", "dtp", "umerla", "cuser", "clpu", "fio_r", "don", "ves", "nreb", "mrojd", "fam_m", 
				"im_m", "ot_m", "datarm", "dataz", "fio_pol", "vdok", "sdok", "ndok", "dvdok", "kvdok", 
				"vz_ser", "vz_nomer", "vz_datav", "adm_raion");
		mssTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, Time.class,
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Time.class, String.class, Integer.class,
				Integer.class,String.class, Integer.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Integer.class,
				String.class, String.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class,
				Integer.class, Integer.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class,
				String.class, Date.class, Date.class, String.class, Integer.class, String.class, String.class, Date.class, String.class, 
				Integer.class, Integer.class, Date.class, String.class};
		
		mssPatient = new TResultSetMapper<>(PatientCommonInfo.class, "npasp","fam", "im", "ot", "datar", "pol", "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_korp", "adm_kv", "region_liv");
		patTypes = new Class<?>[]{Integer.class,String.class,String.class,String.class,Date.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class};
		
		mssDop = new TResultSetMapper<>(Psmertdop.class, "cpodr", "cslu", "prizn", "nomer_n", "nomer_k", "nomer_t");
		dopTypes = new Class<?>[] {Integer.class,Integer.class,Boolean.class,Integer.class,Integer.class,Integer.class};
		
		mssClass = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		intcTypes = new Class<?>[] {Integer.class, String.class};
		
		mssStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
		strcTypes = new Class<?>[] {String.class, String.class};
	
		mssMestn = new TResultSetMapper<>(PatientMestn.class, "vid_np", "c_ffomc", "nam_kem");
		mestnTypes = new Class<?>[] {Integer.class, Integer.class, String.class};
	
		mssUser = new TResultSetMapper<>(UserFio.class, "pcod", "fam", "im", "ot");
		userTypes = new Class<?>[] {Integer.class, String.class, String.class, String.class};
	}

	@Override
	public void testConnection() throws TException {
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new TException();
		}
	}

	@Override
	public void start() throws Exception {
		ThriftMss.Processor<Iface> proc = new ThriftMss.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public P_smert getPsmert(int npasp) throws MssNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_smert WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return mssDoc.map(acrs.getResultSet());
			else
				throw new MssNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int setPsmert(P_smert npasp) throws TException {
		String sql;
		
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPsmert(npasp.getNpasp());
				
				sql = "UPDATE p_smert SET ser = ?, nomer = ?, vid = ?, datav = ?, datas = ?, vrems = ?, " + 
						"ads_obl = ?, ads_raion = ?, ads_gorod = ?, ads_ul = ?, ads_dom = ?, ads_korp = ?, ads_kv = ?, ads_mestn = ?, " + 
						"nastupila = ?, semp = ?, obraz = ?, zan = ?, proiz = ?, datatr = ?, vid_tr = ?, vrem_tr = ?, obst = ?, ustan = ?, cvrach = ?, " + 
						"cdol = ?, osn = ?, psm_a = ?, psm_an = ?, psm_ak = ?, psm_ad = ?, psm_b = ?, psm_bn = ?, psm_bk = ?, psm_bd = ?, " + 
						"psm_v = ?, psm_vn = ?, psm_vk = ?, psm_vd = ?, psm_g = ?, psm_gn = ?, psm_gk = ?, psm_gd = ?, psm_p = ?, psm_pn = ?, " + 
						"psm_pk = ?, psm_pd = ?, psm_p1 = ?, psm_p1n = ?, psm_p1k = ?, psm_p1d = ?, psm_p2 = ?, psm_p2n = ?, psm_p2k = ?, " + 
						"psm_p2d = ?, dtp = ?, umerla = ?, cuser = ?, clpu = ?, fio_r = ?, don = ?, ves = ?, nreb = ?, mrojd = ?, fam_m = ?, " + 
						"im_m = ?, ot_m = ?, datarm = ?, dataz = ?, fio_pol = ?, vdok = ?, sdok = ?, ndok = ?, dvdok = ?, kvdok = ?, " + 
						"vz_ser = ?, vz_nomer = ?, vz_datav = ?, adm_raion = ?  WHERE npasp = ?";

				sme.execPreparedT(sql, false, npasp, mssTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 1);
				sme.setCommit();
			} catch (MssNotFoundException e) {
				try {
					sql = "INSERT INTO p_smert (npasp, ser, nomer, vid, datav, datas, vrems, " + 
						"ads_obl, ads_raion, ads_gorod, ads_ul, ads_dom, ads_korp, ads_kv, ads_mestn, " + 
						"nastupila, semp, obraz, zan, proiz, datatr, vid_tr, vrem_tr, obst, ustan, cvrach, " + 
						"cdol, osn, psm_a, psm_an, psm_ak, psm_ad, psm_b, psm_bn, psm_bk, psm_bd, " + 
						"psm_v, psm_vn, psm_vk, psm_vd, psm_g, psm_gn, psm_gk, psm_gd, psm_p, psm_pn, " + 
						"psm_pk, psm_pd, psm_p1, psm_p1n, psm_p1k, psm_p1d, psm_p2, psm_p2n, psm_p2k, " + 
						"psm_p2d, dtp, umerla, cuser, clpu, fio_r, don, ves, nreb, mrojd, fam_m, " + 
						"im_m, ot_m, datarm, dataz, fio_pol, vdok, sdok, ndok, dvdok, kvdok, " + 
						"vz_ser, vz_nomer, vz_datav, adm_raion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
					sme.execPreparedT(sql, false, npasp, mssTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80);
					sme.setCommit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (InterruptedException | SqlExecutorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delPsmert(int npasp) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_smert WHERE npasp = ? ", false, npasp);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public PatientCommonInfo getPatientCommonInfo(int npasp)
			throws KmiacServerException, TException, PatientNotFoundException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, pol,adm_obl,adm_gorod, adm_ul, adm_dom, adm_korp, " +
				"adm_kv, region_liv FROM patient WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return mssPatient.map(acrs.getResultSet());
			else
				throw new PatientNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public Psmertdop getPsmertdop(int cpodr) throws MssdopNotFoundException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_smert_dop WHERE cpodr = ? ", cpodr)) {
			if (acrs.getResultSet().next())
				return mssDop.map(acrs.getResultSet());
			else
				throw new MssdopNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}

	}

	@Override
	public int setPsmertdop(Psmertdop cpodr) throws TException {
		String sql;
		
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPsmertdop(cpodr.getCpodr());
				
				sql = "UPDATE p_smert_dop SET prizn = ?, nomer_n = ?, nomer_k = ?, nomer_t = ?  WHERE cpodr = ? and cslu = ?" ;
				sme.execPreparedT(sql, false, cpodr, dopTypes, 3, 4, 5, 6, 1);
				sme.setCommit();
			} catch (MssdopNotFoundException e) {
				try {
					sql = "INSERT INTO p_smert_dop (cpodr, cslu, prizn, nomer_n, nomer_k, nomer_t) VALUES (?, ?, ?, ?, ?, ?)";
					sme.execPreparedT(sql, false, cpodr, dopTypes, 1, 2, 3, 4, 5, 6);
					sme.setCommit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (InterruptedException | SqlExecutorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public PatientMestn getL00(int c_ffomc, String nam_kem)
			throws MestnNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT vid_np, c_ffomc, nam_kem  FROM n_l00 WHERE (c_ffomc = ?) and (nam_kem = ?)", c_ffomc, nam_kem)) {
			if (acrs.getResultSet().next())
				return mssMestn.map(acrs.getResultSet());
			else
				throw new MestnNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public UserFio getUserFio(int pcod) throws UserNotFoundException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, fam, im, ot From s_vrach where pcod = ?", pcod)) {
			if (acrs.getResultSet().next())
				return mssUser.map(acrs.getResultSet());
			else
				throw new UserNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
		
		}

	@Override
	public List<IntegerClassifier> gets_vrach(int clpu, int cslu, int cpodr) throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT s.pcod, s.fam||' '||s.im||' '||s.ot as name FROM s_vrach s, s_mrab r where s.pcod=r.pcod and r.priznd = 1 and r.clpu = ? and r.cslu = ? and r.cpodr = ?",clpu,cslu,cpodr)) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> gets_dolj(int clpu, int cslu, int cpodr)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT s.pcod, s.name FROM n_s00 s, s_mrab r where s.pcod=r.cdol and r.priznd = 1 and r.clpu = ? and r.cslu = ? and r.cpodr = ?",clpu,cslu,cpodr)) {
			return mssStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z00() throws TException, KmiacServerException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod_s AS pcod, name_s AS name FROM n_z00 ")) {
			return 	mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

		
}




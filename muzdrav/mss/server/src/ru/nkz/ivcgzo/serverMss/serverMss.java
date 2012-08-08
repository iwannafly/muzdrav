package ru.nkz.ivcgzo.serverMss;

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
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMss.MssNotFoundException;
import ru.nkz.ivcgzo.thriftMss.MssdopNotFoundException;
import ru.nkz.ivcgzo.thriftMss.P_smert;
import ru.nkz.ivcgzo.thriftMss.Psmertdop;
import ru.nkz.ivcgzo.thriftMss.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftMss.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftMss.ThriftMss;
import ru.nkz.ivcgzo.thriftMss.ThriftMss.Iface;

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

	
	public serverMss(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		mssDoc = new TResultSetMapper<>(P_smert.class, "id", "npasp", "ser", "nomer", "vid", "datav", "datas", "vrems", 
				"ads_obl", "ads_raion", "ads_gorod", "ads_ul", "ads_dom", "ads_korp", "ads_kv", "ads_mestn", 
				"nastupila", "semp", "obraz", "zan", "proiz", "datatr", "vid_tr", "obst", "ustan", "cvrach",
				"cdol", "osn", "psm_a", "psm_an", "psm_ak", "psm_ad", "psm_b", "psm_bn", "psm_bk", "psm_bd",
				"psm_v", "psm_vn", "psm_vk", "psm_vd", "psm_g", "psm_gn", "psm_gk", "psm_gd", "psm_p", "psm_pn",
				"psm_pk", "psm_pd", "psm_p1", "psm_p1n", "psm_p1k", "psm_p1d", "psm_p2", "psm_p2n", "psm_p2k",
				"psm_p2d", "dtp", "umerla", "cuser", "clpu", "fio_r", "don", "ves", "nreb", "mrojd", "fam_m", 
				"im_m", "ot_m", "datarm", "dataz", "fio_pol", "sdok", "ndok", "dvdok", "kvdok", "gpol", "upol", 
				"dpol", "kpol");
		mssTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class,
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, Integer.class, Integer.class,
				String.class, Integer.class, String.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class, String.class,
				String.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class, String.class, String.class, String.class,
				Integer.class, String.class, String.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class,
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class,
				String.class, String.class, Date.class, Date.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class};
		
		mssPatient = new TResultSetMapper<>(PatientCommonInfo.class, "npasp","fam", "im", "ot", "datar", "pol", "adm_obl", "adm_gorod", "adm_dom", "adm_korp", "adm_kvart", "mrab", "name_mr");
		patTypes = new Class<?>[]{Integer.class,String.class,String.class,String.class,Date.class,Integer.class,String.class,String.class,String.class,String.class,String.class,Integer.class,String.class};
		
		mssDop = new TResultSetMapper<>(Psmertdop.class, "cpodr", "cslu", "prizn", "nomer_n", "nomer_k", "nomer_t");
		dopTypes = new Class<?>[] {Integer.class,Integer.class,Boolean.class,Integer.class,Integer.class,Integer.class};
		
		mssClass = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		intcTypes = new Class<?>[] {Integer.class, String.class};
	
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
						"nastupila = ?, semp = ?, obraz = ?, zan = ?, proiz = ?, datatr = ?, vid_tr = ?, obst = ?, ustan = ?, cvrach = ?, " + 
						"cdol = ?, osn = ?, psm_a = ?, psm_an = ?, psm_ak = ?, psm_ad = ?, psm_b = ?, psm_bn = ?, psm_bk = ?, psm_bd = ?, " + 
						"psm_v = ?, psm_vn = ?, psm_vk = ?, psm_vd = ?, psm_g = ?, psm_gn = ?, psm_gk = ?, psm_gd = ?, psm_p = ?, psm_pn = ?, " + 
						"psm_pk = ?, psm_pd = ?, psm_p1 = ?, psm_p1n = ?, psm_p1k = ?, psm_p1d = ?, psm_p2 = ?, psm_p2n = ?, psm_p2k = ?, " + 
						"psm_p2d = ?, dtp = ?, umerla = ?, cuser = ?, clpu = ?, fio_r = ?, don = ?, ves = ?, nreb = ?, mrojd = ?, fam_m = ?, " + 
						"im_m = ?, ot_m = ?, datarm = ?, dataz = ?, fio_pol = ?, sdok = ?, ndok = ?, dvdok = ?, kvdok = ?, gpol = ?, upol = ?, " + 
						"dpol = ?, kpol = ?, vdok = ? WHERE npasp = ?";

				sme.execPreparedT(sql, false, npasp, mssTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 1);
				sme.setCommit();
			} catch (MssNotFoundException e) {
				try {
					sql = "INSERT INTO p_smert (npasp, ser, nomer, vid, datav, datas, vrems, " + 
						"ads_obl, ads_raion, ads_gorod, ads_ul, ads_dom, ads_korp, ads_kv, ads_mestn, " + 
						"nastupila, semp, obraz, zan, proiz, datatr, vid_tr, obst, ustan, cvrach, " + 
						"cdol, osn, psm_a, psm_an, psm_ak, psm_ad, psm_b, psm_bn, psm_bk, psm_bd, " + 
						"psm_v, psm_vn, psm_vk, psm_vd, psm_g, psm_gn, psm_gk, psm_gd, psm_p, psm_pn, " + 
						"psm_pk, psm_pd, psm_p1, psm_p1n, psm_p1k, psm_p1d, psm_p2, psm_p2n, psm_p2k, " + 
						"psm_p2d, dtp, umerla, cuser, clpu, fio_r, don, ves, nreb, mrojd, fam_m, " + 
						"im_m, ot_m, datarm, dataz, fio_pol, sdok, ndok, dvdok, kvdok, gpol, upol, " + 
						"dpol, kpol, vdok) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					sme.execPreparedT(sql, false, npasp, mssTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79);
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
	public void delPsmert(P_smert npasp) throws TException {
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, pol,adm_obl,adm_gorod, adm_dom,adm_korp," +
				"adm_kvart, mrab, name_mr FROM patient WHERE npasp = ? ", npasp)) {
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
	public List<IntegerClassifier> get_n_z60() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z60 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z10() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z10 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z42() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z42 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z43() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z43 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z00() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z00 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z70() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z70 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

//	@Override
//	public List<IntegerClassifier> get_n_l00() throws KmiacServerException,
//			TException {
//		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_l00 ")) {
//			return mssClass.mapToList(acrs.getResultSet());
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new KmiacServerException();
//		}
//
//	}

	@Override
	public List<IntegerClassifier> get_n_z01() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z01 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z80() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z80 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_z90() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z90 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<IntegerClassifier> get_n_r0l() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_r0l ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_u50() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_u50 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_p07() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p07 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_az0() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_az0 ")) {
			return mssClass.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}


}

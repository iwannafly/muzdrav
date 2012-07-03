package ru.nkz.ivcgzo.serverMss;

import java.sql.Date;
import java.sql.SQLException;

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
import ru.nkz.ivcgzo.thriftMss.MssNotFoundException;
import ru.nkz.ivcgzo.thriftMss.P_smert;
import ru.nkz.ivcgzo.thriftMss.ThriftMss;
import ru.nkz.ivcgzo.thriftMss.ThriftMss.Iface;

public class serverMss extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<P_smert, P_smert._Fields> mssDoc;
	private static Class<?>[] mssTypes; 

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
						"dpol = ?, kpol = ? WHERE npasp = ?";

				sme.execPreparedT(sql, false, npasp, mssTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 1);
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
						"dpol, kpol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					sme.execPreparedT(sql, false, npasp, mssTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78);
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
		// TODO Auto-generated method stub
		
	}

}

package ru.nkz.ivcgzo.serverDisp;

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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftDisp.Pdisp_ds;
import ru.nkz.ivcgzo.thriftDisp.Pfiz;
import ru.nkz.ivcgzo.thriftDisp.PfizNotFoundException;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp.Iface;

public class ServerDisp extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<Pfiz, Pfiz._Fields> rsmPfiz;
	private final Class<?>[] pfizTypes; 
	private final TResultSetMapper<Pdisp_ds, Pdisp_ds._Fields> rsmPdisp_ds;
	private final Class<?>[] pdispdsTypes;

	public ServerDisp(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmPfiz = new TResultSetMapper<>(Pfiz.class, "npasp", "pe", "pp","pi", "fv","fr", "grzd", "prb", "prk",
				"ves", "rost", "dataz", "vrk", "pfm1", "pfm2", "pfm3", "pfd1", "pfd2", "pfd3", "pfd4", "pfd5", 
				"pfd6", "prs", "priv", "priv_pr", "priv_n", "bcg", "polio", "akds", "adsm", "adm", "kor", 
				"parotit", "krasn", "gepatit", "bcg_vr", "polio_vr", "akds_vr", "kor_vr", "parotit_vr", "krasn_vr", 
				"gepatit_vr", "dat_p", "profil", "vedom", "vib1", "vib2", "ipr", "dat_ipr", "menses", "menses1", 
				"okr", "pf1", "mf1", "ef1", "rf1", "grzd_s");
		pfizTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Double.class, Double.class, Date.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class,Integer.class, Integer.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmPdisp_ds = new TResultSetMapper<>(Pdisp_ds.class, "npasp", "id", "d_do","diag_do", "obs_n","obs_v", 
				"lech_n", "lech_v", "d_po", "diag_po", "xzab", "pu", "disp", "vmp1", "vmp2", "vrec1", "vrec2",
				"vrec3", "vrec4", "vrec5", "vrec6",	"vrec7", "vrec8", "vrec9", "vrec10", "nrec1", "nrec2",
				"nrec3", "nrec4", "nrec5", "nrec6", "nrec7", "parotit", "krasn", "gepatit", "bcg_vr", "polio_vr", "akds_vr", "kor_vr", "parotit_vr", "krasn_vr", 
				"gepatit_vr", "dat_p", "profil", "vedom", "vib1", "vib2", "ipr", "dat_ipr", "menses", "menses1", 
				"okr", "pf1", "mf1", "ef1", "rf1", "grzd_s");
		pdispdsTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Double.class, Double.class, Date.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class,Integer.class, Integer.class, 
				String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
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
				sme.execPreparedT("update p_disp set pe=?, pp=?, pi=?, fv=?, fr=?, grzd=?, prb=?, prk=?, ves=?, rost=?, dataz=?, vrk=?, pfm1=?, pfm2=?, pfm3=?, pfd1=?, pfd2=?, pfd3=?, pfd4=?, pfd5=?, pfd6=?, prs=?, priv=?, priv_pr=?, priv_n=?, bcg=?, polio=?, akds=?, adsm=?, adm=?, kor=?, parotit=?, krasn=?, gepatit=?, bcg_vr=?, polio_vr=?, akds_vr=?, kor_vr=?, parotit_vr=?, krasn_vr=?, gepatit_vr=?, dat_p=?, profil=?, vedom=?, vib1=?, vib2=?, ipr=?, dat_ipr=?, menses=?, menses1=?, okr=?, pf1=?, mf1=?, ef1=?, rf1=?, grzd_s=? where npasp = ? ", false, fiz, pfizTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 0);
				sme.setCommit();
			} catch (PfizNotFoundException e) {
				sme.execPreparedT("insert into p_fiz (npasp, pe, pp, pi, fv, fr, grzd, prb, prk, ves, rost, dataz, vrk, pfm1, pfm2, pfm3, pfd1, pfd2, pfd3, pfd4, pfd5, pfd6, prs, priv, priv_pr, priv_n, bcg, polio, akds, adsm, adm, kor, parotit, krasn, gepatit, bcg_vr, polio_vr, akds_vr, kor_vr, parotit_vr, krasn_vr, gepatit_vr, dat_p, profil, vedom, vib1, vib2, ipr, dat_ipr, menses, menses1, okr, pf1, mf1, ef1, rf1, grzd_s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", false, fiz, pfizTypes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56);
				sme.setCommit();
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
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

}

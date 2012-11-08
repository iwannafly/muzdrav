package ru.nkz.ivcgzo.serverKartaRInv;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TFieldIdEnum;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftKartaRInv.Pinvk;
import ru.nkz.ivcgzo.thriftKartaRInv.PinvkNotFoundException;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv.Iface;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv;
import ru.nkz.ivcgzo.thriftKartaRInv.PatientCommonInfo;



public class ServerKartaRInv extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<Pinvk, Pinvk._Fields> invk;
	private static Class<?>[] invkTypes; 
	private TResultSetMapper<PatientCommonInfo, PatientCommonInfo._Fields> invkPatient;
	private static Class<?>[] patTypes;
	
	public ServerKartaRInv(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		invk = new TResultSetMapper<>(Pinvk.class, "ninv", "npasp", "dataz", "datav", "vrach", "mesto1", "preds",
			    "uchr", "nom_mse", "name_mse", "ruk_mse", "rez_mse", "d_osv", "d_otpr", "d_inv", "d_invp", "d_srok",
				"srok_inv", "diag", "diag_s1", "diag_s2", "diag_s3", "oslog", "factor", "fact1", "fact2",
				"fact3", "fact4", "prognoz", "potencial", "klin_prognoz", "med_reab", "ps_reab", "prof_reab", 
				"soc_reab",	"zakl", "zakl_name",  "nar1", "nar2", "nar3", "nar4", "nar5", "nar6",
				"ogr1", "ogr2", "ogr3", "ogr4", "ogr5", "ogr6", "ogr7", "mr1n", "mr2n", "mr3n", "mr4n",
				"mr5n", "mr6n", "mr7n", "mr8n", "mr9n", "mr10n", "mr11n", "mr12n", "mr13n", "mr14n", "mr15n",
				"mr16n", "mr17n", "mr18n", "mr19n", "mr20n","mr21n", "mr22n", "mr23n", "mr1v", "mr2v", "mr3v", "mr4v",
				"mr5v", "mr6v", "mr7v", "mr8v", "mr9v", "mr10v", "mr11v", "mr12v", "mr13v", "mr14v", "mr15v",
				"mr16v", "mr17v", "mr18v", "mr19v", "mr20v","mr21v", "mr22v", "mr23v", "mr1d", "mr2d", "mr3d", "mr4d",
				"pr1n", "pr2n", "pr3n", "pr4n",	"pr5n", "pr6n", "pr7n", "pr8n", "pr9n", "pr10n", "pr11n", "pr12n",
				"pr13n", "pr14n", "pr15n", "pr16n", "pr1v", "pr2v", "pr3v", "pr4v",	"pr5v", "pr6v", "pr7v", "pr8v", 
				"pr9v", "pr10v", "pr11v", "pr12v", "pr13v", "pr14v", "pr15v", "pr16v", "pr1d");
		
		invkTypes = new Class<?>[] {Integer.class, Integer.class, Date.class, Date.class, String.class,Integer.class, Integer.class,
				String.class, String.class, String.class, String.class, Integer.class, Date.class, Date.class, Date.class, Date.class, Date.class,
				Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, 
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,								Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class,
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class};
		// TODO Auto-generated constructor stub
		invkPatient = new TResultSetMapper<>(PatientCommonInfo.class, "npasp","fam", "im", "ot", "datar", "pol", "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_korp", "adm_kv", "region_liv");
		patTypes = new Class<?>[]{Integer.class,String.class,String.class,String.class,Date.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class};
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
		thriftKartaRInv.Processor<Iface> proc = new thriftKartaRInv.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
			}

	@Override
	public Pinvk getPinvk(int npasp, int ninv) throws KmiacServerException,
			PinvkNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_invk WHERE npasp = ? ", npasp)) {
		//	if (acrs.getResultSet().next())
		//		return mssDoc.map(acrs.getResultSet());
		//	else
		//		throw new MssNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		
	}
		return null;
	}

	@Override
	public List<Pinvk> getAllPinvk(int npasp) throws PinvkNotFoundException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPinvk(Pinvk invk) throws KmiacServerException, TException {
String sql;
		
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sql = "UPDATE p_invk SET dataz = ?, datav = ?, vrach = ?, mesto1 = ?, preds = ?, uchr = ?, " + 
					"nom_mse = ?, name_mse = ?, ruk_mse = ?, rez_mse = ?, d_osv = ?, d_otpr = ?, d_inv = ?, d_invp = ?, " + 
					"d_srok = ?, srok_inv = ?, diag = ?, diag_s1 = ?, diag_s2 = ?, diag_s3 = ?, oslog = ?, factor = ?, fact1 = ?, fact2 = ?, fact3 = ?, " + 
					"fact4 = ?, prognoz= ?, potencial = ?, klin_prognoz = ?, med_reab = ?, ps_reab = ?, prof_reab = ?, soc_reab = ?, zakl = ?, zakl_name = ?, " + 
					"nar1= ?, nar2 = ?, nar3 = ?, nar4 = ?, nar5 = ?, nar6 = ?, ogr1 = ?, ogr2 = ?, ogr3 = ?, ogr4 = ?, " + 
					"ogr5 = ?, ogr6= ?, ogr7 = ?, mr1n = ?, mr2n = ?, mr3n = ?, mr4n = ?, mr5n = ?, mr6n = ?, " + 
					"mr7n= ?, mr8n = ?, mr9n = ?, mr10n = ?, mr11n = ?, mr12n = ?, mr13n = ?, mr14n = ?, mr15n = ?, mr16n = ?, " + 
					"mr17n= ?, mr18n = ?, mr19n = ?, mr20n = ?, mr21n = ?, mr22n= ?, mr23n = ?,  mr1v = ?, mr2v = ?, mr3v = ?, mr4v = ?, mr5v = ?, mr6v = ?, " + 
					"mr7v= ?, mr8v = ?, mr9v = ?, mr10v = ?, mr11v = ?, mr12v = ?, mr13v = ?, mr14v = ?, mr15v = ?, mr16v = ?, " + 
					"mr17v= ?, mr18v = ?, mr19v = ?, mr20v = ?, mr21v = ?, mr22v= ?, mr23v = ?,   mr1d = ?, mr2d = ?, mr3d = ?, mr4d = ?, " + 
					"pr1n = ?, pr2n = ?, pr3n = ?, pr4n = ?, pr5n = ?, pr6n = ?, pr7n = ?, pr8n= ?, pr9n= ?, pr10n = ?, " + 
					"pr11n = ?, pr12n = ?, pr13n = ?, pr14n = ?, pr15n = ?, pr16n = ?, pr1v = ?, pr2v = ?, pr3v = ?, pr4v = ?, pr5v = ?, pr6v = ?, pr7v = ?, " +
					"pr8v= ?, pr9v= ?, pr10v = ?, pr11v = ?, pr12v = ?, pr13v = ?, pr14v = ?, "+
					"pr15v = ?, pr16v = ? , pr1d = ? WHERE npasp = ?";

//	sme.execPreparedT(sql, false, npasp, invkTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 1);
			sme.setCommit();
		} catch (InterruptedException | SqlExecutorException e) {
			try {
				sql = "INSERT INTO p_invk (ninv, npasp, dataz, datav, vrach, mesto1, preds, " +
					    "uchr, nom_mse, name_mse, ruk_mse, rez_mse, d_osv, d_otpr, d_inv, d_invp, d_srok, " +
						"srok_inv, diag, diag_s1, diag_s2, diag_s3, oslog, factor, fact1, fact2, " +
						"fact3, fact4, prognoz, potencial, klin_prognoz, med_reab, ps_reab, prof_reab, " + 
						"soc_reab,	zakl, zakl_name,  nar1, nar2, nar3, nar4, nar5, nar6, " +
						"ogr1, ogr2, ogr3, ogr4, ogr5, ogr6, ogr7, mr1n, mr2n, mr3n, mr4n, " +
						"mr5n, mr6n, mr7n, mr8n, mr9n, mr10n, mr11n, mr12n, mr13n, mr14n, mr15n, " +
						"mr16n, mr17n, mr18n, mr19n, mr20n, mr21n, mr22n, mr23n, mr1v, mr2v, mr3v, mr4v, " +
						"mr5v, mr6v, mr7v, mr8v, mr9v, mr10v, mr11v, mr12v, mr13v, mr14v, mr15v, " +
						"mr16v, mr17v, mr18v, mr19v, mr20v, mr21v, mr22v, mr23v, mr1d, mr2d, mr3d, mr4d, " +
						"pr1n, pr2n, pr3n, pr4n, pr5n, pr6n, pr7n, pr8n, pr9n, pr10n, pr11n, pr12n, " +
						"pr13n, pr14n, pr15n, pr16n, pr1v, pr2v, pr3v, pr4v, pr5v, pr6v, pr7v, pr8v, " + 
						"pr9v, pr10v, pr11v, pr12v, pr13v, pr14v, pr15v, pr16v, pr1d) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

				//sme.execPreparedT(sql, false, npasp, ninv, invkTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80);
				//sme.setCommit();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	  //     } catch (InterruptedException | SqlExecutorException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	//}
				//e.printStackTrace();
	//	}
		// TODO Auto-generated method stub
	//return 0;
	}

	@Override
	public int addPinvk(Pinvk invk) throws PinvkNotFoundException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

		@Override
	public List<IntegerClassifier> get_n_c00() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}
		

	public void DeletePinvk(int ninv) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_invk WHERE ninv = ? ", false, ninv);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void setPinvk(Pinvk invk, TBase<?, TFieldIdEnum> npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}



}

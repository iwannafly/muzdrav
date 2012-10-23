package ru.nkz.ivcgzo.serverKartaRInv;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;
import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftKartaRInv.Pinvk;
import ru.nkz.ivcgzo.thriftKartaRInv.PinvkNotFoundException;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv.Iface;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv;
import ru.nkz.ivcgzo.thriftKartaRInv.PatientCommonInfo;



public class serverKartaRInv extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<Pinvk, Pinvk._Fields> invk;
	private static Class<?>[] invkTypes; 
	private TResultSetMapper<PatientCommonInfo, PatientCommonInfo._Fields> invkPatient;
	private static Class<?>[] patTypes;
	
	public serverKartaRInv(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
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
				"pr9v", "pr10v", "pr11v", "pr12v", "pr13v", "pr14v", "pr15v", "pr16v");
		
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
				Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
		// TODO Auto-generated constructor stub
	}

	@Override
	public void testConnection() throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int addPinvk(Pinvk invk) throws PinvkNotFoundException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IntegerClassifier> get_n_v0a() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0b() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0c() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0d() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0e() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0f() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0g() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0h() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0m() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0n() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0p() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0r() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0s() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_v0t() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntegerClassifier> get_n_c00() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DeletePinvk(int ninv) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

}

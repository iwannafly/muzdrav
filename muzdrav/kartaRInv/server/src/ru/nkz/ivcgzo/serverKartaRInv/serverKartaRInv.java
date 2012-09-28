package ru.nkz.ivcgzo.serverKartaRInv;

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
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftKartaRInv.Pinvk;
import ru.nkz.ivcgzo.thriftKartaRInv.PinvkNotFoundException;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv.Iface;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv;


public class serverKartaRInv extends Server implements Iface {
	private TServer thrServ;
	public serverKartaRInv(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
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
	public void DeletePinvk(int ninv) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
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

}

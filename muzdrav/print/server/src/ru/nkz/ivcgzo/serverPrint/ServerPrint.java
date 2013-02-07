package ru.nkz.ivcgzo.serverPrint;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.server.TThreadedSelectorServer.Args;

import ru.nkz.ivcgzo.configuration;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftPrint.KartaBer;
import ru.nkz.ivcgzo.thriftPrint.Napr;
import ru.nkz.ivcgzo.thriftPrint.NaprKons;
import ru.nkz.ivcgzo.thriftPrint.Protokol;
import ru.nkz.ivcgzo.thriftPrint.SpravNetrud;
import ru.nkz.ivcgzo.thriftPrint.ThriftPrint;
import ru.nkz.ivcgzo.thriftPrint.ThriftPrint.Iface;
import ru.nkz.ivcgzo.thriftPrint.Vypis;

public class ServerPrint extends Server implements Iface {
	private TServer thrServ;

	public ServerPrint(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
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
		ThriftPrint.Processor<Iface> proc = new ThriftPrint.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();

	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();

	}

	@Override
	public String printNapr(Napr na) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printNaprKons(NaprKons nk) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printVypis(Vypis vp) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printKek(int npasp, int pvizitId)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printProtokol(Protokol pk) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printMSK(int npasp) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printAnamZab(int id_pvizit) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printSpravNetrud(SpravNetrud sn) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printKartaBer(KartaBer kb) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

}

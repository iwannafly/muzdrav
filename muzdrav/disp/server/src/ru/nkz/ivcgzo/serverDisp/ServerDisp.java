package ru.nkz.ivcgzo.serverDisp;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftDisp.Pfiz;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp.Iface;

public class ServerDisp extends Server implements Iface {
	private TServer thrServ;

	public ServerDisp(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
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
		
	}

}

package ru.nkz.ivcgzo.ServerPbol;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol.Iface;

public class serverPbol extends Server implements Iface {
	private TServer thrServ;

	public serverPbol(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
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
		ThriftPbol.Processor<Iface> proc = new ThriftPbol.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();


	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();


	}

}

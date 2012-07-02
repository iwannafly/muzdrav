package ru.nrz.ivcgzo.serverOutputInfo;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo.Iface;

public class OutputInfo extends Server implements Iface {
	private TServer thrServ;
	public OutputInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
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
		ThriftOutputInfo.Processor<Iface> proc = new ThriftOutputInfo.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

}

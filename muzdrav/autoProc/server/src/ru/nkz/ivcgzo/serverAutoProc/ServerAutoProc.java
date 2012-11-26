package ru.nkz.ivcgzo.serverAutoProc;

import java.sql.SQLException;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.thriftServerAutoProc.ThriftServerAutoProc;
import ru.nkz.ivcgzo.thriftServerAutoProc.ThriftServerAutoProc.Iface;

public class ServerAutoProc extends Server implements Iface {
	private TServer thrServ;
	
	public ServerAutoProc(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}

	@Override
	public void start() throws Exception {
		try {
			ThriftServerAutoProc.Processor<Iface> proc = new ThriftServerAutoProc.Processor<Iface>(this);
			thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
			thrServ.serve();
		} catch (TException e) {
			throw new Exception(e);
		}
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public void testConnection() throws TException {
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new TException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new TException();
		}
	}

}

package ru.nkz.ivcgzo.serverAuth;

import java.io.File;
import java.io.IOException;
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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.Constants;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.FileNotFoundException;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.OpenFileException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth.Iface;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;
import ru.nkz.ivcgzo.thriftServerAuth.UserNotFoundException;

public class ServerAuth extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<UserAuthInfo, UserAuthInfo._Fields> rsmAuth;
	private SocketManager scMan;

	public ServerAuth(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmAuth = new TResultSetMapper<>(UserAuthInfo.class, "pcod", "clpu", "cpodr", "pdost", "name");
		
		scMan = new SocketManager(5, Constants.bufSize);
	}

	@Override
	public void start() throws Exception {
		ThriftServerAuth.Processor<Iface> proc = new ThriftServerAuth.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
		thrServ.stop();
	}

	@Override
	public UserAuthInfo auth(String login, String password) throws UserNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT u.pcod, u.clpu, u.cpodr, u.pdost, v.fam || ' ' || v.im || ' ' || v.ot AS name FROM s_users u join s_vrach v ON (v.pcod = u.pcod) WHERE (u.login = ?) AND (u.password = ?) ", login, password)) {
			if (acrs.getResultSet().next())
				return rsmAuth.map(acrs.getResultSet());
			else
				throw new UserNotFoundException();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new TException(e);
		}
	}

	@Override
	public String getServerVersion() throws TException {
		return configuration.appVersion;
	}

	@Override
	public String getClientVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int openReadServerSocket(String path) throws FileNotFoundException, OpenFileException, TException {
		try {
			if (!new File(path).exists())
				throw new FileNotFoundException();
			return scMan.openRead(path);
		} catch (IOException e) {
			throw new TException(e);
		} catch (InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public int openWriteServerSocket(String path) throws OpenFileException, TException {
		try {
			return scMan.openWrite(path);
		} catch (IOException e) {
			throw new TException(e);
		} catch (InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void closeServerSocket(int port, boolean delFile) throws TException {
		scMan.close(port, delFile);
	}

}

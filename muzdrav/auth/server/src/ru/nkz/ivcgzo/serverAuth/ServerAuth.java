package ru.nkz.ivcgzo.serverAuth;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
import ru.nkz.ivcgzo.thriftCommon.libraryUpdater.LibraryInfo;
import ru.nkz.ivcgzo.thriftCommon.libraryUpdater.ModuleNotFound;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth.Iface;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;
import ru.nkz.ivcgzo.thriftServerAuth.UserNotFoundException;

public class ServerAuth extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<UserAuthInfo, UserAuthInfo._Fields> rsmAuth;
	private TResultSetMapper<LibraryInfo, LibraryInfo._Fields> rsmLibInfo;
	private SocketManager scMan;
	private RemoteInstaller remInst;

	public ServerAuth(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmAuth = new TResultSetMapper<>(UserAuthInfo.class, "pcod", "clpu", "cpodr", "pdost", "name");
		rsmLibInfo = new TResultSetMapper<>(LibraryInfo.class, "id", "name", "md5", "size");
		
		scMan = new SocketManager(5, Constants.bufSize);
		
		remInst = new RemoteInstaller(sse);
	}

	@Override
	public void start() throws Exception {
		try {
			remInst.startListen();
		} catch (IOException e) {
			throw new Exception("Error starting remote installation service.", e);
		}
		
		try {
			ThriftServerAuth.Processor<Iface> proc = new ThriftServerAuth.Processor<Iface>(this);
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

	@Override
	public List<LibraryInfo> getModulesList() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id, name, md5, size FROM libs WHERE (id > 0) ")) {
			return rsmLibInfo.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int openModuleReadSocket(int id) throws ModuleNotFound, OpenFileException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT name FROM libs WHERE (id = ?) ", id)) {
			acrs.getResultSet().next();
			File modFile = getModuleFile(acrs.getResultSet().getString(1));
			if (!modFile.exists())
				throw new ModuleNotFound();
			return scMan.openRead(modFile.getAbsolutePath());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OpenFileException();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new OpenFileException();
		}
	}
	
	private File getModuleFile(String name) {
		File root = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile();
		File cliDir = new File(root, "client");
		return new File(cliDir, name);
	}

	@Override
	public void closeReadSocket(int port) throws TException {
		closeServerSocket(port, false);
	}

	@Override
	public int getFileSize(int port) throws TException {
		return scMan.getSize(port);
	}

}

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
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.fileTransferConstants;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.FileNotFoundException;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.OpenFileException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftCommon.libraryUpdater.LibraryInfo;
import ru.nkz.ivcgzo.thriftCommon.libraryUpdater.ModuleNotFound;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth.Iface;
import ru.nkz.ivcgzo.thriftServerAuth.UserNotFoundException;

public class ServerAuth extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<UserAuthInfo, UserAuthInfo._Fields> rsmAuth;
	private TResultSetMapper<LibraryInfo, LibraryInfo._Fields> rsmLibInfo;
	private SocketManager scMan;
	private RemoteInstaller remInst;
	
	public ServerAuth(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmAuth = new TResultSetMapper<>(UserAuthInfo.class, "pcod", "clpu", "cpodr", "pdost", "name", "id", "config", "cdol", "cdol_name", "name_short", "cpodr_name", "clpu_name", "cslu", "cslu_name", "cspec", "cspec_name", "c_nom", "kdate", "kateg", "priznd", "priznd_name");
		rsmLibInfo = new TResultSetMapper<>(LibraryInfo.class, "id", "name", "md5", "size", "req");
		
		scMan = new SocketManager(5, fileTransferConstants.bufSize);
		
		remInst = new RemoteInstaller(sse);
	}

	@Override
	public int getId() {
		return configuration.appId;
	}
	
	@Override
	public int getPort() {
		return configuration.thrPort;
	}
	
	@Override
	public String getName() {
		return configuration.appName;
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
		if (remInst != null)
			remInst.stopListen();
	}

	@Override
	public UserAuthInfo auth(String login, String password) throws UserNotFoundException, TException {
		String cpodrTableName;
		String n00KategField;
		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT r.cslu, v.pcod, u.id, u.pdost FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) JOIN s_mrab r ON (r.pcod = u.pcod AND r.cpodr = u.cpodr) LEFT JOIN n_p0s p ON (r.cslu = p.pcod) WHERE (u.login = ?) AND (u.password = ?) ", login, password)) {
			if (acrs.getResultSet().next())
				switch (acrs.getResultSet().getInt(1)) {
				case -1:
					return rsmAuth.map(acrs.getResultSet());
				case 1:
					cpodrTableName = "n_o00";
					n00KategField = "''::character varying(1)";
					break;
				case 2:
					cpodrTableName = "n_n00";
					n00KategField = "cpn.kateg";
					break;
				case 3:
					cpodrTableName = "n_lds";
					n00KategField = "''::character varying(1)";
					break;
				case 4:
				case 5:
					cpodrTableName = "n_t40";
					n00KategField = "''::character varying(1)";
					break;
				default:
					throw new UserNotFoundException();
				}
			else
				throw new UserNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
		
		String sql = String.format("SELECT u.pcod, u.clpu, u.cpodr, u.pdost, v.fam || ' ' || v.im || ' ' || v.ot AS name, u.id, u.config, r.cdol, s.name AS cdol_name, get_short_fio(v.fam, v.im, v.ot) AS name_short, cpn.name AS cpodr_name, m.name AS clpu_name, p.pcod AS cslu, p.name AS cslu_name, ns.pcod AS cspec, ns.name AS cspec_name, m.c_nom, np.kdate, %s, r.priznd, nd.name AS priznd_name FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) JOIN s_mrab r ON (r.pcod = u.pcod AND r.cpodr = u.cpodr) JOIN n_s00 s ON (s.pcod = r.cdol) JOIN n_m00 m ON (m.pcod = u.clpu) LEFT JOIN %s cpn ON (cpn.pcod = u.cpodr) JOIN n_p0s p ON (r.cslu = p.pcod) JOIN n_spec ns ON (ns.pcod = s.spec) LEFT JOIN n_nsipol np ON (np.kdlpu = u.clpu AND np.kdpodr = u.cpodr) JOIN n_priznd nd ON (nd.pcod = r.priznd) WHERE (u.login = ?) AND (u.password = ?) ", n00KategField, cpodrTableName);
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, login, password)) {
			if (acrs.getResultSet().next())
				return rsmAuth.map(acrs.getResultSet());
			else
				throw new UserNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
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
	public void testConnection() throws TException {
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
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id, name, md5, size, req FROM s_libs WHERE (id > 0) ")) {
			return rsmLibInfo.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int openModuleReadSocket(int id) throws ModuleNotFound, OpenFileException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT name FROM s_libs WHERE (id = ?) ", id)) {
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

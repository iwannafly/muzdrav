package ru.nkz.ivcgzo.serverStaticInputVrachInfo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

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
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.MestoRab;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.MestoRabExistsException;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.MestoRabNotFoundException;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.VrachExistsException;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.VrachNotFoundException;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfoAdmin.ThriftServerStaticInputVrachInfoAdmin.Iface;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfoAdmin.ThriftServerStaticInputVrachInfoAdmin;

public class serverStaticInputVrachInfo extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<VrachInfo, VrachInfo._Fields> rsmVrach;
	private static final Class<?>[] vrachTypes = new Class<?>[] {Integer.class, String.class, String.class, String.class, Short.class, Date.class, Short.class, String.class, String.class}; 
	private static final Class<?>[] mrabTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class}; 
	private TResultSetMapper<MestoRab, MestoRab._Fields> rsmMrab;
	private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmClf;
	
	@Override
	public void start() throws Exception {
		ThriftServerStaticInputVrachInfoAdmin.Processor<Iface> proc = new ThriftServerStaticInputVrachInfoAdmin.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
		thrServ.stop();
	}

	
	public serverStaticInputVrachInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);

		rsmVrach = new TResultSetMapper<>(VrachInfo.class, "pcod", "fam", "im", "ot", "pol", "datar", "obr", "snils", "idv");
		rsmMrab = new TResultSetMapper<>(MestoRab.class, "id", "pcod", "clpu", "cslu", "cpodr", "cdol", "datau", "priznd");
		rsmClf = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
	}

	@Override
	public List<VrachInfo> GetVrachList() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT * FROM s_vrach ")) {
			return rsmVrach.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public VrachInfo GetVrach(int pcod) throws VrachNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM s_vrach WHERE pcod = ? ", pcod)) {
			if (acrs.getResultSet().next())
				return rsmVrach.map(acrs.getResultSet());
			else
				throw new VrachNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int AddVrach(VrachInfo vr) throws VrachExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (!isVrachExists(vr)) {
				sme.execPrepared("INSERT INTO s_vrach (fam, im, ot, pol, datar, obr, snils, idv) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, vr, VrachInfo._Fields.values(), vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8);
				int pcod = sme.getGeneratedKeys().getInt("pcod");
				sme.setCommit();
				return pcod;
			} else
				throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void UpdVrach(VrachInfo vr) throws VrachExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (!isVrachExists(vr)) {
				sme.execPrepared("UPDATE s_vrach SET fam = ?, im = ?, ot = ?, pol = ?, datar = ?, obr = ?, snils = ?, idv = ? WHERE pcod = ? ", false, vr, VrachInfo._Fields.values(), vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8, 0);
				sme.setCommit();
			} else
				throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelVrach(int pcod) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_vrach WHERE pcod = ? ", false, pcod);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<MestoRab> GetMrabList(int vrPcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_mrab WHERE pcod = ?", vrPcod)) {
			return rsmMrab.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public MestoRab GetMrab(int id) throws MestoRabNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_mrab WHERE id = ? ", id)) {
			if (acrs.getResultSet().next())
				return rsmMrab.map(acrs.getResultSet());
			else
				throw new MestoRabNotFoundException();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public int AddMrab(MestoRab mr) throws MestoRabExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (!isMrabExists(mr)) {
				sme.execPrepared("INSERT INTO s_mrab (pcod, clpu, cslu, cpodr, cdol, datau, priznd) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, mr, MestoRab._Fields.values(), mrabTypes, 1, 2, 3, 4, 5, 6, 7);
				int id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;
			} else
				throw new MestoRabExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public void UpdMrab(MestoRab mr) throws MestoRabExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (!isMrabExists(mr)) {
				sme.execPrepared("UPDATE s_mrab SET cslu = ?, cpodr = ?, cdol = ?, datau = ?, priznd = ? WHERE id = ? ", false, mr, MestoRab._Fields.values(), mrabTypes, 3, 4, 5, 6, 7, 0);
				sme.setCommit();
			} else
				throw new MestoRabExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelMrab(int id) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_mrab WHERE id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void ClearVrachMrab(int vrPcod) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_mrab WHERE pcod = ? ", false, vrPcod);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}
	

	@Override
	public String getLogin(int vrachPcod, int lpuPcod, int podrPcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT login FROM s_users WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", vrachPcod, lpuPcod, podrPcod)) {
			if (acrs.getResultSet().next())
				return acrs.getResultSet().getString(1);
			else
				return "";
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public String setPassword(int vrachPcod, int lpuPcod, int podrPcod, String login) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction(); AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod FROM s_users WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", vrachPcod, lpuPcod, podrPcod)) {
			while (true) {
				try {
					String pass = generatePassword(login);
					if (acrs.getResultSet().next())
						sme.execPrepared("UPDATE s_users SET login = ?, password = ? WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, login, pass, vrachPcod, lpuPcod, podrPcod);
					else
						sme.execPrepared("INSERT INTO s_users (pcod, login, password, clpu, cpodr) VALUES (?, ?, ?, ?, ?) ", false, vrachPcod, login, pass, lpuPcod, podrPcod);
					sme.setCommit();
					return pass;
				} catch (SQLException e) {
					if (((SQLException)e.getCause()).getSQLState().equals("23505"))
						continue;
					throw new TException(e);
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
					throw new SQLException("Ошибка генерации пароля.", e);
				}
			}
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void remPassword(int vrachPcod, int lpuPcod, int podrPcod) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_users WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, vrachPcod, lpuPcod, podrPcod);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public String getPermissions(int vrachPcod, int lpuPcod, int podrPcod) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pdost FROM s_users WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", vrachPcod, lpuPcod, podrPcod)) {
			if (acrs.getResultSet().next())
				return acrs.getResultSet().getString(1);
			else
				return "";
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void setPermissions(int vrachPcod, int lpuPcod, int podrPcod, String pdost) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET pdost = ? WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, pdost, vrachPcod, lpuPcod, podrPcod);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}


	private boolean isVrachExists(VrachInfo vi) throws SQLException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod FROM s_vrach WHERE (fam = ?) AND (im = ?) AND (ot = ?) AND (pol = ?) AND (datar = ?) AND (obr = ?) AND (idv = ?) ", vi, VrachInfo._Fields.values(), vrachTypes, 1, 2, 3, 4, 5, 6, 8)) {
			return acrs.getResultSet().next();
		}
	}
	
	private boolean isMrabExists(MestoRab mr) throws SQLException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id FROM s_mrab WHERE (pcod = ?) AND (clpu = ?) AND (cslu = ?) AND (cpodr = ?) AND (cdol = ?) AND (datau = ?) AND (priznd = ?) ", mr, MestoRab._Fields.values(), mrabTypes, 1, 2, 3, 4, 5, 6, 7)) {
			return acrs.getResultSet().next();
		}
	}
	
	private String generatePassword(String login) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		while (true) {
			login += " " + Long.valueOf(System.nanoTime()).toString();
			MessageDigest mdg = MessageDigest.getInstance("md5");
			byte[] md5Bytes = mdg.digest(login.getBytes("utf-8"));
			String pass = "";
			for (int i = 0; i < md5Bytes.length; i += 4) {
				int remLen = (i + 4 < md5Bytes.length) ? 4
						: md5Bytes.length - i;
				int md5Int = 0;
				for (int j = 0; j < remLen; j++)
					md5Int |= (md5Bytes[i + j] << (j * 8));
				pass += Integer.toString(md5Int, 32);
			}
			Random rnd = new Random();
			int cnt = 6 + rnd.nextInt(7);
			byte[] passBytes = pass.getBytes();
			if (cnt > passBytes.length)
				continue;
			byte[] pass1 = new byte[cnt];
			for (int i = 0; i < cnt; i++) {
				if (passBytes[i] == 45) { //-
					pass1[i] = (byte) (224 + rnd.nextInt(6)); //а + rnd
				} else if ((47 < passBytes[i]) && (passBytes[i] < 58)) { //0-9
					pass1[i] = passBytes[i];
				} else { //a-z
					pass1[i] = (byte) (passBytes[i] - 97 + 224); //a=а, b=б..., z=щ
				}
			}
			return new String(pass1, Charset.forName("cp1251"));
		}
	}
	
	@Override
	public List<IntegerClassifier> getPrizndList() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT * FROM n_priznd ")) {
			return rsmClf.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
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

}
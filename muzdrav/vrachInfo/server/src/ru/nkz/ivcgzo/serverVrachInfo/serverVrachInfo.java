package ru.nkz.ivcgzo.serverVrachInfo;

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
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftServerVrachInfo.MestoRab;
import ru.nkz.ivcgzo.thriftServerVrachInfo.MestoRabExistsException;
import ru.nkz.ivcgzo.thriftServerVrachInfo.MestoRabNotFoundException;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonPok;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonRazd;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonText;
import ru.nkz.ivcgzo.thriftServerVrachInfo.VrachExistsException;
import ru.nkz.ivcgzo.thriftServerVrachInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftServerVrachInfo.VrachNotFoundException;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ThriftServerVrachInfo.Iface;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ThriftServerVrachInfo;

public class serverVrachInfo extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<VrachInfo, VrachInfo._Fields> rsmVrach;
	private static final Class<?>[] vrachTypes = new Class<?>[] {Integer.class, String.class, String.class, String.class, Short.class, Date.class, Short.class, String.class, String.class}; 
	private static final Class<?>[] mrabTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class}; 
	private TResultSetMapper<MestoRab, MestoRab._Fields> rsmMrab;
	private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
	private TResultSetMapper<ShablonRazd, ShablonRazd._Fields> rsmShabRazd;
	private TResultSetMapper<ShablonPok, ShablonPok._Fields> rsmShabPok;
	private TResultSetMapper<ShablonText, ShablonText._Fields> rsmShabText;
	private static final Class<?>[] shabTextTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, String.class, String.class}; 
	
	@Override
	public void start() throws Exception {
		ThriftServerVrachInfo.Processor<Iface> proc = new ThriftServerVrachInfo.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
		thrServ.stop();
	}

	
	public serverVrachInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);

		rsmVrach = new TResultSetMapper<>(VrachInfo.class, "pcod", "fam", "im", "ot", "pol", "datar", "obr", "snils", "idv");
		rsmMrab = new TResultSetMapper<>(MestoRab.class, "id", "pcod", "clpu", "cslu", "cpodr", "cdol", "datau", "priznd");
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
		rsmShabRazd = new TResultSetMapper<>(ShablonRazd.class, "id", "name");
		rsmShabPok = new TResultSetMapper<>(ShablonPok.class, "id", "id_razd", "name", "checked");
		rsmShabText = new TResultSetMapper<>(ShablonText.class, "id", "id_razd", "id_pok", "pcod_s00", "text");
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
				sme.execPreparedT("INSERT INTO s_vrach (fam, im, ot, pol, datar, obr, snils, idv) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8);
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
				sme.execPreparedT("UPDATE s_vrach SET fam = ?, im = ?, ot = ?, pol = ?, datar = ?, obr = ?, snils = ?, idv = ? WHERE pcod = ? ", false, vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8, 0);
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
				sme.execPreparedT("INSERT INTO s_mrab (pcod, clpu, cslu, cpodr, cdol, datau, priznd) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, mr, mrabTypes, 1, 2, 3, 4, 5, 6, 7);
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
	public void UpdMrab(MestoRab mr, int user_id) throws MestoRabExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (!isMrabExists(mr)) {
				sme.execPreparedT("UPDATE s_mrab SET cslu = ?, cpodr = ?, cdol = ?, datau = ?, priznd = ? WHERE id = ? ", false, mr, mrabTypes, 3, 4, 5, 6, 7, 0);
				sme.execPrepared("UPDATE s_users SET cpodr = ? WHERE id = ?", false, mr.getCpodr(), user_id);
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQueryT("SELECT pcod FROM s_vrach WHERE (fam = ?) AND (im = ?) AND (ot = ?) AND (pol = ?) AND (datar = ?) AND (obr = ?) AND (idv = ?) ", vi, vrachTypes, 1, 2, 3, 4, 5, 6, 8)) {
			return acrs.getResultSet().next();
		}
	}
	
	private boolean isMrabExists(MestoRab mr) throws SQLException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQueryT("SELECT id FROM s_mrab WHERE (pcod = ?) AND (clpu = ?) AND (cslu = ?) AND (cpodr = ?) AND (cdol = ?) AND (datau = ?) AND (priznd = ?) ", mr, mrabTypes, 1, 2, 3, 4, 5, 6, 7)) {
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
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void testConnection() throws TException {
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
	public List<StringClassifier> get_n_s00() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_s00 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<ShablonRazd> getShabRazd() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id, name FROM sh_n_razd ")) {
			return rsmShabRazd.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<ShablonPok> getShabPok(int id_razd, String cdol) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT p.*, COALESCE(c.checked, FALSE) AS checked FROM sh_n_pok p LEFT JOIN sh_s_cdol c ON (c.id_razd = p.id_razd AND c.id_pok = p.id AND c.pcod_s00 = ?) WHERE p.id_razd = ? ", cdol, id_razd)) {
			return rsmShabPok.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void setShabPok(ShablonPok shPok, String cdol) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT checked FROM sh_s_cdol WHERE (id_razd = ?) AND (id_pok = ?) AND (pcod_s00 = ?) ", shPok.getId_razd(), shPok.getId(), cdol)) {
			if (!acrs.getResultSet().next())
				sme.execPrepared("INSERT INTO sh_s_cdol VALUES (?, ?, ?, ?) ", false, shPok.getId_razd(), shPok.getId(), cdol, shPok.checked);
			else
				sme.execPrepared("UPDATE sh_s_cdol SET checked = ? WHERE (id_razd = ?) AND (id_pok = ?) AND (pcod_s00 = ?) ", false, shPok.checked, shPok.getId_razd(), shPok.getId(), cdol);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public List<ShablonText> getShablonTexts(ShablonPok shPok, String cdol) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT t.* FROM sh_s_text t JOIN sh_s_cdol c ON (c.id_razd = t.id_razd AND c.id_pok = t.id_pok AND c.pcod_s00 = t.pcod_s00) WHERE c.checked = true AND c.id_razd = ? AND c.id_pok = ? AND c.pcod_s00 = ? ", shPok.getId_razd(), shPok.getId(), cdol)) {
			return rsmShabText.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<ShablonText> getShablonTextsEdit(ShablonPok shPok, String cdol) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM sh_s_text WHERE id_razd = ? AND id_pok = ? AND pcod_s00 = ? ", shPok.getId_razd(), shPok.getId(), cdol)) {
			return rsmShabText.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int addShablonText(ShablonText shText) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPreparedT("INSERT INTO sh_s_text (id_razd, id_pok, pcod_s00, text) VALUES (?, ?, ?, ?) ", true, shText, shabTextTypes, 1, 2, 3, 4);
				int id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateShablonText(ShablonText shText) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE sh_s_text SET text = ? WHERE (id = ?) ", false, shText.getText(), shText.getId());
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new TException();
		}
	}

	@Override
	public void setShabPokGrup(ShablonRazd shRazd, String cdol, boolean value) throws TException {
		for (ShablonPok shPok : getShabPok(shRazd.getId(), cdol)) {
			shPok.setChecked(value);
			setShabPok(shPok, cdol);
		}
	}

}
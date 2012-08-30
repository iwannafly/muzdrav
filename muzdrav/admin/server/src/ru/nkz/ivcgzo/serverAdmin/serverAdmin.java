package ru.nkz.ivcgzo.serverAdmin;

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
import ru.nkz.ivcgzo.serverManager.common.ResultSetMapper;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftServerAdmin.MestoRab;
import ru.nkz.ivcgzo.thriftServerAdmin.MestoRabExistsException;
import ru.nkz.ivcgzo.thriftServerAdmin.MestoRabNotFoundException;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonOsm;
import ru.nkz.ivcgzo.thriftServerAdmin.ThriftServerAdmin;
import ru.nkz.ivcgzo.thriftServerAdmin.ThriftServerAdmin.Iface;
import ru.nkz.ivcgzo.thriftServerAdmin.UserIdPassword;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachExistsException;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachInfo;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachNotFoundException;

public class serverAdmin extends Server implements Iface {
	private TServer thrServ;
	private TResultSetMapper<VrachInfo, VrachInfo._Fields> rsmVrach;
	private static final Class<?>[] vrachTypes = new Class<?>[] {Integer.class, String.class, String.class, String.class, Integer.class, Date.class, String.class, String.class, String.class}; 
	private static final Class<?>[] mrabTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class, Integer.class}; 
	private TResultSetMapper<MestoRab, MestoRab._Fields> rsmMrab;
	private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
	
	@Override
	public void start() throws Exception {
		ThriftServerAdmin.Processor<Iface> proc = new ThriftServerAdmin.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
		thrServ.stop();
	}

	
	public serverAdmin(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);

		rsmVrach = new TResultSetMapper<>(VrachInfo.class, "pcod", "fam", "im", "ot", "pol", "datar", "obr", "snils", "idv");
		rsmMrab = new TResultSetMapper<>(MestoRab.class, "id", "pcod", "clpu", "cslu", "cpodr", "cdol", "datau", "priznd", "user_id");
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
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
				sme.execPreparedT("INSERT INTO s_mrab (pcod, clpu, cslu, cpodr, cdol, datau, priznd, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, mr, mrabTypes, 1, 2, 3, 4, 5, 6, 7, 8);
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
				sme.execPreparedT("UPDATE s_mrab SET cslu = ?, cpodr = ?, cdol = ?, datau = ?, priznd = ? WHERE id = ? ", false, mr, mrabTypes, 3, 4, 5, 6, 7, 0);
				sme.execPrepared("UPDATE s_users SET cpodr = ? WHERE id = ?", false, mr.getCpodr(), mr.getUser_id());
				sme.setCommit();
			} else
				throw new MestoRabExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelMrab(MestoRab mr) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_mrab WHERE id = ? ", false, mr.getId());
			sme.execPrepared("DELETE FROM s_users WHERE id = ? ", false, mr.getUser_id());
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
	public UserIdPassword setPassword(int vrachPcod, int lpuPcod, int podrPcod, String login) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction(); AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id FROM s_users WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", vrachPcod, lpuPcod, podrPcod)) {
			while (true) {
				try {
					String pass = generatePassword(login);
					UserIdPassword idPass;
					if (acrs.getResultSet().next()) {
						sme.execPrepared("UPDATE s_users SET login = ?, password = ? WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, login, pass, vrachPcod, lpuPcod, podrPcod);
						idPass = new UserIdPassword(acrs.getResultSet().getInt(1), pass);
						sme.execPrepared("UPDATE s_mrab SET user_id = ? WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, idPass.getUser_id(), vrachPcod, lpuPcod, podrPcod);
					} else {
						sme.execPrepared("INSERT INTO s_users (pcod, login, password, clpu, cpodr) VALUES (?, ?, ?, ?, ?) ", true, vrachPcod, login, pass, lpuPcod, podrPcod);
						idPass = new UserIdPassword(sme.getGeneratedKeys().getInt("id"), pass);
						sme.execPrepared("UPDATE s_mrab SET user_id = ? WHERE (pcod = ?) AND (clpu = ?) AND (cpodr = ?) ", false, idPass.getUser_id(), vrachPcod, lpuPcod, podrPcod);
					}
					sme.setCommit();
					return idPass;
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
	public List<IntegerClassifier> get_n_p0s13() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p0s WHERE pcod BETWEEN 1 AND 3 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> get_n_o00(int clpu) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_o00 WHERE clpu = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> get_n_n00(int clpu) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_n00 WHERE clpu = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> get_n_lds(int clpu) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_lds WHERE clpu = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<StringClassifier> get_n_z00() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z00 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z30() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z30 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> getReqShOsmList() throws KmiacServerException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_shablon WHERE prizn = true ORDER BY pcod ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting required template osm list");
		}
	}

	@Override
	public int saveShablonOsm(ShablonOsm sho) throws KmiacServerException {
		int shId = sho.id;
		
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT id FROM sh_osm WHERE id = ? ", sho.id)) {
			if (acrs.getResultSet().next()) {
				sme.execPrepared("UPDATE sh_osm SET name = ?, diag = ?, cdin = ?, cslu = ? WHERE id = ? ", false, sho.name, sho.diag, sho.cDin, sho.cslu, shId);
				sme.execPrepared("DELETE FROM sh_osm_text WHERE id_sh_osm = ? ", false, shId);
				sme.execPrepared("DELETE FROM sh_ot_spec WHERE id_sh_osm = ? ", false, shId);
			} else {
				sme.execPrepared("INSERT INTO sh_osm (name, diag, cdin, cslu) VALUES (?, ?, ?, ?) ", true, sho.name, sho.diag, sho.cDin, sho.cslu);
				shId = sme.getGeneratedKeys().getInt("id");
			}
			for (IntegerClassifier txt : sho.textList)
				sme.execPrepared("INSERT INTO sh_osm_text (id_sh_osm, id_n_shablon, sh_text) VALUES (?, ?, ?) ", false, shId, txt.pcod, txt.name);
			for (Integer spc : sho.specList)
				sme.execPrepared("INSERT INTO sh_ot_spec (id_sh_osm, cspec) VALUES (?, ?) ", false, shId, spc);
			sme.setCommit();
			
			return shId;
		} catch (SQLException | InterruptedException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error saving template osm");
		}
	}

	@Override
	public List<IntegerClassifier> getAllShablonOsm() throws KmiacServerException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id AS pcod, name FROM sh_osm ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting all templates osm list");
		}
	}

	@Override
	public ShablonOsm getShablonOsm(int id) throws KmiacServerException {
		AutoCloseableResultSet acrs = null;
		
		try {
			acrs = sse.execPreparedQuery("SELECT name, diag, cdin, cslu FROM sh_osm WHERE id = ? ", id);
			if (acrs.getResultSet().next()) {
				ShablonOsm shOsm = new ShablonOsm(id, acrs.getResultSet().getString(1), acrs.getResultSet().getString(2).trim(), acrs.getResultSet().getInt(3), acrs.getResultSet().getInt(4), null, null);
				acrs.close();
				
				acrs = sse.execPreparedQuery("SELECT cspec FROM sh_ot_spec WHERE id_sh_osm = ? ", id);
				shOsm.setSpecList(ResultSetMapper.mapToList(Integer.class, acrs.getResultSet()));
				acrs.close();
				
				acrs = sse.execPreparedQuery("SELECT id_n_shablon AS pcod, sh_text AS name FROM sh_osm_text WHERE id_sh_osm = ? ", id);
				shOsm.setTextList(rsmIntClas.mapToList(acrs.getResultSet()));
				
				return shOsm;
			} else {
				throw new SQLException("No templates with specified id");
			}
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting all templates osm list");
		} finally {
			if (acrs != null)
				acrs.close();
		}
	}

	@Override
	public List<StringClassifier> getShablonOsmDiagList(String srcStr) throws KmiacServerException {
		String sql = "SELECT DISTINCT c00.pcod, c00.name FROM sh_osm sho JOIN n_c00 c00 ON (c00.pcod = sho.diag) ";
		if (srcStr != null) {
			srcStr = '%' + srcStr + '%';
			sql += "WHERE (sho.name LIKE ?) OR (c00.pcod LIKE ?) OR (c00.name LIKE ?) ";
		}
		sql += "ORDER BY c00.pcod ";
		try (AutoCloseableResultSet acrs = (srcStr != null) ? sse.execPreparedQuery(sql, srcStr, srcStr, srcStr) : sse.execQuery(sql)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates osm diag list");
		}
	}

	@Override
	public List<IntegerClassifier> getShablonOsmListByDiag(String diag) throws KmiacServerException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, name FROM sh_osm WHERE (diag = ?) ", diag)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates osm list by diag code");
		}
	}

}
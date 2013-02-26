package ru.nkz.ivcgzo.serverAdmin;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonDop;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonLds;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonOper;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonOsm;
import ru.nkz.ivcgzo.thriftServerAdmin.TemplateExistsException;
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
	private TResultSetMapper<ShablonDop, ShablonDop._Fields> rsmShDopClas;
	private TResultSetMapper<ShablonLds, ShablonLds._Fields> rsmShLdsClas;
	private TResultSetMapper<ShablonOper, ShablonOper._Fields> rsmShOperClas;
	
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
		rsmShDopClas = new TResultSetMapper<>(ShablonDop.class, "id", "id_n_shablon", "name", "text");
		rsmShLdsClas = new TResultSetMapper<>(ShablonLds.class, "id", "c_p0e1", "c_ldi", "name", "opis", "zakl");
		rsmShOperClas = new TResultSetMapper<>(ShablonOper.class);
	}

	@Override
	public List<VrachInfo> GetVrachList(int clpu) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT v.* FROM s_vrach v JOIN s_mrab m ON (m.pcod = v.pcod) WHERE m.clpu = ? ", clpu)) {
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
	public int AddVrach(VrachInfo vr) throws TException {
		AutoCloseableResultSet acrs = null;
		boolean found = false;
		int pcod;
		
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			if (vr.isSetSnils()) {
				acrs = sme.execPreparedQuery("SELECT pcod FROM s_vrach WHERE (snils = ?) ", vr.snils);
				found = acrs.getResultSet().next();
			}
			if (!found) {
				acrs = sme.execPreparedQueryT("SELECT pcod FROM s_vrach WHERE (fam = ?) AND (im = ?) AND (ot = ?) AND (pol = ?) AND (datar = ?) ", vr, vrachTypes, 1, 2, 3, 4, 5);
				found = acrs.getResultSet().next();
			}
			if (!found) {
				sme.execPreparedT("INSERT INTO s_vrach (fam, im, ot, pol, datar, obr, snils, idv) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8);
				pcod = sme.getGeneratedKeys().getInt("pcod");
			} else {
				pcod = acrs.getResultSet().getInt(1);
				vr.setPcod(pcod);
				sme.execPreparedT("UPDATE s_vrach SET fam = ?, im = ?, ot = ?, pol = ?, datar = ?, obr = ?, snils = ?, idv = ? WHERE pcod = ? ", false, vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8, 0);
			}
			sme.setCommit();
			return pcod;
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		} finally {
			if (acrs != null)
				acrs.close();
		}
	}

	@Override
	public void UpdVrach(VrachInfo vr) throws VrachExistsException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQueryT("SELECT pcod FROM s_vrach WHERE (fam = ?) AND (im = ?) AND (ot = ?) AND (pol = ?) AND (datar = ?) AND (obr = ?) AND (snils = ?) AND (idv = ?) ", vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8)) {
			if (!acrs.getResultSet().next()) {
				sme.execPreparedT("UPDATE s_vrach SET fam = ?, im = ?, ot = ?, pol = ?, datar = ?, obr = ?, snils = ?, idv = ? WHERE pcod = ? ", false, vr, vrachTypes, 1, 2, 3, 4, 5, 6, 7, 8, 0);
				sme.setCommit();
			} else
				throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelVrach(int vrPcod, int lpuPcod) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM s_vrach WHERE pcod = ? ", false, vrPcod);
			sme.execPrepared("DELETE FROM s_mrab WHERE (pcod = ?) AND (clpu = ?) ", false, vrPcod, lpuPcod);
			sme.execPrepared("DELETE FROM s_users WHERE (pcod = ?) AND (clpu = ?) ", false, vrPcod, lpuPcod);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	
	@Override
	public List<MestoRab> GetMrabList(int vrPcod, int clpu) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM s_mrab WHERE (pcod = ?) AND (clpu = ?) ", vrPcod, clpu)) {
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
	public String getLogin(int userId) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT login FROM s_users WHERE id = ? ", userId)) {
			if (acrs.getResultSet().next())
				return acrs.getResultSet().getString(1);
			else
				return "";
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public UserIdPassword setPassword(int mrId, String login) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT u.id FROM s_users u JOIN s_mrab r ON (r.user_id = u.id) WHERE r.id = ? ", mrId)) {
			while (true) {
				try {
					String pass = generatePassword(login);
					UserIdPassword idPass;
					if (acrs.getResultSet().next()) {
						idPass = new UserIdPassword(acrs.getResultSet().getInt(1), pass);
						sme.execPrepared("UPDATE s_users SET login = ?, password = ? WHERE id = ? ", false, login, pass, idPass.user_id);
					} else {
						sme.execPrepared("INSERT INTO s_users (pcod, clpu, cpodr, login, password) (SELECT pcod, clpu, cpodr, ?, ? FROM s_mrab WHERE id = ?) ", true, login, pass, mrId);
						idPass = new UserIdPassword(sme.getGeneratedKeys().getInt("id"), pass);
					}
					sme.execPrepared("UPDATE s_mrab SET user_id = ? WHERE id = ? ", false, idPass.user_id, mrId);
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
	public void remPassword(int userId) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_mrab SET user_id = NULL WHERE user_id = ? ", false, userId);
			sme.execPrepared("DELETE FROM s_users WHERE id = ? ", false, userId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public String getPermissions(int userId) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pdost FROM s_users WHERE id = ? ", userId)) {
			if (acrs.getResultSet().next())
				return acrs.getResultSet().getString(1);
			else
				return "";
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void setPermissions(int userId, String pdost) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET pdost = ? WHERE id = ? ", false, pdost, userId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
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
			return new String(pass1, Charset.forName("cp1251")).replace('б', 'э').replace('з', 'ю').replace('о', 'я');
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
	public List<IntegerClassifier> get_n_p0s15() throws TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p0s WHERE pcod BETWEEN 1 AND 5 ")) {
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
	public List<IntegerClassifier> getReqShOsmList() throws KmiacServerException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_shablon WHERE prizn = true ORDER BY pcod ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting required template osm list");
		}
	}

	@Override
	public int saveShablonOsm(ShablonOsm sho) throws KmiacServerException, TemplateExistsException {
		int shId = sho.id;
		
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT id FROM sh_osm WHERE id = ? ", shId)) {
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
			if (e instanceof SQLException)
				if (((SQLException) e.getCause()).getSQLState().equals("23505"))
					throw new TemplateExistsException();
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
			throw new KmiacServerException("Error loading osm template");
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

	@Override
	public void deleteShablonOsm(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("DELETE FROM sh_osm_text WHERE id_sh_osm = ? ", false, id);
				sme.execPrepared("DELETE FROM sh_ot_spec WHERE id_sh_osm = ? ", false, id);
				sme.execPrepared("DELETE FROM sh_osm WHERE id = ? ", false, id);
				sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error deleting template osm");
		}
	}

	@Override
	public List<IntegerClassifier> getShDopRazdList() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_shablon WHERE prizn = false ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates dop razd list");
		}
	}

	@Override
	public List<IntegerClassifier> getShDopList(int idNshablon) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, name FROM sh_dop WHERE id_n_shablon = ? ", idNshablon)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates dop list");
		}
	}

	@Override
	public ShablonDop getShDop(int id) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM sh_dop WHERE id = ? ", id)) {
			if (acrs.getResultSet().next())
				return rsmShDopClas.map(acrs.getResultSet());
			else
				throw new KmiacServerException("No template dop found with this id");
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting template dop by id");
		}
	}

	@Override
	public int saveShDop(ShablonDop shDop) throws TemplateExistsException, KmiacServerException, TException {
		int shId = shDop.id;
		
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT id FROM sh_dop WHERE id = ? ", shId)) {
			if (acrs.getResultSet().next()) {
				sme.execPrepared("UPDATE sh_dop SET name = ?, text = ? WHERE id = ? ", false, shDop.name, shDop.text, shId);
			} else {
				sme.execPrepared("INSERT INTO sh_dop (id_n_shablon, name, text) VALUES (?, ?, ?) ", true, shDop.idNshablon, shDop.name, shDop.text);
				shId = sme.getGeneratedKeys().getInt("id");
			}
			sme.setCommit();
			
			return shId;
		} catch (SQLException | InterruptedException e) {
			if (e instanceof SQLException)
				if (((SQLException) e.getCause()).getSQLState().equals("23505"))
					throw new TemplateExistsException();
			System.err.println(e.getCause());
			throw new KmiacServerException("Error saving template dop");
		}
	}

	@Override
	public void deleteShDop(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM sh_dop WHERE id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error deleting template dop");
		}
	}

	@Override
	public List<IntegerClassifier> getShLdsVidIsslList() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p0e1 WHERE gruppa = 2 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates lds vid issl list");
		}
	}

	@Override
	public List<StringClassifier> getShLdsIsslList(int cP0e1) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT ldi.pcod, ldi.name FROM sh_lds shl JOIN n_ldi ldi ON (ldi.pcod = shl.c_ldi) WHERE shl.c_p0e1 = ? ", cP0e1)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates lds issl list");
		}
	}

	@Override
	public List<IntegerClassifier> getShLdsList(int cP0e1, String cLdi) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, name FROM sh_lds WHERE (c_p0e1 = ?) AND (c_ldi = ?) ", cP0e1, cLdi)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates lds vid issl list");
		}
	}

	@Override
	public ShablonLds getShLds(int id) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM sh_lds WHERE id = ? ", id)) {
			if (acrs.getResultSet().next())
				return rsmShLdsClas.map(acrs.getResultSet());
			else
				throw new KmiacServerException("No template dop found with this id");
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting template lds by id");
		}
	}

	@Override
	public int saveShLds(ShablonLds shLds) throws KmiacServerException, TemplateExistsException, TException {
		int shId = shLds.id;
		
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT id FROM sh_lds WHERE id = ? ", shId)) {
			if (acrs.getResultSet().next()) {
				sme.execPrepared("UPDATE sh_lds SET c_ldi = ?, name = ?, opis = ?, zakl = ? WHERE id = ? ", false, shLds.c_ldi, shLds.name, shLds.opis, shLds.zakl, shId);
			} else {
				sme.execPrepared("INSERT INTO sh_lds (c_p0e1, c_ldi, name, opis, zakl) VALUES (?, ?, ?, ?, ?) ", true, shLds.c_p0e1, shLds.c_ldi, shLds.name, shLds.opis, shLds.zakl);
				shId = sme.getGeneratedKeys().getInt("id");
			}
			sme.setCommit();
			
			return shId;
		} catch (SQLException | InterruptedException e) {
			if (e instanceof SQLException)
				if (((SQLException) e.getCause()).getSQLState().equals("23505"))
					throw new TemplateExistsException();
			System.err.println(e.getCause());
			throw new KmiacServerException("Error saving template lds");
		}
	}

	@Override
	public void deleteShLds(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM sh_lds WHERE id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error deleting template lds");
		}
	}
	
	@Override
	public List<IntegerClassifier> getShOperStatList() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_tip WHERE (pcod = 1) OR (pcod = 3) ORDER BY pcod ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates oper stat type list");
		}
	}
	
	@Override
	public List<IntegerClassifier> getShOperList(int statTip, String srcStr) throws KmiacServerException, TException {
		String sql = "SELECT id AS pcod, oper_pcod || ' ' || name AS name FROM sh_oper WHERE (stat_tip = ?) ";
		if (srcStr != null)
			sql += "AND (src_text LIKE ?) ";
		sql += "ORDER BY oper_pcod ";
		
		try (AutoCloseableResultSet acrs = (srcStr == null) ? sse.execPreparedQuery(sql, statTip) :  sse.execPreparedQuery(sql, statTip, srcStr)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting templates oper list");
		}
	}
	
	@Override
	public ShablonOper getShOper(int id) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM sh_oper WHERE id = ? ", id)) {
			if (acrs.getResultSet().next())
				return rsmShOperClas.map(acrs.getResultSet());
			else
				throw new KmiacServerException("No template oper found with this id");
		} catch (SQLException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error getting template oper");
		}
	}
	
	@Override
	public int saveShOper(ShablonOper shOper) throws KmiacServerException, TemplateExistsException, TException {
		int shId = shOper.id;
		String srcStr = String.format("%s %s %s %s %s %s %s %s", shOper.oper_pcod.toLowerCase(), shOper.oper_name.toLowerCase(), shOper.diag_pcod.toLowerCase(), shOper.diag_name.toLowerCase(), shOper.name.toLowerCase(), new Time(shOper.oper_dlit), shOper.mat.toLowerCase(), shOper.text.toLowerCase());
		
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sme.execPreparedQuery("SELECT id FROM sh_oper WHERE id = ? ", shId)) {
			if (acrs.getResultSet().next()) {
				sme.execPrepared("UPDATE sh_oper SET name = ?, oper_pcod = ?, diag_pcod = ?, oper_dlit = ?, mat = ?, text = ?, src_text = ? WHERE id = ? ", false, shOper.name, shOper.oper_pcod, shOper.diag_pcod, new Time(shOper.oper_dlit), shOper.mat, shOper.text, srcStr, shId);
			} else {
				sme.execPrepared("INSERT INTO sh_oper (stat_tip, name, oper_pcod, diag_pcod, oper_dlit, mat, text, src_text) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, shOper.stat_tip, shOper.name, shOper.oper_pcod, shOper.diag_pcod, new Time(shOper.oper_dlit), shOper.mat, shOper.text, srcStr);
				shId = sme.getGeneratedKeys().getInt("id");
			}
			sme.setCommit();
			
			return shId;
		} catch (SQLException | InterruptedException e) {
			if (e instanceof SQLException)
				if (((SQLException) e.getCause()).getSQLState().equals("23505"))
					throw new TemplateExistsException();
			System.err.println(e.getCause());
			throw new KmiacServerException("Error saving template oper");
		}
	}
	
	@Override
	public void deleteShOper(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM sh_oper WHERE id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			System.err.println(e.getCause());
			throw new KmiacServerException("Error deleting template oper");
		}
	}
}
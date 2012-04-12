package ru.nkz.ivcgzo.lds_server;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.server.TThreadedSelectorServer.Args; 

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.ldsThrift.DiagIsl;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift.Iface;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;





public class LDSserver extends Server implements Iface { 
	private TServer	thrServ;
	private TResultSetMapper<ObInfIsl, ObInfIsl._Fields> rsmObInIs;
	private static final Class<?>[] islTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, String.class, Short.class, Date.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Data.class};	
	private TResultSetMapper<DiagIsl, DiagIsl._Fields> rsmDiIs;
	private static final Class<?>[] dislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, Integer.class, String.class, String.class, String.class, Short.class, String.class, String.class, Double.class, String.class};
	private TResultSetMapper<LabIsl, LabIsl._Fields> rsmLabIs;	
	private static final Class<?>[] lislTypes = new Class<?>[] {Integer.class, Integer.class, String.class, String.class, Double.class, String.class, Integer.class};
	public LDSserver(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
				
		rsmObInIs = new TResultSetMapper<>(ObInfIsl.class, "npasp", "nisl", "kodotd", "nprob", "pcisl", "cisl", "datap", "datav", "prichina", "popl", "napravl", "naprotd", "fio", "vopl", "diag", "kodvr", "kodm", "kods", "dataz");
		rsmDiIs = new TResultSetMapper<>(DiagIsl.class, "npasp", "nisl", "kodisl", "rez", "anamnez", "anastezi", "model", "kol", "op_name", "rez_name", "stoim", "pcod_m");
		rsmLabIs = new TResultSetMapper<>(LabIsl.class, "npasp", "nisl", "cpok", "zpok", "stoim", "pcod_m", "pvibor");	
		
	}

	@Override
	public List<ObInfIsl> GetObInfIslt(int npasp) throws TException {
		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_isl_ld where npasp = ? ", npasp)) {
			return rsmObInIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}		
		
		
	}

	@Override
	public ObInfIsl GetIsl(int npasp) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_isl_ld WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmObInIs.map(acrs.getResultSet());
			else
				return new ObInfIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int AddIsl(ObInfIsl info) throws TException {

		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_isl_ld (npasp, kodotd, nprob, pcisl, cisl, datap, datav, prichina, popl, napravl, naprotd, fio, vopl, diag, kodvr, kodm, kods, dataz) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, info, islTypes, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
				int nisl = sme.getGeneratedKeys().getInt("nisl");
				sme.setCommit();
				return nisl;
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void UpdIsl(ObInfIsl info) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_isl_ld SET nprob = ?, pcisl = ?, cisl = ?, datap = ?, datav = ?, prichina = ?, popl = ?, napravl = ?, naprotd = ?, fio = ?, vopl = ?, diag = ?, kodvr = ?, kodm = ?, kods = ? WHERE nisl = ? ", false, info, islTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 1 );
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void DelIsl(int nisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_isl_ld WHERE nisl = ? ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public List<DiagIsl> GetDiagIsl(int nisl) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d where nisl = ? ", nisl)) {
			return rsmDiIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public DiagIsl GetDIsl(int nisl) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d WHERE nisl = ? ", nisl)) {
			if (acrs.getResultSet().next())
				return rsmDiIs.map(acrs.getResultSet());
			else
				return new DiagIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void AddDIsl(DiagIsl di) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl, rez, anamnez, anestezi, model, kol, op_name, rez_name, stoim, pcod_m) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, di, dislTypes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void UpdDIsl(DiagIsl di) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_rez_d SET kodisl = ?, rez = ?, anamnez = ?, anestezi = ?, model = ?, kol = ?, op_name = ?, rez_name = ?, stoim = ?, pcod_m = ? WHERE (nisl = ?) ", false, di, dislTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1);
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void DelDIsl(int nisl, String kodisl) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_d WHERE (nisl = ?) and (kodisl = ?) ", false, nisl);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<LabIsl> GetLabIsl(int nisl) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_l where nisl = ? ", nisl)) {
			return rsmLabIs.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public LabIsl GetLIsl(int nisl) throws TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_rez_l WHERE nisl = ? ", nisl)) {
			if (acrs.getResultSet().next())
				return rsmLabIs.map(acrs.getResultSet());
			else
				return new LabIsl();
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public void AddLIsl(LabIsl li) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok, zpok, stoim, pcod_m, pvibor) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, li, lislTypes, 0, 1, 2, 3, 4, 5, 6);
				sme.setCommit();
			//} else
				//throw new VrachExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}
	}

	@Override
	public void UpdLIsl(LabIsl li) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			if (!isIslExists(info)) {
				sme.execPreparedT("UPDATE p_rez_l SET zpok = ?, stoim = ?, pcod_m = ?, pvibor = ? WHERE (nisl = ?) and (cpok = ?) ", false, li, lislTypes, 3, 4, 5, 6, 1, 2);
				sme.setCommit();
//			} else
//				throw new IslExistsException();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void DelLIsl(int nisl, String cpok) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rez_l WHERE (nisl = ?) and (cpok = ?) ", false, nisl, cpok);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new TException(e);
		}

	}

	@Override
	public void start() throws Exception {
		LDSThrift.Processor<Iface> proc = new LDSThrift.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public String getServerVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}

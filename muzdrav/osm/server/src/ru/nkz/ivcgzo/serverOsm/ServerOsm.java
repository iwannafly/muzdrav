package ru.nkz.ivcgzo.serverOsm;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmbNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmbNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.Iface;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class ServerOsm extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<ZapVr, ZapVr._Fields> rsmZapVr;
	private final Class<?>[] zapVrTypes; 
	private final TResultSetMapper<Pvizit, Pvizit._Fields> rsmPvizit;
	private final Class<?>[] pvizitTypes; 

	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp", "vidp", "timepn", "fam", "im", "ot", "poms_ser", "poms_nom");
		zapVrTypes = new Class<?>[] {Integer.class, Integer.class, Time.class, String.class, String.class, String.class, String.class, String.class};
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id", "npasp", "cpol", "cobr", "datao", "ishod", "rezult", "talon", "cod_sp", "cdol", "cuser", "ztext", "dataz", "id_etal");
		pvizitTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, Integer.class};
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

	@Override
	public void start() throws Exception {
		// FIXME ffff
		
		Pvizit obr = new Pvizit(0, 1, 2, 3, 4, 5, 6, 7, 8, "9", 10, "11", 12, 13);
//		obr.setId(AddPvizit(obr));
//		Pvizit obr1 = getPvizit(obr.id);
//		obr = new Pvizit(obr1.id, 2, 3, 4, 5, 6, 7, 8, 9, "10", 11, "12", 13, 14);
//		UpdatePvizit(obr);
//		DeletePvizit(obr.id);
		
		ThriftOsm.Processor<Iface> proc = new ThriftOsm.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public List<ZapVr> getZapVr(int idvr, String cdol, long datap) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, tal.vidp, tal.timepn, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?)", idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO pvizit (npasp, cpol, cobr, datao, ishod, rezult, talon, cod_sp, cdol, cuser, ztext, dataz, id_etal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, obr, pvizitTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public Pvizit getPvizit(int obrId) throws PvizitNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM pvizit WHERE id = ? ", obrId)) {
			if (acrs.getResultSet().next())
				return rsmPvizit.map(acrs.getResultSet());
			else
				throw new PvizitNotFoundException();
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE pvizit SET ishod = ?, rezult = ?, talon = ?, ztext = ?, dataz = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 12, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePvizit(int obrId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM pvizit WHERE id = ? ", false, obrId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PvizitAmb getPvizitAmb(int posId) throws KmiacServerException, PvizitAmbNotFoundException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdatePvizitAmb(PvizitAmb pos) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePvizitAmb(int posId) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int AddPdiagAmb(PdiagAmb diag) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PdiagAmb getPdiagAmb(int diagId) throws KmiacServerException,
			PdiagAmbNotFoundException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdatePdiagAmb(PdiagAmb diag) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePdiagAmb(int diagId) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int AddPsign(Psign sign) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Psign getPsign(int signId) throws KmiacServerException,
			PsignNotFoundException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdatePsign(Psign sign) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePsign(int signId) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int AddPriem(Priem pr) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Priem getPriem(int prId) throws KmiacServerException,
			PriemNotFoundException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdatePriem(Priem pr) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePriem(int prId) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddPdiagZ(PdiagZ dz) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IntegerClassifier> get_n_cpos() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}
}

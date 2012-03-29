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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.Iface;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class ServerOsm extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<ZapVr, ZapVr._Fields> rsmZapVr;
	private final Class<?>[] zapVrTypes; 

	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp", "vidp", "timepn", "fam", "im", "ot", "poms_ser", "poms_nom");
		zapVrTypes = new Class<?>[] {Integer.class, Integer.class, Time.class, String.class, String.class, String.class, String.class, String.class};
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
	public List<ZapVr> getZapVr(int idvr, String cdol, long datap) throws TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, tal.vidp, tal.timepn, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?)", idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public int AddPvizit(Pvizit vizit) throws TException {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void UpdatePvizit(Pvizit vizit) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePvizit(int zab) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int AddPvizitAmb(PvizitAmb pos) throws TException {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void UpdatePvizitAmb(PvizitAmb pos) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeletePvizitAmb(int pos) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IntegerClassifier> get_n_cpos() throws TException {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}

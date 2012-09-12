package ru.nrz.ivcgzo.serverGenReestr;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
import ru.nkz.ivcgzo.thriftGenReestr.ReestrNotFoundException;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr.Iface;

public class GenReestr extends Server implements Iface {
////////////////////////////////////////////////////////////////////////
//							Fields                                    //
////////////////////////////////////////////////////////////////////////

private static Logger log = Logger.getLogger(GenReestr.class.getName());

private TServer thrServ;

	@Override
	public void start() throws Exception {
		ThriftGenReestr.Processor<Iface> proc = new ThriftGenReestr.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
        log.info("Start serverRegPatient");
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
			log.info("Stop serverRegPatient");
}

	public GenReestr(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void testConnection() throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void GenReestrFile(int cpodr, long dn, long dk, int vidreestr)
			throws ReestrNotFoundException, TException {
		// TODO Auto-generated method stub
		
	}

//////////////////////////Classifiers ////////////////////////////////////

	@Override
    public final List<IntegerClassifier> getAllPolForCurrentLpu(final int lpuId) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM n_n00 WHERE clpu = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmN00 =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
			return rsmN00.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
    public final List<IntegerClassifier> getPolForCurrentLpu(final int polId) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM n_n00 WHERE pcod = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmN00 =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, polId)) {
			return rsmN00.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
    public final List<IntegerClassifier> getOtdForCurrentLpu(final int lpuId) throws TException {
        final String sqlQuery = "SELECT pcod, name FROM n_o00 WHERE clpu = ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmO00 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
            return rsmO00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new TException(e);
        }
    }

}

package ru.nrz.ivcgzo.serverGenReestr;

import java.sql.ResultSet;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
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
//		rsmMedInfo = new TResultSetMapper<>(PokazMet.class, "pcod",       "name_n");
//		MedTypes = new Class<?>[] {                     String.class, String.class};
	}

	@Override
	public void testConnection() throws TException {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
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

	@Override
	public void getReestrInfoPol(int cpodr, long dn, long dk, int vidr, int vopl)
			 throws KmiacServerException, ReestrNotFoundException, TException {
        String sqlwhere;
        sqlwhere = "";
        if (vidr == 1) sqlwhere = "AND v.dataz >= ? AND v.dataz <= ? AND v.kod_rez = 0";
        if (vidr == 2) sqlwhere = "AND (v.kod_rez = 0 AND v.dataz >= ? AND v.dataz <= ?) OR (v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11))";
        if (vidr == 3) sqlwhere = "AND v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11)";
//		c_mu, prof_fn,         	
		String sqlmed = "SELECT  v.id AS sl_id, v.id AS id_med, v.kod_rez AS kod_rez, v.datap as d_pst, 2 AS kl_usl, v.pl_extr AS pl_extr, " +
				"v.uet AS kol_usl, null AS c_mu, v.diag AS diag, null AS prof_fn, v.stoim AS stoim, "+
				"null AS case, null AS place, null AS spec, null AS prvd, null AS v_mu, null AS res_g, null AS ssd, 1 AS psv, 0 AS pr_pv, p.v_sch AS v_sch"+
			    " FROM p_vizit_amb v, patient p" + 
				" WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpodr = ? "+sqlwhere;
		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, vopl, cpodr, dn, dk, dn, dk)) : (sse.execPreparedQuery(sqlmed, vopl, cpodr, dn, dk))) {
            ResultSet rs = acrs.getResultSet();
            
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		}
		String sqlpasp = "SELECT  v.id AS sl_id, 2 AS vid_rstr, p.str_org AS str_org, p. as name_str, 2 AS kl_usl, v.pl_extr AS pl_extr, " +
				"v.uet AS kol_usl, null AS c_mu, v.diag AS diag, null AS prof_fn, v.stoim AS stoim, "+
				"null AS case, null AS place, null AS spec, null AS prvd, null AS v_mu, null AS res_g, null AS ssd, 1 AS psv, 0 AS pr_pv, p.v_sch AS v_sch"+
			    " FROM p_vizit_amb v, patient p" + 
				" WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpodr = ? "+sqlwhere;
		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, vopl, cpodr, dn, dk, dn, dk)) : (sse.execPreparedQuery(sqlpasp, vopl, cpodr, dn, dk))) {
            ResultSet rs = acrs.getResultSet();
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		}
	}


}

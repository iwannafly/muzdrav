package ru.nkz.ivcgzo.serverGenTalons;

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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.Calendar;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons.Iface;
import ru.nkz.ivcgzo.thriftGenTalon.Vidp;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;

public class ServerGenTalons extends Server implements Iface {

    private TServer thrServ;

    private TResultSetMapper<Spec, Spec._Fields> rsmSpec;
    private TResultSetMapper<Vrach, Vrach._Fields> rsmVrach;
    private static final String[] SPEC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] VRACH_FIELD_NAMES = {
        "pcod", "fam", "im", "ot", "cdol"
    };
    public ServerGenTalons(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        rsmSpec = new TResultSetMapper<>(Spec.class, SPEC_FIELD_NAMES);
        rsmVrach = new TResultSetMapper<>(Vrach.class, VRACH_FIELD_NAMES);
    }

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public final void start() throws Exception {
        ThriftGenTalons.Processor<Iface> proc =
                new ThriftGenTalons.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        thrServ.serve();
    }

    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
        }
    }

    //TODO дописать NotFoundException
    @Override
    public final List<Spec> getAllSpecForPolikliniki(final int cpodr)
            throws KmiacServerException {
        String sqlQuery = "SELECT DISTINCT n_s00.pcod, n_s00.name FROM n_s00 "
            + "INNER JOIN s_mrab ON n_s00.pcod = s_mrab.cdol "
            + "WHERE s_mrab.cpodr = ? ORDER BY n_s00.name";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
            return rsmSpec.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //TODO дописать NotFoundException
    @Override
    public final List<Vrach> getVrachForCurrentSpec(final int cpodr, final String cdol)
            throws KmiacServerException {
        String  sqlQuery = "SELECT s_vrach.pcod, s_vrach.fam, s_vrach.im, s_vrach.ot, "
            + "s_mrab.cdol FROM s_vrach INNER JOIN s_mrab ON s_vrach.pcod=s_mrab.pcod "
            + "WHERE s_mrab.cpodr=? AND s_mrab.cdol=? AND s_mrab.datau is null "
            + "ORDER BY fam, im, ot";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            return rsmVrach.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Calendar> getCalendar(final int cyear) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Ndv> getNdv(final int pcodvrach, final int cpol) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Norm> getNorm(final int pcodvrach, final int cpol) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Nrasp> getNrasp(final int pcodvrach, final int cpol, final int cxema)
            throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Rasp> getRasp(final int pcodvrach, final int cpol) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Talon> getTalon(final int pcodvrach, final int cpol, final long datap)
            throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Vidp> getVidp() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

}

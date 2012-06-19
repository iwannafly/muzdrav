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
    private TResultSetMapper<Calendar, Calendar._Fields> rsmCalendar;
    private TResultSetMapper<Norm, Norm._Fields> rsmNorm;
    private TResultSetMapper<Ndv, Ndv._Fields> rsmNdv;
    private TResultSetMapper<Nrasp, Nrasp._Fields> rsmNrasp;
    private TResultSetMapper<Rasp, Rasp._Fields> rsmRasp;
    private TResultSetMapper<Talon, Talon._Fields> rsmTalon;
    private TResultSetMapper<Vidp, Vidp._Fields> rsmVidp;
    private static final String[] SPEC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] VRACH_FIELD_NAMES = {
        "pcod", "fam", "im", "ot", "cdol"
    };
    private static final String[] CALENDAR_FIELD_NAMES = {
        "datacal", "dweek", "nweek", "cday", "cmonth", "cyear",
        "pr_rab", "d_rab"
    };
    private static final String[] NORM_FIELD_NAMES = {
        "cdol", "vidp", "dlit"
    };
    private static final String[] NDV_FIELD_NAMES = {
        "pcod", "datan", "datak", "cdol"
    };
    private static final String[] NRASP_FIELD_NAMES = {
        "pcod", "denn", "vidp", "time_n", "time_k",
        "cxema", "cdol"
    };
    private static final String[] RASP_FIELD_NAMES = {
        "nrasp", "pcod", "nned", "denn", "datap", "time_n",
        "time_k", "vidp", "cdol"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "nrasp", "pcod_sp", "cdol",
        "cdol_kem", "vidp", "timepn", "timepk", "datapt",
        "datap", "timep"
    };
    private static final String[] VIDP_FIELD_NAMES = {
        "pcod","name","vcolor"
    };

    public ServerGenTalons(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        rsmSpec = new TResultSetMapper<>(Spec.class, SPEC_FIELD_NAMES);
        rsmVrach = new TResultSetMapper<>(Vrach.class, VRACH_FIELD_NAMES);
        rsmCalendar = new TResultSetMapper<>(Calendar.class, CALENDAR_FIELD_NAMES);
        rsmNorm = new TResultSetMapper<>(Norm.class, NORM_FIELD_NAMES);
        rsmNdv = new TResultSetMapper<>(Ndv.class, NDV_FIELD_NAMES);
        rsmNrasp = new TResultSetMapper<>(Nrasp.class, NRASP_FIELD_NAMES);
        rsmRasp = new TResultSetMapper<>(Rasp.class, RASP_FIELD_NAMES);
        rsmTalon = new TResultSetMapper<>(Talon.class, TALON_FIELD_NAMES);
        rsmVidp = new TResultSetMapper<>(Vidp.class, VIDP_FIELD_NAMES);
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
            throws KmiacServerException, TException {
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
            throws KmiacServerException, TException {
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
    public List<Calendar> getCalendar(int cyear) 
            throws KmiacServerException, TException  {
        String  sqlQuery = "SELECT datacal, dweek, nweek, cday, cmonth, cyear, "
            + "pr_rab, d_rab FROM e_calendar WHERE cyear=? ORDER BY datacal";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cyear)) {
            return rsmCalendar.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Norm> getNorm(int cpodr, String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT cdol, vidp, dlit "
            + "FROM e_norm WHERE cpol=? AND cdol =? ORDER BY vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            return rsmNorm.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Ndv> getNdv(int cpodr, int pcodvrach, String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, datan, datak, cdol "
            + "FROM e_ndv WHERE cpol=? AND pcod =? AND cdol =? ORDER BY datan, datak";
        try (AutoCloseableResultSet acrs = 
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            return rsmNdv.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Nrasp> getNrasp(int cpodr, int pcodvrach, String cdol, int cxema)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol FROM e_nrasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "AND cxema =? ORDER BY denn, vidp, time_n";
        try (AutoCloseableResultSet acrs = 
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol, cxema)) {
            return rsmNrasp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Rasp> getRasp(int cpodr, int pcodvrach, String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT nrasp, pcod, nned, denn, datap, time_n, time_k, " 
            + "vidp, cdol FROM e_rasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "ORDER BY datap, vidp, time_n";
        try (AutoCloseableResultSet acrs = 
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            return rsmRasp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Talon> getTalon(int cpodr, int pcodvrach, String cdol,
            long datap) throws KmiacServerException{
        String  sqlQuery = "SELECT id, ntalon, nrasp, pcod_sp, cdol, cdol_kem, "
            + "vidp, timepn, timepk, datapt, datap , timep " 
            + "FROM e_talon WHERE cpol=? AND pcod_sp =? AND cdol =? AND datap =? "
            + "ORDER BY datap, timepn, timepk, vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, cpodr, pcodvrach, cdol, new java.sql.Date(datap))) {
            return rsmTalon.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            //log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Vidp> getVidp() throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, name, vcolor FROM e_vidp "
                + "ORDER BY pcod";
            try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
                return rsmVidp.mapToList(acrs.getResultSet());
            } catch (SQLException e) {
                //log.log(Level.ERROR, "SQl Exception: ", e);
                throw new KmiacServerException();
            }
    }
}

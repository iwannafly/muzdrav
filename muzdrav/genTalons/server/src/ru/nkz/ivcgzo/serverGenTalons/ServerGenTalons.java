package ru.nkz.ivcgzo.serverGenTalons;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
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
import ru.nkz.ivcgzo.thriftGenTalon.Calend;
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

    private static Logger log = Logger.getLogger(ServerGenTalons.class.getName());

    private TResultSetMapper<Spec, Spec._Fields> rsmSpec;
    private TResultSetMapper<Vrach, Vrach._Fields> rsmVrach;
    private TResultSetMapper<Calend, Calend._Fields> rsmCalendar;
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
        "cdol", "vidp", "dlit", "cpol", "id" //TODO сравнить с гет методом
    };
    private static final String[] NDV_FIELD_NAMES = {
        "pcod", "datan", "datak", "cdol", "cpol", "id" //TODO сравнить с гет методом
    };
    private static final String[] NRASP_FIELD_NAMES = {
        "pcod", "denn", "vidp", "time_n", "time_k",
        "cxema", "cdol", "cpol", "id", "pfd" //TODO сравнить с гет методом
    };
    private static final String[] RASP_FIELD_NAMES = {
        "nrasp", "pcod", "nned", "denn", "datap", "time_n",
        "time_k", "vidp", "cdol", "cpol", "id", "pfd" //TODO сравнить с гет методом
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "nrasp", "pcod_sp", "cdol",
        "cdol_kem", "vidp", "timepn", "timepk", "datapt",
        "datap", "timep", "cpol" //TODO сравнить с гет методом
    };
    private static final String[] VIDP_FIELD_NAMES = {
        "pcod", "name", "vcolor"
    };

    public ServerGenTalons(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmSpec = new TResultSetMapper<>(Spec.class, SPEC_FIELD_NAMES);
        rsmVrach = new TResultSetMapper<>(Vrach.class, VRACH_FIELD_NAMES);
        rsmCalendar = new TResultSetMapper<>(Calend.class, CALENDAR_FIELD_NAMES);
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
            log.log(Level.ERROR, "SQl Exception: ", e);
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
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Calend getCalendar(final long datacal) throws KmiacServerException,
            TException {
        String  sqlQuery = "SELECT datacal, dweek, nweek, cday, cmonth, cyear, "
                + "pr_rab, d_rab FROM e_calendar WHERE datacal=? ORDER BY datacal";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, new Date(datacal))) {
            if (acrs.getResultSet().next()) {
                Calend tmpCalend = rsmCalendar.map(acrs.getResultSet());
                return tmpCalend;
            } else {
                throw new KmiacServerException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Norm> getNorm(final int cpodr, final String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT cdol, vidp, dlit, cpol, id "
            + "FROM e_norm WHERE cpol=? AND cdol =? ORDER BY vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            return rsmNorm.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Ndv> getNdv(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, datan, datak, cdol, cpol, id "
            + "FROM e_ndv WHERE cpol=? AND pcod =? AND cdol =? ORDER BY datan, datak";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            return rsmNdv.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Nrasp> getNrasp(final int cpodr, final int pcodvrach, final String cdol,
            final int cxema) throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd FROM e_nrasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "AND cxema =? ORDER BY denn, vidp, time_n";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol, cxema)) {
            return rsmNrasp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Rasp> getRasp(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException {
        String  sqlQuery = "SELECT nrasp, pcod, nned, denn, datap, time_n, time_k, "
            + "vidp, cdol, cpol, id, pfd FROM e_rasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "ORDER BY datap, vidp, time_n";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            return rsmRasp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Talon> getTalon(final int cpodr, final int pcodvrach, final String cdol,
            final long datap) throws KmiacServerException {
        String  sqlQuery = "SELECT id, ntalon, nrasp, pcod_sp, cdol, cdol_kem, "
            + "vidp, timepn, timepk, datapt, datap , timep, cpol "
            + "FROM e_talon WHERE cpol=? AND pcod_sp =? AND cdol =? AND datap =? "
            + "ORDER BY datap, timepn, timepk, vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, cpodr, pcodvrach, cdol, new java.sql.Date(datap))) {
            return rsmTalon.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Vidp> getVidp() throws KmiacServerException, TException {
        String  sqlQuery = "SELECT pcod, name, vcolor FROM e_vidp "
                + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmVidp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Nrasp> getNraspCpodr(final int cpodr) throws KmiacServerException,
            TException {
        String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
                + "cxema, cdol, cpol, id, pfd FROM e_nrasp"
                + "WHERE cpol =? "
                + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmNrasp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Nrasp> getNraspCdol(final int cpodr, final String cdol)
            throws KmiacServerException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    //Выбирать по cpol из e_norm (как сейчас) или по cpodr из s_mrab
    @Override
    public final List<Nrasp> getNraspVrach(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addNrasp(final List<Nrasp> nrasp) throws KmiacServerException,
            TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addNdv(final Ndv ndv) throws KmiacServerException, TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addNorm(final List<Norm> nrasp) throws KmiacServerException,
            TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteNrasp(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteNdv(final int id) throws KmiacServerException, TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateNrasp(final List<Nrasp> nrasp) throws KmiacServerException,
            TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateNorm(final List<Norm> nrasp) throws KmiacServerException,
            TException {
        // TODO Auto-generated method stub
    }

}

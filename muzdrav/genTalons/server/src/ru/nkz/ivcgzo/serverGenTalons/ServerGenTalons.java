package ru.nkz.ivcgzo.serverGenTalons;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.Calend;
import ru.nkz.ivcgzo.thriftGenTalon.CalendNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.NdvNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.NormNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.NraspNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.RaspNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons.Iface;
import ru.nkz.ivcgzo.thriftGenTalon.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;
import ru.nkz.ivcgzo.thriftGenTalon.VrachNotFoundException;

public class ServerGenTalons extends Server implements Iface {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private TServer thrServ;

    private static Logger log = Logger.getLogger(ServerGenTalons.class.getName());

//////////////////////////////// Mappers /////////////////////////////////

    private TResultSetMapper<Spec, Spec._Fields> rsmSpec;
    private TResultSetMapper<Vrach, Vrach._Fields> rsmVrach;
    private TResultSetMapper<Calend, Calend._Fields> rsmCalendar;
    private TResultSetMapper<Norm, Norm._Fields> rsmNorm;
    private TResultSetMapper<Ndv, Ndv._Fields> rsmNdv;
    private TResultSetMapper<Nrasp, Nrasp._Fields> rsmNrasp;
    private TResultSetMapper<Rasp, Rasp._Fields> rsmRasp;
    private TResultSetMapper<Talon, Talon._Fields> rsmTalon;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmVidp;

//////////////////////////// Field Name Arrays ////////////////////////////

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
        "cdol", "vidp", "dlit", "cpol", "id"
    };
    private static final String[] NDV_FIELD_NAMES = {
        "pcod", "datan", "datak", "cdol", "cpol", "id"
    };
    private static final String[] NRASP_FIELD_NAMES = {
        "pcod", "denn", "vidp", "time_n", "time_k",
        "cxema", "cdol", "cpol", "id", "pfd", "timep_n", "timep_k"
    };
    private static final String[] RASP_FIELD_NAMES = {
        "nrasp", "pcod", "nned", "denn", "datap", "time_n",
        "time_k", "vidp", "cdol", "cpol", "id", "pfd"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "nrasp", "pcod_sp", "cdol",
        "cdol_kem", "vidp", "timepn", "timepk", "datapt",
        "datap", "timep", "cpol"
    };
    private static final String[] VIDP_FIELD_NAMES = {
        "pcod", "name"
    };

////////////////////////////////Type Arrays /////////////////////////////////

    private static final Class<?>[] NORM_TYPES = new Class<?>[] {
    //  cdol          vidp           dlit           cpol
        String.class, Integer.class, Integer.class, Integer.class,
    //  id
        Integer.class
    };
    private static final Class<?>[] NDV_TYPES = new Class<?>[] {
    //  pcod           datan       datak       cdol
        Integer.class, Date.class, Date.class, String.class,
    //  cpol           id
        Integer.class, Integer.class
    };
    private static final Class<?>[] NRASP_TYPES = new Class<?>[] {
    //  pcod           denn           vidp           time_n
        Integer.class, Integer.class, Integer.class, Time.class,
    //  time_k       cxema          cdol          cpol
        Time.class,  Integer.class, String.class, Integer.class,
    //  id             pfd            timep_n     timep_k
        Integer.class, Boolean.class, Time.class, Time.class
    };
    private static final Class<?>[] RASP_TYPES = new Class<?>[] {
    //  nrasp          pcod           nned           denn
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  datap        time_n      time_k      vidp
        Date.class,  Time.class, Time.class, Integer.class,
    //  cdol          cpol           id             pfd
        String.class, Integer.class, Integer.class, Boolean.class
    };
    private static final Class<?>[] TALON_TYPES = new Class<?>[] {
    //  id             ntalon         nrasp          pcod_sp
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  cdol          cdol_kem       vidp           timepn
        String.class, Integer.class, Integer.class, Time.class,
    //  timepk      datapt      datap       timep
        Time.class, Date.class, Date.class, Time.class,
    //  cpol
        Integer.class
    };

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

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
        rsmVidp = new TResultSetMapper<>(IntegerClassifier.class, VIDP_FIELD_NAMES);
    }

////////////////////////////////////////////////////////////////////////
//                       Private Methods                              //
////////////////////////////////////////////////////////////////////////

    /**
     * Вызывается при вызове открытого метода удаления. Заменяет признак
     * талона на 4 (отмененный талон), в случае если признак равен 2 или 3.
     */
    private void updateTalonPrvCpodr(final long datan, final long datak, final int cpodr)
            throws KmiacServerException, TException {
        final int startPrvToUpdate = 2;
        final int endPrvToUpdate = 3;
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE e_talon SET prv = 4 "
                + "WHERE datap >= ? AND datap <= ? AND cpol = ? AND (prv = ? OR prv = ?) ;",
                false, new Date(datan), new Date(datak), cpodr, startPrvToUpdate, endPrvToUpdate);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    /**
     * Вызывается при вызове открытого метода удаления. Заменяет признак
     * талона на 4 (отмененный талон), в случае если признак равен 2 или 3.
     */
    private void updateTalonPrvCdol(final long datan, final long datak, final int cpodr,
            final String cdol) throws KmiacServerException, TException {
        final int startPrvToUpdate = 2;
        final int endPrvToUpdate = 3;
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE e_talon SET prv = 4 "
                + "WHERE datap >= ? AND datap <= ? AND cpol = ? AND cdol = ? "
                + "AND (prv = ? OR prv = ?) ;",
                false, new Date(datan), new Date(datak), cpodr, cdol, startPrvToUpdate,
                endPrvToUpdate);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    /**
     * Вызывается при вызове открытого метода удаления. Заменяет признак
     * талона на 4 (отмененный талон), в случае если признак равен 2 или 3.
     */
    private void updateTalonPrvVrach(final long datan, final long datak, final int cpodr,
            final int pcodvrach, final String cdol) throws KmiacServerException, TException {
        final int startPrvToUpdate = 2;
        final int endPrvToUpdate = 3;
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE e_talon SET prv = 4 "
                + "WHERE datap >= ? AND datap <= ? AND cpol = ? AND cdol =? AND pcod_sp = ? "
                + "AND (prv = ? OR prv = ?) ;",
                false, new Date(datan), new Date(datak), cpodr, cdol, pcodvrach,
                startPrvToUpdate, endPrvToUpdate);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

//////////////////////// Start/Stop Methods ////////////////////////////////////

    @Override
    public final void start() throws Exception {
        ThriftGenTalons.Processor<Iface> proc =
                new ThriftGenTalons.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.info("Start serverGenTalons");
        thrServ.serve();
    }

    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
            log.info("Stop serverGenTalons");
        }
    }

//////////////////////// Configuration Methods ////////////////////////////////////

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Auto-generated method stub
    }

//////////////////////// Select Methods ////////////////////////////////////

    @Override
    public final List<Spec> getAllSpecForPolikliniki(final int cpodr)
            throws KmiacServerException, SpecNotFoundException, TException {
        final String sqlQuery = "SELECT DISTINCT n_s00.pcod, n_s00.name FROM n_s00 "
            + "INNER JOIN s_mrab ON n_s00.pcod = s_mrab.cdol "
            + "WHERE s_mrab.cpodr = ? ORDER BY n_s00.name";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
            List<Spec> tmpList = rsmSpec.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new SpecNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Vrach> getVrachForCurrentSpec(final int cpodr, final String cdol)
            throws KmiacServerException, VrachNotFoundException, TException {
        final String  sqlQuery = "SELECT s_vrach.pcod, s_vrach.fam, s_vrach.im, s_vrach.ot, "
            + "s_mrab.cdol FROM s_vrach INNER JOIN s_mrab ON s_vrach.pcod=s_mrab.pcod "
            + "WHERE s_mrab.cpodr=? AND s_mrab.cdol=? AND s_mrab.datau is null "
            + "ORDER BY fam, im, ot";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            List<Vrach> tmpList = rsmVrach.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new VrachNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final Calend getCalendar(final long datacal) throws KmiacServerException,
            CalendNotFoundException, TException {
        final String  sqlQuery = "SELECT datacal, dweek, nweek, cday, cmonth, cyear, "
            + "pr_rab, d_rab FROM e_calendar WHERE datacal=? ORDER BY datacal";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, new Date(datacal))) {
            if (acrs.getResultSet().next()) {
                Calend tmpCalend = rsmCalendar.map(acrs.getResultSet());
                return tmpCalend;
            } else {
                throw new CalendNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Norm> getNorm(final int cpodr, final String cdol)
            throws KmiacServerException, TException, NormNotFoundException {
        final String  sqlQuery = "SELECT cdol, vidp, dlit, cpol, id "
            + "FROM e_norm WHERE cpol=? AND cdol =? ORDER BY vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            List<Norm> tmpList = rsmNorm.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NormNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Ndv> getNdv(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException, NdvNotFoundException {
        final String  sqlQuery = "SELECT pcod, datan, datak, cdol, cpol, id "
            + "FROM e_ndv WHERE cpol=? AND pcod =? AND cdol =? ORDER BY datan, datak";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            List<Ndv>tmpList = rsmNdv.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NdvNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Nrasp> getNrasp(final int cpodr, final int pcodvrach, final String cdol,
            final int cxema) throws KmiacServerException, TException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k "
            + "FROM e_nrasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "AND cxema =? ORDER BY denn, vidp, time_n";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol, cxema)) {
            List<Nrasp> tmpList = rsmNrasp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NraspNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Rasp> getRasp(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException, RaspNotFoundException {
        final String  sqlQuery = "SELECT nrasp, pcod, nned, denn, datap, time_n, time_k, "
            + "vidp, cdol, cpol, id, pfd FROM e_rasp WHERE cpol=? AND pcod =? AND cdol =? "
            + "ORDER BY datap, vidp, time_n";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            List<Rasp> tmpList = rsmRasp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new RaspNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Talon> getTalon(final int cpodr, final int pcodvrach, final String cdol,
            final long datap) throws KmiacServerException, TalonNotFoundException {
        final String  sqlQuery = "SELECT id, ntalon, nrasp, pcod_sp, cdol, cdol_kem, "
            + "vidp, timepn, timepk, datapt, datap , timep, cpol "
            + "FROM e_talon WHERE cpol=? AND pcod_sp =? AND cdol =? AND datap =? "
            + "ORDER BY datap, timepn, timepk, vidp";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, cpodr, pcodvrach, cdol, new java.sql.Date(datap))) {
            List<Talon> tmpList = rsmTalon.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new TalonNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<IntegerClassifier> getVidp() throws KmiacServerException, TException,
            VidpNotFoundException {
        final String  sqlQuery = "SELECT pcod, name FROM e_vidp "
                + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            List<IntegerClassifier> tmpList = rsmVidp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new VidpNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Nrasp> getNraspCpodr(final int cpodr) throws KmiacServerException,
            TException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k FROM e_nrasp "
            + "WHERE cpol =? "
            + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
            List<Nrasp> tmpList = rsmNrasp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NraspNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Nrasp> getNraspCdol(final int cpodr, final String cdol)
            throws KmiacServerException, TException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k FROM e_nrasp "
            + "WHERE cpol =? AND cdol =? "
            + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, cdol)) {
            List<Nrasp> tmpList = rsmNrasp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NraspNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Nrasp> getNraspVrach(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
                + "cxema, cdol, cpol, id, pfd, timep_n, timep_k FROM e_nrasp "
                + "WHERE cpol =? AND pcod = ? AND cdol =? "
                + "ORDER BY pcod";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpodr, pcodvrach, cdol)) {
            List<Nrasp> tmpList = rsmNrasp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new NraspNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final int getTalonCountCpodr(final long datan, final long datak, final int cpodr)
            throws KmiacServerException, TException {
        final String  sqlQuery = "SELECT count(*) FROM e_talon "
                + "WHERE datap >= ? AND datap <= ?  AND cpol = ? ";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, new Date(datan), new Date(datak), cpodr)) {
            acrs.getResultSet().next();
            return acrs.getResultSet().getInt("count");
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final int getTalonCountCdol(final long datan, final long datak,
            final int cpodr, final String cdol) throws KmiacServerException, TException {
        final String  sqlQuery = "SELECT count(*) FROM e_talon "
                + "WHERE datap >= ? AND datap <= ?  AND cpol = ? AND cdol = ?";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, new Date(datan), new Date(datak), cpodr, cdol)) {
            acrs.getResultSet().next();
            return acrs.getResultSet().getInt("count");
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final int getTalonCountVrach(final long datan, final long datak, final int cpodr,
            final int pcodvrach, final String cdol) throws KmiacServerException, TException {
        final String  sqlQuery = "SELECT count(*) FROM e_talon "
                + "WHERE datap >= ? AND datap <= ?  AND cpol = ? AND cdol = ? AND pcod_sp = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, new Date(datan), new Date(datak), cpodr, cdol, pcodvrach)) {
            acrs.getResultSet().next();
            return acrs.getResultSet().getInt("count");
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

//////////////////////// Add Methods ////////////////////////////////////

    @Override
    public final void addRasp(final List<Rasp> rasp) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Rasp elemRasp : rasp) {
                sme.execPreparedT("INSERT INTO e_rasp (nrasp, pcod, nned, denn, datap, time_n, "
                    + "time_k, vidp, cdol, cpol, pfd) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    false, elemRasp, RASP_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }

    }

    @Override
    public final void addNrasp(final List<Nrasp> nrasp) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Nrasp elemNrasp : nrasp) {
                sme.execPreparedT("INSERT INTO e_nrasp (pcod, denn, vidp, time_n, time_k, "
                    + "cxema, cdol, cpol, pfd, timep_n, timep_k) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    false, elemNrasp, NRASP_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void addNdv(final Ndv ndv) throws KmiacServerException, TException {
        final int[] indexes = {0, 1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO e_ndv (pcod, datan, datak, cdol, cpol) "
                + "VALUES (?, ?, ?, ?, ?);",
                false, ndv, NDV_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void addNorm(final List<Norm> norm) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Norm elemNorm : norm) {
                sme.execPreparedT("INSERT INTO e_norm (cdol, vidp, dlit, cpol) "
                    + "VALUES (?, ?, ?, ?);",
                    false, elemNorm, NORM_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void addTalons(final List<Talon> talon) throws KmiacServerException,
            TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Talon elemTalon : talon) {
                sme.execPreparedT("INSERT INTO e_talon (ntalon, nrasp, pcod_sp, cdol, "
                    + "cdol_kem, vidp, timepn, timepk, datapt, datap, timep, cpol) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    false, elemTalon, TALON_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

//////////////////////// Update Methods ////////////////////////////////////

    @Override
    public final void updateNrasp(final List<Nrasp> nrasp) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 8};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Nrasp elemNrasp : nrasp) {
                sme.execPreparedT("UPDATE e_nrasp SET "
                    + "pcod = ?, denn = ?, vidp = ?, time_n = ?, time_k = ?, "
                    + "cxema = ?, cdol = ?, cpol = ?, pfd = ?, timep_n = ?, timep_k = ? "
                    + "WHERE id = ?;",
                    false, elemNrasp, NRASP_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void updateNorm(final List<Norm> norm) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Norm elemNorm : norm) {
                sme.execPreparedT("UPDATE e_norm SET "
                    + "cdol = ?, vidp = ?, dlit = ?, cpol = ? "
                    + "WHERE id = ?;",
                    false, elemNorm, NORM_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void updateNdv(final List<Ndv> ndv) throws KmiacServerException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Ndv elemNdv : ndv) {
                sme.execPreparedT("UPDATE e_ndv SET "
                    + "pcod = ?, datan = ?, datak = ?, cdol = ?, cpol =? "
                    + "WHERE id = ?;",
                    false, elemNdv, NDV_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

//////////////////////// Delete Methods ////////////////////////////////////

    @Override
    public final void deleteNrasp(final int cpodr, final int pcodvrach, final String cdol)
            throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_nrasp WHERE cpol =? AND pcod = ? AND cdol = ?;",
                    false, cpodr, pcodvrach, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteNdv(final int id) throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_ndv WHERE id =?;",
                    false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void deleteRaspCpodr(final long datan, final long datak, final int cpodr)
            throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_rasp WHERE datap >=? AND datap <= ? AND cpol = ?;",
                    false, new Date(datan), new Date(datak), cpodr);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }

    }

    @Override
    public final void deleteRaspCdol(final long datan, final long datak,
            final int cpodr, final String cdol) throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_rasp WHERE datap >=? AND datap <= ? AND cpol = ? "
                    + "AND cdol = ?;",
                    false, new Date(datan), new Date(datak), cpodr, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }

    }

    @Override
    public final void deleteRaspVrach(final long datan, final long datak, final int cpodr,
            final int pcodvrach, final String cdol) throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_rasp WHERE datap >=? AND datap <= ? AND cpol = ? "
                    + "AND pcod = ? AND cdol = ?;",
                    false, new Date(datan), new Date(datak), cpodr, pcodvrach, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void deleteTalonCpodr(final long datan, final long datak, final int cpodr)
            throws KmiacServerException, TException {
        updateTalonPrvCpodr(datan, datak, cpodr);
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_talon WHERE datap >=? AND datap <= ? AND cpol = ?;",
                    false, new Date(datan), new Date(datak), cpodr);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void deleteTalonCdol(final long datan, final long datak, final int cpodr,
            final String cdol) throws KmiacServerException, TException {
        updateTalonPrvCdol(datan, datak, cpodr, cdol);
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_talon WHERE datap >=? AND datap <= ? AND cpol = ? "
                    + "AND cdol = ?;",
                    false, new Date(datan), new Date(datak), cpodr, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void deleteTalonVrach(final long datan, final long datak, final int cpodr,
            final int pcodvrach, final String cdol) throws KmiacServerException, TException {
        updateTalonPrvVrach(datan, datak, cpodr, pcodvrach, cdol);
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_talon WHERE datap >=? AND datap <= ? AND cpol = ? "
                    + "AND pcod_sp = ? AND cdol = ?;",
                    false, new Date(datan), new Date(datak), cpodr, pcodvrach, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }
}

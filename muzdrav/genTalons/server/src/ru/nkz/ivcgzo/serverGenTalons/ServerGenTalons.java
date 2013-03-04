package ru.nkz.ivcgzo.serverGenTalons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import ru.nkz.ivcgzo.thriftGenTalon.AztNotFoundException;
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
import ru.nkz.ivcgzo.thriftGenTalon.RepStruct;
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
        "cxema", "cdol", "cpol", "id", "pfd", "timep_n", "timep_k", "time_int_n", "time_int_k"
    };
    private static final String[] RASP_FIELD_NAMES = {
        "nrasp", "pcod", "nned", "denn", "datap", "time_n",
        "time_k", "vidp", "cdol", "cpol", "id", "pfd"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "nrasp", "pcod_sp", "cdol",
        "cdol_kem", "vidp", "timepn", "timepk", "datapt",
        "datap", "timep", "cpol", "prv"
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
    //  id             pfd            timep_n     timep_k	  time_int_n     time_int_k
        Integer.class, Boolean.class, Time.class, Time.class, Time.class, Time.class
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
        Time.class, Timestamp.class, Date.class, Time.class,
    //  cpol			prv
        Integer.class, Integer.class
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
            throws KmiacServerException {
        final int startPrvToUpdate = 2;
        final int endPrvToUpdate = 3;
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE e_talon SET prv = 4 "
                + "WHERE datap >= ? AND datap <= ? AND cpol = ? AND (prv = ? OR prv = ?) ;",
                false, new Date(datan), new Date(datak), cpodr, startPrvToUpdate, endPrvToUpdate);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Вызывается при вызове открытого метода удаления. Заменяет признак
     * талона на 4 (отмененный талон), в случае если признак равен 2 или 3.
     */
    private void updateTalonPrvCdol(final long datan, final long datak, final int cpodr,
            final String cdol) throws KmiacServerException {
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
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Вызывается при вызове открытого метода удаления. Заменяет признак
     * талона на 4 (отмененный талон), в случае если признак равен 2 или 3.
     */
    private void updateTalonPrvVrach(final long datan, final long datak, final int cpodr,
            final int pcodvrach, final String cdol) throws KmiacServerException {
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
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

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
            throws KmiacServerException, SpecNotFoundException {
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
            throws KmiacServerException, VrachNotFoundException {
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
            CalendNotFoundException {
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
            throws KmiacServerException, NormNotFoundException {
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
            throws KmiacServerException, NdvNotFoundException {
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
            final int cxema) throws KmiacServerException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k, time_int_n, time_int_k "
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
            throws KmiacServerException, RaspNotFoundException {
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
            + "vidp, timepn, timepk, datapt, datap , timep, cpol, prv "
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
    public final List<IntegerClassifier> getVidp() throws KmiacServerException,
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
            NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k, time_int_n, time_int_k FROM e_nrasp "
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
            throws KmiacServerException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
            + "cxema, cdol, cpol, id, pfd, timep_n, timep_k, time_int_n, time_int_k FROM e_nrasp "
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
            throws KmiacServerException, NraspNotFoundException {
        final String  sqlQuery = "SELECT pcod, denn, vidp, time_n, time_k, "
                + "cxema, cdol, cpol, id, pfd, timep_n, timep_k, time_int_n, time_int_n FROM e_nrasp "
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
            throws KmiacServerException {
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
            final int cpodr, final String cdol) throws KmiacServerException {
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
            final int pcodvrach, final String cdol) throws KmiacServerException {
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

    @Override
    public final List<IntegerClassifier> getAzt() throws KmiacServerException,
            AztNotFoundException {
        final String sqlQuery = "SELECT pcod, name FROM n_azt";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAzt =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAzt.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

//////////////////////// Add Methods ////////////////////////////////////

    @Override
    public final void addRasp(final List<Rasp> rasp) throws KmiacServerException {
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
	public final int addRaspReturnId(Rasp rasp) throws KmiacServerException,
			TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
        	sme.execPreparedT("INSERT INTO e_rasp (nrasp, pcod, nned, denn, datap, time_n, time_k, vidp, cdol, cpol, pfd) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", true, rasp, RASP_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
			((SQLException) e.getCause()).printStackTrace();
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
	}

    
    @Override
    public final void addNrasp(final List<Nrasp> nrasp) throws KmiacServerException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Nrasp elemNrasp : nrasp) {
                sme.execPreparedT("INSERT INTO e_nrasp (pcod, denn, vidp, time_n, time_k, "
                    + "cxema, cdol, cpol, pfd, timep_n, timep_k, time_int_n, time_int_k) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    false, elemNrasp, NRASP_TYPES, indexes);
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void addNdv(final Ndv ndv) throws KmiacServerException {
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
    public final void addNorm(final List<Norm> norm) throws KmiacServerException {
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
    public final void addTalons(final List<Talon> talon) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Talon elemTalon : talon) {
                sme.execPreparedT("INSERT INTO e_talon (ntalon, nrasp, pcod_sp, cdol, "
                    + "cdol_kem, vidp, timepn, timepk, datapt, datap, timep, cpol, prv) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
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
    public final void updateNrasp(final List<Nrasp> nrasp) throws KmiacServerException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 8};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Nrasp elemNrasp : nrasp) {
                sme.execPreparedT("UPDATE e_nrasp SET "
                    + "pcod = ?, denn = ?, vidp = ?, time_n = ?, time_k = ?, "
                    + "cxema = ?, cdol = ?, cpol = ?, pfd = ?, timep_n = ?, timep_k = ?, time_int_n = ?, time_int_k = ? "
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
    public final void updateNorm(final List<Norm> norm) throws KmiacServerException {
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
    public final void updateNdv(final List<Ndv> ndv) throws KmiacServerException {
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
            throws KmiacServerException {
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
    public final void deleteNorm(final int cpodr, final String cdol) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM e_norm WHERE cpol =? AND cdol = ?;",
                false, cpodr, cdol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteNdv(final int id) throws KmiacServerException {
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
            throws KmiacServerException {
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
            final int cpodr, final String cdol) throws KmiacServerException {
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
            final int pcodvrach, final String cdol) throws KmiacServerException {
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
            throws KmiacServerException {
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
            final String cdol) throws KmiacServerException {
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
            final int pcodvrach, final String cdol) throws KmiacServerException {
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

	@Override
	public String printReport(RepStruct rep) throws KmiacServerException,
			TException {
		String path = null;
		int kol_int = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("napr", ".htm").getAbsolutePath()), "utf-8")) {
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Сводки</title>");
			sb.append("</head>");
			sb.append("<body>");

	        switch (rep.getNsv()) {
	        case 0:
				sb.append("<p align=\"center\" >Списки пациентов, записанных на прием");
				sb.append(String.format(" %1$td.%1$tm.%1$tY <br>", new Date(rep.getDatan())));
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery("select v.fam ||' '|| v.im ||' '|| v.ot as fio, s.name as cdol from s_vrach v JOIN s_mrab m ON (v.pcod = m.pcod) LEFT JOIN n_s00 s ON (s.pcod = m.cdol) where v.pcod = ? AND s.pcod = ?", rep.getPcod(), rep.getCdol());
					if (acr.getResultSet().next()){
						sb.append(String.format("%s, %s <br>", acr.getResultSet().getString("fio"), acr.getResultSet().getString("cdol")));
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</p>");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\">№ п/п</th><th>ФИО пациента, Д.р.</th><th>№ амб.карты, № уч.</th><th>Адрес, тел.</th><th>Время приема</th><th>Прием</th><th>Записан</th></tr>");
//				sb.append("<p align=\"left\"></p>");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT (CASE p.npasp<>-1 WHEN TRUE THEN p.fam ||' '|| p.im ||' '|| p.ot ELSE e.fed_fio END) as fio, "+ 
							"p.datar, p.adm_ul ||' '|| p.adm_dom ||' - '|| p.adm_kv as adres, "+
							"p.tel, e.timep::char(10), v.name as priem, n.nambk, n.nuch, "+
							"(CASE e.prv=0 OR e.prv=1 WHEN TRUE THEN 'интернет' ELSE (CASE e.prv=2 WHEN TRUE THEN 'регистратура' ELSE (CASE e.prv=3 WHEN TRUE THEN 'инфомат' ELSE (CASE e.prv=4 WHEN TRUE THEN 'нет приема' ELSE NULL END) END) END) END)as zap " +
							"FROM e_talon e LEFT JOIN patient p ON (e.npasp = p.npasp) " +
							"LEFT JOIN e_vidp v ON (e.vidp = v.pcod) " +
							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = e.cpol) " +
							"WHERE e.npasp<>0 AND e.cpol=? AND pcod_sp=? AND e.cdol=? AND e.datap=?" +
							"ORDER BY e.timep",
						    rep.getCpol(), rep.getPcod(), rep.getCdol(), new Date(rep.getDatan()));
					if (acr.getResultSet().next()){
						do {
							String date_birthay = "";
							String nambk = "";
							String nuch = "";
							String adres = "";
							String telefon = "";
							if (acr.getResultSet().getDate("datar") != null) date_birthay = ", Д.р. "+sdf.format(new Date(acr.getResultSet().getDate("datar").getTime()));
							if (acr.getResultSet().getString("nambk") != null) nambk = "№ амб. "+acr.getResultSet().getString("nambk");
							if (acr.getResultSet().getInt("nuch") != 0) nuch = ", № уч. "+acr.getResultSet().getString("nuch");
							if (acr.getResultSet().getString("adres") != null) adres = acr.getResultSet().getString("adres");
							if (acr.getResultSet().getString("tel") != null) telefon = ", тел. "+acr.getResultSet().getString("tel");
							sb.append("<tr bgcolor=\"white\">");
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getRow()));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("fio")+date_birthay));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", nambk+nuch ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", adres+telefon ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("timep") ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("priem") ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("zap"))); 
							sb.append("</tr>");
							
							//sb.append(String.format("<tr bgcolor=\"white\"><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td></tr>", acr.getResultSet().getRow(), acr.getResultSet().getString("fio"), acr.getResultSet().getString("nambk")+", "+acr.getResultSet().getString("nuch"), acr.getResultSet().getString("adres")+", "+acr.getResultSet().getString("tel"), acr.getResultSet().getString("timep"), acr.getResultSet().getString("priem"), acr.getResultSet().getString("zap")));
						}
						while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
				break;        

	        case 1: 
				sb.append("<p align=\"center\" >Количество неиспользованных талонов");
				sb.append(String.format(" %1$td.%1$tm.%1$tY <br>", new Date(rep.getDatan())));
				sb.append("</p>");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\">ФИО врача, специальность</th><th>Всего талонов</th><th>Неиспользовано талонов</th></tr>");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT e.pcod_sp, e.cdol, get_short_fio(v.fam, v.im, v.ot) as fio, s.name as name, count(*) as kol "+
							"FROM e_talon e LEFT JOIN s_vrach v ON (e.pcod_sp = v.pcod) "+ 
							"LEFT JOIN n_s00 s ON (s.pcod = e.cdol) "+
							"WHERE e.cpol = ? AND datap = ? "+
							"GROUP BY e.pcod_sp, fio, e.cdol, s.name "+
							"ORDER BY e.cdol, e.pcod_sp", rep.getCpol(), new Date(rep.getDatan()));
					if (acr.getResultSet().next()){
						do {
							sb.append("<tr bgcolor=\"white\">");
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("fio")+", "+acr.getResultSet().getString("name").toLowerCase()));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getInt("kol")));
							AutoCloseableResultSet acrs1 = sse.execPreparedQuery("select count(*) as kol from e_talon where npasp = 0 AND cpol = ? AND datap = ? AND pcod_sp = ? AND cdol = ?", rep.getCpol(), new Date(rep.getDatan()), acr.getResultSet().getInt("pcod_sp"), acr.getResultSet().getString("cdol"));
							if (acrs1.getResultSet().next())
								sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acrs1.getResultSet().getInt("kol"))); 
							sb.append("</tr>");
						}
						while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
	        	break;        

	        case 2:
				sb.append("<p align=\"center\" >Списки пациентов, отмененного приема за период ");
				sb.append(String.format("с  %1$td.%1$tm.%1$tY ", new Date(rep.getDatan())));
				sb.append(String.format(" по  %1$td.%1$tm.%1$tY <br>", new Date(rep.getDatak())));
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery("select v.fam ||' '|| v.im ||' '|| v.ot as fio, s.name as cdol from s_vrach v JOIN s_mrab m ON (v.pcod = m.pcod) LEFT JOIN n_s00 s ON (s.pcod = m.cdol) where v.pcod = ? AND s.pcod = ?", rep.getPcod(), rep.getCdol());
					if (acr.getResultSet().next()){
						sb.append(String.format("%s, %s <br>", acr.getResultSet().getString("fio"), acr.getResultSet().getString("cdol")));
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</p>");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\">№ п/п</th><th>ФИО пациента, Д.р.</th><th>№ амб.карты, № уч.</th><th>Адрес, тел.</th><th>Время приема</th><th>Прием</th><th>Дата</th></tr>");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT (CASE p.npasp<>-1 WHEN TRUE THEN p.fam ||' '|| p.im ||' '|| p.ot ELSE e.fed_fio END) as fio, "+ 
							"p.datar, p.adm_ul ||' '|| p.adm_dom ||' - '|| p.adm_kv as adres, "+
							"p.tel, e.timep::char(10), e.datap, v.name as priem, n.nambk, n.nuch "+
							"FROM e_talon e LEFT JOIN patient p ON (e.npasp = p.npasp) " +
							"LEFT JOIN e_vidp v ON (e.vidp = v.pcod) " +
							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = e.cpol) " +
							"WHERE e.npasp<>0 AND e.prv=4 AND e.cpol=? AND pcod_sp=? AND e.cdol=? AND e.datap between ? AND ?" +
							"ORDER BY e.datap, e.timep",
						    rep.getCpol(), rep.getPcod(), rep.getCdol(), new Date(rep.getDatan()), new Date(rep.getDatak()));
					if (acr.getResultSet().next()){
						do {
							String date_birthay = "";
							String nambk = "";
							String nuch = "";
							String adres = "";
							String telefon = "";
							if (acr.getResultSet().getDate("datar") != null) date_birthay = ", Д.р. "+sdf.format(new Date(acr.getResultSet().getDate("datar").getTime()));
							if (acr.getResultSet().getString("nambk") != null) nambk = "№ амб. "+acr.getResultSet().getString("nambk");
							if (acr.getResultSet().getInt("nuch") != 0) nuch = ", № уч. "+acr.getResultSet().getString("nuch");
							if (acr.getResultSet().getString("adres") != null) adres = acr.getResultSet().getString("adres");
							if (acr.getResultSet().getString("tel") != null) telefon = ", тел. "+acr.getResultSet().getString("tel");
							sb.append("<tr bgcolor=\"white\">");
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getRow()));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("fio")+date_birthay));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", nambk+nuch ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", adres+telefon ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("timep") ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("priem") ));
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", sdf.format(new Date(acr.getResultSet().getDate("datap").getTime())))); 
							sb.append("</tr>");
							
							//sb.append(String.format("<tr bgcolor=\"white\"><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td><td style=\"font: 12px times new roman;\"> %s </td></tr>", acr.getResultSet().getRow(), acr.getResultSet().getString("fio"), acr.getResultSet().getString("nambk")+", "+acr.getResultSet().getString("nuch"), acr.getResultSet().getString("adres")+", "+acr.getResultSet().getString("tel"), acr.getResultSet().getString("timep"), acr.getResultSet().getString("priem"), acr.getResultSet().getString("zap")));
						}
						while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
				break;        

	        case 3:
				sb.append("<p align=\"center\" >Количество выданных талонов за период ");
				sb.append(String.format("с  %1$td.%1$tm.%1$tY ", new Date(rep.getDatan())));
				sb.append(String.format(" по  %1$td.%1$tm.%1$tY <br>", new Date(rep.getDatak())));
				sb.append("</p>");
				sb.append("<table width=\"75%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\">Запись</th><th>Количество</th></tr>");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT (CASE e.prv=0 OR e.prv=1 WHEN TRUE THEN 'Через интернет' ELSE (CASE e.prv=2 WHEN TRUE THEN 'Через регистратуру' ELSE (CASE e.prv=3 WHEN TRUE THEN 'Через инфомат' ELSE NULL END) END) END)as zap," +
							"(CASE e.prv=2 WHEN TRUE THEN count(*) ELSE 0 END)as kol_reg,"+
							"(CASE e.prv=3 WHEN TRUE THEN count(*) ELSE 0 END)as kol_inf, "+
							"(CASE e.prv=1 OR e.prv=0 WHEN TRUE THEN count(*) ELSE 0 END)as kol_int, e.prv "+
							"FROM e_talon e " +
							"WHERE e.npasp<>0 AND e.prv<>4 AND e.cpol=? AND e.datap between ? AND ?" +
							"GROUP BY e.prv " +
							"ORDER BY e.prv",
						    rep.getCpol(), new Date(rep.getDatan()), new Date(rep.getDatak()));
					if (acr.getResultSet().next()){
						do {
							sb.append("<tr bgcolor=\"white\">");
					        switch (acr.getResultSet().getInt("prv")) {
					        	case 0:
//					        		sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getInt("kol_int")));
					        		kol_int += acr.getResultSet().getInt("kol_int");
					        		break;
					        	case 1:
									sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("zap")));
					        		kol_int += acr.getResultSet().getInt("kol_int");
					        		sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", kol_int));
					        		break;
					        	case 2:
									sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("zap")));
					        		sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getInt("kol_reg"))); 
					        		break;
					        	case 3:
									sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("zap")));
					        		sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getInt("kol_inf"))); 
					        		break;
					        }
					        sb.append("</tr>");
						}
						while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
				break;        

	        case 4:
				sb.append("<p align=\"center\" >Расписание работы врачей ");
				sb.append("</p>");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\">ФИО врача</th><th>Пн.</th><th>Вт.</th><th>Ср.</th><th>Чт.</th><th>Птн.</th></tr>");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT distinct(e.pcod), e.cdol, get_short_fio(v.fam, v.im, v.ot) as fio, s.name as name, e.cxema " +
							"FROM e_nrasp e LEFT JOIN s_vrach v ON (e.pcod = v.pcod) " +
							"LEFT JOIN n_s00 s ON (s.pcod = e.cdol) " +
							"WHERE e.cpol=? AND time_n != '00:00:00'" +
							"ORDER BY e.cdol, e.pcod, e.cxema", rep.getCpol());
					if (acr.getResultSet().next()){
						do{
							sb.append("<tr bgcolor=\"white\">");
							sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", acr.getResultSet().getString("fio")+", "+acr.getResultSet().getString("name").toLowerCase()));
							for(int idn=1; idn<6; idn++){
								String mint= "";
								String maxt= "";
//TODO запрос возвращает одну строку, даже если этой строки нет 
								AutoCloseableResultSet acrs1 = sse.execPreparedQuery("select min(time_n) as min_time, max(time_k) as max_time from e_nrasp where denn = ? AND cpol = ? AND cxema = ? AND pcod = ? AND cdol = ? AND time_n != '00:00:00'", idn, rep.getCpol(), acr.getResultSet().getInt("cxema"), acr.getResultSet().getInt("pcod"), acr.getResultSet().getString("cdol"));
//								AutoCloseableResultSet acrs1 = sse.execPreparedQuery("select min(time_n) as min_time, max(time_k) as max_time from e_nrasp where denn = ? AND cpol = ? AND cxema = ? AND pcod = ? AND cdol = ? AND time_n != '00:00:00' GROUP BY time_n, time_k ORDER BY time_n", idn, rep.getCpol(), acr.getResultSet().getInt("cxema"), acr.getResultSet().getInt("pcod"), acr.getResultSet().getString("cdol"));
						        String cxema = "";
								if (acrs1.getResultSet().next()){
									if (acrs1.getResultSet().getTime("min_time") != null || acrs1.getResultSet().getTime("max_time") != null){
										if (acrs1.getResultSet().getTime("min_time") != null)mint= stf.format(new Time(acrs1.getResultSet().getTime("min_time").getTime()));
										if (acrs1.getResultSet().getTime("max_time") != null)maxt= stf.format(new Time(acrs1.getResultSet().getTime("max_time").getTime()));
										switch (acr.getResultSet().getInt("cxema")) {
							        		case 1:
							        			cxema += "чет. ";
							        			break;
							        		case 2:
							        			cxema += "нечет. ";
							        			break;
							        		default:
							        			break;
								        }
									}
								}
								sb.append(String.format("<td style=\"font: 12px times new roman;\"> %s </td>", cxema+mint+"-"+maxt));
							}
							sb.append("</tr>");
						}while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
				break;        
	        
	        case 5: 
				sb.append("<p align=\"center\" >Печать талонов на прием к врачу");
				sb.append("</p>");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> ");
				try {
					AutoCloseableResultSet acr = sse.execPreparedQuery(
							"SELECT (v.fam||' '|| v.im||' '|| v.ot) as fio, s.name as name, e.timep, e.datap "+
							"FROM e_talon e LEFT JOIN s_vrach v ON (e.pcod_sp = v.pcod) "+ 
							"LEFT JOIN n_s00 s ON (s.pcod = e.cdol) "+
							"WHERE e.cpol = ? AND e.datap = ? AND e.pcod_sp = ? AND e.cdol = ? AND e.vidp != 3  "+
							"ORDER BY e.datap, e.timep", rep.getCpol(), new Date(rep.getDatan()), rep.getPcod(), rep.getCdol());
					if (acr.getResultSet().next()){
						do {
							sb.append("<tr bgcolor=\"white\">");
							for (int i=0; i <= 1; i++) {
								sb.append("<td style=\"font: 12px times new roman;\"> ");
								sb.append(String.format(" Талон на посещение "));
								sb.append(String.format(" %1$td.%1$tm.%1$tY <br>", new Date(acr.getResultSet().getDate("datap").getTime())));
								sb.append(String.format(" %s <br>", acr.getResultSet().getString("name")));
								sb.append(String.format(" Время: %s <br>", stf.format(new Time(acr.getResultSet().getTime("timep").getTime()))));
								sb.append(String.format(" Врач: %s <br>", acr.getResultSet().getString("fio")));
								sb.append("Каб. <br>");
								sb.append("</td>");
								if (i % 2 == 0) acr.getResultSet().next();
							}
							sb.append("</tr>");
						}
						while (acr.getResultSet().next());
					}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}
				sb.append("</table><br>");
	        	break;        

	        default: 
	        	break;
	        }
			
			osw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
			return path;
	}

}

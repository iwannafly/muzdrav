package ru.nkz.ivcgzo.serverInfomat;

import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.OmsNotValidException;
import ru.nkz.ivcgzo.thriftInfomat.PatientHasSomeReservedTalonsOnThisDay;
import ru.nkz.ivcgzo.thriftInfomat.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;
import ru.nkz.ivcgzo.thriftInfomat.ThriftInfomat;
import ru.nkz.ivcgzo.thriftInfomat.ThriftInfomat.Iface;

public class ServerInfomat extends Server implements Iface {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private static Logger log = Logger.getLogger(ServerInfomat.class.getName());
    private TServer tServer;

////////////////////////////////  Mappers   /////////////////////////////////

    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClassifier;
    private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClassifier;
    private TResultSetMapper<TTalon, TTalon._Fields> rsmTalon;
    private TResultSetMapper<TPatient, TPatient._Fields> rsmPatient;
    private TResultSetMapper<TSheduleDay, TSheduleDay._Fields> rsmSheduleDay;

////////////////////////////  Field Name Arrays  ////////////////////////////

    private static final String[] INT_CLASSIFIER_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] STR_CLASSIFIER_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "vidp", "timep", "datap", "npasp", "pcod_sp", "lpu", "spec", "fio", "data"
    };
    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "fam", "im", "ot"
    };
    private static final String[] SHEDULE_DAY_FIELD_NAMES = {
        "time_n", "time_k", "denn", "vidp"
    };


////////////////////////////   Field Type Arrays  ////////////////////////////

/////////////////////////////////////////////////////////////////////////////
//                              Constructors                               //
/////////////////////////////////////////////////////////////////////////////

    public ServerInfomat(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmIntClassifier = new TResultSetMapper<>(
            IntegerClassifier.class, INT_CLASSIFIER_FIELD_NAMES);
        rsmStrClassifier = new TResultSetMapper<>(
            StringClassifier.class, STR_CLASSIFIER_FIELD_NAMES);
        rsmTalon = new TResultSetMapper<>(TTalon.class, TALON_FIELD_NAMES);
        rsmPatient = new TResultSetMapper<>(TPatient.class, PATIENT_FIELD_NAMES);
        rsmSheduleDay = new TResultSetMapper<>(TSheduleDay.class, SHEDULE_DAY_FIELD_NAMES);
    }

////////////////////       Configuration Methods     /////////////////////////////

    @Override
    public void testConnection() throws TException {
        // TODO Тест соединения. ХЗ что тут должно быть
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Сохранение конфигурации пользователя. ХЗ что тут должно быть.
    }

////////////////////////      Start/Stop Methods    /////////////////////////////

    @Override
    public final void start() throws Exception {
        ThriftInfomat.Processor<Iface> proc =
                new ThriftInfomat.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.log(Level.INFO, "infomat server started");
        tServer.serve();
    }

    @Override
    public final void stop() {
        tServer.stop();
        log.log(Level.INFO, "infomat server stopped");
    }

///////////////////////       Select Methods    //////////////////////////////////

    @Override
    public final List<IntegerClassifier> getPoliclinics() throws KmiacServerException {
        final String sqlQuery = "SELECT DISTINCT ON (n_n00.pcod) n_n00.pcod, "
            + "(n_m00.name_s || ', ' || n_n00.name) as name "
            + "FROM n_n00 INNER JOIN n_m00 ON n_m00.pcod = n_n00.clpu "
            + "INNER JOIN e_talon ON n_n00.pcod = e_talon.cpol;";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmIntClassifier.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getSpecialities(final int cpol)
            throws KmiacServerException {
        final String sqlQuery = "SELECT DISTINCT ON (n_s00.pcod) n_s00.pcod, n_s00.name "
                + "FROM n_s00 "
                + "INNER JOIN e_talon ON n_s00.pcod = e_talon.cdol "
                + "WHERE e_talon.cpol = ? AND e_talon.prv = ? AND "
                + "((e_talon.npasp = 0) OR (e_talon.npasp is null));";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpol, 0)) {
            return rsmStrClassifier.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getDoctors(final int cpol, final String cdol)
            throws KmiacServerException {
        final String sqlQuery = "SELECT DISTINCT s_vrach.pcod, s_vrach.fam, s_vrach.im, s_vrach.ot "
                + "FROM s_vrach INNER JOIN e_talon ON s_vrach.pcod = e_talon.pcod_sp "
                + "WHERE e_talon.cpol = ? AND e_talon.cdol = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpol, cdol)) {
            List<IntegerClassifier> tmpList = new ArrayList<IntegerClassifier>();
            ResultSet rs = acrs.getResultSet();
            while (rs.next()) {
                tmpList.add(
                    new IntegerClassifier(
                        rs.getInt(1),
                        String.format("%s %s %s", rs.getString(2), rs.getString(3), rs.getString(4))
                    )
                );
            }
            return tmpList;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TTalon> getTalons(final int cpol, final String cdol, final int pcod)
            throws KmiacServerException {
        final int prv = 0;
        // java.sql.Date не имеет нулевого конструктора, а preparedQuery() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "SELECT id, vidp, timep, datap, npasp, pcod_sp "
                + "FROM e_talon WHERE cpol = ? AND cdol = ? AND pcod_sp = ? "
                + "AND ((datap > ?) OR (datap = ? AND timep >= ?)) AND prv = ? "
                + "AND vidp = 1 ORDER BY datap, timep;";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpol, cdol, pcod, new Date(todayMillisec),
                        new Date(todayMillisec), new Time(System.currentTimeMillis()), prv)) {
            return rsmTalon.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void reserveTalon(final TPatient pat, final TTalon talon)
            throws KmiacServerException, ReserveTalonOperationFailedException,
            PatientHasSomeReservedTalonsOnThisDay {
        final int prv = 3;
        // java.sql.Date не имеет нулевого конструктора, а preparedUpdate() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "UPDATE e_talon SET npasp = ?, dataz = ?, "
            + "prv = ?, id_pvizit = nextval('p_vizit_id_seq') "
            + "WHERE  id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isPatientAlreadyReserveTalonOnThisDay(pat, talon)) {
                int numUpdated = 0;
                numUpdated = sme.execPreparedUpdate(sqlQuery, false, pat.getId(),
                    new Date(todayMillisec), prv, talon.getId());
                if (numUpdated == 1) {
                    sme.setCommit();
                } else {
                    sme.rollbackTransaction();
                    throw new ReserveTalonOperationFailedException();
                }
            } else {
                throw new PatientHasSomeReservedTalonsOnThisDay();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final boolean isPatientAlreadyReserveTalonOnThisDay(final TPatient patient,
            final TTalon talon) throws KmiacServerException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT id FROM e_talon WHERE npasp = ? AND pcod_sp = ? AND datap = ?",
                patient.getId(), talon.getPcodSp(), new Date(talon.getDatap()))) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final TPatient checkOmsAndGetPatient(final String oms)
            throws KmiacServerException, OmsNotValidException {
        final String sqlQuery = "SELECT npasp, fam, im, ot "
            + "FROM patient WHERE ((poms_ser || poms_nom) = ?) OR (poms_nom = ?)";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, oms, oms)) {
            if (acrs.getResultSet().next()) {
                return rsmPatient.map(acrs.getResultSet());
            } else {
                throw new OmsNotValidException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TTalon> getReservedTalon(final int patientId)
            throws KmiacServerException {
        // java.sql.Date не имеет нулевого конструктора, а preparedQuery() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "SELECT id, ntalon, vidp, timep, datap, "
            + "npasp, pcod_sp, dataz, prv, "
            + "n_s00.name as spec, (fam || ' ' || im || ' ' || ot) as fio, n_n00.name_s as lpu "
            + "FROM e_talon "
            + "INNER JOIN n_s00 ON n_s00.pcod = e_talon.cdol "
            + "INNER JOIN s_vrach ON s_vrach.pcod = e_talon.pcod_sp "
            + "INNER JOIN n_n00 ON n_n00.pcod = e_talon.cpol "
            + "WHERE datap >= ? "
            + "AND npasp = ? AND npasp <> 0 ORDER BY datap, timep;";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, new Date(todayMillisec), patientId)) {
            return rsmTalon.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final void releaseTalon(final TTalon talon) throws KmiacServerException,
            ReleaseTalonOperationFailedException {
        final int defaultNpasp = 0;
        final int defaultPrv = 0;
        final int defaultIdPVizit = 0;
        final String sqlQuery = "UPDATE e_talon SET npasp = ?, dataz = ?, prv = ?, id_pvizit = ? "
                + "WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            final int numUpdated = sme.execPreparedUpdate(
                sqlQuery, false, defaultNpasp, null, defaultPrv, defaultIdPVizit, talon.getId());
            if (numUpdated == 1) {
                sme.setCommit();
            } else {
                sme.rollbackTransaction();
                throw new ReleaseTalonOperationFailedException();
            }
        } catch (SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TSheduleDay> getShedule(final int pcod, final int cpol, final String cdol)
            throws KmiacServerException {
        final String sqlQuery = "SELECT time_n, time_k, vidp, denn "
            + "FROM e_nrasp WHERE pcod =? AND cpol = ? AND cdol = ? ORDER BY time_n";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, pcod, cpol, cdol)) {
            return rsmSheduleDay.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

///////////////////////       Add Methods    /////////////////////////////////////



///////////////////////      Update Methods   ////////////////////////////////////



///////////////////////      Delete Methods   ////////////////////////////////////



}

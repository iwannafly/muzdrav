package ru.nkz.ivcgzo.serverReception;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
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
import ru.nkz.ivcgzo.serverManager.serverManager;
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
import ru.nkz.ivcgzo.thriftReception.Patient;
import ru.nkz.ivcgzo.thriftReception.PatientHasSomeReservedTalonsOnThisDay;
import ru.nkz.ivcgzo.thriftReception.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftReception.PoliclinicNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ThriftReception;
import ru.nkz.ivcgzo.thriftReception.ThriftReception.Iface;
import ru.nkz.ivcgzo.thriftReception.Vidp;
import ru.nkz.ivcgzo.thriftReception.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftReception.VrachNotFoundException;

public class ServerReception extends Server implements Iface {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private TServer thrServ;

    private static Logger log = Logger.getLogger(ServerReception.class.getName());

//////////////////////////////// Mappers /////////////////////////////////

    private TResultSetMapper<Patient, Patient._Fields> rsmPatient;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmPoliclinic;
    private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmSpec;
    private TResultSetMapper<Vidp, Vidp._Fields> rsmVidp;
    private TResultSetMapper<Talon, Talon._Fields> rsmTalon;
    private TResultSetMapper<Talon, Talon._Fields> rsmReservedTalon;

//////////////////////////// Field Name Arrays ////////////////////////////

    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
    };
    private static final String[] POLICLINIC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] SPEC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] VIDP_FIELD_NAMES = {
        "pcod", "name", "vcolor"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "vidp", "timep", "datap", "npasp", "dataz", "prv", "pcod_sp"
    };
    private static final String[] RESERVED_TALON_FIELD_NAMES = {
        "id", "ntalon", "vidp", "timep", "datap", "npasp", "dataz", "prv", "pcod_sp",
        "spec", "fio"
    };

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

    public ServerReception(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmPatient = new TResultSetMapper<>(Patient.class, PATIENT_FIELD_NAMES);
        rsmPoliclinic = new TResultSetMapper<>(IntegerClassifier.class, POLICLINIC_FIELD_NAMES);
        rsmSpec = new TResultSetMapper<>(StringClassifier.class, SPEC_FIELD_NAMES);
        rsmVidp = new TResultSetMapper<>(Vidp.class, VIDP_FIELD_NAMES);
        rsmTalon = new TResultSetMapper<>(Talon.class, TALON_FIELD_NAMES);
        rsmReservedTalon = new TResultSetMapper<>(Talon.class, RESERVED_TALON_FIELD_NAMES);
    }

////////////////////////////////////////////////////////////////////////
//                       Private Methods                              //
////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

//////////////////////// Start/Stop Methods /////////////////////////////

    @Override
    public final void start() throws Exception {
        ThriftReception.Processor<Iface> proc =
                new ThriftReception.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.recThrPort)).processor(proc));
        log.info("Start serverReception");
        thrServ.serve();
    }

    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
            log.info("Stop serverReception");
        }
    }

//////////////////// Configuration Methods /////////////////////////////

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Auto-generated method stub
    }

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
	
/////////////////////// Select Methods //////////////////////////////////

    @Override
    public final Patient getPatient(final String omsSer, final String omsNum)
            throws KmiacServerException, PatientNotFoundException {
        final String sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom "
                + "FROM patient WHERE poms_ser = ? AND poms_nom = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, omsSer, omsNum)) {
            if (acrs.getResultSet().next()) {
                return rsmPatient.map(acrs.getResultSet());
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getPoliclinic() throws KmiacServerException,
            PoliclinicNotFoundException {
        final String sqlQuery = "SELECT DISTINCT n_n00.pcod, "
                + "(n_m00.name_s || ', ' || n_n00.name) as name "
                + "FROM n_n00 INNER JOIN n_m00 ON n_m00.pcod = n_n00.clpu "
                + "INNER JOIN e_talon ON n_n00.pcod = e_talon.cpol;";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            List<IntegerClassifier> tmpList = rsmPoliclinic.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new PoliclinicNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getSpec(final int cpol) throws KmiacServerException,
            SpecNotFoundException {
        final String sqlQuery = "SELECT DISTINCT n_s00.pcod, n_s00.name FROM n_s00 "
                + "INNER JOIN e_talon ON n_s00.pcod = e_talon.cdol "
                + "WHERE e_talon.cpol = ? AND e_talon.prv = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpol, 0)) {
            List<StringClassifier> tmpList = rsmSpec.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new SpecNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getVrach(final int cpol, final String cdol)
            throws KmiacServerException, VrachNotFoundException {
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
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new VrachNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Vidp> getVidp() throws KmiacServerException,
            VidpNotFoundException {
        final String sqlQuery = "SELECT pcod, name, vcolor FROM e_vidp";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            List<Vidp> tmpList = rsmVidp.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new VidpNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Talon> getTalon(final int cpol, final String cdol, final int pcod, final int npasp, final boolean secOnly)
            throws KmiacServerException, TalonNotFoundException {
        final int prv = 0;
        final String sqlQuery = String.format("WITH t AS (SELECT datap FROM e_talon WHERE npasp = ?) "
        		+ "SELECT e.id, e.ntalon, e.vidp, e.timep, e.datap, e.npasp, e.dataz, e.prv, e.pcod_sp "
        		+ "FROM e_talon e WHERE e.cpol = ? AND e.cdol = ? AND e.pcod_sp = ? "
        		+ "AND ((e.datap > CURRENT_DATE) OR (e.datap = CURRENT_DATE AND e.timep >= CURRENT_TIME)) "
        		+ "AND e.prv = ? AND e.datap NOT IN (SELECT datap FROM t) AND e.vidp %s 2 "
        		+ "ORDER BY e.datap, e.timep ", (secOnly) ? "=" : "!=");
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, npasp, cpol, cdol, pcod, prv)) {
            List<Talon> tmpList = rsmTalon.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new TalonNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    @Override
    public final List<Talon> getReservedTalons(final int cpol, final String cdol,
            final int doctorId, final int patientId) throws KmiacServerException,
            TalonNotFoundException {
        // java.sql.Date не имеет нулевого конструктора, а preparedQuery() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "SELECT id, ntalon, vidp, timep, datap, npasp, dataz, prv, "
                + "name as spec, (fam || ' ' || im || ' ' || ot) as fio FROM e_talon "
                + "INNER JOIN n_s00 ON n_s00.pcod = e_talon.cdol "
                + "INNER JOIN s_vrach ON s_vrach.pcod = e_talon.pcod_sp "
                + "WHERE cpol = ? AND datap >= ? "
                + "AND npasp = ? AND npasp <> 0 ORDER BY datap, timep;";
        try (AutoCloseableResultSet acrs =
                sse.execPreparedQuery(sqlQuery, cpol, new Date(todayMillisec), patientId)) {
            List<Talon> tmpList = rsmReservedTalon.mapToList(acrs.getResultSet());
            if (tmpList.size() > 0) {
                return tmpList;
            } else {
                throw new TalonNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
    }

    //TODO Добавить проверку на занятость талона прямо перед записью
    //TODO Рефакторить
    @Override
    public final void reserveTalon(final Patient pat, final Talon talon)
            throws KmiacServerException, ReserveTalonOperationFailedException,
            PatientHasSomeReservedTalonsOnThisDay {
        final int prv = 2;
        // java.sql.Date не имеет нулевого конструктора, а preparedUpdate() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "UPDATE e_talon SET npasp = ?, dataz = ?, prv = ?, id_pvizit = ? "
                + "WHERE  id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isPatientAlreadyReserveTalonOnThisDay(pat, talon)) {
                int numUpdated = 0;
                if (pat.getIdPvizit() != 0) {
                    numUpdated = sme.execPreparedUpdate(
                        sqlQuery, false, pat.getId(), new Date(todayMillisec), prv,
                        pat.getIdPvizit(), talon.getId());
                } else {
                    numUpdated = sme.execPreparedUpdate("UPDATE e_talon SET npasp = ?, dataz = ?, "
                        + "prv = ?, id_pvizit = nextval('p_vizit_id_seq') "
                        + "WHERE  id = ?;",
                        false, pat.getId(), new Date(todayMillisec), prv, talon.getId());
                }
                if (numUpdated == 1) {
                    sme.commitTransaction();
                    serverManager.instance.getServerById(18).executeServerMethod(1801, talon.id);
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
        } catch (Exception e) {
        	e.printStackTrace();
            throw new KmiacServerException();
        }
    }

    private boolean isPatientAlreadyReserveTalonOnThisDay(final Patient patient,
            final Talon talon) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT id FROM e_talon WHERE npasp = ? AND pcod_sp = ? AND datap = ?",
                patient.getId(), talon.getPcodSp(), new Date(talon.getDatap()))) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public final void releaseTalon(final Talon talon) throws KmiacServerException,
            ReleaseTalonOperationFailedException, TException {
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
                serverManager.instance.getServerById(18).executeServerMethod(1802, talon.id);
            } else {
                sme.rollbackTransaction();
                throw new ReleaseTalonOperationFailedException();
            }
        } catch (SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SQL Exception: ", e);
            throw new KmiacServerException();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new KmiacServerException();
        }
    }

}

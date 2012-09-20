package ru.nkz.ivcgzo.serverHospital;

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
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital.Iface;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;


public class ServerHospital extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerHospital.class.getName());
    private TServer tServer;
    private TResultSetMapper<TSimplePatient, TSimplePatient._Fields> rsmSimplePatient;
    private TResultSetMapper<TPatient, TPatient._Fields> rsmPatient;
    private TResultSetMapper<TPriemInfo, TPriemInfo._Fields> rsmPriemInfo;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmMedicalHistory;
    private TResultSetMapper<TDiagnosis, TDiagnosis._Fields> rsmDiagnosis;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;

    private static final String[] SIMPLE_PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "fam", "im", "ot", "datar", "dataz", "cotd", "npal", "nist"
    };
    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "datar", "fam", "im", "ot", "pol", "nist", "sgrp", "poms",
        "pdms", "mrab", "npal", "reg_add", "real_add"
    };
    private static final String[] MEDICAL_HISTORY_FIELD_NAMES = {
        "id", "id_gosp", "jalob", "morbi", "status_praesense", "status_localis",
        "fisical_obs", "pcod_vrach", "dataz", "timez"
    };
    private static final String[] PRIEM_INFO_FIELD_NAMES = {
        "pl_extr", "datap", "dataosm", "naprav",
        "n_org", "diag_n", "diag_n_text", "diag_p", "diag_p_text",
        "t0c", "ad", "nal_z", "nal_p", "vid_tran", "alkg", "jalob"
    };
    private static final String[] DIAGNOSIS_FIELD_NAMES = {
        "id", "id_gosp", "diag", "prizn", "ustan", "named", "dataz"
    };
    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };

    private static final Class<?>[] DIAGNOSIS_TYPES = new Class<?>[] {
    //  id             id_gosp        diag          prizn
        Integer.class, Integer.class, String.class, Integer.class,
    //  ustan          named         dataz
        Integer.class, String.class, Date.class
    };
    private static final Class<?>[] MEDICAL_HISTORY_TYPES = {
    //  id             id_gosp       jalob         morbi          st_praesense  status_localis
        Integer.class, Integer.class, String.class, String.class, String.class, String.class,
    //  fisical_obs   pcod_vrach    dataz       timez
        String.class, String.class, Date.class, Time.class
    };

    /**
     * @param seq
     * @param tse
     */
    public ServerHospital(final ISqlSelectExecutor seq, final ITransactedSqlExecutor tse) {
        super(seq, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmSimplePatient  = new TResultSetMapper<>(
                TSimplePatient.class, SIMPLE_PATIENT_FIELD_NAMES);
        rsmPatient = new TResultSetMapper<>(TPatient.class, PATIENT_FIELD_NAMES);
        rsmPriemInfo = new TResultSetMapper<>(TPriemInfo.class, PRIEM_INFO_FIELD_NAMES);
        rsmMedicalHistory = new TResultSetMapper<>(TMedicalHistory.class,
            MEDICAL_HISTORY_FIELD_NAMES);
        rsmDiagnosis = new TResultSetMapper<>(TDiagnosis.class, DIAGNOSIS_FIELD_NAMES);
        rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
    }

    @Override
    public final void start() throws Exception {
        ThriftHospital.Processor<Iface> proc =
                new ThriftHospital.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        System.out.println("hospital server started");

        tServer.serve();
    }

    @Override
    public final void stop() {
        tServer.stop();
        System.out.println("hospital server stopped");
    }

    @Override
    public void testConnection() throws TException {
        // TODO Тест соединения. ХЗ что тут должно быть
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Сохранение конфигурации пользователя. ХЗ что тут должно быть.
    }

    @Override
    public final List<TSimplePatient> getAllPatientForDoctor(final int doctorId, final int otdNum)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, c_otd.id_gosp, patient.fam, patient.im, "
                + "patient.ot, patient.datar, c_otd.dataz, c_otd.cotd, c_otd.npal, c_otd.nist "
                + "FROM c_otd INNER JOIN c_gosp ON c_gosp.id = c_otd.id_gosp "
                + "INNER JOIN patient ON c_gosp.npasp = patient.npasp "
                + "WHERE c_otd.vrach = ? AND c_otd.cotd = ? ORDER BY fam, im, ot;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, doctorId, otdNum)) {
            List<TSimplePatient> patientList = rsmSimplePatient.mapToList(acrs.getResultSet());
            if (patientList.size() > 0) {
                return patientList;
            } else {
                log.log(Level.INFO, String.format(
                    "PatientNotFoundException, otdNum = %s, doctorId = %s;", otdNum, doctorId));
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TSimplePatient> getAllPatientFromOtd(final int otdNum)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, c_otd.id_gosp, patient.fam, patient.im,"
                + "patient.ot, patient.datar, c_otd.dataz, c_otd.cotd "
                + "FROM c_otd INNER JOIN c_gosp ON c_gosp.id = c_otd.id_gosp "
                + "INNER JOIN patient ON c_gosp.npasp = patient.npasp "
                + "WHERE c_otd.cotd = ? AND c_otd.vrach is null ORDER BY fam, im, ot;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, otdNum)) {
            List<TSimplePatient> patientList = rsmSimplePatient.mapToList(acrs.getResultSet());
            if (patientList.size() > 0) {
                return patientList;
            } else {
                log.log(Level.INFO, "PatientNotFoundException, otdNum = " + otdNum);
                throw new PatientNotFoundException();
            }
        } catch (Exception e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final TPatient getPatientPersonalInfo(final int patientId)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, c_otd.id_gosp, patient.datar, patient.fam, "
                + "patient.im, patient.ot, n_z30.name as pol, c_otd.nist, patient.sgrp, "
                + "(patient.poms_ser||patient.poms_nom) as poms, "
                + "(patient.pdms_ser || patient.pdms_nom) as pdms, "
                + "n_z43.name_s as mrab, c_otd.npal, "
                + "(adp_gorod || ', ' || adp_ul || ', ' || adp_dom) as reg_add, "
                + "(adm_gorod || ', ' || adm_UL || ', ' || adm_dom) as real_add "
                + "FROM patient LEFT JOIN c_gosp ON c_gosp.npasp = patient.npasp "
                + "LEFT JOIN  c_otd ON c_gosp.id = c_otd.id_gosp "
                + "LEFT JOIN n_z30 ON n_z30.pcod = patient.pol "
                + "LEFT JOIN n_z43 ON n_z43.pcod = patient.mrab "
                + "WHERE patient.npasp= ?;";
        ResultSet rs = null;

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, patientId)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmPatient.map(rs);
            } else {
                log.log(Level.INFO, "PatientNotFoundException, patientId = " + patientId);
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updatePatientChamberNumber(final int gospId, final int chamberNum)
            throws TException {
        final String sqlQuery = "UPDATE c_otd SET npal = ? WHERE id_gosp = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, chamberNum, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final TPriemInfo getPriemInfo(final int idGosp)
            throws PriemInfoNotFoundException, KmiacServerException {
        final String sqlQuery = "SELECT n_vgo.name as pl_extr, datap, dataosm, "
                + "n_k02.name as naprav, n_n00.name as n_org, diag_n, "
                + "(SELECT name FROM n_c00 WHERE n_c00.pcod = c_gosp.diag_n) "
                + "as diag_n_text, diag_p, "
                + "(SELECT name FROM n_c00 WHERE n_c00.pcod = c_gosp.diag_p) as  diag_p_text, "
                + "t0c, ad, nal_z, nal_p, n_vtr.name as vid_tran, n_alk.name as alkg, jalob "
                + "FROM c_gosp "
                + "LEFT JOIN n_k02 ON n_k02.pcod = c_gosp.naprav "
                + "LEFT JOIN n_n00 ON n_n00.pcod = c_gosp.n_org "
                + "LEFT JOIN n_alk ON n_alk.pcod = c_gosp.alkg "
                + "LEFT JOIN n_vtr ON n_vtr.pcod = c_gosp.vid_tran "
                + "LEFT JOIN n_vgo ON n_vgo.pcod = c_gosp.pl_extr "
                + "WHERE id = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            if (acrs.getResultSet().next()) {
                return rsmPriemInfo.map(acrs.getResultSet());
            } else {
                log.log(Level.INFO, "PriemInfoNotFoundException, idGosp = " + idGosp);
                throw new PriemInfoNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addPatientToDoctor(final int gospId, final int doctorId) throws TException {
        final String sqlQuery = "UPDATE c_otd SET vrach = ? WHERE id_gosp = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, doctorId, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final TDiagnosis getMainDiagnosis(final int gospId)
            throws TException, DiagnosisNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, diag, prizn, ustan, named, dataz "
                + "FROM c_diag WHERE id_gosp = ? AND prizn = 1";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            if (acrs.getResultSet().next()) {
                return rsmDiagnosis.map(acrs.getResultSet());
            } else {
                throw new DiagnosisNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final int addMainDiagnosis(final TDiagnosis inDiagnos)
            throws TException {
        final int[] indexes = {1, 2, 4, 5, 6};
        final String sqlQuery = "INSERT INTO c_diag (id_gosp, diag, 1, ustan, named, dataz) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, inDiagnos,
                    DIAGNOSIS_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateMainDiagnosis(final int id, final TDiagnosis inDiagnos)
            throws TException {
        final String sqlQuery = "UPDATE c_diag SET ustan = ?, named = ?, dataz = ? "
                + "WHERE id = ? AND pizn = 1;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, inDiagnos.getUstan(),
                    inDiagnos.getNamed(), new Date(inDiagnos.getDataz()), id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteMainDiagnosis(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_diag WHERE id = ? AND prizn = 1;", false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<TDiagnosis> getAccompDiagnosis(final int gospId)
            throws TException, DiagnosisNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, diag, prizn, ustan, named, dataz "
                + "FROM c_diag WHERE id_gosp = ? AND prizn = 2";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            List<TDiagnosis> diagList = rsmDiagnosis.mapToList(acrs.getResultSet());
            if (diagList.size() > 0) {
                return diagList;
            } else {
                throw new DiagnosisNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final int addAccompDiagnosis(final TDiagnosis inDiagnos)
            throws TException {
        final int[] indexes = {1, 2, 4, 5, 6};
        final String sqlQuery = "INSERT INTO c_diag (id_gosp, diag, 2, ustan, named, dataz) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, inDiagnos,
                    DIAGNOSIS_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateAccompDiagnosis(final int id, final TDiagnosis inDiagnos)
            throws TException {
        final String sqlQuery = "UPDATE c_diag SET ustan = ?, named = ?, dataz = ? "
                + "WHERE id = ? AND prizn = 2;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, inDiagnos.getUstan(),
                    inDiagnos.getNamed(), new Date(inDiagnos.getDataz()), id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteAccompDiagnosis(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_diag WHERE id = ? AND prizn = 2;", false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<StringClassifier> getAzj() throws TException {
        final String sqlQuery = "SELECT pcod, name FROM n_azj";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmAzj =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAzj.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<IntegerClassifier> getAp0() throws TException {
        final String sqlQuery = "SELECT pcod, name FROM n_ap0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAp0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAp0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<IntegerClassifier> getAj0() throws TException {
        final String sqlQuery = "SELECT pcod, name FROM n_aq0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAq0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAq0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<IntegerClassifier> getShablonNames(final int cspec, final int cslu,
            final String srcText) throws KmiacServerException, TException {
        String sql = "SELECT DISTINCT sho.id AS pcod, sho.name, "
                + "sho.diag || ' ' || sho.name AS name "
                + "FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) "
                + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
                + "JOIN n_c00 c00 ON (c00.pcod = sho.diag) "
                + "WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";

        if (srcText != null) {
            sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?)"
                    + "OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
        }
        sql += "ORDER BY sho.name ";

        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, cspec, cslu, cslu)
                : sse.execPreparedQuery(sql, cspec, cslu, cslu,
                        srcText, srcText, srcText, srcText)) {
            return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            System.err.println(e.getCause());
            throw new KmiacServerException("Error searching template");
        }
    }

    @Override
    public final Shablon getShablon(final int idSh) throws KmiacServerException {
        final String sqlQuery = "SELECT nd.name, sho.next, nsh.pcod,nsh.name, sht.sh_text "
            + "FROM sh_osm sho JOIN n_din nd ON (nd.pcod = sho.cdin) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) "
            + "WHERE sho.id = ? ORDER BY nsh.pcod;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idSh)) {
            if (acrs.getResultSet().next()) {
                Shablon sho = new Shablon(acrs.getResultSet().getString(1),
                        acrs.getResultSet().getString(2), new ArrayList<ShablonText>());
                do {
                    sho.textList.add(new ShablonText(acrs.getResultSet().getInt(3),
                            acrs.getResultSet().getString(4), acrs.getResultSet().getString(5)));
                } while (acrs.getResultSet().next());
                return sho;
            } else {
                throw new SQLException("No templates with this id");
            }
        } catch (SQLException e) {
            System.err.println(e.getCause());
            throw new KmiacServerException("Error loading template by its id");
        }
    }

    @Override
    public final List<TMedicalHistory> getMedicalHistory(final int idGosp)
            throws KmiacServerException, MedicalHistoryNotFoundException {
        final String sqlQuery = "SELECT * FROM c_osmotr WHERE id_gosp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            List<TMedicalHistory> tmpMedHistories =
                rsmMedicalHistory.mapToList(acrs.getResultSet());
            if (tmpMedHistories.size() > 0) {
                return tmpMedHistories;
            } else {
                log.log(Level.INFO,  "MedicalHistoryNotFoundException, idGosp = " + idGosp);
                throw new MedicalHistoryNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addMedicalHistory(final TMedicalHistory medHist)
            throws KmiacServerException, TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        final String sqlQuery = "INSERT INTO c_osmotr (id, id_gosp, jalob, "
            + "morbi, status_praesense, "
            + "status_localis, fisical_obs, pcod_vrach, dataz, timez) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, medHist, MEDICAL_HISTORY_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (InterruptedException | SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteMedicalHistory(final int idGosp) throws KmiacServerException,
            TException {
        final String sqlQuery = "DELETE * FROM c_osmotr WHERE id_gosp = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, idGosp);
            sme.setCommit();
        } catch (SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

}

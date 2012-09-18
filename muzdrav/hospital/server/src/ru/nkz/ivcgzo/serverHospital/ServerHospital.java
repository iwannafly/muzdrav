package ru.nkz.ivcgzo.serverHospital;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;


import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.ComplaintNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DesiaseHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.ObjectiveStateNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.SpecialStateNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital.Iface;
import ru.nkz.ivcgzo.thriftHospital.TComplaint;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;


public class ServerHospital extends Server implements Iface {
    private TServer tServer;
    private TResultSetMapper<TSimplePatient, TSimplePatient._Fields> rsmSimplePatient;
    private TResultSetMapper<TPatient, TPatient._Fields> rsmPatient;
    private TResultSetMapper<TPriemInfo, TPriemInfo._Fields> rsmPriemInfo;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmLifeHistory;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmDesiaseHistory;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmState;
    private TResultSetMapper<TComplaint, TComplaint._Fields> rsmComplaint;
    private TResultSetMapper<TDiagnosis, TDiagnosis._Fields> rsmDiagnosis;

    private static final String[] SIMPLE_PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "fam", "im", "ot", "datar", "dataz", "cotd", "npal", "nist"
    };
    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "datar", "fam", "im", "ot", "pol", "nist", "sgrp", "poms",
        "pdms", "mrab", "npal", "reg_add", "real_add"
    };
    private static final String[] PRIEM_INFO_FIELD_NAMES = {
        "pl_extr", "datap", "dataosm", "naprav",
        "n_org", "diag_n", "diag_n_text", "diag_p", "diag_p_text",
        "t0c", "ad", "nal_z", "nal_p", "vid_tran", "alkg", "jalob"
    };
    private static final String[] LIFE_HISTORY_FIELD_NAMES = {
        "id", "id_gosp", "vitae", "dataz"
    };
    private static final String[] DESIASE_HISTORY_FIELD_NAMES = {
        "id", "id_gosp", "morbi", "dataz"
    };
    private static final String[] STATE_FIELD_NAMES = {
        "id", "id_gosp", "osost", "dataz"
    };
    private static final String[] COMPLAINT_FIELD_NAMES = {
        "id", "id_gosp", "datez", "timez", "jalob"
    };
    private static final String[] DIAGNOSIS_FIELD_NAMES = {
        "id", "id_gosp", "diag", "prizn", "ustan", "named", "dataz"
    };

    private static final Class<?>[] COMPLAINT_TYPES = new Class<?>[] {
    //  id             id_gosp        dataz       timez
        Integer.class, Integer.class, Date.class, Time.class,
    //  jalob
        String.class
    };
    private static final Class<?>[] DIAGNOSIS_TYPES = new Class<?>[] {
    //  id             id_gosp        diag          prizn
        Integer.class, Integer.class, String.class, Integer.class,
    //  ustan          named         dataz
        Integer.class, String.class, Date.class
    };

    /**
     * @param seq
     * @param tse
     */
    public ServerHospital(final ISqlSelectExecutor seq, final ITransactedSqlExecutor tse) {
        super(seq, tse);

        rsmSimplePatient  = new TResultSetMapper<>(
                TSimplePatient.class, SIMPLE_PATIENT_FIELD_NAMES);
        rsmPatient = new TResultSetMapper<>(TPatient.class, PATIENT_FIELD_NAMES);
        rsmPriemInfo = new TResultSetMapper<>(TPriemInfo.class, PRIEM_INFO_FIELD_NAMES);
        rsmLifeHistory = new TResultSetMapper<>(TMedicalHistory.class, LIFE_HISTORY_FIELD_NAMES);
        rsmDesiaseHistory = new TResultSetMapper<>(
                TMedicalHistory.class, DESIASE_HISTORY_FIELD_NAMES);
        rsmState = new TResultSetMapper<>(TMedicalHistory.class, STATE_FIELD_NAMES);
        rsmComplaint = new TResultSetMapper<>(TComplaint.class, COMPLAINT_FIELD_NAMES);
        rsmDiagnosis = new TResultSetMapper<>(TDiagnosis.class, DIAGNOSIS_FIELD_NAMES);
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
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TSimplePatient> getAllPatientFromOtd(final int otdNum)
            throws TException, PatientNotFoundException {
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
                throw new PatientNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
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
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
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
                throw new PriemInfoNotFoundException();
            }
        } catch (SQLException e) {
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
    public final TMedicalHistory getLifeHistory(final int gospId)
            throws TException, LifeHistoryNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, vitae, dataz FROM c_vitae WHERE id_gosp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            if (acrs.getResultSet().next()) {
                return rsmLifeHistory.map(acrs.getResultSet());
            } else {
                throw new LifeHistoryNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final TMedicalHistory getDesiaseHistory(final int gospId)
            throws TException, DesiaseHistoryNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, morbi, dataz FROM c_morbi WHERE id_gosp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            if (acrs.getResultSet().next()) {
                return rsmDesiaseHistory.map(acrs.getResultSet());
            } else {
                throw new DesiaseHistoryNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final TMedicalHistory getObjectiveState(final int gospId)
            throws TException, ObjectiveStateNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, osost, dataz FROM c_praesens "
                + "WHERE id_gosp = ? AND k_osost = 0;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            if (acrs.getResultSet().next()) {
                return rsmState.map(acrs.getResultSet());
            } else {
                throw new ObjectiveStateNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final TMedicalHistory getSpecialState(final int gospId)
            throws TException, SpecialStateNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, osost, dataz FROM c_praesens "
                + "WHERE id_gosp = ? AND k_osost = 1;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            if (acrs.getResultSet().next()) {
                return rsmState.map(acrs.getResultSet());
            } else {
                throw new SpecialStateNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateLifeHistory(final int gospId, final TMedicalHistory lifeHistory)
            throws TException {
        final String sqlQuery = "UPDATE c_vitae SET vitae = ?, dataz = ? "
                + "WHERE id_gosp = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false,
                    lifeHistory.getText(), new Date(lifeHistory.getDataz()), gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateDesiaseHistory(final int gospId, final TMedicalHistory desiaseHistory)
            throws TException {
        final String sqlQuery = "UPDATE c_morbi SET morbi = ?, dataz = ? "
                + "WHERE id_gosp = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false,
                    desiaseHistory.getText(), new Date(desiaseHistory.getDataz()), gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateObjectiveState(final int gospId, final TMedicalHistory objectiveState)
            throws TException {
        final String sqlQuery = "UPDATE c_praesens SET osost = ?, dataz = ? "
                + "WHERE id_gosp = ? AND k_osost = 0";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false,
                    objectiveState.getText(), new Date(objectiveState.getDataz()), gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateSpecialState(final int gospId, final TMedicalHistory specialState)
            throws TException {
        final String sqlQuery = "UPDATE c_praesens SET osost = ?, dataz = ? "
                + "WHERE id_gosp = ? AND k_osost = 1";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false,
                    specialState.getText(), new Date(specialState.getDataz()), gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteLifeHistory(final int gospId) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_vitae WHERE id_gosp = ?;", false, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteDesiaseHistory(final int gospId) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_morbi WHERE id_gosp = ?;", false, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteObjectiveState(final int gospId) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_praesens WHERE id_gosp = ? AND k_osost = 0;",
                    false, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteSpecialState(final int gospId) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_praesens WHERE id_gosp = ? AND k_osost = 1;",
                    false, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final List<TComplaint> getComplaints(final int gospId)
            throws TException, ComplaintNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, dataz, timez, jalob FROM c_jalob WHERE id_gosp = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            List<TComplaint> jalobList = rsmComplaint.mapToList(acrs.getResultSet());
            if (jalobList.size() > 0) {
                return jalobList;
            } else {
                throw new ComplaintNotFoundException();
            }
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public final int addComplaint(final TComplaint complaint) throws TException {
        final int[] indexes = {1, 2, 3, 4};
        final String sqlQuery = "INSERT INTO c_jalob (id_gosp, dataz, timez, jalob) "
                + "VALUES (?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, complaint,
                    COMPLAINT_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateComplaint(final int id, final TComplaint complaint)
            throws TException {
        final String sqlQuery = "UPDATE c_jalob SET dataz = ?, timez = ?, jalob = ? "
                + "WHERE id = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, new Date(complaint.getDate()),
                    new Time(complaint.getTime()), complaint.getText(), id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteComplaint(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_jalob WHERE id = ?;", false, id);
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
    public final List<TDiagnosis> getCopmlication(final int gospId)
            throws TException, DiagnosisNotFoundException {
        String sqlQuery = "SELECT id, id_gosp, diag, prizn, ustan, named, dataz "
                + "FROM c_diag WHERE id_gosp = ? AND prizn = 3";
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
    public final int addCopmlication(final TDiagnosis inDiagnos)
            throws TException {
        final int[] indexes = {1, 2, 4, 5, 6};
        final String sqlQuery = "INSERT INTO c_diag (id_gosp, diag, 3, ustan, named, dataz) "
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
    public final void updateCopmlication(final int id, final TDiagnosis inDiagnos)
            throws TException {
        final String sqlQuery = "UPDATE c_diag SET ustan = ?, named = ?, dataz = ? "
                + "WHERE id = ? AND prizn = 3;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, inDiagnos.getUstan(),
                    inDiagnos.getNamed(), new Date(inDiagnos.getDataz()), id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteCopmlication(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_diag WHERE id = ? AND prizn = 3;", false, id);
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
        // TODO Auto-generated method stub
        return null;
    }

}

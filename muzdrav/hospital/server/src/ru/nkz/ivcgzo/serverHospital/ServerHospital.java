package ru.nkz.ivcgzo.serverHospital;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import ru.nkz.ivcgzo.thriftHospital.ChildDocNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.ChildbirthNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DopShablon;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MesNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PrdDinNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PrdIshodNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PrdSlNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.RdDinStruct;
import ru.nkz.ivcgzo.thriftHospital.RdSlStruct;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TDiagnostic;
import ru.nkz.ivcgzo.thriftHospital.TLifeHistory;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TMedication;
import ru.nkz.ivcgzo.thriftHospital.TPatientCommonInfo;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.TRdIshod;
import ru.nkz.ivcgzo.thriftHospital.TRd_Novor;
import ru.nkz.ivcgzo.thriftHospital.TRd_Svid_Rojd;
import ru.nkz.ivcgzo.thriftHospital.TStage;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital;
import ru.nkz.ivcgzo.thriftHospital.ThriftHospital.Iface;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;
import ru.nkz.ivcgzo.thriftHospital.Zakl;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;

public class ServerHospital extends Server implements Iface {
    protected static Logger log = Logger.getLogger(ServerHospital.class.getName());
    private TServer tServer = null;
    private TResultSetMapper<TSimplePatient, TSimplePatient._Fields> rsmSimplePatient;
    private TResultSetMapper<TPatient, TPatient._Fields> rsmPatient;
    private TResultSetMapper<TPriemInfo, TPriemInfo._Fields> rsmPriemInfo;
    private TResultSetMapper<TLifeHistory, TLifeHistory._Fields> rsmLifeHistory;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmMedicalHistory;
    private TResultSetMapper<TDiagnosis, TDiagnosis._Fields> rsmDiagnosis;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
    private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
    private TResultSetMapper<TStage, TStage._Fields> rsmStage;
    private TResultSetMapper<TRdIshod, TRdIshod._Fields> rsmRdIshod;
    private TResultSetMapper<RdSlStruct, RdSlStruct._Fields> rsmRdSl;
    private TResultSetMapper<RdDinStruct, RdDinStruct._Fields> rsmRdDin;
    private ServerAssignments serverAssignments;
    private ServerChildren serverChildren;

    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] STR_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] SIMPLE_PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "fam", "im", "ot", "datar", "datap", "cotd", "npal", "nist"
    };
    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "datar", "fam", "im", "ot", "pol", "nist", "sgrp", "poms",
        "pdms", "mrab", "npal", "reg_add", "real_add", "ngosp"
    };
    private static final String[] LIFE_HISTORY_FIELD_NAMES = {
        "npasp", "allerg", "farmkol", "vitae"
    };
    private static final String[] MEDICAL_HISTORY_FIELD_NAMES = {
        "id", "id_gosp", "jalob", "morbi", "status_praesense", "status_localis",
        "fisical_obs", "pcod_vrach", "dataz", "timez", "pcod_added", "cpodr"
    };
    private static final String[] PRIEM_INFO_FIELD_NAMES = {
        "pl_extr", "datap", "dataosm", "naprav",
        "n_org", "diag_n", "diag_n_text", "diag_p", "diag_p_text",
        "t0c", "ad", "nal_z", "nal_p", "vid_tran", "alkg", "jalob"
    };
    private static final String[] DIAGNOSIS_FIELD_NAMES = {
        "id", "id_gosp", "cod", "med_op", "date_ustan", "prizn", "vrach" , "diagname"
    };
    private static final String[] STAGE_FIELD_NAMES = {
        "id", "id_gosp", "stl", "mes", "date_start", "date_end",
        "ukl", "ishod", "result", "time_start", "time_end"
    };
    private static final String[] RDISHOD_FIELD_NAMES = {
        "npasp", "ngosp", "id_berem", "id", "serdm", "mesto",
        "deyat", "shvatd", "vodyd", "kashetv", "polnd", "potugid",
        "posled", "vremp", "obol", "lpupov", "obvit", "osobp", "krov", "psih", "obezb",
        "eff", "prr1", "prr2", "prr3", "prinyl", "osmposl", "vrash", "akush", "daterod",
        "vespl", "detmesto", "shvatt", "vodyt", "polnt", "potugit"
    };

    private static final Class<?>[] RD_ISHOD_TYPES = new Class<?>[] {
//      "npasp",      "ngosp",   "id_berem",         "id",          "serdm",       "mesto",
        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class,
//      "deyat",  "shvatd",   "vodyd",   "kashetv",   "polnd","potugid",
        String.class, Date.class, Date.class, String.class, Date.class, Date.class,
//      "posled",     "vremp",        "obol",       "lpupov",      "obvit",      "osobp",
        Integer.class, Integer.class, String.class, Integer.class, String.class, String.class,
//      "krov",        "psih",        "obezb",      "eff",         "prr1",        "prr2",
        Integer.class, Boolean.class, String.class, Integer.class, Integer.class, Integer.class,
//      "prr3",        "prinyl",      "osmposl",     "vrash",       "akush",
        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//      "daterod",        "vespl", "detmesto"
        Date.class, Double.class, String.class,
//      "shvatt",   "vodyt",   "polnt", "potugit"
        Time.class, Time.class, Time.class, Time.class
    };
    private static final String[] RDSL_STRUCT_FIELDS_NAMES = {
        "id", "npasp", "datay", "dataosl", "abort", "shet", "datam", "yavka1", "ishod",
        "datasn", "datazs", "kolrod", "deti", "kont", "vesd", "dsp", "dsr", "dtroch", "cext",
        "indsol", "prmen", "dataz", "datasert", "nsert", "ssert", "oslab", "plrod", "prrod",
        "vozmen", "oslrod", "polj", "dataab", "srokab", "cdiagt", "cvera", "id_pvizit",
        "rost", "eko", "rub", "predp", "osp", "cmer"
    };
    private static final Class<?>[] RDSL_TYPES = new Class<?>[] {
        Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class,
        Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class,
        Integer.class, Boolean.class, Double.class, Integer.class, Integer.class, Integer.class,
        Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class,
        String.class, String.class, Integer.class, String.class, Integer.class, Integer.class,
        Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class,
        Integer.class, Boolean.class, Boolean.class, Boolean.class, Integer.class, Integer.class
    };
    private static final String[] RDDIN_STRUCT_FIELDS_NAMES = {
        "id_rd_sl", "id_pvizit", "npasp", "srok", "grr", "ball", "oj", "hdm", "dspos",
        "art1", "art2", "art3", "art4", "oteki", "spl", "chcc", "polpl", "predpl", "serd",
        "serd1", "id_pos", "ves" , "ngosp", "pozpl", "vidpl"
    };
    private static final Class<?>[] RDDIN_TYPES = new Class<?>[] {
        Integer.class,
//      "id_rd_sl",
        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//      "id_pvizit",   "npasp",       "srok",        "grr",         "ball",
        Integer.class, Integer.class, String.class, Integer.class, Integer.class,
//      "oj",          "hdm",         "dspos",      "art1",        "art2",
        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//      "art3",        "art4",        "oteki",       "spl",         "chcc",
        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//      "polpl",       "predpl",      "serd",        "serd1",       "id_pos",
        Double.class, Integer.class, Integer.class, Integer.class
//      "ves" ,       "ngosp",       "pozpl",       "vidpl"
    };
    private static final Class<?>[] DIAGNOSIS_TYPES = new Class<?>[] {
    //  id             id_gosp         cod           med_op        date_ustan
        Integer.class, Integer.class , String.class, String.class, Date.class,
    //  prizn          vrach          diagName
        Integer.class, Integer.class, String.class
    };
    private static final Class<?>[] MEDICAL_HISTORY_TYPES = {
    //  id             id_gosp       jalob         morbi          st_praesense  status_localis
        Integer.class, Integer.class, String.class, String.class, String.class, String.class,
    //  fisical_obs   pcod_vrach     dataz       timez       pcod_added     cpodr
        String.class, Integer.class, Date.class, Time.class, Integer.class, Integer.class
    };
    private static final Class<?>[] LIFE_HISTORY_TYPES = {
    //  npasp          allerg        farmkol       vitae
        Integer.class, String.class, String.class, String.class
    };
    private static final Class<?>[] STAGE_TYPES = {
    //  id             id_gosp        stl            mes
        Integer.class, Integer.class, Integer.class, String.class,
    //  date_start  date_end    ukl            ishod          result
        Date.class, Date.class, Double.class, Integer.class, Integer.class,
    //  time_start  time_end
        Time.class, Time.class
    };

    /**
     * @param seq
     * @param tse
     */
    public ServerHospital(final ISqlSelectExecutor seq, final ITransactedSqlExecutor tse) {
        super(seq, tse);
        serverAssignments = new ServerAssignments(seq, tse);
        serverChildren = new ServerChildren(seq, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmSimplePatient  = new TResultSetMapper<>(
                TSimplePatient.class, SIMPLE_PATIENT_FIELD_NAMES);
        rsmPatient = new TResultSetMapper<>(TPatient.class, PATIENT_FIELD_NAMES);
        rsmPriemInfo = new TResultSetMapper<>(TPriemInfo.class, PRIEM_INFO_FIELD_NAMES);
        rsmLifeHistory = new TResultSetMapper<>(TLifeHistory.class, LIFE_HISTORY_FIELD_NAMES);
        rsmMedicalHistory = new TResultSetMapper<>(TMedicalHistory.class,
            MEDICAL_HISTORY_FIELD_NAMES);
        rsmDiagnosis = new TResultSetMapper<>(TDiagnosis.class, DIAGNOSIS_FIELD_NAMES);
        rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmStrClas = new TResultSetMapper<>(StringClassifier.class, STR_CLAS_FIELD_NAMES);
        rsmStage = new TResultSetMapper<>(TStage.class, STAGE_FIELD_NAMES);
        rsmRdIshod = new TResultSetMapper<>(TRdIshod.class, RDISHOD_FIELD_NAMES);
        rsmRdDin = new TResultSetMapper<>(RdDinStruct.class, RDDIN_STRUCT_FIELDS_NAMES);
        rsmRdSl = new TResultSetMapper<>(RdSlStruct.class, RDSL_STRUCT_FIELDS_NAMES);
    }

    @Override
    public final void start() throws Exception {
        ThriftHospital.Processor<Iface> proc =
                new ThriftHospital.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.log(Level.INFO, "Hospital server started");
        tServer.serve();
    }

    @Override
    public final void stop() {
        if (this.tServer != null) {
            tServer.stop();
            log.log(Level.INFO, "Hospital server stopped");
        }
    }

    @Override
    public void testConnection() throws TException {
        // TODO Тест соединения. ХЗ что тут должно быть
    }

    @Override
    public final int getId() {
        return configuration.appId;
    }

    @Override
    public final int getPort() {
        return configuration.thrPort;
    }

    @Override
    public final String getName() {
        return configuration.appName;
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Сохранение конфигурации пользователя. ХЗ что тут должно быть.
    }

    @Override
    public final List<TSimplePatient> getAllPatientForDoctor(final int doctorId, final int otdNum)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT p.npasp, c_otd.id_gosp, p.fam, p.im, "
                + "p.ot, p.datar, c_gosp.datap, c_otd.cotd, c_otd.npal, c_otd.nist "
                + "FROM c_otd "
                + "INNER JOIN c_gosp ON (c_gosp.id = c_otd.id_gosp) "
                + "INNER JOIN patient p ON (c_gosp.npasp = p.npasp) "
                + "WHERE (c_otd.vrach = ?) AND (c_otd.cotd = ?) "
                + "ORDER BY p.fam, p.im, p.ot;";
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
                + "patient.ot, patient.datar, c_gosp.datap, c_otd.cotd, c_otd.nist "
                + "FROM c_otd INNER JOIN c_gosp ON c_gosp.id = c_otd.id_gosp "
                + "INNER JOIN patient ON c_gosp.npasp = patient.npasp "
                + "WHERE c_otd.cotd = ? AND c_otd.vrach is null AND c_otd.result is null "
                + "AND (c_otd.ishod <> 2  OR c_otd.ishod is null) "
                + "ORDER BY fam, im, ot;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, otdNum)) {
            List<TSimplePatient> patientList = rsmSimplePatient.mapToList(acrs.getResultSet());
            if (patientList.size() > 0) {
                return patientList;
            } else {
                log.log(Level.INFO, "PatientNotFoundException, otdNum = " + otdNum);
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final TPatient getPatientPersonalInfo(final int idGosp)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT p.npasp, c_otd.id_gosp, p.datar, p.fam, "
                + "p.im, p.ot, n_z30.name as pol, c_otd.nist, n_t00.pcod as sgrp, "
                + "(p.poms_ser || p.poms_nom) as poms, "
                + "(p.pdms_ser || p.pdms_nom) as pdms, "
                + "n_z43.name_s as mrab, c_otd.npal, "
                + "(adp_gorod || ', ' || adp_ul || ', ' || adp_dom) as reg_add, "
                + "(adm_gorod || ', ' || adm_UL || ', ' || adm_dom) as real_add, "
                + "c_gosp.ngosp "
                + "FROM patient p JOIN c_gosp ON (c_gosp.npasp = p.npasp) "
                + "JOIN  c_otd ON (c_gosp.id = c_otd.id_gosp) "
                + "LEFT JOIN n_t00 ON (n_t00.pcod = c_otd.cprof) "
                + "LEFT JOIN n_z30 ON (n_z30.pcod = p.pol) "
                + "LEFT JOIN n_z43 ON (n_z43.pcod = p.mrab) "
                + "WHERE (c_otd.id_gosp = ?);"; // тут был еще npasp, но он лишний
        ResultSet rs = null;

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmPatient.map(rs);
            } else {
                log.log(Level.INFO, "PatientNotFoundException ");
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final TPatient getPatientPersonalInfoByCotd(final int idCotd)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, c_otd.id_gosp, patient.datar, patient.fam, "
                + "patient.im, patient.ot, n_z30.name as pol, c_otd.nist, n_t00.pcod as sgrp, "
                + "(patient.poms_ser||patient.poms_nom) as poms, "
                + "(patient.pdms_ser || patient.pdms_nom) as pdms, "
                + "n_z43.name_s as mrab, c_otd.npal, "
                + "(adp_gorod || ', ' || adp_ul || ', ' || adp_dom) as reg_add, "
                + "(adm_gorod || ', ' || adm_UL || ', ' || adm_dom) as real_add, "
                + "c_gosp.ngosp "
                + "FROM patient JOIN c_gosp ON c_gosp.npasp = patient.npasp "
                + "JOIN  c_otd ON c_gosp.id = c_otd.id_gosp "
                + "LEFT JOIN n_t00 ON n_t00.pcod = c_otd.cprof "
                + "LEFT JOIN n_z30 ON n_z30.pcod = patient.pol "
                + "LEFT JOIN n_z43 ON n_z43.pcod = patient.mrab "
                + "WHERE (c_otd.id = ?);"; // тут был еще npasp, но он лишний
        ResultSet rs = null;

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idCotd)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmPatient.map(rs);
            } else {
                log.log(Level.INFO, "PatientNotFoundException: ");
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updatePatientChamberNumber(final int gospId, final String chamberNum,
            final int profPcod, final int nist) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_otd SET npal = ?, cprof = ?, nist = ?"
                + "WHERE (id_gosp = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, chamberNum, profPcod, nist, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new KmiacServerException();
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
                + "WHERE (id = ?)";
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
    public final void addPatientToDoctor(final int gospId, final int doctorId,
            final int stationType) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_otd SET vrach = ?, cprof = ? WHERE id_gosp = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, doctorId, stationType, gospId);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TDiagnosis> getDiagnosis(final int gospId)
            throws KmiacServerException, DiagnosisNotFoundException {
        String sqlQuery = "SELECT c_diag.id, c_diag.id_gosp, c_diag.cod, c_diag.med_op, "
            + "c_diag.date_ustan, c_diag.prizn, c_diag.vrach, n_c00.name as diagname "
            + "FROM c_diag INNER JOIN n_c00 ON c_diag.cod = n_c00.pcod WHERE id_gosp = ? "
            + "ORDER BY c_diag.date_ustan;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, gospId)) {
            List<TDiagnosis> diagList = rsmDiagnosis.mapToList(acrs.getResultSet());
            if (diagList.size() > 0) {
                return diagList;
            } else {
                log.log(Level.INFO, "DiagnosisNotFoundException, idGosp = " + gospId);
                throw new DiagnosisNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addDiagnosis(final TDiagnosis inDiagnos)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 4, 6};
        final String sqlQuery = "INSERT INTO c_diag (id_gosp, cod, date_ustan, vrach) "
                + "VALUES (?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, inDiagnos,
                    DIAGNOSIS_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateDiagnosis(final TDiagnosis inDiagnos)
            throws KmiacServerException {
        final int[] indexes = {3, 4, 5, 0};
        final String sqlQuery = "UPDATE c_diag SET med_op = ?, date_ustan = ?, prizn = ? "
                + "WHERE id = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, inDiagnos, DIAGNOSIS_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteDiagnosis(final int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM c_diag WHERE id = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getAzj() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_azj";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmAzj =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAzj.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getAp0() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_ap0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAp0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAp0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getAq0() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_aq0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAq0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAq0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getShablonNames(final int cspec, final int cslu,
            final String srcText) throws KmiacServerException {
        String sql = "SELECT DISTINCT sho.id AS pcod, sho.name, "
            + "sho.diag || ' ' || sho.name AS name "
            + "FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_c00 c00 ON (c00.pcod = sho.diag) "
            + "JOIN n_t00 t00 ON t00.spec = shp.cspec  "
            + "JOIN n_n45 n45 ON n45.codprof = t00.pcod "
            + "WHERE (n45.codotd = ?) AND ((sho.cslu = 1) OR (sho.cslu =3)) ";

        if (srcText != null) {
            sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?) "
                    + "OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
        }

        sql += "ORDER BY sho.name ";
        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, cspec)
                : sse.execPreparedQuery(sql, cspec,
                        srcText, srcText, srcText, srcText)) {
            return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Template searching error", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getDopShablonNames(final int nShablon,
            final String srcText) throws KmiacServerException {
        String sql = "SELECT id as pcod, name "
                + "FROM sh_dop WHERE id_n_shablon = ? ";

        if (srcText != null) {
            sql += "AND ((name LIKE ?) OR (text LIKE ?)) ";
        }

        sql += "ORDER BY name ";
        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, nShablon)
                : sse.execPreparedQuery(sql, nShablon, srcText, srcText)) {
            return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            System.err.println(e.getCause());
            throw new KmiacServerException("Error searching template");
        }
    }

    @Override
    public final Shablon getShablon(final int idSh) throws KmiacServerException {
        final String sqlQuery = "SELECT sho.id, nd.name, sho.next, nsh.pcod,nsh.name, sht.sh_text "
            + "FROM sh_osm sho JOIN n_din nd ON (nd.pcod = sho.cdin) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) "
            + "WHERE sho.id = ? ORDER BY nsh.pcod;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idSh)) {
            if (acrs.getResultSet().next()) {
                Shablon sho = new Shablon(acrs.getResultSet().getString(2),
                    acrs.getResultSet().getString(3), new ArrayList<ShablonText>(),
                    acrs.getResultSet().getInt(1));
                do {
                    sho.textList.add(new ShablonText(acrs.getResultSet().getInt(4),
                            acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
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
    public final DopShablon getDopShablon(final int idSh) throws KmiacServerException {
        final String sqlQuery = "SELECT id, id_n_shablon, name, text "
            + "FROM sh_dop WHERE id = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idSh)) {
            if (acrs.getResultSet().next()) {
                DopShablon sho = new DopShablon(acrs.getResultSet().getInt(1),
                        acrs.getResultSet().getInt(2), acrs.getResultSet().getString(3),
                        acrs.getResultSet().getString(4));
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
        final String sqlQuery = "SELECT * FROM c_osmotr WHERE id_gosp = ? ORDER BY dataz, timez;";
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
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        final String sqlQuery = "INSERT INTO c_osmotr (id_gosp, jalob, "
            + "morbi, status_praesense, "
            + "status_localis, fisical_obs, pcod_vrach, dataz, timez, pcod_added, cpodr) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, medHist, MEDICAL_HISTORY_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteMedicalHistory(final int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM c_osmotr WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final TLifeHistory getLifeHistory(final int patientId)
            throws LifeHistoryNotFoundException, KmiacServerException {
        final String sqlQuery = "SELECT npasp, allerg, farmkol, vitae "
                + "FROM p_sign WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, patientId)) {
            if (acrs.getResultSet().next()) {
                return rsmLifeHistory.map(acrs.getResultSet());
            } else {
                throw new LifeHistoryNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateLifeHistory(final TLifeHistory lifeHist)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 0};
        String sqlQuery = null;
        if (isLifeHistoryExist(lifeHist.getId())) {
            sqlQuery = "UPDATE p_sign SET allerg = ?, farmkol = ?, vitae = ? WHERE npasp = ?";
        } else {
            sqlQuery = "INSERT INTO p_sign (allerg, farmkol, vitae, npasp) VALUES (?, ?, ?, ?);";
        }
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, lifeHist, LIFE_HISTORY_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    private boolean isLifeHistoryExist(final int patientId) throws KmiacServerException {
        final String sqlQuery = "SELECT npasp FROM p_sign WHERE npasp = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, patientId)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void disharge(final int idGosp) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_otd SET vrach = ? "
                + "WHERE id_gosp = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, null, idGosp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateMedicalHistory(final TMedicalHistory medHist)
            throws KmiacServerException {
        final int[] indexes = {2, 3, 4, 5, 6, 8, 9, 0};
        final String sqlQuery = "UPDATE c_osmotr SET jalob = ?, "
            + "morbi = ?, status_praesense = ?, "
            + "status_localis = ?, fisical_obs = ?, dataz = ?, timez = ? "
            + "WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, medHist, MEDICAL_HISTORY_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addZakl(final Zakl zakl, final int otd) throws KmiacServerException {
        String sqlQuery = "UPDATE c_otd SET result = ?, ishod = ?, datav = ?, vremv = ?, "
            + "sostv = ?, recom = ?, vrach = ?,  vid_opl = ?, vid_pom = ?, ukl = ? "
            + "WHERE id_gosp = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (zakl.isSetNewOtd() && (zakl.getIshod() == 3)) {
                int newIdGosp = addToGosp(zakl, otd);
                addToOtd(zakl, newIdGosp);
                sme.execPrepared(sqlQuery, false, zakl.getResult(), zakl.getIshod(),
                        new Date(zakl.getDatav()), new Time(zakl.getVremv()),
                        zakl.getSostv(), zakl.getRecom(),
                        null, zakl.getVidOpl(), zakl.getVidPom(), zakl.getUkl(),
                        zakl.getIdGosp());
//                sqlQuery = "UPDATE c_otd SET ishod = ?, "
//                    + "sostv = ?, recom = ?, vrach = ?, vid_opl = ?, vid_pom = ?, ukl = ? "
//                    + "WHERE id_gosp = ?";
//                sme.execPrepared(sqlQuery, false, zakl.getIshod(),
//                    zakl.getSostv(), zakl.getRecom(),
//                    null, zakl.getVidOpl(), zakl.getVidPom(), zakl.getUkl(),
//                    zakl.getIdGosp());
            } else {
                sme.execPrepared(sqlQuery, false, zakl.getResult(), zakl.getIshod(),
                    new Date(zakl.getDatav()), new Time(zakl.getVremv()),
                    zakl.getSostv(), zakl.getRecom(),
                    null, zakl.getVidOpl(), zakl.getVidPom(), zakl.getUkl(),
                    zakl.getIdGosp());
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    private int addToOtd(final Zakl zakl, final int idGosp)
            throws KmiacServerException {
        String sqlQuery = "INSERT INTO c_otd (id_gosp, cotd, dataz) VALUES (?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, true, idGosp, zakl.getNewOtd(),
                    new Date(System.currentTimeMillis()));
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    private int addToGosp(final Zakl zakl, final int otd)
            throws KmiacServerException {
        String sqlQuery = "INSERT INTO c_gosp (npasp, ngosp, naprav, n_org, dataz, datagos, datap) "
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, true, zakl.getNpasp(), zakl.getNgosp(), "С", otd,
                    new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()));
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getStationTypes(final int cotd)
            throws KmiacServerException {
        final String sqlQuery = "SELECT n_n45.codprof as pcod, n_t00.name as name "
                + "FROM n_n45 "
                + "INNER JOIN n_t00 ON n_t00.pcod = n_n45.codprof "
                + "WHERE n_n45.codotd = ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmStaionTypes =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cotd)) {
            return rsmStaionTypes.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<TStage> getStage(final int idGosp) throws KmiacServerException {
        String sqlQuery = "SELECT * FROM c_etap WHERE id_gosp = ? ORDER BY date_start;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            List<TStage> stageList = rsmStage.mapToList(acrs.getResultSet());
            if (stageList.size() > 0) {
                return stageList;
            } else {
                log.log(Level.INFO, "StagesNotFoundException, idGosp = " + idGosp);
                return Collections.<TStage>emptyList();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addStage(final TStage stage) throws KmiacServerException,
            MesNotFoundException {
        final int[] indexes = {1, 4, 2, 3, 9, 6, 7, 8, 10, 5};
        final String sqlQuery = "INSERT INTO c_etap (id_gosp, date_start, stl, mes, "
            + "time_start, ukl, ishod, result, time_end, date_end) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isCodMesValid(stage.getStage(), stage.getMes())) {
                sme.execPreparedT(sqlQuery, true, stage,
                        STAGE_TYPES, indexes);
                int id = sme.getGeneratedKeys().getInt("id");
                sme.setCommit();
                return id;
            } else {
                throw new MesNotFoundException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateStage(final TStage stage)
            throws KmiacServerException, MesNotFoundException {
        final int[] indexes = {4, 5, 2, 3, 6, 7, 8, 9, 10, 0};
        String sqlQuery = "UPDATE c_etap SET date_start = ?, date_end = ?, stl = ?, mes = ?, "
            + "ukl = ?, ishod = ?, result = ?, time_start = ?, time_end = ? "
            + "WHERE id = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isCodMesValid(stage.getStage(), stage.getMes())) {
                sme.execPreparedT(sqlQuery, false, stage, STAGE_TYPES, indexes);
                sme.setCommit();
            } else {
                throw new MesNotFoundException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteStage(final int idStage) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM c_etap WHERE id = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, idStage);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getStagesClassifier(final int idGosp)
            throws KmiacServerException {
        final String sqlQuery = "SELECT n_etp.pcod, n_etp.name FROM n_etp "
            + "INNER JOIN c_otd ON c_otd.stat_type = n_etp.tip "
            + "WHERE id_gosp = ? ";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmStagesClassifier =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            return rsmStagesClassifier.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    private boolean isCodMesValid(final int stage, final String codMes) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM n_messtet WHERE pcod = ?;",
                codMes)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
    }

    @Override
    public final String printHospitalDiary(
            final int idGosp, final long dateStart, final long dateEnd)
            throws KmiacServerException {
        final String path;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            HtmTemplate htmTemplate = new HtmTemplate(
                new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                + "\\plugin\\reports\\HospitalDiary.htm");
            String recurentBody = htmTemplate.getBody();
            List<TMedicalHistory> tmpDiaries = getMedicalHistoryBetweenDates(
                idGosp, new Date(dateStart), new Date(dateEnd));
            int prevDoctor = -1;
            String curVrachFio = "";
            for (TMedicalHistory curDayNotes:tmpDiaries) {
                if (prevDoctor != curDayNotes.getPcodVrach()) {
                    curVrachFio = getDoctorFio(curDayNotes.getPcodVrach());
                    prevDoctor = curDayNotes.getPcodVrach();
                }
                htmTemplate.replaceLabels(
                    true,
                    dateFormat.format(curDayNotes.getDataz()),
                    timeFormat.format(curDayNotes.getTimez()),
                    curDayNotes.isSetJalob() ? curDayNotes.getJalob() : " ",
                    curDayNotes.isSetMorbi() ? curDayNotes.getMorbi() : " ",
                    curDayNotes.isSetStatusPraesense() ? curDayNotes.getStatusPraesense() : " ",
                    curDayNotes.isSetFisicalObs() ? curDayNotes.getFisicalObs() : " ",
                    curDayNotes.isSetStatusLocalis() ? curDayNotes.getStatusLocalis() : " ",
                    curVrachFio
                );
                htmTemplate.insertInEndOfBodySection(recurentBody);
                htmTemplate.refindLabels();
            }
            htmTemplate.replaceText(recurentBody, "");
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            throw new  KmiacServerException(); // тут должен быть кмиац сервер иксепшн
        }
    }

    private List<TMedicalHistory> getMedicalHistoryBetweenDates(
            final int idGosp, final Date dateStart, final Date dateEnd)
            throws KmiacServerException {
        final String sqlQuery = "SELECT * FROM c_osmotr "
            + "WHERE id_gosp = ? AND dataz >= ? AND dataz <= ?"
            + "ORDER BY dataz, timez;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, idGosp, dateStart, dateEnd)) {
            List<TMedicalHistory> tmpMedHistories =
                rsmMedicalHistory.mapToList(acrs.getResultSet());
            return tmpMedHistories;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    private String getDoctorFio(final int doctorPcod)
            throws KmiacServerException {
        final String sqlQuery = "SELECT fam, im, ot FROM s_vrach "
            + "WHERE pcod = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                sqlQuery, doctorPcod)) {
            if (acrs.getResultSet().next()) {
                return String.format("%s %s %s",
                    acrs.getResultSet().getString("fam"),
                    acrs.getResultSet().getString("im"),
                    acrs.getResultSet().getString("ot"));
            } else {
                return "";
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getOtd(final int lpu) throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_o00 "
                + "WHERE clpu = ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmStaionTypes =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpu)) {
            return rsmStaionTypes.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getShablonDiagnosis(final int cspec, final int cslu,
            final String srcText) throws KmiacServerException {
        String sql = "SELECT DISTINCT sho.diag AS pcod, c00.name "
            + "FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_c00 c00 ON (c00.pcod = sho.diag) "
            + "WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";

        if (srcText != null) {
            sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
        }
        sql += "ORDER BY sho.diag ";

        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, 38, 2, 2)
                : sse.execPreparedQuery(sql, 38, 2, 2,
                    srcText, srcText, srcText)) {
            return rsmStrClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException("Error searching template");
        }
    }

    @Override
    public final List<IntegerClassifier> getShablonBySelectedDiagnosis(final int cspec,
            final int cslu, final String diag, final String srcText) throws KmiacServerException {
        String sql = "SELECT DISTINCT sho.id AS pcod, sho.name "
            + "FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) "
            + "JOIN n_c00 c00 ON (c00.pcod = sho.diag) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) AND (sho.diag = ?) ";

        if (srcText != null) {
            sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
        }
        sql += "ORDER BY sho.name ";

        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, 38, 2, 2, diag)
                : sse.execPreparedQuery(sql, 38, 2, 2, diag,
                    srcText, srcText, srcText)) {
            return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException("Error searching template");
        }
    }

    //TODO отрефакторить это уродство
    @Override
    public final String printHospitalSummary(final int idGosp, final String lpuInfo,
            final TPatient patient) throws KmiacServerException {
        final String path;
        AutoCloseableResultSet acrs;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            HtmTemplate htmTemplate = new HtmTemplate(
                new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                + "\\plugin\\reports\\HospitalSummary.htm");
            htmTemplate.replaceLabel("~lpu", lpuInfo);
            htmTemplate.replaceLabel("~surname", patient.getSurname());
            htmTemplate.replaceLabel("~name", patient.getName());
            htmTemplate.replaceLabel("~middlename", patient.getMiddlename());
            htmTemplate.replaceLabel("~age", dateFormat.format(patient.getBirthDate()));
            htmTemplate.replaceLabel("~gender", patient.getGender());
            htmTemplate.replaceLabel("~address", patient.getRegistrationAddress());
            htmTemplate.replaceLabel("~job", patient.getJob());
            htmTemplate.replaceLabel("~surname", patient.getSurname());
            htmTemplate.replaceLabel("~name", patient.getName());
            htmTemplate.replaceLabel("~middlename", patient.getMiddlename());

            acrs = sse.execPreparedQuery(
                "SELECT DISTINCT ON (c_gosp.id) c_gosp.nist, c_gosp.datap, "
                + "c_gosp.jalob, c_otd.datav, "
                + "c_otd.ishod, c_otd.result, c_otd.sostv, c_otd.recom, c_osmotr.morbi "
                + "FROM c_gosp INNER JOIN c_otd ON c_gosp.id = c_otd.id_gosp "
                + "LEFT JOIN c_osmotr ON c_osmotr.id_gosp = c_gosp.id "
                + "WHERE c_gosp.id = ?", idGosp);
            if (!acrs.getResultSet().next()) {
                throw new KmiacServerException("Logged user info not found.");
            } else {
                htmTemplate.replaceLabel("~nist",
                    String.valueOf(acrs.getResultSet().getInt("nist")));
                htmTemplate.replaceLabel(
                    "~dateStart", dateFormat.format(acrs.getResultSet().getDate("datap")));
                if (acrs.getResultSet().getDate("datav") != null) {
                    htmTemplate.replaceLabel(
                        "~dateEnd", dateFormat.format(acrs.getResultSet().getDate("datav")));
                } else {
                    htmTemplate.replaceLabel("~dateEnd", "");
                }
                htmTemplate.replaceLabel(
                    "~dateStart", dateFormat.format(acrs.getResultSet().getDate("datap")));
//                if (acrs.getResultSet().getString("jalob") != null) {
                htmTemplate.replaceLabel(
                    "~jalob", acrs.getResultSet().getString("jalob"));
                htmTemplate.replaceLabel(
                    "~result", acrs.getResultSet().getString("result"));
                htmTemplate.replaceLabel(
                    "~ishod", acrs.getResultSet().getString("ishod"));
                htmTemplate.replaceLabel(
                    "~out_cond", acrs.getResultSet().getString("sostv"));
                htmTemplate.replaceLabel(
                    "~recommendation", acrs.getResultSet().getString("recom"));
                htmTemplate.replaceLabel(
                    "~desiaseHistory", acrs.getResultSet().getString("morbi"));
//                }
            }

            acrs = sse.execPreparedQuery("SELECT n_c00.name, c_diag.prizn FROM c_diag "
                + "INNER JOIN n_c00 ON c_diag.cod = n_c00.pcod WHERE c_diag.id_gosp = ?", idGosp);
            while (acrs.getResultSet().next()) {
                switch (acrs.getResultSet().getInt("prizn")) {
                    case 1:
                        htmTemplate.replaceLabel("~mainDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~mainDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    case 2:
                        htmTemplate.replaceLabel("~oslDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~oslDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    case 3:
                        htmTemplate.replaceLabel("~sopDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~sopDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    default:
                        break;
                }
            }
            htmTemplate.replaceLabel("~mainDiagnosis", "");
            htmTemplate.replaceLabel("~oslDiagnosis", "");
            htmTemplate.replaceLabel("~sopDiagnosis", "");

            acrs = sse.execPreparedQuery("SELECT p_isl_ld.nisl, n_ldi.pcod, "
                + "n_ldi.name_n, p_rez_l.zpok, "
                + "p_isl_ld.datav, '' as op_name, '' as rez_name "
                + "FROM p_isl_ld JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) "
                + "JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) "
                + "WHERE p_isl_ld.id_gosp = ? AND p_rez_l.zpok is not null "
                + "UNION "
                + "SELECT p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, "
                + "p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name "
                + "FROM p_isl_ld JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) "
                + "JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) "
                + "LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) "
                + "WHERE p_isl_ld.id_gosp = ? AND n_arez.name is not null", idGosp, idGosp);
            while (acrs.getResultSet().next()) {
                String tmpIsl =
                    "<li> <b>показатель:</b> " + acrs.getResultSet().getString("name_n")
                    + " <br/><b> &nbsp &nbsp значение:</b>"
                    + acrs.getResultSet().getString("zpok");
                if ((acrs.getResultSet().getString("op_name") != null)
                        && (!acrs.getResultSet().getString("op_name").equals(""))) {
                    tmpIsl += " <br/><b> &nbsp &nbsp описание:</b>"
                        + acrs.getResultSet().getString("op_name");
                }
                if ((acrs.getResultSet().getString("rez_name") != null)
                        && (!acrs.getResultSet().getString("rez_name").equals(""))) {
                    tmpIsl += " <br/><b> &nbsp &nbsp заключение:</b> "
                        + acrs.getResultSet().getString("rez_name");
                }
                if (acrs.getResultSet().getDate("datav") != null) {
                    tmpIsl += "<br/><b> &nbsp &nbsp дата: </b>" + dateFormat.format(
                            acrs.getResultSet().getDate("datav")) + "</li><br/> ~issled ";
                } else {
                    tmpIsl += "</li><br/> ~issled ";
                }

                htmTemplate.replaceLabel("~issled", tmpIsl);
            }
            htmTemplate.replaceLabel("~issled", "");

            acrs = sse.execPreparedQuery("SELECT n_med.name FROM c_lek "
                + "INNER JOIN n_med ON c_lek.klek = n_med.pcod WHERE c_lek.id_gosp = ?", idGosp);
            String medications = "";
            while (acrs.getResultSet().next()) {
                medications += acrs.getResultSet().getString("name") + ", ";
            }
            if (medications.length() > 0) {
                medications = medications.substring(0, medications.length() - ", ".length());
            }
            htmTemplate.replaceLabel("~medications", medications);
            acrs.close();
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            throw new  KmiacServerException(); // тут должен быть кмиац сервер иксепшн
        }
    }

    @Override
    public final TRdIshod getRdIshodInfo(final int npasp, final int ngosp)
            throws PrdIshodNotFoundException, KmiacServerException {
        System.out.println("случай родов выбор");
        System.out.println(npasp);
        System.out.println(ngosp);

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_ishod WHERE (npasp = ?) AND (ngosp = ?);", npasp, ngosp)) {
            if (acrs.getResultSet().next()) {
                return rsmRdIshod.map(acrs.getResultSet());
            } else {
                throw new PrdIshodNotFoundException();
            }
        } catch (SQLException e) {
            ((SQLException) e.getCause()).printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addRdIshod(final TRdIshod rdIs) throws KmiacServerException {
        System.out.println("Добавление случая родов");
        System.out.println(rdIs);
        AutoCloseableResultSet acrs = null;
        AutoCloseableResultSet acrs1 = null;
        Integer id1 = 0;
        Integer numr = 0;
        Integer numdin = 0;
        rdIs.setId_berem(0);
        Date datarod = Date(System.currentTimeMillis());
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            acrs = sse.execPreparedQuery("select max(id) from p_rd_sl where npasp= ? ", 1);
            if (acrs.getResultSet().next()) {
                id1 = acrs.getResultSet().getInt(1);
                System.out.println(id1);
                acrs1 = sse.execPreparedQuery(
                    "select (current_date-datay)/7+yavka1,id_pvizit from p_rd_sl where id= ? ",
                    id1
                );
                if (acrs1.getResultSet().next()) {
                    rdIs.setId_berem(acrs1.getResultSet().getInt(2));
                }
                acrs1.close();
                acrs1 = sse.execPreparedQuery(
                    "select max(id_pos) from p_rd_din where id_pvizit = ? ", numr);
                if (acrs1.getResultSet().next()) {
                    numdin = acrs1.getResultSet().getInt(1);
                }
            }
            sme.execPreparedT(
                "insert into c_rd_ishod (npasp, ngosp, daterod, id_berem, "
                + "shvatd, potugid, polnd, vodyd) "
                + "VALUES (?,?,?,?,?,?,?,?) ", true, rdIs,
                RD_ISHOD_TYPES, 0, 1, 29, 2, 7, 11, 10, 8);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            System.out.println("Добавление случая родов готово");
            return id;
        } catch (SQLException e) {
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            throw new KmiacServerException();
        }
    }

    private Date Date(final long currentTimeMillis) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final void deleteRdIshod(final int npasp, final int ngosp)
            throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_rd_ishod WHERE npasp = ? and ngosp = ? ",
                false, npasp, ngosp);
            sme.setCommit();
        } catch (SQLException e) {
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateRdIshod(final TRdIshod rdIs) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE c_rd_ishod SET mesto = ?, deyat = ?, shvatd = ?, vodyd = ?, "
                + "kashetv = ?, polnd = ?, potugid = ?, posled = ?, vremp = ?, obol = ?, "
                + "lpupov = ?, obvit = ?, osobp = ?, krov = ?, psih = ?, obezb = ?, eff = ?, "
                + "prr1 = ?, prr2 = ?, prr3 = ?, prinyl = ?, osmposl = ?, vrash = ?, "
                + "akush = ?, daterod = ?, vespl =?, detmesto = ?, shvatt = ?, vodyt = ?, "
                + "polnt = ?, potugit = ? "
                + "WHERE npasp = ? and ngosp = ? and id = ? ",
                false, rdIs, RD_ISHOD_TYPES, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 1, 3);
            sme.setCommit();
    	} catch (SQLException e) {
    	    ((SQLException) e.getCause()).printStackTrace();
    	    throw new KmiacServerException();
    	} catch (InterruptedException e1) {
    	    e1.printStackTrace();
    	    throw new KmiacServerException();
    	}
    }

    @Override
    public final String printHospitalDeathSummary(final int idGosp, final String lpuInfo,
            final TPatient patient) throws KmiacServerException {
        final String path;
        AutoCloseableResultSet acrs;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            HtmTemplate htmTemplate = new HtmTemplate(
                new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                + "\\plugin\\reports\\HospitalDeathSummary.htm");
            htmTemplate.replaceLabel("~lpu", lpuInfo);
            htmTemplate.replaceLabel("~surname", patient.getSurname());
            htmTemplate.replaceLabel("~name", patient.getName());
            htmTemplate.replaceLabel("~middlename", patient.getMiddlename());
            htmTemplate.replaceLabel("~age", dateFormat.format(patient.getBirthDate()));
            htmTemplate.replaceLabel("~gender", patient.getGender());
            htmTemplate.replaceLabel("~address", patient.getRegistrationAddress());
            htmTemplate.replaceLabel("~job", patient.getJob());
            htmTemplate.replaceLabel("~surname", patient.getSurname());
            htmTemplate.replaceLabel("~name", patient.getName());
            htmTemplate.replaceLabel("~middlename", patient.getMiddlename());

            acrs = sse.execPreparedQuery(
                "SELECT DISTINCT ON (c_gosp.id) c_gosp.nist, c_gosp.datap, "
                + "c_gosp.jalob, c_otd.datav, "
                + "c_otd.ishod, c_otd.result, c_otd.sostv, c_otd.recom, c_osmotr.morbi "
                + "FROM c_gosp INNER JOIN c_otd ON c_gosp.id = c_otd.id_gosp "
                + "LEFT JOIN c_osmotr ON c_osmotr.id_gosp = c_gosp.id "
                + "WHERE c_gosp.id = ?", idGosp);
            if (!acrs.getResultSet().next()) {
                throw new KmiacServerException("Logged user info not found.");
            } else {
                htmTemplate.replaceLabel("~nist",
                    String.valueOf(acrs.getResultSet().getInt("nist")));
                htmTemplate.replaceLabel(
                    "~dateStart", dateFormat.format(acrs.getResultSet().getDate("datap")));
                if (acrs.getResultSet().getDate("datav") != null) {
                    htmTemplate.replaceLabel(
                        "~dateEnd", dateFormat.format(acrs.getResultSet().getDate("datav")));
                } else {
                    htmTemplate.replaceLabel("~dateEnd", "");
                }
                if (acrs.getResultSet().getDate("datav") != null) {
                    htmTemplate.replaceLabel(
                        "~dateEnd", dateFormat.format(acrs.getResultSet().getDate("datav")));
                } else {
                    htmTemplate.replaceLabel("~dateEnd", "");
                }
                htmTemplate.replaceLabel(
                    "~dateStart", dateFormat.format(acrs.getResultSet().getDate("datap")));
                htmTemplate.replaceLabel(
                    "~jalob", acrs.getResultSet().getString("jalob"));
                htmTemplate.replaceLabel(
                    "~desiaseHistory", acrs.getResultSet().getString("morbi"));
            }

            acrs = sse.execPreparedQuery("SELECT n_c00.name, c_diag.prizn FROM c_diag "
                + "INNER JOIN n_c00 ON c_diag.cod = n_c00.pcod WHERE c_diag.id_gosp = ?", idGosp);
            while (acrs.getResultSet().next()) {
                switch (acrs.getResultSet().getInt("prizn")) {
                    case 1:
                        htmTemplate.replaceLabel("~mainDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~mainDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    case 2:
                        htmTemplate.replaceLabel("~oslDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~oslDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    case 3:
                        htmTemplate.replaceLabel("~sopDiagnosis",
                            acrs.getResultSet().getString("name") + "<br/> ~sopDiagnosis ");
                        htmTemplate.refindLabels();
                        break;
                    default:
                        break;
                }
            }
            htmTemplate.replaceLabel("~mainDiagnosis", "");
            htmTemplate.replaceLabel("~oslDiagnosis", "");
            htmTemplate.replaceLabel("~sopDiagnosis", "");

            acrs = sse.execPreparedQuery("SELECT n_c00.name FROM c_diag "
                    + "INNER JOIN n_c00 ON c_diag.cod = n_c00.pcod WHERE c_diag.id_gosp = ? "
                    + "AND c_diag.prizn = ?", idGosp, 5);
            if (acrs.getResultSet().next()) {
                htmTemplate.replaceLabel("~patDiagnosis", acrs.getResultSet().getString("name"));
            } else {
                htmTemplate.replaceLabel("~patDiagnosis", "");
            }

            acrs = sse.execPreparedQuery("SELECT p_isl_ld.nisl, n_ldi.pcod, "
                + "n_ldi.name_n, p_rez_l.zpok, "
                + "p_isl_ld.datav, '' as op_name, '' as rez_name "
                + "FROM p_isl_ld JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) "
                + "JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) "
                + "WHERE p_isl_ld.id_gosp = ? AND p_rez_l.zpok is not null "
                + "UNION "
                + "SELECT p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, "
                + "p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name "
                + "FROM p_isl_ld JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) "
                + "JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) "
                + "LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) "
                + "WHERE p_isl_ld.id_gosp = ? AND n_arez.name is not null", idGosp, idGosp);
            while (acrs.getResultSet().next()) {
                String tmpIsl =
                    "<li> <b>показатель:</b> " + acrs.getResultSet().getString("name_n")
                    + " <br/><b> &nbsp &nbsp значение:</b>"
                    + acrs.getResultSet().getString("zpok");
                if ((acrs.getResultSet().getString("op_name") != null)
                        && (!acrs.getResultSet().getString("op_name").equals(""))) {
                    tmpIsl += " <br/><b> &nbsp &nbsp описание:</b>"
                        + acrs.getResultSet().getString("op_name");
                }
                if ((acrs.getResultSet().getString("rez_name") != null)
                        && (!acrs.getResultSet().getString("rez_name").equals(""))) {
                    tmpIsl += " <br/><b> &nbsp &nbsp заключение:</b> "
                        + acrs.getResultSet().getString("rez_name");
                }
                if (acrs.getResultSet().getDate("datav") != null) {
                    tmpIsl += "<br/><b> &nbsp &nbsp дата: </b>" + dateFormat.format(
                            acrs.getResultSet().getDate("datav")) + "</li><br/> ~issled ";
                } else {
                    tmpIsl += "</li><br/> ~issled ";
                }

                htmTemplate.replaceLabel("~issled", tmpIsl);
            }
            htmTemplate.replaceLabel("~issled", "");

            acrs = sse.execPreparedQuery("SELECT n_med.name FROM c_lek "
                + "INNER JOIN n_med ON c_lek.klek = n_med.pcod WHERE c_lek.id_gosp = ?", idGosp);
            String medications = "";
            while (acrs.getResultSet().next()) {
                medications += acrs.getResultSet().getString("name") + ", ";
            }
            if (medications.length() > 0) {
                medications = medications.substring(0, medications.length() - ", ".length());
            }
            htmTemplate.replaceLabel("~medications", medications);
            acrs.close();
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            log.log(Level.ERROR, "SQL Exception:", e);
            throw new KmiacServerException(); // тут должен быть кмиац сервер иксепшн
        }
    }

	@Override
    public final List<IntegerClassifier> get_s_vrach(final int clpu)
			throws KmiacServerException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
	    		"SELECT s_vrach.pcod, (fam || ' ' || im || ' ' || ot) AS name " +
	    		"FROM s_vrach " +
	    		"JOIN s_mrab ON (s_mrab.pcod = s_vrach.pcod) " +
	    		"WHERE (s_mrab.clpu = ?);", clpu)) {
	        return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQL Exception (get_s_vrach):", e);
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
    }
	    
		@Override
        public final RdSlStruct getRdSlInfo(final int npasp) 
				throws PrdSlNotFoundException, KmiacServerException {
        AutoCloseableResultSet acrs1;
        Date daterod =  new Date(System.currentTimeMillis()-24192000000L);
        Date daterod1 =  new Date(System.currentTimeMillis());
		Integer ish = 1;
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where npasp = ? and datay>= ? ", npasp,daterod)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSl.map(acrs.getResultSet());
            } else {
						try (SqlModifyExecutor sme = tse.startTransaction()) {
							sme.execPrepared("insert into p_rd_sl " +
								"(npasp,yavka1,datay,datasn) VALUES (?,?,?,?) ",true, npasp,40,daterod1,daterod1);
							sme.setCommit();
					        System.out.println("случай беременности добавлен");
						} catch (InterruptedException e) {
							throw new KmiacServerException();
						}
		 //          	
				  		try (AutoCloseableResultSet acrs2 = sse.execPreparedQuery("select * from p_rd_sl where npasp = ? ", npasp)) {
							if (acrs2.getResultSet().next()) {
				                return rsmRdSl.map(acrs2.getResultSet());
				            } else {
				                throw new PrdSlNotFoundException();
				            }

						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							log.log(Level.ERROR, "SqlException", e);
							throw new KmiacServerException();
						}
		            }

		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			log.log(Level.ERROR, "SqlException", e);
			throw new KmiacServerException();
		}
	}

	@Override
    public final RdDinStruct getRdDinInfo(final int npasp,final int ngosp)
			throws PrdDinNotFoundException, KmiacServerException {
	    Integer srok = 0;
	    Integer oj = 0;
	    Integer hdm = 0;
	    Integer spl = 0;Integer chcc = 0;Integer polpl =0 ;Integer predpl =0;
	    Integer serd =0 ;Integer serd1 =0 ; Integer idpos = 0;
	    Double ves = 0.0; Integer pozpl = 0; Integer vidpl = 0;
        System.out.println("динамика");
        System.out.println(npasp);
        System.out.println(ngosp);
  		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_din where npasp = ? and ngosp= ? ", npasp,ngosp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdDin.map(acrs.getResultSet());
            } else {
				AutoCloseableResultSet acrs1 = sse.execPreparedQuery("select srok,oj, "+
		        "hdm,spl,chcc,polpl,predpl,serd,serd1,ves,id_pos,pozpl,vidpl "+	
			    " from p_rd_din where npasp = ? order by id_pos", npasp);
				if (acrs1.getResultSet().next()) {
//присваиваем значения из динамики, в итоге из-за сортировки имеем последние 
// значения, если в поликлинике не было записей - значения будут нулевыми					
    				srok = acrs1.getResultSet().getInt(1);
    				oj = acrs1.getResultSet().getInt(2);
    				ves = acrs1.getResultSet().getDouble(10);
    				hdm = acrs1.getResultSet().getInt(3);
    				spl = acrs1.getResultSet().getInt(4);
    				chcc = acrs1.getResultSet().getInt(5);
    				polpl = acrs1.getResultSet().getInt(6);
    				predpl = acrs1.getResultSet().getInt(7);
    				serd = acrs1.getResultSet().getInt(8);
    				serd1 = acrs1.getResultSet().getInt(9);
    				idpos = acrs1.getResultSet().getInt(11);
    				pozpl = acrs1.getResultSet().getInt(12);
    				vidpl = acrs1.getResultSet().getInt(13);
				}
				idpos = idpos+1;
				try (SqlModifyExecutor sme = tse.startTransaction()) {
					sme.execPrepared("insert into p_rd_din " +
						"(npasp,ngosp,srok,oj,hdm,spl,chcc,polpl,predpl,serd,serd1,ves,id_pos,pozpl,vidpl) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ",true, npasp,ngosp,srok,oj,hdm,spl,chcc,polpl,predpl,serd,serd1,ves,idpos,pozpl,vidpl);
//					int id = sme.getGeneratedKeys().getInt("id");
					sme.setCommit();
			        System.out.println("динамика добавлена");
				} catch (InterruptedException e) {
					throw new KmiacServerException();
				}
 //          	
		  		try (AutoCloseableResultSet acrs2 = sse.execPreparedQuery("select * from p_rd_din where npasp = ? and ngosp= ? ", npasp,ngosp)) {
					if (acrs2.getResultSet().next()) {
		                return rsmRdDin.map(acrs2.getResultSet());
		            } else {
		                throw new PrdDinNotFoundException();
		            }

				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					log.log(Level.ERROR, "SqlException", e);
					throw new KmiacServerException();
				}
            }
//
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.ERROR, "SqlException", e);
			throw new KmiacServerException();
		}
	}

	@Override
    public final void AddRdSl(final RdSlStruct rdSl) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
    public final void DeleteRdDin(final int ngosp) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPrepared("DELETE FROM p_rd_din WHERE ngosp = ? ", false, ngosp);
		sme.setCommit();
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	} catch (InterruptedException e1) {
		e1.printStackTrace();
		throw new KmiacServerException();
	}
	}
	
	@Override
    public final void UpdateRdDin(final RdDinStruct Din) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_din SET  srok = ?, oj = ?, hdm = ?, chcc = ?, polpl = ?, "+
		"predpl = ?, serd = ?, serd1 = ?, ves = ?, pozpl = ?, "+
		"vidpl = ?  WHERE ngosp = ? and npasp = ? ", false, Din, RDDIN_TYPES,3,6,7,15,16,17,18,19,21,23,24, 22,2);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
    public final void UpdateRdSl(final RdSlStruct Dispb) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPreparedT("UPDATE p_rd_sl SET  dataosl = ?, shet = ?, datam = ?, ishod = ?, kolrod = ?, " +
		"dsp = ?,dsr = ?,dtroch = ?, cext = ?, cdiagt = ?, cvera = ?  WHERE npasp = ? and datasn = ?", 
		false, Dispb, RDSL_TYPES, 3,5,6,8,11,15,16,17,18,33,34,1,9);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
    public final List<IntegerClassifier> getChildBirths(final long BirthDate)
			throws KmiacServerException {
		return serverChildren.getChildBirths(BirthDate);
	}

	@Override
    public final void addChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
		serverChildren.addChildInfo(Child);
	}

	@Override
    public final TRd_Novor getChildInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
		return serverChildren.getChildInfo(npasp);
	}

	@Override
    public final void updateChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
		serverChildren.updateChildInfo(Child);
	}

	@Override
    public final int addChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, PatientNotFoundException {
		return serverChildren.addChildDocument(ChildDocument);
	}

	@Override
    public final TRd_Svid_Rojd getChildDocument(final int npasp)
			throws KmiacServerException, ChildDocNotFoundException {
		return serverChildren.getChildDocument(npasp);
	}

	@Override
    public final void updateChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, ChildDocNotFoundException {
		serverChildren.updateChildDocument(ChildDocument);
	}

	@Override
    public final TPatientCommonInfo getPatientCommonInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
		return serverChildren.getPatientCommonInfo(npasp);
	}

	@Override
    public final String printChildBirthDocument(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException,
			ChildbirthNotFoundException, PatientNotFoundException {
		return serverChildren.printChildBirthDocument(ndoc);
	}

	@Override
    public final String printChildBirthBlankDocument()
			throws KmiacServerException, TException {
		return serverChildren.printChildBirthBlankDocument();
	}
	
	@Override
	public void AddRdDin(final int npasp, final int ngosp) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
    public final void DeleteRdSl(final int id_pvizit, final int npasp)
			throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_sl WHERE id_pvizit = ? and npasp = ?", false, id_pvizit, npasp);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
    public final List<TMedication> getMedications(final int idGosp)
			throws KmiacServerException {
		return serverAssignments.getMedications(idGosp);
	}

	@Override
    public final void updateMedication(final TMedication med) throws KmiacServerException {
		serverAssignments.updateMedication(med);
	}

	@Override
    public final void deleteMedication(final int nlek) throws KmiacServerException {
		serverAssignments.deleteMedication(nlek);
	}

	@Override
    public final List<TDiagnostic> getDiagnostics(final int idGosp) throws KmiacServerException {
		return serverAssignments.getDiagnostics(idGosp);
	}

    @Override
    public final TMedicalHistory getPriemOsmotr(final int idGosp)
            throws KmiacServerException {
        final String sqlQuery = "SELECT * FROM c_osmotr WHERE id_gosp = ? AND is_po = true;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            if (acrs.getResultSet().next()) {
                return rsmMedicalHistory.map(acrs.getResultSet());
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

//	public void addRdIshod(int npasp, int ngosp) throws KmiacServerException,
//			TException {
//		// TODO Auto-generated method stub
//
//	}

}

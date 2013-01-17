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
import java.util.Formatter;
import java.util.GregorianCalendar;
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
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DopShablon;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MesNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PrdIshodNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.RdDinStruct;
import ru.nkz.ivcgzo.thriftHospital.RdInfStruct;
import ru.nkz.ivcgzo.thriftHospital.RdSlStruct;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TLifeHistory;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatientCommonInfo;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.TRdIshod;
import ru.nkz.ivcgzo.thriftHospital.TRd_Novor;
import ru.nkz.ivcgzo.thriftHospital.TRd_Svid;
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
    //TODO: при изменении региона или серии в регионе - поменять:
	private static final String childBirthDocSeries = "32";
    private static Logger log = Logger.getLogger(ServerHospital.class.getName());
    private TServer tServer;
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
	private TResultSetMapper<TRd_Novor, TRd_Novor._Fields> rsmRdNovor;
	private TResultSetMapper<TRd_Svid, TRd_Svid._Fields> rsmRdSvid;
	private TResultSetMapper<TPatientCommonInfo, TPatientCommonInfo._Fields> rsmCommonPatient;
	private TResultSetMapper<RdSlStruct, RdSlStruct._Fields> rsmRdSl;
	private TResultSetMapper<RdDinStruct, RdDinStruct._Fields> rsmRdDin;

    private static final String[] SIMPLE_PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "fam", "im", "ot", "datar", "datap", "cotd", "npal", "nist"
    };
    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "id_gosp", "datar", "fam", "im", "ot", "pol", "nist", "sgrp", "poms",
        "pdms", "mrab", "npal", "reg_add", "real_add"
    };
    private static final String[] LIFE_HISTORY_FIELD_NAMES = {
        "npasp", "allerg", "farmkol", "vitae"
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
        "id", "id_gosp", "cod", "med_op", "date_ustan", "prizn", "vrach" , "diagname"
    };
    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] STR_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] STAGE_FIELD_NAMES = {
        "id", "id_gosp", "stl", "mes", "date_start", "date_end",
        "ukl", "ishod", "result", "time_start", "time_end"
    };
    private static final String[] RDISHOD_FIELD_NAMES = {
   "npasp","ngosp","id_berem","id","oj","hdm","polpl","predpl",
   "vidpl","serd","serd1","serdm","chcc","pozpl","mesto",
   "deyat","shvat","vody","kashetv","poln","potugi",
   "posled","vremp","obol","pupov","obvit","osobp","krov","psih","obezb",
   "eff","prr1","prr2","prr3","prinyl","osmposl","vrash","akush","daterod","srok","ves","vespl","detmesto"
   };
    private static final String[] RDNOVOR_FIELD_NAMES = {
	   "npasp", "nrod", "timeon", "kolchild", "nreb", "massa", "rost",
	   "apgar1", "apgar5", "krit1", "krit2", "krit3", "krit4", "mert", "donosh", "datazap"
   };
    private static final String[] RDSVID_FIELD_NAMES = {
    	"npasp", "ndoc", "doctype", "dateoff", "famreb", "svidvrach"
   };
    private static final String[] COMMON_PATIENT_FIELD_NAMES = {
        "npasp", "full_name", "datar", "pol", "jitel",
        "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_kv"
    };
    private static final Class<?>[] RdIshodtipes = new Class<?>[] {
//    	   "npasp",      "ngosp",   "id_berem",         "id",         "oj",        "hdm",     "polpl",     "predpl",
     Integer.class,Integer.class,Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Integer.class,
//    	   "vidpl",       "serd",     "serd1",      "serdm",        "chcc",     "pozpl",      "mesto",
     Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,
//    	  "deyat",     "shvat",     "vody",   "kashetv",       "poln",    "potugi",
     String.class,String.class,String.class,String.class,String.class,String.class,
//    	   "posled",     "vremp",        "obol",      "pupov",     "obvit",      "osobp",       "krov",      "psih",    "obezb",
     Integer.class, String.class, String.class,Integer.class,String.class,String.class,Integer.class,Boolean.class,String.class, 
//    	     "eff",      "prr1",      "prr2",      "prr3",   "prinyl",   "osmposl",      "vrash",     "akush", "daterod",       "srok",       "ves",   "vespl", "detmesto"
     Integer.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class,Integer.class,Double.class,Double.class,String.class
    };
     private static final String[] RdSlStruct_Fields_names  = {
    "id","npasp","datay","dataosl","abort","shet","datam","yavka1","ishod",
    "datasn","datazs","kolrod","deti","kont","vesd","dsp","dsr","dtroch","cext",        
    "indsol","prmen","dataz","datasert","nsert","ssert","oslab","plrod","prrod",      
    "vozmen","oslrod","polj","dataab","srokab","cdiagt","cvera","id_pvizit",     
    "rost","eko","rub","predp","osp","cmer"
    };
    private static final Class<?>[] rdSlTypes = new Class<?>[] {Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Boolean.class, Double.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,Boolean.class,Boolean.class,Boolean.class,Integer.class,Integer.class};
    private static final String[] RdDinStruct_Fields_names  = {"id_rd_sl",
    "id_pvizit","npasp","srok","grr","ball","oj","hdm","dspos","art1","art2",        
    "art3","art4","spl","oteki","chcc","polpl","predpl","serd","serd1","id_pos",      
    "ves" ,"ngosp","pozpl","vidpl"};
    private static final Class<?>[] rdDinTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Double.class,Integer.class, Integer.class, Integer.class};

    private static final Class<?>[] DIAGNOSIS_TYPES = new Class<?>[] {
    //  id             id_gosp         cod           med_op        date_ustan
        Integer.class, Integer.class , String.class, String.class, Date.class,
    //  prizn          vrach          diagName
        Integer.class, Integer.class, String.class
    };
    private static final Class<?>[] MEDICAL_HISTORY_TYPES = {
    //  id             id_gosp       jalob         morbi          st_praesense  status_localis
        Integer.class, Integer.class, String.class, String.class, String.class, String.class,
    //  fisical_obs   pcod_vrach    dataz       timez
        String.class, Integer.class, Date.class, Time.class
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
    private static final Class<?>[] CHILD_TYPES = new Class<?>[] {
	//	npasp,			nrod,   		timeon,			kolchild,		nreb,			massa,			rost,
    	Integer.class,	Integer.class,	String.class,	Integer.class,	Integer.class,	Integer.class,	Integer.class,
	//	apgar1,			apgar5,			krit1,			krit2,			krit3,			krit4,			mert,
    	Integer.class,	Integer.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,
	//	donosh,			datazap
    	Boolean.class,	Date.class
    };
    private static final Class<?>[] CHILD_DOC_TYPES = new Class<?>[] {
	//	npasp,			ndoc,   		doctype,		dateoff,	famreb,			svidvrach
    	Integer.class,	Integer.class,	Boolean.class,	Date.class,	String.class,	Integer.class
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
        rsmLifeHistory = new TResultSetMapper<>(TLifeHistory.class, LIFE_HISTORY_FIELD_NAMES);
        rsmMedicalHistory = new TResultSetMapper<>(TMedicalHistory.class,
            MEDICAL_HISTORY_FIELD_NAMES);
        rsmDiagnosis = new TResultSetMapper<>(TDiagnosis.class, DIAGNOSIS_FIELD_NAMES);
        rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmStrClas = new TResultSetMapper<>(StringClassifier.class, STR_CLAS_FIELD_NAMES);
        rsmStage = new TResultSetMapper<>(TStage.class, STAGE_FIELD_NAMES);
        rsmRdIshod = new TResultSetMapper<>(TRdIshod.class, RDISHOD_FIELD_NAMES);
        rsmRdNovor = new TResultSetMapper<>(TRd_Novor.class, RDNOVOR_FIELD_NAMES);
        rsmRdSvid = new TResultSetMapper<>(TRd_Svid.class, RDSVID_FIELD_NAMES);
        rsmCommonPatient = new TResultSetMapper<>(TPatientCommonInfo.class, COMMON_PATIENT_FIELD_NAMES);
    }

    @Override
    public final void start() throws Exception {
        ThriftHospital.Processor<Iface> proc =
                new ThriftHospital.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.log(Level.INFO, "hospital server started");
        tServer.serve();
    }

    @Override
    public final void stop() {
        tServer.stop();
        log.log(Level.INFO, "hospital server stopped");
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
                + "patient.ot, patient.datar, c_gosp.datap, c_otd.cotd, c_otd.npal, c_otd.nist "
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
    public final TPatient getPatientPersonalInfo(final int patientId, final int idGosp)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, c_otd.id_gosp, patient.datar, patient.fam, "
                + "patient.im, patient.ot, n_z30.name as pol, c_otd.nist, n_t00.pcod as sgrp, "
                + "(patient.poms_ser||patient.poms_nom) as poms, "
                + "(patient.pdms_ser || patient.pdms_nom) as pdms, "
                + "n_z43.name_s as mrab, c_otd.npal, "
                + "(adp_gorod || ', ' || adp_ul || ', ' || adp_dom) as reg_add, "
                + "(adm_gorod || ', ' || adm_UL || ', ' || adm_dom) as real_add "
                + "FROM patient JOIN c_gosp ON c_gosp.npasp = patient.npasp "
                + "JOIN  c_otd ON c_gosp.id = c_otd.id_gosp "
                + "LEFT JOIN n_t00 ON n_t00.pcod = c_otd.cprof "
                + "LEFT JOIN n_z30 ON n_z30.pcod = patient.pol "
                + "LEFT JOIN n_z43 ON n_z43.pcod = patient.mrab "
                + "WHERE patient.npasp= ? AND c_otd.id_gosp = ?;";
        ResultSet rs = null;

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, patientId, idGosp)) {
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
    public final void updatePatientChamberNumber(final int gospId, final int chamberNum,
            final int profPcod) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_otd SET npal = ?, cprof = ? WHERE id_gosp = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, chamberNum, profPcod, gospId);
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

        // Запрос не работает, т.к. профиль и специализация это разные вещи
//        SELECT DISTINCT sho.id AS pcod, sho.name, 
//        sho.diag || ' ' || sho.name AS name 
//        FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) 
//        JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) 
//        JOIN n_c00 c00 ON (c00.pcod = sho.diag) 
//        JOIN n_t00 t00 ON t00.spec = shp.cspec  
//        JOIN n_n45 n45 ON n45.codprof = t00.pcod
//        WHERE (n45.codotd = 2019) AND ((sho.cslu = 1) OR (sho.cslu =3))

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
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        final String sqlQuery = "INSERT INTO c_osmotr (id_gosp, jalob, "
            + "morbi, status_praesense, "
            + "status_localis, fisical_obs, pcod_vrach, dataz, timez) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
    public final void addZakl(final Zakl zakl) throws KmiacServerException {
        String sqlQuery = "UPDATE c_otd SET result = ?, ishod = ?, datav = ?, vremv = ?, "
            + "sostv = ?, recom = ?, vrach = ?,  vid_opl = ?, vid_pom = ?, ukl = ? "
            + "WHERE id_gosp = ?";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (zakl.isSetNewOtd() && (zakl.getIshod() == 3)) {
                sqlQuery = "UPDATE c_otd SET ishod = ?, "
                    + "sostv = ?, recom = ?, vrach = ?, vid_opl = ?, vid_pom = ?, ukl = ? "
                    + "WHERE id_gosp = ?";
                sme.execPrepared(sqlQuery, false, zakl.getIshod(),
                    zakl.getSostv(), zakl.getRecom(),
                    null, zakl.getVidOpl(), zakl.getVidPom(), zakl.getUkl(),
                    zakl.getIdGosp());
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
    public TRdIshod getRdIshodInfo(int npasp,int ngosp)
			throws PrdIshodNotFoundException, KmiacServerException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "select * from c_rd_ishod where npasp = ? and ngosp = ? ", npasp, ngosp)) {
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
    public final void addRdIshod(int npasp, int ngosp) throws KmiacServerException,
			TException {
		AutoCloseableResultSet acrs = null; AutoCloseableResultSet acrs1 = null;
		Integer id1 = 0; Integer numr = 0;Integer srok = 40;Integer numdin = 0;
		Integer oj = 100; Integer hdm = 30; Integer polpl = 1;Integer predpl = 1;
		Integer chcc = 110; Integer serd = 1; Integer serd1 = 1; double ves = 70.0;double vespl = 3.00;
		Date datarod = Date(System.currentTimeMillis());
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			 acrs = sse.execPreparedQuery("select max(id) from p_rd_sl where npasp= ? ", npasp);
				if (acrs.getResultSet().next()) {id1 = acrs.getResultSet().getInt(1);
				 acrs1 = sse.execPreparedQuery("select (current_date-datay)/7+yavka1,id_pvizit from p_rd_sl where id= ? ", id1);
				 if (acrs1.getResultSet().next()){
				 numr = acrs1.getResultSet().getInt(2);
				 srok = acrs1.getResultSet().getInt(1);
				 }
				 acrs1.close();
				 acrs1 = sse.execPreparedQuery("select max(id_pos) from p_rd_din where id_pvizit = ? ", numr);
				 if (acrs1.getResultSet().next()) {
                    numdin = acrs1.getResultSet().getInt(1);
                }
				 acrs1.close();
				 acrs1 = sse.execPreparedQuery("select (current_date-datap)/7+srok,oj,hdm,polpl,predpl,chcc,serd,serd1,ves from p_rd_din,p_vizit_amb where p_rd_din.id_pos=p_vizit_amb.id and p_rd_din.id_pos= ? ", numdin);
				 if (acrs1.getResultSet().next()){
					 srok = acrs1.getResultSet().getInt(1);oj = acrs1.getResultSet().getInt(2); 
					 hdm = acrs1.getResultSet().getInt(3); polpl = acrs1.getResultSet().getInt(4);
					 predpl = acrs1.getResultSet().getInt(5);chcc = acrs1.getResultSet().getInt(6); 
					 serd = acrs1.getResultSet().getInt(7); serd1 = acrs1.getResultSet().getInt(8); 
					 ves = acrs1.getResultSet().getDouble(9);
					 vespl = ((oj*hdm)/4*100+oj*hdm+(hdm-11)*155+ves/20)/4;
					}
				}
				int id = sme.getGeneratedKeys().getInt("id");
			sme.execPreparedQuery("insert into c_rd_ishod (npasp,ngosp,id_berem,id,oj,hdm,polpl,predpl,serd,serd1,chcc, "+
   "daterod) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ",npasp,ngosp,numr,id,oj,hdm,polpl,predpl,serd,serd1,chcc,datarod);
//			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return;
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
    public final void deleteRdIshod(int npasp, final int ngosp)
			throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM c_rd_ishod WHERE npasp = ? and ngosp = ? ", false, npasp,ngosp);
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
    public final void updateRdIshod(TRdIshod RdIs) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPreparedT("UPDATE c_rd_ishod SET oj = ?,hdm = ?,polpl = ?,predpl = ?,vidpl = ?,serd = ?,serd1 = ?,serdm = ?,chcc = ?,pozpl = ?,mesto = ?,deyat = ?,shvat = ?,vody = ?,kashetv = ?,poln = ?,potugi = ?, "+
"posled = ?,vremp = ?,obol = ?,pupov = ?,obvit = ?,osobp = ?,krov = ?,psih = ?,obezb = ?,eff = ?,prr1 = ?,prr2 = ?,prr3 = ?,prinyl = ?,osmposl = ?,vrash = ?,akush = ?, "+
"daterod = ?  WHERE ngosp = ? and npasp = ?", false,RdIs, RdIshodtipes,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,36,37,38,0,1);
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
//                if (acrs.getResultSet().getString("jalob") != null) {
                htmTemplate.replaceLabel(
                    "~jalob", acrs.getResultSet().getString("jalob"));
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

            acrs = sse.execPreparedQuery("SELECT n_c00.name FROM c_diag "
                    + "INNER JOIN n_c00 ON c_diag.cod = n_c00.pcod WHERE c_diag.id_gosp = ? "
                    + "AND c_diag.prizn = ?", idGosp, 5);

            if (acrs.getResultSet().next()) {
                htmTemplate.replaceLabel("~patDiagnosis",
                    acrs.getResultSet().getString("name"));
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
	public List<IntegerClassifier> get_s_vrach() throws KmiacServerException, TException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod,fam||' '||im||' '||ot as name from s_vrach")) {
	        return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
    }

	@Override
	public List<IntegerClassifier> getChildBirths(final long BirthDate) throws KmiacServerException, TException {
		final String SQLQuery = "SELECT a.id AS pcod, 'Мама - '||b.fam||' '||b.im||' '||b.ot AS name FROM c_rd_ishod a " +
								"JOIN patient b ON (a.npasp = b.npasp) " +
								"WHERE a.daterod = ?;";
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(SQLQuery, new java.sql.Date(BirthDate))) {
	        return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
	}

	/**
	 * Проверка существования пациента
	 * @param npasp - уникальный номер пациента
	 * @return Возвращает <code>true</code>, если пациент существует; иначе - <code>false</code>
	 */
	private boolean isPatientExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM patient WHERE npasp = ?;", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
    }

	/**
	 * Проверка существования информации о новорождённом
	 * @param npasp - уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о новорождённом существует; иначе - <code>false</code>
	 */
	private boolean isChildExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_novor WHERE npasp = ?;", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
    }

	/**
	 * Проверка существования свидетельства о рождении/перинатальной смерти новорождённого
	 * @param npasp - уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о свидетельстве существует; иначе - <code>false</code>
	 */
	private boolean isChildDocExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
    }

	/**
	 * Проверка уникальности номера свидетельства заданного типа
	 * @param ndoc - номер свидетельства
	 * @param doctype - тип свидетельства (<code>true</code>, если свидетельство о рождении;
	 * <code>false</code> - о перинатальной смерти новорождённого)
	 * @return Возвращает <code>true</code>, если номер свидетельства уникален; иначе - <code>false</code>
	 */
	private boolean isChildDocUnique(final int ndoc, final boolean doctype) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid WHERE (ndoc = ?) AND (doctype = ?);", ndoc, doctype)) {
            return !acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
	}

	@Override
	public void addChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException, TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        final String sqlQuery = "INSERT INTO c_rd_novor (npasp, nrod, timeon, kolchild, nreb, massa, rost, " +
    		"apgar1, apgar5, krit1, krit2, krit3, krit4, mert, donosh, datazap) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
                sme.setCommit();
            } else
                throw new PatientNotFoundException();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TRd_Novor getChildInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException, TException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_novor WHERE npasp = ?;", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdNovor.map(acrs.getResultSet());
            } else
                throw new PatientNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	    
		@Override
		public RdSlStruct getRdSlInfo(int npasp) throws KmiacServerException,
			TException {
        AutoCloseableResultSet acrs1;
        Date daterod =  new Date(System.currentTimeMillis()-280*24*60*60*1000);
//         daterod =  new Date(System.currentTimeMillis()-24192000000);
		Integer ish = 1;
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where npasp = ? and datay>= ? ", npasp,daterod)) {
			if (!acrs.getResultSet().next()) {
				try (SqlModifyExecutor sme = tse.startTransaction()) {
					sme.execPrepared("insert into p_rd_sl " +
						"(npasp,datay,ishod) VALUES (?,?,?) ",true, npasp,daterod,ish);
					int id = sme.getGeneratedKeys().getInt("id");
					sme.setCommit();
				} catch (InterruptedException e) {
					throw new KmiacServerException();
				}
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		try (AutoCloseableResultSet acrs2 = sse.execPreparedQuery(
				"select * from p_rd_sl where npasp = ? and datay>= ? ", npasp,daterod)) {
			if (acrs2.getResultSet().next())
				return rsmRdSl.map(acrs2.getResultSet());
			else
				throw new KmiacServerException("rd sl not found");
		} catch (SQLException e) {
			throw new KmiacServerException();
		}	
	}

	@Override
	public RdDinStruct getRdDinInfo(int npasp,int ngosp)
			throws KmiacServerException, TException {
	    Integer srok = 0;
	    Integer oj = 0;
	    Integer hdm = 0;
	    Integer spl = 0;Integer chcc = 0;Integer polpl =0 ;Integer predpl =0;
	    Integer serd =0 ;Integer serd1 =0 ;
	    Double ves = 0.0; 
  		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_din where npasp = ? and ngosp= ? ", npasp,ngosp)) {
			if (!acrs.getResultSet().next()) {
				AutoCloseableResultSet acrs1 = sse.execPreparedQuery("select srok,oj, "+
		        "hdm,spl,chcc,polpl,predpl,serd,serd1,ves "+	
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
				}
				try (SqlModifyExecutor sme = tse.startTransaction()) {
					sme.execPrepared("insert into p_rd_din " +
						"(npasp,ngosp,srok,oj,hdm,spl,chcc,polpl,predpl,serd,serd1,ves) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ",true, npasp,ngosp,srok,oj,hdm,spl,chcc,polpl,predpl,serd,serd1,ves);
//					int id = sme.getGeneratedKeys().getInt("id");
					sme.setCommit();
				} catch (InterruptedException e) {
					throw new KmiacServerException();
				}
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		try (AutoCloseableResultSet acrs2 = sse.execPreparedQuery(
				"select * from p_rd_din where npasp = ? and ngosp= ? ", npasp,ngosp)) {
			if (acrs2.getResultSet().next())
				return rsmRdDin.map(acrs2.getResultSet());
			else
				throw new KmiacServerException("rd sl not found");
		} catch (SQLException e) {
			throw new KmiacServerException();
		}	
	}

	@Override
	public RdInfStruct getRdInfInfo(int npasp) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int AddRdSl(RdSlStruct rdSl) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void DeleteRdDin(int ngosp) throws KmiacServerException, TException {
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
	public void UpdateRdSl(RdSlStruct Dispb) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_sl SET npasp = ?, datay = ?, dataosl = ?, abort = ?, shet = ?, datam = ?, yavka1 = ?, ishod = ?,datasn = ?, datazs = ?,kolrod = ?, deti = ?, kont = ?, vesd = ?, dsp = ?,dsr = ?,dtroch = ?, cext = ?, indsol = ?, prmen = ?,dataz = ?, datasert = ?, nsert = ?, ssert = ?, oslab = ?, plrod = ?, prrod = ?, vozmen = ?, oslrod = ?, polj = ?, dataab = ?, srokab = ?, cdiagt = ?, cvera = ?, rost = ?,eko =?, rub = ?, predp = ?, osp = ?, cmer = ?  WHERE id_pvizit = ?", false, Dispb, rdSlTypes, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,36,37,38,39,40,41, 35);
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
	public void updateChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException, TException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        final String sqlQuery = "UPDATE c_rd_novor " +
        						"SET timeon = ?, kolchild = ?, nreb = ?, massa = ?, rost = ?, apgar1 = ?, apgar5 = ?, " +
        						"krit1 = ?, krit2 = ?, krit3 = ?, krit4 = ?, mert = ?, donosh = ?, datazap = ? " +
        						"WHERE npasp = ?;";
		try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
				sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
				sme.setCommit();
            } else
                throw new PatientNotFoundException();
		} catch (Exception e) {
            throw new KmiacServerException();
		}
	}
	
	@Override
	public void UpdateRdDin(RdDinStruct Din) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_din SET  srok = ?, grr = ?, ball = ?, oj = ?, hdm = ?, dspos = ?, art1 = ?, art2 = ?, art3 = ?, art4 = ?, spl = ?, oteki = ?, chcc = ?, polpl = ?, predpl = ?, serd = ?, serd1 = ?, ves = ?,ngosp = ?, pozpl = ?,vidpl = ?  WHERE ngosp = ? and npasp = ? ", false, Din, rdDinTypes,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,23,24,22, 2);
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
	public int addChildDocument(final TRd_Svid ChildDocument)
			throws KmiacServerException, PatientNotFoundException, TException {
        final int[] indexes = {0, 2, 3, 4, 5};
        final String sqlQuery = "INSERT INTO c_rd_svid (npasp, doctype, dateoff, famreb, svidvrach) " +
        						"VALUES (?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, true, ChildDocument, CHILD_DOC_TYPES, indexes);
                int ndoc = sme.getGeneratedKeys().getInt("ndoc");
                sme.setCommit();
                return ndoc;
            } else {
            	sme.rollbackTransaction();
            	throw new PatientNotFoundException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TRd_Svid getChildDocument(final int npasp)
			throws KmiacServerException, ChildDocNotFoundException, TException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid WHERE (npasp = ?);", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvid.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	private TRd_Svid getChildDocumentByDoc(final int ndoc, final boolean doctype)
			throws KmiacServerException, ChildDocNotFoundException, TException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid WHERE (ndoc = ?) AND (doctype = ?);", ndoc, doctype)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvid.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void updateChildDocument(final TRd_Svid ChildDocument)
			throws KmiacServerException, ChildDocNotFoundException, TException {
        final int[] indexes = {3, 4, 5, 0};
        //Поля ndoc и doctype изменять нельзя (в списке параметров не присутствуют):
        final String sqlQuery = "UPDATE c_rd_svid " +
        						"SET dateoff = ?, famreb = ?, svidvrach = ? " +
        						"WHERE (npasp = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildDocExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, ChildDocument, CHILD_DOC_TYPES, indexes);
                sme.setCommit();
            } else
                throw new ChildDocNotFoundException();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TPatientCommonInfo getPatientCommonInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException, TException {
        String sqlQuery = "SELECT npasp, fam||' '||im||' '||ot as full_name, datar, "
        		+ "n_z30.name as pol, n_am0.name as jitel, adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv "
                + "FROM patient "
                + "LEFT JOIN n_z30 ON (n_z30.pcod = patient.pol) "
                + "LEFT JOIN n_am0 ON (n_am0.pcod = patient.jitel) "
                + "WHERE patient.npasp = ?;";
        ResultSet rs = null;
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmCommonPatient.map(rs);
            } else {
                log.log(Level.INFO, "PatientNotFoundException, patientId = " + npasp);
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения идентификатора матери новорождённого
	 * @param childId Идентификатор новорождённого
	 * @return Идентификатор матери
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws PatientNotFoundException новорождённый не найден
	 */
	private int getMotherId(final int childId)
			throws KmiacServerException, PatientNotFoundException {
		final String Query = "SELECT c_rd_ishod.npasp " +
				"FROM c_rd_ishod " +
				"JOIN c_rd_novor ON (c_rd_novor.nrod = c_rd_ishod.id) " +
				"WHERE c_rd_novor.npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
                throw new PatientNotFoundException();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public String printChildBirthDocument(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException, TException {
        final String path;
        final String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
        						"июля", "августа", "сентября", "октября", "ноября", "декабря"};
        if (isChildDocUnique(ndoc, true))	//Свидетельство с таким номером не существует
        	throw new ChildDocNotFoundException();
        Formatter f = new Formatter();
        final String childBirthNumber = f.format("%6d", ndoc).toString();
        TRd_Svid childDoc = getChildDocumentByDoc(ndoc, true);
        TRd_Novor childBirthInfo = getChildInfo(childDoc.getNpasp());
        TPatientCommonInfo childInfo = getPatientCommonInfo(childDoc.getNpasp());
        TPatientCommonInfo motherInfo = getPatientCommonInfo(getMotherId(childDoc.getNpasp()));
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().getAbsolutePath()
                    + "\\plugin\\reports\\ChildBirthDocument.htm");
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMMMMM");
            SimpleDateFormat sdfMonthShort = new SimpleDateFormat("MM");
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            GregorianCalendar dateOff = new GregorianCalendar();
            GregorianCalendar curDate = new GregorianCalendar();
            dateOff.setTimeInMillis(childDoc.getDateoff());	//Дата выдачи мед.свид-ва
            //Местность регистрации матери:
            String city1 = "", city2 = "";
            String country1 = "", country2 = "";
            if (motherInfo.isSetJitel())
            	if (motherInfo.getJitel().toLowerCase().equals("городской"))
            	{
            		city1 = "<u>";
            		city2 = "</u>";
            	}
            	else
            		if (motherInfo.getJitel().toLowerCase().equals("сельский"))
                	{
            			country1 = "<u>";
            			country2 = "</u>";
                	}
            //Пол новорождённого:
            String boy1 = "", boy2 = "";
            String girl1 = "", girl2 = "";
            if (childInfo.isSetPol())
            	if (childInfo.getPol().toLowerCase().equals("мужской"))
            	{
            		boy1 = "<u>";
            		boy2 = "</u>";
            	}
            	else
            		if (childInfo.getPol().toLowerCase().equals("женский"))
                	{
            			girl1 = "<u>";
            			girl2 = "</u>";
                	}
            //Полное имя матери:
            String motherFullName = motherInfo.getFull_name();
            //Фамилия матери:
            int firstSpace = motherFullName.indexOf(' ');
            final String motherSurname = motherFullName.substring(0, firstSpace);
            //Имя и отчество матери:
            final String motherFirstName = motherFullName.substring(firstSpace + 1, motherFullName.length());
            //Время рождения:
            final String childBirthTime = (childBirthInfo.isSetTimeon()) ? childBirthInfo.getTimeon() : "";
            String childBirthHour = "", childBirthMinute = "";
            if (childBirthInfo.isSetTimeon()) {
	            childBirthHour = childBirthTime.substring(0, 2);
	            childBirthMinute = childBirthTime.substring(3, 5);
            }
            //Который по счёту:
            String nChildren = "  ";
            if (childBirthInfo.isSetKolchild())
            	nChildren = f.format("%2d", childBirthInfo.getKolchild()).toString();
            //Вес:
            String weight = "    ";
            if (childBirthInfo.isSetMassa())
            	weight = f.format("%4d", childBirthInfo.getMassa()).toString();
            //Рост:
            String height = "  ";
            if (childBirthInfo.isSetRost())
            	height = f.format("%2d", childBirthInfo.getRost()).toString();
            //Одноплодные\многоплодные роды:
            String only = "", nreb = "", nreb_all = "";	//TODO: nreb_all - продумать!
            if (childBirthInfo.isSetNreb())
            {
            	if (childBirthInfo.getNreb() == 0)
            		only = "V";
            	else
            		nreb = f.format("%d", childBirthInfo.getNreb()).toString();
            }
            f.close();
            htmTemplate.replaceLabels(false,
        		ServerHospital.childBirthDocSeries, childBirthNumber,
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		sdfDay.format(childInfo.getDatar()), sdfMonth.format(childInfo.getDatar()).toUpperCase(),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		motherFullName,
        		sdfDay.format(motherInfo.getDatar()), sdfMonth.format(motherInfo.getDatar()).toUpperCase(),
        		sdfYear.format(motherInfo.getDatar()),
        		(motherInfo.isSetAdp_obl()) ? motherInfo.getAdp_obl() : "",
        		"",	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		(motherInfo.isSetAdp_gorod()) ? motherInfo.getAdp_gorod() : "",
        		(motherInfo.isSetAdp_ul()) ? motherInfo.getAdp_ul() : "",
        		(motherInfo.isSetAdp_dom()) ? motherInfo.getAdp_dom() : "",
        		(motherInfo.isSetAdp_kv()) ? motherInfo.getAdp_kv() : "",
        		city1, city2, country1, country2,
        		boy1, boy2, girl1, girl2,
        		//TODO: ЗАПИСАТЬ ШАПКУ:
        		"", "", "", "",
        		childBirthDocSeries, childBirthNumber,
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		sdfDay.format(childInfo.getDatar()),
        		sdfMonth.format(childInfo.getDatar()).toUpperCase(),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		motherSurname, motherFirstName, childDoc.getFamreb().toUpperCase(),
        		sdfDay.format(motherInfo.getDatar()).substring(0, 1),
        		sdfDay.format(motherInfo.getDatar()).substring(1, 2),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(0, 1),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(0, 1),
        		sdfYear.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(2, 3),
        		sdfYear.format(motherInfo.getDatar()).substring(3, 4),
        		(motherInfo.isSetAdp_obl()) ? motherInfo.getAdp_obl() : "",
        		"",	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		(motherInfo.isSetAdp_gorod()) ? motherInfo.getAdp_gorod() : "",
        		(motherInfo.isSetAdp_ul()) ? motherInfo.getAdp_ul() : "",
        		(motherInfo.isSetAdp_dom()) ? motherInfo.getAdp_dom() : "",
        		(motherInfo.isSetAdp_kv()) ? motherInfo.getAdp_kv() : "",
        		city1, city2, country1, country2,
        		//МЕСТО РОЖДЕНИЯ:
    			"", "", "",
        		//МЕСТНОСТЬ РОЖДЕНИЯ:
    			"", "",
    			"", "",
        		boy1, boy2, girl1, girl2,
        		sdfDay.format(curDate.getTimeInMillis()), months[curDate.get(GregorianCalendar.MONTH)],
        		sdfYear.format(curDate.getTimeInMillis()),
        		nChildren.substring(0, 1), nChildren.substring(1, 2),
        		weight.substring(0, 1), weight.substring(1, 2), weight.substring(2, 3), weight.substring(3, 4),
        		height.substring(0, 1), height.substring(1, 2),
        		only, nreb, nreb_all);
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            throw new KmiacServerException();
        }
	}

	@Override
	public String printChildDeathDocument(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException, TException {
        if (isChildDocUnique(ndoc, false))	//Свидетельства с таким номером не существует
        	throw new ChildDocNotFoundException();
		return null;
	}

	@Override
	public String printChildBlankDocument(boolean isLiveChild)
			throws KmiacServerException, TException {
        final String path, patternPath, dblSpace = "&nbsp;&nbsp;", spaceBar = "&nbsp;&nbsp;&nbsp;&nbsp;";
        if (isLiveChild)	//Печать бланка мед.свидетельства о рождении
        	patternPath = "\\plugin\\reports\\ChildBirthDocument.htm";
        else				//Печать бланка мед.свидетельства о перинатальной смерти
        	patternPath = "\\plugin\\reports\\ChildDeathDocument.htm";
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().
            		getAbsolutePath() + patternPath);
            htmTemplate.replaceLabels(true,
            		ServerHospital.childBirthDocSeries,
            		spaceBar + dblSpace, spaceBar + dblSpace, spaceBar + spaceBar + spaceBar + dblSpace,
            		spaceBar + spaceBar + "&nbsp;", "", spaceBar + spaceBar + spaceBar,
            		spaceBar, "", "", "", "", spaceBar + spaceBar + spaceBar,
            		spaceBar, "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            		"", "", "", "", ServerHospital.childBirthDocSeries,
            		spaceBar + dblSpace, spaceBar + dblSpace, spaceBar + spaceBar + spaceBar + dblSpace,
            		spaceBar + spaceBar + "&nbsp;", "", spaceBar + spaceBar + spaceBar,
            		spaceBar, "", "");
	        osw.write(htmTemplate.getTemplateText());
	        return path;
	    } catch (Exception e) {
	        throw new KmiacServerException();
	    }
	}
	
	@Override
	public void UpdateRdInf(RdInfStruct inf) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddRdInf(RdInfStruct rdInf) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteRdInf(int npasp) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddRdDin(int npasp, int ngosp) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteRdSl(int id_pvizit, int npasp)
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
}

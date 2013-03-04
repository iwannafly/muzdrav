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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

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
import ru.nkz.ivcgzo.thriftHospital.TBirthPlace;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TDiagnostic;
import ru.nkz.ivcgzo.thriftHospital.TInfoLPU;
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
    //TODO: при изменении региона или серии в регионе - поменять:
	private static final String childBirthDocSeries = "32";
	private static final String childBirthDocPath = "\\plugin\\reports\\ChildBirthDocument.htm";
    private static Logger log = Logger.getLogger(ServerHospital.class.getName());
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
	private TResultSetMapper<TRd_Novor, TRd_Novor._Fields> rsmRdNovor;
	private TResultSetMapper<TRd_Svid_Rojd, TRd_Svid_Rojd._Fields> rsmRdSvidRojd;
	private TResultSetMapper<TPatientCommonInfo, TPatientCommonInfo._Fields> rsmCommonPatient;
	private TResultSetMapper<RdSlStruct, RdSlStruct._Fields> rsmRdSl;
	private TResultSetMapper<RdDinStruct, RdDinStruct._Fields> rsmRdDin;
	private TResultSetMapper<TBirthPlace, TBirthPlace._Fields> rsmBirthPlace;
	private TResultSetMapper<TInfoLPU, TInfoLPU._Fields> rsmInfoLPU;
	private TResultSetMapper<TMedication, TMedication._Fields> rsmMedication;
	private TResultSetMapper<TDiagnostic, TDiagnostic._Fields> rsmDiagnostic;

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
   "npasp","ngosp","id_berem","id","serdm","mesto",
   "deyat","shvatd","vodyd","kashetv","polnd","potugid",
   "posled","vremp","obol","lpupov","obvit","osobp","krov","psih","obezb",
   "eff","prr1","prr2","prr3","prinyl","osmposl","vrash","akush","daterod","vespl","detmesto",
   "shvatt","vodyt","polnt","potugit"
   };
    private static final String[] RDNOVOR_FIELD_NAMES = {
	   "npasp", "nrod", "timeon", "kolchild", "nreb", "massa", "rost",
	   "apgar1", "apgar5", "krit1", "krit2", "krit3", "krit4", "mert", "donosh", "datazap"
   };
    private static final String[] RDSVID_ROJD_FIELD_NAMES = {
    	"npasp", "ndoc", "famreb", "m_rojd", "zan", "r_proiz", "svid_write", "cdol_write",
    	"clpu", "svid_give", "cdol_give", "dateoff"
   };
    private static final String[] COMMON_PATIENT_FIELD_NAMES = {
        "npasp", "full_name", "datar", "pol", "jitel",
        "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_korp",
        "adp_kv", "obraz", "status"
    };
    private static final String[] LPU_FIELD_NAMES = {
        "name", "adres", "okpo", "zaved"
    };
    private static final String[] BIRTHPLACE_FIELD_NAMES = {
        "region", "city", "type"
    };
    private static final String[] MEDICATION_FIELD_NAMES = {
        "name", "nlek", "id_gosp", "vrach", "datan", "klek", "flek", "doza", "ed", "sposv",
        "spriem", "pereod", "datae", "komm", "datao", "vracho", "dataz"
    };    
    private static final String[] DIAGNOSTIC_FIELD_NAMES = {
        "nisl", "cpok", "cpok_name", "result", "datav", "op_name", "rez_name"
    };
    
    private static final Class<?>[] RdIshodtipes = new Class<?>[] {
//    	   "npasp",      "ngosp",   "id_berem",         "id",	   "serdm",     "mesto", 
     Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,
//    	  "deyat",  "shvatd",   "vodyd",   "kashetv",   "polnd","potugid",
     String.class,Date.class,Date.class,String.class,Date.class,Date.class,
//    	   "posled",     "vremp",        "obol",      "lpupov",     "obvit",      "osobp",       "krov",      "psih",    "obezb",
     Integer.class, Integer.class, String.class,Integer.class,String.class,String.class,Integer.class,Boolean.class,String.class, 
//    	     "eff",      "prr1",      "prr2",      "prr3",   "prinyl",   "osmposl",      "vrash",     "akush", "daterod",        "vespl", "detmesto"
     Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class,Double.class,String.class,
//     "shvatt",   "vodyt",   "polnt", "potugit"
     Time.class,Time.class,Time.class,Time.class
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
    "art3","art4","oteki","spl","chcc","polpl","predpl","serd","serd1","id_pos",      
    "ves" ,"ngosp","pozpl","vidpl"};
    private static final Class<?>[] rdDinTypes = new Class<?>[] {Integer.class,
//                                                              	"id_rd_sl",
    Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//    "id_pvizit",       "npasp",        "srok",         "grr",        "ball", 
    Integer.class, Integer.class, String.class, Integer.class, Integer.class,
//           "oj",         "hdm",      "dspos",        "art1",        "art2",  
    Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//         "art3",        "art4",       "oteki",         "spl",        "chcc",
    Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, 
//        "polpl",      "predpl",        "serd",       "serd1",      "id_pos",  
    Double.class,Integer.class, Integer.class, Integer.class};
//        "ves" ,      "ngosp",       "pozpl",       "vidpl"};

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
    private static final Class<?>[] CHILD_TYPES = new Class<?>[] {
	//	npasp,			nrod,   		timeon,			kolchild,		nreb,			massa,			rost,
    	Integer.class,	Integer.class,	String.class,	Integer.class,	Integer.class,	Integer.class,	Integer.class,
	//	apgar1,			apgar5,			krit1,			krit2,			krit3,			krit4,			mert,
    	Integer.class,	Integer.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,
	//	donosh,			datazap
    	Boolean.class,	Date.class
    };
    private static final Class<?>[] CHILDBIRTH_DOC_TYPES = new Class<?>[] {
	//	npasp,			ndoc,   		famreb,			m_rojd			zan				r_proiz
    	Integer.class,	Integer.class,	String.class,	Integer.class,	Integer.class,	Integer.class,
    //	svid_write		cdol_write		clpu			svid_give		cdol_give		dateoff
    	Integer.class,	String.class,	Integer.class,	Integer.class,	String.class,	Date.class
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
        rsmRdDin = new TResultSetMapper<>(RdDinStruct.class, RdDinStruct_Fields_names);
        rsmRdSl = new TResultSetMapper<>(RdSlStruct.class, RdSlStruct_Fields_names);
        rsmRdNovor = new TResultSetMapper<>(TRd_Novor.class, RDNOVOR_FIELD_NAMES);
        rsmRdSvidRojd = new TResultSetMapper<>(TRd_Svid_Rojd.class, RDSVID_ROJD_FIELD_NAMES);
        rsmCommonPatient = new TResultSetMapper<>(TPatientCommonInfo.class,
                COMMON_PATIENT_FIELD_NAMES);
        rsmBirthPlace = new TResultSetMapper<>(TBirthPlace.class, BIRTHPLACE_FIELD_NAMES);
        rsmInfoLPU = new TResultSetMapper<>(TInfoLPU.class, LPU_FIELD_NAMES);
        rsmMedication = new TResultSetMapper<>(TMedication.class, MEDICATION_FIELD_NAMES);
        rsmDiagnostic = new TResultSetMapper<>(TDiagnostic.class, DIAGNOSTIC_FIELD_NAMES);
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
                + "WHERE c_otd.id_gosp = ?;"; // тут был еще npasp, но он лишний
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
                + "WHERE c_otd.id = ?;"; // тут был еще npasp, но он лишний
        ResultSet rs = null;

        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idCotd)) {
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
    public final void updatePatientChamberNumber(final int gospId, final int chamberNum,
            final int profPcod, final int nist) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_otd SET npal = ?, cprof = ?, nist = ? WHERE id_gosp = ?;";
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
    public TRdIshod getRdIshodInfo(int npasp, int ngosp)
			throws PrdIshodNotFoundException, KmiacServerException {
        System.out.println("случай родов выбор");
        System.out.println(npasp);
        System.out.println(ngosp);

	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * " +
		        "FROM c_rd_ishod " +
		        "WHERE (npasp = ?) AND (ngosp = ?);", npasp, ngosp)) {
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
	public int addRdIshod(TRdIshod rdIs) throws KmiacServerException,
			TException {
        System.out.println("Добавление случая родов");
        System.out.println(rdIs);
  		AutoCloseableResultSet acrs = null; AutoCloseableResultSet acrs1 = null;
		Integer id1 = 0; Integer numr = 0;Integer numdin = 0;
		rdIs.setId_berem(0);
		Date datarod = Date(System.currentTimeMillis());
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			 acrs = sse.execPreparedQuery("select max(id) from p_rd_sl where npasp= ? ", 1);
			 if (acrs.getResultSet().next()) {id1 = acrs.getResultSet().getInt(1);     System.out.println(id1);
				 acrs1 = sse.execPreparedQuery("select (current_date-datay)/7+yavka1,id_pvizit from p_rd_sl where id= ? ", id1);
				 if (acrs1.getResultSet().next()){
				 rdIs.setId_berem(acrs1.getResultSet().getInt(2));
				 }
				 acrs1.close();
				 acrs1 = sse.execPreparedQuery("select max(id_pos) from p_rd_din where id_pvizit = ? ", numr);
				 if (acrs1.getResultSet().next()) {
                    numdin = acrs1.getResultSet().getInt(1);
                }
				}
				sme.execPreparedT("insert into c_rd_ishod (npasp,ngosp,daterod,id_berem,shvatd,potugid,polnd,vodyd) "+				
						   "VALUES (?,?,?,?,?,?,?,?) ", true, rdIs, RdIshodtipes,0,1,29,2,7,11,10,8);
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
		sme.execPreparedT("UPDATE c_rd_ishod SET mesto = ?,deyat = ?,shvatd = ?,vodyd = ?,kashetv = ?,polnd = ?,potugid = ?, "+
"posled = ?,vremp = ?,obol = ?,lpupov = ?,obvit = ?,osobp = ?,krov = ?,psih = ?,obezb = ?,eff = ?,prr1 = ?,prr2 = ?,prr3 = ?,prinyl = ?,osmposl = ?,vrash = ?,akush = ?, "+
"daterod = ?, vespl =?, detmesto = ?,shvatt = ?,vodyt = ?,polnt = ?,potugit = ?  WHERE npasp = ? and ngosp = ? and id = ? ", 
false,RdIs, RdIshodtipes,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,0,1,3);
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
	public List<IntegerClassifier> get_s_vrach(final int clpu)
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
		public RdSlStruct getRdSlInfo(int npasp) 
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
	public RdDinStruct getRdDinInfo(int npasp,int ngosp)
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
	public void AddRdSl(RdSlStruct rdSl) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return ;
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
	public void UpdateRdDin(RdDinStruct Din) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_din SET  srok = ?, oj = ?, hdm = ?, chcc = ?, polpl = ?, "+
		"predpl = ?, serd = ?, serd1 = ?, ves = ?, pozpl = ?, "+
		"vidpl = ?  WHERE ngosp = ? and npasp = ? ", false, Din, rdDinTypes,3,6,7,15,16,17,18,19,21,23,24, 22,2);
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
		sme.execPreparedT("UPDATE p_rd_sl SET  dataosl = ?, shet = ?, datam = ?, ishod = ?, kolrod = ?, " +
		"dsp = ?,dsr = ?,dtroch = ?, cext = ?, cdiagt = ?, cvera = ?  WHERE npasp = ? and datasn = ?", 
		false, Dispb, rdSlTypes, 3,5,6,8,11,15,16,17,18,33,34,1,9);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	/**
	 * Проверка существования пациента
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если пациент существует;
	 * иначе - <code>false</code>
	 * @author Балабаев Никита Дмитриевич
	 */
	private boolean isPatientExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM patient " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (isPatientExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка существования информации о новорождённом
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о новорождённом существует;
	 * иначе - <code>false</code>
	 * @author Балабаев Никита Дмитриевич
	 */
	private boolean isChildExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_novor " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (isChildExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка существования мед.свидетельства о рождении
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о свидетельстве существует;
	 * иначе - <code>false</code>
	 * @author Балабаев Никита Дмитриевич
	 */
	private boolean isChildDocExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid_rojd " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (isChildDocExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка уникальности номера мед.свидетельства о рождении
	 * @param ndoc Номер свидетельства
	 * @return Возвращает <code>true</code>, если номер свидетельства уникален;
	 * иначе - <code>false</code>
	 * @author Балабаев Никита Дмитриевич
	 */
	private boolean isChildDocUnique(final int ndoc) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid_rojd " +
                "WHERE (ndoc = ?);", ndoc)) {
            return !acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (isChildDocUnique): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
	}
	
	/**
	 * Функция получения информации о мед.свидетельстве о рождении
	 * по номеру свидетельства
	 * @param ndoc Идентификатор свидетельства
	 * @return Возвращает информацию о свидетельстве
	 * @throws ChildDocNotFoundException свидетельство не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private TRd_Svid_Rojd getChildDocumentByDoc(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid_rojd " +
		        "WHERE (ndoc = ?);", ndoc)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvidRojd.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildDocumentByDoc): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	/**
	 * Функция получения идентификатора матери новорождённого
	 * @param childId Идентификатор новорождённого
	 * @return Идентификатор матери
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws PatientNotFoundException новорождённый не найден
	 * @author Балабаев Никита Дмитриевич
	 */
	private int getMotherId(final int childId)
			throws KmiacServerException, PatientNotFoundException {
		final String Query = "SELECT c_rd_ishod.npasp " +
				"FROM c_rd_ishod " +
				"JOIN c_rd_novor ON (c_rd_novor.nrod = c_rd_ishod.id) " +
				"WHERE (c_rd_novor.npasp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
                throw new PatientNotFoundException();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getMotherId): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения количества новорождённых в многоплодных родах
	 * @param childbirthId Идентификатор родов
	 * @return Количество новорождённых
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws ChildbirthNotFoundException роды не найдены
	 * @author Балабаев Никита Дмитриевич
	 */
	private int getChildCountInChildbirth(final int childbirthId)
			throws KmiacServerException, ChildbirthNotFoundException {
		final String Query = "SELECT MAX(nreb) " +
				"FROM c_rd_novor " +
				"WHERE (nrod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childbirthId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
                throw new ChildbirthNotFoundException();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildCountInChildbirth): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения срока первой явки матери к врачу
	 * @param nrod Идентификатор родов
	 * @return Срок первой явки (количество недель беременности),
	 * либо <code>-1</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private int getFirstConsultWeekNum(final int nrod)
			throws KmiacServerException {
		final String Query = "SELECT p_rd_sl.yavka1 " +
				"FROM c_rd_ishod " +
				"LEFT JOIN p_rd_sl ON (p_rd_sl.id = c_rd_ishod.id_berem) " +
				"WHERE (c_rd_ishod.id = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, nrod)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
        		return -1;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getFirstConsultWeekNum): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения списка кодов занимаемых специалистом должностей
	 * @param doctorId Идентификатор специалиста
	 * @return Массив кодов занимаемых должностей,
	 * либо <code>null</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private ArrayList<Integer> getDoctorCod_sp(final int doctorId)
			throws KmiacServerException {
		final String Query = "SELECT n_s00.cod_sp " +
				"FROM s_vrach " +
				"JOIN s_mrab ON (s_mrab.pcod = s_vrach.pcod) " +
				"JOIN n_s00 ON (n_s00.pcod = s_mrab.cdol) " +
				"WHERE (s_vrach.pcod = ?) AND (s_mrab.datau IS NULL);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, doctorId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next()) {
        		ArrayList<Integer> arrCodes = new ArrayList<Integer>();
        		do {
        			arrCodes.add(rs.getInt(1));
        		}while (rs.next());
        		return arrCodes;
        	} else
        		return null;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getDoctorCod_sp): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	/**
	 * Функция получения идентификатора специалиста, принимавшего роды
	 * @param childbirthId Идентификатор родов
	 * @return Идентификатор специалиста,
	 * либо <code>-1</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private int getDoctorWhoGetChildId(final int childBirthId)
			throws KmiacServerException {
		final String Query = "SELECT prinyl " +
				"FROM c_rd_ishod " +
				"WHERE (id = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childBirthId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
        		return -1;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getDoctorWhoGetChildId): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	/**
	 * Функция получения информации о месте рождения
	 * @param cityId Идентификатор места рождения
	 * @return Информация о месте рождения (область, нас. пункт и тип нас. пункта)
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private TBirthPlace getChildBirthCityInfo(final int cityId)
			throws KmiacServerException {
		final String Query = "SELECT n_l02.name AS region, n_l00.name AS city, n_l00.vid_np AS type " +
				"FROM n_l00 " +
				"LEFT JOIN n_l02 ON (n_l02.c_ffomc = n_l00.c_ffomc) " +
				"WHERE (n_l00.pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, cityId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rsmBirthPlace.map(rs);
        	else
        		throw new KmiacServerException();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildBirthCityInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения информации о ЛПУ
	 * @param clpu Код ЛПУ
	 * @return Информация о ЛПУ (наименование, адрес, ОКПО и имя руководителя)
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private TInfoLPU getLPU_Info(final int clpu)
			throws KmiacServerException {
		final String Query = "SELECT name, adres, kodokpo AS okpo, zaved " +
				"FROM n_m00 " +
				"WHERE (pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, clpu)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rsmInfoLPU.map(rs);
        	else
        		throw new KmiacServerException();
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getLPU_Info): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения информации о специалисте и заданной должности
	 * @param pcod Код специалиста
	 * @param dolj Код должности
	 * @return Вектор строк, состоящий из двух элементов - [полное имя специалиста, название должности],
	 * либо <code>null</code> (в случае если специалист или должность не найдены)
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	private Vector<String> getDoctorInfo(final int pcod, final String dolj)
			throws KmiacServerException {
		final String Query = "SELECT (v.fam || ' ' || v.im || ' ' || v.ot) AS name, " +
				"n_s00.name AS dolj " +
	    		"FROM s_vrach v " +
	    		"CROSS JOIN n_s00 " +
	    		"WHERE (v.pcod = ?) AND (n_s00.pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, pcod, dolj)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next()) {
        		Vector<String> vecToReturn = new Vector<String>(2);
        		vecToReturn.add(rs.getString(1));
        		vecToReturn.add(rs.getString(2));
        		return vecToReturn;
        	} else
        		return null;
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getDoctorInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	@Override
	public List<IntegerClassifier> getChildBirths(final long BirthDate)
			throws KmiacServerException {
		final String SQLQuery = "SELECT a.id AS pcod, " +
				"'Мама - ' || b.fam || ' ' || b.im || ' ' || b.ot AS name " +
				"FROM c_rd_ishod a " +
				"JOIN patient b ON (a.npasp = b.npasp) " +
				"WHERE (a.daterod = ?);";
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
	    		SQLQuery, new Date(BirthDate))) {
	        return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildBirths): ", e);
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
	}

	@Override
	public void addChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        //Поле "Дата записи" (datazap) не присутствует в списке параметров 
        //из-за наличия соответствующего правила (rule) на СУБД:
        final String sqlQuery = "INSERT INTO c_rd_novor " +
        		"(npasp, nrod, timeon, kolchild, nreb, massa, rost, " +
        		"apgar1, apgar5, krit1, krit2, krit3, krit4, mert, donosh) " +
        		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
                sme.setCommit();
            } else
                throw new PatientNotFoundException();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (addChildInfo): ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TRd_Novor getChildInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_novor " +
		        "WHERE (npasp = ?);", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdNovor.map(acrs.getResultSet());
            } else
                throw new PatientNotFoundException();
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void updateChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0};
        final String sqlQuery = "UPDATE c_rd_novor " +
        						"SET timeon = ?, kolchild = ?, nreb = ?, massa = ?, rost = ?, " +
        						"apgar1 = ?, apgar5 = ?, " +
        						"krit1 = ?, krit2 = ?, krit3 = ?, krit4 = ?, " +
        						"mert = ?, donosh = ? " +
        						"WHERE npasp = ?;";
		try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
				sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
				sme.setCommit();
            } else
                throw new PatientNotFoundException();
		} catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (updateChildInfo): ", e);
            throw new KmiacServerException();
		}
	}

	@Override
	public int addChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        final String sqlQuery = "INSERT INTO c_rd_svid_rojd " +
        						"(npasp, famreb, m_rojd, zan, r_proiz, svid_write, cdol_write, " +
        						"clpu, svid_give, cdol_give, dateoff) " +
        						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, true, ChildDocument, CHILDBIRTH_DOC_TYPES, indexes);
                int ndoc = sme.getGeneratedKeys().getInt("ndoc");
                sme.setCommit();
                return ndoc;
            } else
            	throw new PatientNotFoundException();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (addChildDocument): ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TRd_Svid_Rojd getChildDocument(final int npasp)
			throws KmiacServerException, ChildDocNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid_rojd " +
		        "WHERE (npasp = ?);", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvidRojd.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getChildDocument): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void updateChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, ChildDocNotFoundException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0};
        //Поле ndoc изменять нельзя (в списке параметров не присутствует):
        final String sqlQuery = "UPDATE c_rd_svid_rojd " +
        						"SET famreb = ?, m_rojd = ?, zan = ?, r_proiz = ?, " +
        						"svid_write = ?, cdol_write = ?, clpu = ?, " +
        						"svid_give = ?, cdol_give = ?, dateoff = ? " +
        						"WHERE (npasp = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildDocExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, ChildDocument, CHILDBIRTH_DOC_TYPES, indexes);
                sme.setCommit();
            } else
                throw new ChildDocNotFoundException();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (updateChildDocument): ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public TPatientCommonInfo getPatientCommonInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
        String sqlQuery = "SELECT p.npasp, p.fam || ' ' || p.im || ' ' || p.ot as full_name, p.datar, "
        		+ "n_z30.name as pol, n_am0.name as jitel, p.adp_obl, p.adp_gorod, p.adp_ul, "
        		+ "p.adp_dom, p.adp_korp, p.adp_kv, p.obraz, p.status "
                + "FROM patient p "
                + "LEFT JOIN n_z30 ON (n_z30.pcod = p.pol) "
                + "LEFT JOIN n_am0 ON (n_am0.pcod = p.jitel) "
                + "WHERE (p.npasp = ?);";
        ResultSet rs = null;
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmCommonPatient.map(rs);
            } else {
                log.log(Level.INFO,
                		"PatientNotFoundException (getPatientCommonInfo), patientId = " + npasp);
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getPatientCommonInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Получение строки в заданном регистре (только первый символ заглавный)
	 * @param sourceStr Строка для преобразования
	 * @return Возвращает преобразованную строку, либо
	 * <code>null</code> в случае пустой строки
	 * @author Балабаев Никита Дмитриевич
	 */
	private String setNormalNameReg(final String sourceStr) {
		if (sourceStr == null)
			return null;
		if (sourceStr.length() == 0)
			return sourceStr;
		if (sourceStr.length() == 1)
			return sourceStr.toUpperCase();
		return (sourceStr.substring(0, 1).toUpperCase()).concat(sourceStr.substring(1).toLowerCase());
	}
	
	/**
	 * Получение из строки содержащихся в ней слов в установленном регистре
	 * (только первый символ каждого слова заглавный)
	 * @param sourceStr Исходная строка
	 * @return Возвращает слова в виде массива строк, либо
	 * <code>null</code> в случае пустой строки
	 * @author Балабаев Никита Дмитриевич
	 */
	private String[] getNormalRegWords(final String sourceStr) {
		if ((sourceStr == null) || (sourceStr.length() == 0))
			return null;
		String[] retArr = sourceStr.split(" ");
        for(int i = 0; i < retArr.length; i++)
        	retArr[i] = setNormalNameReg(retArr[i]);
        return retArr;
	}

	/**
	 * Получение из массива строк одной строки с заданным разделителем
	 * @param sourceStrArr Исходный массив строк
	 * @param sepStr Разделитель
	 * @return Возвращает полученную строку
	 * @author Балабаев Никита Дмитриевич
	 */
	private String getConcatStrFromAray(final String[] sourceStrArr, final String sepStr) {
		if ((sourceStrArr == null) || (sourceStrArr.length == 0))
			return "";
		String retStr = "";
        for(int i = 0; i < sourceStrArr.length - 1; i++)
        	retStr += sourceStrArr[i] + sepStr;
        retStr += sourceStrArr[sourceStrArr.length - 1];
        return retStr;
	}

	@Override
	public String printChildBirthDocument(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException,
			ChildbirthNotFoundException, PatientNotFoundException {
        final String pathToReturn;
        final String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
        						"июля", "августа", "сентября", "октября", "ноября", "декабря"};
        if (isChildDocUnique(ndoc))	//Свидетельство с таким номером не существует
        	throw new ChildDocNotFoundException();
        //Загрузка данных:
        TRd_Svid_Rojd childDoc = getChildDocumentByDoc(ndoc);
        TRd_Novor childBirthInfo = getChildInfo(childDoc.getNpasp());
        TPatientCommonInfo childInfo = getPatientCommonInfo(childDoc.getNpasp());
        TPatientCommonInfo motherInfo = getPatientCommonInfo(getMotherId(childDoc.getNpasp()));
        int iFirstConsult = getFirstConsultWeekNum(childBirthInfo.getNrod());
        int iChildCount = getChildCountInChildbirth(childBirthInfo.getNrod());
        ArrayList<Integer> arrCod_sp = getDoctorCod_sp(getDoctorWhoGetChildId(childBirthInfo.getNrod()));
        TBirthPlace birthPlace = getChildBirthCityInfo(childDoc.getM_rojd());
        TInfoLPU infoLPU = getLPU_Info(childDoc.getClpu());
        Vector<String> doctorWriteDoc = getDoctorInfo(childDoc.getSvid_write(), childDoc.getCdol_write());
        Vector<String> doctorGiveDoc = getDoctorInfo(childDoc.getSvid_give(), childDoc.getCdol_give());
        //Создание документа:
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			pathToReturn = File.createTempFile("svid_rojd_", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().getAbsolutePath()
                    + ServerHospital.childBirthDocPath);
            //Номер свидетельства:
            String childBirthNumber = String.format("%6d", ndoc);
            childBirthNumber = childBirthNumber.replaceAll(" ", "0");
            //Форматы вывода даты:
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd"), sdfMonth = new SimpleDateFormat("MMMMMMM"),
    			sdfMonthShort = new SimpleDateFormat("MM"), sdfYear = new SimpleDateFormat("yyyy");
            GregorianCalendar dateOff = new GregorianCalendar();
            dateOff.setTimeInMillis(childDoc.getDateoff());	//Дата выдачи мед. свидетельства
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
            //Роды произошли:
            String birthHappen[] = new String[] {"", "", "", "", "", "", "", ""};
            birthHappen[2*(childDoc.getR_proiz() - 1)] = "<u>";
            birthHappen[2*childDoc.getR_proiz() - 1] = "</u>";
            //Место рождения:
            int iCityType = birthPlace.getType();
            String birthPlaceRegion = birthPlace.getRegion();
            String birthPlaceTown = (iCityType > 0) ? birthPlace.getCity() : "";
            //Местность рождения:
            String cityChild1 = "", cityChild2 = "";
            String countryChild1 = "", countryChild2 = "";
            if ((iCityType == 1) || (iCityType == 2)) {
            	cityChild1 = "<u>";
            	cityChild2 = "</u>";
            } else {
            	countryChild1 = "<u>";
            	countryChild2 = "</u>";
            }
            //Пол новорождённого:
            String boy1 = "", boy2 = "";
            String girl1 = "", girl2 = "";
            if (childInfo.isSetPol()) {
            	String childGenderLower = childInfo.getPol().toLowerCase();
            	if (childGenderLower.equals("мужской"))
            	{
            		boy1 = "<u>";
            		boy2 = "</u>";
            	}
            	else
            		if (childGenderLower.equals("женский"))
                	{
            			girl1 = "<u>";
            			girl2 = "</u>";
                	}
            }
            //Полное имя матери:
            String[] motherNameArr = getNormalRegWords(motherInfo.getFull_name());
            final String motherMiddleName = motherNameArr[1].concat(" ".concat(motherNameArr[2]));
            //Время рождения:
            final String childBirthTime = (childBirthInfo.isSetTimeon()) ? childBirthInfo.getTimeon() : "";
            String childBirthHour = "", childBirthMinute = "";
            if (childBirthInfo.isSetTimeon()) {
	            childBirthHour = childBirthTime.substring(0, 2);
	            childBirthMinute = childBirthTime.substring(3, 5);
            }
            //Образование матери:
            int iEduc = (motherInfo.isSetObraz()) ? motherInfo.getObraz() : 0;
            String[] motherEduc = new String[] {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            if ((iEduc > 0) && (iEduc < 10)) {
	            motherEduc[2*(iEduc - 1)] = "<u>";
	            motherEduc[2*iEduc - 1] = "</u>";
            } else {	//Неизвестный код образования - ставим "Неизвестно":
	            motherEduc[16] = "<u>";
	            motherEduc[17] = "</u>";
            }
            //Семейное положение матери:
            int iStatus = motherInfo.isSetStatus() ? motherInfo.getStatus() : 0;
            String[] motherStatus = new String[] {"", "", "", "", "", ""};
            if ((iStatus > 0) && (iStatus < 5)) {
            	if (iStatus == 1) {	//Состоит в браке
	            	motherStatus[0] = "<u>";
	            	motherStatus[1] = "</u>";
            	} else {			//Не состоит в браке
	            	motherStatus[2] = "<u>";
	            	motherStatus[3] = "</u>";
            	}
            } else {	//Неизвестный код семейного положения - ставим "Неизвестно":
            	motherStatus[4] = "<u>";
            	motherStatus[5] = "</u>";
            }            
            //Занятость матери:
            String[] motherWork = new String[] {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            if ((childDoc.getZan() > 0) && (childDoc.getZan() < 11)) {
            	motherWork[2*(childDoc.getZan() - 1)] = "<u>";
            	motherWork[2*childDoc.getZan() - 1] = "</u>";
            } else {	//Неизвестный код занятости - ставим "Прочие":
            	motherWork[18] = "<u>";
            	motherWork[19] = "</u>";
            }
            //Срок первой явки к врачу:
            String firstConsult = "  ";
            if (iFirstConsult >= 0)
            	firstConsult = String.format("%2d", iFirstConsult);
            //Который по счёту:
            String nChildren = "  ";
            if (childBirthInfo.isSetKolchild())
            	nChildren = String.format("%2d", childBirthInfo.getKolchild());
            //Вес:
            String weight = "    ";
            if (childBirthInfo.isSetMassa())
            	weight = String.format("%4d", childBirthInfo.getMassa());
            //Рост:
            String height = "  ";
            if (childBirthInfo.isSetRost())
            	height = String.format("%2d", childBirthInfo.getRost());
            //Одноплодные\многоплодные роды:
            String only = "", nreb = "", nreb_all = "";
            if (childBirthInfo.isSetNreb())
            {
            	if (childBirthInfo.getNreb() == 0)
            		only = "V";
            	else {
            		nreb = String.format("%d", childBirthInfo.getNreb());
            		nreb_all = String.format("%d", iChildCount);
            	}
            }
    		//Лицо, принимавшее роды:
            boolean isKnownDoctor = false;
            String[] whoGetChild = new String[] {"", "", "", "", "", ""};
            if (arrCod_sp != null)
	        	for (int curCode : arrCod_sp) {
	        		//Акушер-гинеколог:
					if (curCode == 1) {
						whoGetChild[0] = "<u>";
						whoGetChild[1] = "</u>";
						isKnownDoctor = true;
						break;
					}
					//Фельдшер:
					if (curCode == 167) {
						whoGetChild[2] = "<u>";
						whoGetChild[3] = "</u>";
						isKnownDoctor = true;
						break;
					}
				}
            if (!isKnownDoctor) {
            	whoGetChild[4] = "<u>";
            	whoGetChild[5] = "</u>";
            }
            //Место регистрации матери:
            String[] motherRegPlace = new String[] {
        		(motherInfo.isSetAdp_obl()) ? setNormalNameReg(motherInfo.getAdp_obl()) : "",
        		"",	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		(motherInfo.isSetAdp_gorod()) ? setNormalNameReg(motherInfo.getAdp_gorod()) : "",
				(motherInfo.isSetAdp_ul()) ? setNormalNameReg(motherInfo.getAdp_ul()) : "",
				(motherInfo.isSetAdp_dom()) ? motherInfo.getAdp_dom() : "",
				(motherInfo.isSetAdp_korp()) ? " ".concat(motherInfo.getAdp_korp()) : "",
				(motherInfo.isSetAdp_kv()) ? motherInfo.getAdp_kv() : "",
            };
            //Запись данных в шаблон:
            htmTemplate.replaceLabels(false,
            	//Серия и номер свидетельства:
        		ServerHospital.childBirthDocSeries, childBirthNumber,
        		//Дата выдачи:
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		//Ребенок родился:
        		sdfDay.format(childInfo.getDatar()), sdfMonth.format(childInfo.getDatar()),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		//Фамилия, имя, отчество матери:
        		motherNameArr[0].concat(" ".concat(motherMiddleName)),
        		//Дата рождения матери:
        		sdfDay.format(motherInfo.getDatar()), sdfMonth.format(motherInfo.getDatar()),
        		sdfYear.format(motherInfo.getDatar()),
        		//Место регистрации матери:
        		motherRegPlace[0],
        		motherRegPlace[1],	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		motherRegPlace[2],
        		motherRegPlace[3],
        		motherRegPlace[4].concat(motherRegPlace[5]),
        		motherRegPlace[6],
				//Местность регистрации:
        		city1, city2, country1, country2,
        		//Пол ребёнка:
        		boy1, boy2, girl1, girl2,
        		//Информация об организации:
        		"",	//КОД ФОРМЫ ПО ОКУД
        		(infoLPU.isSetName()) ? infoLPU.getName() : "",
        		(infoLPU.isSetAdres()) ? infoLPU.getAdres() : "",
        		(infoLPU.isSetOkpo() && (infoLPU.getOkpo() > 0)) ? String.format("%d", infoLPU.getOkpo()) : "",
            	//Серия и номер свидетельства:
        		ServerHospital.childBirthDocSeries, childBirthNumber,
        		//Дата выдачи:
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		//Ребенок родился:
        		sdfDay.format(childInfo.getDatar()),
        		sdfMonth.format(childInfo.getDatar()),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		//Фамилия, имя, отчество матери:
        		motherNameArr[0], motherMiddleName,
        		//Фамилия ребёнка:
        		setNormalNameReg(childDoc.getFamreb()),
        		//Дата рождения матери:
        		sdfDay.format(motherInfo.getDatar()).substring(0, 1),
        		sdfDay.format(motherInfo.getDatar()).substring(1, 2),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(0, 1),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(0, 1),
        		sdfYear.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(2, 3),
        		sdfYear.format(motherInfo.getDatar()).substring(3, 4),
        		//Место регистрации матери:
        		motherRegPlace[0],
        		motherRegPlace[1],	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		motherRegPlace[2],
        		motherRegPlace[3],
        		motherRegPlace[4].concat(motherRegPlace[5]),
        		motherRegPlace[6],
        		//Местность регистрации:
        		city1, city2, country1, country2,
        		//Семейное положение матери:
        		motherStatus[0], motherStatus[1],
        		motherStatus[2], motherStatus[3],
        		motherStatus[4], motherStatus[5],
        		//Место рождения:
        		setNormalNameReg(birthPlaceRegion),
        		"",	//РАЙОН РОЖДЕНИЯ
        		setNormalNameReg(birthPlaceTown),
        		//Местность рождения:
    			cityChild1, cityChild2,
    			countryChild1, countryChild2,
    			//Роды произошли:
    			birthHappen[0], birthHappen[1],
    			birthHappen[2], birthHappen[3],
    			birthHappen[4], birthHappen[5],
    			birthHappen[6], birthHappen[7],
        		//Пол ребёнка:
        		boy1, boy2, girl1, girl2,
    			//Роды произошли:
    			birthHappen[0], birthHappen[1],
    			birthHappen[2], birthHappen[3],
    			birthHappen[4], birthHappen[5],
    			birthHappen[6], birthHappen[7],
        		//Должность и имя врача, выдавшего свидетельство:
        		(doctorGiveDoc != null) ? setNormalNameReg(doctorGiveDoc.get(1)) : "",
        		(doctorGiveDoc != null) ? getConcatStrFromAray(getNormalRegWords(doctorGiveDoc.get(0)), " ") : "",
        		//Образование матери:
        		motherEduc[0], motherEduc[1],
        		motherEduc[2], motherEduc[3],
        		motherEduc[4], motherEduc[5],
        		motherEduc[6], motherEduc[7],
        		motherEduc[8], motherEduc[9],
        		motherEduc[10], motherEduc[11],
        		motherEduc[12], motherEduc[13],
        		motherEduc[14], motherEduc[15],
        		motherEduc[16], motherEduc[17],
        		//Занятость матери:
        		motherWork[0], motherWork[1],
        		motherWork[0], motherWork[1],
        		motherWork[2], motherWork[3],
        		motherWork[4], motherWork[5],
        		motherWork[6], motherWork[7],
        		motherWork[8], motherWork[9],
        		motherWork[10], motherWork[11],
        		motherWork[12], motherWork[13],
        		motherWork[14], motherWork[15],
        		motherWork[14], motherWork[15],
        		motherWork[16], motherWork[17],
        		motherWork[18], motherWork[19],
        		//Срок первой явки к врачу:
        		firstConsult.substring(0, 1), firstConsult.substring(1, 2),
        		//Которым по счету ребенок был рожден у матери:
        		nChildren.substring(0, 1), nChildren.substring(1, 2),
        		//Масса тела при рождении:
        		weight.substring(0, 1), weight.substring(1, 2),
        		weight.substring(2, 3), weight.substring(3, 4),
        		//Длина  тела при рождении:
        		height.substring(0, 1), height.substring(1, 2),
        		only, nreb, nreb_all,
        		//Лицо, принимавшее роды:
        		whoGetChild[0], whoGetChild[1],
        		whoGetChild[2], whoGetChild[3],
        		whoGetChild[4], whoGetChild[5],
        		//Должность и имя врача, заполнившего свидетельство:
        		(doctorWriteDoc != null) ? setNormalNameReg(doctorWriteDoc.get(1)) : "",
        		(doctorWriteDoc != null) ? getConcatStrFromAray(getNormalRegWords(doctorWriteDoc.get(0)), " ") : "",
        		//Имя руководителя организации:
        		(infoLPU.isSetZaved()) ? infoLPU.getZaved() : ""
        		);
            osw.write(htmTemplate.getTemplateText());
            return pathToReturn;
        } catch (Exception e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public String printChildBirthBlankDocument()
			throws KmiacServerException, TException {
        final String blankDocPath;
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			blankDocPath = File.createTempFile("svid_blank_", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().
            		getAbsolutePath() + ServerHospital.childBirthDocPath);
            htmTemplate.replaceLabel("~seria", ServerHospital.childBirthDocSeries);
            htmTemplate.replaceLabel("~seria", ServerHospital.childBirthDocSeries);
            htmTemplate.replaceLabel("~ndoc", "______");
            htmTemplate.replaceLabel("~ndoc", "______");
            htmTemplate.replaceLabels(true);
	        osw.write(htmTemplate.getTemplateText());
	        return blankDocPath;
	    } catch (Exception e) {
            log.log(Level.ERROR, "Exception: ", e);
	        throw new KmiacServerException();
	    }
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

	@Override
	public List<TMedication> getMedications(final int idGosp)
			throws KmiacServerException {
        String sqlQuery = "SELECT n_med.name as name, c_lek.* " +
                "FROM c_lek " +
                "INNER JOIN n_med ON (c_lek.klek = n_med.pcod) " +
                "WHERE (c_lek.id_gosp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            return rsmMedication.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception (getMedications): ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public void updateMedication(final TMedication med) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_lek " +
        						"SET doza = ?, spriem = ?, pereod = ?, datao = ?, " +
        						"vracho = ? " +
        						"WHERE (nlek = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, med.getDoza(), med.getSpriem(), med.getPereod(),
            		(med.isSetDatao()) ? new Date(med.getDatao()) : null,
            		(med.isSetVracho()) ? med.getVracho() : null,
            		med.getNlek());
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (updateMedication): ", e);
            throw new KmiacServerException();
        }
		
	}

	@Override
	public void deleteMedication(final int nlek) throws KmiacServerException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM c_lek WHERE (nlek = ?);", false, nlek);
			sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQLException | InterruptedException (deleteMedication): ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public List<TDiagnostic> getDiagnostics(int idGosp) throws KmiacServerException {
        String sqlQuery = 
		"SELECT DISTINCT p_isl_ld.nisl, n_ldi.pcod AS cpok, n_ldi.name_n AS cpok_name, " +
				"p_rez_l.zpok AS result, p_isl_ld.datav, '' AS op_name, '' AS rez_name " +
		"FROM p_isl_ld " +
		"JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) " +
		"JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) " +
		"WHERE (p_isl_ld.id_gosp = ?) " +
		"UNION " +
		"SELECT DISTINCT p_isl_ld.nisl, n_ldi.pcod AS cpok, n_ldi.name_n AS cpok_name, " +
			"n_arez.name AS result, p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name " +
		"FROM p_isl_ld " +
		"JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) " +
		"JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) " +
		"LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) " +
		"WHERE (p_isl_ld.id_gosp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp, idGosp)) {
            return rsmDiagnostic.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception (getDiagnostics): ", e);
            throw new KmiacServerException();
        }
	}

//	public void addRdIshod(int npasp, int ngosp) throws KmiacServerException,
//			TException {
//		// TODO Auto-generated method stub
//		
//	}

}

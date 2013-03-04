package ru.nkz.ivcgzo.serverRegPatient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftRegPatient.Address;
import ru.nkz.ivcgzo.thriftRegPatient.Agent;
import ru.nkz.ivcgzo.thriftRegPatient.AgentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.AllGosp;
import ru.nkz.ivcgzo.thriftRegPatient.AllLgota;
import ru.nkz.ivcgzo.thriftRegPatient.Anam;
import ru.nkz.ivcgzo.thriftRegPatient.Gosp;
import ru.nkz.ivcgzo.thriftRegPatient.GospAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.GospNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Info;
import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Lgota;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Nambk;
import ru.nkz.ivcgzo.thriftRegPatient.NambkAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.NambkNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.OgrnNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientGospYesOrNoNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.PokazNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Polis;
import ru.nkz.ivcgzo.thriftRegPatient.RegionLiveNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Shablon;
import ru.nkz.ivcgzo.thriftRegPatient.ShablonText;
import ru.nkz.ivcgzo.thriftRegPatient.Sign;
import ru.nkz.ivcgzo.thriftRegPatient.SignNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.SmocodNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.SmorfNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.TerLiveNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient.Iface;
import ru.nkz.ivcgzo.thriftRegPatient.TipPodrNotFoundException;

/**
 * Класс, имплементирующий трифтовый интерфейс для связи с клиентом.
 * @author Avdeev Alexander
 */
public class ServerRegPatient extends Server implements Iface {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private static Logger log = Logger.getLogger(ServerRegPatient.class.getName());

    private TServer thrServ;

//////////////////////////////// Mappers /////////////////////////////////

    private TResultSetMapper<PatientBrief, PatientBrief._Fields> rsmPatientBrief;
    private TResultSetMapper<Address, Address._Fields> rsmAdpAdress;
    private TResultSetMapper<Address, Address._Fields> rsmAdmAdress;
    private TResultSetMapper<PatientFullInfo, PatientFullInfo._Fields> rsmPatientFullInfo;
    private TResultSetMapper<Polis, Polis._Fields> rsmPolisOms;
    private TResultSetMapper<Polis, Polis._Fields> rsmPolisDms;
    private TResultSetMapper<Nambk, Nambk._Fields> rsmNambk;
    private TResultSetMapper<Agent, Agent._Fields> rsmAgent;
    private TResultSetMapper<AllLgota, AllLgota._Fields> rsmAllLgota;
    private TResultSetMapper<Lgota, Lgota._Fields> rsmLgota;
    private TResultSetMapper<Kontingent, Kontingent._Fields> rsmKontingent;
    private TResultSetMapper<Sign, Sign._Fields> rsmSign;
    private TResultSetMapper<AllGosp, AllGosp._Fields> rsmAllGosp;
    private TResultSetMapper<Gosp, Gosp._Fields> rsmGosp;
    private QueryGenerator<PatientBrief> qgPatientBrief;
    private TResultSetMapper<Anam, Anam._Fields> rsmAnam;
	private final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	@SuppressWarnings("unused")
	private final Class<?>[] intClasTypes; 
	private final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
	@SuppressWarnings("unused")
	private final Class<?>[] strClasTypes; 

//////////////////////////////// Type Arrays /////////////////////////////////

    private static final Class<?>[] PATIENT_BRIEF_TYPES = new Class<?>[] {
    //  npasp          fam           im            ot
        Integer.class, String.class, String.class, String.class,
    //  datar       spolis        npolis        adpRegion
        Date.class, String.class, String.class, String.class,
    //  adpCity       adpStreet     adpHouse      adpFlat
        String.class, String.class, String.class, String.class,
    //  admRegion     admCity       admStreet     admHouse
        String.class, String.class, String.class, String.class,
    //  admFlat
        String.class
    };
    private static final Class<?>[] PATIENT_FULL_TYPES = new Class<?>[] {
    //  npasp          fam           im            ot
        Integer.class, String.class, String.class, String.class,
    //  datar       pol          jitel        sgrp
        Date.class, Integer.class, Integer.class, Integer.class,
    //  mrab          name_mr       ncex           cpol_pr
        Integer.class, String.class, Integer.class, Integer.class,
    //  terp           tdoc           docser        docnum
        Integer.class, Integer.class, String.class, String.class,
    //  datadoc     odoc          snils         dataz
        Date.class, String.class, String.class, Date.class,
    //  prof          tel           dsv         prizn
        String.class, String.class, Date.class, Integer.class,
    //  ter_liv        region_liv     birthplace    ogrn_smo      obraz          status
        Integer.class, Integer.class, String.class, String.class, Integer.class, Integer.class
    };
    private static final Class<?>[] KONTINGENT_TYPES = new Class<?>[] {
    //  id             npasp          kateg          datal
        Integer.class, Integer.class, Integer.class, Date.class,
    //  name
        String.class
    };
    private static final Class<?>[] AGENT_TYPES = new Class<?>[] {
    //  npasp          fam           im            ot
        Integer.class, String.class, String.class, String.class,
    //  datar       pol             name_str      ogrn_str
        Date.class, Integer.class, String.class, String.class,
    //  vpolis         spolis        npolis        birthplace
        Integer.class, String.class, String.class, String.class,
    //  tdoc           docser        docnum     
        Integer.class, String.class, String.class
    };
    private static final Class<?>[] SIGN_TYPES = new Class<?>[] {
    //  npasp          grup          ph            allerg
        Integer.class, String.class, String.class, String.class,
    //  farmkol       vitae         vred
        String.class, String.class, String.class
    };
    private static final Class<?>[] GOSP_TYPES = new Class<?>[] {
    //  id             ngosp          npasp          nist
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  datap       vremp       pl_extr       naprav         n_org
        Date.class, Time.class, Integer.class, String.class, Integer.class,
    //  cotd           sv_time        sv_day         ntalon
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  vidtr          pr_out         alkg           meesr
        Integer.class, Integer.class, Integer.class, Boolean.class,
    //  vid_tran       diag_n        diag_p        named_n       named_p
        Integer.class, String.class, String.class, String.class, String.class,
    //  nal_z          nal_p          t0c           ad            smp_data
        Boolean.class, Boolean.class, String.class, String.class, Date.class,
    //  smp_time    smp_num        cotd_p         datagos     vremgos
        Time.class, Integer.class, Integer.class, Date.class, Time.class,
    //  cuser          dataosm     vremosm     dataz       jalob
        Integer.class, Date.class, Time.class, Date.class, String.class,
    //  vid_st         pr_ber			srok_ber	komm
        Integer.class, Boolean.class, String.class, String.class
    };
    private static final Class<?>[] NAMBK_TYPES = new Class<?>[] {
    //  npasp          nambk         nuch           cpol
        Integer.class, String.class, Integer.class, Integer.class,
    //  datapr      dataot      ishod
        Date.class, Date.class, Integer.class
    };
    // Отражение таблицы p_kov (кроме поля name - это поле классификатора n_lkn)
    private static final Class<?>[] LGOTA_TYPES = new Class<?>[] {
    //  id             npasp          lgot          datal
        Integer.class, Integer.class, Integer.class, Date.class,
    //  name          gri            sin            pp             drg
        String.class, Integer.class, Integer.class, Integer.class, Date.class,
    //  dot         obo            ndoc
        Date.class, Integer.class, String.class
    };
    private static final Class<?>[] ANAM_TYPES = new Class<?>[] {
    //  npasp          datap		numstr			vybor		 comment		name			prof_anz
        Integer.class, Date.class, Integer.class, Boolean.class, String.class, String.class, String.class
    };

//////////////////////////// Field Name Arrays ////////////////////////////

    private static final String[] POLIS_OMS_FIELD_NAMES = {
        "poms_strg", "poms_ser", "poms_nom", "poms_tdoc"
    };
    private static final String[] POLIS_DMS_FIELD_NAMES = {
        "pdms_strg", "pdms_ser", "pdms_nom"
    };
    private static final String[] ADP_ADDRESS_FIELD_NAMES = {
        "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_kv"
    };
    private static final String[] ADM_ADDRESS_FIELD_NAMES = {
        "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_kv"
    };
    private static final String[] PATIENT_BRIEF_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
    };
    private static final String[] PATIENT_FULL_INFO_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "pol", "jitel", "sgrp", "mrab", "name_mr",
        "ncex", "cpol_pr", "terp", "tdoc", "docser", "docnum",  "datadoc", "odoc",
        "snils", "dataz", "prof", "tel", "dsv", "prizn", "ter_liv", "region_liv",
        "birthplace", "ogrn_smo", "obraz", "status"
    };
    private static final String[] NAMBK_FIELD_NAMES = {
        "npasp", "nambk", "nuch", "cpol", "datapr", "dataot", "ishod"
    };
    private static final String[] AGENT_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "pol", "name_str", "ogrn_str",
        "vpolis", "spolis" , "npolis", "birthplace", "tdoc", "docser", "docnum"
    };
    private static final String[] KONTINGENT_FIELD_NAMES = {
        "id", "npasp", "kateg", "datal", "name"
    };
    private static final String[] SIGN_FIELD_NAMES = {
        "npasp", "grup", "ph", "allerg", "farmkol", "vitae", "vred"
    };
    private static final String[] ALL_GOSP_FIELD_NAMES = {
        "id", "ngosp", "npasp", "nist", "datap", "cotd", "diag_p", "named_p"
    };
    private static final String[] GOSP_FIELD_NAMES = {
        "id", "ngosp", "npasp", "nist", "datap", "vremp", "pl_extr", "naprav", "n_org",
        "cotd", "sv_time", "sv_day", "ntalon", "vidtr", "pr_out", "alkg", "meesr",
        "vid_tran", "diag_n", "diag_p", "named_n", "named_p", "nal_z", "nal_p", "t0c",
        "ad", "smp_data", "smp_time", "smp_num", "cotd_p", "datagos", "vremgos", "cuser",
        "dataosm", "vremosm", "dataz", "jalob", "vid_st", "pr_ber", "srok_ber", "komm"
    };
    private static final String[] LGOTA_FIELD_NAMES = {
        "id", "npasp", "lgot", "datal", "name", "gri", "sin", "pp", "drg", "dot", "obo", "ndoc"
    };
    private static final String[] ANAM_FIELD_NAMES = {
    	"npasp", "datap", "numstr", "vybor", "comment", "name", "prof_anz"
    };

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

    /**
     * Конструктор класса.
     * @param sse - Определяет методы для выполнения запросов
     *              на чтение данных и работы с подключениями
     * @param tse - Определяет методы для управления транзакциями
     */
    public ServerRegPatient(final ISqlSelectExecutor sse,
            final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmPatientBrief = new TResultSetMapper<>(PatientBrief.class,
                PATIENT_BRIEF_FIELD_NAMES);
        qgPatientBrief = new QueryGenerator<PatientBrief>(PatientBrief.class,
                PATIENT_BRIEF_FIELD_NAMES);
        rsmAdpAdress = new TResultSetMapper<>(Address.class, ADP_ADDRESS_FIELD_NAMES);
        rsmAdmAdress = new TResultSetMapper<>(Address.class, ADM_ADDRESS_FIELD_NAMES);
        rsmPatientFullInfo = new TResultSetMapper<>(PatientFullInfo.class,
                PATIENT_FULL_INFO_FIELD_NAMES);
        rsmPolisOms = new TResultSetMapper<>(Polis.class, POLIS_OMS_FIELD_NAMES);
        rsmPolisDms = new TResultSetMapper<>(Polis.class, POLIS_DMS_FIELD_NAMES);
        rsmNambk = new TResultSetMapper<>(Nambk.class, NAMBK_FIELD_NAMES);
        rsmAgent = new TResultSetMapper<>(Agent.class, AGENT_FIELD_NAMES);
        rsmKontingent = new TResultSetMapper<>(Kontingent.class, KONTINGENT_FIELD_NAMES);
        rsmSign = new TResultSetMapper<>(Sign.class, SIGN_FIELD_NAMES);
        rsmAllGosp = new TResultSetMapper<>(AllGosp.class, ALL_GOSP_FIELD_NAMES);
        rsmGosp = new TResultSetMapper<>(Gosp.class, GOSP_FIELD_NAMES);
        rsmAllLgota = new TResultSetMapper<>(AllLgota.class, LGOTA_FIELD_NAMES);
        rsmLgota = new TResultSetMapper<>(Lgota.class, LGOTA_FIELD_NAMES);
        rsmAnam = new TResultSetMapper<>(Anam.class, ANAM_FIELD_NAMES);
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
    }

////////////////////////////////////////////////////////////////////////
//                       Private Methods                              //
////////////////////////////////////////////////////////////////////////

///////////////////// Is Exist Methods /////////////////////////////////

    /**
     * Проверяет, существует ли в БД запись амбулаторной карты с такими данными
     * @param nambk - thrift-объект с информацией об амбулаторной карте
     * @return true - если амбулаторная карта с такими данными уже существует,
     * false - если не существует
     */
    private boolean isNambkExist(final Nambk nambk) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT npasp FROM p_nambk WHERE (npasp = ?) AND (cpol = ?)",
                nambk, NAMBK_TYPES, 0, 3)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД запись госпитализации пациента с такими данными
     * @param gosp - thrift-объект с информацией  госпитализации
     * @return true - если особая информация о пациенте с такими данными уже существует,
     * false - если не существует
     */
    private boolean isGospExist(final Gosp gosp) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT id FROM c_gosp WHERE (npasp = ?) and (ngosp = ?)",
                gosp, GOSP_TYPES, 0)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД запись госпитализации пациента с такими данными
     * @param idGosp - thrift-объект с информацией  госпитализации
     * @return true - если особая информация о пациенте с такими данными уже существует,
     * false - если не существует
     */
    private boolean isOtdExist(final int idGosp) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT id_gosp FROM c_otd WHERE id_gosp = ?",
                idGosp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД пациент с такими данными
     * @param patinfo - thrift-объект с информацией о пациенте
     * @return true - если пациент с такими данными уже существует,
     * false - если не существует
     */
    private boolean isPatientExist(final PatientFullInfo patinfo) throws SQLException {
        final int[] indexes = {1, 2, 3, 4};
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT npasp FROM patient WHERE (fam = ?) AND (im = ?) AND (ot = ?) "
                + "AND (datar = ?);",
                patinfo, PATIENT_FULL_TYPES, indexes)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД льгота пациента с такими данными
     * @param lgota - thrift-объект с информацией о льготе
     * @return true - если льгота с такими данными уже существует,
     * false - если не существует
     */
    private boolean isLgotaExist(final AllLgota lgota) throws SQLException {
        final int[] indexes = {1, 2};
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT id FROM p_kov WHERE (npasp = ?) AND (lgot = ?);",
                lgota, LGOTA_TYPES, indexes)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД категория пациента с такими данными
     * @param kontingent - thrift-объект с информацией о категории
     * @return true - если категория с такими данными уже существует,
     * false - если не существует
     */
    private boolean isKontingentExist(final Kontingent kontingent) throws SQLException {
        final int[] indexes = {1, 2};
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT id FROM p_konti WHERE (npasp = ?) AND (kateg = ?);",
                kontingent, KONTINGENT_TYPES, indexes)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД представитель пациента с такими данными
     * @param agent - thrift-объект с информацией о представителе пациента
     * @return true - если категория с такими данными уже существует,
     * false - если не существует
     */
    private boolean isAgentExist(final Agent agent) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT npasp FROM p_preds WHERE (npasp = ?)",
                agent, AGENT_TYPES, 0)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Проверяет, существует ли в БД особая информация о пациенте с такими данными
     * @param sign - thrift-объект с особой информацией о пациенте
     * @return true - если особая информация о пациенте с такими данными уже существует,
     * false - если не существует
     */
    private boolean isSignExist(final Sign sign) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT npasp FROM p_sign WHERE (npasp = ?)",
                sign, SIGN_TYPES, 0)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Возвращает null вместо 0, что помогает избегать записи в БД дефолтной даты '1970-01-01'
     * @param inDateMillisec - количество миллисекунд
     * @return Date - если количество миллисекунд больше 0,
     * null - если количество миллисекунд равно 0
     */
    private Date avoidDefaultSqlDateValue(final long inDateMillisec) {
        if (inDateMillisec == 0) {
            return null;
        }
        return new Date(inDateMillisec);
    }

///////////////////// Get Transcription Methods /////////////////////////////////

    /**
     * Находит в БД текстовое представление контингента по его id.
     * @param id - идентификатор контингента
     * @return String - текстовое описание контингента
     */
    private String getStringTranscriptionForKontingent(final int kontId) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT name FROM n_lkr WHERE (pcod = ?)", kontId)) {
            ResultSet rs = acrs.getResultSet();
            String tmpName = null;
            if (rs.next()) {
                tmpName = rs.getString("name");
            } else {
                tmpName = "описание контингента не найдено";
            }
            return tmpName;
        } catch (SQLException e) {
            return "описание контингента не найдено";
        }
    }

    /**
     * Находит в БД текстовое представление льготы по её id.
     * @param id - идентификатор льгота
     * @return String - текстовое описание вида льготы
     */
    private String getStringTranscriptionForLgota(final int lgotaId) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT name FROM n_lkn WHERE (pcod = ?)", lgotaId)) {
            ResultSet rs = acrs.getResultSet();
            String tmpName = null;
            if (rs.next()) {
                tmpName = rs.getString("name");
            } else {
                tmpName = "описание льготы не найдено";
            }
            return tmpName;
        } catch (SQLException e) {
            return "описание льготы не найдено";
        }
    }

/////////////////////////////////// Check OS Type /////////////////////////////

//    private boolean isWindows() {
//        String os = System.getProperty("os.name").toLowerCase();
//        //windows
//        return (os.indexOf("win") >= 0);
//    }
//
//    private boolean isUnix() {
//        String os = System.getProperty("os.name").toLowerCase();
//        //linux or unix
//        return ((os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0));
//    }

////////////////////////////// Other ///////////////////////////////////////////

//    private String setReportPath() {
//        if (isWindows()) {
//            return "C:\\Temp\\MedCardAmbPriem_t.htm";
//        } else if (isUnix()) {
//            return System.getProperty("user.home")
//                    + "/Work/muzdrav_reports/temp/MedCardAmbPriem_t.htm";
//        } else {
//            return "MedCardAmbPriem_t.htm";
//        }
//    }

////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

//////////////////////// Start/Stop Methods ////////////////////////////////////

    /**
     * Запускает сервер.
     */
    @Override
    public final void start() throws Exception {
        ThriftRegPatient.Processor<Iface> proc =
                new ThriftRegPatient.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.info("Start serverRegPatient");
        thrServ.serve();
    }

    /**
     * Останавливает сервер.
     */
    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
            log.info("Stop serverRegPatient");
        }
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
	
//////////////////////// Select Methods ////////////////////////////////////

    @Override
    public final List<PatientBrief> getAllPatientBrief(final PatientBrief patient)
            throws PatientNotFoundException, KmiacServerException {
        String  sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv "
                + "FROM patient";
        InputData inData = qgPatientBrief.genSelect(patient, sqlQuery);
        sqlQuery = inData.getQueryText() + " ORDER BY fam, im, ot";
        int[] indexes = inData.getIndexes();
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(sqlQuery, patient,
                PATIENT_BRIEF_TYPES, indexes)) {
            ResultSet rs = acrs.getResultSet();
            List<PatientBrief> patientsInfo = new ArrayList<PatientBrief>();
            if (rs.next()) {
                do {
                    PatientBrief curPatient = rsmPatientBrief.map(rs);
                    curPatient.setAdpAddress(new Address(rsmAdpAdress.map(rs)));
                    curPatient.setAdmAddress(new Address(rsmAdmAdress.map(rs)));
                    patientsInfo.add(curPatient);
                } while (rs.next());
                inData = null;
                return patientsInfo;
            } else {
                inData = null;
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final PatientFullInfo getPatientFullInfo(final int npasp)
            throws PatientNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT patient.npasp, patient.fam, patient.im, patient.ot, "
            + "patient.datar, patient.pol, patient.jitel, patient.poms_ser, "
            + "patient.poms_nom, patient.sgrp, patient.adp_obl, patient.adp_gorod, "
            + "patient.adp_ul, patient.adp_dom, patient.adp_korp, patient.adp_kv, "
            + "patient.adm_obl, patient.adm_gorod, patient.adm_ul, patient.adm_dom, "
            + "patient.adm_korp, patient.adm_kv, patient.mrab, patient.name_mr, "
            + "patient.ncex, patient.poms_strg, patient.poms_tdoc, "
            + "patient.poms_ndog, patient.pdms_strg, patient.pdms_ser, "
            + "patient.pdms_nom, patient.pdms_ndog, patient.cpol_pr, patient.terp, "
            + "patient.datapr, patient.tdoc, patient.docser, patient.docnum, "
            + "patient.datadoc, patient.odoc, patient.snils, patient.dataz, "
            + "patient.prof, tel, patient.dsv, patient.prizn, patient.ter_liv, "
            + "patient.region_liv, patient.birthplace, patient.ogrn_smo, patient.obraz, patient.status "
            + "FROM patient "
            + "WHERE patient.npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                PatientFullInfo patient = rsmPatientFullInfo.map(rs);
                patient.setAdpAddress(rsmAdpAdress.map(rs));
                patient.setAdmAddress(rsmAdmAdress.map(rs));
                patient.setPolis_oms(rsmPolisOms.map(rs));
                patient.setPolis_dms(rsmPolisDms.map(rs));
                return patient;
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Nambk getNambk(final int npasp, final int cpodr) throws NambkNotFoundException,
            KmiacServerException {
        String sqlQuery = "SELECT * FROM p_nambk WHERE npasp = ? AND cpol = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp,  cpodr)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                Nambk nambk = rsmNambk.map(rs);
                return nambk;
            } else {
                throw new NambkNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Agent getAgent(final int npasp)
            throws AgentNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT * FROM p_preds WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                Agent agent = rsmAgent.map(rs);
                return agent;
            } else {
                throw new AgentNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Kontingent> getKontingent(final int npasp)
            throws KontingentNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT id , npasp, kateg, datal, name FROM p_konti "
            + "INNER JOIN n_lkr ON p_konti.kateg = n_lkr.pcod WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            List<Kontingent> kontingent = rsmKontingent.mapToList(rs);
            if (kontingent.size() > 0) {
                return kontingent;
            } else {
                throw new KontingentNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Lgota getLgota(final int id)
            throws LgotaNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT id, npasp, lgot, datal, name, gri, sin, pp, drg, "
            + "dot, obo, ndoc FROM p_kov "
            + "INNER JOIN n_lkn ON p_kov.lgot = n_lkn.pcod WHERE id = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, id)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmLgota.map(rs);
            } else {
                throw new LgotaNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Sign getSign(final int npasp)
            throws SignNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT * FROM p_sign WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmSign.map(rs);
            } else {
                throw new SignNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<AllGosp> getAllGosp(final int npasp)
            throws GospNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT id, ngosp, npasp, nist, datap, cotd, "
                + "diag_p, named_p FROM c_gosp WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            List<AllGosp> allGosp = rsmAllGosp.mapToList(rs);
            if (allGosp.size() > 0) {
                return allGosp;
            } else {
                throw new GospNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Gosp getGosp(final int id) throws GospNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT * FROM c_gosp WHERE id = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, id)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmGosp.map(rs);
            } else {
                throw new GospNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final String getOgrn(final String smocod) throws OgrnNotFoundException,
            KmiacServerException {
        String sqlQuery = "SELECT ogrn FROM n_smorf WHERE smocod = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, smocod)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rs.getString("ogrn");
            } else {
                throw new OgrnNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int getRegionLive(final int pcod)
            throws RegionLiveNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT c_ffomc FROM n_l02 WHERE pcod = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rs.getInt("c_ffomc");
            } else {
                throw new RegionLiveNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int getTerLive(final int pcod)
            throws TerLiveNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT ter FROM n_l00 WHERE pcod = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rs.getInt("ter");
            } else {
                throw new TerLiveNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final String getSmocod(final String ogrn, final int pcod)
            throws SmocodNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT smocod FROM n_smorf WHERE ogrn = ? AND pcod = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, ogrn, pcod)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rs.getString("smocod");
            } else {
                throw new SmocodNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

//////////////////////// Add Methods ////////////////////////////////////

    @Override
    public final int addPatient(final PatientFullInfo patinfo)
            throws PatientAlreadyExistException, KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isPatientExist(patinfo)) {
                sme.execPrepared("INSERT INTO patient "
                    + "(fam, im, ot, datar, poms_ser, poms_nom, pol, jitel, sgrp, "
                    + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, adm_obl, "
                    + "adm_gorod, adm_ul, adm_dom, adm_kv, mrab, name_mr, "
                    + "ncex, poms_strg, poms_tdoc, pdms_strg, pdms_ser, pdms_nom, "
                    + "cpol_pr, terp, tdoc, docser, docnum, datadoc, odoc, snils, dataz, prof, tel, dsv,"
                    + "prizn, ter_liv, region_liv, birthplace, ogrn_smo, obraz, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?);", true,
                    patinfo.getFam(), patinfo.getIm(), patinfo.getOt(),
                    avoidDefaultSqlDateValue(patinfo.getDatar()),
                    patinfo.getPolis_oms().getSer(), patinfo.getPolis_oms().getNom(),
                    patinfo.getPol(), patinfo.getJitel(), patinfo.getSgrp(),
                    patinfo.getAdpAddress().getRegion(), patinfo.getAdpAddress().getCity(),
                    patinfo.getAdpAddress().getStreet(), patinfo.getAdpAddress().getHouse(),
                    patinfo.getAdpAddress().getFlat(), patinfo.getAdmAddress().getRegion(),
                    patinfo.getAdmAddress().getCity(), patinfo.getAdmAddress().getStreet(),
                    patinfo.getAdmAddress().getHouse(), patinfo.getAdmAddress().getFlat(),
                    patinfo.getMrab(), patinfo.getNamemr(), patinfo.getNcex(),
                    patinfo.getPolis_oms().getStrg(), patinfo.getPolis_oms().getTdoc(),
                    patinfo.getPolis_dms().getStrg(), patinfo.getPolis_dms().getSer(),
                    patinfo.getPolis_dms().getNom(), patinfo.getCpol_pr(), patinfo.getTerp(),
                    patinfo.getTdoc(), patinfo.getDocser(), patinfo.getDocnum(),
                    avoidDefaultSqlDateValue(patinfo.getDatadoc()), patinfo.getOdoc(),
                    patinfo.getSnils(), avoidDefaultSqlDateValue(patinfo.getDataz()),
                    patinfo.getProf(), patinfo.getTel(), avoidDefaultSqlDateValue(patinfo.getDsv()), 
                    patinfo.getPrizn(), patinfo.getTer_liv(), patinfo.getRegion_liv(), 
                    patinfo.getBirthplace(), patinfo.getOgrn_smo(), patinfo.getObraz(), patinfo.getStatus());
                int id = sme.getGeneratedKeys().getInt("npasp");
                sme.commitTransaction();
                try {
					serverManager.instance.getServerById(18).executeServerMethod(1803, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
                return id;
            } else {
                throw new PatientAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Info addLgota(final AllLgota lgota) throws LgotaAlreadyExistException,
            KmiacServerException {
        final int[] indexes = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isLgotaExist(lgota)) {
                sme.execPreparedT(
                    "INSERT INTO p_kov (npasp, lgot, datal, gri, sin, pp, drg, dot, obo, ndoc) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", true, lgota,
                    LGOTA_TYPES, indexes);
                Info tmpInfo = new Info();
                tmpInfo.setId(sme.getGeneratedKeys().getInt("id"));
                sme.setCommit();
                tmpInfo.setName(getStringTranscriptionForLgota(lgota.getLgota()));
                return tmpInfo;
            } else {
                throw new LgotaAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Info addKont(final Kontingent kont)
            throws KontingentAlreadyExistException, KmiacServerException {
        final int[] indexes = {1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isKontingentExist(kont)) {
                sme.execPreparedT(
                        "INSERT INTO p_konti (npasp, kateg, datal) "
                        + "VALUES (?, ?, ?);", true, kont,
                        KONTINGENT_TYPES, indexes);
                Info tmpInfo = new Info();
                tmpInfo.setId(sme.getGeneratedKeys().getInt("id"));
                sme.setCommit();
                tmpInfo.setName(getStringTranscriptionForKontingent(kont.getKateg()));
                return tmpInfo;
            } else {
                throw new KontingentAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addOrUpdateAgent(final Agent agent)
            throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isAgentExist(agent)) {
                final int[] indexes = {
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
                sme.execPreparedT(
                        "INSERT INTO p_preds (npasp, fam, im, ot, "+
                        "datar, pol, name_str, ogrn_str, vpolis, "+
                        "spolis, npolis, birthplace, tdoc, docser, docnum) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                        false, agent, AGENT_TYPES, indexes);
                sme.setCommit();
            } else {
                final int[] indexes = {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0
                };
                sme.execPreparedT("UPDATE p_preds SET "
                        + "fam = ?, im = ?, ot = ?, datar = ?, pol = ?, name_str = ?, ogrn_str = ?, "
                        + "vpolis = ?, spolis = ?, npolis = ?, birthplace = ?, tdoc = ?, docser = ?, docnum = ? WHERE npasp = ?;", 
                        false, agent, AGENT_TYPES, indexes);
                sme.setCommit();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addOrUpdateSign(final Sign sign) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isSignExist(sign)) {
                final int[] indexes = {0, 1, 2, 3, 4, 5, 6};
                sme.execPreparedT(
                    "INSERT INTO p_sign (npasp, grup, ph, allerg, "
                    + "farmkol, vitae, vred) VALUES (?, ?, ?, ?, ?, ?, ?);",
                    false, sign, SIGN_TYPES, indexes);
                sme.setCommit();
            } else {
                final int[] indexes = {1, 2, 3, 4, 5, 6, 0};
                sme.execPreparedT("UPDATE p_sign SET "
                    + "grup = ?, ph = ?, allerg = ?, "
                    + "farmkol = ?, vitae = ?, vred = ? WHERE npasp = ?;", false,
                    sign, SIGN_TYPES, indexes);
                sme.setCommit();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addGosp(final Gosp gosp) throws GospAlreadyExistException,
            KmiacServerException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isGospExist(gosp)) {
                sme.execPreparedT(
                    "INSERT INTO c_gosp(npasp, nist, datap, vremp, "
                    + "pl_extr, naprav, n_org, cotd, sv_time, sv_day, ntalon, "
                    + "vidtr, pr_out, alkg, meesr, vid_tran, diag_n, diag_p, "
                    + "named_n, named_p, nal_z, nal_p, t0c, ad, smp_data, "
                    + "smp_time, smp_num, cotd_p, datagos, vremgos, cuser, "
                    + "dataosm, vremosm, dataz, jalob, vid_st, pr_ber, srok_ber, komm) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?);", true, gosp, GOSP_TYPES, indexes);
                int id = sme.getGeneratedKeys().getInt("id");
                sme.setCommit();
                return id;
            } else {
                log.log(Level.INFO, "Запись госпитализации с такими данными уже существует");
                throw new GospAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception - Ошибка при добавлении госпитализации: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addNambk(final Nambk nambk) throws NambkAlreadyExistException,
            KmiacServerException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isNambkExist(nambk)) {
                sme.execPreparedT("INSERT INTO p_nambk ("
                        + "npasp, nambk, nuch, cpol, datapr, dataot, ishod)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?);",
                        false, nambk, NAMBK_TYPES, indexes);
                sme.setCommit();
            } else {
                final int[] indexes1 = {1, 2, 3, 4, 5, 6, 0, 3};
                sme.execPreparedT("UPDATE p_nambk SET "
                        + "nambk = ?, nuch = ?, cpol = ?, "
                        + "datapr = ?, dataot = ?, ishod = ? "
                        + "WHERE npasp = ? AND cpol = ?",
                        false, nambk, NAMBK_TYPES, indexes1);
                sme.setCommit();
                throw new NambkAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

//////////////////////// Delete Methods ////////////////////////////////////

    @Override
    public final void deletePatient(final int npasp) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM patient WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteNambk(final int npasp, final int cpol) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_nambk WHERE npasp = ? AND cpol = ?;",
                    false, npasp, cpol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteLgota(final int id) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_kov WHERE id=?;",
                    false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteKont(final int id) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_konti WHERE id = ?;",
                    false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteAgent(final int npasp) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_preds WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteSign(final int npasp) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_sign WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteGosp(final int id) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_osmotr WHERE id_gosp = ?;",
                false, id);
            sme.execPrepared("DELETE FROM c_otd WHERE id_gosp = ?;",
                false, id);
            sme.execPrepared("DELETE FROM c_izmer WHERE id_gosp = ?;",
                false, id);
            sme.execPrepared("DELETE FROM c_diag WHERE id_gosp = ?;",
                false, id);
            sme.execPrepared("DELETE FROM c_gosp WHERE id = ?;",
                false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

//////////////////////// Update Methods ////////////////////////////////////

    @Override
    public final void updatePatient(final PatientFullInfo patinfo)
            throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE patient SET "
                + "fam = ?, im = ?, ot = ?, datar = ?, poms_ser = ?, poms_nom = ?, "
                + "pol = ?, jitel = ?, sgrp = ?, adp_obl = ?, adp_gorod = ?, "
                + "adp_ul = ?, adp_dom = ?, adp_kv = ?, adm_obl = ?, adm_gorod = ?, "
                + "adm_ul = ?, adm_dom = ?, adm_kv = ?, mrab = ?, name_mr = ?, "
                + "ncex = ?, poms_strg = ?, poms_tdoc = ?, pdms_strg = ?, "
                + "pdms_ser = ?, pdms_nom = ?, cpol_pr = ?, terp = ?, tdoc=?, "
                + "docser = ?, docnum = ?, datadoc = ?, odoc = ?, snils = ?, "
                + "dataz = ?, prof = ?, tel = ?, dsv = ?, prizn = ?, ter_liv = ?, "
                + "region_liv = ?, birthplace = ?, ogrn_smo = ?, obraz = ?, status = ? WHERE npasp = ?", false,
                patinfo.getFam(), patinfo.getIm(), patinfo.getOt(),
                avoidDefaultSqlDateValue(patinfo.getDatar()),
                patinfo.getPolis_oms().getSer(), patinfo.getPolis_oms().getNom(),
                patinfo.getPol(), patinfo.getJitel(), patinfo.getSgrp(),
                patinfo.getAdpAddress().getRegion(), patinfo.getAdpAddress().getCity(),
                patinfo.getAdpAddress().getStreet(), patinfo.getAdpAddress().getHouse(),
                patinfo.getAdpAddress().getFlat(), patinfo.getAdmAddress().getRegion(),
                patinfo.getAdmAddress().getCity(), patinfo.getAdmAddress().getStreet(),
                patinfo.getAdmAddress().getHouse(), patinfo.getAdmAddress().getFlat(),
                patinfo.getMrab(), patinfo.getNamemr(), patinfo.getNcex(),
                patinfo.getPolis_oms().getStrg(), patinfo.getPolis_oms().getTdoc(),
                patinfo.getPolis_dms().getStrg(), patinfo.getPolis_dms().getSer(),
                patinfo.getPolis_dms().getNom(), patinfo.getCpol_pr(), patinfo.getTerp(),
                patinfo.getTdoc(), patinfo.getDocser(), patinfo.getDocnum(),
                avoidDefaultSqlDateValue(patinfo.getDatadoc()), patinfo.getOdoc(),
                patinfo.getSnils(), avoidDefaultSqlDateValue(patinfo.getDataz()),
                patinfo.getProf(), patinfo.getTel(), avoidDefaultSqlDateValue(patinfo.getDsv()),patinfo.getPrizn(), patinfo.getTer_liv(),patinfo.getRegion_liv(), patinfo.getBirthplace(), patinfo.getOgrn_smo(), 
                patinfo.getObraz(), patinfo.getStatus(), patinfo.getNpasp());
            sme.commitTransaction();
            try {
				serverManager.instance.getServerById(18).executeServerMethod(1803, patinfo.npasp);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateNambk(final Nambk nambk) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 0, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_nambk SET "
                    + "nambk = ?, nuch = ?, cpol = ?, "
                    + "datapr = ?, dataot = ?, ishod = ? "
                    + "WHERE npasp = ? AND cpol = ?",
                    false, nambk, NAMBK_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateKont(final Kontingent kont) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_konti SET npasp = ?, kateg = ?, datal = ? "
                    + "WHERE id = ?", false,
                    kont, KONTINGENT_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateGosp(final Gosp gosp) throws KmiacServerException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 0
        };
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE c_gosp SET "
                    + "npasp = ?, nist = ?, datap = ?, vremp = ?, "
                    + "pl_extr = ?, naprav = ?, n_org = ?, cotd = ?, "
                    + "sv_time = ?, sv_day = ?, ntalon = ?, vidtr = ?, "
                    + "pr_out = ?, alkg = ?, meesr = ?, vid_tran = ?, "
                    + "diag_n = ?, diag_p = ?, named_n = ?, named_p = ?, "
                    + "nal_z = ?, nal_p = ?, t0c = ?, ad = ?, smp_data = ?, "
                    + "smp_time = ?, smp_num = ?, cotd_p = ?, datagos = ?, "
                    + "vremgos = ?, cuser = ?, dataosm = ?, vremosm = ?, "
                    + "dataz = ?, jalob = ?, vid_st = ?, pr_ber = ?, srok_ber = ?, komm = ? WHERE id = ?;", false,
                    gosp, GOSP_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }


    @Override
    public final void updateOgrn(final int npasp) throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE p_preds SET name_str = null, ogrn_str = null WHERE npasp=?",
                    false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

//////////////////////// Configuration Methods ////////////////////////////////////

    @Override
    public final void saveUserConfig(final int id, final String config) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new TException();
        }
    }

    @Override
    public final void testConnection() throws TException {
    }

////////////////////////// Classifiers ////////////////////////////////////

    @Override
    public final List<IntegerClassifier> getSgrp() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_az9";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmSgrp =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmSgrp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getPomsTdoc() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_f008";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmTdoc =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmTdoc.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getTdoc() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_az0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmTdoc =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmTdoc.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getNaprav() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_k02";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmNaprav =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmNaprav.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getM00() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name_s FROM n_m00 WHERE pr='Л' ORDER BY pcod";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmM00 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name_s");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmM00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getN00() throws KmiacServerException {
//        final String sqlQuery = "SELECT n_n00.pcod, (n_m00.name_s || ', ' || n_n00.name) as name "
//                + "FROM n_n00 INNER JOIN n_m00 ON n_m00.pcod = n_n00.clpu;";
        final String sqlQuery = "SELECT pcod, name_u as name "
                                + "FROM n_n00 WHERE amb='П' "
                                + "ORDER BY pcod;";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmN00 =
            new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
        return rsmN00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getO00() throws KmiacServerException {
//        final String sqlQuery = "SELECT n_o00.pcod, (n_m00.name_s || ', ' || n_o00.name) as name "
//            + "FROM n_o00 INNER JOIN n_m00 ON n_m00.pcod = n_o00.clpu;";
        final String sqlQuery = "SELECT pcod, name_u as name "
                + "FROM n_o00 "
                + "ORDER BY pcod;";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmO00 =
            new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmO00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getOtdForCurrentLpu(final int lpuId)
            throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_o00 WHERE clpu = ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmO00 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
            return rsmO00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getAL0() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_al0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAl0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAl0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getW04() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_w04";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmW04 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmW04.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getAI0() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_ai0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAi0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAi0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getAF0() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_af0";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAf0 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAf0.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getALK() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_alk";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAlk =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAlk.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getVTR() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_vtr";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmVtr =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmVtr.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getABB() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_abb WHERE (pcod=2) "
            + "OR (pcod=7) OR (pcod=10)";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmVtr =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmVtr.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getSmorf(final int kodsmo)
            throws SmorfNotFoundException, KmiacServerException {
        final String sqlQuery = "SELECT smocod as pcod, nam_smop as name FROM n_smorf WHERE pcod = ?";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmSmorf =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, kodsmo)) {
            return rsmSmorf.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final String printMedCart(final Nambk nambk, final PatientFullInfo pat,
            final UserAuthInfo uai, final String docInfo, final String omsOrg,
            final String lgot) throws KmiacServerException {
        final String path;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT c_ogrn "
                    + "FROM n_m00 WHERE pcod = ?", uai.getClpu());
            String ogrn = "";
            while (acrs.getResultSet().next()) {
                if (acrs.getResultSet().getString(1) != null) {
                    ogrn = acrs.getResultSet().getString(1);
                }
            }
            acrs.close();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String gender;
            if (pat.getPol() == 1) {
                gender = "мужской";
            } else if (pat.getPol() == 2) {
                gender = "женский";
            } else {
                gender = "";
            }
            HtmTemplate htmTemplate = new HtmTemplate(
                    new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                    + "\\plugin\\reports\\MedCardAmbPriem.htm");
            htmTemplate.replaceLabels(true,
                uai.getClpu_name(),
                ogrn,
                nambk.getNambk(),
                omsOrg,
                pat.getPolis_oms().getSer() + pat.getPolis_oms().getNom(),
                pat.getSnils(),
                "",
                lgot,
                pat.getFam(),
                pat.getIm(),
                pat.getOt(),
                gender,
                dateFormat.format(new Date(pat.getDatar())),
                pat.getAdmAddress().getCity()
                    + "," + pat.getAdmAddress().getStreet() + " "
                    + pat.getAdmAddress().getHouse()
                    + " - " + pat.getAdmAddress().getFlat(),
                pat.getAdpAddress().getCity()
                    + "," + pat.getAdpAddress().getStreet() + " "
                    + pat.getAdpAddress().getHouse()
                    + " - " + pat.getAdpAddress().getFlat(),
                pat.getTel(),
                docInfo
            );
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            throw new  KmiacServerException(); // тут должен быть кмиац сервер иксепшн
        }
    }

    @Override
    public final String printAmbCart(final PatientFullInfo pat) throws TException {
        return null;
    }

    @Override
    public final List<IntegerClassifier> getLKN() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_lkn;";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLkn =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmLkn.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getLKR() throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_lkr;";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLkr =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmLkr.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getL00(final int pcod) throws KmiacServerException {
        String sqlQuery;
        if (pcod == 42) {
            sqlQuery = "SELECT n_l00.pcod, (nam_kem || ' ' || n_l01.name) as nam_kem FROM n_l00 "
                + "INNER JOIN n_l01 ON n_l00.ter=n_l01.pcod WHERE c_ffomc = ? ORDER BY nam_kem;";
        } else {
            sqlQuery = "SELECT n_l00.pcod, nam_kem FROM n_l00 WHERE c_ffomc = ? ORDER BY nam_kem;";
        }
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmL00 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "nam_kem");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
            return rsmL00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getU10(final String name) throws KmiacServerException {
        final String sqlQuery = "SELECT name1, ndom FROM n_u10 WHERE name1 = ? ORDER BY ndom;";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmU10 =
                new TResultSetMapper<>(StringClassifier.class, "name1", "ndom");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, name)) {
            return rsmU10.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void addOrUpdateOtd(final int idGosp, final int nist, final int cotd)
            throws KmiacServerException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isOtdExist(idGosp)) {
                String sqlQuery = "INSERT INTO c_otd (id_gosp, nist, cotd, dataz)"
                    + "VALUES (?, ?, ?, ?);";
                sme.execPrepared(sqlQuery, true, idGosp, nist, cotd,
                    new Date(System.currentTimeMillis()));
                sme.setCommit();
                    //return id;
            } else {
                String sqlQuery = "UPDATE c_otd SET nist = ?, cotd = ?, dataz = ? "
                        + "WHERE id_gosp = ?;";
                sme.execPrepared(sqlQuery, true, nist, cotd,
                    new Date(System.currentTimeMillis()), idGosp);
                sme.setCommit();
            }
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addToOtd(final int idGosp, final int nist, final int cotd)
            throws KmiacServerException {
        String sqlQuery = "INSERT INTO c_otd (id_gosp, nist, cotd, dataz) VALUES (?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, true, idGosp, nist, cotd,
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
    public final void updateOtd(final int id, final int idGosp, final int nist, final int cotd)
            throws KmiacServerException {
        String sqlQuery = "UPDATE c_otd SET id_gosp = ?, nist = ?, cotd = ?, dataz = ? "
                + "WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, true, idGosp, nist, cotd,
                new Date(System.currentTimeMillis()), id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final String printStacCart(final PatientFullInfo pat, final Gosp gosp,
            final String otdName, final String naprName, final String vidTrans,
            final String grBl, final String rezus) throws KmiacServerException {
        final String path;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            String gender;
            if (pat.getPol() == 1) {
                gender = "мужской";
            } else if (pat.getPol() == 2) {
                gender = "женский";
            } else {
                gender = "";
            }
            int let = (int) ((System.currentTimeMillis() - pat.getDatar()) / 31556952000L);
            HtmTemplate htmTemplate = new HtmTemplate(
                    new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                    + "\\plugin\\reports\\MedCardStac.htm");
            System.out.println(new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                    + "\\plugin\\reports\\MedCardStac.htm");
            htmTemplate.replaceLabels(true,
                (pat.getPolis_oms().isSetSer()) ? pat.getPolis_oms().getSer() : "",
                (pat.getPolis_oms().isSetNom()) ? pat.getPolis_oms().getNom() : "",
                (pat.getPolis_oms().isSetStrg())
                    ? String.valueOf(pat.getPolis_oms().getStrg())  : "",
                (pat.getPolis_dms().isSetSer()) ? pat.getPolis_dms().getSer() : "",
                (pat.getPolis_dms().isSetNom()) ? pat.getPolis_dms().getNom() : "",
                (pat.getPolis_dms().isSetStrg())
                    ? String.valueOf(pat.getPolis_dms().getStrg()) : "",
                (gosp.isSetNal_p() && gosp.nal_p) ? "педикулез" : "",
                (gosp.isSetNal_z() && gosp.nal_z) ? "часотка" : "",
                (gosp.isSetToc()) ? gosp.getToc() : "",
                (gosp.isSetAd()) ? gosp.getAd() : "",
                (gosp.isSetNist()) ? String.valueOf(gosp.getNist()) : "",
                (gosp.isSetDatap()) ? dateFormat.format(gosp.getDatap()) : "",
                (gosp.isSetVremp()) ? timeFormat.format(gosp.getVremp()) : "",
                "",
                "",
                otdName,
                "",
                vidTrans,
                grBl,
                rezus,
                String.format("%s %s %s", pat.getFam(), pat.getIm(), pat.getOt()),
                gender,
                String.valueOf(let),
                pat.getAdmAddress().getCity()
                    + "," + pat.getAdmAddress().getStreet() + " "
                    + pat.getAdmAddress().getHouse()
                    + " - " + pat.getAdmAddress().getFlat(),
                pat.getTel(),
                "",
                naprName
            );
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            throw new  KmiacServerException(); // тут должен быть кмиац сервер иксепшн
        }
    }

    @Override
    public final List<AllLgota> getAllLgota(final int npasp) throws LgotaNotFoundException,
            KmiacServerException {
        String sqlQuery = "SELECT id, npasp, lgot, datal, name, gri, sin, pp, drg, "
                + "dot, obo, ndoc FROM p_kov "
                + "INNER JOIN n_lkn ON p_kov.lgot = n_lkn.pcod WHERE npasp = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            ResultSet rs = acrs.getResultSet();
            List<AllLgota> lgotaList = rsmAllLgota.mapToList(rs);
            if (lgotaList.size() > 0) {
                return lgotaList;
            } else {
                throw new LgotaNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateLgota(final AllLgota lgota) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_kov SET npasp = ?, lgot = ?, datal = ?, gri = ?, "
                + "sin = ?, pp = ?, drg = ?, dot = ?, obo = ?, ndoc = ? "
                + "WHERE id = ?", false,
                lgota, LGOTA_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final String getNameOtdGosp(final int id)
            throws PatientGospYesOrNoNotFoundException, KmiacServerException {
        String sqlQuery = "SELECT n.name_u as otd FROM patient p "
            + "JOIN c_gosp g ON (p.npasp = g.npasp) JOIN c_otd o ON (g.id = o.id_gosp) "
            + "JOIN n_o00 n ON (o.cotd = n.pcod) "
            + "WHERE p.npasp=? AND (g.pr_out=0 or g.pr_out is null) AND o.datav is null";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, id)) {
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rs.getString("otd");
            } else {
                throw new PatientGospYesOrNoNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
    }

	//выбор показателей для отображения наименования
	@Override
	public final List<IntegerClassifier> getPokaz() throws KmiacServerException,
			PokazNotFoundException, TException {
        final String sqlQuery = "SELECT nstr as pcod, name FROM n_anz";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmAnz =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmAnz.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
	}
	@Override
	public List<Anam> getAnamnez(int npasp, int cslu, int cpodr)
			throws TipPodrNotFoundException, KmiacServerException, TException {
	    String sqlQuery = null;

	    if (cslu == 1){
			sqlQuery = "SELECT p.npasp, p.datap, n.nstr "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON (p.numstr = n.nstr and p.npasp = ?) " +
	                   "INNER JOIN n_o00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "ORDER BY n.nstr;";
		}
		if (cslu == 2){
			sqlQuery = "SELECT p.npasp, p.datap, n.nstr "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON (p.numstr = n.nstr and p.npasp = ?) " +
	                   "INNER JOIN n_n00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "ORDER BY n.nstr;";
		}
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp, cpodr)) {
            ResultSet rs = acrs.getResultSet();
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				while (rs.next()){
					if (rs.getInt("npasp") == 0){
						sme.execPrepared("INSERT INTO p_anamnez (npasp, numstr, datap) "+
	                    "VALUES (?, ?, ?);",  false, npasp, rs.getInt("nstr"), new Date(System.currentTimeMillis()));
					}
                }
				sme.setCommit();
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				throw new KmiacServerException();
			}
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }

		if (cslu == 1){
			sqlQuery = "SELECT p.npasp, p.datap, p.numstr, p.vybor, p.comment, n.name, ot.prof_anz "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON p.numstr = n.nstr " +
	                   "INNER JOIN n_o00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "WHERE p.npasp = ?"+
	                   "ORDER BY n.nstr;";
		}
		if (cslu == 2){
			sqlQuery = "SELECT p.npasp, p.datap, p.numstr, p.vybor, p.comment, n.name, ot.prof_anz "+
	                   "FROM p_anamnez p FULL JOIN n_anz n ON p.numstr = n.nstr " +
	                   "INNER JOIN n_n00 o ON o.pcod = ? " +
	                   "INNER JOIN n_ot_str ot ON (ot.prlpu = o.prlpu and n.nstr = ot.nstr) "+
	                   "WHERE p.npasp = ?"+
	                   "ORDER BY n.nstr;";
		}
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr, npasp)) {
            ResultSet rs = acrs.getResultSet();
            List<Anam> anamList = rsmAnam.mapToList(rs);
            if (anamList.size() > 0) {
                return anamList;
            } else {
                throw new TipPodrNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public void updateAnam(List<Anam> anam) throws KmiacServerException,
			TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            for (Anam elemAnam : anam) {
            	sme.execPrepared("UPDATE p_anamnez SET vybor = ?, comment = ? WHERE npasp = ? and numstr = ?;",
            			false, elemAnam.vybor, elemAnam.getComment(), elemAnam.getNpasp(), elemAnam.getNumstr());
            }
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException(e.getMessage());
        }
	}

	@Override
	public void deleteAnam(int npasp, int cslu, int cpodr)
			throws KmiacServerException, TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_anamnez p " +
            		"WHERE p.npasp=?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new KmiacServerException();
        }
	}

	@Override
	public String printAnamnez(PatientFullInfo pat, List<Anam> anam,
			UserAuthInfo uai) throws KmiacServerException, TException {
	    final String path;
		String sqlQuery = null;
		int numline = 0;
		int prlpu = 0;
			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("anam", ".htm").getAbsolutePath()), "utf-8")) {
   				StringBuilder sb = new StringBuilder(0x10000);
	    			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
	    			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	    			sb.append("<head>");
	       			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
	   				sb.append("<title>Эпидемиологический анамнез</title>");
	   				sb.append("</head>");
	   				sb.append("<body>");
					try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name from n_m00 where pcod = ?", uai.getClpu())) {
						if (acr.getResultSet().next())
		      				sb.append(String.format("<h4 align=center> %s </h4>", acr.getResultSet().getString("name")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (uai.getCslu() == 1){
   						sqlQuery = "SELECT o.prlpu FROM n_o00 o WHERE o.pcod = ?";
					}
					if (uai.getCslu() == 2){
   						sqlQuery = "SELECT o.prlpu FROM n_n00 o WHERE o.pcod = ?";
					}
	            	try (AutoCloseableResultSet acr = sse.execPreparedQuery(sqlQuery, uai.getCpodr())) {
						if (acr.getResultSet().next()){
		      				prlpu = acr.getResultSet().getInt("prlpu");
	          				sb.append("<h4 align=center> <b>Эпидемиологический анамнез</b> </h4>");
//							if (acr.getResultSet().getInt("prlpu") == 5){
//		          				sb.append("<h4 align=center> <b>Эпидемиологический анамнез</b> </h4>");
//		      				}else{
//		          				sb.append("<h4 align=center> <b>ПЕРВИЧНЫЙ ОСМОТР В ПРИЕМНО-ДИАГНОСТИЧЕСКОМ ОТДЕЛЕНИИ</b> </h4>");
//		      				}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
   					sb.append(String.format("Ф.И.О. <b> %s %s %s </b> <br>", pat.getFam(), pat.getIm(), pat.getOt()));
					sb.append(String.format("Дата рождения  <b> %1$td.%1$tm.%1$tY </b> <br>", pat.getDatar()));
   					sb.append(String.format("Домашний адрес  %s %s %s - %s <br>", pat.getAdmAddress().getCity(), pat.getAdmAddress().getStreet(), pat.getAdmAddress().getHouse(), pat.getAdmAddress().getFlat()));
   				    

					if (uai.getCslu() == 1){
  	   						sqlQuery = "SELECT n.name, n.numstr, n.yn, ot.numline  "+
  	   				                   "FROM n_anz n INNER JOIN n_ot_str ot ON (n.nstr = ot.nstr) " +
  	   				                   "INNER JOIN n_o00 o ON (ot.prlpu = o.prlpu and o.pcod = ?) " +
  	   				                   "WHERE n.nstr = ?";
   					}
					if (uai.getCslu() == 2){
  	   						sqlQuery = "SELECT n.name, n.numstr, n.yn, ot.numline "+
  	   				                   "FROM n_anz n INNER JOIN n_ot_str ot ON (n.nstr = ot.nstr) " +
  	   				                   "INNER JOIN n_n00 o ON (ot.prlpu = o.prlpu and o.pcod = ?) " +
  	   				                   "WHERE n.nstr = ?";
   					}
   		            for (Anam elemAnam : anam) {
   		            	try (AutoCloseableResultSet acr = sse.execPreparedQuery(sqlQuery, uai.getCpodr(),elemAnam.getNumstr())) {
   							if (acr.getResultSet().next()){
   			      				if (numline != acr.getResultSet().getInt("numstr")){
   			      					sb.append("<br>");
   			      				}else{
   			      					sb.append(", ");
   			      				}
		      					if (acr.getResultSet().getString("numline") != null)
 			      						sb.append(String.format("%s %s ", acr.getResultSet().getString("numline"), acr.getResultSet().getString("name")));
		      					else sb.append(String.format("%s ", acr.getResultSet().getString("name")));
		      					System.out.println(acr.getResultSet().getString("yn"));
		      					if (acr.getResultSet().getString("yn").equalsIgnoreCase("T"))
		      						if(elemAnam.isVybor())sb.append("<b>да </b>"); else sb.append("<b>нет </b>");
		      					if (elemAnam.getComment() != null) sb.append(String.format("<b>%s</b> ", elemAnam.getComment().toLowerCase()));
   		      					numline = acr.getResultSet().getInt("numstr");
   							}
   						} catch (Exception e) {
   							e.printStackTrace();
   						}
   		            }
   					if (prlpu == 2){
   	   		            sb.append(String.format("<br><br>Подпись  _______________________________________________    %1$td.%1$tm.%1$tY г. <br><br>", new Date(System.currentTimeMillis())));
   	   					sb.append(String.format("Подпись врача __________________________________________    %1$td.%1$tm.%1$tY г.<br>", new Date(System.currentTimeMillis())));
   					}else{
   	   					sb.append("<br>Согласие на госпитализацию:   Я, __________________________________________________,<br>");
   	   					sb.append("как родитель/законный представитель __________________________________________________,<br>");
   	   		            sb.append(String.format("<br><br>Подпись врача _______________________________________________    %1$td.%1$tm.%1$tY г. <br><br>", new Date(System.currentTimeMillis())));
   	   					sb.append(String.format("Подпись медицинской сестры ________________________________________    %1$td.%1$tm.%1$tY г.<br>", new Date(System.currentTimeMillis())));
   					}

					osw.write(sb.toString());
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
			return path;
	}

	@Override
	public List<StringClassifier> getShOsmPoiskDiag(int cspec, int cslu, String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.diag AS pcod, c00.name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.diag ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu) : sse.execPreparedQuery(sql, cspec, cslu, cslu, srcText, srcText, srcText)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public List<IntegerClassifier> getShOsmPoiskName(int cspec, int cslu,
			String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.id AS pcod, sho.diag, sho.diag || ' ' || substring(din.name from 1 for 1) || ' ' || sho.name AS name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) JOIN n_din din ON (din.pcod = sho.cdin) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.diag ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu) : sse.execPreparedQuery(sql, cspec, cslu, cslu, srcText, srcText, srcText, srcText)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public List<IntegerClassifier> getShOsmByDiag(int cspec, int cslu,
			String diag, String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.id AS pcod, sho.name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) AND (sho.diag = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.name ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu, diag) : sse.execPreparedQuery(sql, cspec, cslu, cslu, diag, srcText, srcText, srcText)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public Shablon getShOsm(int id_sh) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT sho.id, sho.diag, nd.name, sho.name, nsh.pcod, nsh.name, sht.sh_text FROM sh_osm sho JOIN n_din nd ON (nd.pcod = sho.cdin) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) WHERE sho.id = ? ORDER BY nsh.pcod ", id_sh)) {
			if (acrs.getResultSet().next()) {
				Shablon sho = new Shablon(acrs.getResultSet().getInt(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3), acrs.getResultSet().getString(4), new ArrayList<ShablonText>());
				do {
					sho.textList.add(new ShablonText(acrs.getResultSet().getInt(5), acrs.getResultSet().getString(6), acrs.getResultSet().getString(7)));
				} while (acrs.getResultSet().next());
				return sho;
			} else {
				throw new SQLException("No templates with this id");
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template by its id");
		}
	}

	@Override
	public List<IntegerClassifier> getShDopNames(int idRazd)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, name FROM sh_dop WHERE id_n_shablon = ? ", idRazd)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template names by razd id");
		}
	}

	@Override
	public IntegerClassifier getShDop(int id_sh) throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, text AS name FROM sh_dop WHERE id = ? ", id_sh)) {
			if (acrs.getResultSet().next())
				return rsmIntClas.map(acrs.getResultSet());
			else
				throw new SQLException("No templates with this id");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template by its id");
		}
	}

}

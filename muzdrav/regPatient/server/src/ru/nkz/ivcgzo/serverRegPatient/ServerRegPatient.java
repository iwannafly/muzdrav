/**
 *
 */
package ru.nkz.ivcgzo.serverRegPatient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
import ru.nkz.ivcgzo.thriftRegPatient.Address;
import ru.nkz.ivcgzo.thriftRegPatient.Agent;
import ru.nkz.ivcgzo.thriftRegPatient.AgentAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.AgentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.AllGosp;
import ru.nkz.ivcgzo.thriftRegPatient.Gosp;
import ru.nkz.ivcgzo.thriftRegPatient.GospAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.GospNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Jalob;
import ru.nkz.ivcgzo.thriftRegPatient.JalobAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.JalobNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Lgota;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Nambk;
import ru.nkz.ivcgzo.thriftRegPatient.NambkAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Polis;
import ru.nkz.ivcgzo.thriftRegPatient.Sign;
import ru.nkz.ivcgzo.thriftRegPatient.SignAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.SignNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.SpravStruct;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient.Iface;

/**
 * Класс, имплементирующий трифтовый интерфейс для связи с клиентом.
 * @author Avdeev Alexander
 */
public class ServerRegPatient extends Server implements Iface {
    private TServer thrServ;
    private TResultSetMapper<PatientBrief, PatientBrief._Fields> rsmPatientBrief;
    private TResultSetMapper<Address, Address._Fields> rsmAdpAdress;
    private TResultSetMapper<Address, Address._Fields> rsmAdmAdress;
    private TResultSetMapper<PatientFullInfo, PatientFullInfo._Fields> rsmPatientFullInfo;
    private TResultSetMapper<Polis, Polis._Fields> rsmPolisOms;
    private TResultSetMapper<Polis, Polis._Fields> rsmPolisDms;
    private TResultSetMapper<Nambk, Nambk._Fields> rsmNambk;
    private TResultSetMapper<Agent, Agent._Fields> rsmAgent;
    private TResultSetMapper<Kontingent, Kontingent._Fields> rsmKontingent;
    private TResultSetMapper<Sign, Sign._Fields> rsmSign;
    private TResultSetMapper<AllGosp, AllGosp._Fields> rsmAllGosp;
    private TResultSetMapper<Gosp, Gosp._Fields> rsmGosp;
    private TResultSetMapper<Jalob, Jalob._Fields> rsmJalob;
    private QueryGenerator<PatientBrief> qgPatientBrief;
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
        String.class, String.class, Integer.class, Integer.class,
    //  terp           tdoc           docser        docnum
        Integer.class, Integer.class, String.class, String.class,
    //  datadoc     odoc          snils         dataz
        Date.class, String.class, String.class, Date.class,
    //  prof          tel           dsv         prizn
        String.class, String.class, Date.class, Integer.class,
    //  ter_liv        region_liv
        Integer.class, Integer.class
    };
    private static final Class<?>[] KONTINGENT_TYPES = new Class<?>[] {
    //  id             npasp          kateg         datal
        Integer.class, Integer.class, Integer.class, Date.class,
    //  name
        String.class
    };
    private static final Class<?>[] AGENT_TYPES = new Class<?>[] {
    //  npasp          fam           im            ot
        Integer.class, String.class, String.class, String.class,
    //  datar       pol          name_str      ogrn_str
        Date.class, Integer.class, String.class, String.class,
    //  vpolis         spolis        npolis        tdoc
        Integer.class, String.class, String.class, Integer.class,
    //  docser        docnum        birthplace
        String.class, String.class, String.class
    };
    private static final Class<?>[] SIGN_TYPES = new Class<?>[] {
    //  id             npasp          kateg         datal
        Integer.class, Integer.class, Integer.class, Date.class,
    //  name
        String.class
    };
    private static final Class<?>[] GOSP_TYPES = new Class<?>[] {
    //  id             ngosp          npasp          nist
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  datap       timep       s_napr       naprav        ush_n
        Date.class, Time.class, Integer.class, String.class, Integer.class,
    //  cotd           svoevr         svoevrd        ntalon
        Integer.class, Integer.class, Integer.class, Integer.class,
    //  vidtr          pr_out         alkg         soobr
        Integer.class, Integer.class, Integer.class, Boolean.class,
    //  vid_tran       diag_n        diag_p        named_n       named_p
        Integer.class, String.class, String.class, String.class, String.class,
    //  nal_z          nal_p          t0c           ad            datacp
        Boolean.class, Boolean.class, String.class, String.class, Date.class,
    //  vremcp      nomcp          kodotd         datagos     vremgos
        Time.class, Integer.class, Integer.class, Date.class, Time.class,
    //  cuser          dataosm     vremosm     dataz
        Integer.class, Date.class, Time.class, Date.class
    };
    private static final Class<?>[] JALOB_TYPES = new Class<?>[] {
    //  id             id_gosp        dataz       timez
        Integer.class, Integer.class, Date.class, Time.class,
    //  jalob
        String.class
    };
    private static final Class<?>[] NAMBK_TYPES = new Class<?>[] {
        //  npasp          nambk         cpol           nuch
        Integer.class, String.class, Integer.class, Integer.class,
        //  datapr      dataot      ishod
        Date.class, Date.class, Integer.class
    };
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
        "snils", "dataz", "prof", "tel", "dsv", "prizn", "ter_liv", "region_liv"
    };
    private static final String[] NAMBK_FIELD_NAMES = {
        "npasp", "nambk", "nuch", "cpol", "datapr", "dataot", "ishod"
    };
    private static final String[] AGENT_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "pol", "name_str", "ogrn_str",
        "vpolis", "spolis" , "npolis", "tdoc", "docser", "docnum", "birthplace"
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
        "id", "ngosp", "npasp", "nist", "datap", "vremp", "s_napr", "naprav", "ush_n",
        "cotd", "svoevr", "svoevrd", "ntalon", "vidtr", "pr_out", "alkg", "soobr",
        "vid_tran", "diag_n", "diag_p", "named_n", "named_p", "nal_z", "nal_p", "t0c",
        "ad", "datacp", "vremcp", "nomcp", "kodotd", "datagos", "vremgos", "cuser",
        "dataosm", "vremosm", "dataz"
    };
    private static final String[] JALOB_FIELD_NAMES = {
        "id", "id_gosp", "dataz", "jalob"
    };

    /**
     * Конструктор класса.
     * @param sse - Определяет методы для выполнения запросов
     *              на чтение данных и работы с подключениями
     * @param tse - Определяет методы для управления транзакциями
     */
    public ServerRegPatient(final ISqlSelectExecutor sse,
            final ITransactedSqlExecutor tse) {
        super(sse, tse);

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
        rsmJalob = new TResultSetMapper<>(Jalob.class, JALOB_FIELD_NAMES);
    }

    /**
     * Запускает сервер.
     */
    @Override
    public final void start() throws Exception {
        ThriftRegPatient.Processor<Iface> proc =
                new ThriftRegPatient.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        System.out.println("12132123132123123132");

        thrServ.serve();
    }

    /**
     * Останавливает сервер.
     */
    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
        }
    }

    /**
     * Возвращает краткие сведения
     * о всех пациентах, удовлетворяющих введенным данным.
     * @param patient - информация о пациентах, по которой производится поиск
     * @return список thrift-объектов, содержащих краткую информацию о пациентах
     * @throws PatientNotFoundException
     */
    @Override
    public final List<PatientBrief> getAllPatientBrief(final PatientBrief patient)
            throws TException, PatientNotFoundException {
        String  sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv "
                + "FROM patient";
        InputData inData = qgPatientBrief.genSelect(patient, sqlQuery);
        try {
            sqlQuery = inData.getQueryText();
            int[] indexes = inData.getIndexes();
            AutoCloseableResultSet acrs = sse.execPreparedQueryT(sqlQuery, patient,
                    PATIENT_BRIEF_TYPES, indexes);
            ResultSet rs = acrs.getResultSet();
            List<PatientBrief> patientsInfo = new ArrayList<PatientBrief>();
            if (rs.next()) {
                do {
                    PatientBrief curPatient = rsmPatientBrief.map(rs);
                    curPatient.setAdpAddress(new Address(rsmAdpAdress.map(rs)));
                    curPatient.setAdmAddress(new Address(rsmAdmAdress.map(rs)));
                    patientsInfo.add(curPatient);
                } while (rs.next());
                return patientsInfo;
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает полные сведения о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий полную информацию о пациенте
     * @throws PatientNotFoundException
     */
    @Override
    public final PatientFullInfo getPatientFullInfo(final int npasp)
            throws TException, PatientNotFoundException {
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
                + "patient.region_liv, p_nambk.nambk, p_nambk.cpol, p_nambk.nuch, "
                + "p_nambk.datapr, p_nambk.dataot, p_nambk.ishod "
                + "FROM patient LEFT JOIN p_nambk ON "
                + "patient.npasp = p_nambk.npasp WHERE patient.npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                PatientFullInfo patient = rsmPatientFullInfo.map(rs);
                patient.setAdpAddress(rsmAdpAdress.map(rs));
                patient.setAdmAddress(rsmAdmAdress.map(rs));
                patient.setPolis_oms(rsmPolisOms.map(rs));
                patient.setPolis_dms(rsmPolisDms.map(rs));
                patient.setNambk(rsmNambk.map(rs));
                return patient;
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает сведения о представителе пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий информацию о представителе пациента
     * @throws AgentNotFoundException
     */
    @Override
    public final Agent getAgent(final int npasp)
            throws AgentNotFoundException, TException {
        String sqlQuery = "SELECT * FROM p_preds WHERE npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                Agent agent = rsmAgent.map(rs);
                return agent;
            } else {
                throw new AgentNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает сведения о льготах пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о льготах пациента
     * @throws LgotaNotFoundException
     */
    @Override
    public final List<Lgota> getLgota(final int npasp)
            throws LgotaNotFoundException, TException {
        //TODO допилить ибо нихрена не понятно
        // в thrift объекте три стринга
        // в базе данных один, где-что-куда хз
        return null;
    }

    /**
     * Возвращает сведения о категориях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о категориях пациента
     * @throws KontingentNotFoundException
     */
    @Override
    public final List<Kontingent> getKontingent(final int npasp)
            throws KontingentNotFoundException, TException {
        String sqlQuery = "SELECT * FROM p_konti WHERE npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            ResultSet rs = acrs.getResultSet();
            List<Kontingent> kontingent = rsmKontingent.mapToList(rs);
            if (kontingent.size() > 0) {
                return kontingent;
            } else {
                throw new KontingentNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает особую информацию о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий особую информацию о пациенте
     * @throws SignNotFoundException
     */
    @Override
    public final Sign getSign(final int npasp) throws SignNotFoundException, TException {
        String sqlQuery = "SELECT * FROM p_sign WHERE npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmSign.map(rs);
            } else {
                throw new SignNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает записи о всех госпитализациях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о госпитализациях пациента
     * @throws GospNotFoundException
     */
    @Override
    public final List<AllGosp> getAllGosp(final int npasp)
            throws GospNotFoundException, TException {
        String sqlQuery = "SELECT id, ngosp, npasp, nist, datap, cotd, "
                + "diag_p, named_p FROM c_gosp WHERE npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            ResultSet rs = acrs.getResultSet();
            List<AllGosp> allGosp = rsmAllGosp.mapToList(rs);
            if (allGosp.size() > 0) {
                return allGosp;
            } else {
                throw new GospNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает запись госпитализации пациента с указанным идентификатором
     * госпитализации
     * @param id - уникальный идентификатор госпитализации
     * @return thrift-объект, содержащий особую информацию о госпитализации пациента
     * @throws GospNotFoundException
     */
    @Override
    public final Gosp getGosp(final int id) throws GospNotFoundException, TException {
        String sqlQuery = "SELECT * FROM c_gosp WHERE id = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, id);
            ResultSet rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmGosp.map(rs);
            } else {
                throw new GospNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    /**
     * Возвращает все жалобы пациента с указанным идентификатором госпитализации
     * @param idGosp - уникальный идентификатор госпитализации
     * @return список thrift-объектов, содержащий все жалобы пациента
     * @throws GospNotFoundException
     */
    @Override
    public final List<Jalob> getAllJalob(final int idGosp)
            throws JalobNotFoundException, TException {
        String sqlQuery = "SELECT id, id_gosp, dataz, jalob "
                + "FROM c_jalob WHERE id_gosp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp);
            ResultSet rs = acrs.getResultSet();
            List<Jalob> jalob = rsmJalob.mapToList(rs);
            if (jalob.size() > 0) {
                return jalob;
            } else {
                throw new JalobNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
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
        }
    }

    /**
     * Добавляет информацию о пациенте в БД
     * @param patinfo - thrift-объект с информацией о пациенте
     * @return целочисленный первичный ключ id, сгенерированный при добавлении
     * @throws PatientAlreadyExistException
     */
    //Не нравится этот метод? Мне он тоже не нравится. Говно, а не метод.
    //TODO перепилить добавление объектов с вложенными пользовательскими типами
    //TODO сделать проверку дат до добавления, иначе вместо пустой даты
    //добавляет 1970 год, а это не есть хорошо.
    @Override
    public final int addPatient(final PatientFullInfo patinfo)
            throws TException, PatientAlreadyExistException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isPatientExist(patinfo)) {
                sme.execPrepared("INSERT INTO patient "
                        + "(fam, im, ot, datar, poms_ser, poms_nom, pol, jitel, sgrp, "
                        + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, adm_obl, "
                        + "adm_gorod, adm_ul, adm_dom, adm_kv, mrab, name_mr, "
                        + "ncex, poms_strg, poms_tdoc, pdms_strg, pdms_ser, pdms_nom, "
                        + "cpol_pr, terp, tdoc, docser, docnum, datadoc, "
                        + "odoc, snils, dataz, prof, tel, dsv, prizn, ter_liv, region_liv) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?);", true,
                        patinfo.getFam(), patinfo.getIm(), patinfo.getOt(),
                        new Date(patinfo.getDatar()),
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
                        new Date(patinfo.getDatadoc()), patinfo.getOdoc(), patinfo.getSnils(),
                        new Date(patinfo.getDataz()), patinfo.getProf(), patinfo.getTel(),
                        new Date(patinfo.getDsv()), patinfo.getPrizn(), patinfo.getTer_liv(),
                        patinfo.getRegion_liv());
                int id = sme.getGeneratedKeys().getInt("npasp");
                sme.setCommit();
                return id;
            } else {
                throw new PatientAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    /**
     * Добавляет сведения о льготах пациента
     * @param lgota - сведения о льготах пациента
     * @return целочисленный первичный ключ id, сгенерированный при добавлении
     * @throws LgotaAlreadyExistException
     */
    @Override
    public final int addLgota(final Lgota lgota)
            throws LgotaAlreadyExistException, TException {
        // TODO Реализовать метод
        // Структура таблицы БД и трифт объекта не совпадают,
        // поэтому непонятно что и куда
        return 0;
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
        }
    }

    /**
     * Добавляет информацию о категории пациента в БД
     * @param kont - информация о категории пациента
     * @return целочисленный первичный ключ id, сгенерированный при добавлении
     * @throws KontingentAlreadyExistException
     */
    @Override
    public final int addKont(final Kontingent kont)
            throws KontingentAlreadyExistException, TException {
        final int[] indexes = {1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isKontingentExist(kont)) {
                sme.execPreparedT(
                        "INSERT INTO p_konti (npasp, kateg, datal, name) "
                        + "VALUES (?, ?, ?, ?);", true, kont,
                        KONTINGENT_TYPES, indexes);
                int id = sme.getGeneratedKeys().getInt("id");
                sme.setCommit();
                return id;
            } else {
                throw new KontingentAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
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
        }
    }

    /**
     * Добавляет информацию о представителе пациента в БД
     * @param agent - информация о представителе пациента
     * @throws AgentAlreadyExistException
     */
    @Override
    public final void addAgent(final Agent agent)
            throws AgentAlreadyExistException, TException {
        final int[] indexes = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
        };
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isAgentExist(agent)) {
                sme.execPreparedT(
                        "INSERT INTO p_preds (npasp, fam, im, ot, "
                        + "datar, pol, name_str, ogrn_str, vpolis, "
                        + "spolis, npolis, tdoc, docser, docnum, "
                        + "birthplace) VALUES (?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?);", false, agent,
                        AGENT_TYPES, indexes);
                sme.setCommit();
            } else {
                throw new AgentAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
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
        }
    }

    /**
     * Добавляет особую информацию о пациенте в БД
     * @param sign - особая информация о пациенте
     * @throws SignAlreadyExistException
     */
    @Override
    public final void addSign(final Sign sign) throws SignAlreadyExistException, TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isSignExist(sign)) {
                sme.execPreparedT(
                        "INSERT INTO p_sign (npasp, grup, ph, allerg, "
                        + "farmkol, vitae, vred VALUES (?, ?, ?, ?, ?, ?, ?);",
                        false, sign, SIGN_TYPES, indexes);
                sme.setCommit();
            } else {
                throw new SignAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    /**
     * Проверяет, существует ли в БД жалоба пациента с такими данными
     * @param jalob - thrift-объект с информацией о жалобе пациента
     * @return true - если жалоба пациента с такими данными уже существует,
     * false - если не существует
     */
    private boolean isJalobExist(final Jalob jalob) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT id FROM c_jalob WHERE (id_gosp = ?) and (jalob = ?)",
                jalob, JALOB_TYPES, 1, 2)) {
            return acrs.getResultSet().next();
        }
    }

    /**
     * Добавляет жалобу пациента в БД
     * @param jalob - thrift-объект с информацией о жалобе пациента
     * @throws JalobAlreadyExistException
     */
    @Override
    public final int addJalob(final Jalob jalob) throws JalobAlreadyExistException,
            TException {
        final int[] indexes = {1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isJalobExist(jalob)) {
                sme.execPreparedT(
                        "INSERT INTO c_jalob (id_gosp, dataz, timez, jalob) "
                        + "VALUES (?, ?, ?, ?);", true, jalob,
                        JALOB_TYPES, indexes);
                int id = sme.getGeneratedKeys().getInt("id");
                sme.setCommit();
                return id;
            } else {
                throw new JalobAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
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
        }
    }

    /**
     * Добавляет запись госпитализации в БД
     * @param gosp - особая информация о представителе пациента
     * @throws GospAlreadyExistException
     */
    @Override
    public final int addGosp(final Gosp gosp) throws GospAlreadyExistException, TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isGospExist(gosp)) {
                sme.execPreparedT(
                        "INSERT INTO c_gosp(ngosp, npasp, nist, datap, vremp, "
                        + "pl_extr, naprav, n_org, cotd, sv_time, sv_day, ntalon, "
                        + "vidtr, pr_out, alkg, meesr, vid_tran, diag_n, diag_p, "
                        + "named_n, named_p, nal_z, nal_p, t0c, ad, smp_data, "
                        + "smp_time, smp_num, cotd_p, datagos, vremgos, cuser, "
                        + "dataosm, vremosm, dataz) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?);", true, gosp, GOSP_TYPES, indexes);
                int id = sme.getGeneratedKeys().getInt("id");
                sme.setCommit();
                return id;
            } else {
                throw new GospAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    /**
     * Проверяет, существует ли в БД запись амбулаторной карты с такими данными
     * @param nambk - thrift-объект с информацией об амбулаторной карте
     * @return true - если амбулаторная карта с такими данными уже существует,
     * false - если не существует
     */
    private boolean isNambkExist(final Nambk nambk) throws SQLException {
        try (AutoCloseableResultSet acrs = sse.execPreparedQueryT(
                "SELECT npasp FROM p_nambk WHERE (npasp = ?)",
                nambk, NAMBK_TYPES, 0)) {
            return acrs.getResultSet().next();
        }
    }

    /**
     * Добавляет запись об амбулаторной карте в БД
     * @param nambk - thrift-объект с информацией об амбулаторной карте
     * @throws NambkAlreadyExistException
     */
    @Override
    public final void addNambk(final Nambk nambk) throws NambkAlreadyExistException,
            TException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (!isNambkExist(nambk)) {
                sme.execPreparedT("INSERT INTO p_nambk ("
                        + "npasp, nambk, cpol, nuch, datapr, dataot, ishod)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?);",
                        false, nambk, NAMBK_TYPES, indexes);
                sme.setCommit();
            } else {
                throw new NambkAlreadyExistException();
            }
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deletePatient(final int npasp) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM patient WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteNambk(final int npasp, final int cpol) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_nambk WHERE npasp = ? AND cpol = ?;",
                    false, npasp, cpol);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteLgota(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_konti WHERE id=?;",
                    false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteKont(final int id) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_konti WHERE id = ?;",
                    false, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteAgent(final int npasp) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_preds WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void deleteSign(final int npasp) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM p_sign WHERE npasp = ?;", false, npasp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    //TODO исправить - скорее всего удалять не по npasp+ngosp, а по id_gosp
    @Override
    public final void deleteJalob(final int npasp, final int ngosp) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_jalob WHERE npasp = ? AND ngosp = ?;",
                    false, npasp, ngosp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    //TODO исправить - скорее всего удалять не по npasp+ngosp, а по id_gosp
    @Override
    public final void deleteGosp(final int npasp, final int ngosp) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("DELETE FROM c_gosp WHERE npasp = ? AND ngosp = ?;",
                    false, npasp, ngosp);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updatePatient(final PatientFullInfo patinfo)
            throws TException {
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
                + "region_liv = ? WHERE npasp = ?", false,
                patinfo.getFam(), patinfo.getIm(), patinfo.getOt(),
                new Date(patinfo.getDatar()),
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
                new Date(patinfo.getDatadoc()), patinfo.getOdoc(), patinfo.getSnils(),
                new Date(patinfo.getDataz()), patinfo.getProf(), patinfo.getTel(),
                new Date(patinfo.getDsv()), patinfo.getPrizn(), patinfo.getTer_liv(),
                patinfo.getRegion_liv(), patinfo.getNpasp());
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateNambk(final Nambk nambk) throws TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_nambk SET "
                    + "nambk = ?, cpol = ?, nuch = ?, "
                    + "datapr = ?, dataot = ?, ishod = ? "
                    + "WHERE npasp =?",
                    false, nambk, NAMBK_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public void updateLgota(final Lgota lgota) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public final void updateKont(final Kontingent kont) throws TException {
        final int[] indexes = {1, 2, 3, 4, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_konti SET npasp = ?, kateg =?, datal = ?, "
                    + "name = ? WHERE id = ?", false,
                    kont, KONTINGENT_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateAgent(final Agent agent) throws TException {
        final int[] indexes = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0
        };
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_preds SET "
                    + "fam = ?, im = ?, ot = ?, "
                    + "datar = ?, pol = ?, name_str = ?, ogrn_str = ?, "
                    + "vpolis = ?, spolis = ?, npolis = ?, tdoc = ?, docser = ?, "
                    + "docnum  = ?, birthplace  = ? WHERE npasp = ?;", false,
                    agent, AGENT_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateSign(final Sign sign) throws TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE p_sign SET "
                    + "grup = ?, ph = ?, allerg = ?, "
                    + "farmkol = ?, vitae = ?, vred = ? WHERE npasp = ?;", false,
                    sign, SIGN_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateJalob(final Jalob jalob) throws TException {
        final int[] indexes = {1, 2, 3, 4, 0};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE c_jalob SET "
                    + "id_gosp = ?, dataz = ?, timez = ?, jalob = ? "
                    + "WHERE id = ?;", false,
                    jalob, JALOB_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final void updateGosp(final Gosp gosp) throws TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0
        };
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("UPDATE c_gosp SET "
                    + "ngosp = ?, npasp = ?, nist = ?, datap = ?, vremp = ?, "
                    + "pl_extr = ?, naprav = ?, n_org = ?, cotd = ?, "
                    + "sv_time = ?, sv_day = ?, ntalon = ?, vidtr = ?, "
                    + "pr_out = ?, alkg = ?, meesr = ?, vid_tran = ?, "
                    + "diag_n = ?, diag_p = ?, named_n = ?, named_p = ?, "
                    + "nal_z = ?, nal_p = ?, t0c = ?, ad = ?, smp_data = ?, "
                    + "smp_time = ?, smp_num = ?, cotd_p = ?, datagos = ?, "
                    + "vremgos = ?, cuser = ?, dataosm = ?, vremosm = ?, "
                    + "dataz = ? WHERE id = ?;", false,
                    gosp, GOSP_TYPES, indexes);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            throw new TException(e);
        }
    }

    @Override
    public final String getServerVersion() throws TException {
        return configuration.appVersion;
    }

    @Override
    public final String getClientVersion() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final void saveUserConfig(final int id, final String config) throws TException {
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            throw new TException();
        }
    }

    @Override
    public List<IntegerClassifier> getPol() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getSgrp() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getObl() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getGorod(int codObl) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getUl(int codGorod) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getDom(int codUl) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getKorp(int codDom) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getMrab() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getMsStrg() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getPomsTdoc() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getCpolPr() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getTdoc() throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IntegerClassifier> getTerCod(int pcod) throws TException {
        // TODO Auto-generated method stub
        return null;
    }
}

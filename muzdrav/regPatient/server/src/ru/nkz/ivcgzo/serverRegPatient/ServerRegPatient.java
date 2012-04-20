/**
 *
 */
package ru.nkz.ivcgzo.serverRegPatient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftRegPatient.Address;
import ru.nkz.ivcgzo.thriftRegPatient.Agent;
import ru.nkz.ivcgzo.thriftRegPatient.AllGosp;
import ru.nkz.ivcgzo.thriftRegPatient.Gosp;
import ru.nkz.ivcgzo.thriftRegPatient.Jalob;
import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;
import ru.nkz.ivcgzo.thriftRegPatient.Lgota;
import ru.nkz.ivcgzo.thriftRegPatient.Nambk;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Polis;
import ru.nkz.ivcgzo.thriftRegPatient.Sign;
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
        "fam", "im", "ot", "datar", "pol", "jitel", "sgrp", "mrab", "name_mr",
        "ncex", "cpol_pr", "terp", "tdoc", "docser", "docnum",  "datadoc", "odoc",
        "snils", "dataz", "prof", "tel", "dsv", "prizn", "ter_liv", "region_liv"
    };
    private static final String[] NAMBK_FIELD_NAMES = {
        "nambk", "nuch", "cpol", "datapr", "dataot", "ishod"
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
    public final List<PatientBrief> getAllPatientBrief(
            final PatientBrief patient) throws TException, PatientNotFoundException {
        String  sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv "
                + "FROM patient";
        try {
            sqlQuery = qgPatientBrief.genSelectQuery(patient, sqlQuery);
            int[] indexes = qgPatientBrief.genIndexes(patient);
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
        String sqlQuery = "SELECT * FROM patient LEFT JOIN p_nambk ON "
            + "patient.npasp = p_nambk.npasp WHERE npasp = ?;";
        try {
            AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp);
            if (acrs.getResultSet().next()) {
                PatientFullInfo patient = rsmPatientFullInfo.map(acrs.getResultSet());
                patient.setNpasp(npasp);
                patient.setAdpAddress(rsmAdpAdress.map(acrs.getResultSet()));
                patient.setAdmAddress(rsmAdmAdress.map(acrs.getResultSet()));
                patient.setPolis_oms(rsmPolisOms.map(acrs.getResultSet()));
                patient.setPolis_dms(rsmPolisDms.map(acrs.getResultSet()));
                Nambk nambk = new Nambk(rsmNambk.map(acrs.getResultSet()));
                nambk.setNpasp(npasp);
                patient.setNambk(nambk);
                return patient;
            } else {
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    @Override
    public final Agent getAgent(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Lgota> getLgota(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Kontingent> getKontingent(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Sign getSign(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Nambk getNambk(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<AllGosp> getAllGosp(final int npasp, final int ngosp) 
            throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Gosp getGosp(final int npasp, final int ngosp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<SpravStruct> getSpravInfo(final String param) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final int addPatient(final PatientFullInfo patinfo) throws TException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addLgota(final Lgota lgota) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addKont(final Kontingent kont) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAgent(final Agent agent) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addSign(final Sign sign) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addJalob(final Jalob jalob) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addGosp(final Gosp gosp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addNambk(final Nambk nambk) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deletePatient(final int npasp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteNambk(final int npasp, final int cpol) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteLgota(final int npasp, final int lgota) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteKont(final int npasp, final int kateg) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAgent(final int npasp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteSign(final int npasp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteJalob(final int npasp, final int ngosp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteGosp(final int npasp, final int ngosp) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updatePatient(final PatientFullInfo patinfo) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateNambk(final Nambk nambk) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateLgota(final int npasp, final int lgota) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateKont(final int npasp, final int kateg) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateAgent(final Agent agent) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateSign(final Sign sign) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateJalob(final Jalob jalob) throws TException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateGosp(final Gosp gosp) throws TException {
        // TODO Auto-generated method stub
        
    }
}

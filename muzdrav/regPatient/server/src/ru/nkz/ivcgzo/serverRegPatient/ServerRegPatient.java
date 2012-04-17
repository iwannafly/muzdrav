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
import ru.nkz.ivcgzo.thriftRegPatient.PatientAgent;
import ru.nkz.ivcgzo.thriftRegPatient.PatientAllGosp;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientGosp;
import ru.nkz.ivcgzo.thriftRegPatient.PatientJalob;
import ru.nkz.ivcgzo.thriftRegPatient.PatientKontingent;
import ru.nkz.ivcgzo.thriftRegPatient.PatientLgota;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNambk;
import ru.nkz.ivcgzo.thriftRegPatient.PatientSign;
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
    private static final String[] ADP_ADDRESS_FIELD_NAMES = {
        "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_kv"
    };
    private static final String[] ADM_ADDRESS_FIELD_NAMES = {
        "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_kv"
    };
    private static final String[] PATIENT_BRIEF_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
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

        rsmPatientBrief = new TResultSetMapper<>(PatientBrief.class, PATIENT_BRIEF_FIELD_NAMES);
        qgPatientBrief = new QueryGenerator<PatientBrief>(PatientBrief.class, 
                PATIENT_BRIEF_FIELD_NAMES);
        rsmAdpAdress = new TResultSetMapper<>(Address.class, ADP_ADDRESS_FIELD_NAMES);
        rsmAdmAdress = new TResultSetMapper<>(Address.class, ADM_ADDRESS_FIELD_NAMES);
    }

    /**
     * Метод запускающий сервер.
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
     * Метод останавливающий сервер.
     */
    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
        }
    }

    /**
     * Метод, возвращающий краткие сведения
     * о всех пациентах, удовлетворяющих введенным данным.
     * @param patient - информация о пациентах, по которой производится поиск
     */
    @Override
    public final List<PatientBrief> getAllPatientBrief(
            final PatientBrief patient) throws TException {
        String  sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv "
                + "FROM patient";
        try {
            sqlQuery = qgPatientBrief.genSelectQuery(patient, sqlQuery);
            System.out.println(sqlQuery);
            int[] indexes = qgPatientBrief.genIndexes(patient);
            AutoCloseableResultSet acrs = sse.execPreparedQueryT(sqlQuery, patient,
                    PATIENT_BRIEF_TYPES, indexes);
            ResultSet rs = acrs.getResultSet();
            List<PatientBrief> patientsInfo = new ArrayList<PatientBrief>();
            while (rs.next()) {
                PatientBrief curPatient = rsmPatientBrief.map(rs);
                curPatient.setAdpAddress(new Address(rsmAdpAdress.map(rs)));
                curPatient.setAdmAddress(new Address(rsmAdmAdress.map(rs)));
                patientsInfo.add(curPatient);
            }
            return patientsInfo;
        } catch (SQLException e) {
            throw new TException(e);
        }
    }

    @Override
    public final PatientFullInfo getPatientFullInfo(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final PatientAgent getPatientAgent(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<PatientLgota> getPatientLgota(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<PatientKontingent> getPatientKontingent(
            final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final PatientSign getPatientSign(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final PatientNambk getPatientNambk(final int npasp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<PatientAllGosp> getPatientAllGosp(final int npasp,
            final int ngosp) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final PatientGosp getPatientGosp(final int npasp,
            final int ngosp) throws TException {
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
    public void addLgota(final PatientLgota lgota) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addKont(final PatientKontingent kont) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addAgent(final PatientAgent agent) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addSign(final PatientSign sign) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addJalob(final PatientJalob jalob) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addGosp(final PatientGosp gosp) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addNambk(final PatientNambk nambk) throws TException {
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
    public void updateNambk(final PatientNambk nambk) throws TException {
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
    public void updateAgent(final PatientAgent agent) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateSign(final PatientSign sign) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateJalob(final PatientJalob jalob) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateGosp(final PatientGosp gosp) throws TException {
        // TODO Auto-generated method stub
    }
}

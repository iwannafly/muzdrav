package ru.nkz.ivcgzo.serverReception;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftReception.Patient;
import ru.nkz.ivcgzo.thriftReception.Policlinic;
import ru.nkz.ivcgzo.thriftReception.PoliclinicNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.Spec;
import ru.nkz.ivcgzo.thriftReception.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Vidp;
import ru.nkz.ivcgzo.thriftReception.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Vrach;
import ru.nkz.ivcgzo.thriftReception.VrachNotFoundException;

/**
 * @author Avdeev Alexander
 */
public class TestServerReception {

    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.242", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerReception testServer;
    private static final int COUNT_CONNECTIONS = 1;

    @Rule
    public ExpectedException testException = ExpectedException.none();

    @Before
    public final void setUp() throws Exception {
        sse = new SqlSelectExecutor(conn, "postgres", "postgres");
        tse = new TransactedSqlManager(conn, "postgres", "postgres", COUNT_CONNECTIONS);
        testServer = new ServerReception(sse, tse);
    }

    @Test
    public void testStart() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public void testStop() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public void getPoliclinic_IsListSizeCorrect() throws KmiacServerException,
            PoliclinicNotFoundException, TException {
        final int excpectedListSize = 0;
        List<Policlinic> listPoliclinic = testServer.getPoliclinic();
        assertEquals("listSize", excpectedListSize, listPoliclinic.size());
    }

    @Test
    public void getSpec_IsListSizeCorrect() throws KmiacServerException,
            SpecNotFoundException, TException {
        final int default_cpol = 2000004;
        final int excpectedListSize = 1;
        List<Spec> listSpec = testServer.getSpec(default_cpol);
        assertEquals("listSize", excpectedListSize, listSpec.size());
    }

    @Test
    public void getVrach_IsListSizeCorrect() throws KmiacServerException,
            VrachNotFoundException, TException {
        final int default_cpol = 2000004;
        final String default_cdol = "8";
        final int excpectedListSize = 1;
        List<Vrach> listVrach = testServer.getVrach(default_cpol, default_cdol);
        assertEquals("listSize", excpectedListSize, listVrach.size());
    }

    @Test
    public void getVidp_IsListSizeCorrect() throws KmiacServerException,
            VidpNotFoundException, TException {
        final int excpectedListSize = 3;
        List<Vidp> listVidp = testServer.getVidp();
        assertEquals("listSize", excpectedListSize, listVidp.size());
    }

    @Test
    public void getTalon_IsListSizeCorrect() throws KmiacServerException,
            TalonNotFoundException, TException {        
        final int default_cpol = 2000004;
        final String default_cdol = "8";
        final int default_pcod = 48;
        final int excpectedListSize = 14;
        System.out.println(new Date());
        List<Talon> listTalon = testServer.getTalon(default_cpol, default_cdol,
                default_pcod);
        assertEquals("listSize", excpectedListSize, listTalon.size());
    }

    @Test
    public void reserveTalon_IsActuallyReserved() throws KmiacServerException,
            TException, ReserveTalonOperationFailedException {
        Patient patient = new Patient();
        patient.setId(48);
        Talon talon = new Talon();
        talon.setId(20125);
        testServer.reserveTalon(patient, talon);
    }

    @Test
    public void releaseTalon_IsActuallyReliased() throws KmiacServerException,
            ReleaseTalonOperationFailedException, TException {
        Talon talon = new Talon();
        talon.setId(20125);
        testServer.releaseTalon(talon);
    }
}

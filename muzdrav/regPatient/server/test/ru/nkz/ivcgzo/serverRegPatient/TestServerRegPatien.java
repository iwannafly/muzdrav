package ru.nkz.ivcgzo.serverRegPatient;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;

/**
 * @author Avdeev Alexander
 */
public class TestServerRegPatien {
    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.66", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerRegPatient testServer;
    private static final int COUNT_CONNECTIONS = 5;
    private PatientBrief testPatientFull;
    private PatientBrief testPatientEmpty;

    @Rule
    public ExpectedException testException = ExpectedException.none();

    @Before
    public final void setUp() throws Exception {
        sse = new SqlSelectExecutor(conn, "postgres", "postgres");
        tse = new TransactedSqlManager(conn, "postgres", "postgres", COUNT_CONNECTIONS);
        testServer = new ServerRegPatient(sse, tse);
        testPatientFull = new PatientBrief();
        testPatientFull.setIm("СЕРГЕЙ");
        testPatientEmpty = new PatientBrief();
        testPatientEmpty.setNpasp(-1);
    }

    @Test
    public void testStart() {
    }

    @Test
    public void testStop() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public void testServerRegPatient() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetAllPatientBrief()
            throws TException, PatientNotFoundException {
        final int expectedListSize = 312;
        java.util.List <PatientBrief> testPatientList;
        testPatientList = testServer.getAllPatientBrief(testPatientFull);
        assertEquals("list size", expectedListSize, testPatientList.size());
        assertEquals("elemet 0 city field", "НОВОКУЗНЕЦК Г.",
                testPatientList.get(0).getAdpAddress().getCity());

        testPatientList.clear();
        testException.expect(PatientNotFoundException.class);
        testPatientList = testServer.getAllPatientBrief(testPatientEmpty);
        testPatientList.clear();
    }
}

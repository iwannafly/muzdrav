package ru.nkz.ivcgzo.serverRegPatient;

import junit.framework.TestCase;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;

public class TestServerRegPatien extends TestCase {
    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.66", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerRegPatient srp;
    private static final int COUNT_CONNECTIONS = 5;

    @Before
    public final void setUp() throws Exception {
        sse = new SqlSelectExecutor(conn, "postgres", "postgres");
        tse = new TransactedSqlManager(conn, "postgres", "postgres", COUNT_CONNECTIONS);
        srp = new ServerRegPatient(sse, tse);
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
    public final void testGetAllPatientBrief() throws TException {
        java.util.List <PatientBrief> lPb;
        PatientBrief patient = new PatientBrief();
        //patient.setNpasp(1);
        patient.setIm("Иван");
        lPb = srp.getAllPatientBrief(patient);
        //assertNotNull(lPb);
        System.out.println(lPb.size());
        System.out.println(lPb.get(0).getSpolis());
        System.out.println(lPb.get(0).getAdpAddress().getCity());
        //assertEquals(lPb.size(),4);
        //assertTrue(lPb.size()>0);
    }
}

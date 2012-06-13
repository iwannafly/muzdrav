package ru.nkz.ivcgzo.serverGenTalons;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;

/**
 * @author Avdeev Alexander
 */
public class TestServerGenTalons {

    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.66", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerGenTalons testServer;
    private static final int COUNT_CONNECTIONS = 1;

    @Rule
    public ExpectedException testException = ExpectedException.none();

    @Before
    public final void setUp() throws Exception {
        sse = new SqlSelectExecutor(conn, "postgres", "postgres");
        tse = new TransactedSqlManager(conn, "postgres", "postgres", COUNT_CONNECTIONS);
        testServer = new ServerGenTalons(sse, tse);
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
    public final void getAllSpecForPolikliniki_isListSizeCorrect() throws KmiacServerException {
        final int expectedListSize = 5;
		final int cLpu = 20;
        java.util.List <Spec> testSpecList =
                testServer.getAllSpecForPolikliniki(cLpu);
        assertEquals("list size", expectedListSize, testSpecList.size());
    }

    @Test
    public final void getVrachForCurrentSpec_isListSizeCorrect() throws KmiacServerException {
        final int expectedListSize = 2;
        final int cLpu = 20;
        final String cdol = "9";
        java.util.List <Vrach> testVrachList =
                testServer.getVrachForCurrentSpec(cLpu, cdol);
        assertEquals("list size", expectedListSize, testVrachList.size());
    }
}

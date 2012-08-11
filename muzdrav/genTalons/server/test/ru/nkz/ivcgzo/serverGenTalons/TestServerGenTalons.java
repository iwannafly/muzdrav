package ru.nkz.ivcgzo.serverGenTalons;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
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
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.Calend;
import ru.nkz.ivcgzo.thriftGenTalon.CalendNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.NdvNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.NormNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.NraspNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.RaspNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;
import ru.nkz.ivcgzo.thriftGenTalon.VrachNotFoundException;

/**
 * @author Avdeev Alexander
 */
public class TestServerGenTalons {

    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.242", "5432", "zabol");
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
    public final void getAllSpecForPolikliniki_isListSizeCorrect()
            throws KmiacServerException, TException, SpecNotFoundException {
        final int expectedListSize = 3;
        final int cLpu = 2000004;
        java.util.List <Spec> testSpecList =
                testServer.getAllSpecForPolikliniki(cLpu);
        assertEquals("list size", expectedListSize, testSpecList.size());
    }

    @Test
    public final void getVrachForCurrentSpec_isListSizeCorrect()
            throws KmiacServerException, TException, VrachNotFoundException {
        final int expectedListSize = 1;
        final int cLpu = 2000004;
        final String cdol = "9";
        java.util.List <Vrach> testVrachList =
                testServer.getVrachForCurrentSpec(cLpu, cdol);
        assertEquals("list size", expectedListSize, testVrachList.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getCalendar_isValueCorrect()
            throws KmiacServerException, TException, CalendNotFoundException {
        final int nweek = 52;
        final long cdate = new Date(112, 0, 1).getTime();
        Calend testCalendar =
                testServer.getCalendar(cdate);
        assertEquals("list nweek value", nweek, testCalendar.getNweek());
    }

    @Test
    public final void getNorm_isListSizeCorrect()
            throws KmiacServerException, TException, NormNotFoundException {
        final int expectedListSize = 4;
        final int cpodr = 2000004;
        final String cdol = "9";
        java.util.List <Norm> testNorm =
                testServer.getNorm(cpodr, cdol);
        assertEquals("list size", expectedListSize, testNorm.size());
    }

    @Test
    public final void getNdv_isListSizeCorrect()
            throws KmiacServerException, TException, NdvNotFoundException {
        final int expectedListSize = 2;
        final int cpodr = 2000004;
        final int pcodvrach = 6;
        final String cdol = "9";
        java.util.List <Ndv> testNdv =
                testServer.getNdv(cpodr, pcodvrach, cdol);
        assertEquals("list size", expectedListSize, testNdv.size());
    }

    @Test
    public final void getNrasp_isListSizeCorrect()
            throws KmiacServerException, TException, NraspNotFoundException {
        final int expectedListSize = 1;
        final int cpodr = 2000004;
        final int pcodvrach = 6;
        final String cdol = "9";
        final int cxema = 0;
        java.util.List <Nrasp> testNrasp =
                testServer.getNrasp(cpodr, pcodvrach, cdol, cxema);
        assertEquals("list size", expectedListSize, testNrasp.size());
    }

    @Test
    public final void getRasp_isListSizeCorrect()
            throws KmiacServerException, TException, RaspNotFoundException {
        final int expectedListSize = 1;
        final int cpodr = 2000004;
        final int pcodvrach = 6;
        final String cdol = "9";
        java.util.List <Rasp> testRasp =
                testServer.getRasp(cpodr, pcodvrach, cdol);
        assertEquals("list size", expectedListSize, testRasp.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getTalon_isListSizeCorrect()
            throws KmiacServerException, TException, TalonNotFoundException {
        final int expectedListSize = 1;
        final int cpodr = 2000004;
        final int pcodvrach = 6;
        final String cdol = "9";
        final long datap = new Date(112,2,27).getTime();
        java.util.List <Talon> testTalon =
                testServer.getTalon(cpodr, pcodvrach, cdol, datap);
        assertEquals("list size", expectedListSize, testTalon.size());
    }

    @Test
    public final void getVidp_isListSizeCorrect()
            throws KmiacServerException, TException, VidpNotFoundException {
        final int expectedListSize = 3;
        java.util.List <IntegerClassifier> testVidp =
                testServer.getVidp();
        assertEquals("list size", expectedListSize, testVidp.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getTalonCountCpodr_IsValueCorrect() throws KmiacServerException, TException {
        final int expectedCount = 1;
        final long daten = new Date(112,2,27).getTime();
        final long datek = new Date(112,3,13).getTime();
        final int cpodr = 2000004;
        int talonCount = testServer.getTalonCountCpodr(daten, datek, cpodr);
        assertEquals("talon's count", expectedCount, talonCount);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getTalonCountCdol_IsValueCorrect() throws KmiacServerException, TException {
        final int expectedCount = 1;
        final long daten = new Date(112,2,27).getTime();
        final long datek = new Date(112,3,13).getTime();
        final int cpodr = 2000004;
        final String cdol = "9";
        int talonCount = testServer.getTalonCountCdol(daten, datek, cpodr, cdol);
        assertEquals("talon's count", expectedCount, talonCount);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getTalonCountVrach_IsValueCorrect() throws KmiacServerException, TException {
        final int expectedCount = 1;
        final long daten = new Date(112,2,27).getTime();
        final long datek = new Date(112,3,13).getTime();
        final int cpodr = 2000004;
        final String cdol = "9";
        final int pcodvrach = 6;
        int talonCount = testServer.getTalonCountVrach(daten, datek, cpodr, pcodvrach, cdol);
        assertEquals("talon's count", expectedCount, talonCount);
    }

    @Test
    public final void addRasp_IsActuallyAdded() throws KmiacServerException, TException {
        List<Rasp> testList = new ArrayList<>();
        testList.add(new Rasp(1, 13, 2, 3, 0, 0, 0, 5, "L100", 2, 2, true));
        testList.add(new Rasp(1, 13, 2, 3, 0, 0, 0, 5, "K100", 2, 2, true));
        testServer.addRasp(testList);
    }

    @Test
    public final void updateNrasp_IsActuallyUpdated() throws KmiacServerException, TException {
        List<Nrasp> testList = new ArrayList<>();
        testList.add(new Nrasp(6, 1, 1, 0, 0, 0,
                "9", 200004, 3, true, 0, 0));
        testServer.updateNrasp(testList);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void deleteTalonVrach_IsActuallyDeleted() throws KmiacServerException, TException {
        testServer.deleteTalonVrach(new Date(112,2,26).getTime(), new Date(112,2,28).getTime(), 2000004, 6, "9");
    }
}

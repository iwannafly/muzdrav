package ru.nkz.ivcgzo.serverRegPatient;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftRegPatient.Agent;
import ru.nkz.ivcgzo.thriftRegPatient.AgentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Gosp;
import ru.nkz.ivcgzo.thriftRegPatient.GospAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.GospNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Info;
import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Sign;

/**
 * @author Avdeev Alexander
 */
public class TestServerRegPatient {
    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.242", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerRegPatient testServer;
    private static final int COUNT_CONNECTIONS = 1;
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
    public final void getAllPatientBrief_isListSizeCorrect()
            throws TException, PatientNotFoundException {
        final int expectedListSize = 308;
        java.util.List <PatientBrief> testPatientList =
                testServer.getAllPatientBrief(testPatientFull);
        assertEquals("list size", expectedListSize, testPatientList.size());
    }

    @Test
    public final void getAllPatientBrief_isListValueCorrect()
            throws TException, PatientNotFoundException {
        java.util.List <PatientBrief> testPatientList =
                testServer.getAllPatientBrief(testPatientFull);
        assertEquals("elemet 0 city field", "НОВОКУЗНЕЦК Г.",
                testPatientList.get(0).getAdpAddress().getCity());
    }

    @Test
    public final void getAllPatientBrief_isThrowNotFoundException()
            throws TException, PatientNotFoundException {
        testException.expect(PatientNotFoundException.class);
        java.util.List <PatientBrief> testPatientList =
                testServer.getAllPatientBrief(testPatientEmpty);
        testPatientList.clear();
    }

    @Test
    public final void getPatientFullInfo_returnValueIsNotNull()
            throws TException, PatientNotFoundException {
        int npasp = 2;
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        assertNotNull("return value is not null", patientFullInfo);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getPatientFullInfo_isFieldValueCorrect()
            throws TException, PatientNotFoundException {
        int npasp = 2;
        final int pomsStrg = 9;
        final int nambkIshod = 5;
        final int terp = 10;
        final Date birthDate = new Date(101, 4, 4);
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        assertEquals("npasp value", 2, patientFullInfo.getNpasp());
        assertEquals("fam value", "Уникальная_фамилия", patientFullInfo.getFam());
        assertEquals("im value", "ДИАНА", patientFullInfo.getIm());
        assertEquals("ot value", "АЛЕКСЕЕВНА", patientFullInfo.getOt());
        assertEquals("datar value", birthDate,
                new Date(patientFullInfo.getDatar()));
        assertEquals("pol value", 2, patientFullInfo.getPol());
        assertEquals("terp value", terp, patientFullInfo.getTerp());
        assertEquals("name_mr value", "555555", patientFullInfo.getNamemr());
        assertEquals("poms_nom value", "43090551104",
                patientFullInfo.getPolis_oms().getNom());
        assertEquals("pol value", 2, patientFullInfo.getPol());
        assertEquals("jitel value", 1, patientFullInfo.getJitel());
        assertEquals("adp_obl value", "КЕМЕРОВСКАЯ ОБЛАСТЬ",
                patientFullInfo.getAdpAddress().getRegion());
        assertEquals("adp_gorod value", "НОВОКУЗНЕЦК Г.",
                patientFullInfo.getAdpAddress().getCity());
        assertEquals("adp_ul value", "НОВОСЕЛОВ",
                patientFullInfo.getAdpAddress().getStreet());
        assertEquals("adp_dom value", "39", patientFullInfo.getAdpAddress().getHouse());
        assertEquals("adp_kv value", "107", patientFullInfo.getAdpAddress().getFlat());
        assertEquals("adm_obl value", "КЕМЕРОВСКАЯ ОБЛАСТЬ",
                patientFullInfo.getAdmAddress().getRegion());
        assertEquals("adm_gorod value", "НОВОКУЗНЕЦК Г.",
                patientFullInfo.getAdmAddress().getCity());
        assertEquals("adm_ul value", "НОВОСЕЛОВ",
                patientFullInfo.getAdmAddress().getStreet());
        assertEquals("adm_dom value", "39", patientFullInfo.getAdmAddress().getHouse());
        assertEquals("adm_kv value", "107", patientFullInfo.getAdmAddress().getFlat());
        assertEquals("poms_strg value", pomsStrg,
                patientFullInfo.getPolis_oms().getStrg());
        assertEquals("nambk_npasp", 2,
                patientFullInfo.getNambk().getNpasp());
        assertEquals("nambk_nambk", "намбк2",
                patientFullInfo.getNambk().getNambk());
        assertEquals("nambk_ishod", nambkIshod,
                patientFullInfo.getNambk().getIshod());
    }

    @Test
    public final void getPatientFullInfo_isThrowNotFoundException()
            throws TException, PatientNotFoundException {
        int npasp = -1;
        testException.expect(PatientNotFoundException.class);
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        patientFullInfo.clear();
    }

    @Test
    public final void getAgent_returnValueIsNotNull()
            throws TException, AgentNotFoundException {
        int npasp = 2;
        Agent agent =
                testServer.getAgent(npasp);
        assertNotNull("return value is not null", agent);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getAgent_isFieldValueCorrect()
            throws TException, AgentNotFoundException {
        final int npasp = 2;
        final int vpolis = 3;
        final int tdoc = 5;
        final Date birthDate = new Date(85, 1, 5);
        Agent agent =
                testServer.getAgent(npasp);
        assertEquals("npasp value", npasp, agent.getNpasp());
        assertEquals("fam value", "Представителев", agent.getFam());
        assertEquals("im value", "Представитель", agent.getIm());
        assertEquals("ot value", "Представитеевич", agent.getOt());
        assertEquals("datar value", birthDate, new Date(agent.getDatar()));
        assertEquals("pol value", 1, agent.getPol());
        assertEquals("name_str value", "нэйм_эстээр", agent.getName_str());
        assertEquals("ogrn_str value", "огэрээн", agent.getOgrn_str());
        assertEquals("vpolis value", vpolis, agent.getVpolis());
        assertEquals("spolis value", "сполис", agent.getSpolis());
        assertEquals("npolis value", "нполис", agent.getNpolis());
        assertEquals("tdoc value", tdoc, agent.getTdoc());
        assertEquals("docser value", "доксер", agent.getDocser());
        assertEquals("docnum value", "докнум", agent.getDocnum());
        assertEquals("birthplace value", "бертхплэйс", agent.getBirthplace());
    }

    @Test
    public final void getAgent_isThrowNotFoundException()
            throws TException, AgentNotFoundException {
        int npasp = -1;
        testException.expect(AgentNotFoundException.class);
        Agent agent = testServer.getAgent(npasp);
        agent.clear();
    }

    @Test
    public final void getKontingent_isListSizeCorrect()
            throws TException, KontingentNotFoundException {
        int npasp = 2;
        List<Kontingent> kontingent =
                testServer.getKontingent(npasp);
        assertEquals("list size", 4, kontingent.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getKontingent_isFieldValueCorrect()
            throws TException, KontingentNotFoundException {
        final int npasp = 2;
        final Date birthDate1 = new Date(101, 0, 1);
        final Date birthDate2 = new Date(102, 1, 2);
        List<Kontingent> kontingent =
                testServer.getKontingent(npasp);
        assertEquals("id value", 2, kontingent.get(0).getId());
        assertEquals("npasp value", npasp, kontingent.get(0).getNpasp());
        assertEquals("kateg value", 2, kontingent.get(0).getKateg());
        assertEquals("datau value", birthDate1, new Date(kontingent.get(0).getDatau()));
        assertEquals("name value", "нэйм1", kontingent.get(0).getName());
        assertEquals("id value", 2, kontingent.get(1).getId());
        assertEquals("npasp value", npasp, kontingent.get(1).getNpasp());
        assertEquals("kateg value", 2, kontingent.get(1).getKateg());
        assertEquals("datau value", birthDate2, new Date(kontingent.get(1).getDatau()));
        assertEquals("name value", "нэйм2", kontingent.get(1).getName());
    }

    @Test
    public final void getKontingent_isThrowNotFoundException()
            throws TException, KontingentNotFoundException {
        int npasp = -1;
        testException.expect(KontingentNotFoundException.class);
        List<Kontingent> kontingent =
                testServer.getKontingent(npasp);
        kontingent.clear();
    }

    @Test
    public final void getGosp_isFieldValueCorrect()
            throws TException, GospNotFoundException {
        final int id = 1;
        Gosp testGosp =
                testServer.getGosp(id);
        assertEquals("npasp value", 2, testGosp.getNpasp());
    }

    @Test
    public final void addGosp_isGospActuallyAdded()
            throws TException, GospNotFoundException, GospAlreadyExistException {
        final int id = 1;
        Gosp testGosp = testServer.getGosp(id);
        testGosp.ngosp = 3;
        System.out.println(testGosp.npasp);
        System.out.println(testGosp.nist);
        System.out.println(testGosp.datap);
        System.out.println(testGosp.vremp);
        System.out.println(testGosp.naprav);
        System.out.println(testGosp.pl_extr);
        @SuppressWarnings("unused")
        int i = testServer.addGosp(testGosp);
    }

    @Test
    public final void isPatientExist_isThrowAlreadyExistException()
            throws TException, PatientNotFoundException, PatientAlreadyExistException {
        int npasp = 2;
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        testException.expect(PatientAlreadyExistException.class);
        int afterAddId = testServer.addPatient(patientFullInfo);
        assertEquals(afterAddId, 0);
    }

    @Test
    public final void addPatient_isPatientActuallyAdded()
            throws TException, PatientNotFoundException, PatientAlreadyExistException {
        int npasp = 2;
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        patientFullInfo.setFam("Уникальная_фамилия");
        @SuppressWarnings("unused")
        int afterAddId = testServer.addPatient(patientFullInfo);
        //assertEquals(afterAddId, 0);
    }

    @Test
    public final void isKontingentExist_isThrowAlreadyExistException()
            throws TException, KontingentNotFoundException, KontingentAlreadyExistException {
        int npasp = 2;
        List<Kontingent> kontingent =
                testServer.getKontingent(npasp);
        testException.expect(KontingentAlreadyExistException.class);
        @SuppressWarnings("unused")
        Info afterAddId = testServer.addKont(kontingent.get(0));
        //assertEquals(afterAddId, 0);
    }

    @Test
    public final void addKontingent_isKontingentActuallyAdded()
            throws TException, KontingentNotFoundException, KontingentAlreadyExistException {
        int npasp = 2;
        List<Kontingent> kontingent =
                testServer.getKontingent(npasp);
        kontingent.get(0).setKateg((short) 12);
        @SuppressWarnings("unused")
        Info afterAddId = testServer.addKont(kontingent.get(0));
    }

    @Test
    public final void addSign_isSignActuallyAdded()
            throws TException {
        Sign s =new Sign();
        s.setNpasp(26006);
        s.setGrup("9");
        s.setPh("1");
        s.setAllerg("9");
        s.setFarmkol("9");
        s.setVitae("9");
        s.setVred("9");
        testServer.addOrUpdateSign(s);
    }

    @Test
    public final void deleteGosp_isGospActuallyDeleted()
            throws TException {
        int id = 2;
        testServer.deleteGosp(id);
    }

    @Test
    public final void updatePatient_isPatientActuallyUpdated()
            throws TException, PatientNotFoundException, PatientAlreadyExistException {
        int npasp = 2;
        PatientFullInfo patientFullInfo =
                testServer.getPatientFullInfo(npasp);
        patientFullInfo.setFam("Уникальная_фамилия");
        testServer.updatePatient(patientFullInfo);
        //assertEquals(afterAddId, 0);
    }

    @Test
    public final void updateOgrn_isActuallyUpdated() throws TException {
        int npasp = 5;
        testServer.updateOgrn(npasp);
    }
//    @Test
//    public final void isAgentExist_isThrowAlreadyExistException()
//            throws TException, AgentNotFoundException, AgentAlreadyExistException {
//        int npasp = 2;
//        Agent agent = testServer.getAgent(npasp);
//        testException.expect(AgentAlreadyExistException.class);
//        testServer.addAgent(agent);
//        //assertEquals(afterAddId, 0);
//    }
//
//    @Test
//    public final void addAgent_isAgentActuallyAdded()
//            throws TException, AgentNotFoundException, AgentAlreadyExistException {
//        int npasp = 2;
//        Agent agent = testServer.getAgent(npasp);
//        agent.setNpasp(5);
//        testServer.addAgent(agent);
//    }

}

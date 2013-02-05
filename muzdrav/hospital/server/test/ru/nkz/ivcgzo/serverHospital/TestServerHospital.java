package ru.nkz.ivcgzo.serverHospital;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

/**
 * @author Avdeev Alexander
 */
public class TestServerHospital {

    private String conn = String.format("jdbc:postgresql://%s:%s/%s",
            "10.0.0.242", "5432", "zabol");
    private ISqlSelectExecutor sse;
    private ITransactedSqlExecutor tse;
    private ServerHospital testServer;
    private static final int COUNT_CONNECTIONS = 1;

    @Before
    public final void setUp() throws Exception {
        sse = new SqlSelectExecutor(conn, "postgres", "postgres");
        tse = new TransactedSqlManager(conn, "postgres", "postgres", COUNT_CONNECTIONS);
        testServer = new ServerHospital(sse, tse);
    }

    @Test
    public void testStart() {
    }

    @Test
    public void testStop() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getAllPatientForDoctor_isListSizeCorrect()
            throws KmiacServerException, TException, PatientNotFoundException {
        final int expectedListSize = 1;
        final int doctorId = 42;
        final int otdNum = 2005;
        java.util.List <TSimplePatient> patientList =
                testServer.getAllPatientForDoctor(doctorId, otdNum);
        System.out.print("patient = " + doctorId + " lll " + patientList.get(0));
        assertEquals("list size", expectedListSize, patientList.size());
    }

    @Test
    public final void getAllPatientFromOtd_isListSizeCorrect()
            throws KmiacServerException, TException, PatientNotFoundException {
        final int expectedListSize = 1;
        final int otdNum = 2005;
        java.util.List <TSimplePatient> patientList =
                testServer.getAllPatientFromOtd(otdNum);
        assertEquals("list size", expectedListSize, patientList.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getPatientPersonalInfo_isInfoCorrect()
            throws KmiacServerException, TException, PatientNotFoundException {
        final int patientId = 450; //10001;
        final int idGosp = 25;
        TPatient patient =
                testServer.getPatientPersonalInfo(idGosp);
        System.out.print(patient);
        final int cGosp = 4;
        final Date birthdate = new Date(104, 4, 1); // 2004-05-01
        final String fam = "РЫМАНОВ";
        final String im = "СЕРГЕЙ";
        final String ot = "АЛЕКСАНДРОВИЧ";
        final int sgrp = 9;
        final String poms = null;
        assertEquals("patientId", patientId, patient.getPatientId());
        assertEquals("id_gosp", cGosp, patient.getGospitalCod());
        assertEquals("birthdate", birthdate, new Date(patient.getBirthDate()));
        assertEquals("fam", fam, patient.getSurname());
        assertEquals("im", im, patient.getName());
        assertEquals("ot", ot, patient.getMiddlename());
        assertEquals("sgrp", sgrp, patient.getStatus());
        assertEquals("poms", poms, patient.getOms());
    }

    @Test
    public final void updatePatientChamberNumber_isActuallyUpdated() throws TException {
        final int gospId = 4;
        final int chamberNum = 108;
        final int profPcod = 123;
        final int nist = 1;
        testServer.updatePatientChamberNumber(gospId, chamberNum, profPcod, nist);
    }

    @Test
    public final void addPatientToDoctor_isActuallyAdded() throws KmiacServerException {
        final int gospId = 8;
        final int doctorId = 45;
        final int stationType = 1;
        testServer.addPatientToDoctor(gospId, stationType, doctorId);
    }

//    @SuppressWarnings("deprecation")
//    @Test
//    public final void getMainDiagnosis_isValueCorrect()
//            throws TException, DiagnosisNotFoundException {
//        final int id = 1;
//        final int gospId = 4;
//        final String diag = "K12.1";
//        final int prizn = 1;
//        final int ustan = 4;
//        final String named = "основной диагноз";
//        final Date dataz = new Date(112, 5, 1);
//        TDiagnosis diagnoz = testServer.getMainDiagnosis(gospId);
//        assertEquals("id", id, diagnoz.getId());
//        assertEquals("id_gosp", gospId, diagnoz.getId_gosp());
//        assertEquals("diag", diag, diagnoz.getDiag());
//        assertEquals("prizn", prizn, diagnoz.getPrizn());
//        assertEquals("ustan", ustan, diagnoz.getUstan());
//        assertEquals("named", named, diagnoz.getNamed());
//        assertEquals("dataz", dataz, new Date(diagnoz.getDataz()));
//    }
}

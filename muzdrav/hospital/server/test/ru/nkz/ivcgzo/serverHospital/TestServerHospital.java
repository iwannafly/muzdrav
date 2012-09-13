package ru.nkz.ivcgzo.serverHospital;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.TransactedSqlManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.ComplaintNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DesiaseHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.ObjectiveStateNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.SpecialStateNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TComplaint;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
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
        TPatient patient =
                testServer.getPatientPersonalInfo(patientId);
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
        testServer.updatePatientChamberNumber(gospId, chamberNum);
    }

    @Test
    public final void addPatientToDoctor_isActuallyAdded() throws TException {
        final int gospId = 8;
        final int doctorId = 45;
        testServer.addPatientToDoctor(gospId, doctorId);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getLifeHistory_isValueCorrect()
            throws TException, LifeHistoryNotFoundException {
        final int id = 1;
        final int gospId = 4;
        final String text = "история жизни";
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory lifeHistory = testServer.getLifeHistory(gospId);
        assertEquals("id", id, lifeHistory.getId());
        assertEquals("id_gosp", gospId, lifeHistory.getId_gosp());
        assertEquals("text", text, lifeHistory.getText());
        assertEquals("dataz", dataz, new Date(lifeHistory.getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getDesiaseHistory_isValueCorrect()
            throws TException, DesiaseHistoryNotFoundException {
        final int id = 1;
        final int gospId = 4;
        final String text = "история болезни";
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory desiaseHistory = testServer.getDesiaseHistory(gospId);
        assertEquals("id", id, desiaseHistory.getId());
        assertEquals("id_gosp", gospId, desiaseHistory.getId_gosp());
        assertEquals("text", text, desiaseHistory.getText());
        assertEquals("dataz", dataz, new Date(desiaseHistory.getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getObjectiveState_isValueCorrect()
            throws TException, ObjectiveStateNotFoundException {
        final int id = 1;
        final int gospId = 4;
        final String text = "объективный статус";
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory objectiveState = testServer.getObjectiveState(gospId);
        assertEquals("id", id, objectiveState.getId());
        assertEquals("id_gosp", gospId, objectiveState.getId_gosp());
        assertEquals("text", text, objectiveState.getText());
        assertEquals("dataz", dataz, new Date(objectiveState.getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getSpecialState_isValueCorrect()
            throws TException, SpecialStateNotFoundException {
        final int id = 2;
        final int gospId = 4;
        final String text = "специальный статус";
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory specialState = testServer.getSpecialState(gospId);
        assertEquals("id", id, specialState.getId());
        assertEquals("id_gosp", gospId, specialState.getId_gosp());
        assertEquals("text", text, specialState.getText());
        assertEquals("dataz", dataz, new Date(specialState.getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void updateLifeHistory_isActuallyUpdated()
            throws TException {
        final int gospId = 4;
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory lifeHistory = new TMedicalHistory();
        lifeHistory.setText("история жизни");
        lifeHistory.setDataz(dataz.getTime());
        testServer.updateLifeHistory(gospId, lifeHistory);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void updateDesiaseHistory_isActuallyUpdated()
            throws TException {
        final int gospId = 4;
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory desiaseHistory = new TMedicalHistory();
        desiaseHistory.setText("история болезни");
        desiaseHistory.setDataz(dataz.getTime());
        testServer.updateDesiaseHistory(gospId, desiaseHistory);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void updateObjectiveState_isActuallyUpdated()
            throws TException {
        final int gospId = 4;
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory objectiveState = new TMedicalHistory();
        objectiveState.setText("объективный статус");
        objectiveState.setDataz(dataz.getTime());
        testServer.updateObjectiveState(gospId, objectiveState);
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void updateSpecialState_isActuallyUpdated()
            throws TException {
        final int gospId = 4;
        final Date dataz = new Date(112, 5, 1);
        TMedicalHistory specialState = new TMedicalHistory();
        specialState.setText("специальный статус");
        specialState.setDataz(dataz.getTime());
        testServer.updateSpecialState(gospId, specialState);
    }

    @Test
    public final void getComplaints_isListSizeCorrect()
            throws KmiacServerException, TException, ComplaintNotFoundException {
        final int expectedListSize = 1;
        final int gospId = 4;
        java.util.List <TComplaint> complaintList =
                testServer.getComplaints(gospId);
        assertEquals("list size", expectedListSize, complaintList.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getMainDiagnosis_isValueCorrect()
            throws TException, DiagnosisNotFoundException {
        final int id = 1;
        final int gospId = 4;
        final String diag = "K12.1";
        final int prizn = 1;
        final int ustan = 4;
        final String named = "основной диагноз";
        final Date dataz = new Date(112, 5, 1);
        TDiagnosis diagnoz = testServer.getMainDiagnosis(gospId);
        assertEquals("id", id, diagnoz.getId());
        assertEquals("id_gosp", gospId, diagnoz.getId_gosp());
        assertEquals("diag", diag, diagnoz.getDiag());
        assertEquals("prizn", prizn, diagnoz.getPrizn());
        assertEquals("ustan", ustan, diagnoz.getUstan());
        assertEquals("named", named, diagnoz.getNamed());
        assertEquals("dataz", dataz, new Date(diagnoz.getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getAccompDiagnosis_isValueCorrect()
            throws TException, DiagnosisNotFoundException {
        final int id = 4;
        final int gospId = 4;
        final String diag = "K18.3";
        final int prizn = 2;
        final int ustan = 3;
        final String named = "сопутствующий диагноз";
        final Date dataz = new Date(112, 5, 1);
        List<TDiagnosis> diagList = testServer.getAccompDiagnosis(gospId);
        assertEquals("id", id, diagList.get(0).getId());
        assertEquals("id_gosp", gospId, diagList.get(0).getId_gosp());
        assertEquals("diag", diag, diagList.get(0).getDiag());
        assertEquals("prizn", prizn, diagList.get(0).getPrizn());
        assertEquals("ustan", ustan, diagList.get(0).getUstan());
        assertEquals("named", named, diagList.get(0).getNamed());
        assertEquals("dataz", dataz, new Date(diagList.get(0).getDataz()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void getCopmlication_isValueCorrect()
            throws TException, DiagnosisNotFoundException {
        final int id = 5;
        final int gospId = 4;
        final String diag = "K22.5";
        final int prizn = 3;
        final int ustan = 5;
        final String named = "осложнение";
        final Date dataz = new Date(112, 5, 1);
        List<TDiagnosis> diagList = testServer.getCopmlication(gospId);
        assertEquals("id", id, diagList.get(0).getId());
        assertEquals("id_gosp", gospId, diagList.get(0).getId_gosp());
        assertEquals("diag", diag, diagList.get(0).getDiag());
        assertEquals("prizn", prizn, diagList.get(0).getPrizn());
        assertEquals("ustan", ustan, diagList.get(0).getUstan());
        assertEquals("named", named, diagList.get(0).getNamed());
        assertEquals("dataz", dataz, new Date(diagList.get(0).getDataz()));
    }
}

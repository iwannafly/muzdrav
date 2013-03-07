package ru.nkz.ivcgzo.clientHospital.model;

import java.io.IOException;
import java.util.List;

import ru.nkz.ivcgzo.clientHospital.controllers.HospitalDataTransferException;
import ru.nkz.ivcgzo.clientHospital.model.observers.IDiagnosisObserver;
import ru.nkz.ivcgzo.clientHospital.model.observers.IDiaryRecordObserver;
import ru.nkz.ivcgzo.clientHospital.model.observers.IPatientObserver;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

//TODO удалить лишнее
/**
 * Интерфейс модели.
 * Модель активная - все вьюхи могут получать данные из модели
 * get методами (но не менять состояние модели!) от интерфейсной ссылки на модель.
 */
public interface IHospitalModel {

    TPatient getPatient();

    void setPatient(int patientGospId) throws PatientNotFoundException,
        HospitalDataTransferException;

    void setPatientByCotd(int idCotd) throws PatientNotFoundException,
        HospitalDataTransferException;

    void registerPatientObserver(IPatientObserver obs);

    void removePatientObserver(IPatientObserver obs);

    TPriemInfo getPriemInfo();

    TMedicalHistory getPerOsmotr();

    void setPriemInfo();

    List<IntegerClassifier> getOtdProfs();

    void updatePatient(String chamber, int otdProf, String surname,
        String name, String middlename) throws HospitalDataTransferException;

    List<IntegerClassifier> loadMedicalHistoryShablons();

    List<IntegerClassifier> loadMedicalHistoryShablons(String srcText);

    Shablon loadShablon(int idSh);

    List<IntegerClassifier> getShablonBySelectedDiagnosis(String diag, String srcText);

    List<StringClassifier> getShablonDiagnosis(String srcText);

    List<TMedicalHistory> getDiaryList();

    void setDiaryList() throws HospitalDataTransferException;

    TMedicalHistory getMedicalHistory();

    void deleteMedicalHistory(TMedicalHistory currentMedHist) throws HospitalDataTransferException;

    void removeDiaryRecordObserver(IDiaryRecordObserver obs);

    void registerDiaryRecordObserver(IDiaryRecordObserver obs);

    void setMedicalHistory(TMedicalHistory currentMedicalHistory);

    void addMedicalHistory(TMedicalHistory newMedHist) throws HospitalDataTransferException;

    void updateMedicalHistory(TMedicalHistory currentMedHist) throws HospitalDataTransferException;

    void removeDiagnosisObserver(IDiagnosisObserver obs);

    void registerDiagnosisObserver(IDiagnosisObserver obs);

    TDiagnosis getCurrentDiagnosis();

    void addDiagnosis(TDiagnosis diagnosis) throws HospitalDataTransferException;

    void deleteDiagnosis(TDiagnosis diagnosis) throws HospitalDataTransferException;

    void updateDiagnosis(TDiagnosis diagnosis) throws HospitalDataTransferException;

    void setDiagnosisList() throws HospitalDataTransferException;

    List<TDiagnosis> getDiagnosisList();

    void setCurrentDiagnosis(TDiagnosis inCurrentDiagnosis);

    List<IntegerClassifier> getIshodClassifier();

    List<IntegerClassifier> getResultClassifier();

    void addZakl(Zakl tmpZakl) throws HospitalDataTransferException;

    void printOutEpicris() throws HospitalDataTransferException, IOException;

    void printDeathEpicris() throws IOException, HospitalDataTransferException;

    List<StringClassifier> getExistedDiags();

    List<IntegerClassifier> getAllOtd();

    List<IntegerClassifier> loadZaklShablons();
}

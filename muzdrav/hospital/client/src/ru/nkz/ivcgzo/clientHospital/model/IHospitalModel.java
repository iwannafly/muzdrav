package ru.nkz.ivcgzo.clientHospital.model;

import java.util.List;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;

public interface IHospitalModel {

    TPatient getPatient();

    void setPatient(int patientGospId);

    void setPatientByCotd(int idCotd);

    void registerPatientObserver(IPatientObserver obs);

    void removePatientObserver(IPatientObserver obs);

    TPriemInfo getPriemInfo();

    TMedicalHistory getPerOsmotr();

    void setPriemInfo();

    List<IntegerClassifier> getOtdProfs();

    void updatePatient(String chamber, int otdProf, String surname,
        String name, String middlename);

    List<IntegerClassifier> loadMedicalHistoryShablons();

    List<IntegerClassifier> loadMedicalHistoryShablons(String srcText);

    Shablon loadShablon(int idSh);

    List<IntegerClassifier> getShablonBySelectedDiagnosis(String diag, String srcText);

    List<StringClassifier> getShablonDiagnosis(String srcText);

    List<TMedicalHistory> getDiaryList();

    void setDiaryList();

    TMedicalHistory getMedicalHistory();

    void updateMedicalHistory();

    void deleteMedicalHistory(TMedicalHistory currentMedHist);

    void removeDiaryRecordObserver(IDiaryRecordObserver obs);

    void registerDiaryRecordObserver(IDiaryRecordObserver obs);

    void setMedicalHistory(TMedicalHistory currentMedicalHistory);

    void addMedicalHistory(TMedicalHistory newMedHist);
}

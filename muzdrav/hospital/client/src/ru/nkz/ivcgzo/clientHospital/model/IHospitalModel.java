package ru.nkz.ivcgzo.clientHospital.model;

import java.util.List;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;

public interface IHospitalModel {

    TPatient getPatient();

    void setPatient(int patientGospId);

    void registerPatientObserver(IPatientObserver obs);

    void removePatientObserver(IPatientObserver obs);

    TPriemInfo getPriemInfo();

    TMedicalHistory getPerOsmotr();

    void setPriemInfo();

    List<IntegerClassifier> getOtdProfs();

    void updatePatient(String chamber, int otdProf);

    List<IntegerClassifier> loadMedicalHistoryShablons();
}

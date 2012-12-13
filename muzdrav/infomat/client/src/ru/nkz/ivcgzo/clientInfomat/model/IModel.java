package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.List;

import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentDoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentPoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentSpecialityObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IDoctorsObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPatientObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPoliclinicsObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentIReservedTalonObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISelectedTalonObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISpecialitiesObserver;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.SheduleTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public interface IModel {
    void setPoliclinics();

    void setSpecialities(int cpol);

    void setDoctors(int cpol, String cdol);

    void setTalons(int cpol, String cdol, int pcod);

    void setPatient(String oms);

    void setReservedTalons(int patientId);

    void setShedule(int pcod, int cpol, String cdol);

    List<IntegerClassifier> getPoliclinics();

    List<StringClassifier> getSpecialities();

    TTalon getCurrentReservedTalon();

    void setCurrentReservedTalon(TTalon talon);

    List<IntegerClassifier> getDoctors();

    IntegerClassifier getCurrentPoliclinic();

    void setCurrentPoliclinic(IntegerClassifier currentPoliclinic);

    void setCurrentSpeciality(StringClassifier currentSpeciality);

    void setCurrentDoctor(IntegerClassifier currentDoctor);

    StringClassifier getCurrentSpeciality();

    IntegerClassifier getCurrentDoctor();

    TalonList getTalons();

    TalonTableModel getTalonTableModel(final int cpol, final String cdol, final int pcod);

    SheduleTableModel getSheduleTableModel(final int pcod, final int cpol, final String cdol);

    ReservedTalonTableModel getReservedTalonTableModel(final int pcod);

    TPatient getPatient();

    List<TTalon> getReservedTalons();

    List<TSheduleDay> getShedule();

    void setTalon(TTalon talon);

    TTalon getTalon();

    void reserveTalon(TPatient pat, TTalon talon);

    void releaseTalon(TTalon talon);

    void registerDoctorsObserver(IDoctorsObserver obs);

    void removeDoctorsObserver(IDoctorsObserver obs);

    void registerPoliclinicsObserver(IPoliclinicsObserver obs);

    void removePoliclinicsObserver(IPoliclinicsObserver obs);

    void registerSpecialitiesObserver(ISpecialitiesObserver obs);

    void removeSpecialitiesObserver(ISpecialitiesObserver obs);

    void registerCurrentDoctorObserver(ICurrentDoctorObserver obs);

    void removeCurrentDoctorObserver(ICurrentDoctorObserver obs);

    void registerCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs);

    void removeCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs);

    void registerCurrentSpecialityObserver(ICurrentSpecialityObserver obs);

    void removeCurrentSpecialityObserver(ICurrentSpecialityObserver obs);

    void registerPatientObserver(IPatientObserver obs);

    void removePatientObserver(IPatientObserver obs);

    void registerSelectedTalonObserver(ISelectedTalonObserver obs);

    void removeSelectedTalonObserver(ISelectedTalonObserver obs);

    void registerReservedTalonObserver(ICurrentIReservedTalonObserver obs);

    void removeReservedTalonObserver(ICurrentIReservedTalonObserver obs);
}

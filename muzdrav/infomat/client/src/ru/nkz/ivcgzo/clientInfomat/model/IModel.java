package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.List;

import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentDoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentPoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentSpecialityObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IDoctorsObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPatientObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPoliclinicsObserver;
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
    public void setPoliclinics();

    public void setSpecialities(int cpol);

    public void setDoctors(int cpol, String cdol);

    public void setTalons(int cpol, String cdol, int pcod);

    public void setPatient(String oms);

    public void setReservedTalon(int patientId);

    public void setShedule(int pcod, int cpol, String cdol);

    public List<IntegerClassifier> getPoliclinics();

    public List<StringClassifier> getSpecialities();

    public List<IntegerClassifier> getDoctors();

    public IntegerClassifier getCurrentPoliclinic();

    public void setCurrentPoliclinic(IntegerClassifier currentPoliclinic);

    public void setCurrentSpeciality(StringClassifier currentSpeciality);

    public void setCurrentDoctor(IntegerClassifier currentDoctor);

    public StringClassifier getCurrentSpeciality();

    public IntegerClassifier getCurrentDoctor();

    public TalonList getTalons();

    public TalonTableModel getTalonTableModel(final int cpol, final String cdol, final int pcod);

    public SheduleTableModel getSheduleTableModel(final int pcod, final int cpol, final String cdol);

    public ReservedTalonTableModel getReservedTalonTableModel(final int pcod);

    public TPatient getPatient();

    public List<TTalon> getReservedTalon();

    public List<TSheduleDay> getShedule();

    public void setTalon(TTalon talon);

    public TTalon getTalon();

    public void reserveTalon(TPatient pat, TTalon talon);

    public void releaseTalon(TTalon talon);

    public void registerDoctorsObserver(IDoctorsObserver obs);

    public void removeDoctorsObserver(IDoctorsObserver obs);

    public void registerPoliclinicsObserver(IPoliclinicsObserver obs);

    public void removePoliclinicsObserver(IPoliclinicsObserver obs);

    public void registerSpecialitiesObserver(ISpecialitiesObserver obs);

    public void removeSpecialitiesObserver(ISpecialitiesObserver obs);

    public void registerCurrentDoctorObserver(ICurrentDoctorObserver obs);

    public void removeCurrentDoctorObserver(ICurrentDoctorObserver obs);

    public void registerCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs);

    public void removeCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs);

    public void registerCurrentSpecialityObserver(ICurrentSpecialityObserver obs);

    public void removeCurrentSpecialityObserver(ICurrentSpecialityObserver obs);

    public void registerPatientObserver(IPatientObserver obs);

    public void removePatientObserver(IPatientObserver obs);

    public void registerSelectedTalonObserver(ISelectedTalonObserver obs);

    public void removeSelectedTalonObserver(ISelectedTalonObserver obs);
}

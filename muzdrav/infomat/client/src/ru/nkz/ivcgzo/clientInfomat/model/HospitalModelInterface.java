package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.List;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public interface HospitalModelInterface {
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

    public TalonList getTalons();

    public TPatient getPatient();

    public List<TTalon> getReservedTalon();

    public List<TSheduleDay> getShedule();

    public void reserveTalon(TPatient pat, TTalon talon);

    public void releaseTalon(TTalon talon);
}

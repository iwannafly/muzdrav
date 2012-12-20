package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.model.observers.IInfomatObserver;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.SheduleTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public interface IModel {
    void setPoliclinics() throws TException;

    void setSpecialities(int cpol) throws TException;

    void setDoctors(int cpol, String cdol) throws TException;

    void setTalons(int cpol, String cdol, int pcod) throws TException;

    void setPatient(String oms) throws TException;

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

    TalonTableModel getTalonTableModel(
        final int cpol, final String cdol, final int pcod) throws TException;

    SheduleTableModel getSheduleTableModel(
        final int pcod, final int cpol, final String cdol) throws TException;

    ReservedTalonTableModel getReservedTalonTableModel(final int pcod) throws TException;

    TPatient getPatient();

    List<TTalon> getReservedTalons();

    List<TSheduleDay> getShedule();

    void setTalon(TTalon talon);

    TTalon getTalon();

    void reserveTalon(TPatient pat, TTalon talon);

    void releaseTalon(TTalon talon);

    void registerInfomatObserver(IInfomatObserver obs);

    void removeInfomatObserver(IInfomatObserver obs);
}

package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.ClientInfomat;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IDoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISpecialityObserver;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.OmsNotValidException;
import ru.nkz.ivcgzo.thriftInfomat.PatientHasSomeReservedTalonsOnThisDay;
import ru.nkz.ivcgzo.thriftInfomat.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class Model implements IModel {
    private List<IDoctorObserver> doctorObservers = new ArrayList<IDoctorObserver>();
    private List<IPoliclinicObserver> policlinicObservers = new ArrayList<IPoliclinicObserver>();
    private List<ISpecialityObserver> specialityObservers = new ArrayList<ISpecialityObserver>();
    private List<IntegerClassifier> policlinics;
    private List<StringClassifier> specialities;
    private List<IntegerClassifier> doctors;
    private TalonList talons;
    private List<TTalon> reservedTalons;
    private List<TSheduleDay> sheduleDays;
    private IntegerClassifier currentPoliclinic;
    private StringClassifier currentSpeciality;
    private IntegerClassifier currentDoctor;
    private TPatient patient;

    @Override
    public List<IntegerClassifier> getPoliclinics() {
        return policlinics;
    }

    @Override
    public List<StringClassifier> getSpecialities() {
        return specialities;
    }

    @Override
    public List<IntegerClassifier> getDoctors() {
        return doctors;
    }

    @Override
    public TalonList getTalons() {
        return talons;
    }

    @Override
    public TPatient getPatient() {
        return patient;
    }

    @Override
    public List<TTalon> getReservedTalon() {
        return reservedTalons;
    }

    @Override
    public List<TSheduleDay> getShedule() {
        return sheduleDays;
    }

    public IntegerClassifier getCurrentPoliclinic() {
        return currentPoliclinic;
    }

    public StringClassifier getCurrentSpeciality() {
        return currentSpeciality;
    }

    public IntegerClassifier getCurrentDoctor() {
        return currentDoctor;
    }

    @Override
    public void setPoliclinics() {
        try {
            policlinics = ClientInfomat.tcl.getPoliclinics();
            notifyPoliclinicObservers();
        } catch (KmiacServerException e1) {
            policlinics = Collections.<IntegerClassifier>emptyList();
            e1.printStackTrace();
        } catch (TException e1) {
            policlinics = Collections.<IntegerClassifier>emptyList();
            e1.printStackTrace();
            ClientInfomat.conMan.reconnect(e1);
        }
    }

    @Override
    public void setSpecialities(int cpol) {
        try {
            specialities = ClientInfomat.tcl.getSpecialities(cpol);
        } catch (KmiacServerException e) {
            specialities = Collections.<StringClassifier>emptyList();
            e.printStackTrace();
        } catch (TException e) {
            specialities = Collections.<StringClassifier>emptyList();
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void setDoctors(int cpol, String cdol) {
        try {
            doctors = ClientInfomat.tcl.getDoctors(cpol, cdol);
        } catch (KmiacServerException e) {
            doctors = Collections.<IntegerClassifier>emptyList();
            e.printStackTrace();
        } catch (TException e) {
            doctors = Collections.<IntegerClassifier>emptyList();
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void setTalons(int cpol, String cdol, int pcod) {
        try {
            talons = new TalonList(ClientInfomat.tcl.getTalons(cpol, cdol, pcod));
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void setPatient(String oms) {
        if ((oms == null) || (oms.isEmpty() || (oms.trim().isEmpty()))) {
            patient = null;
        }
        try {
            patient = ClientInfomat.tcl.checkOmsAndGetPatient(oms);
        } catch (KmiacServerException e) {
            patient = null;
            e.printStackTrace();
        } catch (OmsNotValidException e) {
            patient = null;
            e.printStackTrace();
        } catch (TException e) {
            patient = null;
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void setReservedTalon(int patientId) {
        try {
            reservedTalons = ClientInfomat.tcl.getReservedTalon(patientId);
        } catch (KmiacServerException e) {
            reservedTalons = Collections.<TTalon>emptyList();
            e.printStackTrace();
        } catch (TException e) {
            reservedTalons = Collections.<TTalon>emptyList();
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void setShedule(int pcod, int cpol, String cdol) {
        try {
            sheduleDays = ClientInfomat.tcl.getShedule(pcod, cpol, cdol);
        } catch (KmiacServerException e) {
            sheduleDays = Collections.<TSheduleDay>emptyList();
            e.printStackTrace();
        } catch (TException e) {
            sheduleDays = Collections.<TSheduleDay>emptyList();
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    public void setCurrentPoliclinic(IntegerClassifier currentPoliclinic) {
        this.currentPoliclinic = currentPoliclinic;
    }

    public void setCurrentSpeciality(StringClassifier currentSpeciality) {
        this.currentSpeciality = currentSpeciality;
    }

    public void setCurrentDoctor(IntegerClassifier currentDoctor) {
        this.currentDoctor = currentDoctor;
    }

    @Override
    public void reserveTalon(TPatient pat, TTalon talon) {
        try {
            ClientInfomat.tcl.reserveTalon(pat, talon);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (ReserveTalonOperationFailedException e) {
            e.printStackTrace();
        } catch (PatientHasSomeReservedTalonsOnThisDay e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void releaseTalon(TTalon talon) {
        try {
            ClientInfomat.tcl.releaseTalon(talon);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (ReleaseTalonOperationFailedException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public void registerDoctorObserver(IDoctorObserver obs) {
        doctorObservers.add(obs);
    }

    @Override
    public void removeDoctorObserver(IDoctorObserver obs) {
        doctorObservers.remove(obs);
    }

    public void notifyDoctorObservers() {
        for (IDoctorObserver obs: doctorObservers) {
            obs.updateDoctor();
        }
    }

    @Override
    public void registerPoliclinicObserver(IPoliclinicObserver obs) {
        policlinicObservers.add(obs);
    }

    @Override
    public void removePoliclinicObserver(IPoliclinicObserver obs) {
        policlinicObservers.remove(obs);
    }

    public void notifyPoliclinicObservers() {
        for (IPoliclinicObserver obs: policlinicObservers) {
            obs.updatePoliclinic();
        }
    }

    @Override
    public void registerSpecialityObserver(ISpecialityObserver obs) {
        specialityObservers.add(obs);
    }

    @Override
    public void removeSpecialityObserver(ISpecialityObserver obs) {
        specialityObservers.remove(obs);
    }

    public void notifySpecialityObservers() {
        for (ISpecialityObserver obs: specialityObservers) {
            obs.updateSpeciality();
        }
    }
}

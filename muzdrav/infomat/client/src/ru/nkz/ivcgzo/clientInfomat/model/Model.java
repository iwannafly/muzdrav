package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.ClientInfomat;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.OmsNotValidException;
import ru.nkz.ivcgzo.thriftInfomat.PatientHasSomeReservedTalonsOnThisDay;
import ru.nkz.ivcgzo.thriftInfomat.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class Model implements IModel {
    private List<IDoctorsObserver> doctorsObservers = new ArrayList<IDoctorsObserver>();
    private List<IPoliclinicsObserver> policlinicsObservers =
        new ArrayList<IPoliclinicsObserver>();
    private List<ISpecialitiesObserver> specialitiesObservers =
        new ArrayList<ISpecialitiesObserver>();
    private List<ICurrentDoctorObserver> currentDoctorsObservers =
        new ArrayList<ICurrentDoctorObserver>();
    private List<ICurrentPoliclinicObserver> currentPoliclinicObservers =
        new ArrayList<ICurrentPoliclinicObserver>();
    private List<ICurrentSpecialityObserver> currentSpecialityObservers =
        new ArrayList<ICurrentSpecialityObserver>();
    private List<IPatientObserver> patientObservers =
            new ArrayList<IPatientObserver>();
    private List<ISelectedTalonObserver> selectedTalonObservers =
            new ArrayList<ISelectedTalonObserver>();
    private List<ICurrentIReservedTalonObserver> currentReservedTalonObservers =
            new ArrayList<ICurrentIReservedTalonObserver>();
    private List<IntegerClassifier> policlinics;
    private List<StringClassifier> specialities;
    private List<IntegerClassifier> doctors;
    private TalonList talons;
    private List<TTalon> reservedTalons;
    private List<TSheduleDay> sheduleDays;
    private IntegerClassifier currentPoliclinic;
    private StringClassifier currentSpeciality;
    private IntegerClassifier currentDoctor;
    private TTalon currentTalon;
    private TTalon currentReservedTalon;
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
    public List<TTalon> getReservedTalons() {
        return reservedTalons;
    }

    @Override
    public List<TSheduleDay> getShedule() {
        return sheduleDays;
    }

    @Override
    public IntegerClassifier getCurrentPoliclinic() {
        return currentPoliclinic;
    }

    @Override
    public StringClassifier getCurrentSpeciality() {
        return currentSpeciality;
    }

    @Override
    public IntegerClassifier getCurrentDoctor() {
        return currentDoctor;
    }

    @Override
    public void setPoliclinics() {
        try {
            policlinics = ClientInfomat.tcl.getPoliclinics();
            notifyPoliclinicsObservers();
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
            notifySpecialitiesObservers();
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
            notifyDoctorsObservers();
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
            notifyPatientObservers();
        } else {
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
            } finally {
                notifyPatientObservers();
            }
        }
    }

    @Override
    public void setReservedTalons(int patientId) {
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

    @Override
    public void setCurrentPoliclinic(IntegerClassifier currentPoliclinic) {
        this.currentPoliclinic = currentPoliclinic;
        notifyCurrentPoliclinicObservers();
    }

    @Override
    public void setCurrentSpeciality(StringClassifier currentSpeciality) {
        this.currentSpeciality = currentSpeciality;
        notifyCurrentSpecialityObservers();
    }


    @Override
    public void setCurrentDoctor(IntegerClassifier currentDoctor) {
        this.currentDoctor = currentDoctor;
        notifyCurrentDoctorObservers();
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
    public void registerDoctorsObserver(IDoctorsObserver obs) {
        doctorsObservers.add(obs);
    }

    @Override
    public void removeDoctorsObserver(IDoctorsObserver obs) {
        doctorsObservers.remove(obs);
    }

    public void notifyDoctorsObservers() {
        for (IDoctorsObserver obs: doctorsObservers) {
            obs.updateDoctors();
        }
    }

    @Override
    public void registerPoliclinicsObserver(IPoliclinicsObserver obs) {
        policlinicsObservers.add(obs);
    }

    @Override
    public void removePoliclinicsObserver(IPoliclinicsObserver obs) {
        policlinicsObservers.remove(obs);
    }

    public void notifyPoliclinicsObservers() {
        for (IPoliclinicsObserver obs: policlinicsObservers) {
            obs.updatePoliclinics();
        }
    }

    @Override
    public void registerSpecialitiesObserver(ISpecialitiesObserver obs) {
        specialitiesObservers.add(obs);
    }

    @Override
    public void removeSpecialitiesObserver(ISpecialitiesObserver obs) {
        specialitiesObservers.remove(obs);
    }

    public void notifySpecialitiesObservers() {
        for (ISpecialitiesObserver obs: specialitiesObservers) {
            obs.updateSpecialities();
        }
    }

    @Override
    public void registerCurrentDoctorObserver(ICurrentDoctorObserver obs) {
        currentDoctorsObservers.add(obs);
    }

    @Override
    public void removeCurrentDoctorObserver(ICurrentDoctorObserver obs) {
        currentDoctorsObservers.remove(obs);
    }

    public void notifyCurrentDoctorObservers() {
        for (ICurrentDoctorObserver obs: currentDoctorsObservers) {
            obs.updateCurrentDoctor();
        }
    }

    @Override
    public void registerCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs) {
        currentPoliclinicObservers.add(obs);
    }

    @Override
    public void removeCurrentPoliclinicObserver(ICurrentPoliclinicObserver obs) {
        currentPoliclinicObservers.remove(obs);
    }

    public void notifyCurrentPoliclinicObservers() {
        for (ICurrentPoliclinicObserver obs: currentPoliclinicObservers) {
            obs.updateCurrentPoliclinic();
        }
    }

    @Override
    public void registerCurrentSpecialityObserver(ICurrentSpecialityObserver obs) {
        currentSpecialityObservers.add(obs);
    }

    @Override
    public void removeCurrentSpecialityObserver(ICurrentSpecialityObserver obs) {
        currentSpecialityObservers.remove(obs);
    }

    public void notifyCurrentSpecialityObservers() {
        for (ICurrentSpecialityObserver obs: currentSpecialityObservers) {
            obs.updateCurrentSpeciaity();
        }
    }

    @Override
    public TalonTableModel getTalonTableModel(int cpol, String cdol, int pcod) {
        return new TalonTableModel(cpol, cdol, pcod);
    }

    @Override
    public SheduleTableModel getSheduleTableModel(int pcod, int cpol,
            String cdol) {
        return new SheduleTableModel(pcod, cpol, cdol);
    }

    @Override
    public void registerPatientObserver(IPatientObserver obs) {
        patientObservers.add(obs);
    }

    @Override
    public void removePatientObserver(IPatientObserver obs) {
        patientObservers.remove(obs);
    }

    public void notifyPatientObservers() {
        for (IPatientObserver obs: patientObservers) {
            obs.updatePatient();
        }
    }

    @Override
    public ReservedTalonTableModel getReservedTalonTableModel(int pcod) {
        return new ReservedTalonTableModel(pcod);
    }

    @Override
    public void setTalon(TTalon talon) {
        this.currentTalon = talon;
        notifySelectedTalonObservers();
    }

    @Override
    public TTalon getTalon() {
        return currentTalon;
    }

    @Override
    public void registerSelectedTalonObserver(ISelectedTalonObserver obs) {
        selectedTalonObservers.add(obs);
    }

    @Override
    public void removeSelectedTalonObserver(ISelectedTalonObserver obs) {
        selectedTalonObservers.remove(obs);
    }

    public void notifySelectedTalonObservers() {
        for (ISelectedTalonObserver obs: selectedTalonObservers) {
            obs.updateSelectedTalon();
        }
    }

    @Override
    public void registerReservedTalonObserver(ICurrentIReservedTalonObserver obs) {
        currentReservedTalonObservers.add(obs);
    }

    @Override
    public void removeReservedTalonObserver(ICurrentIReservedTalonObserver obs) {
        currentReservedTalonObservers.remove(obs);
    }

    public void notifyCurrentReservedTalonObservers() {
        for (ICurrentIReservedTalonObserver obs: currentReservedTalonObservers) {
            obs.updateReservedTalon();
        }
    }

    @Override
    public TTalon getCurrentReservedTalon() {
        return currentReservedTalon;
    }

    @Override
    public void setCurrentReservedTalon(TTalon talon) {
        this.currentReservedTalon = talon;
        notifyCurrentReservedTalonObservers();
    }
}

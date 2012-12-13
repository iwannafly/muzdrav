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
    public final List<IntegerClassifier> getPoliclinics() {
        return policlinics;
    }

    @Override
    public final List<StringClassifier> getSpecialities() {
        return specialities;
    }

    @Override
    public final List<IntegerClassifier> getDoctors() {
        return doctors;
    }

    @Override
    public final TalonList getTalons() {
        return talons;
    }

    @Override
    public final TPatient getPatient() {
        return patient;
    }

    @Override
    public final List<TTalon> getReservedTalons() {
        return reservedTalons;
    }

    @Override
    public final List<TSheduleDay> getShedule() {
        return sheduleDays;
    }

    @Override
    public final IntegerClassifier getCurrentPoliclinic() {
        return currentPoliclinic;
    }

    @Override
    public final StringClassifier getCurrentSpeciality() {
        return currentSpeciality;
    }

    @Override
    public final IntegerClassifier getCurrentDoctor() {
        return currentDoctor;
    }

    @Override
    public final void setPoliclinics() {
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
    public final void setSpecialities(final int cpol) {
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
    public final void setDoctors(final int cpol, final String cdol) {
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
    public final void setTalons(final int cpol, final String cdol, final int pcod) {
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
    public final void setPatient(final String oms) {
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
    public final void setReservedTalons(final int patientId) {
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
    public final void setShedule(final int pcod, final int cpol, final String cdol) {
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
    public final void setCurrentPoliclinic(final IntegerClassifier inCurrentPoliclinic) {
        this.currentPoliclinic = inCurrentPoliclinic;
        notifyCurrentPoliclinicObservers();
    }

    @Override
    public final void setCurrentSpeciality(final StringClassifier inCurrentSpeciality) {
        this.currentSpeciality = inCurrentSpeciality;
        notifyCurrentSpecialityObservers();
    }


    @Override
    public final void setCurrentDoctor(final IntegerClassifier inCurrentDoctor) {
        this.currentDoctor = inCurrentDoctor;
        notifyCurrentDoctorObservers();
    }

    @Override
    public final void reserveTalon(final TPatient pat, final TTalon talon) {
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
    public final void releaseTalon(final TTalon talon) {
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
    public final void registerDoctorsObserver(final IDoctorsObserver obs) {
        doctorsObservers.add(obs);
    }

    @Override
    public final void removeDoctorsObserver(final IDoctorsObserver obs) {
        doctorsObservers.remove(obs);
    }

    public final void notifyDoctorsObservers() {
        for (IDoctorsObserver obs: doctorsObservers) {
            obs.updateDoctors();
        }
    }

    @Override
    public final void registerPoliclinicsObserver(final IPoliclinicsObserver obs) {
        policlinicsObservers.add(obs);
    }

    @Override
    public final void removePoliclinicsObserver(final IPoliclinicsObserver obs) {
        policlinicsObservers.remove(obs);
    }

    public final void notifyPoliclinicsObservers() {
        for (IPoliclinicsObserver obs: policlinicsObservers) {
            obs.updatePoliclinics();
        }
    }

    @Override
    public final void registerSpecialitiesObserver(final ISpecialitiesObserver obs) {
        specialitiesObservers.add(obs);
    }

    @Override
    public final void removeSpecialitiesObserver(final ISpecialitiesObserver obs) {
        specialitiesObservers.remove(obs);
    }

    public final void notifySpecialitiesObservers() {
        for (ISpecialitiesObserver obs: specialitiesObservers) {
            obs.updateSpecialities();
        }
    }

    @Override
    public final void registerCurrentDoctorObserver(final ICurrentDoctorObserver obs) {
        currentDoctorsObservers.add(obs);
    }

    @Override
    public final void removeCurrentDoctorObserver(final ICurrentDoctorObserver obs) {
        currentDoctorsObservers.remove(obs);
    }

    public final void notifyCurrentDoctorObservers() {
        for (ICurrentDoctorObserver obs: currentDoctorsObservers) {
            obs.updateCurrentDoctor();
        }
    }

    @Override
    public final void registerCurrentPoliclinicObserver(final ICurrentPoliclinicObserver obs) {
        currentPoliclinicObservers.add(obs);
    }

    @Override
    public final void removeCurrentPoliclinicObserver(final ICurrentPoliclinicObserver obs) {
        currentPoliclinicObservers.remove(obs);
    }

    public final void notifyCurrentPoliclinicObservers() {
        for (ICurrentPoliclinicObserver obs: currentPoliclinicObservers) {
            obs.updateCurrentPoliclinic();
        }
    }

    @Override
    public final void registerCurrentSpecialityObserver(final ICurrentSpecialityObserver obs) {
        currentSpecialityObservers.add(obs);
    }

    @Override
    public final void removeCurrentSpecialityObserver(final ICurrentSpecialityObserver obs) {
        currentSpecialityObservers.remove(obs);
    }

    public final void notifyCurrentSpecialityObservers() {
        for (ICurrentSpecialityObserver obs: currentSpecialityObservers) {
            obs.updateCurrentSpeciaity();
        }
    }

    @Override
    public final TalonTableModel getTalonTableModel(
            final int cpol, final String cdol, final int pcod) {
        return new TalonTableModel(cpol, cdol, pcod);
    }

    @Override
    public final SheduleTableModel getSheduleTableModel(final int pcod, final int cpol,
            final String cdol) {
        return new SheduleTableModel(pcod, cpol, cdol);
    }

    @Override
    public final void registerPatientObserver(final IPatientObserver obs) {
        patientObservers.add(obs);
    }

    @Override
    public final void removePatientObserver(final IPatientObserver obs) {
        patientObservers.remove(obs);
    }

    public final void notifyPatientObservers() {
        for (IPatientObserver obs: patientObservers) {
            obs.updatePatient();
        }
    }

    @Override
    public final ReservedTalonTableModel getReservedTalonTableModel(final int pcod) {
        return new ReservedTalonTableModel(pcod);
    }

    @Override
    public final void setTalon(final TTalon talon) {
        this.currentTalon = talon;
        notifySelectedTalonObservers();
    }

    @Override
    public final TTalon getTalon() {
        return currentTalon;
    }

    @Override
    public final void registerSelectedTalonObserver(final ISelectedTalonObserver obs) {
        selectedTalonObservers.add(obs);
    }

    @Override
    public final void removeSelectedTalonObserver(final ISelectedTalonObserver obs) {
        selectedTalonObservers.remove(obs);
    }

    public final void notifySelectedTalonObservers() {
        for (ISelectedTalonObserver obs: selectedTalonObservers) {
            obs.updateSelectedTalon();
        }
    }

    @Override
    public final void registerReservedTalonObserver(final ICurrentIReservedTalonObserver obs) {
        currentReservedTalonObservers.add(obs);
    }

    @Override
    public final void removeReservedTalonObserver(final ICurrentIReservedTalonObserver obs) {
        currentReservedTalonObservers.remove(obs);
    }

    public final void notifyCurrentReservedTalonObservers() {
        for (ICurrentIReservedTalonObserver obs: currentReservedTalonObservers) {
            obs.updateReservedTalon();
        }
    }

    @Override
    public final TTalon getCurrentReservedTalon() {
        return currentReservedTalon;
    }

    @Override
    public final void setCurrentReservedTalon(final TTalon talon) {
        this.currentReservedTalon = talon;
        notifyCurrentReservedTalonObservers();
    }
}

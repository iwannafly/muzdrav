package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.ClientInfomat;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IInfomatObserver;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.SheduleTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TSheduleDay;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class Model implements IModel {
    private List<IInfomatObserver> infomatObservers = new ArrayList<IInfomatObserver>();
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
    private boolean talonMode;
    private boolean scheduleMode;

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
    public final void setPoliclinics() throws TException {
    	if (talonMode)
    		policlinics = ClientInfomat.tcl.getPoliclinicsTalon();
    	else if (scheduleMode)
    		policlinics = ClientInfomat.tcl.getPoliclinicsSchedule();
    	else
    		throw new TException("Choose ether talon or shcedule mode.");
        firePoliclinicsChanged();
    }

    @Override
    public final void setSpecialities(final int cpol) throws TException {
    	if (talonMode)
            specialities = ClientInfomat.tcl.getSpecialitiesTalon(cpol);
        else if (scheduleMode)
            specialities = ClientInfomat.tcl.getSpecialitiesSchedule(cpol);
        else
            throw new TException("Choose ether talon or shcedule mode.");
        fireSpecialitiesChanged();
    }

    @Override
    public final void setDoctors(final int cpol, final String cdol) throws TException {
    	if (talonMode)
            doctors = ClientInfomat.tcl.getDoctorsTalon(cpol, cdol);
        else if (scheduleMode)
            doctors = ClientInfomat.tcl.getDoctorsSchedule(cpol, cdol);
        else
            throw new TException("Choose ether talon or shcedule mode.");
        fireDoctorsChanged();
    }

    @Override
    public final void setTalons(final int cpol, final String cdol, final int pcod)
            throws TException {
        talons = new TalonList(ClientInfomat.tcl.getTalons(cpol, cdol, pcod));
    }

    @Override
    public final void setPatient(final String oms) throws TException {
//        if ((oms == null) || (oms.isEmpty() || (oms.trim().isEmpty()))) {
//            patient = null;
//            firePatientChanged();
//        } else {
//            try {
        patient = ClientInfomat.tcl.checkOmsAndGetPatient(oms);
        firePatientChanged();
//            } catch (KmiacServerException e) {
//                patient = null;
//                e.printStackTrace();
//            } catch (OmsNotValidException e) {
//                patient = null;
//                e.printStackTrace();
//            } catch (TException e) {
//                patient = null;
//                e.printStackTrace();
//                ClientInfomat.conMan.reconnect(e);
//            } finally {
//                firePatientChanged();
//            }
//        }
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
        fireCurrentPoliclinicChanged();
    }

    @Override
    public final void setCurrentSpeciality(final StringClassifier inCurrentSpeciality) {
        this.currentSpeciality = inCurrentSpeciality;
        fireCurrentSpeciaityChanged();
    }


    @Override
    public final void setCurrentDoctor(final IntegerClassifier inCurrentDoctor) {
        this.currentDoctor = inCurrentDoctor;
        fireCurrentDoctorChanged();
    }

    @Override
    public final void reserveTalon(final TPatient pat, final TTalon talon) throws TException {
        ClientInfomat.tcl.reserveTalon(pat, talon);
    }

    @Override
    public final void releaseTalon(final TTalon talon) {
        try {
            ClientInfomat.tcl.releaseTalon(talon);
        } catch (ReleaseTalonOperationFailedException e) {
            e.printStackTrace();
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final TalonTableModel getTalonTableModel(
            final int cpol, final String cdol, final int pcod) throws TException {
        return new TalonTableModel(cpol, cdol, pcod);
    }

    @Override
    public final SheduleTableModel getSheduleTableModel(final int pcod, final int cpol,
            final String cdol) throws TException {
        return new SheduleTableModel(pcod, cpol, cdol);
    }

    @Override
    public final ReservedTalonTableModel getReservedTalonTableModel(final int pcod)
            throws TException {
        return new ReservedTalonTableModel(pcod);
    }

    @Override
    public final void setTalon(final TTalon talon) {
        this.currentTalon = talon;
        fireSelectedTalonChanged();
    }

    @Override
    public final TTalon getTalon() {
        return currentTalon;
    }

    @Override
    public final TTalon getCurrentReservedTalon() {
        return currentReservedTalon;
    }

    @Override
    public final void setCurrentReservedTalon(final TTalon talon) {
        this.currentReservedTalon = talon;
        fireReservedTalonChanged();
    }

    @Override
    public final void registerInfomatObserver(final IInfomatObserver obs) {
        infomatObservers.add(obs);
    }

    @Override
    public final void removeInfomatObserver(final IInfomatObserver obs) {
        infomatObservers.remove(obs);
    }

    public void setTalonMode() {
    	talonMode = true;
    	scheduleMode = false;
    }

    public void setScheduleMode() {
    	scheduleMode = true;
    	talonMode = false;
    }

    private void fireCurrentPoliclinicChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateCurrentPoliclinic();
        }
    }

    private void fireCurrentSpeciaityChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateCurrentSpeciaity();
        }
    }

    private void fireCurrentDoctorChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateCurrentDoctor();
        }
    }

    private void firePoliclinicsChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updatePoliclinics();
        }
    }

    private void fireSpecialitiesChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateSpecialities();
        }
    }

    private void fireDoctorsChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateDoctors();
        }
    }

    private void firePatientChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updatePatient();
        }
    }

    private void fireSelectedTalonChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateSelectedTalon();
        }
    }


    private void fireReservedTalonChanged() {
        for (IInfomatObserver obs: infomatObservers) {
            obs.updateReservedTalon();
        }
    }

    @Override
    public final boolean isPatientAlreadyReserveTalonOnThisDay(final TPatient pat,
            final TTalon talon) throws TException {
        return ClientInfomat.tcl.isPatientAlreadyReserveTalonOnThisDay(pat, talon);
    }

    @Override
    public final void setPatient(final String oms,
            final int clpu) throws TException {
        patient = ClientInfomat.tcl.checkOmsAndGetPatientInCurrentPoliclinic(oms, clpu);
        firePatientChanged();
    }
}

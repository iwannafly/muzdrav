package ru.nkz.ivcgzo.clientHospital.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;

public class HospitalModel implements IHospitalModel {
    private List<IPatientObserver> patientObservers = new ArrayList<IPatientObserver>();
    private TPatient patient;
    private TPriemInfo priemInfo;
    private TMedicalHistory perOsmotr;
    @SuppressWarnings("unused")
    private List<TMedicalHistory> diaryList;

    @Override
    public final TPatient getPatient() {
        return this.patient;
    }

    @Override
    public final void setPatient(final int patientGospId) {
        try {
            patient = ClientHospital.tcl.getPatientPersonalInfo(patientGospId);
            setPriemInfo();
            setPerOsmotr();
        } catch (PatientNotFoundException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
        } catch (KmiacServerException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            e.printStackTrace();
        } catch (TException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            ClientHospital.conMan.reconnect(e);
        } finally {
            notifyPatientObservers();
        }
    }

    @Override
    public final TPriemInfo getPriemInfo() {
        return this.priemInfo;
    }

    @Override
    public final TMedicalHistory getPerOsmotr() {
        return this.perOsmotr;
    }

    private void setPerOsmotr() {
        if (patient != null) {
            try {
                perOsmotr = ClientHospital.tcl.getPriemOsmotr(patient.getGospitalCod());
            } catch (KmiacServerException e) {
                perOsmotr = null;
                e.printStackTrace();
            } catch (TException e) {
                perOsmotr = null;
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    @Override
    public final void setPriemInfo() {
        if (patient != null) {
            try {
                priemInfo = ClientHospital.tcl.getPriemInfo(patient.getGospitalCod());
            } catch (PriemInfoNotFoundException e) {
                priemInfo = null;
            } catch (KmiacServerException e) {
                priemInfo = null;
                e.printStackTrace();
            } catch (TException e) {
                priemInfo = null;
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    @Override
    public final void registerPatientObserver(final IPatientObserver obs) {
        patientObservers.add(obs);
    }

    @Override
    public final void removePatientObserver(final IPatientObserver obs) {
        patientObservers.remove(obs);
    }

    private void notifyPatientObservers() {
        for (IPatientObserver obs: patientObservers) {
            obs.patientChanged();
        }
    }

    @Override
    public final List<IntegerClassifier> getOtdProfs() {
        try {
            return ClientHospital.tcl.getStationTypes(ClientHospital.authInfo.getCpodr());
        } catch (KmiacServerException e) {
            e.printStackTrace();
            return Collections.<IntegerClassifier>emptyList();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
            return Collections.<IntegerClassifier>emptyList();
        }
    }

    @Override
    public final void updatePatient(final String chamber, final int otdProf) {
        if (patient != null) {
            try {
                ClientHospital.tcl.updatePatientChamberNumber(
                        patient.getGospitalCod(), chamber, otdProf, patient.getNist());
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    public final List<IntegerClassifier> loadMedicalHistoryShablons() {
        try {
            return ClientHospital.tcl.getShablonNames(
                    ClientHospital.authInfo.getCpodr(),
                    ClientHospital.authInfo.getCslu(),  null);
        } catch (KmiacServerException e) {
            e.printStackTrace();
            return Collections.<IntegerClassifier>emptyList();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
            return Collections.<IntegerClassifier>emptyList();
        }
    }
}

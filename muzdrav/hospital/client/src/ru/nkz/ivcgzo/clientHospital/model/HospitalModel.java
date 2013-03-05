package ru.nkz.ivcgzo.clientHospital.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.meta_data.SetMetaData;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.InfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;

public class HospitalModel implements IHospitalModel {
    private List<IPatientObserver> patientObservers = new ArrayList<IPatientObserver>();
    private List<IDiaryRecordObserver> diaryObservers = new ArrayList<IDiaryRecordObserver>();
    private TPatient patient;
    private TPriemInfo priemInfo;
    private TMedicalHistory perOsmotr;
    private List<TMedicalHistory> diaryList;
    private TMedicalHistory currentDiaryRecord;

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
            setDiaryList();
        } catch (PatientNotFoundException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
        } catch (KmiacServerException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
            e.printStackTrace();
        } catch (TException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
            ClientHospital.conMan.reconnect(e);
        } finally {
            notifyPatientObservers();
        }
    }

    @Override
    public final void setPatientByCotd(final int idCotd) {
        try {
            patient = ClientHospital.tcl.getPatientPersonalInfoByCotd(idCotd);
            setPriemInfo();
            setPerOsmotr();
        } catch (PatientNotFoundException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
        } catch (KmiacServerException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
            e.printStackTrace();
        } catch (TException e) {
            patient = null;
            priemInfo = null;
            perOsmotr = null;
            diaryList = null;
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
            } catch (InfoNotFoundException e) {
                perOsmotr = null;
            } catch (KmiacServerException e) {
                perOsmotr = null;
                e.printStackTrace();
            } catch (TException e) {
                perOsmotr = null;
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    //FIXME private ?
    @Override
    public final void setPriemInfo() {
        if (patient != null) {
            try {
                priemInfo = ClientHospital.tcl.getPriemInfo(patient.getGospitalCod());
            } catch (InfoNotFoundException e) {
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
    public final void registerDiaryRecordObserver(final IDiaryRecordObserver obs) {
        diaryObservers.add(obs);
    }

    @Override
    public final void removeDiaryRecordObserver(final IDiaryRecordObserver obs) {
        diaryObservers.remove(obs);
    }

    private void notifyDiaryRecordObserver() {
        for (IDiaryRecordObserver obs: diaryObservers) {
            obs.diaryRecordChanged();
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
    public final void updatePatient(final String chamber, final int otdProf,
            final String surname, final String name, final String middlename) {
        if (patient != null) {
            try {
                ClientHospital.tcl.updatePatientChamberNumber(
                    patient.getGospitalCod(), chamber, otdProf, patient.getNist(),
                    surname, name, middlename);
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    @Override
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

    @Override
    public final List<IntegerClassifier> loadMedicalHistoryShablons(final String srcText) {
        try {
            return ClientHospital.tcl.getShablonNames(
                    ClientHospital.authInfo.getCpodr(),
                    ClientHospital.authInfo.getCslu(),  srcText);
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
    public final Shablon loadShablon(final int idSh) {
        try {
            return ClientHospital.tcl.getShablon(idSh);
        } catch (KmiacServerException e) {
            e.printStackTrace();
            return null;
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            return null;
        }
    }

    @Override
    public final List<IntegerClassifier> getShablonBySelectedDiagnosis(
            final String diag, final String srcText) {
        try {
            return ClientHospital.tcl.getShablonBySelectedDiagnosis(
                ClientHospital.authInfo.cspec, ClientHospital.authInfo.cslu, diag, srcText);
        } catch (KmiacServerException e) {
            e.printStackTrace();
            return Collections.<IntegerClassifier>emptyList();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            return Collections.<IntegerClassifier>emptyList();
        }
    }
    @Override
    public final List<StringClassifier> getShablonDiagnosis(final String srcText) {
        try {
            return ClientHospital.tcl.getShablonDiagnosis(
                    ClientHospital.authInfo.cspec, ClientHospital.authInfo.cslu, srcText);
        } catch (KmiacServerException e) {
            e.printStackTrace();
            return Collections.<StringClassifier>emptyList();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            return Collections.<StringClassifier>emptyList();
        }
    }

    @Override
    public final List<TMedicalHistory> getDiaryList() {
        if (diaryList == null) {
            return Collections.<TMedicalHistory>emptyList();
        }
        return diaryList;
    }

    @Override
    public final void setDiaryList() {
        try {
            diaryList = ClientHospital.tcl.getMedicalHistory(patient.getGospitalCod());
        } catch (KmiacServerException e) {
            diaryList = Collections.<TMedicalHistory>emptyList();
        } catch (MedicalHistoryNotFoundException e) {
            diaryList = Collections.<TMedicalHistory>emptyList();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
            diaryList = Collections.<TMedicalHistory>emptyList();
        }
    }

    @Override
    public final TMedicalHistory getMedicalHistory() {
        return currentDiaryRecord;
    }

    @Override
    public final void setMedicalHistory(final TMedicalHistory currentMedicalHistory) {
        currentDiaryRecord = currentMedicalHistory;
        notifyDiaryRecordObserver();
    }

    @Override
    public final void addMedicalHistory(final TMedicalHistory newMedHist) {
        try {
            ClientHospital.tcl.addMedicalHistory(newMedHist);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
        }
    }

    @Override
    public final void deleteMedicalHistory(final TMedicalHistory currentMedHist) {
        try {
            ClientHospital.tcl.deleteMedicalHistory(currentMedHist.getId());
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
        }
    }

    @Override
    public final void updateMedicalHistory() {

    }

}

package ru.nkz.ivcgzo.clientHospital.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.controllers.HospitalDataTransferException;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.InfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

public class HospitalModel implements IHospitalModel {
    private List<IPatientObserver> patientObservers = new ArrayList<IPatientObserver>();
    private List<IDiaryRecordObserver> diaryObservers = new ArrayList<IDiaryRecordObserver>();
    private List<IDiagnosisObserver> diagnosisObservers = new ArrayList<IDiagnosisObserver>();
    private TPatient patient;
    private TPriemInfo priemInfo;
    private TMedicalHistory perOsmotr;
    private List<TMedicalHistory> diaryList;
    private List<TDiagnosis> diagnosisList;
    private TMedicalHistory currentDiaryRecord;
    private TDiagnosis currentDiagnosis;

    @Override
    public final TPatient getPatient() {
        return this.patient;
    }

    @Override
    public final void setPatient(final int patientGospId) throws PatientNotFoundException,
            HospitalDataTransferException {
        try {
            patient = ClientHospital.tcl.getPatientPersonalInfo(patientGospId);
            setPriemInfo();
            setPerOsmotr();
            setDiaryList();
            setDiagnosisList();
        } catch (PatientNotFoundException e) {
            setNullFields();
            throw e;
        } catch (KmiacServerException e) {
            setNullFields();
            throw new HospitalDataTransferException("Ошибка при попытке выбора пациента", e);
        } catch (TException e) {
            setNullFields();
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при попытке выбора пациента", e);
        } finally {
            notifyPatientObservers();
        }
    }

    @Override
    public final void setPatientByCotd(final int idCotd) throws PatientNotFoundException,
            HospitalDataTransferException {
        try {
            patient = ClientHospital.tcl.getPatientPersonalInfoByCotd(idCotd);
            setPriemInfo();
            setPerOsmotr();
            setDiaryList();
            setDiagnosisList();
        } catch (PatientNotFoundException e) {
            setNullFields();
            throw e;
        } catch (KmiacServerException e) {
            setNullFields();
            throw new HospitalDataTransferException("Ошибка при попытке выбора пациента", e);
        } catch (TException e) {
            setNullFields();
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при попытке выбора пациента", e);
        } finally {
            notifyPatientObservers();
        }
    }

    private void setNullFields() {
        patient = null;
        priemInfo = null;
        perOsmotr = null;
        diaryList = null;
        diagnosisList = null;
        currentDiagnosis = null;
        currentDiaryRecord = null;
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
    public final void registerDiagnosisObserver(final IDiagnosisObserver obs) {
        diagnosisObservers.add(obs);
    }

    @Override
    public final void removeDiagnosisObserver(final IDiagnosisObserver obs) {
        diagnosisObservers.remove(obs);
    }

    private void notifyDiagnosisObserver() {
        for (IDiagnosisObserver obs: diagnosisObservers) {
            obs.diagnosisChanged();
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
            final String surname, final String name, final String middlename)
            throws HospitalDataTransferException {
        if (patient != null) {
            try {
                ClientHospital.tcl.updatePatientChamberNumber(
                    patient.getGospitalCod(), chamber, otdProf, patient.getNist(),
                    surname, name, middlename);
            } catch (KmiacServerException e) {
                throw new HospitalDataTransferException(
                        "Ошибка при обновлении информации о пациенте. Данные не сохранены!", e);
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
    public final void setDiaryList() throws HospitalDataTransferException {
        try {
            diaryList = ClientHospital.tcl.getMedicalHistory(patient.getGospitalCod());
        } catch (MedicalHistoryNotFoundException e) {
            diaryList = Collections.<TMedicalHistory>emptyList();
        } catch (KmiacServerException e) {
            diaryList = Collections.<TMedicalHistory>emptyList();
            throw new HospitalDataTransferException("Ошибка загрузки дневника", e);
        }  catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            diaryList = Collections.<TMedicalHistory>emptyList();
            throw new HospitalDataTransferException("Ошибка загрузки дневника", e);
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
    public final void addMedicalHistory(final TMedicalHistory newMedHist)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.addMedicalHistory(newMedHist);
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при добавлении записи в дневник", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при добавлении записи в дневник", e);
        }
    }

    @Override
    public final void deleteMedicalHistory(final TMedicalHistory currentMedHist)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.deleteMedicalHistory(currentMedHist.getId());
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при удалении записи из дневника", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при удалении записи из дневника", e);
        }
    }

    @Override
    public final void updateMedicalHistory(final TMedicalHistory currenMedHist)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.updateMedicalHistory(currenMedHist);
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при изменении записи в дневнике", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при изменении записи в дневнике", e);
        }
    }
    @Override
    public final void setDiagnosisList() throws HospitalDataTransferException {
        try {
            diagnosisList = ClientHospital.tcl.getDiagnosis(patient.getGospitalCod());
        } catch (DiagnosisNotFoundException e) {
            diagnosisList =  Collections.<TDiagnosis>emptyList();
        } catch (KmiacServerException e) {
            diagnosisList =  Collections.<TDiagnosis>emptyList();
            throw new HospitalDataTransferException("Ошибка при загрузке списка диагнозов", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            diagnosisList =  Collections.<TDiagnosis>emptyList();
            throw new HospitalDataTransferException("Ошибка при загрузке списка диагнозов", e);
        }
    }
    @Override
    public final List<TDiagnosis> getDiagnosisList() {
        if (diagnosisList == null) {
            return Collections.<TDiagnosis>emptyList();
        }
        return diagnosisList;
    }

    @Override
    public final void setCurrentDiagnosis(final TDiagnosis inCurrentDiagnosis) {
        currentDiagnosis = inCurrentDiagnosis;
        notifyDiagnosisObserver();
    }

    @Override
    public final TDiagnosis getCurrentDiagnosis() {
        return currentDiagnosis;
    }

    @Override
    public final void addDiagnosis(final TDiagnosis diagnosis)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.addDiagnosis(diagnosis);
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при добавлении диагноза", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при добавлении диагноза", e);
        }
    }

    @Override
    public final void deleteDiagnosis(final TDiagnosis diagnosis)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.deleteDiagnosis(diagnosis.getId());
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при удалении диагноза", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при удалении списка диагноза", e);
        }
    }

    @Override
    public final void updateDiagnosis(final TDiagnosis diagnosis)
            throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.updateDiagnosis(diagnosis);
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при обновлении диагноза", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при обновлении диагноза", e);
        }
    }

    @Override
    public final List<IntegerClassifier> getIshodClassifier() {
        try {
            return ClientHospital.tcl.getAp0();
        } catch (KmiacServerException e) {
            return Collections.<IntegerClassifier>emptyList();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            return Collections.<IntegerClassifier>emptyList();
        }
    }

    @Override
    public final List<IntegerClassifier> getResultClassifier() {
        try {
            return ClientHospital.tcl.getAq0();
        } catch (KmiacServerException e) {
            return Collections.<IntegerClassifier>emptyList();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            return Collections.<IntegerClassifier>emptyList();
        }
    }

    @Override
    public final void addZakl(final Zakl tmpZakl) throws HospitalDataTransferException {
        try {
            ClientHospital.tcl.addZakl(tmpZakl, ClientHospital.authInfo.getCpodr());
        } catch (KmiacServerException e) {
            throw new HospitalDataTransferException("Ошибка при обновлении заключения", e);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
            throw new HospitalDataTransferException("Ошибка при обновлении заключения", e);
        }
    }

}

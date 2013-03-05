package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiagnosisPanel;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;

public class DiagnosisController implements IComponentController {
    private IHospitalModel model;
    private DiagnosisPanel view;

    public DiagnosisController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new DiagnosisPanel(this, model);
    }

    @Override
    public final Component getComponent() {
        return view.getComponent();
    }

    @Override
    public final String getPanelTooltipText() {
        return view.getPanelToolTipText();
    }

    @Override
    public final String getTitle() {
        return view.getTitle();
    }

    @Override
    public final URL getIconURL() {
        return view.getIconURL();
    }

    public final void setCurrentDiagnosisRecord(final TDiagnosis inCurrentDiagnosis) {
        model.setCurrentDiagnosis(inCurrentDiagnosis);
    }

    public final void addDiagnosis() {
        StringClassifier curDiagnosis = ClientHospital.conMan.showMkbTreeForm("Диагноз", "");
        if ((curDiagnosis != null) && (model.getPatient() != null)) {
            TDiagnosis diag = new TDiagnosis();
            diag.setIdGosp(model.getPatient().getGospitalCod());
            diag.setCod(curDiagnosis.getPcod());
            diag.setDateUstan(System.currentTimeMillis());
            diag.setVrach(ClientHospital.authInfo.getPcod());
            diag.setDiagName(curDiagnosis.getName());
            diag.setMedOp(curDiagnosis.getName());
            diag.setPredv(false);
//            taDiagMedOp.setText(curDiagnosis.getName());
            try {
                model.addDiagnosis(diag);
            } catch (HospitalDataTransferException e) {
                JOptionPane.showMessageDialog(
                        view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }

            updateDiagnosisTable();
        }
    }

    public final void deleteDiagnosis(final TDiagnosis tDiagnosis) {
        try {
            model.deleteDiagnosis(tDiagnosis);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        updateDiagnosisTable();
    }

    public final void updateDiagnosis(final TDiagnosis tDiagnosis) {
        try {
            model.updateDiagnosis(tDiagnosis);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        updateDiagnosisTable();
    }

    private void updateDiagnosisTable() {
        try {
            model.setDiagnosisList();
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        view.updateDiagonsisTable();
    }
}

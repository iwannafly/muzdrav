package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiagnosisPanel;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;

/**
 * Контроллер панели диагнозов
 */
public class DiagnosisController implements IComponentController {
    private IHospitalModel model;
    private DiagnosisPanel view;

    public DiagnosisController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new DiagnosisPanel(this, model);
    }

    /**
     * Передает ссылку на представление
     */
    @Override
    public final Component getComponent() {
        return view.getComponent();
    }

    /**
     * Передает текст для подсказки представления
     */
    @Override
    public final String getPanelTooltipText() {
        return view.getPanelToolTipText();
    }

    /**
     * Передает заголовок представления
     */
    @Override
    public final String getTitle() {
        return view.getTitle();
    }

    /**
     * Передает путь к иконке представления
     */
    @Override
    public final URL getIconURL() {
        return view.getIconURL();
    }

    /**
     * Устанавливает текущий выбранный диагноз
     */
    public final void setCurrentDiagnosisRecord(final TDiagnosis inCurrentDiagnosis) {
        model.setCurrentDiagnosis(inCurrentDiagnosis);
    }

    /**
     * Передает в модель диагноз для добавления
     */
    public final void addDiagnosis() {
        // Вызов формы выбора диагноза из МКБ
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

    /**
     * Передает в модель диагноз для удаления
     */
    public final void deleteDiagnosis(final TDiagnosis tDiagnosis) {
        try {
            model.deleteDiagnosis(tDiagnosis);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        updateDiagnosisTable();
    }

    /**
     * Передает в модель диагноз для обновления
     */
    public final void updateDiagnosis(final TDiagnosis tDiagnosis) {
        try {
            model.updateDiagnosis(tDiagnosis);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        updateDiagnosisTable();
    }

    /**
     * Обновляет таблицу диагнозов в представлении
     */
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

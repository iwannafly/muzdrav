package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiaryPanel;
import ru.nkz.ivcgzo.clientHospital.views.ShablonForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;

/**
 * Контроллер панели дневников. Организует доставку данных из модели в представление
 */
public class DiaryController implements IComponentController {
    private IHospitalModel model;
    private DiaryPanel view;
    private ru.nkz.ivcgzo.clientHospital.views.ShablonForm shForm;

    public DiaryController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new DiaryPanel(this, model);

        // создание формы выбора шаблонов
        shForm = new ShablonForm(model);

        // установка списка шаблонов в представлении
        view.setMedicalHistoryShablons(model.loadMedicalHistoryShablons());
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
     * Отображает форму выбора шаблонов
     */
    public final void showShablonForm(final String text, final IntegerClassifier value) {
        shForm.showShablonForm(text, value);
        view.syncShablonList(shForm.getSearchString(), shForm.getShablon());
        view.smartPasteShablon(shForm.getShablon());
    }

    /**
     * Устанавливает текущую выбранную запись дневника
     */
    public final void setCurrentDiaryRecord(final TMedicalHistory currentMedicalHistory) {
        model.setMedicalHistory(currentMedicalHistory);
    }

    /**
     * Передает в модель для добавления новую запись дневника
     */
    public final void addDiaryRecord() {
        TMedicalHistory medHist = new TMedicalHistory();
        medHist.setDataz(System.currentTimeMillis());
        medHist.setTimez(System.currentTimeMillis());
        medHist.setPcodVrach(ClientHospital.authInfo.getPcod());
        medHist.setIdGosp(model.getPatient().getGospitalCod());
        medHist.setPcod_added(ClientHospital.authInfo.getPcod());
        medHist.setCpodr(ClientHospital.authInfo.getCpodr());

        try {
            model.addMedicalHistory(medHist);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }

        updateDiaryTable();
    }

    /**
     * Передает в модель для удаления запись дневника
     */
    public final void deleteDiaryRecord(final TMedicalHistory currentMedHist) {
        try {
            model.deleteMedicalHistory(currentMedHist);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
        updateDiaryTable();
    }

    /**
     * Передает в модель для обновления запись дневника
     */
    public final void updateDiaryRecord(final TMedicalHistory currentMedHist) {
        try {
            model.updateMedicalHistory(currentMedHist);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
        updateDiaryTable();
    }

    /**
     * Обновляет таблицу дневников в представлении
     */
    private void updateDiaryTable() {
        try {
            model.setDiaryList();
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
        view.updateDiaryTable();
    }

}

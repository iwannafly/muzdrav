package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.PersonalInfoPanel;

/**
 * Контроллер панели персональной информации пациента
 */
public class PersonalInfoController implements IComponentController {
    private IHospitalModel model;
    private PersonalInfoPanel view;

    public PersonalInfoController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new PersonalInfoPanel(this, model);

        // установка профилей доступных для текущего отделения
        view.setOtdProfList(model.getOtdProfs());
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
     * Передает запрос модели на установку информации из приёмного отделения
     */
    public final void setPriemInfo() {
        model.setPriemInfo();
    }

    /**
     * Передает запрос модели на обновление информации о пациенте
     */
    public final void updatePatient(final String chamber, final int otdProf,
            final String surname, final String name, final String middlename) {
        try {
            model.updatePatient(chamber, otdProf, surname, name, middlename);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }

}

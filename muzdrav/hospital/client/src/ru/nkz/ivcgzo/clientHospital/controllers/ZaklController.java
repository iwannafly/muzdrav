package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;
import java.util.List;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.ZaklPanel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

/**
 * Контроллер панели заключения
 */
public class ZaklController implements IComponentController {
    private IHospitalModel model;
    private ZaklPanel view;

    public ZaklController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new ZaklPanel(this, model);

        // установка списка шаблонов в представлении
        view.setZaklHistoryShablons(model.loadZaklShablons());
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
     * Передает список возможных исходов заболевания
     */
    public final List<IntegerClassifier> getIshodClassifier() {
        return model.getIshodClassifier();
    }

    /**
     * Передает список возможных результатов лечения
     */
    public final List<IntegerClassifier> getResultClassifier() {
        return model.getResultClassifier();
    }

    /**
     * Передает в модель запрос на добавление заключения
     * @param zakl
     */
    public final void addZakl(final Zakl zakl) {
        try {
            model.addZakl(zakl);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(view, "Ошибка при добавлении заключения", "Ошибка!",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

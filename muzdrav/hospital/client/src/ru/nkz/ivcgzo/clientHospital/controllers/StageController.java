package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.StagePanel;

/**
 * Контроллер панели этапов лечения
 */
public class StageController implements IComponentController {
    private IHospitalModel model;
    private StagePanel view;

    public StageController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new StagePanel(this, model);
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

}

package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.Assignments;

public class AssignmentsController implements IComponentController {
    private IHospitalModel model;
    private Assignments view;

    public AssignmentsController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new Assignments(this, model);

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

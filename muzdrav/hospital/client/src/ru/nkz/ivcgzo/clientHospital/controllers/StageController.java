package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.StagePanel;

public class StageController implements IComponentController {
    private IHospitalModel model;
    private StagePanel view;

    public StageController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new StagePanel(this, model);
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

}

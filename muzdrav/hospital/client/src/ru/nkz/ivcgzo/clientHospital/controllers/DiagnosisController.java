package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiagnosisPanel;

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
}

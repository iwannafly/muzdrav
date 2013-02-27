package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiaryPanel;

public class DiaryController implements IComponentController {
    private IHospitalModel model;
    private DiaryPanel view;

    public DiaryController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new DiaryPanel(this, model);

        view.setMedicalHistoryShablons(model.loadMedicalHistoryShablons());
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

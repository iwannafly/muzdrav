package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;
import java.util.List;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.ZaklPanel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

public class ZaklController implements IComponentController {
    private IHospitalModel model;
    private ZaklPanel view;

    public ZaklController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new ZaklPanel(this, model);

        view.setZaklHistoryShablons(model.loadMedicalHistoryShablons());
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

    public final List<IntegerClassifier> getIshodClassifier() {
        return model.getIshodClassifier();
    }

    public final List<IntegerClassifier> getResultClassifier() {
        return model.getResultClassifier();
    }

    public final void addZakl(final Zakl zakl) {
        try {
            model.addZakl(zakl);
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(view, "Ошибка при добавлении заключения", "Ошибка!",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.PersonalInfoPanel;


public class PersonalInfoController implements IComponentController {
    private IHospitalModel model;
    private PersonalInfoPanel view;

    public PersonalInfoController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new PersonalInfoPanel(this, model);
        view.setOtdProfList(model.getOtdProfs());
//        view.createFrames();
//        view.createControls();
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

    public final void setPriemInfo() {
        model.setPriemInfo();
    }

    public final void updatePatient(final String chamber, final int otdProf,
            final String surname, final String name, final String middlename) {
        model.updatePatient(chamber, otdProf, surname, name, middlename);
    }

}

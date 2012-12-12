package ru.nkz.ivcgzo.clientInfomat;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientInfomat.ui.InfomatView;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class Controller implements IController {
    private IModel model;
    private InfomatView view;

    public Controller(final IModel curModel) {
        this.model = curModel;
        this.view = new InfomatView(this, model);
        view.createFrames();
        view.createControls();
    }

    //FIXME костыль
    public final JFrame getMainFrame() {
        return view.getMainFrame();
    }

    @Override
    public final void setPoliclinics() {
        model.setPoliclinics();
    }

    @Override
    public final void setCurrentPoliclinic(final IntegerClassifier currentPoliclinic) {
        model.setCurrentPoliclinic(currentPoliclinic);
    }

    @Override
    public final void setSpecialities(final int cpol) {
        model.setSpecialities(cpol);
    }

    @Override
    public final void setDoctors(final int cpol, final String cdol) {
        model.setDoctors(cpol, cdol);
    }

    @Override
    public final void setTalons(final int cpol, final String cdol, final int pcod) {
        model.setTalons(cpol, cdol, pcod);
    }

    @Override
    public final void setPatient(final String oms) {
        model.setPatient(oms);
    }

    @Override
    public final void setReservedTalon(final int patientId) {
        model.setReservedTalons(patientId);
    }

    @Override
    public final void setShedule(final int pcod, final int cpol, final String cdol) {
        model.setShedule(pcod, cpol, cdol);
    }

    @Override
    public final void reserveTalon(final TPatient pat, final TTalon talon) {
        model.reserveTalon(pat, talon);
    }

    @Override
    public final void releaseTalon(final TTalon talon) {
        model.releaseTalon(talon);
    }

    @Override
    public final void setCurrentSpeciality(final StringClassifier currentSpeciality) {
        model.setCurrentSpeciality(currentSpeciality);
    }

    @Override
    public final void setCurrentDoctor(final IntegerClassifier currentDoctor) {
        model.setCurrentDoctor(currentDoctor);
    }

    @Override
    public final void setSelectedTalon(final TTalon talon) {
        model.setTalon(talon);
    }

    @Override
    public final void setCurrentReservedTalon(final TTalon talon) {
        model.setCurrentReservedTalon(talon);
    }

}

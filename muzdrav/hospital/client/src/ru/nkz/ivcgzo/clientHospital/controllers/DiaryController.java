package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.DiaryPanel;
import ru.nkz.ivcgzo.clientHospital.views.ShablonForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;

public class DiaryController implements IComponentController {
    private IHospitalModel model;
    private DiaryPanel view;
    private ru.nkz.ivcgzo.clientHospital.views.ShablonForm shForm;

    public DiaryController(final IHospitalModel curModel) {
        this.model = curModel;
        this.view = new DiaryPanel(this, model);

        shForm = new ShablonForm(model);
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

    public final void showShablonForm(final String text, final IntegerClassifier value) {
        shForm.showShablonForm(text, value);
        view.syncShablonList(shForm.getSearchString(), shForm.getShablon());
        view.pasteSelectedShablon(shForm.getShablon());
    }

    public final void setCurrentDiaryRecord(final TMedicalHistory currentMedicalHistory) {
        model.setMedicalHistory(currentMedicalHistory);
    }

    public final void addDiaryRecord() {
        TMedicalHistory medHist = new TMedicalHistory();
        medHist.setDataz(System.currentTimeMillis());
        medHist.setTimez(System.currentTimeMillis());
        medHist.setPcodVrach(ClientHospital.authInfo.getPcod());
        medHist.setIdGosp(model.getPatient().getGospitalCod());
        medHist.setPcod_added(ClientHospital.authInfo.getPcod());
        medHist.setCpodr(ClientHospital.authInfo.getCpodr());
        System.out.println(ClientHospital.authInfo.getCpodr());
        System.out.println(ClientHospital.authInfo.getPcod());
        model.addMedicalHistory(medHist);

        model.setDiaryList();
        view.updateDiaryTable();
    }

    public final void deleteMedicalHistory(final TMedicalHistory currentMedHist) {
        model.deleteMedicalHistory(currentMedHist);
        model.setDiaryList();
        view.updateDiaryTable();
    }

}

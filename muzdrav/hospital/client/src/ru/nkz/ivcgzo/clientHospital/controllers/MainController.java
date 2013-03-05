package ru.nkz.ivcgzo.clientHospital.controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.views.MainFrame;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

public class MainController {
    private IComponentController personalInfoController;
    private IComponentController diaryController;
    private IComponentController diagnosisController;
    private IComponentController stageController;
    private IComponentController zaklController;
    private IHospitalModel model;
    private MainFrame view;
    private ru.nkz.ivcgzo.clientHospital.views.CurationFrame curFrame;
    private ru.nkz.ivcgzo.clientHospital.views.PatientSelectFrame patSelectFrame;

    public MainController(final IHospitalModel inModel) {
        this.model = inModel;
        this.view = new MainFrame(this, model);
    }

    public final JFrame getFrame() {
        return view.getFrame();
    }

    public final void showCurationFrame() {
        curFrame.refreshModel(ClientHospital.authInfo);
        curFrame.setVisible(true);
    }

    public final void onConnect() {
        curFrame = new ru.nkz.ivcgzo.clientHospital.views.CurationFrame();
        patSelectFrame = new ru.nkz.ivcgzo.clientHospital.views.PatientSelectFrame(this, model);

        personalInfoController = new PersonalInfoController(model);
        addComponentInTabbedPane(personalInfoController);

        diaryController = new DiaryController(model);
        addComponentInTabbedPane(diaryController);

        diagnosisController = new DiagnosisController(model);
        addComponentInTabbedPane(diagnosisController);

        if (isRd()) {
            //TODO показать вкладки для роддомов
        }

        stageController = new StageController(model);
        addComponentInTabbedPane(stageController);

        zaklController = new ZaklController(model);
        addComponentInTabbedPane(zaklController);
    }

    private void addComponentInTabbedPane(final IComponentController controller) {
        view.addComponentInTabbedPane(
            controller.getComponent(),
            controller.getTitle(),
            controller.getIconURL(),
            controller.getPanelTooltipText()
        );
    }

    private boolean isRd() {
        return (ClientHospital.authInfo.getClpu() == 62)
                || (ClientHospital.authInfo.getClpu() == 63)
                || (ClientHospital.authInfo.getClpu() == 64)
                || (ClientHospital.authInfo.getCpodr() == 2407)
                || (ClientHospital.authInfo.getCpodr() == 2409)
                || (ClientHospital.authInfo.getCpodr() == 2411)
                || (ClientHospital.authInfo.getCpodr() == 2412)
                || (ClientHospital.authInfo.getCpodr() == 8301)
                || (ClientHospital.authInfo.getCpodr() == 8305);
    }

    public final void selectPatient(final TSimplePatient selectedPatient) {
        patSelectFrame.refreshModel();
        patSelectFrame.setVisible(true);

        try {
            model.setPatient(selectedPatient.getIdGosp());
            view.setFrameTitle(String.format("%s %s %s",
                    model.getPatient().getSurname(),
                    model.getPatient().getName(),
                    model.getPatient().getMiddlename())
            );
        } catch (PatientNotFoundException e) {
            JOptionPane.showMessageDialog(patSelectFrame, "Пациент не найден!");
            view.setFrameTitle("Пациент не выбран");
        } catch (HospitalDataTransferException e) {
            JOptionPane.showMessageDialog(
                    patSelectFrame, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
            view.setFrameTitle("Пациент не выбран");
        }
    }

    public final void showPatientSelectFrame() {
        patSelectFrame.refreshModel();
        patSelectFrame.setVisible(true);
    }

    public final void showExternalPatientInfoFrame() {
        if (model.getPatient() != null) {
            ClientHospital.conMan.showPatientInfoForm(
                "Информация о пациенте", model.getPatient().getPatientId()
            );
        }
    }

    public final void showExternalAnamnezFrame() {
        if (model.getPatient() != null) {
            ClientHospital.conMan.showPatientAnamnezForm(
                model.getPatient().getPatientId()
            );
        }
    }

    public final void showExternalBolListFrame() {
        if (model.getPatient() != null) {
            ClientHospital.conMan.showBolListForm(
                model.getPatient().getPatientId(), 0, model.getPatient().getGospitalCod()
            );
        }
    }

    public final void showExternalIssledFrame() {
        if (model.getPatient() != null) {
            ClientHospital.conMan.showLabRecordForm(
                model.getPatient().getPatientId(),
                model.getPatient().getSurname(),
                model.getPatient().getName(),
                model.getPatient().getMiddlename(),
                model.getPatient().getGospitalCod()
            );
        }
    }

    public final void showExternalOperationFrame() {
        if (model.getPatient() != null) {
            ClientHospital.conMan.showOperationForm(
                model.getPatient().getPatientId(),
                model.getPatient().getSurname(),
                model.getPatient().getName(),
                model.getPatient().getMiddlename(),
                model.getPatient().getGospitalCod()
            );
        }
    }

    public final void showExternalReestrFrame() {
        Integer result = ClientHospital.conMan.showMedStaErrorsForm();
        if (result != null) {
            try {
                model.setPatientByCotd(result);
                view.setFrameTitle(String.format("%s %s %s",
                    model.getPatient().getSurname(),
                    model.getPatient().getName(),
                    model.getPatient().getMiddlename())
                );
            } catch (PatientNotFoundException e) {
                JOptionPane.showMessageDialog(view, "Пациент не найден!");
                view.setFrameTitle("Пациент не выбран");
            } catch (HospitalDataTransferException e) {
                JOptionPane.showMessageDialog(
                    view, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
                view.setFrameTitle("Пациент не выбран");
            }
        }
    }

}

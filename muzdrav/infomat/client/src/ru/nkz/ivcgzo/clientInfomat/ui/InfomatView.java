package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientInfomat.IController;
import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IDoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISpecialityObserver;

public class InfomatView implements IDoctorObserver, ISpecialityObserver,
        IPoliclinicObserver {
    private IModel model;
    private IController controller;
    private LpuSelectFrame lpuSelectFrame;
    private AuthorizationFrame authFrame;
    private DoctorSelectFrame doctorFrame;
    private OptionsDialog optionsDialog;
    private MainFrame mainFrame;
    private TalonSelectFrame talonSelectFrame;
    private ReservedTalonsFrame resTalonSelectFrame;
    private SheduleFrame sheduleFrame;

    public InfomatView(IController controller,
            IModel model) {
        this.controller = controller;
        this.model = model;
        model.registerDoctorObserver((IDoctorObserver) this);
        model.registerPoliclinicObserver((IPoliclinicObserver) this);
        model.registerSpecialityObserver((ISpecialityObserver) this);
    }

    public void createFrames() {
        mainFrame = new MainFrame();
        lpuSelectFrame = new LpuSelectFrame();
        lpuSelectFrame.setVisible(false);
        authFrame = new AuthorizationFrame();
        authFrame.setVisible(false);
        doctorFrame = new DoctorSelectFrame();
        doctorFrame.setVisible(false);
        talonSelectFrame = new TalonSelectFrame();
        talonSelectFrame.setVisible(false);
        resTalonSelectFrame = new ReservedTalonsFrame();
        resTalonSelectFrame.setVisible(false);
        sheduleFrame = new SheduleFrame();
        sheduleFrame.setVisible(false);
    }

    public void createControls() {
        setMainFrameControls();
    }

    private void setMainFrameControls() {
        mainFrame.addAppointmentListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setPoliclinics();
                mainFrame.setVisible(false);
                lpuSelectFrame.showAsModal(0);
            }
        });
        mainFrame.addPersonalInfoListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authFrame.setVisible(true);
            }
        });
        mainFrame.addSheduleListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setPoliclinics();
                mainFrame.setVisible(false);
                lpuSelectFrame.showAsModal(1);
            }
        });
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    @Override
    public void updatePoliclinic() {
        lpuSelectFrame.updateLpuList(model.getPoliclinics());
    }

    @Override
    public void updateSpeciality() {

    }

    @Override
    public void updateDoctor() {

    }

}

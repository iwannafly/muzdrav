package ru.nkz.ivcgzo.clientInfomat.ui;

import ru.nkz.ivcgzo.clientInfomat.model.HospitalModelInterface;
import ru.nkz.ivcgzo.clientInfomat.model.observers.DoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.PoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.SpecialityObserver;

public class HospitalView implements DoctorObserver, SpecialityObserver,
        PoliclinicObserver {
    private HospitalModelInterface hospitalModel;
    private LpuSelectFrame lpuSelectFrame;
    private AuthorizationFrame authFrame;
    private DoctorSelectFrame doctorFrame;
    private OptionsDialog optionsDialog;
    private MainFrame mainFrame;
    private TalonSelectFrame talonSelectFrame;
    private ReservedTalonsFrame resTalonSelectFrame;
    private SheduleFrame sheduleFrame;

    @Override
    public void updatePoliclinic() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateSpeciality() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateDoctor() {
        // TODO Auto-generated method stub

    }

}

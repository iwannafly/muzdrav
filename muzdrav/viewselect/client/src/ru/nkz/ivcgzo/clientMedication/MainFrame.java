package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMedication.Patient;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8573682902821548961L;
    private UserAuthInfo doctorInfo;
    private Patient patient;

    public MainFrame(final UserAuthInfo authInfo) {
        doctorInfo = authInfo;
        initialization();
    }

    private void initialization() {
        
    }

    public final void fillPatient(final int id, final String surname,
            final String name, final String middlename, final int idGosp) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        patient.setIdGosp(idGosp);
    }
}

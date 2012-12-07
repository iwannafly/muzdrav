package ru.nkz.ivcgzo.clientInfomat;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;


public interface IController {
    public void setPoliclinics();

    public void setSpecialities(int cpol);

    public void setDoctors(int cpol, String cdol);

    public void setTalons(int cpol, String cdol, int pcod);

    public void setPatient(String oms);

    public void setReservedTalon(int patientId);

    public void setShedule(int pcod, int cpol, String cdol);

    public void reserveTalon(TPatient pat, TTalon talon);

    public void releaseTalon(TTalon talon);

    //FIXME костыль, избавиться от него!
    public JFrame getMainFrame();
}

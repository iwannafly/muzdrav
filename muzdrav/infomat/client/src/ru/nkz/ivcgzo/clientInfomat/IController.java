package ru.nkz.ivcgzo.clientInfomat;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientInfomat.ui.InfomatFrame;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TPatient;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;


public interface IController {
    void setCurrentPoliclinic(IntegerClassifier currentPoliclinic);

    void setCurrentSpeciality(StringClassifier currentSpeciality);

    void setCurrentDoctor(IntegerClassifier currentDoctor);

    void setTalons(int cpol, String cdol, int pcod);

    void setReservedTalon(int patientId);

    void setShedule(int pcod, int cpol, String cdol);

    void reserveTalon(TPatient pat, TTalon talon);

    void releaseTalon(TTalon talon);

    void setSelectedTalon(TTalon talon);

    void setCurrentReservedTalon(TTalon talon);

    //FIXME костыль, избавиться от него!
    JFrame getMainFrame();

    void openLpuSelectFrame();

    void openAuthorizationFrame();

    void openDoctorSelectFrame(IntegerClassifier currentPoliclinic);

    void backToMainFrame(InfomatFrame currentFrame);

    void setDoctorList(StringClassifier currentSpeciality);

    void openTalonSelectFrame(IntegerClassifier currentDoctor);

    void openSheduleFrame(IntegerClassifier currentDoctor);

    void initiateReservedTalonSelect(TTalon curTalon);

    void closeAuthorizationFrame();

    void checkPatientOms(String omsNumber);
}

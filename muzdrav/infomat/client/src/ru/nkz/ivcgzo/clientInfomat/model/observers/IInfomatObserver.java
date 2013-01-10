package ru.nkz.ivcgzo.clientInfomat.model.observers;

public interface IInfomatObserver {
    void updateCurrentDoctor();
    void updateReservedTalon();
    void updateCurrentPoliclinic();
    void updateCurrentSpeciaity();
    void updateDoctors();
    void updatePatient();
    void updatePoliclinics();
    void updateSelectedTalon();
    void updateSpecialities();
}

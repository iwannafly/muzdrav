package ru.nkz.ivcgzo.clientInfomat;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientInfomat.model.Model;
import ru.nkz.ivcgzo.clientInfomat.ui.InfomatFrame;
import ru.nkz.ivcgzo.clientInfomat.ui.InfomatView;
import ru.nkz.ivcgzo.clientInfomat.ui.OptionsDialog;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.OmsNotValidException;
import ru.nkz.ivcgzo.thriftInfomat.PatientHasSomeReservedTalonsOnThisDay;
import ru.nkz.ivcgzo.thriftInfomat.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.SomebodyElseReservedThisTalon;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class Controller implements IController {
    private IModel model;
    private InfomatView view;
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy");

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
    public final void setCurrentPoliclinic(final IntegerClassifier currentPoliclinic) {
        model.setCurrentPoliclinic(currentPoliclinic);
    }

    @Override
    public final void setTalons(final int cpol, final String cdol, final int pcod) {
        try {
            model.setTalons(cpol, cdol, pcod);
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            ClientInfomat.conMan.reconnect(e);
        }
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
    public final void reserveTalon() {
        if ((model.getPatient() != null) && (model.getTalon() != null)) {
            view.closeAuthrizationFrame();
            try {
                if (!model.isPatientAlreadyReserveTalonOnThisDay(
                        model.getPatient(), model.getTalon())) {
                    String talonText = " ЛПУ: " + model.getCurrentPoliclinic().getName()
                        + "\n Врач: " + model.getCurrentDoctor().getName()
                        + "\n Специальность: " + model.getCurrentSpeciality()
                        + "\n Пациент : " + model.getPatient().getSurname()
                            + " " + model.getPatient().getName()
                            + " " + model.getPatient().getMiddlename()
                        + "\n Дата приёма: "
                            + DEFAULT_DATE_FORMAT.format(new Date(model.getTalon().getDatap()))
                        + "\n Время приёма: "
                            + DEFAULT_TIME_FORMAT.format(new Time(model.getTalon().getTimep()));
                    final int dialogResult = view.showPrintDialog(talonText);
                    if (dialogResult == OptionsDialog.PRINT) {
                        try {
                            model.reserveTalon(model.getPatient(), model.getTalon());
                            view.showMessageDialog("Вы успешно записаны на приём.");
                        } catch (ReserveTalonOperationFailedException e) {
                            view.showMessageDialog("Ошибка во время записи. Запись отменена.");
                            e.printStackTrace();
                        } catch (PatientHasSomeReservedTalonsOnThisDay e) {
                            view.showMessageDialog(
                                "Ошибка! Вы уже записаны на "
                                + DEFAULT_DATE_FORMAT.format(new Date(model.getTalon().getDatap()))
                                + " к выбранному специалисту."
                            );
                            e.printStackTrace();
                        } catch (SomebodyElseReservedThisTalon e) {
                            view.showMessageDialog("Кто-то уже успел записаться на этот талон.");
                            e.printStackTrace();
                        } catch (KmiacServerException e) {
                            view.showMessageDialog("Ошибка во время записи. Запись отменена.");
                            e.printStackTrace();
                        } catch (TException e) {
                            view.showMessageDialog("Ошибка во время записи. Запись отменена.");
                            e.printStackTrace();
                            ClientInfomat.conMan.reconnect(e);
                        }
                    } else {
                        view.showMessageDialog("Запись отменена.");
                    }
                } else {
                    view.showMessageDialog(
                        "Ошибка! Вы уже записаны на "
                        + DEFAULT_DATE_FORMAT.format(new Date(model.getTalon().getDatap()))
                        + " к выбранному специалисту."
                    );
                }
            } catch (TException e) {
                view.showMessageDialog("Ошибка во время записи. Запись отменена.");
                ClientInfomat.conMan.reconnect(e);
                e.printStackTrace();
            }
            refreshTalonTable();
        } else {
            view.showMessageDialog("Введенный номер полиса не найден в БД.");
        }
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
        if (talon != null) {
            model.setTalon(talon);
        }
    }

    @Override
    public final void setCurrentReservedTalon(final TTalon talon) {
        model.setCurrentReservedTalon(talon);
    }

    public void setTalonMode() {
    	((Model) model).setTalonMode();
    }

    public void setScheduleMode() {
    	((Model) model).setScheduleMode();
    }

    @Override
    public final void openLpuSelectFrame() {
        try {
            model.setPoliclinics();
            view.openLpuSelectFrame();
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки поликлиник! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки поликлиник! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void openAuthorizationFrame() {
        view.showAuthorizationFrame();
    }

    @Override
    public final void openDoctorSelectFrame(final IntegerClassifier currentPoliclinic) {
        model.setCurrentPoliclinic(currentPoliclinic);
        try {
            model.setSpecialities(currentPoliclinic.getPcod());
            view.openDoctorSelectFrame();
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки специальностей! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки специальностей! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void backToMainFrame(final InfomatFrame currentFrame) {
        currentFrame.setVisible(false);
        view.openMainFrame();
    }

    @Override
    public final void setDoctorList(final StringClassifier currentSpeciality) {
        model.setCurrentSpeciality(currentSpeciality);
        try {
            model.setDoctors(model.getCurrentPoliclinic().getPcod(),
                currentSpeciality.getPcod());
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки врачей! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки врачей! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void openTalonSelectFrame(final IntegerClassifier currentDoctor) {
        model.setCurrentDoctor(currentDoctor);
        try {
            view.openTalonSelectFrame(
                model.getTalonTableModel(
                    model.getCurrentPoliclinic().getPcod(),
                    model.getCurrentSpeciality().getPcod(),
                    model.getCurrentDoctor().getPcod()
                )
            );
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void openSheduleFrame(final IntegerClassifier currentDoctor) {
        model.setCurrentDoctor(currentDoctor);
        try {
            view.openSheduleFrame(
                model.getSheduleTableModel(
                    model.getCurrentDoctor().getPcod(),
                    model.getCurrentPoliclinic().getPcod(),
                    model.getCurrentSpeciality().getPcod()
                )
            );
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки расписания! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки расписания! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    private void refreshReservedTalonTable(final int id) {
        try {
            view.refreshReservedTalonTable(
                model.getReservedTalonTableModel(
                    model.getPatient().getId()
                )
            );
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки занятых талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки занятых талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void initiateReservedTalonSelect(final TTalon curTalon) {
        final int dialogResult = view.showConfirmDialog("Удалить выбранный талон?");
        if (dialogResult == OptionsDialog.ACCEPT) {
            if (curTalon != null) {
                model.releaseTalon(curTalon);
                refreshReservedTalonTable(model.getPatient().getId());
            }
        } else {
            refreshReservedTalonTable(model.getPatient().getId());
        }
    }

    @Override
    public final void closeAuthorizationFrame() {
        view.closeAuthrizationFrame();
    }

    @Override
    public final void checkPatientOms(final String omsNumber) {
        if ((omsNumber == null) || (omsNumber.isEmpty() || (omsNumber.trim().isEmpty()))) {
            view.closeAuthrizationFrame();
            view.showMessageDialog("Номер полиса не найден в базе данных! "
                + "Обратитесь к системному администратору!");
        } else {
            try {
                model.setPatient(omsNumber);
                view.closeAuthrizationFrame();
            } catch (OmsNotValidException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Номер полиса не найден в базе данных! "
                    + "Обратитесь к системному администратору!");
            } catch (KmiacServerException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Ошибка проверки полиса в БД! "
                        + "Обратитесь к системному администратору!");
                e.printStackTrace();
            } catch (TException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Ошибка проверки полиса в БД! "
                        + "Обратитесь к системному администратору!");
                e.printStackTrace();
                ClientInfomat.conMan.reconnect(e);
            }
        }
        view.closeAuthrizationFrame();
    }

    @Override
    public final void refreshTalonTable() {
        try {
            view.refreshTalonTable(
                model.getTalonTableModel(
                    model.getCurrentPoliclinic().getPcod(),
                    model.getCurrentSpeciality().getPcod(),
                    model.getCurrentDoctor().getPcod()
                )
            );
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void openReservedTalonFrame() {
        try {
            view.openReservedTalonFrame(
                model.getReservedTalonTableModel(
                    model.getPatient().getId()
                )
            );
        } catch (KmiacServerException e) {
            view.showMessageDialog("Ошибка загрузки занятых талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
        } catch (TException e) {
            view.showMessageDialog("Ошибка загрузки занятых талонов! "
                + "Обратитесь к системному администратору!");
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    }

    @Override
    public final void checkPatientOms(final String omsNumber,
            final IntegerClassifier currentPoliclinic) {
        if ((omsNumber == null) || (omsNumber.isEmpty() || (omsNumber.trim().isEmpty()))) {
            view.closeAuthrizationFrame();
            view.showMessageDialog("Номер полиса не найден в базе данных! "
                + "Обратитесь к системному администратору!");
        } else {
            try {
                model.setPatient(omsNumber, currentPoliclinic.getPcod());
                view.closeAuthrizationFrame();
            } catch (OmsNotValidException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Номер полиса не найден в базе данных! "
                    + "Обратитесь к системному администратору!");
            } catch (KmiacServerException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Ошибка проверки полиса в БД! "
                    + "Обратитесь к системному администратору!");
                e.printStackTrace();
            } catch (TException e) {
                view.closeAuthrizationFrame();
                view.showMessageDialog("Ошибка проверки полиса в БД! "
                    + "Обратитесь к системному администратору!");
                e.printStackTrace();
                ClientInfomat.conMan.reconnect(e);
            }
        }
        view.closeAuthrizationFrame();
    }

}

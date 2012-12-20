package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.text.StyleConstants;

import ru.nkz.ivcgzo.clientInfomat.IController;
import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IInfomatObserver;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.SheduleTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class InfomatView implements IInfomatObserver {
    private enum FrameSet {
        appointment,
        personalOffice,
        shedule
    }
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy");
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
    private FrameSet lastFrameSet;

    public InfomatView(final IController inController,
            final IModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerInfomatObserver((IInfomatObserver) this);
    }

    public final void createFrames() {
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
        optionsDialog = new OptionsDialog();
    }

    public final void createControls() {
        setControls();
    }

    // FIXME Вынести управление видимостью окон на уровень контроллера
    // TODO Пробросить все манипуляции, перехваченные листенерами свинга, туда же, в контроллер
    private void setControls() {
        setMainFrameControls();
        setLpuSelectFrameControls();
        setDoctorFrameControls();
        setTalonSelectFrameControls();
        setSheduleFrameControls();
        setReservedTalonFrameControls();
        setAuthFrameControls();
    }

    private void setMainFrameControls() {
        mainFrame.addAppointmentListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lastFrameSet = FrameSet.appointment;
                controller.openLpuSelectFrame();
            }
        });
        mainFrame.addPersonalInfoListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lastFrameSet = FrameSet.personalOffice;
                controller.openAuthorizationFrame();
            }
        });
        mainFrame.addSheduleListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lastFrameSet = FrameSet.shedule;
                controller.openLpuSelectFrame();
            }
        });
    }

    private void setLpuSelectFrameControls() {
        lpuSelectFrame.addListClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftIntegerClassifierList) e.getSource()).getSelectedValue() != null) {
                    IntegerClassifier currentPoliclinic =
                        ((ThriftIntegerClassifierList) e.getSource()).getSelectedValue();
                    controller.openDoctorSelectFrame(currentPoliclinic);
                }
            }
        });
        lpuSelectFrame.addLpuSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.backToMainFrame(lpuSelectFrame);
            }
        });
    }

    private void setDoctorFrameControls() {
        doctorFrame.addSpecialityListClickListener(new MouseAdapter() {
            @SuppressWarnings("rawtypes")
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftStringClassifierList) e.getSource()).getSelectedValue() != null) {
                    StringClassifier currentSpeciality =
                        ((ThriftStringClassifierList) e.getSource()).getSelectedValue();
                    controller.setDoctorList(currentSpeciality);
                }
            }
        });
        doctorFrame.addDoctorListClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftIntegerClassifierList) e.getSource()).getSelectedValue() != null) {
                    IntegerClassifier currentDoctor =
                            ((ThriftIntegerClassifierList) e.getSource()).getSelectedValue();
                    if (lastFrameSet == FrameSet.appointment) {
                        controller.openTalonSelectFrame(currentDoctor);
                    } else if (lastFrameSet == FrameSet.shedule) {
                        controller.openSheduleFrame(currentDoctor);
                    }
                }
            }
        });
        doctorFrame.addDoctorSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.backToMainFrame(doctorFrame);
            }
        });
    }

    private void setTalonSelectFrameControls() {
        talonSelectFrame.addTalonTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                JTable curTable = (JTable) e.getSource();
                final int curRow = curTable.getSelectedRow();
                final int curColumn = curTable.getSelectedColumn();
                TTalon curTalon = ((TalonTableModel) curTable.getModel()).getTalonList()
                    .getTalonByDay(curRow, curColumn);
                controller.setSelectedTalon(curTalon);
            }
        });
        talonSelectFrame.addTalonSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.backToMainFrame(talonSelectFrame);
            }
        });
    }

    private void setSheduleFrameControls() {
        sheduleFrame.addShedulerSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.backToMainFrame(sheduleFrame);
            }
        });
    }

    private void setReservedTalonFrameControls() {
        resTalonSelectFrame.addReservedTalonTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                JTable curTable = (JTable) e.getSource();
                final int curRow = curTable.getSelectedRow();
                TTalon curTalon = ((ReservedTalonTableModel) curTable.getModel())
                    .getReservedTalonList()
                    .get(curRow);
                controller.initiateReservedTalonSelect(curTalon);
            }
        });
        resTalonSelectFrame.addReservedSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.backToMainFrame(resTalonSelectFrame);
            }
        });
    }

    private void setAuthFrameControls() {
        authFrame.addButtonAcceptPatientCheckListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.checkPatientOms(authFrame.getOmsText().trim());
            }
        });
        authFrame.addButtonCancelPatientCheckListener(new ActionListener()  {
            public void actionPerformed(final ActionEvent e) {
                controller.closeAuthorizationFrame();
            }
        });
    }

    public final JFrame getMainFrame() {
        return mainFrame;
    }

    @Override
    public final void updatePoliclinics() {
        lpuSelectFrame.updateLpuList(model.getPoliclinics());
    }

    @Override
    public final void updateSpecialities() {
        doctorFrame.updateSpecialitiesList(model.getSpecialities());
    }

    @Override
    public final void updateDoctors() {
        doctorFrame.updateDoctorsList(model.getDoctors());
    }

    @Override
    public void updateCurrentDoctor() {
    }

    @Override
    public void updateCurrentSpeciaity() {
    }

    @Override
    public void updateCurrentPoliclinic() {
    }

    @Override
    public final void updatePatient() {
        if (lastFrameSet == FrameSet.personalOffice) {
            if (model.getPatient() != null) {
                controller.openReservedTalonFrame();
                authFrame.setVisible(false);
                mainFrame.setVisible(false);
            } else {
                authFrame.setVisible(false);
                optionsDialog.showMessageDialog(mainFrame, "Номер ОМС не найден в базе данных!");
            }
        } else if (lastFrameSet == FrameSet.appointment) {
//            if ((model.getPatient() != null) && (model.getTalon() != null)) {
              controller.reserveTalon();
//                authFrame.setVisible(false);
//                String talonText = " ЛПУ: " + model.getCurrentPoliclinic().getName()
//                    + "\n Врач: " + model.getCurrentDoctor().getName()
//                    + "\n Специальность: " + model.getCurrentSpeciality()
//                    + "\n Пациент : " + model.getPatient().getSurname()
//                        + " " + model.getPatient().getName()
//                        + " " + model.getPatient().getMiddlename()
//                    + "\n Дата приёма: "
//                        + DEFAULT_DATE_FORMAT.format(new Date(model.getTalon().getDatap()))
//                    + "\n Время приёма: "
//                        + DEFAULT_TIME_FORMAT.format(new Time(model.getTalon().getTimep()));
//                int optResult = optionsDialog.showPrintDialog(talonSelectFrame, talonText,
//                    12, StyleConstants.ALIGN_LEFT);
//                if (optResult == OptionsDialog.PRINT) {
//                    controller.reserveTalon(model.getPatient(), model.getTalon());
//                    optionsDialog.showMessageDialog(talonSelectFrame,
//                        "Вы успешно записаны на приём.");
//                } else {
//                    optionsDialog.showMessageDialog(talonSelectFrame, "Запись отменена.");
//                }
//                controller.refreshTalonTable();
//            } else {
//                optionsDialog.showMessageDialog(talonSelectFrame,
//                    "Введенный номер полиса не найден в БД.");
//            }
        }
    }

    @Override
    public final void updateSelectedTalon() {
        authFrame.setVisible(true);
    }

    @Override
    public final void updateReservedTalon() {
//        if ((model.getPatient() != null) && (model.getTalon() != null)) {
//            controller.releaseTalon(model.getTalon());
//            resTalonSelectFrame.refreshTalonTableModel(
//                model.getReservedTalonTableModel(model.getPatient().getId())
//            );
//        }
    }

    public final void openLpuSelectFrame() {
        lpuSelectFrame.showAsModal();
        mainFrame.setVisible(false);
    }

    public final void showAuthorizationFrame() {
        authFrame.setVisible(true);
    }

    public final void openDoctorSelectFrame() {
        doctorFrame.showModal();
        lpuSelectFrame.setVisible(false);
    }

    public final void openMainFrame() {
        mainFrame.setVisible(true);
    }

    public final void openTalonSelectFrame(final TalonTableModel talonTableModel) {
        talonSelectFrame.showModal(talonTableModel);
        doctorFrame.setVisible(false);
    }

    public final void openReservedTalonFrame(final ReservedTalonTableModel resTalonTableModel) {
        resTalonSelectFrame.showModal(resTalonTableModel);
    }

    public final void openSheduleFrame(final SheduleTableModel sheduleTableModel) {
        sheduleFrame.showModal(sheduleTableModel);
        doctorFrame.setVisible(false);
    }

    public final void showMessageDialog(final String message) {
        if (optionsDialog != null) {
            optionsDialog.showMessageDialog(null, message);
        }
    }

    public final int showConfirmDialog(final String message) {
        if (optionsDialog != null) {
            return optionsDialog.showConfirmDialog(null, message);
        } else {
            return OptionsDialog.DECLINE;
        }
    }

    public final int showPrintDialog(final String message) {
        if (optionsDialog != null) {
            return optionsDialog.showPrintDialog(null, message, 12, StyleConstants.ALIGN_LEFT);
        } else {
            return OptionsDialog.DECLINE;
        }
    }

    public final void refreshReservedTalonTable(final ReservedTalonTableModel tableModel) {
        resTalonSelectFrame.refreshTalonTableModel(tableModel);
    }

    public final void refreshTalonTable(final TalonTableModel tableModel) {
        talonSelectFrame.refreshTalonTableModel(tableModel);
    }

    public final void closeAuthrizationFrame() {
        authFrame.clearOmsText();
        authFrame.setVisible(false);
    }
}

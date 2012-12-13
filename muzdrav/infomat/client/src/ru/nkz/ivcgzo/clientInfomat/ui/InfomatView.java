package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientInfomat.IController;
import ru.nkz.ivcgzo.clientInfomat.model.IModel;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentDoctorObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentIReservedTalonObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentPoliclinicObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ICurrentSpecialityObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IDoctorsObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPatientObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.IPoliclinicsObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISelectedTalonObserver;
import ru.nkz.ivcgzo.clientInfomat.model.observers.ISpecialitiesObserver;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class InfomatView implements IDoctorsObserver, ISpecialitiesObserver,
        IPoliclinicsObserver, ICurrentPoliclinicObserver, ICurrentSpecialityObserver,
        ICurrentDoctorObserver, IPatientObserver, ISelectedTalonObserver,
        ICurrentIReservedTalonObserver {
    private enum FrameSet {
        appointment,
        personalOffice,
        shedule
    }
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
        model.registerDoctorsObserver((IDoctorsObserver) this);
        model.registerPoliclinicsObserver((IPoliclinicsObserver) this);
        model.registerSpecialitiesObserver((ISpecialitiesObserver) this);
        model.registerCurrentDoctorObserver((ICurrentDoctorObserver) this);
        model.registerCurrentPoliclinicObserver((ICurrentPoliclinicObserver) this);
        model.registerCurrentSpecialityObserver((ICurrentSpecialityObserver) this);
        model.registerPatientObserver((IPatientObserver) this);
        model.registerSelectedTalonObserver((ISelectedTalonObserver) this);
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
        setMainFrameControls();
    }

    // FIXME Вынести управление видимостью окон на уровень контроллера
    // TODO Пробросить все манипуляции, перехваченные листенерами свинга, туда же, в контроллер
    private void setMainFrameControls() {
        mainFrame.addAppointmentListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.setPoliclinics();
                lastFrameSet = FrameSet.appointment;
                lpuSelectFrame.showAsModal();
                mainFrame.setVisible(false);
            }
        });
        mainFrame.addPersonalInfoListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lastFrameSet = FrameSet.personalOffice;
                authFrame.setVisible(true);
            }
        });
        mainFrame.addSheduleListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.setPoliclinics();
                lastFrameSet = FrameSet.shedule;
                lpuSelectFrame.showAsModal();
                mainFrame.setVisible(false);
            }
        });

        lpuSelectFrame.addListClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftIntegerClassifierList) e.getSource()).getSelectedValue() != null) {
                    IntegerClassifier currentPoliclinic =
                        ((ThriftIntegerClassifierList) e.getSource()).getSelectedValue();
                    controller.setCurrentPoliclinic(currentPoliclinic);
                    controller.setSpecialities(currentPoliclinic.getPcod());
                    doctorFrame.showModal();
                    lpuSelectFrame.setVisible(false);
                }
            }
        });
        lpuSelectFrame.addLpuSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                lpuSelectFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        doctorFrame.addSpecialityListClickListener(new MouseAdapter() {
            @SuppressWarnings("rawtypes")
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftStringClassifierList) e.getSource()).getSelectedValue() != null) {
                    StringClassifier currentSpeciality =
                        ((ThriftStringClassifierList) e.getSource()).getSelectedValue();
                    controller.setCurrentSpeciality(currentSpeciality);
                    controller.setDoctors(model.getCurrentPoliclinic().getPcod(),
                        currentSpeciality.getPcod());
                }
            }
        });
        doctorFrame.addDoctorListClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (((ThriftIntegerClassifierList) e.getSource()).getSelectedValue() != null) {
                    IntegerClassifier currentDoctor =
                            ((ThriftIntegerClassifierList) e.getSource()).getSelectedValue();
                    controller.setCurrentDoctor(currentDoctor);
                    if (lastFrameSet == FrameSet.appointment) {
                        talonSelectFrame.showModal(
                            model.getTalonTableModel(
                                model.getCurrentPoliclinic().getPcod(),
                                model.getCurrentSpeciality().getPcod(),
                                model.getCurrentDoctor().getPcod()
                            )
                        );
                        doctorFrame.setVisible(false);
                        mainFrame.setVisible(false);
                    } else if (lastFrameSet == FrameSet.shedule) {
                        sheduleFrame.showModal(
                            model.getSheduleTableModel(
                                model.getCurrentDoctor().getPcod(),
                                model.getCurrentPoliclinic().getPcod(),
                                model.getCurrentSpeciality().getPcod()
                            )
                        );
                        doctorFrame.setVisible(false);
                        mainFrame.setVisible(false);
                    }
                }
            }
        });
        doctorFrame.addDoctorSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                doctorFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        talonSelectFrame.addTalonTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                JTable curTable = (JTable) e.getSource();
                final int curRow = curTable.getSelectedRow();
                final int curColumn = curTable.getSelectedColumn();
                TTalon curTalon = ((TalonTableModel) curTable.getModel()).getTalonList()
                    .getTalonByDay(curRow, curColumn);
                if (curTalon != null) {
                    controller.setSelectedTalon(curTalon);
                }
            }
        });
        talonSelectFrame.addTalonSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                talonSelectFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        sheduleFrame.addShedulerSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                sheduleFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        resTalonSelectFrame.addReservedTalonTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                int dialogResult = new OptionsDialog().showConfirmDialog(resTalonSelectFrame,
                    "Отменить запись?");
                if (dialogResult == OptionsDialog.ACCEPT) {
                    JTable curTable = (JTable) e.getSource();
                    final int curRow = curTable.getSelectedRow();
                    TTalon curTalon = ((ReservedTalonTableModel) curTable.getModel())
                        .getReservedTalonList()
                        .get(curRow);
                    if (curTalon != null) {
                        controller.releaseTalon(curTalon);
                        resTalonSelectFrame.refreshTalonTableModel(
                            model.getReservedTalonTableModel(
                                model.getPatient().getId()
                            )
                        );
                    }
                } else {
                    resTalonSelectFrame.refreshTalonTableModel(
                        model.getReservedTalonTableModel(
                            model.getPatient().getId()
                        )
                    );
                }
            }
        });
        resTalonSelectFrame.addReservedSelectBackwardListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                resTalonSelectFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        authFrame.addButtonAcceptPatientCheckListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.setPatient(authFrame.getOmsText().trim());
                authFrame.clearOmsText();
                authFrame.setVisible(false);
            }
        });
        authFrame.addButtonCancelPatientCheckListener(new ActionListener()  {
            public void actionPerformed(final ActionEvent e) {
                authFrame.clearOmsText();
                authFrame.setVisible(false);
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
                resTalonSelectFrame.showModal(
                    model.getReservedTalonTableModel(
                        model.getPatient().getId()
                    )
                );
                authFrame.setVisible(false);
                mainFrame.setVisible(false);
            } else {
                authFrame.setVisible(false);
                optionsDialog.showMessageDialog(mainFrame, "Номер ОМС не найден в базе данных!");
            }
        } else if (lastFrameSet == FrameSet.appointment) {
            if ((model.getPatient() != null) && (model.getTalon() != null)) {
                authFrame.setVisible(false);
                controller.reserveTalon(model.getPatient(), model.getTalon());
                talonSelectFrame.refreshTalonTableModel(
                    model.getTalonTableModel(
                        model.getCurrentPoliclinic().getPcod(),
                        model.getCurrentSpeciality().getPcod(),
                        model.getCurrentDoctor().getPcod()
                    )
                );
            }
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

}
package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import ru.nkz.ivcgzo.clientHospital.controllers.MainController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -6126551810745026830L;
    @SuppressWarnings("unused")
    private IHospitalModel model;
    private MainController controller;

/////////////////////////////////// FRAME CONSTANTS ///////////////////////////////////////////////

    private static final int WIDTH = 970;
    private static final int HEIGHT = 700;
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/hospital.png");

////////////////////////// TOOLBAR BUTTON CONSTANTS ///////////////////////////////////////////////

    private static final int TOOLBAR_BUTTON_SIZE = 35;
    private static final URL TOOLBAR_PATIENT_INFO_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/patientInfo.png");
    private static final URL TOOLBAR_ANAMNEZ_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/lifeHistory.png");
    private static final URL TOOLBAR_BOL_LIST_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/diary.png");
    private static final URL TOOLBAR_ISSLED_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/issled.png");
    private static final URL TOOLBAR_OPERATION_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/Skalpell.png");
    private static final URL TOOLBAR_REESTR_ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/archive.png");

////////////////////////////// SWING COMPONENTS ///////////////////////////////////////////////////

    private JTabbedPane tabbedPane;
    private JMenuBar mbMain;
    private JMenu mnPatientOperation;
    private JMenuItem mntmSelectPatient;
    private JMenuItem mntmReception;
    private JMenu mnPrintForms;
    private JMenuItem mntmPrintStationDiary;
    private JMenuItem mntmPrintHospitalSummary;
    private JMenuItem mntmPrintHospitalDeathSummary;
    private JToolBar toolBar;
    private JButton btnShowPatientInfo;
    private JButton btnShowPatientAnamnez;
    private JButton btnShowPatientBolList;
    private JButton btnIssled;
    private JButton btnOperation;
    private JButton btnReestr;


    public MainFrame(final MainController inController, final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        setDefaults();
        setMainMenu();
        setToolBar();
        setTabbedPane();
    }

    private void setDefaults() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ICON));
        setTitle("Врач стаицонара");
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
    }

    private void setMainMenu() {
        mbMain = new JMenuBar();
        setJMenuBar(mbMain);

        addPatientOptionsMenu();
        addPrintFormsMenu();
    }

    private void addPatientOptionsMenu() {
        mnPatientOperation = new JMenu("Управление пациентами");
        mbMain.add(mnPatientOperation);

        mntmSelectPatient = new JMenuItem("Выбор пациента");
        mntmSelectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.showPatientSelectFrame();
            }
        });
        mnPatientOperation.add(mntmSelectPatient);

        mntmReception = new JMenuItem("Приём в курацию");
        mntmReception.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.showCurationFrame();
            }
        });
        mnPatientOperation.add(mntmReception);
    }

    private void addPrintFormsMenu() {
        mnPrintForms = new JMenu("Печатные формы");
        mbMain.add(mnPrintForms);

        mntmPrintStationDiary = new JMenuItem("Дневник стационарного больного");
        mntmPrintStationDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
//                if (patient != null) {
//                    frmPrint.setPatient(patient);
//                    frmPrint.setVisible(true);
//                }
            }
        });
        mnPrintForms.add(mntmPrintStationDiary);

        mntmPrintHospitalSummary = new JMenuItem("Выписной эпикриз");
        mntmPrintHospitalSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
//                if (patient != null) {
//                    try {
//                        String servPath =
//                            ClientHospital.tcl.printHospitalSummary(patient.getGospitalCod(),
//                                doctorAuth.getClpu_name() + " "
//                                + doctorAuth.getCpodr_name(), patient);
//                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
//                        ClientHospital.conMan.transferFileFromServer(servPath, cliPath);
//                        ClientHospital.conMan.openFileInEditor(cliPath, false);
//                    } catch (KmiacServerException e1) {
//                        e1.printStackTrace();
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    } catch (TException e1) {
//                        e1.printStackTrace();
//                        ClientHospital.conMan.reconnect(e1);
//                    }
//                }
            }
        });
        mnPrintForms.add(mntmPrintHospitalSummary);

        mntmPrintHospitalDeathSummary = new JMenuItem("Посмертный эпикриз");
        mntmPrintHospitalDeathSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
//                if (patient != null) {
//                    try {
//                        String servPath =
//                            ClientHospital.tcl.printHospitalDeathSummary(patient.getGospitalCod(),
//                                doctorAuth.getClpu_name() + " "
//                                + doctorAuth.getCpodr_name(), patient);
//                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
//                        ClientHospital.conMan.transferFileFromServer(servPath, cliPath);
//                        ClientHospital.conMan.openFileInEditor(cliPath, false);
//                    } catch (KmiacServerException e1) {
//                        e1.printStackTrace();
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    } catch (TException e1) {
//                        e1.printStackTrace();
//                        ClientHospital.conMan.reconnect(e1);
//                    }
//                }
            }
        });
        mnPrintForms.add(mntmPrintHospitalDeathSummary);
    }

    private void setToolBar() {
        getContentPane().setLayout(new BorderLayout(0, 0));
        toolBar = new JToolBar("Панель инструментов");
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);

        addPatientInfoToolButton();
        addAnamnezToolButon();
        addBolListToolButton();
        toolBar.add(new JToolBar.Separator());
        addIssledToolButton();
        addOperationToolButton();
        toolBar.add(new JToolBar.Separator());
        addReestrToolButton();
    }

    private void addPatientInfoToolButton() {
        btnShowPatientInfo = new JButton();
        setTooltipButtonDefaults(btnShowPatientInfo, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_PATIENT_INFO_ICON, "Информация о пациенте");
        btnShowPatientInfo.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalPatientInfoFrame();
            }
        });
        toolBar.add(btnShowPatientInfo);
    }

    private void addAnamnezToolButon() {
        btnShowPatientAnamnez = new JButton();
        setTooltipButtonDefaults(btnShowPatientAnamnez, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_ANAMNEZ_ICON, "Анамнез жизни");
        btnShowPatientAnamnez.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalAnamnezFrame();
            }
        });
        toolBar.add(btnShowPatientAnamnez);
    }

    private void addBolListToolButton() {
        btnShowPatientBolList = new JButton();
        setTooltipButtonDefaults(btnShowPatientBolList, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_BOL_LIST_ICON, "Больничный лист");
        btnShowPatientBolList.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalBolListFrame();
            }
        });
        toolBar.add(btnShowPatientBolList);
    }

    private void addIssledToolButton() {
        btnIssled = new JButton();
        setTooltipButtonDefaults(btnIssled, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_ISSLED_ICON, "Лабораторные исследования");
        btnIssled.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalIssledFrame();
            }
        });
        toolBar.add(btnIssled);
    }

    private void addOperationToolButton() {
        btnOperation = new JButton();
        setTooltipButtonDefaults(btnOperation, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_OPERATION_ICON, "Операции");
        btnOperation.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalOperationFrame();
            }
        });
        toolBar.add(btnOperation);
    }

    private void addReestrToolButton() {
        btnReestr = new JButton();
        setTooltipButtonDefaults(btnReestr, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                TOOLBAR_REESTR_ICON, "Исправление ошибок реестра");
        btnReestr.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.showExternalReestrFrame();
            }
        });
        toolBar.add(btnReestr);

    }

    private void setTooltipButtonDefaults(
            final JButton button, final int height, final int width,
            final URL iconUrl, final String tooltipText) {
        Dimension dimension = new Dimension(width, height);
        button.setMaximumSize(dimension);
        button.setMinimumSize(dimension);
        button.setPreferredSize(dimension);
        button.setIcon(new ImageIcon(iconUrl));
        button.setToolTipText(tooltipText);
        button.setBorder(null);
        button.setRequestFocusEnabled(false);
    }

    private void setTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public final void setFrameTitle(final String title) {
        setTitle(title);
    }

    public final void addComponentInTabbedPane(final Component component, final String title,
            final URL iconURL, final String tootlipText) {
        tabbedPane.addTab(title, new ImageIcon(iconURL), component, tootlipText);
    }

    public final JFrame getFrame() {
        return this;
    }
}

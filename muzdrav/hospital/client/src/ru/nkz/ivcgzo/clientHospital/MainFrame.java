package ru.nkz.ivcgzo.clientHospital;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TLifeHistory;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;

import org.apache.thrift.TException;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 3513837719265529744L;
    private static final String WINDOW_HEADER = "Врач стационара";
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
    private JMenuBar mbMain;
    private JMenu mnPatientOperation;
    private JMenuItem mntmSelectPatient;
    private JMenuItem mntmReception;
    private JTabbedPane tabbedPane;
    private JSplitPane spPatientInfo;
    private JPanel pPersonalInfo;
    private JPanel pReceptionInfo;
    private JLabel lblNumberDesiaseHistory;
    private JLabel lblGender;
    private JLabel lblChamber;
    private JLabel lblRegistrationAddress;
    private JTextField tfNumberOfDesiaseHistory;
    private JTextField tfGender;
    private JTextField tfChamber;
    private JLabel lblSurname;
    private JLabel lblBirthdate;
    private JLabel lblStatus;
    private JTextField tfSurname;
    private JTextField tfBirthdate;
    private JTextField tfStatus;
    private JLabel lblName;
    private JLabel lblOms;
    private JLabel lblWork;
    private JTextField tfName;
    private JTextField tfOms;
    private JTextField tfWork;
    private JLabel lblMiddlename;
    private JLabel lblDms;
    private JTextField tfMiddlename;
    private JTextField tfDms;
    private JTextField tfRegistrationAddress;
    private JLabel lblRealAddress;
    private JTextField tfRealAddress;
    private JTextPane textPane;
    private JButton btnShowPatientInfo;
    private UserAuthInfo doctorAuth;
    private TPatient patient;
    private TPriemInfo priemInfo;
    private JPanel pMedicalHistory;
    private JTabbedPane tbpMedicalHistory;
    private JTextField tfShablonFilter;
    private JButton btnFilterShablon;
    private JPanel pnStatusLocalis;
    private JPanel pnRecomendation;
    private JPanel pnZakl;
    private JPanel pnStatusPraence;
    private JPanel pnJalob;
    private JPanel pnDesiaseHistory;
    private JPanel pnFisicalObs;
    private JTextArea taJalob;
    private JTextArea taDesiaseHistory;
    private JTextArea taStatusPraence;
    private JTextArea taFisicalObs;
    private JTextArea taStatusLocalis;
    private JTextArea taRecomdation;
    private JTextArea taZakl;
    private JScrollPane spShablonNames;
    private ThriftIntegerClassifierList lShablonNames;
    private JButton btnSaveMedicalHistory;
    private JPanel pLifeHistory;
    private JLabel lblLifeHistory;
    private JScrollPane spLifeHistory;
    private JLabel lblAllergo;
    private JScrollPane spAllergo;
    private JLabel lblFarmo;
    private JScrollPane spFarmo;
    private JTextField tfLifeHShablonFilter;
    private JButton btnLifeHShablonFilter;
    private JScrollPane spLifeHShablonNames;
    private JButton btnSaveLifeHistory;
    private JTextArea taLifeHistory;
    private JTextArea taAllergo;
    private JTextArea taFarmo;
    private ThriftIntegerClassifierList lLifeHistoryShabloNames;
    private JButton btnUpdateChamber;
    private TLifeHistory lifeHistory;
    private PatientSelectFrame frmPatientSelect;
    private JPanel pDiagnosis;
    private JButton btnSaveDiag;
    private JRadioButton rdbtnMain;
    private JRadioButton rdbtnSoput;
    private JRadioButton rdbtnOsl;
    private JTextField tfDiagShablonFilter;
    private ThriftIntegerClassifierList lDiagShablonNames;
    private CustomTable<TDiagnosis, TDiagnosis._Fields> tbDiag;
    private JScrollPane spDiag;
    private JButton btnAddDiag;
    private JButton btnDelDiag;
    private JLabel lblDiagMedOp;
    private JTextArea taDiagMedOp;
    private JPanel pDiagTypes;
    private JButton btnDiagShablonFilter;
    private JScrollPane spDiagShablonNames;
    private Component hzstMainDiagSopDiag;
    private Component hzstSopDiagOslDiag;
    private JScrollPane scrollPane;

    public MainFrame(final UserAuthInfo authInfo) {
        doctorAuth = authInfo;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(WINDOW_HEADER);
        setMainMenu();
        setTabbedPane();
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// Общие методы //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setTabbedPane() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);
        setPatientInfoPanel();
        setLifeHistoryPanel();
        setMedicalHistoryPanel();
        setDiagnosisPanel();
    }

    public final void onConnect() {
        createModalFrames();
        try {
            lShablonNames.setData(ClientHospital.tcl.getShablonNames(
                    doctorAuth.getCspec(), doctorAuth.getCslu(),  null));
            lLifeHistoryShabloNames.setData(ClientHospital.tcl.getShablonNames(
                    doctorAuth.getCspec(), doctorAuth.getCslu(),  null));
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// Модульные фреймы ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void createModalFrames() {
        frmPatientSelect = new PatientSelectFrame(doctorAuth);
        frmPatientSelect.pack();
        frmPatientSelect.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent arg0) {
                if (frmPatientSelect.getCurrentPatient() != null) {
                    try {
                        patient = ClientHospital.tcl.getPatientPersonalInfo(
                            frmPatientSelect.getCurrentPatient().getPatientId());
                        fillPersonalInfoTextFields();
                        priemInfo = ClientHospital.tcl.getPriemInfo(
                                patient.getGospitalCod());
                        fillReceptionPanel();
                        lifeHistory =
                                ClientHospital.tcl.getLifeHistory(patient.getPatientId());
                        fillLifeHistoryPanel();
                        setTitle(String.format("%s %s %s",
                                patient.getSurname(), patient.getName(),
                                patient.getMiddlename()));
                    } catch (PatientNotFoundException e) {
                        JOptionPane.showMessageDialog(frmPatientSelect,
                            "Персональная инфомация о данном пациенте не найдена.",
                            "Внимание!", JOptionPane.WARNING_MESSAGE);
                    } catch (PriemInfoNotFoundException e) {
                        JOptionPane.showMessageDialog(frmPatientSelect,
                            "Информация из приёмного отделения"
                            + "о данном пациенте не найдена.",
                            "Внимание!", JOptionPane.WARNING_MESSAGE);
                    } catch (LifeHistoryNotFoundException e) {
                        JOptionPane.showMessageDialog(frmPatientSelect,
                            "История жизни данного пациента не найдена.",
                            "Внимание!", JOptionPane.WARNING_MESSAGE);
                    } catch (KmiacServerException | TException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////   Главное меню   ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setMainMenu() {
        mbMain = new JMenuBar();
        setJMenuBar(mbMain);

        mnPatientOperation = new JMenu("Управление пациентами");
        mbMain.add(mnPatientOperation);

        mntmSelectPatient = new JMenuItem("Выбор пациента");
        mntmSelectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frmPatientSelect.setVisible(true);
            }
        });
        mnPatientOperation.add(mntmSelectPatient);

        mntmReception = new JMenuItem("Приём в курацию");
        mnPatientOperation.add(mntmReception);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   Информация о пациенте   //////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setPatientInfoPanel() {
        spPatientInfo = new JSplitPane();
        spPatientInfo.setOrientation(JSplitPane.VERTICAL_SPLIT);
        tabbedPane.addTab("Информация о пациенте", null, spPatientInfo, null);
        setPersonalInfoPanel();
        setReceptionPanel();
    }

    private void setPersonalInfoPanel() {
        pPersonalInfo = new JPanel();
        spPatientInfo.setLeftComponent(pPersonalInfo);

        lblNumberDesiaseHistory = new JLabel("Номер истории болезни");
        tfNumberOfDesiaseHistory = new JTextField();
        tfNumberOfDesiaseHistory.setEditable(false);
        tfNumberOfDesiaseHistory.setColumns(15);

        lblSurname = new JLabel("Фамилия");
        tfSurname = new JTextField();
        tfSurname.setEditable(false);
        tfSurname.setColumns(15);

        lblName = new JLabel("Имя");
        tfName = new JTextField();
        tfName.setEditable(false);
        tfName.setColumns(15);

        lblMiddlename = new JLabel("Отчество");
        tfMiddlename = new JTextField();
        tfMiddlename.setEditable(false);
        tfMiddlename.setColumns(15);

        lblGender = new JLabel("Пол");
        tfGender = new JTextField();
        tfGender.setEditable(false);
        tfGender.setColumns(15);

        lblBirthdate = new JLabel("Дата рождения");
        tfBirthdate = new JTextField();
        tfBirthdate.setEditable(false);
        tfBirthdate.setColumns(15);

        lblOms = new JLabel("Полис ОМС");
        tfOms = new JTextField();
        tfOms.setEditable(false);
        tfOms.setColumns(20);

        lblDms = new JLabel("Полис ДМС");
        tfDms = new JTextField();
        tfDms.setEditable(false);
        tfDms.setColumns(20);

        lblChamber = new JLabel("Номер палаты");
        tfChamber = new JTextField();
        tfChamber.setColumns(15);

        lblStatus = new JLabel("Статус");
        tfStatus = new JTextField();
        tfStatus.setEditable(false);
        tfStatus.setColumns(15);

        lblWork = new JLabel("Место работы");
        tfWork = new JTextField();
        tfWork.setEditable(false);
        tfWork.setColumns(20);

        lblRegistrationAddress = new JLabel("Адрес прописки");
        tfRegistrationAddress = new JTextField();
        tfRegistrationAddress.setEditable(false);
        tfRegistrationAddress.setColumns(85);

        lblRealAddress = new JLabel("Адрес проживания");
        tfRealAddress = new JTextField();
        tfRealAddress.setEditable(false);
        tfRealAddress.setColumns(85);

        btnUpdateChamber = new JButton("Сохранить");
        btnShowPatientInfo = new JButton("Информация о пациенте");
        btnShowPatientInfo.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    ClientHospital.conMan.showPatientInfoForm("Информация о пациенте",
                            patient.getPatientId());
                }
            }
        });

        setPatientInfoPanelGroupLayout();
    }

    private void fillPersonalInfoTextFields() {
        if (patient != null) {
            tfNumberOfDesiaseHistory.setText(String.valueOf(patient.getNist()));
            tfSurname.setText(patient.getSurname());
            tfName.setText(patient.getName());
            tfMiddlename.setText(patient.getMiddlename());
            tfGender.setText(patient.getGender());
            tfBirthdate.setText(DEFAULT_DATE_FORMAT.format(patient.getBirthDate()));
            tfOms.setText(patient.getOms());
            tfDms.setText(patient.getDms());
            tfChamber.setText(String.valueOf(patient.getChamber()));
            tfStatus.setText(String.valueOf(patient.getStatus()));
            tfWork.setText(patient.getJob());
            tfRegistrationAddress.setText(patient.getRegistrationAddress());
            tfRealAddress.setText(patient.getRealAddress());
        }
    }

    private void setReceptionPanel() {
        pReceptionInfo = new JPanel();
        spPatientInfo.setRightComponent(pReceptionInfo);
        pReceptionInfo.setLayout(new BoxLayout(pReceptionInfo, BoxLayout.X_AXIS));

        textPane = new JTextPane();
        textPane.setEditable(false);
        pReceptionInfo.add(textPane);
    }

    private void fillReceptionPanel() {
        String priemInfoText =
            "ДОСТУПНАЯ ИНФОРМАЦИЯ О ПАЦИЕНТЕ ИЗ ПРИЁМНОГО ОТДЕЛЕНИЯ:" + "\n\n";
        if (priemInfo != null) {
            if (priemInfo.getPl_extr() != null) {
                priemInfoText += "   " + "Форма обращения в приёмное отделение: "
                    + priemInfo.getPl_extr() + "\n";
            }
            if (priemInfo.getDatap() != 0) {
                priemInfoText += "   " + "Дата поступления: " + DEFAULT_DATE_FORMAT.format(
                    priemInfo.getDatap()) + "\n";
            }
            if (priemInfo.getDataosm() != 0) {
                priemInfoText += "   " + "Дата осмотра: " + DEFAULT_DATE_FORMAT.format(
                    priemInfo.getDataosm()) + "\n";
            }
            if ((priemInfo.getNaprav() != null) && (priemInfo.getNOrg() != null)) {
                priemInfoText += "   " + "Кем направлен: " + priemInfo.getNaprav() + "  "
                    + priemInfo.getNOrg() + "\n";
            }
            if ((priemInfo.getDiagN() != null) && (priemInfo.getDiagNtext() != null)) {
                priemInfoText += "   " + "Диагноз напр. учреждения: " + priemInfo.getDiagN() + " "
                    + priemInfo.getDiagNtext() + "\n";
            }
            if ((priemInfo.getDiagP() != null) && (priemInfo.getDiagPtext() != null)) {
                priemInfoText += "   " + "Диагноз приёмного отделения: "
                    + priemInfo.getDiagP() + " "
                    + priemInfo.getDiagPtext() + "\n";
            }
            if (priemInfo.getT0c() != null) {
                priemInfoText += "   " + "Температура: " + priemInfo.getT0c() + "\n";
            }
            if (priemInfo.getAd() != null) {
                priemInfoText += "   " + "Давление: " + priemInfo.getAd() + "\n";
            }
            if (priemInfo.getJalob() != null) {
                priemInfoText += "   " + "Жалобы при поступлении: :" + priemInfo.getJalob();
            }
        }
        textPane.setText(priemInfoText);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////   История жизни  ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setLifeHistoryPanel() {
        pLifeHistory = new JPanel();
        tabbedPane.addTab("История жизни", null, pLifeHistory, null);
        setLifeHistoryTextAreas();
        setLifeHistoryShablonComponents();
        setLifeHistoryButtons();
        setLifeHistoryPanelGroupLayout();
    }

    private void setLifeHistoryTextAreas() {
        lblLifeHistory = new JLabel("История жизни");
        spLifeHistory = new JScrollPane();
        taLifeHistory = new JTextArea();
        taLifeHistory.setLineWrap(true);
        taLifeHistory.setWrapStyleWord(true);
        taLifeHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spLifeHistory.setViewportView(taLifeHistory);

        lblAllergo = new JLabel("Аллергоанамнез");
        spAllergo = new JScrollPane();
        taAllergo = new JTextArea();
        taAllergo.setWrapStyleWord(true);
        taAllergo.setLineWrap(true);
        taAllergo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spAllergo.setViewportView(taAllergo);

        lblFarmo = new JLabel("Фармоанамнез");
        spFarmo = new JScrollPane();
        taFarmo = new JTextArea();
        taFarmo.setLineWrap(true);
        taFarmo.setWrapStyleWord(true);
        taFarmo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spFarmo.setViewportView(taFarmo);
    }

    private void setLifeHistoryShablonComponents() {
        tfLifeHShablonFilter = new JTextField();
        tfLifeHShablonFilter.setColumns(10);

        btnLifeHShablonFilter = new JButton("Выбрать");

        spLifeHShablonNames = new JScrollPane();
        lLifeHistoryShabloNames = new ThriftIntegerClassifierList();
        lLifeHistoryShabloNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lLifeHistoryShabloNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lShablonNames.getSelectedValue() != null) {
                        try {
                            pasteSelectedLifeHShablon(ClientHospital.tcl.getShablon(
                                lShablonNames.getSelectedValue().pcod));
                        } catch (KmiacServerException e1) {
                            JOptionPane.showMessageDialog(MainFrame.this,
                                "Ошибка загрузки шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } catch (TException e1) {
                            ClientHospital.conMan.reconnect(e1);
                        }
                    }
                }
            }
        });
        spLifeHShablonNames.setViewportView(lLifeHistoryShabloNames);
    }

    private void setLifeHistoryButtons() {
        btnSaveLifeHistory = new JButton("Сохранить");
        btnSaveLifeHistory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if ((lifeHistory != null) && (patient != null)) {
                    try {
                        updateLifeHistoryFromTextAreas();
                        ClientHospital.tcl.updateLifeHistory(lifeHistory);
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "История жизни сохранена", "Операция успешно завершена",
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (KmiacServerException | TException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Ошибка при "
                            + "изменении истории жизни. Информация не будет сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка при "
                            + "изменении истории жизни. Информация не будет сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void fillLifeHistoryPanel() {
        if (lifeHistory != null) {
            taAllergo.setText(lifeHistory.getAllerg());
            taFarmo.setText(lifeHistory.getFarmkol());
            taLifeHistory.setText(lifeHistory.getVitae());
        }
    }

    private void pasteSelectedLifeHShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        for (ShablonText shText : shablon.textList) {
            switch (shText.grupId) {
                case 4:
                    taLifeHistory.setText(shText.getText());
                    break;
                case 3:
                    taAllergo.setText(shText.getText());
                    break;
                case 5:
                    taFarmo.setText(shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private void updateLifeHistoryFromTextAreas() {
        lifeHistory.setAllerg(taAllergo.getText());
        lifeHistory.setFarmkol(taFarmo.getText());
        lifeHistory.setVitae(taLifeHistory.getText());
    };

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////   Медицинская история   ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setMedicalHistoryPanel() {
        pMedicalHistory = new JPanel();
        tabbedPane.addTab("Медицинская история", null, pMedicalHistory, null);
        setMedicalHistoryTabs();
        setMedicalHistoryShablonComponents();
        setMedicalHistoryButtons();
        setMedicalHistoryPanelGroupLayout();
    }

    private void setMedicalHistoryTabs() {
        tbpMedicalHistory = new JTabbedPane(JTabbedPane.LEFT);
        tbpMedicalHistory.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        addJalonPanel();
        addDesiaseHistoryPanel();
        addStatusPraencePanel();
        addFisicalObsPanel();
        addStausLocalisPanel();
        addRecomendationPanel();
        addZaklPanel();
    }

    private void setMedicalHistoryShablonComponents() {
        tfShablonFilter = new JTextField();
        tfShablonFilter.setColumns(10);

        spShablonNames = new JScrollPane();
        lShablonNames = new ThriftIntegerClassifierList();
        lShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lShablonNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lShablonNames.getSelectedValue() != null) {
                        try {
                            pasteSelectedShablon(ClientHospital.tcl.getShablon(
                                lShablonNames.getSelectedValue().pcod));
                        } catch (KmiacServerException e1) {
                            JOptionPane.showMessageDialog(MainFrame.this,
                                "Ошибка загрузки шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } catch (TException e1) {
                            ClientHospital.conMan.reconnect(e1);
                        }
                    }
                }
            }
        });
        spShablonNames.setViewportView(lShablonNames);

        btnFilterShablon = new JButton("Выбрать");
    }

    private void setMedicalHistoryButtons() {
        btnSaveMedicalHistory = new JButton("Сохранить");
        btnSaveMedicalHistory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    TMedicalHistory medicalHist = createMedicalHistory();
                    try {
                        medicalHist.setId(ClientHospital.tcl.addMedicalHistory(medicalHist));
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Медицинская история сохранена", "Операция успешно завершена",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (KmiacServerException | TException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Ошибка при записи "
                            + "медицинской истории. Информация не будет сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пациент не выбран!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addJalonPanel() {
        pnJalob = new JPanel();
        pnJalob.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Жалобы", null, pnJalob, null);
        pnJalob.setLayout(new BoxLayout(pnJalob, BoxLayout.Y_AXIS));

        taJalob = new JTextArea();
        taJalob.setFont(new Font("Tahoma", Font.PLAIN, 11));
        taJalob.setLineWrap(true);
        taJalob.setWrapStyleWord(true);
        pnJalob.add(taJalob);
    }

    private void addDesiaseHistoryPanel() {
        pnDesiaseHistory = new JPanel();
        pnDesiaseHistory.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("История болезни", null, pnDesiaseHistory, null);
        pnDesiaseHistory.setLayout(new BoxLayout(pnDesiaseHistory, BoxLayout.X_AXIS));

        taDesiaseHistory = new JTextArea();
        taDesiaseHistory.setWrapStyleWord(true);
        taDesiaseHistory.setLineWrap(true);
        taDesiaseHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnDesiaseHistory.add(taDesiaseHistory);
    }

    private void addStatusPraencePanel() {
        pnStatusPraence = new JPanel();
        pnStatusPraence.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Status praense", null, pnStatusPraence, null);
        pnStatusPraence.setLayout(new BoxLayout(pnStatusPraence, BoxLayout.X_AXIS));

        taStatusPraence = new JTextArea();
        taStatusPraence.setLineWrap(true);
        taStatusPraence.setWrapStyleWord(true);
        taStatusPraence.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnStatusPraence.add(taStatusPraence);
    }

    private void addFisicalObsPanel() {
        pnFisicalObs = new JPanel();
        pnFisicalObs.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Физикальное обследование", null, pnFisicalObs, null);
        pnFisicalObs.setLayout(new BoxLayout(pnFisicalObs, BoxLayout.X_AXIS));

        taFisicalObs = new JTextArea();
        taFisicalObs.setLineWrap(true);
        taFisicalObs.setWrapStyleWord(true);
        taFisicalObs.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnFisicalObs.add(taFisicalObs);
    }

    private void addStausLocalisPanel() {
        pnStatusLocalis = new JPanel();
        pnStatusLocalis.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Status localis", null, pnStatusLocalis, null);
        pnStatusLocalis.setLayout(new BoxLayout(pnStatusLocalis, BoxLayout.X_AXIS));

        taStatusLocalis = new JTextArea();
        taStatusLocalis.setLineWrap(true);
        taStatusLocalis.setWrapStyleWord(true);
        taStatusLocalis.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnStatusLocalis.add(taStatusLocalis);
    }

    private void addRecomendationPanel() {
        pnRecomendation = new JPanel();
        pnRecomendation.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Рекомендации", null, pnRecomendation, null);
        pnRecomendation.setLayout(new BoxLayout(pnRecomendation, BoxLayout.X_AXIS));

        taRecomdation = new JTextArea();
        taRecomdation.setLineWrap(true);
        taRecomdation.setWrapStyleWord(true);
        taRecomdation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnRecomendation.add(taRecomdation);
    }

    private void addZaklPanel() {
        pnZakl = new JPanel();
        pnZakl.setBorder(new LineBorder(new Color(0, 0, 0)));
        tbpMedicalHistory.addTab("Заключение", null, pnZakl, null);
        pnZakl.setLayout(new BoxLayout(pnZakl, BoxLayout.X_AXIS));

        taZakl = new JTextArea();
        taZakl.setWrapStyleWord(true);
        taZakl.setLineWrap(true);
        taZakl.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnZakl.add(taZakl);
    }

    private void clearMedicalHistoryText() {
        taJalob.setText("");
        taDesiaseHistory.setText("");
        taFisicalObs.setText("");
        taStatusLocalis.setText("");
        taStatusPraence.setText("");
        taRecomdation.setText("");
        taZakl.setText("");
    }

    private void pasteSelectedShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        clearMedicalHistoryText();

        for (ShablonText shText : shablon.textList) {
            switch (shText.grupId) {
                case 1:
                    taJalob.setText(shText.getText());
                    break;
                case 2:
                    taDesiaseHistory.setText(shText.getText());
                    break;
                case 6:
                    taStatusPraence.setText(shText.getText());
                    break;
                case 7:
                    taFisicalObs.setText(shText.getText());
                    break;
                case 8:
                    taStatusLocalis.setText(shText.getText());
                    break;
                case 12:
                    taRecomdation.setText(shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private TMedicalHistory createMedicalHistory() {
        TMedicalHistory tmpHist = new TMedicalHistory();
        tmpHist.setIdGosp(patient.getGospitalCod());
        tmpHist.setJalob(taJalob.getText());
        tmpHist.setMorbi(taDesiaseHistory.getText());
        tmpHist.setStatusPraesense(taStatusPraence.getText());
        tmpHist.setStatusLocalis(taStatusLocalis.getText());
        tmpHist.setFisicalObs(taFisicalObs.getText());
        tmpHist.setPcodVrach(doctorAuth.getPcod());
        tmpHist.setDataz(new Date().getTime());
        tmpHist.setTimez(new Date().getTime());
        return tmpHist;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// Диагнозы //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setDiagnosisPanel() {
        pDiagnosis = new JPanel();
        tabbedPane.addTab("Диагнозы", null, pDiagnosis, null);
        setDiagnosisShablonComponents();
        setDiagnosisTable();
        setDiagnosisTextArea();
        setDiagnosisRadioButtons();
        setDiagnosisPanelGroupLayout();
    }

    private void setDiagnosisShablonComponents() {
        tfDiagShablonFilter = new JTextField();
        tfDiagShablonFilter.setColumns(10);

        spDiagShablonNames = new JScrollPane();
        lDiagShablonNames = new ThriftIntegerClassifierList();
        lDiagShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        spDiagShablonNames.setViewportView(lDiagShablonNames);

        btnDiagShablonFilter = new JButton("Выбрать");
    }

    private void setDiagnosisTable() {
        setDiagTable();
        setDiagTableControlButtons();
    }

    private void setDiagTableControlButtons() {
        btnAddDiag = new JButton();
        btnAddDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                addDiagnosisToTable(ClientHospital.conMan.showMkbTreeForm("Диагноз", ""));
            }
        });
        btnAddDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));

        btnDelDiag = new JButton();
        btnDelDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (tbDiag.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            btnDelDiag, "Удалить диагноз?",
                            "Удаление диагноза", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientHospital.tcl.deleteDiagnosis(tbDiag.getSelectedItem().getId());
                            System.out.println(tbDiag.getSelectedItem().getId());
                            tbDiag.setData(
                                ClientHospital.tcl.getDiagnosis(patient.getGospitalCod()));
                        }
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientHospital.conMan.reconnect(e1);
                } catch (DiagnosisNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnDelDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));

        btnSaveDiag = new JButton();
        btnSaveDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
    }

    private void addDiagnosisToTable(final StringClassifier curDiagnosis) {
        try {
            if ((curDiagnosis != null) && (patient != null)) {
                TDiagnosis diag = new TDiagnosis();
                diag.setIdGosp(patient.getGospitalCod());
                diag.setCod(curDiagnosis.getPcod());
                diag.setDateUstan(System.currentTimeMillis());
                diag.setVrach(doctorAuth.getPcod());
                diag.setDiagName(curDiagnosis.getName());
                diag.setId(ClientHospital.tcl.addDiagnosis(diag));
                tbDiag.addItem(diag);
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void setDiagTable() {
        spDiag = new JScrollPane();
        tbDiag = new CustomTable<TDiagnosis, TDiagnosis._Fields>(
            false, true, TDiagnosis.class, 4, "Дата", 2, "Код МКБ", 7, "Наименование диагноза");
        tbDiag.setDateField(0);
        tbDiag.setBorder(new LineBorder(new Color(0, 0, 0)));
        spDiag.setViewportView(tbDiag);
    }

    private void setDiagnosisTextArea() {
        lblDiagMedOp = new JLabel("Медицинское описание диагноза");
    }

    private void setDiagnosisRadioButtons() {
        pDiagTypes = new JPanel();
        pDiagTypes.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDiagTypes.setLayout(new BoxLayout(pDiagTypes, BoxLayout.X_AXIS));

        rdbtnMain = new JRadioButton("Основной");
        pDiagTypes.add(rdbtnMain);

        hzstMainDiagSopDiag = Box.createHorizontalStrut(150);
        pDiagTypes.add(hzstMainDiagSopDiag);

        rdbtnSoput = new JRadioButton("Сопутствующий");
        pDiagTypes.add(rdbtnSoput);

        hzstSopDiagOslDiag = Box.createHorizontalStrut(150);
        pDiagTypes.add(hzstSopDiagOslDiag);

        rdbtnOsl = new JRadioButton("Осложнение основного");
        pDiagTypes.add(rdbtnOsl);
    }

////////////////////////////////////////// CAUTION! ///////////////////////////////////////////////
/////////////////// Автогенерируемое нечитаемое говно. Спасибо ВиндоуБилдеру за это. //////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setPatientInfoPanelGroupLayout() {
        GroupLayout glPersonalInfo = new GroupLayout(pPersonalInfo);
        glPersonalInfo.setHorizontalGroup(
            glPersonalInfo.createParallelGroup(Alignment.LEADING)
                .addGroup(glPersonalInfo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNumberDesiaseHistory)
                        .addComponent(lblGender)
                        .addComponent(lblChamber)
                        .addComponent(lblRegistrationAddress)
                        .addComponent(lblRealAddress))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPersonalInfo.createSequentialGroup()
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(tfChamber, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfGender, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfNumberOfDesiaseHistory,
                                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                            .addGap(31)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblSurname)
                                .addComponent(lblBirthdate)
                                .addComponent(lblStatus))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(tfBirthdate, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfSurname, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfStatus, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(34)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblName)
                                .addComponent(lblOms)
                                .addComponent(lblWork))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPersonalInfo.createSequentialGroup()
                                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                        .addComponent(tfName, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfOms, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                    .addGap(43)
                                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblMiddlename)
                                        .addComponent(lblDms)))
                                .addComponent(tfWork, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addComponent(tfRealAddress, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfRegistrationAddress, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(btnShowPatientInfo, GroupLayout.DEFAULT_SIZE,
                                166, Short.MAX_VALUE)
                        .addComponent(tfMiddlename)
                        .addComponent(tfDms)
                        .addComponent(btnUpdateChamber, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(5))
        );
        glPersonalInfo.setVerticalGroup(
            glPersonalInfo.createParallelGroup(Alignment.LEADING)
                .addGroup(glPersonalInfo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNumberDesiaseHistory)
                        .addComponent(tfNumberOfDesiaseHistory, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSurname)
                        .addComponent(tfSurname, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblName)
                        .addComponent(tfName, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMiddlename)
                        .addComponent(tfMiddlename, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblGender)
                        .addComponent(tfGender, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBirthdate)
                        .addComponent(tfBirthdate, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblOms)
                        .addComponent(tfOms, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDms)
                        .addComponent(tfDms, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblChamber)
                        .addComponent(tfChamber, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStatus)
                        .addComponent(tfStatus, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblWork)
                        .addComponent(tfWork, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnShowPatientInfo))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRegistrationAddress)
                        .addComponent(tfRegistrationAddress, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateChamber))
                    .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRealAddress)
                        .addComponent(tfRealAddress, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
        pPersonalInfo.setLayout(glPersonalInfo);
    }

    private void setMedicalHistoryPanelGroupLayout() {
        GroupLayout glPMedicalHistory = new GroupLayout(pMedicalHistory);
        glPMedicalHistory.setHorizontalGroup(
            glPMedicalHistory.createParallelGroup(Alignment.LEADING)
                .addGroup(glPMedicalHistory.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPMedicalHistory.createParallelGroup(Alignment.TRAILING)
                        .addComponent(tbpMedicalHistory, GroupLayout.PREFERRED_SIZE, 700,
                            GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveMedicalHistory, GroupLayout.PREFERRED_SIZE,
                            541, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPMedicalHistory.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPMedicalHistory.createSequentialGroup()
                            .addComponent(tfShablonFilter, GroupLayout.DEFAULT_SIZE,
                                249, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnFilterShablon, GroupLayout.PREFERRED_SIZE, 90,
                                GroupLayout.PREFERRED_SIZE)
                            .addGap(5))
                        .addGroup(glPMedicalHistory.createSequentialGroup()
                            .addComponent(spShablonNames, GroupLayout.DEFAULT_SIZE, 340,
                                Short.MAX_VALUE)
                            .addContainerGap())))
        );
        glPMedicalHistory.setVerticalGroup(
            glPMedicalHistory.createParallelGroup(Alignment.LEADING)
                .addGroup(glPMedicalHistory.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPMedicalHistory.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPMedicalHistory.createSequentialGroup()
                            .addComponent(tbpMedicalHistory, GroupLayout.DEFAULT_SIZE, 252,
                                Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnSaveMedicalHistory)
                            .addGap(5))
                        .addGroup(glPMedicalHistory.createSequentialGroup()
                            .addGroup(glPMedicalHistory.createParallelGroup(Alignment.BASELINE)
                                .addComponent(tfShablonFilter, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFilterShablon))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spShablonNames, GroupLayout.DEFAULT_SIZE, 246,
                                Short.MAX_VALUE)
                            .addContainerGap())))
        );
        pMedicalHistory.setLayout(glPMedicalHistory);
    }

    private void setLifeHistoryPanelGroupLayout() {
        GroupLayout glPLifeHistory = new GroupLayout(pLifeHistory);
        glPLifeHistory.setHorizontalGroup(
            glPLifeHistory.createParallelGroup(Alignment.TRAILING)
                .addGroup(glPLifeHistory.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPLifeHistory.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(btnSaveLifeHistory, GroupLayout.PREFERRED_SIZE, 722,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLifeHistory)
                        .addComponent(lblAllergo)
                        .addComponent(lblFarmo)
                        .addComponent(spLifeHistory, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                        .addComponent(spAllergo, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                        .addComponent(spFarmo, GroupLayout.PREFERRED_SIZE, 722,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPLifeHistory.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPLifeHistory.createSequentialGroup()
                            .addComponent(tfLifeHShablonFilter, GroupLayout.PREFERRED_SIZE, 249,
                                    GroupLayout.PREFERRED_SIZE)
                            .addGap(6)
                            .addComponent(btnLifeHShablonFilter, GroupLayout.PREFERRED_SIZE, 90,
                                    GroupLayout.PREFERRED_SIZE))
                        .addGroup(glPLifeHistory.createSequentialGroup()
                            .addComponent(spLifeHShablonNames, GroupLayout.DEFAULT_SIZE, 349,
                                    Short.MAX_VALUE)
                            .addGap(5)))
                    .addContainerGap())
        );
        glPLifeHistory.setVerticalGroup(
            glPLifeHistory.createParallelGroup(Alignment.LEADING)
                .addGroup(glPLifeHistory.createSequentialGroup()
                    .addGroup(glPLifeHistory.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPLifeHistory.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblLifeHistory)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spLifeHistory, GroupLayout.DEFAULT_SIZE,
                                    135, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblAllergo)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spAllergo, GroupLayout.PREFERRED_SIZE, 135,
                                    Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblFarmo)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spFarmo, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addGap(17)
                            .addComponent(btnSaveLifeHistory))
                        .addGroup(glPLifeHistory.createSequentialGroup()
                            .addGroup(glPLifeHistory.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPLifeHistory.createSequentialGroup()
                                    .addGap(1)
                                    .addComponent(tfLifeHShablonFilter,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnLifeHShablonFilter))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spLifeHShablonNames, GroupLayout.DEFAULT_SIZE, 499,
                                    Short.MAX_VALUE)))
                    .addContainerGap())
        );
        pLifeHistory.setLayout(glPLifeHistory);
    }

    private void setDiagnosisPanelGroupLayout() {

        scrollPane = new JScrollPane();
        GroupLayout glPDiagnosis = new GroupLayout(pDiagnosis);
        glPDiagnosis.setHorizontalGroup(
            glPDiagnosis.createParallelGroup(Alignment.LEADING)
                .addGroup(glPDiagnosis.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblDiagMedOp)
                        .addGroup(glPDiagnosis.createSequentialGroup()
                            .addGroup(glPDiagnosis.createParallelGroup(Alignment.TRAILING, false)
                                .addComponent(pDiagTypes, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 635, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollPane, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 635, GroupLayout.PREFERRED_SIZE)
                                .addComponent(spDiag, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
                                        635, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(btnAddDiag, GroupLayout.PREFERRED_SIZE,
                                        52, Short.MAX_VALUE)
                                .addComponent(btnSaveDiag, GroupLayout.PREFERRED_SIZE,
                                        52, Short.MAX_VALUE)
                                .addComponent(btnDelDiag, 0, 0, Short.MAX_VALUE))
                            .addGap(9)
                            .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPDiagnosis.createSequentialGroup()
                                    .addComponent(tfDiagShablonFilter, GroupLayout.DEFAULT_SIZE,
                                            276, Short.MAX_VALUE)
                                    .addGap(6)
                                    .addComponent(btnDiagShablonFilter, GroupLayout.PREFERRED_SIZE,
                                            90, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10))
                                .addGroup(glPDiagnosis.createSequentialGroup()
                                    .addComponent(spDiagShablonNames, GroupLayout.DEFAULT_SIZE, 377,
                                            Short.MAX_VALUE)
                                    .addGap(5)))))
                    .addGap(0))
        );
        glPDiagnosis.setVerticalGroup(
            glPDiagnosis.createParallelGroup(Alignment.LEADING)
                .addGroup(glPDiagnosis.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPDiagnosis.createSequentialGroup()
                            .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPDiagnosis.createSequentialGroup()
                                    .addGap(1)
                                    .addComponent(tfDiagShablonFilter, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnDiagShablonFilter))
                            .addGap(6)
                            .addComponent(spDiagShablonNames, GroupLayout.DEFAULT_SIZE, 488,
                                    Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(glPDiagnosis.createSequentialGroup()
                            .addGroup(glPDiagnosis.createParallelGroup(Alignment.LEADING, false)
                                .addGroup(glPDiagnosis.createSequentialGroup()
                                    .addComponent(btnAddDiag, GroupLayout.PREFERRED_SIZE, 54,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(btnDelDiag, GroupLayout.PREFERRED_SIZE, 54,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(btnSaveDiag, GroupLayout.PREFERRED_SIZE, 54,
                                            GroupLayout.PREFERRED_SIZE))
                                .addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 299,
                                        GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblDiagMedOp)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(pDiagTypes, GroupLayout.PREFERRED_SIZE, 28,
                                    GroupLayout.PREFERRED_SIZE)
                            .addGap(75))))
        );
        taDiagMedOp = new JTextArea();
        scrollPane.setViewportView(taDiagMedOp);
        taDiagMedOp.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pDiagnosis.setLayout(glPDiagnosis);
    }
}

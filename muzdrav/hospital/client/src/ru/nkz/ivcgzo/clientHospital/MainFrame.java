//////////////////////////////   АДЪ И ИЗРАИЛЪ /////////////////////////////////
////////////TODO Рефакторить, рефакторить, рефакторить... /////////////////////
package ru.nkz.ivcgzo.clientHospital;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.DiagnosisNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.DopShablon;
import ru.nkz.ivcgzo.thriftHospital.LifeHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.MesNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PriemInfoNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TLifeHistory;
import ru.nkz.ivcgzo.thriftHospital.TMedicalHistory;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TPriemInfo;
import ru.nkz.ivcgzo.thriftHospital.TStage;
import ru.nkz.ivcgzo.thriftHospital.Zakl;
import ru.nkz.ivcgzo.thriftHospital.TRdIshod;
import ru.nkz.ivcgzo.clientHospital.Children;
import ru.nkz.ivcgzo.thriftHospital.RdDinStruct;
import ru.nkz.ivcgzo.thriftHospital.RdSlStruct;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.Timer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import org.apache.thrift.TException;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JTable;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 3513837719265529744L;
    private static final String WINDOW_HEADER = "Врач стационара";
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
    private JMenuBar mbMain;
    private JMenu mnPatientOperation;
    private JMenuItem mntmSelectPatient;
    private JMenuItem mntmReception;
    private JTabbedPane tabbedPane;
    private Children pChildren;
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
    private ThriftIntegerClassifierCombobox<IntegerClassifier> tfStatus;
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
    private UserAuthInfo doctorAuth;
    private TPatient patient;
    private TPriemInfo priemInfo;
    private JPanel pMedicalHistory;
    private JTabbedPane tbpMedicalHistory;
    private CustomTextField tfMedHShablonFilter;
    private JPanel pnStatusLocalis;
    private JPanel pnStatusPraence;
    private JPanel pnJalob;
    private JPanel pnDesiaseHistory;
    private JPanel pnFisicalObs;
    private JTextArea taJalob;
    private JTextArea taDesiaseHistory;
    private JTextArea taStatusPraence;
    private JTextArea taFisicalObs;
    private JTextArea taStatusLocalis;
    private JScrollPane spMedicalHistoryShablonNames;
    private ThriftIntegerClassifierList lMedicalHistoryShablonNames;
    private JPanel pLifeHistory;
    private JLabel lblLifeHistory;
    private JScrollPane spLifeHistory;
    private JLabel lblAllergo;
    private JScrollPane spAllergo;
    private JLabel lblFarmo;
    private JScrollPane spFarmo;
    private CustomTextField tfLifeHShablonFilter;
    private JScrollPane spLifeHShablonNames;
    private JButton btnSaveLifeHistory;
    private JTextArea taLifeHistory;
    private JTextArea taAllergo;
    private JTextArea taFarmo;
    private ThriftIntegerClassifierList lLifeHistoryShabloNames;
    private JButton btnUpdateChamber;
    private TLifeHistory lifeHistory;
    private PatientSelectFrame frmPatientSelect;
    private CurationFrame frmCuration;
    private JPanel pDiagnosis;
    private JButton btnSaveDiag;
    private JRadioButton rdbtnMain;
    private JRadioButton rdbtnSoput;
    private JRadioButton rdbtnOsl;
    private CustomTextField tfDiagShablonFilter;
    private ThriftIntegerClassifierList lDiagShablonNames;
    private CustomTable<TDiagnosis, TDiagnosis._Fields> tbDiag;
    private RdDinStruct rddin;
    private RdSlStruct rdsl;
    private JScrollPane spDiag;
    private JButton btnAddDiag;
    private JButton btnDelDiag;
    private JLabel lblDiagMedOp;
    private JTextArea taDiagMedOp;
    private JPanel pDiagTypes;
    private JScrollPane spDiagShablonNames;
    private Component hzstMainDiagSopDiag;
    private Component hzstSopDiagOslDiag;
    private JScrollPane spDiagnosisMedOp;
    private ButtonGroup btgDiag;
    private JPanel pZakl;
    private CustomTextField tfZaklShablonNames;
    private JButton btnSaveZakl;
    private JLabel lblRecomend;
    private JLabel lblZakluch;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxIshod;
    private JScrollPane spZaklShablonNames;
    private ThriftIntegerClassifierList lZaklShablonNames;
    private JScrollPane spRecomend;
    private JTextArea taRecomend;
    private JScrollPane spZakluch;
    private JTextArea taZakluch;
    private ShablonSearchListener lifeHiSearchListener;
    private ShablonSearchListener medHiSearchListener;
    private ShablonSearchListener diagSearchListener;
    private ShablonSearchListener zaklSearchListener;
    private CustomTable<TMedicalHistory, TMedicalHistory._Fields> tbMedHist;
    private JScrollPane spMedHist;
    private JButton btnMedHistUpd;
    private JButton btnMedHistDel;
    private JButton btnMedHistAdd;
    private JLabel lblResult;
    private JLabel lblIshod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxResult;
    private CustomDateEditor cdeZaklDate;
    private CustomTimeEditor cdeZaklTime;
    private JLabel lblZaklDate;
    private JLabel lblZaklTime;
    private Color defCol = UIManager.getColor("TabbedPane.foreground");
    private Color selCol = Color.red;
    private JPanel pStage;
    private JScrollPane spStageTable;
    private JPanel pStageButtons;
    private CustomTable<TStage, TStage._Fields> tbStages;
    private JButton btnAddStage;
    private JButton btnUpdateStage;
    private JButton btnDeleteStage;
    private JLabel lblVidPom;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidPom;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDefect;
    private JLabel lblDefect;
    private JLabel lblUkl;
    private JTextField tfUkl;
    private JLabel lblLifeHistioryShablonHeader;
    private Box vbLifeHistoryShablonComponents;
    private Box vbLifeHistoryTextFields;
    private Component hsLifeHistorySecond;
    private Box hbLifeHistoryShablonFind;
    private Component hsLifeHistoryThird;
    private Component hsLifeHistoryFirst;
    private Box vbMedicalHistoryTextFields;
    private Box vbMedicalHistoryShablonComponents;
    private JLabel lblMedicalHistioryShablonHeader;
    private Box hbMedicalHistoryShablonFind;
    private JButton btnMedicalHistoryShablonFind;
    private Box hbMedicalHistoryTableControls;
    private Box vbMedicalHistoryTableButtons;
    private Component hsMedicalHistorySecond;
    private Component hsMedicalHistoryFirst;
    private Component hsMedicalHistoryThird;
    private Component vsMedicalHistoryControlsDelim;
    private Box vbDiagnosisTextFields;
    private Box vbDiagnosisShablonComponents;
    private Box hbDiagnosisTableControls;
    private Box vbDiagnosisTableButtons;
    private JLabel lblDiagnosisShablonHeader;
    private Box hbDiagnosisShablonFind;
    private JButton btnDiagnosisShablonFind;
    private Component hsDiagnosisSecond;
    private Component hsDiagnosisThird;
    private Component hsDiagnosisFirst;
    private Component vsDiagnosisControlsDelimFirst;
    private Component verticalStrut;
    private JMenu mnPrintForms;
    private JMenuItem mntmPrintStationDiary;
    private PrintFrame frmPrint;
    private JLabel lblVidOpl;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidOpl;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxAnotherOtd;
    private ShablonForm frmShablon;
    private JButton btnZaklShablonFind;
    private JLabel lblPatalogoAnDiagHeader;
    private JTextField tfPatalogoAnDiagName;
    private JButton btnPatalogoAnDiag;
    private JTextField tfPatalogoAnDiagPcod;
    private JPanel panel;
    private JButton btnShowPatientInfo;
    private JButton btnMedication;
    private JButton btnIssled;
    private JToolBar toolBar;
    private JTextField tfZaklDiagPcod;
    private JTextField tfZaklDiagName;
    private JLabel lblZaklDiag;
    private JButton btnZaklDiag;
    private JMenuItem mntmPrintHospitalSummary;
    private JPanel pChildbirth;
    //public static TRdIshod trdIshod;
    private TRdIshod trdIshod;
    private JPanel panel_2;
    private JTextField TVes;
    private JTextField TShvat;
    private JTextField TVod;
    private JTextField TKash;
    private JTextField TPoln;
    private JTextField TNash;
    private JTextField TVremp;
    private JTextField TObol;
    private JTextField TOsob;
    private JTextField TPer1;
    private JTextField TPer2;
    private JTextField TPer3;
    private CustomDateEditor TDatarod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPolpl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPoz;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBVid;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd1;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPred;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPrinial;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBOsmotr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBAkush;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPosled;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBVrash;
	private JSpinner Soj;
	private JSpinner Shdm;
	private JSpinner Schcc;
	private JSpinner SKrov;
	private JSpinner SVes;
	private JSpinner SDlina;
	private JTextField TGde;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBEff;
	private JTextField TDet;
	private JTextField TObvit;
	private JTextField TRod;
	private JTextField TMed;
	private JTextField textField;
    private JMenuItem mntmPrintHospitalDeathSummary;
    private JLabel lblZaklDiagStep;
    private JRadioButton rdbtnZaklDiagSrT;
    private JRadioButton rdbtnZaklDiagTT;
    private JPanel pRecButtons;
    private CustomDateEditor Tdatam;
    private CustomDateEditor Tdataosl;
	private JSpinner Sber;
	private JSpinner Srod;
	private JSpinner Sdtr;
	private JSpinner Stvera;
	private JSpinner Sdcr;
	private JSpinner Scdiag;
	private JSpinner Sdsp;
	private JSpinner Scext;
	private JCheckBox ChBpsi;
	private JScrollPane spJalob;
	private JScrollPane spDesiaseHistory;
	private JScrollPane spStatusLocalis;
	private JScrollPane spFisicalObs;
	private JScrollPane spStatusPraence;
//    private JLabel lblNewLabel_33;
//    private JTextField textField_1;
    private JButton btnOperation;

    public MainFrame(final UserAuthInfo authInfo) {
        setMinimumSize(new Dimension(950, 700));
//        setPreferredSize(new Dimension(1000, 800));
//        setSize(new Dimension(1000, 800));
        doctorAuth = authInfo;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(WINDOW_HEADER);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/hospital.png")));
        setMainMenu();
        setToolBar();
        setTabbedPane();
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(final WindowEvent e) {
                tabbedPane.requestFocusInWindow();
            }
        });
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// Общие методы //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        setPatientInfoPanel();
        setLifeHistoryPanel();
        setMedicalHistoryPanel();
        setStagePanel();
        setChildrenPanel();
        setDiagnosisPanel();
//        setChildbirthPanel();
        setZaklPanel();

        if ((doctorAuth.getClpu() == 62)
            || (doctorAuth.getClpu() == 63)
            || (doctorAuth.getClpu() == 64)) {
        } else {
            tabbedPane.removeTabAt(5);
//            pChildbirth.setVisible(false);
        }
    }

	public final void onConnect() {
        createModalFrames();
        try {
            System.out.println(ClientHospital.authInfo.getCpodr());
            lMedicalHistoryShablonNames.setData(ClientHospital.tcl.getShablonNames(
                doctorAuth.getCpodr(), doctorAuth.getCslu(),  null));
            lLifeHistoryShabloNames.setData(ClientHospital.tcl.getDopShablonNames(
                3, null));
            lDiagShablonNames.setData(ClientHospital.tcl.getShablonNames(
                doctorAuth.getCpodr(), doctorAuth.getCslu(),  null));
            lZaklShablonNames.setData(ClientHospital.tcl.getShablonNames(
                doctorAuth.getCpodr(), doctorAuth.getCslu(),  null));
            cbxIshod.setData(ClientHospital.tcl.getAp0());
            cbxResult.setData(ClientHospital.tcl.getAq0());
            tfStatus.setData(ClientHospital.tcl.getStationTypes(doctorAuth.getCpodr()));
            cbxAnotherOtd.setData(ClientHospital.tcl.getOtd(doctorAuth.getClpu()));
            CBPrinial.setData(ClientHospital.tcl.get_s_vrach());
            CBAkush.setData(ClientHospital.tcl.get_s_vrach());
            CBVrash.setData(ClientHospital.tcl.get_s_vrach());
            CBOsmotr.setData(ClientHospital.tcl.get_s_vrach());
            pChildren.setDoctors(ClientHospital.tcl.get_s_vrach());
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
    }

    private void clearAllComponentsAndObjects() {
        patient = null;
        clearPatientText();
        priemInfo = null;
        clearPriemInfoText();
        lifeHistory = null;
        clearLifeHistoryText();
        clearMedicalHistory();
        clearDiagnosisText();
        clearZaklText();
    }

    private class ShablonSearchListener implements DocumentListener {
        private CustomTextField ctf;
        private ThriftIntegerClassifierList ticl;

        public ShablonSearchListener(final CustomTextField inCtf,
                final ThriftIntegerClassifierList inTicl) {
            ctf = inCtf;
            ticl = inTicl;
        }

        private Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                updateNow();
            }
        });

        @Override
        public void removeUpdate(final DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void insertUpdate(final DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            timer.restart();
        }

        public void updateNow() {
            timer.stop();
            loadShablonList(ctf, ticl);
        }

        public void updateNow(final String searchString) {
            ctf.setText(searchString);
            updateNow();
        }
    }

    private void loadShablonList(final CustomTextField inCtf,
            final ThriftIntegerClassifierList inTicl) {
        try {
            List<IntegerClassifier> intClassif = ClientHospital.tcl.getShablonNames(
                doctorAuth.getCpodr(), doctorAuth.getCslu(),
                (inCtf.getText().length() < 3)
                ? null : '%' + inCtf.getText() + '%');
            inTicl.setData(intClassif);
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void syncShablonList(final String searchString, final Shablon shablon,
            final ShablonSearchListener shSl, final ThriftIntegerClassifierList ticl) {
        if (shablon != null) {
            shSl.updateNow(searchString);
            for (int i = 0; i < ticl.getData().size(); i++) {
                if (ticl.getData().get(i).pcod == shablon.getId()) {
                    ticl.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            ticl.setSelectedIndex(-1);
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
                    clearAllComponentsAndObjects();
                    fillPersonalInfoTextFields();
                    fillReceptionPanel();
                    fillLifeHistoryPanel();
                    fillMedHistoryTable();
                    fillDiagnosisTable();
                    fillStageTable();
                    pChildren.SetPatient(patient);

        			try {
						trdIshod = ClientHospital.tcl.getRdIshodInfo(
							patient.getPatientId(), patient.gospitalCod);
						setDefaultValues();
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						e.printStackTrace();
						ClientHospital.conMan.reconnect(e);
					}
//        			try {
//						trdIshod = ClientHospital.tcl.getRdIshodInfo(
//							patient.getPatientId(), patient.gospitalCod);
//						setDefaultValues();
//					} catch (KmiacServerException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (TException e) {
//						e.printStackTrace();
//						ClientHospital.conMan.reconnect(e);
//					}
//                  private void setChildbirthPanel() {
            		rddin = new RdDinStruct();
            		try {
            			rddin = ClientHospital.tcl.getRdDinInfo(patient.getPatientId(), patient.gospitalCod);
            			SVes.setValue(rddin.getVes());
            			Soj.setValue(rddin.getOj());
            			Shdm.setValue(rddin.getHdm());
                        Schcc.setValue(rddin.getChcc());
            			if (rddin.isSetPozpl())
            			CBPoz.setSelectedPcod(rddin.getPozpl());
            			else CBPoz.setSelectedItem(null);
            			if (rddin.isSetPolpl())
            			CBPolpl.setSelectedPcod(rddin.getPolpl());
            			else CBPolpl.setSelectedItem(null);
            			if (rddin.isSetVidpl())
            			CBVid.setSelectedPcod(rddin.getVidpl());
            			else CBVid.setSelectedItem(null);
            			if (rddin.isSetSerd())
            			CBSerd.setSelectedPcod(rddin.getSerd());
            			else CBSerd.setSelectedItem(null);
            			if (rddin.isSetSerd1())
            			CBSerd1.setSelectedPcod(rddin.getSerd1());
            			else CBSerd1.setSelectedItem(null);
            			if (rddin.isSetPredpl())
            			CBPred.setSelectedPcod(rddin.getPredpl());
            			else CBPred.setSelectedItem(null);
            			TVes.setText(String.valueOf(rddin.getOj()*rddin.getHdm()));

            		} catch (KmiacServerException e2) {
            			// TODO Auto-generated catch block
            			e2.printStackTrace();
            		} catch (TException e2) {
            			// TODO Auto-generated catch block
            			e2.printStackTrace();
            		}
            		trdIshod = new TRdIshod();
            		try {
            			trdIshod = ClientHospital.tcl.getRdIshodInfo(patient.getPatientId(), patient.gospitalCod);
            			setDefaultValues();
            			} catch (KmiacServerException e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		} catch (TException e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		}
            		rdsl = new RdSlStruct();
            		try {
            			rdsl = ClientHospital.tcl.getRdSlInfo(patient.getPatientId());
            			Tdataosl.setDate(rdsl.getDataosl());
            			if (rdsl.getDataosl() == 0)
            			Tdataosl.setText(null);
            			Tdatam.setDate(rdsl.getDataM());
            			if (rdsl.getDataM() == 0)
            			Tdatam.setText(null);
            			Srod.setValue(rdsl.getKolrod());
            			Sber.setValue(rdsl.getShet());
            			Sdtr.setValue(rdsl.getDTroch());
            			Stvera.setValue(rdsl.getCvera());
            			Sdcr.setValue(rdsl.getDsp());
            			Scdiag.setValue(rdsl.getCdiagt());
            			Sdsp.setValue(rdsl.getDsr());
            			Scext.setValue(rdsl.getCext());
            		} catch (KmiacServerException e2) {
            			// TODO Auto-generated catch block
            			e2.printStackTrace();
            		} catch (TException e2) {
            			// TODO Auto-generated catch block
            			e2.printStackTrace();
            		};
                }
                }
//            }
        });
        frmCuration = new CurationFrame(doctorAuth);
        frmCuration.pack();
        frmPrint = new PrintFrame();
        frmPrint.pack();
        frmShablon = new ShablonForm();
        frmShablon.pack();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////   Главное меню   ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

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
                frmPatientSelect.refreshModel(doctorAuth);
                frmPatientSelect.setVisible(true);
            }
        });
        mnPatientOperation.add(mntmSelectPatient);

        mntmReception = new JMenuItem("Приём в курацию");
        mntmReception.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frmCuration.refreshModel(doctorAuth);
                frmCuration.setVisible(true);
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
                if (patient != null) {
                    frmPrint.setPatient(patient);
                    frmPrint.setVisible(true);
                }
            }
        });
        mnPrintForms.add(mntmPrintStationDiary);

        mntmPrintHospitalSummary = new JMenuItem("Выписной эпикриз");
        mntmPrintHospitalSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    try {
                        String servPath =
                            ClientHospital.tcl.printHospitalSummary(patient.getGospitalCod(),
                                doctorAuth.getClpu_name() + " "
                                + doctorAuth.getCpodr_name(), patient);
                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                        ClientHospital.conMan.transferFileFromServer(servPath, cliPath);
                        ClientHospital.conMan.openFileInEditor(cliPath, false);
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        e1.printStackTrace();
                        ClientHospital.conMan.reconnect(e1);
                    }
                }
            }
        });
        mnPrintForms.add(mntmPrintHospitalSummary);

        mntmPrintHospitalDeathSummary = new JMenuItem("Посмертный эпикриз");
        mntmPrintHospitalDeathSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    try {
                        String servPath =
                            ClientHospital.tcl.printHospitalDeathSummary(patient.getGospitalCod(),
                                doctorAuth.getClpu_name() + " "
                                + doctorAuth.getCpodr_name(), patient);
                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                        ClientHospital.conMan.transferFileFromServer(servPath, cliPath);
                        ClientHospital.conMan.openFileInEditor(cliPath, false);
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        e1.printStackTrace();
                        ClientHospital.conMan.reconnect(e1);
                    }
                }
            }
        });
        mnPrintForms.add(mntmPrintHospitalDeathSummary);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////   Тулбар    ////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setToolBar() {
        getContentPane().setLayout(new BorderLayout(0, 0));
        toolBar = new JToolBar("Панель инструментов");
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);

        btnShowPatientInfo = new JButton();
        btnShowPatientInfo.setToolTipText("Информация о пациенте");
        toolBar.add(btnShowPatientInfo);
        btnShowPatientInfo.setMaximumSize(new Dimension(35, 35));
        btnShowPatientInfo.setMinimumSize(new Dimension(35, 35));
        btnShowPatientInfo.setPreferredSize(new Dimension(35, 35));
        btnShowPatientInfo.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    ClientHospital.conMan.showPatientInfoForm("Информация о пациенте",
                        patient.getPatientId());
                }
            }
        });
        btnShowPatientInfo.setBorder(null);
        btnShowPatientInfo.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/patientInfo.png")));
        btnShowPatientInfo.setRequestFocusEnabled(false);

        toolBar.add(new JToolBar.Separator());

        btnIssled = new JButton();
        btnIssled.setToolTipText("Лабораторные исследования");
        toolBar.add(btnIssled);
        btnIssled.setMaximumSize(new Dimension(35, 35));
        btnIssled.setMinimumSize(new Dimension(35, 35));
        btnIssled.setPreferredSize(new Dimension(35, 35));
        btnIssled.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    ClientHospital.conMan.showLabRecordForm(patient.getPatientId(),
                        patient.getSurname(), patient.getName(), patient.getMiddlename(),
                        patient.getGospitalCod());
                }
            }
        });
        btnIssled.setBorder(null);
        btnIssled.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/issled.png")));
        btnIssled.setRequestFocusEnabled(false);

        btnMedication = new JButton();
        btnMedication.setToolTipText("Лекарственные назначения");
        toolBar.add(btnMedication);
        btnMedication.setMaximumSize(new Dimension(35, 35));
        btnMedication.setMinimumSize(new Dimension(35, 35));
        btnMedication.setPreferredSize(new Dimension(35, 35));
        btnMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    ClientHospital.conMan.showMedicationForm(patient.getPatientId(),
                        patient.getSurname(), patient.getName(), patient.getMiddlename(),
                        patient.getGospitalCod());
                }
            }
        });
        btnMedication.setBorder(null);
        btnMedication.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/medication.png")));
        btnMedication.setRequestFocusEnabled(false);

        btnOperation = new JButton();
        btnOperation.setToolTipText("Операции");
        toolBar.add(btnOperation);
        btnOperation.setMaximumSize(new Dimension(35, 35));
        btnOperation.setMinimumSize(new Dimension(35, 35));
        btnOperation.setPreferredSize(new Dimension(35, 35));
        btnOperation.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    ClientHospital.conMan.showOperationForm(patient.getPatientId(),
                        patient.getSurname(), patient.getName(), patient.getMiddlename(),
                        patient.getGospitalCod());
                }
            }
        });
        btnOperation.setBorder(null);
        btnOperation.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/Skalpell.png")));
        btnOperation.setRequestFocusEnabled(false);
    }


//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   Информация о пациенте   //////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////


    private void setPatientInfoPanel() {
        spPatientInfo = new JSplitPane();
        spPatientInfo.setOrientation(JSplitPane.VERTICAL_SPLIT);
        tabbedPane.addTab("Информация о пациенте", new ImageIcon(
            MainFrame.class.getResource(
               "/ru/nkz/ivcgzo/clientHospital/resources/personalInfo.png")), spPatientInfo, null);
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

        lblStatus = new JLabel("Профиль отделения");
        tfStatus = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        tfStatus.setEditable(false);

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
        btnUpdateChamber.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if ((patient != null) && (!tfChamber.getText().isEmpty())
                        && (tfStatus.getSelectedItem() != null)) {
                    try {
                        ClientHospital.tcl.updatePatientChamberNumber(
                            patient.gospitalCod,
                            Integer.parseInt(tfChamber.getText()),
                            tfStatus.getSelectedPcod());
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Информация успешно сохранена!", "Сохранение завершено!",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(frmPatientSelect,
                            "Формат номера палаты неверный",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    } catch (KmiacServerException e1) {
                        JOptionPane.showMessageDialog(frmPatientSelect,
                            "Ошибка сохранения номера палаты",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    } catch (TException e1) {
                        ClientHospital.conMan.reconnect(e1);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Не проставлен номер палаты или тип стационара", "Ошибка!",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setPatientInfoPanelGroupLayout();
    }

    private void clearPatientText() {
        tfNumberOfDesiaseHistory.setText("");
        tfSurname.setText("");
        tfName.setText("");
        tfMiddlename.setText("");
        tfGender.setText("");
        tfBirthdate.setText("");
        tfOms.setText("");
        tfDms.setText("");
        tfStatus.setSelectedIndex(-1);
        tfChamber.setText("");
        tfStatus.setText("");
        tfWork.setText("");
        tfRegistrationAddress.setText("");
        tfRealAddress.setText("");
    }

    private void fillPersonalInfoTextFields() {
        try {
            patient = ClientHospital.tcl.getPatientPersonalInfo(
                frmPatientSelect.getCurrentPatient().getPatientId(),
                frmPatientSelect.getCurrentPatient().getIdGosp());
            setTitle(String.format("%s %s %s",
                patient.getSurname(), patient.getName(),
                patient.getMiddlename()));
        } catch (PatientNotFoundException e) {
            patient = null;
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }

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
            if (patient.getStatus() != 0) {
                tfStatus.setSelectedPcod(patient.getStatus());
            }
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

    private void clearPriemInfoText() {
        textPane.setText("");
    }

    private void fillReceptionPanel() {
        try {
            priemInfo = ClientHospital.tcl.getPriemInfo(
                patient.getGospitalCod());
        } catch (PriemInfoNotFoundException e) {
            priemInfo = null;
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }

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
        pLifeHistory.setLayout(new BoxLayout(pLifeHistory, BoxLayout.X_AXIS));
        tabbedPane.addTab("История жизни", new ImageIcon(
            MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/lifeHistory.png")), pLifeHistory, null);

        hsLifeHistoryFirst = Box.createHorizontalStrut(5);
        pLifeHistory.add(hsLifeHistoryFirst);

        setLifeHistoryVerticalTextPanels();

        hsLifeHistorySecond = Box.createHorizontalStrut(5);
        pLifeHistory.add(hsLifeHistorySecond);

        setLifeHistoryVerticalShablonPanel();

        hsLifeHistoryThird = Box.createHorizontalStrut(5);
        pLifeHistory.add(hsLifeHistoryThird);
    }

    private void setLifeHistoryVerticalTextPanels() {
        vbLifeHistoryTextFields = Box.createVerticalBox();
        vbLifeHistoryTextFields.setPreferredSize(new Dimension(500, 0));
        vbLifeHistoryTextFields.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbLifeHistoryTextFields.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        pLifeHistory.add(vbLifeHistoryTextFields);

        setLifeHistoryTextAreas();
        setLifeHistoryButtons();
    }

    private void setLifeHistoryTextAreas() {
        setLifeHistoryScrollPane();
        setAllergoScrollPane();
        setFarmoScrollPane();
    }

    private void setLifeHistoryScrollPane() {
        lblLifeHistory = new JLabel("История жизни");
        lblLifeHistory.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLifeHistory.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbLifeHistoryTextFields.add(lblLifeHistory);
        spLifeHistory = new JScrollPane();
        vbLifeHistoryTextFields.add(spLifeHistory);
        taLifeHistory = new JTextArea();
        spLifeHistory.setViewportView(taLifeHistory);
        taLifeHistory.setLineWrap(true);
        taLifeHistory.setWrapStyleWord(true);
        taLifeHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
        taLifeHistory.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                try {
                    if (!tfLifeHShablonFilter.isEmpty()) {
                        lLifeHistoryShabloNames.setData(
                            ClientHospital.tcl.getDopShablonNames(
                                4, tfLifeHShablonFilter.getText()));
                    } else {
                        lLifeHistoryShabloNames.setData(
                                ClientHospital.tcl.getDopShablonNames(4, null));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    e1.printStackTrace();
                    ClientHospital.conMan.reconnect(e1);
                }
            }
        });
    }

    private void setAllergoScrollPane() {
        lblAllergo = new JLabel("Аллергоанамнез");
        lblAllergo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAllergo.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbLifeHistoryTextFields.add(lblAllergo);
        spAllergo = new JScrollPane();
        vbLifeHistoryTextFields.add(spAllergo);
        taAllergo = new JTextArea();
        taAllergo.setWrapStyleWord(true);
        taAllergo.setLineWrap(true);
        taAllergo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spAllergo.setViewportView(taAllergo);
        taAllergo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                try {
                    if (!tfLifeHShablonFilter.isEmpty()) {
                        lLifeHistoryShabloNames.setData(
                            ClientHospital.tcl.getDopShablonNames(
                                3, tfLifeHShablonFilter.getText()));
                    } else {
                        lLifeHistoryShabloNames.setData(
                                ClientHospital.tcl.getDopShablonNames(3, null));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    e1.printStackTrace();
                    ClientHospital.conMan.reconnect(e1);
                }
            }
        });
    }

    private void setFarmoScrollPane() {
        lblFarmo = new JLabel("Фармоанамнез");
        lblFarmo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFarmo.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbLifeHistoryTextFields.add(lblFarmo);
        spFarmo = new JScrollPane();
        vbLifeHistoryTextFields.add(spFarmo);
        taFarmo = new JTextArea();
        taFarmo.setLineWrap(true);
        taFarmo.setWrapStyleWord(true);
        taFarmo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spFarmo.setViewportView(taFarmo);
        taFarmo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                try {
                    if (!tfLifeHShablonFilter.isEmpty()) {
                        lLifeHistoryShabloNames.setData(
                            ClientHospital.tcl.getDopShablonNames(
                                5, tfLifeHShablonFilter.getText()));
                    } else {
                        lLifeHistoryShabloNames.setData(
                            ClientHospital.tcl.getDopShablonNames(5, null));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    e1.printStackTrace();
                    ClientHospital.conMan.reconnect(e1);
                }
            }
        });
    }

    private void setLifeHistoryButtons() {
        btnSaveLifeHistory = new JButton("Сохранить");
        btnSaveLifeHistory.setMaximumSize(new Dimension(350, 40));
        btnSaveLifeHistory.setPreferredSize(new Dimension(350, 40));
        btnSaveLifeHistory.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSaveLifeHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        vbLifeHistoryTextFields.add(btnSaveLifeHistory);
        btnSaveLifeHistory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (patient != null) {
                    try {
                        updateLifeHistoryFromTextAreas();
                        ClientHospital.tcl.updateLifeHistory(lifeHistory);
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "История жизни сохранена", "Операция успешно завершена",
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (TException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Ошибка при "
                            + "изменении истории жизни. Информация не будет сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                        ClientHospital.conMan.reconnect(e1);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка при "
                            + "изменении истории жизни. Информация не будет сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void clearLifeHistoryText() {
        taLifeHistory.setText("");
        taAllergo.setText("");
        taFarmo.setText("");
    }

    private void setLifeHistoryVerticalShablonPanel() {
        vbLifeHistoryShablonComponents = Box.createVerticalBox();
        vbLifeHistoryShablonComponents.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbLifeHistoryShablonComponents.setPreferredSize(new Dimension(300, 0));
        pLifeHistory.add(vbLifeHistoryShablonComponents);

        setLifeHistoryShablonLabel();
        setLifeHistoryShablonHorizontalBox();
        setLifeHistoryShablonScrollPane();
    }

    private void setLifeHistoryShablonLabel() {
        lblLifeHistioryShablonHeader = new JLabel("Строка поиска шаблона");
        lblLifeHistioryShablonHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLifeHistioryShablonHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbLifeHistoryShablonComponents.add(lblLifeHistioryShablonHeader);
        lblLifeHistioryShablonHeader.setHorizontalTextPosition(SwingConstants.LEFT);
        lblLifeHistioryShablonHeader.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void setLifeHistoryShablonHorizontalBox() {
        hbLifeHistoryShablonFind = Box.createHorizontalBox();
        hbLifeHistoryShablonFind.setAlignmentY(Component.CENTER_ALIGNMENT);
        vbLifeHistoryShablonComponents.add(hbLifeHistoryShablonFind);

        setLifeHistoryShablonTextField();
        setLifeHistoryShablonButton();
    }

    private void setLifeHistoryShablonTextField() {
        tfLifeHShablonFilter = new CustomTextField(true, true, false);
        tfLifeHShablonFilter.setMaximumSize(new Dimension(450, 50));
        hbLifeHistoryShablonFind.add(tfLifeHShablonFilter);
        tfLifeHShablonFilter.getDocument().addDocumentListener(lifeHiSearchListener);
        tfLifeHShablonFilter.setColumns(10);
        lifeHiSearchListener =
            new ShablonSearchListener(tfLifeHShablonFilter, lLifeHistoryShabloNames);
    }

    private void setLifeHistoryShablonButton() {
    }

    private void setLifeHistoryShablonScrollPane() {
        spLifeHShablonNames = new JScrollPane();
        vbLifeHistoryShablonComponents.add(spLifeHShablonNames);

        setLifeHistoryShablonList();
    }

    private void setLifeHistoryShablonList() {
        lLifeHistoryShabloNames = new ThriftIntegerClassifierList();
        spLifeHShablonNames.setViewportView(lLifeHistoryShabloNames);
        lLifeHistoryShabloNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lLifeHistoryShabloNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lLifeHistoryShabloNames.getSelectedValue() != null) {
                        try {
                            pasteSelectedLifeHShablon(ClientHospital.tcl.getDopShablon(
                                lLifeHistoryShabloNames.getSelectedValue().pcod));
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
    }

    private void fillLifeHistoryPanel() {
        try {
            lifeHistory =
                ClientHospital.tcl.getLifeHistory(patient.getPatientId());
        } catch (LifeHistoryNotFoundException e) {
            lifeHistory = null;
        } catch (KmiacServerException e) {
            lifeHistory = null;
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
        if (lifeHistory != null) {
            taAllergo.setText(lifeHistory.getAllerg());
            taFarmo.setText(lifeHistory.getFarmkol());
            taLifeHistory.setText(lifeHistory.getVitae());
        }
    }

    private void pasteSelectedLifeHShablon(final DopShablon shablon) {
        if (shablon == null) {
            return;
        } else {
            switch (shablon.getNShablon()) {
                case 4:
                    taLifeHistory.setText(shablon.getText());
                    break;
                case 3:
                    taAllergo.setText(shablon.getText());
                    break;
                case 5:
                    taFarmo.setText(shablon.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private void updateLifeHistoryFromTextAreas() {
        if (lifeHistory == null) {
            lifeHistory = new TLifeHistory();
        }
        lifeHistory.setId(patient.getPatientId());
        lifeHistory.setAllerg(taAllergo.getText());
        lifeHistory.setFarmkol(taFarmo.getText());
        lifeHistory.setVitae(taLifeHistory.getText());
    };

//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////   Медицинская история   ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setMedicalHistoryPanel() {
        pMedicalHistory = new JPanel();
        tabbedPane.addTab("Дневник", new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/diary.png")),
            pMedicalHistory, null);
        pMedicalHistory.setLayout(new BoxLayout(pMedicalHistory, BoxLayout.X_AXIS));

        hsMedicalHistoryFirst = Box.createHorizontalStrut(5);
        pMedicalHistory.add(hsMedicalHistoryFirst);

        setMedicalHistoryVerticalTextComponents();

        hsMedicalHistorySecond = Box.createHorizontalStrut(5);
        pMedicalHistory.add(hsMedicalHistorySecond);

        setMedicalHistoryVerticalShablonPanel();

        hsMedicalHistoryThird = Box.createHorizontalStrut(5);
        pMedicalHistory.add(hsMedicalHistoryThird);
    }

    private void setMedicalHistoryVerticalTextComponents() {
        vbMedicalHistoryTextFields = Box.createVerticalBox();
        vbMedicalHistoryTextFields.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbMedicalHistoryTextFields.setPreferredSize(new Dimension(500, 0));
        pMedicalHistory.add(vbMedicalHistoryTextFields);

        setMedicalHistoryHorizontalTableComponents();
        setMedicalHistoryTabPaneComponents();
    }

    private void setMedicalHistoryHorizontalTableComponents() {
        hbMedicalHistoryTableControls = Box.createHorizontalBox();
        hbMedicalHistoryTableControls.setBorder(
            new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
        vbMedicalHistoryTextFields.add(hbMedicalHistoryTableControls);

        setMedicalHistoryTableScrollPane();
        setMedicalHistoryTableButtonsPanel();
    }

    private void setMedicalHistoryTableScrollPane() {
        spMedHist = new JScrollPane();
        spMedHist.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
        spMedHist.setPreferredSize(new Dimension(300, 250));
        hbMedicalHistoryTableControls.add(spMedHist);

        addMedicalHistoryTable();
    }

    private void addMedicalHistoryTable() {
        tbMedHist = new CustomTable<TMedicalHistory, TMedicalHistory._Fields>(
            true, true, TMedicalHistory.class, 8, "Дата", 9, "Время");
        spMedHist.setViewportView(tbMedHist);
        tbMedHist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (tbMedHist.getSelectedItem() != null) {
                    setMedicalHistoryText();
                }
            }
        });
        tbMedHist.setDateField(0);
        tbMedHist.setTimeField(1);
    }

    private void setMedicalHistoryTableButtonsPanel() {
        vbMedicalHistoryTableButtons = Box.createVerticalBox();
        hbMedicalHistoryTableControls.add(vbMedicalHistoryTableButtons);

        addMedicalHistoryButtons();
    }

    private void addMedicalHistoryButtons() {
        addMedicalHistoryAddButton();
        addMedicalHistoryDeleteButton();
        addMedicalHistoryUpdateButton();
    }

    private void addMedicalHistoryAddButton() {
        btnMedHistAdd = new JButton();
        btnMedHistAdd.setPreferredSize(new Dimension(50, 50));
        btnMedHistAdd.setMaximumSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistAdd);
        btnMedHistAdd.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                addMedHistoryToTable();
            }
        });
        btnMedHistAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
    }

    private void addMedicalHistoryDeleteButton() {
        btnMedHistDel = new JButton();
        btnMedHistDel.setMaximumSize(new Dimension(50, 50));
        btnMedHistDel.setPreferredSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistDel);
        btnMedHistDel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                deleteMedHistoryFormTable();
            }
        });
        btnMedHistDel.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
    }

    private void addMedicalHistoryUpdateButton() {
        btnMedHistUpd = new JButton();
        btnMedHistUpd.setPreferredSize(new Dimension(50, 50));
        btnMedHistUpd.setMaximumSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistUpd);
        btnMedHistUpd.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                updateMedHistoryToTable();
            }
        });
        btnMedHistUpd.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
    }


    private void setMedicalHistoryTabPaneComponents() {
        vsMedicalHistoryControlsDelim = Box.createVerticalStrut(20);
        vbMedicalHistoryTextFields.add(vsMedicalHistoryControlsDelim);

        addMedicalHistoryTabbedPane();
    }

    private void addMedicalHistoryTabbedPane() {
        tbpMedicalHistory = new JTabbedPane(JTabbedPane.LEFT);
        tbpMedicalHistory.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
        tbpMedicalHistory.setPreferredSize(new Dimension(300, 250));
        vbMedicalHistoryTextFields.add(tbpMedicalHistory);
        tbpMedicalHistory.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        setMedicalHistoryTabs();
        tbpMedicalHistory.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                for (int i = 0; i < tbpMedicalHistory.getTabCount(); i++) {
                    JLabel lbl = (JLabel) tbpMedicalHistory.getTabComponentAt(i);
                    if (lbl != null) {
                        if (i == tbpMedicalHistory.getSelectedIndex()) {
                            lbl.setForeground(selCol);
                        } else {
                            lbl.setForeground(defCol);
                        }
                    }
                }
            }
        });
    }

    private void setMedicalHistoryTabs() {
        addJalonPanel();
        addDesiaseHistoryPanel();
        addStatusPraencePanel();
        addFisicalObsPanel();
        addStausLocalisPanel();
    }

    private void deleteMedHistoryFormTable() {
        try {
            if (tbMedHist.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnMedHistDel, "Удалить запись?",
                    "Удаление записи", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    ClientHospital.tcl.deleteMedicalHistory(
                        tbMedHist.getSelectedItem().getId());
                    tbMedHist.setData(
                        ClientHospital.tcl.getMedicalHistory((patient.getGospitalCod())));
                }
                if (tbMedHist.getRowCount() > 0) {
                    tbMedHist.setRowSelectionInterval(tbMedHist.getRowCount() - 1,
                        tbMedHist.getRowCount() - 1);
                }
                clearMedicalHistoryTextAreas();
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (MedicalHistoryNotFoundException e1) {
            tbMedHist.setData(new ArrayList<TMedicalHistory>());
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }

    }

    private void updateMedHistoryToTable() {
        try {
            if (tbMedHist.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnMedHistUpd, "Обновить информацию о диагнозе?",
                    "Изменение диагноза", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    tbMedHist.getSelectedItem().setFisicalObs(taFisicalObs.getText());
                    tbMedHist.getSelectedItem().setJalob(taJalob.getText());
                    tbMedHist.getSelectedItem().setMorbi(taDesiaseHistory.getText());
                    tbMedHist.getSelectedItem().setStatusLocalis(taStatusLocalis.getText());
                    tbMedHist.getSelectedItem().setStatusPraesense(taStatusPraence.getText());
                    ClientHospital.tcl.updateMedicalHistory(tbMedHist.getSelectedItem());
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void addMedHistoryToTable() {
        try {
            if (patient != null) {
                TMedicalHistory medHist = new TMedicalHistory();
                medHist.setDataz(System.currentTimeMillis());
                medHist.setTimez(System.currentTimeMillis());
                medHist.setPcodVrach(doctorAuth.getPcod());
                medHist.setIdGosp(patient.getGospitalCod());
                medHist.setId(ClientHospital.tcl.addMedicalHistory(medHist));
                tbMedHist.addItem(medHist);
                tbMedHist.setData(
                        ClientHospital.tcl.getMedicalHistory(patient.getGospitalCod()));
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (MedicalHistoryNotFoundException e) {
            tbMedHist.setData(new ArrayList<TMedicalHistory>());
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void setMedicalHistoryText() {
        taJalob.setText(tbMedHist.getSelectedItem().getJalob());
        taDesiaseHistory.setText(tbMedHist.getSelectedItem().getMorbi());
        taFisicalObs.setText(tbMedHist.getSelectedItem().getFisicalObs());
        taStatusLocalis.setText(tbMedHist.getSelectedItem().getStatusLocalis());
        taStatusPraence.setText(tbMedHist.getSelectedItem().getStatusPraesense());
    }

    private void addJalonPanel() {
        pnJalob = new JPanel();
        pnJalob.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnJalob.setLayout(new BoxLayout(pnJalob, BoxLayout.Y_AXIS));

        spJalob = new JScrollPane();
        pnJalob.add(spJalob);

        taJalob = new JTextArea();
        spJalob.setViewportView(taJalob);
        taJalob.setFont(new Font("Tahoma", Font.PLAIN, 11));
        taJalob.setLineWrap(true);
        taJalob.setWrapStyleWord(true);

        tbpMedicalHistory.addTab("Жалобы", null, pnJalob, null);
        tbpMedicalHistory.setTabComponentAt(0, new JLabel("<html><br>Жалобы<br><br></html>"));
        ((JLabel) tbpMedicalHistory.getTabComponentAt(0)).setForeground(selCol);
    }

    private void addDesiaseHistoryPanel() {
        pnDesiaseHistory = new JPanel();
        pnDesiaseHistory.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnDesiaseHistory.setLayout(new BoxLayout(pnDesiaseHistory, BoxLayout.X_AXIS));

        spDesiaseHistory = new JScrollPane();
        pnDesiaseHistory.add(spDesiaseHistory);

        taDesiaseHistory = new JTextArea();
        spDesiaseHistory.setViewportView(taDesiaseHistory);
        taDesiaseHistory.setWrapStyleWord(true);
        taDesiaseHistory.setLineWrap(true);
        taDesiaseHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));

        tbpMedicalHistory.addTab("История болезни", null, pnDesiaseHistory, null);
        tbpMedicalHistory.setTabComponentAt(
            1, new JLabel("<html><br>История болезни<br><br></html>"));

    }

    private void addStatusPraencePanel() {
        pnStatusPraence = new JPanel();
        pnStatusPraence.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnStatusPraence.setLayout(new BoxLayout(pnStatusPraence, BoxLayout.X_AXIS));

        spStatusPraence = new JScrollPane();
        pnStatusPraence.add(spStatusPraence);

        taStatusPraence = new JTextArea();
        spStatusPraence.setViewportView(taStatusPraence);
        taStatusPraence.setLineWrap(true);
        taStatusPraence.setWrapStyleWord(true);
        taStatusPraence.setFont(new Font("Tahoma", Font.PLAIN, 11));

        tbpMedicalHistory.addTab("Объективный статус (Status praesens)",
            null, pnStatusPraence, null);
        tbpMedicalHistory.setTabComponentAt(
            2, new JLabel("<html><br>Объективный статус (Status praesens)<br><br></html>"));
    }

    private void addFisicalObsPanel() {
        pnFisicalObs = new JPanel();
        pnFisicalObs.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnFisicalObs.setLayout(new BoxLayout(pnFisicalObs, BoxLayout.X_AXIS));

        spFisicalObs = new JScrollPane();
        pnFisicalObs.add(spFisicalObs);

        taFisicalObs = new JTextArea();
        spFisicalObs.setViewportView(taFisicalObs);
        taFisicalObs.setLineWrap(true);
        taFisicalObs.setWrapStyleWord(true);
        taFisicalObs.setFont(new Font("Tahoma", Font.PLAIN, 11));

        tbpMedicalHistory.addTab("Физикальное обследование", null, pnFisicalObs, null);
        tbpMedicalHistory.setTabComponentAt(
            3, new JLabel("<html><br>Физикальное обследование<br><br></html>"));
    }

    private void addStausLocalisPanel() {
        pnStatusLocalis = new JPanel();
        pnStatusLocalis.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnStatusLocalis.setLayout(new BoxLayout(pnStatusLocalis, BoxLayout.X_AXIS));

        spStatusLocalis = new JScrollPane();
        pnStatusLocalis.add(spStatusLocalis);
        taStatusLocalis = new JTextArea();
        spStatusLocalis.setViewportView(taStatusLocalis);
        taStatusLocalis.setLineWrap(true);
        taStatusLocalis.setWrapStyleWord(true);
        taStatusLocalis.setFont(new Font("Tahoma", Font.PLAIN, 11));

        tbpMedicalHistory.addTab("Локальный статус (Status localis)",
            null, pnStatusLocalis, null);
        tbpMedicalHistory.setTabComponentAt(
            4, new JLabel("<html><br>Локальный статус (Status localis)<br><br></html>"));
    }

    private void clearMedicalHistory() {
        tbMedHist.setData(Collections.<TMedicalHistory>emptyList());
        taJalob.setText("");
        taDesiaseHistory.setText("");
        taFisicalObs.setText("");
        taStatusLocalis.setText("");
        taStatusPraence.setText("");
    }

    private void clearMedicalHistoryTextAreas() {
        taJalob.setText("");
        taDesiaseHistory.setText("");
        taFisicalObs.setText("");
        taStatusLocalis.setText("");
        taStatusPraence.setText("");
    }

    private void setMedicalHistoryVerticalShablonPanel() {
        vbMedicalHistoryShablonComponents = Box.createVerticalBox();
        vbMedicalHistoryShablonComponents.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbMedicalHistoryShablonComponents.setPreferredSize(new Dimension(300, 0));
        pMedicalHistory.add(vbMedicalHistoryShablonComponents);

        setMedicalHistoryShablonLabel();
        setMedicalHistoryShablonHorizontalBox();
        setMedicalHistoryShablonScrollPane();
        setMedicalHistoryShablonListener();
    }

    private void setMedicalHistoryShablonLabel() {
        lblMedicalHistioryShablonHeader = new JLabel("Строка поиска шаблона");
        lblMedicalHistioryShablonHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMedicalHistioryShablonHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbMedicalHistoryShablonComponents.add(lblMedicalHistioryShablonHeader);
        lblMedicalHistioryShablonHeader.setHorizontalTextPosition(SwingConstants.LEFT);
        lblMedicalHistioryShablonHeader.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void setMedicalHistoryShablonHorizontalBox() {
        hbMedicalHistoryShablonFind = Box.createHorizontalBox();
        vbMedicalHistoryShablonComponents.add(hbMedicalHistoryShablonFind);

        setMedicalHistoryShablonTextField();
        setMedicalHistoryShablonButton();
    }

    private void setMedicalHistoryShablonTextField() {
        tfMedHShablonFilter = new CustomTextField(true, true, false);
        tfMedHShablonFilter.setMaximumSize(new Dimension(450, 50));
        hbMedicalHistoryShablonFind.add(tfMedHShablonFilter);
        tfMedHShablonFilter.setColumns(10);
    }

    private void setMedicalHistoryShablonButton() {
        btnMedicalHistoryShablonFind = new JButton("...");
        btnMedicalHistoryShablonFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                frmShablon.showShablonForm(tfLifeHShablonFilter.getText(),
                    lLifeHistoryShabloNames.getSelectedValue());
                syncShablonList(frmShablon.getSearchString(), frmShablon.getShablon(),
                    medHiSearchListener, lMedicalHistoryShablonNames);
                pasteSelectedShablon(frmShablon.getShablon());
            }
        });
        btnMedicalHistoryShablonFind.setMinimumSize(new Dimension(63, 23));
        btnMedicalHistoryShablonFind.setMaximumSize(new Dimension(63, 23));
        btnMedicalHistoryShablonFind.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMedicalHistoryShablonFind.setPreferredSize(new Dimension(63, 23));
        hbMedicalHistoryShablonFind.add(btnMedicalHistoryShablonFind);
    }

    private void setMedicalHistoryShablonScrollPane() {
        spMedicalHistoryShablonNames = new JScrollPane();
        vbMedicalHistoryShablonComponents.add(spMedicalHistoryShablonNames);

        setMedicalHistoryShablonList();
    }

    private void setMedicalHistoryShablonList() {
        lMedicalHistoryShablonNames = new ThriftIntegerClassifierList();
        spMedicalHistoryShablonNames.setViewportView(lMedicalHistoryShablonNames);
        lMedicalHistoryShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lMedicalHistoryShablonNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lMedicalHistoryShablonNames.getSelectedValue() != null) {
                        try {
                            pasteSelectedShablon(ClientHospital.tcl.getShablon(
                                lMedicalHistoryShablonNames.getSelectedValue().pcod));
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
    }

    private void setMedicalHistoryShablonListener() {
        medHiSearchListener =
                new ShablonSearchListener(tfMedHShablonFilter, lMedicalHistoryShablonNames);
        tfMedHShablonFilter.getDocument().addDocumentListener(medHiSearchListener);
    }

    private void pasteSelectedShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        clearMedicalHistoryTextAreas();

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
                default:
                    break;
            }
        }
    }

    private void fillMedHistoryTable() {
        if (patient != null) {
            try {
                tbMedHist.setData(
                    ClientHospital.tcl.getMedicalHistory(patient.getGospitalCod()));
            } catch (MedicalHistoryNotFoundException e) {
                tbMedHist.setData(Collections.<TMedicalHistory>emptyList());
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// Диагнозы //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    private void setDiagnosisPanel() {
        pChildbirth = new JPanel();
        tabbedPane.addTab("Роды", new ImageIcon(
            MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/childbirth.png")), pChildbirth, null);
        panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JLabel lblNewLabel_11 = new JLabel("Схватки начались");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_12 = new JLabel("Воды отошли");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_13 = new JLabel("Качество и количество вод");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_14 = new JLabel("Полное открытие");
        lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_15 = new JLabel("Начало потуг");
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        ChBpsi = new JCheckBox("Психопрофилактическая подготовка");
        ChBpsi.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_24 = new JLabel("Медикаментозное обезболивание");
        lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_25 = new JLabel("Эффект");
        lblNewLabel_25.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TShvat = new JTextField();
        TShvat.setFont(new Font("Tahoma", Font.BOLD, 12));
        TShvat.setColumns(10);
        
        TVod = new JTextField();
        TVod.setFont(new Font("Tahoma", Font.BOLD, 12));
        TVod.setColumns(10);
        
        TKash = new JTextField();
        TKash.setFont(new Font("Tahoma", Font.BOLD, 12));
        TKash.setColumns(10);
        
        TPoln = new JTextField();
        TPoln.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPoln.setColumns(10);
        
        TNash = new JTextField();
        TNash.setFont(new Font("Tahoma", Font.BOLD, 12));
        TNash.setColumns(10);
        
        CBEff = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db13);
        
        TMed = new JTextField();
        TMed.setFont(new Font("Tahoma", Font.BOLD, 12));
        TMed.setColumns(10);
        
        JLabel lblNewLabel_23 = new JLabel("Продолжительность I период");
        lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer1 = new JTextField();
        TPer1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer1.setColumns(10);
        
        JLabel lblNewLabel_26 = new JLabel("II период");
        lblNewLabel_26.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer2 = new JTextField();
        TPer2.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer2.setColumns(10);
        
        JLabel lblNewLabel_27 = new JLabel("III период");
        lblNewLabel_27.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer3 = new JTextField();
        TPer3.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer3.setColumns(10);
        
        JButton btnNewButton = new JButton("Ребенок родился");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        						.addComponent(ChBpsi)
        						.addGroup(gl_panel_2.createSequentialGroup()
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblNewLabel_24)
        								.addComponent(lblNewLabel_25)
        								.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
        									.addComponent(lblNewLabel_23)
        									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        										.addComponent(lblNewLabel_27)
        										.addComponent(lblNewLabel_26))))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(TPer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(TPer2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(TPer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(CBEff, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
        								.addComponent(TMed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_panel_2.createSequentialGroup()
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblNewLabel_11)
        								.addComponent(lblNewLabel_12))
        							.addGap(67)
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(TVod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(TShvat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_panel_2.createSequentialGroup()
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblNewLabel_13)
        								.addComponent(lblNewLabel_14)
        								.addComponent(lblNewLabel_15))
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        								.addComponent(TNash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(TPoln, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
// тут был header конфликта мерджа
//        								.addComponent(TKash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
//        					.addPreferredGap(ComponentPlacement.RELATED, 427, Short.MAX_VALUE))
//        				.addGroup(gl_panel_2.createSequentialGroup()
//        					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
//        						.addComponent(lblNewLabel_24)
//        						.addComponent(lblNewLabel_25))
//        					.addPreferredGap(ComponentPlacement.RELATED)
//        					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
//        						.addComponent(CBEff, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
//        						.addComponent(TMed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
//        				.addComponent(chckbxNewCheckBox))
//        			.addContainerGap())
        								.addComponent(TKash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addGap(104)
        					.addComponent(btnNewButton)))
        			.addContainerGap(67, Short.MAX_VALUE))
        );
        gl_panel_2.setVerticalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_11)
        				.addComponent(TShvat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_12)
        				.addComponent(TVod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_13)
        				.addComponent(TKash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_14)
        				.addComponent(TPoln, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(TNash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_15))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
//        			.addComponent(chckbxNewCheckBox)
        			.addGap(10)
        			.addComponent(btnNewButton)
        			.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
        			.addComponent(ChBpsi)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_24)
        				.addComponent(TMed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_25)
        				.addComponent(CBEff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_23)
        				.addComponent(TPer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_26)
        				.addComponent(TPer2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_27)
        				.addComponent(TPer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        panel_2.setLayout(gl_panel_2);
        
        JLabel lblNewLabel = new JLabel("Окружность живота");
        lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        
        JLabel lblNewLabel_1 = new JLabel("Высота дна матки");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_2 = new JLabel("Положение плода");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_3 = new JLabel("Позиция");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_4 = new JLabel("Вид");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_5 = new JLabel("Сердцебиение плода");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_6 = new JLabel("Где находился");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_7 = new JLabel("Число ударов");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_8 = new JLabel("Предлежание");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_9 = new JLabel("Родовая деятельность");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_10 = new JLabel("Предполагаемый вес");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Soj = new JSpinner();
        Soj.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        Soj.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Shdm = new JSpinner();
        Shdm.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        Shdm.setFont(new Font("Tahoma", Font.BOLD, 12));
        
		CBPolpl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);

		CBPolpl.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBPoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db10);
		CBPoz.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBVid = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db11);
		CBVid.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBSerd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
		CBSerd.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBSerd1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
		CBSerd1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBPred = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
		CBPred.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		Schcc = new JSpinner();
		Schcc.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		TVes = new JTextField();
		TVes.setFont(new Font("Tahoma", Font.BOLD, 12));
		TVes.setColumns(10);
		
		TGde = new JTextField();
		TGde.setFont(new Font("Tahoma", Font.BOLD, 12));
		TGde.setColumns(10);
		
		JLabel lblNewLabel_33 = new JLabel("Вес женщины");
		lblNewLabel_33.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JSpinner SVes_1 = new JSpinner();
		SVes_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		TRod = new JTextField();
		TRod.setFont(new Font("Tahoma", Font.BOLD, 12));
		TRod.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblNewLabel_38 = new JLabel("Таз: D Sp.");
		lblNewLabel_38.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_39 = new JLabel("D Gr.");
		lblNewLabel_39.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_40 = new JLabel("D Tr.");
		lblNewLabel_40.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_41 = new JLabel("C ext.");
		lblNewLabel_41.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_42 = new JLabel("C Diag.");
		lblNewLabel_42.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_43 = new JLabel("T vera");
		lblNewLabel_43.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		Sdtr = new JSpinner();
		Sdtr.setFont(new Font("Dialog", Font.BOLD, 12));
		
		Stvera = new JSpinner();
		Stvera.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		Sdcr = new JSpinner();
		Sdcr.setFont(new Font("Dialog", Font.BOLD, 12));
		
		Scdiag = new JSpinner();
		Scdiag.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		Sdsp = new JSpinner();
		Sdsp.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		Scext = new JSpinner();
		Scext.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_41)
						.addComponent(lblNewLabel_38))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Sdsp)
						.addComponent(Scext, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_42)
						.addComponent(lblNewLabel_39))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Scdiag)
						.addComponent(Sdcr, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
					.addGap(10)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_40)
						.addComponent(lblNewLabel_43))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Stvera)
						.addComponent(Sdtr, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_38)
								.addComponent(Sdsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_41)
								.addComponent(Scext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_39)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_42))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(Sdcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_40)
								.addComponent(Sdtr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(Scdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_43)
								.addComponent(Stvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		JLabel lblNewLabel_37 = new JLabel("Первое шевеление плода");
		lblNewLabel_37.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		Tdataosl = new CustomDateEditor();
		Tdataosl.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tdataosl.setColumns(10);
		
		JLabel lblNewLabel_36 = new JLabel("Дата последних месячных");
		lblNewLabel_36.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		Tdatam = new CustomDateEditor();
		Tdatam.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tdatam.setColumns(10);
		
		JLabel lblNewLabel_34 = new JLabel("Которая беременность");
		lblNewLabel_34.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		Sber = new JSpinner();
		Sber.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_35 = new JLabel(" роды");
		lblNewLabel_35.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		Srod = new JSpinner();
		Srod.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_16 = new JLabel("Послед выделен");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		CBPosled = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db12);
		CBPosled.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel LVrem = new JLabel("Через ");
		LVrem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TVremp = new JTextField();
		TVremp.setFont(new Font("Tahoma", Font.BOLD, 12));
		TVremp.setColumns(10);
		
		JLabel lblNewLabel_17 = new JLabel("Детское место");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TDet = new JTextField();
		TDet.setFont(new Font("Tahoma", Font.BOLD, 12));
		TDet.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("Оболочки");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TObol = new JTextField();
		TObol.setFont(new Font("Tahoma", Font.BOLD, 12));
		TObol.setColumns(10);
		        
		        JLabel lblNewLabel_19 = new JLabel("Длина пуповины");
		        lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JPanel panel_4 = new JPanel();
		        panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		        
		        JLabel lblNewLabel_28 = new JLabel("Принял ребенка");
		        lblNewLabel_28.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JLabel lblNewLabel_29 = new JLabel("Послед осматривал");
		        lblNewLabel_29.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JLabel lblNewLabel_30 = new JLabel("Дежурный врач");
		        lblNewLabel_30.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JLabel lblNewLabel_31 = new JLabel("Акушерка");
		        lblNewLabel_31.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        //StringKlassifier s_vrash
		        CBPrinial = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		        CBPrinial.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        CBOsmotr = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		        CBOsmotr.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        CBVrash = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		        CBVrash.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        CBAkush = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		        CBAkush.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        JButton btnNewButton_1 = new JButton("");
		        btnNewButton_1.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		          		try {
		          	 		System.out.println(patient.getPatientId());	
		          	trdIshod.setNpasp(patient.getPatientId());
		 			if (TDatarod.getDate() != null)
		            trdIshod.setDatarod(TDatarod.getDate().getTime());
      		trdIshod.setDeyat(TRod.getText());
			if (CBEff.getSelectedPcod() != null)
				trdIshod.setEff(CBEff.getSelectedPcod());
				else trdIshod.unsetEff();
      		trdIshod.setKashetv(TKash.getText());
      		trdIshod.setKrov((int) SKrov.getModel().getValue());
      		trdIshod.setMesto(TDet.getText());
      		trdIshod.setObezb(TMed.getText());
 		   	trdIshod.setObol(TObol.getText());
 		   	trdIshod.setObvit(TObvit.getText());
      		trdIshod.setOsobp(TOsob.getText());
      		trdIshod.setPoln(TPoln.getText());
			if (CBPosled.getSelectedPcod() != null)
				trdIshod.setPosled(CBPosled.getSelectedPcod());
				else trdIshod.unsetPosled();
			if (CBAkush.getSelectedPcod() != null)
				trdIshod.setAkush(CBAkush.getSelectedPcod());
				else trdIshod.unsetAkush();
			if (CBPrinial.getSelectedPcod() != null)
				trdIshod.setPrinyl(CBPrinial.getSelectedPcod());
			    else trdIshod.unsetPrinyl();
			if (CBVrash.getSelectedPcod() != null)
				trdIshod.setVrash(CBVrash.getSelectedPcod());
			    else trdIshod.unsetVrash();
			if (CBOsmotr.getSelectedPcod() != null)
				trdIshod.setOsmposl(CBOsmotr.getSelectedPcod());
			else trdIshod.unsetOsmposl();
  		trdIshod.setDetmesto(TDet.getText());
 		System.out.println(trdIshod);	
      	rddin.setChcc( (int) Schcc.getModel().getValue());
  		rddin.setVes((int) SVes.getModel().getValue());
  		rddin.setOj((int) Soj.getModel().getValue());
  		rddin.setHdm((int) Shdm.getModel().getValue());
		if (CBVid.getSelectedPcod() != null)
			rddin.setVidpl(CBVid.getSelectedPcod());
			else rddin.unsetVidpl();
		if (CBSerd.getSelectedPcod() != null)
			rddin.setSerd(CBSerd.getSelectedPcod());
			else rddin.unsetSerd();
		if (CBSerd1.getSelectedPcod() != null)
			rddin.setSerd1(CBSerd1.getSelectedPcod());
			else rddin.unsetSerd1();
		if (CBPred.getSelectedPcod() != null)
			rddin.setPredpl(CBPred.getSelectedPcod());
			else rddin.unsetPredpl();
		if (CBPolpl.getSelectedPcod() != null)
			rddin.setPolpl(CBPolpl.getSelectedPcod());
			else rddin.unsetPolpl();
		if (CBPoz.getSelectedPcod() != null)
			rddin.setPozpl(CBPoz.getSelectedPcod());
			else rddin.unsetPozpl();
 		System.out.println(rddin);	
		rdsl.setShet((int) Sber.getModel().getValue());
		rdsl.setKolrod((int) Srod.getModel().getValue());
		rdsl.setDTroch((int) Sdtr.getModel().getValue());
		rdsl.setCvera((int) Stvera.getModel().getValue());
		rdsl.setDsr((int) Sdcr.getModel().getValue());
		rdsl.setCdiagt((int)Scdiag.getModel().getValue());
		rdsl.setDsp((int) Sdsp.getModel().getValue());
		rdsl.setCext((int) Scext.getModel().getValue());
		rdsl.setDatasn(System.currentTimeMillis());
		rddin.setNpasp(patient.getPatientId());
		rddin.setNgosp(patient.gospitalCod);
		//внести роды в исход беременности
 		System.out.println(rdsl);	
			ClientHospital.tcl.updateRdIshod(trdIshod);
			ClientHospital.tcl.UpdateRdSl(rdsl);
			ClientHospital.tcl.UpdateRdDin(rddin);
		} catch (KmiacServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		        	}
		        });
		        btnNewButton_1.setToolTipText("Сохранить");
		        btnNewButton_1.setIcon(new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
		        
		        JButton btnNewButton_2 = new JButton("");
		        btnNewButton_2.setIcon(new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
		        btnNewButton_2.setToolTipText("Удалить");
		        
		        TDatarod = new CustomDateEditor();
		        TDatarod.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        JComboBox CBishod = new JComboBox();
		        CBishod.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        JLabel lblNewLabel_32 = new JLabel("Дата родов");
		        lblNewLabel_32.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JLabel lblNewLabel_44 = new JLabel("Исход беременности");
		        lblNewLabel_44.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		        gl_panel_4.setHorizontalGroup(
		        	gl_panel_4.createParallelGroup(Alignment.LEADING)
		        		.addGroup(gl_panel_4.createSequentialGroup()
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addComponent(lblNewLabel_29)
		        					.addGap(18)
		        					.addComponent(CBOsmotr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(18)
		        					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        						.addGroup(gl_panel_4.createSequentialGroup()
		        							.addComponent(lblNewLabel_28)
		        							.addGap(18)
		        							.addComponent(CBPrinial, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_4.createSequentialGroup()
		        							.addGap(10)
		        							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
		        								.addGroup(gl_panel_4.createSequentialGroup()
		        									.addComponent(lblNewLabel_31)
		        									.addGap(46)
		        									.addComponent(CBAkush, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
		        								.addGroup(gl_panel_4.createSequentialGroup()
		        									.addComponent(lblNewLabel_30)
		        									.addGap(10)
		        									.addComponent(CBVrash, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))))))
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addComponent(lblNewLabel_44)
		        					.addPreferredGap(ComponentPlacement.RELATED)
		        					.addComponent(CBishod, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addContainerGap()
		        					.addComponent(lblNewLabel_32, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
		        					.addPreferredGap(ComponentPlacement.RELATED)
		        					.addComponent(TDatarod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        					.addGap(60)
		        					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
		        					.addPreferredGap(ComponentPlacement.UNRELATED)
		        					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
		        			.addContainerGap(28, Short.MAX_VALUE))
		        );
		        gl_panel_4.setVerticalGroup(
		        	gl_panel_4.createParallelGroup(Alignment.LEADING)
		        		.addGroup(gl_panel_4.createSequentialGroup()
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_29))
		        				.addComponent(CBOsmotr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_28))
		        				.addComponent(CBPrinial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_30))
		        				.addComponent(CBVrash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_31))
		        				.addComponent(CBAkush, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_44))
		        				.addComponent(CBishod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
		        				.addComponent(btnNewButton_1)
		        				.addGroup(gl_panel_4.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblNewLabel_32))
		        				.addComponent(TDatarod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addComponent(btnNewButton_2))
		        			.addContainerGap(42, Short.MAX_VALUE))
		        );
		        panel_4.setLayout(gl_panel_4);
		        GroupLayout gl_pChildbirth = new GroupLayout(pChildbirth);
		        gl_pChildbirth.setHorizontalGroup(
		        	gl_pChildbirth.createParallelGroup(Alignment.LEADING)
		        		.addGroup(gl_pChildbirth.createSequentialGroup()
		        			.addContainerGap()
		        			.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_pChildbirth.createParallelGroup(Alignment.LEADING, false)
		        				.addComponent(panel_4, 0, 0, Short.MAX_VALUE)
		        				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
		        			.addContainerGap(52, Short.MAX_VALUE))
		        );
		        gl_pChildbirth.setVerticalGroup(
		        	gl_pChildbirth.createParallelGroup(Alignment.TRAILING)
		        		.addGroup(gl_pChildbirth.createSequentialGroup()
		        			.addGap(11)
		        			.addGroup(gl_pChildbirth.createParallelGroup(Alignment.TRAILING)
		        				.addGroup(Alignment.LEADING, gl_pChildbirth.createSequentialGroup()
		        					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
		        					.addPreferredGap(ComponentPlacement.RELATED)
		        					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE))
		        				.addComponent(panel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 637, Short.MAX_VALUE))
		        			.addContainerGap())
		        );
		        
		        JSpinner SDlina_1 = new JSpinner();
		        SDlina_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		        
		        JLabel lblNewLabel_20 = new JLabel("Обвитие вокруг");
		        lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        textField = new JTextField();
		        textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		        textField.setColumns(10);
		        
		        JLabel lblNewLabel_21 = new JLabel("Особенности");
		        lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        TOsob = new JTextField();
		        TOsob.setFont(new Font("Tahoma", Font.BOLD, 12));
		        TOsob.setColumns(10);
		        
		        JLabel lblNewLabel_22 = new JLabel("Кровопотеря мл.");
		        lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        JSpinner SKrov_1 = new JSpinner();
		        SKrov_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		        gl_panel_3.setHorizontalGroup(
		        	gl_panel_3.createParallelGroup(Alignment.LEADING)
		        		.addGroup(gl_panel_3.createSequentialGroup()
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(10)
		        					.addComponent(lblNewLabel_34)
		        					.addGap(18)
		        					.addComponent(Sber, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
		        					.addGap(4)
		        					.addComponent(lblNewLabel_35)
		        					.addGap(10)
		        					.addComponent(Srod, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(10)
		        					.addComponent(lblNewLabel_36)
		        					.addGap(4)
		        					.addComponent(Tdatam, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(10)
		        					.addComponent(lblNewLabel_37)
		        					.addGap(4)
		        					.addComponent(Tdataosl, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addContainerGap()
		        					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_1)
		        							.addPreferredGap(ComponentPlacement.RELATED)
		        							.addComponent(Shdm, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
		        							.addPreferredGap(ComponentPlacement.UNRELATED)
		        							.addComponent(lblNewLabel_10)
		        							.addPreferredGap(ComponentPlacement.UNRELATED)
		        							.addComponent(TVes, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_2)
		        							.addGap(25)
		        							.addComponent(CBPolpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_3)
		        							.addGap(82)
		        							.addComponent(CBPoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_4)
		        							.addGap(109)
		        							.addComponent(CBVid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_5)
		        							.addGap(10)
		        							.addComponent(CBSerd, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
		        							.addGap(6)
		        							.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
		        							.addGap(10)
		        							.addComponent(lblNewLabel_7)
		        							.addPreferredGap(ComponentPlacement.UNRELATED)
		        							.addComponent(Schcc, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))))
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(10)
		        					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_33)
		        							.addGap(10)
		        							.addComponent(SVes_1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
		        							.addGap(18)
		        							.addComponent(lblNewLabel)
		        							.addGap(10)
		        							.addComponent(Soj, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
		        							.addPreferredGap(ComponentPlacement.RELATED))
		        						.addComponent(panel_6, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)))
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addContainerGap()
		        					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_8)
		        							.addGap(52)
		        							.addComponent(CBPred, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_6)
		        							.addGap(48)
		        							.addComponent(TGde, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addGap(10)
		        							.addComponent(lblNewLabel_16)
		        							.addGap(10)
		        							.addComponent(CBPosled, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
		        							.addGap(10)
		        							.addComponent(LVrem)
		        							.addPreferredGap(ComponentPlacement.RELATED)
		        							.addComponent(TVremp, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_17)
		        							.addGap(18)
		        							.addComponent(TDet, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_9)
		        							.addGap(4)
		        							.addComponent(TRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addGap(28)
		        							.addComponent(lblNewLabel_18)
		        							.addGap(18)
		        							.addComponent(TObol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addComponent(lblNewLabel_19)
		        							.addPreferredGap(ComponentPlacement.UNRELATED)
		        							.addComponent(SDlina_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
		        							.addGap(18)
		        							.addComponent(lblNewLabel_20)
		        							.addPreferredGap(ComponentPlacement.RELATED)
		        							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
		        						.addGroup(gl_panel_3.createSequentialGroup()
		        							.addGap(10)
		        							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        								.addGroup(gl_panel_3.createSequentialGroup()
		        									.addComponent(lblNewLabel_21)
		        									.addGap(18)
		        									.addComponent(TOsob, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
		        								.addGroup(gl_panel_3.createSequentialGroup()
		        									.addComponent(lblNewLabel_22)
		        									.addGap(18)
		        									.addComponent(SKrov_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))))))
		        			.addContainerGap(40, Short.MAX_VALUE))
		        );
		        gl_panel_3.setVerticalGroup(
		        	gl_panel_3.createParallelGroup(Alignment.LEADING)
		        		.addGroup(gl_panel_3.createSequentialGroup()
		        			.addGap(1)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(2)
		        					.addComponent(lblNewLabel_34))
		        				.addComponent(Sber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(2)
		        					.addComponent(lblNewLabel_35))
		        				.addComponent(Srod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(11)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblNewLabel_36))
		        				.addComponent(Tdatam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblNewLabel_37))
		        				.addComponent(Tdataosl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(11)
		        			.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(2)
		        					.addComponent(lblNewLabel_33))
		        				.addComponent(SVes_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(1)
		        					.addComponent(lblNewLabel))
		        				.addComponent(Soj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(2)
		        					.addComponent(lblNewLabel_1))
		        				.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        					.addComponent(Shdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        					.addComponent(lblNewLabel_10)
		        					.addComponent(TVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(8)
		        					.addComponent(lblNewLabel_2))
		        				.addComponent(CBPolpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(8)
		        					.addComponent(lblNewLabel_3))
		        				.addComponent(CBPoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(8)
		        					.addComponent(lblNewLabel_4))
		        				.addComponent(CBVid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_5))
		        				.addComponent(CBSerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        					.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        					.addComponent(lblNewLabel_7)
		        					.addComponent(Schcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(8)
		        					.addComponent(lblNewLabel_8))
		        				.addComponent(CBPred, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(6)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(6)
		        					.addComponent(lblNewLabel_6))
		        				.addComponent(TGde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(11)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblNewLabel_9))
		        				.addComponent(TRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addGroup(gl_panel_3.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel_16))
		        				.addComponent(CBPosled, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
		        					.addComponent(TVremp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        					.addComponent(LVrem)))
		        			.addGap(14)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        				.addComponent(lblNewLabel_17)
		        				.addComponent(TDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(12)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        				.addComponent(lblNewLabel_18)
		        				.addComponent(TObol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(9)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        				.addComponent(lblNewLabel_19)
		        				.addComponent(SDlina_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addComponent(lblNewLabel_20)
		        				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		        				.addComponent(lblNewLabel_21)
		        				.addComponent(TOsob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
		        				.addComponent(lblNewLabel_22)
		        				.addComponent(SKrov_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(66))
		        );
		        panel_3.setLayout(gl_panel_3);
		        pChildbirth.setLayout(gl_pChildbirth);
        pDiagnosis = new JPanel();
        tabbedPane.addTab("Диагнозы", new ImageIcon(
            MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/firstAid.png")), pDiagnosis, null);
        pDiagnosis.setLayout(new BoxLayout(pDiagnosis, BoxLayout.X_AXIS));

        hsDiagnosisFirst = Box.createHorizontalStrut(5);
        pDiagnosis.add(hsDiagnosisFirst);

        setDiagnosisVerticalTextComponents();

        hsDiagnosisSecond = Box.createHorizontalStrut(5);
        pDiagnosis.add(hsDiagnosisSecond);

        setDiagnosisVerticalShablonPanel();

        hsDiagnosisThird = Box.createHorizontalStrut(5);
        pDiagnosis.add(hsDiagnosisThird);
    }

    private void setDiagnosisVerticalTextComponents() {
        vbDiagnosisTextFields = Box.createVerticalBox();
        vbDiagnosisTextFields.setBorder(new EtchedBorder(
            EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbDiagnosisTextFields.setPreferredSize(new Dimension(500, 0));
        pDiagnosis.add(vbDiagnosisTextFields);

        setDiagnosisHorizontalTableComponents();
        setDiagnosisTextComponents();
    }

    private void setDiagnosisHorizontalTableComponents() {
        hbDiagnosisTableControls = Box.createHorizontalBox();
        hbDiagnosisTableControls.setBorder(
            new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
        vbDiagnosisTextFields.add(hbDiagnosisTableControls);

        setDiagnosisTableScrollPane();
        setDiagnosisTableButtonsPanel();
    }

    private void setDiagnosisTableScrollPane() {
        spDiag = new JScrollPane();
        spDiag.setPreferredSize(new Dimension(300, 250));
        spDiag.setBorder(
            new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
        hbDiagnosisTableControls.add(spDiag);

        addDiagnosisTable();
    }

    private void addDiagnosisTable() {
        tbDiag = new CustomTable<TDiagnosis, TDiagnosis._Fields>(
                true, true, TDiagnosis.class, 4, "Дата", 2, "Код МКБ", 7, "Наименование диагноза");
        tbDiag.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (tbDiag.getSelectedItem() != null) {
                    setDiagPriznRdbtn();
                    taDiagMedOp.setText(tbDiag.getSelectedItem().getMedOp());
                }
            }
        });
        tbDiag.setDateField(0);
        tbDiag.setEditableFields(false, 1);
        tbDiag.setEditableFields(false, 2);
        spDiag.setViewportView(tbDiag);
    }

    private void setDiagnosisTableButtonsPanel() {
        vbDiagnosisTableButtons = Box.createVerticalBox();
        vbDiagnosisTableButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbDiagnosisTableControls.add(vbDiagnosisTableButtons);

        addDiagnosisButtons();
    }

    private void addDiagnosisButtons() {
        addDiagnosisAddButton();
        addDiagnosisDeleteButton();
        addDiagnosisUpdateButton();
    }

    private void addDiagnosisAddButton() {
        btnAddDiag = new JButton();
        btnAddDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddDiag.setMaximumSize(new Dimension(50, 50));
        btnAddDiag.setPreferredSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnAddDiag);
        btnAddDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                addDiagnosisToTable(ClientHospital.conMan.showMkbTreeForm("Диагноз", ""));
            }
        });
        btnAddDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
    }

    private void addDiagnosisDeleteButton() {
        btnDelDiag = new JButton();
        btnDelDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelDiag.setPreferredSize(new Dimension(50, 50));
        btnDelDiag.setMaximumSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnDelDiag);
        btnDelDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                delDiagnosisFromTable();
            }
        });
        btnDelDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
    }

    private void addDiagnosisUpdateButton() {
        btnSaveDiag = new JButton();
        btnSaveDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSaveDiag.setPreferredSize(new Dimension(50, 50));
        btnSaveDiag.setMaximumSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnSaveDiag);
        btnSaveDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                saveDiagnosisToTable();
            }
        });
        btnSaveDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
    }

    private void setDiagnosisTextComponents() {
        vsDiagnosisControlsDelimFirst = Box.createVerticalStrut(10);
        vbDiagnosisTextFields.add(vsDiagnosisControlsDelimFirst);

        addDiagnosisRadioButtonsGroup();

        panel = new JPanel();
        vbDiagnosisTextFields.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        verticalStrut = Box.createVerticalStrut(10);
        vbDiagnosisTextFields.add(verticalStrut);

        addDiagnosisMedOpHeader();
        addDiagnosisMedOpScrollPane();
    }

    private void addDiagnosisMedOpHeader() {
        lblDiagMedOp = new JLabel("Медицинское описание диагноза");
        lblDiagMedOp.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDiagMedOp.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbDiagnosisTextFields.add(lblDiagMedOp);
    }

    private void addDiagnosisMedOpScrollPane() {
        spDiagnosisMedOp = new JScrollPane();
        spDiagnosisMedOp.setPreferredSize(new Dimension(300, 250));
        vbDiagnosisTextFields.add(spDiagnosisMedOp);

        addDiagnosisMedOpTextArea();
    }

    private void addDiagnosisMedOpTextArea() {
        taDiagMedOp = new JTextArea();
        taDiagMedOp.setBorder(new LineBorder(new Color(0, 0, 0)));
        taDiagMedOp.setLineWrap(true);
        taDiagMedOp.setWrapStyleWord(true);
        spDiagnosisMedOp.setViewportView(taDiagMedOp);
        taDiagMedOp.setFont(new Font("Tahoma", Font.PLAIN, 11));
    }

    private void addDiagnosisRadioButtonsGroup() {
        addDiagnosisRadioGroupPanel();
        addDiagnosisRadioGroupButtons();
    }

    private void addDiagnosisRadioGroupPanel() {
        pDiagTypes = new JPanel();
        pDiagTypes.setPreferredSize(new Dimension(425, 25));
        pDiagTypes.setMaximumSize(new Dimension(425, 25));
        vbDiagnosisTextFields.add(pDiagTypes);
        pDiagTypes.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDiagTypes.setLayout(new BoxLayout(pDiagTypes, BoxLayout.X_AXIS));
        pDiagTypes.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void addDiagnosisRadioGroupButtons() {
        rdbtnMain = new JRadioButton("Основной");
        rdbtnMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDiagTypes.add(rdbtnMain);

        hzstMainDiagSopDiag = Box.createHorizontalStrut(50);
        hzstMainDiagSopDiag.setPreferredSize(new Dimension(50, 0));
        hzstMainDiagSopDiag.setMaximumSize(new Dimension(50, 50));
        hzstMainDiagSopDiag.setMinimumSize(new Dimension(5, 0));
        pDiagTypes.add(hzstMainDiagSopDiag);

        rdbtnSoput = new JRadioButton("Сопутствующий");
        rdbtnSoput.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDiagTypes.add(rdbtnSoput);

        hzstSopDiagOslDiag = Box.createHorizontalStrut(50);
        hzstSopDiagOslDiag.setPreferredSize(new Dimension(50, 0));
        hzstSopDiagOslDiag.setMaximumSize(new Dimension(50, 50));
        hzstSopDiagOslDiag.setMinimumSize(new Dimension(5, 0));
        pDiagTypes.add(hzstSopDiagOslDiag);

        rdbtnOsl = new JRadioButton("Осложнение основного");
        rdbtnOsl.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDiagTypes.add(rdbtnOsl);

        btgDiag = new ButtonGroup();
        btgDiag.add(rdbtnMain);
        btgDiag.add(rdbtnSoput);
        btgDiag.add(rdbtnOsl);
    }

    private void setDiagnosisVerticalShablonPanel() {
        vbDiagnosisShablonComponents = Box.createVerticalBox();
        vbDiagnosisShablonComponents.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbDiagnosisShablonComponents.setPreferredSize(new Dimension(300, 0));
        pDiagnosis.add(vbDiagnosisShablonComponents);

        setDiagnosisShablonLabel();
        setDiagnosisShablonHorizontalBox();
        setDiagnosisShablonScrollPane();
        setDiagnosisShablonListener();
    }

    private void setDiagnosisShablonLabel() {
        lblDiagnosisShablonHeader = new JLabel("Строка поиска шаблона");
        lblDiagnosisShablonHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDiagnosisShablonHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbDiagnosisShablonComponents.add(lblDiagnosisShablonHeader);
        lblDiagnosisShablonHeader.setHorizontalTextPosition(SwingConstants.LEFT);
        lblDiagnosisShablonHeader.setHorizontalAlignment(SwingConstants.LEFT);
        lblDiagnosisShablonHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void setDiagnosisShablonHorizontalBox() {
        hbDiagnosisShablonFind = Box.createHorizontalBox();
        vbDiagnosisShablonComponents.add(hbDiagnosisShablonFind);

        setDiagnosisShablonTextField();
        setDiagnosisShablonButton();
    }

    private void setDiagnosisShablonTextField() {
        tfDiagShablonFilter = new CustomTextField(true, true, false);
        tfDiagShablonFilter.setMaximumSize(new Dimension(450, 50));
        hbDiagnosisShablonFind.add(tfDiagShablonFilter);
        tfDiagShablonFilter.setColumns(10);
    }

    private void setDiagnosisShablonButton() {
        btnDiagnosisShablonFind = new JButton("...");
        btnDiagnosisShablonFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                frmShablon.showShablonForm(tfDiagShablonFilter.getText(),
                    lDiagShablonNames.getSelectedValue());
                syncShablonList(frmShablon.getSearchString(), frmShablon.getShablon(),
                    diagSearchListener, lDiagShablonNames);
//                pasteSelectedShablon(frmShablon.getShablon());
            }
        });
        btnDiagnosisShablonFind.setMinimumSize(new Dimension(63, 23));
        btnDiagnosisShablonFind.setMaximumSize(new Dimension(63, 23));
        btnDiagnosisShablonFind.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDiagnosisShablonFind.setPreferredSize(new Dimension(63, 23));
        hbDiagnosisShablonFind.add(btnDiagnosisShablonFind);
    }

    private void setDiagnosisShablonScrollPane() {
        spDiagShablonNames = new JScrollPane();
        vbDiagnosisShablonComponents.add(spDiagShablonNames);

        setDiagnosisShablonList();
    }

    private void setDiagnosisShablonList() {
        lDiagShablonNames = new ThriftIntegerClassifierList();
        lDiagShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        spDiagShablonNames.setViewportView(lDiagShablonNames);
    }

    private void setDiagnosisShablonListener() {
        diagSearchListener = new ShablonSearchListener(tfDiagShablonFilter, lDiagShablonNames);
        tfDiagShablonFilter.getDocument().addDocumentListener(diagSearchListener);
    }

    private void clearDiagnosisText() {
        tbDiag.setData(Collections.<TDiagnosis>emptyList());
        taDiagMedOp.setText(null);
        rdbtnMain.setSelected(false);
        rdbtnSoput.setSelected(false);
        rdbtnOsl.setSelected(false);
    }

    private void fillDiagnosisTable() {
        if (patient != null) {
            try {
                tbDiag.setData(
                    ClientHospital.tcl.getDiagnosis(patient.getGospitalCod()));
                setDiagPriznRdbtn();
            } catch (DiagnosisNotFoundException e) {
                Collections.<TDiagnosis>emptyList();
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    private void setDiagPriznRdbtn() {
        if (tbDiag.getSelectedItem() != null) {
            if (tbDiag.getSelectedItem().getPrizn() == 1) {
                rdbtnMain.setSelected(true);
            } else if (tbDiag.getSelectedItem().getPrizn() == 3) {
                rdbtnSoput.setSelected(true);
            } else if (tbDiag.getSelectedItem().getPrizn() == 2) {
                rdbtnOsl.setSelected(true);
            } else {
                rdbtnMain.setSelected(false);
                rdbtnSoput.setSelected(false);
                rdbtnOsl.setSelected(false);
            }
        }
    }

    private void delDiagnosisFromTable() {
        try {
            if (tbDiag.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnDelDiag, "Удалить диагноз?",
                    "Удаление диагноза", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    ClientHospital.tcl.deleteDiagnosis(tbDiag.getSelectedItem().getId());
                    tbDiag.setData(
                        ClientHospital.tcl.getDiagnosis(patient.getGospitalCod()));
                }
                if (tbDiag.getRowCount() > 0) {
                    tbDiag.setRowSelectionInterval(tbDiag.getRowCount() - 1,
                        tbDiag.getRowCount() - 1);
                }
                taDiagMedOp.setText("");
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (DiagnosisNotFoundException e1) {
            tbDiag.setData(new ArrayList<TDiagnosis>());
            //e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void saveDiagnosisToTable() {
        try {
            if (tbDiag.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnDelDiag, "Добавить информацию о диагнозе?",
                    "Изменение диагноза", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    tbDiag.getSelectedItem().setMedOp(taDiagMedOp.getText());
                    if (rdbtnMain.isSelected()) {
                        tbDiag.getSelectedItem().setPrizn(1);
                    }
                    if (rdbtnSoput.isSelected()) {
                        tbDiag.getSelectedItem().setPrizn(3);
                    }
                    if (rdbtnOsl.isSelected()) {
                        tbDiag.getSelectedItem().setPrizn(2);
                    }
                    tbDiag.getSelectedItem().setIdGosp(patient.getGospitalCod());
                    ClientHospital.tcl.updateDiagnosis(tbDiag.getSelectedItem());
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
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
//                taDiagMedOp.setText(curDiagnosis.getName());
//                tbDiag.addItem(diag);
                tbDiag.setData(
                    ClientHospital.tcl.getDiagnosis(patient.getGospitalCod()));
                if (tbDiag.getRowCount() > 1) {
                    tbDiag.setRowSelectionInterval(tbDiag.getRowCount() - 2,
                        tbDiag.getRowCount() - 1);
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (DiagnosisNotFoundException e) {
            tbDiag.setData(new ArrayList<TDiagnosis>());
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////            Этапы            ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setStagePanel() {
        pStage = new JPanel();
        tabbedPane.addTab("Этапы лечения", new ImageIcon(
            MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/stages.png")), pStage, null);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                if ((tabbedPane.getSelectedIndex() != 3) && (!isStageTableSaved())) {
                    tabbedPane.setSelectedIndex(3);
                }
            }
        });
        pStage.setLayout(new BoxLayout(pStage, BoxLayout.X_AXIS));

        addStageTablePanel();
        addStageButtonPanel();
    }

    private void addStageTablePanel() {
        spStageTable = new JScrollPane();
        spStageTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        pStage.add(spStageTable);

        tbStages = new CustomTable<TStage, TStage._Fields>(
            true, true, TStage.class, 4, "Дата начала", 9, "Время начала", 5, "Дата окончания",
            10, "Время окончания", 2, "Этап", 3, "МЭС", 6, "УКЛ", 7, "Исход", 8, "Результат");
        tbStages.setDateField(0);
        tbStages.setTimeField(1);
        tbStages.setDateField(2);
        tbStages.setTimeField(3);
        tbStages.setIntegerClassifierSelector(4, IntegerClassifiers.n_etp);
        tbStages.setIntegerClassifierSelector(7, IntegerClassifiers.n_ap0);
        tbStages.setIntegerClassifierSelector(8, IntegerClassifiers.n_aq0);
        spStageTable.setViewportView(tbStages);
    }

    private void addStageButtonPanel() {
        pStageButtons = new JPanel();
        pStageButtons.setMaximumSize(new Dimension(50, 32767));
        pStage.add(pStageButtons);
        pStageButtons.setLayout(new BoxLayout(pStageButtons, BoxLayout.Y_AXIS));

        addStageAddButton();
        addStageDeleteButton();
        addStageUpdateButton();
    }

    private void addStageAddButton() {
        btnAddStage = new JButton();
        btnAddStage.setMaximumSize(new Dimension(50, 50));
        btnAddStage.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
        btnAddStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                addStageToTable();
            }
        });
        pStageButtons.add(btnAddStage);
    }

    //есть ли в таблице этапов несохраненные элементы
    private boolean isStageTableSaved() {
        if (tbStages.getData().size() != 0) {
            for (TStage stage: tbStages.getData()) {
                if (!stage.isSetId()) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Последний добавленный этап не сохранен! "
                        + "Сохраните его перед добавлением нового этапа "
                        + "или переходом на другую вкладку!",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            return true;
        }
        return true;
    }

    private boolean isStageTableReadyToOut() {
        if (tbStages.getData().size() != 0) {
            for (TStage stage: tbStages.getData()) {
                if (!stage.isSetId()) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Последний добавленный этап не сохранен! "
                        + "Сохраните его перед добавлением нового этапа "
                        + "или переходом на другую вкладку!",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    if (!isStageUpdateRequiredFieldsSet(stage)) {
                        return false;
                    }
                }
            }
        } else {
            return true;
//            правильный вариант этот, в последний момент попросили заменить верхним костылем
//            return false;
        }
        return true;
    }

    private boolean isStageDatesCorrect() {
        // костыль по заявкам 28, добавленный в последний момент. мне он тоже не нравится.
        // по уму нужен только нижний ретурн
        if (tbStages.getData().size() == 0) {
            return true;
        }
        return ((tbStages.getData().get(0).getDateStart() == priemInfo.getDatap())
            && (tbStages.getData().get(tbStages.getData().size() - 1).getDateEnd()
                == cdeZaklDate.getDate().getTime()));
    }

    private boolean isLastStageItemSetCorrectly() {
        if (tbStages.getData().size() != 0) {
            return isStageUpdateRequiredFieldsSet(
                tbStages.getData().get(tbStages.getData().size() - 1));
        } else {
            return true;
        }
    }

    private long calcCorrectStageDateStart() {
        if (tbStages.getData().size() == 0) {
            return priemInfo.getDatap();
        } else {
            return tbStages.getData().get(tbStages.getData().size() - 1).getDateEnd();
        }
    }

    private boolean isStageUpdateRequiredFieldsSet(final TStage stage) {
        if (isStageAddRequiredFieldsSet(stage)) {
            if (!stage.isSetDateEnd()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                    "Дата окончания этапа не заполнена! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetIshod()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Исход этапа не заполнен! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetResult()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                    "Результат этапа не заполнен! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetUkl()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                    "Укл этапа не заполнен! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (stage.getStage() == 1) {
                if (!stage.isSetTimeEnd()) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Время конца этапа не заполнено! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isStageAddRequiredFieldsSet(final TStage stage) {
        if (!stage.isSetDateStart()) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Дата начала этапа не заполнена! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!stage.isSetIdGosp()) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Идентификатор госпитализации этапа не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!stage.isSetStage()) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Этап не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (stage.getStage() == 1) {
                if (!stage.isSetTimeStart()) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Время начала этапа не заполнено! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        if (!stage.isSetMes()) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Код МЭС этапа не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void addStageToTable() {
        if ((patient != null) && (isStageTableSaved()) && (isLastStageItemSetCorrectly())) {
            TStage stage = new TStage();
            stage.setDateStart(calcCorrectStageDateStart());
            stage.setIdGosp(patient.getGospitalCod());
//                stage.setId(ClientHospital.tcl.addStage(stage));
            tbStages.addItem(stage);
//                tbStages.setData(
//                    ClientHospital.tcl.getStage(patient.getGospitalCod()));
            if (tbStages.getData().size() > 1) {
                tbStages.getData().get(
                    tbStages.getData().size() - 1).setMes(tbStages.getData().get(0).getMes());
            }
        }
    }

    private void addStageDeleteButton() {
        btnDeleteStage = new JButton();
        btnDeleteStage.setMaximumSize(new Dimension(50, 50));
        btnDeleteStage.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
        btnDeleteStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                deleteStageFromTable();
            }
        });
        pStageButtons.add(btnDeleteStage);
    }

    private void deleteStageFromTable() {
        try {
            if (tbStages.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    MainFrame.this, "Удалить этап лечения?",
                    "Удаление этапа лечения", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    ClientHospital.tcl.deleteStage(tbStages.getSelectedItem().getId());
                    tbStages.setData(
                        ClientHospital.tcl.getStage(patient.getGospitalCod()));
                }
                if (tbStages.getRowCount() > 0) {
                    tbStages.setRowSelectionInterval(tbStages.getRowCount() - 1,
                        tbStages.getRowCount() - 1);
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void addStageUpdateButton() {
        btnUpdateStage = new JButton();
        btnUpdateStage.setMaximumSize(new Dimension(50, 50));
        btnUpdateStage.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
        btnUpdateStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                saveStageToTable();
            }
        });
        pStageButtons.add(btnUpdateStage);
    }

    private void saveStageToTable() {
        try {
            if (tbStages.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    MainFrame.this, "Добавить информацию об этапе лечения?",
                    "Изменение этапа лечения", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    if (!tbStages.getSelectedItem().isSetId()) {
                        if (isStageAddRequiredFieldsSet(tbStages.getSelectedItem())) {
                            ClientHospital.tcl.addStage(tbStages.getSelectedItem());
                            tbStages.setData(
                                ClientHospital.tcl.getStage(patient.getGospitalCod()));
                        }
                    } else {
                        if (isStageAddRequiredFieldsSet(tbStages.getSelectedItem())) {
                            ClientHospital.tcl.updateStage(tbStages.getSelectedItem());
                            tbStages.setData(
                                ClientHospital.tcl.getStage(patient.getGospitalCod()));
                        }
                    }
                }
            }
        } catch (MesNotFoundException e) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Данного кода МЭС не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
    }

    private void fillStageTable() {
        if (patient != null) {
            try {
                List<TStage> tmpStages =
                    ClientHospital.tcl.getStage(patient.getGospitalCod());
                tbStages.setData(tmpStages);
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                ClientHospital.conMan.reconnect(e);
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////     Роды    ////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

//    private void setChildbirthPanel() {
// процедуру на сервер (если есть запись - вызвать, если нет - создать), определить npasp, ngosp        
//		rddin = new RdDinStruct();
//		try {
//			rddin = ClientHospital.tcl.getRdDinInfo(patient.getPatientId(), patient.gospitalCod);
//			SVes.setValue(rddin.getVes());
//			Soj.setValue(rddin.getOj());
//			Shdm.setValue(rddin.getHdm());
//            Schcc.setValue(rddin.getChcc());
//			if (rddin.isSetPozpl())
//			CBPoz.setSelectedPcod(rddin.getPozpl());
//			else CBPoz.setSelectedItem(null);
//			if (rddin.isSetPolpl())
//			CBPolpl.setSelectedPcod(rddin.getPolpl());
//			else CBPolpl.setSelectedItem(null);
//			if (rddin.isSetVidpl())
//			CBVid.setSelectedPcod(rddin.getVidpl());
//			else CBVid.setSelectedItem(null);
//			if (rddin.isSetSerd())
//			CBSerd.setSelectedPcod(rddin.getSerd());
//			else CBSerd.setSelectedItem(null);
//			if (rddin.isSetSerd1())
//			CBSerd1.setSelectedPcod(rddin.getSerd1());
//			else CBSerd1.setSelectedItem(null);
//			if (rddin.isSetPredpl())
//			CBPred.setSelectedPcod(rddin.getPredpl());
//			else CBPred.setSelectedItem(null);
//			TVes.setText(String.valueOf(rddin.getOj()*rddin.getHdm()));
//
//		} catch (KmiacServerException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		} catch (TException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		trdIshod = new TRdIshod();
//		try {
//			trdIshod = ClientHospital.tcl.getRdIshodInfo(patient.getPatientId(), patient.gospitalCod);
//			setDefaultValues();
//			} catch (KmiacServerException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (TException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		rdsl = new RdSlStruct();
//		try {
//			rdsl = ClientHospital.tcl.getRdSlInfo(patient.getPatientId());
//			Tdataosl.setDate(rdsl.getDataosl());
//			if (rdsl.getDataosl() == 0)
//			Tdataosl.setText(null);
//			Tdatam.setDate(rdsl.getDataM());
//			if (rdsl.getDataM() == 0)
//			Tdatam.setText(null);
//			Srod.setValue(rdsl.getKolrod());
//			Sber.setValue(rdsl.getShet());
//			Sdtr.setValue(rdsl.getDTroch());
//			Stvera.setValue(rdsl.getCvera());
//			Sdcr.setValue(rdsl.getDsp());
//			Scdiag.setValue(rdsl.getCdiagt());
//			Sdsp.setValue(rdsl.getDsr());
//			Scext.setValue(rdsl.getCext());
//		} catch (KmiacServerException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		} catch (TException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		};;;;;
//    }

	private void setDefaultValues() {
	try {
		System.out.println("начальные значения");		
//	System.out.println(Vvod.zapVr.id_pvizit);		
		TDatarod.setDate(trdIshod.getDatarod());
		if (trdIshod.getDatarod() == 0)
		TDatarod.setText(null);
		TPoln.setText(trdIshod.getPoln());
		TDet.setText(trdIshod.getDetmesto());
		TNash.setText(trdIshod.getDeyat());
		TVremp.setText(trdIshod.getVremp());
		TObol.setText(trdIshod.getObol()); 
		SDlina.setValue(trdIshod.getPupov());
		TOsob.setText(trdIshod.getOsobp());
		SKrov.setValue(trdIshod.getKrov());
		TPer1.setText(trdIshod.getPrr1());
		TPer2.setText(trdIshod.getPrr2());
		TPer3.setText(trdIshod.getPrr3());
		TGde.setText(trdIshod.getMesto());
		TShvat.setText(trdIshod.getShvat());
		TVod.setText(trdIshod.getVody());
		TKash.setText(trdIshod.getKashetv());
		TRod.setText(trdIshod.getDeyat());
		TMed.setText(trdIshod.getObezb());
		if (trdIshod.isSetPolpl())
			CBPolpl.setSelectedPcod(trdIshod.getPolpl());
		else
			CBPolpl.setSelectedItem(null);
		if (trdIshod.isSetPozpl())
			CBPoz.setSelectedPcod(trdIshod.getPozpl());
		else
			CBPoz.setSelectedItem(null);
		if (trdIshod.isSetVidpl())
			CBVid.setSelectedPcod(trdIshod.getVidpl());
		else
			CBVid.setSelectedItem(null);
		if (trdIshod.isSetSerd())
			CBSerd.setSelectedPcod(trdIshod.getSerd());
		else
			CBSerd.setSelectedItem(null);
		if (trdIshod.isSetSerd1())
			CBSerd1.setSelectedPcod(trdIshod.getSerd1());
		else
			CBSerd1.setSelectedItem(null);
		if (trdIshod.isSetPredpl())
			CBPred.setSelectedPcod(trdIshod.getPredpl());
		else
			CBPred.setSelectedItem(null);
		ChBpsi.setSelected(trdIshod.isPsih());
		if (trdIshod.isSetEff())
			CBEff.setSelectedPcod(trdIshod.getEff());
		else
			CBEff.setSelectedItem(null);
		if (trdIshod.isSetPosled())
			CBPosled.setSelectedPcod(trdIshod.getPosled());
		else
			CBPosled.setSelectedItem(null);
		if (trdIshod.isSetAkush())
			CBAkush.setSelectedPcod(trdIshod.getAkush());
		else
			CBAkush.setSelectedItem(null);
		if (trdIshod.isSetVrash())
			CBVrash.setSelectedPcod(trdIshod.getVrash());
		else
			CBVrash.setSelectedItem(null);
		if (trdIshod.isSetPrinyl())
			CBPrinial.setSelectedPcod(trdIshod.getPrinyl());
		else
			CBPrinial.setSelectedItem(null);
		if (trdIshod.isSetOsmposl())
			CBOsmotr.setSelectedPcod(trdIshod.getOsmposl());
		else
			CBOsmotr.setSelectedItem(null);
	} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(MainFrame.this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
	}
	
	}

    
///////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////Новорожденный//////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setChildrenPanel() {
    	this.pChildren = new Children(this.doctorAuth, this.patient);
        tabbedPane.addTab("Новорожденный", new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/childbirth.png")), pChildren, null);
	}
    
///////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////// Заключение ///////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setZaklPanel() {
        pZakl = new JPanel();
        tabbedPane.addTab("Заключение", new ImageIcon(
            MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/out.png")), pZakl, null);
        setZaklShablonComponents();
        setZaklTextAreas();
        setZaklComboboxes();
        setZaklButtons();
        setZaklPanelGroupLayout();
    }

    private void setZaklShablonComponents() {
        spZaklShablonNames = new JScrollPane();
        lZaklShablonNames = new ThriftIntegerClassifierList();
        lZaklShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lZaklShablonNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lZaklShablonNames.getSelectedValue() != null) {
                        try {
                            pasteZaklSelectedShablon(ClientHospital.tcl.getShablon(
                                lZaklShablonNames.getSelectedValue().pcod));
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
        spZaklShablonNames.setViewportView(lZaklShablonNames);

        tfZaklShablonNames = new CustomTextField(true, true, false);
        tfZaklShablonNames.setColumns(10);
        zaklSearchListener = new ShablonSearchListener(tfZaklShablonNames, lZaklShablonNames);
        tfZaklShablonNames.getDocument().addDocumentListener(zaklSearchListener);

        btnZaklShablonFind = new JButton("...");
        btnZaklShablonFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                frmShablon.showShablonForm(tfZaklShablonNames.getText(),
                    lZaklShablonNames.getSelectedValue());
                syncShablonList(frmShablon.getSearchString(), frmShablon.getShablon(),
                    zaklSearchListener, lZaklShablonNames);
                pasteZaklSelectedShablon(frmShablon.getShablon());
            }
        });
    }

    private void pasteZaklSelectedShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        clearZaklText();

        for (ShablonText shText : shablon.textList) {
            switch (shText.grupId) {
                case 12:
                    taRecomend.setText(shText.getText());
                    break;
                case 13:
                    taZakluch.setText(shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private void clearZaklText() {
        taRecomend.setText("");
        taZakluch.setText("");
        cdeZaklDate.setDate(new Date());
        cdeZaklTime.setTime(new Date());
    }

    private void setZaklTextAreas() {
        spRecomend = new JScrollPane();
        lblRecomend = new JLabel("Рекомендации");
        taRecomend = new JTextArea();
        taRecomend.setWrapStyleWord(true);
        taRecomend.setLineWrap(true);
        taRecomend.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spRecomend.setViewportView(taRecomend);

        spZakluch = new JScrollPane();
        lblZakluch = new JLabel("Состояние при выписке");
        taZakluch = new JTextArea();
        taZakluch.setLineWrap(true);
        taZakluch.setWrapStyleWord(true);
        taZakluch.setFont(new Font("Tahoma", Font.PLAIN, 11));
        spZakluch.setViewportView(taZakluch);

        lblIshod = new JLabel("Исход заболевания");
        lblResult = new JLabel("Результат лечения");

        lblZaklDate = new JLabel("Дата выписки");
        cdeZaklDate = new CustomDateEditor();
        cdeZaklDate.setColumns(10);

        lblZaklTime = new JLabel("Время выписки");
        cdeZaklTime = new CustomTimeEditor();
        cdeZaklTime.setColumns(10);

        lblPatalogoAnDiagHeader = new JLabel("Паталогоанатомический диагноз");
        tfPatalogoAnDiagName = new JTextField();
        tfPatalogoAnDiagName.setColumns(10);
        tfPatalogoAnDiagPcod = new JTextField();
        tfPatalogoAnDiagPcod.setColumns(10);
        btnPatalogoAnDiag = new JButton("Выбрать");
        btnPatalogoAnDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                StringClassifier patDiag = ClientHospital.conMan.showMkbTreeForm("Диагноз", "");
                if (patDiag != null) {
                    tfPatalogoAnDiagPcod.setText(patDiag.getPcod());
                    tfPatalogoAnDiagName.setText(patDiag.getName());
                }
            }
        });
        setPatalAnatComponentsVisble(false);

        lblZaklDiag = new JLabel("Заключительный  диагноз");

        tfZaklDiagPcod = new JTextField();
        tfZaklDiagPcod.setColumns(10);

        tfZaklDiagName = new JTextField();
        tfZaklDiagName.setColumns(10);

        btnZaklDiag = new JButton("Выбрать");
        btnZaklDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                StringClassifier patDiag = ClientHospital.conMan.showMkbTreeForm("Диагноз", "");
                if (patDiag != null) {
                    tfZaklDiagPcod.setText(patDiag.getPcod());
                    tfZaklDiagName.setText(patDiag.getName());
                }
            }
        });
    }

    private void setPatalAnatComponentsVisble(final boolean isVisible) {
        lblPatalogoAnDiagHeader.setVisible(isVisible);
        tfPatalogoAnDiagName.setVisible(isVisible);
        btnPatalogoAnDiag.setVisible(isVisible);
        tfPatalogoAnDiagPcod.setVisible(isVisible);
    }

    private void setZaklComboboxes() {
        lblVidPom = new JLabel("Вид помощи");

        cbxVidPom = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_vp1);

        cbxDefect = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_def);

        lblDefect = new JLabel("Дефекты догосп. этапа");

        lblUkl = new JLabel("УКЛ");

        tfUkl = new JTextField();
        tfUkl.setColumns(10);
        cbxIshod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxIshod.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if (((IntegerClassifier) e.getItem()).getPcod() == 3) {
                    cbxAnotherOtd.setVisible(true);
                    setPatalAnatComponentsVisble(false);
                } else if (((IntegerClassifier) e.getItem()).getPcod() == 2) {
                    setPatalAnatComponentsVisble(true);
                    cbxAnotherOtd.setVisible(false);
                } else {
                    cbxAnotherOtd.setVisible(false);
                    setPatalAnatComponentsVisble(false);
                }
            }
        });
        cbxResult = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);

        lblVidOpl = new JLabel("Вид оплаты");
        cbxVidOpl =
            new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_opl);

        cbxAnotherOtd = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxAnotherOtd.setVisible(false);



        lblZaklDiagStep = new JLabel("Степень тяжести:");

        rdbtnZaklDiagSrT = new JRadioButton("Средняя");
        rdbtnZaklDiagTT = new JRadioButton("Тяжелая");
        ButtonGroup btngDiagRadioT = new ButtonGroup();
        btngDiagRadioT.add(rdbtnZaklDiagSrT);
        btngDiagRadioT.add(rdbtnZaklDiagTT);
    }

    private void setZaklButtons() {
        btnSaveZakl = new JButton("Выписать");
        btnSaveZakl.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (isStagesCorrect() && isStageDatesCorrect()) {
                        if (isPatientOut()) {
                            if (isAllRequiredOutFieldsSet()) {
                                Zakl tmpZakl = new Zakl();
                                tmpZakl.setRecom(taRecomend.getText());
                                tmpZakl.setSostv(taZakluch.getText());
                                tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                                tmpZakl.setResult(cbxResult.getSelectedPcod());
                                tmpZakl.setDatav(cdeZaklDate.getDate().getTime());
                                tmpZakl.setVremv(cdeZaklTime.getTime().getTime());
                                tmpZakl.setVidOpl(cbxVidOpl.getSelectedPcod());
                                tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(patient.getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl);
                                JOptionPane.showMessageDialog(MainFrame.this,
                                    "Пациент успешно выписан", "Выписка пациента",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else if (isPatientDead()) {
                            if (isAllRequiredDeadFieldsSet()) {
                                Zakl tmpZakl = new Zakl();
                                tmpZakl.setRecom(taRecomend.getText());
                                tmpZakl.setSostv(taZakluch.getText());
                                tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                                tmpZakl.setDatav(cdeZaklDate.getDate().getTime());
                                tmpZakl.setVremv(cdeZaklTime.getTime().getTime());
                                tmpZakl.setVidOpl(cbxVidOpl.getSelectedPcod());
                                tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(patient.getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl);
                                JOptionPane.showMessageDialog(MainFrame.this,
                                    "Пациент успешно выписан", "Выписка пациента",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else if (isPatientMoved()) {
                            if (isAllRequiredMovedFieldsSet()) {
                                Zakl tmpZakl = new Zakl();
                                tmpZakl.setRecom(taRecomend.getText());
                                tmpZakl.setSostv(taZakluch.getText());
                                tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                                tmpZakl.setResult(cbxResult.getSelectedPcod());
                                tmpZakl.setNewOtd(cbxAnotherOtd.getSelectedPcod());
                                tmpZakl.setDatav(cdeZaklDate.getDate().getTime());
                                tmpZakl.setVremv(cdeZaklTime.getTime().getTime());
                                tmpZakl.setVidOpl(cbxVidOpl.getSelectedPcod());
                                tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(patient.getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl);
                                JOptionPane.showMessageDialog(MainFrame.this,
                                    "Пациент успешно выписан", "Выписка пациента",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this,
                                "Не выбран пациент или не установлен исход. "
                                + "Информация не сохранена", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Этапы лечения заполнены некорректно! Выписка невозможна!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (KmiacServerException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка при выписке пациента. Информация не сохранена", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка при выписке пациента. Информация не сохранена", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                    ClientHospital.conMan.reconnect(e1);
                }
            }
        });
    }

    private boolean isStagesCorrect() {
        return isStageTableReadyToOut();
    }

    private boolean isPrimaryOutValueSet() {
        return (patient != null) && (cbxIshod.getSelectedItem() != null);
    }

    private boolean isPatientOut() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() != 2) && (cbxIshod.getSelectedPcod() != 3);
        } else {
            return false;
        }
    }

    private boolean isPatientDead() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() == 2);
        } else {
            return false;
        }
    }

    private boolean isPatientMoved() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() == 3);
        } else {
            return false;
        }
    }

    private boolean isAllRequiredOutFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty()) || (tfZaklDiagName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxResult.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран результат лечения. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрана дата выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрано время выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isAllRequiredDeadFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty())
                || (tfZaklDiagName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
//FIXME
//        if ((tfPatalogoAnDiagPcod.getText().isEmpty())
//                || (tfPatalogoAnDiagName.getText().isEmpty())) {
//            JOptionPane.showMessageDialog(MainFrame.this,
//                "Не выбран паталогоанатомический диагноз. Информация не сохранена", "Ошибка",
//                JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрана дата смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрано время смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isAllRequiredMovedFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид помощи", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty())
                || (tfZaklDiagName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрана дата перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрано время перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxResult.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбран результат лечения. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxAnotherOtd.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(MainFrame.this,
                "Не выбрано отделения для перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
                                .addComponent(tfChamber, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(tfGender, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(tfNumberOfDesiaseHistory, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                            .addGap(31)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblSurname)
                                .addComponent(lblBirthdate)
                                .addComponent(lblStatus))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(tfBirthdate, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(tfSurname, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(tfStatus, 0, 225, Short.MAX_VALUE))
                            .addGap(34)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblName)
                                .addComponent(lblOms)
                                .addComponent(lblWork))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPersonalInfo.createSequentialGroup()
                                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                        .addGroup(glPersonalInfo.createSequentialGroup()
                                            .addComponent(tfName, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                            .addGap(40))
                                        .addComponent(tfOms, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                                    .addGap(43)
                                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblMiddlename)
                                        .addComponent(lblDms)))
                                .addGroup(glPersonalInfo.createSequentialGroup()
                                    .addComponent(tfWork, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                    .addGap(99))))
                        .addGroup(glPersonalInfo.createSequentialGroup()
                            .addComponent(tfRealAddress, GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                            .addGap(79))
                        .addGroup(glPersonalInfo.createSequentialGroup()
                            .addComponent(tfRegistrationAddress, GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                            .addGap(79)))
                    .addGap(18)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnUpdateChamber, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfDms)
                        .addComponent(tfMiddlename, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                    .addGap(0))
        );
        glPersonalInfo.setVerticalGroup(
            glPersonalInfo.createParallelGroup(Alignment.LEADING)
                .addGroup(glPersonalInfo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNumberDesiaseHistory)
                        .addComponent(tfNumberOfDesiaseHistory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSurname)
                        .addComponent(tfSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblName)
                        .addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMiddlename)
                        .addComponent(tfMiddlename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblGender)
                        .addComponent(tfGender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBirthdate)
                        .addComponent(tfBirthdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblOms)
                        .addComponent(tfOms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDms)
                        .addComponent(tfDms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblChamber)
                        .addComponent(tfChamber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStatus)
                        .addComponent(tfStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblWork)
                        .addComponent(tfWork, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateChamber))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRegistrationAddress)
                        .addComponent(tfRegistrationAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPersonalInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRealAddress)
                        .addComponent(tfRealAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        pPersonalInfo.setLayout(glPersonalInfo);
    }

    private void setZaklPanelGroupLayout() {
        GroupLayout glPZakl = new GroupLayout(pZakl);
        glPZakl.setHorizontalGroup(
            glPZakl.createParallelGroup(Alignment.LEADING)
                .addGroup(glPZakl.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPZakl.createParallelGroup(Alignment.TRAILING, false)
                            .addGroup(glPZakl.createSequentialGroup()
                                .addComponent(lblZaklDate)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(cdeZaklDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(lblZaklTime)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(cdeZaklTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(glPZakl.createSequentialGroup()
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                                    .addComponent(tfZaklDiagPcod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblZaklDiagStep))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                                    .addGroup(glPZakl.createSequentialGroup()
                                        .addComponent(rdbtnZaklDiagSrT)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(rdbtnZaklDiagTT))
                                    .addGroup(glPZakl.createSequentialGroup()
                                        .addComponent(tfZaklDiagName, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
                                        .addGap(55)
                                        .addComponent(btnZaklDiag, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))))
                            .addComponent(cbxIshod, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxAnotherOtd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(glPZakl.createSequentialGroup()
                                .addComponent(tfPatalogoAnDiagPcod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(tfPatalogoAnDiagName, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPatalogoAnDiag, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbxResult, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveZakl, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                            .addGroup(glPZakl.createSequentialGroup()
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblVidPom)
                                    .addComponent(cbxVidPom, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblUkl)
                                    .addComponent(tfUkl, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(cbxVidOpl, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblVidOpl))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                                    .addComponent(cbxDefect, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDefect)))
                            .addComponent(spZakluch, 0, 0, Short.MAX_VALUE)
                            .addComponent(spRecomend, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
                        .addComponent(lblResult)
                        .addComponent(lblPatalogoAnDiagHeader)
                        .addComponent(lblIshod)
                        .addComponent(lblZaklDiag)
                        .addComponent(lblZakluch)
                        .addComponent(lblRecomend))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPZakl.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPZakl.createSequentialGroup()
                            .addComponent(tfZaklShablonNames, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnZaklShablonFind, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addComponent(spZaklShablonNames, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)))
        );
        glPZakl.setVerticalGroup(
            glPZakl.createParallelGroup(Alignment.LEADING)
                .addGroup(glPZakl.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPZakl.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPZakl.createSequentialGroup()
                            .addGroup(glPZakl.createParallelGroup(Alignment.BASELINE)
                                .addComponent(tfZaklShablonNames, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblRecomend)
                                .addComponent(btnZaklShablonFind))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spZaklShablonNames, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                        .addGroup(glPZakl.createSequentialGroup()
                            .addGap(20)
                            .addComponent(spRecomend, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblZakluch)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spZakluch, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPZakl.createParallelGroup(Alignment.TRAILING)
                                .addGroup(glPZakl.createSequentialGroup()
                                    .addComponent(lblVidPom)
                                    .addGap(1)
                                    .addComponent(cbxVidPom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(glPZakl.createSequentialGroup()
                                    .addComponent(lblUkl)
                                    .addGap(1)
                                    .addComponent(tfUkl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(glPZakl.createSequentialGroup()
                                    .addComponent(lblVidOpl)
                                    .addGap(1)
                                    .addComponent(cbxVidOpl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGroup(glPZakl.createSequentialGroup()
                                    .addComponent(lblDefect)
                                    .addGap(1)
                                    .addComponent(cbxDefect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblZaklDiag)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPZakl.createParallelGroup(Alignment.BASELINE)
                                .addComponent(tfZaklDiagPcod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfZaklDiagName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnZaklDiag))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPZakl.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblZaklDiagStep)
                                .addComponent(rdbtnZaklDiagSrT)
                                .addComponent(rdbtnZaklDiagTT))
                            .addGap(18)
                            .addComponent(lblIshod)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(cbxIshod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(cbxAnotherOtd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblPatalogoAnDiagHeader)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPZakl.createParallelGroup(Alignment.BASELINE)
                                .addComponent(tfPatalogoAnDiagName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfPatalogoAnDiagPcod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPatalogoAnDiag))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblResult)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(cbxResult, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPZakl.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblZaklDate)
                                .addComponent(cdeZaklDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblZaklTime)
                                .addComponent(cdeZaklTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnSaveZakl)))
                    .addContainerGap(13, GroupLayout.PREFERRED_SIZE))
        );
        pZakl.setLayout(glPZakl);
    }
}

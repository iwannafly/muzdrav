package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.Lek;
import ru.nkz.ivcgzo.thriftMedication.Patient;

public class MedicationOptionsFrame extends JDialog {

    private static final long serialVersionUID = 2753395204214670046L;
    private CustomDateEditor cdeDateFrom;
    private CustomDateEditor cdeDateTo;
    private JTextField tfIntakePerDay;
    private JTextField tfChargeOff;
    private JPanel pHeader;
    private JLabel lblMedicationName;
    private JLabel lblMedicationForm;
    private Component vsFirstInterval;
    private JPanel pDates;
    private JLabel lblDateFrom;
    private JLabel lblDateTo;
    private JPanel pOptions;
    private JLabel lblPeriod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbPeriod;
    private JLabel lblInputMethod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxInputMethod;
    private Component vsSecondInterval;
    private JPanel pDailyRules;
    private JLabel lblDose;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDoseEdd;
    private JLabel lblIntakePerDay;
    private JLabel lblChargeOff;
    private JLabel lblComment;
    private JCheckBox chbxReestr;
    private Component vsThirdInterval;
    private JPanel pButtons;
    private JButton btnAdd;
    private JButton btnCancel;
    private JScrollPane spComment;
    private JTextArea taComment;
    private JPanel pMain;
    private Component hsSecond;
    private Component hsThird;
    private Component hsFouth;
    private Component hsFirst;
    private Component hsFifth;
    private IntegerClassifier curMedication;
    private IntegerClassifier curMedicationForm;
    private Patient patient;
    private Box hzbDose;
    private JTextField tfDose;

    public MedicationOptionsFrame() {
        initialization();
    }

    private void initialization() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setFrameSize();
        setUndecorated(true);

        addMainPanel();
    }

    private void setFrameSize() {
        setPreferredSize(new Dimension(400, 500));
        setSize(new Dimension(395, 581));
        setLocationRelativeTo(null);
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
            (int) ((screenSize.getHeight() - getHeight()) / 2));
    }

    private void addMainPanel() {
        pMain = new JPanel();
        pMain.setBorder(new EtchedBorder(EtchedBorder.RAISED,
            new Color(112, 128, 144), new Color(0, 0, 0)));
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addHeaderPanel();
        addDatesPanel();
        addOptionsPanel();
        addDailyRules();
        addButtonsPanel();
    }

    private void addHeaderPanel() {
        pHeader = new JPanel();
        pMain.add(pHeader);
        pHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
        pHeader.setLayout(new BoxLayout(pHeader, BoxLayout.Y_AXIS));

        lblMedicationName = new JLabel("Название лекарства:");
        lblMedicationName.setForeground(new Color(220, 20, 60));
        lblMedicationName.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblMedicationName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMedicationName.setMaximumSize(new Dimension(600, 30));
        pHeader.add(lblMedicationName);

        lblMedicationForm = new JLabel("Форма выпуска:");
        lblMedicationForm.setForeground(new Color(165, 42, 42));
        lblMedicationForm.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
        lblMedicationForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMedicationForm.setMaximumSize(new Dimension(600, 30));
        pHeader.add(lblMedicationForm);

        vsFirstInterval = Box.createVerticalStrut(20);
        pHeader.add(vsFirstInterval);
    }

    private void addDatesPanel() {
        pDates = new JPanel();
        pDates.setMaximumSize(new Dimension(32767, 25));
        pMain.add(pDates);
        pDates.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDates.setLayout(new BoxLayout(pDates, BoxLayout.X_AXIS));

        hsFirst = Box.createHorizontalStrut(20);
        hsFirst.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsFirst);

        lblDateFrom = new JLabel(" С");
        lblDateFrom.setMaximumSize(new Dimension(10, 20));
        pDates.add(lblDateFrom);

        hsSecond = Box.createHorizontalStrut(20);
        pDates.add(hsSecond);

        cdeDateFrom = new CustomDateEditor();
        cdeDateFrom.setMaximumSize(new Dimension(2147483647, 20));
        pDates.add(cdeDateFrom);
        cdeDateFrom.setColumns(10);

        hsThird = Box.createHorizontalStrut(20);
        hsThird.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsThird);

        lblDateTo = new JLabel(" По");
        lblDateTo.setMaximumSize(new Dimension(16, 20));
        pDates.add(lblDateTo);

        hsFouth = Box.createHorizontalStrut(20);
        hsFouth.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsFouth);

        cdeDateTo = new CustomDateEditor();
        cdeDateTo.setMaximumSize(new Dimension(2147483647, 20));
        pDates.add(cdeDateTo);
        cdeDateTo.setColumns(10);

        hsFifth = Box.createHorizontalStrut(20);
        hsFifth.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsFifth);
    }

    private void addOptionsPanel() {
        pOptions = new JPanel();
        pMain.add(pOptions);
        pOptions.setBorder(new LineBorder(new Color(0, 0, 0)));
        pOptions.setLayout(new BoxLayout(pOptions, BoxLayout.Y_AXIS));

        addPeriod();
        addInputMethod();
    }

    private void addPeriod() {
        lblPeriod = new JLabel("Периодичность приёма:");
        pOptions.add(lblPeriod);

        cmbPeriod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_period);
        cmbPeriod.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cmbPeriod);
    }

    private void addInputMethod() {
        lblInputMethod = new JLabel("Способ ввода:");
        pOptions.add(lblInputMethod);

        cbxInputMethod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_svl);
        cbxInputMethod.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cbxInputMethod);

        vsSecondInterval = Box.createVerticalStrut(20);
        pOptions.add(vsSecondInterval);
    }

    private void addDailyRules() {
        pDailyRules = new JPanel();
        pMain.add(pDailyRules);
        pDailyRules.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDailyRules.setLayout(new BoxLayout(pDailyRules, BoxLayout.Y_AXIS));

        addDose();
        addIntakePerDay();
        addChargeOff();
        addComment();
        addReestr();
    }

    private void addDose() {
        lblDose = new JLabel("Количество на приём:");
        lblDose.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblDose);
        
        hzbDose = Box.createHorizontalBox();
        pDailyRules.add(hzbDose);
        
        tfDose = new JTextField();
        tfDose.setMinimumSize(new Dimension(200, 20));
        tfDose.setPreferredSize(new Dimension(200, 20));
        tfDose.setMaximumSize(new Dimension(200, 20));
        hzbDose.add(tfDose);
        tfDose.setColumns(10);

        cbxDoseEdd = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_edd);
        cbxDoseEdd.setPreferredSize(new Dimension(100, 20));
        cbxDoseEdd.setMinimumSize(new Dimension(100, 20));
        hzbDose.add(cbxDoseEdd);
        cbxDoseEdd.setMaximumSize(new Dimension(100, 20));
    }

    private void addIntakePerDay() {
        lblIntakePerDay = new JLabel("Число в день:");
        lblIntakePerDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblIntakePerDay);

        tfIntakePerDay = new JTextField();
        tfIntakePerDay.setMaximumSize(new Dimension(2147483647, 20));
        pDailyRules.add(tfIntakePerDay);
        tfIntakePerDay.setColumns(10);
    }

    private void addChargeOff() {
        lblChargeOff = new JLabel("Списание:");
        lblChargeOff.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblChargeOff);

        tfChargeOff = new JTextField();
        tfChargeOff.setMaximumSize(new Dimension(2147483647, 20));
        pDailyRules.add(tfChargeOff);
        tfChargeOff.setColumns(10);
    }

    private void addComment() {
        lblComment = new JLabel("Примечания:");
        lblComment.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblComment);

        spComment = new JScrollPane();
        pDailyRules.add(spComment);

        taComment = new JTextArea();
        taComment.setFont(new Font("Tahoma", Font.PLAIN, 11));
        taComment.setWrapStyleWord(true);
        taComment.setLineWrap(true);
        spComment.setViewportView(taComment);
    }

    private void addReestr() {
        chbxReestr = new JCheckBox("Вкл. в реестр на оплату");
        chbxReestr.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(chbxReestr);

        vsThirdInterval = Box.createVerticalStrut(20);
        pDailyRules.add(vsThirdInterval);
    }

    private void addButtonsPanel() {
        pButtons = new JPanel();
        pMain.add(pButtons);
        pButtons.setBorder(new LineBorder(new Color(0, 0, 0)));

        addButtonAdd();
        addButtonCancel();
    }

    private void addButtonAdd() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        btnAdd = new JButton("Выбрать");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Lek newMedication = new Lek();
                newMedication.setIdGosp(patient.getIdGosp());
                newMedication.setVrach(ClientMedication.authInfo.getPcod());
                if (curMedication.getPcod() >= 0) {
                	newMedication.unsetLek_name();
                	newMedication.setKlek(curMedication.getPcod());
                } else {
                	newMedication.unsetKlek();
                	newMedication.setLek_name(curMedication.getName());
                }
                if (curMedicationForm != null)
                	newMedication.setFlek(curMedicationForm.getName());
                if (!tfDose.getText().isEmpty()) {
                	newMedication.setDoza(Integer.valueOf(tfDose.getText()));
                }
                newMedication.setEd(cbxDoseEdd.getSelectedPcod());
                newMedication.setKomm(taComment.getText());
                newMedication.setSposv(cbxInputMethod.getSelectedPcod());
                newMedication.setSpriem(cmbPeriod.getSelectedPcod());
                if (!tfIntakePerDay.getText().isEmpty()) {
                	newMedication.setPereod(Integer.valueOf(tfIntakePerDay.getText()));
                }
                newMedication.setDatae(cdeDateTo.getDate().getTime());
                newMedication.setDatan(cdeDateFrom.getDate().getTime());
                newMedication.setDataz(System.currentTimeMillis());
                try {
                	newMedication.setNlek(ClientMedication.tcl.addLek(newMedication));
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    e1.printStackTrace();
                    ClientMedication.conMan.reconnect(e1);
                }
                MedicationOptionsFrame.this.dispatchEvent(new WindowEvent(
                    MedicationOptionsFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        btnAdd.setMinimumSize(new Dimension(200, 23));
        btnAdd.setMaximumSize(new Dimension(200, 23));
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        pButtons.add(btnAdd);
    }

    private void addButtonCancel() {
        btnCancel = new JButton("Отмена");
        btnCancel.setMinimumSize(new Dimension(200, 23));
        btnCancel.setMaximumSize(new Dimension(200, 23));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicationOptionsFrame.this.dispatchEvent(new WindowEvent(
                    MedicationOptionsFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        pButtons.add(btnCancel);
    }

    public void prepareForm(IntegerClassifier medication,
            IntegerClassifier medicationForm, Patient inPatient) {
        setHeader(medication, medicationForm);
        clearTextFields();
        patient = inPatient;
    }

    private void clearTextFields() {
        cmbPeriod.setSelectedIndex(-1);
        cbxDoseEdd.setSelectedIndex(-1);
        cbxInputMethod.setSelectedIndex(-1);
        chbxReestr.setEnabled(false);
        tfChargeOff.setText("");
        tfDose.setText("");
        tfIntakePerDay.setText("");
    }

    private void setHeader(IntegerClassifier medication,
            IntegerClassifier medicationForm) {
        if (medication != null) {
            curMedication = medication;
            lblMedicationName.setText(curMedication.getName());
            if (medicationForm != null) {
                curMedicationForm = medicationForm;
                lblMedicationForm.setText(curMedicationForm.getName());
            }
        }
    }

}

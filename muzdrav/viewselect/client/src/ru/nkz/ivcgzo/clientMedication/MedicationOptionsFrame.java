package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

public class MedicationOptionsFrame extends JDialog {

    private static final long serialVersionUID = 2753395204214670046L;
    private JTextField tfDateFrom;
    private JTextField tfDateTo;
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
    private JComboBox cmbPeriod;
    private JLabel lblOsobNazn;
    private JComboBox cbxOsobNazn;
    private JLabel lblInputMethod;
    private JComboBox cbxInputMethod;
    private Component vsSecondInterval;
    private JPanel pDailyRules;
    private JLabel lblDose;
    private JComboBox cbxDose;
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

    public MedicationOptionsFrame() {
        initialization();
    }

    private void initialization() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
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
        lblMedicationName.setAlignmentX(0.5f);
        lblMedicationName.setMaximumSize(new Dimension(600, 30));
        pHeader.add(lblMedicationName);

        lblMedicationForm = new JLabel("Форма выпуска:");
        lblMedicationForm.setForeground(new Color(165, 42, 42));
        lblMedicationForm.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
        lblMedicationForm.setAlignmentX(0.5f);
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

        tfDateFrom = new JTextField();
        tfDateFrom.setMaximumSize(new Dimension(2147483647, 20));
        pDates.add(tfDateFrom);
        tfDateFrom.setColumns(10);

        hsThird = Box.createHorizontalStrut(20);
        hsThird.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsThird);

        lblDateTo = new JLabel(" По");
        lblDateTo.setMaximumSize(new Dimension(16, 20));
        pDates.add(lblDateTo);

        hsFouth = Box.createHorizontalStrut(20);
        hsFouth.setMaximumSize(new Dimension(20, 20));
        pDates.add(hsFouth);

        tfDateTo = new JTextField();
        tfDateTo.setMaximumSize(new Dimension(2147483647, 20));
        pDates.add(tfDateTo);
        tfDateTo.setColumns(10);

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
        addOsobNazn();
        addInputMethod();
    }

    private void addPeriod() {
        lblPeriod = new JLabel("Периодичность приёма:");
        pOptions.add(lblPeriod);

        cmbPeriod = new JComboBox();
        cmbPeriod.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cmbPeriod);
    }

    private void addOsobNazn() {
        lblOsobNazn = new JLabel("Особые назначения:");
        pOptions.add(lblOsobNazn);

        cbxOsobNazn = new JComboBox();
        cbxOsobNazn.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cbxOsobNazn);
    }

    private void addInputMethod() {
        lblInputMethod = new JLabel("Способ ввода:");
        pOptions.add(lblInputMethod);

        cbxInputMethod = new JComboBox();
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
        pDailyRules.add(lblDose);

        cbxDose = new JComboBox();
        cbxDose.setMaximumSize(new Dimension(32767, 20));
        pDailyRules.add(cbxDose);
    }

    private void addIntakePerDay() {
        lblIntakePerDay = new JLabel("Число в день:");
        pDailyRules.add(lblIntakePerDay);

        tfIntakePerDay = new JTextField();
        tfIntakePerDay.setMaximumSize(new Dimension(2147483647, 20));
        pDailyRules.add(tfIntakePerDay);
        tfIntakePerDay.setColumns(10);
    }

    private void addChargeOff() {
        lblChargeOff = new JLabel("Списание:");
        pDailyRules.add(lblChargeOff);

        tfChargeOff = new JTextField();
        tfChargeOff.setMaximumSize(new Dimension(2147483647, 20));
        pDailyRules.add(tfChargeOff);
        tfChargeOff.setColumns(10);
    }

    private void addComment() {
        lblComment = new JLabel("Примечания:");
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
        btnAdd.setMinimumSize(new Dimension(200, 23));
        btnAdd.setMaximumSize(new Dimension(200, 23));
        btnAdd.setAlignmentX(0.5f);
        pButtons.add(btnAdd);
    }

    private void addButtonCancel() {
        btnCancel = new JButton("Отмена");
        btnCancel.setMinimumSize(new Dimension(200, 23));
        btnCancel.setMaximumSize(new Dimension(200, 23));
        btnCancel.setAlignmentX(0.5f);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicationOptionsFrame.this.dispatchEvent(new WindowEvent(
                    MedicationOptionsFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        pButtons.add(btnCancel);
    }

    public void setHeader(IntegerClassifier medication,
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

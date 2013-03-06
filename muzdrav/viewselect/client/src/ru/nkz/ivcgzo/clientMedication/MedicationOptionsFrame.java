package ru.nkz.ivcgzo.clientMedication;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.Lek;
import ru.nkz.ivcgzo.thriftMedication.Patient;

public class MedicationOptionsFrame extends JDialog {

    private static final long serialVersionUID = 2753395204214670046L;
    private Patient patient;
    private IntegerClassifier curMedication;
    private IntegerClassifier curMedicationForm;
    private StringClassifier curDiag;
    private CustomDateEditor cdeDateFrom;
    private CustomDateEditor cdeDateTo;
    private JSpinner spnrIntakePerDay;
    private JSpinner spnrDose;
    private JPanel pMain;
    private JPanel pHeader;
    private JPanel pDates;
    private JPanel pOptions;
    private JPanel pDailyRules;
    private JPanel pButtons;
    private JLabel lblMedicationName;
    private JLabel lblMedicationForm;
    private JLabel lblDateFrom;
    private JLabel lblDateTo;
    private JLabel lblPeriod;
    private JLabel lblInputMethod;
    private JLabel lblDose;
    private JLabel lblIntakePerDay;
    private JLabel lblPayment;
    private JLabel lblComment;
    private JLabel lblDiagnosis;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbPeriod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxInputMethod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDoseEdd;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxPayment;
    private JButton btnAdd;
    private JButton btnCancel;
	private JButton btnAddDiag;
    private JScrollPane spComment;
    private JTextArea taComment;
	private JTextField tfDiagCode;
    private Component vsFirstInterval;
    private Component vsSecondInterval;
    private Component vsThirdInterval;
    private Component vsFourInterval;
    private Component hsSecond;
    private Component hsThird;
    private Component hsFouth;
    private Component hsFirst;
    private Component hsFifth;
    private Component hsDiagFirstInterval;
    private Component hsDiagSecondInterval;
    private Box hzbDose;
    private Box hzbDiagnosis;

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
        pMain.setBorder(
        		new EtchedBorder(
        				EtchedBorder.RAISED,
        				new Color(112, 128, 144),
        				new Color(0, 0, 0)
        		)
        );
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
        addDiagnosisHeader();

        vsSecondInterval = Box.createVerticalStrut(10);
        pOptions.add(vsSecondInterval);
    }

    private void addPeriod() {
        lblPeriod = new JLabel("Периодичность приёма:");
        lblPeriod.setAlignmentX(Component.CENTER_ALIGNMENT);
        pOptions.add(lblPeriod);

        cmbPeriod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_period);
        cmbPeriod.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cmbPeriod);
    }

    private void addInputMethod() {
        lblInputMethod = new JLabel("Способ ввода:");
        lblInputMethod.setAlignmentX(Component.CENTER_ALIGNMENT);
        pOptions.add(lblInputMethod);

        cbxInputMethod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_svl);
        cbxInputMethod.setMaximumSize(new Dimension(32767, 20));
        pOptions.add(cbxInputMethod);
    }
    
    private void addDiagnosisHeader() {
    	vsFourInterval = Box.createVerticalStrut(10);
    	pOptions.add(vsFourInterval);
        
        hzbDiagnosis = Box.createHorizontalBox();
        
    	lblDiagnosis = new JLabel("Диагноз:");
    	lblDiagnosis.setAlignmentX(Component.CENTER_ALIGNMENT);
    	hzbDiagnosis.add(lblDiagnosis);
    	
    	hsDiagFirstInterval = Box.createHorizontalStrut(15);
    	hsDiagFirstInterval.setMaximumSize(new Dimension(15, 0));
    	hzbDiagnosis.add(hsDiagFirstInterval);
        
        addDiagnosisInput();
        
        pOptions.add(hzbDiagnosis);
    }
    
    private void addDiagnosisInput() {
        tfDiagCode = new JTextField(7);
        tfDiagCode.setEditable(false);
        tfDiagCode.setPreferredSize(new Dimension(70, 20));
        tfDiagCode.setMaximumSize(new Dimension(70, 20));
        hzbDiagnosis.add(tfDiagCode);

        hsDiagSecondInterval = Box.createHorizontalStrut(15);
        hsDiagSecondInterval.setMaximumSize(new Dimension(15, 0));
        hzbDiagnosis.add(hsDiagSecondInterval);
        
        btnAddDiag = new JButton("Выбрать");
        btnAddDiag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                curDiag = ClientMedication.conMan.showMkbTreeForm("Диагноз", "");
                if (curDiag != null)
                    tfDiagCode.setText(curDiag.getPcod());
            	setVisible(true);
            }
        });
        hzbDiagnosis.add(btnAddDiag);
    }

    private void addDailyRules() {
        pDailyRules = new JPanel();
        pMain.add(pDailyRules);
        pDailyRules.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDailyRules.setLayout(new BoxLayout(pDailyRules, BoxLayout.Y_AXIS));

        addDose();
        addIntakePerDay();
        addComment();
        addPayment();
        
        vsThirdInterval = Box.createVerticalStrut(15);
        pDailyRules.add(vsThirdInterval);
    }

    private void addDose() {
        lblDose = new JLabel("Количество на приём:");
        lblDose.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblDose);
        
        hzbDose = Box.createHorizontalBox();
        pDailyRules.add(hzbDose);
        
        spnrDose = new JSpinner();
        spnrDose.setModel(
        		new SpinnerNumberModel(
        				new Integer(0), new Integer(0), null, new Integer(1)));
        spnrDose.setMinimumSize(new Dimension(200, 20));
        spnrDose.setPreferredSize(new Dimension(200, 20));
        spnrDose.setMaximumSize(new Dimension(200, 20));
        hzbDose.add(spnrDose);

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

        spnrIntakePerDay = new JSpinner();
        spnrIntakePerDay.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        spnrIntakePerDay.setMaximumSize(new Dimension(2147483647, 20));
        pDailyRules.add(spnrIntakePerDay);
    }

    private void addPayment() {
        lblPayment = new JLabel("Средства оплаты:");
        lblPayment.setAlignmentX(Component.CENTER_ALIGNMENT);
        pDailyRules.add(lblPayment);

        cbxPayment = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_opl1);
        cbxPayment.setMaximumSize(new Dimension(32767, 20));
        pDailyRules.add(cbxPayment);
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

    private void addButtonsPanel() {
        pButtons = new JPanel();
        pMain.add(pButtons);
        pButtons.setBorder(new LineBorder(new Color(0, 0, 0)));

        addButtonAdd();
        addButtonCancel();
    }
    
    /**
     * Загрузка данных из интерфейса в заданную переменную
     * лекарственного назначения
     * @param newMedication Лекарственное назначение
     * @throws NullPointerException исключение в случае
     * передачи нулевой переменной
     * @author Балабаев Никита Дмитриевич
     */
    private void loadValuesFromInterface(Lek newMedication)
    		throws NullPointerException {
    	if (newMedication == null)
    		throw new NullPointerException();
        if (curMedication.getPcod() >= 0) {
        	newMedication.unsetLek_name();
        	newMedication.setKlek(curMedication.getPcod());
        } else {
        	newMedication.unsetKlek();
        	newMedication.setLek_name(curMedication.getName());
        }
        if (curMedicationForm != null)
        	newMedication.setFlek(curMedicationForm.getName());
        else
        	newMedication.unsetFlek();
        if (spnrDose.getValue() != null)
        	newMedication.setDoza((int) spnrDose.getValue());
        else
        	newMedication.unsetDoza();
        if (cbxDoseEdd.getSelectedItem() != null)
        	newMedication.setEd(cbxDoseEdd.getSelectedPcod());
        else
        	newMedication.unsetEd();
        if (cbxInputMethod.getSelectedItem() != null)
        	newMedication.setSposv(cbxInputMethod.getSelectedPcod());
        else
        	newMedication.unsetSposv();
        if (cmbPeriod.getSelectedItem() != null)
            newMedication.setSpriem(cmbPeriod.getSelectedPcod());
        else
        	newMedication.unsetSpriem();
        String strKomment = taComment.getText();
        if ((strKomment != null) && (strKomment.length() > 0))
        	newMedication.setKomm(strKomment);
        else
        	newMedication.unsetKomm();
        if (spnrIntakePerDay.getValue() != null)
        	newMedication.setPereod((int) spnrIntakePerDay.getValue());
        else
        	newMedication.unsetPereod();
        if (cbxPayment.getSelectedItem() != null)
        	newMedication.setOpl(cbxPayment.getSelectedPcod());
        else
        	newMedication.unsetOpl();
    }

    /**
     * Функция проверки корректности выбранных пользователем дат
     * @return Возвращает <code>true</code>, если данные верны;
	 * иначе - <code>false</code>
     * @author Балабаев Никита Дмитриевич
     */
    private boolean checkDates() {
    	if ((cdeDateFrom.getDate() == null) || (cdeDateTo.getDate() == null)) {
    		JOptionPane.showMessageDialog(this.getContentPane(), 
    				"Не установлены дата начала и/или дата окончания приёма",
    				"Информация", JOptionPane.INFORMATION_MESSAGE);
    		return false;
    	}
    	if (cdeDateFrom.getDate().after(cdeDateTo.getDate())) {
    		JOptionPane.showMessageDialog(this.getContentPane(), 
    				"Дата начала приёма не может быть позже даты окончания",
    				"Информация", JOptionPane.INFORMATION_MESSAGE);
    		return false;
    	}
    	return true;
    }

    /**
     * Событие, вызываемое при нажатии на кнопку добавления
     * лекарственного назначения
     */
    private void btnAddMedicationClick() {
    	if (!checkDates())
    		return;
        Lek newMedication = new Lek();
        loadValuesFromInterface(newMedication);
        newMedication.setIdGosp(patient.getIdGosp());
        newMedication.setVrach(ClientMedication.authInfo.getPcod());
        newMedication.setDatan(cdeDateFrom.getDate().getTime());
        newMedication.setDatae(cdeDateTo.getDate().getTime());
        newMedication.setDataz(System.currentTimeMillis());
        if (curDiag != null)
        	newMedication.setDiag(curDiag.getPcod());
        else
        	newMedication.unsetDiag();
        try {
        	newMedication.setNlek(ClientMedication.tcl.addLek(newMedication));
            MedicationOptionsFrame.this.dispatchEvent(new WindowEvent(
                    MedicationOptionsFrame.this, WindowEvent.WINDOW_CLOSING));
            return;
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            e1.printStackTrace();
            ClientMedication.conMan.reconnect(e1);
        }
    	JOptionPane.showMessageDialog(getContentPane(),
        		"Ошибка во время добавления медикамента", "Ошибка",
        		JOptionPane.ERROR_MESSAGE);
    }

    private void addButtonAdd() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        btnAdd = new JButton("Выбрать");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnAddMedicationClick();
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
        cbxPayment.setSelectedIndex(-1);
        spnrDose.setValue(0);
        spnrIntakePerDay.setValue(1);
        tfDiagCode.setText(null);
        curDiag = null;
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

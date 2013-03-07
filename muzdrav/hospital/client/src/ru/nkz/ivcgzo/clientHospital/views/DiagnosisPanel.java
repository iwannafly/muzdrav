package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.nkz.ivcgzo.clientHospital.controllers.DiagnosisController;
import ru.nkz.ivcgzo.clientHospital.model.IDiagnosisObserver;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis._Fields;
import javax.swing.JCheckBox;

/**
 * Панель диагнозов
 */
public class DiagnosisPanel extends JPanel implements IPatientObserver, IDiagnosisObserver {
    private static final long serialVersionUID = -3813268997543431546L;
    private static final String TOOLTIP_TEXT =
            "<html><b>Панель диагнозов</b> - позволяет врачу стационара  "
            + "сохранять диагнозы, установленные при лечнии пациента стационара. </html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/firstAid.png");
    private static final String TITLE = "Диагнозы";

    private DiagnosisController controller;
    private IHospitalModel model;

    private Component hsDiagnosisFirst;
    private Component hsDiagnosisSecond;
    private CustomTable<TDiagnosis, _Fields> tbDiag;
    private JButton btnDelDiag;
    private JTextArea taDiagMedOp;
    private ButtonGroup btgDiag;
    private JRadioButton rdbtnMain;
    private JRadioButton rdbtnSoput;
    private JRadioButton rdbtnOsl;
    private JPanel pDiagTypes;
    private Component hzstSopDiagOslDiag;
    private Component hzstMainDiagSopDiag;
    private Box vbDiagnosisTextFields;
    private JScrollPane spDiagnosisMedOp;
    private JLabel lblDiagMedOp;
    private Component verticalStrut;
    private Component vsDiagnosisControlsDelimFirst;
    private JButton btnSaveDiag;
    private Box vbDiagnosisTableButtons;
    private JButton btnAddDiag;
    private JScrollPane spDiag;
    private Box hbDiagnosisTableControls;
    private JCheckBox chbxPredv;
    private Component verticalStrutFirst;

    public DiagnosisPanel(final DiagnosisController inController,
            final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        // регистрация слушателя изменения пациента
        model.registerPatientObserver((IPatientObserver) this);
        // регистрация слушателя изменения текущего (выбранного в таблице) диагноза
        model.registerDiagnosisObserver((IDiagnosisObserver) this);

        setDiagnosisPanel();
    }

    private void setDiagnosisPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        hsDiagnosisFirst = Box.createHorizontalStrut(5);
        add(hsDiagnosisFirst);

        setDiagnosisVerticalTextComponents();

        hsDiagnosisSecond = Box.createHorizontalStrut(5);
        add(hsDiagnosisSecond);
    }


    private void setDiagnosisVerticalTextComponents() {
        vbDiagnosisTextFields = Box.createVerticalBox();
        vbDiagnosisTextFields.setBorder(new EtchedBorder(
            EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbDiagnosisTextFields.setPreferredSize(new Dimension(500, 0));
        add(vbDiagnosisTextFields);

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

    /**
     * добавление таблицы диагнозов
     */
    private void addDiagnosisTable() {
        tbDiag = new CustomTable<TDiagnosis, TDiagnosis._Fields>(
                true, false, TDiagnosis.class, 4, "Дата", 2, "Код МКБ", 7, "Наименование диагноза");
        tbDiag.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tbDiag.getSelectedItem() != null) {
                        controller.setCurrentDiagnosisRecord(tbDiag.getSelectedItem());
                    }
                }
            }
        });
        tbDiag.setDateField(0);
        tbDiag.setEditableFields(false, 1);
        tbDiag.setEditableFields(false, 2);
        spDiag.setViewportView(tbDiag);
    }

    /**
     * Заполнение информации о выбранном диагнозе
     */
    private void fillCurrentDiagInfo() {
        setDiagPriznRdbtn();
        setDiagChBx();
        taDiagMedOp.setText(tbDiag.getSelectedItem().getMedOp());
    }

    /**
     * Заполнение информации о предварительности диагноза
     */
    private void setDiagChBx() {
        if (!tbDiag.getSelectedItem().isSetPredv()) {
            chbxPredv.setSelected(false);
        } else if (tbDiag.getSelectedItem().isPredv()) {
            chbxPredv.setSelected(true);
        } else {
            chbxPredv.setSelected(false);
        }
    }

    private void setDiagnosisTableButtonsPanel() {
        vbDiagnosisTableButtons = Box.createVerticalBox();
        vbDiagnosisTableButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbDiagnosisTableControls.add(vbDiagnosisTableButtons);

        addDiagnosisButtons();
    }

    /**
     *   Добавление кнопок
     */
    private void addDiagnosisButtons() {
        addDiagnosisAddButton();
        addDiagnosisDeleteButton();
        addDiagnosisUpdateButton();
    }

    /**
     *   Добавление кнопки "Добавить"
     */
    private void addDiagnosisAddButton() {
        btnAddDiag = new JButton();
        btnAddDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddDiag.setMaximumSize(new Dimension(50, 50));
        btnAddDiag.setPreferredSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnAddDiag);
        btnAddDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                controller.addDiagnosis();
                if (tbDiag.getRowCount() > 0) {
                    tbDiag.setRowSelectionInterval(tbDiag.getRowCount() - 1,
                        tbDiag.getRowCount() - 1);
                }
            }
        });
        btnAddDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
    }

    /**
     *   Добавление кнопки "Удалить"
     */
    private void addDiagnosisDeleteButton() {
        btnDelDiag = new JButton();
        btnDelDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelDiag.setPreferredSize(new Dimension(50, 50));
        btnDelDiag.setMaximumSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnDelDiag);
        btnDelDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (tbDiag.getSelectedItem() != null) {
                    int opResult = JOptionPane.showConfirmDialog(
                        DiagnosisPanel.this, "Удалить диагноз?",
                        "Удаление диагноза", JOptionPane.YES_NO_OPTION);
                    if (opResult == JOptionPane.YES_OPTION) {
                        controller.deleteDiagnosis(tbDiag.getSelectedItem());
                    }
                    if (tbDiag.getRowCount() > 0) {
                        tbDiag.setRowSelectionInterval(tbDiag.getRowCount() - 1,
                            tbDiag.getRowCount() - 1);
                    }
                }
            }
        });
        btnDelDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
    }

    /**
     *   Добавление кнопки "Обновить"
     */
    private void addDiagnosisUpdateButton() {
        btnSaveDiag = new JButton();
        btnSaveDiag.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSaveDiag.setPreferredSize(new Dimension(50, 50));
        btnSaveDiag.setMaximumSize(new Dimension(50, 50));
        vbDiagnosisTableButtons.add(btnSaveDiag);
        btnSaveDiag.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (tbDiag.getSelectedItem() != null) {
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
                    if (chbxPredv.isSelected()) {
                        tbDiag.getSelectedItem().setPredv(true);
                    }
                    tbDiag.getSelectedItem().setIdGosp(model.getPatient().getGospitalCod());

                    // сохраняем номер выделенной строки
                    int selRowNumber = tbDiag.getSelectedRow();
                    // передаем контроллеру строку для обновления
                    controller.updateDiagnosis(tbDiag.getSelectedItem());
                    // устанавливаем выделение на строку с сохраненным ранее номером
                    tbDiag.setRowSelectionInterval(selRowNumber, selRowNumber);
                }
            }
        });
        btnSaveDiag.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
    }

    private void setDiagnosisTextComponents() {
        vsDiagnosisControlsDelimFirst = Box.createVerticalStrut(10);
        vbDiagnosisTextFields.add(vsDiagnosisControlsDelimFirst);

        addDiagnosisRadioButtonsGroup();

        verticalStrutFirst = Box.createVerticalStrut(10);
        vbDiagnosisTextFields.add(verticalStrutFirst);

        addDiagnosisCheckBox();

        verticalStrut = Box.createVerticalStrut(10);
        vbDiagnosisTextFields.add(verticalStrut);

        addDiagnosisMedOpHeader();
        addDiagnosisMedOpScrollPane();
    }

    /**
     *   Добавление компонета отображения предварительности диагноза
     */
    private void addDiagnosisCheckBox() {
        chbxPredv = new JCheckBox("Предварительный диагноз");
        chbxPredv.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbDiagnosisTextFields.add(chbxPredv);
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

    /**
     *   Добавление компонета отображения медицинского описания диагноза
     */
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

    /**
     *   Добавление панели radioButton'ов
     */
    private void addDiagnosisRadioGroupPanel() {
        pDiagTypes = new JPanel();
        pDiagTypes.setPreferredSize(new Dimension(425, 25));
        pDiagTypes.setMaximumSize(new Dimension(425, 25));
        vbDiagnosisTextFields.add(pDiagTypes);
        pDiagTypes.setBorder(new LineBorder(new Color(0, 0, 0)));
        pDiagTypes.setLayout(new BoxLayout(pDiagTypes, BoxLayout.X_AXIS));
        pDiagTypes.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     *   Добавление radioButton'ов
     */
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

    /**
     *   Установка состояния radioButton'ов согласно полю Prizn
     * выбранного объекта
     */
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

    /**
     * Очистка всех полей
     */
    private void clearDiagnosisText() {
        tbDiag.setData(Collections.<TDiagnosis>emptyList());
        taDiagMedOp.setText(null);
        rdbtnMain.setSelected(false);
        rdbtnSoput.setSelected(false);
        rdbtnOsl.setSelected(false);
        chbxPredv.setSelected(false);
    }

    public final Component getComponent() {
        return this;
    }

    public final String getPanelToolTipText() {
        return TOOLTIP_TEXT;
    }

    public final String getTitle() {
        return TITLE;
    }

    public final URL getIconURL() {
        return ICON;
    }

    /**
     * Все изменения, необходимые при смене пациента
     */
    @Override
    public final void patientChanged() {
        clearDiagnosisText();
        updateDiagonsisTable();
    }

    /**
     * Все изменения, необходимые при смене текущего диагноза
     */
    @Override
    public final void diagnosisChanged() {
        fillCurrentDiagInfo();
    }

    public final void updateDiagonsisTable() {
        if (model.getPatient() != null) {
            tbDiag.setData(model.getDiagnosisList());
        }
    }
}

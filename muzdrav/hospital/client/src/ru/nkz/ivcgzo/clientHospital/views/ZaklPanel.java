package ru.nkz.ivcgzo.clientHospital.views;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.controllers.ZaklController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.observers.IDiagnosisObserver;
import ru.nkz.ivcgzo.clientHospital.model.observers.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.TDiagnosis;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

import javax.swing.border.LineBorder;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.UIManager;
import javax.swing.JTextField;

/**
 * Панель заключения
 */
public class ZaklPanel extends JPanel implements IPatientObserver, IDiagnosisObserver {
    private static final long serialVersionUID = 1454864680563116962L;
    private static final String TOOLTIP_TEXT =
            "<html><b>Панель заключения</b> - позволяет врачу стационара  "
            + "сохранять информацию об итогах лечения пациента в данном "
            + "отделении стационара. </html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/out.png");
    private static final String TITLE = "Заключение";

    // код исхода при переводе пациента в другое отделение
    private static final int ISHOD_NAPRAV = 3;
    // код исхода при смерти пациента
    private static final int ISHOD_DEAD = 2;
    // признак заключительного диагноза
    private static final int DIAG_ZAKL_PRIZN = 4;
    // признак паталогоанатомического диагноза
    private static final int DIAG_PATALOG_PRIZN = 5;

    private ZaklController controller;
    private IHospitalModel model;

    private JTextField tfUkl;
    private CustomDateEditor cdeDate;
    private CustomTimeEditor cdeTime;
    private Box vbZaklInfoComponents;
    private Box vbRecomendations;
    private JScrollPane spRecomend;
    private JTextArea taRecomend;
    private Box vbSost;
    private JScrollPane spSost;
    private JTextArea taSost;
    private Box hbZaklOplComponents;
    private Box vbDefects;
    private JLabel lblDefects;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDefects;
    private Box vbOplat;
    private JLabel lblOplat;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxOplat;
    private Box vbUkl;
    private JLabel lblUkl;
    private Box vbVipPom;
    private JLabel lblVipPom;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidPom;
    private Box vbDiag;
    private JLabel lblZaklDiag;
    private ThriftStringClassifierCombobox<StringClassifier> cbxZaklDiag;
    private JLabel lblPatalogDiag;
    private ThriftStringClassifierCombobox<StringClassifier> cbxPatalogDiag;
    private Box vbIshod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxIshod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxNaprav;
    private Box hbDateOut;
    private Component hgLeft;
    private JLabel lblDate;
    private Component hgCenter;
    private JLabel lblTime;
    private Component hgRight;
    private Box vbResult;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxResult;
    private JButton btnOut;
    private JLabel lblZaklShablon;
    private Box vbZaklShablon;
    private CustomTextField tfZaklShablon;
    private JButton btnZaklShablon;
    private ThriftIntegerClassifierList lZaklShablon;
    private JScrollPane spZaklShablon;
    private Component verticalStrut;
    private Component hgDataLeft;
    private Component hgDataRight;
    private Component hsDataRight;
    private Component hsOplat;
    private Component hsDataLeft;
    private ShablonSearchListener shablonListener;

    public ZaklPanel(final ZaklController inController,
            final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerPatientObserver((IPatientObserver) this);
        // регистрируем наблюдателем за текущим диагнозом -
        // для перезаполнения копмпонент выбора заключительного
        // и паталогоанатомического диагноза
        model.registerDiagnosisObserver((IDiagnosisObserver) this);

        setZaklPanel();
        cbxNaprav.setData(model.getAllOtd());
    }

    /**
     * Формирование панели заключения
     */
    private void setZaklPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Component hzLeft = Box.createHorizontalStrut(5);
        add(hzLeft);

        setDataComponentsPanel();

        Component hsCenter = Box.createHorizontalStrut(5);
        add(hsCenter);

        setShablonComponentsPanel();

        Component hsRight = Box.createHorizontalStrut(5);
        add(hsRight);
    }

    /**
     * Установка компонентов для ввода/отображения данных
     */
    private void setDataComponentsPanel() {
        vbZaklInfoComponents = Box.createVerticalBox();
        vbZaklInfoComponents.setPreferredSize(new Dimension(500, 0));
        vbZaklInfoComponents.setBorder(
                new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        add(vbZaklInfoComponents);

        setRecomendationComponents();
        setSostComponents();
        setVerticalComponents();
        setDiagnosisComponents();
        setIshodComponents();
        setResultComponents();
        setDateOutComponents();
        setButtonOut();
    }

    /**
     * Установка компонентов для ввода/отображения данных о рекомендациях
     */
    private void setRecomendationComponents() {
        vbRecomendations = Box.createVerticalBox();
        vbRecomendations.setBorder(
            new TitledBorder(
                null, "Рекомендации", TitledBorder.LEADING, TitledBorder.TOP, null, null
            )
        );
        vbZaklInfoComponents.add(vbRecomendations);

        spRecomend = new JScrollPane();
        spRecomend.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbRecomendations.add(spRecomend);

        taRecomend = new JTextArea();
        taRecomend.setWrapStyleWord(true);
        taRecomend.setLineWrap(true);
        taRecomend.setFont(new Font("Monospaced", Font.PLAIN, 11));
        spRecomend.setViewportView(taRecomend);
    }

    /**
     * Установка компонентов для ввода/отображения данных о состоянии при выписке
     */
    private void setSostComponents() {
        vbSost = Box.createVerticalBox();
        vbSost.setBorder(
            new TitledBorder(
                UIManager.getBorder("TitledBorder.border"),
                "Состояние при выписке",
                TitledBorder.LEADING, TitledBorder.TOP,
                null, null
            )
        );
        vbZaklInfoComponents.add(vbSost);

        spSost = new JScrollPane();
        spSost.setAlignmentX(0.0f);
        vbSost.add(spSost);

        taSost = new JTextArea();
        taSost.setLineWrap(true);
        taSost.setWrapStyleWord(true);
        taSost.setFont(new Font("Monospaced", Font.PLAIN, 11));
        spSost.setViewportView(taSost);
    }

    private void setVerticalComponents() {

        hbZaklOplComponents = Box.createHorizontalBox();
        hbZaklOplComponents.setPreferredSize(new Dimension(500, 50));
        hbZaklOplComponents.setAlignmentY(Component.CENTER_ALIGNMENT);
        hbZaklOplComponents.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbZaklInfoComponents.add(hbZaklOplComponents);

        setVidPomComponents();
        setUklComponents();
        setOplatComponents();
        setDefectsComponents();

    }

    /**
     * Установка компонентов для ввода/отображения данных о виде помощи
     */
    private void setVidPomComponents() {
        hgDataLeft = Box.createHorizontalGlue();
        hbZaklOplComponents.add(hgDataLeft);
        vbVipPom = Box.createVerticalBox();
        vbVipPom.setPreferredSize(new Dimension(100, 50));
        vbVipPom.setMaximumSize(new Dimension(100, 50));
        hbZaklOplComponents.add(vbVipPom);

        lblVipPom = new JLabel("Вид помощи");
        vbVipPom.add(lblVipPom);

        cbxVidPom =
            new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_vp1);
        cbxVidPom.setPreferredSize(new Dimension(100, 20));
        cbxVidPom.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbxVidPom.setEditable(false);
        cbxVidPom.setMaximumSize(new Dimension(100, 25));
        vbVipPom.add(cbxVidPom);
    }

    /**
     * Установка компонентов для ввода/отображения данных об УКЛ
     */
    private void setUklComponents() {
        hsDataLeft = Box.createHorizontalStrut(20);
        hsDataLeft.setMaximumSize(new Dimension(20, 0));
        hbZaklOplComponents.add(hsDataLeft);

        vbUkl = Box.createVerticalBox();
        vbUkl.setPreferredSize(new Dimension(50, 50));
        vbUkl.setMaximumSize(new Dimension(50, 50));
        hbZaklOplComponents.add(vbUkl);

        lblUkl = new JLabel("УКЛ");
        lblUkl.setMaximumSize(new Dimension(50, 14));
        vbUkl.add(lblUkl);

        tfUkl = new JTextField();
        tfUkl.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfUkl.setMaximumSize(new Dimension(50, 25));
        tfUkl.setColumns(10);
        vbUkl.add(tfUkl);
    }

    /**
     * Установка компонентов для ввода/отображения данных об оплате
     */
    private void setOplatComponents() {
        hsOplat = Box.createHorizontalStrut(20);
        hsOplat.setMaximumSize(new Dimension(20, 0));
        hbZaklOplComponents.add(hsOplat);
        vbOplat = Box.createVerticalBox();
        vbOplat.setPreferredSize(new Dimension(100, 50));
        vbOplat.setMaximumSize(new Dimension(100, 50));
        hbZaklOplComponents.add(vbOplat);

        lblOplat = new JLabel("Вид оплаты");
        vbOplat.add(lblOplat);

        cbxOplat = new ThriftIntegerClassifierCombobox<IntegerClassifier>(
            IntegerClassifiers.n_opl);
        cbxOplat.setMinimumSize(new Dimension(100, 20));
        cbxOplat.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbxOplat.setEditable(false);
        cbxOplat.setMaximumSize(new Dimension(100, 25));
        vbOplat.add(cbxOplat);
    }

    /**
     * Установка компонентов для ввода/отображения данных о дефектах догоспитального этапа
     */
    private void setDefectsComponents() {
        hsDataRight = Box.createHorizontalStrut(20);
        hsDataRight.setMaximumSize(new Dimension(20, 0));
        hbZaklOplComponents.add(hsDataRight);
        vbDefects = Box.createVerticalBox();
        vbDefects.setPreferredSize(new Dimension(150, 50));
        vbDefects.setMaximumSize(new Dimension(150, 50));
        hbZaklOplComponents.add(vbDefects);

        lblDefects = new JLabel("Дефекты догосп. этапа");
        vbDefects.add(lblDefects);

        cbxDefects =
            new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_def);
        cbxDefects.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbxDefects.setEditable(false);
        cbxDefects.setMaximumSize(new Dimension(150, 25));
        vbDefects.add(cbxDefects);

        hgDataRight = Box.createHorizontalGlue();
        hbZaklOplComponents.add(hgDataRight);
    }

    /**
     * Установка компонентов для ввода/отображения данных о диагнозах
     */
    private void setDiagnosisComponents() {
        verticalStrut = Box.createVerticalStrut(15);
        vbZaklInfoComponents.add(verticalStrut);

        vbDiag = Box.createVerticalBox();
        vbDiag.setBorder(
            new TitledBorder(
                UIManager.getBorder("TitledBorder.border"),
                "Диагнозы",
                TitledBorder.LEADING, TitledBorder.TOP,
                null, null
            )
        );
        vbZaklInfoComponents.add(vbDiag);

        lblZaklDiag = new JLabel("Заключительный диагноз");
        vbDiag.add(lblZaklDiag);

        cbxZaklDiag = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxZaklDiag.setEditable(false);
        cbxZaklDiag.setMaximumSize(new Dimension(32767, 25));
        cbxZaklDiag.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbDiag.add(cbxZaklDiag);

        lblPatalogDiag = new JLabel("Паталогоанатомический диагноз");
        vbDiag.add(lblPatalogDiag);

        cbxPatalogDiag = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxPatalogDiag.setEditable(false);
        cbxPatalogDiag.setMaximumSize(new Dimension(32767, 25));
        cbxPatalogDiag.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbDiag.add(cbxPatalogDiag);

        lblPatalogDiag.setVisible(false);
        cbxPatalogDiag.setVisible(false);
    }

    /**
     * Установка компонентов для ввода/отображения данных об исходе
     */
    private void setIshodComponents() {
        vbIshod = Box.createVerticalBox();
        vbIshod.setBorder(
            new TitledBorder(
                null, "Исход заболевания", TitledBorder.LEADING,
                TitledBorder.TOP, null, null
            )
        );
        vbZaklInfoComponents.add(vbIshod);

        cbxIshod = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxIshod.setEditable(false);
        cbxIshod.setMaximumSize(new Dimension(32767, 25));
        cbxIshod.setAlignmentX(Component.LEFT_ALIGNMENT);
        // регулирование отображения компонентов выбора отделения куда направлен пациент
        // и паталогоанаомического диагноза в зависимости от вида исхода
        cbxIshod.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if (((IntegerClassifier) e.getItem()).getPcod() == ISHOD_NAPRAV) {
                    // если пациент переведен в другое отделение
                    cbxNaprav.setVisible(true);
                    setPatalAnatComponentsVisble(false);
                } else if (((IntegerClassifier) e.getItem()).getPcod() == ISHOD_DEAD) {
                    // если пациент умер
                    cbxNaprav.setVisible(false);
                    setPatalAnatComponentsVisble(true);
                } else {
                    // остальные варианты исхода
                    cbxNaprav.setVisible(false);
                    setPatalAnatComponentsVisble(false);
                }
            }
        });
        cbxIshod.setData(controller.getIshodClassifier());
        vbIshod.add(cbxIshod);

        cbxNaprav = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxNaprav.setEditable(false);
        cbxNaprav.setMaximumSize(new Dimension(32767, 25));
        cbxNaprav.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbIshod.add(cbxNaprav);


        cbxNaprav.setVisible(false);
    }

    /**
     * Управление отображением/скрытием компонентов ввода паталогоанатомического диагноза
     */
    private void setPatalAnatComponentsVisble(final boolean visibility) {
        if (visibility) {
            lblPatalogDiag.setVisible(true);
            cbxPatalogDiag.setVisible(true);
        } else {
            lblPatalogDiag.setVisible(false);
            cbxPatalogDiag.setVisible(false);
        }
    }

    /**
     * Установка компонентов для ввода/отображения данных о результате лечения
     */
    private void setResultComponents() {
        vbResult = Box.createVerticalBox();
        vbResult.setBorder(
            new TitledBorder(
                null, "Результат лечения", TitledBorder.LEADING,
                TitledBorder.TOP, null, null
            )
        );
        vbZaklInfoComponents.add(vbResult);

        cbxResult = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxResult.setEditable(false);
        cbxResult.setMaximumSize(new Dimension(32767, 25));
        cbxResult.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbxResult.setData(controller.getResultClassifier());
        vbResult.add(cbxResult);
    }

    /**
     * Установка компонентов для ввода/отображения данных о дате и времени выписки
     */
    private void setDateOutComponents() {
        hbDateOut = Box.createHorizontalBox();
        hbDateOut.setAlignmentX(Component.LEFT_ALIGNMENT);
        hbDateOut.setAlignmentY(Component.CENTER_ALIGNMENT);
        vbZaklInfoComponents.add(hbDateOut);

        hgLeft = Box.createHorizontalGlue();
        hbDateOut.add(hgLeft);

        lblDate = new JLabel("Дата выписки: ");
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbDateOut.add(lblDate);

        cdeDate = new CustomDateEditor();
        cdeDate.setMaximumSize(new Dimension(50, 25));
        hbDateOut.add(cdeDate);
        cdeDate.setColumns(10);

        hgCenter = Box.createHorizontalGlue();
        hbDateOut.add(hgCenter);

        lblTime = new JLabel("Время выписки: ");
        hbDateOut.add(lblTime);

        cdeTime = new CustomTimeEditor();
        cdeTime.setMaximumSize(new Dimension(50, 25));
        hbDateOut.add(cdeTime);
        cdeTime.setColumns(10);

        hgRight = Box.createHorizontalGlue();
        hbDateOut.add(hgRight);
    }

    /**
     * Установка кнопки выписки
     */
    private void setButtonOut() {
        verticalStrut = Box.createVerticalStrut(15);
        vbZaklInfoComponents.add(verticalStrut);

        btnOut = new JButton("Выписать");
        btnOut.setMaximumSize(new Dimension(32000, 23));
        vbZaklInfoComponents.add(btnOut);

        addButtonOutListener();
    }

    private void addButtonOutListener() {
        btnOut.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                //FIXME вернуть проверку дат
                //TODO перенести логику выбора в контроллер
                if (isStagesCorrect()) { // && isStageDatesCorrect()) {
                    if (isPatientOut()) {
                        if (isAllRequiredOutFieldsSet()) {
                            Zakl tmpZakl = new Zakl();
                            tmpZakl.setRecom(taRecomend.getText());
                            tmpZakl.setSostv(taSost.getText());
                            tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                            tmpZakl.setResult(cbxResult.getSelectedPcod());
                            tmpZakl.setDatav(cdeDate.getDate().getTime());
                            tmpZakl.setVremv(cdeTime.getTime().getTime());
                            tmpZakl.setVidOpl(cbxOplat.getSelectedPcod());
                            tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                            tmpZakl.setNpasp(model.getPatient().getPatientId());
                            tmpZakl.setNgosp(model.getPatient().getNgosp());
                            setZaklDiag(tmpZakl);
                            if (!tfUkl.getText().isEmpty()) {
                                tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                            }
                            tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                            controller.addZakl(tmpZakl);
                            JOptionPane.showMessageDialog(null,
                                "Пациент успешно выписан", "Выписка пациента",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (isPatientDead()) {
                        if (isAllRequiredDeadFieldsSet()) {
                            Zakl tmpZakl = new Zakl();
                            tmpZakl.setRecom(taRecomend.getText());
                            tmpZakl.setSostv(taSost.getText());
                            tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                            tmpZakl.setDatav(cdeDate.getDate().getTime());
                            tmpZakl.setVremv(cdeTime.getTime().getTime());
                            tmpZakl.setVidOpl(cbxOplat.getSelectedPcod());
                            tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                            tmpZakl.setNpasp(model.getPatient().getPatientId());
                            tmpZakl.setNgosp(model.getPatient().getNgosp());
                            setZaklDiag(tmpZakl);
                            setPatalogDiag(tmpZakl);
                            if (!tfUkl.getText().isEmpty()) {
                                tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                            }
                            tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                            controller.addZakl(tmpZakl);
                            JOptionPane.showMessageDialog(null,
                                "Пациент успешно выписан", "Выписка пациента",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (isPatientMoved()) {
                        if (isAllRequiredMovedFieldsSet()) {
                            Zakl tmpZakl = new Zakl();
                            tmpZakl.setRecom(taRecomend.getText());
                            tmpZakl.setSostv(taSost.getText());
                            tmpZakl.setIshod(cbxIshod.getSelectedPcod());
                            tmpZakl.setResult(cbxResult.getSelectedPcod());
                            tmpZakl.setNewOtd(cbxNaprav.getSelectedPcod());
                            tmpZakl.setDatav(cdeDate.getDate().getTime());
                            tmpZakl.setVremv(cdeTime.getTime().getTime());
                            tmpZakl.setVidOpl(cbxOplat.getSelectedPcod());
                            tmpZakl.setVidPom(cbxVidPom.getSelectedPcod());
                            tmpZakl.setNpasp(model.getPatient().getPatientId());
                            tmpZakl.setNgosp(model.getPatient().getNgosp());
                            setZaklDiag(tmpZakl);
                            if (!tfUkl.getText().isEmpty()) {
                                tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                            }
                            tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                            controller.addZakl(tmpZakl);
                            JOptionPane.showMessageDialog(ZaklPanel.this,
                                "Пациент успешно выписан", "Выписка пациента",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ZaklPanel.this,
                            "Не выбран пациент или не установлен исход. "
                            + "Информация не сохранена", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ZaklPanel.this,
                        "Этапы лечения заполнены некорректно! Выписка невозможна!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setZaklDiag(final Zakl tmpZakl) {
        TDiagnosis diag = new TDiagnosis();
        diag.setIdGosp(model.getPatient().getGospitalCod());
        diag.setCod(cbxZaklDiag.getSelectedPcod());
        diag.setDateUstan(cdeDate.getDate().getTime());
        diag.setVrach(ClientHospital.authInfo.getPcod());
        diag.setMedOp(cbxZaklDiag.getSelectedItem().getName());
        diag.setPrizn(DIAG_ZAKL_PRIZN);
        tmpZakl.setZaklDiag(diag);
    }

    private void setPatalogDiag(final Zakl tmpZakl) {
        TDiagnosis diag = new TDiagnosis();
        diag.setIdGosp(model.getPatient().getGospitalCod());
        diag.setCod(cbxPatalogDiag.getSelectedPcod());
        diag.setDateUstan(cdeDate.getDate().getTime());
        diag.setVrach(ClientHospital.authInfo.getPcod());
        diag.setMedOp(cbxPatalogDiag.getSelectedItem().getName());
        diag.setPrizn(DIAG_PATALOG_PRIZN);
        tmpZakl.setPatalogDiag(diag);
    }

    /**
     * Установка компонентов отображения шаблонов
     */
    private void setShablonComponentsPanel() {
        vbZaklShablon = Box.createVerticalBox();
        vbZaklShablon.setPreferredSize(new Dimension(300, 0));
        vbZaklShablon.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        add(vbZaklShablon);

        setShablonFindComponents();
        setShablonNamesListComponents();
        setShablonListener();
    }

    /**
     * Установка компонентов поиска шаблонов
     */
    private void setShablonFindComponents() {
        lblZaklShablon = new JLabel("Строка поиска шаблона");
        lblZaklShablon.setHorizontalTextPosition(SwingConstants.LEFT);
        lblZaklShablon.setHorizontalAlignment(SwingConstants.LEFT);
        lblZaklShablon.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblZaklShablon.setAlignmentX(0.5f);
        vbZaklShablon.add(lblZaklShablon);

        Box hbZaklShbalonFind = Box.createHorizontalBox();
        vbZaklShablon.add(hbZaklShbalonFind);

        tfZaklShablon = new CustomTextField(true, true, false);
        tfZaklShablon.setMaximumSize(new Dimension(450, 50));
        tfZaklShablon.setColumns(10);
        hbZaklShbalonFind.add(tfZaklShablon);

        btnZaklShablon = new JButton("...");
        btnZaklShablon.setPreferredSize(new Dimension(63, 23));
        btnZaklShablon.setMinimumSize(new Dimension(63, 23));
        btnZaklShablon.setMaximumSize(new Dimension(63, 23));
        btnZaklShablon.setAlignmentX(0.5f);
        hbZaklShbalonFind.add(btnZaklShablon);
    }

    /**
     * Установка списка шаблонов
     */
    private void setShablonNamesListComponents() {
        spZaklShablon = new JScrollPane();
        vbZaklShablon.add(spZaklShablon);

        lZaklShablon = new ThriftIntegerClassifierList();
        lZaklShablon.setBorder(new LineBorder(new Color(0, 0, 0)));
        lZaklShablon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lZaklShablon.getSelectedValue() != null) {
                        Shablon selectedShablon = model.loadShablon(
                                lZaklShablon.getSelectedValue().pcod);
                        smartPasteShablon(selectedShablon);
                    }
                }
            }
        });
        spZaklShablon.setViewportView(lZaklShablon);
    }

    public final void smartPasteShablon(final Shablon selectedShablon) {
        if (isAllTextFieldsEmpty()) {
            pasteSelectedShablon(selectedShablon);
        } else {
            int opResult = JOptionPane.showConfirmDialog(ZaklPanel.this,
                "Заменить набранный текст при вставке нового шаблона? ",
                "Замена текста", JOptionPane.YES_NO_OPTION);
            if (opResult == JOptionPane.YES_OPTION) {
                pasteSelectedShablon(selectedShablon);
            } else {
                addSelectedShablon(selectedShablon);
            }
        }
    }

    private boolean isAllTextFieldsEmpty() {
        return ((taRecomend.getText().isEmpty())
                && (taSost.getText().isEmpty()));
    }

    private void clearZaklText() {
        taRecomend.setText("");
        taSost.setText("");
        cdeDate.setDate(new Date());
        cdeTime.setTime(new Date());
    }

    private void pasteSelectedShablon(final Shablon shablon) {
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
                    taSost.setText(shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private void addSelectedShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        for (ShablonText shText : shablon.textList) {
            switch (shText.grupId) {
                case 12:
                    addToExistingTextField(taRecomend, shText.getText());
                    break;
                case 13:
                    addToExistingTextField(taSost, shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    private void addToExistingTextField(final JTextArea tArea, final String text) {
        if (tArea.getText().isEmpty()) {
            tArea.setText(text);
        } else {
            tArea.setText(tArea.getText() + "\n" + text);
        }
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

    private boolean isStagesCorrect() {
//FIXME просили удалить проверку удаления этапов. Т.к. наверняка передумают пока что
//  тут будет заглушка.
        return true;
//      return isStageTableReadyToOut();
    }

    private boolean isPrimaryOutValueSet() {
        return (model.getPatient() != null) && (cbxIshod.getSelectedItem() != null);
    }

    private boolean isPatientOut() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() != ISHOD_DEAD)
                && (cbxIshod.getSelectedPcod() != ISHOD_NAPRAV);
        } else {
            return false;
        }
    }

    private boolean isPatientDead() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() == ISHOD_DEAD);
        } else {
            return false;
        }
    }

    private boolean isPatientMoved() {
        if (isPrimaryOutValueSet()) {
            return (cbxIshod.getSelectedPcod() == ISHOD_NAPRAV);
        } else {
            return false;
        }
    }

    /**
     * Проверяет - все ли необходимые поля заполнены (при выписке пациента)
     */
    private boolean isAllRequiredOutFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxOplat.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxZaklDiag.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxResult.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран результат лечения. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeTime.getTime() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано время выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Проверяет - все ли необходимые поля заполнены (при смерти пациента)
     */
    private boolean isAllRequiredDeadFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxOplat.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxZaklDiag.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((cbxZaklDiag.getSelectedItem() == null)
                || (cbxPatalogDiag.getSelectedItem() == null)) {
            JOptionPane.showMessageDialog(null,
                "Не выбран паталогоанатомический диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeTime.getTime() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано время смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Проверяет - все ли необходимые поля заполнены (при переводе пациента в другое отделение)
     */
    private boolean isAllRequiredMovedFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxOplat.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxZaklDiag.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeTime.getTime() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано время перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxResult.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран результат лечения. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxNaprav.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано отделения для перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void setShablonListener() {
        shablonListener =
                new ShablonSearchListener(tfZaklShablon, lZaklShablon);
        tfZaklShablon.getDocument().addDocumentListener(shablonListener);
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

    /**
     * Загрузка списка шаблонов
     */
    private void loadShablonList(final CustomTextField inCtf,
            final ThriftIntegerClassifierList inTicl) {
        List<IntegerClassifier> intClassif = model.loadMedicalHistoryShablons(
            (inCtf.getText().length() < 3)
            ? null : '%' + inCtf.getText() + '%');
        inTicl.setData(intClassif);
    }

    /**
     * Синхронизация списка шаблонов (с формой выбора)
     */
    public final void syncShablonList(final String searchString, final Shablon shablon) {
        if (shablon != null) {
            shablonListener.updateNow(searchString);
            for (int i = 0; i < lZaklShablon.getData().size(); i++) {
                if (lZaklShablon.getData().get(i).pcod == shablon.getId()) {
                    lZaklShablon.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            lZaklShablon.setSelectedIndex(-1);
        }
    }

    /**
     * Установка списка шаблонов
     */
    public final void setZaklHistoryShablons(
            final List<IntegerClassifier> zaklShablonList) {
        lZaklShablon.setData(zaklShablonList);
    }

    /**
     * Реакция на смену пациента
     */
    @Override
    public final void patientChanged() {
        clearZaklText();

        //FIXME очень вероятно что здесь они не нужны и просто дублируют
        //      свой же вызов в diagnosisChanged(), не успеваю проверить
        //      как будет время - протестите и попровьте если надо
        cbxZaklDiag.setData(model.getExistedDiags());
        cbxPatalogDiag.setData(model.getExistedDiags());
        lZaklShablon.setData(model.loadZaklShablons());
    }

    /**
     * При смене диагноза перезаполняем соответствующие компоненты
     */
    @Override
    public final void diagnosisChanged() {
        cbxZaklDiag.setData(model.getExistedDiags());
        cbxPatalogDiag.setData(model.getExistedDiags());
        lZaklShablon.setData(model.loadZaklShablons());
    }
}

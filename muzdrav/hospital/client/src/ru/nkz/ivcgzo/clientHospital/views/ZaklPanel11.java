package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.controllers.ZaklController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;
import ru.nkz.ivcgzo.thriftHospital.Zakl;

public class ZaklPanel11 extends JPanel implements IPatientObserver {
    private static final long serialVersionUID = -1144680238391649525L;
    private static final String TOOLTIP_TEXT =
            "<html><b>Панель заключения</b> - позволяет врачу стационара  "
            + "сохранять информацию об итогах лечения пациента в данном "
            + "отделении стационара. </html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/out.png");
    private static final String TITLE = "Заключение";
    @SuppressWarnings("unused")
    private ZaklController controller;
    private IHospitalModel model;
    private JScrollPane spZaklShablonNames;
    private ThriftIntegerClassifierList lZaklShablonNames;
    private CustomTextField tfZaklShablonNames;
    private ShablonSearchListener zaklSearchListener;
    private ShablonForm frmShablon;
    private JButton btnZaklShablonFind;
    private JTextArea taRecomend;
    private JTextArea taZakluch;
    private CustomDateEditor cdeZaklDate;
    private CustomTimeEditor cdeZaklTime;
    private JScrollPane spRecomend;
    private JLabel lblRecomend;
    private JScrollPane spZakluch;
    private JLabel lblZakluch;
    private JLabel lblIshod;
    private JLabel lblResult;
    private JLabel lblZaklDate;
    private JLabel lblZaklTime;
    private JLabel lblPatalogoAnDiagHeader;
    private JTextField tfPatalogoAnDiagName;
    private JTextField tfPatalogoAnDiagPcod;
    private JButton btnPatalogoAnDiag;
    private JLabel lblZaklDiag;
    private JTextField tfZaklDiagPcod;
    private JTextField tfZaklDiagName;
    private JButton btnZaklDiag;
    private JLabel lblVidPom;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidPom;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDefect;
    private JLabel lblDefect;
    private JLabel lblUkl;
    private JTextField tfUkl;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxIshod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxAnotherOtd;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxResult;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidOpl;
    private JLabel lblVidOpl;
    private JLabel lblZaklDiagStep;
    private JRadioButton rdbtnZaklDiagSrT;
    private JRadioButton rdbtnZaklDiagTT;
    private JButton btnSaveZakl;

    public ZaklPanel11(final ZaklController inController,
            final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerPatientObserver((IPatientObserver) this);

        setZaklPanel();
    }

    private void setZaklPanel() {
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
                            JOptionPane.showMessageDialog(null,
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
                    //FIXME вернуть проверку дат
                    if (isStagesCorrect()) { // && isStageDatesCorrect()) {
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
                                tmpZakl.setNpasp(model.getPatient().getPatientId());
                                tmpZakl.setNgosp(model.getPatient().getNgosp());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl,
                                        ClientHospital.authInfo.getCpodr());
                                JOptionPane.showMessageDialog(null,
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
                                tmpZakl.setNpasp(model.getPatient().getPatientId());
                                tmpZakl.setNgosp(model.getPatient().getNgosp());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl,
                                        ClientHospital.authInfo.getCpodr());
                                JOptionPane.showMessageDialog(null,
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
                                tmpZakl.setNpasp(model.getPatient().getPatientId());
                                tmpZakl.setNgosp(model.getPatient().getNgosp());
                                if (!tfUkl.getText().isEmpty()) {
                                    tmpZakl.setUkl(Double.valueOf(tfUkl.getText()));
                                }
                                tmpZakl.setIdGosp(model.getPatient().getGospitalCod());
                                ClientHospital.tcl.addZakl(tmpZakl,
                                        ClientHospital.authInfo.getCpodr());
                                JOptionPane.showMessageDialog(null,
                                    "Пациент успешно выписан", "Выписка пациента",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "Не выбран пациент или не установлен исход. "
                                + "Информация не сохранена", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Этапы лечения заполнены некорректно! Выписка невозможна!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (KmiacServerException e1) {
                    JOptionPane.showMessageDialog(null,
                        "Ошибка при выписке пациента. Информация не сохранена", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    JOptionPane.showMessageDialog(null,
                        "Ошибка при выписке пациента. Информация не сохранена", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                    ClientHospital.conMan.reconnect(e1);
                }
            }
        });
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
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty()) || (tfZaklDiagName.getText().isEmpty())) {
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
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано время выписки. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isAllRequiredDeadFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty())
                || (tfZaklDiagName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
//FIXME
//        if ((tfPatalogoAnDiagPcod.getText().isEmpty())
//                || (tfPatalogoAnDiagName.getText().isEmpty())) {
//            JOptionPane.showMessageDialog(null,
//                "Не выбран паталогоанатомический диагноз. Информация не сохранена", "Ошибка",
//                JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано время смерти. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isAllRequiredMovedFieldsSet() {
        if (cbxVidPom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид помощи", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbxVidOpl.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбран вид оплаты. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((tfZaklDiagPcod.getText().isEmpty())
                || (tfZaklDiagName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null,
                "Не выбран заключительный диагноз. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklDate.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрана дата перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cdeZaklTime.getTime() == null) {
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
        if (cbxAnotherOtd.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Не выбрано отделения для перевода. Информация не сохранена", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
                ClientHospital.authInfo.getCpodr(), ClientHospital.authInfo.getCslu(),
                (inCtf.getText().length() < 3)
                ? null : '%' + inCtf.getText() + '%');
            inTicl.setData(intClassif);
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
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

    @Override
    public void patientChanged() {
        // TODO Auto-generated method stub
        
    }

    private void setZaklPanelGroupLayout() {
        GroupLayout glPZakl = new GroupLayout(this);
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
        setLayout(glPZakl);
    }
}

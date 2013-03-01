package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import ru.nkz.ivcgzo.clientHospital.controllers.PersonalInfoController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import javax.swing.Box;
import javax.swing.JScrollPane;

public class PersonalInfoPanel extends JSplitPane implements IPatientObserver {
    private static final long serialVersionUID = 5266597523898831944L;
    private IHospitalModel model;
    private PersonalInfoController controller;

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("hh:mm");
    private static final String TOOLTIP_TEXT =
            "<html><b>Панель персональной информации</b> - позволяет просматривать: "
            + "<ul><li>паспортные данные выбранного пациента стационара,"
            + "<li>информацию из приемного отделения о выбранном пациенте стационара.</ul></html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/personalInfo.png");
    private static final String TITLE = "Персональная информация";
    private static final int TEXT_COMPONENT_HEIGHT = 25;
    private static final int TEXT_COMPONENT_WIDTH = 320;
    private static final int LABEL_WIDTH = 150;

    private JPanel pPersonalInfo;
    private JTextField tfNumberOfDesiaseHistory;
    private JTextField tfSurname;
    private JTextField tfName;
    private JTextField tfMiddlename;
    private JTextField tfGender;
    private JTextField tfBirthdate;
    private JTextField tfOms;
    private JTextField tfDms;
    private JTextField tfChamber;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxOtdProf;
    private JTextField tfWork;
    private JTextField tfRegistrationAddress;
    private JTextField tfRealAddress;
    private JButton btnUpdateChamber;
    private JPanel pReceptionInfo;
    private JTextPane textPane;
    private JScrollPane scrollPane;

    public PersonalInfoPanel(final PersonalInfoController inController,
            final IHospitalModel inModel) {
        super();
        this.controller = inController;
        this.model = inModel;
        model.registerPatientObserver((IPatientObserver) this);
        setPersonalInfoPanel();
        setReceptionPanel();
    }

    private void setPersonalInfoPanel() {
        pPersonalInfo = new JPanel();
        setLeftComponent(pPersonalInfo);
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
        cbxOtdProf.setSelectedIndex(-1);
        tfChamber.setText("");
        cbxOtdProf.setText("");
        tfWork.setText("");
        tfRegistrationAddress.setText("");
        tfRealAddress.setText("");
    }

    private void fillPersonalInfoTextFields() {
        if (model.getPatient() != null) {
            tfNumberOfDesiaseHistory.setText(String.valueOf(model.getPatient().getNist()));
            tfSurname.setText(model.getPatient().getSurname());
            tfName.setText(model.getPatient().getName());
            tfMiddlename.setText(model.getPatient().getMiddlename());
            tfGender.setText(model.getPatient().getGender());
            tfBirthdate.setText(DEFAULT_DATE_FORMAT.format(model.getPatient().getBirthDate()));
            tfOms.setText(model.getPatient().getOms());
            tfDms.setText(model.getPatient().getDms());
            tfChamber.setText(model.getPatient().getChamber());
            if (model.getPatient().getStatus() != 0) {
                cbxOtdProf.setSelectedPcod(model.getPatient().getStatus());
            }
            tfWork.setText(model.getPatient().getJob());
            tfRegistrationAddress.setText(model.getPatient().getRegistrationAddress());
            tfRealAddress.setText(model.getPatient().getRealAddress());
        }
    }

    private void setReceptionPanel() {
        pReceptionInfo = new JPanel();
        setRightComponent(pReceptionInfo);
        pReceptionInfo.setLayout(new BoxLayout(pReceptionInfo, BoxLayout.X_AXIS));
        scrollPane = new JScrollPane();
        pReceptionInfo.add(scrollPane);

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        scrollPane.setViewportView(textPane);
    }

    private void clearPriemInfoText() {
        textPane.setText("");
    }

    private void fillReceptionPanel() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<html>");
        strBuilder.append(fillPriemInfo());
        strBuilder.append(fillPervOsmotr());
        strBuilder.append("</html>");
        textPane.setText(strBuilder.toString());
    }

    private String fillPriemInfo() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<b><u>Информация о пациенте из приёмного отделения:</u></b><br>");
        if (model.getPriemInfo() != null) {
            if (model.getPriemInfo().getPl_extr() != null) {
                strBuilder.append(
                    String.format("<br><b>Форма обращения в приёмное отделение:</b> %s",
                        model.getPriemInfo().getPl_extr())
                );
            }
            if (model.getPriemInfo().getDatap() != 0) {
                strBuilder.append(
                    String.format("<br><b>Дата поступления:</b> %s",
                        DEFAULT_DATE_FORMAT.format(model.getPriemInfo().getDatap()))
                );
            }
            if (model.getPriemInfo().getDataosm() != 0) {
                strBuilder.append(
                    String.format("<br><b>Дата осмотра:</b> %s",
                        DEFAULT_DATE_FORMAT.format(model.getPriemInfo().getDataosm()))
                );
            }
            if ((model.getPriemInfo().getNaprav() != null)
                    && (model.getPriemInfo().getNOrg() != null)) {
                strBuilder.append(
                    String.format("<br><b>Кем направлен:</b> %s %s",
                        model.getPriemInfo().getNaprav(),
                        model.getPriemInfo().getNOrg())
                );
            }
            if ((model.getPriemInfo().getDiagN() != null)
                    && (model.getPriemInfo().getDiagNtext() != null)) {
                strBuilder.append(
                    String.format("<br><b>Диагноз напр. учреждения:</b> %s %s",
                        model.getPriemInfo().getDiagN(),
                        model.getPriemInfo().getDiagNtext())
                );
            }
            if ((model.getPriemInfo().getDiagP() != null)
                    && (model.getPriemInfo().getDiagPtext() != null)) {
                strBuilder.append(
                    String.format("<br><b>Диагноз приёмного отделения:</b> %s %s",
                        model.getPriemInfo().getDiagP(),
                        model.getPriemInfo().getDiagPtext())
                );
            }
            if (model.getPriemInfo().getT0c() != null) {
                strBuilder.append(
                    String.format("<br><b>Температура:</b> %s", model.getPriemInfo().getT0c())
                );
            }
            if (model.getPriemInfo().getAd() != null) {
                strBuilder.append(
                    String.format("<br><b>Давление:</b> %s <br>", model.getPriemInfo().getAd())
                );
            }
        } else {
            strBuilder.append("<b>Нет доступной информации</b>");
        }
        return strBuilder.toString();
    }



    private String fillPervOsmotr() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<br><br><b><u>Первичный осмотр в приемном отделении:</u></b><br>");
        if (model.getPerOsmotr() != null) {
            if (model.getPerOsmotr().getMorbi() != null) {
                strBuilder.append(
                    String.format("<br><b>История болезни:</b> %s",
                        model.getPerOsmotr().getMorbi())
                );
            }
            if (model.getPerOsmotr().getJalob() != null) {
                strBuilder.append(
                    String.format("<br><b>Жалобы:</b> %s",
                            model.getPerOsmotr().getJalob())
                );
            }
            if (model.getPerOsmotr().getStatusPraesense() != null) {
                strBuilder.append(
                    String.format("<br><b>Объективный статус:</b> %s",
                        model.getPerOsmotr().getStatusPraesense())
                );
            }
            if (model.getPerOsmotr().getStatusLocalis() != null) {
                strBuilder.append(
                    String.format("<br><b>Локальный статус:</b> %s",
                        model.getPerOsmotr().getStatusLocalis())
                );
            }
            if (model.getPerOsmotr().getFisicalObs() != null) {
                strBuilder.append(
                    String.format("<br><b>Физикальное обследование:</b> %s",
                        model.getPerOsmotr().getFisicalObs())
                );
            }
            if (model.getPerOsmotr().getDataz() != 0) {
                strBuilder.append(
                    String.format("<br><b>Дата осмотра:</b> %s",
                        DEFAULT_DATE_FORMAT.format(model.getPerOsmotr().getDataz()))
                );
            }
            if (model.getPerOsmotr().getTimez() != 0) {
                strBuilder.append(
                    String.format("<br><b>Время осмотра:</b> %s",
                        DEFAULT_TIME_FORMAT.format(model.getPerOsmotr().getTimez()))
                );
            }
        } else {
            strBuilder.append("<b>Нет доступной информации</b>");
        }
        return strBuilder.toString();
    }

    public final void setOtdProfList(final List<IntegerClassifier> otdProfList) {
        cbxOtdProf.setData(otdProfList);
    }

    @Override
    public final void patientChanged() {
        clearPatientText();
        fillPersonalInfoTextFields();
        clearPriemInfoText();
        fillReceptionPanel();
    }

    private void setPatientInfoPanelGroupLayout() {
        pPersonalInfo.setLayout(new BoxLayout(pPersonalInfo, BoxLayout.Y_AXIS));

        tfNumberOfDesiaseHistory = new JTextField();
        addPatientInfoHorizontalBox("Номер истории болезни", tfNumberOfDesiaseHistory);

        tfSurname = new JTextField();
        tfSurname.setEditable(false);
        addPatientInfoHorizontalBox("Фамилия", tfSurname);

        tfName = new JTextField();
        tfName.setEditable(false);
        addPatientInfoHorizontalBox("Имя", tfName);

        tfMiddlename = new JTextField();
        tfMiddlename.setEditable(false);
        addPatientInfoHorizontalBox("Отчество", tfMiddlename);

        tfGender = new JTextField();
        tfGender.setEditable(false);
        addPatientInfoHorizontalBox("Пол", tfGender);

        tfBirthdate = new JTextField();
        tfBirthdate.setEditable(false);
        addPatientInfoHorizontalBox("Дата рождения", tfBirthdate);

        tfOms = new JTextField();
        tfOms.setEditable(false);
        addPatientInfoHorizontalBox("Полис ОМС", tfOms);

        tfDms = new JTextField();
        tfDms.setEditable(false);
        addPatientInfoHorizontalBox("Полис ДМС", tfDms);

        cbxOtdProf = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxOtdProf.setEditable(false);
        addPatientInfoHorizontalBox("Профиль отделения", cbxOtdProf);

        tfChamber = new JTextField();
        addPatientInfoHorizontalBox("Номер палаты", tfChamber);

        tfWork = new JTextField();
        tfWork.setEditable(false);
        addPatientInfoHorizontalBox("Место работы", tfWork);

        tfRegistrationAddress = new JTextField();
        tfRegistrationAddress.setEditable(false);
        addPatientInfoHorizontalBox("Адрес прописки", tfRegistrationAddress);

        tfRealAddress = new JTextField();
        tfRealAddress.setEditable(false);
        addPatientInfoHorizontalBox("Адрес проживания", tfRealAddress);

        btnUpdateChamber = new JButton("Сохранить");
        btnUpdateChamber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.updatePatient(
                      tfChamber.getText(),
                      cbxOtdProf.getSelectedPcod()
                );
            }
        });
        addPatientInfoHorizontalBox("", btnUpdateChamber);
    }

    private void addPatientInfoHorizontalBox(final String title, final JComponent component) {
        Box hzBox = Box.createHorizontalBox();
        final int minHzBoxWidth = 510;
        hzBox.setMinimumSize(new Dimension(minHzBoxWidth, TEXT_COMPONENT_HEIGHT));
        hzBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_COMPONENT_HEIGHT));
        hzBox.setAlignmentX(LEFT_ALIGNMENT);

        final int strutHorizontalSize = 5;
        hzBox.add(Box.createHorizontalStrut(strutHorizontalSize));

        JLabel label = new JLabel(title);
        component.setPreferredSize(new Dimension(LABEL_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setSize(new Dimension(LABEL_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setMinimumSize(new Dimension(LABEL_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setMaximumSize(new Dimension(LABEL_WIDTH, TEXT_COMPONENT_HEIGHT));
        label.setAlignmentX(LEFT_ALIGNMENT);
        hzBox.add(new JLabel(title));

        final int leftColumnMaxWidth = 180;
        hzBox.add(Box.createHorizontalStrut(
                (int) (leftColumnMaxWidth - label.getPreferredSize().getWidth())));

        component.setPreferredSize(new Dimension(TEXT_COMPONENT_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setSize(new Dimension(TEXT_COMPONENT_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setMinimumSize(new Dimension(TEXT_COMPONENT_WIDTH, TEXT_COMPONENT_HEIGHT));
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_COMPONENT_HEIGHT));
        hzBox.add(component);

        pPersonalInfo.add(hzBox);
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
}

package ru.nkz.ivcgzo.clientReception;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.Color;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftReception.PoliclinicNotFoundException;
import ru.nkz.ivcgzo.thriftReception.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftReception.VrachNotFoundException;

/**
 * @author as
 */
public class TalonSelectFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    // Tables
    private JTable tbTalonSelect;
    private JTable tbTalonDelete;
    // Panels
    private JPanel pnPatientInfo;
    private JPanel pnTalonType;
    private JPanel pnInformation;
    // Labels
    private JLabel lblIdHeader;
    private JLabel lblId;
    private JLabel lblSurnameHeader;
    private JLabel lblSurname;
    private JLabel lblNameHeader;
    private JLabel lblName;
    private JLabel lblMiddlenameHeader;
    private JLabel lblMiddlename;
    private JLabel lblBirthdateHeader;
    private JLabel lblBirthdate;
    // ComboBoxes
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxPoliclinic;
    private ThriftStringClassifierCombobox<StringClassifier> cbxSpeciality;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDoctor;
    // Buttons
    private JButton btnUpdate;
    // Panes
    private JSplitPane splitPane;
    private JTabbedPane tbpTalonOperations;
    private JScrollPane pnTalonSelect;
    private JScrollPane pnTalonDelete;
    // Layouts
    private GroupLayout glPnPatientInfo;
    private GroupLayout glPnTalonType;

    public TalonSelectFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.3);
        fillSplitPane();

        getContentPane().add(splitPane);

    }

    private void fillSplitPane() {
        pnInformation = new JPanel();
        fillInformationPanel();
        splitPane.setLeftComponent(pnInformation);

        tbpTalonOperations = new JTabbedPane(JTabbedPane.TOP);
        fillTalonTabbedPane();
        splitPane.setRightComponent(tbpTalonOperations);
    }

    private void fillInformationPanel() {
        pnInformation.setLayout(new BoxLayout(pnInformation, BoxLayout.Y_AXIS));

        pnPatientInfo = new JPanel();
        pnPatientInfo.setBorder(new TitledBorder(null, "Информация о пациенте",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        fillPatientInfoPanel();
        pnInformation.add(pnPatientInfo);

        pnTalonType = new JPanel();
        pnTalonType.setBorder(new TitledBorder(null, "Выбор специалиста", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        pnTalonType.setBackground(new Color(153, 153, 255));
        fillTalonTypePanel();
        pnInformation.add(pnTalonType);
    }

    private void fillPatientInfoPanel() {
        lblIdHeader = new JLabel("Персональный номер");
        lblId = new JLabel("13");
        lblSurnameHeader = new JLabel("Фамилия");
        lblSurname = new JLabel("Иванов");
        lblNameHeader = new JLabel("Имя");
        lblName = new JLabel("Иван");
        lblMiddlenameHeader = new JLabel("Отечество");
        lblMiddlename = new JLabel("Иванович");
        lblBirthdateHeader = new JLabel("Дата рождения");
        lblBirthdate = new JLabel("12.05.2006");
        glPnPatientInfo = new GroupLayout(pnPatientInfo);
        glPnPatientInfo.setAutoCreateContainerGaps(true);
        fillPatientInfoGroupLayout();
        pnPatientInfo.setLayout(glPnPatientInfo);
    }

    private void fillPatientInfoGroupLayout() {
        glPnPatientInfo.setHorizontalGroup(
            glPnPatientInfo.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, glPnPatientInfo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblBirthdateHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                            .addComponent(lblBirthdate))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblMiddlenameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                            .addComponent(lblMiddlename))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblNameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                            .addComponent(lblName))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblSurnameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                            .addComponent(lblSurname))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(
                                    lblIdHeader, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblId)))
                        .addContainerGap())
        );
        glPnPatientInfo.setVerticalGroup(
            glPnPatientInfo.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnPatientInfo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblIdHeader, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblSurnameHeader)
                        .addComponent(lblSurname))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNameHeader)
                        .addComponent(lblName))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblMiddlenameHeader)
                        .addComponent(lblMiddlename))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPnPatientInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblBirthdateHeader, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBirthdate))
                    .addGap(173))
        );
    }

    private void fillTalonTypePanel() {
        cbxPoliclinic = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxSpeciality = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxDoctor = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        btnUpdate = new JButton("Обновить");
        glPnTalonType = new GroupLayout(pnTalonType);
        fillTalonTypeGroupLayout();
        pnTalonType.setLayout(glPnTalonType);
    }

    private void fillTalonTypeGroupLayout() {
        glPnTalonType.setHorizontalGroup(
            glPnTalonType.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnTalonType.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPnTalonType.createParallelGroup(Alignment.TRAILING)
                        .addComponent(btnUpdate, Alignment.LEADING,
                                GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                        .addComponent(cbxDoctor, Alignment.LEADING, 0, 252, Short.MAX_VALUE)
                        .addComponent(
                                cbxSpeciality, Alignment.LEADING, 0, 252, Short.MAX_VALUE)
                        .addComponent(
                                cbxPoliclinic, Alignment.LEADING, 0, 252, Short.MAX_VALUE))
                    .addContainerGap())
        );
        glPnTalonType.setVerticalGroup(
            glPnTalonType.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnTalonType.createSequentialGroup()
                    .addGap(20)
                    .addComponent(cbxPoliclinic, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(cbxSpeciality, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(cbxDoctor, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(37)
                    .addComponent(btnUpdate)
                    .addContainerGap(86, Short.MAX_VALUE))
        );
    }

    private void fillTalonTabbedPane() {
        pnTalonSelect = new JScrollPane();
        tbpTalonOperations.addTab("Свободные талоны", null, pnTalonSelect, null);
        fillTalonSelectPane();

        pnTalonDelete = new JScrollPane();
        tbpTalonOperations.addTab("Занятые талоны", null, pnTalonDelete, null);
        fillTalonDeletePane();
    }

    private void fillTalonSelectPane() {
        tbTalonSelect = new JTable();
        pnTalonSelect.setViewportView(tbTalonSelect);
    }

    private void fillTalonDeletePane() {
        tbTalonDelete = new JTable();
        pnTalonDelete.setViewportView(tbTalonDelete);
    }

    public final void fillPatientInfoLabels(final int patientId, final String patientSurname,
            final String patientName, final String patientMiddlename, final long patientBirthdate) {
        lblId.setText(String.valueOf(patientId));
        lblSurname.setText(patientSurname);
        lblName.setText(patientName);
        lblMiddlename.setText(patientMiddlename);
        lblBirthdate.setText(new Date(patientBirthdate).toString());

    }

    public final void onConnect() {
        fillTalonTypeComboboxes();
    }

    private void fillTalonTypeComboboxes() {
        try {
            cbxPoliclinic.setData(MainForm.tcl.getPoliclinic());
            cbxPoliclinic.setSelectedIndex(0);
            cbxSpeciality.setData(MainForm.tcl.getSpec(cbxPoliclinic.getSelectedItem().getPcod()));
            cbxSpeciality.setSelectedIndex(0);
            cbxDoctor.setData(
                MainForm.tcl.getVrach(
                    cbxPoliclinic.getSelectedItem().getPcod(),
                    cbxSpeciality.getSelectedItem().getPcod()
                )
            );
        } catch (KmiacServerException | PoliclinicNotFoundException
                | TException | SpecNotFoundException | VrachNotFoundException e) {
            e.printStackTrace();
        }
    }
}

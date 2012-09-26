package ru.nkz.ivcgzo.clientReception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftReception.Patient;
import ru.nkz.ivcgzo.thriftReception.PoliclinicNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.VrachNotFoundException;

/**
 * @author as
 */
public class TalonSelectFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
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
    private JLabel lblPoliclinicCbx;
    private JLabel lblSpecialityCbx;
    private JLabel lblDoctorCbx;
    // ComboBoxes
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxPoliclinic;
    private ThriftStringClassifierCombobox<StringClassifier> cbxSpeciality;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxDoctor;
    // Panes
    private JSplitPane splitPane;
    private JTabbedPane tbpTalonOperations;
    private JScrollPane pnTalonSelect;
    private JScrollPane pnTalonDelete;
    // Layouts
    private GroupLayout glPnPatientInfo;
    private GroupLayout glPnTalonType;
    //Patient
    private Patient curPatient;
    private UserAuthInfo curDoctorInfo;
    private JButton btnBackward;
    private JButton btnForward;
    private JLabel lblWeekNevigation;

    public TalonSelectFrame(final UserAuthInfo authInfo) {
        curDoctorInfo = authInfo;
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(950, 600));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        splitPane = new JSplitPane();
        splitPane.setResizeWeight(0);
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
        tbpTalonOperations.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent e) {
                switch (tbpTalonOperations.getSelectedIndex()) {
                    case 0:
                        refreshTalonTableModel();
                        break;
                    case 1:
                        refreshReservedTalonTableModel();
                        break;
                    default:
                        break;
                }
            }
        });
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
                                    ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                            .addComponent(lblBirthdate))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblMiddlenameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                            .addComponent(lblMiddlename))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblNameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                            .addComponent(lblName))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(lblSurnameHeader)
                            .addPreferredGap(
                                    ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                            .addComponent(lblSurname))
                        .addGroup(glPnPatientInfo.createSequentialGroup()
                            .addComponent(
                                    lblIdHeader, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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
        addCbxLabels();
        addPoliclinicComboboxes();
        addSpecialityComboboxes();
        addDoctorComboboxes();

        addNavigationButtons();

        fillTalonTypeGroupLayout();
        pnTalonType.setLayout(glPnTalonType);
    }

    private void addCbxLabels() {
        lblPoliclinicCbx = new JLabel("Выберите отделение:");
        lblSpecialityCbx = new JLabel("Выберите специальность:");
        lblDoctorCbx = new JLabel("Выберите врача:");
        lblWeekNevigation = new JLabel("Изменить неделю для отображения талонов:");
    }

    private void addPoliclinicComboboxes() {
        cbxPoliclinic = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxPoliclinic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (cbxPoliclinic.getSelectedItem() != null) {
                        cbxSpeciality.setData(
                            MainForm.tcl.getSpec(cbxPoliclinic.getSelectedItem().getPcod())
                        );
                        cbxSpeciality.setSelectedPcod(curDoctorInfo.getCdol());
                    }
                } catch (KmiacServerException | SpecNotFoundException
                        | TException e1) {
                    e1.printStackTrace();
                } catch (RuntimeException re) {
                    cbxSpeciality.setSelectedIndex(0);
                }
            }
        });
    }

    private void addSpecialityComboboxes() {
        cbxSpeciality = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxSpeciality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (cbxSpeciality.getSelectedItem() != null) {
                        cbxDoctor.setData(
                            MainForm.tcl.getVrach(
                                cbxPoliclinic.getSelectedItem().getPcod(),
                                cbxSpeciality.getSelectedItem().getPcod()
                            )
                        );
                        cbxDoctor.setSelectedPcod(curDoctorInfo.getPcod());
                    }
                } catch (KmiacServerException | VrachNotFoundException
                        | TException e1) {
                    e1.printStackTrace();
                } catch (RuntimeException re) {
                    cbxDoctor.setSelectedIndex(0);
                }
            }
        });
    }

    private void addDoctorComboboxes() {
        cbxDoctor = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (cbxDoctor.getSelectedItem() != null) {
                    refreshTalonTableModel();
                }
                if ((cbxDoctor.getSelectedItem() != null) && (curPatient != null))  {
                    refreshReservedTalonTableModel();
                }
            }
        });
    }

    private void addNavigationButtons() {
        btnBackward = new JButton("Пред. неделя");
        btnBackward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ((TalonTableModel) tbTalonSelect.getModel()).setPrevWeek();
                tbTalonSelect.repaint();
                pnTalonSelect.updateUI();
            }
        });

        btnForward = new JButton("След. неделя");
        btnForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ((TalonTableModel) tbTalonSelect.getModel()).setNextWeek();
                tbTalonSelect.repaint();
                pnTalonSelect.updateUI();
            }
        });
    }

    private void fillTalonTypeGroupLayout() {
        glPnTalonType = new GroupLayout(pnTalonType);
        glPnTalonType.setHorizontalGroup(
            glPnTalonType.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnTalonType.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPnTalonType.createParallelGroup(Alignment.LEADING)
                        .addComponent(cbxDoctor, 0, 375, Short.MAX_VALUE)
                        .addComponent(cbxSpeciality, 0, 375, Short.MAX_VALUE)
                        .addComponent(cbxPoliclinic, 0, 375, Short.MAX_VALUE)
                        .addGroup(glPnTalonType.createSequentialGroup()
                            .addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 181,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
                            .addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 173,
                                    GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblPoliclinicCbx)
                        .addComponent(lblSpecialityCbx)
                        .addComponent(lblDoctorCbx)
                        .addComponent(lblWeekNevigation))
                    .addContainerGap())
        );
        glPnTalonType.setVerticalGroup(
            glPnTalonType.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnTalonType.createSequentialGroup()
                    .addComponent(lblPoliclinicCbx)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(cbxPoliclinic, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblSpecialityCbx)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(cbxSpeciality, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblDoctorCbx)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(cbxDoctor, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblWeekNevigation)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPnTalonType.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnBackward)
                        .addComponent(btnForward))
                    .addContainerGap(77, Short.MAX_VALUE))
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

    private void refreshTalonTableModel() {
        TalonTableModel tbtModel = new TalonTableModel(
                cbxPoliclinic.getSelectedItem().getPcod(),
                cbxSpeciality.getSelectedItem().getPcod(),
                cbxDoctor.getSelectedItem().getPcod()
        );
        tbTalonSelect.setModel(tbtModel);
    }

    private void fillTalonSelectPane() {
        tbTalonSelect = new JTable();
        tbTalonSelect.setDefaultRenderer(String.class, new TalonTableCellRenderer());
        tbTalonSelect.setRowHeight(50);
        tbTalonSelect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) { //TODO отрефакторить
                JTable curTable = (JTable) e.getSource();
                final int curRow = curTable.getSelectedRow();
                final int curColumn = curTable.getSelectedColumn();
                final int indexOfSelectedOption = JOptionPane.showConfirmDialog(
                        TalonSelectFrame.this,
                        String.format("Записать на приём %s?", curTable.getColumnName(curColumn)),
                        "Выбор талона", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (indexOfSelectedOption  == 0) {
                    Talon curTalon = ((TalonTableModel) curTable.getModel()).getTalonList()
                        .getTalonByDay(curRow, curColumn);
                    try {
                        if ((curTalon != null) && (curPatient != null)) {
                            MainForm.tcl.reserveTalon(curPatient, curTalon);
                            refreshTalonTableModel();
                        } else if (curPatient == null) {
                            JOptionPane.showMessageDialog(
                                    TalonSelectFrame.this, "Пациент не выбран",
                                    "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (KmiacServerException
                            | ReserveTalonOperationFailedException | TException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        pnTalonSelect.setViewportView(tbTalonSelect);
    }

    private void refreshReservedTalonTableModel() {
        ReservedTalonTableModel resTbtModel = new ReservedTalonTableModel(
                cbxPoliclinic.getSelectedItem().getPcod(),
                cbxSpeciality.getSelectedItem().getPcod(),
                cbxDoctor.getSelectedItem().getPcod(),
                curPatient.getId()
        );
        tbTalonDelete.setModel(resTbtModel);
    }

    private void fillTalonDeletePane() {
        tbTalonDelete = new JTable();
        tbTalonDelete.setDefaultRenderer(String.class, new ReservedTalonTableCellRenderer());
        tbTalonDelete.setRowHeight(50);
        tbTalonDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) { //TODO отрефакторить
                JTable curTable = (JTable) e.getSource();
                final int curRow = curTable.getSelectedRow();
                final int indexOfSelectedOption = JOptionPane.showConfirmDialog(
                        TalonSelectFrame.this, "Удалить запись?",
                        "Удаление талона", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (indexOfSelectedOption  == 0) {
                    Talon curTalon = ((ReservedTalonTableModel) curTable.getModel())
                            .getReservedTalonList().get(curRow);
                    try {
                        if ((curTalon != null) && (curPatient != null)) {
                            MainForm.tcl.releaseTalon(curTalon);
                            refreshReservedTalonTableModel();
                        } else if (curPatient == null) {
                            JOptionPane.showMessageDialog(
                                    TalonSelectFrame.this, "Пациент не выбран",
                                    "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (KmiacServerException
                            | TException | ReleaseTalonOperationFailedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        pnTalonDelete.setViewportView(tbTalonDelete);
    }

    public final void fillPatientInfoLabels(final int patientId, final String patientSurname,
            final String patientName, final String patientMiddlename, final long patientBirthdate,
            final int idPvizit) {
        lblId.setText(String.valueOf(patientId));
        lblSurname.setText(patientSurname);
        lblName.setText(patientName);
        lblMiddlename.setText(patientMiddlename);
        lblBirthdate.setText(DEFAULT_DATE_FORMAT.format(new Date(patientBirthdate)));
        curPatient = new Patient(patientId, patientSurname, patientName, patientMiddlename,
                idPvizit);
    }

    public final void onConnect() {
        fillTalonTypeComboboxes();
    }

    private void fillTalonTypeComboboxes() {
        try {
            cbxPoliclinic.setData(MainForm.tcl.getPoliclinic());
            cbxPoliclinic.setSelectedPcod(curDoctorInfo.getCpodr());
        } catch (KmiacServerException | PoliclinicNotFoundException
                | TException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            cbxPoliclinic.setSelectedIndex(0);
        }
    }
}

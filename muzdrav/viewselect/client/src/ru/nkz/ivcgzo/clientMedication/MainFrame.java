package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMedication.Lek;
import ru.nkz.ivcgzo.thriftMedication.Patient;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import org.apache.thrift.TException;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Dimension;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8573682902821548961L;
//    private UserAuthInfo doctorInfo;
    private Patient patient;
//    private CustomTable<LekPriem, LekPriem._Fields> tbMedication;
//    private JScrollPane spMedicationTable;
    private JPanel pButtons;
    private JButton btnAdd;
//    private JButton btnUpdate;
    private JButton btnDelete;
    private MedicationCatalogFrame frmMedicationCatalog;
    private ThriftIntegerClassifierList lMedication;
    private JScrollPane spMedicationList;
    private JPanel pMedicationInfo;
    private JPanel pInfo;
    private JLabel lblCountInDay;
    private JLabel lblFormLek;
    private JLabel lblDose;
    private JLabel lblInputMethod;
    private JLabel lblPeriod;
    private JTextField tfFormLek;
    private JTextField tfDose;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxInputMethod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxPeriod;
    private JTextField tfCountInDay;
    private JLabel lblDateFrom;
    private CustomDateEditor cdeDateFrom;
    private JLabel lblVrachFrom;
    private JTextField tfVrachFrom;
    private JLabel lblDateTo;
    private CustomDateEditor cdeDateTo;
    private JLabel lblVrachTo;
    private JTextField tfVrachTo;
    private Lek curLek;

    public MainFrame(final UserAuthInfo authInfo) {
//        doctorInfo = authInfo;
        initialization();
    }

    private void initialization() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientMedication/resources/medication.png")));

        addMedicationPanels();
//        addTableScrollPane();
        addButtonPanel();
    }

    private void addMedicationPanels() {
        pMedicationInfo = new JPanel();
        getContentPane().add(pMedicationInfo);
        pMedicationInfo.setLayout(new BoxLayout(pMedicationInfo, BoxLayout.Y_AXIS));

        addMedicationListScrollPane();
        addMedicationInfoPanel();
    }

    private void addMedicationListScrollPane() {
        spMedicationList = new JScrollPane();
        pMedicationInfo.add(spMedicationList);
        lMedication = new ThriftIntegerClassifierList();
        lMedication.addListSelectionListener(new ListSelectionListener() {            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {  
                    if (lMedication.getSelectedValue() != null) {
                        try {
                            curLek = ClientMedication.tcl.getLek(lMedication.getSelectedPcod());
                            if (curLek != null) {
                                fillCurLekTextFields();
                            }
                        } catch (KmiacServerException e1) {
                            e1.printStackTrace();
                        } catch (TException e1) {
                            e1.printStackTrace();
                            ClientMedication.conMan.reconnect(e1);
                        }        
                    }
                }
            }
        });
        spMedicationList.setViewportView(lMedication);
    }
	
    private void fillCurLekTextFields() {  
        if (curLek.isSetFlek()) {
            tfFormLek.setText(curLek.getFlek());
        }

        if (curLek.isSetDoza()) {
            tfDose.setText(String.valueOf(curLek.getDoza()));
        }

        if (curLek.isSetSposv()) {
            cbxInputMethod.setSelectedPcod(curLek.getSposv());
        }

        if (curLek.isSetSpriem()) {
            cbxPeriod.setSelectedPcod(curLek.getSpriem());
        }

        if (curLek.isSetPereod()) {
            tfCountInDay.setText(String.valueOf(curLek.getPereod()));
        }

        if (curLek.isSetDatan()) {
            cdeDateFrom.setDate(curLek.getDatan());
        }

        if (curLek.isSetVrach()) {
            tfVrachFrom.setText(String.valueOf(curLek.getVrach()));
        }

        if (curLek.isSetDatao()) {
            cdeDateTo.setDate(curLek.getDatao());
        }

        if (curLek.isSetVracho()) {
            tfVrachTo.setText(String.valueOf(curLek.getVracho()));
        }
    }

    private void addMedicationInfoPanel() {
        pInfo = new JPanel();
        pInfo.setMaximumSize(new Dimension(32767, 250));
        pMedicationInfo.add(pInfo);
        pInfo.setLayout(new GridLayout(9, 2, 0, 0));

        lblFormLek = new JLabel("  Форма выпуска");
        pInfo.add(lblFormLek);

        tfFormLek = new JTextField();
        tfFormLek.setEditable(false);
        pInfo.add(tfFormLek);
        tfFormLek.setColumns(10);

        lblDose = new JLabel("  Доза");
        pInfo.add(lblDose);

        tfDose = new JTextField();
        tfDose.setEditable(false);
        pInfo.add(tfDose);
        tfDose.setColumns(10);

        lblInputMethod = new JLabel("  Способ ввода");
        pInfo.add(lblInputMethod);

        cbxInputMethod =
            new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_svl);
        cbxInputMethod.setEditable(false);
        pInfo.add(cbxInputMethod);

        lblPeriod = new JLabel("  Периодичность приёма");
        pInfo.add(lblPeriod);

        cbxPeriod =
            new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_period);
        cbxPeriod.setEditable(false);
        pInfo.add(cbxPeriod);

        lblCountInDay = new JLabel("  Раз в день");
        pInfo.add(lblCountInDay);

        tfCountInDay = new JTextField();
        tfCountInDay.setEditable(false);
        pInfo.add(tfCountInDay);
        tfCountInDay.setColumns(10);

        lblDateFrom = new JLabel("  Дата назначения");
        pInfo.add(lblDateFrom);

        cdeDateFrom = new CustomDateEditor();
        cdeDateFrom.setEditable(false);
        pInfo.add(cdeDateFrom);
        cdeDateFrom.setColumns(10);

        lblVrachFrom = new JLabel("  Назначивший врач");
        pInfo.add(lblVrachFrom);

        tfVrachFrom = new JTextField();
        tfVrachFrom.setEditable(false);
        pInfo.add(tfVrachFrom);
        tfVrachFrom.setColumns(10);

        lblDateTo = new JLabel("  Дата отмены");
        pInfo.add(lblDateTo);

        cdeDateTo = new CustomDateEditor();
        cdeDateTo.setEditable(false);
        pInfo.add(cdeDateTo);
        cdeDateTo.setColumns(10);

        lblVrachTo = new JLabel("  Отменивший врач");
        pInfo.add(lblVrachTo);

        tfVrachTo = new JTextField();
        tfVrachTo.setEditable(false);
        pInfo.add(tfVrachTo);
        tfVrachTo.setColumns(10);
    }

//    private void addTableScrollPane() {
//        spMedicationTable = new JScrollPane();
//        getContentPane().add(spMedicationTable);
//
//        addMedicationTable();
//    }
//
//    private void addMedicationTable() {
//        tbMedication = new CustomTable<LekPriem, LekPriem._Fields>(
//            false, false, LekPriem.class, 2, "Дата приёма", 4, "Статус приёма");
//        tbMedication.setEditableFields(true, 0);
//        spMedicationTable.setViewportView(tbMedication);
//    }

    private void addButtonPanel() {
        pButtons = new JPanel();
        getContentPane().add(pButtons);

        addAddButton();
        addUpdateButton();
        addDeleteButton();
    }

    private void addAddButton() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        btnAdd = new JButton("Добавить");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmMedicationCatalog.prepareForm(patient);
                frmMedicationCatalog.setVisible(true);
            }
        });
        pButtons.add(btnAdd);
    }

    private void addUpdateButton() {
//        btnUpdate = new JButton("Отменить приём");
//        pButtons.add(btnUpdate);
    }

    private void addDeleteButton() {
        btnDelete = new JButton("Удалить");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (lMedication.getSelectedValue() != null) {
                    try {
                        ClientMedication.tcl.deleteLek(lMedication.getSelectedPcod());
                        prepareFrame();
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        e1.printStackTrace();
                        ClientMedication.conMan.reconnect(e1);
                    }
                }
            }
        });
        pButtons.add(btnDelete);
    }

    public final void fillPatient(final int id, final String surname,
            final String name, final String middlename, final int idGosp) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        patient.setIdGosp(idGosp);
    }

    private void createModalFrames(Patient patient) {
        frmMedicationCatalog = new MedicationCatalogFrame();
        frmMedicationCatalog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                prepareFrame();
            }
        });
        frmMedicationCatalog.pack();
    }

    public final void prepareFrame() {
        if (patient != null) {
            try {
                clearTextFields();
                lMedication.getSelectionModel().clearSelection();
                lMedication.setSelectedIndex(0);
                List<IntegerClassifier> tmpLekShortList =
                    ClientMedication.tcl.getLekShortList(patient.getIdGosp());
                lMedication.setData(tmpLekShortList);                
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
                ClientMedication.conMan.reconnect(e);
            }
        }
    }

    public final void onConnect() {
        createModalFrames(patient);
        if (patient != null) {
            try {
                List<IntegerClassifier> tmpLekShortList =
                    ClientMedication.tcl.getLekShortList(patient.getIdGosp());
                lMedication.setData(tmpLekShortList);               
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
                ClientMedication.conMan.reconnect(e);
            }
        }
    }

    private final void clearTextFields() {
        tfCountInDay.setText("");
        tfDose.setText("");
        tfFormLek.setText("");
        tfVrachFrom.setText("");
        tfVrachTo.setText("");
        cbxInputMethod.setSelectedIndex(-1);
        cbxPeriod.setSelectedIndex(-1);
        cdeDateFrom.setText(null);
        cdeDateTo.setText(null);
    }
}

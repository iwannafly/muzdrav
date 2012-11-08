package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMedication.LekPriem;
import ru.nkz.ivcgzo.thriftMedication.Patient;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8573682902821548961L;
//    private UserAuthInfo doctorInfo;
    private Patient patient;
    private CustomTable<LekPriem, LekPriem._Fields> tbMedication;
    private JScrollPane spMedicationTable;
    private JPanel pButtons;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private MedicationCatalogFrame frmMedicationCatalog;
    private ThriftIntegerClassifierList lMedication;
    private JScrollPane spMedicationList;
    private JPanel pMedicationInfo;
    private JPanel pInfo;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    public MainFrame(final UserAuthInfo authInfo) {
//        doctorInfo = authInfo;
        initialization();
    }

    private void initialization() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        addMedicationPanels();
        addTableScrollPane();
        addButtonPanel();
    }

    private void addMedicationPanels() {
        pMedicationInfo = new JPanel();
        getContentPane().add(pMedicationInfo);
        pMedicationInfo.setLayout(new BoxLayout(pMedicationInfo, BoxLayout.Y_AXIS));

        addMedicationInfoPanel();
        addMedicationListScrollPane();
    }

    private void addMedicationInfoPanel() {
        pInfo = new JPanel();
        pInfo.setMaximumSize(new Dimension(32767, 250));
        pMedicationInfo.add(pInfo);
        pInfo.setLayout(new GridLayout(5, 2, 0, 0));

        lblNewLabel_1 = new JLabel("New label");
        pInfo.add(lblNewLabel_1);

        textField = new JTextField();
        pInfo.add(textField);
        textField.setColumns(10);

        lblNewLabel_2 = new JLabel("New label");
        pInfo.add(lblNewLabel_2);

        textField_1 = new JTextField();
        pInfo.add(textField_1);
        textField_1.setColumns(10);

        lblNewLabel_3 = new JLabel("New label");
        pInfo.add(lblNewLabel_3);

        textField_2 = new JTextField();
        pInfo.add(textField_2);
        textField_2.setColumns(10);

        lblNewLabel_4 = new JLabel("New label");
        pInfo.add(lblNewLabel_4);

        textField_3 = new JTextField();
        pInfo.add(textField_3);
        textField_3.setColumns(10);

        lblNewLabel = new JLabel("New label");
        pInfo.add(lblNewLabel);

        textField_4 = new JTextField();
        pInfo.add(textField_4);
        textField_4.setColumns(10);
    }

    private void addMedicationListScrollPane() {
        spMedicationList = new JScrollPane();
        pMedicationInfo.add(spMedicationList);
        lMedication = new ThriftIntegerClassifierList();
        spMedicationList.setViewportView(lMedication);
    }

    private void addTableScrollPane() {
        spMedicationTable = new JScrollPane();
        getContentPane().add(spMedicationTable);

        addMedicationTable();
    }

    private void addMedicationTable() {
        tbMedication = new CustomTable<LekPriem, LekPriem._Fields>(
            false, false, LekPriem.class, 2, "Дата приёма", 4, "Статус приёма");
        tbMedication.setEditableFields(true, 0);
        spMedicationTable.setViewportView(tbMedication);
    }

    private void addButtonPanel() {
        pButtons = new JPanel();
        getContentPane().add(pButtons);

        addAddButton();
        addUpdateButton();
        addDeleteButton();
    }

    private void addAddButton() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.Y_AXIS));
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
        btnUpdate = new JButton("Изменить");
        pButtons.add(btnUpdate);
    }

    private void addDeleteButton() {
        btnDelete = new JButton("Удалить");
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
        frmMedicationCatalog.pack();
    }

    public final void onConnect() {
        createModalFrames(patient);
        if (patient != null) {
            try {
                List<IntegerClassifier> tmpLekShortList =
                    ClientMedication.tcl.getLekShortList(patient.getIdGosp());
                if (tmpLekShortList.size() == 0){
                    lMedication.setData(Collections.<IntegerClassifier>emptyList());
                } else {
                    lMedication.setData(tmpLekShortList);
                }
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
                ClientMedication.conMan.reconnect(e);
            }
        }
    }
}

package ru.nkz.ivcgzo.clientMedication;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.Dimension;

import java.awt.Color;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

public class MedicationCatalogFrame extends JDialog{

    private static final long serialVersionUID = -4086993922032539356L;
    private JTextField tfMedicationName;
    private JPanel pMedicationButtons;
    private JButton btnAddMedication;
    private JButton btnCancel;
    private JPanel pMain;
    private JLabel lblMedicationHeader;
    private JScrollPane spMedicationList;
    private JLabel lblMedicationFormHeader;
    private JScrollPane spMedicationFormList;
    private ThriftIntegerClassifierList lMedications;
    private ThriftIntegerClassifierList lMedicationForms;
    private JLabel lblMedicationSearchHeader;
    private ShablonSearchListener medicationSearchListener;
    

    public MedicationCatalogFrame() {
        getContentPane().setBackground(new Color(153, 204, 255));
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
        setPreferredSize(new Dimension(400, 700));
        setSize(new Dimension(400, 700));
        setLocationRelativeTo(null);
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
            (int) ((screenSize.getHeight() - getHeight()) / 2));
    }

    private void addMainPanel() {
        pMain = new JPanel();
        pMain.setBorder(new EtchedBorder(
            EtchedBorder.RAISED, new Color(0, 0, 0), new Color(102, 102, 102)));
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addMedicationSearchHeader();
        addMedicationSearchTextField();
        addMedicationListHeader();
        addMedicationListScrollPane();
        addMedicationFormHeader();
        addMedicationFormScrollPane();
        addButtonPanel();
    }

    private void addMedicationSearchHeader() {
        lblMedicationSearchHeader = new JLabel("Поиск медикамента:");
        lblMedicationSearchHeader.setAlignmentX(0.5f);
        pMain.add(lblMedicationSearchHeader);
    }

    private void addMedicationSearchTextField() {
        tfMedicationName = new JTextField();
        tfMedicationName.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        pMain.add(tfMedicationName);
        tfMedicationName.setMaximumSize(new Dimension(2147483647, 100));
        medicationSearchListener = new ShablonSearchListener();
        tfMedicationName.getDocument().addDocumentListener(medicationSearchListener);
        tfMedicationName.setColumns(10);
    }

    private void addMedicationListHeader() {
        lblMedicationHeader = new JLabel("Список медикаментов:");
        lblMedicationHeader.setAlignmentX(0.5f);
        pMain.add(lblMedicationHeader);
    }

    private void addMedicationListScrollPane() {
        spMedicationList = new JScrollPane();
        spMedicationList.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        pMain.add(spMedicationList);

        addMedicationList();
    }

    private void addMedicationList() {
        lMedications = new ThriftIntegerClassifierList();
        try {
            lMedications.setData(ClientMedication.tcl.getMedications());
        } catch (KmiacServerException e) {
            lMedications.setData(Collections.<IntegerClassifier>emptyList());
        } catch (TException e) {
            lMedications.setData(Collections.<IntegerClassifier>emptyList());
            ClientMedication.conMan.reconnect(e);
        }
        lMedications.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    if ((lMedicationForms != null) && (lMedications.getSelectedValue() != null)) {
                        try {
                            lMedicationForms.setData(
                                ClientMedication.tcl.getMedicationForms(
                                    lMedications.getSelectedPcod()));
                        } catch (KmiacServerException e1) {
                            lMedicationForms.setData(Collections.<IntegerClassifier>emptyList());
                        } catch (TException e1) {
                            lMedicationForms.setData(Collections.<IntegerClassifier>emptyList());
                            ClientMedication.conMan.reconnect(e1);
                        }
                    } else {
                        lMedicationForms.setData(Collections.<IntegerClassifier>emptyList());
                    }
                }
            }
        });
        spMedicationList.setViewportView(lMedications);
    }

    private void addMedicationFormHeader() {
        lblMedicationFormHeader = new JLabel("Формы выпуска выбранного медикамента:");
        lblMedicationFormHeader.setAlignmentX(0.5f);
        pMain.add(lblMedicationFormHeader);
    }

    private void addMedicationFormScrollPane() {
        spMedicationFormList = new JScrollPane();
        spMedicationFormList.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        pMain.add(spMedicationFormList);

        addMedicationFormsList();
    }

    private void addMedicationFormsList() {
        lMedicationForms = new ThriftIntegerClassifierList();
        spMedicationFormList.setViewportView(lMedicationForms);
    }

    private void addButtonPanel() {
        pMedicationButtons = new JPanel();
        pMain.add(pMedicationButtons);
        pMedicationButtons.setMaximumSize(new Dimension(32767, 100));

        addAddButton();
        addCancelButton();
    }

    private void addAddButton() {
        btnAddMedication = new JButton("Выбрать медикамент");
        pMedicationButtons.add(btnAddMedication);
    }

    private void addCancelButton() {
        btnCancel = new JButton("Отмена");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                MedicationCatalogFrame.this.dispatchEvent(new WindowEvent(
                    MedicationCatalogFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        pMedicationButtons.add(btnCancel);
    }

    private class ShablonSearchListener implements DocumentListener {
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
            loadShablonList();
        }
    }

    private void loadShablonList() {
        try {
            List<IntegerClassifier> intClassif =
                ClientMedication.tcl.getMedicationsUsingTemplate(tfMedicationName.getText());
            if (intClassif.size() > 0) {
                lMedications.setData(intClassif);
                lMedications.getSelectionModel().clearSelection();
                lMedications.setSelectedIndex(0);
            } else {
                lMedications.setData(Collections.<IntegerClassifier>emptyList());
                lMedications.getSelectionModel().clearSelection();
            }
        } catch (KmiacServerException e) {
            JOptionPane.showMessageDialog(MedicationCatalogFrame.this,
                "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (TException e) {
            ClientMedication.conMan.reconnect(e);
        }
    }
}

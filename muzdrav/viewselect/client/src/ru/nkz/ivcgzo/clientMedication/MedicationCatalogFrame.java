package ru.nkz.ivcgzo.clientMedication;

import javax.swing.Box;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.Patient;

import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;
import java.awt.Component;

public class MedicationCatalogFrame extends JDialog{

    private static final long serialVersionUID = -4086993922032539356L;
    private JTextField tfMedicationName;
    private JTextField tfMedicationCustomName, tfMedicationCustomForm;
    private JPanel pMedicationButtons;
    private JButton btnSelectMedication;
    private JButton btnAddMedication;
    private JButton btnCancel;
    private JPanel pMain, pCustomMed;
    private JLabel lblMedicationHeader;
    private JLabel lblMedicationSearchHeader;
    private JLabel lblMedicationFormHeader;
    private JLabel lblMedicationCustomNameHeader;
    private JLabel lblMedicationCustomFormHeader;
    private JScrollPane spMedicationList;
    private JScrollPane spMedicationFormList;
    private Component vsCustomPanelHeaderDelim;
    private Component vsCustomPanelBtnDelimUpper, vsCustomPanelBtnDelimLower;
    private ThriftIntegerClassifierList lMedications;
    private ThriftIntegerClassifierList lMedicationForms;
    private ShablonSearchListener medicationSearchListener;
    private MedicationOptionsFrame frmMedicationOptions;
    private Patient patient;
    

    public MedicationCatalogFrame() {
        getContentPane().setBackground(new Color(153, 204, 255));
        initialization();
    }

    private void initialization() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setFrameSize();
        setUndecorated(true);

        createModalFrames();
        addMainPanel();
    }

    private void createModalFrames() {
        frmMedicationOptions = new MedicationOptionsFrame();
        frmMedicationOptions.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
        frmMedicationOptions.pack();
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
        addCustomMedPanel();
    }
    
    private void addCustomMedPanel() {
        pCustomMed = new JPanel();
        pCustomMed.setBorder(new EtchedBorder(
                EtchedBorder.RAISED, new Color(0, 0, 0), new Color(192, 102, 102)));
        pCustomMed.setLayout(new BoxLayout(pCustomMed, BoxLayout.Y_AXIS));
        vsCustomPanelHeaderDelim = Box.createVerticalStrut(5);
        pCustomMed.add(vsCustomPanelHeaderDelim);
        addMedicationCustomNameHeader();
        addMedicationCustomName();
        addMedicationCustomFormHeader();
        addMedicationCustomForm();
        vsCustomPanelBtnDelimUpper = Box.createVerticalStrut(7);
        pCustomMed.add(vsCustomPanelBtnDelimUpper);
        addAddButton();
        vsCustomPanelBtnDelimLower = Box.createVerticalStrut(5);
        pCustomMed.add(vsCustomPanelBtnDelimLower);
        pMain.add(pCustomMed);
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
    
    private void changeSelectedMedication() {
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
        	if((lMedicationForms != null))
        		lMedicationForms.setData(Collections.<IntegerClassifier>emptyList());
        }
    }

    private void addMedicationList() {
        lMedications = new ThriftIntegerClassifierList();
        lMedications.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	changeSelectedMedication();
                }
            }
        });
        try {
            lMedications.setData(ClientMedication.tcl.getMedications());
        } catch (KmiacServerException e) {
            lMedications.setData(Collections.<IntegerClassifier>emptyList());
        } catch (TException e) {
            lMedications.setData(Collections.<IntegerClassifier>emptyList());
            ClientMedication.conMan.reconnect(e);
        }
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
        changeSelectedMedication();
    }

    private void addMedicationCustomNameHeader() {
        lblMedicationCustomNameHeader = new JLabel("Название медикамента:");
        lblMedicationCustomNameHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCustomMed.add(lblMedicationCustomNameHeader);
    }
    
    private void addMedicationCustomName() {
    	tfMedicationCustomName = new JTextField();
    	tfMedicationCustomName.setBorder(new LineBorder(new Color(0, 0, 0), 1));
    	tfMedicationCustomName.setMaximumSize(new Dimension(2147483647, 100));
    	tfMedicationCustomName.setColumns(10);
    	pCustomMed.add(tfMedicationCustomName);
    }

    private void addMedicationCustomFormHeader() {
        lblMedicationCustomFormHeader = new JLabel("Форма выпуска:");
        lblMedicationCustomFormHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCustomMed.add(lblMedicationCustomFormHeader);
    }
    
    private void addMedicationCustomForm() {
    	tfMedicationCustomForm = new JTextField();
    	tfMedicationCustomForm.setBorder(new LineBorder(new Color(0, 0, 0), 1));
    	tfMedicationCustomForm.setMaximumSize(new Dimension(2147483647, 100));
    	tfMedicationCustomForm.setColumns(10);
    	pCustomMed.add(tfMedicationCustomForm);
    }

    private void addButtonPanel() {
        pMedicationButtons = new JPanel();
        pMain.add(pMedicationButtons);
        pMedicationButtons.setMaximumSize(new Dimension(32767, 100));

        addSelButton();
        addCancelButton();
    }

    private void addSelButton() {
        btnSelectMedication = new JButton("Выбрать медикамент");
        btnSelectMedication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (lMedications.getSelectedValue() != null) {
                    if (lMedicationForms.getData().size() > 0) {
                        frmMedicationOptions.prepareForm(lMedications.getSelectedValue(),
                            lMedicationForms.getSelectedValue(), patient);
                    } else {
                        frmMedicationOptions.prepareForm(lMedications.getSelectedValue(),
                            new IntegerClassifier(-1, "форма выпуска неизвестна"), patient);
                    }
                }
                frmMedicationOptions.setVisible(true);
            }
        });
        pMedicationButtons.add(btnSelectMedication);
    }    
    
    private void addAddButton() {
    	btnAddMedication = new JButton("Добавить медикамент");
    	btnAddMedication.setAlignmentX(Component.CENTER_ALIGNMENT);
    	btnAddMedication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String newMedName = tfMedicationCustomName.getText();
            	String newMedForm = tfMedicationCustomForm.getText();
                if ((newMedName != null) && (newMedName.length() > 0)) {
                    setVisible(false);
                    if ((newMedForm == null) || (newMedForm.length() == 0))
                    	newMedForm = "форма выпуска неизвестна";
                    frmMedicationOptions.prepareForm(
                    	new IntegerClassifier(-1, newMedName),
                        new IntegerClassifier(-1, newMedForm), patient);
                    frmMedicationOptions.setVisible(true);
                    tfMedicationCustomName.setText(null);
                    tfMedicationCustomForm.setText(null);
                } else {
                	JOptionPane.showMessageDialog(getContentPane(),
                		"Название медикамента не может быть пустым", "Ошибка",
                		JOptionPane.OK_OPTION);
                }
            }
        });
    	pCustomMed.add(btnAddMedication);
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

    public void prepareForm(Patient inPatient) {
        patient = inPatient;
    }
}

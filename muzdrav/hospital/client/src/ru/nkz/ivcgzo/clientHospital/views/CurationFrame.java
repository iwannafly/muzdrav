package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.model.CurationTableModel;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

import javax.swing.border.MatteBorder;

import org.apache.thrift.TException;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collections;

import javax.swing.JLabel;

/**
 * Форма приёма в курацию
 */
public class CurationFrame extends JDialog {

    private static final long serialVersionUID = -6475694293017689541L;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton btnSelect;
    private JButton btnCancel;
    private JPanel pStationType;
    private JLabel lblStationType;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxStationType;

    public CurationFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addScrollPane();
        addStationPanel();
        addButtonsPanel();
        pack();
    }

    private void addScrollPane() {
        scrollPane = new JScrollPane();
        getContentPane().add(scrollPane);
        setPreferredSize(new Dimension(800, 400));
        setSize(new Dimension(800, 400));
//        setLocationRelativeTo(null);
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
                (int) ((screenSize.getHeight() - getHeight()) / 2));
        setUndecorated(true);

        CurationTableModel tbModel = new CurationTableModel();
        table = new JTable();
        table.setModel(tbModel);
        scrollPane.setViewportView(table);
    }

    private void addButtonsPanel() {
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        getContentPane().add(panel);

        btnSelect = new JButton("Добавить пациента в курацию");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if ((table.getSelectedRow() != -1) && (cbxStationType.getSelectedItem() != null)) {
                    TSimplePatient currentPatient = (
                        (CurationTableModel) table.getModel()).getPatientList()
                        .get(table.convertRowIndexToModel(table.getSelectedRow()));
                    try {
                        // Закрепление пациента за определенным врачём
                        ClientHospital.tcl.addPatientToDoctor(currentPatient.getIdGosp(),
                                ClientHospital.authInfo.getPcod(),
                                cbxStationType.getSelectedPcod());
                        CurationTableModel tbModel =
                            new CurationTableModel();
                        table.setModel(tbModel);
                    } catch (PatientNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        ClientHospital.conMan.reconnect(e1);
                    }
                } else {
                    JOptionPane.showMessageDialog(CurationFrame.this,
                        "Не выбран пациент или профиль!");
                }
            }
        });
        panel.add(btnSelect);

        btnCancel = new JButton("Выход");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CurationFrame.this.dispatchEvent(new WindowEvent(
                    CurationFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        panel.add(btnCancel);
    }

    private void addStationPanel() {
        pStationType = new JPanel();
        pStationType.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
        getContentPane().add(pStationType);
        pStationType.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        lblStationType = new JLabel("Профиль отделения: ");
        pStationType.add(lblStationType);

        cbxStationType = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        try {
            cbxStationType.setData(ClientHospital.tcl.getStationTypes(
                    ClientHospital.authInfo.getCpodr()));
        } catch (KmiacServerException e) {
            cbxStationType.setData(Collections.<IntegerClassifier>emptyList());
        } catch (TException e) {
            cbxStationType.setData(Collections.<IntegerClassifier>emptyList());
            ClientHospital.conMan.reconnect(e);
        }
        pStationType.add(cbxStationType);
    }

    /**
     * Обновление таблицы пациентов
     */
    public final void refreshModel(final UserAuthInfo authInfo) {
        CurationTableModel tbModel = new CurationTableModel();
        table.setModel(tbModel);
    }

}

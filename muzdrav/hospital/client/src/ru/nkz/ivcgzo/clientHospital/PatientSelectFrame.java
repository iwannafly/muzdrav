package ru.nkz.ivcgzo.clientHospital;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

import javax.swing.border.MatteBorder;
import java.awt.Color;

public class PatientSelectFrame extends JDialog {

    private static final long serialVersionUID = -6475694293017689541L;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton btnSelect;
    private JButton btnCancel;
    private TSimplePatient currentPatient;

    public PatientSelectFrame(final UserAuthInfo authInfo) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addScrollPane(authInfo);
        addButtonsPanel();
    }

    private void addScrollPane(final UserAuthInfo authInfo) {
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

        AllPatientTableModel tbModel = new AllPatientTableModel(authInfo.getPcod(),
                authInfo.getCpodr());
        table = new JTable();
        table.setModel(tbModel);
        scrollPane.setViewportView(table);
    }

    private void addButtonsPanel() {
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        getContentPane().add(panel);

        btnSelect = new JButton("Выбрать пациента");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    currentPatient = ((AllPatientTableModel) table.getModel()).getPatientList()
                            .get(table.convertRowIndexToModel(table.getSelectedRow()));
                    PatientSelectFrame.this.dispatchEvent(new WindowEvent(
                            PatientSelectFrame.this, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
        panel.add(btnSelect);
//        if (table.getRowCount() == 0) {
//            btnSelect.setEnabled(false);
//        }

        btnCancel = new JButton("Отмена");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PatientSelectFrame.this.dispatchEvent(new WindowEvent(
                        PatientSelectFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        panel.add(btnCancel);
    }

    public final TSimplePatient getCurrentPatient() {
        return currentPatient;
    }

    public final void refreshModel(final UserAuthInfo authInfo) {
        AllPatientTableModel tbModel = new AllPatientTableModel(authInfo.getPcod(),
                authInfo.getCpodr());
        table.setModel(tbModel);
    }



}

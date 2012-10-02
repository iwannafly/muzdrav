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

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TSimplePatient;

import javax.swing.border.MatteBorder;

import org.apache.thrift.TException;

import java.awt.Color;

public class CurationFrame extends JDialog {

    private static final long serialVersionUID = -6475694293017689541L;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton btnSelect;
    private JButton btnCancel;
    private TSimplePatient currentPatient;

    public CurationFrame(final UserAuthInfo authInfo) {
//        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addScrollPane(authInfo);
        addButtonsPanel(authInfo);
    }

    private void addScrollPane(final UserAuthInfo authInfo) {
        scrollPane = new JScrollPane();
        getContentPane().add(scrollPane);
        setPreferredSize(new Dimension(800, 400));
        setSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
                (int) ((screenSize.getHeight() - getHeight()) / 2));
        setUndecorated(true);

        AllPatientTableModel tbModel = new AllPatientTableModel(authInfo.getCpodr());
        table = new JTable();
        table.setModel(tbModel);
        scrollPane.setViewportView(table);
    }

    private void addButtonsPanel(final UserAuthInfo authInfo) {
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        getContentPane().add(panel);

        btnSelect = new JButton("Добавить пациента в курацию");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    currentPatient = ((AllPatientTableModel) table.getModel()).getPatientList()
                            .get(table.convertRowIndexToModel(table.getSelectedRow()));
                    try {
                        ClientHospital.tcl.addPatientToDoctor(currentPatient.getIdGosp(),
                                authInfo.getPcod());
                        AllPatientTableModel tbModel =
                                new AllPatientTableModel(authInfo.getCpodr());
                        table.setModel(tbModel);
                    } catch (PatientNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        ClientHospital.conMan.reconnect(e1);
                    }
//                    CurationFrame.this.dispatchEvent(new WindowEvent(
//                            CurationFrame.this, WindowEvent.WINDOW_CLOSING));
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

    public final TSimplePatient getCurrentPatient() {
        return currentPatient;
    }

    public final void refreshModel(final UserAuthInfo authInfo) {
        AllPatientTableModel tbModel = new AllPatientTableModel(authInfo.getCpodr());
        table.setModel(tbModel);
    }

}

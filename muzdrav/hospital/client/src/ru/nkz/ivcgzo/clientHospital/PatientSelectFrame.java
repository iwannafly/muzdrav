package ru.nkz.ivcgzo.clientHospital;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class PatientSelectFrame extends JFrame {

    private static final long serialVersionUID = -6475694293017689541L;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton btnSelect;
    private JButton btnCancel;

    public PatientSelectFrame(final UserAuthInfo authInfo) {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addScrollPane(authInfo);
        addButtonsPanel();
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

        AllPatientTableModel tbModel = new AllPatientTableModel(authInfo);
        table = new JTable();
        table.setModel(tbModel);
        scrollPane.setViewportView(table);
    }

    private void addButtonsPanel() {
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        getContentPane().add(panel);

        btnSelect = new JButton("Выбрать пациента");
        panel.add(btnSelect);
        if (table.getRowCount() == 0) {
            btnSelect.setEnabled(false);
        }

        btnCancel = new JButton("Отмена");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel);
    }

}

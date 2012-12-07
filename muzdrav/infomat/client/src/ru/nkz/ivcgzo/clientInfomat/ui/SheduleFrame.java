package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientInfomat.SheduleTableCellRenderer;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.SheduleTableModel;

public class SheduleFrame extends JFrame {

    private static final long serialVersionUID = 9214019994911833185L;
    private JPanel pMain;
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward;
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spTalon;
//    private LpuListModel llm;
    private JTable tbTalons;
//    private DoctorSelectFrame frmDoctorSelect;

    public SheduleFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        createModalFrames();
        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

    private void createModalFrames() {  
    }

    private void addMainPanel() {
        pMain = new JPanel();
        pMain.setBackground(Color.WHITE);
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addBackwardButtonHorizBox();
        addTalonTablePanel();
    }

    private void addBackwardButtonHorizBox() {
        hbBackwardButton = Box.createHorizontalBox();
        pMain.add(hbBackwardButton);

        hgRight = Box.createHorizontalGlue();
        hbBackwardButton.add(hgRight);

        addBackwardButton();
        
        hgLeft = Box.createHorizontalGlue();
        hbBackwardButton.add(hgLeft);
    }

    private void addBackwardButton() {
        btnBackward = new JButton("");
        btnBackward.setRequestFocusEnabled(false);
        btnBackward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        btnBackward.setIcon(new ImageIcon(SheduleFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void addTalonTablePanel() {
        spTalon = new JScrollPane();
        spTalon.setBackground(Color.WHITE);
        spTalon.getVerticalScrollBar().setPreferredSize(
            new Dimension(50, Integer.MAX_VALUE));
        spTalon.getHorizontalScrollBar().setPreferredSize(
            new Dimension(Integer.MAX_VALUE, 50));
        pMain.add(spTalon);

        addTalonTable();
    }

    private void addTalonTable() {
        tbTalons = new JTable();
        tbTalons.setDefaultRenderer(String.class, new SheduleTableCellRenderer());
        tbTalons.setRowHeight(50);
        spTalon.setViewportView(tbTalons);
    }
    private void refreshTalonTableModel(int pcod, int cpol, String cdol) {
        SheduleTableModel tbtModel = new SheduleTableModel(pcod, cpol, cdol);
        tbTalons.setModel(tbtModel);
    }

    public void showModal(int pcod, int cpol, String cdol) {
        refreshTalonTableModel(pcod, cpol, cdol);
        setVisible(true);
    }

}

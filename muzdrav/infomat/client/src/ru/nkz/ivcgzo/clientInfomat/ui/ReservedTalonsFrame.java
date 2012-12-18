package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientInfomat.ReservedTalonTableCellRenderer;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.ReservedTalonTableModel;

public class ReservedTalonsFrame extends InfomatFrame {

    private static final long serialVersionUID = 4278188155287891545L;
    private static final int TABLE_ROW_HEIGHT = 50;
    private static final int VERTICAL_SCROLLBAR_WIDTH = 50;
    private static final int HORIZONTAL_SCROLLBAR_HEIGHT = 50;
    private JPanel pMain;
    private Box hbBackwardButton;
    private Component hgRight;
    private JButton btnBackward;
    private Component hgLeft;
    private JScrollPane spTalon;
    private JTable tbTalons;

    public ReservedTalonsFrame() {
        initialization();
    }

    private void initialization() {
        addMainPanel();

        pack();
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
        btnBackward.setIcon(new ImageIcon(ReservedTalonsFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public final void addReservedSelectBackwardListener(final ActionListener listener) {
        btnBackward.addActionListener(listener);
    }

    private void addTalonTablePanel() {
        spTalon = new JScrollPane();
        spTalon.setBackground(Color.WHITE);
        spTalon.getVerticalScrollBar().setPreferredSize(
            new Dimension(VERTICAL_SCROLLBAR_WIDTH, Integer.MAX_VALUE));
        spTalon.getHorizontalScrollBar().setPreferredSize(
            new Dimension(Integer.MAX_VALUE, HORIZONTAL_SCROLLBAR_HEIGHT));
        pMain.add(spTalon);

        addTalonTable();
    }

    private void addTalonTable() {
        tbTalons = new JTable();
        tbTalons.setDefaultRenderer(String.class, new ReservedTalonTableCellRenderer());
        tbTalons.setRowHeight(TABLE_ROW_HEIGHT);
        spTalon.setViewportView(tbTalons);
    }

    public final void addReservedTalonTableMouseListener(final MouseListener listener) {
        tbTalons.addMouseListener(listener);
    }

    public final void refreshTalonTableModel(
            final ReservedTalonTableModel reservedTalonTableModel) {
        tbTalons.setModel(reservedTalonTableModel);
    }

    public final void showModal(final ReservedTalonTableModel reservedTalonTableModel) {
        refreshTalonTableModel(reservedTalonTableModel);
        setVisible(true);
    }

}

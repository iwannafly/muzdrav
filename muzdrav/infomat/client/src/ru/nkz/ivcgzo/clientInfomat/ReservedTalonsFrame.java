package ru.nkz.ivcgzo.clientInfomat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftInfomat.ReleaseTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class ReservedTalonsFrame extends JFrame {

    private static final long serialVersionUID = 4278188155287891545L;
    private JPanel pMain;
    private Box hbBackwardButton;
    private Component hgRight;
    private JButton btnBackward;
    private Component hgLeft;
    private JScrollPane spTalon;
    private JTable tbTalons;
    int pcod;

    public ReservedTalonsFrame() {
        pcod = -1;
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
        btnBackward.setIcon(new ImageIcon(ReservedTalonsFrame.class.getResource(
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
        tbTalons.setDefaultRenderer(String.class, new ReservedTalonTableCellRenderer());
        tbTalons.setRowHeight(50);
        tbTalons.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(tbTalons,
                    "Отменить запись на приём", "Отмена талона",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    JTable curTable = (JTable) e.getSource();
                    final int curRow = curTable.getSelectedRow();
                    TTalon curTalon = ((ReservedTalonTableModel) curTable.getModel())
                        .getReservedTalonList()
                        .get(curRow);
                    if (curTalon != null) {
                        releaseTalon(curTalon);
                        refreshTalonTableModel(pcod);
                    }
                } else {
                    refreshTalonTableModel(pcod);
                }
            }
        });
        spTalon.setViewportView(tbTalons);
    }

    @SuppressWarnings("unused")
    private void refreshTalonTableModel() {
        ReservedTalonTableModel tbtModel = new ReservedTalonTableModel(pcod);
        tbTalons.setModel(tbtModel); 
    }

    private void refreshTalonTableModel(int inPcod) {
        pcod = inPcod;
        ReservedTalonTableModel tbtModel = new ReservedTalonTableModel(inPcod);
        tbTalons.setModel(tbtModel);
        //updateSelectTableHeaders();
    }

    public void showModal(int pcod) {
        refreshTalonTableModel(pcod);
        setVisible(true);
    }

    private void releaseTalon(TTalon curTalon) {
        try {
            ClientInfomat.tcl.releaseTalon(curTalon);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (ReleaseTalonOperationFailedException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientInfomat.conMan.reconnect(e);
        }
    } 

}

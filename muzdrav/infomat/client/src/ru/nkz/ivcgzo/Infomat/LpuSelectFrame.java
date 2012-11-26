package ru.nkz.ivcgzo.Infomat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;

public class LpuSelectFrame extends JFrame {
    private static final long serialVersionUID = 5394050664711305366L;
    private JPanel pMain = new JPanel();
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward = new JButton("");
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spLpu = new JScrollPane();
    private JList lLpu = new JList();

    public LpuSelectFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

    private void addMainPanel() {
        pMain = new JPanel();
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addBackwardButtonHorizBox();

        spLpu = new JScrollPane();
        pMain.add(spLpu);

        lLpu = new JList();
        spLpu.setViewportView(lLpu);
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
        btnBackward.setIcon(new ImageIcon(MainFrame.class.getResource(
            "resources/backward.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

package ru.nkz.ivcgzo.clientOperation;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -3201408430800941030L;

    public MainFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
//        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
//                "/ru/nkz/ivcgzo/clientLab/resources/issled.png")));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

    }

    public void onConnect() {
        // TODO Auto-generated method stub
    }
}

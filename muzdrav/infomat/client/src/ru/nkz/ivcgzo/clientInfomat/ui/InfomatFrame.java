package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public abstract class InfomatFrame extends JFrame {
    private static final long serialVersionUID = 2076432555872144340L;

    public InfomatFrame() {
//        setCursor(getToolkit().createCustomCursor(
//            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
//            "null"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}

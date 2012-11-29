/**
 * Класс для вызова undecorated аналога JOptionPane
 */
package ru.nkz.ivcgzo.clientInfomat;

import javax.swing.*;
//import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;

//сделать статическим?
public class OptionsDialog implements ActionListener {
    protected JDialog dialog;
    protected int value;
    public static final int ACCEPT = 1;
    public static final int DECLINE = 0;
    protected final JButton submit = new JButton("Да");
    protected final JButton cancel = new JButton("Нет");

    public OptionsDialog() {
        submit.addActionListener(this);
        cancel.addActionListener(this);
    }

    public int showConfirmDialog(Window parent, String msg) {
        // иконка. нахуй не нужна пока что.
        /*Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/icon.gif"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        img = img.getScaledInstance(48,48,Image.SCALE_DEFAULT);
        JLabel icon = new JLabel(new ImageIcon(img),SwingConstants.CENTER); */
        if (parent instanceof Dialog) {
            dialog = new JDialog((Dialog)parent,true);
        }
        else {
            dialog = new JDialog((Frame)parent,true);
        }        
        buildDialogDefaults(dialog);
        buildAcceptDialog(dialog, msg);
        setToCenter(dialog);
        dialog.setVisible(true);
        return value;
    }

    public void showMessageDialog(Window parent, String msg) {
        
    }

    private void buildDialogDefaults(JDialog dialog) {
        dialog.setUndecorated(true);
        dialog.getContentPane().setLayout(new BorderLayout());
    }

    private void buildAcceptDialog(JDialog dialog, String msg) {
        JPanel panel[] = new JPanel[3];
        panel[0] = new JPanel();
        panel[0].setLayout(new BorderLayout());
        panel[0].add(new JLabel(msg, SwingConstants.CENTER),BorderLayout.NORTH);

        panel[1] = new JPanel();
        panel[1].setLayout(new GridLayout(0,1));

        panel[2] = new JPanel();
        panel[2].setLayout(new FlowLayout(FlowLayout.CENTER));
        panel[2].add(submit);
        panel[2].add(cancel);

        dialog.getContentPane().add(panel[0],BorderLayout.NORTH);
        dialog.getContentPane().add(panel[1],BorderLayout.CENTER);
        dialog.getContentPane().add(panel[2],BorderLayout.SOUTH);
        dialog.pack();
    }

    private void buildMessageDialog(JDialog dialog) {
        
    }

    private void setToCenter(JDialog dialog) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((d.width>>1)-(dialog.getWidth()>>1),
            (d.height>>1)-(dialog.getHeight()>>1));
    }

    public void actionPerformed(ActionEvent e) {
        if (!dialog.isVisible()) {
            return;
        }
        if (e.getSource() == submit) {
            value = ACCEPT;
            dialog.setVisible(false);
            dialog.dispose();
        }
        else if (e.getSource() == cancel) {
            value = DECLINE;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}

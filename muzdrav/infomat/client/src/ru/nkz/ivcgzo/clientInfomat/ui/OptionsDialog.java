/**
 * Класс для вызова undecorated аналога JOptionPane
 */
package ru.nkz.ivcgzo.clientInfomat.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
//import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.awt.image.BufferedImage;

public class OptionsDialog implements ActionListener {
    protected JDialog dialog;
    protected int value;
    public static final int ACCEPT = 1;
    public static final int OK = 0;
    public static final int DECLINE = -1;
    protected final JButton btnSubmit = new JButton("Да");
    protected final JButton btnOk = new JButton("Ok");
    protected final JButton btnCancel = new JButton("Нет");
    protected final JPanel pMessage = new JPanel();
    protected final JPanel pButtons = new JPanel();

    public OptionsDialog() {
        btnSubmit.addActionListener(this);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    public int showConfirmDialog(Window parent, String msg) {      
        dialog = createEmptyDialog(parent);     
        buildDialogDefaults(dialog, msg);
        buildAcceptDialog();
        dialog.setVisible(true);
        return value;
    }

    public void showMessageDialog(Window parent, String msg) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(dialog, msg);
        buildMessageDialog();
        dialog.setVisible(true);
    }

    private JDialog createEmptyDialog(Window parent) {
        if (parent instanceof Dialog) {
            return new JDialog((Dialog)parent,true);
        }
        else {
            return new JDialog((Frame)parent,true);
        }
    }

    private void buildDialogDefaults(JDialog dialog, String msg) {
        dialog.setCursor(dialog.getToolkit().createCustomCursor(
            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
            "null"));
        dialog.setUndecorated(true);
        dialog.getContentPane().setLayout(new BoxLayout(
            dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(400, 300));
        dialog.setSize(new Dimension(400, 300));
        setMessagePanelDefaults(msg);
        setButtonPanelDefaults();
        addPanelsToDialog();
        setToCenter(dialog);
    }

    private void setMessagePanelDefaults(String msg) {
        pMessage.setMinimumSize(new Dimension(400,200));
        pMessage.setMaximumSize(new Dimension(400,200));
        pMessage.setPreferredSize(new Dimension(400,200));
        pMessage.setSize(new Dimension(400,200));
        pMessage.setLayout(new BoxLayout(pMessage, BoxLayout.X_AXIS));
        pMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        pMessage.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel(msg);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Courier New", Font.PLAIN, 25));
        pMessage.add(label);
        pMessage.setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
    }

    private void setButtonPanelDefaults() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        pButtons.setMinimumSize(new Dimension(400,100));
        pButtons.setSize(new Dimension(400,100));
        pButtons.setAlignmentY(SwingConstants.CENTER);
        pButtons.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
    }

    private void buildAcceptDialog() { 
        Component hsFirst = Box.createHorizontalGlue();
        pButtons.add(hsFirst);

        btnSubmit.setMinimumSize(new Dimension(100,50));
        btnSubmit.setPreferredSize(new Dimension(100,50));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnSubmit);

        Component hsSecond = Box.createHorizontalStrut(15);
        pButtons.add(hsSecond);

        btnCancel.setMinimumSize(new Dimension(100,50));
        btnCancel.setPreferredSize(new Dimension(100,50));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnCancel);

        Component hsThird = Box.createHorizontalGlue();
        pButtons.add(hsThird);
    }

    private void buildMessageDialog() {
        btnOk.setMinimumSize(new Dimension(100,50));
        pButtons.add(btnOk);
    }

    private void addPanelsToDialog() {
        dialog.getContentPane().add(pMessage,BorderLayout.NORTH);
        dialog.getContentPane().add(pButtons,BorderLayout.CENTER);
        dialog.pack();
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
        if (e.getSource() == btnSubmit) {
            value = ACCEPT;
            dialog.setVisible(false);
            dialog.dispose();
        } else if (e.getSource() == btnCancel) {
            value = DECLINE;
            dialog.setVisible(false);
            dialog.dispose();
        } else if (e.getSource() == btnOk) {
            value = OK;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }


    // иконка. нахуй не нужна пока что.
/*    private void setImageIcon(Image img) {
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/icon.gif"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        img = img.getScaledInstance(48,48,Image.SCALE_DEFAULT);
        JLabel icon = new JLabel(new ImageIcon(img),SwingConstants.CENTER);
    }
*/
}

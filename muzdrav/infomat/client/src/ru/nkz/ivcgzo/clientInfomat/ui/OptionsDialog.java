package ru.nkz.ivcgzo.clientInfomat.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.imageio.*;
//import java.io.*;
import java.awt.image.BufferedImage;

/**
 * Класс-костыль, вызывающий undecorated аналог JOptionPane. В самом JOptionPane установка
 * undecorated невозможна.
 * Вызываемое окно позиционируется по центру экрана поверх всех окон.
 * В случае окна подтверждения - возвращает результат выбора.
 *
 * @author Avdeev Alexander
 */
public class OptionsDialog implements ActionListener {
    private JDialog dialog;
    private int value;
    public static final int ACCEPT = 1;
    public static final int OK = 0;
    public static final int DECLINE = -1;
    private final JButton btnSubmit = new JButton("Да");
    private final JButton btnOk = new JButton("Ok");
    private final JButton btnCancel = new JButton("Нет");
    private final JPanel pMessage = new JPanel();
    private final JPanel pButtons = new JPanel();

    /**
     * Конструктор по умолчанию. Регистрирует класс слушателем на все виды кнопок.
     */
    public OptionsDialog() {
        btnSubmit.addActionListener(this);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    /**
     * Отображает диалоговое окно с подтверждением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @return int представление выбора пользователя
     * @see ACCEPT
     * @see DECLINE
     */
    public final int showConfirmDialog(final Window parent, final String msg) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(msg);
        buildAcceptDialog();
        dialog.setVisible(true);
        return value;
    }

    /**
     * Отображает диалоговое окно с сообщением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @see OK
     */
    public final void showMessageDialog(final Window parent, final String msg) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(msg);
        buildMessageDialog();
        dialog.setVisible(true);
        dialog.pack();
    }

    /**
     * Создает диалоговое окно.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @see JDialog
     */
    private JDialog createEmptyDialog(final Window parent) {
        if (parent instanceof Dialog) {
            return new JDialog((Dialog) parent, true);
        } else {
            return new JDialog((Frame) parent, true);
        }
    }

    /**
     * Задает параметры диалогового окна
     * @param msg - сообщение отображаемое в диалоговом окне
     */
    private void buildDialogDefaults(final String msg) {
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
        setToCenter();
    }

    /**
     * Задает параметры панели сообщения диалогового окна
     * @param msg - сообщение отображаемое в диалоговом окне
     */
    private void setMessagePanelDefaults(final String msg) {
        pMessage.setMinimumSize(new Dimension(400, 200));
        pMessage.setMaximumSize(new Dimension(400, 200));
        pMessage.setPreferredSize(new Dimension(400, 200));
        pMessage.setSize(new Dimension(400, 200));
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

    /**
     * Задает параметры панели кнопок диалогового окна
     * @param msg - сообщение отображаемое в диалоговом окне
     */
    private void setButtonPanelDefaults() {
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        pButtons.setMinimumSize(new Dimension(400, 100));
        pButtons.setSize(new Dimension(400, 100));
        pButtons.setAlignmentY(SwingConstants.CENTER);
        pButtons.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
    }

    /**
     * Размещает компоненты в диалоговом окне подтверждения
     */
    private void buildAcceptDialog() {
        Component hsFirst = Box.createHorizontalGlue();
        pButtons.add(hsFirst);

        btnSubmit.setMinimumSize(new Dimension(100, 50));
        btnSubmit.setPreferredSize(new Dimension(100, 50));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnSubmit);

        Component hsSecond = Box.createHorizontalStrut(15);
        pButtons.add(hsSecond);

        btnCancel.setMinimumSize(new Dimension(100, 50));
        btnCancel.setPreferredSize(new Dimension(100, 50));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnCancel);

        Component hsThird = Box.createHorizontalGlue();
        pButtons.add(hsThird);
    }

    /**
     * Размещает компоненты в диалоговом окне сообщения
     */
    private void buildMessageDialog() {
        btnOk.setMinimumSize(new Dimension(100, 50));
        pButtons.add(btnOk);
    }

    /**
     * Добавляет панели в диалоговое окно
     */
    private void addPanelsToDialog() {
        dialog.getContentPane().add(pMessage, BorderLayout.NORTH);
        dialog.getContentPane().add(pButtons, BorderLayout.CENTER);
        dialog.pack();
    }

    /**
     * Устанавливает диалоговое окно по центру экрана
     */
    private void setToCenter() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((d.width >> 1) - (dialog.getWidth() >> 1),
            (d.height >> 1) - (dialog.getHeight() >> 1));
    }

    /**
     * Обработка нажатия на кнопку
     */
    public final void actionPerformed(final ActionEvent e) {
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

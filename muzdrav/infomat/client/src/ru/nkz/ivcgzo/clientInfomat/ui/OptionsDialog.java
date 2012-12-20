package ru.nkz.ivcgzo.clientInfomat.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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
import java.awt.print.PrinterException;

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
    public static final int PRINT = 2;
    public static final int ACCEPT = 1;
    public static final int OK = 0;
    public static final int DECLINE = -1;
    public static final int DEFAULT_FONT_SIZE = 25;
    public static final int DEFAULT_ALIGN = StyleConstants.ALIGN_CENTER;
    private final JButton btnSubmit = new JButton("Да");
    private final JButton btnOk = new JButton("Ok");
    private final JButton btnCancel = new JButton("Отмена");
    private final JButton btnPrint = new JButton("Выбрать");
    private JPanel pMessage;
    private JPanel pButtons;
    private JTextPane tpMessage;

    /**
     * Конструктор по умолчанию. Регистрирует класс слушателем на все виды кнопок.
     */
    public OptionsDialog() {
        btnSubmit.addActionListener(this);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
        btnPrint.addActionListener(this);
    }


    //TODO Добавить телескопический вызов, как в методах ниже
    /**
     * Отображает диалоговое окно с печатью в случае подтверждения.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @param align - выравнивание
     * @return int представление выбора пользователя
     * @see PRINT
     * @see DECLINE
     */
    public final int showPrintDialog(final Window parent,
            final String msg, final int fontSize, final int align) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(msg, fontSize, align);
        buildPrintDialog();
        dialog.setVisible(true);
        return value;
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
        return showConfirmDialog(parent, msg, DEFAULT_FONT_SIZE);
    }

    /**
     * Отображает диалоговое окно с подтверждением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @return int представление выбора пользователя
     * @see ACCEPT
     * @see DECLINE
     */
    public final int showConfirmDialog(final Window parent,
            final String msg, final int fontSize) {
        return showConfirmDialog(parent, msg, fontSize, DEFAULT_ALIGN);
    }

    /**
     * Отображает диалоговое окно с подтверждением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @param align - выравнивание
     * @return int представление выбора пользователя
     * @see ACCEPT
     * @see DECLINE
     */
    public final int showConfirmDialog(final Window parent,
            final String msg, final int fontSize, final int align) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(msg, fontSize, align);
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
        showMessageDialog(parent, msg, DEFAULT_FONT_SIZE);
    }

    /**
     * Отображает диалоговое окно с сообщением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @see OK
     */
    public final void showMessageDialog(final Window parent, final String msg,
            final int fontSize) {
        showMessageDialog(parent, msg, fontSize, DEFAULT_ALIGN);
    }

    /**
     * Отображает диалоговое окно с сообщением.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @param align - выравнивание
     * @see OK
     */
    public final void showMessageDialog(final Window parent, final String msg,
            final int fontSize, final int align) {
        dialog = createEmptyDialog(parent);
        buildDialogDefaults(msg, fontSize, align);
        buildMessageDialog();
        dialog.setVisible(true);
        dialog.pack();
    }

    /**
     * Создает диалоговое окно.
     * @param parent - родительское окно (окно из которого вызывается диалог)
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @param align - выравнивание
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
     * @param fontSize - размер шрифта
     * @param align - выравнивание
     */
    private void buildDialogDefaults(final String msg, final int fontSize, final int align) {
        dialog.setCursor(dialog.getToolkit().createCustomCursor(
            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
            "null"));
        dialog.setUndecorated(true);
        dialog.getContentPane().setLayout(new BoxLayout(
            dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setMinimumSize(new Dimension(400, 150));
        dialog.setPreferredSize(new Dimension(400, 150));
        dialog.setSize(new Dimension(400, 150));
        setMessagePanelDefaults(msg, fontSize, align);
        setButtonPanelDefaults();
        addPanelsToDialog();
        setToCenter();
    }

    /**
     * Задает параметры панели сообщения диалогового окна
     * @param msg - сообщение отображаемое в диалоговом окне
     * @param fontSize - размер шрифта
     * @param align - выравнивание
     */
    private void setMessagePanelDefaults(final String msg, final int fontSize, final int align) {
        pMessage = new JPanel();
        pMessage.setBackground(Color.WHITE);
        pMessage.setMinimumSize(new Dimension(400, 100));
        pMessage.setMaximumSize(new Dimension(400, 100));
        pMessage.setPreferredSize(new Dimension(400, 100));
        pMessage.setSize(new Dimension(400, 100));
        pMessage.setLayout(new BoxLayout(pMessage, BoxLayout.X_AXIS));
        pMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        pMessage.setAlignmentY(Component.CENTER_ALIGNMENT);
        StyledDocument document = new DefaultStyledDocument();
        Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(defaultStyle, align);
        StyleConstants.setFontSize(defaultStyle, fontSize);
        tpMessage = new JTextPane(document);
        tpMessage.setFont(new Font("Courier New", Font.PLAIN, fontSize));
        tpMessage.setText(msg);
        tpMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        tpMessage.setAlignmentY(Component.CENTER_ALIGNMENT);
        tpMessage.setEditable(false);
        pMessage.add(tpMessage);
        pMessage.setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
    }

    /**
     * Задает параметры панели кнопок диалогового окна
     */
    private void setButtonPanelDefaults() {
        pButtons = new JPanel();
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.X_AXIS));
        pButtons.setMinimumSize(new Dimension(400, 50));
        pButtons.setSize(new Dimension(400, 50));
        pButtons.setAlignmentY(SwingConstants.CENTER);
        pButtons.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
        pButtons.setBackground(Color.WHITE);
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
     * Размещает компоненты в диалоговом окне распечатки
     */
    private void buildPrintDialog() {
        Component hsFirst = Box.createHorizontalGlue();
        pButtons.add(hsFirst);

        btnPrint.setMinimumSize(new Dimension(100, 50));
        btnPrint.setPreferredSize(new Dimension(100, 50));
        btnPrint.setSize(new Dimension(100, 50));
        btnPrint.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPrint.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnPrint);

        Component hsSecond = Box.createHorizontalStrut(15);
        pButtons.add(hsSecond);

        btnCancel.setMinimumSize(new Dimension(100, 50));
        btnCancel.setPreferredSize(new Dimension(100, 50));
        btnCancel.setSize(new Dimension(100, 50));
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
        Component hsFirst = Box.createHorizontalGlue();
        pButtons.add(hsFirst);

        btnOk.setMinimumSize(new Dimension(100, 50));
        btnOk.setPreferredSize(new Dimension(100, 50));
        btnOk.setSize(new Dimension(100, 50));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOk.setAlignmentY(Component.CENTER_ALIGNMENT);
        pButtons.add(btnOk);

        Component hsThird = Box.createHorizontalGlue();
        pButtons.add(hsThird);
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
        } else if (e.getSource() == btnPrint) {
            value = PRINT;
            dialog.setVisible(false);
            try {
                tpMessage.print(null, null, false, null, null, false);
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
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

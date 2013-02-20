package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class AuthorizationFrame extends JDialog {
    private static final long serialVersionUID = 2313445455119747466L;
    private static final int KEYBOARD_BUTTON_SIDE_SIZE = 80;
    private static final int FRAME_WIDTH = 950;
    private static final int FRAME_HEIGHT = 425;
    private static final int CONTROL_BUTTON_WIDTH = 400;
    private static final int CONTROL_BUTTON_HEIGHT = 80;
    private static final int KEYBOARD_BUTTON_COUNT = 10;
    private static final int FONT_SIZE = 30;
    private static final int CUSTOM_CURSOR_SIDE_SIZE = 3;
    private static final int VERTICAL_STRUTS_HEIGHT = 30;
    private JTextField tfOmsNumber;
    private JButton btnAccept;
    private JButton btnCancel;
    private Component vgBottom;
    private Component hgRightButtonPanelGlue;
    private Component hgMiddleButtonPanelGlue;
    private Component hgLeftButtonPanelGlue;
    private Box hbControlButtonPanel;
    private Component hgKeyboardPanelRightGlue;
    private Component vsMiddle;
    private JPanel pMain;
    private Box buildKeyboardPanel;
    private Component hgKeyboardPanelLeftGlue;
    private Component vsOsTextFieldTopGlue;
    private Component vsOmsTextBottomGlue;
    private Box vbOmsTextPanel;
    private JLabel lblInstruction;
    private Component vgTop;

    public AuthorizationFrame() {
        initialization();
    }

    private void initialization() {
        setDialogDefaults();

        buildMainPanel();
    }

    private void setDialogDefaults() {
//        setCursor(getToolkit().createCustomCursor(
//            new BufferedImage(CUSTOM_CURSOR_SIDE_SIZE, CUSTOM_CURSOR_SIDE_SIZE,
//                BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null")
//        );
        setAlwaysOnTop(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDialogToCenter();
    }

    private void buildMainPanel() {
        pMain = new JPanel();
        setMainPanelDefaults();

        vgTop = Box.createVerticalGlue();
        pMain.add(vgTop);

        buildOmsTextPanel();

        buildKeyboardPanel();

        vsMiddle = Box.createVerticalStrut(VERTICAL_STRUTS_HEIGHT);
        pMain.add(vsMiddle);

        buildButtonsPanel();

        vgBottom = Box.createVerticalGlue();
        pMain.add(vgBottom);

        pack();
    }

    private void setMainPanelDefaults() {
        pMain.setBackground(Color.WHITE);
        pMain.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        pMain.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        pMain.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        pMain.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
    }

    private void setDialogToCenter() {
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
            (int) ((screenSize.getHeight() - getHeight()) / 2));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void buildOmsTextPanel() {
        vbOmsTextPanel = Box.createVerticalBox();
        vbOmsTextPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        vbOmsTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pMain.add(vbOmsTextPanel);

        lblInstruction = new JLabel("Введите серию и номер полиса: ");
        lblInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInstruction.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        vbOmsTextPanel.add(lblInstruction);

        vsOsTextFieldTopGlue = Box.createVerticalStrut(VERTICAL_STRUTS_HEIGHT);
        vbOmsTextPanel.add(vsOsTextFieldTopGlue);

        tfOmsNumber = new JTextField();
        tfOmsNumber.setHorizontalAlignment(SwingConstants.CENTER);
        tfOmsNumber.setDisabledTextColor(Color.WHITE);
        tfOmsNumber.getCaret().setVisible(false);
        tfOmsNumber.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        tfOmsNumber.setMinimumSize(new Dimension(900, 100));
        tfOmsNumber.setPreferredSize(new Dimension(900, 100));
        tfOmsNumber.setMaximumSize(new Dimension(900, 100));
        vbOmsTextPanel.add(tfOmsNumber);
        tfOmsNumber.setColumns(10);

        vsOmsTextBottomGlue = Box.createVerticalStrut(VERTICAL_STRUTS_HEIGHT);
        pMain.add(vsOmsTextBottomGlue);
    }

    private void buildKeyboardPanel() {
        buildKeyboardPanel = Box.createHorizontalBox();
        buildKeyboardPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.add(buildKeyboardPanel);

        hgKeyboardPanelLeftGlue = Box.createHorizontalGlue();
        buildKeyboardPanel.add(hgKeyboardPanelLeftGlue);

        createKeyboardButtons();

        hgKeyboardPanelRightGlue = Box.createHorizontalGlue();
        buildKeyboardPanel.add(hgKeyboardPanelRightGlue);
    }

    private void createKeyboardButtons() {
        for (int i = 0; i <= KEYBOARD_BUTTON_COUNT; i++) {
            createCurrentKeyboardButton(i);
        }
    }

    private void createCurrentKeyboardButton(final int buttonNumber) {
        JButton btnCurrentKeyboardButton = new JButton();
        btnCurrentKeyboardButton.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        btnCurrentKeyboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (buttonNumber == KEYBOARD_BUTTON_COUNT) {
            btnCurrentKeyboardButton.setText("<");
            btnCurrentKeyboardButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    if (tfOmsNumber.getText().length() > 0) {
                        tfOmsNumber.setText(tfOmsNumber.getText().substring(0,
                            tfOmsNumber.getText().length() - 1));
                    }
                }
            });
        } else {
            btnCurrentKeyboardButton.setText(String.valueOf(buttonNumber));
            btnCurrentKeyboardButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    tfOmsNumber.setText(tfOmsNumber.getText() + String.valueOf(buttonNumber));
                }
            });
        }

        buildKeyboardPanel.add(btnCurrentKeyboardButton);
        btnCurrentKeyboardButton.setPreferredSize(
            new Dimension(KEYBOARD_BUTTON_SIDE_SIZE, KEYBOARD_BUTTON_SIDE_SIZE)
        );
        btnCurrentKeyboardButton.setMinimumSize(
            new Dimension(KEYBOARD_BUTTON_SIDE_SIZE, KEYBOARD_BUTTON_SIDE_SIZE)
        );
        btnCurrentKeyboardButton.setMaximumSize(
            new Dimension(KEYBOARD_BUTTON_SIDE_SIZE, KEYBOARD_BUTTON_SIDE_SIZE)
        );
    }

    private void buildButtonsPanel() {
        hbControlButtonPanel = Box.createHorizontalBox();
        hbControlButtonPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.add(hbControlButtonPanel);

        hgLeftButtonPanelGlue = Box.createHorizontalGlue();
        hbControlButtonPanel.add(hgLeftButtonPanelGlue);

        addAcceptButton();

        hgMiddleButtonPanelGlue = Box.createHorizontalGlue();
        hbControlButtonPanel.add(hgMiddleButtonPanelGlue);

        addDeclineButton();

        hgRightButtonPanelGlue = Box.createHorizontalGlue();
        hbControlButtonPanel.add(hgRightButtonPanelGlue);
    }

    private void addAcceptButton() {
        btnAccept = new JButton("Принять");
        btnAccept.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        btnAccept.setMinimumSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        btnAccept.setPreferredSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        btnAccept.setMaximumSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        hbControlButtonPanel.add(btnAccept);
    }

    private void addDeclineButton() {
        btnCancel = new JButton("Отмена");
        btnCancel.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        btnCancel.setPreferredSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        btnCancel.setMinimumSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        btnCancel.setMaximumSize(new Dimension(CONTROL_BUTTON_WIDTH, CONTROL_BUTTON_HEIGHT));
        hbControlButtonPanel.add(btnCancel);
    }

    public final void addButtonAcceptPatientCheckListener(final ActionListener listener) {
        btnAccept.addActionListener(listener);
    }

    public final void addButtonCancelPatientCheckListener(final ActionListener listener) {
        btnCancel.addActionListener(listener);
    }

    public final void clearOmsText() {
        tfOmsNumber.setText("");
    }

    public final String getOmsText() {
        return tfOmsNumber.getText();
    }

}

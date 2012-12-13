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
        setCursor(getToolkit().createCustomCursor(
            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
            "null"));
        setAlwaysOnTop(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setPreferredSize(new Dimension(950, 425));
        setSize(new Dimension(950, 425));
        setDialogToCenter();
    }

    private void buildMainPanel() {
        pMain = new JPanel();
        setMainPanelDefaults();

        vgTop = Box.createVerticalGlue();
        pMain.add(vgTop);

        buildOmsTextPanel();

        buildKeyboardPanel();

        vsMiddle = Box.createVerticalStrut(20);
        vsMiddle.setMaximumSize(new Dimension(32767, 30));
        vsMiddle.setMinimumSize(new Dimension(0, 30));
        vsMiddle.setPreferredSize(new Dimension(0, 30));
        pMain.add(vsMiddle);

        buildButtonsPanel();

        vgBottom = Box.createVerticalGlue();
        pMain.add(vgBottom);

        pack();
    }

    private void setMainPanelDefaults() {
        pMain.setBackground(Color.WHITE);
        pMain.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        pMain.setMaximumSize(new Dimension(950, 425));
        pMain.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.setMinimumSize(new Dimension(950, 425));
        pMain.setPreferredSize(new Dimension(950, 425));
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
        lblInstruction.setFont(new Font("Courier New", Font.BOLD, 30));
        vbOmsTextPanel.add(lblInstruction);

        vsOsTextFieldTopGlue = Box.createVerticalStrut(20);
        vsOsTextFieldTopGlue.setPreferredSize(new Dimension(0, 30));
        vsOsTextFieldTopGlue.setMinimumSize(new Dimension(0, 30));
        vsOsTextFieldTopGlue.setMaximumSize(new Dimension(32767, 30));
        vbOmsTextPanel.add(vsOsTextFieldTopGlue);

        tfOmsNumber = new JTextField();
        tfOmsNumber.setHorizontalAlignment(SwingConstants.CENTER);
        tfOmsNumber.setDisabledTextColor(Color.WHITE);
        tfOmsNumber.setEditable(false);
        tfOmsNumber.setFont(new Font("Courier New", Font.BOLD, 40));
        tfOmsNumber.setMinimumSize(new Dimension(900, 100));
        tfOmsNumber.setPreferredSize(new Dimension(900, 100));
        tfOmsNumber.setMaximumSize(new Dimension(900, 100));
        vbOmsTextPanel.add(tfOmsNumber);
        tfOmsNumber.setColumns(10);

        vsOmsTextBottomGlue = Box.createVerticalStrut(20);
        vsOmsTextBottomGlue.setMinimumSize(new Dimension(0, 30));
        vsOmsTextBottomGlue.setMaximumSize(new Dimension(32767, 30));
        vsOmsTextBottomGlue.setPreferredSize(new Dimension(0, 30));
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
        for (int i = 0; i <= 10; i++) {
            createCurrentKeyboardButton(i);
        }
    }

    private void createCurrentKeyboardButton(final int buttonNumber) {
        JButton btnCurrentKeyboardButton = new JButton();
        btnCurrentKeyboardButton.setFont(new Font("Courier New", Font.BOLD, 30));
        btnCurrentKeyboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (buttonNumber == 10) {
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
        btnAccept.setFont(new Font("Courier New", Font.BOLD, 30));
        btnAccept.setMinimumSize(new Dimension(400, 80));
        btnAccept.setPreferredSize(new Dimension(400, 80));
        btnAccept.setMaximumSize(new Dimension(400, 80));
        hbControlButtonPanel.add(btnAccept);
    }

    private void addDeclineButton() {
        btnCancel = new JButton("Отмена");
        btnCancel.setFont(new Font("Courier New", Font.BOLD, 30));
        btnCancel.setPreferredSize(new Dimension(400, 80));
        btnCancel.setMinimumSize(new Dimension(400, 80));
        btnCancel.setMaximumSize(new Dimension(400, 80));
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

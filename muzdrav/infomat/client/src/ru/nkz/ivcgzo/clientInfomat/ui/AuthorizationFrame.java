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
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AuthorizationFrame extends JDialog {
    private static final long serialVersionUID = 2313445455119747466L;
    private JTextField tfOmsNumber;
    private JButton btnAccept;
    private JButton btnCancel;
    
    public AuthorizationFrame() {
        initialization();
    }

    private void initialization() {
        setAlwaysOnTop(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setPreferredSize(new Dimension(950, 425));
        setSize(new Dimension(950, 425));
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
            (int) ((screenSize.getHeight() - getHeight()) / 2));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel pMain = new JPanel();
        pMain.setBackground(Color.WHITE);
        pMain.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        pMain.setMaximumSize(new Dimension(950, 425));
        pMain.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.setMinimumSize(new Dimension(950, 425));
        pMain.setPreferredSize(new Dimension(950, 425));
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        Component verticalGlue = Box.createVerticalGlue();
        pMain.add(verticalGlue);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        pMain.add(verticalBox);

        JLabel lblNewLabel = new JLabel("Введите серию и номер полиса: ");
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 30));
        verticalBox.add(lblNewLabel);

        Component verticalStrut_3 = Box.createVerticalStrut(20);
        verticalStrut_3.setPreferredSize(new Dimension(0, 30));
        verticalStrut_3.setMinimumSize(new Dimension(0, 30));
        verticalStrut_3.setMaximumSize(new Dimension(32767, 30));
        verticalBox.add(verticalStrut_3);

        tfOmsNumber = new JTextField();
        tfOmsNumber.setHorizontalAlignment(SwingConstants.CENTER);
        tfOmsNumber.setDisabledTextColor(Color.WHITE);
        tfOmsNumber.setEditable(false);
        tfOmsNumber.setFont(new Font("Courier New", Font.BOLD, 40));
        tfOmsNumber.setMinimumSize(new Dimension(900, 100));
        tfOmsNumber.setPreferredSize(new Dimension(900, 100));
        tfOmsNumber.setMaximumSize(new Dimension(900, 100));
        verticalBox.add(tfOmsNumber);
        tfOmsNumber.setColumns(10);

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setMinimumSize(new Dimension(0, 30));
        verticalStrut_1.setMaximumSize(new Dimension(32767, 30));
        verticalStrut_1.setPreferredSize(new Dimension(0, 30));
        pMain.add(verticalStrut_1);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.add(horizontalBox);

        Component horizontalGlue = Box.createHorizontalGlue();
        horizontalBox.add(horizontalGlue);

        JButton btnOne = new JButton("1");
        btnOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "1");
            }
        });
        btnOne.setFont(new Font("Courier New", Font.BOLD, 30));
        btnOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOne.setPreferredSize(new Dimension(80, 80));
        btnOne.setMaximumSize(new Dimension(80, 80));
        btnOne.setMinimumSize(new Dimension(80, 80));
        horizontalBox.add(btnOne);

        JButton btnTwo = new JButton("2");
        btnTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "2");
            }
        });
        btnTwo.setFont(new Font("Courier New", Font.BOLD, 30));
        btnTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTwo.setPreferredSize(new Dimension(80, 80));
        btnTwo.setMinimumSize(new Dimension(80, 80));
        btnTwo.setMaximumSize(new Dimension(80, 80));
        horizontalBox.add(btnTwo);

        JButton btnThree = new JButton("3");
        btnThree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "3");
            }
        });
        btnThree.setFont(new Font("Courier New", Font.BOLD, 30));
        btnThree.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnThree);
        btnThree.setPreferredSize(new Dimension(80, 80));
        btnThree.setMinimumSize(new Dimension(80, 80));
        btnThree.setMaximumSize(new Dimension(80, 80));

        JButton btnFour = new JButton("4");
        btnFour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "4");
            }
        });
        btnFour.setFont(new Font("Courier New", Font.BOLD, 30));
        btnFour.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnFour);
        btnFour.setPreferredSize(new Dimension(80, 80));
        btnFour.setMinimumSize(new Dimension(80, 80));
        btnFour.setMaximumSize(new Dimension(80, 80));

        JButton btnFive = new JButton("5");
        btnFive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "5");
            }
        });
        btnFive.setFont(new Font("Courier New", Font.BOLD, 30));
        btnFive.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnFive);
        btnFive.setPreferredSize(new Dimension(80, 80));
        btnFive.setMinimumSize(new Dimension(80, 80));
        btnFive.setMaximumSize(new Dimension(80, 80));

        JButton btnSix = new JButton("6");
        btnSix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "6");
            }
        });
        btnSix.setFont(new Font("Courier New", Font.BOLD, 30));
        btnSix.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnSix);
        btnSix.setPreferredSize(new Dimension(80, 80));
        btnSix.setMinimumSize(new Dimension(80, 80));
        btnSix.setMaximumSize(new Dimension(80, 80));
        
        JButton btnSeven = new JButton("7");
        btnSeven.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "7");
            }
        });
        btnSeven.setFont(new Font("Courier New", Font.BOLD, 30));
        btnSeven.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnSeven);
        btnSeven.setPreferredSize(new Dimension(80, 80));
        btnSeven.setMinimumSize(new Dimension(80, 80));
        btnSeven.setMaximumSize(new Dimension(80, 80));

        JButton btnEight = new JButton("8");
        btnEight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "8");
            }
        });
        btnEight.setFont(new Font("Courier New", Font.BOLD, 30));
        btnEight.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnEight);
        btnEight.setPreferredSize(new Dimension(80, 80));
        btnEight.setMinimumSize(new Dimension(80, 80));
        btnEight.setMaximumSize(new Dimension(80, 80));

        JButton btnNine = new JButton("9");
        btnNine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "9");
            }
        });
        btnNine.setFont(new Font("Courier New", Font.BOLD, 30));
        btnNine.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnNine);
        btnNine.setPreferredSize(new Dimension(80, 80));
        btnNine.setMinimumSize(new Dimension(80, 80));
        btnNine.setMaximumSize(new Dimension(80, 80));

        JButton btnZero = new JButton("0");
        btnZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfOmsNumber.setText(tfOmsNumber.getText() + "0");
            }
        });
        btnZero.setPreferredSize(new Dimension(80, 80));
        btnZero.setMinimumSize(new Dimension(80, 80));
        btnZero.setMaximumSize(new Dimension(80, 80));
        btnZero.setFont(new Font("Courier New", Font.BOLD, 30));
        btnZero.setAlignmentX(0.5f);
        horizontalBox.add(btnZero);

        JButton btnReset = new JButton("<");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tfOmsNumber.getText().length() > 0 ) {
                    tfOmsNumber.setText(tfOmsNumber.getText().substring(0,
                        tfOmsNumber.getText().length()-1));
                }
            }
        });
        btnReset.setFont(new Font("Courier New", Font.BOLD, 30));
        btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.add(btnReset);
        btnReset.setPreferredSize(new Dimension(80, 80));
        btnReset.setMinimumSize(new Dimension(80, 80));
        btnReset.setMaximumSize(new Dimension(80, 80));

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalBox.add(horizontalGlue_1);

        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setMaximumSize(new Dimension(32767, 30));
        verticalStrut.setMinimumSize(new Dimension(0, 30));
        verticalStrut.setPreferredSize(new Dimension(0, 30));
        pMain.add(verticalStrut);

        Box horizontalBox_1 = Box.createHorizontalBox();
        horizontalBox_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pMain.add(horizontalBox_1);

        Component horizontalGlue_2 = Box.createHorizontalGlue();
        horizontalBox_1.add(horizontalGlue_2);

        btnAccept = new JButton("Принять");
//        btnAccept.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                isOmsAccepted = checkOms(tfOmsNumber.getText().trim());
//                tfOmsNumber.setText("");
//                AuthorizationFrame.this.dispatchEvent(new WindowEvent(
//                    AuthorizationFrame.this, WindowEvent.WINDOW_CLOSED));
//                setVisible(false);
//            }
//        });
        btnAccept.setFont(new Font("Courier New", Font.BOLD, 30));
        btnAccept.setMinimumSize(new Dimension(400, 80));
        btnAccept.setPreferredSize(new Dimension(400, 80));
        btnAccept.setMaximumSize(new Dimension(400, 80));
        horizontalBox_1.add(btnAccept);

        Component horizontalGlue_3 = Box.createHorizontalGlue();
        horizontalBox_1.add(horizontalGlue_3);

        btnCancel = new JButton("Отмена");
//        btnCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                tfOmsNumber.setText("");
//                AuthorizationFrame.this.dispatchEvent(new WindowEvent(
//                    AuthorizationFrame.this, WindowEvent.WINDOW_CLOSED));
//                setVisible(false);
//            }
//        });
        btnCancel.setFont(new Font("Courier New", Font.BOLD, 30));
        btnCancel.setPreferredSize(new Dimension(400, 80));
        btnCancel.setMinimumSize(new Dimension(400, 80));
        btnCancel.setMaximumSize(new Dimension(400, 80));
        horizontalBox_1.add(btnCancel);

        Component horizontalGlue_4 = Box.createHorizontalGlue();
        horizontalBox_1.add(horizontalGlue_4);

        Component verticalGlue_1 = Box.createVerticalGlue();
        pMain.add(verticalGlue_1);

//        createModalFrames();
//        addMainPanel();

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

    public void addButtonAcceptPatientCheckListener(ActionListener listener) {
        btnAccept.addActionListener(listener);
    }

    public void addButtonCancelPatientCheckListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }

    public void clearOmsText() {
        tfOmsNumber.setText("");
    }

    public String getOmsText() {
        return tfOmsNumber.getText();
    }

}

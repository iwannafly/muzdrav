package ru.nkz.ivcgzo.clientInfomat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -4704345769287875616L;
    private JPanel mainPanel;
    private JButton btnAppointment;
    private JButton btnPersonalInfo;
    private JButton btnSchedule;
    private LpuSelectFrame frmLpuSelect;
    private AuthorizationFrame frmAuth;
    private ReservedTalonsFrame frmResTalons;
    
    public MainFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

    private void createModalFrames() {
        if (frmLpuSelect == null) {
            frmLpuSelect = new LpuSelectFrame(); 
        }
        if (frmAuth == null) {
            frmAuth = new AuthorizationFrame();
            frmAuth.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if ((frmAuth.getPatient() != null) && (frmAuth.isValueAccepted())) {
                        frmResTalons.showModal(frmAuth.getPatient().getId());
                        toBack();
                    }
                }
            });
        }
        if (frmResTalons == null) {
            frmResTalons = new ReservedTalonsFrame();
        }
    }

    private void addMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(153, 204, 255));
        getContentPane().add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        Component hgLeftIndentation = Box.createHorizontalGlue();
        mainPanel.add(hgLeftIndentation);

        addAppointmentButton();

        Component hsAppointmentToPersinalInfoIndent = Box.createHorizontalStrut(20);
        hsAppointmentToPersinalInfoIndent.setPreferredSize(new Dimension(125, 0));
        mainPanel.add(hsAppointmentToPersinalInfoIndent);

        addPersonalInfoButton();

        Component hsPersinalToScheduleInfoIndent = Box.createHorizontalStrut(20);
        hsPersinalToScheduleInfoIndent.setPreferredSize(new Dimension(125, 0));
        mainPanel.add(hsPersinalToScheduleInfoIndent);

        addScheduleButton();

        Component hgRightIndentation = Box.createHorizontalGlue();
        mainPanel.add(hgRightIndentation);
    }

    private void addAppointmentButton() {
        btnAppointment = new JButton("Запись на приём");
        btnAppointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmLpuSelect.showAsModal(0);
            }
        });
        btnAppointment.setFont(new Font("Courier New", Font.PLAIN, 25));
        btnAppointment.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAppointment.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAppointment.setBorder(new CompoundBorder(
                new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
                new EtchedBorder(EtchedBorder.LOWERED, null, null))
        );
        btnAppointment.setBackground(Color.WHITE);
        btnAppointment.setForeground(Color.BLACK);
        btnAppointment.setIcon(new ImageIcon(MainFrame.class.getResource(
                "resources/appointment.png")));
        mainPanel.add(btnAppointment);
    }

    private void addPersonalInfoButton() {
        btnPersonalInfo = new JButton("Личный кабинет");
        btnPersonalInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmAuth.setVisible(true);
            }
        });
        btnPersonalInfo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPersonalInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPersonalInfo.setFont(new Font("Courier New", Font.PLAIN, 25));
        btnPersonalInfo.setBackground(Color.WHITE);
        btnPersonalInfo.setBorder(new CompoundBorder(
            new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
            new EtchedBorder(EtchedBorder.LOWERED, null, null))
        );
        btnPersonalInfo.setIcon(new ImageIcon(MainFrame.class.getResource(
                "resources/personalOffice.png")));
        mainPanel.add(btnPersonalInfo);
    }

    private void addScheduleButton() {
        btnSchedule = new JButton("Расписание");
        btnSchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmLpuSelect.showAsModal(1);
            }
        });
        btnSchedule.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSchedule.setFont(new Font("Courier New", Font.PLAIN, 25));
        btnSchedule.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSchedule.setBackground(Color.WHITE);
        btnSchedule.setBorder(new CompoundBorder(
            new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
            new EtchedBorder(EtchedBorder.LOWERED, null, null))
        );
        btnSchedule.setIcon(new ImageIcon(MainFrame.class.getResource(
            "resources/shedule.png")));
        mainPanel.add(btnSchedule);
    }

    public void onConnect() {
        createModalFrames();
    }
}

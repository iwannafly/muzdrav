package ru.nkz.ivcgzo.clientInfomat.ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class MainFrame extends InfomatFrame {

    private static final long serialVersionUID = -4704345769287875616L;
    private JPanel mainPanel;
    private JButton btnAppointment;
    private JButton btnPersonalInfo;
    private JButton btnSchedule;

    public MainFrame() {
        initialization();
    }

    private void initialization() {
        addMainPanel();

        pack();
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

    public final void addAppointmentListener(final ActionListener listener) {
        btnAppointment.addActionListener(listener);
    }

    public final void addPersonalInfoListener(final ActionListener listener) {
        btnPersonalInfo.addActionListener(listener);
    }

    public final void addSheduleListener(final ActionListener listener) {
        btnSchedule.addActionListener(listener);
    }
}

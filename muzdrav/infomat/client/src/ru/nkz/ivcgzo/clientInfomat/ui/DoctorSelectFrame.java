package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EtchedBorder;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.Box;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

public class DoctorSelectFrame extends InfomatFrame {
    private static final long serialVersionUID = -7345770092441907375L;
    private JPanel pnLists;
    private JPanel pnMain;
    private JPanel pnButton;
    private JButton btnBackward;
    private JScrollPane spSpeciality;
    private ThriftStringClassifierList<StringClassifier> lSpeciality;
    private JScrollPane spDoctor;
    private ThriftIntegerClassifierList lDoctor;
    private TalonSelectFrame frmTalonSelect;
    private SheduleFrame frmShedule;

    public DoctorSelectFrame() {
        super();
        initialization();
    }

    private void initialization() {
        createModalFrames();
        addMainPanel();

        pack();
    }

    private void createModalFrames() {
        if (frmTalonSelect == null) {
            frmTalonSelect = new TalonSelectFrame();
        }
        if (frmShedule == null) {
            frmShedule = new SheduleFrame();
        }
    }

    @SuppressWarnings("rawtypes")
    private class InfomatListCellRenderer extends JLabel implements ListCellRenderer {
        private static final long serialVersionUID = -5424295659452002306L;
        private Color unfocusedColor;
        private Color focusedColor;

        public InfomatListCellRenderer() {
            setOpaque(true);
            unfocusedColor = Color.white;
            focusedColor = Color.red;
        }

        public InfomatListCellRenderer(
                final Color inUnfocusedColor, final Color inFocusedColor) {
            setOpaque(true);
            unfocusedColor = inUnfocusedColor;
            focusedColor = inFocusedColor;
        }

        public Component getListCellRendererComponent(
                final JList list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
            setText(value.toString());
            setFont(new Font("Courier New", Font.PLAIN, 25));
            setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(isSelected ? focusedColor : unfocusedColor); // Color.white);
            setForeground(isSelected ? Color.white : Color.black);
            return this;
        }
    }

    private void addMainPanel() {
        pnMain = new JPanel();
        pnMain.setBackground(Color.WHITE);
        getContentPane().add(pnMain);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        addButtonPanel();
        addListPanel();
    }

    private void addButtonPanel() {
        pnButton = new JPanel();
        pnButton.setBackground(Color.WHITE);
        pnMain.add(pnButton);
        pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.X_AXIS));
        
        addLeftHorizontalDelimiter();
        addBackwardButton();
        addRightHorizontalDelimiter();
    }

    public final void updateSpecialitiesList(final List<StringClassifier> specialities) {
        lSpeciality.setData(specialities);
    }

    public final void updateDoctorsList(final List<IntegerClassifier> doctors) {
        lDoctor.setData(doctors);
    }

    private void addBackwardButton() {
        btnBackward = new JButton("");
        btnBackward.setRequestFocusEnabled(false);
        btnBackward.setIcon(new ImageIcon(DoctorSelectFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        pnButton.add(btnBackward);
    }

    public final void addDoctorSelectBackwardListener(final ActionListener listener) {
        btnBackward.addActionListener(listener);
    }

    private void addLeftHorizontalDelimiter() {
        Component hgLeft = Box.createHorizontalGlue();
        pnButton.add(hgLeft);
    }

    private void addRightHorizontalDelimiter() {
        Component hgRigth = Box.createHorizontalGlue();
        pnButton.add(hgRigth);
    }

    private void addListPanel() {
        pnLists = new JPanel();
        pnMain.add(pnLists);
        pnLists.setLayout(new BoxLayout(pnLists, BoxLayout.X_AXIS));

        addSpecialityListScrollPane();
        addDoctorListScrollPane();
    }

    private void addSpecialityListScrollPane() {
        spSpeciality = new JScrollPane();
        spSpeciality.getVerticalScrollBar().setPreferredSize(
            new Dimension(50, Integer.MAX_VALUE));
        addSpecialityList();
        pnLists.add(spSpeciality);
    }

    @SuppressWarnings("unchecked")
    private void addSpecialityList() {
        lSpeciality = new ThriftStringClassifierList<StringClassifier>();
        lSpeciality.setCellRenderer(new InfomatListCellRenderer(new Color(153, 204, 255),
            Color.red));
        lSpeciality.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lSpeciality.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lSpeciality.setFont(new Font("Courier New", Font.PLAIN, 25));
        spSpeciality.setViewportView(lSpeciality);
    }

    public final void addSpecialityListClickListener(final MouseListener listener) {
        lSpeciality.addMouseListener(listener);
    }

    public final void addDoctorListClickListener(final MouseListener listener) {
        lDoctor.addMouseListener(listener);
    }

    private void addDoctorListScrollPane() {
        spDoctor = new JScrollPane();
        spDoctor.getVerticalScrollBar().setPreferredSize(
            new Dimension(50, Integer.MAX_VALUE));
        addDoctorList();
        pnLists.add(spDoctor);
    }

    @SuppressWarnings("unchecked")
    private void addDoctorList() {
        lDoctor = new ThriftIntegerClassifierList();
        lDoctor.setCellRenderer(new InfomatListCellRenderer());
        lDoctor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lDoctor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lDoctor.setFont(new Font("Courier New", Font.PLAIN, 25));
        spDoctor.setViewportView(lDoctor);
    }

    public final void showModal() {
        lDoctor.setData(Collections.<IntegerClassifier>emptyList());
        setVisible(true);
    }
}

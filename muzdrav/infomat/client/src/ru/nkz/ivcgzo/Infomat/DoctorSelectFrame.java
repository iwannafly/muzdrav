package ru.nkz.ivcgzo.Infomat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorSelectFrame extends JFrame {
    private static final long serialVersionUID = -7345770092441907375L;
    private JPanel pnLists;
    private JPanel pnMain;
    private JPanel pnButton;
    private JButton btnBackward;
//    private JButton btnForward;
    private JScrollPane spSpeciality;
    private JList<Speciality> lSpeciality;
    private JScrollPane spDoctor;
    private JList<Doctor> lDoctor;
    private DoctorListModel dlm;


    public DoctorSelectFrame () {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
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

        public InfomatListCellRenderer(Color inUnfocusedColor, Color inFocusedColor) {
            setOpaque(true);
            unfocusedColor = inUnfocusedColor;
            focusedColor = inFocusedColor;
        }

        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            setFont(new Font("Courier New", Font.PLAIN, 25));
            setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(isSelected ? focusedColor : unfocusedColor);// Color.white);
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
//        addForwardButton();
    }

    private void addBackwardButton() {
        btnBackward = new JButton("");
        btnBackward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        btnBackward.setIcon(new ImageIcon(MainFrame.class.getResource(
                "resources/backward.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        pnButton.add(btnBackward);
    }

    private void addLeftHorizontalDelimiter() {
        Component hgLeft = Box.createHorizontalGlue();
        pnButton.add(hgLeft);
    }

    private void addRightHorizontalDelimiter() {
        Component hgRigth = Box.createHorizontalGlue();
        pnButton.add(hgRigth);
    }

//    private void addForwardButton() {
//        btnForward = new JButton("");
//        btnForward.setIcon(new ImageIcon(MainFrame.class.getResource(
//                "resources/forwardButton.png")));
//        btnForward.setBorder(null);
//        btnForward.setBackground(Color.WHITE);
//        btnForward.setForeground(Color.BLACK);
//        pnButton.add(btnForward);
//    }

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
        SpecialityListModel slm = new SpecialityListModel();        
        lSpeciality = new JList<Speciality>(slm);
        lSpeciality.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lSpeciality.getSelectedValue() != null) {
                    dlm.updateModel(lSpeciality.getSelectedValue().getCdol());
                    lDoctor.setModel(dlm);
                    spDoctor.setViewportView(lDoctor);
                }
            }
        });
        lSpeciality.setCellRenderer(new InfomatListCellRenderer(new Color(153, 204, 255), Color.red));
        lSpeciality.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lSpeciality.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lSpeciality.setFont(new Font("Courier New", Font.PLAIN, 25));
        spSpeciality.setViewportView(lSpeciality);
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
        dlm = new DoctorListModel();
        lDoctor = new JList<Doctor>();
        lDoctor.setCellRenderer(new InfomatListCellRenderer());
        lDoctor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lDoctor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lDoctor.setFont(new Font("Courier New", Font.PLAIN, 25));
        spDoctor.setViewportView(lDoctor);
    }
}

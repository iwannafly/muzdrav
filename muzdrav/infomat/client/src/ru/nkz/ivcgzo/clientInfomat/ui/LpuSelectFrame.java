package ru.nkz.ivcgzo.clientInfomat.ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class LpuSelectFrame extends JFrame {
    private static final long serialVersionUID = 5394050664711305366L;
    private JPanel pMain = new JPanel();
    private Box hbBackwardButton;
    private Component hgRight;
    private JButton btnBackward;
    private Component hgLeft;
    private JScrollPane spLpu;
    private ThriftIntegerClassifierList lLpu;
//    private DoctorSelectFrame frmDoctorSelect;

    public LpuSelectFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

//        createModalFrames();
        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

//    private void createModalFrames() {
//        if (frmDoctorSelect == null) {
//            frmDoctorSelect = new DoctorSelectFrame(); 
//        }
//    }

    @SuppressWarnings("rawtypes")
    private class InfomatListCellRenderer extends JLabel implements ListCellRenderer {
        private static final long serialVersionUID = -5424295659452002306L;
        private Color unfocusedColor;
        private Color focusedColor;

        @SuppressWarnings("unused")
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
        pMain = new JPanel();
        pMain.setBackground(Color.WHITE);
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addBackwardButtonHorizBox();
        addLpuListPanel();
    }

    private void addBackwardButtonHorizBox() {
        hbBackwardButton = Box.createHorizontalBox();
        pMain.add(hbBackwardButton);

        hgRight = Box.createHorizontalGlue();
        hbBackwardButton.add(hgRight);

        addBackwardButton();
        
        hgLeft = Box.createHorizontalGlue();
        hbBackwardButton.add(hgLeft);
    }

    private void addBackwardButton() {
        btnBackward = new JButton("");
        btnBackward.setRequestFocusEnabled(false);
//        btnBackward.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//            }
//        });
        btnBackward.setIcon(new ImageIcon(LpuSelectFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void addLpuSelectBackwardListener(ActionListener listener) {
        btnBackward.addActionListener(listener);
    }

    private void addLpuListPanel() {
        spLpu = new JScrollPane();
        spLpu.getVerticalScrollBar().setPreferredSize(
                new Dimension(50, Integer.MAX_VALUE));
        spLpu.getHorizontalScrollBar().setPreferredSize(
                new Dimension(Integer.MAX_VALUE, 50));
        pMain.add(spLpu);
        
        addLpuList();
    }

    @SuppressWarnings("unchecked")
    private void addLpuList() {
        lLpu = new ThriftIntegerClassifierList();
//        updateLpuList();
//        lLpu.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (lLpu.getSelectedValue() != null) {
//                    frmDoctorSelect.showModal(nextWindowFlag, lLpu.getSelectedValue().getPcod());
//                    updateLpuList();
//                }
//            }
//        });
        lLpu.setCellRenderer(new InfomatListCellRenderer(new Color(153, 204, 255), Color.red));
        lLpu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lLpu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lLpu.setFont(new Font("Courier New", Font.PLAIN, 25));
        spLpu.setViewportView(lLpu);
    }

    public void addListClickListener(MouseListener listener) {
        lLpu.addMouseListener(listener);
    }

    public void updateLpuList(List<IntegerClassifier> policlinics) {
        lLpu.setData(policlinics);
//        try {
//            lLpu.setData(ClientInfomat.tcl.getPoliclinics());
//        } catch (KmiacServerException e1) {
//            e1.printStackTrace();
//        } catch (TException e1) {
//            e1.printStackTrace();
//            ClientInfomat.conMan.reconnect(e1);
//        }
    }

    public void showAsModal() {
//       updateLpuList();
       setVisible(true);
    }
}

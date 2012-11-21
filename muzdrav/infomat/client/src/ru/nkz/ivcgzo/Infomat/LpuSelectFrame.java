package ru.nkz.ivcgzo.Infomat;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LpuSelectFrame extends JFrame {
    private static final long serialVersionUID = 5394050664711305366L;
    private JPanel pMain = new JPanel();
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward = new JButton("");
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spLpu = new JScrollPane();
    private LpuListModel llm;
    private JList<Lpu> lLpu = new JList<Lpu>();
    private DoctorSelectFrame frmDoctorSelect;
    private int nextWindowFlag;

    public LpuSelectFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        createModalFrames();
        addMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }

    private void createModalFrames() {
        if (frmDoctorSelect == null) {
            frmDoctorSelect = new DoctorSelectFrame(); 
        }
    }

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
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        llm = new LpuListModel();
        lLpu = new JList<Lpu>(llm);
        lLpu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lLpu.getSelectedValue() != null) {
                    frmDoctorSelect.showModal(nextWindowFlag, lLpu.getSelectedValue().getPcod());
                    llm.updateModel();
                    lLpu.setModel(llm);
//                    setVisible(false);
                }
            }
        });
        lLpu.setCellRenderer(new InfomatListCellRenderer(new Color(153, 204, 255), Color.red));
        lLpu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lLpu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lLpu.setFont(new Font("Courier New", Font.PLAIN, 25));
        spLpu.setViewportView(lLpu);
    }

    public void showModal(int flag) {
       nextWindowFlag = flag; 
       llm.updateModel();
       lLpu.setModel(llm);       
       spLpu.setViewportView(lLpu);
       setVisible(true);
    }
}

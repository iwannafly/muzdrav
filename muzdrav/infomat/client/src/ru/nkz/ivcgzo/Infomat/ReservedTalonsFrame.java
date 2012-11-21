package ru.nkz.ivcgzo.Infomat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReservedTalonsFrame extends JFrame {

    private static final long serialVersionUID = 4278188155287891545L;
    private JPanel pMain;
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward = new JButton("");
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spTalon;
//    private LpuListModel llm;
    private JTable tbTalons;
    int pcod;
//    private DoctorSelectFrame frmDoctorSelect;

    public ReservedTalonsFrame() {
        pcod = -1;
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
    }

    private void addMainPanel() {
        pMain = new JPanel();
        pMain.setBackground(Color.WHITE);
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addBackwardButtonHorizBox();
        addTalonTablePanel();
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

    private void addTalonTablePanel() {
        spTalon = new JScrollPane();
        spTalon.setBackground(Color.WHITE);
        spTalon.getVerticalScrollBar().setPreferredSize(
                new Dimension(50, Integer.MAX_VALUE));
        spTalon.getHorizontalScrollBar().setPreferredSize(
                new Dimension(Integer.MAX_VALUE, 50));
        pMain.add(spTalon);

        addTalonTable();
    }

    private void addTalonTable() {
        tbTalons = new JTable();
        tbTalons.setDefaultRenderer(String.class, new ReservedTalonTableCellRenderer());
        tbTalons.setRowHeight(50);
        tbTalons.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JTable curTable = (JTable) e.getSource();
//                final int curRow = curTable.getSelectedRow();
//                final int curColumn = curTable.getSelectedColumn();
//                curTalon = ((TalonTableModel) curTable.getModel()).getTalonList()
//                    .getTalonByDay(curRow, curColumn);
//                if (curTalon != null) {
//                    
//                }
            }
        });
        spTalon.setViewportView(tbTalons);
    }

    @SuppressWarnings("unused")
    private void refreshTalonTableModel() {
        ReservedTalonTableModel tbtModel = new ReservedTalonTableModel(pcod);
        tbTalons.setModel(tbtModel); 
    }

    private void refreshTalonTableModel(int inPcod) {
        pcod = inPcod;
        ReservedTalonTableModel tbtModel = new ReservedTalonTableModel(inPcod);
        tbTalons.setModel(tbtModel);
        //updateSelectTableHeaders();
    }

    public void showModal(int pcod) {
        refreshTalonTableModel(pcod);
        setVisible(true);
    }

    @SuppressWarnings("unused")
    private void reserveTalon(Patient patient, Talon curTalon) {
        final int prv = 3;
        // java.sql.Date не имеет нулевого конструктора, а preparedUpdate() не работает с
        // java.util.Date. Поэтому для передачи сегодняшней даты требуется такой велосипед.
        final long todayMillisec = new java.util.Date().getTime();
        final String sqlQuery = "UPDATE e_talon SET npasp = ?, dataz = ?, "
            + "prv = ?, id_pvizit = nextval('p_vizit_id_seq') "
            + "WHERE  id = ?;";
        PreparedStatement statement = null;
        DbManager dbManager = DbManager.getInstance();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, patient.getNpasp());
            statement.setDate(2, new Date(todayMillisec));
            statement.setInt(3, prv);
            statement.setInt(4, curTalon.getId());
            int numUpdated = statement.executeUpdate();
            if (numUpdated == 1) {
                dbManager.getConnection().commit();
            } else {
                dbManager.getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

}

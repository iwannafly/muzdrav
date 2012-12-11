package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientInfomat.TalonTableCellRenderer;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;

public class TalonSelectFrame extends JFrame{
    private static final long serialVersionUID = -869834846316758484L;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
    private static final String[] DAY_NAMES = {
        "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вск"
    };
    private JPanel pMain;
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward;
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spTalon;
    private JPanel pTableButtons;    
    private JButton btnTalonBackward;    
    private JButton btnTalonForward;
//    private LpuListModel llm;
    private JTable tbTalons;
//    int cpol;
//    String cdol;
//    int pcod;
    private Component horizontalGlue;
//    private DoctorSelectFrame frmDoctorSelect;

    public TalonSelectFrame() {
//        cpol = -1;
//        cdol = "";
//        pcod = -1;
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

    private void addMainPanel() {
        pMain = new JPanel();
        pMain.setBackground(Color.WHITE);
        getContentPane().add(pMain);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        addBackwardButtonHorizBox();
        addTableButtonsPanel();
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
        btnBackward.setRequestFocusEnabled(false);
//        btnBackward.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//            }
//        });
        btnBackward.setIcon(new ImageIcon(TalonSelectFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void addTalonSelectBackwardListener(ActionListener listener) {
        btnBackward.addActionListener(listener);
    }

    private void addTableButtonsPanel() {
        pTableButtons = new JPanel();
        pTableButtons.setBackground(Color.WHITE);
        pTableButtons.setPreferredSize(new Dimension(10, 50));
        pTableButtons.setMinimumSize(new Dimension(10, 50));
        pTableButtons.setMaximumSize(new Dimension(32767, 50));
        pMain.add(pTableButtons);
        
        btnTalonBackward = new JButton("");
        btnTalonBackward.setRequestFocusEnabled(false);
        btnTalonBackward.setIcon(new ImageIcon(TalonSelectFrame.class.getResource(
                "resources/backward.png")));
        btnTalonBackward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ((TalonTableModel) tbTalons.getModel()).setPrevWeek();
                tbTalons.repaint();
                updateSelectTableHeaders();
            }
        });
        btnTalonBackward.setBorder(null);
        btnTalonBackward.setBackground(Color.WHITE);
        btnTalonBackward.setForeground(Color.BLACK);
        pTableButtons.setLayout(new BoxLayout(pTableButtons, BoxLayout.X_AXIS));
        pTableButtons.add(btnTalonBackward);


        
        horizontalGlue = Box.createHorizontalGlue();
        pTableButtons.add(horizontalGlue);

        btnTalonForward = new JButton("");
        btnTalonForward.setRequestFocusEnabled(false);
        btnTalonForward.setIcon(new ImageIcon(TalonSelectFrame.class.getResource(
                "resources/forward.png")));
        btnTalonForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ((TalonTableModel) tbTalons.getModel()).setNextWeek();
                tbTalons.setModel(tbTalons.getModel());
                tbTalons.repaint();
                updateSelectTableHeaders();
            }
        });
        btnTalonForward.setBorder(null);
        btnTalonForward.setBackground(Color.WHITE);
        btnTalonForward.setForeground(Color.BLACK);
        pTableButtons.add(btnTalonForward);
    }

    public void addTalonTableMouseListener(MouseListener listener) {
        tbTalons.addMouseListener(listener);
    }

    private void updateSelectTableHeaders() {
        for (int i = 0; i < 7; i++) {
            tbTalons.getColumnModel().getColumn(i).setHeaderValue(
                DAY_NAMES[i] + " " + DEFAULT_DATE_FORMAT.format(
                    ((TalonTableModel) tbTalons.getModel())
                        .getTalonList().getWeekDays()[i]
                )
            );
        }
        tbTalons.repaint();
        tbTalons.updateUI();
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
        tbTalons.setDefaultRenderer(String.class, new TalonTableCellRenderer());
        tbTalons.setRowHeight(50);
//        tbTalons.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JTable curTable = (JTable) e.getSource();
//                final int curRow = curTable.getSelectedRow();
//                final int curColumn = curTable.getSelectedColumn();
//                curTalon = ((TalonTableModel) curTable.getModel()).getTalonList()
//                    .getTalonByDay(curRow, curColumn);
//                if (curTalon != null) {
//                    frmAuth.setVisible(true);
//                }
//            }
//        });
        spTalon.setViewportView(tbTalons);
    }

//    public void refreshTalonTableModel() {
//        TalonTableModel tbtModel = new TalonTableModel(cpol, cdol, pcod);
//        tbTalons.setModel(tbtModel);
//    }

    public void refreshTalonTableModel(TalonTableModel curTableModel) {
//        cpol = inCpol;
//        cdol = inCdol;
//        pcod = inPcod;
//        TalonTableModel tbtModel = new TalonTableModel(inCpol, inCdol, inPcod);
        tbTalons.setModel(curTableModel);
        //updateSelectTableHeaders();
    }

    public void showModal(TalonTableModel curTableModel) {
        refreshTalonTableModel(curTableModel);
//        refreshTalonTableModel(cpol, cdol,  pcod);
        setVisible(true);
    }

//    private void reserveTalon(TPatient patient, TTalon curTalon) {
//        try {
//            ClientInfomat.tcl.reserveTalon(patient, curTalon);
//        } catch (KmiacServerException e) {
//            new OptionsDialog().showConfirmDialog(TalonSelectFrame.this,
//                "Ошибка во время записи талона!");
//            e.printStackTrace();
//        } catch (ReserveTalonOperationFailedException e) {
//            new OptionsDialog().showConfirmDialog(TalonSelectFrame.this,
//                "Ошибка во время записи талона!");
//            e.printStackTrace();
//        } catch (PatientHasSomeReservedTalonsOnThisDay e) {
//            new OptionsDialog().showConfirmDialog(TalonSelectFrame.this,
//                "У вас уже есть запись к данному врачу на выбранный день!"
//                + "Повторная запись невозможна!");
//            e.printStackTrace();
//        } catch (TException e) {
//            e.printStackTrace();
//            ClientInfomat.conMan.reconnect(e);
//        }
//    } 

}

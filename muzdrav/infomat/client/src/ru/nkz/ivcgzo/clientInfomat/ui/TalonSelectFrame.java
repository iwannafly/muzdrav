package ru.nkz.ivcgzo.clientInfomat.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientInfomat.TalonTableCellRenderer;
import ru.nkz.ivcgzo.clientInfomat.model.TalonList;
import ru.nkz.ivcgzo.clientInfomat.model.tableModels.TalonTableModel;
import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class TalonSelectFrame extends InfomatFrame {
    private static final long serialVersionUID = -869834846316758484L;
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
    private static final String[] DAY_NAMES = {
        "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вск"
    };
    private static final int TABLE_ROW_HEIGHT = 50;
    private static final int VERTICAL_SCROLLBAR_WIDTH = 50;
    private static final int HORIZONTAL_SCROLLBAR_HEIGHT = 50;
    private static final int BUTTONS_PANEL_HEIGHT = 50;
    private static final int BUTTONS_PANEL_WIDTH = 10;
    private JPanel pMain;
    private Box hbBackwardButton = Box.createHorizontalBox();
    private Component hgRight = Box.createHorizontalGlue();
    private JButton btnBackward;
    private Component hgLeft = Box.createHorizontalGlue();
    private JScrollPane spTalon;
    private JPanel pTableButtons;
    private JButton btnTalonBackward;
    private JButton btnTalonForward;
    private JTable tbTalons;
    private Component horizontalGlue;

    public TalonSelectFrame() {
        initialization();
    }

    private void initialization() {
        addMainPanel();

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
        btnBackward.setIcon(new ImageIcon(TalonSelectFrame.class.getResource(
            "resources/backwardBig.png")));
        btnBackward.setBorder(null);
        btnBackward.setBackground(Color.WHITE);
        btnBackward.setForeground(Color.BLACK);
        hbBackwardButton.add(btnBackward);
        btnBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public final void addTalonSelectBackwardListener(final ActionListener listener) {
        btnBackward.addActionListener(listener);
    }

    private void addTableButtonsPanel() {
        pTableButtons = new JPanel();
        pTableButtons.setBackground(Color.WHITE);
        pTableButtons.setPreferredSize(new Dimension(BUTTONS_PANEL_WIDTH, BUTTONS_PANEL_HEIGHT));
        pTableButtons.setMinimumSize(new Dimension(BUTTONS_PANEL_WIDTH, BUTTONS_PANEL_HEIGHT));
        pTableButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTONS_PANEL_HEIGHT));
        pMain.add(pTableButtons);

        btnTalonBackward = new JButton("");
        btnTalonBackward.setRequestFocusEnabled(false);
        btnTalonBackward.setIcon(new ImageIcon(TalonSelectFrame.class.getResource(
            "resources/backward.png")));
        btnTalonBackward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ((TalonTableModel) tbTalons.getModel()).setPrevWeek();
                setPrevNextVisible();
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
                setPrevNextVisible();
                tbTalons.repaint();
                updateSelectTableHeaders();
            }
        });
        btnTalonForward.setBorder(null);
        btnTalonForward.setBackground(Color.WHITE);
        btnTalonForward.setForeground(Color.BLACK);
        pTableButtons.add(btnTalonForward);
    }

    private void setPrevNextVisible() {
    	TalonList tl = ((TalonTableModel) tbTalons.getModel()).getTalonList();
    	List<TTalon> atl = tl.getAllTalonList();
    	Date[] ds = tl.getWeekDays();
    	
    	if ((atl != null) && (atl.size() != 0)) {
    		btnTalonBackward.setVisible(atl.get(0).datap < ds[0].getTime());
    		btnTalonForward.setVisible(atl.get(atl.size() - 1).datap > ds[ds.length - 1].getTime());
    	} else {
    		btnTalonBackward.setVisible(false);
    		btnTalonForward.setVisible(false);
    	}
    }

    public final void addTalonTableMouseListener(final MouseListener listener) {
        tbTalons.addMouseListener(listener);
    }

    private void updateSelectTableHeaders() {
        for (int i = 0; i < DAY_NAMES.length; i++) {
            tbTalons.getColumnModel().getColumn(i).setHeaderValue(
                DAY_NAMES[i] + " " + DEFAULT_DATE_FORMAT.format(
                    ((TalonTableModel) tbTalons.getModel()).getTalonList().getWeekDays()[i]
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
                new Dimension(VERTICAL_SCROLLBAR_WIDTH, Integer.MAX_VALUE));
        spTalon.getHorizontalScrollBar().setPreferredSize(
                new Dimension(Integer.MAX_VALUE, HORIZONTAL_SCROLLBAR_HEIGHT));
        pMain.add(spTalon);

        addTalonTable();
    }

    private void addTalonTable() {
        tbTalons = new JTable();
        tbTalons.setDefaultRenderer(String.class, new TalonTableCellRenderer());
        tbTalons.setRowHeight(TABLE_ROW_HEIGHT);
        tbTalons.getTableHeader().setReorderingAllowed(false);
        spTalon.setViewportView(tbTalons);
    }

    public final void refreshTalonTableModel(final TalonTableModel curTableModel) {
        tbTalons.setModel(curTableModel);
        setPrevNextVisible();
    }

    public final void showModal(final TalonTableModel curTableModel) {
        refreshTalonTableModel(curTableModel);
        setVisible(true);
    }

}

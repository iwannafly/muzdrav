package ru.nkz.ivcgzo.Infomat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
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

public class DoctorSelectFrame extends JFrame {
    private static final long serialVersionUID = -7345770092441907375L;
    private JPanel mainPanel;

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

    private class MyCellRenderer extends JLabel implements ListCellRenderer {
        private static final long serialVersionUID = -5424295659452002306L;
        private Color unfocusedColor;
        private Color focusedColor;

        public MyCellRenderer() {
            setOpaque(true);
            unfocusedColor = Color.white;
            focusedColor = Color.red;
        }

        public MyCellRenderer(Color inUnfocusedColor, Color inFocusedColor) {
            setOpaque(true);
            unfocusedColor = inUnfocusedColor;
            focusedColor = inFocusedColor;
        }

        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            setFont(new Font("Courier New", Font.PLAIN, 30));
            setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(isSelected ? focusedColor : unfocusedColor);// Color.white);
            setForeground(isSelected ? Color.white : Color.black);
            return this;
        }
    }

    private void addMainPanel() {
        mainPanel = new JPanel();
        getContentPane().add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setPreferredSize(
                new Dimension(50, Integer.MAX_VALUE));
        mainPanel.add(scrollPane);
        JList list = new JList(new String[] {"Терапевт", "Офтальмолог", "Эндокринолог",
                "Терапевт-участковый", "Офтальмолог", "Эндокринолог", "Терапевт-участковый",
                "Офтальмолог", "Эндокринолог", "Терапевт-участковый", "Офтальмолог", "Эндокринолог",
                "Терапевт-участковый", "Офтальмолог", "Эндокринолог", "Терапевт-участковый",
                "Офтальмолог", "Эндокринолог", "Терапевт-участковый", "Офтальмолог",
                "Эндокринолог", "Терапевт-участковый", "Офтальмолог", "Эндокринолог",
                "Терапевт-участковый", "Офтальмолог", "Эндокринолог", "Терапевт-участковый"});
        list.setCellRenderer(new MyCellRenderer(new Color(153, 204, 255), Color.red));
        list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Courier New", Font.PLAIN, 25));
//        scrollPane.getVerticalScrollBar().setSize(150, scrollPane.getHeight());
        scrollPane.setViewportView(list);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.getVerticalScrollBar().setPreferredSize(
                new Dimension(50, Integer.MAX_VALUE));
        mainPanel.add(scrollPane_1);
        JList list1 = new JList(new String[] {"Иванов И.С. (кабинет 405)", "Петров В.А. (кабинет 408)",
                "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Петров В.А. (кабинет 408)", "Имунбекова И.Г. (кабинет 2)", "Петров В.А. (кабинет 408)",
                "Имунбекова И.Г. (кабинет 2)"});
        list1.setCellRenderer(new MyCellRenderer());
        list1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setFont(new Font("Courier New", Font.PLAIN, 25));
        scrollPane_1.setViewportView(list1);
    }
}

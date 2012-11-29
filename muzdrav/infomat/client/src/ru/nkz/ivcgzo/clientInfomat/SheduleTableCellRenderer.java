package ru.nkz.ivcgzo.clientInfomat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class SheduleTableCellRenderer extends JLabel implements TableCellRenderer {

    private static final long serialVersionUID = -6093149415269084185L;
    /**
     * Cell border thickness
     */
    public static final int BT = 3;
    /**
     * Selected cell border thickness
     */
    private static final int SBT = 1;

    @Override
    public final Component getTableCellRendererComponent(final JTable table, final Object value,
            final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        setOpaque(true);
        int curVidp = ((SheduleTableModel) table.getModel()).getVidp(row, column);
        switch (curVidp) {
            case 0:
                setBackground(Color.white);
                setText("");
                break;
            case 1:
                setBackground(Color.green);
                setText((String) value);
                break;
            case 2:
                setBackground(Color.yellow);
                setText((String) value);
                break;
            case 3:
                setBackground(Color.pink);
                setText((String) value);
                break;
            case 4:
                setBackground(Color.magenta);
                setText((String) value);
                break;
            default:
                setBackground(Color.white);
                setText("");
                break;
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        if (hasFocus) {
            setBorder(BorderFactory.createLineBorder(
                UIManager.getColor("Table.selectionForeground"), SBT));
        } else {
            setBorder(BorderFactory.createEmptyBorder(BT, BT, BT, BT));
        }
        return this;
    }

}

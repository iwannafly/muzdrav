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
        String strVal = (String) value;
        if ((strVal != null) && (!strVal.isEmpty()))
        	setBackground(Color.green);
        else
        	setBackground(Color.white);
        setText(strVal);
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

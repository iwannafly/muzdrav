package ru.nkz.ivcgzo.clientReception;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ru.nkz.ivcgzo.thriftReception.Talon;

public final class TalonTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1499800387887121736L;
    private DateChecker dateController;
    private List<Talon> mondayTalonList;
    private List<Talon> tuesdayTalonList;
    private List<Talon> wednesdayTalonList;
    private List<Talon> thursdayTalonList;
    private List<Talon> fridayTalonList;
    private List<Talon> saturdayTalonList;
    private List<Talon> sundayTalonList;

    private TalonTableModel() {

    }

    private int calcMaxTalonListSize() {
        int[] maxSizeArray = {
            mondayTalonList.size(), tuesdayTalonList.size(), wednesdayTalonList.size(),
            thursdayTalonList.size(), fridayTalonList.size(), saturdayTalonList.size(),
            sundayTalonList.size()
        };
        int max = maxSizeArray[0];
        for (int i = 1; i < maxSizeArray.length; i++) {
            if (maxSizeArray[i] > max) {
                max = maxSizeArray[i];   // new maximum
            }
        }
        return max;
    }

    @Override
    public int getRowCount() {
        return calcMaxTalonListSize();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 7;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

}

package ru.nkz.ivcgzo.Infomat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class DoctorListModel implements ListModel<Doctor> {
    private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
    private List<Doctor> items;

    public DoctorListModel(String cdol) {
        items = getItemsFromDb(cdol);
    }

    private List<Doctor>  getItemsFromDb(String cdol) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT DISTINCT s_vrach.pcod, s_vrach.fam, s_vrach.im, s_vrach.ot, "
                + "s_vrach.nkab FROM s_vrach INNER JOIN n_cdol ON s_vrach.cdol = n_cdol.pcod "
                + "INNER JOIN e_talon ON s_vrach.pcod = e_talon.pcod "
        		+ "WHERE n_cdol.pcod = ? AND s_vrach.yvolen = 0 AND s_vrach.opl=2 "
        		+ "AND ((s_vrach.prizn is null) or (s_vrach.prizn <> ?)) "
        		+ "ORDER BY s_vrach.fam, s_vrach.im;";
        DbManager dbManager = DbManager.getInstance();
        List<Doctor> doctors = new ArrayList<Doctor>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setString(1, cdol);
            statement.setString(2, "*");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                doctors.add(
                    new Doctor(
                        resultSet.getInt("pcod"),
                        cdol,
                        resultSet.getString("fam"),
                        resultSet.getString("im"),
                        resultSet.getString("ot"),
                        resultSet.getString("nkab")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return doctors;
    }
    
    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Doctor getElementAt(int index) {
        return items.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

}

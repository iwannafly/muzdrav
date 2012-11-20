package ru.nkz.ivcgzo.Infomat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class DoctorListModel implements ListModel<Doctor> {
    private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
    private List<Doctor> items;

    public DoctorListModel() {
        items = Collections.emptyList();
    }

    public DoctorListModel(int cpol, String cdol) {
        items = getItemsFromDb(cpol, cdol);
    }

    private List<Doctor>  getItemsFromDb(int cpol, String cdol) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT DISTINCT s_vrach.pcod, s_vrach.fam, s_vrach.im, s_vrach.ot "
                + "FROM s_vrach INNER JOIN e_talon ON s_vrach.pcod = e_talon.pcod_sp "
                + "WHERE e_talon.cpol = ? AND e_talon.cdol = ?;";
        DbManager dbManager = DbManager.getInstance();
        List<Doctor> doctors = new ArrayList<Doctor>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, cpol);
            statement.setString(2, cdol);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                doctors.add(
                    new Doctor(
                        resultSet.getInt("pcod"),
                        cdol,
                        resultSet.getString("fam"),
                        resultSet.getString("im"),
                        resultSet.getString("ot")
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

    public void updateModel(int cpol, String cdol) {
        items = getItemsFromDb(cpol, cdol);
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
        listeners.remove(listener);
    }

}

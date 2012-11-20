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

public class SpecialityListModel implements ListModel<Speciality> {
    private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
    private List<Speciality> items;

    public SpecialityListModel() {
        items = new ArrayList<Speciality>();
    }

    public SpecialityListModel(final int cpol) {
        items = getItemsFromDb(cpol);
    }

    private List<Speciality>  getItemsFromDb(final int cpol) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        String sqlQuery = "SELECT DISTINCT n_s00.pcod, n_s00.name FROM n_s00 "
//                + "INNER JOIN s_vrach ON s_vrach.cdol = n_s00.pcod "
//                + "INNER JOIN e_talon ON s_vrach.cdol = e_talon.cdol "
//                + "where s_vrach.opl = 2 AND s_vrach.yvolen = 0 AND "
//                + "((s_vrach.prizn is null) OR (s_vrach.prizn <> '*')) "
//                + "AND e_talon.npasp = 0 order BY n_s00.name ";
        String sqlQuery = "SELECT DISTINCT n_s00.pcod, n_s00.name FROM n_s00 "
                + "INNER JOIN e_talon ON n_s00.pcod = e_talon.cdol "
                + "WHERE e_talon.cpol = ? AND e_talon.prv = ? AND "
                + "((e_talon.npasp = 0) OR (e_talon.npasp is null));";
        DbManager dbManager = DbManager.getInstance();
        List<Speciality> specs = new ArrayList<Speciality>();
        try {
            statement = dbManager.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, cpol);
            statement.setInt(2, 0);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
              specs.add(new Speciality(resultSet.getString("pcod").trim(), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return specs;
    }
    
    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Speciality getElementAt(int index) {
        return items.get(index);
    }

    public void updateModel(final int cpol) {
        items = getItemsFromDb(cpol);
    }

    @Override
    public void addListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

    public void fireContentsChanged() {
        
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        listeners.remove(listener);
    }

}

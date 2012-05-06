package ru.nkz.ivcgzo.Infomat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        items = getItemsFromDb();
    }

    private List<Speciality>  getItemsFromDb() {
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT DISTINCT n_cdol.pcod, n_cdol.name FROM n_cdol "
                + "INNER JOIN s_vrach ON s_vrach.cdol = n_cdol.pcod "
                + "INNER JOIN e_talon ON s_vrach.cdol = e_talon.cdol "
                + "where s_vrach.opl = 2 AND s_vrach.yvolen = 0 AND "
                + "((s_vrach.prizn is null) or (s_vrach.prizn <> '*')) "
                + "AND e_talon.npasp = 0 order by n_cdol.name ";
        DbManager dbManager = DbManager.getInstance();
        List<Speciality> specs = new ArrayList<Speciality>();
        try {
            statement = dbManager.getConnection().createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
              specs.add(new Speciality(resultSet.getString("pcod"), resultSet.getString("name")));
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

    @Override
    public void addListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

}

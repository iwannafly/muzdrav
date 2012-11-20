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

public class LpuListModel implements ListModel<Lpu>{
    private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
    private List<Lpu> items;

    public LpuListModel() {
        items = getItemsFromDb();
    }

    private List<Lpu>  getItemsFromDb() {
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT DISTINCT n_n00.pcod, "
            + "(n_m00.name_s || ', ' || n_n00.name) as name "
            + "FROM n_n00 INNER JOIN n_m00 ON n_m00.pcod = n_n00.clpu "
            + "INNER JOIN e_talon ON n_n00.pcod = e_talon.cpol;";
        DbManager dbManager = DbManager.getInstance();
        List<Lpu> lpuList = new ArrayList<Lpu>();
        try {
            statement = dbManager.getConnection().createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
              lpuList.add(new Lpu(resultSet.getInt("pcod"), resultSet.getString("name")));
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
        return lpuList;
    }
    
    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Lpu getElementAt(int index) {
        return items.get(index);
    }

    public void updateModel() {
        items = getItemsFromDb();
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

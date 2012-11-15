package ru.nkz.ivcgzo.zabolDataImporter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

public class SqlClassifier <K> {
	private Class<K> keyClass;
	private Hashtable<K, String> tbl;
	
	public SqlClassifier(Class<K> key, Connection conn, String sql) throws Exception {
		tbl = new Hashtable<>();
		this.keyClass = key;
		
		try (Statement stm = conn.createStatement()) {
			ResultSet rs = stm.executeQuery(sql);
			
			if (key == String.class)
				while (rs.next()) {
					tbl.put(key.cast(rs.getString(1).trim().toLowerCase()), rs.getString(2));
				}
			else
				while (rs.next()) {
					tbl.put(key.cast(rs.getObject(1)), rs.getString(2));
				}
		} catch (Exception e) {
			throw new Exception(String.format("Error loading classifier with SQL: '%s'.", sql), e);
		}
	}
	
	public String getValueOrSame(K key) {
		if (key == null)
			return null;
		if (key instanceof String)
			key = keyClass.cast(((String) key).toLowerCase());
		if (tbl.containsKey(key))
			return tbl.get(key);
		else
			return key.toString();
	}
	
	public String getValueOrNull(K key) {
		if (key == null)
			return null;
		if (key instanceof String)
			key = keyClass.cast(((String) key).toLowerCase());
		if (tbl.containsKey(key))
			return tbl.get(key);
		else
			return null;
	}
}

package ru.nkz.ivcgzo.serverManager.common;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Класс для отображения полей результирующего набора в класс типа T.
 * @author bsv798
 */
public class ResultSetMapper<T> {
	protected final Field[] flds;
	protected final HashMap<String, Integer> rsFlds;
	protected final Class<T> cls;
	protected static final Hashtable<Integer, String> sqlTypeNames = getSqlTypeNames();
	
	public ResultSetMapper(Class<T> cls, String... fieldList) {
		this.cls = cls;
		flds = this.cls.getFields();
		rsFlds = new HashMap<>();
		for (int i = 0; i < fieldList.length; i++)
			rsFlds.put(fieldList[i], i);
	}
	
	/**
	 * Создает экземпляр класса типа T и записывает в его значения полей из
	 * результирующего набора, используя рефлексию.
	 * @throws Exception 
	 */
	public T map(ResultSet rs) throws SQLException {
		T obj = null;
		Field fld = null;
		
		try {
			obj = (T) cls.newInstance();
			ResultSetMetaData rsMet = rs.getMetaData();
			int rsColCnt = rsMet.getColumnCount() + 1;
			for (int i = 1; i < rsColCnt; i++) {
				Integer fldIdx = rsFlds.get(rsMet.getColumnName(i));
				if (fldIdx != null) {
				fld = flds[fldIdx];
				switch (rsMet.getColumnType(i)) {
					case java.sql.Types.INTEGER:
						fld.set(obj, rs.getInt(i));
						break;
					case java.sql.Types.VARCHAR:
					case java.sql.Types.CHAR: {
						String val = rs.getString(i);
						if (val != null)
							fld.set(obj, val.trim());
						break; }
					case java.sql.Types.SMALLINT:
						fld.set(obj, rs.getShort(i));
						break;
					case java.sql.Types.DATE: {
						Date val = rs.getDate(i);
						if (val != null)
							fld.set(obj, val.getTime());
						break; }
					case java.sql.Types.TIME: {
						Time val = rs.getTime(i);
						if (val != null)
							fld.set(obj, val.getTime());
						break; }
					case java.sql.Types.TIMESTAMP: {
						Timestamp val = rs.getTimestamp(i);
						if (val != null)
							fld.set(obj, val.getTime());
						break; }
					case java.sql.Types.FLOAT:
						fld.set(obj, rs.getFloat(i));
						break;
					case java.sql.Types.BIGINT:
						fld.set(obj, rs.getLong(i));
						break;
					case java.sql.Types.DOUBLE:
						fld.set(obj, rs.getDouble(i));
						break;
					case java.sql.Types.BIT:
						fld.set(obj, rs.getBoolean(i));
						break;
					default:
						throw new SQLException(String.format("Unsupported sql data type %s (%d) in column %s.", sqlTypeNames.get(rsMet.getColumnType(i)), rsMet.getColumnType(i), rsMet.getColumnName(i)));
					}
				}
			}
		} catch (Exception e) {
			throw new SQLException(String.format("Error mapping to %s, field %s.", cls, fld), e);
		}
		return obj;
	}
	
	/**
	 * Отображает все записи из результирующего набора в список типа T.
	 */
	public List<T> mapToList(ResultSet rs) throws SQLException {
		ArrayList<T> lst = new ArrayList<>();
		
		while (rs.next())
			lst.add(map(rs));
		
		return lst;
	}
	
	/**
	 * Отображает все записи из результирующего набора в список типа E.
	 * Работает только с простыми типами данных и наборами, содержащими
	 * не более одного поля.
	 */
	public static <E> List<E> mapToList(Class<E> cls, ResultSet rs) throws SQLException {
		ArrayList<E> lst = new ArrayList<>();
		
		if (rs.getMetaData().getColumnCount() != 1)
			throw new SQLException("Error mapping to list. Result set has to has exactly one field.");
			
		if (cls == Integer.class)
			while (rs.next())
				lst.add(cls.cast(rs.getInt(1)));
		else if (cls == String.class)
			while (rs.next())
				lst.add(cls.cast(rs.getString(1)));
		else if (cls == Short.class)
			while (rs.next())
				lst.add(cls.cast(rs.getShort(1)));
		else if (cls == java.sql.Date.class)
			while (rs.next())
				lst.add(cls.cast(rs.getDate(1)));
		else if (cls == java.sql.Time.class)
			while (rs.next())
				lst.add(cls.cast(rs.getTime(1)));
		else if (cls == java.sql.Timestamp.class)
			while (rs.next())
				lst.add(cls.cast(rs.getTimestamp(1)));
		else if (cls == Float.class)
			while (rs.next())
				lst.add(cls.cast(rs.getFloat(1)));
		else if (cls == Long.class)
			while (rs.next())
				lst.add(cls.cast(rs.getLong(1)));
		else if (cls == Double.class)
			while (rs.next())
				lst.add(cls.cast(rs.getDouble(1)));
		else if (cls == Boolean.class)
			while (rs.next())
				lst.add(cls.cast(rs.getBoolean(1)));
		else
			throw new SQLException(String.format("Error mapping to list. Unsupported %s.", cls));

		return lst;
	}
	
	/**
	 * Отображает все записи из результирующего набора в карту с ключом
	 * типа K и значением типа V. Работает только с простыми типами 
	 * данных и наборами, содержащими не более двух полей.
	 */
	public static <K, V> HashMap<K, V> mapToHashMap(Class<K> clsKey, Class<V> clsVal, ResultSet rs) throws SQLException {
		HashMap<K, V> map = new HashMap<>();
		Class<?> cls = null;
		K key = null;
		V val = null;
		Object obj = null;
		
		if (rs.getMetaData().getColumnCount() != 2)
			throw new SQLException("Error mapping to map. Result set has to has exactly two fields.");
		
		while (rs.next())
			for (int i = 1; i < 3; i++) {
				if (i == 1) {
					cls = clsKey;
				} else {
					cls = clsVal;
				}
				
				if (cls == Integer.class)
					obj = cls.cast(rs.getInt(i));
				else if (cls == String.class)
					obj = cls.cast(rs.getString(i));
				else if (cls == Short.class)
					obj = cls.cast(rs.getShort(i));
				else if (cls == java.sql.Date.class)
					obj = cls.cast(rs.getDate(i));
				else if (cls == java.sql.Time.class)
					obj = cls.cast(rs.getTime(i));
				else if (cls == java.sql.Timestamp.class)
					obj = cls.cast(rs.getTimestamp(i));
				else if (cls == Float.class)
					obj = cls.cast(rs.getFloat(i));
				else if (cls == Long.class)
					obj = cls.cast(rs.getLong(i));
				else if (cls == Double.class)
					obj = cls.cast(rs.getDouble(i));
				else if (cls == Boolean.class)
					obj = cls.cast(rs.getBoolean(i));
				else
					throw new SQLException(String.format("Error mapping to map. Unsupported %s %s.", (i == 1) ? "key" : "value", cls));
				
				if (i == 1) {
					key = clsKey.cast(obj);
				} else {
					val = clsVal.cast(obj);
					map.put(key, val);
				}
			}
		
		return map;
	}
	
	private static Hashtable<Integer, String> getSqlTypeNames() {
		Hashtable<Integer, String> typeNames = new Hashtable<>();
		Field[] fields = java.sql.Types.class.getDeclaredFields();
		
		try {
			for (int i = 0; i < fields.length; i++)
				typeNames.put((int) fields[i].get(null), fields[i].getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return typeNames;
	}
}
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
import java.util.List;

/**
 * Класс для отображения полей результирующего набора в класс типа T.
 * @author bsv798
 */
public class ResultSetMapper<T> {
	protected final Field[] flds;
	protected final HashMap<String, Integer> rsFlds;
	protected final Class<T> cls;
	
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
	 */
	public T map(ResultSet rs) {
		T obj = null;
		
		try {
			obj = (T) cls.newInstance();
			ResultSetMetaData rsMet = rs.getMetaData();
			int rsColCnt = rsMet.getColumnCount() + 1;
			for (int i = 1; i < rsColCnt; i++) {
				Integer fldIdx = rsFlds.get(rsMet.getColumnName(i));
				if (fldIdx != null) {
				Field fld = flds[fldIdx];
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
					}
				}
			}
		} catch (Exception e) {
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

		return lst;
		
	}
}
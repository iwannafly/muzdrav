package ru.nkz.ivcgzo.serverManager.common.thrift;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import ru.nkz.ivcgzo.serverManager.common.ResultSetMapper;

/**
 * Класс для отображения полей результирующего набора в thrift-класс типа T.
 * @author bsv798
 */
public final class TResultSetMapper<T extends TBase<?, F>, F extends TFieldIdEnum> extends ResultSetMapper<T> {
	public TResultSetMapper(Class<T> cls, String... fieldList) {
		super(cls, fieldList);
	}
	
	@Override
	public T map(ResultSet rs) {
		T obj = null;
		
		try {
			obj = (T) cls.newInstance();
			ResultSetMetaData rsMet = rs.getMetaData();
			int rsColCnt = rsMet.getColumnCount() + 1;
			for (int i = 1; i < rsColCnt; i++) {
				Integer fldIdx = rsFlds.get(rsMet.getColumnName(i));
				if (fldIdx != null) {
				F thrFld = obj.fieldForId(fldIdx + 1);
				switch (rsMet.getColumnType(i)) {
					case java.sql.Types.INTEGER:
						obj.setFieldValue(thrFld, rs.getInt(i));
						break;
					case java.sql.Types.VARCHAR:
					case java.sql.Types.CHAR: {
						String val = rs.getString(i);
						if (val != null)
							obj.setFieldValue(thrFld, val.trim());
						break; }
					case java.sql.Types.SMALLINT:
						obj.setFieldValue(thrFld, rs.getShort(i));
						break;
					case java.sql.Types.DATE: {
						Date val = rs.getDate(i);
						if (val != null)
							obj.setFieldValue(thrFld, val.getTime());
						break; }
					case java.sql.Types.TIME: {
						Time val = rs.getTime(i);
						if (val != null)
							obj.setFieldValue(thrFld, val.getTime());
						break; }
					case java.sql.Types.TIMESTAMP: {
						Timestamp val = rs.getTimestamp(i);
						if (val != null)
							obj.setFieldValue(thrFld, val.getTime());
						break; }
					case java.sql.Types.FLOAT:
						obj.setFieldValue(thrFld, rs.getFloat(i));
						break;
					case java.sql.Types.BIGINT:
						obj.setFieldValue(thrFld, rs.getLong(i));
						break;
					case java.sql.Types.DOUBLE:
						obj.setFieldValue(thrFld, rs.getDouble(i));
						break;
					}
				if (rs.wasNull())
					obj.setFieldValue(thrFld, null);
				}
			}
		} catch (Exception e) {
		}
		return obj;
	}
}

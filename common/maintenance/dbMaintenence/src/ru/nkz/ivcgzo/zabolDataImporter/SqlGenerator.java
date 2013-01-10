package ru.nkz.ivcgzo.zabolDataImporter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlGenerator {
	public static String genSelect(String[] fld, String tbl, String selDop) {
		String fldStr;
		
		if ((fld != null) && (fld.length > 0)) {
			fldStr = "";
			for (int i = 0; i < fld.length - 1; i++)
				fldStr += String.format("%s%s, ", (!fld[i].toLowerCase().equals("null")) ? tbl + '.' : "", fld[i]);
			fldStr += String.format("%s%s", (!fld[fld.length - 1].toLowerCase().equals("null")) ? tbl + '.' : "", fld[fld.length - 1]);
		} else {
			fldStr = "*";
		}
		
		if ((selDop != null) && (selDop.length() > 0))
			return String.format("SELECT %s, %s FROM %s ", fldStr, selDop, tbl);
		else
			return String.format("SELECT %s FROM %s ", fldStr, tbl);
	}
	
	public static String genUpdate(String tbl, String[] fld, String[] val) throws SQLException {
		String setStr = "";
		
		if ((fld != null) && (fld.length > 0)) {
			for (int i = 0; i < fld.length - 1; i++) {
				if ((val[i] != null) && (val[i].length() > 0))
					setStr += String.format("%s = '%s', ", fld[i], val[i].replace('\'', '"'));
				else
					setStr += String.format("%s = NULL, ", fld[i]);
			}
			if ((val[fld.length - 1] != null) && (val[fld.length - 1].length() > 0))
				setStr += String.format("%s = '%s'", fld[fld.length - 1], val[fld.length - 1].replace('\'', '"'));
			else
				setStr += String.format("%s = NULL", fld[fld.length - 1]);
		}
		
		return String.format("UPDATE %s SET %s ", tbl, setStr);
	}
	
	public static String genUpdate(String tbl, String[] fld, ResultSet rs) throws SQLException {
		return genUpdate(tbl, fld, getStringValues(rs));
	}
	
	public static String genInsert(String tbl, String[] fld, String[] val) throws SQLException {
		String valStr = "";
		String fldStr = "";
		
		if ((fld != null) && (fld.length > 0)) {
			for (int i = 0; i < fld.length - 1; i++) {
				if ((val[i] != null) && (val[i].length() > 0)) {
					fldStr += String.format("%s, ", fld[i]);
					valStr += String.format("'%s', ", val[i].replace('\'', '"'));
				}
			}
			if ((val[fld.length - 1] != null) && (val[fld.length - 1].length() > 0)) {
				fldStr += String.format("%s", fld[fld.length - 1]);
				valStr += String.format("'%s'", val[fld.length - 1].replace('\'', '"'));
			} else {
				fldStr = fldStr.substring(0, fldStr.length() - 2);
				valStr = valStr.substring(0, valStr.length() - 2);
			}
		}
		
		return String.format("INSERT INTO %s (%s) VALUES (%s) ", tbl, fldStr, valStr);
	}
	
	public static String genInsert(String tbl, String[] fld, ResultSet rs) throws SQLException {
		return genInsert(tbl, fld, getStringValues(rs));
	}
	
	public static String[] getStringValues(ResultSet rs) throws SQLException {
		String[] vals = new String[rs.getMetaData().getColumnCount()];
		String val;
		
		for (int i = 0; i < vals.length; i++) {
			val = rs.getString(i + 1);
			if (!rs.wasNull() && val.length() > 0)
				vals[i] = val;
		}
		
		return vals;
	}
}

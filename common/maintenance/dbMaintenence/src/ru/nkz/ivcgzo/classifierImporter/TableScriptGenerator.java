package ru.nkz.ivcgzo.classifierImporter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.nkz.ivcgzo.dbConnection.DBConnection;

public class TableScriptGenerator {
	private DBConnection conn;
	
	public TableScriptGenerator(DBConnection conn) {
		this.conn = conn;
	}
	
	public String generateScript(TableParams tblParams) throws Exception {
		switch (conn.databaseParams.type) {
		case oracle:
			return oraGenScr(tblParams);
		default:
			throw new Exception(String.format("Table CREATE script generator not yet implemented for %s database.", conn.databaseParams.type));
		}
	}
	
	private String oraGenScr(TableParams tblParams) throws Exception {
		try (
				Statement stm = conn.createStatement();
				ResultSet rs = conn.executeQuery(stm, String.format("SELECT column_name, data_type, data_length, data_precision, data_scale, nullable FROM sys.all_tab_columns WHERE (owner = '%s') AND (table_name = '%s') ORDER BY column_id ", conn.databaseParams.name, tblParams.srcName))) {
			if (!rs.isBeforeFirst())
				throw new Exception(String.format("Table '%s' not found in source database.", tblParams.srcName));
			String scr = String.format("CREATE TABLE %s (", tblParams.dstName);
			while (rs.next()) {
				if (rs.getString(1).equals("KOMM"))
					continue;
				switch (rs.getString(2)) {
				case "NUMBER":
					if (rs.getInt(5) == 0)
						scr += String.format("%s %s", rs.getString(1).toLowerCase(), (rs.getInt(4) < 11) ? "integer" : "bigint");
					else
						scr += String.format("%s double precision", rs.getString(1).toLowerCase());
					break;
				case "CHAR":
					scr += String.format("%s character(%d)", rs.getString(1).toLowerCase(), rs.getInt(3));
					break;
				case "VARCHAR2":
					scr += String.format("%s character varying(%d)", rs.getString(1).toLowerCase(), rs.getInt(3));
					break;
				case "DATE":
					scr += String.format("%s date", rs.getString(1).toLowerCase());
					break;
				default:
					throw new Exception(String.format("Unsupported data type '%s' in source table '%s'.", rs.getString(2), tblParams.srcName));
				}
				if (rs.getString(6).equals("N"))
					scr += " NOT NULL";
				scr += ", ";
			}
			for (String constraint : oraGetPKConstraints(stm, tblParams))
				scr += constraint;
			scr = scr.substring(0, scr.length() - 2) + ");";
			return scr;
		}
	}
	
	private List<String> oraGetPKConstraints(Statement stm, TableParams tblParams) throws SQLException {
		List<String> list = new ArrayList<>();
		
		try (ResultSet rs = conn.executeQuery(stm, String.format("SELECT acc.column_name FROM sys.all_constraints ac, sys.all_cons_columns acc WHERE (ac.constraint_name = acc.constraint_name) AND (acc.owner = '%s') AND (ac.owner = '%s') AND (ac.table_name = '%s') AND (ac.constraint_type = 'P') ", conn.databaseParams.name, conn.databaseParams.name, tblParams.srcName))) {
			while (rs.next())
				list.add(String.format("CONSTRAINT pk_%s_%s PRIMARY KEY (%s), ", tblParams.dstName, rs.getString(1).toLowerCase(), rs.getString(1).toLowerCase()));
		}
		
		return list;
	}
}

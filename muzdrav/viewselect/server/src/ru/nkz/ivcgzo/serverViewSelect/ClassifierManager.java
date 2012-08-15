package ru.nkz.ivcgzo.serverViewSelect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_1;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_2;

public class ClassifierManager {
	private static final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClass = new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
	private static final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClass = new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
	private ISqlSelectExecutor sse;
	private Map<Integer, List<IntegerClassifier>> intClassList;
	private Map<Integer, List<StringClassifier>> strClassList;
	private List<mkb_0> mkbTreeClass;
	
	public ClassifierManager(ISqlSelectExecutor executor) {
		sse = executor;
		intClassList = new HashMap<>();
		strClassList = new HashMap<>();
	}
	
	private Integer getKey(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		if (ord.getValue() == 0)
			return cls.getValue();
		else
			return (fld.getValue() << 0x1c) | (ord.getValue() << 0x18) | cls.getValue();
	}
	
	private Integer getKey(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		if (ord.getValue() == 0)
			return cls.getValue();
		else
			return (fld.getValue() << 0x1c) | (ord.getValue() << 0x18) | cls.getValue();
	}
	
	private String getOrderByClause (ClassifierSortOrder ord, ClassifierSortFields fld, String fldPcod, String fldName) {
		String order = (ord.getValue() == 1) ? "ASC" : "DESC";
		
		switch (fld) {
		case pcod:
			return String.format("ORDER BY %s %s ", fldPcod, order);
		case name:
			return String.format("ORDER BY %s %s ", fldName, order);
		default:
			return String.format("ORDER BY %s %s, %s %s ", fldPcod, order, fldName, order);
		}
	}
	
	private void loadIntegerClassifier(int key, IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		String fldPcod = "pcod";
		String fldName = "name";
		String sql;
		
		switch (cls) {
		default:
			break;
		}
		sql = String.format("SELECT %s AS pcod, %s AS name FROM %s ", fldPcod, fldName, cls.toString());
		
		if (ord.getValue() > 0)
			sql += getOrderByClause(ord, fld, fldPcod, fldName);
		
		try (AutoCloseableResultSet acrs = sse.execQuery(sql)) {
			intClassList.put(key, rsmIntClass.mapToList(acrs.getResultSet()));
		} catch (SQLException e) {
			throw new KmiacServerException("Error loading integer classifier.");
		}
	}
	
	private void loadStringClassifier(int key, StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		String fldPcod = "pcod";
		String fldName = "name";
		String sql;
		
		switch (cls) {
		default:
			break;
		}
		sql = String.format("SELECT %s AS pcod, %s AS name FROM %s ", fldPcod, fldName, cls.toString());
		
		if (ord.getValue() > 0)
			sql += getOrderByClause(ord, fld, fldPcod, fldName);
		
		try (AutoCloseableResultSet acrs = sse.execQuery(sql)) {
			strClassList.put(key, rsmStrClass.mapToList(acrs.getResultSet()));
		} catch (SQLException e) {
			throw new KmiacServerException("Error loading string classifier.");
		}
	}
	
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		int key = getKey(cls, ord, fld);
		
		if (!intClassList.containsKey(key))
			loadIntegerClassifier(key, cls, ord, fld);
		
		return intClassList.get(key);
	}
	
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls) throws KmiacServerException {
		return getIntegerClassifier(cls, ClassifierSortOrder.none, null);
	}
	
	public List<StringClassifier> getStringClassifier(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException {
		int key = getKey(cls, ord, fld);
		
		if (!strClassList.containsKey(key))
			loadStringClassifier(key, cls, ord, fld);
		
		return strClassList.get(key);
	}
	
	public List<StringClassifier> getStringClassifier(StringClassifiers cls) throws KmiacServerException {
		return getStringClassifier(cls, ClassifierSortOrder.none, null);
	}
	
	public List<mkb_0> getMkbTreeClassifier() throws KmiacServerException {
		if (mkbTreeClass != null)
			return mkbTreeClass;
		
		String sql0 = "SELECT a.pcod, a.name, a.cod_mkb FROM n_a00 a ORDER BY a.cod_mkb ";
		String sql1 = "SELECT b.pcod, b.klass, b.name FROM n_a00 a JOIN n_b00 b ON (b.klass = a.pcod) ORDER BY b.pcod ";
		String sql2 = "SELECT c.pcod, c.name FROM n_c00 c ORDER BY c.pcod ";
		
		try (AutoCloseableResultSet acrs0 = sse.execPreparedQuery(sql0); 
				AutoCloseableResultSet acrs1 = sse.execPreparedQuery(sql1);
				AutoCloseableResultSet acrs2 = sse.execPreparedQuery(sql2) ) {
			ResultSet rs0 = acrs0.getResultSet();
			ResultSet rs1 = acrs1.getResultSet(); rs1.next();
			ResultSet rs2 = acrs2.getResultSet(); rs2.next();
			
			mkbTreeClass = new ArrayList<>();
			while (rs0.next()) {
				String clas0 = rs0.getString(1);
				List<mkb_1> mkb1List = new ArrayList<>();
				mkb_0 mkb0 = new mkb_0(clas0.trim(), rs0.getString(2).trim(), rs0.getString(3).trim(), mkb1List);
				while (clas0.equals(rs1.getString(2))) {
					List<mkb_2> mkb2List = new ArrayList<>();
					mkb_1 mkb1 = new mkb_1(rs1.getString(1).trim(), rs1.getString(2).trim(), rs1.getString(3).trim(), mkb2List);
					String str = rs1.getString(1).substring(0, 3) + "    ";
					String end = (rs1.getString(1).charAt(3) == ' ') ? str : rs1.getString(1).substring(4, 7) + "    ";
					while ((rs2.getString(1).compareTo(str) > -1) && (rs2.getString(1).compareTo(end) < 1)) {
						List<StringClassifier> mkb3 = new ArrayList<>();
						mkb_2 mkb2 = new mkb_2(rs2.getString(1).trim(), rs2.getString(2).trim(), mkb3);
						if (!rs2.isAfterLast() && !rs2.next()) break;
						while (rs2.getString(1).charAt(3) == '.') {
							mkb3.add(new StringClassifier(rs2.getString(1).trim(), rs2.getString(2).trim()));
							if (!rs2.isAfterLast() && !rs2.next()) break;
						}
						mkb2List.add(mkb2);
						if (rs2.isAfterLast()) break;
					}
					mkb1List.add(mkb1);
					if (!rs1.isAfterLast() && !rs1.next()) break;
				}
				mkbTreeClass.add(mkb0);
			}
			
			return mkbTreeClass;
		} catch (SQLException e) {
			throw new KmiacServerException("Error loading mkb tree classifier.");
		}
	}
}

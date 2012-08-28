package ru.nkz.ivcgzo.shablonImporter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.nkz.ivcgzo.dbConnection.DBConnection;

public class ShablonImporter {
	private Configuration conf;
	private DBConnection srcConn;
	private DBConnection dstConn;
	private Classifier classDin;
	private Classifier classSpec;
	
	public ShablonImporter(String configFilePath) throws Exception {
		conf = new Configuration(configFilePath);
		
		connectToDatabases();
		loadClassifiers();
	}
	
	private void connectToDatabases() throws Exception {
		srcConn = new DBConnection(conf.srcBaseParams);
		srcConn.connect();
		dstConn = new DBConnection(conf.dstBaseParams);
		dstConn.connect();
	}
	
	private void loadClassifiers () {
		classDin = new Classifier(dstConn, "SELECT pcod, name FROM n_din ");
		classSpec = new Classifier(dstConn, "SELECT pcod, name FROM n_spec ");
	}
	
	public void importShablonOsm() {
		int nameCnt = 0;
		String[] diags;
		try (Statement stmr = srcConn.createStatement();
				PreparedStatement stmOsm = dstConn.createPreparedStatement("INSERT INTO sh_osm (name, diag, cdin, cslu, next) VALUES (?, ?, ?, ?, ?) ", true);
				PreparedStatement stmOsmSpec = dstConn.createPreparedStatement("INSERT INTO sh_ot_spec VALUES (?, ?) ", false);
				PreparedStatement stmOsmTxt = dstConn.createPreparedStatement("INSERT INTO sh_osm_text VALUES (?, ?, ?) ", false);
				Statement stmw = dstConn.createStatement()) {
			ResultSet rsr = stmr.executeQuery(String.format("SELECT * FROM %s.SHPOL ", srcConn.databaseParams.name));
			
			dstConn.rollback();
			stmw.execute("DELETE FROM sh_osm_text ");
			stmw.execute("DELETE FROM sh_ot_spec ");
			stmw.execute("DELETE FROM sh_osm ");
			
			while (rsr.next()) {
				diags = (rsr.getString("DIAG") == null) ? new String[] {} : rsr.getString("DIAG").split("[; ]");
				for (int i = 0; i < diags.length; i++) {
					if (diags[i].isEmpty())
						continue;
					if (rsr.getString("NAME") != null)
						stmOsm.setString(1, rsr.getString("NAME"));
					else
						stmOsm.setString(1, String.format("Название %s", nameCnt++));
					stmOsm.setString(2,	diags[i].trim());
					stmOsm.setInt(3, getCdin(rsr));
					stmOsm.setInt(4, getCslu(rsr));
					stmOsm.setString(5, rsr.getString("OSM"));
					stmOsm.execute();
					
					stmOsm.getGeneratedKeys().next();
					int osmId = stmOsm.getGeneratedKeys().getInt("id");
					stmOsmSpec.setInt(1, osmId);
					for (Integer spec : getCspec(rsr)) {
						stmOsmSpec.setInt(2, spec);
						stmOsmSpec.execute();
					}
					
					stmOsmTxt.setInt(1, osmId);
					for (PcodName txt : getTxt(rsr)) {
						stmOsmTxt.setInt(2, Integer.parseInt(txt.pcod));
						stmOsmTxt.setString(3, txt.name);
						stmOsmTxt.execute();
					}
				}
			}
			dstConn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<PcodName> getTxt(ResultSet rs) throws SQLException {
		List<PcodName> lst = new ArrayList<>();
		String txt;
		
		txt = concat(rs.getString("JALOB1"), rs.getString("JALOB2"), rs.getString("JALOB3"));
		if (txt.length() > 0)
			lst.add(new PcodName("1", txt));
		
		txt = concat(rs.getString("ISTORZAB1"), rs.getString("ISTORZAB2"), rs.getString("ISTORZAB3"));
		if (txt.length() > 0)
			lst.add(new PcodName("2", txt));
		
		txt = concat(rs.getString("STATUSP1"), rs.getString("STATUSP2"), rs.getString("STATUSP3"));
		if (txt.length() > 0)
			lst.add(new PcodName("6", txt));
		
		txt = concat(rs.getString("STATUSLOC1"), rs.getString("STATUSLOC2"), rs.getString("STATUSLOC3"));
		if (txt.length() > 0)
			lst.add(new PcodName("8", txt));
		
		txt = concat(rs.getString("OBS1"), rs.getString("OBS2"), rs.getString("OBS3"));
		if (txt.length() > 0)
			lst.add(new PcodName("9", txt));
		
		txt = concat(rs.getString("LECH1"), rs.getString("LECH2"), rs.getString("LECH3"));
		if (txt.length() > 0)
			lst.add(new PcodName("10", txt));
		
		txt = concat(rs.getString("NAPR1"), rs.getString("NAPR2"));
		if (txt.length() > 0)
			lst.add(new PcodName("11", txt));
		
		txt = concat(rs.getString("RECOM1"), rs.getString("RECOM2"));
		if (txt.length() > 0)
			lst.add(new PcodName("12", txt));
		
		txt = concat(rs.getString("ZAK"));
		if (txt.length() > 0)
			lst.add(new PcodName("13", txt));
		
		return lst;
	}
	
	private String concat(String ...strs)
	{
		String str = "";
		
		for (int i = 0; i < strs.length; i++)
			if (strs[i] != null)
				str += strs[i];
		
		return str;
	}

	private int getCslu(ResultSet rs) throws SQLException {
		String slu = rs.getString("TIP");
		
		if (slu != null) {
			slu = slu.toLowerCase();
			if (slu.equals("стационар"))
				return 1;
			else if (slu.equals("поликлиника"))
				return 2;
		}
		
		return 3;
	}
	
	private int getCdin(ResultSet rs) throws SQLException {
		String din = rs.getString("DINAM");
		
		din = classDin.getPcodFromName(din);
		if (din != null)
			return Integer.parseInt(din);
		
		return 0;
	}
	
	private List<Integer> getCspec(ResultSet rs) throws SQLException {
		List<Integer> lst = new ArrayList<>();
		
		if (rs.getString("DREAL") == null)
			return lst;
		
		String[] specs = rs.getString("DREAL").split(",");
		
		for (int i = 0; i < specs.length; i++) {
			String spec = classSpec.getPcodFromName(specs[i].trim());
			
			if (spec != null)
				lst.add(Integer.parseInt(spec));
			else
				System.out.println();
		}
		
		return lst;
	}
	
	public class Classifier {
		public List<PcodName> codeNames;
		
		public Classifier(DBConnection conn, String sql) {
			codeNames = new ArrayList<>();
			
			try (Statement stm = conn.createStatement()) {
				ResultSet rs = stm.executeQuery(sql);
				
				while (rs.next())
					codeNames.add(new PcodName(rs.getString(1), rs.getString(2)));
				
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String getPcodFromName(String name) {
			for (PcodName pn : codeNames) {
				String pcod = pn.getPcodFromName(name);
				
				if (pcod != null)
					return pcod;
			}
			
			return null;
		}
	}
	
	public class PcodName {
		public String pcod;
		public String name;
		
		public PcodName(String pcod, String name) {
			this.pcod = pcod.toLowerCase();
			this.name = name.toLowerCase();
		}
		
		public String getPcodFromName(String name) {
			if (name != null)
				if (name.toLowerCase().equals(this.name))
					return pcod;
			
			return null;
		}
	}
}

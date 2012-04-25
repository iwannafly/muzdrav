package ru.nkz.ivcgzo.classifierImporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ru.nkz.ivcgzo.dbConnection.DBConnection;
import ru.nkz.ivcgzo.misc.Misc;

public class ClassifierImporter {
	private Configuration conf;
	private DBConnection srcConn;
	private DBConnection dstConn;
	private String[] dstCreationArray;
	
	public ClassifierImporter(String configFilePath) throws Exception {
		try {
			conf = new Configuration(configFilePath);
			connectToDatabases();
		} catch (ClassNotFoundException | SAXException | IOException | ParserConfigurationException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void connectToDatabases() throws Exception {
		srcConn = new DBConnection(conf.srcBaseParams);
		srcConn.connect();
		dstConn = new DBConnection(conf.dstBaseParams);
		dstConn.connect();
	}
	
	public void importClassifiers() {
		try {
			System.out.println(String.format("Importing from %s to %s database", srcConn.databaseParams.type, dstConn.databaseParams.type));

			dstCreationArray = getDstDatabaseCreationArray();
			BinaryExporter binExporter = new BinaryExporter(srcConn);
			for (TableParams tableParams : conf.tableParams) {
				System.out.println(String.format("Importing from '%s' to '%s'", tableParams.srcName, tableParams.dstName));
				
				Path binFile = binExporter.exportToFile(tableParams.srcName);
				Misc.setReadWriteFilePermissions(binFile, dstConn.databaseParams.login);
				String[] impScript = generateImportScript(tableParams, binFile.toString());
				executeImportScript(impScript);
				binFile.toFile().deleteOnExit();
			}
			System.out.println("Done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String[] generateImportScript(TableParams tableParams, String binFileName) throws Exception {
		List<String> script = new ArrayList<>();
		TableScriptGenerator tabScrGen = new TableScriptGenerator(srcConn);
		
		script.add("BEGIN;");
		if (!tableExists(tableParams.dstName)) {
			script.add(tabScrGen.generateScript(tableParams));
			script.add(String.format("COPY %s FROM '%s' BINARY;", tableParams.dstName, binFileName));
		} else {
			String[][] foreignDep = getTableForeignDependees(tableParams.dstName);
			
//			for (int i = 0; i < foreignDep.length; i++)
//				script.add(String.format("ALTER TABLE ONLY %s DROP CONSTRAINT %s;", foreignDep[i][0], foreignDep[i][1]));
			
			script.add(String.format("DROP TABLE %s CASCADE;", tableParams.dstName));
			script.add(findCreationString(String.format("CREATE TABLE %s", tableParams.dstName)));
			script.add(findCreationString(String.format("COMMENT ON TABLE %s", tableParams.dstName)));
			script.add(findCreationString(String.format("ALTER TABLE ONLY %s ADD CONSTRAINT", tableParams.dstName)));
			script.addAll(findCreationStringList(String.format("COMMENT ON COLUMN %s.", tableParams.dstName)));
			script.add(String.format("COPY %s FROM '%s' BINARY;", tableParams.dstName, binFileName));
			
			if (foreignDep.length > 0) {
				for (int i = 0; i < foreignDep.length; i++) {
					script.add(findCreationString(String.format("ADD CONSTRAINT %s FOREIGN KEY", foreignDep[i][1])));
					script.add(findCreationString(String.format("COMMENT ON CONSTRAINT %s", foreignDep[i][1])));
				}
			}
		}
		script.add("END;");
		
		return script.toArray(new String[]{});
	}
	
	private boolean tableExists(String tableName) throws SQLException {
		try (
				Statement stm = dstConn.createStatement();
				ResultSet rs = dstConn.executeQuery(stm, String.format("SELECT * FROM information_schema.tables WHERE (table_catalog = '%s') AND (table_schema = '%s') AND (table_name = '%s') ", dstConn.databaseParams.name, dstConn.databaseParams.schema, tableName))) {
			return rs.next();
		}
	}

	private String[][] getTableForeignDependees(String tableName) throws SQLException {
		try (
				Statement stm = dstConn.createStatement();
				ResultSet rs = dstConn.executeQuery(stm, String.format("SELECT tc.table_name, tc.constraint_name FROM information_schema.constraint_column_usage ccu JOIN information_schema.table_constraints tc ON (tc.constraint_name = ccu.constraint_name) WHERE ccu.table_name = '%s' AND tc.constraint_type = 'FOREIGN KEY' ", tableName))) {
			List<String> lstTables = new ArrayList<>();
			List<String> lstForeigns = new ArrayList<>();
			while(rs.next()) {
				lstTables.add(rs.getString(1));
				lstForeigns.add(rs.getString(2));
			}
			String[][] deps = new String[lstTables.size()][2];
			for (int i = 0; i < lstTables.size(); i++) {
				deps[i][0] = lstTables.get(i);
				deps[i][1] = lstForeigns.get(i);
			}
			return deps;
		}
	}
	
	private String findCreationString(String condition) throws Exception {
		for (int i = 0; i < dstCreationArray.length; i++) {
			if (dstCreationArray[i].indexOf(condition) > -1)
				return dstCreationArray[i];
		}
		
		return "";
	}
	
		private List<String> findCreationStringList(String condition) throws Exception {
			List<String> list = new ArrayList<>();
			
			for (int i = 0; i < dstCreationArray.length; i++) {
				if (dstCreationArray[i].indexOf(condition) > -1)
					list.add(dstCreationArray[i]);
			}
			
//		throw new Exception(String.format("Can't find string '%s' in creation script.", condition));
		return list;
	}
	
	private void executeImportScript(String[] impScript) throws Exception {
		File scrFile = new File(saveScriptToTempFile(impScript));
		scrFile.deleteOnExit();
		try {
			String errorMsg = "";
			String lastLine = "";
			ProcessBuilder psqlProcBuilder = new ProcessBuilder(getExecuteCommandArray(scrFile.getAbsolutePath()));
			psqlProcBuilder.environment().put("PGPASSWORD", conf.dstBaseParams.password);
			psqlProcBuilder.redirectErrorStream(true);
			Process psqlProcess = psqlProcBuilder.start();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(psqlProcess.getInputStream()))) {
				String procLine;
				while ((procLine = br.readLine()) != null) {
					lastLine = procLine;
					int errIdx = procLine.indexOf("ERROR:  ");
					if (errIdx > -1)
						errorMsg += procLine + System.lineSeparator();
				}

			} catch (IOException e) {
				psqlProcess.destroy();
				throw new IOException("Can't open psql input stream.", e);
			}
			psqlProcess.waitFor();
			if (psqlProcess.exitValue() != 0)
				throw new Exception(String.format("psql failed with code %d: '%s'.", psqlProcess.exitValue(), lastLine));
			else if (errorMsg.length() > 0)
				throw new Exception(String.format("psql failed with errors: '%s'.", errorMsg.substring(0, errorMsg.length() - System.lineSeparator().length())));
			} catch (Exception e) {
			throw new Exception("Error executing psql.", e);
		}
	}
	
	private String saveScriptToTempFile(String[] impScript) throws Exception {
		String tmpFile = Misc.createTempFile(dstConn.databaseParams.login);
		try (PrintWriter writer = new PrintWriter(tmpFile);) {
			for (String impStr : impScript) {
				writer.println(impStr);
			}
			return tmpFile;
		}
	}
	
	private String[] getExecuteCommandArray(String sqlCommandFileName) {
		String cmdStr = "";
		if (conf.dstBaseParams.host != null) {
			cmdStr += String.format("-h %s ", conf.dstBaseParams.host);
			if (conf.dstBaseParams.port != null)
				cmdStr += String.format("-p %d " , conf.dstBaseParams.port);
		}
		cmdStr += String.format("-U %s -w ", conf.dstBaseParams.login);
		cmdStr += String.format("-f %s ", sqlCommandFileName);
		cmdStr += conf.dstBaseParams.name;
		
		StringTokenizer strTok = new StringTokenizer(cmdStr);
		String[] cmdArray = new String[strTok.countTokens() + 1];
		for (int i = 1; strTok.hasMoreTokens(); i++) {
			cmdArray[i] = strTok.nextToken();
		}
		cmdArray[0] = new File(dstConn.binPath, "psql").getAbsolutePath();
		
		return cmdArray;
	}

	private String[] getDstDatabaseCreationArray() throws Exception {
		List<String> creationList = new ArrayList<>();
		try {
			String lastLine = null;
			ProcessBuilder pgDumpProcBuilder = new ProcessBuilder(getDumpCommandArray());
			pgDumpProcBuilder.environment().put("PGPASSWORD", conf.dstBaseParams.password);
			pgDumpProcBuilder.redirectErrorStream(true);
			Process pgDumpProcess = pgDumpProcBuilder.start();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(pgDumpProcess.getInputStream()))) {
				String crLine = "";
				String procLine;
				while ((procLine = br.readLine()) != null) {
					lastLine = procLine;
					if (procLine.length() > 0)
						if (!procLine.startsWith("--"))
							if (procLine.endsWith(";")) {
								crLine = ((crLine.length() == 0) ? "" : crLine + " ") + procLine;
								crLine = crLine.replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ");
								creationList.add(crLine);
								crLine = "";
							} else 
								crLine += procLine + " ";
				}

			} catch (IOException e) {
				pgDumpProcess.destroy();
				throw new IOException("Can't open pg_dump input stream.", e);
			}
			pgDumpProcess.waitFor();
			if (pgDumpProcess.exitValue() != 0)
				throw new Exception(String.format("pg_dump failed with code %d: '%s'.", pgDumpProcess.exitValue(), lastLine));
		} catch (Exception e) {
			throw new Exception("Error executing pg_dump.", e);
		}
		
		return creationList.toArray(new String[]{});
	}
	
	private String[] getDumpCommandArray() {
		String cmdStr = "";
		if (conf.dstBaseParams.host != null) {
			cmdStr += String.format("-h %s ", conf.dstBaseParams.host);
			if (conf.dstBaseParams.port != null)
				cmdStr += String.format("-p %d " , conf.dstBaseParams.port);
		}
		cmdStr += String.format("-U %s -w ", conf.dstBaseParams.login);
		cmdStr += String.format("-s %s", conf.dstBaseParams.name);
		
		StringTokenizer strTok = new StringTokenizer(cmdStr);
		String[] cmdArray = new String[strTok.countTokens() + 1];
		for (int i = 1; strTok.hasMoreTokens(); i++) {
			cmdArray[i] = strTok.nextToken();
		}
		cmdArray[0] = new File(dstConn.binPath, "pg_dump").getAbsolutePath();
		
		return cmdArray;
	}
	
	
}

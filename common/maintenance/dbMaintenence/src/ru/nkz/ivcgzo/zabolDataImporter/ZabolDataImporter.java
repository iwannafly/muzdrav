package ru.nkz.ivcgzo.zabolDataImporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

public class ZabolDataImporter {
	private Connection srcCon;
	private Connection dstCon;
	
	public void importData(String[] args) throws Exception {
		if (args.length < 7)
			throw new Exception("Not all params spectified.");
		
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		Class.forName("org.postgresql.Driver");
		Properties srcProp = new Properties();
		srcProp.put("user", args[5]);
		srcProp.put("password", args[6]);
		srcProp.put("charSet", "Cp866");
		try (
				Connection srcCon = DriverManager.getConnection(String.format("jdbc:firebirdsql://%s", args[4]), srcProp);
				Connection dstCon = DriverManager.getConnection(String.format("jdbc:postgresql://%s", args[1]), args[2], args[3]);
				){
			srcCon.setAutoCommit(false);
			dstCon.setAutoCommit(false);
			
			this.srcCon = srcCon;
			this.dstCon = dstCon;
			
			importPatient();
			importPnambk();
			importPpreds();
			importPkov();
			importPtub();
			importSvrach();
			importSmrab();
			importPfiz();
			importPinv();
			importPinvk();
			importPdiag();
			importPdisp();
		}
	}
	
	private void importPatient() throws Exception {
		System.out.println("Importing patient.");
		String[] patientSrcFld = new String[] {"NPASPG", "DATAZ", "FAM", "IM", "OT", "DATAR", "POL", "JITEL", "ADP_OBL", "ADP_GOROD", "ADP_UL", "ADP_DOM", "ADP_KORP", "ADP_KV", "ADM_OBL", "ADM_GOROD", "ADM_UL", "ADM_DOM", "ADM_KORP", "ADM_KV", "TDOC", "DOCSER", "DOCUM",  "ODOC", "DATADOC", "MRAB", "SGRP", "SNILS", "POMS_SER", "POMS_NOM", "PDMS_SER", "PDMS_NOM", "CPOL_PR", "POMS_STRG", "PDMS_STRG", "POMS_NDOG", "PDMS_NDOG", "DATAPR", "NAMEMR",  "NCEX", "PRIZN", "TEL", "TERP", "DSV", "TER_LIV", "VPOLIS"};
		String[] patientDstFld = new String[] {"npasp",  "dataz", "fam", "im", "ot", "datar", "pol", "jitel", "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_korp", "adp_kv", "adm_obl", "adm_gorod", "adm_ul", "adm_dom", "adm_korp", "adm_kv", "tdoc", "docser", "docnum", "odoc", "datadoc", "mrab", "sgrp", "snils", "poms_ser", "poms_nom", "pdms_ser", "pdms_nom", "cpol_pr", "poms_strg", "pdms_strg", "poms_ndog", "pdms_ndog", "datapr", "name_mr", "ncex", "prizn", "tel", "terp", "dsv", "ter_liv", "poms_tdoc"};
		
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement patSrcStm = srcCon.createStatement();
				Statement patDstStm = dstCon.createStatement();
			) {
			ResultSet patSrcRs = patSrcStm.executeQuery(sql = String.format("%s WHERE (%s > 0) AND (%s != '') AND (%s != '') AND (%s != '') ORDER BY %s, %s DESC ", SqlGenerator.genSelect(patientSrcFld, "PATIENT", null), patientSrcFld[0], patientSrcFld[2], patientSrcFld[3], patientSrcFld[4], patientSrcFld[0], patientSrcFld[1]));
			ResultSet patDstRs = patDstStm.executeQuery(sql = String.format("%s WHERE %s > 0 ORDER BY %s ", SqlGenerator.genSelect(patientDstFld, "patient", null), patientDstFld[0], patientDstFld[0]));
			
			if (!patSrcRs.next())
				return;
			while (true) {
				while (patDstRs.next() && (patDstRs.getInt(1) < patSrcRs.getInt(1))) {
				}
				while (true) {
					try (Statement patDstModStm = dstCon.createStatement()) {
						if (patDstRs.getInt(1) == patSrcRs.getInt(1)) {
							patDstModStm.executeUpdate(sql = String.format("%s WHERE %s = %d ", SqlGenerator.genUpdate("patient", patientDstFld, patSrcRs), patientDstFld[0], patDstRs.getInt(1)));
							updCnt++;
							srcPrevNpasp = patSrcRs.getInt(1);
							patSrcRs.next();
							break;
						} else if (patSrcRs.getInt(1) < patDstRs.getInt(1)) {
							if (srcPrevNpasp != patSrcRs.getInt(1)) {
								patDstModStm.executeUpdate(sql = SqlGenerator.genInsert("patient", patientDstFld, patSrcRs));
								insCnt++;
							}
							srcPrevNpasp = patSrcRs.getInt(1);
							if (!patSrcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (patSrcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Patient: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPnambk() throws Exception {
		System.out.println("Importing pnambk.");
		String[] srcFld = new String[] {"NPASP", "CPOL_PR", "NUCH", "NAMBK", "DATAPR", "DATAOT", "ISHOD"};
		String[] dstFld = new String[] {"npasp", "cpol",    "nuch", "nambk", "datapr", "dataot", "ishod"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet nparsSrcRs = srcStm.executeQuery(sql = "SELECT P1 FROM N_PARS WHERE PTS = 0 ");
			if (!nparsSrcRs.next())
				throw new Exception("No npars cpol specified.");
			String npars = nparsSrcRs.getString(1);
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PATIENT", "PATIENT.NPASPG") + "WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PATIENT.DATAZ DESC ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_nambk", null) + String.format("WHERE p_nambk.cpol = %s ORDER BY p_nambk.npasp ", npars));
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(8))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(8))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[7];
							vals[1] = npars;
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = %s) ", SqlGenerator.genUpdate("p_nambk", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
							updCnt++;
							srcPrevNpasp = srcRs.getInt(8);
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(8) < dstRs.getInt(1))) {
							if (srcPrevNpasp != srcRs.getInt(8)) {
								String[] vals = SqlGenerator.getStringValues(srcRs);
								vals[0] = vals[7]; 
								vals[1] = npars;
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_nambk", dstFld, vals));
								insCnt++;
							}
							srcPrevNpasp = srcRs.getInt(8);
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pnambk: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPpreds() throws Exception {
		System.out.println("Importing ppreds.");
		String[] srcFld = new String[] {"NPASP", "FAM_RP", "IM_RP", "OT_RP", "SEX_PR", "DR_PR", "DOG_PR", "SPOLIS_PR", "POLIS_PR", "NAME_STR", "OGRN_STR", "BIRTHPLACE"};
		String[] dstFld = new String[] {"npasp", "fam",    "im",    "ot",    "pol",    "datar", "ndog",   "spolis",    "npolis",   "name_str", "ogrn_str", "birthplace"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PATIENT", "PATIENT.NPASPG") + "WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PATIENT.DATAZ DESC  ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_preds", null) + "ORDER BY p_preds.npasp ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(13))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(13))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[12];
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) ", SqlGenerator.genUpdate("p_preds", dstFld, vals), dstFld[0], vals[0]));
							updCnt++;
							srcPrevNpasp = srcRs.getInt(13);
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(13) < dstRs.getInt(1))) {
							if (srcPrevNpasp != srcRs.getInt(13)) {
								String[] vals = SqlGenerator.getStringValues(srcRs);
								vals[0] = vals[12]; 
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_preds", dstFld, vals));
								insCnt++;
							}
							srcPrevNpasp = srcRs.getInt(13);
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Ppreds: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPkov() throws Exception {
		System.out.println("Importing pkov.");
		String[] srcFld = new String[] {"NPASP", "LGOT", "DATAL", "GRI", "SIN", "PP", "DRG", "DOT", "OBO", "NDOC"};
		String[] dstFld = new String[] {"npasp", "lgot", "datal", "gri", "sin", "pp", "drg", "dot", "obo", "ndoc"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PKOV", "PATIENT.NPASPG") + "JOIN PATIENT ON (PATIENT.NPASP = PKOV.NPASP) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PKOV.LGOT ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_kov", null) + "ORDER BY p_kov.npasp, p_kov.lgot ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(11))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(11)) && (dstRs.getInt(2) == srcRs.getInt(2))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[10]; 
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = %s) ", SqlGenerator.genUpdate("p_kov", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
							updCnt++;
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(11) < dstRs.getInt(1)) || ((srcRs.getInt(11) == dstRs.getInt(1)) && (srcRs.getInt(2) < dstRs.getInt(2)))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[10]; 
							dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_kov", dstFld, vals));
							insCnt++;
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pkov: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPtub() throws Exception {
		System.out.println("Importing ptub.");
		String[] srcFld = new String[] {"NPASP", "KATEG", "DATAL"};
		String[] dstFld = new String[] {"npasp", "kateg", "datal"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PTUB", "PATIENT.NPASPG") + "JOIN PATIENT ON (PATIENT.NPASP = PTUB.NPASP) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PTUB.DATAL DESC ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_konti", null) + "ORDER BY p_konti.npasp ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(4))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(4))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[3]; 
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = %s) ", SqlGenerator.genUpdate("p_konti", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
							updCnt++;
							srcPrevNpasp = srcRs.getInt(4);
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(4) < dstRs.getInt(1))) {
							if (srcPrevNpasp != srcRs.getInt(4)) {
								String[] vals = SqlGenerator.getStringValues(srcRs);
								vals[0] = vals[3]; 
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_konti", dstFld, vals));
								insCnt++;
							}
							srcPrevNpasp = srcRs.getInt(4);
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pkonti: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importSvrach() throws Exception {
		System.out.println("Importing svrach.");
		String[] srcFld = new String[] {"FAM", "IM", "OT", "DATAR", "POL", "OBR", "IDV", "SNILS"};
		String[] dstFld = new String[] {"fam", "im", "ot", "datar", "pol", "obr", "idv", "snils"};
		
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "S_VRACH", null) + "WHERE (S_VRACH.PRIZN IS DISTINCT FROM '*') AND (NOT S_VRACH.SNILS IS NULL) AND COALESCE(S_VRACH.FAM, '') != '' AND COALESCE(S_VRACH.IM, '') != '' AND COALESCE(S_VRACH.OT, '') != '' AND NOT S_VRACH.DATAR IS NULL ");
			SqlClassifier<Integer> cls = new SqlClassifier<>(Integer.class, dstCon, "select pcod_s, pcod from n_z00 ");
			
			while (srcRs.next()) {
				ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "s_vrach", "s_vrach.pcod") + String.format("WHERE (s_vrach.snils = '%s') ", srcRs.getString(8)));
				
				try (Statement dstModStm = dstCon.createStatement()) {
					String[] vals = SqlGenerator.getStringValues(srcRs);
					vals[5] = cls.getValueOrSame(srcRs.getInt(6));
					if (dstRs.next()) {
						dstModStm.executeUpdate(sql = String.format("%s WHERE (s_vrach.pcod = %s) ", SqlGenerator.genUpdate("s_vrach", dstFld, vals), dstRs.getString(9)));
						updCnt++;
					} else {
						dstModStm.executeUpdate(sql = SqlGenerator.genInsert("s_vrach", dstFld, vals));
						insCnt++;
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Svrach: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importSmrab() throws Exception {
		System.out.println("Importing smrab.");
		String[] srcFld = new String[] {"PCOD", "CDOL", "CPOL",  "DATAU", "PRIZND"};
		String[] dstFld = new String[] {"pcod", "cdol", "cpodr", "datau", "priznd", "clpu", "cslu"};
		
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet nparsSrcRs = srcStm.executeQuery(sql = "SELECT P1 FROM N_PARS WHERE PTS = 6 ");
			if (!nparsSrcRs.next())
				throw new Exception("No npars clpu specified.");
			String clpu = nparsSrcRs.getString(1);
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "S_VRACH", "S_VRACH.SNILS") + "WHERE (S_VRACH.PRIZN IS DISTINCT FROM '*') ");
			
			while (srcRs.next()) {
				ResultSet dstRs = dstStm.executeQuery(sql = String.format("SELECT s_vrach.pcod FROM s_vrach WHERE (s_vrach.snils = '%s') ", srcRs.getString(6)));
				
					try (Statement dstModStm = dstCon.createStatement()) {
						if (dstRs.next()) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals = Arrays.copyOf(vals, dstFld.length);
							vals[0] = dstRs.getString(1);
							vals[5] = clpu;
							vals[2] = vals[2].replace(" ", "");
							switch (vals[2].length()) {
							case 3:
								vals[6] = "2";
								break;
							case 4:
								vals[6] = "1";
								break;
							case 7:
								vals[6] = "3";
								break;
							default:
								throw new Exception(String.format("Wrong cpodr code: %s.", vals[2]));
							}
							if (vals[4] != null)
								switch (vals[4]) {
								case "В":
									vals[4] = "1";
									break;
								case "Л":
									vals[4] = "2";
									break;
								case "М":
									vals[4] = "3";
									break;
								case "С":
									vals[4] = "4";
									break;
								default:
									throw new Exception(String.format("Wrong priznd code: %s.", vals[4]));
								}
							else
								vals[4] = "5";
							if (vals[1] == null)
								vals[1] = "254";
							
							dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "s_mrab", "s_mrab.id") + String.format("WHERE (s_mrab.pcod = '%s') AND (s_mrab.cpodr = '%s') AND (s_mrab.cdol = '%s') ", vals[0], vals[2], vals[1]));
							if (dstRs.next()) {
								dstModStm.executeUpdate(sql = String.format("%s WHERE (s_mrab.id = '%s') ", SqlGenerator.genUpdate("s_mrab", dstFld, vals), dstRs.getString(8)));
								updCnt++;
							} else {
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("s_mrab", dstFld, vals));
								insCnt++;
							}
						}
					}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Smrab: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPfiz() throws Exception {
		System.out.println("Importing pfiz.");
		String[] srcFld = new String[] {"NPASP", "PE", "PP", "PI", "FV", "FR", "GRZD", "PRB", "PRK", "VES", "ROST", "DATAZ", "VRK", "PFM1", "PFM2", "PFM3", "PFD1", "PFD2", "PFD3", "PFD4", "PFD5", "PFD6", "PRS", "PRIV", "PRIV_PR", "PRIV_N", "BCG", "POLIO", "AKDS", "ADSM", "ADM", "KOR", "PAROTIT", "KRASN", "GEPATIT", "BCG_VR", "POLIO_VR", "AKDS_VR", "KOR_VR", "PAROTIT_VR", "KRASN_VR", "GEPATIT_VR", "DAT_P", "PROFIL", "VEDOM", "VIB1", "VIB2", "IPR", "DAT_IPR", "OKR", "MENSES", "MENSES1", "PF1", "MF1", "EF1", "RF1"};
		String[] dstFld = new String[] {"npasp", "pe", "pp", "pi", "fv", "fr", "grzd", "prb", "prk", "ves", "rost", "dataz", "vrk", "pfm1", "pfm2", "pfm3", "pfd1", "pfd2", "pfd3", "pfd4", "pfd5", "pfd6", "prs", "priv", "priv_pr", "priv_n", "bcg", "polio", "akds", "adsm", "adm", "kor", "parotit", "krasn", "gepatit", "bcg_vr", "polio_vr", "akds_vr", "kor_vr", "parotit_vr", "krasn_vr", "gepatit_vr", "dat_p", "profil", "vedom", "vib1", "vib2", "ipr", "dat_ipr", "okr", "menses", "menses1", "pf1", "mf1", "ef1", "rf1"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PFIZ", "PATIENT.NPASPG") + "JOIN PATIENT ON (PATIENT.NPASP = PFIZ.NPASP) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PATIENT.DATAZ DESC ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_fiz", null) + "ORDER BY p_fiz.npasp ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(57))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(57))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[56]; 
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) ", SqlGenerator.genUpdate("p_fiz", dstFld, vals), dstFld[0], vals[0]));
							updCnt++;
							srcPrevNpasp = srcRs.getInt(57);
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(57) < dstRs.getInt(1))) {
							if (srcPrevNpasp != srcRs.getInt(57)) {
								String[] vals = SqlGenerator.getStringValues(srcRs);
								vals[0] = vals[56]; 
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_fiz", dstFld, vals));
								insCnt++;
							}
							srcPrevNpasp = srcRs.getInt(57);
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pfiz: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPinv() throws Exception {
		System.out.println("Importing pinv.");
		String[] srcFld = new String[] {"NPASP", "ZAB0", "ZAB1", "ZAB2", "ZAB3", "ZAB4", "ZAB5", "ZAB6", "ZAB7", "ZAB8", "ZAB9", "ZAB10", "ZAB11", "ZAB12", "ZAB13", "ZAB14", "ZAB15", "ZAB16", "ZAB17", "ZAB18", "ZAB19", "ZAB20", "ZAB21", "ZAB22", "ZAB23", "ZAB24", "ZAB25", "ZAB26", "ZAB27", "NAR0", "NAR1", "NAR2", "NAR3", "NAR4", "NAR5", "NAR6", "NAR7"};
		String[] dstFld = new String[] {"npasp", "zab0", "zab1", "zab2", "zab3", "zab4", "zab5", "zab6", "zab7", "zab8", "zab9", "zab10", "zab11", "zab12", "zab13", "zab14", "zab15", "zab16", "zab17", "zab18", "zab19", "zab20", "zab21", "zab22", "zab23", "zab24", "zab25", "zab26", "zab27", "nar0", "nar1", "nar2", "nar3", "nar4", "nar5", "nar6", "nar7"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		int srcPrevNpasp = -1;
		String sql = null;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PINV", "PATIENT.NPASPG") + "JOIN PATIENT ON (PATIENT.NPASP = PINV.NPASP) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PATIENT.DATAZ DESC ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_inv", null) + "ORDER BY p_inv.npasp ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(38))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(38))) {
							String[] vals = SqlGenerator.getStringValues(srcRs);
							vals[0] = vals[37]; 
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) ", SqlGenerator.genUpdate("p_inv", dstFld, vals), dstFld[0], vals[0]));
							updCnt++;
							srcPrevNpasp = srcRs.getInt(38);
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(38) < dstRs.getInt(1))) {
							if (srcPrevNpasp != srcRs.getInt(38)) {
								String[] vals = SqlGenerator.getStringValues(srcRs);
								vals[0] = vals[37]; 
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_inv", dstFld, vals));
								insCnt++;
							}
							srcPrevNpasp = srcRs.getInt(38);
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pinv: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPinvk() throws Exception {
		System.out.println("Importing pinvk.");
		String[] srcFld = new String[] {"NPASP", "DATAZ", "VRACH", "MESTO1", "PREDS", "UCHR", "NOM_MSE", "NAME_MSE", "RUK_MSE", "REZ_MSE", "D_OTPR", "D_INV", "D_INVP", "D_SROK", "SROK_INV", "DIAG", "DIAG_S1", "DIAG_S2", "DIAG_S3", "OSLOG", "FACTOR", "FACT1", "FACT2", "FACT3", "FACT4", "PROGNOZ", "POTENCIAL", "MED_REAB", "PS_REAB", "PROF_REAB", "SOC_REAB", "ZAKL", "DATAV", "ZAKL_NAME", "KLIN_PROGNOZ", "NAR1", "NAR2", "NAR3", "NAR4", "NAR5", "NAR6", "OGR1", "OGR2", "OGR3", "OGR4", "OGR5", "OGR6", "OGR7", "MR1N", "MR2N", "MR3N", "MR4N", "MR5N", "MR6N", "MR7N", "MR8N", "MR9N", "MR10N", "MR11N", "MR12N", "MR13N", "MR14N", "MR15N", "MR16N", "MR17N", "MR18N", "MR19N", "MR20N", "MR21N", "MR22N", "MR23N", "MR1V", "MR2V", "MR3V", "MR4V", "MR5V", "MR6V", "MR7V", "MR8V", "MR9V", "MR10V", "MR11V", "MR12V", "MR13V", "MR14V", "MR15V", "MR16V", "MR17V", "MR18V", "MR19V", "MR20V", "MR21V", "MR22V", "MR23V", "MR1D", "MR2D", "MR3D", "MR4D", "PR1N", "PR2N", "PR3N", "PR4N", "PR5N", "PR6N", "PR7N", "PR8N", "PR9N", "PR10N", "PR11N", "PR12N", "PR13N", "PR14N", "PR15N", "PR16N", "PR1V", "PR2V", "PR3V", "PR4V", "PR5V", "PR6V", "PR7V", "PR8V", "PR9V", "PR10V", "PR11V", "PR12V", "PR13V", "PR14V", "PR15V", "PR16V", "PR1D", "D_OSV"};
		String[] dstFld = new String[] {"npasp", "dataz", "vrach", "mesto1", "preds", "uchr", "nom_mse", "name_mse", "ruk_mse", "rez_mse", "d_otpr", "d_inv", "d_invp", "d_srok", "srok_inv", "diag", "diag_s1", "diag_s2", "diag_s3", "oslog", "factor", "fact1", "fact2", "fact3", "fact4", "prognoz", "potencial", "med_reab", "ps_reab", "prof_reab", "soc_reab", "zakl", "datav", "zakl_name", "klin_prognoz", "nar1", "nar2", "nar3", "nar4", "nar5", "nar6", "ogr1", "ogr2", "ogr3", "ogr4", "ogr5", "ogr6", "ogr7", "mr1n", "mr2n", "mr3n", "mr4n", "mr5n", "mr6n", "mr7n", "mr8n", "mr9n", "mr10n", "mr11n", "mr12n", "mr13n", "mr14n", "mr15n", "mr16n", "mr17n", "mr18n", "mr19n", "mr20n", "mr21n", "mr22n", "mr23n", "mr1v", "mr2v", "mr3v", "mr4v", "mr5v", "mr6v", "mr7v", "mr8v", "mr9v", "mr10v", "mr11v", "mr12v", "mr13v", "mr14v", "mr15v", "mr16v", "mr17v", "mr18v", "mr19v", "mr20v", "mr21v", "mr22v", "mr23v", "mr1d", "mr2d", "mr3d", "mr4d", "pr1n", "pr2n", "pr3n", "pr4n", "pr5n", "pr6n", "pr7n", "pr8n", "pr9n", "pr10n", "pr11n", "pr12n", "pr13n", "pr14n", "pr15n", "pr16n", "pr1v", "pr2v", "pr3v", "pr4v", "pr5v", "pr6v", "pr7v", "pr8v", "pr9v", "pr10v", "pr11v", "pr12v", "pr13v", "pr14v", "pr15v", "pr16v", "pr1d", "d_osv"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		String prevKey = "";
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PINVK", "PATIENT.NPASPG") + "JOIN PATIENT ON (PATIENT.NPASP = PINVK.NPASP) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PATIENT.DATAZ DESC, PINVK.DATAZ DESC ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_invk", null) + "ORDER BY p_invk.npasp, p_invk.dataz DESC ");
			
			SqlClassifier<String> n_v0m = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0m ");
			SqlClassifier<String> n_v0p = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0p ");
			SqlClassifier<String> n_v0r = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0r ");
			SqlClassifier<String> n_v0s = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0s ");
			SqlClassifier<String> n_v0a = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0a ");
			SqlClassifier<String> n_v0b = new SqlClassifier<>(String.class, dstCon, "SELECT pcod || '. ' || name, pcod FROM n_v0b ");
			SqlClassifier<String> n_v0c = new SqlClassifier<>(String.class, dstCon, "SELECT pcod || '. ' || name, pcod FROM n_v0c ");
			SqlClassifier<String> n_v0d = new SqlClassifier<>(String.class, dstCon, "SELECT pcod || '. ' || name, pcod FROM n_v0d ");
			SqlClassifier<String> n_v0e = new SqlClassifier<>(String.class, dstCon, "SELECT pcod || '. ' || name, pcod FROM n_v0e ");
			SqlClassifier<String> n_v0f = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0f ");
			SqlClassifier<String> n_v0g = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0g ");
			SqlClassifier<String> n_v0n = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0n ");
			SqlClassifier<String> n_v0h = new SqlClassifier<>(String.class, dstCon, "SELECT name, pcod FROM n_v0h ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(133))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						String[] vals = SqlGenerator.getStringValues(srcRs);
						vals[0] = vals[132];
						vals[3] = n_v0m.getValueOrNull(vals[3]);
						vals[4] = n_v0p.getValueOrNull(vals[4]);
						vals[9] = n_v0r.getValueOrNull(vals[9]);
						vals[14] = n_v0s.getValueOrNull(vals[14]);
						if ((vals[14] == null) || (vals[14].compareTo("2") > 0))
							vals[14] = "3";
						vals[20] = n_v0a.getValueOrNull(vals[20]);
						vals[21] = n_v0b.getValueOrNull(vals[21]);
						vals[22] = n_v0c.getValueOrNull(vals[22]);
						vals[23] = n_v0d.getValueOrNull(vals[23]);
						if ((vals[24] != null) && (vals[24].startsWith("52. способность к установлению контактов между людьми путем восприятия,переработки и передачи")))
							vals[24] = n_v0e.getValueOrNull("52. способность к установлению контактов между людьми путем восприятия,переработки и передачи информации");
						else
							vals[24] = n_v0e.getValueOrNull(vals[24]);
						vals[25] = n_v0f.getValueOrNull(vals[25]);
						vals[26] = n_v0g.getValueOrNull(vals[26]);
						vals[27] = n_v0n.getValueOrNull(vals[27]);
						vals[28] = n_v0n.getValueOrNull(vals[28]);
						vals[29] = n_v0n.getValueOrNull(vals[29]);
						vals[30] = n_v0n.getValueOrNull(vals[30]);
						vals[31] = n_v0h.getValueOrNull(vals[31]);
						
						if (hasData && (dstRs.getInt(1) == srcRs.getInt(133)) && (dstRs.getDate(2).getTime() == srcRs.getDate(2).getTime())) {
							dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = '%s') ", SqlGenerator.genUpdate("p_invk", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
							updCnt++;
							prevKey = vals[0] + vals[1];
							srcRs.next();
							break;
						} else if (!hasData || (srcRs.getInt(133) < dstRs.getInt(1)) ||  ((srcRs.getInt(133) == dstRs.getInt(1)) && (dstRs.getDate(2).getTime() < srcRs.getDate(2).getTime()))) {
							if (!prevKey.equals(vals[0] + vals[1])) {
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_invk", dstFld, vals));
								insCnt++;
							}
							prevKey = vals[0] + vals[1];
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pinvk: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPdiag() throws Exception {
		System.out.println("Importing pdiag.");
		String[] srcFld = new String[] {"NPASP", "DIAG", "DISP", "DATAD", "XZAB", "STADY", "D_VZ", "D_GRUP", "ISHOD", "DATAG", "DATAISH", "NMVD", "PAT", "PRIZ",  "PRIZI", "PPI", "NULL"};
		String[] dstFld = new String[] {"npasp", "diag", "disp", "datad", "xzab", "stady", "d_vz", "d_grup", "ishod", "datag", "dataish", "nmvd", "pat", "prizb", "prizi", "ppi", "named"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		String prevKey = "";
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
			) {
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PDIAG", "PATIENT.NPASPG, PDIAG.PD_PU, N_MKB.NAME") + "JOIN PATIENT ON (PATIENT.NPASP = PDIAG.NPASP) JOIN N_MKB ON (N_MKB.PCOD = PDIAG.DIAG) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') ORDER BY PATIENT.NPASPG, PDIAG.DIAG ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_diag", null) + "ORDER BY p_diag.npasp, p_diag.diag ");
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(18))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						boolean write = srcRs.getInt(19) == 1;
						String[] vals = SqlGenerator.getStringValues(srcRs);
						vals[0] = vals[17];
						vals[16] = vals[19];
						if (vals[7] != null) {
							if (vals[7].equals("2А"))
								vals[7] = "21";
							else if (vals[7].equals("2Б"))
								vals[7] = "22";
							else if (!vals[7].equals("1") && !vals[7].equals("3") && !vals[7].equals("4") && !vals[7].equals("5"))
									vals[7] = null;
						}
						if ((srcRs.getInt(3) == 1) && (srcRs.getInt(9) != 0) && (srcRs.getInt(9) != 1) && (srcRs.getInt(9) != 5) && (srcRs.getInt(9) != 12))
							write = true;
						if (hasData && write && (dstRs.getInt(1) == srcRs.getInt(18)) && dstRs.getString(2).equals(srcRs.getString(2))) {
							if ((dstRs.getDate(4) != null) && (srcRs.getDate(4) != null))
								if (dstRs.getDate(4).getTime() < srcRs.getDate(4).getTime()) {
									dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = '%s') ", SqlGenerator.genUpdate("p_diag", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
									updCnt++;
								}
							prevKey = vals[0] + vals[1];
							srcRs.next();
							break;
						} else if (!hasData || ((srcRs.getInt(18) < dstRs.getInt(1)) || ((srcRs.getInt(18) == dstRs.getInt(1)) && (srcRs.getString(2).compareTo(dstRs.getString(2)) < 0)))) {
							if (write && !prevKey.equals(vals[0] + vals[1])) {
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_diag", dstFld, vals));
								insCnt++;
							}
							prevKey = vals[0] + vals[1];
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pdiag: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
	
	private void importPdisp() throws Exception {
		System.out.println("Importing pdisp.");
		String[] srcFld = new String[] {"NPASP", "DIAG", "NULL", "D_VZ", "ISHOD", "DATAISH", "DATAG", "DATAD", "NULL",   "D_CDOL",  "D_GRUP", "NUCH",  "NEW_DIAG"};
		String[] dstFld = new String[] {"npasp", "diag", "pcod", "d_vz", "ishod", "dataish", "datag", "datad", "cod_sp", "cdol_ot", "d_grup", "d_uch", "diag_n"};
		
		boolean hasData;
		int updCnt = 0;
		int insCnt = 0;
		String sql = null;
		String prevKey = "";
		Hashtable<String, String> vrTbl = new Hashtable<>();
		String vrKey;
		String vrCode;
		
		try (Statement srcStm = srcCon.createStatement();
				Statement dstStm = dstCon.createStatement();
				Statement vrStm = dstCon.createStatement();
			) {
			ResultSet nparsSrcRs = srcStm.executeQuery(sql = "SELECT P1 FROM N_PARS WHERE PTS = 0 ");
			if (!nparsSrcRs.next())
				throw new Exception("No npars cpol specified.");
			String npars = nparsSrcRs.getString(1);
			ResultSet srcRs = srcStm.executeQuery(sql = SqlGenerator.genSelect(srcFld, "PDIAG", "PATIENT.NPASPG, S_VRACH.FAM, S_VRACH.IM, S_VRACH.OT, S_VRACH.DATAR") + "JOIN PATIENT ON (PATIENT.NPASP = PDIAG.NPASP) LEFT JOIN S_VRACH ON (S_VRACH.PCOD = PDIAG.D_NVR) WHERE (PATIENT.NPASPG > 0) AND (PATIENT.FAM != '') AND (PATIENT.IM != '') AND (PATIENT.OT != '') AND ((PDIAG.DISP = 1) OR (PDIAG.DISP = 2)) ORDER BY PATIENT.NPASPG, PDIAG.DIAG ");
			ResultSet dstRs = dstStm.executeQuery(sql = SqlGenerator.genSelect(dstFld, "p_disp", null) + "ORDER BY p_disp.npasp, p_disp.diag ");
			ResultSet vrRs;
			
			if (!srcRs.next())
				return;
			while (true) {
				while ((hasData = dstRs.next()) && (dstRs.getInt(1) < srcRs.getInt(14))) {
				}
				while (true) {
					try (Statement dstModStm = dstCon.createStatement()) {
						vrCode = null;
						if (srcRs.getString(15) != null) {
							vrKey = String.format("%s_%s_%s_%s", srcRs.getString(15), srcRs.getString(16), srcRs.getString(17), srcRs.getString(18));
							if (!vrTbl.containsKey(vrKey)) {
								vrRs = vrStm.executeQuery(String.format("SELECT pcod FROM s_vrach WHERE (fam = '%s') AND (im = '%s') AND (ot = '%s') AND (datar = '%s') ", srcRs.getString(15), srcRs.getString(16), srcRs.getString(17), srcRs.getString(18)));
								if (vrRs.next()) {
									vrCode = vrRs.getString(1);
									vrTbl.put(vrKey, vrCode);
								}
								vrRs.close();
							} else {
								vrCode = vrTbl.get(vrKey);
							}
						}
						String[] vals = SqlGenerator.getStringValues(srcRs);
						vals[0] = vals[13];
						vals[2] = npars;
						vals[8] = vrCode;
						if (vrCode == null) {
							vals[8] = "0";
							vals[9] = "0";
						}
						if (vals[7] == null)
							vals[7] = "2000-01-01";
						if (vals[3] == null)
							vals[3] = vals[7];
						if (vals[10] != null) {
							if (vals[10].equals("2А"))
								vals[10] = "21";
							else if (vals[10].equals("2Б"))
								vals[10] = "22";
							else if (!vals[10].equals("1") && !vals[10].equals("3") && !vals[10].equals("4") && !vals[10].equals("5"))
								vals[10] = null;
						}
						if (hasData &&(dstRs.getInt(1) == srcRs.getInt(14)) && dstRs.getString(2).equals(srcRs.getString(2))) {
							if ((dstRs.getDate(8) != null) && (srcRs.getDate(8) != null))
								if (dstRs.getDate(8).getTime() < srcRs.getDate(8).getTime()) {
									dstModStm.executeUpdate(sql = String.format("%s WHERE (%s = %s) AND (%s = '%s') ", SqlGenerator.genUpdate("p_disp", dstFld, vals), dstFld[0], vals[0], dstFld[1], vals[1]));
									updCnt++;
								}
							prevKey = vals[0] + vals[1];
							srcRs.next();
							break;
						} else if (!hasData || ((srcRs.getInt(14) < dstRs.getInt(1)) || ((srcRs.getInt(14) == dstRs.getInt(1)) && (srcRs.getString(2).compareTo(dstRs.getString(2)) < 0)))) {
							if (!prevKey.equals(vals[0] + vals[1])) {
								dstModStm.executeUpdate(sql = SqlGenerator.genInsert("p_disp", dstFld, vals));
								insCnt++;
							}
							prevKey = vals[0] + vals[1];
							if (!srcRs.next())
								break;
						} else {
							break;
						}
					}
				}
				if (srcRs.isAfterLast())
					break;
			}
			
			dstCon.commit();
			
			System.out.println(String.format("Pdisp: %d rows updated, %d rows inserted", updCnt, insCnt));
		} catch (Exception e) {
			dstCon.rollback();
			throw new Exception(sql, e);
		}
	}
}

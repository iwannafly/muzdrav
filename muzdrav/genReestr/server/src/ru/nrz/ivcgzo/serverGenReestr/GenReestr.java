package ru.nrz.ivcgzo.serverGenReestr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.DbfMapper;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenReestr.MedPolErrorInfo;
import ru.nkz.ivcgzo.thriftGenReestr.PaspErrorInfo;
import ru.nkz.ivcgzo.thriftGenReestr.ReestrNotFoundException;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr.Iface;
import ru.nkz.ivcgzo.thriftGenReestr.Err;
import ru.nkz.ivcgzo.thriftGenReestr.Med;
import ru.nkz.ivcgzo.thriftGenReestr.Pasp;

public class GenReestr extends Server implements Iface {
////////////////////////////////////////////////////////////////////////
//							Fields                                    //
////////////////////////////////////////////////////////////////////////

	private static Logger log = Logger.getLogger(GenReestr.class.getName());

	private TServer thrServ;
	private Properties prop;
	private final TResultSetMapper<Err, Err._Fields> rsmErr;
	private final Class<?>[] ErrTypes;
	private final TResultSetMapper<Med, Med._Fields> rsmMed;
	private final Class<?>[] MedTypes;
	private final TResultSetMapper<Pasp, Pasp._Fields> rsmPasp;
	private final Class<?>[] PaspTypes;
	private final TResultSetMapper<PaspErrorInfo, PaspErrorInfo._Fields> rsmPaspError;
	private final TResultSetMapper<MedPolErrorInfo, MedPolErrorInfo._Fields> rsmMedPolError;

	@Override
	public void start() throws Exception {
		try {
			prop = new Properties();
			prop.put("charSet", "Cp866");
			
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		ThriftGenReestr.Processor<Iface> proc = new ThriftGenReestr.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
        log.info("Start serverGenReestr");
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
			log.info("Stop serverGenReestr");
}

	public GenReestr(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		rsmPasp = new TResultSetMapper<>(Pasp.class, "sl_id",     "vid_rstr",   "str_org",    "name_str",  "ter_mu",     "kod_mu",     "df_per",  "fam",       "im",        "otch",      "dr",      "sex",       "fam_rp",    "im_rp",     "otch_rp",   "dr_pr",   "sex_pr",    "spolis_pr", "polis_pr",  "vpolis",     "spolis",    "polis",     "type_doc",   "docser",    "docnum",    "region",     "ter_liv",    "status",      "kob",       "ist_bol",   "vid_hosp",   "talon",     "ter_pol",    "pol",        "n_mk",      "id_lpu",     "id_smo",     "region_liv", "ogrn_str",  "birthplace","ter_mu_dir", "kod_mu_dir" );
		PaspTypes = new Class<?>[] {               Integer.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,Date.class,String.class,String.class,String.class,Date.class,String.class,String.class,String.class,String.class,Date.class,String.class,String.class,String.class,Integer.class,String.class,String.class,Integer.class,String.class,String.class,Integer.class,Integer.class,Integer.class, String.class,String.class,Integer.class,String.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,Integer.class,Integer.class};
		rsmMed = new TResultSetMapper<>(Med.class, "sl_id",     "id_med",     "kod_rez",    "kod_otd",    "d_pst",   "d_end",   "kl_usl",     "pr_exp",     "etap",       "pl_extr",    "usl",       "kol_usl",   "c_mu",       "diag",      "ds_s",      "pa_diag",   "pr_out",     "res_l",     "prof_fn",     "stoim",     "st_acpt",   "case",       "place",      "spec",       "prvd",       "v_mu",       "res_g",     "ssd",        "id_med_smo", "id_med_tf",  "psv",        "pk_mc",      "pr_pv",      "obst",      "n_schet",   "d_schet", "v_sch",      "talon_omt" );
		MedTypes = new Class<?>[] {               Integer.class,Integer.class,Integer.class,Integer.class,Date.class,Date.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,Double.class,Integer.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,Double.class,Double.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,String.class,Date.class,Integer.class,String.class};
		rsmErr = new TResultSetMapper<>(Err.class, "sl_id",      "ter_mu",    "kod_mu",     "id_med",     "kod_err",     "prim");
		ErrTypes = new Class<?>[] {                Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class};
		rsmPaspError = new TResultSetMapper<>(PaspErrorInfo.class);
		rsmMedPolError = new TResultSetMapper<>(MedPolErrorInfo.class);
	}

	@Override
	public void testConnection() throws TException {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		// TODO Auto-generated method stub
	}

	@Override		//ПОЛИКЛИНИКА
	public String getReestrInfoPol(int cpodr, long dn, long dk, int vidr,
			int vopl, int clpu, int terp, long df) throws KmiacServerException,
			ReestrNotFoundException, TException {
        String sqlwhere;
        String sqlmed;
        String sqlpasp;
        String path;
        boolean flag = false;
        sqlwhere = "";
        int npasp=0;
        
        if (vidr == 1) sqlwhere = "AND (v.dataz >= ? AND v.dataz <= ?) AND v.kod_rez = 0";
        if (vidr == 2) sqlwhere = "AND ((v.kod_rez = 0 AND v.dataz >= ? AND v.dataz <= ?) OR (v.d_rez >= ? AND v.d_rez <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 11)))";
        if (vidr == 3) sqlwhere = "AND (v.d_rez >= ? AND v.d_rez <= ?) AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11)";

		try (SqlModifyExecutor sme = tse.startTransaction();
			AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_errors_pasp_pol(?, ?, ?) ", cpodr, new Date(dn), new Date(dk));
			AutoCloseableResultSet acrsm = sme.execPreparedQuery("SELECT check_reestr_med_pol_errors(?, ?, ?) ", cpodr, new Date(dn), new Date(dk));
			AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.sl_id, e.id_med, e.npasp, p.fam, p.im, p.ot, p.datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm, n.pasp_med FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN patient p ON (p.npasp = e.npasp) WHERE (e.cpodr = ?) AND (e.dataz = ?) ORDER BY p.npasp, n.kderr ", cpodr, new Date(System.currentTimeMillis()) )) {
			sme.setCommit();
			ResultSet rs = acrsq.getResultSet();
			
			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
				StringBuilder sb = new StringBuilder(0x10000);
			
				while (rs.next()){
					if (rs.isFirst()){
						flag = true;
						sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
						sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
						sb.append("<head>");
						sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
						sb.append("<title>Файл проверок реестра</title>");
						sb.append("</head>");
						sb.append("<body>");
						sb.append("Протокол проверок реестра случаев оказания мед. помощи");
						sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
					}

					if(npasp != rs.getInt("npasp")){
						sb.append(String.format("<br> %s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("ot").trim()));
						sb.append(String.format("   Д.р. </b>  %1$td.%1$tm.%1$tY <br>", rs.getDate("datar").getTime()));
					}
					sb.append(String.format("%s %s ; %s <br>", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm")));
						if(rs.getInt("pasp_med") == 2){
	   						if(rs.getInt("sl_id") != 0){
	   							try {
	   								AutoCloseableResultSet acr = sse.execPreparedQuery("select datap, diag from p_vizit_amb where id = ?", rs.getInt("sl_id"));
	   								if (acr.getResultSet().next()){
	   									sb.append(String.format("%s %s ; %s ; ДИАГНОЗ: %s ; ", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm"), acr.getResultSet().getString("diag") ));
	   									if (acr.getResultSet().getDate("datap") != null) sb.append(String.format("Дата посещения: </b> %1$td.%1$tm.%1$tY <br>", new Date(acr.getResultSet().getDate("datap").getTime())));
	   								}
	   							} catch (SQLException e1) {
	   								e1.printStackTrace();
	   							}
	   						} 
							
						}
					npasp = rs.getInt("npasp");
				}
				osw.write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
				throw new KmiacServerException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not get pasp errors.");
		}
		
		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;
				sqlmed ="SELECT v.id::integer AS sl_id, " +
						"v.id::integer AS id_med, " +
						"v.kod_rez::integer AS kod_rez, " +
						"null::integer AS kod_otd, "+
						"v.datap::date as d_pst, " +
						"null::date AS d_end, "+
						"2::integer AS kl_usl, " +
						"null::integer AS pr_exp, "+
						"(case v.pl_extr is not null when true then v.pl_extr else 1 end)::integer AS pl_extr, " +
						"null::char(15) AS usl, "+
						"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then v.uet else null end)::double precision AS kol_usl, " +
						"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end)::integer AS c_mu, "+
						"v.diag::char(7) AS diag, " +
						"null::char(15) AS ds_s, "+
						"null::char(6) AS pa_diag, "+
						"null::integer AS pr_out, "+
						"null::integer AS res_l, "+
						"(CASE WHEN (m.c_nom = 2) THEN s.prof_d WHEN ((m.c_nom = 1) OR (m.c_nom = 3) OR (m.c_nom = 5) OR (m.c_nom = 6) OR (m.c_nom = 8) OR (m.c_nom = 9)) THEN s.prof_v ELSE NULL END)::integer AS profil, " +
						"v.stoim::double precision AS stoim, " +
						"v.cpos::integer AS case, " +
						"(case when ((v.mobs=1)or(v.mobs=4)or(v.mobs=8)or(v.mobs=9)) then 1 when (v.mobs = 2) then 2  when (v.mobs = 3) then 3 else 1 end)::integer AS place, " +
						"s.cod_sp::integer AS spec, " +
						"s.cod_m::integer AS prvd, " +
						"v.rezult::integer AS res_g, " +
						"vr.snils::char(14) AS ssd, " +
						"null::integer AS id_med_tf, "+
						"1::integer AS psv, " +
						"0::integer AS pr_pv, " +
						"null::char(15) AS obst, " +
						"null::char(20) AS n_schet, " +
						"null::date AS d_schet, " +
						"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
						"null::char(12) AS talon_omt "+
					    "FROM p_vizit_amb v JOIN patient p ON (v.npasp = p.npasp) " +
					    "LEFT JOIN n_n00 n ON (n.pcod = v.cpol) "+ 
						"LEFT JOIN n_m00 m ON (m.pcod = n.clpu) "+
					    "LEFT JOIN n_s00 s ON (s.pcod = v.cdol) "+
					    "LEFT JOIN s_vrach vr ON (vr.pcod = v.cod_sp) "+
						"WHERE  v.opl = ?  AND v.cpol = ? ";
			    sqlmed += sqlwhere;

//					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk)));
//						InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
//						zos.putNextEntry(new ZipEntry("med.dbf"));
//						while ((bufRead = dbfStr.read(buffer)) > 0)
//							zos.write(buffer, 0, bufRead);
//					} catch (SQLException e) {
//				        log.log(Level.ERROR, "SQl Exception: ", e);
//						throw new KmiacServerException();
//					}
				try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, vopl, cpodr, new Date(dn), new Date(dk)))){
					while (acrs.getResultSet().next()){
						try(InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
							zos.putNextEntry(new ZipEntry("med.dbf"));
							while ((bufRead = dbfStr.read(buffer)) > 0)
								zos.write(buffer, 0, bufRead);
						}
					}
				} catch (SQLException e) {
					log.log(Level.ERROR, "SQl Exception: ", e);
					throw new KmiacServerException();
				}
				    
				sqlpasp = "SELECT  v.id AS sl_id, " +
						"2::integer AS vid_rstr, " +
						"k.kdpsk::integer AS str_org, " +
						"v.kod_ter::integer AS ter_mu, " +
						"v.cpol::integer AS kod_mu, " +
						"CURRENT_DATE::date AS df_per, " +
						"p.fam::char(60) AS fam, " +
						"p.im::char(40) AS im, " +
						"p.ot::char(60) AS otch, " +
						"p.datar AS dr, " +
						"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
						"(case p.poms_strg>99 when true then p.snils else null end)::char(14) AS ssp, " +
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_tdoc END)::integer AS vpolis, "+ 
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_ser END)::char(10) AS spolis, "+
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_nom END)::char(20) AS polis, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.tdoc IS NOT NULL WHEN TRUE then p.tdoc else null end)::integer AS type_doc, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docser IS NOT NULL WHEN TRUE then p.docser else null end)::char(10) AS docser, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docnum IS NOT NULL WHEN TRUE then p.docnum else null end)::char(20) AS docnum, " +
						"ds.fam::char(60) AS fam_pr, "+
						"ds.im::char(40) AS im_pr, "+
						"ds.ot::char(40) AS otch_pr," +
						"ds.datar::date AS dr_pr, "+
						"(CASE (p.poms_strg > 99 AND CURRENT_DATE - p.datar < 18*365) OR (CURRENT_DATE - p.datar < 3*31 AND p.poms_ser IS NULL AND p.poms_nom IS NULL) WHEN TRUE THEN (CASE ds.pol = 1 WHEN TRUE THEN 'М' ELSE 'Ж' END) ELSE NULL END)::char(1) AS sex_pr, "+
						"ds.vpolis::char(10) AS vpolis_pr, "+
						"ds.spolis::char(10) AS spolis_pr, "+
						"ds.npolis::char(20) AS polis_pr, " +
						"ds.tdoc::integer AS type_docpr, "+
						"ds.docser::char(10) AS docser_pr, "+
						"ds.docnum::char(20) AS docnum_pr, " +
						"k.region::integer AS region, " +
						"null::char(10) AS ist_bol, " +
						"v.pl_extr::integer AS vid_hosp, " +
						"p.terp::integer AS ter_pol, " +
						"p.cpol_pr::integer AS pol, " +
						"(case n.nambk is not null when true then n.nambk else cast(p.npasp as char(20)) end)::char(20) AS n_mk, " +
						"p.npasp::integer AS id_lpu, " +
		    			"(case p.poms_strg>99 when true then p.birthplace else null end)::char(100) AS birthplace, " +
						"null::integer AS ter_mu_dir, " +
						"null::integer AS kod_mu_dir "+
				   		"FROM p_vizit_amb v JOIN patient p ON (v.npasp = p.npasp) " +
				   		"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = ?) "+
				   		"LEFT JOIN n_kas k ON (k.pcod = p.poms_strg) "+
				   		"LEFT JOIN p_preds ds ON (ds.npasp = p.npasp) "+
				   		"WHERE  v.opl = ?  AND v.cpol = ? ";
				sqlpasp += sqlwhere;

				try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, cpodr, vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, cpodr, vopl, cpodr, new Date(dn), new Date(dk)))){
					while (acrs.getResultSet().next()){
						try(InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()){
							zos.putNextEntry(new ZipEntry("pasp.dbf"));
							while ((bufRead = dbfStr.read(buffer)) > 0)
								zos.write(buffer, 0, bufRead);
						}
					}
				} catch (SQLException e) {
				    log.log(Level.ERROR, "SQl Exception: ", e);
					throw new KmiacServerException();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	private void InsertToKderr(int cslu, String snils, int kod_mu, int sl_id, int id_med, int id_lpu, int kod_err, String name_err) throws KmiacServerException, SQLException {
		int cuser = 0;
		if (cslu != 1)
			try (AutoCloseableResultSet acr = sse.execPreparedQuery("select m.pcod, m.cpodr from s_vrach v, s_mrab m where v.pcod=m.pcod and v.snils=? and m.cpodr=?", snils, kod_mu)) {
				if (acr.getResultSet().next())
					cuser = acr.getResultSet().getInt("pcod");
			} catch (Exception e) {
				e.printStackTrace();
			}
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("insert into w_kderr (cslu, cuser, cpodr, sl_id, id_med, npasp, kod_err, name_err, dataz) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", false, cslu, cuser, kod_mu, sl_id, id_med, id_lpu, kod_err, name_err, new Date(System.currentTimeMillis()));
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	private String RsTest(ResultSet rs, int cslu) throws KmiacServerException, SQLException {
		String str = "";
		String name_err = "";
		int kod_err = 0;
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("delete from w_kderr where sl_id = ? and id_med=? and cslu = ?", false, rs.getInt("sl_id"), rs.getInt("id_med"), cslu);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}

		if (rs.getInt("vid_rstr") != 1 && rs.getInt("vid_rstr") != 2 && rs.getInt("vid_rstr") != 3){
			str += " 5. Несуществующий вид реестра VID_RSTR<br>";
			kod_err = 5;
			name_err = "Несуществующий вид реестра VID_RSTR";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("kod_mu") == 0){
    		str += " 9. Отсутствует код территории МО KOD_MU<br>";
			kod_err = 9;
			name_err = "Отсутствует код территории МО KOD_MU";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("ter_mu") == 0){
    		str += " 10. Неверная территории МО TER_MU<br>";
			kod_err = 10;
			name_err = "Неверная территории МО TER_MU";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getDate("df_per") == null){
    		str += " 13. Не заполнена дата предоставления реестра в ТФ DF_PER<br>";
			kod_err = 13;
			name_err = "Не заполнена дата предоставления реестра в ТФ DF_PER";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getDate("d_pst") != null && rs.getDate("df_per") != null)
    		if (rs.getDate("d_pst").getTime() > rs.getDate("df_per").getTime()){
    			str += " 14. D_PST, D_END > SYSDATE. Дата услуги ранее или позднее принимаемого к оплате периода.<br>";
    			kod_err = 14;
    			name_err = "D_PST, D_END > SYSDATE. Дата услуги ранее или позднее принимаемого к оплате периода.";
    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
    	if (rs.getString("fam") == null){
    		str += " 15. Не заполнена фамилия FAM.<br>";
			kod_err = 15;
			name_err = "Не заполнена фамилия FAM.";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getString("im") == null){
    		str += " 16. Не заполнено имя IM.<br>";
			kod_err = 16;
			name_err = "Не заполнено имя IM.";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getString("otch") == null){
    		str += " 17. Не заполнено отчество OTCH.<br>";
			kod_err = 17;
			name_err = "Не заполнено отчество OTCH.";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getDate("dr") == null){
    		str += " 18. Не заполнена дата рождения DR<br>";
			kod_err = 18;
			name_err = "Не заполнена дата рождения DR";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getDate("d_pst") != null && rs.getDate("dr") != null)
    		if (rs.getDate("d_pst").getTime()<rs.getDate("dr").getTime()){
    			str += " 19. Ошибка в дате рождения<br>";
    			kod_err = 19;
    			name_err = "Ошибка в дате рождения";
    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
    	if ((rs.getString("sex").toUpperCase().charAt(0) != 'М') && (rs.getString("sex").toUpperCase().charAt(0) != 'Ж')){
    		str += " 21. Ошибка в кодировании пола SEX<br>";
			kod_err = 21;
			name_err = "Ошибка в кодировании пола SEX";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (((rs.getString("sex").toUpperCase().charAt(0) == 'М') && (rs.getString("otch").toUpperCase().endsWith("ИЧ") || rs.getString("otch").toUpperCase().endsWith("ОГЛЫ"))) ||
    		((rs.getString("sex").toUpperCase().charAt(0) == 'Ж') && (rs.getString("otch").toUpperCase().endsWith("НА") || rs.getString("otch").toUpperCase().endsWith("КЫЗЫ")))){
    		str += " 22. Несоответствие пола и отчества<br>";
			kod_err = 22;
			name_err = "Несоответствие пола и отчества";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") != 42 && (rs.getInt("type_doc") < 1 && rs.getInt("type_doc") > 18)){
    		str += " 31. Отсутствует / некорректный тип документа TYPE_DOC<br>";
			kod_err = 31;
			name_err = "Отсутствует / некорректный тип документа TYPE_DOC";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") == 0){
    		str += " 33. Отсутствует код области REGION<br>";
			kod_err = 33;
			name_err = "Отсутствует код области REGION";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") == 42 && rs.getInt("ter_liv") == 0){
    		str += " 35. Отсутствует код административной территории места жительства TER_LIV<br>";
			kod_err = 35;
			name_err = "Отсутствует код административной территории места жительства TER_LIV";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("status") == 0){
    		str += " 38. Неверный статус STATUS<br>";
			kod_err = 38;
			name_err = "Неверный статус STATUS";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") == 42 && rs.getInt("vid_rstr") == 1 && rs.getInt("vid_hosp") == 1 && rs.getInt("kl_usl") == 1 && rs.getString("talon") == null){
    		str += " 41. Плановый больной без талона<br>";
			kod_err = 41;
			name_err = "Плановый больной без талона";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") != 42 && ((rs.getString("docnum") == null) || (rs.getString("docser") == null))){
    		str += " 125. Отсутствует / некорректный номер документа, удостоверяющего личность<br>";
			kod_err = 125;
			name_err = "Отсутствует / некорректный номер документа, удостоверяющего личность";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
    	if (rs.getInt("region") != 42 && rs.getString("ogrn_str") == null){
    		str += " 246. Ошибка заполнения инообластного реестра. Не указан ОГРН.<br>";
			kod_err = 246;
			name_err = "Ошибка заполнения инообластного реестра. Не указан ОГРН.";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
		}
//    	if (rs.getInt("stoim") == 0)
//    		str += " 230. Отсутствует тариф<br>";

    	if (rs.getInt("vid_rstr") == 1){
        	if ((rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 8) && rs.getString("ist_bol") == null){
        		str += " 39. Отсутствует номер истории болезни<br>";
    			kod_err = 39;
    			name_err = "Отсутствует номер истории болезни";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("region") == 42 && rs.getInt("kl_usl") == 1 && rs.getInt("vid_hosp") == 1 && rs.getString("talon") == null){
        		str += " 41. Плановый больной без талона<br>";
    			kod_err = 41;
    			name_err = "Плановый больной без талона";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kod_otd") == 0){
        		str += " 47. Отсутствует код отделения<br>";
    			kod_err = 47;
    			name_err = "Отсутствует код отделения";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getDate("d_pst") == null){
        		str += " 51. Не заполнена дата госпитализации D_PST<br>";
    			kod_err = 51;
    			name_err = "Не заполнена дата госпитализации D_PST";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if ((rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 7 && rs.getInt("kl_usl") != 8) && rs.getDate("d_end") == null){
        		str += " 52. Отсутствует дата выписки<br>";
    			kod_err = 52;
    			name_err = "Отсутствует дата выписки";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getDate("d_pst") != null && rs.getDate("d_end") != null)
            	if (rs.getDate("d_pst").getTime() > rs.getDate("d_end").getTime() || (rs.getDate("d_end").getTime()-rs.getDate("d_pst").getTime())>=365){
            	str += " 53. Ошибка в датах госпитализации и выписки<br>";
    			kod_err = 53;
    			name_err = "Ошибка в датах госпитализации и выписки";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kl_usl") < 1 && rs.getInt("kl_usl") > 11){
        		str += " 54. Неверный вид помощи KL_USL<br>";
    			kod_err = 54;
    			name_err = "Неверный вид помощи KL_USL";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kl_usl") != 1 && rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 8){
        		str += " 55. Несоответствие вида реестра VID_RSTR виду помощи KL_USL<br>";
    			kod_err = 55;
    			name_err = "Несоответствие вида реестра VID_RSTR виду помощи KL_USL";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kl_usl") == 1 && rs.getInt("etap") == 0){
        		str += " 58. Не задан этап<br>";
    			kod_err = 58;
    			name_err = "Не задан этап";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kl_usl") == 1 && (rs.getInt("pr_out") == 8 || rs.getInt("pr_out") == 15)){
        		str += " 60. Ошибка кода причины выбытия<br>";
    			kod_err = 60;
    			name_err = "Ошибка кода причины выбытия";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("region") != 42 && rs.getInt("spec") == 0){
        		str += " 67. Отсутствует код врачебной специальности SPEC<br>";
    			kod_err = 67;
    			name_err = "Отсутствует код врачебной специальности SPEC";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("res_g") != 0){
        		str += " 77. Неверный код результата обращения RES_G<br>";
    			kod_err = 77;
    			name_err = "Неверный код результата обращения RES_G";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("region") == 42 && rs.getInt("str_org") == 0 && rs.getInt("etap") == 4){
        		str += " 93. Для незастрахованного указан этап долечивания<br>";
    			kod_err = 93;
    			name_err = "Для незастрахованного указан этап долечивания";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("region") == 42 && rs.getInt("str_org") == 0 && rs.getInt("vid_hosp") == 1){
        		str += " 94. Для незастрахованного указан признак планового больного<br>";
    			kod_err = 94;
    			name_err = "Для незастрахованного указан признак планового больного";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("etap") != 3 && rs.getInt("etap") != 4){
        		str += " 96. Неверно указан код этапа<br>";
    			kod_err = 96;
    			name_err = "Неверно указан код этапа";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getDate("d_pst") != null && rs.getDate("d_end") != null)
        		if ((rs.getDate("d_end").getTime()-rs.getDate("d_pst").getTime())>150){
        			str += " 215. Срок госпитализации >150 дней<br>";
        			kod_err = 215;
        			name_err = "Срок госпитализации >150 дней";
        			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("prof_fn") == 0 && rs.getInt("kl_usl") != 8 && rs.getInt("pr_exp") != 1){
        		str += " 216. Отсутствует код профиля финансового норматива<br>";
    			kod_err = 216;
    			name_err = "Отсутствует код профиля финансового норматива";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
        	if (rs.getInt("kl_usl") == 3 && rs.getInt("prof_fn") != 84){
        		str += " 257. Несоответствие профиля классу услуги (д.б. kl_usl=3, prof_fn=84)<br>";
    			kod_err = 257;
    			name_err = "Несоответствие профиля классу услуги (д.б. kl_usl=3, prof_fn=84)";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    		}
    	}

    	if (rs.getInt("vid_rstr") == 2){
        	if (rs.getInt("region") == 42 && rs.getInt("ter_pol") == 0){
        		str += " 42. Неверная территория поликлиники прикрепления TER_POL<br>";
    			kod_err = 42;
    			name_err = "Неверная территория поликлиники прикрепления TER_POL";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
    		if (rs.getInt("region") == 42 && rs.getInt("pol") == 0){
        		str += " 43. Неверная поликлиника прикрепления POL<br>";
    			kod_err = 43;
    			name_err = "Неверная поликлиника прикрепления POL";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("kl_usl") != 2 && rs.getInt("kl_usl") != 9){
        		str += " 55. Несоответствие вида реестра VID_RSTR виду помощи KL_USL<br>";
    			kod_err = 55;
    			name_err = "Несоответствие вида реестра VID_RSTR виду помощи KL_USL";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("pr_out") != 0){
        		str += " 60. Ошибка кода причины выбытия<br>";
    			kod_err = 60;
    			name_err = "Ошибка кода причины выбытия";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("res_l") != 0){
        		str += " 61. Ошибка кода результата лечения<br>";
    			kod_err = 61;
    			name_err = "Ошибка кода результата лечения";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("case") == 0){
        		str += " 63. Отсутствует цель посещения<br>";
    			kod_err = 68;
    			name_err = "Отсутствует цель посещения";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("place") == 0){
        		str += " 65. Отсутствует код места обслуживания<br>";
    			kod_err = 65;
    			name_err = "Отсутствует код места обслуживания";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("spec") == 0){
        		str += " 67. Отсутствует код врачебной специальности<br>";
    			kod_err = 67;
    			name_err = "Отсутствует код врачебной специальности";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if ((rs.getInt("spec") == 1) && (rs.getString("sex").toUpperCase().charAt(0) == 'М')){
        		str += " 69. Несоответствие врачебной специальности полу пациента<br>";
    			kod_err = 69;
    			name_err = "Несоответствие врачебной специальности полу пациента";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("prvd") == 0){
        		str += " 70. Отсутствует код врачебной должности PRVD<br>";
    			kod_err = 70;
    			name_err = "Отсутствует код врачебной должности PRVD";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if ((rs.getInt("prvd") == 11 || rs.getInt("prvd") == 12) && (rs.getString("sex").toUpperCase().charAt(0) == 'М')){
        		str += " 72. Несоответствие врачебной должности полу пациента<br>";
    			kod_err = 72;
    			name_err = "Несоответствие врачебной должности полу пациента";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("v_mu") == 0){
        		str += " 73. Отсутствует код вида первичной медико-санитарной помощи V_MU<br>";
    			kod_err = 73;
    			name_err = "Отсутствует код вида первичной медико-санитарной помощи V_MU";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if ((rs.getInt("v_mu") == 32) && (rs.getString("sex").toUpperCase().charAt(0) == 'М')){
        		str += " 75. Несоответствие V_MU полу SEX пациента<br>";
    			kod_err = 75;
    			name_err = "Несоответствие V_MU полу SEX пациента";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("res_g") == 0){
        		str += " 76. Отсутствует код результата обращения RES_G<br>";
    			kod_err = 76;
    			name_err = "Отсутствует код результата обращения RES_G";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getString("ssd") == null){
        		str += " 78. Отсутствует СНИЛС врача SSD<br>";
    			kod_err = 78;
    			name_err = "Отсутствует СНИЛС врача SSD";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("c_mu") == 0){
        		str += " 79. Отсутствует код единицы учета медицинской помощи C_MU<br>";
    			kod_err = 79;
    			name_err = "Отсутствует код единицы учета медицинской помощи C_MU";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
        	if (rs.getInt("kl_usl") == 9 && rs.getString("usl") == null){
        		str += " 86. Некорректные код услуги/ обстоятельства<br>";
    			kod_err = 86;
    			name_err = "Некорректные код услуги/ обстоятельства";
    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
        	}
    	}
    	
    	if (rs.getString("diag") == null){
    		str += " Отсутствует заключительный диагноз<br>";
    		kod_err = 0;
    		name_err = "Отсутствует заключительный диагноз";
			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	}
    	else{
    		try (AutoCloseableResultSet acr = sse.execPreparedQuery("select no_oms, sex_diag, no_osn_dia, no_ext_sta, no_ext_pol, vozr_min, vozr_max, stat from n_c00 where pcod=?", rs.getString("diag"))) {
    			if (acr.getResultSet().next()){
    	        	if (rs.getInt("region") != 42 && acr.getResultSet().getInt("no_oms") == 1){
    	        		str += "105. Диагноз МКБ сверх программы ОМС<br>";
    	        		kod_err = 105;
    	        		name_err = "Диагноз МКБ сверх программы ОМС";
    	    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
    	        	if ((rs.getString("sex").toUpperCase().charAt(0) != 'М' && acr.getResultSet().getInt("sex_diag") == 1) || (rs.getString("sex").toUpperCase().charAt(0) != 'Ж' && acr.getResultSet().getInt("sex_diag") == 2)){
    	        		str += "106. Несоответствие диагноза полу<br>";
    	        		kod_err = 106;
    	        		name_err = "Несоответствие диагноза полу";
    	    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
    	        	if (acr.getResultSet().getInt("no_osn_dia") == 1){
    	        		str += "108. Код МКБ не может быть использован для основного диагноза<br>";
    	        		kod_err = 108;
    	        		name_err = "Код МКБ не может быть использован для основного диагноза";
    	    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
    	        	if (rs.getInt("vid_rstr") == 1 && (rs.getInt("vid_hosp") == 2 || rs.getInt("pl_extr") == 2) && acr.getResultSet().getInt("no_ext_sta") == 1){
    	        		str += "109. Код МКБ не относится к экстренной стационарной помощи<br>";
    	        		kod_err = 109;
    	        		name_err = "Код МКБ не относится к экстренной стационарной помощи";
    	    			InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
    	        	if (rs.getInt("vid_rstr") == 2 && rs.getInt("vid_hosp") == 2 && acr.getResultSet().getInt("no_ext_pol") == 1){
    	        		str += "110. Код МКБ не относится к экстренной поликлинической помощи<br>";
    	        		kod_err = 110;
    	        		name_err = "Код МКБ не относится к экстренной поликлинической помощи";
    	        		InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
    	        	if (acr.getResultSet().getInt("no_oms") == 2){
    	        		str += "126. Не указана подрубрика в диагнозе<br>";
    	        		kod_err = 126;
    	        		name_err = "Не указана подрубрика в диагнозе";
    	    			if (cslu == 1)	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	    			else	InsertToKderr(cslu, rs.getString("ssd"), rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), kod_err, name_err);
    	        	}
	        	}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
		return str;
	}

//////////////////////////Classifiers ////////////////////////////////////

	@Override
    public final List<IntegerClassifier> getAllPolForCurrentLpu(final int lpuId) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM n_n00 WHERE clpu = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmN00 =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
			return rsmN00.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
    public final List<IntegerClassifier> getPolForCurrentLpu(final int polId) throws TException {
		final String sqlQuery = "SELECT pcod, name FROM n_n00 WHERE pcod = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmN00 =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, polId)) {
			return rsmN00.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

//	@Override
//	public List<IntegerClassifier> getAllLDSForCurrentLpu(int lpuId)
//			throws KmiacServerException, TException {
//		final String sqlQuery = "SELECT pcod, name FROM n_lds WHERE clpu = ?";
//    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLDS =
//    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
//		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
//			return rsmLDS.mapToList(acrs.getResultSet());
//		} catch (SQLException e) {
//			log.log(Level.ERROR, "SQl Exception: ", e);
//			throw new TException(e);
//		}
//	}
	@Override
	public List<IntegerClassifier> getAllLDSForCurrentLpu(int lpuId)
			throws KmiacServerException, TException {
		final String sqlQuery = "SELECT cpol as pcod, name FROM n_lds WHERE clpu = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLDS =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
			return rsmLDS.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> getLDSForCurrentLpu(int polId)
			throws KmiacServerException, TException {
		final String sqlQuery = "SELECT pcod, name FROM n_lds WHERE pcod = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLDS =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, polId)) {
			return rsmLDS.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> getAllOtdForCurrentLpu(int lpuId)
			throws KmiacServerException, TException {
		final String sqlQuery = "SELECT pcod, name FROM n_o00 WHERE clpu = ?";
    	final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmO00 =
    			new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
			return rsmO00.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQl Exception: ", e);
			throw new TException(e);
		}
	}

	@Override
    public final List<IntegerClassifier> getOtdForCurrentLpu(final int lpuId) throws TException {
        final String sqlQuery = "SELECT pcod, name FROM n_o00 WHERE pcod = ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmO00 =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, lpuId)) {
            return rsmO00.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
            throw new TException(e);
        }
    }
	
	@Override		//ЛДС
	public String getReestrInfoLDS(int cpodr, long dn, long dk, int vidr,
			int vopl, int clpu, int terp, long df)
			throws KmiacServerException, ReestrNotFoundException, TException {
        String sqlwhere = null;
        String sqlmed = null;
        String sqlpasp = null;
        String sqlr = null;
        String path = null;
        String sqlfrom = null;
        String isl = null;
        boolean flag = false;
		AutoCloseableResultSet acr;
		int npasp = 0;

        if (vidr == 1) 
        	sqlwhere = "WHERE ld.vopl = ? AND s.kdomc = 1 AND (ld.dataz >= ? AND ld.dataz <= ?) AND d.kod_rez = 0 ";
        if (vidr == 2) 
        	sqlwhere = "WHERE ld.vopl = ? AND s.kdomc = 1 AND ((d.kod_rez = 0 AND ld.dataz >= ? AND ld.dataz <= ?) OR (d.d_rez >= ? AND d.d_rez <= ? AND (d.kod_rez = 2 OR d.kod_rez = 4 OR d.kod_rez = 5 OR d.kod_rez = 11))) ";
        if (vidr == 3) 
        	sqlwhere = "WHERE ld.vopl = ? AND s.kdomc = 1 AND d.d_rez >= ? AND d.d_rez <= ? AND (d.kod_rez = 2 OR d.kod_rez = 4 OR d.kod_rez = 5 OR d.kod_rez = 11) ";

    	sqlwhere += " AND lds.cpol = ?";
//        if (cpodr != 0) {
//        	sqlwhere += " AND ld.kodotd = ?";
//        	clpu = cpodr;
//        }
//        else 
//        	sqlwhere += " AND substr(cast(ld.kodotd as varchar(10)),1,2)=cast(? as varchar(10))";

		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_errors_pasp_lds(?, ?, ?) ", cpodr, new Date(dn), new Date(dk));
				AutoCloseableResultSet acrsm = sme.execPreparedQuery("SELECT check_reestr_med_lds_errors(?, ?, ?) ", cpodr, new Date(dn), new Date(dk));
				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.sl_id, e.id_med, e.npasp, p.fam, p.im, p.ot, p.datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm, n.pasp_med FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN patient p ON (p.npasp = e.npasp) LEFT JOIN n_lds lds ON (e.cpodr = lds.pcod )  WHERE (lds.cpol = ?) AND (e.dataz = ?) ORDER BY p.npasp, n.kderr ", cpodr, new Date(System.currentTimeMillis()) )) {
				sme.setCommit();
				ResultSet rs = acrsq.getResultSet();
				
				try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
					StringBuilder sb = new StringBuilder(0x10000);
				
					while (rs.next()){
						if (rs.isFirst()){
							flag = true;
							sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
							sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
							sb.append("<head>");
							sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
							sb.append("<title>Файл проверок реестра</title>");
							sb.append("</head>");
							sb.append("<body>");
							sb.append("Протокол проверок реестра случаев оказания мед. помощи");
							sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
						}

						if(npasp != rs.getInt("npasp")){
							sb.append(String.format("<br> %s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("ot").trim()));
							sb.append(String.format("   Д.р. </b>  %1$td.%1$tm.%1$tY <br>", rs.getDate("datar").getTime()));
						}
						sb.append(String.format("%s %s ; %s <br>", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm")));
   						if(rs.getInt("pasp_med") == 2){
   	   						if(rs.getInt("sl_id") != 0){
   	   							try {
   	   								acr = sse.execPreparedQuery("select datav as datap, diag from p_isl_ld where nisl = ?", rs.getInt("sl_id"));
   	   								if (acr.getResultSet().next()){
   	   									sb.append(String.format("%s %s ; %s ; ДИАГНОЗ: %s ; ", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm"), acr.getResultSet().getString("diag") ));
   	   									if (acr.getResultSet().getDate("datap") != null) sb.append(String.format("Дата посещения: </b> %1$td.%1$tm.%1$tY <br>", new Date(acr.getResultSet().getDate("datap").getTime())));
   	   								}
   	   							} catch (SQLException e1) {
   	   								e1.printStackTrace();
   	   							}
   	   						} 
   							
   						}
						npasp = rs.getInt("npasp");
					}
					osw.write(sb.toString());
				} catch (IOException e) {
					e.printStackTrace();
					throw new KmiacServerException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new KmiacServerException("Could not get pasp errors.");
			}
        
		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;

//				if (isl.equals("Л"))
					sqlmed = "SELECT d.id::integer AS sl_id, " +
							"d.nisl::integer AS id_med, " +
							"d.kod_rez::integer AS kod_rez, " +
							"null::integer AS kod_otd, " +
							"ld.datav::date as d_pst, " +
							"null::date AS d_end, "+
							"9::integer AS kl_usl, " +
							"0::integer AS pr_exp, " +
							"1::integer AS pl_extr, " +
							"substr(d.cpok,2,length(d.cpok))::char(15) AS usl, " +
							"1::double precision AS kol_usl, " +
							"1::integer AS c_mu, "+
							"ld.diag::char(7) AS diag, " +
							"null::char(15) AS ds_s, " +
							"null::char(6) AS pa_diag, " +
							"null::integer AS pr_out, " +
							"null::integer AS res_l, " +
							"(CASE WHEN (m00.c_nom = 2) THEN s.prof_d WHEN ((m00.c_nom = 1) OR (m00.c_nom = 3) OR (m00.c_nom = 5) OR (m00.c_nom = 6) OR (m00.c_nom = 8) OR (m00.c_nom = 9)) THEN s.prof_v ELSE NULL END)::integer AS profil, " +
							"d.stoim::double precision AS stoim, " +
							"5::integer AS case, " +
							"1::integer AS place, " +
							"s.cod_sp::integer AS spec, " +
							"s.cod_m::integer AS prvd, " +
							"3::integer AS res_g, " +
							"vr.snils::char(14) AS ssd, " +
							"null::integer AS id_med_tf, " +
							"1::integer AS psv, " +
							"0::integer AS pr_pv, " +
							"null::char(15) AS obst, " +
							"null::char(20) AS n_schet, " +
							"null::date AS d_schet, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::char(12) AS talon_omt ";
			        sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) " +
			        		"JOIN p_rez_l d ON (ld.nisl = d.nisl) "+
			        		"JOIN n_lds lds ON (lds.pcod = ld.kodotd) "+
							"LEFT JOIN s_mrab m ON (ld.kodvr = m.pcod and ld.kodotd = m.cpodr) " +
							"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
//							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = ?) "+
							"LEFT JOIN n_m00 m00 ON (m00.pcod = lds.clpu) "+
							"LEFT JOIN s_vrach vr ON (vr.pcod = ld.kodvr) ";
			    	sqlwhere += " AND ld.id_gosp IS NULL AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) ";        
					sqlmed += sqlfrom;
					sqlmed += sqlwhere;
					sqlmed += " ORDER BY p.npasp";
					sqlmed += " UNION ";
//				if (isl.equals("Д"))
					sqlmed = "SELECT d.id::integer AS sl_id, " +
							"d.nisl::integer AS id_med, " +
							"d.kod_rez::integer AS kod_rez, " +
							"null::integer AS kod_otd, " +
							"ld.datav::date as d_pst, " +
							"null::date AS d_end, "+
							"9::integer AS kl_usl, " +
							"0::integer AS pr_exp, " +
							"1::integer AS pl_extr, " +
							"substr(d.kodisl,2,length(d.kodisl))::char(15) AS usl, " +
							"d.kol::double precision AS kol_usl, " +
							"1::integer AS c_mu, "+
							"ld.diag::char(7) AS diag, " +
							"null::char(15) AS ds_s, " +
							"null::char(6) AS pa_diag, " +
							"null::integer AS pr_out, " +
							"null::integer AS res_l, " +
							"(CASE WHEN (m00.c_nom = 2) THEN s.prof_d WHEN ((m00.c_nom = 1) OR (m00.c_nom = 3) OR (m00.c_nom = 5) OR (m00.c_nom = 6) OR (m00.c_nom = 8) OR (m00.c_nom = 9)) THEN s.prof_v ELSE NULL END)::integer AS profil, " +
							"d.stoim::double precision AS stoim, " +
							"5::integer AS case, " +
							"1::integer AS place, " +
							"s.cod_sp::integer AS spec, " +
							"s.cod_m::integer AS prvd, " +
							"3::integer AS res_g, " +
							"vr.snils::char(14) AS ssd, " +
							"null::integer AS id_med_tf, " +
							"1::integer AS psv, " +
							"0::integer AS pr_pv, " +
							"null::char(15) AS obst, " +
							"null::char(20) AS n_schet, " +
							"null::date AS d_schet, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::char(12) AS talon_omt ";
					sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) " +
							"JOIN p_rez_d d ON (ld.nisl = d.nisl) "+
							"JOIN n_lds lds ON (lds.pcod = ld.kodotd) "+
							"LEFT JOIN s_mrab m ON (ld.kodvr = m.pcod and ld.kodotd = m.cpodr) " +
							"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
//							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = ?) "+
							"LEFT JOIN n_m00 m00 ON (m00.pcod = lds.clpu) "+
							"LEFT JOIN s_vrach vr ON (vr.pcod = ld.kodvr) ";
			    	sqlwhere += " AND ld.id_gosp IS NULL AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) OR ((substr(d.kodisl,1,1)='B') AND (substr(cast(ld.kodotd as varchar(7)),1,2)=substr(cast(ld.naprotd as varchar(7)),1,2)))) ";        

					sqlmed += sqlfrom;
					sqlmed += sqlwhere;
					sqlmed += " ORDER BY p.npasp";
					
					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, vopl, new Date(dn), new Date(dk), new Date(dn), new Date(dk), cpodr)) : (sse.execPreparedQuery(sqlmed, clpu, vopl, new Date(dn), new Date(dk), cpodr));
							InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
						zos.putNextEntry(new ZipEntry("med.dbf"));
						while ((bufRead = dbfStr.read(buffer)) > 0)
							zos.write(buffer, 0, bufRead);
					} catch (SQLException e) {
				        log.log(Level.ERROR, "SQl Exception: ", e);
						throw new KmiacServerException();
					}
				    
					sqlpasp = "SELECT d.id::integer AS sl_id, " +
							"ld.napravl::integer AS vid_rstr, " +
							"k.kdpsk::integer AS str_org, " +
							"10::integer AS ter_mu, " +
							"lds.cpol::integer AS kod_mu, " +
							"CURRENT_DATE::date AS df_per, " +
							"p.fam::char(60) AS fam, " +
							"p.im::char(40) AS im, " +
							"p.ot::char(60) AS otch, " +
							"p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(case p.poms_strg>99 when true then p.snils else null end)::char(14) AS ssp, " +
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_tdoc END)::integer AS vpolis, "+ 
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_ser END)::char(10) AS spolis, "+
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_nom END)::char(20) AS polis, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.tdoc IS NOT NULL WHEN TRUE then p.tdoc else null end)::integer AS type_doc, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docser IS NOT NULL WHEN TRUE then p.docser else null end)::char(10) AS docser, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docnum IS NOT NULL WHEN TRUE then p.docnum else null end)::char(20) AS docnum, " +
							"ds.fam::char(60) AS fam_pr, "+
							"ds.im::char(40) AS im_pr, "+
							"ds.ot::char(40) AS otch_pr," +
							"ds.datar::date AS dr_pr, " +
							"(CASE (p.poms_strg > 99 AND CURRENT_DATE - p.datar < 18*365) OR (CURRENT_DATE - p.datar < 3*31 AND p.poms_ser IS NULL AND p.poms_nom IS NULL) WHEN TRUE THEN (CASE ds.pol = 1 WHEN TRUE THEN 'М' ELSE 'Ж' END) ELSE NULL END)::char(1) AS sex_pr, "+
							"ds.vpolis::char(10) AS vpolis_pr, "+
							"ds.spolis::char(10) AS spolis_pr, "+
							"ds.npolis::char(20) AS polis_pr, " +
							"ds.tdoc::integer AS type_docpr, "+
							"ds.docser::char(10) AS docser_pr, "+
							"ds.docnum::char(20) AS docnum_pr, " +
							"k.region::integer AS region, " +
							"null::char(10) AS ist_bol, " +
							"1::integer AS vid_hosp, " +
							"p.terp::integer AS ter_pol, " +
							"p.cpol_pr::integer AS pol, " +
//							"(case n.nambk is not null when true then n.nambk else cast(p.npasp as char(20)) end)::char(20) AS n_mk, " +
							"p.npasp::char(20) AS n_mk, " +
							"p.npasp::integer AS id_lpu, " +
		    				"(case p.poms_strg>99 when true then p.birthplace else null end)::char(100) AS birthplace, " +
							"10::integer AS ter_mu_dir, " +
							"ld.naprotd::integer AS kod_mu_dir ";
//							"(case when (length(cast(ld.kodotd as varchar(7)))>3) then substr(cast(ld.kodotd as varchar(7)),1,2)::integer when (length(cast(ld.kodotd as varchar(7)))<=3) then ld.kodotd else 0 end)::integer AS kod_mu_dir ";
					sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) " +
							"JOIN p_rez_d d ON (d.nisl = ld.nisl) " +
							"JOIN n_lds lds ON (lds.pcod = ld.kodotd) "+
							"LEFT JOIN s_mrab m ON (ld.kodvr = m.pcod and ld.kodotd = m.cpodr) " +
							"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
//							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = ?) "+
							"LEFT JOIN n_kas k ON (k.pcod = p.poms_strg) "+
							"LEFT JOIN p_preds ds ON (ds.npasp = p.npasp) "+
							"LEFT JOIN n_m00 m00 ON (m00.pcod = lds.clpu) "+
							"LEFT JOIN s_vrach vr ON (vr.pcod = ld.kodvr) ";
			    	sqlwhere += " AND ld.id_gosp IS NULL AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) OR ((substr(d.kodisl,1,1)='B') AND (substr(cast(ld.kodotd as varchar(7)),1,2)=substr(cast(ld.naprotd as varchar(7)),1,2)))) ";        
					sqlpasp += sqlfrom;
					sqlpasp += sqlwhere;
					sqlpasp += " ORDER BY p.npasp ";
		        	sqlpasp += " UNION ";
					sqlpasp = "SELECT d.id::integer AS sl_id, " +
							"ld.napravl::integer AS vid_rstr, " +
							"k.kdpsk::integer AS str_org, " +
							"10::integer AS ter_mu, " +
							"lds.cpol::integer AS kod_mu, " +
							"CURRENT_DATE::date AS df_per, " +
							"p.fam::char(60) AS fam, " +
							"p.im::char(40) AS im, " +
							"p.ot::char(60) AS otch, " +
							"p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(case p.poms_strg>99 when true then p.snils else null end)::char(14) AS ssp, " +
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_tdoc END)::integer AS vpolis, "+ 
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_ser END)::char(10) AS spolis, "+
							"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_nom END)::char(20) AS polis, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.tdoc IS NOT NULL WHEN TRUE then p.tdoc else null end)::integer AS type_doc, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docser IS NOT NULL WHEN TRUE then p.docser else null end)::char(10) AS docser, "+
							"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docnum IS NOT NULL WHEN TRUE then p.docnum else null end)::char(20) AS docnum, " +
							"ds.fam::char(60) AS fam_pr, "+
							"ds.im::char(40) AS im_pr, "+
							"ds.ot::char(40) AS otch_pr," +
							"ds.datar::date AS dr_pr, " +
							"(CASE (p.poms_strg > 99 AND CURRENT_DATE - p.datar < 18*365) OR (CURRENT_DATE - p.datar < 3*31 AND p.poms_ser IS NULL AND p.poms_nom IS NULL) WHEN TRUE THEN (CASE ds.pol = 1 WHEN TRUE THEN 'М' ELSE 'Ж' END) ELSE NULL END)::char(1) AS sex_pr, "+
							"ds.vpolis::char(10) AS vpolis_pr, "+
							"ds.spolis::char(10) AS spolis_pr, "+
							"ds.npolis::char(20) AS polis_pr, " +
							"ds.tdoc::integer AS type_docpr, "+
							"ds.docser::char(10) AS docser_pr, "+
							"ds.docnum::char(20) AS docnum_pr, " +
							"k.region::integer AS region, " +
							"null::char(10) AS ist_bol, " +
							"1::integer AS vid_hosp, " +
							"p.terp::integer AS ter_pol, " +
							"p.cpol_pr::integer AS pol, " +
//							"(case n.nambk is not null when true then n.nambk else cast(p.npasp as char(20)) end)::char(20) AS n_mk, " +
							"p.npasp::char(20) AS n_mk, " +
							"p.npasp::integer AS id_lpu, " +
		    				"(case p.poms_strg>99 when true then p.birthplace else null end)::char(100) AS birthplace, " +
							"10::integer AS ter_mu_dir, " +
							"ld.naprotd::integer AS kod_mu_dir ";
//							"(case when (length(cast(ld.kodotd as varchar(7)))>3) then substr(cast(ld.kodotd as varchar(7)),1,2)::integer when (length(cast(ld.kodotd as varchar(7)))<=3) then ld.kodotd else 0 end)::integer AS kod_mu_dir ";
					sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) " +
							"JOIN p_rez_l d ON (d.nisl = ld.nisl) " +
							"JOIN n_lds lds ON (lds.pcod = ld.kodotd) "+
							"LEFT JOIN s_mrab m ON (ld.kodvr = m.pcod and ld.kodotd = m.cpodr) " +
							"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
//							"LEFT JOIN p_nambk n ON (n.npasp = p.npasp and n.cpol = ?) "+
							"LEFT JOIN n_kas k ON (k.pcod = p.poms_strg) "+
							"LEFT JOIN p_preds ds ON (ds.npasp = p.npasp) "+
							"LEFT JOIN n_m00 m00 ON (m00.pcod = lds.clpu) "+
							"LEFT JOIN s_vrach vr ON (vr.pcod = ld.kodvr) ";
			    	sqlwhere += " AND ld.id_gosp IS NULL AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) ";        
					sqlpasp += sqlfrom;
					sqlpasp += sqlwhere;
					sqlpasp += " ORDER BY p.npasp ";
						
					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, vopl, new Date(dn), new Date(dk), new Date(dn), new Date(dk), cpodr)) : (sse.execPreparedQuery(sqlpasp, vopl, new Date(dn), new Date(dk), cpodr));
							InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
						zos.putNextEntry(new ZipEntry("pasp.dbf"));
						while ((bufRead = dbfStr.read(buffer)) > 0)
							zos.write(buffer, 0, bufRead);
					} catch (SQLException e) {
				        log.log(Level.ERROR, "SQl Exception: ", e);
						throw new KmiacServerException();
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return path;
	}

	@Override		//СТАЦИОНАР
	public String getReestrInfoOtd(int cpodr, long dn, long dk, int vidr,
			int vopl, int clpu, int terp, long df) throws KmiacServerException,
			ReestrNotFoundException, TException {
        String path = null;
        String sqlr = null;
        String sqlmed = null;
        String sqlpasp = null;
        String sqlfrom = null;
        String sqlwhere = null;
        boolean flag = false;
        int npasp =0;

        if (vidr == 1) sqlwhere = " AND g.dataz >= ? AND g.dataz <= ? AND g.kod_rez = 0";
        if (vidr == 2) sqlwhere = " AND ((g.dataz >= ? AND g.dataz <= ? AND g.kod_rez = 0) OR (g.d_rez >= ? AND g.d_rez <= ? AND (g.kod_rez = 2 OR g.kod_rez = 4 OR g.kod_rez = 5 OR g.kod_rez = 11)))";
        if (vidr == 3) sqlwhere = " AND g.d_rez >= ? AND g.d_rez <= ? AND (g.kod_rez = 2 OR g.kod_rez = 4 OR g.kod_rez = 5 OR g.kod_rez = 11)";

   		try (SqlModifyExecutor sme = tse.startTransaction();
   				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_errors_pasp_hosp(?, ?, ?) ", clpu, new Date(dn), new Date(dk));
   				AutoCloseableResultSet acrsm = sme.execPreparedQuery("SELECT check_reestr_med_sta_errors(?, ?, ?) ", clpu, new Date(dn), new Date(dk));
   				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.sl_id, e.id_med, e.npasp, p.fam, p.im, p.ot, p.datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm, n.pasp_med FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN patient p ON (p.npasp = e.npasp) WHERE e.cslu=1 AND substr(cast(e.cpodr as varchar(10)),1,2)=cast(? as varchar(10)) AND (e.dataz = ?) ORDER BY p.npasp, n.kderr ", clpu, new Date(System.currentTimeMillis()) )) {
   				sme.setCommit();
   				ResultSet rs = acrsq.getResultSet();
   				
   				try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
   					StringBuilder sb = new StringBuilder(0x10000);
   				
   					while (rs.next()){
   						if (rs.isFirst()){
   							flag = true;
   							sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
   							sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
   							sb.append("<head>");
   							sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
   							sb.append("<title>Файл проверок реестра</title>");
   							sb.append("</head>");
   							sb.append("<body>");
   							sb.append("Протокол проверок реестра случаев оказания мед. помощи");
   							sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
   						}

   						if(npasp != rs.getInt("npasp")){
   							sb.append(String.format("<br> %s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("ot").trim()));
   							sb.append(String.format("   Д.р. </b>  %1$td.%1$tm.%1$tY <br>", rs.getDate("datar").getTime()));
   						}
   						
						sb.append(String.format("%s %s ; %s <br>", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm")));
   						if(rs.getInt("pasp_med") == 2){
   	   						if(rs.getInt("sl_id") != 0){
   	   							try {
   	   								AutoCloseableResultSet acr = sse.execPreparedQuery("select datagos as datap, diag_p as diag from c_gosp where ngosp = ?", rs.getInt("sl_id"));
   	   								if (acr.getResultSet().next()){
   	   									sb.append(String.format("%s %s ; %s ; ДИАГНОЗ: %s ; ", rs.getInt("kderr"), rs.getString("err_name"), rs.getString("err_comm"), acr.getResultSet().getString("diag") ));
   	   									if (acr.getResultSet().getDate("datap") != null) sb.append(String.format("Дата госпитализации: </b> %1$td.%1$tm.%1$tY <br>", new Date(acr.getResultSet().getDate("datap").getTime())));
   	   							}
   	   							} catch (SQLException e1) {
   	   								e1.printStackTrace();
   	   							}
   	   						} 
   							
   						}
   							
   						npasp = rs.getInt("npasp");
   					}
   					osw.write(sb.toString());
   				} catch (IOException e) {
   					e.printStackTrace();
   					throw new KmiacServerException();
   				}
   			} catch (Exception e) {
   				e.printStackTrace();
   				throw new KmiacServerException("Could not get pasp errors.");
   			}

   		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;
		        sqlmed = "SELECT g.ngosp::integer AS sl_id, " +
		        		"g.id::integer AS id_med, " +
		        		"g.kod_rez::integer AS kod_rez, " +
		        		"g.cotd_p::integer AS kod_otd, " +
		        		"g.datap::date as d_pst, " +
		        		"null::date as d_end, " +
		        		"3::integer AS kl_usl, " +
		        		"null::integer AS pr_exp, " +
						"null::integer AS pl_extr, " +
						"null::char(15) AS usl, " +
						"null::double precision AS kol_usl, " +
						"2::integer AS c_mu, " +
						"g.diag_p::char(7) AS diag, " +
						"null::char(7) AS ds_s, " +
						"null::char(6) AS pa_diag, " +
						"null::integer AS pr_out, " +
						"null::integer AS res_l, "+
						"(CASE WHEN (m00.c_nom = 2) THEN s.prof_d WHEN ((m00.c_nom = 1) OR (m00.c_nom = 3) OR (m00.c_nom = 5) OR (m00.c_nom = 6) OR (m00.c_nom = 8) OR (m00.c_nom = 9)) THEN s.prof_v ELSE NULL END)::integer AS profil, " +
						"null::double precision AS stoim, " +
						"null::integer AS case, " +
						"null::integer AS place," +
						"s.cod_sp::integer AS spec, " +
						"null::integer AS prvd, " +
						"null::integer AS res_g, " +
						"null::char(14) AS ssd, " +
						"null::integer AS id_med_tf, " +
						"1::integer AS psv, " +
						"null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt ";
	        	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) " +
	        			"JOIN n_o00 o ON (o.pcod = g.cotd_p) "+
	        			"LEFT JOIN s_mrab m ON (g.cuser = m.pcod and g.cotd_p = m.cpodr) " +
	        			"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
	        			"LEFT JOIN n_m00 m00 ON (m00.pcod = m.clpu) ";
	        	sqlmed += sqlfrom; 
	        	sqlmed += "WHERE g.pr_out<>0 ";
	            if (cpodr != 0) {
	            	sqlmed += " AND g.cotd = ? "+sqlwhere;
	            	clpu = cpodr;
	            }
	            else 
	            	sqlmed += " AND substr(cast(g.cotd as varchar(10)),1,2)=cast(? as varchar(10)) "+sqlwhere;
	        	sqlmed += " UNION ";
	            sqlmed = "SELECT g.ngosp::integer AS sl_id, " +
	            		"g.id::integer AS id_med, " +
	            		"g.kod_rez::integer AS kod_rez, " +
	            		"o.cotd::integer AS kod_otd, " +
	            		"g.datagos::date as d_pst, " +
	            		"o.datav::date as d_end, " +
	            		"1::integer AS kl_usl, " +
	            		"0::integer AS pr_exp, " +
	    				"(CASE g.pl_extr=1 when true then 2 else (CASE g.pl_extr=2 when true then 1 else 1 end) end)::integer AS pl_extr, " +
	    				"null::char(15) AS usl, " +
	    				"null::double precision AS kol_usl, " +
	    				"2::integer AS c_mu, " +
	    				"c.cod::char(7) AS diag, " +
	    				"(select get_ds_s(c.id_gosp))::char(7) AS ds_s, " +
	    				"null::char(6) AS pa_diag, " +
	    				"ap0.c_obl::integer AS pr_out, " +
	    				"aq0.cod_obl::integer AS res_l, "+
						"(CASE WHEN (m00.c_nom = 2) THEN s.prof_d WHEN ((m00.c_nom = 1) OR (m00.c_nom = 3) OR (m00.c_nom = 5) OR (m00.c_nom = 6) OR (m00.c_nom = 8) OR (m00.c_nom = 9)) THEN s.prof_v ELSE NULL END)::integer AS profil, " +
	    				"null::double precision AS stoim, " +
	    				"null::integer AS case," +
	    				"null::integer AS place," +
						"s.cod_sp::integer AS spec, " +
	    				"null::integer AS prvd, " +
	    				"null::integer AS res_g, " +
	    				"null::char(14) AS ssd, " +
	    				"null::integer AS id_med_tf, " +
	    				"1::integer AS psv, " +
	    				"null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt ";
            	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) " +
            			"JOIN c_otd o ON (g.id = o.id_gosp) " +
	        			"JOIN n_o00 o00 ON (o00.pcod = o.cotd) "+
            			"LEFT JOIN c_diag c ON (o.id_gosp = c.id_gosp AND c.prizn=4) " +
            			"LEFT JOIN s_mrab m ON (o.vrach = m.pcod and o.cotd = m.cpodr) " +
            			"LEFT JOIN n_s00 s ON (m.cdol = s.pcod) "+
            			"LEFT JOIN n_ap0 ap0 ON (ap0.pcod = o.ishod) "+
            			"LEFT JOIN n_aq0 aq0 ON (aq0.pcod = o.result) "+
            			"LEFT JOIN n_m00 m00 ON (m00.pcod = m.clpu) ";
            	sqlmed += sqlfrom; 
            	sqlmed += "WHERE g.pr_out=0 AND o.datav is not null ";
	            if (cpodr != 0) {
	            	sqlmed += " AND o.cotd = ? "+sqlwhere;
	            	clpu = cpodr;
	            }
	            else 
	            	sqlmed += " AND substr(cast(o.cotd as varchar(10)),1,2)=cast(? as varchar(10)) "+sqlwhere;
            	sqlmed += " ORDER BY p.npasp "; //ругается

//    			try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk)));
//	    				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
//							zos.putNextEntry(new ZipEntry("med.dbf"));
//							while ((bufRead = dbfStr.read(buffer)) > 0)
//								zos.write(buffer, 0, bufRead);
//						} catch (SQLException e) {
//							log.log(Level.ERROR, "SQl Exception: ", e);
//							throw new KmiacServerException();
//						}

    			try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk)))){
					while (acrs.getResultSet().next()){
						try(InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
							zos.putNextEntry(new ZipEntry("med.dbf"));
							while ((bufRead = dbfStr.read(buffer)) > 0)
								zos.write(buffer, 0, bufRead);
						} catch (SQLException e) {
							log.log(Level.ERROR, "SQl Exception: ", e);
							throw new KmiacServerException();
						}
    				}
				}
				    
				sqlpasp ="SELECT g.ngosp::integer AS sl_id, " +
						"1::integer AS vid_rstr, " +
						"k.kdpsk::integer AS str_org, " +
						"10::integer AS ter_mu," +
						"m.clpu::integer AS kod_mu," +
						"current_date::date AS df_per, " +
						"p.fam::char(60) AS fam, " +
						"p.im::char(40) AS im, " +
						"p.ot::char(60) AS otch, " +
						"p.datar AS dr, " +
						"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
						"(case p.poms_strg>99 when true then p.snils else null end)::char(14) AS ssp, " +
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_tdoc END)::integer AS vpolis, "+ 
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_ser END)::char(10) AS spolis, "+
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_nom END)::char(20) AS polis, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.tdoc IS NOT NULL WHEN TRUE then p.tdoc else null end)::integer AS type_doc, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docser IS NOT NULL WHEN TRUE then p.docser else null end)::char(10) AS docser, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docnum IS NOT NULL WHEN TRUE then p.docnum else null end)::char(20) AS docnum, " +
						"ds.fam::char(60) AS fam_pr, "+
						"ds.im::char(40) AS im_pr, "+
						"ds.ot::char(40) AS otch_pr," +
						"ds.datar::date AS dr_pr, " +
						"(CASE (p.poms_strg > 99 AND CURRENT_DATE - p.datar < 18*365) OR (CURRENT_DATE - p.datar < 3*31 AND p.poms_ser IS NULL AND p.poms_nom IS NULL) WHEN TRUE THEN (CASE ds.pol = 1 WHEN TRUE THEN 'М' ELSE 'Ж' END) ELSE NULL END)::char(1) AS sex_pr, "+
						"ds.vpolis::char(10) AS vpolis_pr, "+
						"ds.spolis::char(10) AS spolis_pr, "+
						"ds.npolis::char(20) AS polis_pr, " +
						"ds.tdoc::integer AS type_docpr, "+
						"ds.docser::char(10) AS docser_pr, "+
						"ds.docnum::char(20) AS docnum_pr, " +
						"k.region::integer AS region, " +
						"null::char(10) AS ist_bol, " +
						"null::integer AS vid_hosp, " +
						"null::integer AS ter_pol, " +
						"null::integer AS pol, " +
						"null::char(20) AS n_mk, " +
						"p.npasp::integer AS id_lpu, " +
		    			"(case p.poms_strg>99 when true then p.birthplace else null end)::char(100) AS birthplace, " +
						"null::integer AS ter_mu_dir, " +
						"null::integer AS kod_mu_dir ";
						
	        	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) "+
	        			"JOIN n_o00 o ON (o.pcod = g.cotd_p) "+
	        			"LEFT JOIN s_mrab m ON (g.cuser = m.pcod and g.cotd_p = m.cpodr) " +
	      				"LEFT JOIN n_kas k ON (k.pcod = p.poms_strg) "+
	      				"LEFT JOIN p_preds ds ON (ds.npasp = p.npasp) ";
	        	sqlpasp += sqlfrom; 
	        	sqlpasp += "WHERE g.pr_out<>0 ";
	            if (cpodr != 0) {
	            	sqlpasp += " AND g.cotd = ? "+sqlwhere;
	            	clpu = cpodr;
	            }
	            else 
	            	sqlpasp += " AND substr(cast(g.cotd as varchar(10)),1,2)=cast(? as varchar(10)) "+sqlwhere;
	        	sqlpasp += " UNION ";
				sqlpasp ="SELECT g.ngosp::integer AS sl_id, " +
						"1::integer AS vid_rstr, " +
						"k.kdpsk::integer AS str_org, " +
						"10::integer AS ter_mu," +
						"m.clpu::integer AS kod_mu," +
						"current_date::date AS df_per, " +
						"p.fam::char(60) AS fam, " +
						"p.im::char(40) AS im, " +
						"p.ot::char(60) AS otch, " +
						"p.datar AS dr, " +
						"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
						"(case p.poms_strg>99 when true then p.snils else null end)::char(14) AS ssp, " +
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_tdoc END)::integer AS vpolis, "+ 
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_ser END)::char(10) AS spolis, "+
						"(CASE CURRENT_DATE - p.datar < 3*30 AND p.poms_ser IS NULL AND p.poms_nom IS NULL WHEN TRUE THEN NULL ELSE p.poms_nom END)::char(20) AS polis, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.tdoc IS NOT NULL WHEN TRUE then p.tdoc else null end)::integer AS type_doc, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docser IS NOT NULL WHEN TRUE then p.docser else null end)::char(10) AS docser, "+
						"(CASE p.poms_strg>99 and CURRENT_DATE - p.datar >= 3*30 AND p.docnum IS NOT NULL WHEN TRUE then p.docnum else null end)::char(20) AS docnum, " +
						"ds.fam::char(60) AS fam_pr, "+
						"ds.im::char(40) AS im_pr, "+
						"ds.ot::char(40) AS otch_pr," +
						"ds.datar::date AS dr_pr, " +
						"(CASE (p.poms_strg > 99 AND CURRENT_DATE - p.datar < 18*365) OR (CURRENT_DATE - p.datar < 3*31 AND p.poms_ser IS NULL AND p.poms_nom IS NULL) WHEN TRUE THEN (CASE ds.pol = 1 WHEN TRUE THEN 'М' ELSE 'Ж' END) ELSE NULL END)::char(1) AS sex_pr, "+
						"ds.vpolis::char(10) AS vpolis_pr, "+
						"ds.spolis::char(10) AS spolis_pr, "+
						"ds.npolis::char(20) AS polis_pr, " +
						"ds.tdoc::integer AS type_docpr, "+
						"ds.docser::char(10) AS docser_pr, "+
						"ds.docnum::char(20) AS docnum_pr, " +
						"k.region::integer AS region, " +
						"o.nist::char(10) AS ist_bol, " +
						"(CASE g.pl_extr=1 when true then 2 else (CASE g.pl_extr=2 when true then 1 else 1 end) end)::integer AS vid_hosp, " +
						"null::integer AS ter_pol, " +
						"null::integer AS pol, " +
						"null::char(20) AS n_mk, " +
						"p.npasp::integer AS id_lpu, " +
		    			"(case p.poms_strg>99 when true then p.birthplace else null end)::char(100) AS birthplace, " +
						"null::integer AS ter_mu_dir, " +
						"null::integer AS kod_mu_dir ";

            	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) " +
            			"JOIN c_otd o ON (g.id = o.id_gosp) " +
	        			"JOIN n_o00 o00 ON (o00.pcod = o.cotd) "+
            			"LEFT JOIN s_mrab m ON (o.vrach = m.pcod and o.cotd = m.cpodr) " +
        				"LEFT JOIN n_kas k ON (k.pcod = p.poms_strg) "+
        				"LEFT JOIN p_preds ds ON (ds.npasp = p.npasp) ";
            	sqlpasp += sqlfrom; 
            	sqlpasp += "WHERE g.pr_out=0 AND o.datav is not null ";
	            if (cpodr != 0) {
	            	sqlpasp += " AND o.cotd = ? "+sqlwhere;
	            	clpu = cpodr;
	            }
	            else 
	            	sqlpasp += " AND substr(cast(o.cotd as varchar(10)),1,2)=cast(? as varchar(10)) "+sqlwhere;
            	sqlpasp += " ORDER BY p.npasp ";

				try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, clpu, new Date(df), new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, clpu, new Date(df), new Date(dn), new Date(dk)))){
					while (acrs.getResultSet().next()){
						try(InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
							zos.putNextEntry(new ZipEntry("pasp.dbf"));
							while ((bufRead = dbfStr.read(buffer)) > 0)
								zos.write(buffer, 0, bufRead);
						} catch (SQLException e) {
							log.log(Level.ERROR, "SQl Exception: ", e);
							throw new KmiacServerException();
						}
    				}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return path;
	}

	@Override
	public String getProtokolErrPol(String pf) throws KmiacServerException,
			TException {
        String sqlr;
        String path;
        int sl_id = 0;
        int id_med = 0;

	        sqlr = "SELECT p.sl_id, p.id_lpu, p.kod_mu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.diag, m.ssd, e.kod_err, e.prim " +
	 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
	 			   "ORDER BY p.sl_id, m.id_med";
	        try {
	        	SqlSelectExecutor dbfSse = new SqlSelectExecutor(String.format("jdbc:dbf:/%s", pf), prop);
	        	
	        	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("pr_err", ".htm").getAbsolutePath()), "utf-8");
	        			AutoCloseableResultSet acrs = dbfSse.execQuery(sqlr)) {
	   				StringBuilder sb = new StringBuilder(0x10000);
	   				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
	   				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	   				sb.append("<head>");
	     			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
	   				sb.append("<title>Файл контроля реестров</title>");
	   				sb.append("</head>");
	   				sb.append("<body>");
					sb.append("Протокол контроля реестров за оказанную мед. помощь");
					sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
					sb.append("Имя архивного файла:  "+pf);
					sb.append("<br>Информация об ошибках: <br>");
					
					ResultSet rs = acrs.getResultSet();
					while (rs.next()){
						if (rs.isFirst())
							if (rs.getInt("vid_rstr") != 2 && rs.getInt("kl_usl") != 2){
								sb.append("<br></b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
				   				osw.write(sb.toString());
								return path;
							}
						int kr = rs.getInt("kod_rez");
						if (rs.getInt("kod_err")==14 || rs.getInt("kod_err")==81 || rs.getInt("kod_err")==82 ||rs.getInt("kod_err")==103 ||rs.getInt("kod_err")==231)
							kr = 81;
						try (SqlModifyExecutor sme = tse.startTransaction()) {
							sme.execPrepared("UPDATE p_vizit_amb SET kod_rez=?, datak=?  WHERE id = ? ", false, kr, new Date(System.currentTimeMillis()),rs.getInt("sl_id"));
							sme.setCommit();
						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
							throw new KmiacServerException();
						}
						if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med"))
						try (AutoCloseableResultSet acr = sse.execPreparedQuery("select * from w_kderr where sl_id=? and id_med=? and cslu = 2", rs.getInt("sl_id"), rs.getInt("id_med"))) {
							if (acr.getResultSet().next()){
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("delete from w_kderr where sl_id = ? and id_med=? and cslu = 2", false, rs.getInt("sl_id"), rs.getInt("id_med"));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
							if (kr != 81){
								int cuser = 0;
								try (AutoCloseableResultSet acr = sse.execPreparedQuery("select m.pcod, m.cpodr from s_vrach v, s_mrab m where v.pcod=m.pcod and v.snils=? and m.cpodr=?", rs.getString("ssd"), rs.getInt("kod_mu"))) {
									if (acr.getResultSet().next())
										cuser = acr.getResultSet().getInt("pcod");
								} catch (Exception e) {
									e.printStackTrace();
								}
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("insert into w_kderr (cslu, cuser, cpodr, sl_id, id_med, npasp, kod_err, name_err, dataz) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", false, 2, cuser, rs.getInt("kod_mu"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), rs.getInt("kod_err"), rs.getString("prim"), new Date(System.currentTimeMillis()));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
							}
		    				
							if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med")){
								sb.append(String.format("<br><br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
								sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
								sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
								if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
								else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							}
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s ", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						sl_id = rs.getInt("sl_id");
						id_med = rs.getInt("id_med");
	    	                	
	   	            }
	   				osw.write(sb.toString());
	   				return path;
		   		} catch (SQLException e) {
		    		((SQLException) e.getCause()).printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        	dbfSse.closeConnection();
	   		} catch (SQLException e) {
	   			log.log(Level.ERROR, "SQl Exception: ", e);
	    		((SQLException) e.getCause()).printStackTrace();
	    		throw new KmiacServerException();
			} catch (Exception e) {
				e.printStackTrace();
			}
        
	        return null;
	}

	@Override
	public String getProtokolErrLDS(String pf) throws KmiacServerException,
			TException {
        String sqlr;
        String path;
        int sl_id = 0;
        int id_med = 0;

        sqlr = "SELECT p.sl_id, p.id_lpu, p.kod_mu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.diag, m.ssd, e.kod_err, e.prim " +
 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
 			   "ORDER BY p.sl_id, m.id_med";
	        try {
	        	SqlSelectExecutor dbfSse = new SqlSelectExecutor(String.format("jdbc:dbf:/%s", pf), prop);
	        	
	        	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("pr_err", ".htm").getAbsolutePath()), "utf-8");
	        			AutoCloseableResultSet acrs = dbfSse.execQuery(sqlr)) {
	   				StringBuilder sb = new StringBuilder(0x10000);
	   				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
	   				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	   				sb.append("<head>");
	     			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
	   				sb.append("<title>Файл контроля реестров</title>");
	   				sb.append("</head>");
	   				sb.append("<body>");
					sb.append("Протокол контроля реестров за оказанную мед. помощь");
					sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
					sb.append("Имя архивного файла:  "+pf);
					sb.append("<br>Информация об ошибках: <br>");
					
					ResultSet rs = acrs.getResultSet();

					while (rs.next()){
						if (rs.isFirst())
							if (rs.getInt("vid_rstr") != 2 && (rs.getInt("kl_usl") != 2 || rs.getInt("kl_usl") != 9)){
								sb.append("<br></b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
				   				osw.write(sb.toString());
								return path;
							}
						int kr = rs.getInt("kod_rez");
						if (rs.getInt("kod_err")==14 || rs.getInt("kod_err")==81 || rs.getInt("kod_err")==82 ||rs.getInt("kod_err")==103 ||rs.getInt("kod_err")==231)
							kr = 81;
						try (AutoCloseableResultSet acr = sse.execPreparedQuery("select l.tip from n_lds l, s_vrach v, s_users u where l.pcod = u.cpodr and v.pcod=u.pcod and v.snils=?", rs.getString("ssd"))) {
							if (acr.getResultSet().next()){
								if (acr.getResultSet().getString("tip").equals("Л"))
									try (SqlModifyExecutor sme = tse.startTransaction()) {
										sme.execPrepared("UPDATE p_rez_l SET kod_rez=?, d_rez=?  WHERE id = ? ", false, kr, new Date(System.currentTimeMillis()),rs.getInt("sl_id"));
										sme.setCommit();
									} catch (SQLException e) {
										((SQLException) e.getCause()).printStackTrace();
										throw new KmiacServerException();
									}
								if (acr.getResultSet().getString("tip").equals("Д") || acr.getResultSet().getString("tip").equals("Т"))
									try (SqlModifyExecutor sme = tse.startTransaction()) {
										sme.execPrepared("UPDATE p_rez_d SET kod_rez=?, d_rez=?  WHERE id = ? ", false, kr, new Date(System.currentTimeMillis()),rs.getInt("sl_id"));
										sme.setCommit();
									} catch (SQLException e) {
										((SQLException) e.getCause()).printStackTrace();
										throw new KmiacServerException();
									}
							}
						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
							throw new KmiacServerException();
						}
						if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med"))
						try (AutoCloseableResultSet acr = sse.execPreparedQuery("select * from w_kderr where sl_id=? and id_med=? and cslu = 3", rs.getInt("sl_id"), rs.getInt("id_med"))) {
							if (acr.getResultSet().next()){
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("delete from w_kderr where sl_id = ? and id_med=? and cslu = 3", false, rs.getInt("sl_id"), rs.getInt("id_med"));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
							if (kr != 81){
								int cuser = 0;
								int cpodr = 0;
								try (AutoCloseableResultSet acr = sse.execPreparedQuery("select m.pcod, m.cpodr from s_vrach v, s_mrab m where v.pcod=m.pcod and v.snils=? and m.cslu=3", rs.getString("ssd"))) {
									if (acr.getResultSet().next()){
										cuser = acr.getResultSet().getInt("pcod");
										cpodr = acr.getResultSet().getInt("cpodr");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("insert into w_kderr (cslu, cuser, cpodr, sl_id, id_med, npasp, kod_err, name_err, dataz) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", false, 3, cuser, cpodr, rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), rs.getInt("kod_err"), rs.getString("prim"), new Date(System.currentTimeMillis()));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
							}
		    				
							if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med")){
								sb.append(String.format("<br><br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
								sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
								sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
								if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
								else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							}
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s ", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						sl_id = rs.getInt("sl_id");
						id_med = rs.getInt("id_med");
					}
   					osw.write(sb.toString());
   					return path;
		   		} catch (SQLException e) {
		    		((SQLException) e.getCause()).printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        	dbfSse.closeConnection();
	   		} catch (SQLException e) {
	   			log.log(Level.ERROR, "SQl Exception: ", e);
	    		((SQLException) e.getCause()).printStackTrace();
	    		throw new KmiacServerException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public String getProtokolErrGosp(String pf) throws KmiacServerException,
			TException {
        String sqlr;
        String path;
        int sl_id = 0;
        int id_med = 0;

        sqlr = "SELECT p.sl_id, p.id_lpu, p.kod_mu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.d_end, m.etap, m.diag, m.kod_otd, e.kod_err, e.prim " +
 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
 			   "ORDER BY p.sl_id, m.id_med";
	        try {
	        	SqlSelectExecutor dbfSse = new SqlSelectExecutor(String.format("jdbc:dbf:/%s", pf), prop);
	        	
	        	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("pr_err", ".htm").getAbsolutePath()), "utf-8");
	        			AutoCloseableResultSet acrs = dbfSse.execQuery(sqlr)) {
	   				StringBuilder sb = new StringBuilder(0x10000);
	   				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
	   				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
	   				sb.append("<head>");
	     			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
	   				sb.append("<title>Файл контроля реестров</title>");
	   				sb.append("</head>");
	   				sb.append("<body>");
					sb.append("Протокол контроля реестров за оказанную мед. помощь");
					sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
					sb.append("Имя архивного файла:  "+pf);
					sb.append("<br>Информация об ошибках: <br><br>");
					
					ResultSet rs = acrs.getResultSet();
					
					while (rs.next()){
						if (rs.isFirst())
						if (rs.getInt("vid_rstr") != 1 && (rs.getInt("kl_usl") != 1 || rs.getInt("kl_usl") != 3  || rs.getInt("kl_usl") != 8)){
							sb.append("</b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
							osw.write(sb.toString());
							return path;
						}
						int kr = rs.getInt("kod_rez");
						if (rs.getInt("kod_err")==14 || rs.getInt("kod_err")==81 || rs.getInt("kod_err")==82 ||rs.getInt("kod_err")==103 ||rs.getInt("kod_err")==231)
							kr = 81;
						try (SqlModifyExecutor sme = tse.startTransaction()) {
							sme.execPrepared("UPDATE c_gosp SET kod_rez=?, d_rez=?  WHERE id = ? ", false, kr, new Date(System.currentTimeMillis()),rs.getInt("sl_id"));
							sme.setCommit();
						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
							throw new KmiacServerException();
						}

						if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med"))
						try (AutoCloseableResultSet acr = sse.execPreparedQuery("select * from w_kderr where sl_id=? and id_med=? and cslu = 1", rs.getInt("sl_id"), rs.getInt("id_med"))) {
							if (acr.getResultSet().next()){
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("delete from w_kderr where sl_id = ? and id_med=? and cslu = 1", false, rs.getInt("sl_id"), rs.getInt("id_med"));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
							if (kr != 81){
								try (SqlModifyExecutor sme = tse.startTransaction()) {
									sme.execPrepared("insert into w_kderr (cslu, cuser, cpodr, sl_id, id_med, npasp, kod_err, name_err, dataz) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", false, 1, 0, rs.getInt("kod_otd"), rs.getInt("sl_id"), rs.getInt("id_med"), rs.getInt("id_lpu"), rs.getInt("kod_err"), rs.getString("prim"), new Date(System.currentTimeMillis()));
									sme.setCommit();
								} catch (SQLException e) {
									((SQLException) e.getCause()).printStackTrace();
									throw new KmiacServerException();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
									throw new KmiacServerException();
								}
							}
		    				
							if (sl_id != rs.getInt("sl_id") && id_med != rs.getInt("id_med")){
								sb.append(String.format("<br><br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
								sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
								sb.append(String.format("<br>   Период :  %1$td.%1$tm.%1$tY - %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime(), rs.getDate("d_end").getTime()));
								sb.append(String.format("   Этап </b> %s", rs.getInt("etap")));
								if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
								else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							}
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s ", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						sl_id = rs.getInt("sl_id");
						id_med = rs.getInt("id_med");
	   	            }
	   				osw.write(sb.toString());
	   				return path;
		   		} catch (SQLException e) {
		    		((SQLException) e.getCause()).printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        	dbfSse.closeConnection();
	   		} catch (SQLException e) {
	   			log.log(Level.ERROR, "SQl Exception: ", e);
	    		((SQLException) e.getCause()).printStackTrace();
	    		throw new KmiacServerException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public List<PaspErrorInfo> getPaspErrors(int cpodrz, long datazf, long datazt) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_reestr_pasp_errors(?, ?, ?) ", cpodrz, new Date(datazf), new Date(datazt));
				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.id, e.npasp, p.fam, p.im, p.ot, p.datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN patient p ON (p.npasp = e.npasp) WHERE (n.pasp_med = 1) AND (e.cpodr = ?) AND (e.dataz BETWEEN ? AND ?) ORDER BY p.fam, p.im, p.ot, n.kderr ", cpodrz, new Date(datazf), new Date(datazt))) {
			sme.setCommit();
			return rsmPaspError.mapToList(acrsq.getResultSet());
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not get pasp errors.");
		}
	}

	@Override
	public List<MedPolErrorInfo> getMedPolErrors(int cpodrz, long datazf, long datazt) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrsf = sme.execPreparedQuery("SELECT check_reestr_med_pol_errors(?, ?, ?) ", cpodrz, new Date(datazf), new Date(datazt));
				AutoCloseableResultSet acrsq = sme.execPreparedQuery("SELECT e.id, e.sl_id AS id_obr, e.id_med AS id_pos, v.datao AS dat_obr, a.datap AS dat_pos, a.cod_sp AS vr_pcod, get_short_fio(r.fam, r.im, r.ot) AS vr_fio, a.cdol AS vr_cdol, s.name AS vr_cdol_name, e.npasp, get_short_fio(p.fam, p.im, p.ot) AS pat_fio, p.datar AS pat_datar, n.kderr, n.name_err AS err_name, n.comm AS err_comm FROM w_kderr e JOIN n_kderr n ON (n.kderr = e.kod_err) JOIN p_vizit v ON (v.id = e.sl_id) JOIN p_vizit_amb a ON (a.id = e.id_med AND a.id_obr = e.sl_id) JOIN s_vrach r ON (r.pcod = a.cod_sp) JOIN n_s00 s ON (a.cdol = s.pcod) JOIN patient p ON (p.npasp = e.npasp) WHERE (n.pasp_med = 2) AND (e.cpodr = ?) AND (a.datap BETWEEN ? AND ?) ORDER BY v.datao DESC, a.datap DESC, p.fam, p.im, p.ot, n.kderr ", cpodrz, new Date(datazf), new Date(datazt))) {
			sme.setCommit();
			return rsmMedPolError.mapToList(acrsq.getResultSet());
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException("Could not get med pol errors.");
		}
	}
}

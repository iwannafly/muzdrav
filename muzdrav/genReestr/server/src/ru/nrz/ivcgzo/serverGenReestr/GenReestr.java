package ru.nrz.ivcgzo.serverGenReestr;

import java.io.File;
import java.io.FileNotFoundException;
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
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.DbfReader.DbfResultSet;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
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
        String sqlr;
        String path;
        boolean flag = false;
        sqlwhere = "";
        if (vidr == 1) sqlwhere = "AND v.dataz >= ? AND v.dataz <= ? AND v.kod_rez = 0";
        if (vidr == 2) sqlwhere = "AND (v.kod_rez = 0 AND v.dataz >= ? AND v.dataz <= ?) OR (v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11))";
        if (vidr == 3) sqlwhere = "AND v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11)";

        sqlr = "SELECT v.id::integer AS sl_id, v.id::integer AS id_med, v.kod_rez::integer AS kod_rez, v.datap::date as d_pst, 2::integer AS kl_usl, v.pl_extr::integer AS pl_extr, " +
				"v.uet::double precision AS kol_usl, v.diag::char(7) AS diag, v.stoim::double precision AS stoim, v.cpos::integer AS case, v.rezult::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
				"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end)::integer AS c_mu, "+
				"(select get_prof(?, v.cod_sp))::integer AS prof_fn, " +
				"(select get_kodsp(v.cod_sp))::integer AS spec, " +
				"(select get_kodvr(v.cod_sp)::integer) AS prvd, " +
				"(select get_vmu(v.cod_sp))::integer AS v_mu, " +
				"(select get_vrach_snils(v.cod_sp))::char(14) AS ssd, " +
				"(case when ((v.mobs=1)or(v.mobs=4)or(v.mobs=8)or(v.mobs=9)) then 1 when (v.mobs = 2) then 2  when (v.mobs = 3) then 3 else 1 end)::integer AS place, " +
				"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
				"null::integer AS kod_otd, null::date AS d_end, null::integer AS pr_exp, null::integer AS etap, null::char(15) AS usl, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt, "+
				" 2::integer AS vid_rstr, " +
				"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
				"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
				"v.kod_ter::integer AS ter_mu, v.cpol::integer AS kod_mu, ?::date AS df_per, " +
				"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
				"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
				"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
				"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
				"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
				"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
				"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
				"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
				"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
				"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
				"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
				"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
				"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
				"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
				"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
				"(select get_region(p.poms_strg))::integer AS region," +
				"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
				"(select get_kov(p.npasp))::char(30) AS kob, " +
				"v.pl_extr::integer AS vid_hosp, (select get_talon(v.id_obr))::char(11) AS talon, " +
				"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
				"(select get_namb(p.npasp,v.cpol))::char(20) AS n_mk, " +
				"null::char(10) AS ist_bol, null::integer AS id_smo, null::integer AS ter_mu_dir, null::integer AS kod_mu_dir "+
			    "FROM p_vizit_amb v, patient p " + 
				"WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? ";
        	sqlr += sqlwhere;
        	sqlr += " ORDER BY v.npasp";
		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df),vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df), vopl, cpodr, new Date(dn), new Date(dk)))) {
            ResultSet rs = acrs.getResultSet();
            
   			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
   				StringBuilder sb = new StringBuilder(0x10000);
    				
				while (rs.next()){
    	           	String str = RsTest(rs);
    	            	
    	           	if(str!=null){
    	           		if (!flag){
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
   	    				sb.append(String.format("%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
   						sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
   						sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
   						if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
   						else sb.append(String.format("   Диагноз :  %s", rs.getString("diag")));
   						sb.append("<br>"+str+"<br>");
   						flag = true;
   	                }
   	            }
   					osw.write(sb.toString());
   					//if (sb.length()>0)
//   					return path;
            	
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
   		} catch (SQLException e) {
   			log.log(Level.ERROR, "SQl Exception: ", e);
    		((SQLException) e.getCause()).printStackTrace();
    		throw new KmiacServerException();
		}

		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;
					sqlmed = "SELECT v.id::integer AS sl_id, v.id::integer AS id_med, v.kod_rez::integer AS kod_rez, v.datap::date as d_pst, 2::integer AS kl_usl, v.pl_extr::integer AS pl_extr, " +
							"v.uet::double precision AS kol_usl, v.diag::char(7) AS diag, v.stoim::double precision AS stoim, v.cpos::integer AS case, v.rezult::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
							"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end)::integer AS c_mu, "+
							"(select get_prof(?, v.cod_sp))::integer AS prof_fn, " +
							"(select get_kodsp(v.cod_sp))::integer AS spec, " +
							"(select get_kodvr(v.cod_sp)::integer) AS prvd, " +
							"(select get_vmu(v.cod_sp))::integer AS v_mu, " +
							"(select get_vrach_snils(v.cod_sp))::char(14) AS ssd, " +
							"(case when ((v.mobs=1)or(v.mobs=4)or(v.mobs=8)or(v.mobs=9)) then 1 when (v.mobs = 2) then 2  when (v.mobs = 3) then 3 else 1 end)::integer AS place, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::integer AS kod_otd, null::date AS d_end, null::integer AS pr_exp, null::integer AS etap, null::char(15) AS usl, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt "+
						    "FROM p_vizit_amb v, patient p " + 
							"WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? ";
				    	sqlmed += sqlwhere;
					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk)));
							InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
						zos.putNextEntry(new ZipEntry("med.dbf"));
						while ((bufRead = dbfStr.read(buffer)) > 0)
							zos.write(buffer, 0, bufRead);
					} catch (SQLException e) {
				        log.log(Level.ERROR, "SQl Exception: ", e);
						throw new KmiacServerException();
					}
				    
					sqlpasp = "SELECT  v.id AS sl_id, 2::integer AS vid_rstr, " +
							"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
							"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
							"v.kod_ter::integer AS ter_mu, v.cpol::integer AS kod_mu, ?::date AS df_per, " +
							"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
							"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
							"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
							"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
							"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
							"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
							"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
							"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
							"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
							"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
							"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
							"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
							"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
							"(select get_region(p.poms_strg))::integer AS region," +
							"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
							"(select get_kov(p.npasp))::char(30) AS kob, " +
							"v.pl_extr::integer AS vid_hosp, (select get_talon(v.id_obr))::char(11) AS talon, " +
							"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
							"(select get_namb(p.npasp,v.cpol))::char(20) AS n_mk, " +
							"null::char(10) AS ist_bol, null::integer AS id_smo, null::integer AS ter_mu_dir, null::integer AS kod_mu_dir "+
						    " FROM p_vizit_amb v, patient p" + 
							" WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? ";
							sqlpasp += sqlwhere;
					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk)));
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

	private String RsTest(ResultSet rs) throws KmiacServerException, SQLException {
		String str = "";
		if (rs.getInt("vid_rstr") != 1 && rs.getInt("vid_rstr") != 2 && rs.getInt("vid_rstr") != 3)
			str += " 5. Несуществующий вид реестра VID_RSTR<br>";
    	if (rs.getInt("kod_mu") == 0)
    		str += " 9. Отсутствует код территории МО KOD_MU<br>";
    	if (rs.getInt("ter_mu") == 0)
    		str += " 10. Неверная территории МО TER_MU<br>";
    	if (rs.getDate("df_per").getTime() == 0)
    		str += " 13. Не заполнена дата предоставления реестра в ТФ DF_PER<br>";
		if (rs.getDate("d_pst").getTime() > rs.getDate("df_per").getTime())
    		str += " 14. D_PST, D_END > SYSDATE. Дата услуги ранее или позднее принимаемого к оплате периода.<br>";
    	if (rs.getString("fam") == null)
    		str += " 15. Не заполнена фамилия FAM.<br>";
    	if (rs.getString("im") == null)
    		str += " 16. Не заполнено имя IM.<br>";
    	if (rs.getString("otch") == null)
    		str += " 17. Не заполнено отчество OTCH.<br>";
    	if (rs.getDate("dr").getTime() == 0)
    		str += " 18. Не заполнена дата рождения DR<br>";
    	if (rs.getDate("d_pst").getTime()<rs.getDate("dr").getTime())
    		str += " 19. Ошибка в дате рождения<br>";
    	if ((rs.getString("sex").toUpperCase().charAt(0) != 'М') && (rs.getString("sex").toUpperCase().charAt(0) != 'Ж'))
    		str += " 21. Ошибка в кодировании пола SEX<br>";
    	if (((rs.getString("sex").toUpperCase().charAt(0) == 'М') && (rs.getString("otch").toUpperCase().endsWith("ИЧ") || rs.getString("otch").toUpperCase().endsWith("ОГЛЫ"))) ||
    		((rs.getString("sex").toUpperCase().charAt(0) == 'Ж') && (rs.getString("otch").toUpperCase().endsWith("НА") || rs.getString("otch").toUpperCase().endsWith("КЫЗЫ"))))
    		str += " 22. Несоответствие пола и отчества<br>";
    	if (rs.getInt("region") != 42 && (rs.getInt("type_doc") < 1 && rs.getInt("type_doc") > 18))
    		str += " 31. Отсутствует / некорректный тип документа TYPE_DOC<br>";
    	if (rs.getInt("region") == 0)
    		str += " 33. Отсутствует код области REGION<br>";
    	if (rs.getInt("region") == 42 && rs.getInt("ter_liv") == 0)
    		str += " 35. Отсутствует код административной территории места жительства TER_LIV<br>";
    	if (rs.getInt("status") == 0)
    		str += " 38. Неверный статус STATUS<br>";
    	if (rs.getInt("region") == 42 && rs.getInt("vid_rstr") == 1 && rs.getInt("vid_hosp") == 1 && rs.getInt("kl_usl") == 1 && rs.getString("talon") == null)
    		str += " 41. Плановый больной без талона<br>";
    	if (rs.getInt("region") != 42 && ((rs.getString("docnum") == null) || (rs.getString("docser") == null)))
    		str += " 125. Отсутствует / некорректный номер документа, удостоверяющего личность<br>";
    	if (rs.getInt("region") != 42 && rs.getString("ogrn_str") == null)
    		str += " 246. Ошибка заполнения инообластного реестра. Не указан ОГРН.<br>";
    	if (rs.getInt("stoim") == 0)
    		str += " 230. Отсутствует тариф<br>";

    	if (rs.getInt("vid_rstr") == 1){
        	if ((rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 8) && rs.getString("ist_bol") == null)
        		str += " 39. Отсутствует номер истории болезни<br>";
        	if (rs.getInt("region") == 42 && rs.getInt("kl_usl") == 1 && rs.getInt("vid_hosp") == 1 && rs.getString("talon") == null)
        		str += " 41. Плановый больной без талона<br>";
        	if (rs.getInt("kod_otd") == 0)
        		str += " 47. Отсутствует код отделения<br>";
        	if (rs.getDate("d_pst").getTime() == 0)
        		str += " 51. Не заполнена дата госпитализации D_PST<br>";
        	if ((rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 7 && rs.getInt("kl_usl") != 8) && rs.getDate("d_end").getTime() == 0)
        		str += " 52. Отсутствует дата выписки<br>";
        	if (rs.getDate("d_pst").getTime() != 0 && rs.getDate("d_end").getTime() != 0)
            	if (rs.getDate("d_pst").getTime() > rs.getDate("d_end").getTime() || (rs.getDate("d_end").getTime()-rs.getDate("d_pst").getTime())>=365)
            	str += " 53. Ошибка в датах госпитализации и выписки<br>";
        	if (rs.getInt("kl_usl") < 1 && rs.getInt("kl_usl") > 11)
        		str += " 54. Неверный вид помощи KL_USL<br>";
        	if (rs.getInt("kl_usl") != 1 && rs.getInt("kl_usl") != 3 && rs.getInt("kl_usl") != 8)
        		str += " 55. Несоответствие вида реестра VID_RSTR виду помощи KL_USL<br>";
        	if (rs.getInt("kl_usl") == 1 && rs.getInt("etap") == 0)
        		str += " 58. Не задан этап<br>";
        	if (rs.getInt("kl_usl") == 1 && (rs.getInt("pr_out") == 8 || rs.getInt("pr_out") == 15))
        		str += " 60. Ошибка кода причины выбытия<br>";
        	if (rs.getInt("region") != 42 && rs.getInt("spec") == 0)
        		str += " 67. Отсутствует код врачебной специальности SPEC<br>";
        	if (rs.getInt("res_g") != 0)
        		str += " 77. Неверный код результата обращения RES_G<br>";
        	if (rs.getInt("region") == 42 && rs.getInt("str_org") == 0 && rs.getInt("etap") == 4)
        		str += " 93. Для незастрахованного указан этап долечивания<br>";
        	if (rs.getInt("region") == 42 && rs.getInt("str_org") == 0 && rs.getInt("vid_hosp") == 1)
        		str += " 93. Для незастрахованного указан признак планового больного<br>";
        	if (rs.getInt("kl_usl") == 1 && rs.getInt("etap") != 3 && rs.getInt("etap") != 4)
        		str += " 96. Неверно указан код этапа<br>";
        	if ((rs.getDate("d_end").getTime()-rs.getDate("d_pst").getTime())>150)
        		str += " 215. Срок госпитализации >150 дней<br>";
        	if (rs.getInt("prof_fn") == 0 && rs.getInt("kl_usl") != 8 && rs.getInt("pr_exp") != 1)
        		str += " 216. Отсутствует код профиля финансового норматива<br>";
        	if (rs.getInt("kl_usl") == 3 && rs.getInt("prof_fn") != 84)
        		str += " 257. Несоответствие профиля классу услуги (д.б. kl_usl=3, prof_fn=84)<br>";
    	}

    	if (rs.getInt("vid_rstr") == 2){
        	if (rs.getInt("region") == 42 && rs.getInt("ter_pol") == 0)
        		str += " 42. Неверная территория поликлиники прикрепления TER_POL<br>";
        	if (rs.getInt("region") == 42 && rs.getInt("pol") == 0)
        		str += " 43. Неверная поликлиника прикрепления POL<br>";
        	if (rs.getInt("kl_usl") != 2 || rs.getInt("kl_usl") != 9)
        		str += " 55. Несоответствие вида реестра VID_RSTR виду помощи KL_USL<br>";
        	if (rs.getInt("pr_out") != 0)
        		str += " 60. Ошибка кода причины выбытия<br>";
        	if (rs.getInt("res_l") != 0)
        		str += " 61. Ошибка кода результата лечения<br>";
        	if (rs.getInt("case") == 0)
        		str += " 63. Отсутствует цель посещения<br>";
        	if (rs.getInt("place") == 0)
        		str += " 65. Отсутствует код места обслуживания<br>";
        	if (rs.getInt("spec") == 0)
        		str += " 67. Отсутствует код врачебной специальности<br>";
        	if ((rs.getInt("spec") == 1) && (rs.getString("sex").toUpperCase().charAt(0) == 'М'))
        		str += " 69. Несоответствие врачебной специальности полу пациента<br>";
        	if (rs.getInt("prvd") == 0)
        		str += " 70. Отсутствует код врачебной должности PRVD<br>";
        	if ((rs.getInt("prvd") == 11 || rs.getInt("prvd") == 12) && (rs.getString("sex").toUpperCase().charAt(0) == 'М'))
        		str += " 72. Несоответствие врачебной должности полу пациента<br>";
        	if (rs.getInt("v_mu") == 0)
        		str += " 73. Отсутствует код вида первичной медико-санитарной помощи V_MU<br>";
        	if ((rs.getInt("v_mu") == 32) && (rs.getString("sex").toUpperCase().charAt(0) == 'М'))
        		str += " 75. Несоответствие V_MU полу SEX пациента<br>";
        	if (rs.getInt("res_g") == 0)
        		str += " 76. Отсутствует код результата обращения RES_G<br>";
        	if (rs.getString("ssd") == null)
        		str += " 78. Отсутствует СНИЛС врача SSD<br>";
        	if (rs.getInt("c_mu") == 0)
        		str += " 79. Отсутствует код единицы учета медицинской помощи C_MU<br>";
        	if (rs.getInt("kl_usl") == 9 && rs.getString("usl") == null)
        		str += " 86. Некорректные код услуги/ обстоятельства<br>";
    	}
    	if (rs.getString("diag") == null)
    		str += " Отсутствует диагноз<br>";
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

	@Override
	public List<IntegerClassifier> getAllLDSForCurrentLpu(int lpuId)
			throws KmiacServerException, TException {
		final String sqlQuery = "SELECT pcod, name FROM n_lds WHERE clpu = ?";
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
			int vopl, int clpu, int terp, long df, int ter_mu, int kod_mu)
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

        if (vidr == 1) 
        	sqlwhere = "WHERE ld.kodotd = ? AND ld.vopl = ? AND s.kdomc = 1 AND (ld.dataz >= ? AND ld.dataz <= ?) AND d.kod_rez = 0 ";
        if (vidr == 2) 
        	sqlwhere = "WHERE ld.kodotd = ? AND ld.vopl = ? AND s.kdomc = 1 AND (d.kod_rez = 0 AND ld.dataz >= ? AND ld.dataz <= ?) OR (d.d_rez >= ? AND d.d_rez <= ? AND (d.kod_rez = 2 OR d.kod_rez = 4 OR d.kod_rez = 5 OR d.kod_rez = 11)) ";
        if (vidr == 3) 
        	sqlwhere = "WHERE ld.kodotd = ? AND ld.vopl = ? AND s.kdomc = 1 AND d.d_rez >= ? AND d.d_rez <= ? AND (d.kod_rez = 2 OR d.kod_rez = 4 OR d.kod_rez = 5 OR d.kod_rez = 11) ";

        try {
			acr = sse.execPreparedQuery("select tip from n_lds where pcod=?", cpodr);
			if (acr.getResultSet().next()){
				if (acr.getResultSet().getString("tip").equals("Л")){
			        sqlr = "SELECT d.id::integer AS sl_id, d.nisl::integer AS id_med, d.kod_rez::integer AS kod_rez, ld.datav::date as d_pst, 9::integer AS kl_usl, 0::integer AS pr_exp, " +
							"substr(d.cpok,2,length(d.cpok))::char(15) AS usl, d.kol::double precision AS kol_usl, ld.diag::char(7) AS diag, d.stoim::double precision AS stoim, 5::integer AS case, 1::integer AS place, 3::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
							"1::integer AS c_mu, 1::integer AS pl_extr, "+
							"(select get_prof(?, ld.cuser))::integer AS prof_fn, " +
							"(select get_kodsp(ld.cuser))::integer AS spec, " +
							"(select get_kodvr(ld.cuser)::integer) AS prvd, " +
							"(select get_vmu(ld.cuser))::integer AS v_mu, " +
							"(select get_vrach_snils(ld.cuser))::char(14) AS ssd, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::integer AS kod_otd, null::date AS d_end, null::integer AS etap, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt, "+
							" 2::integer AS vid_rstr, " +
							"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
							"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
							"?::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
							"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
							"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
							"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
							"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
							"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
							"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
							"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
							"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
							"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
							"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
							"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
							"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
							"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
							"(select get_region(p.poms_strg))::integer AS region," +
							"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
							"(select get_kov(p.npasp))::char(30) AS kob, " +
							"1::integer AS vid_hosp, null::char(11) AS talon, " +
							"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
							"(select get_namb(p.npasp,?))::char(20) AS n_mk, " +
							"null::char(10) AS ist_bol, null::integer AS id_smo, 10::integer AS ter_mu_dir, " +
							"(case when (length(cast(ld.kodotd as varchar(7)))>3) then substr(cast(ld.kodotd as varchar(7)),1,2)::integer when (length(cast(ld.kodotd as varchar(7)))<=3) then ld.kodotd else 0 end)::integer AS kod_mu_dir ";
					sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) JOIN p_rez_l d ON (ld.nisl = d.nisl) "+
							  "JOIN s_mrab m ON (ld.cuser = m.pcod and ld.kodotd = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";

		        	sqlwhere += " AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) OR ((substr(d.cpok,1,1)='B') AND (substr(cast(ld.kodotd as varchar(7)),1,2)=substr(cast(ld.naprotd as varchar(7)),1,2)))) ";        
					isl = "Л";
				}
				if (acr.getResultSet().getString("tip").equals("Д") || acr.getResultSet().getString("tip").equals("Т")){
			        sqlr = "SELECT d.id::integer AS sl_id, d.nisl::integer AS id_med, d.kod_rez::integer AS kod_rez, ld.datav::date as d_pst, 9::integer AS kl_usl, 0::integer AS pr_exp, " +
							"substr(d.kodisl,2,length(d.kodisl))::char(15) AS usl, d.kol::double precision AS kol_usl, ld.diag::char(7) AS diag, d.stoim::double precision AS stoim, 5::integer AS case, 1::integer AS place, 3::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
							"1::integer AS c_mu, 1::integer AS pl_extr, "+
							"(select get_prof(?, ld.cuser))::integer AS prof_fn, " +
							"(select get_kodsp(ld.cuser))::integer AS spec, " +
							"(select get_kodvr(ld.cuser)::integer) AS prvd, " +
							"(select get_vmu(ld.cuser))::integer AS v_mu, " +
							"(select get_vrach_snils(ld.cuser))::char(14) AS ssd, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::integer AS kod_otd, null::date AS d_end, null::integer AS etap, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt, "+
							" 2::integer AS vid_rstr, " +
							"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
							"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
							"?::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
							"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
							"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
							"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
							"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
							"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
							"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
							"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
							"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
							"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
							"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
							"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
							"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
							"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
							"(select get_region(p.poms_strg))::integer AS region," +
							"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
							"(select get_kov(p.npasp))::char(30) AS kob, " +
							"1::integer AS vid_hosp, null::char(11) AS talon, " +
							"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
							"(select get_namb(p.npasp,?))::char(20) AS n_mk, " +
							"null::char(10) AS ist_bol, null::integer AS id_smo, 10::integer AS ter_mu_dir, " +
							"(case when (length(cast(ld.kodotd as varchar(7)))>3) then substr(cast(ld.kodotd as varchar(7)),1,2)::integer when (length(cast(ld.kodotd as varchar(7)))<=3) then ld.kodotd else 0 end)::integer AS kod_mu_dir ";
					sqlfrom = "FROM patient p JOIN p_isl_ld ld ON (p.npasp = ld.npasp) JOIN p_rez_d d ON (ld.nisl = d.nisl) "+
							  "JOIN s_mrab m ON (ld.cuser = m.pcod and ld.kodotd = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";

		        	sqlwhere += " AND ((substr(cast(ld.kodotd as varchar(7)),1,2)<>substr(cast(ld.naprotd as varchar(7)),1,2)) OR ((substr(d.kodisl,1,1)='B') AND (substr(cast(ld.kodotd as varchar(7)),1,2)=substr(cast(ld.naprotd as varchar(7)),1,2)))) ";        
					isl = "Д";
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

        sqlr += sqlfrom;
        sqlr += sqlwhere;
        sqlr += " ORDER BY p.npasp";

        try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlr, clpu, clpu, ter_mu, kod_mu, new Date(df), kod_mu, cpodr, vopl, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlr, clpu, clpu, ter_mu, kod_mu, new Date(df), kod_mu, cpodr, vopl, new Date(dn), new Date(dk)))) {
            ResultSet rs = acrs.getResultSet();
            
   			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
   				StringBuilder sb = new StringBuilder(0x10000);
    				
				while (rs.next()){
    	           	String str = RsTest(rs);
    	            	
    	           	if(str!=null){
    	           		if (!flag){
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
   	    				sb.append(String.format("%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
   						sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
   						sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
   						if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
   						else sb.append(String.format("   Диагноз :  %s", rs.getString("diag")));
   						sb.append("<br>"+str+"<br>");
   						flag = true;
   	                }
   	            }
   					osw.write(sb.toString());
   					//if (sb.length()>0)
//   					return path;
            	
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
   		} catch (SQLException e) {
   			log.log(Level.ERROR, "SQl Exception: ", e);
    		((SQLException) e.getCause()).printStackTrace();
    		throw new KmiacServerException();
		}

		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;

				if (isl.equals("Л"))
					sqlmed = "SELECT d.id::integer AS sl_id, d.nisl::integer AS id_med, d.kod_rez::integer AS kod_rez, ld.datav::date as d_pst, 9::integer AS kl_usl, 0::integer AS pr_exp, " +
							"substr(d.cpok,2,length(d.cpok))::char(15) AS usl, d.kol::double precision AS kol_usl, ld.diag::char(7) AS diag, d.stoim::double precision AS stoim, 5::integer AS case, 1::integer AS place, 3::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
							"1::integer AS c_mu, 1::integer AS pl_extr, "+
							"(select get_prof(?, ld.cuser))::integer AS prof_fn, " +
							"(select get_kodsp(ld.cuser))::integer AS spec, " +
							"(select get_kodvr(ld.cuser)::integer) AS prvd, " +
							"(select get_vmu(ld.cuser))::integer AS v_mu, " +
							"(select get_vrach_snils(ld.cuser))::char(14) AS ssd, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::integer AS kod_otd, null::date AS d_end, null::integer AS etap, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt ";
				if (isl.equals("Д"))
					sqlmed = "SELECT d.id::integer AS sl_id, d.nisl::integer AS id_med, d.kod_rez::integer AS kod_rez, ld.datav::date as d_pst, 9::integer AS kl_usl, 0::integer AS pr_exp, " +
							"substr(d.kodisl,2,length(d.kodisl))::char(15) AS usl, d.kol::double precision AS kol_usl, ld.diag::char(7) AS diag, d.stoim::double precision AS stoim, 5::integer AS case, 1::integer AS place, 3::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
							"1::integer AS c_mu, 1::integer AS pl_extr, "+
							"(select get_prof(?, ld.cuser))::integer AS prof_fn, " +
							"(select get_kodsp(ld.cuser))::integer AS spec, " +
							"(select get_kodvr(ld.cuser)::integer) AS prvd, " +
							"(select get_vmu(ld.cuser))::integer AS v_mu, " +
							"(select get_vrach_snils(ld.cuser))::char(14) AS ssd, " +
							"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
							"null::integer AS kod_otd, null::date AS d_end, null::integer AS etap, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt ";
						sqlmed += sqlfrom;
						sqlmed += sqlwhere;
						sqlmed += " ORDER BY p.npasp";
					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, clpu, cpodr, vopl, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, clpu, cpodr, vopl, new Date(dn), new Date(dk)));
							InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
						zos.putNextEntry(new ZipEntry("med.dbf"));
						while ((bufRead = dbfStr.read(buffer)) > 0)
							zos.write(buffer, 0, bufRead);
					} catch (SQLException e) {
				        log.log(Level.ERROR, "SQl Exception: ", e);
						throw new KmiacServerException();
					}
				    
					sqlpasp = "SELECT d.id::integer AS sl_id, 2::integer AS vid_rstr, " +
							"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
							"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
							"?::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
							"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
							"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
							"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
							"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
							"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
							"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
							"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
							"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
							"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
							"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
							"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
							"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
							"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
							"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
							"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
							"(select get_region(p.poms_strg))::integer AS region," +
							"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
							"(select get_kov(p.npasp))::char(30) AS kob, " +
							"1::integer AS vid_hosp, null::char(11) AS talon, " +
							"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
							"(select get_namb(p.npasp,?))::char(20) AS n_mk, " +
							"null::char(10) AS ist_bol, null::integer AS id_smo, 10::integer AS ter_mu_dir, " +
							"(case when (length(cast(ld.kodotd as varchar(7)))>3) then substr(cast(ld.kodotd as varchar(7)),1,2)::integer when (length(cast(ld.kodotd as varchar(7)))<=3) then ld.kodotd else 0 end)::integer AS kod_mu_dir ";
						sqlpasp += sqlfrom;
						sqlpasp += sqlwhere;
						sqlpasp += " ORDER BY p.npasp";

					try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, ter_mu, kod_mu, new Date(df), kod_mu, cpodr, vopl, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, ter_mu, kod_mu, new Date(df), kod_mu, cpodr, vopl, new Date(dn), new Date(dk)));
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

        if (vidr == 1) sqlwhere = " AND g.dataz >= ? AND g.dataz <= ? AND g.kod_rez = 0";
        if (vidr == 2) sqlwhere = " AND (g.kod_rez = 0 AND g.dataz >= ? AND g.dataz <= ?) OR (g.d_rez >= ? AND g.d_rez <= ? AND (g.kod_rez = 2 OR g.kod_rez = 4 OR g.kod_rez = 5 OR g.kod_rez = 11))";
        if (vidr == 3) sqlwhere = " AND g.d_rez >= ? AND g.d_rez <= ? AND (g.kod_rez = 2 OR g.kod_rez = 4 OR g.kod_rez = 5 OR g.kod_rez = 11)";

        sqlr = "SELECT g.id::integer AS sl_id, g.id::integer AS id_med, g.kod_rez::integer AS kod_rez, g.cotd_p::integer AS kod_otd, g.datap::date as d_pst, g.datap::date as d_end, 3::integer AS kl_usl, null::integer AS pr_exp, " +
				"null::integer AS etap, null::integer AS pl_extr, null::char(15) AS usl, null::double precision AS kol_usl, 2::integer AS c_mu, g.diag_p::char(7) AS diag, null::char(7) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, "+
				"(select get_prof(?, g.cuser))::integer AS prof_fn, " +
				"null::double precision AS stoim, null::double precision AS st_acpt, null::integer AS case, null::integer AS place," +
				"(select get_kodsp(g.cuser))::integer AS spec, " +
				"null::integer AS prvd, null::integer AS v_mu, null::integer AS res_g, null::char(14) AS ssd, " +
				"null::integer AS id_med_smo, null::integer AS id_med_tf, 1::integer AS psv, 0::integer AS pk_mc, null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt, "+
				" 1::integer AS vid_rstr, " +
				"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
				"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
				"10::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
				"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
				"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
				"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
				"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
				"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
				"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
				"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
				"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
				"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
				"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
				"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
				"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
				"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
				"(select get_region(p.poms_strg))::integer AS region, " +
				"p.ter_liv::integer AS ter_liv, (select get_status(p.sgrp))::integer AS status, "+
				"(select get_kov(p.npasp))::char(30) AS kob, null::char(10) AS ist_bol, null::integer AS vid_hosp, "+
				"(case when g.pl_extr=1 then g.ntalon else null end)::char(11) AS talon, " +
				"null::integer AS ter_pol, null::integer AS pol, null::integer AS n_mk, p.npasp::integer AS id_lpu, null::integer AS id_smo, p.region_liv::integer AS region_liv," +
				"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
				"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
				"null::integer AS ter_mu_dir, null::integer AS kod_mu_dir ";

        	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) "+
        			"JOIN s_mrab m ON (g.cuser = m.pcod and g.cotd_p = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
        	sqlr += sqlfrom; 
        	sqlr += "WHERE g.pr_out<>0 "+sqlwhere;
        	sqlr += " UNION ";
            sqlr = "SELECT g.id::integer AS sl_id, g.id::integer AS id_med, g.kod_rez::integer AS kod_rez, o.cotd::integer AS kod_otd, d.date_start::date as d_pst, d.date_end::date as d_end, 1::integer AS kl_usl, 0::integer AS pr_exp, " +
    				"d.stl::integer AS etap, g.pl_extr::integer AS pl_extr, null::char(15) AS usl, null::double precision AS kol_usl, 2::integer AS c_mu, c.cod::char(7) AS diag, (select get_ds_s(c.id_gosp))::char(7) AS ds_s, null::char(6) AS pa_diag, " +
    				"(select get_pr_out(o.ishod))::integer AS pr_out, (select get_res_l(o.result))::integer AS res_l, "+
    				"(select get_prof(?, o.vrach))::integer AS prof_fn, " +
    				"null::double precision AS stoim, null::double precision AS st_acpt, null::integer AS case, null::integer AS place," +
    				"(select get_kodsp(o.vrach))::integer AS spec, " +
    				"null::integer AS prvd, null::integer AS v_mu, null::integer AS res_g, null::char(14) AS ssd, " +
    				"null::integer AS id_med_smo, null::integer AS id_med_tf, 1::integer AS psv, 0::integer AS pk_mc, null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt, "+
    				" 1::integer AS vid_rstr, " +
    				"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
    				"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
    				"10::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
    				"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
    				"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
    				"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
    				"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
    				"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
    				"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
    				"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
    				"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
    				"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
    				"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
    				"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
    				"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
    				"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
    				"(select get_region(p.poms_strg))::integer AS region, " +
    				"p.ter_liv::integer AS ter_liv, (select get_status(p.sgrp))::integer AS status, "+
    				"(select get_kov(p.npasp))::char(30) AS kob, o.nist::char(10) AS ist_bol, g.pl_extr::integer AS vid_hosp, "+
    				"(case when g.pl_extr=1 then g.ntalon else null end)::char(11) AS talon, " +
    				"null::integer AS ter_pol, null::integer AS pol, null::integer AS n_mk, p.npasp::integer AS id_lpu, null::integer AS id_smo, p.region_liv::integer AS region_liv," +
    				"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
    				"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
    				"null::integer AS ter_mu_dir, null::integer AS kod_mu_dir ";

            	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) JOIN c_otd o ON (g.id = o.id_gosp) JOIN c_diag c ON (o.id_gosp = c.id_gosp AND c.prizn=1) JOIN c_etap d ON (o.id_gosp = d.id_gosp) "+
            			"JOIN s_mrab m ON (o.vrach = m.pcod and o.cotd = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
            	sqlr += sqlfrom; 
            	sqlr += "WHERE g.pr_out=0 AND o.datav is not null "+sqlwhere;
//            	sqlr += " ORDER BY p.npasp ";
            	
           try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df), new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df), new Date(dn), new Date(dk)))) {
        	   ResultSet rs = acrs.getResultSet();
            
            	try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
            		StringBuilder sb = new StringBuilder(0x10000);
    				
				while (rs.next()){
    	           	String str = RsTest(rs);
    	            	
    	           	if(str!=null){
    	           		if (!flag){
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
   	    				sb.append(String.format("%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
   						sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
   						sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
   						if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
   						else sb.append(String.format("   Диагноз :  %s", rs.getString("diag")));
   						sb.append("<br>"+str+"<br>");
   						flag = true;
   	                }
   	            }
   				osw.write(sb.toString());
   				//if (sb.length()>0) return path;
            	
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
   		} catch (SQLException e) {
   			log.log(Level.ERROR, "SQl Exception: ", e);
    		((SQLException) e.getCause()).printStackTrace();
    		throw new KmiacServerException();
		}

		if (!flag){
			try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("reestrInfoPol", ".zip").getAbsolutePath());
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				byte[] buffer = new byte[8192];
				int bufRead;
		        sqlmed = "SELECT g.id::integer AS sl_id, g.id::integer AS id_med, g.kod_rez::integer AS kod_rez, g.cotd_p::integer AS kod_otd, g.datap::date as d_pst, g.datap::date as d_end, 3::integer AS kl_usl, null::integer AS pr_exp, " +
						"null::integer AS etap, null::integer AS pl_extr, null::char(15) AS usl, null::double precision AS kol_usl, 2::integer AS c_mu, g.diag_p::char(7) AS diag, null::char(7) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, "+
						"(select get_prof(?, g.cuser))::integer AS prof_fn, " +
						"null::double precision AS stoim, null::double precision AS st_acpt, null::integer AS case, null::integer AS place," +
						"(select get_kodsp(g.cuser))::integer AS spec, " +
						"null::integer AS prvd, null::integer AS v_mu, null::integer AS res_g, null::char(14) AS ssd, " +
						"null::integer AS id_med_smo, null::integer AS id_med_tf, 1::integer AS psv, 0::integer AS pk_mc, null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt ";
	        	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) "+
	        			"JOIN s_mrab m ON (g.cuser = m.pcod and g.cotd_p = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
	        	sqlmed += sqlfrom; 
	        	sqlmed += "WHERE g.pr_out<>0 "+sqlwhere;
	        	sqlmed += " UNION ";
	            sqlmed = "SELECT g.id::integer AS sl_id, g.id::integer AS id_med, g.kod_rez::integer AS kod_rez, o.cotd::integer AS kod_otd, d.date_start::date as d_pst, d.date_end::date as d_end, 1::integer AS kl_usl, 0::integer AS pr_exp, " +
	    				"d.stl::integer AS etap, g.pl_extr::integer AS pl_extr, null::char(15) AS usl, null::double precision AS kol_usl, 2::integer AS c_mu, c.cod::char(7) AS diag, (select get_ds_s(c.id_gosp))::char(7) AS ds_s, null::char(6) AS pa_diag, " +
	    				"(select get_pr_out(o.ishod))::integer AS pr_out, (select get_res_l(o.result))::integer AS res_l, "+
	    				"(select get_prof(?, o.vrach))::integer AS prof_fn, " +
	    				"null::double precision AS stoim, null::double precision AS st_acpt, null::integer AS case, null::integer AS place," +
	    				"(select get_kodsp(o.vrach))::integer AS spec, " +
	    				"null::integer AS prvd, null::integer AS v_mu, null::integer AS res_g, null::char(14) AS ssd, " +
	    				"null::integer AS id_med_smo, null::integer AS id_med_tf, 1::integer AS psv, 0::integer AS pk_mc, null::integer AS pr_pv, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::integer AS v_sch, null::char(12) AS talon_omt ";
            	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) JOIN c_otd o ON (g.id = o.id_gosp) JOIN c_diag c ON (o.id_gosp = c.id_gosp AND c.prizn=1) JOIN c_etap d ON (o.id_gosp = d.id_gosp) "+
            			"JOIN s_mrab m ON (o.vrach = m.pcod and o.cotd = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
            	sqlmed += sqlfrom; 
            	sqlmed += "WHERE g.pr_out=0 AND o.datav is not null "+sqlwhere;
            	sqlmed += " ORDER BY p.npasp "; //ругается

    			try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, new Date(dn), new Date(dk)));
					InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
						zos.putNextEntry(new ZipEntry("med.dbf"));
						while ((bufRead = dbfStr.read(buffer)) > 0)
							zos.write(buffer, 0, bufRead);
					} catch (SQLException e) {
						log.log(Level.ERROR, "SQl Exception: ", e);
						throw new KmiacServerException();
					}
				    
				sqlpasp ="SELECT g.id::integer AS sl_id, 1::integer AS vid_rstr, " +
						"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
						"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
						"10::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
						"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
						"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
						"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
						"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
						"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
						"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
						"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
						"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
						"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
						"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
						"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
						"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
						"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
						"(select get_region(p.poms_strg))::integer AS region, " +
						"p.ter_liv::integer AS ter_liv, (select get_status(p.sgrp))::integer AS status, "+
						"(select get_kov(p.npasp))::char(30) AS kob, null::char(10) AS ist_bol, null::integer AS vid_hosp, "+
						"(case when g.pl_extr=1 then g.ntalon else null end)::char(11) AS talon, " +
						"null::integer AS ter_pol, null::integer AS pol, null::integer AS n_mk, p.npasp::integer AS id_lpu, null::integer AS id_smo, p.region_liv::integer AS region_liv," +
						"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
						"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, null::integer AS ter_mu_dir, null::integer AS kod_mu_dir ";

	        	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) "+
	        			"JOIN s_mrab m ON (g.cuser = m.pcod and g.cotd_p = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
	        	sqlpasp += sqlfrom; 
	        	sqlpasp += "WHERE g.pr_out<>0 "+sqlwhere;
	        	sqlpasp += " UNION ";
	            sqlpasp = "SELECT g.id::integer AS sl_id, 1::integer AS vid_rstr, " +
	    				"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
	    				"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
	    				"10::integer AS ter_mu, ?::integer AS kod_mu, ?::date AS df_per, " +
	    				"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
	    				"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
	    				"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
	    				"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
	    				"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
	    				"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
	    				"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
	    				"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
	    				"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
	    				"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
	    				"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
	    				"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
	    				"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
	    				"(select get_region(p.poms_strg))::integer AS region, " +
	    				"p.ter_liv::integer AS ter_liv, (select get_status(p.sgrp))::integer AS status, "+
	    				"(select get_kov(p.npasp))::char(30) AS kob, o.nist::char(10) AS ist_bol, g.pl_extr::integer AS vid_hosp, "+
	    				"(case when g.pl_extr=1 then g.ntalon else null end)::char(11) AS talon, " +
	    				"null::integer AS ter_pol, null::integer AS pol, null::integer AS n_mk, p.npasp::integer AS id_lpu, null::integer AS id_smo, p.region_liv::integer AS region_liv," +
	    				"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
	    				"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, null::integer AS ter_mu_dir, null::integer AS kod_mu_dir ";

            	sqlfrom = "FROM patient p JOIN c_gosp g ON (p.npasp = g.npasp) JOIN c_otd o ON (g.id = o.id_gosp) JOIN c_diag c ON (o.id_gosp = c.id_gosp AND c.prizn=1) JOIN c_etap d ON (o.id_gosp = d.id_gosp) "+
            			"JOIN s_mrab m ON (o.vrach = m.pcod and o.cotd = m.cpodr) JOIN n_s00 s ON (m.cdol = s.pcod) ";
            	sqlpasp += sqlfrom; 
            	sqlpasp += "WHERE g.pr_out=0 AND o.datav is not null "+sqlwhere;
            	sqlpasp += " ORDER BY p.npasp ";

				try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, clpu, new Date(df), new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, clpu, new Date(df), new Date(dn), new Date(dk)));
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

	@Override
	public String getProtokolErrPol(String pf) throws KmiacServerException,
			TException {
        String sqlr;
        String path;

	        sqlr = "SELECT p.sl_id, p.id_lpu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.diag, e.kod_err, e.prim " +
	 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
	 			   "ORDER BY p.id_lpu";
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
					if (rs.getInt("vid_rstr") != 2 && rs.getInt("kl_usl") != 2){
						sb.append("</b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
		   				osw.write(sb.toString());
						return path;
					}
					while (rs.next()){
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
						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
		    				sb.append(String.format("<br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
							sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
							sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
							if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
							else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s <br>", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
	    	                	
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

	        sqlr = "SELECT p.sl_id, p.id_lpu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.diag, m.ssd, e.kod_err, e.prim " +
	 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
	 			   "ORDER BY p.id_lpu";
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
					if (rs.getInt("vid_rstr") != 2 && (rs.getInt("kl_usl") != 2 || rs.getInt("kl_usl") != 9)){
						sb.append("</b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
		   				osw.write(sb.toString());
						return path;
					}
					while (rs.next()){
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
						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
		    				sb.append(String.format("<br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
							sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
							sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
							if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
							else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s <br>", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
	    	                	
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

	        sqlr = "SELECT p.sl_id, p.id_lpu, p.fam, p.im, p.otch, p.dr, p.vid_rstr, m.kl_usl, m.id_med, m.kod_rez, m.d_pst, m.diag, e.kod_err, e.prim " +
	 			   "FROM pasp p JOIN med m ON (p.sl_id = m.sl_id) LEFT JOIN err e ON (m.sl_id = e.sl_id and m.id_med = e.id_med) " + 
	 			   "ORDER BY p.id_lpu";
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
					if (rs.next())
					if (rs.getInt("vid_rstr") != 1 && (rs.getInt("kl_usl") != 1 || rs.getInt("kl_usl") != 3  || rs.getInt("kl_usl") != 8)){
						sb.append("</b>   НЕСООТВЕТСТВИЕ ПОДГРУЖАЕМОГО РЕЕСТРА ВИДУ ПОМОЩИ !!!");
		   				osw.write(sb.toString());
						return path;
					}
					while (rs.next()){
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
						if (rs.getInt("kod_rez") != 3 && rs.getInt("kod_rez") != 10){
		    				sb.append(String.format("<br>%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
							sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
							sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
							if (rs.getString("diag") == null) sb.append("</b>   Диагноз :  ОТСУТСТВУЕТ");
							else sb.append(String.format("%s   Диагноз :  %s", "      ",rs.getString("diag")));
							sb.append(String.format("<br> %s ", rs.getString("kod_err")));
							try (AutoCloseableResultSet acr = sse.execPreparedQuery("select name_err from n_kderr where kderr=?", rs.getInt("kod_err"))) {
								if (acr.getResultSet().next()) 
									sb.append(String.format(" %s ", acr.getResultSet().getString("name_err")));
								sb.append(String.format("<br> %s <br>", rs.getString("prim")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
	    	                	
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


}


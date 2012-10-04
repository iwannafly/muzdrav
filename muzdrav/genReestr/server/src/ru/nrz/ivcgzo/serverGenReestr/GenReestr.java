package ru.nrz.ivcgzo.serverGenReestr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenReestr.ReestrNotFoundException;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr;
import ru.nkz.ivcgzo.thriftGenReestr.ThriftGenReestr.Iface;

public class GenReestr extends Server implements Iface {
////////////////////////////////////////////////////////////////////////
//							Fields                                    //
////////////////////////////////////////////////////////////////////////

private static Logger log = Logger.getLogger(GenReestr.class.getName());

private TServer thrServ;

	@Override
	public void start() throws Exception {
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
//		rsmMedInfo = new TResultSetMapper<>(PokazMet.class, "pcod",       "name_n");
//		MedTypes = new Class<?>[] {                     String.class, String.class};
	}

	@Override
	public void testConnection() throws TException {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		// TODO Auto-generated method stub
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
    public final List<IntegerClassifier> getOtdForCurrentLpu(final int lpuId) throws TException {
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
	public String getReestrInfoPol(int cpodr, long dn, long dk, int vidr,
			int vopl, int clpu, int terp, long df) throws KmiacServerException,
			ReestrNotFoundException, TException {
        String sqlwhere;
        String sqlmed;
        String sqlpasp;
        String sqlr;
        String path;
        sqlwhere = "";
        if (vidr == 1) sqlwhere = "AND v.dataz >= ? AND v.dataz <= ? AND v.kod_rez = 0";
        if (vidr == 2) sqlwhere = "AND (v.kod_rez = 0 AND v.dataz >= ? AND v.dataz <= ?) OR (v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11))";
        if (vidr == 3) sqlwhere = "AND v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11)";

        sqlr = "SELECT v.id::integer AS sl_id, v.id::integer AS id_med, v.kod_rez::integer AS kod_rez, v.datap::date as d_pst, 2::integer AS kl_usl, v.pl_extr::integer AS pl_extr, " +
				"v.uet::double precision AS kol_usl, v.diag::char(7) AS diag, v.stoim::double precision AS stoim, v.cpos::integer AS case, v.rezult::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
				"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end)::integer AS c_mu, "+
				"(select get_prof(?, v.cdol))::integer AS prof_fn, " +
				"(select get_kodsp(v.cdol))::integer AS spec, " +
				"(select get_kodvr(v.cdol)::integer) AS prvd, " +
				"(select get_vmu(v.cdol))::integer AS v_mu, " +
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
		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df),vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlr, clpu, clpu, new Date(df), vopl, cpodr, new Date(dn), new Date(dk)))) {
            ResultSet rs = acrs.getResultSet();
            
   			try	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("reestr_error", ".htm").getAbsolutePath()), "utf-8")) {
    				
   				StringBuilder sb = new StringBuilder(0x10000);
   				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
   				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
   				sb.append("<head>");
   					sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
   					sb.append("<title>Файл проверок реестра</title>");
   					sb.append("</head>");
   					sb.append("<body>");
					sb.append("Протокол проверок реестра случаев оказания мед. помощи");
					sb.append(String.format(" от %1$td.%1$tm.%1$tY <br><br>", new Date(System.currentTimeMillis())));

   					while (rs.next()){
						String str = "";
    	            	if (rs.getInt("vid_rstr") != 1 && rs.getInt("vid_rstr") != 2 && rs.getInt("vid_rstr") != 3)
    	            		str += " 5. Несуществующий вид реестра VID_RSTR<br>";
    	            	if (rs.getInt("kod_mu") == 0)
    	            		str += " 9. Отсутствует код территории МО KOD_MU<br>";
    	            	if (rs.getInt("ter_mu") == 0)
    	            		str += " 10. Неверная территории МО TER_MU<br>";
    	            	if (rs.getDate("df_per").getTime() == 0)
    	            		str += " 13. Незаполнена дата предоставления реестра в ТФ DF_PER<br>";
    	            	
    	            	
    	            	if (rs.getString("diag") == null)
    	            		str += " Отсутствует диагноз<br>";
    	                
    	            	if(str!=null){
    	    				sb.append(String.format("%s %s %s", rs.getString("fam").trim(), rs.getString("im").trim(), rs.getString("otch").trim()));
    						sb.append(String.format("   Д.р. </b> %1$td.%1$tm.%1$tY", rs.getDate("dr").getTime()));
    						sb.append(String.format("<br>   Дата :  %1$td.%1$tm.%1$tY", rs.getDate("d_pst").getTime()));
    	            		sb.append(String.format("   Диагноз :  %s", rs.getString("diag")));
    						sb.append("<br>"+str+"<br>");
    	                	
    	                }
    	                	
    	            }
   					osw.write(sb.toString());
   					return path;
            	
   			} catch (IOException e) {
   				e.printStackTrace();
   				throw new KmiacServerException();
   			}
   		} catch (SQLException e) {
   			log.log(Level.ERROR, "SQl Exception: ", e);
    		((SQLException) e.getCause()).printStackTrace();
    		throw new KmiacServerException();
		}

//      sqlmed = "SELECT v.id::integer AS sl_id, v.id::integer AS id_med, v.kod_rez::integer AS kod_rez, v.datap::date as d_pst, 2::integer AS kl_usl, v.pl_extr::integer AS pl_extr, " +
//				"v.uet::double precision AS kol_usl, v.diag::char(7) AS diag, v.stoim::double precision AS stoim, v.cpos::integer AS case, v.rezult::integer AS res_g, 1::integer AS psv, 0::integer AS pr_pv, " +
//				"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end)::integer AS c_mu, "+
//				"(select get_prof(?, v.cdol))::integer AS prof_fn, " +
//				"(select get_kodsp(v.cdol))::integer AS spec, " +
//				"(select get_kodvr(v.cdol)::integer) AS prvd, " +
//				"(select get_vmu(v.cdol))::integer AS v_mu, " +
//				"(select get_vrach_snils(v.cod_sp))::char(14) AS ssd, " +
//				"(case when ((v.mobs=1)or(v.mobs=4)or(v.mobs=8)or(v.mobs=9)) then 1 when (v.mobs = 2) then 2  when (v.mobs = 3) then 3 else 1 end)::integer AS place, " +
//				"(select get_v_sch(p.npasp, ?))::integer AS v_sch, "+
//				"null::integer AS kod_otd, null::date AS d_end, null::integer AS pr_exp, null::integer AS etap, null::char(15) AS usl, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt "+
//			    "FROM p_vizit_amb v, patient p " + 
//				"WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? ";
//        	sqlmed += sqlwhere;
//		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlmed, clpu, clpu, vopl, cpodr, new Date(dn), new Date(dk)))) {
//            ResultSet rs = acrs.getResultSet();
//            
//            new DbfMapper(rs).mapToFile("c:\\med.dbf");
//            
//		} catch (SQLException e) {
//            log.log(Level.ERROR, "SQl Exception: ", e);
//			throw new KmiacServerException();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//		sqlpasp = "SELECT  v.id AS sl_id, 2::integer AS vid_rstr, " +
//				"(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
//				"(select get_name_str(p.npasp,p.poms_strg))::char(50) AS name_str, " +
//				"v.kod_ter::integer AS ter_mu, v.cpol::integer AS kod_mu, ?::date AS df_per, " +
//				"p.fam::char(60) AS fam, p.im::char(40) AS im, p.ot::char(60) AS otch, p.datar AS dr, " +
//				"(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex, "+
//				"(select get_preds_fam(p.npasp))::char(60) AS fam_rp, "+
//				"(select get_preds_im(p.npasp))::char(40) AS im_rp, "+
//				"(select get_preds_ot(p.npasp))::char(40) AS otch_rp," +
//				"(select get_preds_dr(p.npasp))::date AS dr_pr, "+
//				"(select get_preds_sex(p.npasp))::char(1) AS sex_pr, "+
//				"(select get_preds_spolis(p.npasp))::char(10) AS spolis_pr, "+
//				"(select get_preds_npolis(p.npasp))::char(20) AS polis_pr, " +
//				"p.poms_tdoc::integer AS vpolis, p.poms_ser::char(10) AS spolis, p.poms_nom::char(20) AS polis, " +
//				"(case when p.poms_strg>=100 then p.tdoc else null end)::integer AS type_doc, "+
//				"(case when p.poms_strg>=100 then p.docser else null end)::char(10) AS docser, "+
//				"(case when p.poms_strg>=100 then p.docnum else null end)::char(20) AS docnum, " +
//				"(case when p.poms_strg>=100 then (select get_ogrn(p.npasp)) else null end)::char(15) AS ogrn_str, " +
//				"(case when p.poms_strg>=100 then (select get_birthplace(p.npasp)) else null end)::char(100) AS birthplace, " +
//				"(select get_region(p.poms_strg))::integer AS region," +
//				"p.ter_liv::integer AS ter_liv, p.region_liv::integer AS region_liv, (select get_status(p.sgrp))::integer AS status, " +
//				"(select get_kov(p.npasp))::char(30) AS kob, " +
//				"v.pl_extr::integer AS vid_hosp, (select get_talon(v.id_obr))::char(11) AS talon, " +
//				"p.terp::integer AS ter_pol, p.cpol_pr::integer AS pol, p.npasp::integer AS id_lpu, " +
//				"(select get_namb(p.npasp,v.cpol))::char(20) AS n_mk, " +
//				"null::char(10) AS ist_bol, null::integer AS id_smo, null::integer AS ter_mu_dir, null::integer AS kod_mu_dir "+
//			    " FROM p_vizit_amb v, patient p" + 
//				" WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? ";
//				sqlpasp += sqlwhere;
//		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk)))) {
//            ResultSet rs = acrs.getResultSet();
//            
//            new DbfMapper(rs).mapToFile("c:\\pasp.dbf");
//		
//		} catch (SQLException e) {
//            log.log(Level.ERROR, "SQl Exception: ", e);
//			throw new KmiacServerException();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}

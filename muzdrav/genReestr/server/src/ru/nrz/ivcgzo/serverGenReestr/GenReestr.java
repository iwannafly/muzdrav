package ru.nrz.ivcgzo.serverGenReestr;

import java.io.FileNotFoundException;
import java.io.IOException;
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
        log.info("Start serverRegPatient");
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
			log.info("Stop serverRegPatient");
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
	public void getReestrInfoPol(int cpodr, long dn, long dk, int vidr, int vopl, int clpu, long df)
			 throws KmiacServerException, ReestrNotFoundException, TException {
        String sqlwhere;
        sqlwhere = "";
        if (vidr == 1) sqlwhere = "AND v.dataz >= ? AND v.dataz <= ? AND v.kod_rez = 0";
        if (vidr == 2) sqlwhere = "AND (v.kod_rez = 0 AND v.dataz >= ? AND v.dataz <= ?) OR (v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11))";
        if (vidr == 3) sqlwhere = "AND v.datak >= ? AND v.datak <= ? AND (v.kod_rez = 2 OR v.kod_rez = 4 OR v.kod_rez = 5 OR v.kod_rez = 11)";

//        String sqlmed = "SELECT v.id::integer AS sl_id, v.id AS id_med, v.kod_rez AS kod_rez, v.datap as d_pst, 2 AS kl_usl, v.pl_extr AS pl_extr, " +
//				"v.uet AS kol_usl, v.diag AS diag, v.stoim AS stoim, v.cpos AS case, v.rezult AS res_g, 1 AS psv, 0 AS pr_pv, " +
//				"(case when ((v.cdol::integer=33)or(v.cdol::integer=34)or(v.cdol::integer=142)or(v.cdol::integer=143)or(v.cdol::integer=172)or(v.cdol::integer=212)) then 3 else 1 end) AS c_mu, "+
//				"(select get_prof(?, v.cdol)) AS prof_fn, " +
//				"(select get_kodsp(v.cdol)) AS spec, " +
//				"(select get_kodvr(v.cdol)::integer) AS prvd, " +
//				"(select get_vmu(v.cdol)) AS v_mu, " +
//				"(select get_vrach_snils(v.cod_sp)) AS ssd, " +
//				"(case when ((v.mobs=1)or(v.mobs=4)or(v.mobs=8)or(v.mobs=9)) then 1 when (v.mobs = 2) then 2  when (v.mobs = 3) then 3 else 1 end) AS place, " +
//				"(select get_v_sch(p.npasp, ?)) AS v_sch, "+
//				"null::integer AS kod_otd, null::date AS d_end, null::integer AS pr_exp, null::integer AS etap, null::char(15) AS usl, null::char(15) AS ds_s, null::char(6) AS pa_diag, null::integer AS pr_out, null::integer AS res_l, null::double precision AS st_acpt, null::integer AS id_med_smo, null::integer AS id_med_tf, null::integer AS pk_mc, null::char(15) AS obst, null::char(20) AS n_schet, null::date AS d_schet, null::char(12) AS talon_omt "+
//			    "FROM p_vizit_amb v, patient p " + 
//				"WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? "+sqlwhere;
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
        
		String sqlpasp = "SELECT  v.id AS sl_id, 2::integer AS vid_rstr, " +
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
				"(select get_region(p.poms_strg))::integer AS region "+
			    " FROM p_vizit_amb v, patient p" + 
				" WHERE v.npasp=p.npasp AND v.opl = ?  AND v.cpol = ? "+sqlwhere;
		try (AutoCloseableResultSet acrs = (vidr == 2) ? (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk))) : (sse.execPreparedQuery(sqlpasp, new Date(df), vopl, cpodr, new Date(dn), new Date(dk)))) {
            ResultSet rs = acrs.getResultSet();
            
            new DbfMapper(rs).mapToFile("c:\\pasp.dbf");
		
		} catch (SQLException e) {
            log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

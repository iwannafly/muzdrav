package ru.nkz.ivcgzo.serverVgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.ZipEntry;

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
import ru.nkz.ivcgzo.thriftVgr.Kontidi;
import ru.nkz.ivcgzo.thriftVgr.Kontiis;
import ru.nkz.ivcgzo.thriftVgr.Kontilo;
import ru.nkz.ivcgzo.thriftVgr.Kontios;
import ru.nkz.ivcgzo.thriftVgr.Kontipa;
import ru.nkz.ivcgzo.thriftVgr.KovNotFoundException;
import ru.nkz.ivcgzo.thriftVgr.Lgot;
import ru.nkz.ivcgzo.thriftVgr.ThriftVgr;
import ru.nkz.ivcgzo.thriftVgr.ThriftVgr.Iface;


public class ServerVgr extends Server implements Iface {
	private TServer thrServ;
	private static Logger log = Logger.getLogger(ServerVgr.class.getName());
	private final TResultSetMapper<Kontidi, Kontidi._Fields> rsmKontidi;
	private final Class<?>[] KontidiTypes;
	private final TResultSetMapper<Kontiis, Kontiis._Fields> rsmKontiis;
	private final Class<?>[] KontiisTypes;
	private final TResultSetMapper<Kontilo, Kontilo._Fields> rsmKontilo;
	private final Class<?>[] KontiloTypes;
	private final TResultSetMapper<Kontipa, Kontipa._Fields> rsmKontipa;
	private final Class<?>[] KontipaTypes;
	private final TResultSetMapper<Kontios, Kontios._Fields> rsmKontios;
	private final Class<?>[] KontiosTypes;
	private final TResultSetMapper<Lgot, Lgot._Fields> rsmLgot;
	private final Class<?>[] LgotTypes;
	
	
	
	public ServerVgr(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmKontidi = new TResultSetMapper<>(Kontidi.class, "bn",     "isd",   "pp",    "priz",  "prizi" );
		KontidiTypes = new Class<?>[] {               Integer.class,String.class,String.class,Integer.class,Integer.class};
		rsmKontiis = new TResultSetMapper<>(Kontiis.class, "bn",     "kissl",   "dvi" );
		KontiisTypes = new Class<?>[] {               Integer.class,String.class,Date.class};
		rsmKontilo = new TResultSetMapper<>(Kontilo.class, "bn",     "klo",   "dn",    "dk",  "ter","lpu" );
		KontiloTypes = new Class<?>[] {               Integer.class,String.class,Date.class,Date.class,Integer.class,Integer.class};
		rsmKontios = new TResultSetMapper<>(Kontios.class, "bn",     "kspec",   "dvo" );
		KontiosTypes = new Class<?>[] {               Integer.class,Integer.class,Date.class};
		rsmKontipa = new TResultSetMapper<>(Kontipa.class, "bn",     "fam",      "im",       "otch",      "sex",      "dr",       "stat",        "ul", "nd","nk","kat",                               "sth","polis","pud","sdog","dpp","ter","lpu","dotkr",                                      "nas","nnas","dri","gri",                           "pp","konti","datasm" );
		KontipaTypes = new Class<?>[] {               Integer.class,String.class,String.class,String.class,String.class,Date.class,Integer.class,String.class,String.class,String.class,Integer.class,Integer.class,String.class,Integer.class,Date.class,Integer.class,Integer.class,Date.class,Integer.class,String.class,Date.class,Integer.class,String.class,String.class,Date.class};
		rsmLgot = new TResultSetMapper<>(Lgot.class, "bn",     "klg" );
		LgotTypes = new Class<?>[] {               Integer.class,Integer.class};

		// TODO Auto-generated constructor stub
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
	public void start() throws Exception {
		ThriftVgr.Processor<Iface> proc = new ThriftVgr.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
		log.info("Start serverVgr");
	}

	@Override
	public void stop() {
		if (thrServ != null) thrServ.stop();
		log.info("Stop serverVgr");
	}

	@Override
	public void getKovInfoPol(int cpodr, long dn, long dk, int clpu)
			throws KmiacServerException, KovNotFoundException, TException {
		// TODO Auto-generated method stub
		String sqldi;
		String sqlis;
		String sqllo;
		String sqlos;
		String sqlpa;
		String sqllgot;
		int bufRead;
		byte[] buffer = new byte[8192];
		sqllgot = "SELECT p.npasp::integer AS bn, l.lgot::integer AS kgl "+
		           "FROM p_kov l, patient p, n_lkn k  "+
                   "WHERE l.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" ;		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqllgot, clpu, clpu,  cpodr, new Date(dn), new Date(dk), new Date(dn), new Date(dk)) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
//			zos.putNextEntry(new ZipEntry("lgot.dbf"));
//			while ((bufRead = dbfStr.read(buffer)) > 0)
//				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

				
	
	sqlpa = "SELECT p.npasp::integer AS bn, p.fam::char(20) AS fam,p.im:char(15) AS im,p.ot::char(20) AS otch,  "+
            "(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex,p.datar AS dr, " +
			"(select get_status(p.sgrp))::integer AS stat,p.adp_ul::char(25) AS ul,p.adp_dom::char(5) AS nd,p.adp_kv::char(5) AS nk "+
            "k.gr_kov:: char(1) AS kat, "+
            "(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
			"(case when p.ishod=2 then 7 else 6)::integer AS pud, " +
			"p.doms_ndog::char(11) AS sdog,p.datapr AS dpp,p.terp::integer AS ter,p.cpol_pr::integer AS lpu, "+
			"p.dataot AS datot,p.ter_liv::integer AS nas "+			
	        "FROM p_kov l, patient p, n_lkn k  "+
            "WHERE l.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" ;		

	sqldi = "SELECT p.npasp::integer AS bn, d.diag::char(6) AS isd,  "+
            "(case when d.ppi=1 then '+' else when d.ppi=2 then '-' end)::char(1) AS pp, " +
            "(case when d.priz=1 then '1' else  ' ' end)::char(1) AS priz, " +
            "(case when d.prizi=1 then '1' else ' ' end)::char(1) AS prizi " +
			"FROM p_kov l, patient p, n_lkn k, pdiag d  "+
            "WHERE l.npasp=p.npasp AND d.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" +
	        "d.xzab=2 AND pd_pu=1 AND substr(diag,1,1)<>'Z' "   ;		

	sqlis = "SELECT p.npasp::integer AS bn, a.usl_kov::char(15) AS kissl,  "+
           "m.vdat AS dvi"+
			"FROM p_kov l, patient p, n_lkn k, p_mer m, n_abd a  "+
            "WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" +
	        "m.pmer=n.pcod AND (m.dnl>? AND m.dnl<?) or ( m.vdat>? AND m.vdat<?); "   ;
	
	
	sqllo = "SELECT p.npasp::integer AS bn, a.cod_kov::char(20) AS klo,  "+
	           "m.dnl AS dn, m.dkl AS dk, m.ter::integer AS ter,m.lpu::integer AS lpu"+
				"FROM p_kov l, patient p, n_lkn k, p_mer m, n_abd a  "+
	            "WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" +
		        "AND m.pmer=n.pcod AND (m.dnl>? AND m.dnl<?) or ( m.vdat>? AND m.vdat<?) "   ;
	
	
	sqlos = "SELECT p.npasp::integer AS bn, select get_kodsp(v.cdol))::integer AS kspec,  "+
	           "m.datap AS dvo"+
				"FROM p_kov l, patient p, n_lkn k, p_vizit_amb m,n_s00 s  "+
	            "WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  k.ckov>0  AND p.cpol_pr = ?" +
		        "AND m.datap>? AND m.datap<? AND m.cdol=s.pcod  AND s.cod_kov<>0"   ;
		

	}
}

	

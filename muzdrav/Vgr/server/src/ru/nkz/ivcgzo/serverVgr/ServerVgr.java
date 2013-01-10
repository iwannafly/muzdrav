package ru.nkz.ivcgzo.serverVgr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

import ru.nkz.ivcgzo.thriftVgr.Diag;
import ru.nkz.ivcgzo.thriftVgr.LgkatNotFoundException;
import ru.nkz.ivcgzo.thriftVgr.Lgota;
import ru.nkz.ivcgzo.thriftVgr.Patient;
import ru.nkz.ivcgzo.thriftVgr.PatientNotFoundException;

import ru.nkz.ivcgzo.thriftVgr.RdPatient;
import ru.nkz.ivcgzo.thriftVgr.RdConVizit;
import ru.nkz.ivcgzo.thriftVgr.RdVizit;
import ru.nkz.ivcgzo.thriftVgr.KartaBer;
import ru.nkz.ivcgzo.thriftVgr.Kontidi;
import ru.nkz.ivcgzo.thriftVgr.Kontiis;
import ru.nkz.ivcgzo.thriftVgr.Kontilo;
import ru.nkz.ivcgzo.thriftVgr.Kontios;
import ru.nkz.ivcgzo.thriftVgr.Kontipa;
import ru.nkz.ivcgzo.thriftVgr.KovNotFoundException;
import ru.nkz.ivcgzo.thriftVgr.Lgot;
import ru.nkz.ivcgzo.thriftVgr.RdConVizit;
import ru.nkz.ivcgzo.thriftVgr.RdPatient;
import ru.nkz.ivcgzo.thriftVgr.RdVizit;
import ru.nkz.ivcgzo.thriftVgr.Reg;
import ru.nkz.ivcgzo.thriftVgr.Sv3;
import ru.nkz.ivcgzo.thriftVgr.ThriftVgr;
import ru.nkz.ivcgzo.thriftVgr.ThriftVgr.Iface;

import sun.security.util.Length;


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
	
	private final TResultSetMapper<RdPatient, RdPatient._Fields> rsmRdPat;
	private final Class<?>[] rdPatientTypes;
	private final TResultSetMapper< RdVizit, RdVizit._Fields> rsmRdViz;
	private final Class<?>[] rdVizitTypes;
	private final TResultSetMapper< RdConVizit, RdConVizit._Fields> rsmRdCV;
	private final Class<?>[] rdConVizitTypes;

	private final TResultSetMapper<Sv3, Sv3._Fields> rsmSv3;
	private final Class<?>[] Sv3Types;
	
	private final TResultSetMapper<Reg, Reg._Fields> rsmReg;
	private final Class<?>[] RegTypes;
	private final TResultSetMapper<Diag, Diag._Fields> rsmDiag;
	private final Class<?>[] DiagTypes;
	
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
		
		rsmRdPat = new TResultSetMapper<>(RdPatient.class,"uid","npasp"      ,"fam"       ,"im"        ,"ot"        ,"datar"   ,"docser"    ,"docnum"    ,"tawn"       ,"street"    ,"house"     ,"flat"      ,"poms_ser"  ,"poms_nom"  ,"dog"       ,"stat"       ,"lpup"       ,"terp"       ,"ftawn"      ,"fstreet"   ,"fhouse"    ,"fflat"     ,"grk"       ,"rez"       ,"telm"      ,"vred"      ,"deti"       ,"datay"   ,"yavka1"     ,"datazs"  ,"famv"      ,"imv"       ,"otv"       ,"datasn"  ,"shet"       ,"kolrod"     ,"abort"      ,"vozmmen"    ,"prmen"      ,"datam"   ,"kont"       ,"dsp"        ,"dsr"        ,"dtroch"     ,"cext"       ,"indsol"     ,"vitae"     ,"allerg"    ,"ishod"      ,"prrod"     ,"oslrod"     ,"sem"        ,"rost"       ,"vesd"      ,"osoco"      ,"uslpr"      ,"dataz"   ,"polj"       ,"obr",       "fiootec",   "mrabotec",   "telotec",   "grotec",   "photec",    "vredotec",   "vozotec",     "mrab",     "prof",       "eko",        "rub",        "predp",       "terpr",       "oblpr",      "diag",       "cvera",      "dataosl", "osp");
		rdPatientTypes = new Class<?>[]{          Integer.class,Integer.class,String.class,String.class,String.class,Date.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Date.class,Integer.class,Date.class,String.class,String.class,String.class,Date.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class,Boolean.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,String.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Date.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Integer.class,String.class,String.class,Boolean.class,Boolean.class,Boolean.class, Integer.class, Integer.class,Integer.class,Integer.class,Date.class,Integer.class};
		rsmRdViz = new TResultSetMapper<>(RdVizit.class,"uid",                "dv",         "sp",     "famvr",     "imvr",      "otvr",       "diag",        "mso",         "rzp",         "aim",          "npr",       "npasp");
		rdVizitTypes = new Class<?>[]{                   Integer.class, Date.class,Integer.class,String.class,String.class,String.class,String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
	
		rsmRdCV = new TResultSetMapper<>(RdConVizit.class,"uiv",         "uid",      "npasp",        "ned",        "ves",      "lcad",       "ldad",       "rcad",       "rdad",       "ball",        "hdm",        "spl",         "oj",         "chcc",       "polpl",      "predpl",       "serd",        "serd1",       "oteki");
		rdConVizitTypes = new Class<?>[]{          Integer.class,Integer.class,Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class };
		
/*1*/		rsmSv3 = new TResultSetMapper<>(Sv3.class, "code","dat_v","uchr","cod_uch","uchrnum",   
/*2*/      		"uchrname","fio_u","dat_born","pol","nation","vremen","mesto_k",
/*3*/			"mesto_k1","mesto_k2","mesto_k3","mesto_k4","mesto_k5","mesto_k6",
/*4*/			"gorod_k","street_k","m_v", "where_s1","where_s","p_dou","pos_",   /*25*/
/*5*/			"u_", "m_uth","m_uth1", "wedom","wedom1","vesgr","ves_kg","rost",
/*6*/			"f_r","f_r1","massa", "post","intel","em","ps","d_do","k_s1",
/*7*/           "k_s2","k_s3","k_s4","k_s5","d_po","k_si1",
/*8*/			"p_u_01","n_pu1","f_h_1","k_si2","p_u_02","n_pu2","f_h_2",
/*9*/			"k_si3","p_u_03","n_pu3","f_h_3","k_si4","p_u_04","n_pu4",    /*62*/
/*10*/			"f_h_4","k_si5","p_u_05","n_pu5","f_h_5","inv","zab_inv",
/*11*/			"ch_b","gr_z","l_o_d","l_o_a","l_k_s","l_o_s","m_p_z","prov",
/*12*/			"vrach","cod_reg","err_","postpone","id_fio" );

/*1*/		Sv3Types = new Class<?>[] {Integer.class, Date.class,Integer.class,Integer.class,String.class,
/*2*/		String.class,String.class,Date.class,Integer.class,Integer.class,Integer.class,Integer.class,
/*3*/		String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,
/*4*/       Integer.class,String.class,Boolean.class,String.class,Integer.class,Boolean.class,Integer.class,    /*25*/
/*5*/       Boolean.class,String.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,
/*6*/       Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,
/*7*/       String.class,String.class,String.class,String.class,Integer.class,String.class,
/*8*/       Integer.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,
/*9*/       String.class,Integer.class,Integer.class,Integer.class,String.class,Integer.class,Integer.class,    /*62*/
/*10*/      Integer.class,String.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,
/*11*/      Integer.class,String.class,Integer.class, Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,
/*12*/      String.class,Integer.class,Boolean.class,Integer.class,String.class};
	
rsmReg = new TResultSetMapper<>(Reg.class, "bn","kter","klpu",    
		"fam","im","otch","dr","kterp","adresp",
		"kterf","adresf","kterf","klpup",
		"osn","dn","dk","kpri" );
RegTypes = new Class<?>[] {Integer.class,Integer.class,Integer.class,
		String.class,String.class,String.class,Date.class,Integer.class,String.class,
		Integer.class,String.class,Integer.class,Integer.class,
		String.class,Date.class,Date.class,Integer.class};
rsmDiag = new TResultSetMapper<>(Diag.class, "bn","dia" );
DiagTypes = new Class<?>[] {Integer.class,String.class};

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
	public List<RdVizit> getRdVizit() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT v.datap as dv,v.diag ,v.mobs as mso,v.rezult as rzp,s.cod_sp as sp,c.cod_tf as aim,d.fam as famvr,d.im as imvr,d.ot as otvr,rd.npasp,v.id as uiv,v.n_sp as npr "+
"FROM p_rd_sl rd,p_vizit_amb v,n_s00 s,s_vrach d,n_p0c c "+ 
"where rd.npasp=v.npasp and rd.id_pvizit=v.id_obr and v.cdol=s.pcod and v.cod_sp=d.pcod and v.cpos=c.pcod "+ 
"order by rd.id_pvizit")) {
			if (acrs.getResultSet().next())
				return rsmRdViz.mapToList(acrs.getResultSet());
			else
				throw new KmiacServerException("нет записи");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<RdConVizit> getRdConVizit() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select d.id_pvizit as uid,d.id_pos as uiv,d.npasp,d.srok as ned,d.ves,d.art1 as lcad,d.art2 as ldad,d.art3 as rcad,d.art4 as rdad, "+
"d.ball,d.hdm,d.spl,d.oj,d.chcc,d.polpl,d.predpl,d.serd,d.serd1,d.oteki "+ 
"from p_rd_din d,p_rd_sl rd "+ 
"where d.id_pvizit=rd.id_pvizit")) {
			if (acrs.getResultSet().next())
				return rsmRdCV.mapToList(acrs.getResultSet());
			else
				throw new KmiacServerException("нет записи");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<ru.nkz.ivcgzo.thriftVgr.RdPatient> getRdPatient()
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT rd.id_pvizit as uid,rd.npasp,p.fam as fam, p.im as im, p.ot as ot, p.datar,p.docser,p.docnum,p.adp_gorod as tawn,p.adp_ul as street,  "+
"p.adp_dom as house,p.adp_kv as flat,p.poms_ser,p.poms_nom,p.poms_ndog as dog,a.stat,n.clpu as lpup,p.terp,p.adm_gorod as ftawn, p.adm_ul as fstreet, "+ 
"p.adm_dom as fhouse, p.adm_kv as fflat,s.grup as grk,s.ph as rez, p.tel as telm,s.vred,rd.deti,rd.datay,rd.yavka1,rd.datazs, "+
"vr.fam as famv,vr.im as imv,vr.ot as otv,rd.datasn,rd.shet,rd.kolrod,rd.abort,rd.vozmen,rd.prmen,rd.datam,rd.kont, "+ 
"rd.dsp,rd.dsr,rd.dtroch,rd.cext,rd.indsol,s.vitae,s.allerg,rd.ishod,rd.prrod,rd.oslrod,i.sem, "+ 
"rd.rost,rd.vesd,i.osoco,i.uslpr,rd.dataz,rd.polj,z0.kod_tf as obr, "+ 
"i.fiootec,i.mrotec as mrabotec,i.telotec,i.grotec,i.photec,i.vredotec,i.votec as vozotec,p.name_mr as mrab,p.prof, "+ 
"rd.eko,rd.rub,rd.predp,p.ter_liv as terpr,p.region_liv as oblpr,rd.cdiagt as diag,rd.cvera,rd.dataosl,rd.osp "+ 
"FROM patient p,p_rd_sl rd,p_rd_inf i,p_sign s,p_vizit v,n_az9 a,n_n00 n,s_vrach vr,n_z00 z0 "+ 
"WHERE  p.cpol_pr=n.pcod and v.npasp=s.npasp and i.npasp=s.npasp and "+ 
"p.npasp=s.npasp and p.sgrp=a.pcod and rd.npasp=p.npasp and v.id=rd.id_pvizit "+
"and v.cod_sp=vr.pcod and z0.pcod_s=i.obr ")) {
						if (acrs.getResultSet().next())
							return rsmRdPat.mapToList(acrs.getResultSet());
						else
							throw new KmiacServerException("нет записи");
					} catch (SQLException e) {
						((SQLException) e.getCause()).printStackTrace();
						throw new KmiacServerException();
					}
				}


	@Override
	public String formfilecsv() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		Date p1; Date p2; Date p3; Date p4; Date p5; Date p6; Date p7;
		Date p8;		Date p9; Date p10;
		Integer ball1;Integer ball2;Integer ball3;
		Integer ball4;Integer ball5;String grk;Integer tawn=0;Integer ftawn=0;
		Integer kod2; Integer kod3; Integer kod4;Integer kod5;
		Integer kod6; Integer kod7; Integer kod8; Integer kod9;
		Integer j = 0;Integer hr; Integer disp1;
		Integer risk ;Integer kontr;Integer rod;
		Integer grot; Integer hsm; Integer hal; Integer hdr;
		Integer pr; Integer ek; Integer ru;Integer otec; Integer iw2;
        Integer k1; Integer k2;Integer k3;Integer k4;
        Integer k5; Integer k6;Integer k7;Integer k8;
        Integer k10; Integer k9;
        double ves;
		String dex1 = "";
        String dex2 = "";
        String dex3 = "";
        String dex4 = "";
        String dex5 = "";
        String dex6 = "";
        String dex7 = "";
        String dex9 = "";
        String dex10 = "";
        String dex = "";
        String dak = "";
        String dsost = "";
        String dosl = "";
//        BigInteger fam = null;
//        BigInteger im = null;
//        BigInteger otsh = null;
		AutoCloseableResultSet acrs = null, acrs2 = null;
		//таблица паспортной информации Patient.csv
		StringBuilder sb = new StringBuilder(0x10000);
//		Base64.BASE64DEFAULTLENGTH.
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\patient.htm"), "utf-8")) {
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Паспортные данные</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("uid;fam;im;ot;dr;pasp;terpr;oblpr;tawn;street;house;flat;polis;dog;stat;lpup;ter;obl;terp;ftown;fstreet;fhouse;fflat;adr;grk;rez");
		//Vizit.csv
		StringBuilder sb1 = new StringBuilder(0x10000);
		sb1.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb1.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb1.append("<head>");
			sb1.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb1.append("<title>Посещения</title>");
		sb1.append("</head>");
		sb1.append("<body>");
		sb1.append("uiv;uid;dv;sp;wr;diap;mso;rzp;aim;npr");
		// Con_vizit.scv
		StringBuilder sb2 = new StringBuilder(0x10000);
		sb2.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb2.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb2.append("<head>");
			sb2.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb2.append("<title>Динамическое наблюдение</title>");
		sb2.append("</head>");
		sb2.append("<body>");
		sb2.append("uicv;uiv;uid;ves;ned;dno;plac;lcad;ldad;rcad;rdad;ball1;ball2;ball3;ball4;ball5;nexdate;cirkumference;css;polojpl;predpl;cerdpl;cerdpl2;oteki;otekiras");
		//Con_diagn.csv
		StringBuilder sb3 = new StringBuilder(0x10000);
		sb3.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb3.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb3.append("<head>");
			sb3.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb3.append("<title>Соматические диагнозы</title>");
		sb3.append("</head>");
		sb3.append("<body>");
		sb3.append("ndiag;uid;dex1;dex2;dex3;dex4dex5;dex6;dex7;dex9;dex10;dex;dak;dsost;dosl");
		// Con_main.csv
		StringBuilder sb4 = new StringBuilder(0x10000);
		sb4.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb4.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb4.append("<head>");
			sb4.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb4.append("<title>Особенности течения</title>");
		sb4.append("</head>");
		sb4.append("<body>");
		sb4.append("num;uid;jdet;dvzdu;srokvzu1;grisk;dgrisk;drodr;fiovr;dred;telm;dsndu;nber;nrod;job;vp;vn;circl;hfio;hmrab;htel;hgrk;hrez;hsm;hal;hdr;hhealth;hage;mrab;dolj;dlm;kontr;dsp;dcr;dtroch;cext;solov;cs;allerg;nasl;gemotr;prich;dprich;predp;cdiag;cvera;eko;dvpl;rub");
        //  Con_sob
		StringBuilder sb5 = new StringBuilder(0x10000);
		sb5.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb5.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb5.append("<head>");
			sb5.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb5.append("<title>Социально-гигиенические факторы</title>");
		sb5.append("</head>");
		sb5.append("<body>");
		sb5.append("nsob;uid;obr;sem;height;weight;priv;prof;proj;osl;ak;eks;gen;sost;point1;point2;point3;point4;point5;sob_date");
		
		StringBuilder sb6 = new StringBuilder(0x10000);
		sb6.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb6.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb6.append("<head>");
			sb6.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb6.append("<title>Диагнозы</title>");
		sb6.append("</head>");
		sb6.append("<body>");
		sb6.append("numd;uid;uid_pol;ddiag;spz;diag;dpdiag;un;vp");
//		for (int j = 0; j < rdPatient.size(); j++) {
//			RdPatient rdp = rdPatient.get(j);
//			
//		}
		List<RdPatient> rdPatient = getRdPatient();
		j = 0;
		for (RdPatient rdp : rdPatient) {
			j = j+1;
		kod2 =0 ; kod3 =0 ; kod4 = 0; kod5 = 0; kod6 = 0; 
		kod7 = 0; kod8 = 0; kod9 = 0;	
		ball1 = 0;ball2 = 0;ball3 = 0;
		ball4 = 0;ball5 = 0;grk = "0";
		k1 = 0; k2 = 0; k3 = 0; k4 = 0;
		k5 = 0; k6 = 0; k7 = 0; k8 = 0;k10 = 0; k9 = 0;
       dex1 = "";dex2 = ""; dex3 = ""; dex4 = ""; dex5 = "";
        dex6 = ""; dex7 = "";dex9 = ""; dex10 = "";
        dex = "";  dak = ""; dsost = ""; dosl = "";
		System.out.println(rdp);		
		p7 = new Date(rdp.datar);
		grk=rdp.grk;
		if (rdp.grk.equals("I")) grk = "1";
		if (rdp.grk.equals("II")) grk = "2";
		if (rdp.grk.equals("III")) grk = "3";
		if (rdp.grk.equals("IV")) grk = "4";
		p1 = new Date(rdp.datay);
		p2 = new Date(rdp.dataz);
		p3 = new Date(rdp.datazs);
		p4 = new Date(rdp.dataz);
		p5 = new Date(rdp.datasn);
		p6 = new Date(rdp.datam);
		p8 = new Date(System.currentTimeMillis());
		try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("select kdnpt from n_l00  where nam_kem=?",rdp.tawn)) {
			if (acrs21.getResultSet().next()){
			tawn = acrs21.getResultSet().getInt(1);
			}
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
		try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("select kdnpt from n_l00  where nam_kem=?",rdp.ftawn)) {
			if (acrs21.getResultSet().next()){
			ftawn = acrs21.getResultSet().getInt(1);
			}
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
		risk = 0;kontr = 0;	rod = 0;
		grot = 0; hsm = 0; hdr = 0; hal = 0;
		pr = 0; ek = 0; ru = 0;
		if (rdp.prrod != "") rod =1;
		if (rdp.kont) kontr = 1;
		if (rdp.predp) pr = 1;
		if (rdp.eko) ek = 1;
		if (rdp.rub) ru = 1;
		if (rdp.grotec.length()==1) grot = 1;
		else	{ if(rdp.grotec.length()==3) grot = 3;
		else{
		if (rdp.grotec.substring(0, 2).equals("II")) grot = 2;
		if (rdp.grotec.substring(0, 2).equals("IV")) grot = 4;}}
		otec = rdp.vredotec;
		if ((otec-4)<0){
		hdr=0; iw2=otec;
		}else {
		hdr=1; iw2=otec-4;	
		}
		if ((iw2-2)<0){
		hal=0; 
		}else {
		hsm=1; iw2=iw2-2;	
		}
		hsm=iw2;
		if (rdp.vred != null)
//		{System.out.println(rdp.vred);		
		if (rdp.vred.charAt(0) == '1') kod2= kod2+1;
		if (rdp.vred.charAt(1) == '1') kod2= kod2+2;
		if (rdp.vred.charAt(2) == '1') kod2= kod2+4;
		if (rdp.vred.charAt(3) == '1') kod2= kod2+8;

//		Date dgrisk = null;
		sb4.append("<br>");
		sb4.append(String.format("%d;%d;%d;%td.%4$tm.%4$tY;%d;%d;%td.%7$tm.%7$tY;%td.%8$tm.%8$tY;%s %s %s;%td.%12$tm.%12$tY;%s;", j,rdp.npasp,rdp.deti,p1,rdp.yavka1,risk,p8,p3,rdp.famv,rdp.imv,rdp.otv,p4,rdp.telm));
        if (rdp.datasn != 0)
		sb4.append(String.format("%td.%1$tm.%1$tY;%d;%d;%d;%d;%d;%d;%s;%s;%s;%d;%s;%d;%d;%d;;%d;%s;%s;%td.%19$tm.%19$tY;%d;%d;%d;%d;%d;%d;%s;%s;;;%d;",p5,rdp.shet,rdp.kolrod,rdp.abort,rdp.polj,rdp.vozmen,rdp.prmen,rdp.fiootec,rdp.mrabotec,rdp.telotec,grot,rdp.photec,hsm,hal,hdr,rdp.vozotec,rdp.mrab,rdp.prof,p6,kontr,rdp.dsp,rdp.dsr,rdp.dtroch,rdp.cext,rdp.indsol, rdp.vitae,rdp.allerg,rdp.ishod));		
        else
    	sb4.append(String.format(";%d;%d;%d;%d;%d;%d;%s;%s;%s;%d;%s;%d;%d;%d;;%d;%s;%s;%td.%18$tm.%18$tY;%d;%d;%d;%d;%d;%d;%s;%s;;;%d;",rdp.shet,rdp.kolrod,rdp.abort,rdp.polj,rdp.vozmen,rdp.prmen,rdp.fiootec,rdp.mrabotec,rdp.telotec,grot,rdp.photec,hsm,hal,hdr,rdp.vozotec,rdp.mrab,rdp.prof,p6,kontr,rdp.dsp,rdp.dsr,rdp.dtroch,rdp.cext,rdp.indsol, rdp.vitae,rdp.allerg,rdp.ishod));		
        if (rdp.datasn != 0)
        sb4.append(String.format("%td.%1$tm.%1$tY;%d;%d;%d;%d;",p5,pr,rdp.diag,rdp.cvera,ek));
        else
        sb4.append(String.format(";%d;%d;%d;%d;",pr,rdp.diag,rdp.cvera,ek));
        if (rdp.dataosl != 0)
        sb4.append(String.format("%td.%1$tm.%1$tY;%d",rdp.dataosl,ru));
        else
        sb4.append(String.format(";%d",ru));
        System.out.println(sb4);		
		//		Encoded.Base64(rdp.fam,35,fam);
		sb.append("<br>");
		sb.append(String.format("%d;%s;%s;%s;%td.%5$tm.%5$tY;%s %s;%d;%d;%d;%s;%s;%s;%s%s;%s;%d;%d;%d;%d;%d;%d;%s;%s;%s;%s;%s;%s;%s;%s", rdp.uid, rdp.fam, rdp.im, rdp.ot, p7,rdp.docser,rdp.docnum,rdp.terpr,rdp.oblpr,tawn,rdp.street,rdp.house,rdp.flat,rdp.poms_ser,rdp.poms_nom,rdp.dog,rdp.stat,rdp.lpup,rdp.terp,rdp.terpr,rdp.oblpr,ftawn,rdp.fstreet,rdp.fhouse,rdp.fflat,rdp.fstreet,rdp.fhouse,rdp.fflat,grk,rdp.rez));		
		System.out.println(sb);		
 		
        ves = rdp.vesd;
		if (rdp.rost !=0) {ves = rdp.vesd/rdp.rost/rdp.rost*10000;
		if (ves>= 36)kod8 = kod8 + 8;}
		if (rdp.vozmen >= 16) kod6 = kod6 + 16;
		if (rdp.prmen >=34) kod6 = kod6 + 32;
		if (rdp.vozmen >= 16){ if (rdp.prmen >= 34 ) kod6 = kod6 + 64;}
		if (rdp.polj <= 14) kod6 = kod6 + 128;
		if (rdp.abort >= 4) kod6 = kod6 + 256;
		if (rdp.kont) kod6 = kod6 + 1024;
		//Con_diagn.csv
			try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("select d.diag,c.dex,d.d_vz,d.xzab,d.disp,s.name,da.datad from p_diag d,n_c00 c,n_s00 s, p_diag_amb da  where d.diag = c.pcod and d.cdol_ot = s.pcod  and da.id = d.id_diag_amb and d.npasp=?",rdp.npasp)) {
				if (acrs21.getResultSet().next()){
					p9 = new Date(acrs21.getResultSet().getLong(7));
					p10 = new Date(acrs21.getResultSet().getLong(7));
					if (acrs21.getResultSet().getInt(5) == 1) disp1 = 0; else disp1 = 0;
					if (acrs21.getResultSet().getInt(4) == 1) hr = 1; else hr = 0;
					sb6.append(String.format("%d;%d;%d;%4$td.%4$tm.%4$tY;%s;%s;%7$td.%7$tm.%7$tY;%s;%d", j,rdp.npasp,rdp.npasp,p1,acrs21.getResultSet().getString(6),acrs21.getResultSet().getString(1),p2,acrs21.getResultSet().getString(6),disp1,hr));		
					System.out.println(sb6);		
					 	//				dex = dex + ' '+ acrs1.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex1")){ dex1 =(dex1 +' '+ acrs21.getResultSet().getString(1)).trim();
			k1 = k1+1; k2 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex2")) {dex2 =(dex2 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k5 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex3")) {dex3 =(dex3 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k4 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex4")) {dex4 =(dex4 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k6 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex5")) {dex5 =(dex5+' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k3 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex6")) {dex6 =(dex6+' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k7 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex7")) {dex7 =(dex7 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1;k8 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex9")) {dex9 =(dex9 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k9 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 5).equals("dex10")) {dex10 =(dex10 +' '+ acrs21.getResultSet().getString(1)).trim();	
			k1 = k1+1; k10 = 1;}	
			if (acrs21.getResultSet().getString(2).substring(0, 3).equals("dak")) dak =(dak +' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 5).equals("dsost")) dsost =(dsost +' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dosl")) dosl =(dosl +' '+ acrs21.getResultSet().getString(1)).trim();
				
				if (acrs21.getResultSet().getString(1).charAt(0) == 'N') kod2 = 1;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O21")) kod5 = kod5+1;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O44")) kod5 = kod5+2;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O45")) kod5 = kod5+2;			
				if (acrs21.getResultSet().getString(1).substring(0, 5).equals("O23.0")) kod5 = kod5+4;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O24")) kod5 = kod5+8;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O30")) kod5 = kod5+16;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O32")) kod5 = kod5+32;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O36.0")) kod5 = kod5+64;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O99.0")) kod5 = kod5+128;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O13")) kod5 = kod5+256;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O14")) kod5 = kod5+512;			
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O15")) kod5 = kod5+1024;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I11")) kod7 =  kod7 + 1;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I12")) kod7 =  kod7 + 2;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I50")) kod7 =  kod7 + 4;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I49")) kod7 =  kod7 + 8;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I34")) kod7 =  kod7 + 16;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I35")) kod7 =  kod7 + 32;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I80")) kod7 =  kod7 + 64;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N11")) kod7 =  kod7 + 128;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N03")) kod7 =  kod7 + 256;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N18")) kod7 =  kod7 + 512;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E10")) kod8 =  kod8+1;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E03")) kod8 =  kod8+2;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E04")) kod8 =  kod8+4;
				
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E27")) kod8 =  kod8+16;
				if (acrs21.getResultSet().getString(1).substring(0, 2).equals("D6")) kod8 =  kod8+32;
				if (acrs21.getResultSet().getString(1).substring(0, 2).equals("B1")) kod8 =  kod8+64;
				if (acrs21.getResultSet().getString(1).substring(0, 5).equals("K72.1")) kod8 =  kod8+128;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("J96")) kod8 =  kod8+256;
				if (acrs21.getResultSet().getString(1).charAt(0) == 'F') kod8 =  kod8+512;
				if (acrs21.getResultSet().getString(1).substring(0, 2).equals("A1")) kod8 =  kod8+1024;
				if (acrs21.getResultSet().getString(1).substring(0, 3).equals("B20")) kod8 =  kod8+2048;
				if (acrs21.getResultSet().getString(1).substring(0, 5).equals("M95.5")) kod8 =  kod8+4098;
				if (acrs21.getResultSet().getString(1).substring(0, 2).equals("M3")) kod8 =  kod8+8196;
				if (k1 >=3) kod6 = kod6+1;
				if ((k2+k3+k4+k5+k6+k7+k8+k9+k10)>=3) kod6 = kod6 + 2;
				if (k3>0) kod6 = kod6 + 4;
				if (k4>0) kod6 = kod6 + 8;
				if (kod2 == 1) kod6 = kod6 + 512;
				}
				} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
			try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("SELECT d.dspos,c.dex from p_rd_din d,n_c00 c where c.dex is not null and d.npasp=?",rdp.npasp)) {
				if (acrs21.getResultSet().next()){
//					dex = dex + ' '+ acrs1.getResultSet().getString(0);	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex1")) 
				dex1 =(dex1 +' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex2")) 
				dex2 =(dex2 +' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex3")) 
				dex3 =(dex3 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex4")) 
				dex4 =(dex4 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex5")) 
				dex5 =(dex5 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex6")) 
				dex6 =(dex6 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex7")) 
				dex7 =(dex7 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dex9")) 
				dex9 =(dex9 +' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 5).equals("dex10")) 
				dex10 =(dex10 + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 3).equals("dak")) 
				dak =(dak + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 5).equals("dsost")) 
				dsost =(dsost + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(2).substring(0, 4).equals("dosl")) 
				dosl =(dosl + ' '+ acrs21.getResultSet().getString(1)).trim();	
			if (acrs21.getResultSet().getString(1).charAt(0) == 'N') kod2 = 1;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O21")) kod5 = kod5+1;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O44")) kod5 = kod5+2;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O45")) kod5 = kod5+2;			
			if (acrs21.getResultSet().getString(1).substring(0, 5).equals("O23.0")) kod5 = kod5+4;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O24")) kod5 = kod5+8;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O30")) kod5 = kod5+16;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O32")) kod5 = kod5+32;			
			if (acrs21.getResultSet().getString(1).substring(0, 5).equals("O36.0")) kod5 = kod5+64;			
			if (acrs21.getResultSet().getString(1).substring(0, 5).equals("O99.0")) kod5 = kod5+128;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O13")) kod5 = kod5+256;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O14")) kod5 = kod5+512;			
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("O15")) kod5 = kod5+1024;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I11")) kod7 =  kod7 + 1;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I12")) kod7 =  kod7 + 2;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I50")) kod7 =  kod7 + 4;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I49")) kod7 =  kod7 + 8;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I34")) kod7 =  kod7 + 16;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I35")) kod7 =  kod7 + 32;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("I80")) kod7 =  kod7 + 64;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N11")) kod7 =  kod7 + 128;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N03")) kod7 =  kod7 + 256;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("N18")) kod7 =  kod7 + 512;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E10")) kod8 =  kod8+1;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E03")) kod8 =  kod8+2;
			if (acrs21.getResultSet().getString(1).substring(0, 3).equals("E04")) kod8 =  kod8+4;
				}
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
			dex = (dex1+' '+dex2+' '+dex3+' '+dex4+' '+dex5+ ' '+dex6+' '+dex7 +' '+dex9+' '+dex10).trim();
			sb3.append(String.format("%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",j,rdp.npasp,dex1,dex2,dex3,dex4,dex5,dex6,dex7,dex9,dex10,dex,dak,dsost,dosl));	
			System.out.println(sb3);		
		    sb5.append(String.format("%d;%d;%d;%d;%d;",j,rdp.npasp,rdp.obr,rdp.sem,rdp.rost));
		    sb5.append(String.format("%.2f;", rdp.vesd));
		    sb5.append(String.format("%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;;", kod2,rdp.osoco,rdp.uslpr,kod5,kod6,kod7,kod8,rdp.osp,ball1,ball2,ball3,ball4));
		    sb5.append(String.format("%td.%1$tm.%1$tY;", p8));
			System.out.println(sb5);		
//			acrs21.close();
		}
		//Vizit.csv
		List<RdVizit> rdVizit = getRdVizit();
		ball1 = 0;ball2 = 0;ball3 = 0;
		ball4 = 0;
		for (RdVizit rvz : rdVizit) {
			System.out.println(rvz);		
			p1 = new Date(rvz.dv);
			if (rvz.npr != 0){
			try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("select cod_sp from n_s00  where pcod=?",rvz.npr)) {
				if (acrs21.getResultSet().next()){
				tawn = acrs21.getResultSet().getInt(1);
				}
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
				}}
			sb1.append(String.format("%d;%d;%td.%3$tm.%3$tY;%d;%s %s %s;%s;%d;%d;%d;%d", rvz.uid, rvz.npasp, p1, rvz.sp, rvz.famvr,rvz.imvr,rvz.otvr,rvz.diag,rvz.mso,rvz.rzp,rvz.aim,rvz.npr));		
					}
		System.out.println(sb1);		
		// Con_vizit.scv
		j = 0;
		List<RdConVizit> rdConVizit = getRdConVizit();
		for (RdConVizit rcv : rdConVizit) {
			System.out.println(rcv);		
			j = j+1;
			Integer ot = 0;
			if (rcv.oteki != 0 ) ot = 1;
			sb2.append(String.format("%d;%d;%d;%.2f;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;;;%d;%d;%d;%d;%d;%d;", j, rcv.uiv,rcv.npasp, rcv.ves, rcv.ned,rcv.hdm,rcv.spl,rcv.lcad,rcv.ldad,rcv.rcad,rcv.rdad,ball1,ball2,ball3,ball4,rcv.oj,rcv.chcc,rcv.polpl,rcv.predpl,rcv.serd,rcv.serd1,ot,rcv.oteki));		
//			sb2.append(String.format("%d;%d;%d;%.2f;", j, rcv.uiv,rcv.npasp, rcv.ves));		
		}
		System.out.println(sb2);		
		osw.write(sb.toString());
		return "c:\\patient.html";
	} /*catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}*/ catch (IOException e) {
		e.printStackTrace();
		throw new KmiacServerException();
	} finally {
		if (acrs != null)
			acrs.close();
		if (acrs2 != null)
			acrs2.close();
	}
//		return null;
	}

	@Override
	public String getKovInfoPol(int cpodr, long dn, long dk)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		String sqldi;
		String sqlis;
		String sqllo;
		String sqlos;
		String sqlpa;
		String sqllgot;
		String path = null;
		int bufRead;
		byte[] buffer = new byte[8192];
		
		try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("kovInfoPol", ".zip").getAbsolutePath());
	 		ZipOutputStream zos = new ZipOutputStream(fos)) {
	
		sqllgot = "SELECT p.npasp::integer AS bn, l.lgot::integer AS kgl "+
		           "FROM p_kov l, patient p, n_lkn k  "+
                   "WHERE l.npasp=p.npasp AND l.lgot=k.pcod AND  k.c_kov>0  AND p.cpol_pr = ? " ;		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqllgot,cpodr ) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
			zos.putNextEntry(new ZipEntry("lgot.dbf"));
			while ((bufRead = dbfStr.read(buffer)) > 0)
				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

				
	
	sqlpa = "SELECT p.npasp::integer AS bn, p.fam::char(20) AS fam,p.im::char(15) AS im,p.ot::char(20) AS otch,  "+
            "(case when p.pol=1 then 'М' else 'Ж' end)::char(1) AS sex,p.datar AS dr, " +
			"(select get_status(p.sgrp))::integer AS stat,p.adp_ul::char(25) AS ul,p.adp_dom::char(5) AS nd,p.adp_kv::char(5) AS nk, "+
            "k.gr_kov:: char(1) AS kat, "+
            "(case when p.poms_strg>0 then (select get_str_org(p.poms_strg)) end) AS str_org, " +
			"(case when pa.ishod=2 then 7 else 6 end)::integer AS pud, " +
			"p.poms_ndog::char(11) AS sdog,p.datapr AS dpp,p.terp::integer AS ter,p.cpol_pr::integer AS lpu, "+
			"pa.dataot AS datot,p.ter_liv::integer AS nas "+			
	        "FROM p_kov l, patient p, n_lkn k, p_nambk pa  "+
            "WHERE l.npasp=p.npasp AND pa.npasp=p.npasp AND l.lgot=k.pcod AND  k.c_kov>0  AND p.cpol_pr = ?" ;		
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlpa,  cpodr) ;
			InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
		zos.putNextEntry(new ZipEntry("kontipa.dbf"));
		while ((bufRead = dbfStr.read(buffer)) > 0)
			zos.write(buffer, 0, bufRead);
	} catch (SQLException e) {
        log.log(Level.ERROR, "SQl Exception: ", e);
		throw new KmiacServerException();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	sqldi = "SELECT p.npasp::integer AS bn, d.diag::char(6) AS isd,  "+
            "(case when d.ppi=1 then '+'  end)::char(1) AS pp, " +
            "(case when d.ppi=2 then '-' end)::char(1) AS pp, " +
            "(case when d.prizb=1 then '1' else  ' ' end)::char(1) AS priz, " +
            "(case when d.prizi=1 then '1' else ' ' end)::char(1) AS prizi " +
			"FROM p_kov l, patient p, n_lkn k, p_diag d  "+
            "WHERE l.npasp=p.npasp AND d.npasp=p.npasp AND l.lgot=k.pcod AND  k.c_kov>0  AND p.cpol_pr = ? AND " +
	        "d.xzab=2 AND  substr(diag,1,1)<>'Z' "   ;		
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqldi, cpodr) ;
			InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
		zos.putNextEntry(new ZipEntry("kontidi.dbf"));
		while ((bufRead = dbfStr.read(buffer)) > 0)
			zos.write(buffer, 0, bufRead);
	} catch (SQLException e) {
        log.log(Level.ERROR, "SQl Exception: ", e);
		throw new KmiacServerException();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	sqlis = "SELECT p.npasp::integer AS bn, a.usl_kov::char(15) AS kissl,  "+
           "m.fdat AS dvi  FROM p_kov l, patient p, n_lkn k, p_mer m, n_abd a  "+
            " WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  k.c_kov>0  AND p.cpol_pr = ? AND " +
	        " m.pmer=a.pcod AND ((m.dnl>? AND m.dnl<?) or ( m.fdat>? AND m.fdat<?)) "   ;
	
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlis,  cpodr,new Date(dn), new Date(dk), new Date(dn), new Date(dk)) ;
			InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
		zos.putNextEntry(new ZipEntry("kontiis.dbf"));
		while ((bufRead = dbfStr.read(buffer)) > 0)
			zos.write(buffer, 0, bufRead);
	} catch (SQLException e) {
        log.log(Level.ERROR, "SQl Exception: ", e);
		throw new KmiacServerException();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	sqllo = "SELECT p.npasp::integer AS bn, a.cod_kov::char(20) AS klo,  "+
	           "m.dnl AS dn, m.dkl AS dk, m.ter::integer AS ter,m.lpu::integer AS lpu   "+
				"FROM p_kov l, patient p, n_lkn k, p_mer m, n_abd a  "+
	            "WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  a.cod_kov>0  AND p.cpol_pr = ?" +
		        "AND m.pmer=a.pcod AND ((m.dnl>? AND m.dnl<?) or ( m.fdat>? AND m.fdat<?)) "   ;
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqllo,  cpodr,new Date(dn), new Date(dk), new Date(dn), new Date(dk)) ;
			InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
		zos.putNextEntry(new ZipEntry("kontilo.dbf"));
		while ((bufRead = dbfStr.read(buffer)) > 0)
			zos.write(buffer, 0, bufRead);
	} catch (SQLException e) {
        log.log(Level.ERROR, "SQl Exception: ", e);
		throw new KmiacServerException();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	sqlos = "SELECT p.npasp::integer AS bn, (select get_kodsp(m.cod_sp))::integer AS kspec,  "+
	           "m.datap AS dvo "+
				"FROM p_kov l, patient p, n_lkn k, p_vizit_amb m,n_s00 s  "+
	            "WHERE l.npasp=p.npasp AND m.npasp=p.npasp AND l.lgot=k.pcod AND  k.c_kov>0  AND p.cpol_pr = ?" +
		        "AND (m.datap>? AND m.datap<?) AND m.cdol=s.pcod  AND s.cod_kov<>0"   ;
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlos,   cpodr,new Date(dn), new Date(dk)) ;
			InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
		zos.putNextEntry(new ZipEntry("kontios.dbf"));
		while ((bufRead = dbfStr.read(buffer)) > 0)
			zos.write(buffer, 0, bufRead);
	} catch (SQLException e) {
        log.log(Level.ERROR, "SQl Exception: ", e);
		throw new KmiacServerException();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

		} 
		/*catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	return path;
	}

	@Override
	public String dataSelection(long dbegin, long dend, int porc, String cform, int cpodr, long dclose)
			throws KmiacServerException, TException {
		String servpath = "";
		if (cform.equals("F25")) servpath = f025Selection(dbegin, dend, porc, cpodr);
		if (cform.equals("F39")) servpath = f039Selection(dbegin, dend, porc, cpodr);
		return servpath;
	}

	@Override
	public String getDetInfoPol(int cpodr, long dn, long dk)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		String sqlsv3;
		String path = null;
		int bufRead;
		byte[] buffer = new byte[8192];
		
		try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("DetInfoPol", ".zip").getAbsolutePath());
	 		ZipOutputStream zos = new ZipOutputStream(fos)) {
	
		sqlsv3 = "SELECT (cpol_pr::text||p.npasp::text)::integer AS code, f.dataz AS dat_v ,1::integer AS  uchr,345141024405000::bigint AS cod_uch, "+
                 "cpol_pr::integer AS uchrnum, n.name_u::char(50) AS uchrname, trim(p.fam)||' '||trim(p.im)||' '||trim(p.ot)::char(50) AS fio_u,  "+
				"p.datar AS dat_born, p.pol::integer AS pol, 1::integer AS nation, null::integer AS vremen, 320000000001::bigint AS  mesto_k,p.adm_obl::char(100) AS mesto_k1,"+
                 "p.adm_gorod::char(50) AS mesto_k2, p.adm_gorod::char(50) AS mesto_k3, 324310000001::bigint AS  mesto_k4,null::integer AS mesto_k5,null::integer AS mesto_k6,"+
				"1::integer AS gorod_k, trim(p.adm_ul)||' '||trim(p.adm_dom)||'-'||trim(p.adm_kv):: char(50) AS street_k, false::boolean AS m_v,null::char(30) As where_s1,"+
		       "(case when u.pr='1' then (case when (f.dataz-p.datar)/365.25<4  then 2 else 5 end)  end)::integer AS where_s,"+  /*////уточнить*/
				"(case when p.sgrp=9 then true else false end)::boolean AS p_dou, false::boolean AS u_, "+
				"(case when p.sgrp=9 then 2 else 0 end)::integer AS pos_, "+
				"(case when p.sgrp=7 then 6 when p.sgrp=8 then 1 else 0 end)::integer AS m_uth1, "+
                "null::char(65) AS  m_uth, "+
                "null::char(20) AS  vedom1, "+
                "null::integer AS  vedom, "+
                "null::integer AS  l_o_a, "+
                "null::integer AS  l_k_s, "+
                "null::integer AS  l_o_s, "+                
                /*Wedom ??*/
                "(select ves_gr from get_ves(f.ves::text))::integer AS vesgr,(select ves_kg from get_ves(f.ves::text))::integer AS ves_kg,"+
				"rost*100::integer AS rost, "+
                "(case when f.fv=0 then 1 else 2 end):: integer AS f_r, "+
				"(case when f.fv=0 then null  when f.fv=1 then 2  when f.fv=2 then 1 end)::integer AS massa, "+
		        "(case when f.fr=0 then 1 else 2 end):: integer AS f_r1, "+
				"(case when f.fr=0 then null  when f.fr=1 then 2  when f.fr=2 then 1 end)::integer AS post, "+
		        "(case when f.pi=0 then 1 else 2 end):: integer AS intel,(case when f.pe=0 then 1 else 2 end):: integer AS em,"+
				"(case when f.pp=0 then 1 else 2 end):: integer AS ps,"+
	       /*диагнозы*/
	       "(case when ddo.d_do=1 then 2 else 1 end)::integer AS d_do, "+
	       "(case when po.d_po=1 then 2 else 1 end)::integer AS d_po,"+
     "(case when (ddo.d_do=1) then (select diag1 from get_diag(p.npasp)) else null end)::char(6) AS k_s1, "+
     "(case when (ddo.d_do=1) then (select diag2 from get_diag(p.npasp)) else null end)::char(6) AS k_s2, "+
     "(case when (ddo.d_do=1) then (select diag3 from get_diag(p.npasp))else null end)::char(6) AS k_s3, "+
     "(case when (ddo.d_do=1) then (select diag4 from get_diag(p.npasp))else null end)::char(6) AS k_s4, "+
     "(case when (ddo.d_do=1) then (select diag5 from get_diag(p.npasp))else null end)::char(6) AS k_s5, "+
   
     "(case when (po.d_po=1) then (select pdiag1 from get_diag(p.npasp))else null end)::char(6) AS k_si1, "+
     "(case when (po.d_po=1) then (select pdiag2 from get_diag(p.npasp))else null end)::char(6) AS k_si2, "+
     "(case when (po.d_po=1) then (select pdiag3 from get_diag(p.npasp))else null end)::char(6) AS k_si3, "+
     "(case when (po.d_po=1) then (select pdiag4 from get_diag(p.npasp))else null end)::char(6) AS k_si4, "+
     "(case when (po.d_po=1) then (select pdiag5 from get_diag(p.npasp))else null end)::char(6) AS k_si5, "+
 
  "(case when (po.d_po=1) then (select p_u1 from get_diag(p.npasp))else null end)::integer AS p_u_01, "+
  "(case when (po.d_po=1) then (select p_u2 from get_diag(p.npasp))else null end)::integer AS p_u_02, "+
  "(case when (po.d_po=1) then (select p_u3 from get_diag(p.npasp))else null end)::integer AS p_u_03, "+
  "(case when (po.d_po=1) then (select p_u4 from get_diag(p.npasp))else null end)::integer AS p_u_04, "+
  "(case when (po.d_po=1) then (select p_u5 from get_diag(p.npasp))else null end)::integer AS p_u_05, "+
     
 "(case when (po.d_po=1) then (select n_pu1 from get_diag(p.npasp))else null end)::integer AS n_pu1, "+
 "(case when (po.d_po=1) then (select n_pu2 from get_diag(p.npasp))else null end)::integer AS n_pu2, "+
 "(case when (po.d_po=1) then (select n_pu3 from get_diag(p.npasp))else null end)::integer AS n_pu3, "+
 "(case when (po.d_po=1) then (select n_pu4 from get_diag(p.npasp))else null end)::integer AS n_pu4, "+
 "(case when (po.d_po=1) then (select n_pu5 from get_diag(p.npasp))else null end)::integer AS n_pu5, "+
 
"(case when (po.d_po=1) then (select f_h1 from get_diag(p.npasp))else null end)::integer AS f_h_1, "+
"(case when (po.d_po=1) then (select f_h2 from get_diag(p.npasp))else null end)::integer AS f_h_2, "+
"(case when (po.d_po=1) then (select f_h3 from get_diag(p.npasp))else null end)::integer AS f_h_3, "+
"(case when (po.d_po=1) then (select f_h4 from get_diag(p.npasp))else null end)::integer AS f_h_4, "+
"(case when (po.d_po=1) then (select f_h5 from get_diag(p.npasp))else null end)::integer AS f_h_5, "+
"(case when (po.d_po=1) then (select lod from get_recom_disp(p.npasp))else 0 end)::integer AS l_o_d, "+				
"(case when (po.d_po=1) then (select prov from get_recom_disp(p.npasp))else 0 end)::integer AS prov, "+
/**/

"(case when f.grzd=1 then 'I' when f.grzd=2 then 'II'  when f.grzd=3 then 'III'  when f.grzd=4 then 'IV'  when f.grzd=5 then 'V' end)::char(3) As gr_z,"+
   	    "(case when f.prb=0 then 2 else 1 end):: integer AS ch_b, "+
   	    "(case when f.prk=0 then 1 else 2 end):: integer AS m_p_z, "+
     	"1132::integer AS cod_reg, false::boolean AS err_,"+
   	    "0::integer AS postpone,"+
   	   "'{'||substr(i.nidv::text,1,length(i.nidv)-length(p.npasp::varchar)-1)||p.npasp::char(6)||(length(p.npasp::varchar))||'}'::char(150) AS id_fio,"+
   	  "(case when v.inv>0 then (select get_zab_inv(p.npasp)) else null end)::integer AS zab_inv,"+ 
   	 "(case when v.inv>0 then v.inv else null end)::integer AS inv,"+ 
/* участковый врач */
"trim(vr.fam)||' '||trim(vr.im)||' '||trim(vr.ot):: char(30) AS vrach "+
	  	   "FROM  patient p JOIN p_fiz f ON (p.npasp = f.npasp) LEFT JOIN n_n00 n ON (p.cpol_pr = n.pcod)  LEFT JOIN n_idv i ON (p.cpol_pr = i.cpol)"+
			"LEFT JOIN n_u10 u ON (trim(p.adm_ul)||' '||trim(p.adm_dom) = u.name) LEFT JOIN p_inv v  ON (p.npasp=v.npasp) LEFT JOIN p_disp_ds_do ddo ON (p.npasp=ddo.npasp) LEFT JOIN p_disp_ds_po po ON (p.npasp=po.npasp)"+
	  	   "LEFT JOIN p_nambk na ON (p.npasp = na.npasp AND p.cpol_pr=na.cpol) LEFT JOIN s_uch uc  ON (uc.cpol=na.cpol AND uc.uch=na.nuch)"+
			"LEFT JOIN s_vrach vr ON (vr.pcod=uc.pcod)"+
            "WHERE  p.cpol_pr = ? AND (f.dataz>? AND f.dataz<?) " ;		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlsv3,cpodr, new Date(dn), new Date(dk)) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
			zos.putNextEntry(new ZipEntry("sv3.dbf"));
			while ((bufRead = dbfStr.read(buffer)) > 0)
				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlsv3_14 = "SELECT (cpol_pr::text||p.npasp::text)::integer AS code, f.dataz AS dat_v ,1::integer AS  uchr,345141024405000::bigint AS cod_uch, "+
                "cpol_pr::integer AS uchrnum, n.name_u::char(50) AS uchrname, trim(p.fam)||' '||trim(p.im)||' '||trim(p.ot)::char(50) AS fio_u,  "+
				"p.datar AS dat_born, p.pol::integer AS pol, 1::integer AS nation, null::integer AS vremen, 320000000001::bigint AS  mesto_k,p.adm_obl::char(100) AS mesto_k1,"+
                "p.adm_gorod::char(50) AS mesto_k2, p.adm_gorod::char(50) AS mesto_k3, 324310000001::bigint AS  mesto_k4,null::integer AS mesto_k5,null::integer AS mesto_k6,"+
				"1::integer AS gorod_k, trim(p.adm_ul)||' '||trim(p.adm_dom)||'-'||trim(p.adm_kv):: char(50) AS street_k, false::boolean AS m_v,null::char(30) As where_s1,"+
		       "(case when u.pr='1' then (case when (f.dataz-p.datar)/365.25<4  then 2 else 5 end)  end)::integer AS where_s,"+  /*////уточнить*/
				"(case when p.sgrp=9 then true else false end)::boolean AS p_dou, false::boolean AS u_, "+
				"(case when p.sgrp=9 then 2 else 0 end)::integer AS pos_, "+
				"(case when p.sgrp=7 then 6 when p.sgrp=8 then 1 else 0 end)::integer AS m_uth1, "+
               "null::char(65) AS  m_uth, "+
               "null::char(20) AS  vedom1, "+
               "null::integer AS  vedom, "+
               "null::integer AS  l_o_a, "+
               "null::integer AS  l_k_s, "+
               "null::integer AS  l_o_s, "+                
               /*Wedom ??*/
               "(select ves_gr from get_ves(f.ves::text))::integer AS vesgr,(select ves_kg from get_ves(f.ves::text))::integer AS ves_kg,"+
				"rost*100::integer AS rost, "+
               "(case when f.fv=0 then 1 else 2 end):: integer AS f_r, "+
				"(case when f.fv=0 then null  when f.fv=1 then 2  when f.fv=2 then 1 end)::integer AS massa, "+
		        "(case when f.fr=0 then 1 else 2 end):: integer AS f_r1, "+
				"(case when f.fr=0 then null  when f.fr=1 then 2  when f.fr=2 then 1 end)::integer AS post, "+
		        "(case when f.pi=0 then 1 else 2 end):: integer AS intel,(case when f.pe=0 then 1 else 2 end):: integer AS em,"+
				"(case when f.pp=0 then 1 else 2 end):: integer AS ps,"+
	       /*диагнозы*/
	       "(case when ddo.d_do=1 then 2 else 1 end)::integer AS d_do, "+
	       "(case when po.d_po=1 then 2 else 1 end)::integer AS d_po,"+
    "(case when (ddo.d_do=1) then (select diag1 from get_diag(p.npasp)) else null end)::char(6) AS k_s1, "+
    "(case when (ddo.d_do=1) then (select diag2 from get_diag(p.npasp)) else null end)::char(6) AS k_s2, "+
    "(case when (ddo.d_do=1) then (select diag3 from get_diag(p.npasp))else null end)::char(6) AS k_s3, "+
    "(case when (ddo.d_do=1) then (select diag4 from get_diag(p.npasp))else null end)::char(6) AS k_s4, "+
    "(case when (ddo.d_do=1) then (select diag5 from get_diag(p.npasp))else null end)::char(6) AS k_s5, "+
  
    "(case when (po.d_po=1) then (select pdiag1 from get_diag(p.npasp))else null end)::char(6) AS k_si1, "+
    "(case when (po.d_po=1) then (select pdiag2 from get_diag(p.npasp))else null end)::char(6) AS k_si2, "+
    "(case when (po.d_po=1) then (select pdiag3 from get_diag(p.npasp))else null end)::char(6) AS k_si3, "+
    "(case when (po.d_po=1) then (select pdiag4 from get_diag(p.npasp))else null end)::char(6) AS k_si4, "+
    "(case when (po.d_po=1) then (select pdiag5 from get_diag(p.npasp))else null end)::char(6) AS k_si5, "+

 "(case when (po.d_po=1) then (select p_u1 from get_diag(p.npasp))else null end)::integer AS p_u_01, "+
 "(case when (po.d_po=1) then (select p_u2 from get_diag(p.npasp))else null end)::integer AS p_u_02, "+
 "(case when (po.d_po=1) then (select p_u3 from get_diag(p.npasp))else null end)::integer AS p_u_03, "+
 "(case when (po.d_po=1) then (select p_u4 from get_diag(p.npasp))else null end)::integer AS p_u_04, "+
 "(case when (po.d_po=1) then (select p_u5 from get_diag(p.npasp))else null end)::integer AS p_u_05, "+
    
"(case when (po.d_po=1) then (select n_pu1 from get_diag(p.npasp))else null end)::integer AS n_pu1, "+
"(case when (po.d_po=1) then (select n_pu2 from get_diag(p.npasp))else null end)::integer AS n_pu2, "+
"(case when (po.d_po=1) then (select n_pu3 from get_diag(p.npasp))else null end)::integer AS n_pu3, "+
"(case when (po.d_po=1) then (select n_pu4 from get_diag(p.npasp))else null end)::integer AS n_pu4, "+
"(case when (po.d_po=1) then (select n_pu5 from get_diag(p.npasp))else null end)::integer AS n_pu5, "+

"(case when (po.d_po=1) then (select f_h1 from get_diag(p.npasp))else null end)::integer AS f_h_1, "+
"(case when (po.d_po=1) then (select f_h2 from get_diag(p.npasp))else null end)::integer AS f_h_2, "+
"(case when (po.d_po=1) then (select f_h3 from get_diag(p.npasp))else null end)::integer AS f_h_3, "+
"(case when (po.d_po=1) then (select f_h4 from get_diag(p.npasp))else null end)::integer AS f_h_4, "+
"(case when (po.d_po=1) then (select f_h5 from get_diag(p.npasp))else null end)::integer AS f_h_5, "+
"(case when (po.d_po=1) then (select lod from get_recom_disp(p.npasp))else 0 end)::integer AS l_o_d, "+				
"(case when (po.d_po=1) then (select prov from get_recom_disp(p.npasp))else 0 end)::integer AS prov, "+
/**/

"(case when f.grzd=1 then 'I' when f.grzd=2 then 'II'  when f.grzd=3 then 'III'  when f.grzd=4 then 'IV'  when f.grzd=5 then 'V' end)::char(3) As gr_z,"+
  	    "(case when f.prb=0 then 2 else 1 end):: integer AS ch_b, "+
  	    "(case when f.prk=0 then 1 else 2 end):: integer AS m_p_z, "+
    	"1132::integer AS cod_reg, false::boolean AS err_,"+
  	    "0::integer AS postpone,"+
  	   "'{'||substr(i.nidv::text,1,length(i.nidv)-length(p.npasp::varchar)-1)||p.npasp::char(6)||(length(p.npasp::varchar))||'}'::char(150) AS id_fio,"+
  	  "(case when v.inv>0 then (select get_zab_inv(p.npasp)) else null end)::integer AS zab_inv,"+ 
  	 "(case when v.inv>0 then v.inv else null end)::integer AS inv,"+ 
/* участковый врач*/	
"trim(vr.fam)||' '||trim(vr.im)||' '||trim(vr.ot):: char(30) AS vrach "+
	  	   "FROM  patient p JOIN p_fiz f ON (p.npasp = f.npasp) JOIN n_n00 n ON (p.cpol_pr = n.pcod) JOIN n_idv i ON (p.cpol_pr = i.cpol)"+
			"JOIN n_u10 u ON (trim(p.adm_ul)||' '||trim(p.adm_dom) = u.name) JOIN p_inv v  ON (p.npasp=v.npasp) JOIN p_disp_ds_do ddo ON (p.npasp=ddo.npasp) JOIN p_disp_ds_po po ON (p.npasp=po.npasp)"+
		   "LEFT JOIN p_nambk na ON (p.npasp = na.npasp AND p.cpol_pr=na.cpol) LEFT JOIN s_uch uc  ON (uc.cpol=na.cpol AND uc.uch=na.nuch)"+
			"LEFT JOIN s_vrach vr ON (vr.pcod=uc.pcod)"+
	       
			"WHERE  p.cpol_pr = ? AND (f.dataz>? AND f.dataz<?) AND ((f.dataz-p.datar)/365.25>=14 AND (f.dataz-p.datar)/365.25<15)" ;		
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlsv3_14,cpodr, new Date(dn), new Date(dk)) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
			zos.putNextEntry(new ZipEntry("sv3_14.dbf"));
			while ((bufRead = dbfStr.read(buffer)) > 0)
				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return path;	}

	private String f025Selection(long dbegin, long dend, int porc, int cpodr) throws KmiacServerException, TException {
		String fpath = "", sqlo = "", sBuf, sc, sc0;
		String[] mas = {"","",""};
		int ndok = 0, n = 0, j = 0, sdok = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                fpath = File.createTempFile("p_"+String.valueOf(porc).trim(), ".txt").getAbsolutePath()), "Cp866")) {
    		StringBuilder sb = new StringBuilder();
    		sqlo = "SELECT o.id, o.npasp, o.cpol, o.datao, o.ishod, o.rezult, o.talon, o.cod_sp, o.cdol, o.cobr, "+ 
                   "p.npasp, p.fam,p.im,p.ot,p.datar,p.pol,p.sgrp,p.adm_gorod,p.adm_ul,p.adm_dom,p.adm_korp,p.adm_kv, "+
    			   "p.mrab, p.poms_ndog,p.poms_ser, p.poms_nom, p.tdoc,p.docser,p.docnum,p.ter_liv, d.diag, d.vid_tr, "+
                   "(select sel_kov(o.npasp)::char(30) AS kov), (select sel_perv(o.npasp,d.diag,?,?,o.cpol)::char(3) AS perv), "+
                   "(select sel_obr(o.cod_sp)::char(2) AS obr),(select sel_boll(o.id)::char(30) AS boll),"+
                   "(select sel_pos(o.id,d.diag)::char(25) AS kpos)"+
                   " from p_vizit o, patient p, p_diag_amb d where o.npasp = p.npasp and (o.datao >= ? and o.datao <= ?) and cpol = ?"+
    			   " and o.id=d.id_obr and d.predv=false and (o.ishod > 0 and d.diag<='T99.99' or d.diag>='Z00')";
    		try (AutoCloseableResultSet acrp = sse.execPreparedQuery(sqlo, new Date(dbegin), new Date(dend),new Date(dbegin),new Date(dend),cpodr)){
				ResultSet rs = acrp.getResultSet();
    			if (rs.next()) {
 //   				sBuf = "!.ABIJEMLM,CC2E.JOBLIB,P="+String.valueOf(porc).trim()+",K"+System.lineSeparator();
 //   				sBuf += "!.U025,koldokum,"+System.lineSeparator();
    				sBuf = "*"+Integer.toString(cpodr)+","+Integer.toString(porc)+",1"+System.lineSeparator();
    				sb.append(sBuf);
    				while (rs.next()) {
    					n = 0;
    					j = 0;
    			  // 		System.out.println(rs.getString("fam").trim()+";"+rs.getString("diag")+";"+rs.getString("kpos"));
    			   		if (rs.getString("kpos") != null) {
    					ndok=ndok+1;
    					sdok=sdok+1;
    					if (ndok > 99){
    						sBuf = System.lineSeparator();
    						sb.append(sBuf);
     						porc = porc+1;
    		   				sBuf = "*"+Integer.toString(cpodr)+","+Integer.toString(porc)+",1"+System.lineSeparator();
    		   			  	sb.append(sBuf);
    		   			  	ndok = 1;
    					}
    					sBuf=";";
    					if (ndok < 10) 
    						sBuf += "0"+Integer.toString(ndok);
    					else 
    						sBuf += Integer.toString(ndok);
    					if ((rs.getString("diag").trim().substring(0,1).equals("Z"))){
    						sc = "3";
    						sc0 = "2";
    					}
    						else {
    							sc = rs.getString("perv").substring(0,1);
    						    sc0 = "1";
    						}	
    					sBuf += ","+Integer.toString(cpodr)+","+sc;
    			   	//	if (rs.getString("kpos") != null) {
    					for (int i =0; i < rs.getString("kpos").trim().length(); i++) {
    						if (rs.getString("kpos").substring(i,i+1).equals(",") || rs.getString("kpos").trim().length()-1 == i) {
    							if (rs.getString("kpos").trim().length() - 1 > i )
    							mas[n] = rs.getString("kpos").substring(j,i);
    							else {
    								mas[n] = rs.getString("kpos").substring(j).trim();
        						//	System.out.println(rs.getString("kpos").substring(j));
    							}
        						n=n+1;
    							j=i+1;
    						} 
    					};
    			   		//};
    					sc = mas[0]+","+sdf.format(rs.getDate("datar"));
    					sBuf += ","+sc;
    					sBuf += ","+Integer.toString(rs.getInt("pol"));
    					sBuf += ","+rs.getString("diag").trim(); 
    					if (rs.getInt("vid_tr")>0)
    					sBuf += ","+Integer.toString(rs.getInt("vid_tr"));
    					else
    						sBuf += ",";
    		//	   		System.out.println(rs.getString("obr"));
    			   		if (rs.getString("obr") != null)
    					sBuf += ","+rs.getString("cdol").trim()+"-"+rs.getString("obr").trim()+","+mas[1];
    			   		else
    			   			sBuf += ","+rs.getString("cdol").trim()+"-,"+mas[1];
    		//	   		System.out.println(rs.getString("perv"));
    			   		if (rs.getString("perv").trim().length() > 2)
    					sBuf += ","+rs.getString("perv").substring(2).trim();
    			   		else
    			   			sBuf += ",";
    			   		if (rs.getString("boll").length() >0)
    					sBuf += ","+rs.getString("boll").trim();
    			   		else
    			   			sBuf += ",,,,,";
    					sBuf += ","+rs.getString("kov").trim();
    				    sBuf += ","+rs.getString("fam").trim()+" "+rs.getString("im").trim()+" "+rs.getString("ot").trim();
    				    if (rs.getString("adm_gorod").trim().equals("НОВОКУЗНЕЦК Г.")){
    				    	if (rs.getString("adm_korp") != null)
    				    	sBuf += ","+rs.getString("adm_ul").trim()+","+rs.getString("adm_dom").trim()+","+rs.getString("adm_korp").trim();
    				    	else
    				    		sBuf += ","+rs.getString("adm_ul").trim()+","+rs.getString("adm_dom").trim()+",";
    				    	if (rs.getString("adm_kv") != null)
    				    		sBuf += ","+rs.getString("adm_kv").trim();
    				    	else
    				    		sBuf += ",";
    				    } else
    				    	sBuf += ",("+rs.getString("adm_gorod").trim()+"),,,";
    				    sBuf += ","+Integer.toString(rs.getInt("sgrp"));
    				    if (rs.getInt("mrab") < 0)
    				    	sBuf += ",Л"+Integer.toString(rs.getInt("mrab")).substring(1, Integer.toString(rs.getInt("mrab")).length());
    				    else
    				    	sBuf += ","+Integer.toString(rs.getInt("mrab"));
    				    if (rs.getString("poms_ndog") != null)
				    	sBuf += ","+rs.getString("poms_ndog").toString();
				    	else
				    		sBuf += ",";
				    	sBuf += ","+Integer.toString(rs.getInt("ishod"));
				    	sBuf += ","+Integer.toString(rs.getInt("talon"));
				    	sBuf += ","+Integer.toString(rs.getInt("rezult"));
				    	sBuf += ","+mas[2]+","+sc0;
				    	if (rs.getString("poms_ser") != null)
				    	sBuf += ","+rs.getString("poms_ser").trim();
				    	else
				    		sBuf += ",";
				    	if (rs.getString("poms_nom") != null)
				    	sBuf += ","+rs.getString("poms_nom").trim();
				    	else
				    		sBuf += ",";
				    	if (rs.getInt("ter_liv") == 0) 
				    		sBuf += ","+Integer.toString(rs.getInt("tdoc"))+","+rs.getString("docser").trim()+","+rs.getString("docnum").trim(); 
				    	else   
				    		sBuf += ",,,";
				    	sBuf += ","+Integer.toString(rs.getInt("ter_liv"))+":"+System.lineSeparator();
    				    sb.append(sBuf);
    				//    System.out.println(sBuf);
    				}
    				};	
				} else {sBuf = "Отсутствует информация для выгрузки за заданный период с "+dbegin +" по "+dend;
					sb.append(sBuf);
				}
				sBuf = "!.ABIJEMLM,CC2E.JOBLIB,P="+String.valueOf(porc).trim()+",K"+System.lineSeparator();
				sBuf += "!.U025,"+String.valueOf(sdok).trim()+","+System.lineSeparator();
    			osw.write(sBuf);
				osw.write(sb.toString());	
				}catch (Exception e1) {
					e1.printStackTrace();
				}
    	
    	}catch (Exception e){
    		e.printStackTrace();
    	}
		return fpath;

   	}

	private String f039Selection(long dbegin, long dend, int porc, int cpodr) throws KmiacServerException, TException {
		String fpath = "", sqlo = "", sBuf, sc, sc0, kodvr;
		String[] mas = {"","",""};
		int ndok = 0, hp = 0, mp = 0, hd = 0, md = 0, hda = 0, mda = 0, hprf = 0, mprf = 0, hpr = 0, mpr = 0, kodspec = 0;
		int kodpol = cpodr;
		int p0 = 0, ps = 0, pi = 0, p0_0 = 0, p0_14 = 0, p15_17 = 0, p60 = 0;
		int pz0 = 0, pz0_0 = 0, pz0_14 = 0, pz15_17 = 0, pz60 = 0;
		int pp0 = 0, pps = 0, pd0 = 0, perv_p = 0, perv_d = 0;
		int pzd0 = 0, pzd0_0 = 0, pzd0_14 = 0, pzd15_17 = 0, pzd60 = 0;
		int pp0_14 = 0, pp0_0 = 0, pp15_17 = 0, pbud = 0, poms = 0, pdms = 0, pplat = 0;
		float h; 
		long d1, d2;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		SimpleDateFormat sdfr = new SimpleDateFormat("yyMMdd");
    	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                fpath = File.createTempFile("p_"+String.valueOf(porc).trim(), ".txt").getAbsolutePath()), "Cp866")) {
    		StringBuilder sb = new StringBuilder();
    		sqlo = "SELECT distinct(t.pcod,t.cdol), "+ 
                   "v.fam, v.im, v.ot, v.pol, v.datar ,v.obr from s_tabel t, s_vrach v "+
                   "where t.pcod = v.pcod and (t.datav >= ? and v.datav <= ?) and t.cpodr = ? "+
                   "order by t.pcod, t.cdol";
    		try (AutoCloseableResultSet acrp = sse.execPreparedQuery(sqlo, new Date(dbegin), new Date(dend),kodpol)){
				ResultSet rs = acrp.getResultSet();
    			if (rs.next()) {
    				while (rs.next()) {
    				ndok = ndok+1;
    				if (ndok > 99){
						sBuf = System.lineSeparator();
						sb.append(sBuf);
 						porc = porc+1;
		   				sBuf = "*"+Integer.toString(cpodr)+","+Integer.toString(porc)+",1"+System.lineSeparator();
		   			  	sb.append(sBuf);
		   			  	ndok = 1;
					}
					sBuf=";";
					if (ndok < 10) 
						sBuf += "0"+Integer.toString(ndok);
					else 
						sBuf += Integer.toString(ndok);
    	    		kodspec = rs.getInt("pcod");
    	    		kodvr = rs.getString("kodvr");
    				sBuf += Integer.toString(kodpol).trim()+","+rs.getString("fam").trim()+","+rs.getString("im").trim()+","+
    						rs.getString("ot").trim()+","+Integer.toString(rs.getInt("pol")).trim()+","+sdfr.format(rs.getDate("datar")).trim()+","+
    						rs.getString("im").substring(0,1)+rs.getString("ot").substring(0,1)+","+kodvr+","+
    						rs.getString("obr")+","+sdf.format(dend);
    				
    				sqlo = "SELECT timep, timed, timeda, timeprf, timepr "+ 
    	                    "from s_tabel "+
    	                    "where pcod = ? and cdol = ? and (t.datav >= ? and v.datav <= ?) and t.cpodr = ? "+
    	                    "order by t.pcod, t.cdol";
    				try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlo, kodspec, kodvr, new Date(dbegin), new Date(dend), kodpol)){
    					ResultSet rst = acrs.getResultSet();
    				   	hp = 0; hd = 0; hda = 0; hprf = 0; hpr = 0;
    				   	mp = 0; md = 0; mda = 0; mprf = 0; mpr = 0;
        				while (rst.next()) {
        					hp = hp+(int)rst.getFloat("timep");
        					mp = mp+ (int)((rst.getFloat("timep")-(int)rst.getFloat("timep"))*100);
        					hd = hd+(int)rst.getFloat("timed");
        					md = md+ (int)((rst.getFloat("timed")-(int)rst.getFloat("timed"))*100);
        					hda = hda+(int)rst.getFloat("timeda");
        					mda = mda+ (int)(mp+(rst.getFloat("timeda")-(int)rst.getFloat("timeda"))*100);
        					hprf = hprf+(int)rst.getFloat("timeprf");
        					mprf = mprf+ (int)((rst.getFloat("timeprf")-(int)rst.getFloat("timeprf"))*100);
        					hpr = hpr+(int)rst.getFloat("timepr");
        					mpr = mpr+ (int)((rst.getFloat("timepr")-(int)rst.getFloat("timepr"))*100);
        				}
    				h = (hp+hd+hda+hprf+hpr+(mp+md+mda+mprf+mpr)/60);
    				sBuf += ","+String.valueOf(h).trim();
    				h = (hp+mp/60);
    				sBuf += ","+String.valueOf(h);
    				h = (hprf+mprf/60);
    				sBuf += ","+String.valueOf(h);
    				h = (hd+hda+(md+mda)/60);
    				sBuf += ","+String.valueOf(h);
    				}
    				sqlo = "SELECT distinct(v.npasp,v.datap), v.diag, v.mobs, v.cpos, v.opl, v.id_obr, "+ 
    						"p.datar, p.ter_liv,(select sel_xzab(v.npasp,v.diag)::int(1) AS xzab), "+
    						"(select sel_diag_god(v.npasp,v.diag,?,?)::int(1) AS perv"+
    	                    "from p_vizit_amb v, patient p, "+
    	                    "where v.npasp = p.npasp and (v.datap >= ? and v.datap <= ?) and v.cpol = ? and v.cod_sp = ? and v.cdol = ?";
    				try (AutoCloseableResultSet acrv = sse.execPreparedQuery(sqlo, new Date(dbegin), new Date(dbegin), new Date(dend), kodpol,kodspec,kodvr)){
    				  	ResultSet rsv = acrv.getResultSet();
    					while (rsv.next()) {
    			   		p0 = p0+1;
    			   		if (rsv.getInt("ter_liv")==22) ps = ps+1;
    			   		if (rsv.getInt("ter_liv")!=10) pi = pi+1;
    					d1 = rsv.getDate("datap").getTime();
    					d2 = rsv.getDate("datar").getTime() ;
    					if (rsv.getInt("mobs") == 2 || rsv.getInt("mobs") == 3) pd0 =pd0 + 1;
    					if (rsv.getInt("opl") == 1) pbud = pbud+1;
    					if (rsv.getInt("opl") == 2) poms = poms+1;
    					if (rsv.getInt("opl") == 3) pdms = pdms+1;
    					if (rsv.getInt("opl") == 4) pplat = pplat+1;
    					h = (float) ((d1-d2)/(3600000*24*365.25));
    					if (h < 1) p0_0 = p0_0 +1;
    					if (h < 15) p0_14 = p0_14+1;
    					if (h >= 15 && h < 18) p15_17 = p15_17+1;
    					if (h >= 60) p60 = p60+1;
    					if (rsv.getString("diag").substring(0, 1).equals("Z")){
    						pp0 = pp0+1;
    						if (rsv.getInt("cpos")==2) pps = pps+1;
    						if (h < 1) pp0_0 = pp0_0 +1;
        					if (h < 15) pp0_14 = pp0_14+1;
        					if (h >= 15 && h < 18) pp15_17 = pp15_17+1;
        	 			} else {
    						pz0 = pz0+1;
    						if (h < 1) pz0_0 = pz0_0 +1;
        					if (h < 15) pz0_14 = pz0_14+1;
        					if (h >= 15 && h < 18) pz15_17 = pz15_17+1;
    						if (h >= 60) pz60 = pz60+1;
    						if (rsv.getInt("perv") == 1 || rsv.getInt("xzab") == 1){
    							if (rsv.getInt("mobs") == 1) perv_p = perv_p+1;
    							if (rsv.getInt("mobs") == 2 || rsv.getInt("mobs") == 3) perv_d = perv_d+1;
    						}
    						if (rsv.getInt("mobs") == 2 || rsv.getInt("mobs") == 3) {
    							pzd0 = pzd0 + 1;
        						if (h < 1) pzd0_0 = pzd0_0 +1;
            					if (h < 15) pzd0_14 = pzd0_14+1;
            					if (h >= 15 && h < 18) pzd15_17 = pzd15_17+1;
            					if (h >= 60) pzd60 = pzd60 +1;
    						}
    			        	
    					}
    					
    					}
    					sBuf += ","+String.valueOf(p0).trim()+","+String.valueOf(ps).trim()+
    							","+String.valueOf(pi).trim()+","+String.valueOf(p0_14).trim()+
    							","+String.valueOf(p0_0).trim()+","+String.valueOf(p15_17).trim()+
    							","+String.valueOf(p60).trim()+","+String.valueOf(pz0).trim()+
    							","+String.valueOf(pz0_14).trim()+","+String.valueOf(pz0_0).trim()+
    							","+String.valueOf(pz15_17).trim()+","+String.valueOf(pz60).trim()+
    							","+String.valueOf(pp0).trim()+","+String.valueOf(pps).trim()+
    							","+String.valueOf(pd0).trim()+","+String.valueOf(pzd0).trim()+
    							","+String.valueOf(pzd0_14).trim()+","+String.valueOf(pzd0_0).trim()+
    							","+String.valueOf(pzd15_17).trim()+","+String.valueOf(pzd60).trim()+
    							","+String.valueOf(pp0_14).trim()+","+String.valueOf(pp0_0).trim()+
    							","+String.valueOf(pp15_17).trim()+","+String.valueOf(pbud).trim()+
    							","+String.valueOf(poms).trim()+","+String.valueOf(pdms).trim()+
    							","+String.valueOf(pplat).trim()+","+String.valueOf(perv_p).trim()+
    							","+String.valueOf(perv_d).trim();
    				
    				}
    				}
    			}
    			else {sBuf = "Отсутствует информация для выгрузки за заданный период с "+dbegin +" по "+dend;
				sb.append(sBuf);
			}
    		}
						 
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				
    				
				sBuf = "!.ABBJEMLM,CC2M.JOBLIB,P=2319,K"+System.lineSeparator();
				sBuf += "!.U025,393,"+System.lineSeparator();
//				osw.write(sBuf);
//				osw.write(sb.toString());	
				return fpath;
	}		


	@Override
	public String getFertInfoPol(int cpodr, long dn, long dk)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		String sqlreg;
		String path = null;
		int bufRead;
		byte[] buffer = new byte[8192];
		
		try (FileOutputStream fos = new FileOutputStream(path = File.createTempFile("FertInfoPol", ".zip").getAbsolutePath());
	 		ZipOutputStream zos = new ZipOutputStream(fos)) {
	
		sqlreg = "SELECT p.npasp::integer AS bn, 907::integer AS kter,null::integer AS klpu,p.fam::char(20) AS fam,p.im::char(15) AS im,p.ot::char(20) AS otch,p.datar AS dr,  "+
		"(select terp from get_ter(p.adp_gorod::text,p.adm_gorod::text))::integer AS kterp,(select term from get_ter(p.adp_gorod::text,p.adm_gorod::text))::integer AS kterf," +
		"trim(p.adp_gorod)||' '|| trim(p.adp_ul)||' '||trim(p.adp_dom)||'-'||trim(p.adp_kv):: char(70) AS adresp, "+
		"trim(p.adm_gorod)||' '|| trim(p.adm_ul)||' '||trim(p.adm_dom)||'-'||trim(p.adm_kv):: char(70) AS adresf, "+
		"10::integer AS kterl, p.cpol_pr ::integer AS klpup, f.osn :: char(150) AS osn,f.dn AS dn, f.dk AS dk, f.kpri::integer AS kpri  "+
		"FROM  patient p JOIN p_fert f ON (p.npasp = f.npasp) "+
		"WHERE  p.cpol_pr = ? AND (f.dataz>? AND f.dataz<?) " ;		

       
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlreg,cpodr, new Date(dn), new Date(dk)) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
			zos.putNextEntry(new ZipEntry("reg.dbf"));
			while ((bufRead = dbfStr.read(buffer)) > 0)
				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqldiag = "select d.npasp::integer AS bn, d.diag::char(7) AS dia"+
				"from patient p JOIN p_fert f ON (p.npasp = f.npasp) JOIN p_diag d ON (p.npasp = d.npasp and d.pat=1) "+
		"WHERE  p.cpol_pr = ? AND (f.dataz>? AND f.dataz<?) " ;	
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqldiag,cpodr, new Date(dn), new Date(dk)) ;
				InputStream dbfStr = new DbfMapper(acrs.getResultSet()).mapToStream()) {
			zos.putNextEntry(new ZipEntry("diag.dbf"));
			while ((bufRead = dbfStr.read(buffer)) > 0)
				zos.write(buffer, 0, bufRead);
		} catch (SQLException e) {
	        log.log(Level.ERROR, "SQl Exception: ", e);
			throw new KmiacServerException();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return path;	}

	@Override
	public String getInvInfoPol(int cpodr, long dn, long dk)
			throws KmiacServerException, TException {
		String fpath = "", sqlo = "",sBuf, pl, nmed_reab ="", nps_reab="",nprof_reab="",nsoc_reab="";
		String id_fio="",  name_bk="", name_pr="",d_inv="",d_invp="",pasp="";
		 int med_reab=0, ps_reab=0, prof_reab=0,   soc_reab=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat sdfr = new SimpleDateFormat("dd.mm.yyyy");
    	try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                fpath = File.createTempFile("Invalid_Main",".dat").getAbsolutePath()), "Cp866")) {
    		StringBuilder sb = new StringBuilder();
    		sqlo =  "select p.fam,p.im,p.ot,p.datar,p.pol,p.npasp,p.sgrp,p.adm_gorod,p.adm_ul,p.adm_dom,p.adm_kv,p.poms_ser,p.poms_nom,p.poms_strg,"+
    		   "i.dataz,i.datav,i.mesto1,i.rez_mse,i.preds,i.uchr,i.diag,i.oslog,i.factor,i.fact2,i.fact4,i.prognoz,i.vrach,i.ninv,"+
    		   "i.diag_s1,i.diag_s2,i.diag_s3,i.d_inv,i.d_invp,i.srok_inv,i.zakl,i.med_reab,i.ps_reab,i.prof_reab,i.soc_reab,i.potencial,i.nom_mse,i.name_mse,i.ruk_mse,i.d_otpr,i.d_srok,i.zakl_name,i.d_osv,"+
    		   "klin_prognoz,nar1,nar2,nar3,nar4,nar5,nar6,ogr1,ogr2,ogr3,ogr4,ogr5,ogr6,ogr7,mr1n,mr2n,mr3n,mr4n,mr5n,mr6n,mr7n,mr8n,mr9n,mr10n,mr11n,mr12n,mr13n,mr14n,mr15n,mr16n,mr17n,"+
    		   "mr18n,mr19n,mr20n,mr21n,mr22n,mr23n,pr1n,pr2n,pr3n,pr4n,pr5n,pr6n,pr7n,pr8n,pr9n,pr10n,pr11n,pr12n,pr13n,pr14n,pr15n,pr16n,mr1v,mr2v,mr3v,mr4v,mr5v,mr6v,mr7v,mr8v,mr9v,"+
    		   "mr10v,mr11v,mr12v,mr13v,mr14v,mr15v,mr16v,mr17v,mr18v,mr19v,mr20v,mr21v,mr22v,mr23v,pr1v,pr2v,pr3v,pr4v,pr5v,pr6v,pr7v,pr8v,pr9v,pr10v,pr11v,pr12v,pr13v,pr14v,pr15v,pr16v,mr1d,mr2d,mr3d,mr4d,pr1d"+
    		   "k.name_s, v.name,id.nidv,n.name,v0a.name,v0m.name,v0p.name,v0c.name,v0e.name,v0f.name,v0g.name"+
    		   "from patient p JOIN p_invk i ON (p.npasp = i.npasp) LEFT JOIN p_kas k ON (p.poms_strg = k.pcod) LEFT JOIN n_v0f v ON (p.poms_strg = v.klin_prognoz) "+
    		   "LEFT JOIN n_idv id ON (p.cpol_pr = id.cpol) LEFT JOIN n_n00 n ON (p.cpol_pr = n.pcod)"+
               "LEFT JOIN n_v0a v0a ON (i.factor = v0a.pcod) LEFT JOIN n_v0m v0m ON (mesto1 = v0m.pcod)"+
    		   "LEFT JOIN n_v0p v0p ON (preds = v0p.pcod) LEFT JOIN n_v0c v0c ON (fact2 = v0c.pcod) LEFT JOIN n_v0e v0e ON (fact4 = v0e.pcod)"+
               "LEFT JOIN n_v0f v0f ON (prognoz = v0f.pcod) LEFT JOIN n_v0g v0g ON (potencial = v0g.pcod)"+
    		   "where  p.cpol_pr = ? and (i.datav >= ? and i.datav <= ?)";
                                     
    		try (AutoCloseableResultSet acrp = sse.execPreparedQuery(sqlo, cpodr,new Date(dk), new Date(dn))){
				ResultSet rs = acrp.getResultSet();
    			if (rs.next()) {
    				while (rs.next()) {
    				
    				id_fio=rs.getString("id.nidv").trim().substring(1,rs.getString("id.nidv").trim().length()-rs.getString("npasp").trim().length()-1)+rs.getString("npasp").trim()+rs.getString("npasp").trim().length();
    				name_bk= rs.getString("k.name_s").trim();   
    				name_pr= rs.getString("v.name").trim();  
    				
    			    if (rs.getString("d_inv").trim() == null)  d_inv=""; else d_inv=sdf.format(rs.getDate("d_inv"))+" 00:00:00";
    			    if (rs.getString("d_invp").trim() == null)  d_invp=""; else d_invp=sdf.format(rs.getDate("d_invp"))+" 00:00:00";
    			    
    		//	    if Fields[43].IsNull then d_otp:=#9#9 else d_otp:=FormatDateTime('dd',Fields[43].AsDateTime)+#9+FormatDateTime('mm',Fields[43].AsDateTime)+#9+FormatDateTime('yyyy',Fields[43].AsDateTime);
    		//	    if Fields[44].IsNull then d_srok:=#9#9 else d_srok:=FormatDateTime('dd',Fields[44].AsDateTime)+#9+FormatDateTime('mm',Fields[44].AsDateTime)+#9+FormatDateTime('yyyy',Fields[44].AsDateTime);

					// TODO Auto-generated method stub
    				 if (rs.getInt("p.pol")==1)  pl = "Мужской"; else pl = "Женский";
    				
					if (rs.getInt("mr1n")>0 || rs.getInt("mr2n")>0 || rs.getInt("mr3n")>0 || rs.getInt("mr4n")>0 || rs.getInt("mr5n")>0 || rs.getInt("mr6n")>0 || rs.getInt("mr7n")>0 || rs.getInt("mr8n")>0 || rs.getInt("mr9n")>0 ||
    				 rs.getInt("mr10n")>0 || rs.getInt("mr11n")>0 || rs.getInt("mr12n")>0 || rs.getInt("mr13n")>0 || rs.getInt("mr14n")>0 || rs.getInt("mr15n")>0 || rs.getInt("mr16n")>0 || rs.getInt("mr17n")>0 || rs.getInt("mr18n")>0 ||
    				 rs.getInt("mr19n")>0 || rs.getInt("mr20n")>0 || rs.getInt("mr21n")>0 || rs.getInt("mr22n")>0 || rs.getInt("mr23n")>0) med_reab=1;
    					  
				if (rs.getInt("pr1n")>0 || rs.getInt("pr2n")>0 || rs.getInt("pr3n")>0)  ps_reab=1;
    			    
				if (rs.getInt("pr4n")>0 || rs.getInt("pr5n")>0)  prof_reab=1;
    				   
				if (rs.getInt("pr6n")>0 || rs.getInt("pr7n")>0)  soc_reab=1;
				
				 if (med_reab==1)   nmed_reab = rs.getString("med_reab").trim();  else nmed_reab="";
				 if (ps_reab==1)   nps_reab = rs.getString("ps_reab").trim(); else nps_reab="";
				 if (prof_reab==1) nprof_reab=rs.getString("prof_reab").trim(); else nprof_reab="";
  			     if (soc_reab==1)  nsoc_reab=rs.getString("soc_reab").trim(); else nsoc_reab="";
				  
  			     
  				pasp+=id_fio+"\u0009"+sdf.format(rs.getDate("i.dataz"))+" 12:01:30.000"+"\u0009"+"1"+"\u0009"+"1"+"\u0009"+
  					"\u0009"+sdf.format(rs.getDate("i.datav"))+" 00:00:00"+"\u0009"+"Детская поликлиника"+"\u0009"+rs.getString("n.name").trim()+
  					"\u0009"+"32"+"\u0009"+"231"+rs.getString("fam").trim()+"\u0009"+rs.getString("im").trim()+"\u0009"+rs.getString("ot").trim()+"\u0009"+pl+
  					"\u0009"+rs.getString("p.poms_ser").trim()+rs.getString("p.poms_nom").trim()+"\u0009"+rs.getString("name_bk").trim()+"\u0009"+
  					sdf.format(rs.getDate("datar"))+" 00:00:00"+"\u0009"+rs.getString("mesto1").trim()+"\u0009"+rs.getString("rez_mse").trim()+"\u0009"+
  					rs.getString("adm_dom").trim()+"-"+rs.getString("adm_kv").trim()+"\u0009"+rs.getString("adm_gorod").trim()+"\u0009"+
  					rs.getString("adm_ul").trim()+"\u0009"+rs.getString("v0p.name").trim()+"\u0009"+rs.getString("uchr").trim()+"\u0009"+
  					rs.getString("i.diag").trim()+"\u0009"+rs.getString("oslog").trim()+"\u0009"+rs.getString("v0a.name").trim()+"\u0009"+"\u0009"+"\u0009"+
  					rs.getString("v0c.name").trim()+"\u0009"+rs.getString("v0e.name").trim()+"\u0009"+
  					rs.getString("v0f.name").trim()+"\u0009"+
  					rs.getString("mr1n").trim()+"\u0009"+rs.getString("mr1d").trim()+"\u0009"+get_v0t(rs.getInt("mr1v"))+"\u0009"+
  					rs.getString("mr2n").trim()+"\u0009"+rs.getString("mr2d").trim()+"\u0009"+get_v0t(rs.getInt("mr2v"))+"\u0009"+
  					rs.getString("mr3n").trim()+"\u0009"+get_v0t(rs.getInt("mr3v"))+"\u0009"+
  					rs.getString("mr4n").trim()+"\u0009"+get_v0t(rs.getInt("mr4v"))+"\u0009"+
  					rs.getString("mr5n").trim()+"\u0009"+get_v0t(rs.getInt("mr5v"))+"\u0009"+
  					rs.getString("mr6n").trim()+"\u0009"+get_v0t(rs.getInt("mr6v"))+"\u0009"+
  					rs.getString("mr7n").trim()+"\u0009"+get_v0t(rs.getInt("mr7v"))+"\u0009"+
  					rs.getString("mr3d").trim()+"\u0009"+
  					rs.getString("mr8n").trim()+"\u0009"+get_v0t(rs.getInt("mr8v"))+"\u0009"+
  					rs.getString("mr9n").trim()+"\u0009"+get_v0t(rs.getInt("mr9v"))+"\u0009"+
  					rs.getString("mr10n").trim()+"\u0009"+get_v0t(rs.getInt("mr10v"))+"\u0009"+
  					rs.getString("mr11n").trim()+"\u0009"+get_v0t(rs.getInt("mr11v"))+"\u0009"+
  					rs.getString("mr12n").trim()+"\u0009"+get_v0t(rs.getInt("mr12v"))+"\u0009"+
  					rs.getString("mr13n").trim()+"\u0009"+get_v0t(rs.getInt("mr13v"))+"\u0009"+
  					rs.getString("mr14n").trim()+"\u0009"+get_v0t(rs.getInt("mr14v"))+"\u0009"+
  					rs.getString("mr15n").trim()+"\u0009"+get_v0t(rs.getInt("mr15v"))+"\u0009"+
  					rs.getString("mr16n").trim()+"\u0009"+get_v0t(rs.getInt("mr16v"))+"\u0009"+
  					rs.getString("mr17n").trim()+"\u0009"+get_v0t(rs.getInt("mr17v"))+"\u0009"+
  					rs.getString("mr18n").trim()+"\u0009"+get_v0t(rs.getInt("mr18v"))+"\u0009"+
  					rs.getString("mr19n").trim()+"\u0009"+get_v0t(rs.getInt("mr19v"))+"\u0009"+
  					rs.getString("mr21n").trim()+"\u0009"+get_v0t(rs.getInt("mr21v"))+"\u0009"+
  					rs.getString("mr22n").trim()+"\u0009"+get_v0t(rs.getInt("mr22v"))+"\u0009"+
  					rs.getString("mr23n").trim()+"\u0009"+get_v0t(rs.getInt("mr23v"))+"\u0009"+
  					rs.getString("mr20n").trim()+"\u0009"+get_v0t(rs.getInt("mr20v"))+"\u0009"+
  					String.valueOf(med_reab).trim()+
  					rs.getString("pr1n").trim()+"\u0009"+get_v0t(rs.getInt("pr1v"))+"\u0009"+
  					rs.getString("pr2n").trim()+"\u0009"+get_v0t(rs.getInt("pr2v"))+"\u0009"+
  					rs.getString("pr3n").trim()+"\u0009"+get_v0t(rs.getInt("pr3v"))+"\u0009"+
  					String.valueOf(ps_reab).trim()+
  					rs.getString("pr4n").trim()+"\u0009"+get_v0t(rs.getInt("pr4v"))+"\u0009"+
  					rs.getString("pr5n").trim()+"\u0009"+get_v0t(rs.getInt("pr5v"))+"\u0009"+
  					String.valueOf(prof_reab).trim()+
  					rs.getString("pr6n").trim()+"\u0009"+get_v0t(rs.getInt("pr6v"))+"\u0009"+
  					rs.getString("pr7n").trim()+"\u0009"+get_v0t(rs.getInt("pr7v"))+"\u0009"+
  					String.valueOf(soc_reab).trim()+
  					rs.getString("pr8n").trim()+"\u0009"+get_v0t(rs.getInt("pr8v"))+"\u0009"+
  					rs.getString("pr9n").trim()+"\u0009"+get_v0t(rs.getInt("pr9v"))+"\u0009"+
  					rs.getString("pr10n").trim()+"\u0009"+get_v0t(rs.getInt("pr10v"))+"\u0009"+
  					rs.getString("pr11n").trim()+"\u0009"+get_v0t(rs.getInt("pr11v"))+"\u0009"+
  					rs.getString("pr12n").trim()+"\u0009"+get_v0t(rs.getInt("pr12v"))+"\u0009"+
  					rs.getString("pr13n").trim()+"\u0009"+get_v0t(rs.getInt("pr13v"))+"\u0009"+
  					rs.getString("pr14n").trim()+"\u0009"+get_v0t(rs.getInt("pr14v"))+"\u0009"+
  					rs.getString("pr15n").trim()+"\u0009"+get_v0t(rs.getInt("pr15v"))+"\u0009"+
  					rs.getString("pr16n").trim()+"\u0009"+
  					rs.getString("pr1d").trim()+"\u0009"+get_v0t(rs.getInt("pr16v"))+"\u0009"+
  					d_inv+"\u0009"+d_invp+"\u0009"+
  					rs.getString("srok_inv").trim()+"\u0009"+rs.getString("zakl").trim()+"\u0009"+
  					nmed_reab+"\u0009"+nps_reab+"\u0009"+nprof_reab+"\u0009"+nsoc_reab+"\u0009"+
  					"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"\u0009"+"\u0009"+"\u0009"+"\u0009"+"\u0009"+"\u0009"+"\u0009"+
  					"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"\u0009"+
  					"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"0"+"\u0009"+"\u0009"+
  					rs.getString("v0g.name").trim()+"\u0009"+rs.getString("nom_mse").trim()+"\u0009"+
  					rs.getString("nar1").trim()+"\u0009"+rs.getString("nar2").trim()+"\u0009"+rs.getString("nar3").trim()+"\u0009"+rs.getString("nar4").trim()+"\u0009"+rs.getString("nar5").trim()+"\u0009"+rs.getString("nar6").trim()+"\u0009"+"\u0009"+
  					rs.getString("ogr1").trim()+"\u0009"+rs.getString("ogr2").trim()+"\u0009"+rs.getString("ogr3").trim()+"\u0009"+rs.getString("ogr4").trim()+"\u0009"+rs.getString("ogr5").trim()+"\u0009"+rs.getString("ogr6").trim()+"\u0009"+rs.getString("ogr7").trim()+"\u0009"+
  					"~~~";
    				}}
			else {sBuf = "Отсутствует информация для выгрузки за заданный период с "+dn +" по "+dk;
			}
  			sb.append(pasp);  
  					}}
    		catch (Exception e) {
				e.printStackTrace();
			
    		}
		return fpath ;
	    		

    		}

	private String get_v0t(int int1) throws SQLException {
		// TODO Auto-generated method stub
	
		String sql = "select name from n_v0t  where pcod=?";
		AutoCloseableResultSet acrs = sse.execPreparedQuery(sql,int1) ;
		ResultSet nam1 = acrs.getResultSet();

		return nam1.getString("name").trim() ;
	}}		


	

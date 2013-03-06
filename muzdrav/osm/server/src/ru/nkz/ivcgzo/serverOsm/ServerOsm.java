package ru.nkz.ivcgzo.serverOsm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.serverManager;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.Cgosp;
import ru.nkz.ivcgzo.thriftOsm.Cotd;
import ru.nkz.ivcgzo.thriftOsm.IsslInfo;
import ru.nkz.ivcgzo.thriftOsm.IsslMet;
import ru.nkz.ivcgzo.thriftOsm.IsslPokaz;
import ru.nkz.ivcgzo.thriftOsm.KartaBer;
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.Napr;
import ru.nkz.ivcgzo.thriftOsm.NaprKons;
import ru.nkz.ivcgzo.thriftOsm.PNapr;
import ru.nkz.ivcgzo.thriftOsm.P_isl_ld;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pdisp;
import ru.nkz.ivcgzo.thriftOsm.PdispNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pmer;
import ru.nkz.ivcgzo.thriftOsm.Pobost;
import ru.nkz.ivcgzo.thriftOsm.Pokaz;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;
import ru.nkz.ivcgzo.thriftOsm.PrdslNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PrdDinNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Prez_d;
import ru.nkz.ivcgzo.thriftOsm.Prez_l;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdConVizit;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdPatient;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
import ru.nkz.ivcgzo.thriftOsm.RdVizit;
import ru.nkz.ivcgzo.thriftOsm.Shablon;
import ru.nkz.ivcgzo.thriftOsm.ShablonText;
import ru.nkz.ivcgzo.thriftOsm.SpravNetrud;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.Iface;
import ru.nkz.ivcgzo.thriftOsm.VrachInfo;
import ru.nkz.ivcgzo.thriftOsm.Vypis;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class ServerOsm extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<ZapVr, ZapVr._Fields> rsmZapVr;
	@SuppressWarnings("unused")
	private final Class<?>[] zapVrTypes; 
	private final TResultSetMapper<VrachInfo, VrachInfo._Fields> rsmVrachInfo;
	private final TResultSetMapper<Pvizit, Pvizit._Fields> rsmPvizit;
	private final Class<?>[] pvizitTypes; 
	private final TResultSetMapper<PvizitAmb, PvizitAmb._Fields> rsmPvizitAmb;
	private final Class<?>[] pvizitAmbTypes; 
	private final TResultSetMapper<PdiagAmb, PdiagAmb._Fields> rsmPdiagAmb;
	private final Class<?>[] pdiagAmbTypes; 
	private final TResultSetMapper<PdiagZ, PdiagZ._Fields> rsmPdiagZ;
	private final Class<?>[] pdiagZTypes; 
	private final TResultSetMapper<Psign, Psign._Fields> rsmPsign;
	private final Class<?>[] psignTypes; 
	private final TResultSetMapper<Priem, Priem._Fields> rsmPriem;
	private final Class<?>[] priemTypes; 
	private final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	@SuppressWarnings("unused")
	private final Class<?>[] intClasTypes; 
	private final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClas;
	@SuppressWarnings("unused")
	private final Class<?>[] strClasTypes; 
	private final TResultSetMapper<P_isl_ld, P_isl_ld._Fields> rsmPislld;
	private final Class<?>[] pislldTypes; 
	@SuppressWarnings("unused")
	private final TResultSetMapper<Prez_d, Prez_d._Fields> rsmPrezd;
	private final Class<?>[] prezdTypes; 
	@SuppressWarnings("unused")
	private final TResultSetMapper<Prez_l, Prez_l._Fields> rsmPrezl;
	private final Class<?>[] prezlTypes; 
	private final TResultSetMapper<Metod, Metod._Fields> rsmMetod;
	@SuppressWarnings("unused")
	private final Class<?>[] metodTypes; 
	private final TResultSetMapper<PokazMet, PokazMet._Fields> rsmPokazMet;
	@SuppressWarnings("unused")
	private final Class<?>[] pokazMetTypes; 
	private final TResultSetMapper<Pokaz, Pokaz._Fields> rsmPokaz;
	@SuppressWarnings("unused")
	private final Class<?>[] pokazTypes;
	private final TResultSetMapper<AnamZab, AnamZab._Fields> rsmAnamZab;
	private final Class<?>[] anamZabTypes; 
	private final TResultSetMapper<IsslInfo, IsslInfo._Fields> rsmIsslInfo;
	@SuppressWarnings("unused")
	private final Class<?>[] isslInfoTypes;
	private final TResultSetMapper<Pdisp, Pdisp._Fields> rsmPdisp;
	private final Class<?>[] pdispTypes;
	private final TResultSetMapper<RdSlStruct, RdSlStruct._Fields> rsmRdSl;
	private final Class<?>[] rdSlTypes;
	private final TResultSetMapper<RdInfStruct, RdInfStruct._Fields> rsmRdInf;
	private final Class<?>[] rdInfTypes;
	private final TResultSetMapper<RdDinStruct, RdDinStruct._Fields> rsmRdDin;
	private final Class<?>[] rdDinTypes;
	private final TResultSetMapper<RdPatient, RdPatient._Fields> rsmRdPat;
	@SuppressWarnings("unused")
	private final Class<?>[] rdPatientTypes;
	private final TResultSetMapper< RdVizit, RdVizit._Fields> rsmRdViz;
	@SuppressWarnings("unused")
	private final Class<?>[] rdVizitTypes;
	private final TResultSetMapper< RdConVizit, RdConVizit._Fields> rsmRdCV;
	@SuppressWarnings("unused")
	private final Class<?>[] rdConVizitTypes;
	private final TResultSetMapper<Pmer, Pmer._Fields> rsmPmer;
	private final Class<?>[] pmerTypes;
	private final Class<?>[] pnaprTypes;
	private final TResultSetMapper<Pobost, Pobost._Fields> rsmPobost;
	private final Class<?>[] pobostTypes;
	@SuppressWarnings("unused")
	private final TResultSetMapper<Cgosp, Cgosp._Fields> rsmCgosp;
	private final Class<?>[] cgospTypes;
	@SuppressWarnings("unused")
	private final TResultSetMapper<Cotd, Cotd._Fields> rsmCotd;
	private final Class<?>[] cotdTypes;	


	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp",       "fam",        "im",         "ot",         "poms_ser",   "poms_nom",   "id_pvizit",  "pol",          "datar",    "datap",    "nuch",        "has_pvizit",  "id_pvizit_amb");
		zapVrTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Boolean.class, Integer.class};
		
		rsmVrachInfo = new TResultSetMapper<>(VrachInfo.class);
		
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id",          "npasp",       "cpol",        "datao",    "ishod",       "rezult",      "talon",       "cod_sp",      "cdol",       "cuser",       "zakl",       "dataz",    "recomend",   "lech",       "cobr",        "idzab",       "vrach_fio",  "closed");
		pvizitTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, String.class, String.class, Integer.class, Integer.class, String.class, Boolean.class};
		
		rsmPvizitAmb = new TResultSetMapper<>(PvizitAmb.class, "id",          "id_obr",      "npasp",       "datap",    "cod_sp",      "cdol",       "diag",       "mobs",        "rezult",      "opl",         "stoim",      "uet",         "d_rez",    "kod_rez",     "k_lr",        "n_sp",        "pr_opl",      "pl_extr",     "vpom",        "fio_vr",    "dataz",    "cpos",        "cpol",        "kod_ter",      "cdol_name");
		pvizitAmbTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class, Integer.class, Integer.class, String.class};
		
		rsmPdiagAmb = new TResultSetMapper<>(PdiagAmb.class, "id",          "id_obr",      "npasp",       "diag",       "named",      "diag_stat",   "predv",       "datad",    "obstreg",     "cod_sp",      "cdol",       "dataot",   "obstot",      "cod_spot",    "cdol_ot",    "vid_tr",     "id_pos");
		pdiagAmbTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Boolean.class, Date.class, Integer.class, Integer.class, String.class, Date.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class};
		
		rsmPdiagZ = new TResultSetMapper<>(PdiagZ.class, "id",           "npasp",       "diag",       "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "nmvd",        "xzab",        "stady",       "disp",        "pat",         "prizb",       "prizi",       "named",      "ppi",         "nameC00",    "id_diag_amb");
		pdiagZTypes = new Class<?>[] {                   Integer.class,  Integer.class, String.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class};
		
		
		rsmPsign = new TResultSetMapper<>(Psign.class, "npasp",       "grup",       "ph",         "allerg",     "farmkol",    "vitae",      "vred"       );
		psignTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPriem = new TResultSetMapper<>(Priem.class, "id_obr",      "npasp",       "id_pos",      "sl_ob",       "n_is",        "n_kons",      "n_proc",      "n_lek",       "t_chss",     "t_temp",     "t_ad_sist",  "t_rost",     "t_ves",      "t_st_localis", "t_ocenka",   "t_jalob",    "t_status_praesense", "t_fiz_obsl", "t_recom",   "t_ad_dist");
		priemTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class,   String.class, String.class, String.class,         String.class, String.class, String.class};
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
		
		rsmPislld = new TResultSetMapper<>(P_isl_ld.class, "nisl",        "npasp",       "cisl",        "pcisl",      "napravl",     "naprotd",     "datan",    "vrach",       "diag",       "dataz",    "pvizit_id",   "prichina",    "kodotd",      "datav",    "vopl",        "id_pos",     "datap",     "name_pcisl", "clpu");
		pislldTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, Date.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, String.class, Integer.class};
		
		rsmPrezd = new TResultSetMapper<>(Prez_d.class, "id",          "npasp",       "nisl",        "kodisl",     "rez");
		prezdTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, String.class};
		
		rsmPrezl = new TResultSetMapper<>(Prez_l.class, "id",          "npasp",       "nisl",        "cpok",       "rez");
		prezlTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, String.class};
		
		rsmMetod = new TResultSetMapper<>(Metod.class, "obst",       "name_obst",  "c_p0e1",      "pcod");
		metodTypes = new Class<?>[] {                  String.class, String.class, Integer.class, String.class};
		
		rsmPokazMet = new TResultSetMapper<>(PokazMet.class, "pcod",       "name_n");
		pokazMetTypes = new Class<?>[] {                     String.class, String.class};
		
		rsmPokaz = new TResultSetMapper<>(Pokaz.class, "pcod",       "name_n",     "stoim",      "c_p0e1",      "c_n_nz1");
		pokazTypes = new Class<?>[] {                  String.class, String.class, Double.class, Integer.class, String.class};
		
		rsmAnamZab = new TResultSetMapper<>(AnamZab.class, "id_pvizit",   "npasp",       "t_ist_zab");
		anamZabTypes = new Class<?>[] {                    Integer.class, Integer.class, String.class};
		
		rsmIsslInfo = new TResultSetMapper<>(IsslInfo.class, "nisl",        "cisl",        "name_cisl",  "pokaz",      "pokaz_name", "rez",        "datav",    "datan",    "id",          "op_name",    "rez_name",   "gruppa",      "kodotd",      "datap",    "diag");
		isslInfoTypes = new Class<?>[] {                     Integer.class, Integer.class, String.class, String.class, String.class, String.class, Date.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Date.class, String.class};
																																
		rsmPdisp = new TResultSetMapper<>(Pdisp.class, "npasp",       "id",          "diag",       "pcod",        "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "d_uch",       "diag_n");
		pdispTypes = new Class<?>[] {                  Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Integer.class, String.class};
	
		rsmRdSl = new TResultSetMapper<>(RdSlStruct.class, "id",          "npasp",       "datay",    "dataosl",  "abort",       "shet",        "datam",    "yavka1",      "ishod",       "datasn",   "datazs",   "kolrod",      "deti",        "kont",        "vesd",        "dsp",         "dsr",         "dtroch",      "cext",        "indsol",      "prmen",    "dataz",   "datasert",  "nsert",      "ssert",      "oslab",      "plrod",       "prrod",      "vozmen",      "oslrod",      "polj",        "dataab",   "srokab",      "cdiagt",      "cvera",       "id_pvizit",     "rost",       "eko",        "rub",        "predp",     "osp",        "cmer");
		rdSlTypes = new Class<?>[] {                       Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Boolean.class, Double.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,Boolean.class,Boolean.class,Boolean.class,Integer.class,Integer.class};

		rsmRdInf = new TResultSetMapper<>(RdInfStruct.class, "npasp",       "obr",        "sem",         "votec",       "grotec",     "photec",     "dataz",    "fiootec",    "mrotec",     "telotec",    "vredotec",    "osoco",       "uslpr",      "zotec");
		rdInfTypes = new Class<?>[] {                        Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, Date.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class,String.class};

		rsmRdDin = new TResultSetMapper<>(RdDinStruct.class, "id_rd_sl",    "id_pvizit",   "npasp",       "srok",       "grr",          "ball",        "oj",          "hdm",         "dspos",     "art1",         "art2",        "art3",        "art4",        "spl",         "oteki",       "chcc",        "polpl",       "predpl",      "serd",        "serd1",       "id_pos",      "ves" ,      "tonus");
		rdDinTypes = new Class<?>[] {                        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Double.class,Integer.class};
		
		rsmRdPat = new TResultSetMapper<>(RdPatient.class,"uid","npasp"      ,"fam"       ,"im"        ,"ot"        ,"datar"   ,"docser"    ,"docnum"    ,"tawn"       ,"street"    ,"house"     ,"flat"      ,"poms_ser"  ,"poms_nom"  ,"dog"       ,"stat"       ,"lpup"       ,"terp"       ,"ftawn"      ,"fstreet"   ,"fhouse"    ,"fflat"     ,"grk"       ,"rez"       ,"telm"      ,"vred"      ,"deti"       ,"datay"   ,"yavka1"     ,"datazs"  ,"famv"      ,"imv"       ,"otv"       ,"datasn"  ,"shet"       ,"kolrod"     ,"abort"      ,"vozmmen"    ,"prmen"      ,"datam"   ,"kont"       ,"dsp"        ,"dsr"        ,"dtroch"     ,"cext"       ,"indsol"     ,"vitae"     ,"allerg"    ,"ishod"      ,"prrod"     ,"oslrod"     ,"sem"        ,"rost"       ,"vesd"      ,"osoco"      ,"uslpr"      ,"dataz"   ,"polj"       ,"obr",       "fiootec",   "mrabotec",   "telotec",   "rgotec",   "photec",    "vredotec",   "vozotec",     "mrab",     "prof",       "eko",        "rub",        "predp",       "terpr",       "oblpr",      "diag",       "cvera",      "dataosl", "osp",       "zotec");
		rdPatientTypes = new Class<?>[]{          Integer.class,Integer.class,String.class,String.class,String.class,Date.class,String.class,String.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Date.class,Integer.class,Date.class,String.class,String.class,String.class,Date.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class,Boolean.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,String.class,String.class,Integer.class,String.class,Integer.class,Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Date.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Integer.class,String.class,String.class,Boolean.class,Boolean.class,Boolean.class, Integer.class, Integer.class,Integer.class,Integer.class,Date.class,Integer.class,String.class};
		
		rsmRdViz = new TResultSetMapper<>(RdVizit.class,"uid",         "dv",       "sp",        "famvr",     "imvr",      "otvr",     "diag",       "mso",         "rzp",         "aim",          "npr",       "npasp");
		rdVizitTypes = new Class<?>[]{                   Integer.class, Date.class,String.class,String.class,String.class,String.class,String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
	
		rsmRdCV = new TResultSetMapper<>(RdConVizit.class,"uiv",        "uid",       "ves",       "ned",        "lcad",        "ldad",       "rcad",       "rdad",       "rost",       "datar",    "obr",        "sem",          "ososo",       "vrpr" ,      "npasp",        "hdm",         "spl",         "oj",          "chcc",        "polpl",       "predpl",      "serd",        "serd1",       "oteki");
		rdConVizitTypes = new Class<?>[]{                 Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class };
		
		pnaprTypes = new Class<?>[] {                 Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, String.class};
	
		rsmPmer = new TResultSetMapper<>(Pmer.class, "id",           "npasp",       "diag",       "pmer",        "pdat",     "fdat",     "dataz",    "prichina",    "rez",         "cdol",        "dnl",      "dkl",      "lpu",         "ter",         "cpol",        "id_obr",     "cod_sp",       "cdol_n");
		pmerTypes = new Class<?>[] {                  Integer.class, Integer.class, String.class, Integer.class, Date.class, Date.class, Date.class, Integer.class, Integer.class, String.class,  Date.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class};

		rsmPobost = new TResultSetMapper<>(Pobost.class, "id",         "npasp",       "id_pdiag",    "diag",       "sl_obostr",   "sl_hron",     "cod_sp",      "cdol",       "dataz",    "id_obr");
		pobostTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class};

		rsmCgosp = new TResultSetMapper<>(Cgosp.class, "id",          "ngosp",        "npasp",      "nist",        "naprav",     "diag_n",     "named_n",     "dataz",   "vid_st",      "n_org",     "pl_extr",    "datap",     "vremp",    "cotd",        "diag_p",     "named_p",    "cotd_p",      "dataosm",  "vremosm");
		cgospTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, Date.class, Integer.class, Integer.class, Integer.class, Date.class, Time.class, Integer.class, String.class, String.class, Integer.class, Date.class, Time.class};
		
		rsmCotd = new TResultSetMapper<>(Cotd.class, "id",          "id_gosp",      "nist",       "cotd",         "dataz",   "stat_type");
		cotdTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class};
	}

	@Override
	public void testConnection() throws TException {
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new TException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new TException();
		}
	}

	@Override
	public void start() throws Exception {
		ThriftOsm.Processor<Iface> proc = new ThriftOsm.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public int getId() {
		return configuration.appId;
	}
	
	@Override
	public int getPort() {
		return configuration.thrPort;
	}
	
	@Override
	public String getName() {
		return configuration.appName;
	}
	
	@Override
	public List<ZapVr> getZapVr(int idvr, String cdol, long datap, int cpol) throws KmiacServerException, TException {
		String sql = "SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, tal.id_pvizit, NULL AS datap, pn.nuch, FALSE AS has_pvizit FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) LEFT JOIN p_vizit pv ON (pv.id = tal.id_pvizit) LEFT JOIN p_vizit_amb pa ON (pa.id_obr = pv.id) LEFT JOIN p_nambk pn ON (pat.npasp = pn.npasp AND pn.cpol = ?) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?) AND pa.id IS NULL " +
					 "UNION " +
					 "(SELECT DISTINCT ON (fam, im, ot) pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, pa.id_obr, pa.datap, pn.nuch, pa.datap = ? AS has_pvizit FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) LEFT JOIN p_vizit pv ON (pv.id = tal.id_pvizit) LEFT JOIN p_vizit_amb pa ON (pa.id_obr = pv.id) LEFT JOIN p_nambk pn ON (pat.npasp = pn.npasp AND pn.cpol = ?) WHERE pv.id IN (SELECT id_pvizit FROM e_talon ital WHERE (ital.pcod_sp = ?) AND (ital.cdol = ?) AND (ital.datap = ?) AND (ital.id_pvizit IS NOT NULL)) AND (pa.id_obr IS NOT NULL) " +
					 "ORDER BY fam, im, ot, datap DESC) " +
					 "ORDER BY has_pvizit, fam, im, ot, datap DESC ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, cpol, idvr, cdol, new Date(datap), new Date(datap), cpol, idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public ZapVr getZapVrSrc(int npasp) throws KmiacServerException, TException {
		String sql = "SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, pn.nuch FROM patient pat LEFT JOIN p_nambk pn ON (pat.npasp = pn.npasp) WHERE pat.npasp = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, npasp)) {
			acrs.getResultSet().next();
			return rsmZapVr.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<VrachInfo> getVrachList(int clpu, int cpodr) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT mr.id AS mrab_id, mr.cdol, s00.name AS cdol_name, vr.pcod, get_short_fio(vr.fam, vr.im, vr.ot) AS short_fio FROM s_mrab mr JOIN s_vrach vr ON (vr.pcod = mr.pcod) JOIN n_s00 s00 ON (s00.pcod = mr.cdol) JOIN n_priznd np ON (np.pcod = mr.priznd) WHERE (mr.clpu = ?) AND (mr.cpodr = ?) AND (mr.priznd = 1) ORDER BY vr.fam, vr.im, vr.ot, s00.name ", clpu, cpodr)) {
			return rsmVrachInfo.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public void AddPvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit (id, npasp, cpol, datao, cod_sp, cdol, cuser, dataz) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", false, obr, pvizitTypes, 0, 1, 2, 3, 7, 8, 9, 11);
			sme.execPrepared("INSERT INTO p_anam_zab (id_pvizit, npasp) VALUES (?, ?) ", false, obr.getId(), obr.getNpasp());
			sme.setCommit();
		} catch (SQLException e) {
			if (((SQLException) e.getCause()).getSQLState().equals("23505"))
				return;
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public Pvizit getPvizit(int obrId) throws PvizitNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT *, ishod > 0 AS closed FROM p_vizit WHERE id = ? ", obrId)) {
		if (acrs.getResultSet().next())
				return rsmPvizit.map(acrs.getResultSet());
			else
				throw new PvizitNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<Pvizit> getPvizitList(int npasp) throws KmiacServerException, TException {
		String sql = "SELECT pv.id, pv.datao, pv.ishod, TRUE AS has_pvizit, pv.ishod > 0 AS closed FROM patient pat LEFT JOIN p_vizit pv ON (pv.npasp = pat.npasp)  LEFT JOIN p_vizit_amb pa ON (pa.id_obr = pv.id) WHERE pv.id IN ( " +
					 "SELECT DISTINCT ipv.id FROM p_vizit ipv JOIN p_vizit_amb ipa ON (ipa.id_obr = ipv.id) LEFT JOIN e_talon tal ON (tal.id_pvizit = ipv.id) WHERE (ipv.npasp = ?) AND (ipv.datao BETWEEN (CURRENT_DATE - 365) AND CURRENT_DATE)) " +
					 "ORDER BY has_pvizit, id DESC, datao DESC ";	
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, npasp)) {
		List<Pvizit> pvizitList = rsmPvizit.mapToList(acrs.getResultSet());
		int prevIdObr = -1;
		
		for (int i = 0; i < pvizitList.size(); i++) {
			if (pvizitList.get(i).id != prevIdObr)
				prevIdObr = pvizitList.get(i).id;
			else
				pvizitList.remove(i--);
		}
		
		return pvizitList;
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
	}
	
	@Override
	public int AddPvizitId(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit (npasp, cpol, datao, cod_sp, cdol, cuser, dataz) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, obr, pvizitTypes, 1, 2, 3, 7, 8, 9, 11);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.execPrepared("INSERT INTO p_anam_zab (id_pvizit, npasp) VALUES (?, ?) ", false, id, obr.getNpasp());
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();	
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();	
		}
	}
	
	@Override
	public void UpdatePvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit SET datao = ?, ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ?, cobr = ?, lech = ? WHERE id = ?", false, obr, pvizitTypes, 3, 4, 5, 6, 10, 12, 11, 14, 13, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePvizit(int obrId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_vizit WHERE id = ? ", false, obrId);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit_amb (id_obr, npasp, datap, cod_sp, cdol, dataz, cpol, kod_ter) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", true, pos, pvizitAmbTypes, 1, 2, 3, 4, 5, 20, 22, 23);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.execPrepared("INSERT INTO p_priem (id_obr, npasp, id_pos) VALUES (?, ?, ?) ", false, pos.id_obr, pos.npasp, id);
			AddPrevPosPdiagAmb(sme, pos.id_obr, id);
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
			}
	}

	@Override
	public List<PvizitAmb> getPvizitAmb(int obrId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT pva.*, get_short_fio(svr.fam, svr.im, svr.ot) AS fio_vr, s00.name AS cdol_name FROM p_vizit_amb pva JOIN s_vrach svr ON (svr.pcod = pva.cod_sp) JOIN n_s00 s00 ON (s00.pcod = pva.cdol) WHERE id_obr = ? ORDER BY pva.datap DESC", obrId)) {
			return rsmPvizitAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit_amb SET id_obr = ?, npasp = ?, datap = ?, cod_sp = ?, cdol = ?, mobs = ?, rezult = ?, opl = ?, stoim = ?, uet = ?, k_lr = ?, n_sp = ?, pr_opl = ?, pl_extr = ?, vpom = ?, cpos = ?, cpol = ?, kod_ter = ?, diag = ? WHERE id = ? ", false, pos, pvizitAmbTypes, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 16, 17, 18, 21, 22, 23, 6, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePvizitAmb(int posId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_vizit_amb WHERE id = ? ", false, posId);
			sme.execPrepared("DELETE FROM p_diag_amb WHERE id_pos = ? ", false, posId);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public void DeletePvizitAmbObr(int obrId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_din WHERE id_pvizit = ? ", false, obrId);
			sme.execPrepared("DELETE FROM p_priem WHERE id_obr = ? ", false, obrId);
			sme.execPrepared("DELETE FROM p_vizit_amb WHERE id_obr = ? ", false, obrId);
			sme.execPrepared("UPDATE e_talon SET npasp = NULL, dataz = NULL, prv = 0, id_pvizit = NULL WHERE (id_pvizit = ?) AND (datap > CURRENT_DATE) ", false, obrId);
			sme.execPrepared("DELETE FROM p_diag_amb WHERE id_obr = ? ", false, obrId);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public int AddPdiagAmb(PdiagAmb diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_diag_amb (id_obr, npasp, diag, named, diag_stat, predv, datad, obstreg, cod_sp, cdol, dataot, obstot, cod_spot, cdol_ot, vid_tr, id_pos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, diag, pdiagAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	public void AddPrevPosPdiagAmb(SqlModifyExecutor sme, int obrId, int posId) throws SQLException {
		String sql = "WITH t AS (SELECT pv.id AS id_obr, pa.id AS id_pos FROM p_vizit pv JOIN p_vizit_amb pa ON (pa.id_obr = pv.id) WHERE (pv.id = ?) ORDER BY pa.datap DESC, pa.id DESC LIMIT 1) " +
			"SELECT da.id FROM p_vizit pv JOIN p_vizit_amb pa ON (pa.id_obr = pv.id) JOIN t ON (t.id_obr = pv.id AND t.id_pos = pa.id) JOIN p_diag_amb da ON (da.id_obr = pv.id AND da.id_pos = pa.id) WHERE (pv.id = t.id_obr) AND (pa.id = t.id_pos) AND (da.diag_stat = 3) "; 
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery(sql, obrId)) {
			sql = "WITH t AS (SELECT id_obr, npasp, diag, named, diag_stat, predv, datad, obstreg, cod_sp, cdol, dataot, obstot, cod_spot, cdol_ot, vid_tr FROM p_diag_amb WHERE id = ?) " +
				"INSERT INTO p_diag_amb (id_obr, npasp, diag, named, diag_stat, predv, datad, obstreg, cod_sp, cdol, dataot, obstot, cod_spot, cdol_ot, vid_tr, id_pos) SELECT *, ? FROM t ";
			while (acrs.getResultSet().next()) {
				sme.execPrepared(sql, false, acrs.getResultSet().getInt(1), posId);
			}
		}
	}
	
	@Override
	public List<PdiagAmb> getPdiagAmb(int obrId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_pos = ? ", obrId)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePdiagAmb(PdiagAmb diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_diag_amb SET id_obr = ?, npasp = ?, diag = ?, named = ?, diag_stat = ?, predv = ?, datad = ?, obstreg = ?, cod_sp = ?, cdol = ?, dataot = ?, obstot = ?, cod_spot = ?, cdol_ot = ?, vid_tr = ?, id_pos = ? WHERE id = ? ", false, diag, pdiagAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePdiagAmb(int diagId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_diag_amb WHERE id = ? ", false, diagId);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public void DeletePdiagAmbVizit(int idObr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_diag_amb WHERE id_obr = ? ", false, idObr);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public Psign getPsign(int npasp) throws KmiacServerException, PsignNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_sign WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPsign.map(acrs.getResultSet());
			else
				throw new PsignNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void setPsign(Psign sign) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPsign(sign.npasp);
				sme.execPreparedT("UPDATE p_sign SET grup = ?, ph = ?, allerg = ?, farmkol = ?, vitae = ?, vred = ? WHERE npasp = ? ", false, sign, psignTypes,	1, 2, 3, 4, 5, 6, 0);
				sme.setCommit();
			} catch (PsignNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_sign (npasp, grup, ph, allerg, farmkol, vitae, vred) VALUES (?, ?, ?, ?, ?, ?, ?) ", false, sign, psignTypes, 0, 1, 2, 3, 4, 5, 6);
				sme.setCommit();
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public Priem getPriem(int npasp, int posId) throws KmiacServerException, PriemNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_priem WHERE npasp = ? AND id_pos = ? ", npasp, posId)) {
			if (acrs.getResultSet().next())
				return rsmPriem.map(acrs.getResultSet());
			else
				throw new PriemNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void setPriem(Priem pr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_priem SET sl_ob = ?, n_is = ?, n_kons = ?, n_proc = ?, n_lek = ?, t_chss = ?, t_temp = ?, t_ad_sist = ?, t_rost = ?, t_ves = ?, t_st_localis = ?, t_ocenka = ?, t_jalob = ?, t_status_praesense = ?, t_fiz_obsl = ?, t_recom = ?, t_ad_dist = ? WHERE id_obr = ? AND npasp = ? AND id_pos = ? ", false, pr, priemTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 0, 1, 2);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<StringClassifier> get_n_nz1(int cotd) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT n_nz1.pcod, n_nz1.name FROM n_nz1 JOIN s_ot01 ON (n_nz1.pcod=s_ot01.c_nz1) WHERE s_ot01.cotd = ? ", cotd)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
//	
//	@Override
//	public List<RdDinStruct> getRdDinInfo(int id_pvizit, int npasp) throws KmiacServerException, TException {
//		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select d.*,v.datap from p_rd_din d JOIN p_vizit_amb v on (d.id_pos = v.id) where d.id_pvizit = ? and d.npasp = ? order by v.datap", id_pvizit, npasp)) {
//			return rsmRdDin.mapToList(acrs.getResultSet());
//		} catch (SQLException e) {
//			((SQLException) e.getCause()).printStackTrace();
//			throw new KmiacServerException();
//		}
//
//	}

	@Override
	public RdInfStruct getRdInfInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rd_inf where npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmRdInf.map(acrs.getResultSet());
			else
				throw new KmiacServerException("rd inf not found");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public RdSlStruct getRdSlInfo(int id_pvizit, int npasp) throws PrdslNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where id_pvizit = ? and npasp = ? ", id_pvizit, npasp)) {
			if (acrs.getResultSet().next())
				return rsmRdSl.map(acrs.getResultSet());
			else
				throw new PrdslNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddRdSl(RdSlStruct rdSl) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_rd_sl (npasp,datay,dataosl,abort,shet,datam,yavka1,ishod,datasn,datazs,kolrod,deti,kont,vesd,dsp,dsr,dtroch,cext,indsol,prmen,dataz,datasert,nsert,ssert,oslab,plrod,prrod,vozmen,oslrod,polj,dataab,srokab,cdiagt,cvera,id_pvizit,rost,eko,rub,predp,osp,cmer) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", true, rdSl, rdSlTypes,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void AddRdDin(RdDinStruct RdDin) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rd_din (id_pvizit,  npasp,  srok, oj,  hdm, grr, ball,  dspos,  art1,  art2, art3, art4,  spl,   oteki, chcc, polpl, predpl, serd,  serd1, id_pos,ves) VALUES (?, ?,  ?,  ?, ?,  ?, ?,  ?,  ?, ?,  ?, ?, ?,  ?, ?,  ?, ?, ?, ?, ? ,?) ", false, RdDin, rdDinTypes,  1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public void DeleteRdSl(int id_pvizit, int npasp) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_sl WHERE id_pvizit = ? and npasp = ?", false, id_pvizit, npasp);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}
	
	@Override
	public void UpdateRdSl(RdSlStruct Dispb) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_sl SET npasp = ?, datay = ?, dataosl = ?, abort = ?, shet = ?, datam = ?, yavka1 = ?, ishod = ?,datasn = ?, datazs = ?,kolrod = ?, deti = ?, kont = ?, vesd = ?, dsp = ?,dsr = ?,dtroch = ?, cext = ?, indsol = ?, prmen = ?,dataz = ?, datasert = ?, nsert = ?, ssert = ?, oslab = ?, plrod = ?, prrod = ?, vozmen = ?, oslrod = ?, polj = ?, dataab = ?, srokab = ?, cdiagt = ?, cvera = ?, rost = ?,eko =?, rub = ?, predp = ?, osp = ?, cmer = ?  WHERE id_pvizit = ?", false, Dispb, rdSlTypes, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,36,37,38,39,40,41, 35);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void UpdateRdDin(RdDinStruct din) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_din SET npasp = ?, srok = ?, grr = ?, ball = ?, oj = ?, hdm = ?, dspos = ?, art1 = ?, art2 = ?, art3 = ?, art4 = ?, spl = ?, oteki = ?, chcc = ?, polpl = ?, predpl = ?, serd = ?, serd1 = ?, ves = ?, tonus = ?  WHERE id_pos = ?", false, din, rdDinTypes, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,22,20);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}


	@Override
	public void AddRdInf(RdInfStruct rdInf) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rd_inf (npasp, dataz) VALUES (?, ?) ", false, rdInf, rdInfTypes, 0, 6);
			sme.setCommit();
		} catch (SQLException e) {
			if (((SQLException)e.getCause()).getSQLState().equals("23505"))
				return;
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	
	}

	@Override
	public void DeleteRdInf(int npasp) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_inf WHERE npasp = ? ", false, npasp);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	
	}

	@Override
	public void UpdateRdInf(RdInfStruct inf) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_inf SET obr = ?,sem = ?, votec = ?, grotec = ?, photec = ?, dataz = ?, fiootec = ?, mrotec = ?, telotec = ?, vredotec = ?, osoco = ?, uslpr = ?,zotec = ? WHERE npasp = ?", false, inf, rdInfTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<Metod> getMetod(int kodissl) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT np.pcod AS c_p0e1, no.obst, no.nameobst AS name_obst " +
					"FROM n_nsi_obst no JOIN n_stoim ns ON (ns.c_obst = no.obst) JOIN n_p0e1 np ON (np.pcod = ns.c_p0e1) " +
					"WHERE np.pcod = ? " +
					"ORDER BY np.pcod, no.obst ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, kodissl)) {
			return rsmMetod.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}



	@Override
	public List<Pokaz> getPokaz(int kodissl, String kodsyst) throws KmiacServerException, TException {
		String sql = "SELECT np.pcod AS c_p0e1, nz.pcod AS c_n_nz1, nl.name_n, ns.pcod, ns.stoim " +  
					"FROM n_stoim ns JOIN n_ldi nl ON (nl.pcod = ns.pcod) JOIN n_nz1 nz ON (nz.pcod = nl.c_nz1) JOIN n_p0e1 np ON (np.pcod = ns.c_p0e1) " + 
					"WHERE np.pcod = ? AND nz.pcod = ? " + 
					"ORDER BY np.pcod, nz.pcod ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, kodissl, kodsyst)) {
			return rsmPokaz.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPisl(P_isl_ld npisl) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_isl_ld WHERE (npasp = ?) AND (pvizit_id = ?) AND (id_pos = ?) AND (naprotd = ?) AND (pcisl = ?) AND (diag = ?) AND (kodotd = ?) ", npisl.npasp, npisl.pvizit_id, npisl.id_pos, npisl.naprotd, npisl.pcisl, npisl.diag, npisl.kodotd)) {
			if (acrs.getResultSet().next()) {
				return acrs.getResultSet().getInt("nisl");
			} else {
				sme.execPreparedT("INSERT INTO p_isl_ld (npasp, cisl, pcisl, napravl, naprotd, datan, vrach, diag, dataz, pvizit_id, prichina, kodotd, vopl, id_pos, datap) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, npisl, pislldTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16);
				sme.setCommit();
				return sme.getGeneratedKeys().getInt("nisl");
			}
		} catch (SQLException e) {
			
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPrezd(Prez_d di) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_d WHERE (npasp = ?) AND (nisl = ?) AND (kodisl = ?) ", di.npasp, di.nisl, di.kodisl)) {
			if (acrs.getResultSet().next()) {
				return acrs.getResultSet().getInt("id");
			} else {
				sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl) VALUES (?, ?, ?) ", true, di, prezdTypes, 1, 2, 3);
				sme.setCommit();
				return sme.getGeneratedKeys().getInt("id");
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPrezl(Prez_l li) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_rez_l WHERE (npasp = ?) AND (nisl = ?) AND (cpok = ?) ", li.npasp, li.nisl, li.cpok)) {
			if (acrs.getResultSet().next()) {
				return acrs.getResultSet().getInt("id");
			} else {
				sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok) VALUES (?, ?, ?) ", true, li, prezlTypes, 1, 2, 3);
				sme.setCommit();
				return sme.getGeneratedKeys().getInt("id");
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	//После двух дней мозгойопства с либре офисом выявлено следующее:
	//высоту для таблиц необходимо выставлять в пикселях
	//для корректного отображения вложенных таблиц необходимо
	//в каждом тэге <td> перед любыми данными ставить неразрывный пробел &nbsp;
	//для работы тэга @page нельзя в таблицах использовать стиль
	//style="page-break-after: auto" и ему подобные
	@Override
	public String printIsslMetod(IsslMet im) throws KmiacServerException, TException {
		String path;
		StringBuilder sb;
		List<String> mts;
		int kod_lab = 0, id_issl = 0, napr_vr = 0, kod_lab_pol = 0;
		Date data_napr = null, data_post  =null;
		String diag = null, name_clpu = null, name_lab = null, vrInfo = null;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet oAcrs;
			AutoCloseableResultSet iAcrs;
			
			mts = new ArrayList<>();
			oAcrs = sse.execPreparedQuery("select nisl, kodotd, diag, datan, datap, vrach from p_isl_ld where id_pos = ? ", im.getPvizitambId());
			while (oAcrs.getResultSet().next()) {
				kod_lab = oAcrs.getResultSet().getInt(2);
				id_issl = oAcrs.getResultSet().getInt(1);
				diag = oAcrs.getResultSet().getString(3);
				data_napr = oAcrs.getResultSet().getDate(4);
				data_post = oAcrs.getResultSet().getDate(5);
				napr_vr = oAcrs.getResultSet().getInt(6);
				
				sb = new StringBuilder(0x10000);
				
				sb.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"460\">");
				sb.append("<tr valign=\"top\">");
					sb.append("<td width=\"50%\" style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: none; padding: 5px; font: 11px times new roman;\">");
						sb.append("&nbsp;");
						sb.append("<b>Информация для пациента:</b><br><br>");
						
						iAcrs = sse.execPreparedQuery("select n_m00.name_s, n_lds.name from n_lds inner join n_m00 on (n_m00.pcod=n_lds.clpu) where n_lds.pcod = ? ", kod_lab);
						if (iAcrs.getResultSet().next()) {
							name_clpu = iAcrs.getResultSet().getString(1);
							name_lab = iAcrs.getResultSet().getString(2);
						}
						sb.append("<b>Дата:</b><br>");
						sb.append("<b>Время:</b><br>");
						sb.append("<b>Подготовка:</b><br>");
					sb.append("</td>");
					
					iAcrs.close();
					iAcrs = sse.execPreparedQuery("SELECT v.fam, v.im, v.ot FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) WHERE v.pcod = ? ", napr_vr);
					if (iAcrs.getResultSet().next()) {
						vrInfo = String.format("%s %s %s", iAcrs.getResultSet().getString(1), iAcrs.getResultSet().getString(2), iAcrs.getResultSet().getString(3));
						sb.append("<td width=\"50%\" style=\"border: 1px solid black; padding: 5px; font: 11px times new roman;\">");
						sb.append("&nbsp;");
						sb.append(String.format("<b>%s</b><br><br>", name_clpu));
						sb.append("<b>Направление на исследование</b><br><br>");
						sb.append(String.format("<b>Лаборатория: %s</b><br><br>", name_lab));
					}
					iAcrs.close();
					
					iAcrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom, poms_ser, poms_nom FROM patient WHERE npasp = ? ", im.getNpasp());
					if (iAcrs.getResultSet().next()) {
						sb.append(String.format("<b>ФИО пациента:</b> %s %s %s<br>", iAcrs.getResultSet().getString(1), iAcrs.getResultSet().getString(2), iAcrs.getResultSet().getString(3)));
						if (iAcrs.getResultSet().getString(7)==null)sb.append(String.format("<b>Серия и номер полиса:</b> %s <br>", iAcrs.getResultSet().getString(8)));
						if (iAcrs.getResultSet().getString(8)==null)sb.append(String.format("<b>Серия и номер полиса:</b> %s <br>", iAcrs.getResultSet().getString(7)));
						if ((iAcrs.getResultSet().getString(8)!=null) && (iAcrs.getResultSet().getString(7)!=null)) sb.append(String.format("<b>Серия и номер полиса:</b> %s %s<br>", iAcrs.getResultSet().getString(7), iAcrs.getResultSet().getString(8)));
						sb.append(String.format("<b>Дата рождения:</b> %1$td.%1$tm.%1$tY<br>", iAcrs.getResultSet().getDate(4)));
						if (iAcrs.getResultSet().getString(5)!=null)
						sb.append(String.format("<b>Адрес:</b> %s, %s<br>", iAcrs.getResultSet().getString(5), iAcrs.getResultSet().getString(6)));
					}
					sb.append("<b>Диагноз: </b>");
					sb.append(String.format("%s <br>", diag));
					sb.append(String.format("<b>Врач:</b> %s<br><br>", vrInfo));
					sb.append("<b>Наименование исследований:</b>");
					sb.append("<ol>");
					iAcrs.close();
					
					iAcrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav, p_vizit.datao " +
							"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok)  " +
							"join p_vizit on (p_vizit.id = p_isl_ld.pvizit_id) "+
							"where p_isl_ld.nisl = ? " +
							"union " +
							"select p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav, p_vizit.datao " +
							"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)  " +
							"join p_vizit on (p_vizit.id = p_isl_ld.pvizit_id) "+
							"where p_isl_ld.nisl = ? ", id_issl, id_issl);
					while (iAcrs.getResultSet().next()) {
						if (iAcrs.getResultSet().getString(3) != null) {
							do {
								sb.append(String.format("<li>%s</li>", iAcrs.getResultSet().getString(3)));
							} while (iAcrs.getResultSet().next());
						}
					}	

					sb.append("</ol>");
					sb.append(String.format("<b>Дата направления:</b> %1$td.%1$tm.%1$tY<br>", data_napr));
					if (data_post!=null) sb.append(String.format("<b>Планируемая дата выполнения:</b> %1$td.%1$tm.%1$tY<br>", data_post));
					sb.append("<b>Подпись врача:</b><br>");
					sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");
				iAcrs.close();
				
				mts.add(sb.toString());
			};
			oAcrs.close();
			
			sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Направление на…</title>");
			sb.append("<style type=\"text/css\">");
			sb.append("@page { size: 21cm 29.7cm; margin: 1cm }");
			sb.append("</style>");
			sb.append("</head>");
			sb.append("<body>");
			
			for (int i = 0; i < mts.size(); ) {
				sb.append("<table cellpadding=\"5\" cellspacing=\"8\" width=\"100%\" height=\"460\">");
				for (int j = 0; (j < 1) && (i != mts.size()); j++) {
					sb.append("<tr>");
					for (int k = 0; k < 2; k++) {
						sb.append("<td width=\"50%\" style=\"border: none; padding: 0cm\"><br>");
						sb.append("&nbsp;");
						if (i < mts.size())
							sb.append(mts.get(i++));
						sb.append("</td>");
					}
					sb.append("</tr>");
				}

				sb.append("</table>");
			}

			
			sb.append("</body>");
			sb.append("</html>");
			
			osw.write(sb.toString());
			
			return path;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Deprecated
	@Override
	public String printIsslPokaz(IsslPokaz ip) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\NaprIsslPokaz.htm"), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Направление на…</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<div>");
			
			sb.append("<table cellpadding=\"5\" cellspacing=\"0\">");
			sb.append("<tr valign=\"top\">");
				sb.append("<td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: none; padding: 5px;\" width=\"40%\">");
					sb.append("<h3>Информация для пациента:</h3>");
					if (ip.getMesto()!=null)sb.append(String.format("<b>Место: </b>%s<br />", ip.getMesto()));
					if (ip.getKab()!=null)sb.append(String.format("<b>Каб. №: </b>%s<br />", ip.getKab()));
					sb.append("<b>Дата:</b><br />");
					sb.append("<b>Время:</b><br />");
					sb.append("<b>Подготовка:</b><br />");
				sb.append("</td>");
				acrs = sse.execPreparedQuery("SELECT v.fam, v.im, v.ot FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) WHERE u.id = ? ", ip.getUserId());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");
				sb.append("<td style=\"border: 1px solid black; padding: 5px;\" width=\"60%\">");
					sb.append(String.format("<h3>%s<br />", ip.getClpu_name()));
					sb.append(String.format("%s<br />", ip.getCpodr_name()));
					String vrInfo = String.format("%s %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3));
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT name FROM n_p0e1 WHERE pcod = ?", ip.getKodVidIssl());
					if (!acrs.getResultSet().next())
						throw new KmiacServerException("Exam info info not found.");
					sb.append(String.format("Направление на: %s</h3>", acrs.getResultSet().getString(1)));
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom FROM patient WHERE npasp = ? ", ip.getNpasp());
					if (!acrs.getResultSet().next())
						throw new KmiacServerException("Logged user info not found.");
					sb.append(String.format("<b>ФИО пациента:</b> %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("<b>Дата рождения:</b> %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
					sb.append(String.format("<b>Адрес:</b> %s, %s<br />", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
					sb.append("<b>Диагноз:</b><br />");
					acrs.close();
					acrs = sse.execPreparedQuery("select p_diag_amb.diag from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=1 and p_diag_amb.predv=false order by p_vizit_amb.datap", ip.getPvizitId());
					if (!acrs.getResultSet().next()) 
						throw new KmiacServerException("Diag is null");
					sb.append(String.format("%s <br>", acrs.getResultSet().getString(1)));
					sb.append(String.format("<b>Врач:</b> %s<br />", vrInfo));
					sb.append("<h3>Наименование показателей:</h3>");
					sb.append("<ol>");
					for (String str : ip.getPokaz()) {
						acrs.close();
						acrs = sse.execPreparedQuery("SELECT name_n FROM n_ldi WHERE pcod = ? ", str);
						if (!acrs.getResultSet().next())
							throw new KmiacServerException("Mark info info not found.");
						sb.append(String.format("<li>%s</li>", acrs.getResultSet().getString(1)));
					}
					sb.append("</ol>");
					sb.append(String.format("<b>Дата направления:</b> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
					sb.append("<b>Подпись врача:</b><br />");
				sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			
			sb.append("</div>");
			sb.append("</body>");
			sb.append("</html>");
			
			acrs.close();
			osw.write(sb.toString());
			return "e:\\NaprIsslPokaz.htm";
		} catch (SQLException | IOException | KmiacServerException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_lds(int clpu) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_lds WHERE clpu = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_m00() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT m.pcod, m.name FROM n_m00 m JOIN n_lds l ON (m.pcod = l.clpu) WHERE m.pcod != ? ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_lds_n_m00(int clpu) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT l.pcod,l.name FROM n_m00 m JOIN n_lds l ON (m.pcod = l.clpu) WHERE m.pcod = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public String printNapr(Napr na) throws KmiacServerException, TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("napr", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Направление</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<div align=\"right\"><font size=2 color=black>Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</font></div>");
				sb.append("<br>	<div style=\"width:220px; float:left;\"><font size=2 color=black>Министерство здравоохранения <br> Российской Федерации</font><br>");
				sb.append(String.format("<br><font size=2 color=black>%s, %s</font>", na.getCpodr_name(), na.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"width:150px; float:right;\"><font size=2 color=black>Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</font></div>");
				sb.append("<br><br><br><br><br><br><br><br>");
				sb.append("<h4 align=center>Направление <br>");
				sb.append("на госпитализацию</h4>");
				if (na.getClpu()!=null) sb.append(String.format("<font size=2 color=black> Куда: %s</font>", na.getClpu()));
			 	else sb.append("<font size=2 color=black>  Куда: _________________________________________________________</font>" );
			 	sb.append("<br>");
			 	sb.append("<font size=2 color=black>1. Номер страхового полиса ОМС: " );
				acrs = sse.execPreparedQuery("SELECT poms_nom FROM patient WHERE npasp = ? ", na.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
		
			sb.append("<br>2. Код льготы: ");
			acrs.close();
				acrs = sse.execPreparedQuery("SELECT lgot FROM p_kov WHERE npasp = ? ", na.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT patient.fam, patient.im, patient.ot, patient.datar, patient.adm_ul, patient.adm_dom, patient.adm_kv, n_z43.name, patient.prof FROM patient join n_z43 on (patient.mrab=n_z43.pcod) where patient.npasp= ? ", na.getNpasp());
				if (acrs.getResultSet().next()){
					sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
					if (acrs.getResultSet().getString(5)!=null) sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
					sb.append(String.format("<br>6. Место работы/учебы: %s ",acrs.getResultSet().getString(8)));
			if (acrs.getResultSet().getString(9)!=null) sb.append(String.format(", должность: %s ",acrs.getResultSet().getString(9)));
			}
			sb.append(String.format("<br>7. Код диагноза по МКБ: %s", na.diag));
//			acrs.close();
//			acrs = sse.execPreparedQuery("select p_diag_amb.diag from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=1 and p_diag_amb.predv=false order by p_vizit_amb.datap", na.getPvizitId());
//			if (acrs.getResultSet().next()) 
//			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
			if (na.getObosnov()!=null) sb.append(String.format("<br>8. Обоснование направления: %s",na.getObosnov()));
			else sb.append("<br>8. Обоснование направления: __________________________________________________");
			sb.append("<br>Должность медицинского работника, направившего больного: ");
			acrs.close();
			acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
					  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
					  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",na.getUserId());
								if (acrs.getResultSet().next())
								sb.append(String.format("%s ", acrs.getResultSet().getString(4)));
								sb.append(String.format("<br>ФИО: %s %s %s", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
			sb.append(" Подпись_______________");
			sb.append("<br>Заведующий отделением_____________________________________________________________________________");
			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			sb.append("<br>МП</font>");
			acrs.close();
							osw.write(sb.toString());
							return path;
						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						} catch (IOException e) {
							e.printStackTrace();
							throw new KmiacServerException();
						}

	}

	@Override
	public String printNaprKons(NaprKons nk) throws KmiacServerException, TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("napk", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Направление</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<div align=\"right\"><font size=2 color=black>Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</font></div>");
				sb.append("<br>	<div style=\"width:220px; float:left;\"><font size=2 color=black>Министерство здравоохранения <br> Российской Федерации</font><br>");
				sb.append(String.format("<br><font size=2 color=black>%s, %s</font>", nk.getCpodr_name(), nk.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"width:150px; float:right;\"><font size=2 color=black>Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</font></div>");
				sb.append("<br><br><br><br><br><br><br><br>");
				sb.append("<h4 align=center>Направление <br>");
				sb.append("на консультацию</h4>");
				if (nk.getCpol()!=null) sb.append(String.format("<font size=2 color=black> Куда: %s</font>", nk.getCpol()));
			 	else sb.append("<font size=2 color=black>  Куда: _________________________________________________________</font>" );
			 	sb.append("<br>");
			 	sb.append("<font size=2 color=black>1. Номер страхового полиса ОМС: " );
				acrs = sse.execPreparedQuery("SELECT poms_nom FROM patient WHERE npasp = ? ", nk.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
		
			sb.append("<br>2. Код льготы: ");
			acrs.close();
				acrs = sse.execPreparedQuery("SELECT lgot FROM p_kov WHERE npasp = ? ", nk.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT patient.fam, patient.im, patient.ot, patient.datar, patient.adm_ul, patient.adm_dom, patient.adm_kv, n_z43.name, patient.prof FROM patient join n_z43 on (patient.mrab=n_z43.pcod) where patient.npasp= ? ", nk.getNpasp());
				if (acrs.getResultSet().next()){
					sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
					if (acrs.getResultSet().getString(5)!=null) sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
					sb.append(String.format("<br>6. Место работы/учебы: %s ",acrs.getResultSet().getString(8)));
			if (acrs.getResultSet().getString(9)!=null) sb.append(String.format(", должность: %s ",acrs.getResultSet().getString(9)));
			}
				sb.append(String.format("<br>7. Код диагноза по МКБ: %s", nk.diag));
//			sb.append("<br>7. Код диагноза по МКБ: ");
//			acrs.close();
//			acrs = sse.execPreparedQuery("select p_diag_amb.diag from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=1 and p_diag_amb.predv=false order by p_vizit_amb.datap", nk.getPvizitId());
//			if (acrs.getResultSet().next()) 
//			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
			if (nk.getObosnov()!=null) sb.append(String.format("<br>8. Обоснование направления: %s",nk.getObosnov()));
			else sb.append("<br>8. Обоснование направления: __________________________________________________");
			sb.append("<br>Должность медицинского работника, направившего больного: ");
			acrs.close();
			acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",nk.getUserId());
			if (acrs.getResultSet().next())
			sb.append(String.format("%s ", acrs.getResultSet().getString(4)));
			sb.append(String.format("<br>ФИО: %s %s %s", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
			sb.append(" Подпись_______________");
			sb.append("<br>Заведующий отделением_____________________________________________________________________________");
			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			sb.append("<br>МП</font>");
			acrs.close();
							osw.write(sb.toString());
							return path;
						} catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						} catch (IOException e) {
							e.printStackTrace();
							throw new KmiacServerException();
						}
	}

	@Override
	public String printVypis(Vypis vp) throws KmiacServerException, TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("vypis", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Выписка из медицинской карты амбулаторного больного</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<div align=\"right\">Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</div>");
				sb.append("<br>	<div style=\"width:240px; float:left;\">Министерство здравоохранения <br>Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", vp.getClpu_name(), vp.getCpodr_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"width:150px; float:right;\">Медицинская документация<br>Форма № 027/у<br> Утверждена Минздравом СССР<br>04.10.80 г. № 1090</div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h3 align=center>ВЫПИСКА</h3>");
				sb.append("<h4 align=center>из медицинской карты амбулаторного больного</h4><br>в _____________________________________________________________");//пока черта, потому что в табл.patient поле mrab и спр.z43.pcod разные типы данных.дб одинаковые
				sb.append("<br><div align=\"left\"><sub>название и адрес учреждения, куда направляется выписка</sub></div><br><br>");
				acrs = sse.execPreparedQuery("SELECT patient.fam, patient.im, patient.ot, patient.datar, patient.adm_ul, patient.adm_dom, patient.adm_kv, n_z43.name, patient.prof FROM patient join n_z43 on (patient.mrab=n_z43.pcod) where patient.npasp=?", vp.getNpasp());
				if (acrs.getResultSet().next()) {
				sb.append(String.format("1. Ф.И.О.</b> %s  %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
				sb.append(String.format("<br>2. Дата рождения:  %1$td.%1$tm.%1$tY<br>", acrs.getResultSet().getDate(4)));
				sb.append(String.format("3. Домашний адрес %s  %s-%s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6), acrs.getResultSet().getString(7)));
				if (acrs.getResultSet().getString(8)!=null) 
				sb.append(String.format("<br>4. Место работы/учебы: %s ", acrs.getResultSet().getString(8)));
				if (acrs.getResultSet().getString(9)!=null) 
				sb.append(String.format(",род занятий: %s ", acrs.getResultSet().getString(9)));
				acrs.close();
				}
				sb.append("<br>5. Даты: по амбулатории: заболевания ");
				acrs.close();
				acrs = sse.execPreparedQuery("select datap from p_vizit join p_vizit_amb on (p_vizit.id=p_vizit_amb.id_obr) where p_vizit.id=? order by datap", vp.getPvizit_id());
				acrs.getResultSet().next();
				Date tmpDate = acrs.getResultSet().getDate(1);
				sb.append(String.format("%1$td.%1$tm.%1$tY - ", tmpDate));
				while (acrs.getResultSet().next())
					tmpDate = acrs.getResultSet().getDate(1);
				sb.append(String.format("%1$td.%1$tm.%1$tY", tmpDate));
				sb.append("<br>6. Полный диагноз: <br>");
				sb.append("основное заболевание ");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=1 and p_diag_amb.predv=false order by p_vizit_amb.datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null)sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
					}				
				sb.append("<br>осложнение основного заболевания <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=2 and p_diag_amb.predv=false order by p_vizit_amb.datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}				
				sb.append("<br>сопутствующее заболевание <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=3 and p_diag_amb.predv=false order by p_vizit_amb.datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}
				sb.append("<br>	7. Краткий анамнез, диагностические исследования, течение болезни<br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", vp.getPvizit_id()); 
				if (acrs.getResultSet().next()) {
				if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(1)));
}

//				acrs.close();
//				acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_p0e1.pcod , n_p0e1.name , n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav " +
//					"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) join n_p0e1  on (n_p0e1.pcod = p_isl_ld.cisl) " +
//					"where p_isl_ld.pvizit_id = ? " +
//					"union " +
//					"select p_isl_ld.nisl, n_p0e1.pcod, n_p0e1.name , n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav " +
//					"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) join n_arez  on (n_arez.pcod = p_rez_d.rez) join n_p0e1 on (n_p0e1.pcod = p_isl_ld.cisl) " +
//					"where p_isl_ld.pvizit_id = ? ", vp.getPvizit_id(), vp.getPvizit_id());
//				while (acrs.getResultSet().next()){
//					sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
//	}			
//
//acrs.close();
	acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav, p_vizit.datao " +
		"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok)  " +
		"join p_vizit on (p_vizit.id = p_isl_ld.pvizit_id) "+
		"where p_isl_ld.pvizit_id = ? " +
		"union " +
		"select p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav, p_vizit.datao " +
		"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)  " +
		"join p_vizit on (p_vizit.id = p_isl_ld.pvizit_id) "+
		"where p_isl_ld.pvizit_id = ? ", vp.getPvizit_id(), vp.getPvizit_id());
	if (acrs.getResultSet().isBeforeFirst()) {
		sb.append("<br><br><b>Назначенные исследования </b><br>");
		while (acrs.getResultSet().next()) {
			if (acrs.getResultSet().getString(4) != null) {
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\">Код</th><th>Наименование показателя</th><th>Результат</th></tr>");
			do {
				sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\"> %s </th><th style=\"font: 12px times new roman;\"> %s </th><th style=\"font: 12px times new roman;\"> %s </th></tr>", acrs.getResultSet().getString(2), acrs.getResultSet().getString(3), acrs.getResultSet().getString(4)));
			} 
			while (acrs.getResultSet().next());
			}
			else {
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\">Код</th><th>Наименование показателя</th><th>Результат</th></tr>");
			do {
				sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 12px times new roman;\"> %s </th><th style=\"font: 12px times new roman;\"> %s </th><th style=\"font: 12px times new roman;\">  </th></tr>", acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			} 
			while (acrs.getResultSet().next());
			}
			}		sb.append("</table><br>");	
	}
	acrs.close();
				acrs = sse.execPreparedQuery("select recomend from p_vizit where id=?", vp.getPvizit_id()); 
if (acrs.getResultSet().next()) 
				if (acrs.getResultSet().getString(1)!=null){ sb.append("<br> Лечебные и трудовые рекомендации");sb.append(String.format("%s", acrs.getResultSet().getString(1)));}
sb.append("<br>");

sb.append("Лечащий врач:");
acrs.close();
acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",vp.getUserId());
				if (acrs.getResultSet().next()) 

				sb.append(String.format("%s  %s  %s ", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("<p align=\"right\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			acrs.close();
			osw.write(sb.toString());
			return path;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public String printKek(int npasp, int pvizitId) throws KmiacServerException, TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("kek", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title> Протокол заключения Клинико-Экспертной комиссии</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<h4 align=center>Протокол заключения Клинико-Экспертной комиссии</h4><br>");
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar FROM patient where npasp=?", npasp);
				if (acrs.getResultSet().next()) {
				sb.append(String.format("<b>Ф.И.О.</b> %s  %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
				sb.append(String.format("<br><b>Дата рождения: </b> %1$td.%1$tm.%1$tY<br>", acrs.getResultSet().getDate(4)));
				}
				sb.append("основное заболевание ");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=1 and p_diag_amb.predv=false order by p_vizit_amb.datap", pvizitId);
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null)sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
					}
				sb.append("<br>осложнение основного заболевания <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=2 and p_diag_amb.predv=false order by p_vizit_amb.datap", pvizitId);
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}				
				sb.append("<br>сопутствующее заболевание <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,p_diag_amb.named from p_diag_amb join p_vizit_amb on (p_vizit_amb.id = p_diag_amb.id_pos AND p_vizit_amb.id_obr = p_diag_amb.id_obr) where p_diag_amb.id_obr=? and p_diag_amb.diag_stat=3 and p_diag_amb.predv=false order by p_vizit_amb.datap", pvizitId);
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}
				sb.append("<br> <b>Решение комиссии</b>______________________________________________________________ <br>");
				sb.append("__________________________________________________________________________________________<br>");
				sb.append("__________________________________________________________________________________________<br>");
				sb.append("__________________________________________________________________________________________<br>");
				sb.append("__________________________________________________________________________________________<br>");
			sb.append("<b>Подпись членов комиссии</b> <br>");
			sb.append("<b>Председатель КЭК</b>______________________ <br>");
			sb.append("<b>Зав.отделением</b>_________________________ <br>");
			sb.append("<b>Лечащий врач</b>____________________________ <br>");
			sb.append(String.format("<p align=\"right\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			acrs.close();
			osw.write(sb.toString());
			return path;
			}
		 catch (SQLException e) {
			 ((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public AnamZab getAnamZab(int id_pvizit, int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_anam_zab WHERE id_pvizit = ? AND npasp = ? ", id_pvizit, npasp)) {
			if (acrs.getResultSet().next())
				return rsmAnamZab.map(acrs.getResultSet());
			else
				return new AnamZab();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void setAnamZab(AnamZab anam) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_anam_zab SET  t_ist_zab = ? WHERE id_pvizit = ? ", false, anam, anamZabTypes, 2, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public PdiagAmb getPdiagAmbProsm(int idObr) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_pvizit = ? AND predv = FALSE AND diag_stat=1", idObr)) {
			return rsmPdiagAmb.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int setPdiag(PdiagZ diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPdiagZ(diag.npasp, diag.diag);
				sme.execPreparedT("UPDATE p_diag SET diag = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, nmvd = ?, xzab = ?, stady = ?, disp = ?, pat = ?, prizb = ?, prizi = ?, named = ?, ppi = ? WHERE npasp = ? and diag = ?", false, diag, pdiagZTypes, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 1, 2);
				sme.setCommit();
				return diag.getId();
			} catch (PdiagNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_diag (npasp, diag, d_vz, d_grup, ishod, dataish, datag, datad, nmvd, xzab, stady, disp, pat, prizb, prizi, named, ppi, id_diag_amb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, diag, pdiagZTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19);
				int id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;
			}
		} catch (SQLException  e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public int setPdisp(Pdisp disp) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPdisp(disp.npasp, disp.diag, disp.pcod);
				sme.execPreparedT("UPDATE p_disp SET diag = ?, pcod = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, datad = ?, diag_s = ?, d_grup_s = ?, cod_sp = ?, cdol_ot = ?, d_uch = ?, diag_n = ? WHERE npasp = ? and diag = ? and pcod = ?", false, disp, pdispTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 2, 3);
				sme.setCommit();
				return disp.getId();
			} catch (PdispNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_disp (npasp, diag, pcod, d_vz, d_grup, ishod, dataish, datag, datad, diag_s, d_grup_s, cod_sp, cdol_ot, d_uch, diag_n) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, disp, pdispTypes, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
				int id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@SuppressWarnings("resource")
	@Override
	public String printProtokol(int npasp, int userId, int pvizit_id, int pvizit_ambId, int cpol, int clpu, int nstr) throws KmiacServerException, TException {
		AutoCloseableResultSet acrs = null;
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("protokol", ".htm").getAbsolutePath()), "utf-8")) {
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<STYLE TYPE=\"text/css\">");
			sb.append("	<!--");
			sb.append("@page { size: 29.7cm 21cm; margin-left: 1cm; margin-right: 1.06cm; margin-top: 1.12cm; margin-bottom: 1.2cm }");
			sb.append("P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }");
			sb.append("P.western { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ru-RU }");
			sb.append("P.cjk { font-family: \"Times New Roman\", serif; font-size: 12pt }");
			sb.append("P.ctl { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ar-SA }");
			sb.append("A:link { color: #000080; so-language: zxx; text-decoration: underline }");
			sb.append("A:visited { color: #800000; so-language: zxx; text-decoration: underline }");
			sb.append("-->");
			sb.append("</STYLE>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Вкладыш в амб.карту</title>");
			sb.append("</head>");
			sb.append("<body>");
			acrs = sse.execPreparedQuery("select fam,im,ot from patient where npasp=?", npasp);
			if (acrs.getResultSet().next()) 
				sb.append(String.format("<font size=1> %s %s %s </font>", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append("<div>");
			sb.append("<table cellpadding=\"5\" cellspacing=\"0\">");
			sb.append("<tr valign=\"top\" style=\"height:normal;\">");
			sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; font: 10px times new roman;\" width=\"60%\">");
				acrs = sse.execPreparedQuery("select datap,cpos,n_p0c.name,n_opl.name,n_abs.name FROM p_vizit_amb left join n_p0c on(p_vizit_amb.cpos=n_p0c.pcod) left join n_opl on(p_vizit_amb.opl=n_opl.pcod) left join n_abs on(p_vizit_amb.mobs=n_abs.pcod) where id=?", pvizit_ambId);
				if (acrs.getResultSet().next()) {
					sb.append(String.format("<b>Дата посещения </b> %1$td.%1$tm.%1$tY", acrs.getResultSet().getDate(1)));
					if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("<br><b>Цель посещения </b>%s", acrs.getResultSet().getString(3)));
					if (acrs.getResultSet().getString(4)!=null) sb.append(String.format("<br><b>Вид оплаты </b>%s", acrs.getResultSet().getString(4)));
					if (acrs.getResultSet().getString(5)!=null) sb.append(String.format("<br><b>Место обслуживания </b>%s", acrs.getResultSet().getString(5)));
				}
				acrs.close();
				
//				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", pk.getPvizit_id()); 
//				if (acrs.getResultSet().next()) {
//					sb.append("<br><b>	Анамнез заболевания</b><br>");
//					if (acrs.getResultSet().getString(1)!=null)
//						sb.append(String.format(" %s.", acrs.getResultSet().getString(1)));
//				}				
//				acrs.close();
				
				acrs = sse.execPreparedQuery("select p_priem.t_jalob,p_priem.t_temp,p_priem.t_ad_sist,p_priem.t_rost,p_priem.t_ves,p_priem.t_chss,p_priem.t_status_praesense,p_priem.t_fiz_obsl,p_priem.t_st_localis,p_priem.t_ocenka,p_priem.t_recom,p_priem.t_ad_dist from p_vizit_amb join p_priem on (p_priem.id_pos=p_vizit_amb.id)  where p_vizit_amb.id=? order by p_vizit_amb.id ", pvizit_ambId);
				if (acrs.getResultSet().next()) {
					sb.append("<br><b>Осмотр: </b><br>");
					do {
						if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("<i>Жалобы: </i> %s <br>", acrs.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(2)!=null) sb.append(String.format("<i>Температура </i> %s <br>", acrs.getResultSet().getString(2)));
						if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("<i>Систолическое давление </i> %s <br>", acrs.getResultSet().getString(3)));
						if (acrs.getResultSet().getString(12)!=null) sb.append(String.format("<i>Диастолическое давление </i> %s <br>", acrs.getResultSet().getString(12)));
						if (acrs.getResultSet().getString(4)!=null) sb.append(String.format("<i>Рост </i> %s <br>", acrs.getResultSet().getString(4)));
						if (acrs.getResultSet().getString(5)!=null) sb.append(String.format("<i>Вес </i> %s <br>", acrs.getResultSet().getString(5)));
						if (acrs.getResultSet().getString(6)!=null) sb.append(String.format("<i>ЧСС </i> %s <br>", acrs.getResultSet().getString(6)));
						if (acrs.getResultSet().getString(7)!=null) sb.append(String.format("<i>Status praesense </i> %s <br>", acrs.getResultSet().getString(7)));
						if (acrs.getResultSet().getString(8)!=null) sb.append(String.format("<i>Физикальное обследование </i> %s <br>", acrs.getResultSet().getString(8)));
						if (acrs.getResultSet().getString(9)!=null) sb.append(String.format("<i>Localis status </i> %s <br>", acrs.getResultSet().getString(9)));
						if (acrs.getResultSet().getString(11)!=null) sb.append(String.format("<i>Рекомендации </i> %s <br>", acrs.getResultSet().getString(11)));
						if (acrs.getResultSet().getString(10)!=null) sb.append(String.format("<i>Оценка данных анамнеза и объективного исследования </i> %s <br>", acrs.getResultSet().getString(10)));
					} while (acrs.getResultSet().next());
				}
				acrs.close();
				acrs = sse.execPreparedQuery("select p_diag_amb.diag,n_vdi.name,p_diag_amb.named  from p_vizit_amb join p_diag_amb on (p_diag_amb.id_pos=p_vizit_amb.id) left join n_vdi on(p_diag_amb.diag_stat=n_vdi.pcod) where p_vizit_amb.id=? order by p_vizit_amb.id ", pvizit_ambId);
				if (acrs.getResultSet().next()) {
					do {
						sb.append(String.format("<i>Код диагноза МКБ 10 </i> %s <br>", acrs.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(2)!=null) sb.append(String.format("<i>Статус диагноза</i> %s <br>", acrs.getResultSet().getString(2)));
						if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("<i>Медицинское описание диагноза </i> %s <br>", acrs.getResultSet().getString(3)));

						} while (acrs.getResultSet().next());
				}
				acrs.close();
				acrs = sse.execPreparedQuery("select n_aq0.name from p_vizit_amb left join n_aq0 on (p_vizit_amb.rezult=n_aq0.pcod)  where p_vizit_amb.id=? order by p_vizit_amb.id ", pvizit_ambId);
				if (acrs.getResultSet().next()) {
						if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("<i>Результат: </i> %s <br>", acrs.getResultSet().getString(1)));
						acrs.close();
				}
	
				
					
//				sb.append("<br><b>Поставленные диагнозы: </b><br>");
//				acrs = sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pk.getPvizit_id());
//				if (acrs.getResultSet().next()) {
//					sb.append("основное заболевание <br>");
//					acrs.close();
//					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", pk.getPvizit_id());
//					while (acrs.getResultSet().next()) {
//						if (acrs.getResultSet().getString(1) != null)
//							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
//					}
//					acrs.close();
//					
//					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=2 and predv=false order by datap", pk.getPvizit_id());
//					if (acrs.getResultSet().isBeforeFirst())
//						sb.append("<br>осложнение основного заболевания <br>");
//					while (acrs.getResultSet().next()) {
//						if (acrs.getResultSet().getString(1) != null)
//							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
//						else
//							sb.append(" -<br>");
//						}				
//					acrs.close();
//					
//					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=3 and predv=false order by datap", pk.getPvizit_id());
//					if (acrs.getResultSet().isBeforeFirst())
//						sb.append("<br>сопутствующее заболевание <br>");
//					while (acrs.getResultSet().next()) {
//						if (acrs.getResultSet().getString(1) != null)
//							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
//						else
//							sb.append(" -<br>");
//						}
//					acrs.close();
					
					
					acrs = sse.execPreparedQuery("select p_vizit.recomend,p_vizit.zakl,p_vizit.lech, n_ap0.name from p_vizit left join n_ap0 on (p_vizit.ishod=n_ap0.pcod) where id=?", pvizit_id); 
					if (acrs.getResultSet().next()) {
							if (acrs.getResultSet().getString(3) != null)
							sb.append(String.format("<br><b> Лечение</b> %s", acrs.getResultSet().getString(3).replace("\n+", "\n")));
						if (acrs.getResultSet().getString(1) != null)
							sb.append(String.format("<br><b> Лечебные и трудовые рекомендации</b> %s", acrs.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(2) != null)
							sb.append(String.format("<br><b> Заключение </b> %s", acrs.getResultSet().getString(2)));
						if (nstr != 0){
						}
						else{
							if (acrs.getResultSet().getString(4) != null)
								sb.append(String.format("<br><b> Исход </b> %s", acrs.getResultSet().getString(4)));
						}
					}
					sb.append("<br>");
				acrs.close();
	
				sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
				sb.append("Лечащий врач:");
				acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",userId);
				if (acrs.getResultSet().next())
					sb.append(String.format("%s %s %s",acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
				sb.append("<br>Подпись ____________");
				if (sb.length()>4600){
				sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; font: 10px times new roman;\" width=\"40%\">");
				acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav, p_vizit_amb.datap " +
						"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok)  " +
						"join p_vizit_amb on (p_vizit_amb.id = p_isl_ld.id_pos) "+
						"where p_isl_ld.id_pos = ? " +
						"union " +
						"select p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav, p_vizit_amb.datap " +
						"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)  " +
						"join p_vizit_amb on (p_vizit_amb.id = p_isl_ld.id_pos) "+
						"where p_isl_ld.id_pos = ? ", pvizit_ambId, pvizit_ambId);
					if (acrs.getResultSet().isBeforeFirst()) {
						sb.append("<b>Назначенные исследования </b><br>");
						if (acrs.getResultSet().next()) {
							if (acrs.getResultSet().getString(4) != null) {
								sb.append("<table border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\">Наименование показателя</th><th style=\"font: 10px times new roman;\">Результат</th></tr>");
		//						if (sb.length()>4697){
								do {
									sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\"> %s </th></tr>", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4)));
								} 
								while (acrs.getResultSet().next());
//								}
//								else {
//									sb.append("<>");
//								}
							}
							else {
								sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th>Наименование показателя</th><th>Результат</th></tr>");
								do {
									sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\" width=\"30\">  </th></tr>", acrs.getResultSet().getString(3), acrs.getResultSet().getString(3)));
								} 
								while (acrs.getResultSet().next());
							}
						}		sb.append("</table><br>");		
					}
				}
				else {
					acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav, p_vizit_amb.datap " +
							"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok)  " +
							"join p_vizit_amb on (p_vizit_amb.id = p_isl_ld.id_pos) "+
							"where p_isl_ld.id_pos = ? " +
							"union " +
							"select p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav, p_vizit_amb.datap " +
							"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)  " +
							"join p_vizit_amb on (p_vizit_amb.id = p_isl_ld.id_pos) "+
							"where p_isl_ld.id_pos = ? ", pvizit_ambId, pvizit_ambId);
					if (acrs.getResultSet().isBeforeFirst()) {
						sb.append("<br><b>Назначенные исследования </b><br>");
						if (acrs.getResultSet().next()) {
							
							if (sb.length()<4600){
								sb.append("<br>");
								sb.append("<table border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> <tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\">Наименование показателя</th><th style=\"font: 10px times new roman;\">Результат</th></tr>");
								do{
									if (acrs.getResultSet().getString(4) != null)
										sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\"> %s </th></tr>", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4)));
									else 
										sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\" width=\"30\">  </th></tr>", acrs.getResultSet().getString(3)));
								}
								
								while ((sb.length()<4600) && (acrs.getResultSet().next()));
								
							}
							if (sb.length()>4600){
								sb.append("</table>");
								sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; font: 10px times new roman;\" width=\"40%\">");
								sb.append("<br>");
								sb.append("<table border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> ");
								do {
									if (acrs.getResultSet().getString(4) != null) 
										sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\"> %s </th></tr>", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4)));
									else 
										sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 10px times new roman;\"> %s </th><th style=\"font: 10px times new roman;\" width=\"30\">  </th></tr>", acrs.getResultSet().getString(3)));
								} 
								
								while (acrs.getResultSet().next());
							}
						
								
						}

							sb.append("</table><br>");	
							
					}
				
				}
					acrs.close();
				sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; font: 10px times new roman;\" width=\"40%\">&nbsp<td>");
				sb.append("</td></tr></table></div>");
				sb.append("</body>");
				sb.append("</html>");
			
			osw.write(sb.toString());
			return path;}
		catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		} finally {
			if (acrs != null)
				acrs.close();
		}
	}

	@Override
	public void DeletePriem(int posId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_priem WHERE id_pos = ? ", false, posId);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}		
	}

	@Override
	public void DeleteAnamZab(int pvizit_id) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_anam_zab WHERE id_pvizit = ? ", false, pvizit_id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}	
	}

	@Override
	public int AddPnapr(PNapr pn) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_napr (id_pvizit, vid_doc, text, preds, zaved) VALUES (?, ?, ?, ?, ?) ", true, pn, pnaprTypes, 1, 2, 3, 4, 5);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public String printMSK(int npasp) throws KmiacServerException, TException {
		String path;
		
			try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("msk", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Направление на МСЭК</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<p align=\"center\" >Министерство здравоохранения");
				sb.append("<br>Российской Федерации");
				sb.append("<br>_____________________________________________________________________________________");
				sb.append("<br>(наименование и адрес организации, оказывающей лечебно-профилактическую помощь)");
				sb.append("<br>");
				sb.append("<br>НАПРАВЛЕНИЕ НА МЕДИКО-СОЦИАЛЬНУЮ ЭКСПЕРТИЗУ ОРГАНИЗАЦИЕЙ, <br>ОКАЗЫВАЮЩЕЙ ЛЕЧЕБНО-ПРОФИЛАКТИЧЕСКУЮ ПОМОЩЬ");
				sb.append("</p>");
				sb.append("<br>Дата выдачи ");
				sb.append(String.format(" %1$td.%1$tm.%1$tY", new Date(System.currentTimeMillis())));
				sb.append("<br>");
				sb.append("1. Фамилия,имя, отчество гражданина, направляемого на медико-социальную экспертизу (далее гражданин): ");
				acrs = sse.execPreparedQuery("select fam,im,ot,datar,n_z30.name from patient join n_z30 on(patient.pol=n_z30.pcod) where npasp=?", npasp);
				if (acrs.getResultSet().next()) 
				{	sb.append(String.format("%s %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("<br>2. Дата рождения: </b> %1$td.%1$tm.%1$tY", acrs.getResultSet().getDate(4)));
					sb.append(String.format("<br>3. Пол:  %s", acrs.getResultSet().getString(5)));}
				sb.append("<br>");
				sb.append("4. Фамилия, имя, отчество законного представителя гражданина (заполняется при наличии законного представителя): ______________________________________________________________________________");
				sb.append("<br>");
				sb.append("5. Адрес места жительства гражданина (при отсутствии места жительства указывается адрес пребывания, фактического проживания на территории Российской Федерации): ___________________________________________________________________________");
				sb.append("<br>");
				sb.append("6. Инвалидом не является, инвалид первой, второй, третьей группы, категория 'ребенок-инвалид' (нужное подчеркнуть)");
				sb.append("<br>");
				sb.append("7. Исключен: ___________________________________________________________________");
				sb.append("<br>");
				sb.append("8. Степень утраты профессиональной трудоспособности в процентах (заполняется при повторном направлении) _____________________________________________");
				sb.append("<br>");
				sb.append("9. Направляется первично, повторно (нужное подчеркнуть)");
				sb.append("<br>");
				sb.append("10. Кем работает на момент направления на медико-социальную эксперизу (указать должность, профессию, специальность, квалификацию и стаж работы по указанной должности, профессии, специальности, квалификации; в отношении неработающих граждан сделать запись 'не работает')__________________________________________________");
				sb.append("<br>");
				sb.append("11. Наименование и адрес организации, в которой работает гражданин: ________________________________________________________");
				sb.append("<br>");
				sb.append("12. Условия и характер выполняемого труда: ______________________________________________________________________");
				sb.append("<br>");
				sb.append("13. Основная профессия (специальность): _________________________________________________________________________");
				sb.append("<br>");
				sb.append("14. квалификация по основной професии (класс, разряд, категория, звание): ______________________________________");
				sb.append("<br>");
				sb.append("15. Наименование и адрес образовательного учреждения: ___________________________________________");
				sb.append("<br>");
				sb.append("16. Группа, класс, курс (указываемое подчеркнуть): ________________________");
				sb.append("<br>");
				sb.append("17. Профессия (специальность), для получения которой производится обучение: ______________________________");
				sb.append("<br>");
				sb.append("18. Наблюдается в организациях, оказывающих лечебно-профилактическую помощь, с ____ года.");
				sb.append("<br>");
				sb.append("19. История заболевания (начало, развитие, течение, частота и длительность обострений, проведенные лечебно-оздоровительные и реабилитационные мероприятия и их эффективность):");
				sb.append("____________________________________________________________________________________________________________________________________________________");
				sb.append("<dd>(подробно описывается при первичном направлении, при повторном направлении отражается динамика  за период между освидетельствованиями, детально описываются выявленные за этот период нвоые случаи заболеваний, приведшим к стойким нарушениям функций организма)</dd>");
				sb.append("<br>");
				sb.append("<br>");
				sb.append("20. Анамнез жизни (пречисляются перенесенные в прошлом заболевания, травмы, отравления, операции, заболевания, по которым отягощена наследственность"); 
				sb.append(", дополнительно в отношении ребенка указывается, как протекали беременность и роды у матери, сроки формирования психомоторынх навыков, самообслуживания, показательно-игровой деятельности, навыков опрятности и ухода за собой, как протекало раннее развитие (по возрасту, с отставанием, с опережением)): ________________________________________________________________________________________________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("<br>");
				sb.append("21. Частота и длительность временной нетрудоспособности (сведения за последние 12 месяцев)");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> ");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">№</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Дата (число, месяц, год) начала временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Дата (число, месяц, год) окончания временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Число дней (месяцев и дней) временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Диагноз</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("<tr bgcolor=\"white\"><th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("<th style=\"font: 16px times new roman;\">&nbsp;</th>");
				sb.append("</tr>");
				sb.append("</table>");
				sb.append("<br>");
				sb.append("22. Результаты проведенных мероприятий по медицинской реабилитации в соответствии с индивидуальной программой реабилитации инвалида (заполняется при повторном направлении, указываются конкретные виды восстановительноц терапии, реконструктивной хирургии, санаторно-курортного лечения, технических средств медиицнской реабилитации, в том числе протезирования и ортезирования, а также сроки, в которые они были предоставлены; перечисляются функции организма, которые удалось комепнсировать или восстановить полностью или частично, либо делается отметка, что положительные результаты отсутствуют):");
				sb.append("__________________________________________________________________________________________________________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("23. Состояние гражданина при направлении на медико-социальную экспертизу (указываются жалобы, даннеы осмотра лечащим врачом и врачами других специальностей):_____________________________________");
				sb.append("<br>");
				sb.append("24. Результаты дополнительных методов исследования (указываются результаты проведенных лабораторных, рентгенологичесских, эндоскопических, ультразвуковых, психологических, функциональных и других видов исследований):");
				sb.append("_____________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("25. масса тела (кг)______________, рост (м) ___________, индекс массы тела___________.");
				sb.append("<br>");
				sb.append("26. Оценка физического развития: нормальное, отклонение (дефицит массы тела, избыток массы тела, низкий рост, высокий рост) (нужное подчеркнуть).");
				sb.append("<br>");
				sb.append("27. оценка психофизиологчиеской выносливости: норма, отклонение (нужное подчеркнуть).");
				sb.append("<br>");
				sb.append("28. оценка эмоциональной устойчивости: норма, отклонение (нужное подчеркнуть).");
				sb.append("<br>");
				sb.append("29. Диагноз при направлении на медико-социальную экспертизу:"); 
				sb.append("<br>");
				sb.append("а) код основного заболевания по МКБ:__________________________________");
				sb.append("<br>");
				sb.append("б) основное заболевание:__________________________________");
				sb.append("<br>");
				sb.append("в) сопутствующие заболевания: _____________________________________");
				sb.append("<br>");
				sb.append("г) осложнения: ___________________________________________________");
				sb.append("<br>");
				sb.append("30. Клинический прогноз: благоприятный, относительно благоприятный, сомнительный (неопределенный), неблагоприятный (нужное подчеркнуть).");
				sb.append("<br>");
				sb.append("31. Реабилитационный потенциал: высокий, удовлетворительный, низкий (нужное подчеркнуть).");
				sb.append("<br>");
				sb.append("32. Реабилитационный прогноз: благоприятный, относительно благоприятный, сомнительный (неопределенный), неблагоприятный (нужное подчерукнуть).");
				sb.append("<br>");
				sb.append("33. Цель направления на медико-социальную экспертизу (нужное подчеркнуть): для установления инвалидности, степени ограничения способности к трудовой деятельности, степени утраты профессиональной трудоспособностив процентах, для разработки (коррекции) индивидуальной программы реабилитации инвалида (программы реабилитации пострадавшего в результате несчастного случая на производстве и профессионального заболевания), для другого (указать):");
				sb.append("______________________________________________________________________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("34. Рекомендуемые мероприятия по медицинской реабилитации для формирования или коррекции индивидуальной программы реабилитации инвалида, программы реабилитации пострадавшего в результате несчастного случая на производстве и професионального заболевания (указываются конкретные виды восстановительной терапии, включая лекарственное обеспечение при лечении заболевания, ставшего причиной инвалидности; реконструктивной хирургии, включая лекарственное обеспечение при лечении заболевания, ставшего причиной инвалидности; технических средств медицинской реабилитации, в том числе протезирования и ортезирования; заключение о санаторно-курортном лечениис предписанием профиля, кратности, срока и сезона рекомендуемого лечения, о нуждаемостив специальном медицинском уходе лиц, пострадавших в результате несчастных случаев на производстве и профессиональных заболеваний, о нуждаемости в лекарственных средствах для лечения последствий несчастных случаев на производстве и профессиональных заболеваний, другие виды медицинской реабилитации)");
				sb.append("_______________________________________________________________________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("Председатель врачебной комиссии _________________ (подпись) ________________(расшифровка)");
				sb.append("<br>");
				sb.append("Члены врачебной комисии _________________ (подпись) ________________(расшифровка)");
				sb.append("<br>");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_________________ (подпись) ________________(расшифровка)");
				sb.append("<br>");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_________________ (подпись) ________________(расшифровка)");
				sb.append("</body>");
				osw.write(sb.toString());
				return path;
		} catch (SQLException  e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public String printKartaBer(KartaBer kb) throws KmiacServerException, TException {
		AutoCloseableResultSet acrs = null, acrs2 = null;
		Date dataRod = null;
		Date dataRod1 = null;
		
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("kart1", ".htm").getAbsolutePath()), "utf-8")) {

//		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\kartl.htm"), "utf-8")) {
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Обменная карта беременной</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<p align=center>ИНФО МУЗДРАВ<br></p>");
				sb.append("<h3 align=center>ОБМЕННАЯ КАРТА<br></h3>");
				sb.append("<p align=center>Родильного дома, родильного отделения</p>");
				sb.append("<p align=center>Заполняется врачом женской консультации.</p>");
				sb.append("<p align=center><b>(Данная карта выдается на руки на 32-ой неделе беременности)</b></p>");
				sb.append("<br>");
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, (current_date - datar) / 365, adm_ul, adm_dom, adm_kv, tel FROM patient WHERE npasp = ?", kb.getNpasp());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("1. Фамилия, имя, отчество: %s %s %s<br>", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("2. Возраст: %d<br>", acrs.getResultSet().getInt(5)));
					sb.append(String.format("3. Адрес и телефон: %s %s - %s", acrs.getResultSet().getString(6), acrs.getResultSet().getString(7), acrs.getResultSet().getString(8)));
					if (acrs.getResultSet().getString(9) != null)
						sb.append(String.format(", %s<br>", acrs.getResultSet().getString(9)));
					sb.append("<br>");
				}
				acrs.close();
				
				acrs = sse.execPreparedQuery("SELECT shet, kolrod, abort, datam, (current_date - datay) / 7, datay, dataosl, dsp, dsr, dtroch, cext, cdiagt, cvera, rost, vesd, datazs, prrod, oslrod, dataab, srokab FROM p_rd_sl WHERE id_pvizit = ? ", kb.getId_pvizit());
				//                                   1     2       3      4       5                          6      7        8    9    10      11    12      13     14    15    16      17     18      19      20
				if (acrs.getResultSet().next()) {
					sb.append("4. Перенесенные общие, гинекологические заболевания или операции: ");
					acrs2 = sse.execPreparedQuery("SELECT ps.vitae, db9.name FROM p_rd_sl sl LEFT OUTER JOIN p_sign ps ON (ps.npasp = sl.npasp) LEFT OUTER JOIN n_db9 db9 ON (db9.pcod = sl.oslab) WHERE id_pvizit = ? AND (ps.vitae IS NOT NULL OR db9.name IS NOT NULL) ", kb.getId_pvizit());
					if (acrs2.getResultSet().next()) {
						if (acrs2.getResultSet().getString(1) != null)
							sb.append(String.format("%s;<br>", acrs2.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(17) != null)
						sb.append(String.format("5. Особенности течения прежних беременностей, родов, послеродового периода: %s<br>", acrs.getResultSet().getString(17)));
						else
						sb.append("5. Особенности течения прежних беременностей, родов, послеродового периода: <br>");
						sb.append(String.format("6. Данная беременность %d (по счету), роды %d (по счету)<br>", acrs.getResultSet().getInt(1), acrs.getResultSet().getInt(2)));
						if (acrs.getResultSet().getInt(3) != 0)
						sb.append(String.format("7. Количество абортов %d (всего). Последний в %tY году на сроке %d недель<br>", acrs.getResultSet().getInt(3), acrs.getResultSet().getDate(19), acrs.getResultSet().getInt(20)));
						else
						sb.append(String.format("7. Количество абортов %d (всего)<br>", acrs.getResultSet().getInt(3)));
						if (acrs2.getResultSet().getString(2) != null)
							sb.append(String.format("осложнения после аборта: %s;<br>", acrs2.getResultSet().getString(2)));
					} else
						sb.append("осложнения после аборта: нет<br>");
					acrs2.close();
					
//					sb.append(String.format("5. Особенности течения прежних беременностей, родов, послеродового периода: %s<br>", acrs.getResultSet().getString(17)));
//					sb.append(String.format("6. Данная беременность %d (по счету), роды %d (по счету)<br>", acrs.getResultSet().getInt(1), acrs.getResultSet().getInt(2)));
//					sb.append(String.format("7. Количество абортов %d (всего). Последний в %tY году на сроке %d недель<br>", acrs.getResultSet().getInt(3), acrs.getResultSet().getDate(19), acrs.getResultSet().getInt(20)));
					sb.append("8. Были ли преждевременные роды: ДА _____ / НЕТ _____ . Если ДА, то в каком году _____________<br>");
					sb.append(String.format("9. Дата последней менструации: %1$td %1$tb %1$tY<br>", acrs.getResultSet().getDate(4)));
					sb.append(String.format("10. Срок текущей беременности составляет %d недель при первом посещении женской консультации %2$td %2$tb %2$tY года<br>", acrs.getResultSet().getInt(5), acrs.getResultSet().getDate(6)));
					
					acrs2 = sse.execPreparedQuery("SELECT COUNT(id_pvizit) FROM p_rd_din WHERE id_pvizit = ?", kb.getId_pvizit());
					if (acrs2.getResultSet().next())
						sb.append(String.format("11. Количество посещений: %d<br>", acrs2.getResultSet().getInt(1)));
					acrs2.close();
					
					sb.append(String.format("12. Первое шевеление плода: %1$td %1$tb %1$tY<br>", acrs.getResultSet().getDate(7)));
					
					sb.append("13. Возможные особенности течения беременности: ");					System.out.println("перед select");		
					acrs2 = sse.execPreparedQuery("SELECT diag,named FROM p_diag_amb WHERE id_obr = ?  ", kb.getId_pvizit());
					if (acrs2.getResultSet().next()) {
						do {
							String str = "";
						
							if (acrs2.getResultSet().getString(1) != null)
								str += String.format("диагноз: %s - %s ", acrs2.getResultSet().getString(1), acrs2.getResultSet().getString(2));
							if (str.length() > 0)
								sb.append(str + "<br>");

						} while (acrs2.getResultSet().next());
					} else
						sb.append("нет<br>");
					acrs2.close();
					
					sb.append("14. Размеры таза (при первом посещении)<br>");
					sb.append(String.format("D.Sp = %d; D.Cr = %d; D.troch = %d;<br>", acrs.getResultSet().getInt(8), acrs.getResultSet().getInt(9), acrs.getResultSet().getInt(10)));
					sb.append(String.format("C.ext = %d; C.diag = %d; C.vera - %d;<br>", acrs.getResultSet().getInt(11), acrs.getResultSet().getInt(12), acrs.getResultSet().getInt(13)));
					sb.append(String.format("Рост = %d; Вес = %.2f;<br>", acrs.getResultSet().getInt(14), acrs.getResultSet().getDouble(15)));
					dataRod = acrs.getResultSet().getDate(16);
				}
				acrs.close();
				
				acrs = sse.execPreparedQuery("SELECT db1.name, db2.name, db3.name, db4.name, chcc FROM p_rd_din din JOIN n_db1 db1 ON (db1.pcod = din.polpl) JOIN n_db2 db2 ON (db2.pcod = din.predpl) JOIN n_db3 db3 ON (db3.pcod = din.serd1) JOIN n_db4 db4 ON (db4.pcod = din.serd) WHERE id_pvizit = ? ORDER BY id_rd_sl DESC ", kb.getId_pvizit());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("15. Положение плода: %s<br>", acrs.getResultSet().getString(1)));
					sb.append(String.format("Предлежащая часть плода: %s<br>", acrs.getResultSet().getString(2)));
					sb.append(String.format("Сердцебинение плода: %s, %s, %d ударов в 1 минуту слева, справа _________<br>", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4), acrs.getResultSet().getInt(5)));
				}
				acrs.close();
				
				sb.append("16. Лабораторные и другие исследования<br>");
				acrs2 = sse.execPreparedQuery("select l.datav,l.cisl,n.name,d.zpok from p_isl_ld l,p_rez_l d,n_ldi n where l.nisl=d.nisl and d.cpok=n.pcod and l.datav is not null and l.pvizit_id = ? ", kb.getId_pvizit());
						if (acrs2.getResultSet().next()) {
					do {
						dataRod1 = acrs2.getResultSet().getDate(0);
					sb.append(String.format("Дата %1$td %1$tb %1$tY  %s  %d",dataRod1,acrs2.getResultSet().getString(2),acrs2.getResultSet().getInt(3))); 	

					} while (acrs2.getResultSet().next());
				} else 
					sb.append("нет<br>");
//				sb.append("RW<sub>1</sub>: \"____\" _____________ 20______ года <br>HBS<sub>1</sub>: \"____\" ____________ 20______ года<br>");
//				sb.append("RW<sub>2</sub>: \"____\" _____________ 20______ года <br>HBS<sub>2</sub>: \"____\" ____________ 20______ года<br>");
//				sb.append("RW<sub>3</sub>: \"____\" _____________ 20______ года <br>HCV<sub>1</sub>: \"____\" ____________ 20______ года<br>");
//				sb.append("ВИЧ<sub>1</sub>: \"____\" _____________ 20______ года <br>HCV<sub>2</sub>: \"____\" ____________ 20______ года<br>");
//				sb.append("ВИЧ<sub>2</sub>: \"____\" _____________ 20______ года<br>");
				
				acrs = sse.execPreparedQuery("SELECT ph, grup FROM p_sign WHERE npasp = ? ", kb.getNpasp());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("Резус: %s<br>", acrs.getResultSet().getString(1)));
					sb.append("Титр антител:_________<br>");
					sb.append(String.format("Группа крови: %s<br>", acrs.getResultSet().getString(2)));
				}
				acrs.close();
				acrs = sse.execPreparedQuery("SELECT photec FROM p_rd_inf WHERE npasp = ? ", kb.getNpasp());
				if (acrs.getResultSet().next()) {
					if (acrs.getResultSet().getString(1)=="+")
//					sb.append(String.format("Резус-принадлежность крови мужа: %s<br>", acrs.getResultSet().getString(1)));
					sb.append("<br>Резус-принадлежность крови мужа: положительный");
					else
					sb.append("<br>Резус-принадлежность крови мужа: отрицательный");
				}else 	
					sb.append("<br>Резус-принадлежность крови мужа: неизвестно");
				acrs.close();
				
//				sb.append("<br>Резус-принадлежность крови мужа:__________");
				sb.append("<br>Токсоплазмоз: РСК, кожная проба __________");
//				sb.append("<br><b>Клинические анлизы</b>");
//				sb.append("<br>Крови _____________________________");
//				sb.append("<br>Мочи ______________________________");
//				sb.append("<br>Анализ содержимого влагалища (мазок) _______________________________");
//				sb.append("<br>Кал на яйца-глист _________________________");
				sb.append("<br>17. Школа матерей _________________");
				sb.append("<br>18. Дата выдачи листка нетрудоспособности по дородовому отпуску \"______\" _________ 20___ г.<br>");
				
				if (dataRod != null)
					sb.append(String.format("19. Дата предполагаемых родов %1$td %1$tb %1$tY г.", dataRod));
				
				sb.append("<br>Подпись врача акушера-гинеколога ________________");
				sb.append("<br><b>Дневник последующих посещений");
				sb.append("</b>");
				sb.append("<br>Прибавка в весе во время беременности _________________");
				sb.append("<br>Предполагаемый вес плода ________________");
				sb.append("<br>");
				sb.append("<br>");
				sb.append("<TABLE BORDER=2>");
				sb.append("<TR>");
				sb.append("<TD rowspan=2 align=center>Дата</TD>");
				sb.append("<TD rowspan=2 align=center>Срок</TD>");
				sb.append("<TD colspan=7 align=center>Данные обследования</TD>");
				sb.append("<TD rowspan=2 align=center>Подпись врача</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>АД левая</TD>");
				sb.append("<TD>АД правая</TD>");
				sb.append("<TD>Вес</TD>");
				sb.append("<TD>Окружность живота</TD>");
				sb.append("<TD>ВДМ</TD>");
				sb.append("<TD>Диагноз</TD>");
				sb.append("<TD>Расположение отеков</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				acrs2 = sse.execPreparedQuery("SELECT db6.name, db5.name,srok,oj,hdm,ves,art1,art2,art3,art4,datap,id_pvizit FROM  p_rd_din din LEFT OUTER JOIN n_db6 db6 ON (db6.pcod = din.dspos) LEFT OUTER JOIN n_db5 db5 ON (db5.pcod = din.oteki) join p_vizit_amb on (id= id_pos) WHERE id_pvizit = ? order by datap ", kb.getId_pvizit());
				if (acrs2.getResultSet().next()) {
					do {
							dataRod1 = acrs2.getResultSet().getDate(11);
						sb.append(String.format("<td> %1$td %1$tb %1$tY</TD>",dataRod1));
						sb.append(String.format("<td> %s</TD>",acrs2.getResultSet().getString(3)));
						sb.append(String.format("<TD> %d/%d</TD>",acrs2.getResultSet().getInt(7),acrs2.getResultSet().getInt(8)));
						sb.append(String.format("<TD> %d/%d</TD>",acrs2.getResultSet().getInt(9),acrs2.getResultSet().getInt(10)));
						sb.append(String.format("<TD> %.2f</TD>",acrs2.getResultSet().getDouble(6)));
						sb.append(String.format("<TD> %d</TD>",acrs2.getResultSet().getInt(4)));
						sb.append(String.format("<TD> %d</TD>",acrs2.getResultSet().getInt(5)));
						if (acrs2.getResultSet().getString(1) != null) 
						sb.append(String.format("<TD> %s</TD>",acrs2.getResultSet().getString(1)));
						else sb.append("<TD> </TD>");
						if (acrs2.getResultSet().getString(2) != null) 
						sb.append(String.format("<TD> %s</TD>",acrs2.getResultSet().getString(2)));
						else sb.append("<TD> </TD>");
						sb.append("<TD> </TD>");
						sb.append("</TR>");
//						System.out.println("таблица");		
					} while (acrs2.getResultSet().next());
				} //else
//					sb.append("нет<br>");
				acrs2.close();
				
				sb.append("</TABLE>");
				sb.append("<br>Проведенные исследования: ");
//				System.out.println("Проведенные исследования");		
				acrs2 = sse.execPreparedQuery("select l.datav,l.cisl,n.name,d.rez,d.op_name,d.rez_name from p_isl_ld l,p_rez_d d,n_ldi n where l.nisl=d.nisl and d.kodisl=n.pcod and l.nisl=d.nisl and d.kodisl=n.pcod and l.datav is not null and l.pvizit_id = ? ", kb.getId_pvizit());
//				System.out.println("select");		
				if (acrs2.getResultSet().next()) {
			do {
//				System.out.println("в цикле");		
				dataRod1 = acrs2.getResultSet().getDate(0);
				sb.append(String.format("Дата %1$td %1$tb %1$tY  %s",dataRod1,acrs2.getResultSet().getString(2))); 	
                sb.append(String.format("Описание исследования: $s", acrs2.getResultSet().getString(5)));
			} while (acrs2.getResultSet().next());
		} else
			sb.append("нет<br>");
				sb.append("<br>Заключение: _____________________________________________________");
				sb.append("<br>1. Заключение терапевта _________________________________________");
				sb.append("<br>2. Заключение окулиста ___________________________________________");
			sb.append("</body>"); 
			sb.append("</html>");
			
			osw.write(sb.toString());
			return path;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		} finally {
			if (acrs != null)
				acrs.close();
			if (acrs2 != null)
				acrs2.close();
		}
	}

	@Override
	public List<IntegerClassifier> get_vid_issl() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, tip as name  from n_lds")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public List<StringClassifier> getShOsmPoiskDiag(int cspec, int cslu, String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.diag AS pcod, c00.name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.diag ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu) : sse.execPreparedQuery(sql, cspec, cslu, cslu, srcText, srcText, srcText)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public Shablon getShOsm(int id_sh) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT sho.id, sho.diag, nd.name, sho.name, nsh.pcod, nsh.name, sht.sh_text FROM sh_osm sho JOIN n_din nd ON (nd.pcod = sho.cdin) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) WHERE sho.id = ? ORDER BY nsh.pcod ", id_sh)) {
			if (acrs.getResultSet().next()) {
				Shablon sho = new Shablon(acrs.getResultSet().getInt(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3), acrs.getResultSet().getString(4), new ArrayList<ShablonText>());
				do {
					sho.textList.add(new ShablonText(acrs.getResultSet().getInt(5), acrs.getResultSet().getString(6), acrs.getResultSet().getString(7)));
				} while (acrs.getResultSet().next());
				return sho;
			} else {
				throw new SQLException("No templates with this id");
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template by its id");
	}
}

	@Override
	public List<IntegerClassifier> getShOsmByDiag(int cspec, int cslu, String diag, String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.id AS pcod, sho.name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) AND (sho.diag = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.name ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu, diag) : sse.execPreparedQuery(sql, cspec, cslu, cslu, diag, srcText, srcText, srcText)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public List<IntegerClassifier> getShOsmPoiskName(int cspec, int cslu, String srcText) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT sho.id AS pcod, sho.diag, sho.diag || ' ' || substring(din.name from 1 for 1) || ' ' || sho.name AS name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) JOIN n_din din ON (din.pcod = sho.cdin) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.diag ";
		
		try (AutoCloseableResultSet	acrs = (srcText == null) ? sse.execPreparedQuery(sql, cspec, cslu, cslu) : sse.execPreparedQuery(sql, cspec, cslu, cslu, srcText, srcText, srcText, srcText)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error searching template");
		}
	}

	@Override
	public List<IntegerClassifier> getShDopNames(int idRazd) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, name FROM sh_dop WHERE id_n_shablon = ? ", idRazd)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template names by razd id");
		}
	}
	
	@Override
	public IntegerClassifier getShDop(int id_sh) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id AS pcod, text AS name FROM sh_dop WHERE id = ? ", id_sh)) {
			if (acrs.getResultSet().next())
				return rsmIntClas.map(acrs.getResultSet());
			else
				throw new SQLException("No templates with this id");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException("Error loading template by its id");
		}
	}
	
	@Override
	public List<Pmer> getPmer(int npasp, String diag) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_mer where p_mer.npasp = ? and diag = ? order by pdat", npasp, diag)) {
			return rsmPmer.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public boolean isZapVrNext(int idObr) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id_pvizit FROM e_talon WHERE (id_pvizit = ?) AND (datap > CURRENT_DATE) ", idObr)) {
			return acrs.getResultSet().next();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeleteEtalon(int id_pvizit) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM e_talon WHERE (id_pvizit = ?) AND (datap > CURRENT_DATE) ", false, id_pvizit);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}		
	}

	@Override
	public int AddPmer(Pmer pm) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_mer (npasp, diag, cpol, dataz, id_obr, cdol, cod_sp) values (?, ?, ?, ?, ?, ?, ?) ", true, pm, pmerTypes, 1, 2, 14, 6, 15, 9, 16);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePmer(Pmer pm) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("update p_mer set pmer = ?, pdat = ?, fdat = ?, prichina = ?, rez = ?, dnl = ?, dkl = ?, lpu = ?, ter = ?, cdol_n = ? where id = ? ", false, pm, pmerTypes, 3, 4, 5, 7, 8, 10, 11, 12, 13, 17, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void DeletePmer(int pmer_id) throws KmiacServerException, TException {

		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("delete from p_mer where id = ? ", false, pmer_id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<RdPatient> getRdPatient() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT rd.id_pvizit,rd.npasp,p.fam, p.im, p.ot, p.datar,p.docser,p.docnum,p.adp_gorod,p.adp_ul, " +
       "p.adp_dom,p.adp_kv,p.poms_ser,p.poms_nom,p.poms_ndog,a.stat,n.clpu,p.terp,p.adm_gorod, p.adm_ul, "+
      "p.adm_dom, p.adm_kv,s.grup,s.ph, p.tel,s.vred,rd.deti,rd.datay,rd.yavka1,rd.datazs, " +
      "vr.fam,vr.im,vr.ot,rd.datasn,rd.shet,rd.kolrod,rd.abort,rd.vozmen,rd.prmen,rd.datam,rd.kont, " +
      "rd.dsp,rd.dsr,rd.dtroch,rd.cext,rd.indsol,s.vitae,s.allerg,rd.ishod,rd.datasn,rd.prrod,rd.oslrod,i.sem, "+
      "rd.rost,rd.vesd,i.osoco,i.uslpr,rd.dataz,rd.polj,z0.cod_tf "+
      "i.fiootec,i.mrotec,i.telotec,i.grotec,i.photec,i.vredotec,i.votec,p.name_mr,p.prof, "+
      "rd.eko,rd.rub,rd.predp,p.ter_liv,p.region_liv,rd.cdiagt,rd.cvera,rd.dataosl,rd.osp "+
"FROM patient p,p_rd_sl rd,p_rd_inf i,p_sign s,p_vizit v,n_az9 a,n_n00 n,s_vrach vr,n_z00 z0 "+
"WHERE p.npasp = 16164 and p.cpol_pr=n.pcod and "+
"p.npasp=s.npasp and p.sgrp=a.pcod and rd.npasp=p.npasp "+
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
	public List<RdVizit> getRdVizit() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT v.datap,v.diag,v.mobs,v.rezult,v.n_sp,s.cod_sp,c.cod_tf,d.fam,d.im,d.ot,rd.npasp,v.id FROM p_rd_sl rd,p_vizit_amb v, n_s00 s,n_p0c c,s_vrach d where rd.npasp=v.npasp and v.cdol=s.pcod and v.cpos=c.pcod and v.n_sp=d.pcod")) {
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select d.id_pvizit,d.id_pos,d.npasp,d.srok,d.ves,d.art1,d.art2,d.art3,d.art4,d.ball,d.hdm,d.spl,d.chcc,d.polpl,d.predpl,d.serd,d.serd1,d.oteki from p_rd_din d,p_rd_sl rd where d.id_pvizit=rd.id_pvizit")) {
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
	public List<StringClassifier> get_n_s00(int clpu)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select distinct s_mrab.cdol as pcod,n_s00.name as name from s_users join s_mrab on (s_users.clpu=s_mrab.clpu) join n_s00 on (s_mrab.cdol=n_s00.pcod) where s_mrab.clpu= ?", clpu)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_c00(int npasp, int pcod)
			throws KmiacServerException, TException {
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select n_c00.pcod as pcod,p_disp.diag as name  from p_disp join n_c00 on (p_disp.diag=n_c00.pcod) where p_disp.npasp = ? and p_disp.pcod = ?", npasp, pcod)) {
		return rsmStrClas.mapToList(acrs.getResultSet());
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
	}

	@Override
	public String formfilecsv(KartaBer kb) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		Date p1; Date p2; Date p3; Date p4; Date p5; Date p6; Date p7;
		Date p8; Date p9; Date p10;
		Integer ball1;Integer ball2;Integer ball3;
		Integer ball4;Integer ball5;Integer grk;
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
        String dex2 = null;
        String dex3 = null;
        String dex4 = null;
        String dex5 = null;
        String dex6 = null;
        String dex7 = null;
        String dex9 = null;
        String dex10 = null;
        String dex = null;
        String dak = null;
        String dsost = null;
        String dosl = null;
		AutoCloseableResultSet acrs = null, acrs2 = null;
		//таблица паспортной информации Patient.csv
		StringBuilder sb = new StringBuilder(0x10000);
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
		List<RdPatient> rdPatient = getRdPatient();
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
		j = 0;
		for (RdPatient rdp : rdPatient) {
			j = j+1;
		kod2 =0 ; kod3 =0 ; kod4 = 0; kod5 = 0; kod6 = 0; 
		kod7 = 0; kod8 = 0; kod9 = 0;	
		ball1 = 0;ball2 = 0;ball3 = 0;
		ball4 = 0;ball5 = 0;grk = 0;
		k1 = 0; k2 = 0; k3 = 0; k4 = 0;
		k5 = 0; k6 = 0; k7 = 0; k8 = 0;k10 = 0; k9 = 0;
       dex1 = "";dex2 = ""; dex3 = ""; dex4 = ""; dex5 = "";
        dex6 = ""; dex7 = "";dex9 = ""; dex10 = "";
        dex = "";  dak = ""; dsost = ""; dosl = "";
		p7 = new Date(rdp.datar);
		if (rdp.grk == "I") grk = 1;
		if (rdp.grk == "II") grk = 2;
		if (rdp.grk == "III") grk = 3;
		if (rdp.grk == "IV") grk = 4;
		p1 = new Date(rdp.datay);
		p2 = new Date(rdp.dataz);
		p3 = new Date(rdp.datazs);
		p4 = new Date(rdp.dataz);
		p5 = new Date(rdp.datasn);
		p6 = new Date(rdp.datam);
		p8 = new Date(System.currentTimeMillis());
		risk = 0;kontr = 0;	rod = 0;
		grot = 0; hsm = 0; hdr = 0; hal = 0;
		pr = 0; ek = 0; ru = 0;
		if (rdp.prrod != "") rod =1;
		if (rdp.kont) kontr = 1;
		if (rdp.predp) pr = 1;
		if (rdp.eko) ek = 0;
		if (rdp.rub) ru = 0;
		if (rdp.grotec == "I") grot = 1;
		if (rdp.grotec == "II") grot = 2;
		if (rdp.grotec == "III") grot = 3;
		if (rdp.grotec == "IV") grot = 4;
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
		if (rdp.vred.charAt(0) == '1') kod2= kod2+1;
		if (rdp.vred.charAt(1) == '1') kod2= kod2+2;
		if (rdp.vred.charAt(2) == '1') kod2= kod2+4;
		if (rdp.vred.charAt(3) == '1') kod2= kod2+8;

		Date dgrisk = null;
		sb4.append(String.format("%d;%d;%d;%4$td.%4$tm.%4$tY;%d;%d;%7$td.%7$tm.%7$tY;%8$td.%8$tm.%8$tY;%s %s %s;%10$td.%10$tm.%10$tY;%s;%12$td.%12$tm.%12$tY;%d;%d;%d;%d;%d;%d;%s;%s;%s;%d;%s;%d;%d;%d;;%d;%s;%s;%30$td.%30$tm.%30$tY;%d;%d;%d;%d;%d;%d;%s;%s;;;%d;%42$td.%42$tm.%42$tY;%d;%d;%d;%d;%47$td.%47$tm.%47$tY;%d", j,rdp.npasp,rdp.deti,p1,rdp.yavka1,risk,dgrisk,p3,rdp.fam,rdp.im,rdp.ot,p4,rdp.telm,rdp.datasn,rdp.shet,rdp.kolrod,rdp.abort,rdp.polj,rdp.vozmen,rdp.prmen,rdp.fiootec,rdp.mrabotec,rdp.telotec,grot,rdp.photec,hsm,hal,hdr,rdp.vozotec,rdp.mrab,rdp.prof,rdp.datam,kontr,rdp.dsp,rdp.dsr,rdp.dtroch,rdp.cext,rdp.indsol,rdp.vitae,rdp.allerg,rdp.ishod,p5,pr,rdp.diag,rdp.cvera,ek,rdp.dataosl,ru));		
		sb.append(String.format("%d;%s;%s;%s;%5$td.%5$tm.%5$tY;%s %s;%d;%d;%d;%s;%s;%s;%s%s;%s;%d;%d;%d;%d;%d;%d;%s;%s;%s;%s %s %s;%d;%s", rdp.uid, rdp.fam, rdp.im, rdp.ot, p7,rdp.docser,rdp.docnum,rdp.terpr,rdp.oblpr,rdp.tawn,rdp.street,rdp.house,rdp.flat,rdp.poms_ser,rdp.poms_nom,rdp.dog,rdp.stat,rdp.lpup,rdp.terp,rdp.terpr,rdp.oblpr,rdp.ftawn,rdp.fstreet,rdp.fhouse,rdp.fflat,rdp.fstreet,rdp.fhouse,rdp.fflat,grk,rdp.rez));		
 		
		//Con_diagn.csv
			try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("select d.diag,c.dex,d.d_vz,d.xzab,d.disp,s.name,da.datad from p_diag d,n_c00 c,n_s00 s, p_diag_amb da  where d.diag = c.pcod and d.cdol_ot = s.pcod  and da.id = d.id_diag_amb and d.npasp=?",rdp.npasp)) {
				if (acrs21.getResultSet().next()){
					p9 = new Date(acrs21.getResultSet().getLong(7));
					p10 = new Date(acrs21.getResultSet().getLong(7));
					if (acrs21.getResultSet().getInt(5) == 1) disp1 = 0; else disp1 = 0;
					if (acrs21.getResultSet().getInt(4) == 1) hr = 1; else hr = 0;
					sb6.append(String.format("%d;%d;%d;%4$td.%4$tm.%4$tY;%s;%s;%7$td.%7$tm.%7$tY;%s;%d", j,rdp.npasp,rdp.npasp,p1,acrs21.getResultSet().getString(6),acrs21.getResultSet().getString(1),p2,acrs21.getResultSet().getString(6),disp1,hr));		
	//				dex = dex + ' '+ acrs1.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex1"){ dex1 =dex1 + ' '+ acrs21.getResultSet().getString(1);
			k1 = k1+1; k2 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex2") {dex2 =dex2 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k5 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex3") {dex3 =dex3 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k4 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex4") {dex4 =dex4 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k6 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex5") {dex5 =dex5 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k3 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex6") {dex6 =dex6 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k7 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex7") {dex7 =dex7 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1;k8 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex9") {dex9 =dex9 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k9 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dex10") {dex10 =dex10 + ' '+ acrs21.getResultSet().getString(1);	
			k1 = k1+1; k10 = 1;}	
			if (acrs21.getResultSet().getString(2) == "dak") dak =dak + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dsost") dsost =dsost + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dosl") dosl =dosl + ' '+ acrs21.getResultSet().getString(1);
				}
				if (acrs21.getResultSet().getString(1).charAt(0) == 'N') kod2 = 1;
				if (acrs21.getResultSet().getString(1) == "O21") kod5 = kod5+1;			
				if (acrs21.getResultSet().getString(1) == "O44") kod5 = kod5+2;			
				if (acrs21.getResultSet().getString(1) == "O45") kod5 = kod5+2;			
				if (acrs21.getResultSet().getString(1) == "O23.0") kod5 = kod5+4;			
				if (acrs21.getResultSet().getString(1) == "O24") kod5 = kod5+8;			
				if (acrs21.getResultSet().getString(1) == "O30") kod5 = kod5+16;			
				if (acrs21.getResultSet().getString(1) == "O32") kod5 = kod5+32;			
				if (acrs21.getResultSet().getString(1) == "O36.0") kod5 = kod5+64;			
				if (acrs21.getResultSet().getString(1) == "O99.0") kod5 = kod5+128;			
				if (acrs21.getResultSet().getString(1) == "O13") kod5 = kod5+256;			
				if (acrs21.getResultSet().getString(1) == "O14") kod5 = kod5+512;			
				if (acrs21.getResultSet().getString(1) == "O15") kod5 = kod5+1024;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I11") kod7 =  kod7 + 1;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I12") kod7 =  kod7 + 2;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I50") kod7 =  kod7 + 4;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I49") kod7 =  kod7 + 8;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I34") kod7 =  kod7 + 16;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I35") kod7 =  kod7 + 32;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "I80") kod7 =  kod7 + 64;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "N11") kod7 =  kod7 + 128;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "N03") kod7 =  kod7 + 256;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "N18") kod7 =  kod7 + 512;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "E10") kod8 =  kod8+1;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "E03") kod8 =  kod8+2;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "E04") kod8 =  kod8+4;
                ves = rdp.vesd;
				if (rdp.rost !=0) {ves = ves/rdp.vesd/rdp.vesd*100100;
				if (ves>= 36)kod8 = kod8 + 8;}
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "E27") kod8 =  kod8+16;
				if (acrs21.getResultSet().getString(1).substring(0, 1) == "D6") kod8 =  kod8+32;
				if (acrs21.getResultSet().getString(1).substring(0, 1) == "B1") kod8 =  kod8+64;
				if (acrs21.getResultSet().getString(1) == "K72.1") kod8 =  kod8+128;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "J96") kod8 =  kod8+256;
				if (acrs21.getResultSet().getString(1).charAt(0) == 'F') kod8 =  kod8+512;
				if (acrs21.getResultSet().getString(1).substring(0, 1) == "A1") kod8 =  kod8+1024;
				if (acrs21.getResultSet().getString(1).substring(0, 2) == "B20") kod8 =  kod8+2048;
				if (acrs21.getResultSet().getString(1) == "M95.5") kod8 =  kod8+4098;
				if (acrs21.getResultSet().getString(1).substring(0, 1) == "M3") kod8 =  kod8+8196;
				
				if (k1 >=3) kod6 = kod6+1;
				if ((k2+k3+k4+k5+k6+k7+k8+k9+k10)>=3) kod6 = kod6 + 2;
				if (k3>0) kod6 = kod6 + 4;
				if (k4>0) kod6 = kod6 + 8;
				if (rdp.vozmen >= 16) kod6 = kod6 + 16;
				if (rdp.prmen >=34) kod6 = kod6 + 32;
				if (rdp.vozmen >= 16){ if (rdp.prmen >= 34 ) kod6 = kod6 + 64;}
				if (rdp.polj <= 14) kod6 = kod6 + 128;
				if (rdp.abort >= 4) kod6 = kod6 + 256;
				if (kod2 == 1) kod6 = kod6 + 512;
				if (rdp.kont) kod6 = kod6 + 1024;
				} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
			try (AutoCloseableResultSet acrs21 = sse.execPreparedQuery("SELECT d.dspos,c.dex from p_rd_din d,n_c00 c where c.dex is not null and d.npasp=?",rdp.npasp)) {
				if (acrs21.getResultSet().next()){
//					dex = dex + ' '+ acrs1.getResultSet().getString(0);	
			if (acrs21.getResultSet().getString(2) == "dex1") dex1 =dex1 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex2") dex2 =dex2 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex3") dex3 =dex3 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex4") dex4 =dex4 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex5") dex5 =dex5 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex6") dex6 =dex6 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex7") dex7 =dex7 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex9") dex9 =dex9 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dex10") dex10 =dex10 + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dak") dak =dak + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dsost") dsost =dsost + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(2) == "dosl") dosl =dosl + ' '+ acrs21.getResultSet().getString(1);	
			if (acrs21.getResultSet().getString(1).charAt(0) == 'N') kod2 = 1;
			if (acrs21.getResultSet().getString(1) == "O21") kod5 = kod5+1;			
			if (acrs21.getResultSet().getString(1) == "O44") kod5 = kod5+2;			
			if (acrs21.getResultSet().getString(1) == "O45") kod5 = kod5+2;			
			if (acrs21.getResultSet().getString(1) == "O23.0") kod5 = kod5+4;			
			if (acrs21.getResultSet().getString(1) == "O24") kod5 = kod5+8;			
			if (acrs21.getResultSet().getString(1) == "O30") kod5 = kod5+16;			
			if (acrs21.getResultSet().getString(1) == "O32") kod5 = kod5+32;			
			if (acrs21.getResultSet().getString(1) == "O36.0") kod5 = kod5+64;			
			if (acrs21.getResultSet().getString(1) == "O99.0") kod5 = kod5+128;			
			if (acrs21.getResultSet().getString(1) == "O13") kod5 = kod5+256;			
			if (acrs21.getResultSet().getString(1) == "O14") kod5 = kod5+512;			
			if (acrs21.getResultSet().getString(1) == "O15") kod5 = kod5+1024;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I11") kod7 =  kod7 + 1;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I12") kod7 =  kod7 + 2;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I50") kod7 =  kod7 + 4;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I49") kod7 =  kod7 + 8;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I34") kod7 =  kod7 + 16;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I35") kod7 =  kod7 + 32;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "I80") kod7 =  kod7 + 64;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "N11") kod7 =  kod7 + 128;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "N03") kod7 =  kod7 + 256;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "N18") kod7 =  kod7 + 512;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "E10") kod8 =  kod8+1;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "E03") kod8 =  kod8+2;
			if (acrs21.getResultSet().getString(1).substring(0, 2) == "E04") kod8 =  kod8+4;
				}
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException();
			}
			dex = dex1+' '+dex2+' '+dex3+' '+dex4+' '+dex5+ ' '+dex6+' '+dex7 +' '+dex9+' '+dex10;
			sb3.append(String.format("%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",j,rdp.npasp,dex1,dex2,dex3,dex4,dex5,dex6,dex7,dex9,dex10,dex,dak,dsost,dosl));	
		    sb5.append(String.format("%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;;%18$td.%18$tm.%18$tY",j,rdp.npasp,rdp.obr,rdp.sem,rdp.rost,rdp.vesd,kod2,rdp.osoco,rdp.uslpr,kod5,kod6,kod7,kod8,rdp.osp,ball1,ball2,ball3,ball4,p8));
//			acrs21.close();
		}
		//Vizit.csv
		List<RdVizit> rdVizit = getRdVizit();
		ball1 = 0;ball2 = 0;ball3 = 0;
		ball4 = 0;
		for (RdVizit rvz : rdVizit) {
			p1 = new Date(rvz.dv);
			sb1.append(String.format("%d;%d;%3$td.%3$tm.%3$tY;%d;%s %s %s;%s;%d;%d;%d;%d", rvz.uid, rvz.npasp, p1, rvz.sp, rvz.famwr,rvz.imwr,rvz.otwr,rvz.diag,rvz.mso,rvz.rzp,rvz.aim,rvz.npr));		
		}
		// Con_vizit.scv
		List<RdConVizit> rdConVizit = getRdConVizit();
		for (RdConVizit rcv : rdConVizit) {
			j = j+1;
			Integer ot = 0;
			if (rcv.oteki == 0 ) ot = 1;
			sb2.append(String.format("%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;;;%d;%d;%d;%d;%d;%d;%d;%d", j, rcv.uiv,rcv.npasp, rcv.ves, rcv.ned,rcv.hdm,rcv.spl,rcv.lcad,rcv.ldad,rcv.rcad,rcv.rdad,ball1,ball2,ball3,ball4,rcv.oj,rcv.chcc,rcv.polpl,rcv.predpl,rcv.serd,rcv.serd1,ot,rcv.oteki));		
		}
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
	public List<Pobost> getPobost(int npasp, String diag)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_obost where npasp = ? and diag = ?", npasp, diag)) {
			return rsmPobost.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public int AddPobost(Pobost pbst) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_obost (npasp, id_pdiag, diag, sl_obostr, sl_hron, cod_sp, cdol, dataz, id_obr) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, pbst, pobostTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}

	}

	@Override
	public void UpdatePobost(Pobost pbst) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("update p_obost set sl_obostr = ?, sl_hron = ? where id = ? ", false, pbst, pobostTypes, 4, 5, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}
	@Override
	public void DeletePobost(int pobost_id) throws KmiacServerException, TException{
	try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPrepared("delete from p_obost where id = ? ", false, pobost_id);
		sme.setCommit();
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	} catch (InterruptedException e1) {
		e1.printStackTrace();
		throw new KmiacServerException();
	}
	}

	@Override
	public double getStoim(String kateg, int prv, String cdol) throws TException {   
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select stoim from n_stoim_p where kateg = ? and prv = ? and cdol = ? ", kateg, prv, cdol)) {
			if (acrs.getResultSet().next())
				return acrs.getResultSet().getDouble(1);
			else
				return 0;
		} catch (SQLException e) {
			throw new TException(e);
		}		
	}

	@Override
	public Pmer getDispMer(int id_pmer) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_mer where id = ? ", id_pmer)) {
			return rsmPmer.map(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException(); 
		}
	}

	@Override
	public List<IntegerClassifier> get_n_tip() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name from n_tip where pcod != 9 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_m00() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name_s as name from n_m00 where pr='Л' ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException(); 
		}
	}

	@Override
	public List<Integer> AddCGosp(Cgosp cgsp) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction(); 
				AutoCloseableResultSet acrs = sse.execPreparedQuery("select ngosp, id from c_gosp where (npasp = ?) and (n_org = ?) and (diag_n = ?) and (dataz = ?) ", cgsp.npasp, cgsp.n_org, cgsp.diag_n, cgsp.dataz)) {
			if (acrs.getResultSet().next()) {
				List<Integer> ret = new ArrayList<>();
				ret.add(acrs.getResultSet().getInt(1));
				ret.add(acrs.getResultSet().getInt(2));
				return ret;
			} else {
			sme.execPreparedT("insert into c_gosp (npasp, nist, naprav, diag_n, named_n, dataz, vid_st, n_org, pl_extr, datap, vremp, cotd, diag_p, named_p, cotd_p, dataosm, vremosm) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, cgsp, cgospTypes, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
			List<Integer> ret = new ArrayList<>();
			ret.add(sme.getGeneratedKeys().getInt("id"));
			ret.add(sme.getGeneratedKeys().getInt("ngosp"));
			sme.setCommit();
			return ret;
			}
			}
		 catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddCotd(Cotd cotd) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction(); 
		AutoCloseableResultSet acrs = sse.execPreparedQuery("select id from c_otd where id_gosp = ? ", cotd.id_gosp)) {
			if (acrs.getResultSet().next()) {
				return acrs.getResultSet().getInt("id");
			} else {
			sme.execPreparedT("insert into c_otd (id_gosp, nist, cotd, dataz, stat_type) VALUES (?, ?, ?, ?, ?) ", true, cotd, cotdTypes, 1, 2, 3, 4, 5);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PokazMet> getPokazMet(String c_nz1, int cotd)
			throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT n_ldi.pcod, n_ldi.name_n FROM n_ldi JOIN s_ot01 ON (s_ot01.pcod=n_ldi.pcod) WHERE s_ot01.c_nz1 = ? AND s_ot01.cotd = ? ORDER BY n_ldi.pcod ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, c_nz1, cotd)) {
			return rsmPokazMet.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}



	@Override
	public void DeleteRdDin(int id_pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_din WHERE id_pos = ? ", false, id_pos);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}


	@Override
	public List<IsslInfo> getIsslInfoPokaz(int nisl)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod as pokaz, n_ldi.name_n as pokaz_name, p_rez_l.zpok as rez, p_isl_ld.datav, p_rez_l.pcod_m as op_name,n_nsikodrez.name as rez_name, n_p0e1.gruppa as gruppa, p_isl_ld.kodotd, p_isl_ld.datap, p_isl_ld.diag " +
				"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) left join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) " +
				"join n_nsikodrez on (n_nsikodrez.kod_rez=p_rez_l.kod_rez) "+
				"join n_p0e1 on (n_ldi.c_p0e1=n_p0e1.pcod) "+
				"where p_isl_ld.nisl = ? " +
				"union		" +
				"select p_isl_ld.nisl,n_ldi.pcod as pokaz, n_ldi.name_n as pokaz_name, n_arez.name as rez, p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name, n_p0e1.gruppa as gruppa, p_isl_ld.kodotd, p_isl_ld.datap, p_isl_ld.diag " +
				"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl)  join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)	" +
				"join n_p0e1 on (n_ldi.c_p0e1=n_p0e1.pcod) "+
				"where p_isl_ld.nisl = ?", nisl, nisl))
				{
					return rsmIsslInfo.mapToList(acrs.getResultSet());
				} catch (SQLException e) {
					((SQLException) e.getCause()).printStackTrace();
					throw new KmiacServerException();
			}

	}

	@Override
	public IsslInfo getIsslInfoPokazId(int id_issl)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_ldi.pcod as pokaz, n_ldi.name_n as pokaz_name, p_rez_l.zpok as rez, p_isl_ld.datav " +
				"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) left join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) " +
				"where p_rez_l.id = ? " +
				"union		" +
				"select p_isl_ld.nisl,n_ldi.pcod as pokaz, n_ldi.name_n as pokaz_name, n_arez.name as rez, p_isl_ld.datav	" +
				"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl)  join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) left join n_arez  on (n_arez.pcod = p_rez_d.rez)	" +
				"where p_rez_d.id = ? ", id_issl, id_issl ))
				{
			if (acrs.getResultSet().next())
				return rsmIsslInfo.map(acrs.getResultSet());
			else return null;
			} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<P_isl_ld> getIsslInfoDate(int id_pvizitAmb)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select p_isl_ld.*, n_nz1.name as name_pcisl from p_isl_ld inner join n_nz1 on (p_isl_ld.pcisl=n_nz1.pcod) where p_isl_ld.id_pos = ? ", id_pvizitAmb)) 
		{
			return rsmPislld.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	}
	}

	@Override
	public String printDnevVr(int vrach) throws KmiacServerException, TException {
		AutoCloseableResultSet acrs = null, acrs2 = null;
		
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("kart1", ".htm").getAbsolutePath()), "utf-8")) {

			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Посещения врачей поликлиники</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<p align=center>ИНФО МУЗДРАВ<br></p>");
				sb.append("<h3 align=center>Нагрузка по врачам<br></h3>");
				sb.append("<p align=center>Поликлиники</p>");
				sb.append("<br>");
				sb.append("<TABLE BORDER=2>");
				sb.append("<TR>");
				sb.append("<TD rowspan=2 align=center>N п/п.</TD>");
				sb.append("<TD rowspan=2 align=center>Фамилия, имя, отчество</TD>");
				sb.append("<TD rowspan=2 align=center>Должность</TD>");
				sb.append("<TD rowspan=2 align=center>Время</TD>");
//				sb.append("<TD rowspan=2 align=center>Ставок</TD>");
				sb.append("<TD colspan=3 align=center>Посещения в поликлинике</TD>");
				sb.append("<TD colspan=3 align=center>Посещения на дому</TD>");
				sb.append("<TD colspan=3 align=center>Посещения с профцелью</TD>");
				sb.append("<TD colspan=4 align=center>Посещения всего</TD>");
//				sb.append("<TD rowspan=2 align=center>Подпись врача</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				Integer n1 = 0;Integer vr = 0;
				Double vrem = 0.0; 	Double st = 0.0;
				double ppp = 0; Integer ppf = 0; 
				double pdp = 0; Integer pdf = 0; 
				double ppfp = 0; Integer ppff = 0; 
				double pp = 0; Integer pf = 0; Double proc = 0.0;
				double ippp = 0; Integer ippf = 0; 
				double ipdp = 0; Integer ipdf = 0; 
				double ippfp = 0; Integer ippff = 0; 
				double ipp = 0; Integer ipf = 0; 
				Integer codvr = 0; Integer codpol = 0;

				acrs = sse.execPreparedQuery("select count(*),a.cdol,a.mobs,a.opl,a.cpos,v.cobr,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,p.jitel,v.id,c0.name,v.cpol,v.datao,a.cod_sp "+
//                                                         1       2      3     4      5      6                        7     8      9   10   11     12   13       14    15      16       17
				"from p_vizit_amb a,p_vizit v,patient p,s_vrach s,n_s00 c0 "+
"where a.id_obr=v.id and a.npasp=p.npasp and a.cod_sp=s.pcod and a.cdol=c0.pcod "+
"group by a.id_obr,a.cdol,c0.name,a.mobs,a.opl,a.cpos,v.cpol,v.cobr,v.datao,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,v.id,p.jitel,a.cod_sp "+
"order by a.cod_sp,s.fam,s.im,s.ot,a.cdol,a.id_obr,v.id,p.jitel");
				if (acrs.getResultSet().next()) {
                codvr = acrs.getResultSet().getInt(17);
				while (acrs.getResultSet().next()){
				if (codvr == acrs.getResultSet().getInt(17)){
				if(acrs.getResultSet().getInt(3)==1) {ppf = ppf + acrs.getResultSet().getInt(1);
				ippf = ippf + acrs.getResultSet().getInt(1);}
				if(acrs.getResultSet().getInt(3)==2) {pdf = pdf + acrs.getResultSet().getInt(1);
				ipdf = ipdf + acrs.getResultSet().getInt(1);}
				if(acrs.getResultSet().getInt(6)!=1) {ppfp = ppfp + acrs.getResultSet().getInt(1);
				ippfp = ippfp + acrs.getResultSet().getInt(1);}
				pf = pf + acrs.getResultSet().getInt(1);
				ipf = ipf + acrs.getResultSet().getInt(1);
				}
				}
				n1 = n1 + 1;
				//посчитать процент
				acrs2 = sse.execPreparedQuery("select pospol*prpol,posprof*prprof,posdom*prdom,rabden,koldn,colst "+
 //                                                              1              2            3       4    5     6  
				"from n_n63 where codpol=? and codvrdol=? ",codpol,acrs.getResultSet().getString(2));
				if (acrs2.getResultSet().next()) {
				ppp = acrs2.getResultSet().getDouble(1);
				pdp = acrs2.getResultSet().getDouble(3);
				ppfp = acrs2.getResultSet().getDouble(2);
				}
				acrs2.close();
				sb.append(String.format("<td> %d/TD>",n1));
				sb.append(String.format("<td> %s %s %s</TD>",acrs.getResultSet().getString(9),acrs.getResultSet().getString(10),acrs.getResultSet().getString(11)));
				sb.append(String.format("<TD> %s</TD>",acrs2.getResultSet().getString(2)));
				acrs2 = sse.execPreparedQuery("select sum(timep),sum(timed),sum(timeda),sum(timeprf),sum(timepr) from s_tabel where pcod = ?",codvr);
				if (acrs.getResultSet().next()) {
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3)+acrs2.getResultSet().getDouble(4))));
//				sb.append(String.format("<TD>%.2f ставок</TD>",acrs2.getResultSet().getInt(9),acrs2.getResultSet().getInt(10)));
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*ppp)));//план в поликлинике
				ippp = ippp+acrs2.getResultSet().getDouble(1)*ppp;
				pp = pp+acrs2.getResultSet().getDouble(1)*ppp;
				ipp = ipp+acrs2.getResultSet().getDouble(1)*ppp;
 			    sb.append(String.format("<TD> %d</TD>",ppf));//факт в поликлинике
				proc= ppf*100/(acrs2.getResultSet().getDouble(1)*ppp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(2)*pdp)));//план на дому
				ipdp = ipdp+acrs2.getResultSet().getDouble(2)*pdp;
				pp = pp+acrs2.getResultSet().getDouble(2)*pdp;
				ipp = ipp+acrs2.getResultSet().getDouble(2)*pdp;
 			    sb.append(String.format("<TD> %d</TD>",pdf));//факт на дому
				proc= pdf*100/(acrs2.getResultSet().getDouble(2)*pdp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(4)*ppfp)));//план профцель
				ipdp = ipdp+acrs2.getResultSet().getDouble(4)*ppfp;
				pp = pp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
				ipp = ipp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
			    sb.append(String.format("<TD> %d</TD>",ppff));//факт профцель
				proc= ppff*100/(acrs2.getResultSet().getDouble(4)*ppfp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",pp));//план всего
			    sb.append(String.format("<TD> %d</TD>",pf));//факт всего
				proc= pf*100/(acrs2.getResultSet().getDouble(4)*pp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				}
				acrs2.close();
				sb.append("<TD> </TD>");
				sb.append("</TR>");
				pp=0; ppf=0; pdf=0; ppff=0;
				}
				acrs.close();
				acrs2 = sse.execPreparedQuery("select sum(pospol*prpol*colst),sum(posprof*prprof*colst),sum(posdom*prdom*colst),rabden,koldn "+ 
						"from n_n63 where codpol=? group by rabden,koldn ",codpol);
				if (acrs2.getResultSet().next()) {
//разместить строку ИТОГО	
					sb.append("<td> /TD>");
					sb.append("<td> ИТОГО</TD>");
					sb.append("<TD> </TD>");
					sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500)));//план в поликлинике
	 			    sb.append(String.format("<TD> %d</TD>",ippf));//факт в поликлинике
					proc= ppf*100/((acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план на дому
	 			    sb.append(String.format("<TD> %d</TD>",ipdf));//факт на дому
					proc= pdf*100/((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план профцель
				    sb.append(String.format("<TD> %d</TD>",ippff));//факт профцель
					proc= ppff*100/((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					ipp = (acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3))*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500;
					sb.append(String.format("<TD>%.2f </TD>",ipp));//план всего
				    sb.append(String.format("<TD> %d</TD>",ipf));//факт всего
					proc= pf*100/ipp;
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				}
				sb.append("</TABLE>");
			sb.append("</body>"); 
			sb.append("</html>");
			
			osw.write(sb.toString());
			return path;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		} finally {
			if (acrs != null)
				acrs.close();
			if (acrs2 != null)
				acrs2.close();
		}
//			return null;
	}

	@Override
	public Pdisp getPdisp(int npasp, String diag, int cpol)
			throws KmiacServerException, PdispNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_disp where npasp = ? and diag = ? and pcod = ?", npasp, diag, cpol)) {
			if (acrs.getResultSet().next())
				return rsmPdisp.map(acrs.getResultSet());
			else
				throw new PdispNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public PdiagZ getPdiagZ(int npasp, String diag)
			throws KmiacServerException, PdiagNotFoundException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT d.*, c.name AS nameC00 FROM p_diag d LEFT JOIN n_c00 c ON (c.pcod = d.diag) WHERE d.npasp = ? and d.diag = ?", npasp, diag)) {
			if (acrs.getResultSet().next())
				return rsmPdiagZ.map(acrs.getResultSet());
			else
				throw new PdiagNotFoundException();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> getPdiagInfo(int npasp) throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select named as pcod, diag as name from p_diag where npasp = ? ", npasp)) 
		{
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	}
	}

	@Override
	public List<PdiagZ> getPdiagZInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_diag where npasp = ? ", npasp)) 
		{
			return rsmPdiagZ.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void deleteDiag(int npasp, String diag, int pcod, int idDiagAmb) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction();
				AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id FROM p_diag_amb WHERE npasp = ? and diag = ?", npasp, diag)){
			if (acrs.getResultSet().next()){
				System.out.println("pos is not null");
				//JOptionPane.showMessageDialog(this, "");
			}else{			
				sme.execPrepared("DELETE FROM p_diag WHERE npasp = ? AND diag = ? ", false, npasp, diag);
				sme.execPrepared("DELETE FROM p_disp WHERE npasp = ? AND diag = ? AND pcod = ?", false, npasp, diag, pcod);
				sme.setCommit();
			}
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}		
	}

	@Override
	public String printAnamZab(int id_pvizit) throws KmiacServerException,
			TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("anam", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title> История заболевания</title>");
				sb.append("</head>");
				sb.append("<body>");
				
				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", id_pvizit); 
				if (acrs.getResultSet().next()) {
					if (acrs.getResultSet().getString(1)!=null)
						{sb.append("<br><b>	Анамнез заболевания</b><br>");
						sb.append(String.format(" %s.", acrs.getResultSet().getString(1)));
						sb.append(String.format("<p align=\"right\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
						}
				}				
			acrs.close();
			osw.write(sb.toString());
			return path;
			}
		 catch (SQLException e) {
			 ((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public String printSpravNetrud(SpravNetrud sn) throws KmiacServerException,
			TException {
	String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("spravNetrud", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title> Справка о временной нетрудоспособности</title>");
				sb.append("</head>");
				sb.append("<body>");
				sb.append("<div><table cellpadding=\"5\" cellspacing=\"0\">");
				sb.append("<tr valign=\"top\">");
				sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; \" width=\"800px\">");
				sb.append("<div align=\"right\" style font: 11px times new roman;>");
				sb.append("<font size=2 color=black>Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</font></div>");
				sb.append("<br>");
				sb.append("<div style=\"width:170px; float:left;\"><font size=2 color=black>Министерство здравоохранения ");
				sb.append("<br> Российской федерации<br>");
				sb.append(String.format("<br> %s <br> %s</font></div>", sn.cpodr_name, sn.clpu_name));
				sb.append("<div style=\"width:90px; float:right;\">");
				sb.append("<font size=2 color=black>Форма № 095/у");
				sb.append("<br> Утверждена Минздравом СССР<br>04.10.80, №1030</font></div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h3 align=center>Контрольный талон к справке №____</h3>");
				sb.append("<font size=3 color=black>Дата выдачи \"___\" ______________ 20__г.<br>");
				sb.append(String.format("Фамилия, имя, отчество: %s %s %s<br />", sn.fam, sn.im, sn.oth));
				acrs = sse.execPreparedQuery("select mrab from patient where npasp=?", sn.npasp); 
				if (acrs.getResultSet().next()) 
						sb.append(String.format("Название учебного заведения, детского дошкольного учреждения: %s.<br>", acrs.getResultSet().getString(1)));
					acrs.close();
				sb.append(String.format("Диагноз заболевания %s<br>", sn.diag));
				sb.append("Освобожден с _________________________________ по _____________________<br>");
				sb.append("Освобождение продлено с _________________________________ по _____________________<br>");
				acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
						  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
						  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",sn.getUserId());
									if (acrs.getResultSet().next())
									sb.append(String.format("<br>Фамилия врача, выдающего справку: %s %s %s <br> </font>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
				sb.append("<font size=1 color=black>Примечание: Контрольные талоны служат для учета выданных справок</font>");
				sb.append("</td>");
				sb.append("<td style=\"border: 1px solid white; padding: 5px;\" width=\"200px\">");
				sb.append("<div align=\"right\">");
				sb.append("<font size=2 color=black>Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</font></div>");
				sb.append("<br>");
				sb.append("<div style=\"width:220px; float:left;\"><font size=2 color=black>Министерство здравоохранения ");
				sb.append("<br> Российской федерации<br>");
				sb.append(String.format("<br> %s, %s</font></div>", sn.cpodr_name, sn.clpu_name));
				sb.append("<div  style=\"width:200px; float:right;\" >");
				sb.append("<font size=2 color=black>Медицинская документация<br>Форма № 095/у");
				sb.append("<br> Утверждена Минздравом СССР<br>04.10.80, №1030</font></div>");
				sb.append("<br><br><br><br><br><br><br><br><br>");
				sb.append("<h3 align=center>С П Р А В К А</h3>");
				sb.append("<font size=3 color=black><b><center>о временной нетрудоспособности студента, учащегося техникума, профессионально-технического училища, о болезни, карантине и прочих причинах отсутствия ребенка, посещающего школу, детское дошкольное учреждение (нужное подчеркнуть)</b></center>");
				sb.append("<br> Дата выдачи \"___\" ______________ 20__г.<br>");
				sb.append("Студенту, учащемуся, ребенку, посещаещему дошкольное учреждение (нужное подчеркнуть)___________________________________________________");
				sb.append("____________________________________________________________________");
				sb.append("<br>");
				sb.append(String.format("Фамилия, имя, отчество: %s %s %s<br />", sn.fam, sn.im, sn.oth));
				sb.append(String.format("Дата рождения (год, месяц, для детей до 1 года - день): %1$td.%1$tm.%1$tY <br />", sn.datar));
				sb.append(String.format("Диагноз заболевания %s<br>", sn.diag));
				sb.append("Наличие контакта с инфекционными больными (нет, да, какими)_________________________________</font>");
				sb.append("<br><center> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font size=1 color=black>подчеркнуть, вписать</font></center>");
				sb.append("____________________________________________________________________________________________<br>");
				sb.append("<center><font size=1 color=black>освобожден от занятий, посещений детского дошкольного учреждения</font></center><br>");
				sb.append("<font size=3 color=black>с_______________ по ______________________<br>");
				sb.append("с_______________ по ______________________<br>");
				sb.append("<br>мп</font>");
				sb.append("</td>");
				sb.append("</div>");
				sb.append("</body>");
				sb.append("</html>");
				
				
			osw.write(sb.toString());
			return path;
			}
		catch (SQLException e) {
			 ((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		 catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	
	}

	@Override
	public List<IntegerClassifier> get_n_p0c() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name  from n_p0c order by pcod ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException(); 
		}

	}

	@Override
	public List<IntegerClassifier> get_n_ap0() throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name  from n_ap0 where ap = 1 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException(); 
		}
	}

	@Override
	public String printSprBass(int npasp, int pol) throws KmiacServerException,
			TException {
		try {
			return (String) serverManager.instance.getServerById(21).executeServerMethod(2101, npasp, pol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
		


	}

	@Override
	public String get_n_mkb(String pcod)
			throws KmiacServerException, TException {
		String	mkb = null;
			try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select pcod, name  from n_c00 where pcod = ? ", pcod)) {
				if (acrs.getResultSet().next())
				mkb = acrs.getResultSet().getString(2);
			} catch (SQLException e) {
				((SQLException) e.getCause()).printStackTrace();
				throw new KmiacServerException(); 
			}
			return mkb;
	}

	@Override
	public RdDinStruct getRdDinInfo(int id_Pvizit, int npasp, int id_pos)
			throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_rd_din  where id_pvizit = ? and npasp = ? and id_pos = ? ", id_Pvizit, npasp, id_pos)) {
			if (acrs.getResultSet().next())
			return rsmRdDin.map(acrs.getResultSet());
			else
				throw new KmiacServerException("rd inf not found");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}

	}
}

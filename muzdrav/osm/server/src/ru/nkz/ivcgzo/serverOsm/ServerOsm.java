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
import ru.nkz.ivcgzo.thriftOsm.Prez_d;
import ru.nkz.ivcgzo.thriftOsm.Prez_l;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Protokol;
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
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct1;
import ru.nkz.ivcgzo.thriftOsm.RdVizit;
import ru.nkz.ivcgzo.thriftOsm.Shablon;
import ru.nkz.ivcgzo.thriftOsm.ShablonText;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.Iface;
import ru.nkz.ivcgzo.thriftOsm.Vypis;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class ServerOsm extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<ZapVr, ZapVr._Fields> rsmZapVr;
	@SuppressWarnings("unused")
	private final Class<?>[] zapVrTypes; 
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
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	private final Class<?>[] isslInfoTypes;
	private final TResultSetMapper<Pdisp, Pdisp._Fields> rsmPdisp;
	private final Class<?>[] pdispTypes;
	private final TResultSetMapper<RdSlStruct, RdSlStruct._Fields> rsmRdSl;
	private final Class<?>[] rdSlTypes;
	private final TResultSetMapper<RdSlStruct1, RdSlStruct1._Fields> rsmRdSl1;
	private final Class<?>[] rdSl1Types;
	private final TResultSetMapper<RdInfStruct, RdInfStruct._Fields> rsmRdInf;
	private final Class<?>[] rdInfTypes;
	private final TResultSetMapper<RdDinStruct, RdDinStruct._Fields> rsmRdDin;
	private final Class<?>[] rdDinTypes;
	
	private final TResultSetMapper<RdPatient, RdPatient._Fields> rsmRdPat;
	private final Class<?>[] rdPatientTypes;
	private final TResultSetMapper< RdVizit, RdVizit._Fields> rsmRdViz;
	private final Class<?>[] rdVizitTypes;
	private final TResultSetMapper< RdConVizit, RdConVizit._Fields> rsmRdCV;
	private final Class<?>[] rdConVizitTypes;

	private final TResultSetMapper<Pmer, Pmer._Fields> rsmPmer;
	private final Class<?>[] pmerTypes;
	private final Class<?>[] pnaprTypes;
//	private TResultSetMapper<ZapVr, _Fields> rsmPvizitAmb;
	private final TResultSetMapper<Pobost, Pobost._Fields> rsmPobost;
	private final Class<?>[] pobostTypes;


	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp",       "fam",        "im",         "ot",         "poms_ser",   "poms_nom",   "id_pvizit",  "pol",          "datar",    "datap");
		zapVrTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Date.class, Date.class};
		
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id",          "npasp",       "cpol",        "datao",    "ishod",       "rezult",      "talon",       "cod_sp",      "cdol",       "cuser",       "zakl",       "dataz",    "recomend",   "lech",       "cobr");
		pvizitTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, String.class, String.class, Integer.class};
		
		rsmPvizitAmb = new TResultSetMapper<>(PvizitAmb.class, "id",          "id_obr",      "npasp",       "datap",    "cod_sp",      "cdol",       "diag",       "mobs",        "rezult",      "opl",         "stoim",      "uet",         "datak",    "kod_rez",     "k_lr",        "n_sp",        "pr_opl",      "pl_extr",     "vpom",        "fio_vr",    "dataz",    "cpos",        "cpol",        "kod_ter");
		pvizitAmbTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class, Integer.class, Integer.class};
		
		rsmPdiagAmb = new TResultSetMapper<>(PdiagAmb.class, "id",          "id_obr",      "npasp",       "diag",       "named",      "diag_stat",   "predv",       "datad",    "obstreg",     "cod_sp",      "cdol",       "datap",    "dataot",   "obstot",      "cod_spot",    "cdol_ot",    "vid_tr");
		pdiagAmbTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Boolean.class, Date.class, Integer.class, Integer.class, String.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class};
		
		rsmPdiagZ = new TResultSetMapper<>(PdiagZ.class, "id",          "id_diag_amb", "npasp",       "diag",       "cpodr",       "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "nmvd",        "xzab",        "stady",       "disp",        "pat",         "prizb",       "prizi",       "named",      "nameC00");
		pdiagZTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class};
		
		
		rsmPsign = new TResultSetMapper<>(Psign.class, "npasp",       "grup",       "ph",         "allerg",     "farmkol",    "vitae",      "vred"       );
		psignTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPriem = new TResultSetMapper<>(Priem.class, "id_obr",      "npasp",       "id_pos",      "sl_ob",       "n_is",        "n_kons",      "n_proc",      "n_lek",       "t_chss",     "t_temp",     "t_ad",       "t_rost",     "t_ves",      "t_st_localis", "t_ocenka",   "t_jalob",    "t_status_praesense", "t_fiz_obsl");
		priemTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class,   String.class, String.class, String.class,         String.class};
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
		
		rsmPislld = new TResultSetMapper<>(P_isl_ld.class, "nisl",        "npasp",       "cisl",        "pcisl",      "napravl",     "naprotd",     "datan",    "vrach",       "diag",       "dataz",    "pvizit_id");
		pislldTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, Date.class, Integer.class};
		
		rsmPrezd = new TResultSetMapper<>(Prez_d.class, "id",          "npasp",       "nisl",        "kodisl");
		prezdTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class};
		
		rsmPrezl = new TResultSetMapper<>(Prez_l.class, "id",          "npasp",       "nisl",        "cpok");
		prezlTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class};
		
		rsmMetod = new TResultSetMapper<>(Metod.class, "obst",       "name_obst",  "c_p0e1",      "pcod");
		metodTypes = new Class<?>[] {                  String.class, String.class, Integer.class, String.class};
		
		rsmPokazMet = new TResultSetMapper<>(PokazMet.class, "pcod",       "name_n");
		pokazMetTypes = new Class<?>[] {                     String.class, String.class};
		
		rsmPokaz = new TResultSetMapper<>(Pokaz.class, "pcod",       "name_n",     "stoim",      "c_p0e1",      "c_n_nz1");
		pokazTypes = new Class<?>[] {                  String.class, String.class, Double.class, Integer.class, String.class};
		
		rsmAnamZab = new TResultSetMapper<>(AnamZab.class, "id_pvizit",   "npasp",       "t_ist_zab");
		anamZabTypes = new Class<?>[] {                    Integer.class, Integer.class, String.class};
		
		isslInfoTypes = new Class<?>[] {                     Integer.class, Integer.class, String.class, String.class, String.class, String.class, Date.class};
																																
		rsmPdisp = new TResultSetMapper<>(Pdisp.class, "id_diag",     "npasp",       "id",          "diag",       "pcod",        "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "sob",         "sxoch");
		pdispTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Boolean.class, Boolean.class};
	
		rsmRdSl = new TResultSetMapper<>(RdSlStruct.class, "id",          "npasp",       "datay",    "dataosl",  "abort",       "shet",        "datam",    "yavka1",      "ishod",       "datasn",   "datazs",   "kolrod",      "deti",        "kont",        "vesd",        "dsp",         "dsr",         "dtroch",      "cext",        "indsol",      "prmen",    "dataz",   "datasert",  "nsert",      "ssert",      "oslab",      "plrod",       "prrod",      "vozmen",      "oslrod",      "polj",        "dataab",   "srokab",      "cdiagt",      "cvera",       "id_pvizit",     "rost");
		rdSlTypes = new Class<?>[] {                       Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Boolean.class, Double.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmRdSl1 = new TResultSetMapper<>(RdSlStruct1.class, "id",          "npasp",       "datay",    "dataosl",  "abort",       "shet",        "datam",    "yavka1",      "ishod",       "datasn",   "datazs",   "kolrod",      "deti",        "kont",        "vesd",        "dsp",         "dsr",         "dtroch",      "cext",        "indsol",      "prmen",    "dataz",   "datasert",  "nsert",      "ssert",      "oslab",      "plrod",       "prrod",      "vozmen",      "oslrod",      "polj",        "dataab",   "srokab",      "cdiagt",      "cvera",       "id_pvizit",     "rost");
		rdSl1Types = new Class<?>[] {                       Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Boolean.class, Double.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmRdInf = new TResultSetMapper<>(RdInfStruct.class, "npasp",       "obr",        "sem",         "votec",       "grotec",     "photec",     "dataz",    "fiootec",    "mrotec",     "telotec",    "vredotec",    "osoco",       "uslpr");
		rdInfTypes = new Class<?>[] {                        Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, Date.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class};

		rsmRdDin = new TResultSetMapper<>(RdDinStruct.class, "id_rd_sl",    "id_pvizit",   "npasp",       "srok",       "grr",          "ball",        "oj",          "hdm",         "dspos",     "art1",         "art2",        "art3",        "art4",        "spl",         "oteki",       "chcc",        "polpl",       "predpl",      "serd",        "serd1",       "id_pos",      "ves"      );
		rdDinTypes = new Class<?>[] {                        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Double.class};
		
		rsmRdPat = new TResultSetMapper<>(RdPatient.class,"uid",         "fam",       "im",        "ot",        "datar",   "docser",   "docnum",     "tawn",      "street",     "house",     "flat",      "poms_ser",   "poms_num",   "dog",        "stat",       "lpup",        "terp",        "ftawn",        "fstreet",   "fhouse",     "fflat",      "grk",        "rez");
		rdPatientTypes = new Class<?>[]{                   Integer.class,String.class,String.class,String.class,Date.class,String.class,String.class,Integer.class,String.class,String.class,String.class,String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class};
	
		rsmRdViz = new TResultSetMapper<>(RdVizit.class,"uid",         "dv",       "sp",        "famvr",     "imvr",      "otvr",     "diag",       "mso",         "rzp",         "aim",          "npr",       "npasp");
		rdVizitTypes = new Class<?>[]{                   Integer.class, Date.class,String.class,String.class,String.class,String.class,String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
	
//		rsmPdata = new TResultSetMapper<>(RdVizit.class,"uid",         "dv",       "sp",        "famvr",     "imvr",      "otvr",     "diag",       "mso",         "rzp",         "aim",          "npr");
//		rdVizitTypes = new Class<?>[]{                   Integer.class, Date.class,String.class,String.class,String.class,String.class,String.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmRdCV = new TResultSetMapper<>(RdConVizit.class,"uiv",        "uid",       "ves",       "ned",        "lcad",        "ldad",       "rcad",       "rdad",       "rost",       "datar",    "obr",        "sem",          "ososo",       "vrpr" ,      "npasp");
		rdConVizitTypes = new Class<?>[]{                 Integer.class,Integer.class,Double.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Integer.class,Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class };
		
		pnaprTypes = new Class<?>[] {                 Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, String.class};
	
		rsmPmer = new TResultSetMapper<>(Pmer.class, "id",           "npasp",       "id_pdiag",    "diag",       "pmer",        "pdat",     "fdat",     "cod_sp",      "dataz",    "prichina",    "rez",         "cdol",       "id_pvizit",   "id_pos",      "name_pmer");
		pmerTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Date.class, Integer.class, Date.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, String.class};

		rsmPobost = new TResultSetMapper<>(Pobost.class, "id",         "npasp",       "id_pdiag",    "diag",       "sl_obostr",   "sl_hron",     "cod_sp",      "cdol",       "dataz");
		pobostTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Date.class};

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
		// FIXME replace with junit
		
		//Priem priem = new Priem();
//		Pvizit obr = new Pvizit(0, 1, 2, 3, 4, 5, 6, 7, 8, "9", 10, "11", 12, 13);
//		AddPvizit(new Pvizit());
//		obr.setId(AddPvizit(obr));
//		Pvizit obr1 = getPvizit(obr.id);
//		obr = new Pvizit(obr1.id, 2, 3, 4, 5, 6, 7, 8, 9, "10", 11, "12", 13, 14);
//		UpdatePvizit(obr);
//		DeletePvizit(obr.id);
		
//		Psign psign = new Psign(1, "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16");
//		Psign psign = new Psign().setNpasp(5).setPrim_gorm("11");
//		setPsign(psign);
//		psign = getPsign(psign.npasp);
		
//		PvizitAmb pos = new PvizitAmb(2, obr.id, 2, 3, 4, "5", "6", 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
//		AddPvizitAmb(new PvizitAmb());
//		pos.setId(AddPvizitAmb(pos));
//		PvizitAmb pos1 = getPvizitAmb(pos.id);
//		pos = new PvizitAmb(pos1.id, 2, 3, 4, 5, "6", "7", 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
//		UpdatePvizitAmb(pos);
//		DeletePvizitAmb(pos.id);
		
//		PdiagAmb diag = new PdiagAmb(-1, 21, 2, "3", "4", 5, true, 7, 8, 9, "10", 11, 12, 13, 14, "15", 16);
//		diag.setId(AddPdiagAmb(diag));
//		List<PdiagAmb> diag1 = getPdiagAmb(diag.id_obr);
//		if (diag1.size() == 1) {
//			diag = new PdiagAmb(diag1.get(0).id, 22, 3, "4", "5", 6, true, 8, 9, 10, "11", 12, 13, 14, 15, "16", 17);
//			UpdatePdiagAmb(diag);
//			DeletePdiagAmb(diag.id);
//		}
		
//		P_isl_ld pi = new P_isl_ld(-1, 1, 2, "3", 4, 5, 6, 7, "8", 9);
//		pi.nisl = AddPisl(pi);
//		AddPrezd(new Prez_d(1, pi.nisl, "3", 4));
//		AddPrezl(new Prez_l(5, pi.nisl, "7", 8));
		
//		List<Metod> met = getMetod(1);
//		List<PokazMet> pokMet = getPokazMet("50.01.001");
//		List<Pokaz> pok = getPokaz(1, "05");
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
//		List<Pvizit> pvl = getPvizitInfo(2, sdf.parse("01.01.2012").getTime(), sdf.parse("02.02.2012").getTime());
//		List<PvizitAmb> pal = getPvizitAmb(6);
		
//		List<IsslInfo> lii = getIsslInfo(377);
//		AnamZab az = new AnamZab(377, 2, "начало заболевания", "симптом", "отношение больного к заболеванию", "посттравматические синдромы");
//		setAnamZab(az);
		
//		PdiagZ pdz = new PdiagZ().setId_diag_amb(172).setNpasp(2).setDiag("3").setCpodr(4).setD_post(5).setD_grup(6).setIshod(7).setDataish(8).setDatag(9).setDatad(10).setDiag_s("11").setD_grup_s(12).setCod_sp(13).setCdol_ot("14");
//		AddPdiagZ(pdz);
//		List<PdiagZ> ldz = getPdiagZ(pdz.getNpasp());
		
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
	public List<ZapVr> getZapVr(int idvr, String cdol, long datap) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, tal.id_pvizit, tal.datap FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) LEFT JOIN p_vizit pv ON (pv.id = tal.id_pvizit) LEFT JOIN p_vizit_amb pa ON (pa.id_obr = pv.id AND pa.datap = tal.datap) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?) AND pa.id IS NULL", idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<ZapVr> getZapVrSrc(int npasp, int codsp, String cdol) throws KmiacServerException, TException {
		String sql = "SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, CURRENT_DATE AS datap, 0 AS id_pvizit     FROM patient pat WHERE pat.npasp = ? " +
					 "UNION " +
					 "SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, pat.datar, pat.pol, pa.datap,              pv.id AS id_pvizit FROM patient pat LEFT JOIN p_vizit pv ON (pv.npasp = pat.npasp)  LEFT JOIN p_vizit_amb pa ON (pa.id_obr = pv.id)WHERE (pat.npasp = ?) AND (pv.cod_sp = ?) AND (pv.cdol = ?) AND ((pv.ishod IS NULL) OR (pv.ishod < 1)) " +
					 "ORDER BY id_pvizit, datap DESC ";	
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, npasp, npasp, codsp, cdol)) {
			List<ZapVr> zapVrList = rsmZapVr.mapToList(acrs.getResultSet());
			int prevIdObr = -1;
			
			for (int i = 0; i < zapVrList.size(); i++) {
				if (zapVrList.get(i).id_pvizit != prevIdObr)
					prevIdObr = zapVrList.get(i).id_pvizit;
				else
					zapVrList.remove(i);
			}
			
			return zapVrList;
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
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public Pvizit getPvizit(int obrId) throws PvizitNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_vizit WHERE id = ? ", obrId)) {
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
			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ?, cobr = ?, lech = ? WHERE id = ?", false, obr, pvizitTypes, 4, 5, 6, 10, 12, 11, 14, 13, 0);
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
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT pva.*, get_short_fio(svr.fam, svr.im, svr.ot) AS fio_vr FROM p_vizit_amb pva JOIN s_vrach svr ON (svr.pcod = pva.cod_sp) WHERE id_obr = ? ORDER BY pva.datap", obrId)) {
			return rsmPvizitAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit_amb SET id_obr = ?, npasp = ?, datap = ?, cod_sp = ?, cdol = ?, mobs = ?, rezult = ?, opl = ?, stoim = ?, uet = ?, k_lr = ?, n_sp = ?, pr_opl = ?, pl_extr = ?, vpom = ?, cpos = ?, cpol = ?, kod_ter = ? WHERE id = ? ", false, pos, pvizitAmbTypes, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 16, 17, 18, 21, 22, 23, 0);
			sme.execPreparedT("UPDATE p_vizit_amb SET diag = ? WHERE id_obr = ? ", false, pos, pvizitAmbTypes, 6, 1);
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
			sme.execPreparedT("INSERT INTO p_diag_amb (id_obr, npasp, diag, named, diag_stat, predv, datad, obstreg, cod_sp, cdol, datap, dataot, obstot, cod_spot, cdol_ot, vid_tr) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, diag, pdiagAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
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
	public List<PdiagAmb> getPdiagAmb(int obrId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_obr = ? ", obrId)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePdiagAmb(PdiagAmb diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_diag_amb SET id_obr = ?, npasp = ?, diag = ?, named = ?, diag_stat = ?, predv = ?, datad = ?, obstreg = ?, cod_sp = ?, cdol = ?, datap = ?, dataot = ?, obstot = ?, cod_spot = ?, cdol_ot = ?, vid_tr = ? WHERE id = ? ", false, diag, pdiagAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0);
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
			sme.execPreparedT("UPDATE p_priem SET sl_ob = ?, n_is = ?, n_kons = ?, n_proc = ?, n_lek = ?, t_chss = ?, t_temp = ?, t_ad = ?, t_rost = ?, t_ves = ?, t_st_localis = ?, t_ocenka = ?, t_jalob = ?, t_status_praesense = ?, t_fiz_obsl = ? WHERE id_obr = ? AND npasp = ? AND id_pos = ? ", false, pr, priemTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 0, 1, 2);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
//	@Override
//	public int AddPdiagZ(PdiagZ dz) throws KmiacServerException, TException {
//		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			sme.execPreparedT("INSERT INTO p_diag (id_diag_amb, npasp, diag, cpodr, d_vz, d_grup, ishod, dataish, datag, datad, diag_s, d_grup_s, cod_sp, cdol_ot, nmvd, xzab, stady, disp, pat, prizb, prizi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, dz, pdiagZTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
//			int id = sme.getGeneratedKeys().getInt("id");
//			sme.setCommit();
//			return id;
//		} catch (InterruptedException | SQLException e) {
//			throw new KmiacServerException();
//		}
//	}
//
	
	@Override
	public List<StringClassifier> get_n_nz1(int cotd) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT n_nz1.pcod, n_nz1.name FROM n_nz1 JOIN s_ot01 ON (n_nz1.pcod=s_ot01.c_nz1) WHERE s_ot01.cotd = ? ", cotd)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<RdDinStruct> getRdDinInfo(int id_pvizit, int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_rd_din where id_pvizit = ? and npasp = ? ", id_pvizit, npasp)) {
			return rsmRdDin.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}

	}

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
	public RdSlStruct getRdSlInfo(int id_pvizit, int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where id_pvizit = ? and npasp = ? ", id_pvizit, npasp)) {
			if (acrs.getResultSet().next())
				return rsmRdSl.map(acrs.getResultSet());
			else
				throw new KmiacServerException("нет записи");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddRdSl(RdSlStruct rdSl) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_rd_sl (npasp,datay,dataosl,abort,shet,datam,yavka1,ishod,datasn,datazs,kolrod,deti,kont,vesd,dsp,dsr,dtroch,cext,indsol,prmen,dataz,datasert,nsert,ssert,oslab,plrod,prrod,vozmen,oslrod,polj,dataab,srokab,cdiagt,cvera,id_pvizit,rost) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", true, rdSl, rdSlTypes,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36);
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
	public void DeleteRdDin(int id_pos, int iD) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_din WHERE id_pos = ? and iD = ?", false, id_pos,iD);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}
	
//	public void UpdatePvizit(Pvizit obr) throws KmiacServerException, TException {
//		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 13, 12, 0);
//			sme.setCommit();
//		} catch (InterruptedException | SQLException e) {
//			throw new KmiacServerException();
//		}
//	}
	
	@Override
	public void UpdateRdSl(RdSlStruct Dispb) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_sl SET npasp = ?, datay = ?, dataosl = ?, abort = ?, shet = ?, datam = ?, yavka1 = ?, ishod = ?,datasn = ?, datazs = ?,kolrod = ?, deti = ?, kont = ?, vesd = ?, dsp = ?,dsr = ?,dtroch = ?, cext = ?, indsol = ?, prmen = ?,dataz = ?, datasert = ?, nsert = ?, ssert = ?, oslab = ?, plrod = ?, prrod = ?, vozmen = ?, oslrod = ?, polj = ?, dataab = ?, srokab = ?, cdiagt = ?, cvera = ?, rost = ? WHERE id_pvizit = ?", false, Dispb, rdSlTypes, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,36, 35);
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
			sme.execPreparedT("UPDATE p_rd_din SET npasp = ?, srok = ?, grr = ?, ball = ?, oj = ?, hdm = ?, dspos = ?, art1 = ?, art2 = ?, art3 = ?, art4 = ?, spl = ?, oteki = ?, chcc = ?, polpl = ?, predpl = ?, serd = ?, serd1 = ?, ves = ?  WHERE id_pos = ?", false, din, rdDinTypes, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,20);
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
			if (!((SQLException)e.getCause()).getSQLState().equals("23505"))
				throw new KmiacServerException();
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
			sme.execPreparedT("UPDATE p_rd_inf SET obr = ?,sem = ?, votec = ?, grotec = ?, photec = ?, dataz = ?, fiootec = ?, mrotec = ?, telotec = ?, vredotec = ?, osoco = ?, uslpr = ? WHERE npasp = ?", false, inf, rdInfTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0);
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
	public List<PokazMet> getPokazMet(String c_nz1) throws KmiacServerException, TException {
		String sql = "SELECT DISTINCT n_ldi.pcod, n_ldi.name_n FROM n_ldi JOIN s_ot01 ON (s_ot01.pcod=n_ldi.pcod) WHERE s_ot01.c_nz1 = ? ORDER BY n_ldi.pcod ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, c_nz1)) {
			return rsmPokazMet.mapToList(acrs.getResultSet());
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
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_isl_ld (npasp, cisl, pcisl, napravl, naprotd, datan, vrach, diag, dataz, pvizit_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, npisl, pislldTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			int id = sme.getGeneratedKeys().getInt("nisl");
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
	public int AddPrezd(Prez_d di) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl) VALUES (?, ?, ?) ", true, di, prezdTypes, 1, 2, 3);
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
	public int AddPrezl(Prez_l li) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok) VALUES (?, ?, ?) ", true, li, prezlTypes, 1, 2, 3);
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
	public String printIsslMetod(IsslMet im) throws KmiacServerException, TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
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
					if (im.getMesto()!=null)sb.append(String.format("<b>Место: </b>%s<br />", im.getMesto()));
					if (im.getKab()!=null)sb.append(String.format("<b>Каб. №: </b>%s<br />", im.getKab()));
					sb.append("<b>Дата:</b><br />");
					sb.append("<b>Время:</b><br />");
					sb.append("<b>Подготовка:</b><br />");
				sb.append("</td>");
				acrs = sse.execPreparedQuery("SELECT v.fam, v.im, v.ot FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) WHERE u.id = ? ", im.getUserId());
				if (acrs.getResultSet().next()) {
					sb.append("<td style=\"border: 1px solid black; padding: 5px;\" width=\"60%\">");
					sb.append(String.format("<h3>%s<br />", im.getClpu_name()));
					sb.append(String.format("%s<br />", im.getCpodr_name()));
				}
				String vrInfo = String.format("%s %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3));
				acrs.close();
				acrs = sse.execPreparedQuery("SELECT name FROM n_p0e1 WHERE pcod = ?", im.getKodVidIssl());
				if (acrs.getResultSet().next())
					sb.append(String.format("Направление на: %s</h3>", acrs.getResultSet().getString(1)));
				acrs.close();
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom FROM patient WHERE npasp = ? ", im.getNpasp());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("<b>ФИО пациента:</b> %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("<b>Дата рождения:</b> %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
					sb.append(String.format("<b>Адрес:</b> %s, %s<br />", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
				}
				sb.append("<b>Диагноз:</b><br />");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", im.getPvizitId());
				if (acrs.getResultSet().next()) 
					sb.append(String.format("%s <br>", acrs.getResultSet().getString(1)));
				acrs.close();
				sb.append(String.format("<b>Врач:</b> %s<br />", vrInfo));
				sb.append("<h3>Наименование показателей:</h3>");
				sb.append("<ol>");
				for (String str : im.getPokaz()) {
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT name_n FROM n_ldi WHERE pcod = ? ", str);
					if (acrs.getResultSet().next())
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
					acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", ip.getPvizitId());
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
	public List<IntegerClassifier> get_n_m00(int clpu) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT m.pcod, m.name FROM n_m00 m JOIN n_lds l ON (m.pcod = l.clpu) WHERE m.pcod != ? ", clpu)) {
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
				sb.append("<div align=\"right\">Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</div>");
				sb.append("<br>	<div style=\"width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", na.getCpodr_name(), na.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h2 align=center>Направление </h2>");
				sb.append(String.format("<p align=\"center\"><b>на госпитализацию</b></p>"));
				if (na.getClpu()!=null) sb.append(String.format("<br> Куда: %s", na.getClpu()));
			 	else sb.append("<br> Куда: _________________________________________________________" );
			 	sb.append("<br><br>");
			 	sb.append("1. Номер страхового полиса ОМС: " );
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
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append(String.format("<br>6. Место работы/учебы: %s ",acrs.getResultSet().getString(8)));
			if (acrs.getResultSet().getString(9)!=null) sb.append(String.format(", должность: %s ",acrs.getResultSet().getString(9)));}
			sb.append("<br>7. Код диагноза по МКБ: ");
			acrs.close();
			acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", na.getPvizitId());
			if (acrs.getResultSet().next()) 
			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
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
			sb.append("<br>МП");
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
				sb.append("<div align=\"right\">Код формы по ОКУД____________<br>Код учреждения по ОКПО_______________</div>");
				sb.append("<br>	<div style=\"width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", nk.getCpodr_name(), nk.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h2 align=center>Направление </h2>");
				sb.append(String.format("<p align=\"center\"><b>на %s</b></p>",nk.getNazv()));
			 	if (nk.getCpol()!=null) sb.append(String.format("<br> Куда: %s", nk.getCpol()));
			 	else sb.append("<br> Куда: _________________________________________________________" );
			 	sb.append("<br><br>");
			 	sb.append("1. Номер страхового полиса ОМС: " );
				acrs = sse.execPreparedQuery("SELECT poms_nom FROM patient WHERE npasp = ? ", nk.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
		
			sb.append("<br>2. Код льготы: ");
			acrs.close();
				acrs = sse.execPreparedQuery("SELECT lgot FROM p_kov WHERE npasp = ? ", nk.getNpasp());
				if (acrs.getResultSet().next())
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT patient.fam, patient.im, patient.ot, patient.datar, patient.adm_ul, patient.adm_dom, patient.adm_kv, n_z43.name, patient.prof FROM patient join n_z43 on (patient.mrab=n_z43.pcod) where patient.npasp = ?", nk.getNpasp());
				if (acrs.getResultSet().next()){
			sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append(String.format("<br>6. Место работы/учебы: %s ",acrs.getResultSet().getString(8)));
			if (acrs.getResultSet().getString(9)!=null) sb.append(String.format(", должность: %s ",acrs.getResultSet().getString(9)));}
			sb.append("<br>7. Код диагноза по МКБ: ");
			acrs.close();
			acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", nk.getPvizitId());
			if (acrs.getResultSet().next()) 
			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
			if (nk.getObosnov()!=null) sb.append(String.format("<br>8. Обоснование направления: %s",nk.getObosnov()));
			else sb.append("<br>8. Обоснование направления: __________________________________________________");
			sb.append("<br>Должность медицинского работника, направившего больного: ");
			acrs.close();
			acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",nk.getUserId());
			if (acrs.getResultSet().next())
			sb.append(String.format("%s", acrs.getResultSet().getString(4)));
			sb.append(String.format("<br>ФИО врача: %s %s %s", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
			sb.append("<br>Подпись_______________");
			sb.append("<br>Заведующий отделением _____________________________________________________________________________");
			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			sb.append("<br>МП");
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
				sb.append("<br>	<div style=\"width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
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
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null)sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
					}				
				sb.append("<br>осложнение основного заболевания <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=2 and predv=false order by datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}				
				sb.append("<br>сопутствующее заболевание <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=3 and predv=false order by datap", vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}
				sb.append("<br>	7. Краткий анамнез, диагностические исследования, течение болезни<br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", vp.getPvizit_id()); 
if (acrs.getResultSet().next()) {
				if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(1)));
}

				acrs.close();
				acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_p0e1.pcod , n_p0e1.name , n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) join n_p0e1  on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? " +
					"union " +
					"select p_isl_ld.nisl, n_p0e1.pcod, n_p0e1.name , n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) join n_arez  on (n_arez.pcod = p_rez_d.rez) join n_p0e1 on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? ", vp.getPvizit_id(), vp.getPvizit_id());
				while (acrs.getResultSet().next()){
					sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
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
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", pvizitId);
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null)sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
					}
				sb.append("<br>осложнение основного заболевания <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=2 and predv=false order by datap", pvizitId);
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}				
				sb.append("<br>сопутствующее заболевание <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=3 and predv=false order by datap", pvizitId);
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

//	@Override
//	public void UpdateDiagZ(PdiagZ dz) throws KmiacServerException, TException {
//		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			sme.execPreparedT("UPDATE p_diag SET diag = ?, cpodr = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, datad = ?, diag_s = ?, d_grup_s = ?, cod_sp = ?, cdol_ot = ?, nmvd = ?, xzab = ?, stady = ?, disp = ?, pat = ?, prizb = ?, prizi = ? WHERE id_diag_amb = ?", false, dz, pdiagZTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 1);
//			sme.setCommit();
//		} catch (InterruptedException | SQLException e) {
//			throw new KmiacServerException();
//		}
//	}
//
//	@Override
//	public int AddPdisp(Pdisp disp) throws KmiacServerException, TException {
//		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			sme.execPreparedT("INSERT INTO p_disp (id_diag, npasp, diag, pcod, d_vz, d_grup, ishod, dataish, datag, datad, diag_s, d_grup_s, cod_sp, cdol_ot, sob, sxoch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, disp, pdispTypes, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
//			int id = sme.getGeneratedKeys().getInt("id");
//			sme.setCommit();
//			return id;
//		} catch (InterruptedException | SQLException e) {
//			throw new KmiacServerException();
//		}
//	}
//
//	@Override
//	public void UpdatePdisp(Pdisp disp) throws KmiacServerException, TException {
//		try (SqlModifyExecutor sme = tse.startTransaction()) {
//			sme.execPreparedT("UPDATE p_disp SET diag = ?, pcod = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, datad = ?, diag_s = ?, d_grup_s = ?, cod_sp = ?, cdol_ot = ?, sob = ?, sxoch = ?) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id_diag = ? ", false, disp, pdispTypes, 	3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0);
//			sme.setCommit();
//		} catch (InterruptedException | SQLException e) {
//			throw new KmiacServerException();
//		}
//	}
//
	@Override
	public PdiagZ getPdiagZ(int id_diag_amb) throws PdiagNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT d.*, c.name AS nameC00 FROM p_diag d LEFT JOIN n_c00 c ON (c.pcod = d.diag) WHERE d.id_diag_amb = ? ", id_diag_amb)) {
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
	public Pdisp getPdisp(int id_diag) throws PdispNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_disp WHERE id_diag = ? ", id_diag)) {
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
	public int setPdiag(PdiagZ diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			try {
				getPdiagZ(diag.id_diag_amb);
				sme.execPreparedT("UPDATE p_diag SET diag = ?, cpodr = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, datad = ?, diag_s = ?, d_grup_s = ?, cod_sp = ?, cdol_ot = ?, nmvd = ?, xzab = ?, stady = ?, disp = ?, pat = ?, prizb = ?, prizi = ?, named = ? WHERE id_diag_amb = ?", false, diag, pdiagZTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 1);
				sme.setCommit();
				return diag.getId();
			} catch (PdiagNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_diag (id_diag_amb, npasp, diag, cpodr, d_vz, d_grup, ishod, dataish, datag, datad, diag_s, d_grup_s, cod_sp, cdol_ot, nmvd, xzab, stady, disp, pat, prizb, prizi, named) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, diag, pdiagZTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
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
				getPdisp(disp.id_diag);
				sme.execPreparedT("UPDATE p_disp SET diag = ?, pcod = ?, d_vz = ?, d_grup = ?, ishod = ?, dataish = ?, datag = ?, datad = ?, diag_s = ?, d_grup_s = ?, cod_sp = ?, cdol_ot = ?, sob = ?, sxoch = ? WHERE id_diag = ? ", false, disp, pdispTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0);
				sme.setCommit();
				return disp.getId();
			} catch (PdispNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_disp (id_diag, npasp, diag, pcod, d_vz, d_grup, ishod, dataish, datag, datad, diag_s, d_grup_s, cod_sp, cdol_ot, sob, sxoch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, disp, pdispTypes, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
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
	public String printProtokol(Protokol pk) throws KmiacServerException, TException {
		AutoCloseableResultSet acrs = null;
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("protokol", ".htm").getAbsolutePath()), "utf-8")) {
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Случай заболевания</title>");
			sb.append("</head>");
			sb.append("<body>");
				acrs = sse.execPreparedQuery("SELECT datao,cobr,n_p0c.name FROM p_vizit join n_p0c on(p_vizit.cobr=n_p0c.pcod) where id=?", pk.getPvizit_id());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("<b>Дата перв.обращения</b> %1$td.%1$tm.%1$tY", acrs.getResultSet().getDate(1)));
					sb.append(String.format("<br><b>Цель обращения </b>%s", acrs.getResultSet().getString(3)));
				}
				acrs.close();
				
				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", pk.getPvizit_id()); 
				if (acrs.getResultSet().next()) {
					sb.append("<br><b>	Анамнез заболевания</b><br>");
					if (acrs.getResultSet().getString(1)!=null)
						sb.append(String.format(" %s.", acrs.getResultSet().getString(1)));
				}				
				acrs.close();
				
				acrs = sse.execPreparedQuery("select n_abs.name,n_opl.name,n_ap0.name,p_priem.t_jalob,p_priem.t_temp,p_priem.t_ad,p_priem.t_rost,p_priem.t_ves,p_priem.t_chss,p_priem.t_status_praesense,p_priem.t_fiz_obsl,p_priem.t_st_localis,p_priem.t_ocenka,p_vizit_amb.datap from p_vizit_amb join p_priem on (p_priem.id_pos=p_vizit_amb.id) left join n_abs on(p_vizit_amb.mobs=n_abs.pcod) left join n_opl on(p_vizit_amb.opl=n_opl.pcod) left join n_ap0 on(p_vizit_amb.rezult=n_ap0.pcod) where p_vizit_amb.id_obr=? order by id ", pk.getPvizit_id());
				if (acrs.getResultSet().next()) {
					sb.append("<br><b>Осмотр: </b><br>");
					do {
						sb.append(String.format("Дата посещения %1$td.%1$tm.%1$tY <br>", acrs.getResultSet().getDate(14)));
						if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("<i>Место обслуживания </i> %s <br>", acrs.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(2)!=null) sb.append(String.format("<i>Способ оплаты </i> %s <br>", acrs.getResultSet().getString(2)));
						if (acrs.getResultSet().getString(4)!=null) sb.append(String.format("<i>Жалобы: </i> %s <br>", acrs.getResultSet().getString(4)));
						if (acrs.getResultSet().getString(5)!=null) sb.append(String.format("<i>Температура </i> %s <br>", acrs.getResultSet().getString(5)));
						if (acrs.getResultSet().getString(6)!=null) sb.append(String.format("<i>АД </i> %s <br>", acrs.getResultSet().getString(6)));
						if (acrs.getResultSet().getString(7)!=null) sb.append(String.format("<i>Рост </i> %s <br>", acrs.getResultSet().getString(7)));
						if (acrs.getResultSet().getString(8)!=null) sb.append(String.format("<i>Вес </i> %s <br>", acrs.getResultSet().getString(8)));
						if (acrs.getResultSet().getString(9)!=null) sb.append(String.format("<i>ЧСС </i> %s <br>", acrs.getResultSet().getString(9)));
						if (acrs.getResultSet().getString(10)!=null) sb.append(String.format("<i>Status praesense </i> %s <br>", acrs.getResultSet().getString(10)));
						if (acrs.getResultSet().getString(11)!=null) sb.append(String.format("<i>Физикальное обследование </i> %s <br>", acrs.getResultSet().getString(11)));
						if (acrs.getResultSet().getString(12)!=null) sb.append(String.format("<i>Localis status </i> %s <br>", acrs.getResultSet().getString(12)));
						if (acrs.getResultSet().getString(13)!=null) sb.append(String.format("<i>Оценка данных анамнеза и объективного исследования </i> %s <br>", acrs.getResultSet().getString(13)));
						if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("<i>Результат </i> %s <br>", acrs.getResultSet().getString(3)));
					} while (acrs.getResultSet().next());
				}
				acrs.close();
					
				sb.append("<br><b>Поставленные диагнозы: </b><br>");
				acrs = sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pk.getPvizit_id());
				if (acrs.getResultSet().next()) {
					sb.append("основное заболевание <br>");
					acrs.close();
					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", pk.getPvizit_id());
					while (acrs.getResultSet().next()) {
						if (acrs.getResultSet().getString(1) != null)
							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
					}
					acrs.close();
					
					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=2 and predv=false order by datap", pk.getPvizit_id());
					if (acrs.getResultSet().isBeforeFirst())
						sb.append("<br>осложнение основного заболевания <br>");
					while (acrs.getResultSet().next()) {
						if (acrs.getResultSet().getString(1) != null)
							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
						else
							sb.append(" -<br>");
						}				
					acrs.close();
					
					acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=3 and predv=false order by datap", pk.getPvizit_id());
					if (acrs.getResultSet().isBeforeFirst())
						sb.append("<br>сопутствующее заболевание <br>");
					while (acrs.getResultSet().next()) {
						if (acrs.getResultSet().getString(1) != null)
							sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
						else
							sb.append(" -<br>");
						}
					acrs.close();
					
					acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_p0e1.pcod , n_p0e1.name , n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav " +
						"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) join n_p0e1  on (n_p0e1.pcod = p_isl_ld.cisl) " +
						"where p_isl_ld.pvizit_id = ? " +
						"union " +
						"select p_isl_ld.nisl, n_p0e1.pcod, n_p0e1.name , n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav " +
						"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) join n_arez  on (n_arez.pcod = p_rez_d.rez) join n_p0e1 on (n_p0e1.pcod = p_isl_ld.cisl) " +
						"where p_isl_ld.pvizit_id = ? ", pk.getPvizit_id(), pk.getPvizit_id());
					if (acrs.getResultSet().isBeforeFirst()) {
						sb.append("<br><br><b>Назначенные исследования </b><br>");
						while (acrs.getResultSet().next()) {
							sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
						}			
					}
					acrs.close();
					
					acrs = sse.execPreparedQuery("select p_vizit.recomend,p_vizit.zakl,n_aq0.name from p_vizit left join n_aq0 on (p_vizit.ishod=n_aq0.pcod) where id=?", pk.getPvizit_id()); 
					if (acrs.getResultSet().next()) {
						if (acrs.getResultSet().getString(1) != null)
							sb.append(String.format("<br><b> Лечебные и трудовые рекомендации</b> %s", acrs.getResultSet().getString(1)));
						if (acrs.getResultSet().getString(2) != null)
							sb.append(String.format("<br><b> Заключение </b> %s", acrs.getResultSet().getString(2)));
						if (acrs.getResultSet().getString(3) != null)
							sb.append(String.format("<br><b> Исход </b> %s", acrs.getResultSet().getString(3)));
					}
					sb.append("<br>");
				}
				acrs.close();
	
				sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
				sb.append("Лечащий врач:");
				acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",pk.getUserId());
				if (acrs.getResultSet().next())
					sb.append(String.format("%s %s %s",acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
				sb.append("<br>Подпись ____________");
			sb.append("<body>");
			
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
				sb.append("и социального развития");
				sb.append("<br>Российской Федерации");
				sb.append("<br>_________________________________________________________________________________________");
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
				sb.append("4. Фамилия, имя, отчество законного представителя гражданина (заполняется при наличии законного представителя): ________________");
				sb.append("<br>");
				sb.append("5. Адрес места жительства гражданина (при отсутствии места жительства указывается адрес пребывания, фактического проживания на территории Российской Федерации): ___________________________________________________________________________");
				sb.append("<br>");
				sb.append("6. Инвалидом не является, инвалид первой, второй, третьей группы, категория 'ребенок-инвалид' (нужное подчеркнуть)");
				sb.append("<br>");
				sb.append("7. Степень ограничения к трудовой деятельности (заполняется при повторном направлении): _____________________________________________________________");
				sb.append("<br>");
				sb.append("8. Степень утраты профессиональной трудоспособности в процентах (заполняется при повторном направлении) _____________________________________________");
				sb.append("<br>");
				sb.append("9. Направляется первично, повторно (нужное подчеркнуть)");
				sb.append("<br>");
				sb.append("10. Кем работает на момент направления на медикл-социальную эксперизу (указать должность, профессию, специальность, квалификацию и стаж работы по указанной должности, профессии, специальности, квалификации; в отношении неработающих граждан сделать запись 'не работает')__________________________________________________");
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
				sb.append("_________________________________________________________________________________________________________________");
				sb.append("<dd>(подробно описывается при первичном направлении, при повторном направлении отражается динамика  за период между освидетельствованиями, детально описываются выявленные за этот период нвоые случаи заболеваний, приведшим к стойким нарушениям функций организма)</dd>");
				sb.append("<br>");
				sb.append("20. Анамнез жизни (пречисляются перенесенные в прошлом заболевания, травмы, отравления, операции, заболевания, по которым отягощена наследственность"); 
				sb.append(", дополнительно в отношении ребенка указывается, как протекали беременность и роды у матери, сроки формирования психомоторынх навыков, самообслуживания, показательно-игровой деятельности, навыков опрятности и ухода за собой, как протекало раннее развитие (по возрасту, с отставанием, с опережением)): ________________________________________________________________________________________________________________________________________________________________________________________");
				sb.append("<br>");
				sb.append("21. Частота и длительность временной нетрудоспособности (сведения за последние 12 месяцев)");
				sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\"> ");
				sb.append("<tr bgcolor=\"#F5E8FF\"><th style=\"font: 16px times new roman;\">№</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Дата (число, месяц, год) начала временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Дата (число, месяц, год) окончания временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Число дней (месяцев и дней) временной нетрудоспособности</th>");
				sb.append("<th style=\"font: 16px times new roman;\">Диагноз</th>");
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
				sb.append("_________________ (подпись) ________________(расшифровка)");
				sb.append("<br>");
				sb.append("_________________ (подпись) ________________(расшифровка)");
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
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\kartl.htm"), "utf-8")) {
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Обменная карта беременной</title>");
			sb.append("</head>");
			sb.append("<body>");
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
						if (acrs2.getResultSet().getString(2) != null)
							sb.append(String.format("осложнения после аборта: %s;<br>", acrs2.getResultSet().getString(2)));
					} else
						sb.append("нет<br>");
					acrs2.close();
					
					sb.append(String.format("5. Особенности течения прежних беременностей, родов, послеродового периода: %s; %d<br>", acrs.getResultSet().getString(17), acrs.getResultSet().getInt(18)));
					sb.append(String.format("6. Данная беременность %d (по счету), роды %d (по счету)<br>", acrs.getResultSet().getInt(1), acrs.getResultSet().getInt(2)));
					sb.append(String.format("7. Количество абортов %d (всего). Последний в %tY году на сроке %d недель<br>", acrs.getResultSet().getInt(3), acrs.getResultSet().getDate(19), acrs.getResultSet().getInt(20)));
					sb.append("8. Были ли преждевременные роды: ДА _____ / НЕТ _____ . Если ДА, то в каком году _____________<br>");
					sb.append(String.format("9. Дата последней менструации: %1$td %1$tb %1$tY<br>", acrs.getResultSet().getDate(4)));
					sb.append(String.format("10. Срок текущей беременности составляет %d недель при первом посещении женской консультации %2$td %2$tb %2$tY года<br>", acrs.getResultSet().getInt(5), acrs.getResultSet().getDate(6)));
					
					acrs2 = sse.execPreparedQuery("SELECT COUNT(id_pvizit) FROM p_rd_din WHERE id_pvizit = ?", kb.getId_pvizit());
					if (acrs2.getResultSet().next())
						sb.append(String.format("11. Количество посещений: %d<br>", acrs2.getResultSet().getInt(1)));
					acrs2.close();
					
					sb.append(String.format("12. Первое шевеление плода: %1$td %1$tb %1$tY<br>", acrs.getResultSet().getDate(7)));
					
					sb.append("13. Возможные особенности течения беременности: ");
					acrs2 = sse.execPreparedQuery("SELECT db6.name, db5.name FROM p_rd_din din LEFT OUTER JOIN n_db6 db6 ON (db6.pcod = din.dspos) LEFT OUTER JOIN n_db5 db5 ON (db5.pcod = din.oteki) WHERE id_pvizit = ? AND (db6.name IS NOT NULL OR db5.name IS NOT NULL) ", kb.getId_pvizit());
					if (acrs2.getResultSet().next()) {
						do {
							String str = "";
							
							if (acrs2.getResultSet().getString(1) != null)
								str += String.format("диагноз: %s; ", acrs2.getResultSet().getString(1));
							if (acrs2.getResultSet().getString(2) != null)
								str += String.format("отеки: %s; ", acrs2.getResultSet().getString(2));
							if (str.length() > 0)
								sb.append(str + "<br>");
						} while (acrs2.getResultSet().next());
					} else
						sb.append("нет<br>");
					acrs2.close();
					
					sb.append("14. Размеры таза (при первом посещении)<br>");
					sb.append(String.format("D.Sp %d D.Cr %d D.troch %d<br>", acrs.getResultSet().getInt(8), acrs.getResultSet().getInt(9), acrs.getResultSet().getInt(10)));
					sb.append(String.format("C.ext %d C.diag %d C.vera %d<br>", acrs.getResultSet().getInt(11), acrs.getResultSet().getInt(12), acrs.getResultSet().getInt(13)));
					sb.append(String.format("Рост %d Вес %f<br>", acrs.getResultSet().getInt(14), acrs.getResultSet().getDouble(15)));
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
				sb.append("RW<sub>1</sub>: \"____\" _____________ 20______ года <br>HBS<sub>1</sub>: \"____\" ____________ 20______ года<br>");
				sb.append("RW<sub>2</sub>: \"____\" _____________ 20______ года <br>HBS<sub>2</sub>: \"____\" ____________ 20______ года<br>");
				sb.append("RW<sub>3</sub>: \"____\" _____________ 20______ года <br>HCV<sub>1</sub>: \"____\" ____________ 20______ года<br>");
				sb.append("ВИЧ<sub>1</sub>: \"____\" _____________ 20______ года <br>HCV<sub>2</sub>: \"____\" ____________ 20______ года<br>");
				sb.append("ВИЧ<sub>2</sub>: \"____\" _____________ 20______ года<br>");
				
				acrs = sse.execPreparedQuery("SELECT ph, grup FROM p_sign WHERE npasp = ? ", kb.getNpasp());
				if (acrs.getResultSet().next()) {
					sb.append(String.format("Резус: %s<br>", acrs.getResultSet().getString(1)));
					sb.append("Титр антител:_________<br>");
					sb.append(String.format("Группа крови: %s<br>", acrs.getResultSet().getString(2)));
				}
				acrs.close();
				
				sb.append("<br>Резус-принадлежность крови мужа:__________");
				sb.append("<br>Токсоплазмоз: РСК, кожная проба __________");
				sb.append("<br><b>Клинические анлизы</b>");
				sb.append("<br>Крови _____________________________");
				sb.append("<br>Мочи ______________________________");
				sb.append("<br>Анализ содержимого влагалища (мазок) _______________________________");
				sb.append("<br>Кал на яйца-глист _________________________");
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
				sb.append("<TD colspan=4>Данные обследования</TD>");
				sb.append("<TD rowspan=2 align=center>Подпись врача</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>АД</TD>");
				sb.append("<TD>Вес</TD>");
				sb.append("<TD>Hb.</TD>");
				sb.append("<TD>ан.мочи</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>111</TD>");
				sb.append("<TD>222</TD>");
				sb.append("<TD>333</TD>");
				sb.append("<TD>444</TD>");
				sb.append("<TD>333</TD>");
				sb.append("<TD>444</TD>");
				sb.append("</TR>");
				sb.append("</TABLE>");
				sb.append("<br>УЗИ Дата \"___\" _____________ 20___ г.");
				sb.append("<br>Заключение: _____________________________________________________");
				sb.append("<br>1. Заключение терапевта _________________________________________");
				sb.append("<br>2. Закючение окулиста ___________________________________________");
			sb.append("</body>"); 
			sb.append("</html>");
			
			osw.write(sb.toString());
			return "c:\\kart1.html";
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT sho.id, sho.diag, nd.name, sho.next, nsh.pcod, nsh.name, sht.sh_text FROM sh_osm sho JOIN n_din nd ON (nd.pcod = sho.cdin) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) WHERE sho.id = ? ORDER BY nsh.pcod ", id_sh)) {
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
		String sql = "SELECT DISTINCT sho.id AS pcod, sho.name, sho.diag || ' ' || sho.name AS name FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) JOIN n_c00 c00 ON (c00.pcod = sho.diag) WHERE (shp.cspec = ?) AND (sho.cslu & ? = ?) ";
		
		if (srcText != null)
			sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?) OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
		sql += "ORDER BY sho.name ";
		
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select p_mer.*, n_abd.name as name_pmer from p_mer join n_abd on (p_mer.pmer=n_abd.pcod) where p_mer.npasp = ? and diag = ?", npasp, diag)) {
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
			sme.execPreparedT("insert into p_mer (npasp, id_pdiag, diag, pmer, pdat, fdat, cod_sp, dataz, prichina, rez, cdol, id_pvizit, id_pos) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, pm, pmerTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
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
			sme.execPreparedT("update p_mer set pmer = ?, pdat = ?, fdat = ?, prichina = ?, rez = ? where id = ? ", false, pm, pmerTypes, 4, 5, 6, 9, 10, 0);
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
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT p.fam, p.im, p.ot, p.datar,p.docser,p.docnum,p.adp_gorod,p.adp_ul,p.adp_dom,p.adp_korp,p.adp_kv,p.poms_ser,p.poms_nom,p.poms_ndog,n.clpu,p.terp,p.adm_gorod, p.adm_ul, p.adm_dom,p.adm_korp, p.adm_kv, p.tel,s.grup,s.ph,s.vred,a.stat FROM patient p,n_n00 n,p_sign s,n_az9 a,p_rd_sl rd WHERE p.cpol_pr=n.pcod and p.npasp=s.npasp and p.sgrp=a.pcod and rd.npasp=p.npasp")) {
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select d.id_pvizit,d.id_pos,d.npasp,d.srok,d.ves,d.art1,d.art2,d.art3,d.art4,d.ball from p_rd_din d,p_rd_sl rd where d.id_pvizit=rd.id_pvizit")) {
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
	public List<RdSlStruct1> getRdSl() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select rd.id,rd.npasp,rd.datay,rd.abort,rd.shet,rd.dataM,rd.yavka1,rd.ishod,rd.Datasn,rd.DataZs,rd.kolrod,rd.deti,rd.kont,rd.dsp,rd.dsr,rd.dTroch,rd.cext,rd.indsol,rd.prmen,rd.dataz,rd.prrod,rd.vozmen,rd.oslrod,rd.polj,rd.id_pvizit,vr.fam,vr.im,vr.ot,i.telm,s.vitae,s.allerg from p_sign s,p_rd_sl rd,p_vizit v,s_vrach vr,p_rd_inf i where s.npasp=rd.npasp and i.npasp=rd.npasp and rd.id_pvizit=v.id and v.cod_sp=vr.pcod")) {
			if (acrs.getResultSet().next())
				return (List<RdSlStruct1>) rsmRdSl1.map(acrs.getResultSet());
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
	public List<StringClassifier> get_n_c00(int npasp)
			throws KmiacServerException, TException {
	try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select n_c00.pcod as pcod,p_disp.diag as name  from p_disp join n_c00 on (p_disp.diag=n_c00.pcod) where npasp=? ", npasp)) {
		return rsmStrClas.mapToList(acrs.getResultSet());
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
	}

	@Override
	public String formfilecsv(KartaBer kb) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		Date p1;
		Integer ball1 = 0;
		Integer ball2 = 0;
		Integer ball3 = 0;
		Integer ball4 = 0;
		Integer ball5 = 0;
		Integer grk = 0;
		Integer j = 0;
		AutoCloseableResultSet acrs = null, acrs2 = null, arcs3 = null,
				arcs4 = null, arsc5 = null, arsc6 = null;
		//таблица паспортной информации Patient.csv
		StringBuilder sb = new StringBuilder(0x10000);
		sb.append("uid;fam;im;ot;dr;pasp;tawn;street;house;flat;polis;dog;stat;lpup;terp;ftown;fstreet;fhouse;fflat;grk;rez");
		List<RdPatient> rdPatient = getRdPatient();
//		for (int j = 0; j < rdPatient.size(); j++) {
//			RdPatient rdp = rdPatient.get(j);
//			
//		}
		for (RdPatient rdp : rdPatient) {
		p1 = new Date(rdp.datar);
		if (rdp.grk == "I") grk = 1;
		if (rdp.grk == "II") grk = 2;
		if (rdp.grk == "III") grk = 3;
		if (rdp.grk == "IV") grk = 4;
		sb.append(String.format("%d;%s;%s;%s;%5$td.%5$tm.%5$tY;%s%s;%d;%s;%s;%s;%s%s;%s;%d;%d;%d;%d;%s;%s;%s;%d;%s", rdp.uid, rdp.fam, rdp.im, rdp.ot, p1,rdp.docser,rdp.docnum,rdp.tawn,rdp.street,rdp.house,rdp.flat,rdp.poms_ser,rdp.poms_nom,rdp.dog,rdp.stat,rdp.lpup,rdp.terp,rdp.ftawn,rdp.fstreet,rdp.fhouse,rdp.fflat,grk,rdp.rez));		
		}
		//Vizit.csv
		StringBuilder sb1 = new StringBuilder(0x10000);
		sb1.append("uiv;uid;dv;sp;wr;diap;mso;rzp;aim;npr");
		List<RdVizit> rdVizit = getRdVizit();
		for (RdVizit rvz : rdVizit) {
			p1 = new Date(rvz.dv);
			sb1.append(String.format("%d;%d;%3$td.%3$tm.%3$tY;%d;%s %s %s;%s;%d;%d;%d;%d", rvz.uid, rvz.npasp, p1, rvz.sp, rvz.famwr,rvz.imwr,rvz.otwr,rvz.diag,rvz.mso,rvz.rzp,rvz.aim,rvz.npr));		
		}
		// Con_vizit.scv
		StringBuilder sb2 = new StringBuilder(0x10000);
		sb2.append("uicv;uiv;uid;ves;ned;lcad;ldad;rcad;rdad;ball1;ball2;ball3;ball4;ball5");
		List<RdConVizit> rdConVizit = getRdConVizit();
		for (RdConVizit rcv : rdConVizit) {
			j = j+1;
			sb2.append(String.format("%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;", j, rcv.uiv,rcv.npasp, rcv.ves, rcv.ned,rcv.lcad,rcv.ldad,rcv.rcad,rcv.rdad,ball1,ball2,ball3,ball4));		
		}
		//Con_diagn.csv
		StringBuilder sb3 = new StringBuilder(0x10000);
		sb3.append("ndiag;uid;dex1;dex2;dex3;dex4dex5;dex6;dex7;dex9;dex10;dex;dak;dsost;dosl");
		try (AutoCloseableResultSet acrs1 = sse.execQuery("SELECT npasp rd FROM p_rd_sl rd")) {
//			String npasp;
//			if (acrs1.getResultSet().next())
//				return npasp; //rsmRdPat.mapToList(acrs.getResultSet());
//			else
//				throw new KmiacServerException("нет записи");
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
		// Con_main.csv
		StringBuilder sb4 = new StringBuilder(0x10000);
		sb4.append("num;uid;jdet;dvzdu;srokvzu1;grisk;dgrisk;drodr;fiovr;dred;telm;dsndu;nber;nrod;job;vp;vn;circl;dlm;kontr;dsp;dcr;dtroch;cext;solov;cs;allerg;nasl;gemotr;prich;dprich;ishodprb;ostpber");
		List<RdSlStruct1> rdSl = getRdSl();
		for (RdSlStruct1 rsl : rdSl) {
			j = j+1;
			p1 = new Date(rsl.datay);
			Date p2 = new Date(rsl.dataz);
			Date p3 = new Date(rsl.DataZs);
			Date p4 = new Date(rsl.dataz);
			Date p5 = new Date(rsl.Datasn);
			Date p6 = new Date(rsl.dataM);
			Integer risk = 0;
			Integer kontr = 0;
			Integer rod = 0;
			if (rsl.prrod != "") rod =1;
			if (rsl.kont) kontr=1;
			Date dgrisk = null;
			sb4.append(String.format("%d;%d;%d;%4$td.%4$tm.%4$tY;%d;%d;%7$td.%7$tm.%7$tY;%8$td.%8$tm.%8$tY;%s%s%s;%10$td.%10$tm.%10$tY;%s;%12$td.%12$tm.%12$tY;%d;%d;%d;%d;%d;%d;%19$td.%19$tm.%19$tY;%d;%d;%d;%d;%d;%d;%s;%s;;;%d;%31$td.%31$tm.%31$tY;%d;%d", j,rsl.npasp,rsl.deti,p1,rsl.yavka1,risk,dgrisk,rsl.DataZs,rsl.fam,rsl.im,rsl.ot,rsl.dataz,rsl.telm,rsl.Datasn,rsl.shet,rsl.kolrod,rsl.abort,rsl.polj,rsl.vozmen,rsl.prmen,rsl.dataM,kontr,rsl.dsp,rsl.dsr,rsl.dTroch,rsl.cext,rsl.indsol,rsl.vitae,rsl.allerg,rsl.ishod,p5,rod,rsl.oslrod));		
		}
		return null;
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
			sme.execPreparedT("insert into p_obost (npasp, id_pdiag, diag, sl_obostr, sl_hron, cod_sp, cdol, dataz) values (?, ?, ?, ?, ?, ?, ?, ?) ", true, pbst, pobostTypes, 1, 2, 3, 4, 5, 6, 7, 8);
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



	
}

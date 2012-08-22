package ru.nkz.ivcgzo.serverOsm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ru.nkz.ivcgzo.thriftOsm.IsslInfo;
import ru.nkz.ivcgzo.thriftOsm.IsslMet;
import ru.nkz.ivcgzo.thriftOsm.IsslPokaz;
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.Napr;
import ru.nkz.ivcgzo.thriftOsm.NaprKons;
import ru.nkz.ivcgzo.thriftOsm.PNapr;
import ru.nkz.ivcgzo.thriftOsm.P_isl_ld;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pdisp;
import ru.nkz.ivcgzo.thriftOsm.PdispNotFoundException;
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
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
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
	private final TResultSetMapper<PatientCommonInfo, PatientCommonInfo._Fields> rsmPatComInfo;
	@SuppressWarnings("unused")
	private final Class<?>[] patComInfoTypes; 
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
	private final TResultSetMapper<PNapr, PNapr._Fields> rsmPnapr;
	private final Class<?>[] pnaprTypes;


	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp",       "vidp",        "timepn",   "fam",        "im",         "ot",         "poms_ser",   "poms_nom",   "id_pvizit",  "pol");
		zapVrTypes = new Class<?>[] {                  Integer.class, Integer.class, Time.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class};
		
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id",          "npasp",       "cpol",        "cobr",        "datao",    "ishod",       "rezult",      "talon",       "cod_sp",      "cdol",       "cuser",       "zakl",       "dataz",    "recomend");
		pvizitTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, String.class};
		
		rsmPvizitAmb = new TResultSetMapper<>(PvizitAmb.class, "id",          "id_obr",      "npasp",       "datap",    "cod_sp",      "cdol",       "diag",       "mobs",        "rezult",      "opl",         "stoim",      "uet",         "datak",    "kod_rez",     "k_lr",        "n_sp",        "pr_opl",      "pl_extr",     "vpom",        "fio_vr");
		pvizitAmbTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class};
		
		rsmPdiagAmb = new TResultSetMapper<>(PdiagAmb.class, "id",          "id_obr",      "npasp",       "diag",       "named",      "diag_stat",   "predv",       "datad",    "obstreg",     "cod_sp",      "cdol",       "datap",    "dataot",   "obstot",      "cod_spot",    "cdol_ot",    "vid_tr");
		pdiagAmbTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Boolean.class, Date.class, Integer.class, Integer.class, String.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class};
		
		rsmPdiagZ = new TResultSetMapper<>(PdiagZ.class, "id",          "id_diag_amb", "npasp",       "diag",       "cpodr",       "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "nmvd",        "xzab",        "stady",       "disp",        "pat",         "prizb",       "prizi",       "named",      "nameC00");
		pdiagZTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class};
		
		
		rsmPsign = new TResultSetMapper<>(Psign.class, "npasp",       "grup",       "ph",         "allerg",     "farmkol",    "vitae",      "vred"       );
		psignTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPriem = new TResultSetMapper<>(Priem.class, "id_obr",      "npasp",       "id_pos",      "sl_ob",       "n_is",        "n_kons",      "n_proc",      "n_lek",       "t_chss",     "t_temp",     "t_ad",       "t_rost",     "t_ves",      "t_st_localis", "t_ocenka",   "t_jalob",    "t_status_praesense", "t_fiz_obsl");
		priemTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class,   String.class, String.class, String.class,         String.class};
		
		rsmPatComInfo = new TResultSetMapper<>(PatientCommonInfo.class, "npasp",       "fam",        "im",         "ot",         "datar",    "poms_ser",   "poms_nom",   "pol",         "jitel",       "sgrp",        "adp_obl",    "adp_gorod",  "adp_ul",     "adp_dom",    "adp_korp",   "adp_kv",     "adm_obl",    "adm_gorod",  "adm_ul",     "adm_dom",    "adm_korp",   "adm_kv",     "mrab",       "name_mr",    "ncex",        "poms_strg",   "poms_tdoc",   "poms_ndog",  "pdms_strg",   "pdms_ser",   "pdms_nom",   "pdms_ndog",  "cpol_pr",     "terp",        "datapr",   "tdoc",        "docser",     "docnum",     "datadoc",  "odoc",       "snils",      "dataz",    "prof",       "tel",        "dsv",      "prizn",       "ter_liv",     "region_liv");
		patComInfoTypes = new Class<?>[] {                              Integer.class, String.class, String.class, String.class, Date.class, String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Date.class, String.class, String.class, Date.class, String.class, String.class, Date.class, Integer.class, Integer.class, Integer.class};
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
		
		rsmPislld = new TResultSetMapper<>(P_isl_ld.class, "nisl",        "npasp",       "cisl",        "pcisl",      "napravl",     "naprotd",     "datan",    "vrach",       "diag",       "dataz",    "pvizit_id");
		pislldTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, Date.class, Integer.class};
		
		rsmPrezd = new TResultSetMapper<>(Prez_d.class, "npasp",       "nisl",        "kodisl",     "stoim");
		prezdTypes = new Class<?>[] {                   Integer.class, Integer.class, String.class, Double.class};
		
		rsmPrezl = new TResultSetMapper<>(Prez_l.class, "npasp",       "nisl",        "cpok",       "stoim");
		prezlTypes = new Class<?>[] {                   Integer.class, Integer.class, String.class, Double.class};
		
		rsmMetod = new TResultSetMapper<>(Metod.class, "obst",       "name_obst",  "c_p0e1",      "pcod");
		metodTypes = new Class<?>[] {                  String.class, String.class, Integer.class, String.class};
		
		rsmPokazMet = new TResultSetMapper<>(PokazMet.class, "pcod",       "name_n",     "stoim",      "c_obst");
		pokazMetTypes = new Class<?>[] {                     String.class, String.class, Double.class, String.class};
		
		rsmPokaz = new TResultSetMapper<>(Pokaz.class, "pcod",       "name_n",     "stoim",      "c_p0e1",      "c_n_nz1");
		pokazTypes = new Class<?>[] {                  String.class, String.class, Double.class, Integer.class, String.class};
		
		rsmAnamZab = new TResultSetMapper<>(AnamZab.class, "id_pvizit",   "npasp",       "t_ist_zab");
		anamZabTypes = new Class<?>[] {                    Integer.class, Integer.class, String.class};
		
		rsmIsslInfo = new TResultSetMapper<>(IsslInfo.class, "nisl",        "cp0e1",       "np0e1",      "cldi",       "nldi",       "zpok",       "datav");
		isslInfoTypes = new Class<?>[] {                     Integer.class, Integer.class, String.class, String.class, String.class, String.class, Date.class};
																																
		rsmPdisp = new TResultSetMapper<>(Pdisp.class, "id_diag",     "npasp",       "id",          "diag",       "pcod",        "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "sob",         "sxoch");
		pdispTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Boolean.class, Boolean.class};
	
		rsmRdSl = new TResultSetMapper<>(RdSlStruct.class, "id",          "npasp",       "datay",    "dataosl",  "abort",       "shet",        "datam",    "yavka1",      "ishod",       "datasn",   "datazs",   "kolrod",      "deti",        "kont",        "vesd",        "dsp",         "dsr",         "dtroch",      "cext",        "indsol",      "prmen",    "dataz",   "datasert",  "nsert",      "ssert",      "oslab",      "plrod",       "prrod",      "vozmen",      "oslrod",      "polj",        "dataab",   "srokab",      "cdiagt",      "cvera",       "id_pvizit");
		rdSlTypes = new Class<?>[] {                       Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, Boolean.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmRdInf = new TResultSetMapper<>(RdInfStruct.class, "npasp",       "obr",        "sem",         "votec",       "grotec",     "photec",     "dataz",    "fiootec",    "mrotec",     "telotec",    "vredotec",    "osoco",       "uslpr");
		rdInfTypes = new Class<?>[] {                        Integer.class, String.class, Integer.class, Integer.class, String.class, String.class, Date.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class};

		rsmRdDin = new TResultSetMapper<>(RdDinStruct.class, "id_rd_sl",    "id_pvizit",   "npasp",       "srok",       "grr",          "ball",        "oj",          "hdm",         "dspos",     "art1",         "art2",        "art3",        "art4",        "oteki",       "spl",         "chcc",        "poplp",       "predpl",      "serd",        "serd1",       "id_pos",      "ves"      );
		rdDinTypes = new Class<?>[] {                        Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};

		rsmPnapr = new TResultSetMapper<>(PNapr.class, "id",          "idpvizit",   "vid_doc",      "text",       "preds",       "zaved",       "name"      );
		pnaprTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, String.class};

	}

	@Override
	public void testConnection() throws TException {
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("UPDATE s_users SET config = ? WHERE id = ? ", false, config, id);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
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
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, tal.vidp, tal.timepn, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, tal.id_pvizit,pat.pol FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?)", idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<ZapVr> getZapVrSrc(String npaspList) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pat.npasp, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom FROM patient pat WHERE pat.npasp IN " + npaspList)) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void AddPvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit (id, npasp, cpol, datao, cod_sp, cdol, cuser, dataz) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", false, obr, pvizitTypes, 0, 1, 2, 4, 8, 9, 10, 12);
			sme.execPrepared("INSERT INTO p_anam_zab (id_pvizit, npasp) VALUES (?, ?) ", false, obr.getId(), obr.getNpasp());
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}
	
	@Override
	public int AddPViz(Pvizit pv) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit (npasp, cpol, datao, cod_sp, cdol, cuser, dataz) VALUES (?, ?, ?, ?, ?, ?, ?) ", true, pv, pvizitTypes, 1, 2, 4, 8, 9, 10, 12);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.execPrepared("INSERT INTO p_anam_zab (id_pvizit, npasp) VALUES (?, ?) ", false, id, pv.getNpasp());
			sme.setCommit();
			return id;
		} catch (InterruptedException | SQLException e) {
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
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ?, cobr = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 13, 12, 3, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePvizit(int obrId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_vizit WHERE id = ? ", false, obrId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit_amb (id_obr, npasp, datap, cod_sp, cdol) VALUES (?, ?, ?, ?, ?) ", true, pos, pvizitAmbTypes, 1, 2, 3, 4, 5);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.execPrepared("INSERT INTO p_priem (id_obr, npasp, id_pos) VALUES (?, ?, ?) ", false, pos.id_obr, pos.npasp, id);
			sme.setCommit();
			return id;
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PvizitAmb> getPvizitAmb(int obrId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT pva.*, get_short_fio(svr.fam, svr.im, svr.ot) AS fio_vr FROM p_vizit_amb pva JOIN s_vrach svr ON (svr.pcod = pva.cod_sp) WHERE id_obr = ? ORDER BY pva.datap", obrId)) {
			return rsmPvizitAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePvizitAmb(PvizitAmb pos) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit_amb SET id_obr = ?, npasp = ?, datap = ?, cod_sp = ?, cdol = ?, diag = ?, mobs = ?, rezult = ?, opl = ?, uet = ?, k_lr = ?, n_sp = ?, pr_opl = ?, pl_extr = ?, vpom = ? WHERE id = ? ", false, pos, pvizitAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 14, 15, 16, 17, 18, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePvizitAmb(int posId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_vizit_amb WHERE id = ? ", false, posId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
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
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PdiagAmb> getPdiagAmb(int obrId) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE id_obr = ? ", obrId)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePdiagAmb(PdiagAmb diag) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_diag_amb SET id_obr = ?, npasp = ?, diag = ?, named = ?, diag_stat = ?, predv = ?, datad = ?, obstreg = ?, cod_sp = ?, cdol = ?, datap = ?, dataot = ?, obstot = ?, cod_spot = ?, cdol_ot = ?, vid_tr = ? WHERE id = ? ", false, diag, pdiagAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePdiagAmb(int diagId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_diag_amb WHERE id = ? ", false, diagId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
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
		} catch (SQLException | InterruptedException e) {
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
			throw new KmiacServerException();
		}
	}

	@Override
	public void setPriem(Priem pr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_priem SET sl_ob = ?, n_is = ?, n_kons = ?, n_proc = ?, n_lek = ?, t_chss = ?, t_temp = ?, t_ad = ?, t_rost = ?, t_ves = ?, t_st_localis = ?, t_ocenka = ?, t_jalob = ?, t_status_praesense = ?, t_fiz_obsl = ? WHERE id_obr = ? AND npasp = ? AND id_pos = ? ", false, pr, priemTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 0, 1, 2);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
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
	public PatientCommonInfo getPatientCommonInfo(int npasp) throws KmiacServerException, PatientNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM patient WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPatComInfo.map(acrs.getResultSet());
			else
				throw new PatientNotFoundException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public Psign getPatientMiscInfo(int npasp) throws KmiacServerException, PatientNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_sign WHERE npasp = ? ", npasp)) {
			if (acrs.getResultSet().next())
				return rsmPsign.map(acrs.getResultSet());
			else
				throw new PatientNotFoundException();
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z30() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z30 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_am0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_am0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_az9() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_az9 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z43() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_z43 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_kas() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_kas ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_n00() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name_u as name FROM n_n00 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_l01() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_l01 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_az0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_az0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_l02() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_l02 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<StringClassifier> get_n_nz1(int cotd) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT nz.pcod, nz.name FROM n_nz1 nz WHERE nz.pcod = ? ", cotd)) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<IntegerClassifier> getVdi() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_vdi ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db1() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db1 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db2() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db2 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db3() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db3 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db4() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db4 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db5() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db5 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_db6() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db6 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db7() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db7 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_db8() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db8 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_db9() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_db9 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<RdDinStruct> getRdDinInfo(int id_pos, int npasp) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_rd_din where id_pos = ? and npasp = ? ", id_pos, npasp)) {
			return rsmRdDin.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}

	}

	@Override
	public RdInfStruct getRdInfInfo(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_r0z ")) {
			if (acrs.getResultSet().next())
				return rsmRdInf.map(acrs.getResultSet());
			else
				throw new KmiacServerException("rd inf not found");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public RdSlStruct getRdSlInfo(int id_pvizit, int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where id_pvizit = ? and npasp = ? ", id_pvizit, npasp)) {
			if (acrs.getResultSet().next())
				return rsmRdSl.map(acrs.getResultSet());
			else
				throw new KmiacServerException("rd sl inf not found");
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<RdSlStruct> getRdSlInfoList(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_rd_sl where npasp = ? ", npasp)) {
			return rsmRdSl.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddRdSl(RdSlStruct rdSl) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_rd_sl (npasp,datay,dataosl,abort,shet,datam,yavka1,ishod,datasn,datazs,kolrod,deti,kont,vesd,dsp,dsr,dtroch,cext,indsol,prmen,dataz,datasert,nsert,ssert,oslab,plrod,prrod,vozmen,oslrod,polj,dataab,srokab,cdiagt,cvera,id_pvizit) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", true, rdSl, rdSlTypes,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void AddRdDin(RdDinStruct RdDin) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rd_din (id_rd_sl, id_pvizit,  npasp,  srok, grr,  ball, oj,  hdm,  dspos, art1,  art2, art3, art4,   oteki, spl,  chcc, poplp, predpl, serd,  serd1, id_pos,ves) VALUES (?, ?,  ?,  ?, ?,  ?, ?,  ?,  ?, ?,  ?, ?, ?,  ?, ?,  ?, ?, ?, ?, ?, ?,?) ", false, RdDin, rdDinTypes, 0, 1, 2, 3, 4, 5, 6, 7,  8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}

	}

	@Override
	public void DeleteRdSl(int id_pvizit, int npasp) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_sl WHERE id_pvizit = ? and npasp = ?", false, id_pvizit, npasp);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void DeleteRdDin(int id_pos, int iD) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_din WHERE id_pos = ? and iD = ?", false, id_pos,iD);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
		
	}

	/*	public void UpdatePvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 13, 12, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}
*/
	@Override
	public void UpdateRdSl(RdSlStruct Dispb) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_sl SET npasp = ?, datay = ?, dataosl = ?, abort = ?, shet = ?, datam = ?, yavka1 = ?, ishod = ?,datasn = ?, datazs = ?,kolrod = ?, deti = ?, kont = ?, vesd = ?, dsp = ?,dsr = ?,dtrosh = ?, cext = ?, indsol = ?, prmen = ?,dataz = ?, datasert = ?, nsert = ?, ssert = ?, oslab = ?, plrod = ?, prrod = ?, vozmen = ?, oslrod = ?, polj = ?, dataab = ?, srokab = ?, cdiag = ?, cvera = ? WHERE id = ?", false, Dispb, rdSlTypes, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
		
	}

	@Override
	public void UpdateRdDin(RdDinStruct din) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_din SET npasp = ?, srok = ?, grr = ?, ball = ?, oj = ?, hdm = ?, dspos = ?, art1 = ?, art2 = ?, art3 = ?,art4 = ?, oteki = ?, spl = ?, chcc = ?, polpl = ?, predpl = ?, serd = ?, serd1 = ?, ves = ?  WHERE id_pvizit = ?", false, din, rdDinTypes, 2,3,4,5,6,7,8,9,10,11,12,13,15,16,17,18,19,21,1);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
		
	}


	@Override
	public void AddRdInf(RdInfStruct rdInf) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rd_inf (npasp, obr, sem, votec, grotec, photec, dataz, fiootec, mrotec, telotec, vredotec, osoco, uslpr) VALUES (?, ?,  ?,  ?, ?,  ?, ?,  ?,  ?, ?,  ?, ?, ?) ", false, rdInf, rdInfTypes, 0, 1, 2, 3, 4, 5, 6, 7,  8, 9, 10, 11, 12);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	
	}

	@Override
	public void DeleteRdInf(int npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_rd_inf WHERE npasp = ? ", false, npasp);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
	
	}

	@Override
	public void UpdateRdInf(RdInfStruct inf) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_rd_inf SET obr = ?,sem = ?, votec = ?, grotec = ?, photec = ?, dataz = ?, fiootec = ?, mrotec = ?, telotec = ?, vredotec = ?, osoco = ?, uslpr = ? WHERE npasp = ?", false, inf, rdInfTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
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
			throw new KmiacServerException();
		}
	}


	@Override
	public List<PokazMet> getPokazMet(String metod) throws KmiacServerException, TException {
		String sql = "SELECT no.obst AS c_obst, nl.name_n, ns.pcod, ns.stoim " + 
					"FROM n_nsi_obst no JOIN n_stoim ns ON (ns.c_obst = no.obst) JOIN n_ldi nl ON (ns.pcod = nl.pcod) " +
					"WHERE no.obst = ? " +
					"ORDER BY no.obst, ns.pcod ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, metod)) {
			return rsmPokazMet.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
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
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void AddPrezd(Prez_d di) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl, stoim) VALUES (?, ?, ?, ?) ", false, di, prezdTypes, 0, 1, 2, 3);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void AddPrezl(Prez_l li) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok, stoim) VALUES (?, ?, ?, ?) ", false, li, prezlTypes, 0, 1, 2, 3);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printIsslMetod(IsslMet im) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\111.htm"), "utf-8")) {
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
				if (acrs.getResultSet().next()){
				sb.append("<td style=\"border: 1px solid black; padding: 5px;\" width=\"60%\">");
					sb.append(String.format("<h3>%s<br />", im.getClpu_name()));
					sb.append(String.format("%s<br />", im.getCpodr_name()));
					String vrInfo = String.format("%s %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3));}
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT name FROM n_p0e1 WHERE pcod = ?", im.getKodVidIssl());
					if (acrs.getResultSet().next())
					sb.append(String.format("Направление на: %s</h3>", acrs.getResultSet().getString(1)));
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom FROM patient WHERE npasp = ? ", im.getNpasp());
					if (acrs.getResultSet().next()){
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
					acrs = sse.execPreparedQuery("SELECT v.fam, v.im, v.ot FROM s_users u JOIN s_vrach v ON (v.pcod = u.pcod) WHERE u.id = ? ", im.getUserId());
					if (acrs.getResultSet().next())
					sb.append(String.format("<b>Врач:</b> %s %s %s <br>", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
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
			return "e:\\111.htm";
		} catch (SQLException | IOException  e) {
			throw new KmiacServerException();
		}
	}

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
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_m00(int clpu) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT m.pcod, m.name FROM n_m00 m JOIN n_lds l ON (m.pcod = l.clpu) WHERE m.pcod != ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_lds_n_m00(int clpu) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT l.pcod,l.name FROM n_m00 m JOIN n_lds l ON (m.pcod = l.clpu) WHERE m.pcod = ? ", clpu)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printNapr(Napr na) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\napr.htm"), "utf-8")) {
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
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", na.getCpodr_name(), na.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
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
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom,adm_kv FROM patient WHERE npasp = ? ", na.getNpasp());
				if (acrs.getResultSet().next()){
			sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append("<br>6. Место работы, должность: _______________________________________________________");}
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
							return "e:\\napr.htm";
						} catch (SQLException | IOException e) {
							throw new KmiacServerException();
						}

	}

	@Override
	public String printNaprKons(NaprKons nk) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\naprKons.htm"), "utf-8")) {
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
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", nk.getCpodr_name(), nk.getClpu_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
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
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom,adm_kv FROM patient WHERE npasp = ? ", nk.getNpasp());
				if (acrs.getResultSet().next()){
			sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append("<br>6. Место работы, должность: _______________________________________________________");}
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
							return "e:\\naprKons.htm";
						} catch (SQLException | IOException  e) {
							throw new KmiacServerException();
						}
	}

	@Override
	public String printVypis(Vypis vp) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\vypis.htm"), "utf-8")) {
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
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				sb.append(String.format("%s, %s", vp.getClpu_name(), vp.getCpodr_name()));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 027/у<br> Утверждена Минздравом СССР<br>04.10.80 г. № 1090</div>");
				sb.append("<br><br><br><br><br><br><br><br>");
				sb.append("<h3 align=center>ВЫПИСКА</h3>");
				sb.append("<h4 align=center>из медицинской карты амбулаторного больного</h4><br>в _____________________________________________________________");//пока черта, потому что в табл.patient поле mrab и спр.z43.pcod разные типы данных.дб одинаковые
				sb.append("<br><div align=\"left\"><sub>название и адрес учреждения, куда направляется выписка</sub></div><br><br>");
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar,adm_ul,adm_dom,adm_kv FROM patient where npasp=?", vp.getNpasp());
				if (acrs.getResultSet().next()) {
				sb.append(String.format("1. Ф.И.О.</b> %s  %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
				sb.append(String.format("<br>2. Дата рождения:  %1$td.%1$tm.%1$tY<br>", acrs.getResultSet().getDate(4)));
				sb.append(String.format("3. Домашний адрес %s  %s-%s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6), acrs.getResultSet().getString(7)));
				acrs.close();
				sb.append("<br>4. Место работы и род занятий ___________________________________________________<br>");}
				sb.append("5. Даты: по амбулатории: заболевания ");
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
			return "e:\\vypis.htm";
		} catch (SQLException | IOException  e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printKek(int npasp, int pvizitId) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\kek.htm"), "utf-8")) {
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
				sb.append("<br> <b>Решение комиссии</b>________________________________________ <br>");
				sb.append("__________________________________________________________<br>");
				sb.append("__________________________________________________________<br>");
				sb.append("__________________________________________________________<br>");
				sb.append("__________________________________________________________<br>");
			sb.append("<b>Подпись членов комиссии</b> <br>");
			sb.append("<b>Председатель КЭК</b>________________ <br>");
			sb.append("<b>Зав.отделением</b>___________________ <br>");
			sb.append("<b>Лечащий врач</b>________________________ <br>");
			sb.append(String.format("<p align=\"right\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			acrs.close();
			osw.write(sb.toString());
			return "e:\\kek.htm";}
		 catch (SQLException | IOException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PdiagZ> getPdiagzProsm(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_diag WHERE npasp = ? ", npasp)) {
			return rsmPdiagZ.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<Pvizit> getPvizitInfo(int npasp, long datan, long datak) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT * FROM p_vizit WHERE npasp = ? AND datao BETWEEN ? AND ? ", npasp, new Date(datan), new Date(datak))) {
			return rsmPvizit.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
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
			throw new KmiacServerException();
		}
	}

	@Override
	public void setAnamZab(AnamZab anam) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("UPDATE p_anam_zab SET  t_ist_zab = ? WHERE id_pvizit = ? ", false, anam, anamZabTypes, 2, 0);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IsslInfo> getIsslInfo(int pvizit_id) throws KmiacServerException, TException {
		String sql = "SELECT il.nisl, p0.pcod AS cp0e1, p0.name AS np0e1, ld.pcod AS cldi, ld.name_n AS nldi, rl.zpok, il.datav " + 
					"FROM p_isl_ld il JOIN p_rez_l rl ON (rl.nisl = il.nisl) JOIN n_ldi ld ON (ld.pcod = rl.cpok) JOIN n_p0e1 p0 ON (p0.pcod = il.cisl) " +
					"WHERE il.pvizit_id = ? " +
					"UNION " +
					"SELECT il.nisl, p0.pcod AS cp0e1, p0.name AS np0e1, ld.pcod AS cldi, ld.name_n AS nldi, rz.name, il.datav " +
					"FROM p_isl_ld il JOIN p_rez_d rd ON (rd.nisl = il.nisl) JOIN n_ldi ld ON (ld.pcod = rd.kodisl) JOIN n_arez rz ON (rz.pcod = rd.rez) JOIN n_p0e1 p0 ON (p0.pcod = il.cisl) " +
					"WHERE il.pvizit_id = ? ";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, pvizit_id, pvizit_id)) {
			return rsmIsslInfo.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
		
	}

	@Override
	public List<IntegerClassifier> get_n_ai0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_ai0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_abs() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_abs ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_abv() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_abv ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_abx() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_abx ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_aby() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_aby ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_abc() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_abc ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_abb() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_abb ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PdiagAmb> getPdiagAmbProsm(int npasp) throws KmiacServerException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_diag_amb WHERE npasp = ? AND predv = FALSE ", npasp)) {
			return rsmPdiagAmb.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
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
	public List<IntegerClassifier> getShablonTexts(int id_razd, int id_pok, String pcod_s00) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT t.id AS pcod, t.text AS name FROM sh_s_text t JOIN sh_s_cdol c ON (c.id_razd = t.id_razd AND c.id_pok = t.id_pok AND c.pcod_s00 = t.pcod_s00) WHERE c.checked = true AND c.id_razd = ? AND c.id_pok = ? AND c.pcod_s00 = ? ", id_razd, id_pok, pcod_s00)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> getShablonCdol(int id_razd, String pcod_s00) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT id_pok AS pcod, pcod_s00 AS name FROM sh_s_cdol WHERE checked = true AND id_razd = ? AND pcod_s00 = ? ", id_razd, pcod_s00)) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<IntegerClassifier> getPokNames() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT id AS pcod, name FROM sh_n_pok ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new TException(e);
		}
	}

	@Override
	public PdiagZ getPdiagZ(int id_diag_amb) throws PdiagNotFoundException, KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT d.*, c.name AS nameC00 FROM p_diag d LEFT JOIN n_c00 c ON (c.pcod = d.diag) WHERE d.id_diag_amb = ? ", id_diag_amb)) {
			if (acrs.getResultSet().next())
				return rsmPdiagZ.map(acrs.getResultSet());
			else
				throw new PdiagNotFoundException();
		} catch (SQLException e) {
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
		} catch (SQLException | InterruptedException e) {
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
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}
	}
	
	@Override
	public String printProtokol(Protokol pk) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\protokol.htm"), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Случай заболевания</title>");
				sb.append("</head>");
				sb.append("<body>");
				acrs = sse.execPreparedQuery("SELECT datao,cobr,n_p0c.name FROM p_vizit join n_p0c on(p_vizit.cobr=n_p0c.pcod) where id=?", pk.getPvizit_id());
				if (acrs.getResultSet().next()) {sb.append(String.format("<b>Дата перв.обращения</b> %1$td.%1$tm.%1$tY", acrs.getResultSet().getDate(1)));
				sb.append(String.format("<br><b>Цель обращения </b>%s", acrs.getResultSet().getString(3)));}
				
				acrs.close();
				acrs = sse.execPreparedQuery("select t_ist_zab from p_anam_zab where id_pvizit=?", pk.getPvizit_id()); 
				if (acrs.getResultSet().next()) {
			sb.append("<br><b>	Анамнез заболевания</b><br>");
					if (acrs.getResultSet().getString(1)!=null) sb.append(String.format(" %s.", acrs.getResultSet().getString(1)));
				}				
					acrs.close();
					acrs = sse.execPreparedQuery("select p_vizit_amb.*,p_priem.*,n_abs.name,n_opl.name,n_ap0.name from p_vizit_amb join p_priem on (p_priem.id_pos=p_vizit_amb.id) left join n_abs on(p_vizit_amb.mobs=n_abs.pcod) left join n_opl on(p_vizit_amb.opl=n_opl.pcod) left join n_ap0 on(p_vizit_amb.rezult=n_ap0.pcod) where p_vizit_amb.id_obr=? order by id ", pk.getPvizit_id());
					if (acrs.getResultSet().next()) {
						sb.append("<br><b>Осмотр: </b><br>");
						do {
							sb.append(String.format("Дата посещения %1$td.%1$tm.%1$tY <br>", acrs.getResultSet().getDate(4)));
							if (acrs.getResultSet().getString(37)!=null) sb.append(String.format("<i>Место обслуживания </i> %s <br>", acrs.getResultSet().getString(37)));
							if (acrs.getResultSet().getString(38)!=null) sb.append(String.format("<i>Способ оплаты </i> %s <br>", acrs.getResultSet().getString(38)));
							if (acrs.getResultSet().getString(33)!=null) sb.append(String.format("<i>Жалобы: </i> %s <br>", acrs.getResultSet().getString(33)));
							if (acrs.getResultSet().getString(27)!=null) sb.append(String.format("<i>Температура </i> %s <br>", acrs.getResultSet().getString(27)));
							if (acrs.getResultSet().getString(28)!=null) sb.append(String.format("<i>АД </i> %s <br>", acrs.getResultSet().getString(28)));
							if (acrs.getResultSet().getString(29)!=null) sb.append(String.format("<i>Рост </i> %s <br>", acrs.getResultSet().getString(29)));
							if (acrs.getResultSet().getString(30)!=null) sb.append(String.format("<i>Вес </i> %s <br>", acrs.getResultSet().getString(30)));
							if (acrs.getResultSet().getString(36)!=null) sb.append(String.format("<i>ЧСС </i> %s <br>", acrs.getResultSet().getString(36)));
							if (acrs.getResultSet().getString(34)!=null) sb.append(String.format("<i>Status praesense </i> %s <br>", acrs.getResultSet().getString(34)));
							if (acrs.getResultSet().getString(35)!=null) sb.append(String.format("<i>Физикальное обследование </i> %s <br>", acrs.getResultSet().getString(35)));
							if (acrs.getResultSet().getString(31)!=null) sb.append(String.format("<i>Localis status </i> %s <br>", acrs.getResultSet().getString(31)));
							if (acrs.getResultSet().getString(32)!=null) sb.append(String.format("<i>Оценка данных анамнеза и объективного исследования </i> %s <br>", acrs.getResultSet().getString(32)));
							if (acrs.getResultSet().getString(39)!=null) sb.append(String.format("<i>Результат </i> %s <br>", acrs.getResultSet().getString(39)));
						} while (acrs.getResultSet().next());
					}
					
					
				sb.append("<br><b>Поставленные диагнозы: </b><br>");
				
				acrs.close();
				acrs=sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pk.getPvizit_id());
				if (acrs.getResultSet().next()) {
				sb.append("основное заболевание ");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", pk.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null) sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
				}
				sb.append("<br>осложнение основного заболевания <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=2 and predv=false order by datap", pk.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}				
				sb.append("<br>сопутствующее заболевание <br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=3 and predv=false order by datap", pk.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null){sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));}else {sb.append(" -<br>");}
					}
				
				acrs.close();
				acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_p0e1.pcod , n_p0e1.name , n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) join n_p0e1  on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? " +
					"union " +
					"select p_isl_ld.nisl, n_p0e1.pcod, n_p0e1.name , n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) join n_arez  on (n_arez.pcod = p_rez_d.rez) join n_p0e1 on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? ", pk.getPvizit_id(), pk.getPvizit_id());
				if (acrs.getResultSet().next()) {
				sb.append("<br><br><b>Назначенные исследования </b><br>");sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
				while (acrs.getResultSet().next()){
					sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
	}			
	}
acrs.close();
				acrs = sse.execPreparedQuery("select p_vizit.recomend,p_vizit.zakl,n_aq0.name from p_vizit left join n_aq0 on (p_vizit.ishod=n_aq0.pcod) where id=?", pk.getPvizit_id()); 
if (acrs.getResultSet().next()) {
				if (acrs.getResultSet().getString(1)!=null) sb.append("<br><b> Лечебные и трудовые рекомендации</b>");sb.append(String.format("%s", acrs.getResultSet().getString(1)));
				if (acrs.getResultSet().getString(2)!=null) sb.append("<br><b> Заключение </b>");sb.append(String.format("%s", acrs.getResultSet().getString(2)));
				if (acrs.getResultSet().getString(3)!=null) sb.append("<br><b> Исход </b>");sb.append(String.format("%s", acrs.getResultSet().getString(3)));
				}

				sb.append("<br>");}

			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
sb.append("Лечащий врач:");
acrs.close();
acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",pk.getUserId());
				if (acrs.getResultSet().next()) { 
sb.append(String.format("%s %s %s",acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));}
sb.append("<br>Подпись ____________");
		acrs.close();
			osw.write(sb.toString());
			return "e:\\protokol.htm";}
		 catch (SQLException | IOException  e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePriem(int posId) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_priem WHERE id_pos = ? ", false, posId);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}		
	}

	@Override
	public void DeleteAnamZab(int pvizit_id) throws KmiacServerException,
			TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("DELETE FROM p_anam_zab WHERE id_pvizit = ? ", false, pvizit_id);
			sme.setCommit();
		} catch (SQLException | InterruptedException e) {
			throw new KmiacServerException();
		}	
	}

	@Override
	public List<IntegerClassifier> get_n_z00() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_z00 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z11() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_z11 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_r0z() throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_r0z ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<PNapr> getPnapr(int idpvizit) throws KmiacServerException,
			TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("select * from p_napr join n_vr_doc on(p_napr.vid_doc=n_vr_doc.pcod) where p_napr.id_pvizit = ?", idpvizit)) {
			return rsmPnapr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
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
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printMSK() throws KmiacServerException, TException {
			try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\msk.htm"), "utf-8")) {
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
				sb.append("<br>НАПРАВЛЕНИЕ НА МЕДИКО-СОЦИАЛЬЛЬНУЮ ЭКСПЕРТИЗУ ОРГАНИЗАЦИЕЙ, <br>ОКАЗЫВАЮЩЕЙ ЛЕЧЕБНО-ПРОФИЛАКТИЧЕСКУЮ ПОМОЩЬ");
				sb.append("</p>");
				sb.append("<br>Дата выдачи '__' _______ 20__ г.");
				sb.append("<br>");
				sb.append("1. Фамилия,имя, отчество гражданина, направляемого на медико-социальную экспертизу (далее гражданин):______________________");
				sb.append("<br>");
				sb.append("2. Дата рождения: ______________");
				sb.append("3. Пол: ______________");
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
				return "e:\\msk.htm";
		} catch (IOException  e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printKartaBer() throws KmiacServerException, TException {
				try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:\\kartl.htm"), "utf-8")) {
			AutoCloseableResultSet acrs;
			
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
				sb.append("1. Фамилия, имя, отчество __________________________________________________________________________________<br>");
				sb.append("2. Возраст _____ лет 3. Адрес и телефон ____________________________________________________________________");
				sb.append("<br>4. Перенесенные общие, гинекологические заболевания или операции ___________________________________________");
				sb.append("<br>5. Особенности течения прежних беременностей, родов, послеродового периода _________________________________");
				sb.append("<br>6. Данная беременность ____ (по счету). Роды ____ (по счету)");
				sb.append("<br>");
				sb.append("7. Количество абортов ____ (всего). В  каком году ___________ и на каком сроке _______________");
				sb.append("8. Были ли преждевременные роды : ДА _____ / НЕТ _____ . Если ДА, то в каком году _____________<br>");
				sb.append("9. Дата последней менструации ____________________________________________<br>");
				sb.append("10. Срок текущей беременности составляет ___________________ недель при первом посещении женской консультации (дата)___________ года<br>");
				sb.append("11. Количество посещений ____________<br>");
				sb.append("12. Первое шевеление плода (дата) _________________________");
				sb.append("13. Возможные особенности течения беременности _______________________________________");
				sb.append("<br>14. Размеры таза (при первом посещении)");
				sb.append("<br>D.Sp____ D.Gr_____ D.troch__________");
				sb.append("<br>C.ext____ C.diag_____ C.vera__________");
				sb.append("<br>Вес____ Вес_____ ");
				sb.append("<br>15. Положение плода ____________________________");
				sb.append("<br>Предлежащая часть плода: головка, ягодицы, не определяется ____________________________");
				sb.append("<br>Сердцебинение плода: ясное, ритмичное, ударов _________ в 1 минуту слева, справа _________");
				sb.append("<br>16. Лабораторные и другие исследования");
				sb.append("<br>RW<sub>1</sub>: \"____\" _____________ 20______ года <br>HBS<sub>1</sub>: \"____\" ____________ 20______ года");
				sb.append("<br>RW<sub>2</sub>: \"____\" _____________ 20______ года <br>HBS<sub>2</sub>: \"____\" ____________ 20______ года");
				sb.append("<br>RW<sub>3</sub>: \"____\" _____________ 20______ года <br>HCV<sub>1</sub>: \"____\" ____________ 20______ года");
				sb.append("<br>ВИЧ<sub>1</sub>: \"____\" _____________ 20______ года <br>HCV<sub>2</sub>: \"____\" ____________ 20______ года");
				sb.append("<br>ВИЧ<sub>2</sub>: \"____\" _____________ 20______ года");
				sb.append("<br>Резус положительный/отрицательный/тип крови");
				sb.append("<br>Титр антител:_________");
				sb.append("<br>Группа крови:_________");
				sb.append("<br>Резус-принадлежность крови мужа:__________");
				sb.append("<br>Токсоплазмоз: РСК, кожная проба __________");
				sb.append("<br><b>Клинические анлизы</b>");
				sb.append("<br>Крови _____________________________");
				sb.append("<br>Мочи ______________________________");
				sb.append("<br>Анализ содержимого влагалища (мазок) _______________________________");
				sb.append("<br>Кал на яйца-глист _________________________");
				sb.append("<br>17. Школа матерей _________________");
				sb.append("<br>18. Дата выдачи листка нетрудоспособности по дородовому отпуску \"______\" _________ 20___ г.");
				sb.append("<br>19. Дата предполагаемых родов \"______\" _________ 20___ г.");
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
				osw.write(sb.toString());
				return "e:\\kart.html";
		} catch (IOException  e) {
			throw new KmiacServerException();
		}
	
	}


}

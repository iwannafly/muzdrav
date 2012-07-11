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


	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp",       "vidp",        "timepn",   "fam",        "im",         "ot",         "poms_ser",   "poms_nom",   "id_pvizit");
		zapVrTypes = new Class<?>[] {                  Integer.class, Integer.class, Time.class, String.class, String.class, String.class, String.class, String.class, Integer.class};
		
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id",          "npasp",       "cpol",        "cobr",        "datao",    "ishod",       "rezult",      "talon",       "cod_sp",      "cdol",       "cuser",       "zakl",       "dataz",    "recomend");
		pvizitTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, String.class};
		
		rsmPvizitAmb = new TResultSetMapper<>(PvizitAmb.class, "id",          "id_obr",      "npasp",       "datap",    "cod_sp",      "cdol",       "diag",       "mobs",        "rezult",      "opl",         "stoim",      "uet",         "datak",    "kod_rez",     "k_lr",        "n_sp",        "pr_opl",      "pl_extr",     "vpom",        "fio_vr");
		pvizitAmbTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class};
		
		rsmPdiagAmb = new TResultSetMapper<>(PdiagAmb.class, "id",          "id_obr",      "npasp",       "diag",       "named",      "diag_stat",   "predv",       "datad",    "obstreg",     "cod_sp",      "cdol",       "datap",    "dataot",   "obstot",      "cod_spot",    "cdol_ot",    "vid_tr");
		pdiagAmbTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Boolean.class, Date.class, Integer.class, Integer.class, String.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class};
		
		rsmPdiagZ = new TResultSetMapper<>(PdiagZ.class, "id",          "id_diag_amb", "npasp",       "diag",       "cpodr",       "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "nmvd",        "xzab",        "stady",       "disp",        "pat",         "prizb",       "prizi",       "named",      "nameC00");
		pdiagZTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class};
		
		rsmPsign = new TResultSetMapper<>(Psign.class, "npasp",       "grup",       "ph",         "allerg",     "farmkol",    "vitae",      "vred",       "razv",       "uslov",      "per_zab",    "per_oper",   "gemotrans",     "nasl",       "ginek",      "priem_lek",  "prim_gorm");
		psignTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPriem = new TResultSetMapper<>(Priem.class, "id_obr",      "npasp",       "id_pos",      "sl_ob",       "n_is",        "n_kons",      "n_proc",      "n_lek",       "t_jalob_d",  "t_jalob_krov", "t_jalob_p",  "t_jalob_moch", "t_jalob_endo", "t_jalob_nerv", "t_jalob_opor", "t_jalob_lih", "t_jalob_obh", "t_jalob_proch", "t_ob_sost",  "t_koj_pokr", "t_sliz",     "t_podk_kl",  "t_limf_uzl", "t_kost_mysh", "t_nervn_ps", "t_chss",     "t_temp",     "t_ad",       "t_rost",     "t_ves",      "t_telo",     "t_sust",     "t_dyh",      "t_gr_kl",    "t_perk_l",   "t_aus_l",    "t_bronho",   "t_arter",    "t_obl_s",    "t_perk_s",   "t_aus_s",    "t_pol_rta",  "t_jivot",    "t_palp_jivot", "t_jel_kish", "t_palp_jel", "t_palp_podjel", "t_pechen",   "t_jelch",    "t_selez",    "t_obl_zad",  "t_poyasn",   "t_pochk",    "t_moch",     "t_mol_jel", "t_gr_jel",    "t_matka",    "t_nar_polov", "t_chitov",   "t_st_localis", "t_ocenka",   "t_jalob",    "t_status_praesense", "t_fiz_obsl");
		priemTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class,   String.class, String.class,   String.class,   String.class,   String.class,   String.class,  String.class,  String.class,    String.class, String.class, String.class, String.class, String.class, String.class,  String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,   String.class, String.class, String.class,    String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,  String.class, String.class,   String.class, String.class, String.class,         String.class};
		
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
		
		rsmAnamZab = new TResultSetMapper<>(AnamZab.class, "id_pvizit",   "npasp",       "t_nachalo_zab", "t_sympt",    "t_otn_bol", "t_ps_syt",    "t_ist_zab");
		anamZabTypes = new Class<?>[] {                    Integer.class, Integer.class, String.class,    String.class, String.class, String.class, String.class};
		
		rsmIsslInfo = new TResultSetMapper<>(IsslInfo.class, "nisl",        "cp0e1",       "np0e1",      "cldi",       "nldi",       "zpok",       "datav");
		isslInfoTypes = new Class<?>[] {                     Integer.class, Integer.class, String.class, String.class, String.class, String.class, Date.class};
																																
		rsmPdisp = new TResultSetMapper<>(Pdisp.class, "id_diag",     "npasp",       "id",          "diag",       "pcod",        "d_vz",     "d_grup",      "ishod",       "dataish",  "datag",    "datad",    "diag_s",     "d_grup_s",    "cod_sp",      "cdol_ot",    "sob",         "sxoch");
		pdispTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, String.class, Integer.class, Date.class, Integer.class, Integer.class, Date.class, Date.class, Date.class, String.class, Integer.class, Integer.class, String.class, Boolean.class, Boolean.class};
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, tal.vidp, tal.timepn, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom, tal.id_pvizit FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?)", idvr, cdol, new Date(datap))) {
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
			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, zakl = ?, recomend = ?, dataz = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 13, 12, 0);
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
				sme.execPreparedT("UPDATE p_sign SET grup = ?, ph = ?, allerg = ?, farmkol = ?, vitae = ?, vred = ?, razv = ?, uslov = ?, per_zab = ?, per_oper = ?, gemotrans = ?, nasl = ?, ginek = ?, priem_lek = ?, prim_gorm = ? WHERE npasp = ? ", false, sign, psignTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0);
				sme.setCommit();
			} catch (PsignNotFoundException e) {
				sme.execPreparedT("INSERT INTO p_sign (npasp, grup, ph, allerg, farmkol, vitae, vred, razv, uslov, per_zab, per_oper, gemotrans, nasl, ginek, priem_lek, prim_gorm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", false, sign, psignTypes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
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
			sme.execPreparedT("UPDATE p_priem SET sl_ob = ?, n_is = ?, n_kons = ?, n_proc = ?, n_lek = ?, t_jalob_d = ?, t_jalob_krov = ?, t_jalob_p = ?, t_jalob_moch = ?, t_jalob_endo = ?, t_jalob_nerv = ?, t_jalob_opor = ?, t_jalob_lih = ?, t_jalob_obh = ?, t_jalob_proch = ?, t_ob_sost = ?, t_koj_pokr = ?, t_sliz = ?, t_podk_kl = ?, t_limf_uzl = ?, t_kost_mysh = ?, t_nervn_ps = ?, t_chss = ?, t_temp = ?, t_ad = ?, t_rost = ?, t_ves = ?, t_telo = ?, t_sust = ?, t_dyh = ?, t_gr_kl = ?, t_perk_l = ?, t_aus_l = ?, t_bronho = ?, t_arter = ?, t_obl_s = ?, t_perk_s = ?, t_aus_s = ?, t_pol_rta = ?, t_jivot = ?, t_palp_jivot = ?, t_jel_kish = ?, t_palp_jel = ?, t_palp_podjjel = ?, t_pechen = ?, t_jelch = ?, t_selez = ?, t_obl_zad = ?, t_poyasn = ?, t_pochk = ?, t_moch = ?, t_mol_jel = ?, t_gr_jel = ?, t_matka = ?, t_nar_polov = ?, t_chitov = ?, t_st_localis = ?, t_ocenka = ?, t_jalob = ?, t_status_praesense = ?, t_fiz_obsl = ? WHERE id_obr = ? AND npasp = ? AND id_pos = ? ", false, pr, priemTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 0, 1, 2);
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
	public List<StringClassifier> getP0c() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p0c ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

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
	public List<IntegerClassifier> getAp0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_ap0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
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
	public List<IntegerClassifier> getAq0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_aq0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_s00() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_s00 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> getOpl() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_opl ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_p0e1() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_p0e1 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_nz1(int c_p0e1) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT DISTINCT nz.pcod, nz.name FROM n_nz1 nz JOIN n_ldi nl ON (nl.c_nz1 = nz.pcod) WHERE nl.c_p0e1 = ? ", c_p0e1)) {
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
	public List<RdSlStruct> getRdSlInfo(int idDispb, int npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RdDinStruct> getRdDinInfo(int idDispb, int npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddRdSl(RdSlStruct rdSl) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddRdDin(RdDinStruct RdDin) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteRdSl(int idDispb, int npasp) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteRdDin(int idDispb, int iD) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRdSl(int npasp, int lgota) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRdDin(int idDispb, int iD) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RdInfStruct> getRdInfInfo(int idDispb, int npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddRdInf(RdInfStruct rdInf) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteRdInf(int idDispb, int npasp)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRdInf(int npasp, int lgota) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		
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
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\111.htm"), "utf-8")) {
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
				acrs = sse.execPreparedQuery("SELECT n.name, m.name, v.fam, v.im, v.ot FROM s_users u JOIN n_n00 n ON (n.pcod = u.cpodr) JOIN n_m00 m ON (m.pcod = n.clpu) JOIN s_vrach v ON (v.pcod = u.pcod) WHERE u.id = ? ", im.getUserId());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");
				sb.append("<td style=\"border: 1px solid black; padding: 5px;\" width=\"60%\">");
					sb.append(String.format("<h3>%s<br />", acrs.getResultSet().getString(1)));
					sb.append(String.format("%s<br />", acrs.getResultSet().getString(2)));
					String vrInfo = String.format("%s %s %s", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4), acrs.getResultSet().getString(5));
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT name FROM n_p0e1 WHERE pcod = ?", im.getKodVidIssl());
					if (!acrs.getResultSet().next())
						throw new KmiacServerException("Exam info info not found.");
					sb.append(String.format("Направление на: %s</h3>", acrs.getResultSet().getString(1)));
					acrs.close();
					acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom FROM patient WHERE npasp = ? ", im.getNpasp());
					if (!acrs.getResultSet().next())
						throw new KmiacServerException("Logged user info not found.");
					sb.append(String.format("<b>ФИО пациента:</b> %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
					sb.append(String.format("<b>Дата рождения:</b> %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
					sb.append(String.format("<b>Адрес:</b> %s, %s<br />", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
					sb.append("<b>Диагноз:</b><br />");
					acrs.close();
					acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", im.getPvizitId());
					if (!acrs.getResultSet().next()) 
						throw new KmiacServerException("Diag is null");
					sb.append(String.format("%s <br>", acrs.getResultSet().getString(1)));

					sb.append(String.format("<b>Врач:</b> %s<br />", vrInfo));
					sb.append("<h3>Наименование показателей:</h3>");
					sb.append("<ol>");
					for (String str : im.getPokaz()) {
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
			return "c:\\111.htm";
		} catch (SQLException | IOException | KmiacServerException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printIsslPokaz(IsslPokaz ip) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\NaprIsslPokaz.htm"), "utf-8")) {
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
				acrs = sse.execPreparedQuery("SELECT n.name, m.name, v.fam, v.im, v.ot FROM s_users u JOIN n_n00 n ON (n.pcod = u.cpodr) JOIN n_m00 m ON (m.pcod = n.clpu) JOIN s_vrach v ON (v.pcod = u.pcod) WHERE u.id = ? ", ip.getUserId());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");
				sb.append("<td style=\"border: 1px solid black; padding: 5px;\" width=\"60%\">");
					sb.append(String.format("<h3>%s<br />", acrs.getResultSet().getString(1)));
					sb.append(String.format("%s<br />", acrs.getResultSet().getString(2)));
					String vrInfo = String.format("%s %s %s", acrs.getResultSet().getString(3), acrs.getResultSet().getString(4), acrs.getResultSet().getString(5));
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
			return "c:\\NaprIsslPokaz.htm";
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
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\napr.htm"), "utf-8")) {
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
				acrs = sse.execPreparedQuery("select n_n00.name,n_m00.name,s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join n_n00 on (s_users.cpodr=n_n00.pcod) join n_m00 on (n_n00.clpu=n_m00.pcod) join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",na.getUserId());
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Cpol is null");//заменить текст
				sb.append(String.format("%s, %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2)));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h2 align=center>Направление </h2>");
				sb.append(String.format("<p align=\"center\"><b>на госпитализацию</b></p>"));
			 	sb.append(String.format("<br>Куда %s", na.getClpu()));
			 	sb.append("<br><br>");
			 	sb.append("1. Номер страхового полиса ОМС: " );
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT poms_nom FROM patient WHERE npasp = ? ", na.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
		
			sb.append("<br>2. Код льготы: ");
			acrs.close();
				acrs = sse.execPreparedQuery("SELECT lgot FROM p_kov WHERE npasp = ? ", na.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Lgot is null");
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom,adm_kv FROM patient WHERE npasp = ? ", na.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");	
			sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append("<br>6. Место работы, должность: _______________________________________________________");
			sb.append("<br>7. Код диагноза по МКБ: ");
			acrs.close();
			acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", na.getPvizitId());
			if (!acrs.getResultSet().next()) 
				throw new KmiacServerException("Diag is null");
			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
			sb.append(String.format("<br>8. Обоснование направления: %s",na.getObosnov()));
			sb.append("<br>Должность медицинского работника, направившего больного: ");
			acrs.close();
			acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",na.getUserId());
			if (!acrs.getResultSet().next())
				throw new KmiacServerException("Logged user info not found.");	
			sb.append(String.format("%s ", acrs.getResultSet().getString(4)));
			sb.append(String.format("<br>ФИО: %s %s %s", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
			sb.append(" Подпись_______________");
			sb.append("<br>Заведующий отделением_____________________________________________________________________________");
			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			sb.append("<br>МП");
			acrs.close();
							osw.write(sb.toString());
							return "c:\\napr.htm";
						} catch (SQLException | IOException | KmiacServerException e) {
							throw new KmiacServerException();
						}

	}

	@Override
	public String printNaprKons(NaprKons nk) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\naprKons.htm"), "utf-8")) {
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
				acrs = sse.execPreparedQuery("select n_n00.name,n_m00.name,s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join n_n00 on (s_users.cpodr=n_n00.pcod) join n_m00 on (n_n00.clpu=n_m00.pcod) join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",nk.getUserId());
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Cpol is null");//заменить текст
				sb.append(String.format("%s, %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2)));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 057/у-04<br> Утверждена приказом Минсоцздравразвития России<br>от 22 ноября 2004 г. №255</div>");
				sb.append("<br><br><br><br><br><br><br><br><br><br><br>");
				sb.append("<h2 align=center>Направление </h2>");
				sb.append(String.format("<p align=\"center\"><b>на %s</b></p>",nk.getNazv()));
			 	sb.append(String.format("<br> Куда: %s", nk.getCpol()));
			 	sb.append("<br><br>");
			 	sb.append("1. Номер страхового полиса ОМС: " );
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT poms_nom FROM patient WHERE npasp = ? ", nk.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
		
			sb.append("<br>2. Код льготы: ");
			acrs.close();
				acrs = sse.execPreparedQuery("SELECT lgot FROM p_kov WHERE npasp = ? ", nk.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Lgot is null");
				sb.append(String.format(" %s ", acrs.getResultSet().getString(1)));
			 	acrs.close();
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar, adm_ul, adm_dom,adm_kv FROM patient WHERE npasp = ? ", nk.getNpasp());
				if (!acrs.getResultSet().next())
					throw new KmiacServerException("Logged user info not found.");	
			sb.append(String.format("<br>3. Фамилия, имя, отчество: %s %s %s<br />", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("4. Дата рождения: %1$td.%1$tm.%1$tY<br />", acrs.getResultSet().getDate(4)));
			sb.append(String.format("5. Адрес: %s %s - %s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6),acrs.getResultSet().getString(7)));
			sb.append("<br>6. Место работы, должность: _______________________________________________________");
			sb.append("<br>7. Код диагноза по МКБ: ");
			acrs.close();
			acrs = sse.execPreparedQuery("select diag from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", nk.getPvizitId());
			if (!acrs.getResultSet().next()) 
				throw new KmiacServerException("Diag is null");
			sb.append(String.format("%s", acrs.getResultSet().getString(1)));
			sb.append(String.format("<br>8. Обоснование направления: %s",nk.getObosnov()));
			sb.append("<br>Должность медицинского работника, направившего больного: ");
			acrs.close();
			acrs = sse.execPreparedQuery("SELECT s_vrach.fam, s_vrach.im, s_vrach.ot,n_s00.name from s_mrab "+ 
  "join n_s00 on(s_mrab.cdol=n_s00.pcod)  join s_vrach on "+ 
  "(s_vrach.pcod=s_mrab.pcod) WHERE s_mrab.user_id = ? ",nk.getUserId());
			if (!acrs.getResultSet().next())
				throw new KmiacServerException("Logged user info not found.");	
			sb.append(String.format("%s", acrs.getResultSet().getString(4)));
			sb.append(String.format("<br>ФИО врача: %s %s %s", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
			sb.append("<br>Подпись_______________");
			sb.append("<br>Заведующий отделением _____________________________________________________________________________");
			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			sb.append("<br>МП");
			acrs.close();
							osw.write(sb.toString());
							return "c:\\naprKons.htm";
						} catch (SQLException | IOException | KmiacServerException e) {
							throw new KmiacServerException();
						}
	}

	@Override
	public String printVypis(int npasp, int pvizitId, int userId) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\vypis.htm"), "utf-8")) {
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
				acrs = sse.execPreparedQuery("select n_n00.name,n_m00.name,s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join n_n00 on (s_users.cpodr=n_n00.pcod) join n_m00 on (n_n00.clpu=n_m00.pcod) join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",userId);
				sb.append("<br>	<div style=\"background:000000;width:240px; float:left;\">Министерство здравоохранения и социального<br> развития Российской Федерации<br>");
				sb.append("<br>");
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Vrach is null");//заменить текст
				sb.append(String.format("%s, %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2)));
				sb.append("</div>"); 
				sb.append("<div  style=\"background:000000;width:150px; float:right;\">Медицинская документация<br>Форма № 027/у<br> Утверждена Минздравом СССР<br>04.10.80 г. № 1090</div>");
				sb.append("<br><br><br><br><br><br><br><br>");
				sb.append("<h3 align=center>ВЫПИСКА</h3>");
				sb.append("<h4 align=center>из медицинской карты амбулаторного больного</h4><br>в _____________________________________________________________");//пока черта, потому что в табл.patient поле mrab и спр.z43.pcod разные типы данных.дб одинаковые
				sb.append("<br><div align=\"left\"><sub>название и адрес учреждения, куда направляется выписка</sub></div><br><br>");
				acrs.close();
				acrs = sse.execPreparedQuery("SELECT fam, im, ot, datar,adm_ul,adm_dom,adm_kv FROM patient where npasp=?", npasp);
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Patient is null");//заменить текст
				sb.append(String.format("1. Ф.И.О.</b> %s  %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
				sb.append(String.format("<br>2. Дата рождения:  %1$td.%1$tm.%1$tY<br>", acrs.getResultSet().getDate(4)));
				sb.append(String.format("3. Домашний адрес %s  %s-%s", acrs.getResultSet().getString(5), acrs.getResultSet().getString(6), acrs.getResultSet().getString(7)));
				acrs.close();
				sb.append("<br>4. Место работы и род занятий ___________________________________________________<br>");
				sb.append("5. Даты: по амбулатории: заболевания ");
				acrs.close();
				acrs = sse.execPreparedQuery("select datap from p_vizit join p_vizit_amb on (p_vizit.id=p_vizit_amb.id_obr) where p_vizit.id=? order by datap", pvizitId);
				acrs.getResultSet().next();
				Date tmpDate = acrs.getResultSet().getDate(1);
				sb.append(String.format("%1$td.%1$tm.%1$tY - ", tmpDate));
				while (acrs.getResultSet().next())
					tmpDate = acrs.getResultSet().getDate(1);
				sb.append(String.format("%1$td.%1$tm.%1$tY", tmpDate));
				sb.append("<br>6. Полный диагноз: <br>");
				acrs.close();
				acrs=sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pvizitId);
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Diag is null");//заменить текст
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
				sb.append("<br>	7. Краткий анамнез, диагностические исследования, течение болезни<br>");
				acrs.close();
				acrs = sse.execPreparedQuery("select t_nachalo_zab,t_sympt,t_otn_bol,t_ps_syt from p_anam_zab where id_pvizit=?", pvizitId); 
if (!acrs.getResultSet().next()) 
					throw new KmiacServerException("Anamn is null");
				if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(1)));
if (acrs.getResultSet().getString(2)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(2)));
if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(3)));
if (acrs.getResultSet().getString(4)!=null) sb.append(String.format("%s.", acrs.getResultSet().getString(4)));

				acrs.close();
				acrs = sse.execPreparedQuery("select p_isl_ld.nisl, n_p0e1.pcod , n_p0e1.name , n_ldi.pcod , n_ldi.name_n , p_rez_l.zpok, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_l on (p_rez_l.nisl = p_isl_ld.nisl) join n_ldi  on (n_ldi.pcod = p_rez_l.cpok) join n_p0e1  on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? " +
					"union " +
					"select p_isl_ld.nisl, n_p0e1.pcod, n_p0e1.name , n_ldi.pcod, n_ldi.name_n, n_arez.name, p_isl_ld.datav " +
					"from p_isl_ld  join p_rez_d  on (p_rez_d.nisl = p_isl_ld.nisl) join n_ldi on (n_ldi.pcod = p_rez_d.kodisl) join n_arez  on (n_arez.pcod = p_rez_d.rez) join n_p0e1 on (n_p0e1.pcod = p_isl_ld.cisl) " +
					"where p_isl_ld.pvizit_id = ? ", pvizitId, pvizitId);
				if (!acrs.getResultSet().next()) 
					throw new KmiacServerException("Issl is null");
				sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
				while (acrs.getResultSet().next()){
					sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
	}			

acrs.close();
				acrs = sse.execPreparedQuery("select recomend from p_vizit where id=?", pvizitId); 
if (!acrs.getResultSet().next()) 
					throw new KmiacServerException("Recom is null");
				if (acrs.getResultSet().getString(1)!=null){ sb.append("<br> Лечебные и трудовые рекомендации");sb.append(String.format("%s", acrs.getResultSet().getString(1)));}
sb.append("<br>");

sb.append("Лечащий врач:");
acrs.close();
acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join n_n00 on (s_users.cpodr=n_n00.pcod) join n_m00 on (n_n00.clpu=n_m00.pcod) join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",userId);
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Vrach is null");//заменить текст 

				sb.append(String.format("%s  %s  %s ", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
			sb.append(String.format("<p align=\"right\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
			acrs.close();
			osw.write(sb.toString());
			return "c:\\vypis.htm";
		} catch (SQLException | IOException | KmiacServerException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public String printKek(int npasp, int pvizitId) throws KmiacServerException, TException {
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\kek.htm"), "utf-8")) {
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
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Patient is null");//заменить текст
				sb.append(String.format("<b>Ф.И.О.</b> %s  %s %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), acrs.getResultSet().getString(3)));
				sb.append(String.format("<br><b>Дата рождения: </b> %1$td.%1$tm.%1$tY<br>", acrs.getResultSet().getDate(4)));
				acrs.close();
				acrs=sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pvizitId);
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Diag is null");//заменить текст
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
			return "c:\\kek.htm";
		} catch (SQLException | IOException | KmiacServerException e) {
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
			sme.execPreparedT("UPDATE p_anam_zab SET t_nachalo_zab = ?, t_sympt = ?, t_otn_bol = ?, t_ps_syt = ?, t_ist_zab = ? WHERE id_pvizit = ? ", false, anam, anamZabTypes, 2, 3, 4, 5, 6, 0);
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
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("c:\\protokol.htm"), "utf-8")) {
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
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Datap is null");//заменить текст
				sb.append(String.format("<b>Дата перв.обращения</b> %1$td.%1$tm.%1$tY", acrs.getResultSet().getDate(1)));
				sb.append(String.format("<br><b>Цель обращения </b>%s", acrs.getResultSet().getString(3)));
				
				acrs.close();
				acrs = sse.execPreparedQuery("select t_nachalo_zab,t_sympt,t_otn_bol,t_ps_syt from p_anam_zab where id_pvizit=?", pk.getPvizit_id()); 
				if (!acrs.getResultSet().next()) {
					throw new KmiacServerException("Anamn is null");}
				else{sb.append("<br><b>	Анамнез заболевания</b><br>");
					if (acrs.getResultSet().getString(1)!=null) sb.append(String.format("<i>Начало заболевания </i> %s.", acrs.getResultSet().getString(1)));
					if (acrs.getResultSet().getString(2)!=null) sb.append(String.format("<i>Симптомы </i> %s.", acrs.getResultSet().getString(2)));
					if (acrs.getResultSet().getString(3)!=null) sb.append(String.format("<i>Отношение больного </i> %s.", acrs.getResultSet().getString(3)));
					if (acrs.getResultSet().getString(4)!=null) sb.append(String.format("<i>Психологическая ситуация в связи с болезнью </i> %s.", acrs.getResultSet().getString(4)));
				}				
					
					acrs.close();
					acrs = sse.execPreparedQuery("select * from p_vizit_amb join p_priem on (p_priem.id_pos=p_vizit_amb.id) where p_vizit_amb.id_obr=? order by id", pk.getPvizit_id());
					if (!acrs.getResultSet().next()) {
						throw new KmiacServerException("Priem is null");}
					else{sb.append("<br><b>Осмотр: </b><br>");
					sb.append(String.format("Дата посещения %1$td.%1$tm.%1$tY <br>", acrs.getResultSet().getDate(4)));
					
					if (acrs.getResultSet().getString(28)!=null) sb.append(String.format("<i>Жалобы: дыхательная система </i> %s <br>", acrs.getResultSet().getString(28)));
					if (acrs.getResultSet().getString(29)!=null) sb.append(String.format("<i>Жалобы: система кровообращения </i> %s <br>", acrs.getResultSet().getString(29)));
					if (acrs.getResultSet().getString(30)!=null) sb.append(String.format("<i>Жалобы: система пищеварения </i> %s <br>", acrs.getResultSet().getString(30)));
					if (acrs.getResultSet().getString(31)!=null) sb.append(String.format("<i>Жалобы: мочеполовая система </i> %s <br>", acrs.getResultSet().getString(31)));
					if (acrs.getResultSet().getString(32)!=null) sb.append(String.format("<i>Жалобы: эндокринная система </i> %s <br>", acrs.getResultSet().getString(32)));
					if (acrs.getResultSet().getString(33)!=null) sb.append(String.format("<i>Жалобы: нервная система и органы чувств </i> %s <br>", acrs.getResultSet().getString(33)));
					if (acrs.getResultSet().getString(34)!=null) sb.append(String.format("<i>Жалобы: опорно-двигательная система </i> %s <br>", acrs.getResultSet().getString(34)));
					if (acrs.getResultSet().getString(35)!=null) sb.append(String.format("<i>Жалобы: лихорадка </i> %s <br>", acrs.getResultSet().getString(35)));
					if (acrs.getResultSet().getString(36)!=null) sb.append(String.format("<i>Жалобы: общего характера </i> %s <br>", acrs.getResultSet().getString(36)));
					if (acrs.getResultSet().getString(37)!=null) sb.append(String.format("<i>Жалобы: прочие </i> %s <br>", acrs.getResultSet().getString(37)));
					if (acrs.getResultSet().getString(38)!=null) sb.append(String.format("<i>Общее состояние </i> %s <br>", acrs.getResultSet().getString(38)));
					if (acrs.getResultSet().getString(39)!=null) sb.append(String.format("<i>Кожные покровы </i> %s <br>", acrs.getResultSet().getString(39)));
					if (acrs.getResultSet().getString(40)!=null) sb.append(String.format("<i>Видимые слизистые </i> %s <br>", acrs.getResultSet().getString(40)));
					if (acrs.getResultSet().getString(41)!=null) sb.append(String.format("<i>Подкожная клетчатка </i> %s <br>", acrs.getResultSet().getString(41)));
					if (acrs.getResultSet().getString(42)!=null) sb.append(String.format("<i>Лимфатические узлы </i> %s <br>", acrs.getResultSet().getString(42)));
					if (acrs.getResultSet().getString(43)!=null) sb.append(String.format("<i>Костно-мышечная система </i> %s <br>", acrs.getResultSet().getString(43)));
					if (acrs.getResultSet().getString(44)!=null) sb.append(String.format("<i>Нервно-психический статус </i> %s <br>", acrs.getResultSet().getString(44)));
					if (acrs.getResultSet().getString(45)!=null) sb.append(String.format("<i>ЧСС </i> %s <br>", acrs.getResultSet().getString(45)));
					if (acrs.getResultSet().getString(46)!=null) sb.append(String.format("<i>Температура </i> %s <br>", acrs.getResultSet().getString(46)));
					if (acrs.getResultSet().getString(47)!=null) sb.append(String.format("<i>АД </i> %s <br>", acrs.getResultSet().getString(47)));
					if (acrs.getResultSet().getString(48)!=null) sb.append(String.format("<i>Вес </i> %s <br>", acrs.getResultSet().getString(49)));
					if (acrs.getResultSet().getString(50)!=null) sb.append(String.format("<i>Телосложение </i> %s <br>", acrs.getResultSet().getString(50)));
					if (acrs.getResultSet().getString(51)!=null) sb.append(String.format("<i>Суставы </i> %s <br>", acrs.getResultSet().getString(51)));
					if (acrs.getResultSet().getString(52)!=null) sb.append(String.format("<i>Дыхание </i> %s <br>", acrs.getResultSet().getString(52)));
					if (acrs.getResultSet().getString(53)!=null) sb.append(String.format("<i>Грудная клетка </i> %s <br>", acrs.getResultSet().getString(53)));
					if (acrs.getResultSet().getString(54)!=null) sb.append(String.format("<i>Перкуссия легких </i> %s <br>", acrs.getResultSet().getString(54)));
					if (acrs.getResultSet().getString(55)!=null) sb.append(String.format("<i>Аускультация легких </i> %s <br>", acrs.getResultSet().getString(55)));
					if (acrs.getResultSet().getString(56)!=null) sb.append(String.format("<i>Бронхофония </i> %s <br>", acrs.getResultSet().getString(56)));
					if (acrs.getResultSet().getString(57)!=null) sb.append(String.format("<i>Артерии и шейные вены </i> %s <br>", acrs.getResultSet().getString(57)));
					if (acrs.getResultSet().getString(58)!=null) sb.append(String.format("<i>Область сердца </i> %s <br>", acrs.getResultSet().getString(58)));
					if (acrs.getResultSet().getString(59)!=null) sb.append(String.format("<i>Перкуссия сердца </i> %s <br>", acrs.getResultSet().getString(59)));
					if (acrs.getResultSet().getString(60)!=null) sb.append(String.format("<i>Аускультация сердца </i> %s <br>", acrs.getResultSet().getString(60)));
					if (acrs.getResultSet().getString(61)!=null) sb.append(String.format("<i>Полость рта </i> %s <br>", acrs.getResultSet().getString(61)));
					if (acrs.getResultSet().getString(62)!=null) sb.append(String.format("<i>Живот </i> %s <br>", acrs.getResultSet().getString(62)));
					if (acrs.getResultSet().getString(63)!=null) sb.append(String.format("<i>Пальпация живота </i> %s <br>", acrs.getResultSet().getString(63)));
					if (acrs.getResultSet().getString(64)!=null) sb.append(String.format("<i>Пальпация, перкуссия и аускультация ЖКТ </i> %s <br>", acrs.getResultSet().getString(64)));
					if (acrs.getResultSet().getString(65)!=null) sb.append(String.format("<i>Пальпация желудка </i> %s <br>", acrs.getResultSet().getString(65)));
					if (acrs.getResultSet().getString(66)!=null) sb.append(String.format("<i>Пальпация поджелудочной железы </i> %s <br>", acrs.getResultSet().getString(66)));
					if (acrs.getResultSet().getString(67)!=null) sb.append(String.format("<i>Печень </i> %s <br>", acrs.getResultSet().getString(67)));
					if (acrs.getResultSet().getString(68)!=null) sb.append(String.format("<i>Желчный пузырь </i> %s <br>", acrs.getResultSet().getString(68)));
					if (acrs.getResultSet().getString(69)!=null) sb.append(String.format("<i>Селезенка </i> %s <br>", acrs.getResultSet().getString(69)));
					if (acrs.getResultSet().getString(70)!=null) sb.append(String.format("<i>Область заднего прохода </i> %s <br>", acrs.getResultSet().getString(70)));
					if (acrs.getResultSet().getString(71)!=null) sb.append(String.format("<i>Поясничная область </i> %s <br>", acrs.getResultSet().getString(71)));
					if (acrs.getResultSet().getString(72)!=null) sb.append(String.format("<i>Почки </i> %s <br>", acrs.getResultSet().getString(72)));
					if (acrs.getResultSet().getString(73)!=null) sb.append(String.format("<i>Мочевой пузырь </i> %s <br>", acrs.getResultSet().getString(73)));
					if (acrs.getResultSet().getString(74)!=null) sb.append(String.format("<i>Молочные железы </i> %s <br>", acrs.getResultSet().getString(74)));
					if (acrs.getResultSet().getString(75)!=null) sb.append(String.format("<i>Грудные железы мужчин </i> %s <br>", acrs.getResultSet().getString(75)));
					if (acrs.getResultSet().getString(76)!=null) sb.append(String.format("<i>Матка и ее придатки </i> %s <br>", acrs.getResultSet().getString(76)));
					if (acrs.getResultSet().getString(77)!=null) sb.append(String.format("<i>Наружные половые органы </i> %s <br>", acrs.getResultSet().getString(77)));
					if (acrs.getResultSet().getString(78)!=null) sb.append(String.format("<i>Щитовидная железа </i> %s <br>", acrs.getResultSet().getString(78)));
					if (acrs.getResultSet().getString(79)!=null) sb.append(String.format("<i>Status Localis </i> %s <br>", acrs.getResultSet().getString(79)));
					if (acrs.getResultSet().getString(80)!=null) sb.append(String.format("<i>Оценка данных анамнеза и объективного исследования </i> %s <br>", acrs.getResultSet().getString(80)));}
								while (acrs.getResultSet().next()){
									sb.append(String.format("Дата посещения %1$td.%1$tm.%1$tY <br>", acrs.getResultSet().getDate(4)));
									if (acrs.getResultSet().getString(28)!=null) sb.append(String.format("<i>Жалобы: дыхательная система </i> %s <br>", acrs.getResultSet().getString(28)));
									if (acrs.getResultSet().getString(29)!=null) sb.append(String.format("<i>Жалобы: система кровообращения </i> %s <br>", acrs.getResultSet().getString(29)));
									if (acrs.getResultSet().getString(30)!=null) sb.append(String.format("<i>Жалобы: система пищеварения </i> %s <br>", acrs.getResultSet().getString(30)));
									if (acrs.getResultSet().getString(31)!=null) sb.append(String.format("<i>Жалобы: мочеполовая система </i> %s <br>", acrs.getResultSet().getString(31)));
									if (acrs.getResultSet().getString(32)!=null) sb.append(String.format("<i>Жалобы: эндокринная система </i> %s <br>", acrs.getResultSet().getString(32)));
									if (acrs.getResultSet().getString(33)!=null) sb.append(String.format("<i>Жалобы: нервная система и органы чувств </i> %s <br>", acrs.getResultSet().getString(33)));
									if (acrs.getResultSet().getString(34)!=null) sb.append(String.format("<i>Жалобы: опорно-двигательная система </i> %s <br>", acrs.getResultSet().getString(34)));
									if (acrs.getResultSet().getString(35)!=null) sb.append(String.format("<i>Жалобы: лихорадка </i> %s <br>", acrs.getResultSet().getString(35)));
									if (acrs.getResultSet().getString(36)!=null) sb.append(String.format("<i>Жалобы: общего характера </i> %s <br>", acrs.getResultSet().getString(36)));
									if (acrs.getResultSet().getString(37)!=null) sb.append(String.format("<i>Жалобы: прочие </i> %s <br>", acrs.getResultSet().getString(37)));
									if (acrs.getResultSet().getString(38)!=null) sb.append(String.format("<i>Общее состояние </i> %s <br>", acrs.getResultSet().getString(38)));
									if (acrs.getResultSet().getString(39)!=null) sb.append(String.format("<i>Кожные покровы </i> %s <br>", acrs.getResultSet().getString(39)));
									if (acrs.getResultSet().getString(40)!=null) sb.append(String.format("<i>Видимые слизистые </i> %s <br>", acrs.getResultSet().getString(40)));
									if (acrs.getResultSet().getString(41)!=null) sb.append(String.format("<i>Подкожная клетчатка </i> %s <br>", acrs.getResultSet().getString(41)));
									if (acrs.getResultSet().getString(42)!=null) sb.append(String.format("<i>Лимфатические узлы </i> %s <br>", acrs.getResultSet().getString(42)));
									if (acrs.getResultSet().getString(43)!=null) sb.append(String.format("<i>Костно-мышечная система </i> %s <br>", acrs.getResultSet().getString(43)));
									if (acrs.getResultSet().getString(44)!=null) sb.append(String.format("<i>Нервно-психический статус </i> %s <br>", acrs.getResultSet().getString(44)));
									if (acrs.getResultSet().getString(45)!=null) sb.append(String.format("<i>ЧСС </i> %s <br>", acrs.getResultSet().getString(45)));
									if (acrs.getResultSet().getString(46)!=null) sb.append(String.format("<i>Температура </i> %s <br>", acrs.getResultSet().getString(46)));
									if (acrs.getResultSet().getString(47)!=null) sb.append(String.format("<i>АД </i> %s <br>", acrs.getResultSet().getString(47)));
									if (acrs.getResultSet().getString(48)!=null) sb.append(String.format("<i>Вес </i> %s <br>", acrs.getResultSet().getString(49)));
									if (acrs.getResultSet().getString(50)!=null) sb.append(String.format("<i>Телосложение </i> %s <br>", acrs.getResultSet().getString(50)));
									if (acrs.getResultSet().getString(51)!=null) sb.append(String.format("<i>Суставы </i> %s <br>", acrs.getResultSet().getString(51)));
									if (acrs.getResultSet().getString(52)!=null) sb.append(String.format("<i>Дыхание </i> %s <br>", acrs.getResultSet().getString(52)));
									if (acrs.getResultSet().getString(53)!=null) sb.append(String.format("<i>Грудная клетка </i> %s <br>", acrs.getResultSet().getString(53)));
									if (acrs.getResultSet().getString(54)!=null) sb.append(String.format("<i>Перкуссия легких </i> %s <br>", acrs.getResultSet().getString(54)));
									if (acrs.getResultSet().getString(55)!=null) sb.append(String.format("<i>Аускультация легких </i> %s <br>", acrs.getResultSet().getString(55)));
									if (acrs.getResultSet().getString(56)!=null) sb.append(String.format("<i>Бронхофония </i> %s <br>", acrs.getResultSet().getString(56)));
									if (acrs.getResultSet().getString(57)!=null) sb.append(String.format("<i>Артерии и шейные вены </i> %s <br>", acrs.getResultSet().getString(57)));
									if (acrs.getResultSet().getString(58)!=null) sb.append(String.format("<i>Область сердца </i> %s <br>", acrs.getResultSet().getString(58)));
									if (acrs.getResultSet().getString(59)!=null) sb.append(String.format("<i>Перкуссия сердца </i> %s <br>", acrs.getResultSet().getString(59)));
									if (acrs.getResultSet().getString(60)!=null) sb.append(String.format("<i>Аускультация сердца </i> %s <br>", acrs.getResultSet().getString(60)));
									if (acrs.getResultSet().getString(61)!=null) sb.append(String.format("<i>Полость рта </i> %s <br>", acrs.getResultSet().getString(61)));
									if (acrs.getResultSet().getString(62)!=null) sb.append(String.format("<i>Живот </i> %s <br>", acrs.getResultSet().getString(62)));
									if (acrs.getResultSet().getString(63)!=null) sb.append(String.format("<i>Пальпация живота </i> %s <br>", acrs.getResultSet().getString(63)));
									if (acrs.getResultSet().getString(64)!=null) sb.append(String.format("<i>Пальпация, перкуссия и аускультация ЖКТ </i> %s <br>", acrs.getResultSet().getString(64)));
									if (acrs.getResultSet().getString(65)!=null) sb.append(String.format("<i>Пальпация желудка </i> %s <br>", acrs.getResultSet().getString(65)));
									if (acrs.getResultSet().getString(66)!=null) sb.append(String.format("<i>Пальпация поджелудочной железы </i> %s <br>", acrs.getResultSet().getString(66)));
									if (acrs.getResultSet().getString(67)!=null) sb.append(String.format("<i>Печень </i> %s <br>", acrs.getResultSet().getString(67)));
									if (acrs.getResultSet().getString(68)!=null) sb.append(String.format("<i>Желчный пузырь </i> %s <br>", acrs.getResultSet().getString(68)));
									if (acrs.getResultSet().getString(69)!=null) sb.append(String.format("<i>Селезенка </i> %s <br>", acrs.getResultSet().getString(69)));
									if (acrs.getResultSet().getString(70)!=null) sb.append(String.format("<i>Область заднего прохода </i> %s <br>", acrs.getResultSet().getString(70)));
									if (acrs.getResultSet().getString(71)!=null) sb.append(String.format("<i>Поясничная область </i> %s <br>", acrs.getResultSet().getString(71)));
									if (acrs.getResultSet().getString(72)!=null) sb.append(String.format("<i>Почки </i> %s <br>", acrs.getResultSet().getString(72)));
									if (acrs.getResultSet().getString(73)!=null) sb.append(String.format("<i>Мочевой пузырь </i> %s <br>", acrs.getResultSet().getString(73)));
									if (acrs.getResultSet().getString(74)!=null) sb.append(String.format("<i>Молочные железы </i> %s <br>", acrs.getResultSet().getString(74)));
									if (acrs.getResultSet().getString(75)!=null) sb.append(String.format("<i>Грудные железы мужчин </i> %s <br>", acrs.getResultSet().getString(75)));
									if (acrs.getResultSet().getString(76)!=null) sb.append(String.format("<i>Матка и ее придатки </i> %s <br>", acrs.getResultSet().getString(76)));
									if (acrs.getResultSet().getString(77)!=null) sb.append(String.format("<i>Наружные половые органы </i> %s <br>", acrs.getResultSet().getString(77)));
									if (acrs.getResultSet().getString(78)!=null) sb.append(String.format("<i>Щитовидная железа </i> %s <br>", acrs.getResultSet().getString(78)));
									if (acrs.getResultSet().getString(79)!=null) sb.append(String.format("<i>Status Localis </i> %s <br>", acrs.getResultSet().getString(79)));
									if (acrs.getResultSet().getString(80)!=null) sb.append(String.format("<i>Оценка данных анамнеза и объективного исследования </i> %s <br>", acrs.getResultSet().getString(80)));
						}		
					
					
				sb.append("<br><b>Поставленные диагнозы: </b><br>");
				
				acrs.close();
				acrs=sse.execPreparedQuery("select p_diag_amb. diag, n_c00.name  from p_diag_amb join n_c00 on (p_diag_amb.diag=n_c00.pcod) where p_diag_amb.diag_stat=1 and p_diag_amb.predv=false and id_obr=?", pk.getPvizit_id());
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Diag is null");//заменить текст
				sb.append("основное заболевание ");
				acrs.close();
				acrs = sse.execPreparedQuery("select diag,named from p_diag_amb where id_obr=? and diag_stat=1 and predv=false order by datap", pk.getPvizit_id());
				while (acrs.getResultSet().next()){
					if (acrs.getResultSet().getString(1)!=null)sb.append(String.format(" %s %s <br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2)));
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
				if (!acrs.getResultSet().next()) 
					throw new KmiacServerException("Issl is null");
				sb.append("<br><br><b>Назначенные исследования </b><br>");sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
				while (acrs.getResultSet().next()){
					sb.append(String.format("<br>Код показателя  %s <br>  Наименование показателя %s <br> Результат %s <br>", acrs.getResultSet().getString(4), acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
	}			

acrs.close();
				acrs = sse.execPreparedQuery("select recomend from p_vizit where id=?", pk.getPvizit_id()); 
if (!acrs.getResultSet().next()) 
					throw new KmiacServerException("Recom is null");
				if (acrs.getResultSet().getString(1)!=null) {sb.append("<br><b> Лечебные и трудовые рекомендации</b>");sb.append(String.format("%s", acrs.getResultSet().getString(1)));}
sb.append("<br>");

			sb.append(String.format("<p align=\"left\"></p> %1$td.%1$tm.%1$tY<br />", new Date(System.currentTimeMillis())));
sb.append("Лечащий врач:");
acrs.close();
acrs = sse.execPreparedQuery("select s_vrach.fam,s_vrach.im,s_vrach.ot from s_users join n_n00 on (s_users.cpodr=n_n00.pcod) join n_m00 on (n_n00.clpu=n_m00.pcod) join s_vrach on(s_vrach.pcod=s_users.pcod) where s_users.id=?",pk.getUserId());
				if (!acrs.getResultSet().next()) throw new KmiacServerException("Vrach is null");//заменить текст 
sb.append(String.format("%s %s %s",acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
sb.append("<br>Подпись ____________");
		acrs.close();
			osw.write(sb.toString());
			return "c:\\protokol.htm";
		} catch (SQLException | IOException | KmiacServerException e) {
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
}

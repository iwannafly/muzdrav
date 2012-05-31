package ru.nkz.ivcgzo.serverOsm;

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
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.P_isl_ld;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pokaz;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;
import ru.nkz.ivcgzo.thriftOsm.Prez_d;
import ru.nkz.ivcgzo.thriftOsm.Prez_l;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
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

	public ServerOsm(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmZapVr = new TResultSetMapper<>(ZapVr.class, "npasp",       "vidp",        "timepn",   "fam",        "im",         "ot",         "poms_ser",   "poms_nom");
		zapVrTypes = new Class<?>[] {                  Integer.class, Integer.class, Time.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPvizit = new TResultSetMapper<>(Pvizit.class, "id",          "npasp",       "cpol",        "cobr",        "datao",    "ishod",       "rezult",      "talon",       "cod_sp",      "cdol",       "cuser",       "ztext",      "dataz",    "id_etal");
		pvizitTypes = new Class<?>[] {                   Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Date.class, Integer.class};
		
		rsmPvizitAmb = new TResultSetMapper<>(PvizitAmb.class, "id",          "id_obr",      "npasp",       "datap",    "cod_sp",      "cdol",       "diag",       "mobs",        "rezult",      "opl",         "stoim",      "uet",         "datak",    "kod_rez",     "k_lr",        "n_sp",        "pr_opl",      "pl_extr",     "vpom",        "fio_vr");
		pvizitAmbTypes = new Class<?>[] {                      Integer.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Date.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class};
		
		rsmPdiagAmb = new TResultSetMapper<>(PdiagAmb.class, "id",          "id_obr",      "npasp",       "diag",       "named",      "diag_stat",   "predv",       "datad",    "obstreg",     "cod_sp",      "cdol",       "datap",    "dataot",   "obstot",      "cod_spot",    "cdol_ot",    "vid_tr");
		pdiagAmbTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, String.class, Integer.class, Boolean.class, Date.class, Integer.class, Integer.class, String.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class};
		
		rsmPsign = new TResultSetMapper<>(Psign.class, "npasp",       "grup",       "ph",         "allerg",     "farmkol",    "vitae",      "vred");
		psignTypes = new Class<?>[] {                  Integer.class, String.class, String.class, String.class, String.class, String.class, String.class};
		
		rsmPriem = new TResultSetMapper<>(Priem.class, "id_obr",      "npasp",       "id_pos",      "sl_ob",       "n_is",        "n_kons",      "n_proc",      "n_lek",       "t_jalob_d",  "t_jalob_krov", "t_jalob_p",  "t_jalob_moch", "t_jalob_endo", "t_jalob_nerv", "t_jalob_opor", "t_jalob_lih", "t_jalob_obh", "t_jalob_proch", "t_nachalo_zab", "t_sympt",    "t_otn_bol",  "t_ps_syt",   "t_ob_sost",  "t_koj_pokr", "t_sliz",     "t_podk_kl",  "t_limf_uzl", "t_kost_mysh", "t_nervn_ps", "t_chss",     "t_temp",     "t_ad",       "t_rost",     "t_ves",      "t_telo",     "t_sust",     "t_dyh",      "t_gr_kl",    "t_perk_l",   "t_aus_l",    "t_bronho",   "t_arter",    "t_obl_s",    "t_perk_s",   "t_aus_s",    "t_pol_rta",  "t_jivot",    "t_palp_jivot", "t_jel_kish", "t_palp_jel", "t_palp_podjel", "t_pechen",   "t_jelch",    "t_selez",    "t_obl_zad",  "t_poyasn",   "t_pochk",    "t_moch",     "t_mol_jel", "t_gr_jel",    "t_matka",    "t_nar_polov", "t_chitov",   "t_st_localis", "t_ocenka",   "t_jalob");
		priemTypes = new Class<?>[] {                  Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class,   String.class, String.class,   String.class,   String.class,   String.class,   String.class,  String.class,  String.class,    String.class,    String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,  String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,   String.class, String.class, String.class,    String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,  String.class, String.class,   String.class, String.class};
		
		rsmPatComInfo = new TResultSetMapper<>(PatientCommonInfo.class, "npasp",       "fam",        "im",         "ot",         "datar",    "poms_ser",   "poms_nom",   "pol",         "jitel",       "sgrp",        "adp_obl",    "adp_gorod",  "adp_ul",     "adp_dom",    "adp_korp",   "adp_kv",     "adm_obl",    "adm_gorod",  "adm_ul",     "adm_dom",    "adm_korp",   "adm_kv",     "mrab",       "name_mr",    "ncex",        "poms_strg",   "poms_tdoc",   "poms_ndog",  "pdms_strg",   "pdms_ser",   "pdms_nom",   "pdms_ndog",  "cpol_pr",     "terp",        "datapr",   "tdoc",        "docser",     "docnum",     "datadoc",  "odoc",       "snils",      "dataz",    "prof",       "tel",        "dsv",      "prizn",       "ter_liv",     "region_liv");
		patComInfoTypes = new Class<?>[] {                              Integer.class, String.class, String.class, String.class, Date.class, String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, String.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, String.class, Date.class, String.class, String.class, Date.class, String.class, String.class, Date.class, Integer.class, Integer.class, Integer.class};
		
		rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, "pcod",        "name");
		intClasTypes = new Class<?>[] {                              Integer.class, String.class};
		
		rsmStrClas = new TResultSetMapper<>(StringClassifier.class, "pcod",        "name");
		strClasTypes = new Class<?>[] {                              String.class, String.class};
		
		rsmPislld = new TResultSetMapper<>(P_isl_ld.class, "nisl",        "npasp",       "cisl",        "pcisl",      "napravl",     "naprotd",     "datan",    "vrach",       "diag",       "dataz");
		pislldTypes = new Class<?>[] {                     Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, Date.class, Integer.class, String.class, Date.class};
		
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
	}

	@Override
	public String getServerVersion() throws TException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getClientVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
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
		
//		Pvizit obr = new Pvizit(0, 1, 2, 3, 4, 5, 6, 7, 8, "9", 10, "11", 12, 13);
//		obr.setId(AddPvizit(obr));
//		Pvizit obr1 = getPvizit(obr.id);
//		obr = new Pvizit(obr1.id, 2, 3, 4, 5, 6, 7, 8, 9, "10", 11, "12", 13, 14);
//		UpdatePvizit(obr);
//		DeletePvizit(obr.id);
		
//		Psign psign = new Psign(1, "2", "3", "4", "5", "6", "7");
//		setPsign(psign);
//		psign = getPsign(psign.npasp);
		
//		PvizitAmb pos = new PvizitAmb(2, obr.id, 2, 3, 4, "5", "6", 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pat.npasp, tal.vidp, tal.timepn, pat.fam, pat.im, pat.ot, pat.poms_ser, pat.poms_nom FROM e_talon tal JOIN patient pat ON (pat.npasp = tal.npasp) WHERE (tal.pcod_sp = ?) AND (tal.cdol = ?) AND (tal.datap = ?)", idvr, cdol, new Date(datap))) {
			return rsmZapVr.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public int AddPvizit(Pvizit obr) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("INSERT INTO p_vizit (npasp, cpol, cobr, datao, ishod, rezult, talon, cod_sp, cdol, cuser, ztext, dataz, id_etal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, obr, pvizitTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
			int id = sme.getGeneratedKeys().getInt("id");
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
			sme.execPreparedT("UPDATE p_vizit SET ishod = ?, rezult = ?, talon = ?, ztext = ?, dataz = ? WHERE id = ?", false, obr, pvizitTypes, 5, 6, 7, 11, 12, 0);
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
			sme.execPreparedT("INSERT INTO p_vizit_amb (id_obr, npasp, datap, cod_sp, cdol, diag, mobs, rezult, opl, uet, k_lr, n_sp, pr_opl, pl_extr, vpom) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, pos, pvizitAmbTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 14, 15, 16, 17, 18);
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
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT pva.*, get_short_fio(svr.fam, svr.im, svr.ot) AS fio_vr FROM p_vizit_amb pva JOIN s_vrach svr ON (svr.pcod = pva.cod_sp) WHERE id_obr = ? ", obrId)) {
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
				sme.execPreparedT("UPDATE p_sign SET grup = ?, ph = ?, allerg = ?, farmkol = ?, vitae = ?, vred = ? WHERE npasp = ? ", false, sign, psignTypes, 1, 2, 3, 4, 5, 6, 0);
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
	public Priem getPriem(int obrId, int npasp, int posId) throws KmiacServerException, PriemNotFoundException, TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("SELECT * FROM p_priem WHERE id_obr = ? AND npasp = ? AND id_pos = ? ", obrId, npasp, posId)) {
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
			sme.execPreparedT("UPDATE p_priem SET sl_ob = ?, n_is = ?, n_kons = ?, n_proc = ?, n_lek = ?, t_jalob_d = ?, t_jalob_krov = ?, t_jalob_p = ?, t_jalob_moch = ?, t_jalob_endo = ?, t_jalob_nerv = ?, t_jalob_opor = ?, t_jalob_lih = ?, t_jalob_obh = ?, t_jalob_proch = ?, t_nachalo_zab = ?, t_sympt = ?, t_otn_bol = ?, t_ps_syt = ?, t_ob_sost = ?, t_koj_pokr = ?, t_sliz = ?, t_podk_kl = ?, t_limf_uzl = ?, t_kost_mysh = ?, t_nervn_ps = ?, t_chss = ?, t_temp = ?, t_ad = ?, t_rost = ?, t_ves = ?, t_telo = ?, t_sust = ?, t_dyh = ?, t_gr_kl = ?, t_perk_l = ?, t_aus_l = ?, t_bronho = ?, t_arter = ?, t_obl_s = ?, t_perk_s = ?, t_aus_s = ?, t_pol_rta = ?, t_jivot = ?, t_palp_jivot = ?, t_jel_kish = ?, t_palp_jel = ?, t_palp_podjel = ?, t_pechen = ?, t_jelch = ?, t_selez = ?, t_obl_zad = ?, t_poyasn = ?, t_pochk = ?, t_moch = ?, t_mol_jel = ?, t_gr_jel = ?, t_matka = ?, t_nar_polov = ?, t_chitov = ?, t_st_localis = ?, t_ocenka = ? WHERE id_obr = ?, npasp = ?, id_pos = ? ", false, pr, priemTypes, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 0, 1, 2);
			sme.setCommit();
		} catch (InterruptedException | SQLException e) {
			throw new KmiacServerException();
		}
	}
	
	@Override
	public void AddPdiagZ(PdiagZ dz) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		
	}

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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_z30 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_am0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_am0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_az9() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_az9 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_z43() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_z43 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_kas() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_kas ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_n00() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_n00 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_l01() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_l01 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_az0() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_az0 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<IntegerClassifier> get_n_l02() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_l02 ")) {
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
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT pcod, name FROM n_p0e1 ")) {
			return rsmIntClas.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}

	@Override
	public List<StringClassifier> get_n_nz1() throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execQuery("SELECT pcod, name FROM n_nz1 ")) {
			return rsmStrClas.mapToList(acrs.getResultSet());
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
		String sql = "SELECT np.pcod AS c_p0e1, no.obst, no.nameobst AS name_obst, ns.pcod " +
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
			sme.execPreparedT("INSERT INTO p_isl_ld (npasp, cisl, pcisl, napravl, naprotd, datan, vrach, diag, dataz) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ", true, npisl, pislldTypes, 1, 2, 3, 4, 5, 6, 7, 8, 9);
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
}

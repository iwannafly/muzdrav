namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
 * Запись на пациента в регистратуре
 */
struct ZapVr{
	1: i32 npasp;
	2: i32 vid_p; /*вид приема-первичность*/
	3: i64 timepn; /*время начала приема*/
	4: string fam;
	5: string im;
	6: string oth;
	7: string serpolis;
	8: string nompolis;
}

struct Pvizit {
	1: i32 id;
	2: i32 npasp;
	3: i32 cpol;
	4: i32 cobr;
	5: i64 datao;
	6: i32 ishod;
	7: i32 rezult;
	8: i32 talon;
	9: i32 cod_sp;
	10: string cdol;
	11: i32 cuser;
	12: string zakl;
	13: i64 dataz;
	14: i32 idzab;
	15: string recomend;
}

struct PvizitAmb {
	1: i32 id;
	2: i32 id_obr;
	3: i32 npasp;
	4: i64 datap;
	5: i32 cod_sp;
	6: string cdol;
	7: string diag; 
	8: i32 mobs;
	9: i32 rezult;
	10: i32 opl;
	11: double stoim;
	12: i32 uet;
	13: i64 datak;
	14: i32 kod_rez;
	15: i32 k_lr;
	16: i32 n_sp;
	17: i32 pr_opl;
	18: i32 pl_extr;
	19: i32 vpom;
	20: string fio_vr;
}

struct PdiagAmb {
	 1: i32 id;
	 2: i32 id_obr;
	 3: i32 npasp;
	 4: string diag;
	 5: string named;/*медицинское описание д-за*/
	 6: i32 diag_stat;
	 7: bool predv;
	 8: i64 datad;
	 9: i32 obstreg;
	10: i32 cod_sp;
	11: string cdol;
	12: i64 datap;
	13: i64 dataot;
	14: i32 obstot;
	15: i32 codsp_ot;
	16: string cdol_ot;
	17: i32 vid_tr;
}

struct Psign{
	1: i32 npasp;
	2: string grup;
	3: string ph;
	4: string allerg;
	5: string farmkol;
	6: string vitae;
	7: string vred;
	8: string razv;
	9: string uslov;
	10: string per_zab;
	11: string per_oper;
	12: string gemotr;
	13: string nasl;
	14: string ginek;
	15: string priem_lek;
	16: string prim_gorm;
}

struct Priem{
	1: i32 id;
	2: i32 npasp;
	3: i32 idpos;
	4: i32 sl_ob;
	5: i32 n_is;
	6: i32 n_kons;
	7: i32 n_proc;
	8: i32 n_lek;
	9: string t_jalob_d;
	10: string t_jalob_krov;
	11: string t_jalob_p;
	12: string t_jalob_moch;
	13: string t_jalob_endo;
	14: string t_jalob_nerv;
	15: string t_jalob_opor;
	16: string t_jalob_lih;
	17: string t_jalob_obh;
	18: string t_jalob_proch;
	19: string t_nachalo_zab;
	20: string t_sympt;
	21: string t_otn_bol;
	22: string t_ps_syt;
	23: string t_ob_sost;
	24: string t_koj_pokr;
	25: string t_sliz;
	26: string t_podk_kl;
	27: string t_limf_uzl;
	28: string t_kost_mysh;
	29: string t_nervn_ps;
	30: string t_chss;
	31: string t_temp;
	32: string t_ad;
	33: string t_rost;
	34: string t_ves;
	35: string t_telo;
	36: string t_sust;
	37: string t_dyh;
	38: string t_gr_kl;
	39: string t_perk_l;
	40: string t_aus_l;
	41: string t_bronho;
	42: string t_arter;
	43: string t_obl_s;
	44: string t_perk_s;
	45: string t_aus_s;
	46: string t_pol_rta;
	47: string t_jivot;
	48: string t_palp_jivot;
	49: string t_jel_kish;
	50: string t_palp_jel;
	51: string t_palp_podjel;
	52: string t_pechen;
	53: string t_jelch;
	54: string t_selez;
	55: string t_obl_zad;
	56: string t_poyasn;
	57: string t_pochk;
	58: string t_moch;
	59: string t_mol_jel;
	60: string t_gr_jel;
	61: string t_matka;
	62: string t_nar_polov;
	63: string t_chitov;
	64: string t_st_localis;
	65: string t_ocenka;
	66: string t_jalob;
	67: string t_ist_zab;
	68: string t_status_praesense;
	69: string t_fiz_obsl;
}

struct PdiagZ{
	1: i32 id;
	2: i32 npasp;
	3: string diag;
	4: string named;
	5: i64 datad;
	6: i32 cpodr;
	7: i64 d_post;
	8: i32 d_grup;
	9: i32 d_ish;
	10: i64 dataish;
	11: i64 datag;
	12: string diag_s;
	13: i32 d_grup_s;
	14: i32 cod_sp;
	15: string cdol_ot;
}

struct PatientCommonInfo {
	 1: optional i32 npasp;
	 2: optional string fam;
	 3: optional string im;
	 4: optional string ot;
	 5: optional i64 datar;
	 6: string poms_ser;
	 7: string poms_nom;
	 8: optional i32 pol;
	 9: optional i32 jitel;
	10: optional i32 sgrp;
	11: string adp_obl;
	12: string adp_gorod;
	13: string adp_ul;
	14: string adp_dom;
	15: string adp_korp;
	16: string adp_kv;
	17: string adm_obl;
	18: string adm_gorod;
	19: string adm_ul;
	20: string adm_dom;
	21: string adm_korp;
	22: string adm_kv;
	23: string mrab;
	24: string name_mr;
	25: optional i32 ncex;
	26: optional i32 poms_strg;
	27: optional i32 poms_tdoc;
	28: string poms_ndog;
	29: optional i32 pdms_strg;
	30: string pdms_ser;
	31: string pdms_nom;
	32: string pdms_ndog;
	33: optional i32 cpol_pr;
	34: optional i32 terp;
	35: optional i64 datapr;
	36: optional i32 tdoc;
	37: string docser;
	38: string docnum;
	39: optional i64 datadoc;
	40: string odoc;
	41: string snils;
	42: optional i64 dataz;
	43: string prof;
	44: string tel;
	45: optional i64 dsv;
	46: optional i32 prizn;
	47: optional i32 ter_liv;
	48: optional i32 region_liv;
}

struct RdSlStruct{
        1:i32 idDispb;
        2:i32 npasp;
        3:i64 datay;
        4:i32 kolpr;
        5:i32 abort;
        6:string oslAb;
        7:i64 dataosl;
        8:i64 dataM;
        9:i32 yavka1;
	10:string plrod;
	11:string prRod;
	12:i64 DataZs;
	13:i32 kolRod;
	14:i32 let;
	15:i32 prmen;
	16:i32 deti;
	17:i32 polj;
	18:i32 kont;
	19:i32 oslrod
	20:i32 rost;
	21:i32 vesd;
	22:i32 dsp;
	23:i32 dsr;
	24:i32 dTroch;
	25:i32 cext;
	26:i32 indSol;
	27:i64 Datasn; 
	28:i32 VozMen;
}

struct RdDinStruct{
	1:i32 idDispb;
	2:i32 id;
	3:i64 datapos;
	4:i32 srok;
	5:i32 ves;
	6:i32 oj;
	7:i32 hdm;
	8:string dspos;
	9:i32 art1;
	10:i32 art2;
	11:i32 art3;
	12:i32 art4;
	13:i64 datasl;
	14:i32 spl;
	15:i32 oteki;
	16:i32 chcc;
	17:i32 polpl;
	18:i32 predpl;
	19:i32 serd;
	20:i32 serd1;
}

/*. Rd_Inf*/
struct RdInfStruct{
	1:i32 idDispb;
	2:i32 npasp;
	3:i32 obr;
	4:i32 sem;
	5:i32 oSocO;
	6:i32 UslPr;
	7:i32 vOtec;
	8:i64 grOtec;
	9:string phOtec;
	10:string fioOtec;
	11:string mrOtec;
	12:string telOtec;
	13:i32 vredOtec;
}

/*Список показателей исследований по выбранному методу*/
struct PokazMet{
	1: string pcod;
	2: string name_n;
	3: double stoim;
	4: string c_obst;
	5: bool vybor;
}

/*метод по виду исследования*/
struct Metod{
	1: string obst;
	2: string name_obst;
	3: i32 c_p0e1;
	//4: string pcod;
}

/*Список показателей исследований по выбранной системе*/
struct Pokaz{
	1: string pcod;
	2: string name_n;
	3: double stoim;
	4: i32 c_p0e1;
	5: string c_n_nz1;
	6: bool vybor;
}


struct P_isl_ld {
	 1: i32 nisl;
	 2: i32 npasp;
	 3: i32 cisl;
	 4: string pcisl;
	 5: i32 napravl;
	 6: i32 naprotd;
	 7: i64 datan;
	 8: i32 vrach;
	 9: string diag;
	10: i64 dataz;
	11: i32 pvizit_id;
}

struct Prez_d {
	1: i32 npasp;
	2: i32 nisl;
	3: string kodisl;
	4: double stoim;
}

struct Prez_l {
	1: i32 npasp;
	2: i32 nisl;
	3: string cpok;
	4: double stoim;
}


struct IsslMet {
	1: i32 kodVidIssl;
	2: i32 userId;
	3: i32 npasp;
	4: string kodMetod;
	5: list<string> pokaz;
	6: string mesto;
	7: string kab;
}

struct IsslPokaz {
	1: i32 kodVidIssl;
	2: i32 userId;
	3: i32 npasp;
	4: string kodMetod;
	5: list<string> pokaz;
	6: string mesto;
	7: string kab;
}

struct Napr{
	1: i32 npasp;
	2: i32 userId;
	3: string obosnov;
	4: i32 clpu;
}

struct NaprKons{
	1: i32 npasp;
	2: i32 userId;
	3: string obosnov;
	4: i32 cpol;
}



exception PvizitNotFoundException {
}

exception PsignNotFoundException {
}

exception PriemNotFoundException {
}

exception PatientNotFoundException {
}


/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	
	
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap) throws (1: kmiacServer.KmiacServerException kse);
	
	i32 AddPvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	Pvizit getPvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse, 2: PvizitNotFoundException pne);
	void UpdatePvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	list<PvizitAmb> getPvizitAmb(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizitAmb(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	list<PdiagAmb> getPdiagAmb(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePdiagAmb(1: i32 diagId) throws (1: kmiacServer.KmiacServerException kse);

	Psign getPsign(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PsignNotFoundException sne);
	void setPsign(1: Psign sign) throws (1: kmiacServer.KmiacServerException kse);

	Priem getPriem(1: i32 obrId, 2: i32 npasp, 3: i32 posId) throws (1: kmiacServer.KmiacServerException kse, 2: PriemNotFoundException pne);
	void setPriem(1: Priem pr) throws (1: kmiacServer.KmiacServerException kse);

	void AddPdiagZ(1: PdiagZ dz) throws (1: kmiacServer.KmiacServerException kse);
	list<PdiagZ> getPdiagZ(1: i32 id_diag) throws (1: kmiacServer.KmiacServerException kse);

	/*Исследования*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string metod) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPisl(1: P_isl_ld npisl) throws (1: kmiacServer.KmiacServerException kse);
	void AddPrezd(1: Prez_d di) throws (1: kmiacServer.KmiacServerException kse);
	void AddPrezl(1: Prez_l li) throws (1: kmiacServer.KmiacServerException kse);

	
	string printIsslMetod(1: IsslMet im) throws (1: kmiacServer.KmiacServerException kse);
	string printIsslPokaz(1: IsslPokaz ip) throws (1: kmiacServer.KmiacServerException kse);
	string printNapr(1: Napr na) throws (1: kmiacServer.KmiacServerException kse);//госпитализация и обследование
	string printNaprKons(1: NaprKons nk) throws (1: kmiacServer.KmiacServerException kse);//консультация
	string printVypis(1: i32 npasp, 2: i32 pvizitAmbId, 3:i32 userId) throws (1: kmiacServer.KmiacServerException kse);//выписка.данные из бд по номеру посещения и по номеру обращения.возм...а возм и нет
	string printKek(1: i32 npasp, 2: i32 pvizitAmbId) throws (1: kmiacServer.KmiacServerException kse);


//classifiers
	list<classifier.IntegerClassifier> get_n_z30() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_am0() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_az9() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z43() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_kas() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_n00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_l01() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_az0() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_l02() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getP0c() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getAp0() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getAq0() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getOpl() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_s00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_p0e1() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_nz1(1: i32 c_p0e1) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_m00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds_n_m00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);


//patient info
	PatientCommonInfo getPatientCommonInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PatientNotFoundException pne);
	Psign getPatientMiscInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PatientNotFoundException pne);
	list<Pvizit> getPvizitInfo(1: i32 npasp, 2: i64 datan, 3: i64 datak) throws (1: kmiacServer.KmiacServerException kse);
	list<PvizitAmb> getPvizitAmbInfo(1: i32 id_obr) throws (1: kmiacServer.KmiacServerException kse);

/*DispBer*/
	list<RdSlStruct> getRdSlInfo(1:i32 idDispb,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<RdDinStruct> getRdDinInfo(1:i32 idDispb,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void AddRdSl(1:RdSlStruct rdSl) throws (1: kmiacServer.KmiacServerException kse);
	void AddRdDin(1:RdDinStruct RdDin) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdSl(1:i32 idDispb,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteRdDin(1:i32 idDispb,2:i32 iD) throws (1: kmiacServer.KmiacServerException kse);

	void UpdateRdSl(1:i32 npasp, 2:i32 lgota) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdDin(1:i32 idDispb,2:i32 iD) throws (1: kmiacServer.KmiacServerException kse);

	list<RdInfStruct> getRdInfInfo(1:i32 idDispb,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);

	void AddRdInf(1:RdInfStruct rdInf) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdInf(1:i32 idDispb,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);

	void UpdateRdInf(1:i32 npasp, 2:i32 lgota) throws (1: kmiacServer.KmiacServerException kse);

}
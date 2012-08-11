namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
 * Запись на пациента в регистратуре
 */
struct ZapVr{
	1: optional i32 npasp;
	2: optional i32 vid_p; /*вид приема-первичность*/
	3: optional i64 timepn; /*время начала приема*/
	4: optional string fam;
	5: optional string im;
	6: optional string oth;
	7: optional string serpolis;
	8: optional string nompolis;
	9: optional i32 id_pvizit;
}

struct Pvizit {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 cpol;
	4: optional string cobr;
	5: optional i64 datao;
	6: optional i32 ishod;
	7: optional i32 rezult;
	8: optional i32 talon;
	9: optional i32 cod_sp;
	10: optional string cdol;
	11: optional i32 cuser;
	12: optional string zakl;
	13: optional i64 dataz;
	14: optional i32 idzab;
	15: optional string recomend;
	16: optional string vrach_fio;
}

struct PvizitAmb {
	1: optional i32 id;
	2: optional i32 id_obr;
	3: optional i32 npasp;
	4: optional i64 datap;
	5: optional i32 cod_sp;
	6: optional string cdol;
	7: optional string diag; 
	8: optional i32 mobs;
	9: optional i32 rezult;
	10: optional i32 opl;
	11: optional double stoim;
	12: optional i32 uet;
	13: optional i64 datak;
	14: optional i32 kod_rez;
	15: optional i32 k_lr;
	16: optional i32 n_sp;
	17: optional i32 pr_opl;
	18: optional i32 pl_extr;
	19: optional i32 vpom;
	20: optional string fio_vr;
}

struct PdiagAmb {
	 1: optional i32 id;
	 2: optional i32 id_obr;
	 3: optional i32 npasp;
	 4: optional string diag;
	 5: optional string named;/*медицинское описание д-за*/
	 6: optional i32 diag_stat;
	 7: optional bool predv;
	 8: optional i64 datad;
	 9: optional i32 obstreg;
	10: optional i32 cod_sp;
	11: optional string cdol;
	12: optional i64 datap;
	13: optional i64 dataot;
	14: optional i32 obstot;
	15: optional i32 codsp_ot;
	16: optional string cdol_ot;
	17: optional i32 vid_tr;
}

struct Psign{
	1: optional i32 npasp;
	2: optional string grup;
	3: optional string ph;
	4: optional string allerg;
	5: optional string farmkol;
	6: optional string vitae;
	7: optional string vred;
	8: optional string razv;
	9: optional string uslov;
	10: optional string per_zab;
	11: optional string per_oper;
	12: optional string gemotrans;
	13: optional string nasl;
	14: optional string ginek;
	15: optional string priem_lek;
	16: optional string prim_gorm;
}

struct Priem{
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 idpos;
	4: optional i32 sl_ob;
	5: optional i32 n_is;
	6: optional i32 n_kons;
	7: optional i32 n_proc;
	8: optional i32 n_lek;
	9: optional string t_jalob_d;
	10: optional string t_jalob_krov;
	11: optional string t_jalob_p;
	12: optional string t_jalob_moch;
	13: optional string t_jalob_endo;
	14: optional string t_jalob_nerv;
	15: optional string t_jalob_opor;
	16: optional string t_jalob_lih;
	17: optional string t_jalob_obh;
	18: optional string t_jalob_proch;
	19: optional string t_ob_sost;
	20: optional string t_koj_pokr;
	21: optional string t_sliz;
	22: optional string t_podk_kl;
	23: optional string t_limf_uzl;
	24: optional string t_kost_mysh;
	25: optional string t_nervn_ps;
	26: optional string t_chss;
	27: optional string t_temp;
	28: optional string t_ad;
	29: optional string t_rost;
	30: optional string t_ves;
	31: optional string t_telo;
	32: optional string t_sust;
	33: optional string t_dyh;
	34: optional string t_gr_kl;
	35: optional string t_perk_l;
	36: optional string t_aus_l;
	37: optional string t_bronho;
	38: optional string t_arter;
	39: optional string t_obl_s;
	40: optional string t_perk_s;
	41: optional string t_aus_s;
	42: optional string t_pol_rta;
	43: optional string t_jivot;
	44: optional string t_palp_jivot;
	45: optional string t_jel_kish;
	46: optional string t_palp_jel;
	47: optional string t_palp_podjjel;
	48: optional string t_pechen;
	49: optional string t_jelch;
	50: optional string t_selez;
	51: optional string t_obl_zad;
	52: optional string t_poyasn;
	53: optional string t_pochk;
	54: optional string t_moch;
	55: optional string t_mol_jel;
	56: optional string t_gr_jel;
	57: optional string t_matka;
	58: optional string t_nar_polov;
	59: optional string t_chitov;
	60: optional string t_st_localis;
	61: optional string t_ocenka;
	62: optional string t_jalob;
	63: optional string t_status_praesense;
	64: optional string t_fiz_obsl;
}

struct AnamZab{
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional string t_nachalo_zab;
	4: optional string t_sympt;
	5: optional string t_otn_bol;
	6: optional string t_ps_syt;
	7: optional string t_ist_zab;
}

struct PdiagZ{
	 1: optional i32 id;
	 2: optional i32 id_diag_amb;
	 3: optional i32 npasp;
	 4: optional string diag;
	 5: optional i32 cpodr;
	 6: optional i64 d_vz;
	 7: optional i32 d_grup;
	 8: optional i32 ishod;
	 9: optional i64 dataish;
	10: optional i64 datag;
	11: optional i64 datad;
	12: optional string diag_s;
	13: optional i32 d_grup_s;
	14: optional i32 cod_sp;
	15: optional string cdol_ot;
	16: optional i32 nmvd;
	17: optional i32 xzab;
	18: optional i32 stady;
	19: optional i32 disp;
	20: optional i32 pat;
	21: optional i32 prizb;
	22: optional i32 prizi;
	23: optional string named;
	24: optional string nameC00;
}

struct PatientCommonInfo {
	 1: optional i32 npasp;
	 2: optional string fam;
	 3: optional string im;
	 4: optional string ot;
	 5: optional i64 datar;
	 6: optional string poms_ser;
	 7: optional string poms_nom;
	 8: optional i32 pol;
	 9: optional i32 jitel;
	10: optional i32 sgrp;
	11: optional string adp_obl;
	12: optional string adp_gorod;
	13: optional string adp_ul;
	14: optional string adp_dom;
	15: optional string adp_korp;
	16: optional string adp_kv;
	17: optional string adm_obl;
	18: optional string adm_gorod;
	19: optional string adm_ul;
	20: optional string adm_dom;
	21: optional string adm_korp;
	22: optional string adm_kv;
	23: optional string mrab;
	24: optional string name_mr;
	25: optional i32 ncex;
	26: optional i32 poms_strg;
	27: optional i32 poms_tdoc;
	28: optional string poms_ndog;
	29: optional i32 pdms_strg;
	30: optional string pdms_ser;
	31: optional string pdms_nom;
	32: optional string pdms_ndog;
	33: optional i32 cpol_pr;
	34: optional i32 terp;
	35: optional i64 datapr;
	36: optional i32 tdoc;
	37: optional string docser;
	38: optional string docnum;
	39: optional i64 datadoc;
	40: optional string odoc;
	41: optional string snils;
	42: optional i64 dataz;
	43: optional string prof;
	44: optional string tel;
	45: optional i64 dsv;
	46: optional i32 prizn;
	47: optional i32 ter_liv;
	48: optional i32 region_liv;
}

struct RdSlStruct{
        1: optional  i32 idDispb;
        2: optional i32 npasp;
        3: optional i64 datay;
        4: optional i32 kolpr;
        5: optional i32 abort;
        6: optional string oslAb;
        7: optional i64 dataosl;
        8: optional i64 dataM;
        9: optional i32 yavka1;
	10: optional string plrod;
	11: optional string prRod;
	12: optional i64 DataZs;
	13: optional i32 kolRod;
	14: optional i32 let;
	15: optional i32 prmen;
	16: optional i32 deti;
	17: optional i32 polj;
	18: optional i32 kont;
	19: optional i32 oslrod
	20: optional i32 rost;
	21: optional i32 vesd;
	22: optional i32 dsp;
	23: optional i32 dsr;
	24: optional i32 dTroch;
	25: optional i32 cext;
	26: optional i32 indSol;
	27: optional i64 Datasn; 
	28: optional i32 VozMen;
        29: optional i64 DataSert;
        30: optional string NSert;
        31: optional string SSert;
	32: optional i64 DataRod;
}

struct RdDinStruct{
	1: optional i32 idDispb;
	2: optional i32 id;
	3: optional i64 datapos;
	4: optional i32 srok;
	5: optional i32 ves;
	6: optional i32 oj;
	7: optional i32 hdm;
	8: optional string dspos;
	9: optional i32 art1;
	10: optional i32 art2;
	11: optional i32 art3;
	12: optional i32 art4;
	13: optional i64 datasl;
	14: optional i32 spl;
	15: optional i32 oteki;
	16: optional i32 chcc;
	17: optional i32 polpl;
	18: optional i32 predpl;
	19: optional i32 serd;
	20: optional i32 serd1;
}

/*. Rd_Inf*/
struct RdInfStruct{
	1: optional i32 idDispb;
	2: optional i32 npasp;
	3: optional i32 obr;
	4: optional i32 sem;
	5: optional i32 oSocO;
	6: optional i32 UslPr;
	7: optional i32 vOtec;
	8: optional i64 grOtec;
	9: optional string phOtec;
	10: optional string fioOtec;
	11: optional string mrOtec;
	12: optional string telOtec;
	13: optional i32 vredOtec;
}

/*Список показателей исследований по выбранному методу*/
struct PokazMet{
	1: optional string pcod;
	2: optional string name_n;
	3: optional double stoim;
	4: optional string c_obst;
	5: optional bool vybor;
}

/*метод по виду исследования*/
struct Metod{
	1: optional string obst;
	2: optional string name_obst;
	3: optional i32 c_p0e1;
	//4: string pcod;
}

/*Список показателей исследований по выбранной системе*/
struct Pokaz{
	1: optional string pcod;
	2: optional string name_n;
	3: optional double stoim;
	4: optional i32 c_p0e1;
	5: optional string c_n_nz1;
	6: optional bool vybor;
}


struct P_isl_ld {
	 1: optional i32 nisl;
	 2: optional i32 npasp;
	 3: optional i32 cisl;
	 4: optional string pcisl;
	 5: optional i32 napravl;
	 6: optional i32 naprotd;
	 7: optional i64 datan;
	 8: optional i32 vrach;
	 9: optional string diag;
	10: optional i64 dataz;
	11: optional i32 pvizit_id;
}

struct Prez_d {
	1: optional i32 npasp;
	2: optional i32 nisl;
	3: optional string kodisl;
	4: optional double stoim;
}

struct Prez_l {
	1: optional i32 npasp;
	2: optional i32 nisl;
	3: optional string cpok;
	4: optional double stoim;
}


struct IsslMet {
	1: optional i32 kodVidIssl;
	2: optional i32 userId;
	3: optional i32 npasp;
	4: optional string kodMetod;
	5: optional list<string> pokaz;
	6: optional string mesto;
	7: optional string kab;
	8: optional i32 pvizitId;
	9: optional string cpodr_name;
	10: optional string clpu_name;
}

struct IsslPokaz {
	1: optional i32 kodVidIssl;
	2: optional i32 userId;
	3: optional i32 npasp;
	4: optional string kodMetod;
	5: optional list<string> pokaz;
	6: optional string mesto;
	7: optional string kab;
	8: optional i32 pvizitId;
	9: optional string cpodr_name;
	10: optional string clpu_name;
}

struct Napr{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional string obosnov;
	4: optional string clpu;
	5: optional i32 pvizitId; 
	6: optional string cpodr_name;
	7: optional string clpu_name;
}

struct NaprKons{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional string obosnov;
	4: optional string cpol;
	5: optional string nazv;
	6: optional string cdol;
	7: optional i32 pvizitId;
	8: optional string cpodr_name;
	9: optional string clpu_name;
}

struct IsslInfo{
	1: optional i32 nisl;
	2: optional i32 cisl;
	3: optional string name_cisl;
	4: optional string pokaz;
	5: optional string pokaz_name;
	6: optional string rez;
	7: optional i64 datav;
}


struct Pdisp{
	1: optional i32 id_diag;
	2: optional i32 npasp;
	3: optional i32 id;
	4: optional string diag;
	5: optional i32 pcod;
	6: optional i64 d_vz;
	7: optional i32 d_grup;
	8: optional i32 ishod;
	9: optional i64 dataish;
	10: optional i64 datag;
	11: optional i64 datad; 
	12: optional string diag_s;
	13: optional i32 d_grup_s;
	14: optional i32 cod_sp;
	15: optional string cdol_ot;
	16: optional bool sob;
	17: optional bool sxoch;
}

struct Protokol{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional i32 pvizit_id;
	4: optional i32 pvizit_ambId;
	5: optional i32 cpol;
	6: optional string cpodr_name;
	7: optional string clpu_name;
}

struct Vypis {
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional i32 pvizit_id;
	4: optional string cpodr_name;
	5: optional string clpu_name;
}


exception PvizitNotFoundException {
}

exception PsignNotFoundException {
}

exception PriemNotFoundException {
}

exception PatientNotFoundException {
}

exception PdiagNotFoundException {
}

exception PdispNotFoundException {
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	
	
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap) throws (1: kmiacServer.KmiacServerException kse);
	list<ZapVr> getZapVrSrc(1: string npaspList) throws (1: kmiacServer.KmiacServerException kse);
	
	void AddPvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPViz(1: Pvizit pv) throws (1: kmiacServer.KmiacServerException kse);
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
	list <PdiagAmb> getPdiagAmbProsm(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);

	Psign getPsign(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PsignNotFoundException sne);
	void setPsign(1: Psign sign) throws (1: kmiacServer.KmiacServerException kse);

	AnamZab getAnamZab(1: i32 id_pvizit, 2: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void setAnamZab(1: AnamZab anam) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteAnamZab(1: i32 pvizit_id) throws (1: kmiacServer.KmiacServerException kse);

	Priem getPriem(1: i32 npasp, 2: i32 posId) throws (1: kmiacServer.KmiacServerException kse, 2: PriemNotFoundException pne);
	void setPriem(1: Priem pr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePriem(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);

	list<PdiagZ> getPdiagzProsm(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 setPdiag(1: PdiagZ diag) throws (1: kmiacServer.KmiacServerException kse);
	PdiagZ getPdiagZ(1: i32 id_diag_amb) throws (1: kmiacServer.KmiacServerException kse, 2: PdiagNotFoundException pnf);

	i32 setPdisp(1: Pdisp disp) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp getPdisp(1: i32 id_diag) throws (1: kmiacServer.KmiacServerException kse, 2: PdispNotFoundException pnf);
	

	/*Исследования*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string metod) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPisl(1: P_isl_ld npisl) throws (1: kmiacServer.KmiacServerException kse);
	void AddPrezd(1: Prez_d di) throws (1: kmiacServer.KmiacServerException kse);
	void AddPrezl(1: Prez_l li) throws (1: kmiacServer.KmiacServerException kse);
	list<IsslInfo> getIsslInfo(1: i32 pvizit_id) throws (1: kmiacServer.KmiacServerException kse);

	
	string printIsslMetod(1: IsslMet im) throws (1: kmiacServer.KmiacServerException kse);
	string printIsslPokaz(1: IsslPokaz ip) throws (1: kmiacServer.KmiacServerException kse);
	string printNapr(1: Napr na) throws (1: kmiacServer.KmiacServerException kse);//госпитализация и обследование
	string printNaprKons(1: NaprKons nk) throws (1: kmiacServer.KmiacServerException kse);//консультация
	string printVypis(1: Vypis vp) throws (1: kmiacServer.KmiacServerException kse);//выписка.данные из бд по номеру посещения и по номеру обращения.возм...а возм и нет
	string printKek(1: i32 npasp, 2: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	string printProtokol(1: Protokol pk) throws (1: kmiacServer.KmiacServerException kse);


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
	list<classifier.IntegerClassifier> getVdi() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_ai0() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_abs() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_abv() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_abx() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_aby() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_abc() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_abb() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db1() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db2() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db3() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db4() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db5() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db6() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db7() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_db8() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_db9() throws (1: kmiacServer.KmiacServerException kse);

//patient info
	PatientCommonInfo getPatientCommonInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PatientNotFoundException pne);
	Psign getPatientMiscInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PatientNotFoundException pne);
	list<Pvizit> getPvizitInfo(1: i32 npasp, 2: i64 datan, 3: i64 datak) throws (1: kmiacServer.KmiacServerException kse);

//templates
	list<classifier.IntegerClassifier> getShablonTexts(1: i32 id_razd, 2: i32 id_pok, 3: string pcod_s00) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShablonCdol(1: i32 id_razd, 2: string pcod_s00) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getPokNames() throws (1: kmiacServer.KmiacServerException kse);

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
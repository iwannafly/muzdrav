namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
 * Запись на пациента в регистратуре
 */
struct ZapVr{
	1: optional i32 npasp;
	2: optional string fam;
	3: optional string im;
	4: optional string oth;
	5: optional string serpolis;
	6: optional string nompolis;
	7: optional i32 id_pvizit;
	8: optional i32 pol;
	9: optional i64 datar;
	10: optional i64 datap;
	11: optional i32 nuch;
	12: optional bool hasPvizit;
	13: optional i32 id_pvizit_amb;
}

struct Pvizit {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 cpol;
	4: optional i64 datao;
	5: optional i32 ishod;
	6: optional i32 rezult;
	7: optional i32 talon;
	8: optional i32 cod_sp;
	9: optional string cdol;
	10: optional i32 cuser;
	11: optional string zakl;
	12: optional i64 dataz;
	13: optional string recomend;
	14: optional string lech;
	15: optional i32 cobr;
	16: optional i32 idzab;
	17: optional string vrach_fio;
	18: optional bool closed;
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
	21: optional i64 dataz;
	22: optional i32 cpos;
	23: optional i32 cpol;
	24: optional i32 kod_ter;
	25: optional string cdol_name;
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
	12: optional i64 dataot;
	13: optional i32 obstot;
	14: optional i32 codsp_ot;
	15: optional string cdol_ot;
	16: optional i32 vid_tr;
	17: optional i32 id_pos;
}

struct Psign{
	1: optional i32 npasp;
	2: optional string grup;
	3: optional string ph;
	4: optional string allerg;
	5: optional string farmkol;
	6: optional string vitae;
	7: optional string vred;
}

struct Priem{
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional i32 idpos;
	4: optional i32 sl_ob;
	5: optional i32 n_is;
	6: optional i32 n_kons;
	7: optional i32 n_proc;
	8: optional i32 n_lek;
	9: optional string t_chss;
	10: optional string t_temp;
	11: optional string t_ad_sist;
	12: optional string t_rost;
	13: optional string t_ves;
	14: optional string t_st_localis;
	15: optional string t_ocenka;
	16: optional string t_jalob;
	17: optional string t_status_praesense;
	18: optional string t_fiz_obsl;
	19: optional string t_recom;
	20: optional string t_ad_dist;
}

struct AnamZab{
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional string t_ist_zab;
}

struct PdiagZ{
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string diag;
	4: optional i64 d_vz;
	5: optional i32 d_grup;
	6: optional i32 ishod;
	7: optional i64 dataish;
	8: optional i64 datag;
	9: optional i64 datad;
	10: optional i32 nmvd;
	11: optional i32 xzab;
	12: optional i32 stady;
	13: optional i32 disp;
	14: optional i32 pat;
	15: optional i32 prizb;
	16: optional i32 prizi;
	17: optional string named;
	18: optional i32 ppi;
	19: optional string nameC00;
	20: optional i32 id_diag_amb;

}

struct RdSlStruct{
        1: optional  i32 id;
        2: optional i32 npasp;
        3: optional i64 datay;
	4: optional i64 dataosl;
	5: optional i32 abort;
	6: optional i32 shet;
	7: optional i64 dataM;
	8: optional i32 yavka1;
	9: optional i32 ishod;
	10: optional i64 Datasn; 
	11: optional i64 DataZs;
        12: optional i32 kolrod;
	13: optional i32 deti;
	14: optional bool kont;
	15: optional double vesd;
	16: optional i32 dsp;
	17: optional i32 dsr;
	18: optional i32 dTroch;
	19: optional i32 cext;
	20: optional i32 indsol;
	21: optional i32 prmen;
	22: optional i64 dataz;
	23: optional i64 datasert;
	24: optional string nsert;
	25: optional string ssert;
	26: optional string oslab;
	27: optional i32 plrod;
	28: optional string prrod;
	29: optional i32 vozmen;
	30: optional i32 oslrod;
	31: optional i32 polj;
	32: optional i64 dataab;
	33: optional i32 srokab;
	34: optional i32 cdiagt;
	35: optional i32 cvera;
	36: optional i32 id_pvizit;
        37: optional i32 rost;
        38: optional bool eko;
        39: optional bool rub;
        40: optional bool predp;
        41: optional i32 osp;
        42: optional i32 cmer;
}
struct RdDinStruct{
	1: optional i32 id_rd_sl;
	2: optional i32 id_pvizit;
	3: optional i32 npasp;
	4: optional i32 srok;
	5: optional i32 grr;
	6: optional i32 ball;
	7: optional i32 oj;
	8: optional i32 hdm;
	9: optional string dspos;
	10: optional i32 art1;
	11: optional i32 art2;
	12: optional i32 art3;
	13: optional i32 art4;
	14: optional i32 spl;
	15: optional i32 oteki;
	16: optional i32 chcc;
	17: optional i32 polpl;
	18: optional i32 predpl;
	19: optional i32 serd;
	20: optional i32 serd1;
	21: optional i32 id_pos;
	22: optional double ves;
        23: optional i32 tonus;
}
/*. Rd_Inf*/
struct RdInfStruct{
	1: optional i32 npasp;
	2: optional i32 obr;
	3: optional i32 sem;
	4: optional i32 vOtec;
	5: optional string grotec;
	6: optional string phOtec;
	7: optional i64 dataz;
	8: optional string fioOtec;
	9: optional string mrOtec;
	10: optional string telOtec;
	11: optional i32 vredOtec;
	12: optional i32 osoco;
	13: optional i32 uslpr;
        14: optional string zotec;
}
/*Выгрузка для Кемерово по диспансеризации беременных*/
struct RdPatient{
         1: optional i32    uid;
         2: optional i32 npasp;
         3: optional string fam;
         4: optional string im;
         5: optional string ot;
         6: optional i64    datar;
         7: optional string docser;
         8: optional string docnum;
         9: optional i32    tawn;
        10: optional string street;
        11: optional string house;  
        12: optional string flat;
        13: optional string poms_ser;
        14: optional string poms_nom;
        15: optional string dog;
        16: optional i32    stat;
        17: optional i32    lpup;
        18: optional i32    terp;
        19: optional i32    ftawn; 
        20: optional string fstreet;
        21: optional string fhouse;
        22: optional string fflat;
        23: optional string grk;
        24: optional string rez;  
        25: optional string telm;
        26: optional string vred;
        27: optional i32 deti;
        28: optional i64 datay;
	29: optional i32 yavka1;
        30: optional i64 datazs;
        31: optional string famv;
        32: optional string imv;
        33: optional string otv;
	34: optional i64 datasn; 
	35: optional i32 shet;
        36: optional i32 kolrod;
	37: optional i32 abort;
        38: optional i32 vozmen;
        39: optional i32 prmen;
	40: optional i64 datam;
        41: optional bool kont;
        42: optional i32 dsp;
        43: optional i32 dsr;
        44: optional i32 dtroch;
        45: optional i32 cext;
        46: optional i32 indsol;
        47: optional string vitae;
        48: optional string allerg;
	49: optional i32 ishod;
        50: optional string prrod;
        51: optional i32 oslrod;
        52: optional i32 sem;
        53: optional i32 rost;
        54: optional i32 vesd; 
        55: optional i32 osoco;
        56: optional i32 uslpr;
        57: optional i64 dataz;
        58: optional i32 polj;
        59: optional i32 obr; 
        60: optional string fiootec;
        61: optional string mrabotec;
        62: optional string telotec; 
        63: optional string grotec;
        64: optional string photec;
        65: optional i32 vredotec;
        66: optional i32 vozotec;
        67: optional string mrab;
        68: optional string prof; 
        69: optional bool eko;
        70: optional bool rub;
        71: optional bool predp;
        72: optional i32 terpr;
        73: optional i32 oblpr;
        74: optional i32 diag;
        75: optional i32 cvera;
        76: optional i64 dataosl;
        77: optional i32 osp;
}
struct RdVizit{
         1: optional i32    uid;
         2: optional i64    dv;
         3: optional string sp;
         4: optional string famwr;
         5: optional string imwr;
         6: optional string otwr;
         7: optional string diag;
         8: optional i32    mso;
         9: optional i32    rzp;
        10: optional i32    aim;
        11: optional i32    npr; 
        12: optional i32    npasp; 
}
struct RdConVizit{
         1: optional i32    uiv;
         2: optional i32    uid;
         3: optional double ves;
         4: optional i32    ned;
         5: optional i32    lcad;
         6: optional i32    ldad; 
         7: optional i32    rcad;
         8: optional i32    rdad;
         9: optional i32    rost;
        10: optional i64    datar;
        11: optional i32    obr;
        12: optional i32    sem;
        13: optional i32    osoco;
        14: optional string vrpr;
        15: optional i32 npasp;
        16: optional i32 hdm;
        17: optional i32 spl; 
        18: optional i32 oj;
        19: optional i32 chcc;
        20: optional i32 polpl;
        21: optional i32 predpl;
        22: optional i32 serd;
        23: optional i32 serd1;
        24: optional i32 oteki;
}

/*Список показателей исследований по выбранному методу*/
struct PokazMet{
	1: optional string pcod;
	2: optional string name_n;
	3: optional bool vybor;
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
	12: optional i32 prichina;
	13: optional i32 kodotd;
	14: optional i64 datav;
	15: optional i32 vopl;
	16: optional i32 id_pos;
	17: optional i64 datap;
	18: optional string name_pcisl;
	19: optional i32 clpu;
}

struct Prez_d {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string kodisl;
	5: optional string rez;
}

struct Prez_l {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string cpok;
	5: optional string rez;
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
	11: optional i32 clpu;
	12: optional i32 pvizitambId;
	13: optional i32 cpodr;
	14: optional i32 kod_lab;
	15: optional i64 datap;
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
	8: optional string diag;
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
	10: optional string diag;
}

struct IsslInfo{
	1: optional i32 nisl;
	2: optional i32 cisl;
	3: optional string name_cisl;
	4: optional string pokaz;
	5: optional string pokaz_name;
	6: optional string rez;
	7: optional i64 datav;
	8: optional i64 datan;
	9: optional i32 id;
	10: optional string op_name;
	11: optional string rez_name;
	12: optional i32 gruppa;
	13: optional i32 kodotd;
	14: optional i64 datap;
	15: optional string diag;
}


struct Pdisp{
	1: optional i32 npasp;
	2: optional i32 id;
	3: optional string diag;
	4: optional i32 pcod;
	5: optional i64 d_vz;
	6: optional i32 d_grup;
	7: optional i32 ishod;
	8: optional i64 dataish;
	9: optional i64 datag;
	10: optional i64 datad; 
	11: optional string diag_s;
	12: optional i32 d_grup_s;
	13: optional i32 cod_sp;
	14: optional string cdol_ot;
	15: optional i32 d_uch;
	16: optional string diag_n;
}

struct Vypis {
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional i32 pvizit_id;
	4: optional string cpodr_name;
	5: optional string clpu_name;
}

struct PNapr {
	1: i32 id;
	2: i32 idpvizit;
	3: i32 vid_doc;
	4: string text;
	5: i32 preds;
	6: i32 zaved;
	7: string name;
}

struct KartaBer {
	1: optional i32 npasp;
	2: optional i32 id_pvizit;
	3: optional i32 id_pos;
	4: optional i32 id_rd_sl;
}

struct ShablonText {
	1: i32 grupId;
	2: string grupName;
	3: string text;
}

struct Shablon {
	1: i32 id;
	2: string diag;
	3: string din;
	4: string name;
	5: list<ShablonText> textList;
}

struct Pmer{
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string diag;
	4: optional i32 pmer;
	5: optional i64 pdat;
	6: optional i64 fdat;
	7: optional i64 dataz;
	8: optional i32 prichina;
	9: optional i32 rez;
	10: optional string cdol;
	11: optional i64 dnl;
	12: optional i64 dkl;
	13: optional i32 lpu;
	14: optional i32 ter;
	15: optional i32 cpol;
	16: optional i32 id_obr;
	17: optional i32 cod_sp;
	18: optional string cdol_n;
	
}

struct Pobost{
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 id_pdiag;
	4: optional string diag;
	5: optional i32 sl_obostr;
	6: optional i32 sl_hron;
	7: optional i32 cod_sp;
	8: optional string cdol;
	9: optional i64 dataz;
	10: optional i32 id_obr;
}

struct Cgosp{
	1: optional i32 id;
	2: optional i32 ngosp;
	3: optional i32 npasp;
	4: optional i32 nist;
	5: optional string naprav;
	6: optional string diag_n;
	7: optional string named_n;
	8: optional i64 dataz;
	9: optional i32 vid_st;
	10: optional i32 n_org;
	11: optional i32 pl_extr;
	12: optional i64 datap;
	13: optional i64 vremp;
	14: optional i32 cotd;
	15: optional string diag_p;
	16: optional string named_p;
	17: optional i32 cotd_p;
	18: optional i64 dataosm;
	19: optional i64 vremosm;
}

struct Cotd{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional i32 nist;
	4: optional i32 cotd;
	5: optional i64 dataz;
	6: optional i32 stat_type;
}

struct VrachInfo {
	1: optional i32 mrab_id;
	2: optional string cdol;
	3: optional string cdol_name;
	4: optional i32 pcod;
	5: optional string short_fio;
}

struct SpravNetrud {
	1: optional string fam;
	2: optional string im;
	3: optional string oth;
	4: optional i64 datar;
	5: optional i32 npasp;
	6: optional string diag;
	7: optional i32 userId;
	8: optional string cpodr_name;
	9: optional string clpu_name;
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

exception PrdslNotFoundException {
}
exception PrdDinNotFoundException {
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	
	
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap, 4: i32 cpol) throws (1: kmiacServer.KmiacServerException kse);
	ZapVr getZapVrSrc(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<VrachInfo> getVrachList(1: i32 clpu, 2: i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);
	
	void AddPvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPvizitId(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);	
	Pvizit getPvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse, 2: PvizitNotFoundException pne);
	list<Pvizit> getPvizitList(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteEtalon (1: i32 id_pvizit) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	list<PvizitAmb> getPvizitAmb(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizitAmb(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizitAmbObr(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	list<PdiagAmb> getPdiagAmb(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePdiagAmb(1: i32 diagId) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePdiagAmbVizit(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);
	PdiagAmb getPdiagAmbProsm(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);

	Psign getPsign(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PsignNotFoundException sne);
	void setPsign(1: Psign sign) throws (1: kmiacServer.KmiacServerException kse);

	AnamZab getAnamZab(1: i32 id_pvizit, 2: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void setAnamZab(1: AnamZab anam) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteAnamZab(1: i32 pvizit_id) throws (1: kmiacServer.KmiacServerException kse);

	Priem getPriem(1: i32 npasp, 2: i32 posId) throws (1: kmiacServer.KmiacServerException kse, 2: PriemNotFoundException pne);
	void setPriem(1: Priem pr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePriem(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);

	i32 setPdiag(1: PdiagZ diag) throws (1: kmiacServer.KmiacServerException kse);
	PdiagZ getPdiagZ(1: i32 npasp, 2: string diag) throws (1: kmiacServer.KmiacServerException kse, 2: PdiagNotFoundException pnf);
	list<PdiagZ> getPdiagZInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getPdiagInfo (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void deleteDiag(1: i32 npasp, 2: string diag, 3: i32 pcod, 4: i32 idDiagAmb) throws (1: kmiacServer.KmiacServerException kse);

	i32 setPdisp(1: Pdisp disp) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp getPdisp(1: i32 npasp, 2: string diag, 3: i32 cpol) throws (1: kmiacServer.KmiacServerException kse, 2: PdispNotFoundException pnf);

	i32 AddPnapr(1: PNapr pn) throws (1: kmiacServer.KmiacServerException kse);

	bool isZapVrNext(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);
	
	list<i32> AddCGosp(1: Cgosp cgsp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddCotd(1: Cotd cotd) throws (1: kmiacServer.KmiacServerException kse);

	/*Исследования*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string c_nz1, 2: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPisl(1: P_isl_ld npisl) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezd(1: Prez_d di) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezl(1: Prez_l li) throws (1: kmiacServer.KmiacServerException kse);
	list<P_isl_ld> getIsslInfoDate(1: i32 id_pvizitAmb) throws (1: kmiacServer.KmiacServerException kse);
	list<IsslInfo> getIsslInfoPokaz(1: i32 nisl) throws (1: kmiacServer.KmiacServerException kse);
	IsslInfo getIsslInfoPokazId(1: i32 id_issl) throws (1: kmiacServer.KmiacServerException kse);

	
	string printIsslMetod(1: IsslMet im) throws (1: kmiacServer.KmiacServerException kse);
	string printIsslPokaz(1: IsslPokaz ip) throws (1: kmiacServer.KmiacServerException kse);
	string printNapr(1: Napr na) throws (1: kmiacServer.KmiacServerException kse);//госпитализация и обследование
	string printNaprKons(1: NaprKons nk) throws (1: kmiacServer.KmiacServerException kse);//консультация
	string printVypis(1: Vypis vp) throws (1: kmiacServer.KmiacServerException kse);//выписка.данные из бд по номеру посещения и по номеру обращения.возм...а возм и нет
	string printKek(1: i32 npasp, 2: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	string printProtokol(1: i32 npasp, 2: i32 userId, 3: i32 pvizit_id, 4: i32 pvizit_ambId, 5: i32 cpol, 6: i32 clpu, 7: i32 nstr) throws (1: kmiacServer.KmiacServerException kse);
	string printMSK(1: i32 npasp)  throws (1: kmiacServer.KmiacServerException kse);
	string printAnamZab(1: i32 id_pvizit) throws (1: kmiacServer.KmiacServerException kse);
	string printSpravNetrud(1: SpravNetrud sn) throws (1: kmiacServer.KmiacServerException kse);
	string printSprBass(1: i32 npasp, 2: i32 pol) throws (1: kmiacServer.KmiacServerException kse);


//classifiers
	list<classifier.StringClassifier> get_n_nz1(1: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_m00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds_n_m00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_vid_issl() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_s00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_c00(1: i32 npasp, 2: i32 pcod) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_tip() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_m00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_p0c() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_ap0() throws (1: kmiacServer.KmiacServerException kse);
	string get_n_mkb(1: string pcod) throws (1: kmiacServer.KmiacServerException kse);

/*DispBer*/
	RdSlStruct getRdSlInfo(1: i32 id_pvizit, 2: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PrdslNotFoundException pnf);
	RdDinStruct getRdDinInfo(1: i32 id_Pvizit, 2:i32 npasp, 3:i32 id_pos) throws (1: kmiacServer.KmiacServerException kse);
	RdInfStruct getRdInfInfo (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddRdSl(1:RdSlStruct rdSl) throws (1: kmiacServer.KmiacServerException kse);
	void AddRdDin(1:RdDinStruct RdDin) throws (1: kmiacServer.KmiacServerException kse);
 
	void DeleteRdSl(1:i32 id_pvizit,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteRdDin(1:i32 id_pos) throws (1: kmiacServer.KmiacServerException kse);

	void UpdateRdSl(1: RdSlStruct Dispb) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdDin(1: RdDinStruct Din) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdInf(1: RdInfStruct inf) throws (1: kmiacServer.KmiacServerException kse);

	void AddRdInf(1:RdInfStruct rdInf) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdInf(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	
	string printKartaBer(1:KartaBer kb) throws (1: kmiacServer.KmiacServerException kse);
	string formfilecsv(1:KartaBer kb) throws (1: kmiacServer.KmiacServerException kse);
	string printDnevVr(1: i32 vrach) throws (1: kmiacServer.KmiacServerException kse);

/*Выгрузка для Кемерово по диспансеризации беременных*/
        list<RdPatient> getRdPatient() throws (1: kmiacServer.KmiacServerException kse);
        list<RdVizit> getRdVizit() throws (1: kmiacServer.KmiacServerException kse);
        list<RdConVizit>  getRdConVizit() throws (1: kmiacServer.KmiacServerException kse);
/*       list<RdSlStruct1> getRdSl() throws (1: kmiacServer.KmiacServerException kse);*/
/*Shablon*/
	list<classifier.StringClassifier> getShOsmPoiskDiag(1: i32 cspec, 2: i32 cslu, 3: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShOsmPoiskName(1: i32 cspec, 2: i32 cslu, 3: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShOsmByDiag(1: i32 cspec, 2: i32 cslu, 3: string diag, 4: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	Shablon getShOsm(1: i32 id_sh) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShDopNames(1: i32 idRazd) throws (1: kmiacServer.KmiacServerException kse);
	classifier.IntegerClassifier getShDop(1: i32 id_sh) throws (1: kmiacServer.KmiacServerException kse);
	
/*DispMer*/
	list<Pmer> getPmer (1: i32 npasp, 2: string diag) throws (1: kmiacServer.KmiacServerException kse);
	Pmer getDispMer (1: i32 id_pmer) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPmer(1: Pmer pm) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePmer(1: Pmer pm) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePmer(1: i32 pmer_id) throws (1: kmiacServer.KmiacServerException kse);

/*Disp_sl_obostr*/
	list<Pobost> getPobost (1: i32 npasp, 2: string diag) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPobost(1: Pobost pbst) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePobost(1: Pobost pbst) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePobost(1: i32 pobost_id) throws (1: kmiacServer.KmiacServerException kse);

/*Stoim_p*/
	double getStoim(1: string kateg, 2: i32 prv, 3: string cdol);


}

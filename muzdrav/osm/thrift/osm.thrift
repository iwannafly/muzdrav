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
	10: optional i32 pol;
	11: optional i64 datar;
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
	11: optional string t_ad;
	12: optional string t_rost;
	13: optional string t_ves;
	14: optional string t_st_localis;
	15: optional string t_ocenka;
	16: optional string t_jalob;
	17: optional string t_status_praesense;
	18: optional string t_fiz_obsl;
}

struct AnamZab{
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional string t_ist_zab;
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
}
/*Выгрузка для Кемерово по диспансеризации беременных*/
struct RdPatient{
         1: optional i32    uid;
         2: optional string fam;
         3: optional string im;
         4: optional string ot;
         5: optional i64    datar;
         6: optional string docser;
         7: optional string docnum;
         8: optional i32    tawn;
         9: optional string street;
        10: optional string house;  
        11: optional string flat;
        12: optional string poms_ser;
        13: optional string poms_nom;
        14: optional string dog;
        15: optional i32    stat;
        16: optional i32    lpup;
        17: optional i32    terp;
        18: optional i32    ftawn; 
        19: optional string fstreet;
        20: optional string fhouse;
        21: optional string fflat;
        22: optional string grk;
        23: optional string rez;  
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
}

struct Prez_d {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string kodisl;
}

struct Prez_l {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string cpok;
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
	4: string next_osm;
	5: list<ShablonText> textList;
}

struct Pmer{
	1: i32 id;
	2: i32 npasp;
	3: i32 id_pdiag;
	4: string diag;
	5: i32 pmer;
	6: i64 pdat;
	7: i64 fdat;
	8: i32 cod_sp;
	9: i64 dataz;
	10: i32 prichina;
	11: i32 rez;
	12: string cdol;
	13: i32 id_pvizit;
	14: i32 id_pos;
	15: string name_pmer;
}

struct Pobost{
	1: i32 id;
	2: i32 npasp;
	3: i32 id_pdiag;
	4: string diag;
	5: i32 sl_obostr;
	6: i32 sl_hron;
	7: i32 cod_sp;
	8: string cdol;
	9: i64 dataz;
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
	i32 AddPvizitId(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);	
	Pvizit getPvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse, 2: PvizitNotFoundException pne);
	void UpdatePvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteEtalon (1: i32 id_pvizit) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	list<PvizitAmb> getPvizitAmb(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizitAmb(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	list<PdiagAmb> getPdiagAmb(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePdiagAmb(1: i32 diagId) throws (1: kmiacServer.KmiacServerException kse);
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
	PdiagZ getPdiagZ(1: i32 id_diag_amb) throws (1: kmiacServer.KmiacServerException kse, 2: PdiagNotFoundException pnf);

	i32 setPdisp(1: Pdisp disp) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp getPdisp(1: i32 id_diag) throws (1: kmiacServer.KmiacServerException kse, 2: PdispNotFoundException pnf);

	i32 AddPnapr(1: PNapr pn) throws (1: kmiacServer.KmiacServerException kse);

	bool isZapVrNext(1: i32 idObr) throws (1: kmiacServer.KmiacServerException kse);

	/*Исследования*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string c_nz1) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPisl(1: P_isl_ld npisl) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezd(1: Prez_d di) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezl(1: Prez_l li) throws (1: kmiacServer.KmiacServerException kse);

	
	string printIsslMetod(1: IsslMet im) throws (1: kmiacServer.KmiacServerException kse);
	string printIsslPokaz(1: IsslPokaz ip) throws (1: kmiacServer.KmiacServerException kse);
	string printNapr(1: Napr na) throws (1: kmiacServer.KmiacServerException kse);//госпитализация и обследование
	string printNaprKons(1: NaprKons nk) throws (1: kmiacServer.KmiacServerException kse);//консультация
	string printVypis(1: Vypis vp) throws (1: kmiacServer.KmiacServerException kse);//выписка.данные из бд по номеру посещения и по номеру обращения.возм...а возм и нет
	string printKek(1: i32 npasp, 2: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	string printProtokol(1: Protokol pk) throws (1: kmiacServer.KmiacServerException kse);
	string printMSK(1: i32 npasp)  throws (1: kmiacServer.KmiacServerException kse);


//classifiers
	list<classifier.StringClassifier> get_n_nz1(1: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_m00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_lds_n_m00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_vid_issl() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_s00(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> get_n_c00(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);

/*DispBer*/
	RdSlStruct getRdSlInfo(1: i32 id_pvizit, 2: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<RdDinStruct> getRdDinInfo(1: i32 id_Pvizit, 2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	RdInfStruct getRdInfInfo (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddRdSl(1:RdSlStruct rdSl) throws (1: kmiacServer.KmiacServerException kse);
	void AddRdDin(1:RdDinStruct RdDin) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdSl(1:i32 id_pvizit,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteRdDin(1:i32 id_pos,2:i32 iD) throws (1: kmiacServer.KmiacServerException kse);

	void UpdateRdSl(1: RdSlStruct Dispb) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdDin(1: RdDinStruct Din) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdInf(1: RdInfStruct inf) throws (1: kmiacServer.KmiacServerException kse);

	void AddRdInf(1:RdInfStruct rdInf) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdInf(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	
	string printKartaBer(1:KartaBer kb) throws (1: kmiacServer.KmiacServerException kse);
	string formfilecsv(1:KartaBer kb) throws (1: kmiacServer.KmiacServerException kse);

/*Выгрузка для Кемерово по диспансеризации беременных*/
        list<RdPatient> getRdPatient() throws (1: kmiacServer.KmiacServerException kse);
        list<RdVizit> getRdVizit() throws (1: kmiacServer.KmiacServerException kse);
        list<RdConVizit>  getRdConVizit() throws (1: kmiacServer.KmiacServerException kse);
/*Shablon*/
	list<classifier.StringClassifier> getShOsmPoiskDiag(1: i32 cspec, 2: i32 cslu, 3: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShOsmPoiskName(1: i32 cspec, 2: i32 cslu, 3: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShOsmByDiag(1: i32 cspec, 2: i32 cslu, 3: string diag, 4: string srcText) throws (1: kmiacServer.KmiacServerException kse);
	Shablon getShOsm(1: i32 id_sh) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShDopNames(1: i32 idRazd) throws (1: kmiacServer.KmiacServerException kse);
	classifier.IntegerClassifier getShDop(1: i32 id_sh) throws (1: kmiacServer.KmiacServerException kse);

/*DispMer*/
	list<Pmer> getPmer (1: i32 npasp, 2: string diag) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPmer(1: Pmer pm) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePmer(1: Pmer pm) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePmer(1: i32 pmer_id) throws (1: kmiacServer.KmiacServerException kse);

/*Disp_sl_obostr*/
	list<Pobost> getPobost (1: i32 npasp, 2: string diag) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPobost(1: Pobost pbst) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePobost(1: Pobost pbst) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePobost(1: i32 pobost_id) throws (1: kmiacServer.KmiacServerException kse);

}
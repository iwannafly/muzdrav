namespace java ru.nkz.ivcgzo.ldsThrift

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct ObInfIsl {
	 1: i32 npasp;
	 2: i32 nisl;
	 3: i32 kodotd;
	 4: optional i32 nprob;
	 5: string pcisl;
	 6: i32 cisl;
	 7: optional i64 datap;
	 8: optional i64 datav;
	 9: optional i32 prichina;
       	10: optional i32 popl;
	11: optional i32 napravl;
	12: optional i32 naprotd;
	13: optional i32 vrach;
	14: i32 vopl;
	15: string diag;
	16: i32 kodvr;
	17: i64 dataz;
	18: i32 cuser;
	19: i32 id_gosp;	
	20: i32 id_pos;
	21: string talon;
	22: i32 kod_ter;
}

struct DiagIsl {
	 1: optional i32 npasp;
	 2: optional i32 nisl;
	 3: string kodisl;
	 4: optional i32 rez;
	 5: string anamnez;
	 6: string anastezi;
	 7: string model;
	 8: optional i32 kol;
	 9: string op_name;
        10: string rez_name;
	11: optional double stoim;
	12: string pcod_m;
	13: i32 id;
}

struct LabIsl {
	 1: optional i32 npasp;
	 2: optional i32 nisl;
	 3: string cpok;
	 4: string name;	
	 5: string zpok;
	 6: string norma;
	 7: optional double stoim;
	 8: string pcod_m;
	 9: string name_m;
	10: i32 id;
}

struct S_ot01{
	1: i32 cotd;
	2: string pcod;
	3: string c_obst;
	4: string c_nz1;
}


struct S_ot01IsMet{
	1:string pcod;
	2:string name;
	3:string pcod_m;
	4:string name_m;
	5:double stoim;
	6:bool vibor;
}



struct Patient {
	1: i32 npasp;
	2: string fam;
	3: string im;
	4: string ot;
	5: i64 datar;
	6: string poms_ser;
	7: string poms_nom;
	8: string adp_gorod;
	9: string adp_ul;
	10:string adp_dom;
	11:string adp_kv;
	12:string adm_gorod;
	13:string adm_ul;
	14:string adm_dom;
	15:string adm_kv;
	16:i32 ter_liv;
}


struct Metod {
	1: i32 c_p0e1;
	2: string pcod;
	3: string c_obst;
	4: string name;
	5: double stoim;	
	6: bool vibor;
}


struct N_ldi {
	1: string pcod;
	2: string c_nz1;
	3: string name_n;
	4: string name;
	5: string norma;
	6: i32 c_p0e1;
	7: bool vibor;
	8: i32 id;
}


/*struct S_ldi{
	1: i32 id;
	2: string name; 
}*/


struct N_lds {
	1: i32 clpu;
	2: i32 pcod;
	3: string name;
	4: string tip;
}


struct Sh_lds{
	1: i32 c_p0e1;
	2: string c_ldi;
	3: string name;
	4: string opis;
	5: string zakl;
}

struct InputLG{
    1: i32 kotd;
    2: i64 daten;
    3: i64 datek;
    4: optional string c_nz1;
}


/*
*Исследование уже существует
*/
exception IslExistsException {
}


/**
 * Исследование с такими данными не найдено
 */
exception IslNotFoundException {
}

/*
* Диагностическое исследование уже существует
*/
exception DIslExistsException {
}


/**
 * Диагностическое исследование с такими данными не найдено
 */
exception DIslNotFoundException {
}

/*
* Лабораторные исследование уже существует
*/
exception LIslExistsException {
}


/**
 * Лабораторные исследование с такими данными не найдено
 */
exception LIslNotFoundException {
}


/**
 * Пациент с такими данными не найден
 */
exception PatientNotFoundException {
}


/**
 * Метод исследования ненайден
 */
exception MetodNotFoundException {
}


/**
 * Исследование ненайдено
 */
exception LdiNotFoundException {
}



/**
 * Наименование исследования ненайдено
 */
exception SLdiNotFoundException {
}



/*
* Исследование уже существует
*/
exception S_ot01ExistsException {
}


/**
 * Исследование с такими данными не найдено
 */
exception S_ot01NotFoundException {
}


/**
* Нет шаблона с таким исследованием	
 */
exception Sh_ldsNotFoundException {
}

service LDSThrift extends kmiacServer.KmiacServer {
	list<ObInfIsl> GetObInfIslt(1: i32 npasp, 2: i32 kodotd);
	ObInfIsl GetIsl(1: i32 npasp) throws (1: IslNotFoundException ine);
	i32 AddIsl(1: ObInfIsl info)throws (1: IslExistsException iee);
	void UpdIsl(1: ObInfIsl info)throws (1: IslExistsException iee);
	void DelIsl(1: i32 nisl);

	list<DiagIsl> GetDiagIsl(1: i32 nisl);
	DiagIsl GetDIsl(1: i32 nisl)throws (1: DIslNotFoundException dine);
	DiagIsl GetDIslPos(1: string kodisl; 2: i64 datav; 3: i32 npasp; 4: i32 kodotd)throws (1: DIslNotFoundException dine);
	void AddDIsl(1: DiagIsl di)throws (1: DIslExistsException diee);
	void UpdDIsl(1: DiagIsl di)throws (1: DIslExistsException diee);
	void DelDIsl(1: i32 nisl, 2: string kodisl);
	void DelDIslP(1: i32 nisl);

	list<LabIsl> GetLabIsl(1: i32 nisl; 2: string c_nz1);
	LabIsl GetLIsl(1: i32 nisl; 2: string c_nz1)throws (1: LIslNotFoundException line);
	void AddLIsl(1: LabIsl li)throws (1: LIslExistsException liee);
	void UpdLIsl(1: LabIsl li)throws (1: LIslExistsException liee);
	void DelLIsl(1: i32 nisl, 2: string cpok, 3: i32 id);
	void DelLIslD(1: i32 nisl);
	void DelLIsl2(1: i32 nisl, 2: string cpok, 3: string pcod_m);

	list<S_ot01> GetS_ot01(1: i32 cotd, 2: string c_nz1);
	list<S_ot01> GetMinS_ot01(1: i32 cotd);
	S_ot01 GetSot01(1: i32 cotd, 2: string c_nz1) throws (1: S_ot01NotFoundException sone);
	void AddS_ot01(1: S_ot01 so)throws (1: S_ot01ExistsException soee);
	void UpdS_ot01(1: S_ot01 so)throws (1: S_ot01ExistsException soee);
	void DelS_ot01(1: i32 cotd, 2: string pcod, 3: string c_nz1);
	void DelS_ot01D(1: i32 cotd, 2: string pcod, 3: string c_obst, 4: string c_nz1);
	
	list<S_ot01IsMet> GetS_ot01IsMet(1: i32 cotd, 2:string c_nz1);

    	list<Patient> getPatient(1: string npasp) throws (1: PatientNotFoundException pnfe);
    	list<Patient> getPatDat(1: i32 kodotd; 2: i64 datap) throws (1: PatientNotFoundException pnfe);
	
	list<Metod> getMetod(1: i32 c_p0e1; 2: string pcod; 3: string pcod_m) throws (1: MetodNotFoundException mnfe);
	list<Metod> GetStoim(1: string pcod, 2: string c_obst, 3: i32 kodotd);
	list<Metod> GetLabStoim(1: string pcisl, 2: i32 kodotd);
	
	list<N_ldi> getN_ldi(1: string c_nz1; 2: i32 c_p0e1) throws (1: LdiNotFoundException lnfe);
	list<N_ldi> getAllN_ldi();
	void UpdN_ldi (1: N_ldi nldi) throws (1: LdiNotFoundException lnfe);

/*	list<S_ldi> getAllS_ldi();
	void InsS_ldi (1: S_ldi s_ldi);
	void UpdS_ldi (1: S_ldi s_ldi) throws (1: SLdiNotFoundException s_lnfe);*/
	
	list<N_lds> getN_lds (1: i32 pcod);

	list<Sh_lds> getSh_lds (1: string c_ldi) throws (1: Sh_ldsNotFoundException slnfe);
	list<Sh_lds> getDSh_lds (1: string c_ldi, 2: string name) throws (1: Sh_ldsNotFoundException slnfe);
	
	list <classifier.IntegerClassifier> GetKlasCpos2();
	list <classifier.StringClassifier>  GetKlasS_ot01(1: i32 cotd);
	list <classifier.StringClassifier>  GetKlasIsS_ot01(1: i32 cotd, 2: string organ);
	list <classifier.StringClassifier>  GetKlasMetS_ot01(1: i32 cotd, 2: string organ, 3: string isl, 4: i32 priznak);
	list <classifier.IntegerClassifier> GetKlasPopl();
	list <classifier.IntegerClassifier> GetKlasNapr();
	list <classifier.IntegerClassifier> GetKlasO00(1: i32 clpu);
	list <classifier.IntegerClassifier> GetKlasPrvO00(1: i32 clpu, 2: i32 pcod);
	list <classifier.IntegerClassifier> GetKlasN00();
	list <classifier.IntegerClassifier> GetKlasPrvN00(1: i32 pcod);
	list <classifier.IntegerClassifier> GetKlasM00();
	list <classifier.IntegerClassifier> GetKlasPrvM00(1: i32 pcod);
	list <classifier.IntegerClassifier> GetKlasOpl();
	list <classifier.IntegerClassifier> GetKlasArez();
	list <classifier.IntegerClassifier> GetKlasP0e1(1: i32 grupp);
	list <classifier.IntegerClassifier> GetKlasNoLabP0e1(1: i32 grupp);
	list <classifier.IntegerClassifier> GetKlasSvrach(1: i32 cpodr);
	list <classifier.IntegerClassifier> GetKlasAllSvrach(1: i32 cpodr);
	list <classifier.StringClassifier>  GetKlasNz1();
	list <classifier.StringClassifier>  GetShab_lds(1: string c_lds);
	list <classifier.IntegerClassifier> GetKlasKod_ter();
	list <classifier.IntegerClassifier> GetKlasNsipol(1: i32 kdate);

	string printLabGur(1:InputLG ilg) throws (1: kmiacServer.KmiacServerException kse); 
}

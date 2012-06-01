namespace java ru.nkz.ivcgzo.ldsThrift

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct ObInfIsl {
	 1: i32 npasp;
	 2: i32 nisl;
	 3: i32 kodotd;
	 4: optional i32 nprob;
	 5: string pcisl;
	 6: i16 cisl;
	 7: optional i64 datap;
	 8: optional i64 datav;
	 9: optional i32 prichina;
        10: optional i32 popl;
	11: optional i32 napravl;
	12: optional i32 naprotd;
	13: string fio;
	14: i32 vopl;
	15: string diag;
	16: i32 kodvr;
	17: optional i32 kodm;
	18: optional i32 kods;
	19: i64 dataz;
}

struct DiagIsl {
	 1: i32 npasp;
	 2: i32 nisl;
	 3: string kodisl;
	 4: optional i32 rez;
	 5: string anamnez;
	 6: string anastezi;
	 7: string model;
	 8: i16 kol;
	 9: string op_name;
        10: string rez_name;
	11: double stoim;
	12: string pcod_m;
}

struct LabIsl {
	 1: i32 npasp;
	 2: i32 nisl;
	 3: string cpok;
	 4: string zpok;
	 5: double stoim;
	 6: string pcod_m;
	 7: i32 pvibor;
}

struct S_ot01{
	1: i32 cotd;
	2: string pcod;
	3: string c_obst;
	4: string c_nz1;	
}


struct Patient {
	1: i32 npasp;
	2: string fam;
	3: string im;
	4: string ot;
	5: i64 datar;
	6: string poms_ser;
	7: string poms_nom;
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


service LDSThrift extends kmiacServer.KmiacServer {
	list<ObInfIsl> GetObInfIslt(1: i32 npasp);
	ObInfIsl GetIsl(1: i32 npasp) throws (1: IslNotFoundException ine);
	i32 AddIsl(1: ObInfIsl info)throws (1: IslExistsException iee);
	void UpdIsl(1: ObInfIsl info)throws (1: IslExistsException iee);
	void DelIsl(1: i32 nisl);

	list<DiagIsl> GetDiagIsl(1: i32 nisl);
	DiagIsl GetDIsl(1: i32 nisl)throws (1: DIslNotFoundException dine);
	void AddDIsl(1: DiagIsl di)throws (1: DIslExistsException diee);
	void UpdDIsl(1: DiagIsl di)throws (1: DIslExistsException diee);
	void DelDIsl(1: i32 nisl, 2: string kodisl);

	list<LabIsl> GetLabIsl(1: i32 nisl);
	LabIsl GetLIsl(1: i32 nisl)throws (1: LIslNotFoundException line);
	void AddLIsl(1: LabIsl li)throws (1: LIslExistsException liee);
	void UpdLIsl(1: LabIsl li)throws (1: LIslExistsException liee);
	void DelLIsl(1: i32 nisl, 2: string cpok);


	list<S_ot01> GetS_ot01(1: i32 cotd, 2: string pcod, 3: string c_nz1);
	S_ot01 GetSot01(1: i32 cotd, 2: string pcod, 3: string c_nz1) throws (1: S_ot01NotFoundException sone);
	void AddS_ot01(1: S_ot01 so)throws (1: S_ot01ExistsException soee);
	void UpdS_ot01(1: S_ot01 so)throws (1: S_ot01ExistsException soee);
	void DelS_ot01(1: i32 cotd, 2: string pcod, 3: string c_nz1);


    	list<Patient> getPatient(1: Patient pat) throws (1: PatientNotFoundException pnfe);
	
	list<Metod> getMetod(1: i32 c_p0e1; 2: string pcod; 3: string pcod_m) throws (1: MetodNotFoundException mnfe);
	
	list<N_ldi> getN_ldi(1: string c_nz1; 2: i32 c_p0e1) throws (1: LdiNotFoundException lnfe);

	list <classifier.IntegerClassifier> GetKlasCpos2();
	list <classifier.IntegerClassifier> GetKlasPopl();
	list <classifier.IntegerClassifier> GetKlasNapr();
	list <classifier.IntegerClassifier> GetKlasO00(1: i32 clpu);
	list <classifier.IntegerClassifier> GetKlasN00();
	list <classifier.IntegerClassifier> GetKlasM00();
	list <classifier.IntegerClassifier> GetKlasOpl();
	list <classifier.IntegerClassifier> GetKlasArez();
	list <classifier.IntegerClassifier> GetKlasP0e1();
	list <classifier.StringClassifier> GetKlasNz1();
}

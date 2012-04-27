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

struct KlasM00 {
	1: i32 pcod;
	2: string name;
	3: string pr;
	}


/*
*Исследование уже существует
*/
exception IslExistsException {
}


/**
 * исследование с такими данными не найдено.
 */
exception IslNotFoundException {
}

/*
*Диагностическое исследование уже существует
*/
exception DIslExistsException {
}


/**
 * диагностическое исследование с такими данными не найдено.
 */
exception DIslNotFoundException {
}

/*
*Лабораторные исследование уже существует
*/
exception LIslExistsException {
}


/**
 * Лабораторное исследование с такими данными не найдено.
 */
exception LIslNotFoundException {
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

	list <classifier.IntegerClassifier> GetKlasCpos2();
	list <classifier.IntegerClassifier> GetKlasPopl();
	list <classifier.IntegerClassifier> GetKlasNapr();
	list <classifier.IntegerClassifier> GetKlasO00(1: i32 clpu);
	list <classifier.IntegerClassifier> GetKlasN00();
	list <KlasM00> GetKlasM00(1: string pr);
	list <classifier.IntegerClassifier> GetKlasOpl();
	list <classifier.IntegerClassifier> GetKlasArez();
}

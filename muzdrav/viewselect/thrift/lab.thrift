namespace java ru.nkz.ivcgzo.thriftLab

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Patient {
	1: optional i32 id;
	2: optional string surname;
	3: optional string name;
	4: optional string middlename;
	5: optional i32 idGosp;
}

/*Список показателей исследований по выбранному методу*/
struct PokazMet {
	1: optional string pcod;
	2: optional string nameN;
	3: optional bool vybor;
}

struct Napr {
	1: i32 id;
	2: optional i32 pVizit;
	3: optional i32 vidDoc;
	4: optional string text;
	5: optional i32 preds;
	6: optional i32 zaved;
	7: optional i32 idGosp;
}

/*метод по виду исследования*/
struct Metod {
	1: optional string obst;
	2: optional string nameObst;
	3: optional i32 cP0e1;
	//4: string pcod;
}

/*Список показателей исследований по выбранной системе*/
struct Pokaz {
	1: optional string pcod;
	2: optional string nameN;
	3: optional double stoim;
	4: optional i32 cP0e1;
	5: optional string cNnz1;
	6: optional bool vybor;
}

struct Pisl {
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
	11: optional i32 kodotd;
	12: optional i32 pvizitId;
	13: optional i32 idGosp;
}

struct PrezD {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string kodisl;
}

struct PrezL {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 nisl;
	4: optional string cpok;
}

struct Gosp {
	1: optional i32 idGosp;
	2: optional i32 npasp;
	3: optional i32 cotd;
	4: optional string cotd_name;
	5: optional i64 datap;
	6: optional i64 datav;
	7: optional string ishod;
	8: optional string result;
	9: optional i32 vrach;
	10: optional string vrach_fio;
}

struct Isl{
	1: optional i32 nisl;
	2: optional i32 cisl;
	3: optional string name_cisl;
	4: optional string pokaz;
	5: optional string pokaz_name;
	6: optional string rez;
	7: optional i64 datav;
	8: optional string op_name;
	9: optional string rez_name;
}

service ThriftLab extends kmiacServer.KmiacServer {
	list<classifier.IntegerClassifier> getVidIssled() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLabs(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getOrgAndSys(1: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	/*Исследования*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string cNnz1, 2: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 addPisl(1: Pisl npisl) throws (1: kmiacServer.KmiacServerException kse);
	i32 addPrezd(1: PrezD di) throws (1: kmiacServer.KmiacServerException kse);
	i32 addPrezl(1: PrezL li) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getPoliclinic() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getStacionarTypes() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLpu() throws (1: kmiacServer.KmiacServerException kse);
	i32 addNapr(1: Napr napr) throws (1: kmiacServer.KmiacServerException kse);
	list<Gosp> getGospList(1:i32 npasp, 2:i64 dateStart, 3: i64 dateEnd) throws (1: kmiacServer.KmiacServerException kse);
	list<Isl> getIslList(1: i32 gospId) throws (1: kmiacServer.KmiacServerException kse);
}

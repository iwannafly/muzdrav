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

/*������ ����������� ������������ �� ���������� ������*/
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

/*����� �� ���� ������������*/
struct Metod {
	1: optional string obst;
	2: optional string nameObst;
	3: optional i32 cP0e1;
	//4: string pcod;
}

/*������ ����������� ������������ �� ��������� �������*/
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
	5: optional i32 clpu;
	6: optional string clpu_name;
	7: optional i64 datap;
	8: optional i64 datav;
	9: optional string ishod;
	10: optional string result;
	11: optional i32 vrach;
	12: optional string vrach_fio;
}

service ThriftLab extends kmiacServer.KmiacServer {
	list<classifier.IntegerClassifier> getVidIssled() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLabs(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getOrgAndSys(1: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	/*������������*/
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
}

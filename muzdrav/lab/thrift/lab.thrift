namespace java ru.nkz.ivcgzo.thriftLab

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Patient {
	1: optional i32 id;
	2: optional string surname;
	3: optional string name;
	4: optional string middlename;
}

/*—писок показателей исследований по выбранному методу*/
struct PokazMet{
	1: optional string pcod;
	2: optional string nameN;
	3: optional bool vybor;
}

/*метод по виду исследовани€*/
struct Metod{
	1: optional string obst;
	2: optional string nameObst;
	3: optional i32 cP0e1;
	//4: string pcod;
}

/*—писок показателей исследований по выбранной системе*/
struct Pokaz{
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
	11: optional i32 pvizitId;
	12: optional i32 gospId;
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

service ThriftLab extends kmiacServer.KmiacServer {
	list<classifier.IntegerClassifier> getVidIssled() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLabs(1: i32 clpu) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getOrgAndSys(1: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	/*»сследовани€*/
	list<Metod> getMetod(1: i32 kodissl) throws (1: kmiacServer.KmiacServerException kse);
	list<PokazMet> getPokazMet(1: string cNnz1, 2: i32 cotd) throws (1: kmiacServer.KmiacServerException kse);
	list<Pokaz> getPokaz(1: i32 kodissl, 2: string kodsyst) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPisl(1: Pisl npisl) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezd(1: PrezD di) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPrezl(1: PrezL li) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getPoliclinic() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getStacionarTypes() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLpu() throws (1: kmiacServer.KmiacServerException kse);
}

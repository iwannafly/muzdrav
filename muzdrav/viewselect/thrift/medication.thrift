namespace java ru.nkz.ivcgzo.thriftMedication

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Patient {
	1: optional i32 id;
	2: optional string surname;
	3: optional string name;
	4: optional string middlename;
	5: optional i32 idGosp;
}

struct Lek {
	1: optional i32 nlek;
	2: optional i32 idGosp;
	3: optional i32 vrach;
	4: optional i64 datan;
	5: optional i32 klek;
	6: optional string flek;
	7: optional i32 doza;
	8: optional i32 ed;
	9: optional i32 sposv;
	10: optional i32 spriem;
	11: optional i32 pereod;
	12: optional i32 dlitk;
	13: optional string komm;
	14: optional i64 datao;
	15: optional i32 vracho;
	16: optional i64 dataz;
}

struct LekPriem {
	1: optional i32 id;
	2: optional i32 nlek;
	3: optional i64 date;
	4: optional i64 timep;
	5: optional bool status;
}

service ThriftMedication extends kmiacServer.KmiacServer {
	list<classifier.IntegerClassifier> getMedications()
		throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getMedicationsUsingTemplate(1:string template)
		throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getMedicationForms(1:i32 medId)
		throws (1: kmiacServer.KmiacServerException kse);
	list<LekPriem> getLekPriem(1:i32 nlek)
		throws (1: kmiacServer.KmiacServerException kse);
	void addLekPriem(1: list<LekPriem> lekPriems)
		throws (1: kmiacServer.KmiacServerException kse);
	void deleteLekPriem(1: list<LekPriem> lekPriems)
		throws (1: kmiacServer.KmiacServerException kse);
	void changeLekPriemStatus(1: i32 id, 2: bool status)
		throws (1: kmiacServer.KmiacServerException kse);	
	Lek getLek(1: i32 nlek)
		throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getLekShortList(1: i32 idGosp)
		throws (1: kmiacServer.KmiacServerException kse);
	i32 addLek(1: Lek lek) throws (1: kmiacServer.KmiacServerException kse);
	void deleteLek(1: i32 nlek) throws (1: kmiacServer.KmiacServerException kse);	
	list<classifier.IntegerClassifier> getPeriods()
		throws (1: kmiacServer.KmiacServerException kse);
}

namespace java ru.nkz.ivcgzo.thriftHospital

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct TMedication {
	1: optional i32 nlek;
	2: optional string namelek;
	3: optional i32 idGosp;
	4: optional i32 vrach;
	5: optional i64 datan;
	6: optional i32 klek;
	7: optional string flek;
	8: optional i32 doza;
	9: optional i32 ed;
	10: optional i32 sposv;
	11: optional i32 spriem;
	12: optional i32 pereod;
	13: optional i32 dlitk;
	14: optional string komm;
	15: optional i64 datao;
	16: optional i32 vracho;
	17: optional i64 dataz;
}

struct TLekPriem {
	1: optional i32 id;
	2: optional i32 nlek;
	3: optional i64 date;
	4: optional i64 timep;
	5: optional bool status;
}

struct TDiagnostic {
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

struct TDiet {
}

struct TProcedures {
}

service ThriftHospital extends kmiacServer.KmiacServer {
	list<TMedication> getMedications(1: i32 idGosp)
		throws (1: kmiacServer.KmiacServerException kse);
}

namespace java ru.nkz.ivcgzo.thriftServerAutoProc

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"


/*информация о льготнике из базы*/
struct Patient {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string fam;
	4: optional string im;
	5: optional string ot;
	6: optional i32 pol;
	7: optional i64 datar;
	8: optional i32 poms_tdoc;
	9: optional string poms_ser;
	10: optional string poms_nom;
	11: optional i32 tdoc;
	12: optional string docser;
	13: optional string docnum;
	14: optional i64 datadoc;
	15: optional string snils;
	16: optional string adp_obl;
	17: optional string adp_raion;
	18: optional string adp_gorod;
	19: optional string adp_ul;
	20: optional string adp_dom;
	21: optional string adp_korp;
	22: optional string adp_kv;
}

struct Lgota {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 lgot;
	4: optional i64 datal;
	5: optional i32 gri;
	6: optional i32 sin;
	7: optional i32 pp;
	8: optional i64 drg;
	9: optional i64 dot;
	10: optional i32 obo;
	11: optional string ndoc;
}

/** 
 *пациент не найден
 */
exception PatientNotFoundException {
}
/**
 *не найдена льготная категория
 */
exception LgkatNotFoundException {
}

service ThriftServerAutoProc extends kmiacServer.KmiacServer {

/**
 * поиск льготника в таблице Patient
 */
	Patient getPatientInfo(1:i32 npasp) throws (1: PatientNotFoundException le); 

/**
 * корректировка информации (снилса) пациента
 */
	i32 setPatientInfo(1: Patient npasp);

/**
 * поиск льготы в таблице P_kov
 */ 
	list<Lgota> getLgotaInfo(1:i32 npasp) throws (1: LgkatNotFoundException le);

/**
 * добавление льготы 
 */
	i32 addLgotaInfo(1:Lgota npasp);

/**
 * выбирает информацию о льготнике и корректирует информацию в базе
 */
	string getPL(1:string pl) throws (1:kmiacServer.KmiacServerException kse);

}

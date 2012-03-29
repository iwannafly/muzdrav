namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
 * Запись на пациента в регистратуре
 */
struct ZapVr{
	1: i32 npasp;
	2: i32 vid_p; /*вид приема-первичность*/
	3: i64 timepn; /*время начала приема*/
	4: string fam;
	5: string im;
	6: string oth;
	7: string serpolis;
	8: string nompolis;
}

struct Pvizit {
	1: i32 id;
	2: i32 npasp;
	3: i32 idzab;
	4: i32 idvr;
	5: string cdol;
	6: i64 datap;
	7: string cobr;
	8: i32 ish;
	9: i32 rez_t;
	10: i32 talon;
	11: string ztext;
	12: i32 cod_sp;
}

struct PvizitAmb {
	1: i32 id;
	2: i32 npasp;
	3: i32 idzab;
	4: i32 idpos;
	5: i64 datap;
	6: i32 cod_sp;
	7: i32 rez;
	8: i32 opl;
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap);
	i32 AddPvizit(1: Pvizit vizit);
	void UpdatePvizit(1: Pvizit vizit);
	void DeletePvizit(1: i32 zab);
	i32 AddPvizitAmb(1: PvizitAmb pos);
	void UpdatePvizitAmb(1: PvizitAmb pos);
	void DeletePvizitAmb(1: i32 pos);
	list<classifier.IntegerClassifier> get_n_cpos();
}

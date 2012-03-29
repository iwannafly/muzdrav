namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"

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
	1: i32 npasp;
	2: i32 idzab;
	3: i32 idvr;
	4: string cdol;
	5: i64 datap;
	6: string cobr;
	7: i32 ish;
	8: i32 rez_t;
	9: i32 talon;
	10: string ztext;
	11: i32 cod_sp;
}

struct PvizitAmb {
	1: i32 npasp;
	2: i32 idzab;
	3: i32 idpos;
	4: i64 datap;
	5: i32 cod_sp;
	6: i32 rez;
	7: i32 opl;
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap);
	void AddPvizit(1: i32 zab);
	void UpdatePvizit(1: i32 zab);
	void DeletePvizit(1: i32 zab);
	void AddPvizitAmb(1: i32 pos);
	void UpdatePvizitAmb(1: i32 pos);
	void DeletePvizitAmb(1: i32 pos);
}

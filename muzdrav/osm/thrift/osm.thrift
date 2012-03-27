namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"

/**
 * Запись на пациента в регистратуре
 */
struct ZapVr{
	1: i32 npasp;
	2: i32 idvr;
	3: string cdol;
	4: string fio_vr;
	5: i32 vid_p; /*вид приема-первичность*/
	6: i64 timepn; /*время начала приема*/
	7: i64 datapt; /*Дата и время приема*/
	8: string fam;
	9: string im;
	10: string oth;
	11: string cpos;
	12: string serpolis;
	13: string nompolis;
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap);
}

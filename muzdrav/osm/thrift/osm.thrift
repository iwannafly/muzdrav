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
	13: i32 cuser;
	14: i64 dataz;
	/**16: */
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
	9: string cdol;
	10: i32 mobs;
	11: i32 stoim;
	12: string diag; 
}

struct PdiagAmb {
	1: i32 id;
	2: i32 npasp;
	3: i32 idzab;
	4: i64 datrg;
	5: string diag;
	6: string named;/*медицинское описание д-за*/
	7: i32 cod_sp;
	8: string obstreg;
	9: i64 datap;
	10: i64 dataot;
	11: i32 obstot;
	12: i32 codsp_ot;
	13: string cdol_ot;
	14: i32 vid_tr;
	15: i32 prizn;
	16: i32 priznp; 
}

struct Psign{
	1: i32 id;
	2: i32 npasp;
	3: string grup;
	4: string rezus;
	5: string allerg;
	6: string farmkol;
	7: string vitae;
	8: string vred;
}

struct Priem{
	1: i32 id;
	2: i32 npasp;
	3: i32 idzab;
	4: i32 idpos;
	5: string osmotr;
	6: i32 sl_ob;
	7: i32 n_is;
	8: i32 n_kons;
	9: i32 n_proc;
	10: i32 n_lek;
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
	i32 AddPdiagAmb(1: PdiagAmb diag);
	void UpdatePdiagAmb(1: PdiagAmb diag);
	void DeletePdiagAmb(1: i32 diag);
	i32 AddPsign(1: Psign sign);
	void UpdatePsign(1: Psign sign);
	void DeletePsign(1: i32 sign);
	i32 AddPriem(1: Priem pr);
	void UpdatePriem(1: Priem pr);
	void DeletePriem(1: i32 pr);

	list<classifier.IntegerClassifier> get_n_cpos();
}

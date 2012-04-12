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
	3: i32 cpol;
	4: i32 cobr;
	5: i64 datao;
	6: i32 ishod;
	7: i32 rezult;
	8: i32 talon;
	9: i32 cod_sp;
	10: string cdol;
	11: i32 cuser;
	12: string ztext;
	13: i64 dataz;
	14: i32 idzab;
}

struct PvizitAmb {
	1: i32 id;
	2: i32 id_obr;
	3: i32 npasp;
	4: i64 datap;
	5: i32 cod_sp;
	6: string cdol;
	7: string diag; 
	8: i32 mobs;
	9: i32 rezult;
	10: i32 opl;
	11: double stoim;
	12: i32 uet;
	13: i64 datak;
	14: i32 kod_rez;
	15: i32 k_lr;
	16: i32 n_sp;
	17: i32 pr_opl;
	18: i32 pl_extr;
	19: i32 vpom;
}

struct PdiagAmb {
	1: i32 id;
	2: i32 npasp;
	3: i32 idzab;
	4: i64 datrg;
	5: string diag;
	6: string named;/*медицинское описание д-за*/
	7: i32 cod_sp;
	8: i32 obstreg;
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
	1: i32 npasp;
	2: string grup;
	3: string ph;
	4: string allerg;
	5: string farmkol;
	6: string vitae;
	7: string vred;
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

struct PdiagZ{
	1: i32 id;
	2: i32 npasp;
	3: string diag;
	4: string named;
	5: i64 datad;
	6: i32 nmvd;
}

exception PvizitNotFoundException {
}

exception PvizitAmbNotFoundException {
}

exception PdiagAmbNotFoundException {
}

exception PsignNotFoundException {
}

exception PriemNotFoundException {
}

/**
 * 
 */
service ThriftOsm extends kmiacServer.KmiacServer {
	/**
	 * Получение списка записанных на прием на заданную дату.
	 */
	list<ZapVr> getZapVr(1: i32 idvr, 2: string cdol, 3: i64 datap) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	Pvizit getPvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse, 2: PvizitNotFoundException pne);
	void UpdatePvizit(1: Pvizit obr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizit(1: i32 obrId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	PvizitAmb getPvizitAmb(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse, 2: PvizitAmbNotFoundException pne);
	void UpdatePvizitAmb(1: PvizitAmb pos) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePvizitAmb(1: i32 posId) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	PdiagAmb getPdiagAmb(1: i32 diagId) throws (1: kmiacServer.KmiacServerException kse, 2: PdiagAmbNotFoundException dne);
	void UpdatePdiagAmb(1: PdiagAmb diag) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePdiagAmb(1: i32 diagId) throws (1: kmiacServer.KmiacServerException kse);

	Psign getPsign(1: i32 signId) throws (1: kmiacServer.KmiacServerException kse, 2: PsignNotFoundException sne);
	void setPsign(1: Psign sign) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPriem(1: Priem pr) throws (1: kmiacServer.KmiacServerException kse);
	Priem getPriem(1: i32 prId) throws (1: kmiacServer.KmiacServerException kse, 2: PriemNotFoundException pne);
	void UpdatePriem(1: Priem pr) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePriem(1: i32 prId) throws (1: kmiacServer.KmiacServerException kse);

	void AddPdiagZ(1: PdiagZ dz) throws (1: kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> get_n_cpos() throws (1: kmiacServer.KmiacServerException kse);
}

namespace java ru.nkz.ivcgzo.thriftMss

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct P_smert{
	1:i32 id,
	2:i32 npasp,
	3:i32 ser,
	4:i32 nomer,
	5:i32 vid,
	6:i64 datav,
	7:i64 datas,
	8:string vrems,
	9:string ads_obl,
	10:string ads_raion,
	11:string ads_gorod,
	12:string ads_ul,
	13:string ads_dom,
	14:string ads_korp,
	15:string ads_kv,
	16:i32 ads_mestn	
	17:i32 nastupila,
	18:i32 semp,
	19:i32 obraz,
	20:i32 zan,
	21:i32 proiz,
	22:i64 datatr,
	23:i32 vid_tr,
	24:string obst,
	25:i32 ustan,
	26:i32 cvrach,
	27:string cdol,
	28:i32 osn,
	29:string psm_a,
	30:string psm_an,
	31:i32 psm_ak,
	32:string psm_ad,
	33:string psm_b,
	34:string psm_bn,
	35:i32 psm_bk,
	36:string psm_bd,
	37:string psm_v,
	38:string psm_vn,
	39:i32 psm_vk,
	40:string psm_vd,
	41:string psm_g,
	42:string psm_gn,
	43:i32 psm_gk,
	44:string psm_gd,
	45:string psm_p,
	46:string psm_pn,
	47:i32 psm_pk,
	48:string psm_pd,
	49:string psm_p1,
	50:string psm_p1n,
	51:i32 psm_p1k,
	52:string psm_p1d,
	53:string psm_p2,
	54:string psm_p2n,
	55:i32 psm_p2k,
	56:string psm_p2d,
	57:i32 dtp,
	58:i32 umerla,
	59:i32 cuser,
	60:i32 clpu,
	61:i32 fio_r,
	62:i32 don,
	63:i32 ves,
	64:i32 nreb,
	65:string mrojd,
	66:string fam_m,
	67:string im_m,
	68:string ot_m,
	69:i64 datarm,
	70:i64 dataz,
	71:string fio_pol,
	72:string sdok,
	73:string ndok,
	74:i64 dvdok,
	75:string kvdok,
	76:string gpol,
	77:string upol,
	78:string dpol,
	79:string kpol,
        80:i32 vz_ser,
        81:i32 vz_nomer,
        82:i64 vz_datav,
	83:i32 vdok,
	84:string vrem_tr
}

struct PatientCommonInfo {
	 1: i32 npasp,
	 2: string fam,
	 3: string im,
	 4: string ot,
	 5: i64 datar,
	 6: i32 pol,
	 7: string adm_obl,
	 8: string adm_gorod,
	 9: string adm_ul,
	10: string adm_dom,
	11: string adm_korp,
	12: string adm_kv,
	13: string mrab,
	14: string name_mr	
}
struct Psmertdop {
	 1: i32 cpodr,
	 2: i32 cslu,
	 3: bool prizn,
	 4: i32 nomer_n,
	 5: i32 nomer_k,
	 6: i32 nomer_t
	 
}

/**
 * пациент не зарегистрирован в базе
 */
exception PatientNotFoundException {
}

/**
 * запись на данного пациента отсутствует
 */
exception MssNotFoundException {
}

/**
 * информация диапазона номеров мед. свидетельства отсутствует
 */
exception MssdopNotFoundException {
}

service ThriftMss extends kmiacServer.KmiacServer {
/**
 * выбор записи из таблицы
 */
	P_smert getPsmert(1:i32 npasp) throws (1: MssNotFoundException sne);

/**
 * ввод или корректировка информации медицинского свидетельства
 */
	i32 setPsmert(1: P_smert npasp);

/**
 * удаление записи
 */
	void delPsmert(1: P_smert npasp);

/**
 * возвращает сведения о пациенте для печати мед.свидетельства
 */
	PatientCommonInfo getPatientCommonInfo(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse,2:PatientNotFoundException pnf);

/**
 * выбор записи из таблицы дополнительной информации
 */
	Psmertdop getPsmertdop(1:i32 cpodr) throws (1: MssdopNotFoundException sdne);

/**
 * ввод или корректировка диапазона номеров мед. свидетельства
 */
	i32 setPsmertdop(1: Psmertdop cpodr);


	list<classifier.IntegerClassifier> get_n_z60() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z10() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z42() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z43() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z70() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_r0l() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z01() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z80() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_z90() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_u50() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_p07() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> get_n_az0() throws (1: kmiacServer.KmiacServerException kse);
}

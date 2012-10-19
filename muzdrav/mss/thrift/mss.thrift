namespace java ru.nkz.ivcgzo.thriftMss

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct P_smert{
	1: optional i32 id,
	2: optional i32 npasp,
	3: optional i32 ser,
	4: optional i32 nomer,
	5: optional i32 vid,
	6: optional i64 datav,
	7: optional i64 datas,
	8: optional i64 vrems,
	9: optional string ads_obl,
	10: optional string ads_raion,
	11: optional string ads_gorod,
	12: optional string ads_ul,
	13: optional string ads_dom,
	14: optional string ads_korp,
	15: optional string ads_kv,
	16: optional i32 ads_mestn	
	17: optional i32 nastupila,
	18: optional i32 semp,
	19: optional i32 obraz,
	20: optional i32 zan,
	21: optional i32 proiz,
	22: optional i64 datatr,
	23: optional i32 vid_tr,
	24: optional i64 vrem_tr,
	25: optional string obst,
	26: optional i32 ustan,
	27: optional i32 cvrach,
	28: optional string cdol,
	29: optional i32 osn,
	30: optional string psm_a,
	31: optional string psm_an,
	32: optional i32 psm_ak,
	33: optional i32 psm_ad,
	34: optional string psm_b,
	35: optional string psm_bn,
	36: optional i32 psm_bk,
	37: optional i32 psm_bd,
	38: optional string psm_v,
	39: optional string psm_vn,
	40: optional i32 psm_vk,
	41: optional i32 psm_vd,
	42: optional string psm_g,
	43: optional string psm_gn,
	44: optional i32 psm_gk,
	45: optional i32 psm_gd,
	46: optional string psm_p,
	47: optional string psm_pn,
	48: optional i32 psm_pk,
	49: optional i32 psm_pd,
	50: optional string psm_p1,
	51: optional string psm_p1n,
	52: optional i32 psm_p1k,
	53: optional i32 psm_p1d,
	54: optional string psm_p2,
	55: optional string psm_p2n,
	56: optional i32 psm_p2k,
	57: optional i32 psm_p2d,
	58: optional i32 dtp,
	59: optional i32 umerla,
	60: optional i32 cuser,
	61: optional i32 clpu,
	62: optional string fio_r,
	63: optional i32 don,
	64: optional i32 ves,
	65: optional i32 nreb,
	66: optional string mrojd,
	67: optional string fam_m,
	68: optional string im_m,
	69: optional string ot_m,
	70: optional i64 datarm,
	71: optional i64 dataz,
	72: optional string fio_pol,
	73: optional i32 vdok,
	74: optional string sdok,
	75: optional string ndok,
	76: optional i64 dvdok,
	77: optional string kvdok,
	78: optional i32 vz_ser,
        79: optional i32 vz_nomer,
        80: optional i64 vz_datav,
	81: optional string adm_raion
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
	13: i32 region_liv
}

struct PatientMestn {
	1: i32 vid_np,
	2: i32 c_ffoms,
	3: string nam_kem
}

struct Psmertdop {
	 1: optional i32 id,
	 2: optional i32 cpodr,
	 3: optional i32 cslu,
	 4: optional i32 clpu,
	 5: optional bool prizn,
	 6: optional i32 nomer_n,
	 7: optional i32 nomer_k,
	 8: optional i32 nomer_t,
	 9: optional string fam,
	10: optional string im,
	11: optional string ot		
	 
}

struct UserFio {
	 1: i32 pcod,
	 2: string fam,
	 3: string im,
	 4: string ot
		 
}

/**
 * пользователь не найден
 */
exception UserNotFoundException {
}

/**
 * пациет не зарегистрирован в базе
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

/**
 * населенный пункт не найден
 */
exception MestnNotFoundException {
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
	void delPsmert(1: i32 npasp);

/**
 * возвращает сведения о пациенте для печати мед. свидетельства
 */
	PatientCommonInfo getPatientCommonInfo(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse,2:PatientNotFoundException pnf);

/**
 * выбор записи из таблицы дополнительной информации
 */
	Psmertdop getPsmertdop(1:i32 cpodr,2:i32 cslu,3:i32 clpu) throws (1: MssdopNotFoundException sdne);

/**
 * определение местности проживания
 */
	PatientMestn getL00(1:i32 c_ffoms, 2: string name) throws (1: MestnNotFoundException mstn);	

/**
 * добавляет или корректирует запись таблицы дополнительной информации
 */
	i32 setPsmertdop(1: Psmertdop cpodr);
	

/**
 * заполнивший медицинское свидетельство о смерти
 */
	UserFio getUserFio(1:i32 pcod) throws (1: UserNotFoundException nfio);

	list<classifier.IntegerClassifier> get_n_z00() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> gets_vrach(1:i32 clpu, 2:i32 cslu, 3:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> gets_dolj(1:i32 clpu, 2:i32 cslu, 3:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);

/**
 * печать корешка медицинского свидетельства и медицинского свидетельства о смерти
 */
	string printMedSS(1: string docInfo) throws (1: kmiacServer.KmiacServerException kse);
}

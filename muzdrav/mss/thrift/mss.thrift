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
	16:string ads_mestn	
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
	74:string dvdok,
	75:string kvdok,
	76:string gpol,
	77:string upol,
	78:string dpol,
	79:string kpol
}
/**
 * запись на данного пациента отсутствует
 */
exception MssNotFoundException {
}

service ThriftMss extends kmiacServer.KmiacServer {
/**
 * выбор записи из таблицы
 */
	P_smert getPsmert(1:i32 npasp) throws (1: MssNotFoundException sne);

/**
 * ввод информации
 */
	i32 addPsmert(1: P_smert npasp);

/**
 * корректировка (изменение) информации 
 */
	void UpdPsmert(1: P_smert npasp);

/**
 * удаление записи
 */
	void delPsmert(1: P_smert npasp);
}

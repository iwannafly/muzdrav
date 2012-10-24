namespace java ru.nkz.ivcgzo.thriftDisp

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct Pfiz{
	1: optional i32 npasp;
	2: optional i32 pe;
	3: optional i32 pp;
	4: optional i32 pi;
	5: optional i32 fv;
	6: optional i32 fr;
	7: optional i32 grzd;
	8: optional i32 prb;
	9: optional i32 prk;
	10: optional double ves;
	11: optional double rost;
	12: optional i64 dataz;
	13: optional string vrk;
	14: optional string pfm1;
	15: optional string pfm2;
	16: optional string pfm3;
	17: optional string pfd1;
	18: optional string pfd2;
	19: optional string pfd3;
	20: optional string pfd4;
	21: optional string pfd5;
	22: optional string pfd6;
	23: optional i32 prs;
	24: optional i32 priv;
	25: optional i32 priv_pr;
	26: optional i32 priv_n;
	27: optional i32 bcg;
	28: optional i32 polio;
	29: optional i32 akds;
	30: optional i32 adsm;
	31: optional i32 adm;
	32: optional i32 kor;
	33: optional i32 parotit;
	34: optional i32 krasn;
	35: optional i32 gepatit;
	36: optional i32 bcg_vr;
	37: optional i32 polio_vr;
	38: optional i32 akds_vr;
	39: optional i32 kor_vr;
	40: optional i32 parotit_vr;
	41: optional i32 krasn_vr;
	42: optional i32 gepatit_vr;
	43: optional i64 dat_p;
	44: optional i32 profil;
	45: optional i32 vedom;
	46: optional i32 vib1;
	47: optional i32 vib2;
	48: optional i32 ipr;
	49: optional i64 dat_ipr;
	50: optional i32 menses;
	51: optional i32 menses1;
	52: optional string okr;
	53: optional i32 pf1;
	54: optional i32 mf1;
	55: optional i32 ef1;
	56: optional i32 rf1;
	57: optional i32 grzd_s;
}

struct Pdisp_ds{
	1: optional i32 npasp;
	2: optional i32 id;
	3: optional i32 d_do;
	4: optional string diag_do;
	5: optional i32 obs_n;
	6: optional i32 obs_v;
	7: optional i32 lech_n;
	8: optional i32 lech_v;
	9: optional i32 d_po;
	10: optional string diag_po;
	11: optional i32 xzab;
	12: optional i32 pu;
	13: optional i32 disp;
	14: optional i32 vmp1;
	15: optional i32 vmp2;
	16: optional i32 vrec1;
	17: optional i32 vrec2;
	18: optional i32 vrec3;
	19: optional i32 vrec4;
	20: optional i32 vrec5;
	21: optional i32 vrec6;
	22: optional i32 vrec7;
	23: optional i32 vrec8;
	24: optional i32 vrec9;
	25: optional i32 vrec10;
	26: optional i32 nrec1;
	27: optional i32 nrec2;
	28: optional i32 nrec3;
	29: optional i32 nrec4;
	30: optional i32 nrec5;
	31: optional i32 nrec6;
	32: optional i32 nrec7;
	33: optional i32 nrec8;
	34: optional i32 nrec9;
	35: optional i32 nrec10;
	36: optional i64 dataz;
	37: optional i32 recdop1;
	38: optional i32 recdop2;
	39: optional i32 recdop3;
	40: optional i32 prizn_do;
	41: optional i32 prizn_po;
}


//русские буквы
service ThriftDisp extends kmiacServer.KmiacServer {
	void setPfiz(1: Pfiz fiz) throws (1: kmiacServer.KmiacServerException kse);
	
	
}

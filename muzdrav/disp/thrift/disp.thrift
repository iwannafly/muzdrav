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

struct Pdisp_ds_do{
	1: optional i32 npasp;
	2: optional i32 id;
	3: optional i32 d_do;
	4: optional string diag_do;
	5: optional i32 obs_n;
	6: optional i32 obs_v;
	7: optional i32 lech_n;
	8: optional i32 lech_v;
	9: optional i64 dataz;
	10: optional string nameds;
}

struct Pdisp_ds_po{
	1: optional i32 npasp;
	2: optional i32 id;
	3: optional i32 d_po;
	4: optional string diag_po;
	5: optional i32 xzab;
	6: optional i32 pu;
	7: optional i32 disp;
	8: optional i32 vmp1;
	9: optional i32 vmp2;
	10: optional i32 vrec1;
	11: optional i32 vrec2;
	12: optional i32 vrec3;
	13: optional i32 vrec4;
	14: optional i32 vrec5;
	15: optional i32 vrec6;
	16: optional i32 vrec7;
	17: optional i32 vrec8;
	18: optional i32 vrec9;
	19: optional i32 vrec10;
	20: optional i32 nrec1;
	21: optional i32 nrec2;
	22: optional i32 nrec3;
	23: optional i32 nrec4;
	24: optional i32 nrec5;
	25: optional i32 nrec6;
	26: optional i32 nrec7;
	27: optional i32 nrec8;
	28: optional i32 nrec9;
	29: optional i32 nrec10;
	30: optional i64 dataz;
	31: optional i32 recdop1;
	32: optional i32 recdop2;
	33: optional i32 recdop3;
	34: optional string nameds;
}

struct PatientInfo{
	1: optional i32 npasp;
	2: optional string fam;
	3: optional string im;
	4: optional string ot;
	5: optional i64 datar;
	6: optional string poms_ser;
	7: optional string poms_nom;
	8: optional i32 pol;
}

exception PfizNotFoundException {
}

exception PdispdoNotFoundException {
}


exception PdisppoNotFoundException {
}


//русские буквы
service ThriftDisp extends kmiacServer.KmiacServer {
	void setPfiz(1: Pfiz fiz) throws (1: kmiacServer.KmiacServerException kse);
	Pfiz getPfiz(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PfizNotFoundException pfnfe);
	PatientInfo getPatientInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);

	i32 AddPdispds_do(1: Pdisp_ds_do pds_do) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePdispds_do(1: Pdisp_ds_do pds_do) throws (1: kmiacServer.KmiacServerException kse);
	list<Pdisp_ds_do> getTblDispds_do(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp_ds_do getDispds_do(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteDispds_do(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp_ds_do getDispds_d_do(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PdispdoNotFoundException pddnfe);


	i32 AddPdispds_po(1: Pdisp_ds_po pds_po) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePdispds_po(1: Pdisp_ds_po pds_po) throws (1: kmiacServer.KmiacServerException kse);
	list<Pdisp_ds_po> getTblDispds_po(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp_ds_po getDispds_po(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteDispds_po(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	Pdisp_ds_po getDispds_d_po(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PdisppoNotFoundException pdpnfe);

//classifiers
	list<classifier.IntegerClassifier> get_n_prf() throws (1: kmiacServer.KmiacServerException kse);

	
}

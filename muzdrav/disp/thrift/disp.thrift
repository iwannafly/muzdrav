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
	pfd2 character varying(10);
	pfd3 character varying(10);
	pfd4 character varying(10);
	pfd5 character varying(15);
	pfd6 character varying(30);
	prs integer;
	priv integer;
	priv_pr integer;
	priv_n integer;
	bcg integer;
	polio integer;
	akds integer;
	adsm integer;
	adm integer;
	kor integer;
	parotit integer;
	krasn integer;
	gepatit integer;
	bcg_vr integer;
	polio_vr integer;
	akds_vr integer;
	kor_vr integer;
	parotit_vr integer;
	krasn_vr integer;
	gepatit_vr integer;
	dat_p date;
	profil integer;
	vedom integer;
	vib1 integer;
	vib2 integer;
	ipr integer;
	dat_ipr date;
	menses integer;
	menses1 integer;
	okr character varying(5);
	pf1 integer;
	mf1 integer;
	ef1 integer;
	rf1 integer;
	grzd_s integer;
}


service ThriftDisp extends kmiacServer.KmiacServer {
	void SetPfiz(1: Pfiz fiz) throws (1: kmiacServer.KmiacServerException kse);
}

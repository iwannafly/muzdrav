namespace java ru.nkz.ivcgzo.thriftKartaRInv

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Pinvk {
    1:optional i32 npasp;
    2:optional i64 dataz;
    3:optional i64 datav;
    4:optional string vrach;
    5:optional i32 mesto1;
    6:optional i32 preds;
    7:optional string uchr;
    8:optional string nom_mse;
    9:optional string name_mse;
    10:optional string ruk_mse;
    11:optional i32 rez_mse;
    12:optional i64 d_osv;
    13:optional i64 d_otpr;
    14:optional i64 d_inv;
    15:optional i64 d_invp;
    16:optional i64 d_srok;
    17:optional i32 srok_inv;
    18:optional string diag;
    19:optional string diag_s1;
    20:optional string diag_s2;
    21:optional string diag_s3;
    22:optional string oslog;
    23:optional i32 factor;
    24:optional i32 fact1;
    25:optional i32 fact2;
    26:optional i32 fact3;
    27:optional i32 fact4;
    28:optional i32 prognoz;
    29:optional i32 potencial;
    30:optional i32 med_reab;
    31:optional i32 ps_reab;
    32:optional i32 prof_reab;
    33:optional i32 soc_reab;
    34:optional i32 zakl;
    35:optional string zakl_name;
    36:optional i32 klin_prognoz;
    37:optional i32 nar1;
    38:optional i32 nar2;
    39:optional i32 nar3;
    40:optional i32 nar4;
    41:optional i32 nar5;
    42:optional i32 nar6;
    43:optional i32 ogr1;
    44:optional i32 ogr2;
    45:optional i32 ogr3;
    46:optional i32 ogr4;
    47:optional i32 ogr5;
    48:optional i32 ogr6;
    49:optional i32 ogr7;
    50:optional i32 mr1n;
    51:optional i32 mr2n;
    52:optional i32 mr3n;
    53:optional i32 mr4n;
    54:optional i32 mr5n;
    55:optional i32 mr6n;
    56:optional i32 mr7n;
    57:optional i32 mr8n;
    58:optional i32 mr9n;
    59:optional i32 mr10n;
    60:optional i32 mr11n;
    61:optional i32 mr12n;
    62:optional i32 mr13n;
    63:optional i32 mr14n;
    64:optional i32 mr15n;
    65:optional i32 mr16n;
    66:optional i32 mr17n;
    67:optional i32 mr18n;
    68:optional i32 mr19n;
    69:optional i32 mr20n;
    70:optional i32 mr21n;
    71:optional i32 mr22n;
    72:optional i32 mr23n;
    73:optional i32 mr1v;
    74:optional i32 mr2v;
    75:optional i32 mr3v;
    76:optional i32 mr4v;
    77:optional i32 mr5v;
    78:optional i32 mr6v;
    79:optional i32 mr7v;
    80:optional i32 mr8v;
    81:optional i32 mr9v;
    82:optional i32 mr10v;
    83:optional i32 mr11v;
    84:optional i32 mr12v;
    85:optional i32 mr13v;
    86:optional i32 mr14v;
    87:optional i32 mr15v;
    88:optional i32 mr16v;
    89:optional i32 mr17v;
    90:optional i32 mr18v;
    91:optional i32 mr19v;
    92:optional i32 mr20v;
    93:optional i32 mr21v;
    94:optional i32 mr22v;
    95:optional i32 mr23v;
    96:optional string mr1d;
    97:optional string mr2d;
    98:optional string mr3d;
    99:optional string mr4d;
    100:optional i32 pr1n;
    101:optional i32 pr2n;
    102:optional i32 pr3n;
    103:optional i32 pr4n;
    104:optional i32 pr5n;
    105:optional i32 pr6n;
    106:optional i32 pr7n;
    107:optional i32 pr8n;
    108:optional i32 pr9n;
    109:optional i32 pr10n;
    110:optional i32 pr11n;
    111:optional i32 pr12n;
    112:optional i32 pr13n;
    113:optional i32 pr14n;
    114:optional i32 pr15n;
    115:optional i32 pr16n;
    116:optional i32 pr1v;
    117:optional i32 pr2v;
    118:optional i32 pr3v;
    119:optional i32 pr4v;
    120:optional i32 pr5v;
    121:optional i32 pr6v;
    122:optional i32 pr7v;
    123:optional i32 pr8v;
    124:optional i32 pr9v;
    125:optional i32 pr10v;
    126:optional i32 pr11v;
    127:optional i32 pr12v;
    128:optional i32 pr13v;
    129:optional i32 pr14v;
    130:optional i32 pr15v;
    131:optional i32 pr16v;
    132:optional string pr1d;
    133:optional i32 ninv;
}
struct PatientCommonInfo {
	 1: optional i32 npasp;
	 2: optional string fam;
	 3: optional string im;
	 4: optional string ot;
	 5: optional i64 datar;
	 6: optional string poms_ser;
	 7: optional string poms_nom;
	 8: optional i32 pol;
	 9: optional string adm_gorod;
	10: optional string adm_ul;
	11: optional string adm_dom;
	12: optional string adm_korp;
	13: optional string adm_kv;
	14: optional i32 poms_strg;
	
}


exception PinvkNotFoundException {
}
exception PatientNotFoundException {
}


service thriftKartaRInv extends kmiacServer.KmiacServer {


 Pinvk getPinvk(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse, 2: PinvkNotFoundException pinvknfe);
 list<Pinvk> getAllPinvk(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
 i32 setPinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);
 i32 addPinvk(1:Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);
 void DeletePinvk(1: i32 ninv) throws (1: kmiacServer.KmiacServerException kse);
PatientCommonInfo getPatientCommonInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);


//i32 AddPdispds_do(1: Pdisp_ds_do pds_do) throws (1: kmiacServer.KmiacServerException kse);
//	void UpdatePdispds_do(1: Pdisp_ds_do pds_do) throws (1: kmiacServer.KmiacServerException kse);
//	list<Pdisp_ds_do> getTblDispds_do(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
//	Pdisp_ds_do getDispds_do(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
//	void DeleteDispds_do(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);	


//classifiers
//	list<classifier.IntegerClassifier> get_n_v0a() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0b() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0c() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0d() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0e() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0f() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0g() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0h() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0m() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0n() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0p() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0r() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0s() throws (1: kmiacServer.KmiacServerException kse);
//        list<classifier.IntegerClassifier> get_n_v0t() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_c00() throws (1: kmiacServer.KmiacServerException kse);

}

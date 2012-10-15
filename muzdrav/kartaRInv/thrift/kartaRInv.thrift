namespace java ru.nkz.ivcgzo.thriftKartaRInv

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Pinvk {
    1:i32 npasp;
    2:i32 ninv;
    3:i64 dataz;
    4:i64 datav;
    5:string vrach;
    6:string mesto1;
    7:string preds;
    8:string uchr;
    9:string nom_mse;
    10:string name_mse;
    11:string ruk_mse;
    12:string rez_mse;
    13:i64 d_osv;
    14:i64 d_otpr;
    15:i64 d_inv;
    16:i64 d_invp;
    17:i64 d_srok;
    18:string srok_inv;
    19:string diag;
    20:string diag_s1;
    21:string diag_s2;
    22:string diag_s3;
    23:string oslog;
    24:string factor;
    25:string fact1;
    26:string fact2;
    27:string fact3;
    28:string fact4;
    29:string prognoz;
    30:string potencial;
    31:string klin_prognoz;
    32:string med_reab;
    33:string ps_reab;
    34:string prof_reab;
    35:string soc_reab;
    36:string zakl;
    37:string zakl_name;
    38:i32 nar1;
    39:i32 nar2;
    40:i32 nar3;
    41:i32 nar4;
    42:i32 nar5;
    43:i32 nar6;
    44:i32 ogr1;
    45:i32 ogr2;
    46:i32 ogr3;
    47:i32 ogr4;
    48:i32 ogr5;
    49:i32 ogr6;
    50:i32 ogr7;
    51:i32 mr1n;
    52:i32 mr2n;
    53:i32 mr3n;
    54:i32 mr4n;
    55:i32 mr5n;
    56:i32 mr6n;
    57:i32 mr7n;
    58:i32 mr8n;
    59:i32 mr9n;
    60:i32 mr10n;
    61:i32 mr11n;
    62:i32 mr12n;
    63:i32 mr13n;
    64:i32 mr14n;
    65:i32 mr15n;
    66:i32 mr16n;
    67:i32 mr17n;
    68:i32 mr18n;
    69:i32 mr19n;
    70:i32 mr20n;
    71:i32 mr21n;
    72:i32 mr22n;
    73:i32 mr23n;
    74:i32 mr1v;
    75:i32 mr2v;
    76:i32 mr3v;
    77:i32 mr4v;
    78:i32 mr5v;
    79:i32 mr6v;
    80:i32 mr7v;
    81:i32 mr8v;
    82:i32 mr9v;
    83:i32 mr10v;
    84:i32 mr11v;
    85:i32 mr12v;
    86:i32 mr13v;
    87:i32 mr14v;
    88:i32 mr15v;
    89:i32 mr16v;
    90:i32 mr17v;
    91:i32 mr18v;
    92:i32 mr19v;
    93:i32 mr20v;
    94:i32 mr21v;
    95:i32 mr22v;
    96:i32 mr23v;
    97:string mr1d;
    98:string mr2d;
    99:string mr3d;
    100:string mr4d;
    101:i32 pr1n;
    102:i32 pr2n;
    103:i32 pr3n;
    104:i32 pr4n;
    105:i32 pr5n;
    106:i32 pr6n;
    107:i32 pr7n;
    108:i32 pr8n;
    109:i32 pr9n;
    110:i32 pr10n;
    111:i32 pr11n;
    112:i32 pr12n;
    113:i32 pr13n;
    114:i32 pr14n;
    115:i32 pr15n;
    116:i32 pr16n;
    117:i32 pr1v;
    118:i32 pr2v;
    119:i32 pr3v;
    120:i32 pr4v;
    121:i32 pr5v;
    122:i32 pr6v;
    123:i32 pr7v;
    124:i32 pr8v;
    125:i32 pr9v;
    126:i32 pr10v;
    127:i32 pr11v;
    128:i32 pr12v;
    129:i32 pr13v;
    130:i32 pr14v;
    131:i32 pr15v;
    132:i32 pr16v;
    133:string pr1d;
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
	 9: optional i32 jitel;
	10: optional i32 sgrp;
	11: optional string adp_obl;
	12: optional string adp_gorod;
	13: optional string adp_ul;
	14: optional string adp_dom;
	15: optional string adp_korp;
	16: optional string adp_kv;
	17: optional string adm_obl;
	18: optional string adm_gorod;
	19: optional string adm_ul;
	20: optional string adm_dom;
	21: optional string adm_korp;
	22: optional string adm_kv;
	23: optional string mrab;
	24: optional string name_mr;
	25: optional i32 ncex;
	26: optional i32 poms_strg;
	27: optional i32 poms_tdoc;
	28: optional string poms_ndog;
	29: optional i32 pdms_strg;
	30: optional string pdms_ser;
	31: optional string pdms_nom;
	32: optional string pdms_ndog;
	33: optional i32 cpol_pr;
	34: optional i32 terp;
	35: optional i64 datapr;
	36: optional i32 tdoc;
	37: optional string docser;
	38: optional string docnum;
	39: optional i64 datadoc;
	40: optional string odoc;
	41: optional string snils;
	42: optional i64 dataz;
	43: optional string prof;
	44: optional string tel;
	45: optional i64 dsv;
	46: optional i32 prizn;
	47: optional i32 ter_liv;
	48: optional i32 region_liv;
}


exception PinvkNotFoundException {
}
exception PatientNotFoundException {
}


service thriftKartaRInv extends kmiacServer.KmiacServer {

 Pinvk getPinvk(1:i32 npasp, 2: i32 ninv) throws (1: kmiacServer.KmiacServerException kse, 2: PinvkNotFoundException pnfe),

 list<Pinvk> getAllPinvk(1:i32 npasp) throws (1: PinvkNotFoundException pnfe),
 
 void setPinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);


 i32 addPinvk(1:Pinvk invk) throws (1:PinvkNotFoundException paee),
  
 void DeletePinvk(1: i32 ninv) throws (1: kmiacServer.KmiacServerException kse);

	


//classifiers
	list<classifier.IntegerClassifier> get_n_v0a() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0b() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0c() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0d() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0e() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0f() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0g() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0h() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0m() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0n() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0p() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0r() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0s() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_v0t() throws (1: kmiacServer.KmiacServerException kse);
        list<classifier.IntegerClassifier> get_n_c00() throws (1: kmiacServer.KmiacServerException kse);

}

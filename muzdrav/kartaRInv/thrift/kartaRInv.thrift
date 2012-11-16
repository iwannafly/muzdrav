namespace java ru.nkz.ivcgzo.thriftKartaRInv

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Pinvk {
    1:i32 npasp;
    2:i64 dataz;
    3:i64 datav;
    4:string vrach;
    5:i32 mesto1;
    6:i32 preds;
    7:string uchr;
    8:string nom_mse;
    9:string name_mse;
    10:string ruk_mse;
    11:i32 rez_mse;
    12:i64 d_osv;
    13:i64 d_otpr;
    14:i64 d_inv;
    15:i64 d_invp;
    16:i64 d_srok;
    17:i32 srok_inv;
    18:string diag;
    19:string diag_s1;
    20:string diag_s2;
    21:string diag_s3;
    22:string oslog;
    23:i32 factor;
    24:i32 fact1;
    25:i32 fact2;
    26:i32 fact3;
    27:i32 fact4;
    28:i32 prognoz;
    29:i32 potencial;
    30:i32 klin_prognoz;
    31:i32 med_reab;
    32:i32 ps_reab;
    33:i32 prof_reab;
    34:i32 soc_reab;
    35:i32 zakl;
    36:string zakl_name;
    37:i32 nar1;
    38:i32 nar2;
    39:i32 nar3;
    40:i32 nar4;
    41:i32 nar5;
    42:i32 nar6;
    43:i32 ogr1;
    44:i32 ogr2;
    45:i32 ogr3;
    46:i32 ogr4;
    47:i32 ogr5;
    48:i32 ogr6;
    49:i32 ogr7;
    50:i32 mr1n;
    51:i32 mr2n;
    52:i32 mr3n;
    53:i32 mr4n;
    54:i32 mr5n;
    55:i32 mr6n;
    56:i32 mr7n;
    57:i32 mr8n;
    58:i32 mr9n;
    59:i32 mr10n;
    60:i32 mr11n;
    61:i32 mr12n;
    62:i32 mr13n;
    63:i32 mr14n;
    64:i32 mr15n;
    65:i32 mr16n;
    66:i32 mr17n;
    67:i32 mr18n;
    68:i32 mr19n;
    69:i32 mr20n;
    70:i32 mr21n;
    71:i32 mr22n;
    72:i32 mr23n;
    73:i32 mr1v;
    74:i32 mr2v;
    75:i32 mr3v;
    76:i32 mr4v;
    77:i32 mr5v;
    78:i32 mr6v;
    79:i32 mr7v;
    80:i32 mr8v;
    81:i32 mr9v;
    82:i32 mr10v;
    83:i32 mr11v;
    84:i32 mr12v;
    85:i32 mr13v;
    86:i32 mr14v;
    87:i32 mr15v;
    88:i32 mr16v;
    89:i32 mr17v;
    90:i32 mr18v;
    91:i32 mr19v;
    92:i32 mr20v;
    93:i32 mr21v;
    94:i32 mr22v;
    95:i32 mr23v;
    96:string mr1d;
    97:string mr2d;
    98:string mr3d;
    99:string mr4d;
    100:i32 pr1n;
    101:i32 pr2n;
    102:i32 pr3n;
    103:i32 pr4n;
    104:i32 pr5n;
    105:i32 pr6n;
    106:i32 pr7n;
    107:i32 pr8n;
    108:i32 pr9n;
    109:i32 pr10n;
    110:i32 pr11n;
    111:i32 pr12n;
    112:i32 pr13n;
    113:i32 pr14n;
    114:i32 pr15n;
    115:i32 pr16n;
    116:i32 pr1v;
    117:i32 pr2v;
    118:i32 pr3v;
    119:i32 pr4v;
    120:i32 pr5v;
    121:i32 pr6v;
    122:i32 pr7v;
    123:i32 pr8v;
    124:i32 pr9v;
    125:i32 pr10v;
    126:i32 pr11v;
    127:i32 pr12v;
    128:i32 pr13v;
    129:i32 pr14v;
    130:i32 pr15v;
    131:i32 pr16v;
    132:string pr1d;
    133:i32 ninv;
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

//list<Pinvk> getPinvk (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
//Pinvk getPinvk(1:i32 npasp, 2: i32 ninv) throws (1: kmiacServer.KmiacServerException kse, 2: PinvkNotFoundException //pnfe),
//	i32 AddPinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);
//	void UpdatePinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);
//	void DeletePmer(1: i32 ninv) throws (1: kmiacServer.KmiacServerException kse);

 Pinvk getPinvk(1:i32 npasp, 2: i32 ninv) throws (1: kmiacServer.KmiacServerException kse, 2: PinvkNotFoundException pnfe),

 list<Pinvk> getAllPinvk(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse),
 
 //void setPinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse, 2: PinvkNotFoundException pnfe);
void setPinvk(1: Pinvk invk) throws (1: kmiacServer.KmiacServerException kse);

 i32 addPinvk(1:Pinvk invk) throws (1:PinvkNotFoundException paee),
  
 void DeletePinvk(1: i32 ninv) throws (1: kmiacServer.KmiacServerException kse);

	


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

namespace java ru.nkz.ivcgzo.thriftVgr

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
	 * формирование KOB
	 */
	exception KovNotFoundException {
	}

struct Kontipa{
	1:i32 bn;
	2:string fam;
	3:string im;
	4:string otch;
        5:string sex;	
        6:i64 dr;
        7:i32 stat;
        8:string ul;
        9:string nd;
        10:string nk;
        11:i32 kat;
        12:i32 sth;
        13:string polis;
        14:i32 pud;
        15:string sdog;
        16:i64 dpp;
        17:i32 ter;
        18:i32 lpu;
        19:i64 dotkr;
        20:i32 nas;
        21:string nnas;
        22:i64 dri;
        23:i32 gri;
        24:string pp;
        25:string konti;
        26:i64 datsm;
  }
struct Kontidi{
	1:i32 bn;
        2:string icd;
        3:i32 pp;
        4:i32 priz;
        5:i32 priz1;

}
struct Kontiis{
	1:i32 bn;
        2:string kissl;
        3:i64 dvi;

}

struct Kontilo{
	1:i32 bn;
        2:string klo;
        3:i64 dn;
        4:i64 dk; 
        5:i32 ter;
        6:i32 lpu;
 
}
struct Kontios{
	1:i32 bn;
        2:i32 kspec;
        3:i64 dvo;

}

struct Lgot{
	1:i32 bn;
        2:i32 klg;
}
/*Диспансеризация детей   */

struct Sv3{
	1:string code;
        2:i64 dat_v;
        3:i32 uchr;  
        4:i32 cod_uch;  
        5:string uchrnum;
        6:string uchrname;
        7:string fio_u;
        8:i64 dat_born;
        9:i32 pol; 
        10:i32 nation;

        11:i32 vremen;
        12:i32 mesto_k; 
        13:string mesto_k1; 
        14:string mesto_k2;
        15:string mesto_k3;
        16:i32 mesto_k4;
        17:i32 mesto_k5;
        18:i32 mesto_k6;
        19:i32 gorod_k;
        20:string street_k;

        21:bool m_v;
        22:string where_s1;
        23:i32 where_s;
        24:bool p_dou;
        25:i32 pos_; 
        26:bool u_;
        27:string m_uth;
        28:i32 m_uth1;
        29:i32 Wedom;
        30:string Wedom1;

        31:i32 vesgr;
        32:i32 ves_kg;
        33:i32 rost;
        34:i32 f_r;
        35:i32 f_r1;
        36:i32 massa;
        37:i32 post;
        38:i32 intel;
        39:i32 em;
        40:i32 ps;

        41:i32 d_do;
        42:string k_s1;
        43:string k_s2;
        44:string k_s3;
        45:string k_s4;
        46:string k_s5;
        47:i32 d_po; 
        48:string k_si1;
        49:i32 p_u_01;
        50:i32 n_pu1;

        51:i32 f_h_1; 
        52:string k_si2;
        53:i32 p_u_02;
        54:i32 n_pu2;
        55:i32 f_h_2;
        56:string k_si3;
        57:i32 p_u_03;
        58:i32 n_pu3;
        59:i32 f_h_3;
        60:string k_si4;

        61:i32 p_u_04;
        62:i32 n_pu4;
        63:i32 f_h_4;
        64:string k_si5;
        65:i32 p_u_05;
        66:i32 n_pu5;
        67:i32 f_h_5;
        68:i32 inv;
        69:i32 zab_inv;
        70:i32 ch_b;

        71:string gr_z;
        72:i32 l_o_d;  
        73:i32 l_o_a;
        74:i32 l_k_s;
        75:i32 l_o_s;
        76:i32 m_p_z;
        77:i32 prov;
        78:string vrach;
        79:i32 cod_reg;
        80:bool err_;
        81:i32 postpone;
        82:string id_fio;

}



 
 


/*Выгрузка для Кемерово по диспансеризации беременных*/
struct RdPatient{
         1: optional i32    uid;
         2: optional i32 npasp;
         3: optional string fam;
         4: optional string im;
         5: optional string ot;
         6: optional i64    datar;
         7: optional string docser;
         8: optional string docnum;
         9: optional string    tawn;
        10: optional string street;
        11: optional string house;  
        12: optional string flat;
        13: optional string poms_ser;
        14: optional string poms_nom;
        15: optional string dog;
        16: optional i32    stat;
        17: optional i32    lpup;
        18: optional i32    terp;
        19: optional string ftawn; 
        20: optional string fstreet;
        21: optional string fhouse;
        22: optional string fflat;
        23: optional string grk;
        24: optional string rez;  
        25: optional string telm;
        26: optional string vred;
        27: optional i32 deti;
        28: optional i64 datay;
	29: optional i32 yavka1;
        30: optional i64 datazs;
        31: optional string famv;
        32: optional string imv;
        33: optional string otv;
	34: optional i64 datasn; 
	35: optional i32 shet;
        36: optional i32 kolrod;
	37: optional i32 abort;
        38: optional i32 vozmen;
        39: optional i32 prmen;
	40: optional i64 datam;
        41: optional bool kont;
        42: optional i32 dsp;
        43: optional i32 dsr;
        44: optional i32 dtroch;
        45: optional i32 cext;
        46: optional i32 indsol;
        47: optional string vitae;
        48: optional string allerg;
	49: optional i32 ishod;
        50: optional string prrod;
        51: optional i32 oslrod;
        52: optional i32 sem;
        53: optional i32 rost;
        54: optional double vesd; 
        55: optional i32 osoco;
        56: optional i32 uslpr;
        57: optional i64 dataz;
        58: optional i32 polj;
        59: optional i32 obr; 
        60: optional string fiootec;
        61: optional string mrabotec;
        62: optional string telotec; 
        63: optional string grotec;
        64: optional string photec;
        65: optional i32 vredotec;
        66: optional i32 vozotec;
        67: optional string mrab;
        68: optional string prof; 
        69: optional bool eko;
        70: optional bool rub;
        71: optional bool predp;
        72: optional i32 terpr;
        73: optional i32 oblpr;
        74: optional i32 diag;
        75: optional i32 cvera;
        76: optional i64 dataosl;
        77: optional i32 osp;
}
struct RdVizit{
         1: optional i32    uid;
         2: optional i64    dv;
         3: optional i32 sp;
         4: optional string famvr;
         5: optional string imvr;
         6: optional string otvr;
         7: optional string diag;
         8: optional i32    mso;
         9: optional i32    rzp;
        10: optional i32    aim;
        11: optional i32    npr; 
        12: optional i32    npasp; 
}
struct RdConVizit{
         1: optional i32    uiv;
         2: optional i32    uid;
         3: optional i32    npasp;
         4: optional i32    ned;
         5: optional double ves;
         6: optional i32    lcad;
         7: optional i32    ldad; 
         8: optional i32    rcad;
         9: optional i32    rdad;
        10: optional i32    ball;
        11: optional i32 hdm;
        12: optional i32 spl; 
        13: optional i32 oj;
        14: optional i32 chcc;
        15: optional i32 polpl;
        16: optional i32 predpl;
        17: optional i32 serd;
        18: optional i32 serd1;
        19: optional i32 oteki;
}

struct KartaBer {
	1: optional i32 npasp;
	2: optional i32 id_pvizit;
	3: optional i32 id_pos;
	4: optional i32 id_rd_sl;
}
/*Инфорация о льготниках из пенсионного фонда*/
struct Rr_pl {
	1: optional i32 id_lg;
	2: optional string ss;
	3: optional string fam;
	4: optional string im;
	5: optional string ot;
	6: optional string w;
	7: optional i64 dr;
	8: optional i32 c_doc;
	9: optional string name_doc;
	10: optional string sn_doc;
	11: optional string s_doc;
	12: optional string n_doc;
	13: optional i64 date_doc;
	14: optional string n_org;
	15: optional string adres;
	16: optional i32 okato_reg;
	17: optional i32 kd_ter;
	18: optional i32 kd_ter_mu;
	19: optional string post_reg;
	20: optional string reg_reg;
	21: optional string area_reg;
	22: optional string set_reg;	
	23: optional string str_reg;	
	24: optional string h_reg;	
	25: optional string fr_reg;	
	26: optional string fl_reg;	
	27: optional string post_loc;	
	28: optional string reg_loc;	
	29: optional string area_loc;	
	30: optional string set_loc;	
	31: optional string str_loc;	
	32: optional string h_loc;	
	33: optional string fr_loc;	
	34: optional string fl_loc;	
	35: optional string mesto_pr;	
	36: optional string c_kat1;	
	37: optional string c_kat2;	
	38: optional i32 s_gsp;	
	39: optional i32 s_gspn;	
	40: optional i64 db_edv;	
	41: optional i64 de_edv;	
	42: optional i64 date_rsb;	
	43: optional i64 date_rse;	
	44: optional i32 u_type;	
	45: optional string c_katl;	
	46: optional string name_dl;	
	47: optional string s_dl;	
	48: optional string n_dl;	
	49: optional string name_vd;	
	50: optional i64 date_vd;	
	51: optional i64 date_bl;	
	52: optional i64 date_el;
}

/*информация о льготнике из базы*/
struct Patient {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string fam;
	4: optional string im;
	5: optional string ot;
	6: optional i32 pol;
	7: optional i64 datar;
	8: optional i32 poms_tdoc;
	9: optional string poms_ser;
	10: optional string poms_nom;
	11: optional i32 tdoc;
	12: optional string docser;
	13: optional string docnum;
	14: optional i64 datadoc;
	15: optional string snils;
	16: optional string adp_obl;
	17: optional string adp_raion;
	18: optional string adp_gorod;
	19: optional string adp_ul;
	20: optional string adp_dom;
	21: optional string adp_korp;
	22: optional string adp_kv;
}

struct Lgota {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 lgot;
	4: optional i64 datal;
	5: optional i32 gri;
	6: optional i32 sin;
	7: optional i32 pp;
	8: optional i64 drg;
	9: optional i64 dot;
	10: optional i32 obo;
	11: optional string ndoc;
}

/** 
 *пациент не найден
 */
exception PatientNotFoundException {
}
/**
 *не найдена льготная категория
 */
exception LgkatNotFoundException {
}

service ThriftVgr extends kmiacServer.KmiacServer {
	/**
        * Создает KOB
	*/
	string getKovInfoPol(1:i32 cpodr, 2:i64 dn, 3:i64 dk) throws (1:kmiacServer.KmiacServerException kse);



/**
        * диспансеризация детей
	*/
	string getDetInfoPol(1:i32 cpodr, 2:i64 dn, 3:i64 dk) throws (1:kmiacServer.KmiacServerException kse);




/**
	 * Классификатор поликлиник для текущего ЛПУ (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getAllPolForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор поликлиники для текущего ЛПУ (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getPolForCurrentLpu(1:i32 polId) throws (1: kmiacServer.KmiacServerException kse);

/*DispBer*/
/*Выгрузка для Кемерово по диспансеризации беременных*/
        list<RdPatient> getRdPatient() throws (1: kmiacServer.KmiacServerException kse);
        list<RdVizit> getRdVizit() throws (1: kmiacServer.KmiacServerException kse);
        list<RdConVizit>  getRdConVizit() throws (1: kmiacServer.KmiacServerException kse);
	string formfilecsv() throws (1: kmiacServer.KmiacServerException kse);


/**
 * поиск льготника в таблице Patient
 */
	Patient getPatientInfo(1:i32 npasp) throws (1: PatientNotFoundException le); 

/**
 * корректировка информации (снилса) пациента
 */
	i32 setPatientInfo(1: Patient npasp);

/**
 * поиск льготы в таблице P_kov
 */ 
	list<Lgota> getLgotaInfo(1:i32 npasp) throws (1: LgkatNotFoundException le);

/**
 * добавление льготы 
 */
	i32 addLgotaInfo(1:Lgota npasp);
}

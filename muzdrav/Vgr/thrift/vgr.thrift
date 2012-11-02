namespace java ru.nkz.ivcgzo.thriftVgr

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

/**
	 * Ошибка формирования KOB
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
         9: optional i32    tawn;
        10: optional string street;
        11: optional string house;  
        12: optional string flat;
        13: optional string poms_ser;
        14: optional string poms_nom;
        15: optional string dog;
        16: optional i32    stat;
        17: optional i32    lpup;
        18: optional i32    terp;
        19: optional i32    ftawn; 
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
        54: optional i32 vesd; 
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
         3: optional string sp;
         4: optional string famwr;
         5: optional string imwr;
         6: optional string otwr;
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
         3: optional double ves;
         4: optional i32    ned;
         5: optional i32    lcad;
         6: optional i32    ldad; 
         7: optional i32    rcad;
         8: optional i32    rdad;
         9: optional i32    rost;
        10: optional i64    datar;
        11: optional i32    obr;
        12: optional i32    sem;
        13: optional i32    osoco;
        14: optional string vrpr;
        15: optional i32 npasp;
        16: optional i32 hdm;
        17: optional i32 spl; 
        18: optional i32 oj;
        19: optional i32 chcc;
        20: optional i32 polpl;
        21: optional i32 predpl;
        22: optional i32 serd;
        23: optional i32 serd1;
        24: optional i32 oteki;
}
struct KartaBer {
	1: optional i32 npasp;
	2: optional i32 id_pvizit;
	3: optional i32 id_pos;
	4: optional i32 id_rd_sl;
}

service ThriftVgr extends kmiacServer.KmiacServer {
	/**
        * Создает KOB
	*/
	void getKovInfoPol(1:i32 cpodr, 2:i64 dn, 3:i64 dk,  4:i32 clpu) throws (1:kmiacServer.KmiacServerException kse, 2:KovNotFoundException rnfe);


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
}


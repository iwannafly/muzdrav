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
}


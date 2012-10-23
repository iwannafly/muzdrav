namespace java ru.nkz.ivcgzo.thriftGenReestr

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct Pasp{
	1:i32 sl_id;
	2:i32 vid_rstr;
	3:i32 str_org;
	4:string name_str;
	5:i32 ter_mu;
	6:i32 kod_mu;
	7:i64 df_per;
	8:string fam;
	9:string im;
	10:string otch;
	11:i64 dr;
	12:string sex;
	13:string fam_rp;
	14:string im_rp;
	15:string otch_rp;
	16:i64 dr_pr;
	17:string sex_pr;
	18:string spolis_pr;
	19:string polis_pr;
	20:i32 vpolis;
	21:string spolis;
	22:string polis;
	23:i32 type_doc;
	24:string docser;
	25:string docnum;
	26:i32 region;
	27:i32 ter_liv;
	28:i32 status;
	29:string kob;
	30:string ist_bol;
	31:i32 vid_hosp;
	32:string talon;
	33:i32 ter_pol;
	34:i32 pol;
	35:string n_mk;
	36:i32 id_lpu;
	37:i32 id_smo;
	38:i32 region_liv;
	39:string ogrn_str;
	40:string birthplace;
	41:i32 ter_mu_dir;
	42:i32 kod_mu_dir;
}

struct Med{
	1:i32 sl_id;
	2:i32 id_med;
	3:i32 kod_rez;
	4:i32 kod_otd;
	5:i64 d_pst;
	6:i64 d_end;
	7:i32 kl_usl;
	8:i32 pr_exp;
	9:i32 etap;
	10:i32 pl_extr;
	11:string usl;
	12:double kol_usl;
	13:i32 c_mu;
	14:string diag;
	15:string ds_s;
	16:string pa_diag;
	17:i32 pr_out;
	18:i32 res_l;
	19:i32 prof_fn;
	20:double stoim;
	21:double st_acpt;
	22:i32 cases;
	23:i32 place;
	24:i32 spec;
	25:i32 prvd;
	26:i32 v_mu;
	27:i32 res_g;
	28:string ssd;
	29:i32 id_med_smo;
	30:i32 id_med_tf;
	31:i32 psv;
	32:i32 pk_mc;
	33:i32 pr_pv;
	34:string obst;
	35:string n_schet;
	36:i64 d_schet;
	37:i32 v_sch;
	38:string talon_omt;
}

struct Err{
	1:i32 sl_id;
	2:i32 ter_mu;
	3:i32 kod_mu;
	4:i32 id_med;
	5:i32 kod_err;
	6:string prim;
}

	/**
	 * Ошибка формирования реестра
	 */
	exception ReestrNotFoundException {
	}

service ThriftGenReestr extends kmiacServer.KmiacServer {

	/**
        * Создает реестр поликлиники и возвращает протокол проверок реестра
	*/
	string getReestrInfoPol(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

	/**
        * Записывает код результата проверки реестра и возвращает протокол ошибок
	*/
	string getProtokolErrPol(1:string pf) throws (1:kmiacServer.KmiacServerException kse);

	/**
        * Создает реестр параотделения и возвращает протокол проверок реестра
	*/
	string getReestrInfoLDS(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

	/**
        * Создает реестр стационара и возвращает протокол проверок реестра
	*/
	string getReestrInfoOtd(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

	/*Классификаторы*/

	/**
	 * Классификатор поликлиник для текущего ЛПУ (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getAllPolForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор поликлиники для текущего ЛПУ (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getPolForCurrentLpu(1:i32 polId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор параотделений для текущего ЛПУ (N_LDS (pcod))
	 */
	list<classifier.IntegerClassifier> getAllLDSForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор параотделения для текущего ЛПУ (N_LDS (pcod))
	 */
	list<classifier.IntegerClassifier> getLDSForCurrentLpu(1:i32 polId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор отделений для текущего ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getOtdForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор отделений для текущего ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getAllOtdForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);
}
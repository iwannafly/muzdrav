namespace java ru.nkz.ivcgzo.thriftGenReestr

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct PaspErrorInfo {	
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string fam;
	4: optional string im;
	5: optional string ot;
	6: optional i64 datar;
	7: optional i32 kderr;
	8: optional string err_name;
	9: optional string err_comm;
}

struct MedPolErrorInfo {
	 1: optional i32 id;
	 2: optional i32 id_obr;
	 3: optional i32 id_pos;
	 4: optional i64 dat_obr;
	 5: optional i64 dat_pos;
	 6: optional i32 vr_pcod;
	 7: optional string vr_fio;
	 8: optional string vr_cdol;
	 9: optional string vr_cdol_name;
	10: optional i32 npasp;
	11: optional string pat_fio;
	12: optional i64 pat_datar;
	13: optional i32 kderr;
	14: optional string err_name;
	15: optional string err_comm;
}

struct Pasp{
	1:i32 sl_id;
	2:i32 vid_rstr;
	3:i32 str_org;
	4:i32 ter_mu;
	5:i32 kod_mu;
	6:i64 df_per;
	7:string fam;
	8:string im;
	9:string otch;
	10:i64 dr;
	11:string sex;
	12:string ssp;
	13:i32 vpolis;
	14:string spolis;
	15:string polis;
	16:i32 type_doc;
	17:string docser;
	18:string docnum;
	19:string fam_pr;
	20:string im_pr;
	21:string otch_pr;
	22:i64 dr_pr;
	23:string sex_pr;
	24:i32 vpolis_pr;
	25:string spolis_pr;
	26:string polis_pr;
	27:i32 type_docpr;
	28:string docser_pr;
	29:string docnum_pr;
	30:i32 region;
	31:string ist_bol;
	32:i32 vid_hosp;
	33:i32 ter_pol;
	34:i32 pol;
	35:string n_mk;
	36:i32 id_lpu;
	37:string birthplace;
	38:i32 ter_mu_dir;
	39:i32 kod_mu_dir;
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
	9:i32 pl_extr;
	10:string usl;
	11:double kol_usl;
	12:i32 c_mu;
	13:string diag;
	14:string ds_s;
	15:string pa_diag;
	16:i32 pr_out;
	17:i32 res_l;
	18:i32 profil;
	19:double stoim;
	20:i32 cases;
	21:i32 place;
	22:i32 spec;
	23:i32 prvd;
	24:i32 res_g;
	25:string ssd;
	26:i32 id_med_tf;
	27:i32 psv;
	28:i32 pr_pv;
	29:string obst;
	30:string n_schet;
	31:i64 d_schet;
	32:i32 v_sch;
	33:string talon_omt;
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

	list<PaspErrorInfo> getPaspErrors(1: i32 cpodrz, 2: i64 datazf, 3: i64 datazt) throws (1: kmiacServer.KmiacServerException kse);
	list<MedPolErrorInfo> getMedPolErrors(1: i32 cpodrz, 2: i64 datazf, 3: i64 datazt) throws (1: kmiacServer.KmiacServerException kse);
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
        * Записывает код результата проверки реестра и возвращает протокол ошибок
	*/
	string getProtokolErrLDS(1:string pf) throws (1:kmiacServer.KmiacServerException kse);

	/**
        * Создает реестр стационара и возвращает протокол проверок реестра
	*/
	string getReestrInfoOtd(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

	/**
        * Записывает код результата проверки реестра и возвращает протокол ошибок
	*/
	string getProtokolErrGosp(1:string pf) throws (1:kmiacServer.KmiacServerException kse);

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
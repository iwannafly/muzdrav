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
        * Создает реестр дсп и возвращает протокол проверок реестра
	*/
	string getReestrInfoDSP(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

	/**
        * Записывает код результата проверки реестра и возвращает протокол ошибок
	*/
	string getProtokolErrDSP(1:string pf) throws (1:kmiacServer.KmiacServerException kse);

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
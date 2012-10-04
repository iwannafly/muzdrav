namespace java ru.nkz.ivcgzo.thriftGenReestr

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

	/**
	 * Ошибка формирования реестра
	 */
	exception ReestrNotFoundException {
	}

service ThriftGenReestr extends kmiacServer.KmiacServer {

	/**
        * Создает реестр и возвращает протокол проверок реестра
	*/
	string getReestrInfoPol(1:i32 cpodr, 2:i64 dn, 3:i64 dk, 4:i32 vidreestr, 5:i32 vopl, 6:i32 clpu, 7:i32 terp, 8:i64 df) throws (1:kmiacServer.KmiacServerException kse, 2:ReestrNotFoundException rnfe);

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
	 * Классификатор отделений для текущего ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getOtdForCurrentLpu(1:i32 lpuId) throws (1: kmiacServer.KmiacServerException kse);

}
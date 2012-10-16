namespace java ru.nkz.ivcgzo.thriftOutputInfo

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct InputSvodVed {
    1: i32 kpolik;
    2: string namepol;
    3: string dateb;
    4: string datef;
    5: string vozcat;
}

struct SvodVed {
	1: optional i32 kodVidIssl;
	2: optional i32 userId;
	3: optional i32 npasp;
	4: optional string kodMetod;
	5: optional list<string> pokaz;
	6: optional string mesto;
	7: optional string kab;
	8: optional i32 pvizitId;
	9: optional string cpodr_name;
	10: optional string clpu_name;
}

struct VrachInfo {
	1: i32 pcod;
	2: string fam;
	3: string im;
	4: string ot;
	5: string cdol;
} 

struct VrachTabel {
	1: i32 pcod;
	2: string cdol;
	3: i64 datav;
	4: double timep;
	5: double timed;
	6: double timepda;
	7: double timeprf;
	8: double timepr;
	9: i32 nuch1;
	10: i32 nuch2;
	11: i32 nuch3;
}

/**
 * информация отстутствует
 */
exception VINotFoundException {
}

/**
 * Такая уже запись существует
 */
exception VTDuplException {
}

service ThriftOutputInfo extends kmiacServer.KmiacServer {
    string printSvodVed(1: InputSvodVed sv) throws (1: kmiacServer.KmiacServerException kse);
    
    /**
     * Возвращает список врачей, ведущих прием
     * @param pcod - Уникальный код специалиста
     * @return список thrift-объектов, содержащих информацию о врачах
     */
    list<VrachInfo> getVrachTableInfo(1:i32 cpodr) throws (1: VINotFoundException sse,
		2:kmiacServer.KmiacServerException kse);
    

     /**
     * Возвращает время работы врачей
     */
    list<VrachTabel> getVrachTabel(1:i32 pcod) throws (1: VTDuplException tse,
		2:kmiacServer.KmiacServerException kse);


    /**
     * Добавляет или обновляет информацию о враче
     */
    void addOrUpdateVrachTabel(1:i32 pcod) throws (1:VTDuplException ase,
		2:kmiacServer.KmiacServerException kse);

}


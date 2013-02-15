namespace java ru.nkz.ivcgzo.thriftDiary

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct TMedicalHistory{
    1: optional i32 id;
	2: optional i32 idGosp;
	3: optional string jalob;
	4: optional string morbi;
	5: optional string statusPraesense;
	6: optional string statusLocalis;
	7: optional string fisicalObs;
	8: optional i32 pcodVrach;
	9: optional i64 dataz;
	10: optional i64 timez;
	11: optional i32 cpodr;
	12: optional i32 pcodAdded;
}

struct Patient {
	1: optional i32 id;
	2: optional string surname;
	3: optional string name;
	4: optional string middlename;
	5: optional i32 idGosp;
}

struct ShablonText {
	1: i32 grupId;
	2: string grupName;
	3: string text;
}

struct Shablon {
	1: string din;
	2: string next_osm;
	3: list<ShablonText> textList;
	4: i32 id;
}

/**
 * Медицинская история с такими данными не найдена
 */
exception MedicalHistoryNotFoundException {
}

service ThriftDiary extends kmiacServer.KmiacServer {
	list<TMedicalHistory> getMedicalHistory(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse,
		2: MedicalHistoryNotFoundException mhnfe);
	i32 addMedicalHistory(1:TMedicalHistory medHist) throws (1:kmiacServer.KmiacServerException kse);
	void updateMedicalHistory(1:TMedicalHistory medHist)
		throws (1:kmiacServer.KmiacServerException kse, 2:MedicalHistoryNotFoundException mhnfe);
	void deleteMedicalHistory(1:i32 id)
		throws (1:kmiacServer.KmiacServerException kse, 2:MedicalHistoryNotFoundException mhnfe);

	list<classifier.IntegerClassifier> getShablonNames(1:i32 cspec, 2:i32 cslu, 3:string srcText)
		throws (1:kmiacServer.KmiacServerException kse);
	Shablon getShablon(1:i32 idSh) throws (1:kmiacServer.KmiacServerException kse);
	
    /**
	 * Получение списка подразделений заданного ЛПУ
	 * @param clpu Идентификатор ЛПУ
	 * @return Возвращает список ЛПУ, классифицируемых целым числом - идентификатором ЛПУ
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	list<classifier.IntegerClassifier> getOtdFromLPU(1:i32 clpu)
		throws (1:kmiacServer.KmiacServerException kse);
    /**
	 * Получение списка врачей заданных ЛПУ и подразделения
	 * @param clpu Идентификатор ЛПУ
	 * @param cpodr Идентификатор подразделения ЛПУ
	 * @return Возвращает список врачей, классифицируемых целым числом - идентификатором врача
	 * @throws KmiacServerException исключение на стороне сервера
	 * @author Балабаев Никита Дмитриевич
	 */
	list<classifier.IntegerClassifier> getDoctorsFromOtd(1:i32 clpu, 2:i32 cpodr)
		throws (1:kmiacServer.KmiacServerException kse);
}


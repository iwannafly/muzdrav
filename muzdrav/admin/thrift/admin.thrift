namespace java ru.nkz.ivcgzo.thriftServerAdmin

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct MestoRab {
	 1: i32 id;
	 2: i32 pcod;
	 3: i32 clpu;
	 4: optional i32 cslu;
	 5: optional i32 cpodr;
	 6: optional string cdol;
	 7: optional i64 datau;
	 8: optional i32 priznd;
	 9: optional i32 user_id;
}

struct VrachInfo {
	 1: i32 pcod;
	 2: string fam;
	 3: string im;
	 4: string ot;
	 5: i32 pol;
	 6: i64 datar;
	 7: string obr;
	 8: string snils;
	 9: string idv;
}

//not otional
struct UserIdPassword {
	1: i32 user_id;
	2: string password;
}

struct ShablonOsm {
	1: i32 id;
	2: string name;
	3: string diag;
	4: i32 cDin;
	5: i32 cslu;
	6: list<i32> specList;
	7: list<classifier.IntegerClassifier> textList;
}

struct ShablonDop {
	1: i32 id;
	2: i32 idNshablon
	3: string name;
	4: string text;
}

struct ShablonLds {
	1: i32 id;
	2: i32 c_p0e1;
	3: string c_ldi;
	4: string name;
	5: string opis;
	6: string zakl;
}

struct ShablonOper {
	 1: i32 id;
	 2: i32 stat_tip;
	 3: string name;
	 4: string oper_pcod;
	 5: string oper_name;
	 6: string diag_pcod;
	 7: string diag_name;
	 8: i64 oper_dlit;
	 9: string mat;
	10: string text;
}

/**
 * Врач с такими данными уже существует.
 */
exception VrachExistsException {
}

/**
 * Врач с такими данными не найден.
 */
exception VrachNotFoundException {
}

/**
 * Место работы с такими данными уже существует для данного врача.
 */
exception MestoRabExistsException {
}

/**
 * Место работы с такими данными не найдено.
 */
exception MestoRabNotFoundException {
}

/**
 * Шаблон с такими параметрами уже существует.
 */
exception TemplateExistsException {
}


service ThriftServerAdmin extends kmiacServer.KmiacServer {

	list<classifier.IntegerClassifier> get_n_p0s15();
	list<classifier.IntegerClassifier> get_n_o00(1: i32 clpu);
	list<classifier.IntegerClassifier> get_n_n00(1: i32 clpu);
	list<classifier.IntegerClassifier> get_n_lds(1: i32 clpu);
	list<classifier.StringClassifier> get_n_z00();


	/**
	 * Список всех врачей для данного лпу.
	 */
	list<VrachInfo> GetVrachList(1: i32 clpu);

	/**
	 * Информация на конкретного врача по его коду.
	 */
	VrachInfo GetVrach(1: i32 pcod) throws (1: VrachNotFoundException vne);

	/**
	 * Добавление врача.
	 */
	i32 AddVrach(1: VrachInfo vr);

	/**
	 * Обновление информации о враче.
	 */
	void UpdVrach(1: VrachInfo vr) throws (1: VrachExistsException vee);

	/**
	 * Удаление врача.
	 */
	void DelVrach(1: i32 vrPcod, 2: i32 lpuPcod);


	/**
	 * Список всех мест работ для данного врача.
	 */
	list<MestoRab> GetMrabList(1: i32 vrPcod, 2: i32 clpu);

	/**
	 * Информация о конкретном месте работы по его коду.
	 */
	MestoRab GetMrab(1: i32 id) throws (1: MestoRabNotFoundException mne);

	/**
	 * Добавление места работы.
	 */
	i32 AddMrab(1: MestoRab mr) throws (1: MestoRabExistsException mee);

	/**
	 * Обновление информации о месте работы.
	 */
	void UpdMrab(1: MestoRab mr) throws (1: MestoRabExistsException mee);

	/**
	 * Удаление места работы.
	 */
	void DelMrab(1: MestoRab mr);

	/**
	 * Получает логин пользователя.
	 */
	string getLogin(1: i32 userId);

	/**
	 * Устанавливает пароль для пользователя, открывая ему доступ к системе.
	 */
	UserIdPassword setPassword(1: i32 mrId, 2: string login);

	/**
	 * Очищает пароль пользователя, закрывая ему доступ к системе.
	 */
	void remPassword(1: i32 userId);

	/**
	 * Получает разрешения пользователя, то есть, к каким частям системы у него есть доступ.
	 */
	string getPermissions(1: i32 userId);

	/**
	 * Устанавливает разрешения пользователя.
	 */
	void setPermissions(1: i32 userId, 2: string pdost);

	list<classifier.IntegerClassifier> getReqShOsmList() throws (1: kmiacServer.KmiacServerException kse);
	i32 saveShablonOsm(1: ShablonOsm sho) throws (1: kmiacServer.KmiacServerException kse, 2: TemplateExistsException tee);
	list<classifier.IntegerClassifier> getAllShablonOsm() throws (1: kmiacServer.KmiacServerException kse);
	ShablonOsm getShablonOsm(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getShablonOsmDiagList(1: string srcStr) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShablonOsmListByDiag(1: string diag) throws (1: kmiacServer.KmiacServerException kse);
	void deleteShablonOsm(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getShDopRazdList() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShDopList(1: i32 idNshablon) throws (1: kmiacServer.KmiacServerException kse);
	ShablonDop getShDop(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	i32 saveShDop(1: ShablonDop shDop) throws (1: kmiacServer.KmiacServerException kse, 2: TemplateExistsException tee);
	void deleteShDop(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getShLdsVidIsslList() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getShLdsIsslList(1: i32 cP0e1) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShLdsList(1: i32 cP0e1, 2: string cLdi) throws (1: kmiacServer.KmiacServerException kse);
	ShablonLds getShLds(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	i32 saveShLds(1: ShablonLds shLds) throws (1: kmiacServer.KmiacServerException kse, 2: TemplateExistsException tee);
	void deleteShLds(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getShOperStatList() throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShOperList(1: i32 statTip, 2: string srcStr) throws (1: kmiacServer.KmiacServerException kse);
	ShablonOper getShOper(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
	i32 saveShOper(1: ShablonOper shOper) throws (1: kmiacServer.KmiacServerException kse, 2: TemplateExistsException tee);
	void deleteShOper(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
}

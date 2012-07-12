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

struct ShablonRazd {
	1: i32 id;
	2: string name;
}

struct ShablonPok {
	1: i32 id;
	2: i32 id_razd;
	3: string name;
	4: bool checked;
}

struct ShablonText {
	1: i32 id;
	2: i32 id_razd;
	3: i32 id_pok;
	4: string pcod_s00;
	5: string text;
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


service ThriftServerAdmin extends kmiacServer.KmiacServer {

	list<classifier.IntegerClassifier> getPrizndList();
	list<classifier.StringClassifier> get_n_s00();
	list<classifier.IntegerClassifier> get_n_p0s13();
	list<classifier.IntegerClassifier> get_n_o00(1: i32 clpu);
	list<classifier.IntegerClassifier> get_n_n00(1: i32 clpu);
	list<classifier.IntegerClassifier> get_n_lds(1: i32 clpu);
	list<classifier.StringClassifier> get_n_z00();
	list<classifier.IntegerClassifier> get_n_z30();


	/**
	 * Список всех врачей для данного лпу.
	 */
	list<VrachInfo> GetVrachList();

	/**
	 * Информация на конкретного врача по его коду.
	 */
	VrachInfo GetVrach(1: i32 pcod) throws (1: VrachNotFoundException vne);

	/**
	 * Добавление врача.
	 */
	i32 AddVrach(1: VrachInfo vr) throws (1: VrachExistsException vee);

	/**
	 * Обновление информации о враче.
	 */
	void UpdVrach(1: VrachInfo vr) throws (1: VrachExistsException vee);

	/**
	 * Удаление врача.
	 */
	void DelVrach(1: i32 pcod);


	/**
	 * Список всех мест работ для данного врача.
	 */
	list<MestoRab> GetMrabList(1: i32 vrPcod);

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
	string getLogin(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Устанавливает пароль для пользователя, открывая ему доступ к системе.
	 */
	UserIdPassword setPassword(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod, 4: string login);

	/**
	 * Очищает пароль пользователя, закрывая ему доступ к системе.
	 */
	void remPassword(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Получает разрешения пользователя, то есть, к каким частям системы у него есть доступ.
	 */
	string getPermissions(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Устанавливает разрешения пользователя.
	 */
	void setPermissions(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod, 4: string pdost);

	/**
	 * Получает список разделов шаблонов.
	 */
	list<ShablonRazd> getShabRazd();

	/**
	 * Получает список показателей шаблонов.
	 */
	list<ShablonPok> getShabPok(1: i32 id_razd, 2: string cdol);

	/**
	 * Устанавливает доступность показателя шаблона для кода должности.
	 */
	void setShabPok(1: ShablonPok shPok, 2: string cdol);

	/**
	 * Устанавливает доступность показателей для всего раздела.
	 */
	void setShabPokGrup(1: ShablonRazd shRazd, 2: string cdol, 3: bool value);

	/**
	 * Получает тексты шаблонов для показателя шаблона и кода должности в независимости от доступности.
	 */
	list<ShablonText> getShablonTextsEdit(1: ShablonPok shPok, 2: string cdol);

	/**
	 * Получает тексты шаблонов для показателя шаблона и кода должности.
	 */
	list<ShablonText> getShablonTexts(1: ShablonPok shPok, 2: string cdol);

	/**
	 * Добавляет шаблон.
	 */
	i32 addShablonText(1: ShablonText shText);

	/**
	 * Обновляет шаблон.
	 */
	void updateShablonText(1: ShablonText shText);
}

namespace java ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo

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
}

struct VrachInfo {
	 1: i32 pcod;
	 2: string fam;
	 3: string im;
	 4: string ot;
	 5: i16 pol;
	 6: i64 datar;
	 7: i16 obr;
	 8: string snils;
	 9: string idv;
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


service ThriftServerStaticInputVrachInfo extends kmiacServer.KmiacServer {
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
	void DelMrab(1: i32 id);

/**
 * Удаление всех мест работы конкретного врача.
 */
	void ClearVrachMrab(1: i32 vrPcod);

	list<classifier.IntegerClassifier> getPrizndList();
}

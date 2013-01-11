namespace java ru.nkz.ivcgzo.thriftOperation

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

/**
 * Осложнение операции
 */
struct OperationComplication {
	1: optional i32 id;
	2: optional i32 idOper;
	3: optional string nameOsl;
	4: optional i32 pcod;
	5: optional i64 dataz;
}

/**
 * Источник оплаты операции
 */
struct OperationPaymentFund {
	1: optional i32 id;
	2: optional i32 idOper;
	3: optional i32 pcod;
	4: optional i64 dataz;
}

/**
 * Операция
 */
struct Operation {
	1: optional i32 id;
	2: optional i32 vidSt;
	3: optional i32 cotd;
	4: optional i32 idGosp;
	5: optional i32 npasp;
	6: optional string pcod;
	7: optional string nameOper;
	8: optional i64 date;
	9: optional i64 vrem;
	10: optional string predP;
	11: optional string opOper;
	12: optional string material;
	13: optional i32 dlit;
	14: optional i64  dataz;
}

/**
 * Осложнение после анастезии
 */
struct AnesthesiaComplication {
	1: optional i32 id;
	2: optional i32 idAnast;
	3: optional string name;
	4: optional i32 pcod;
	5: optional i64 dataz;
}

/**
 * Источник оплаты анастезии
 */
struct AnesthesiaPaymentFund {
	1: optional i32 id;
	2: optional i32 idAnast;
	3: optional i32 pcod;
	4: optional i64 dataz;
}

/**
 * Анастезия
 */
struct Anesthesia {
	1: optional i32 id;
	2: optional i32 vidSt;
	3: optional i32 cotd;
	4: optional i32 idGosp;
	5: optional i32 npasp;
	6: optional i32 idOper;
	7: optional i32 pcod;
	8: optional string name_an;
	9: optional i64 date;
	10: optional i64 vrem;
	11: optional i64 dataz;
}

service ThriftOperation extends kmiacServer.KmiacServer {
	/**
	 * Возвращает список всех операций для данной записи госпитализации
	 */
	list<Operation> getOperations(1: i32 idGosp)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новую операцию
	 */
	i32 addOperation(1: Operation curOperation)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет информацию о выбранной операции
	 */
	void updateOperation(1: Operation curOperation)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет операцию
	 */	
	void deleteOperation(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Возвращает список всех осложнений данной операции
	 */
	list<OperationComplication> getOperationComplications(1: i32 idOper)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новое осложнение
	 */
	i32 addOperationComplication(1: OperationComplication curCompl)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет информацию об осложнении
	 */
	void updateOperationComplication(1: OperationComplication curCompl)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет информацию об осложнении
	 */
	void deleteOperationComplication(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Возвращает список всех источников оплаты данной операции
	 */
	list<OperationPaymentFund> getOperationPaymentFunds(1: i32 idOper)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новый источник оплаты
	 */
	i32 addOperationPaymentFund(1: OperationPaymentFund curPaymentFund)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет источник оплаты
	 */
	void updateOperationPaymentFund(1: OperationPaymentFund curPaymentFund)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет источник оплаты
	 */
	void deleteOperationPaymentFund(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);
	
	/**
	 * Возвращает список всех назначений анастезии для данной записи госпитализации
	 */
	list<Anesthesia> getAnesthesias(1: i32 idOper)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новое назначений анастезии
	 */
	i32 addAnesthesia(1: Anesthesia curAnesthesia)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет назначений анастезии
	 */
	void updateAnesthesia(1: Anesthesia curAnesthesia)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет назначений анастезии
	 */
	void deleteAnesthesia(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Возвращает список всех осложнений данной анастезии
	 */
	list<AnesthesiaComplication> getAnesthesiaComplications(1: i32 idOper)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новое осложнение после анастезии
	 */
	i32 addAnesthesiaComplication(1: AnesthesiaComplication curCompl)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет осложнение после анастезии
	 */
	void updateAnesthesiaComplication(1: AnesthesiaComplication curCompl)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет осложнение после анастезии
	 */
	void deleteAnesthesiaComplication(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);

	/**
	 * Возвращает список всех источников оплаты данной анастезии
	 */
	list<AnesthesiaPaymentFund> getAnesthesiaPaymentFunds(1: i32 idOper)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Добавляет новый источник оплаты анастезии
	 */
	i32 addAnesthesiaPaymentFund(1: AnesthesiaPaymentFund curPaymentFund)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Обновляет источник оплаты анастезии
	 */
	void updateAnesthesiaPaymentFund(1: AnesthesiaPaymentFund curPaymentFund)
		throws (1: kmiacServer.KmiacServerException kse);
	/**
	 * Удаляет источник оплаты анастезии
	 */
	void deleteAnesthesiaPaymentFund(1: i32 id)
		throws (1: kmiacServer.KmiacServerException kse);
}

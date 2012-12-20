namespace java ru.nkz.ivcgzo.thriftInfomat

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct TPatient {
    1:i32 id;
    2:string surname;
    3:string name;
    4:string middlename;
}

struct TTalon {
    1:optional i32 id;
    2:optional i32 vidp;
    3:optional i64 timep;
    4:optional i64 datap;
    5:optional i32 npasp;
    6:optional i32 pcodSp;
    7:optional string lpuName;
    8:optional string doctorSpec;
    9:optional string doctorFio;
	10:optional i64 dataz;
}

struct TSheduleDay {
	1:optional i64 timeStart;
	2:optional i64 timeEnd;
	3:optional i32 vidp;
	4:optional i32 weekDay;
}

/**
 * Ошибка во время записи пациента
 */
exception ReserveTalonOperationFailedException {
}

/**
 * У пациента уже есть запись к данному врачу на этот день
 */
exception PatientHasSomeReservedTalonsOnThisDay {
}

/**
 * Ошибка во время отмены талона на приём
 */
exception ReleaseTalonOperationFailedException {
}

/**
 * Полис ОМС не найден в базе
 */
exception OmsNotValidException {
}

service ThriftInfomat extends kmiacServer.KmiacServer{
	list<classifier.IntegerClassifier> getPoliclinics()
		throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getSpecialities(1:i32 cpol)
		throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getDoctors(1:i32 cpol, 2:string cdol)
		throws (1: kmiacServer.KmiacServerException kse);
	list<TTalon> getTalons(1: i32 cpol, 2:string cdol, 3:i32 pcod)
		throws (1: kmiacServer.KmiacServerException kse);
	void reserveTalon(1:TPatient pat, 2:TTalon talon)
		throws (1: kmiacServer.KmiacServerException kse,
			2: ReserveTalonOperationFailedException rtofe,
			3: PatientHasSomeReservedTalonsOnThisDay phsrtotd);
	TPatient checkOmsAndGetPatient(1:string oms)
		throws (1: kmiacServer.KmiacServerException kse,
			2: OmsNotValidException onve);
	list<TTalon> getReservedTalon(1:i32 patientId)
		throws (1: kmiacServer.KmiacServerException kse);
	void releaseTalon(1:TTalon talon)
		throws (1: kmiacServer.KmiacServerException kse,
			2: ReleaseTalonOperationFailedException rtofe);
	list<TSheduleDay> getShedule(1:i32 pcod, 2:i32 cpol, 3: string cdol)
		throws (1: kmiacServer.KmiacServerException kse);
	bool isPatientAlreadyReserveTalonOnThisDay(1:TPatient pat, 2:TTalon talon)
		throws (1: kmiacServer.KmiacServerException kse);
	TPatient checkOmsAndGetPatientInCurrentPoliclinic(1:string oms, 2: i32 clpu)
		throws (1: kmiacServer.KmiacServerException kse,
			2: OmsNotValidException onve);
}

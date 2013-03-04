namespace java ru.nkz.ivcgzo.thriftReception

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Patient {
    1:i32 id;
    2:string surname;
    3:string name;
    4:string middlename;
	5:i32 idPvizit;
    6:optional i64 birthdate;
    7:optional string omsSer;
    8:optional string omsNum;
}

struct Vidp {
	1:i32 pcod;
	2:string name;
	3:optional string vcolor;
}

struct Talon {
	1:i32 id;
	2:i32 ntalon;
	3:i32 vidp;
	4:optional i64 timep;
	5:optional i64 datap;
	6:optional i32 npasp;
    7:optional i64 dataz;
    8:optional i32 prv;
	9:optional i32 pcodSp;
    10:optional string doctorSpec;
    11:optional string doctorFio;
}

/**
 * Пациент не найден
 */
exception PatientNotFoundException {
}

/**
 * Поликлиника не найдена
 */
exception PoliclinicNotFoundException {
}

/**
 * Специальность не найдена
 */
exception SpecNotFoundException {
}

/**
 * Врач не найден
 */
exception VrachNotFoundException {
}

/**
 * Вид приёма не найден
 */
exception VidpNotFoundException {
}

/**
 * Талоны не найдены
 */
exception TalonNotFoundException {
}

/**
 * Ошибка во время записи пациента
 */
exception ReserveTalonOperationFailedException {
}

/**
 * Ошибка во время отмены талона
 */
exception ReleaseTalonOperationFailedException {
}

/**
 * У пациента уже есть запись к этому врачу на этот день
 */
exception PatientHasSomeReservedTalonsOnThisDay {
}

service ThriftReception extends kmiacServer.KmiacServer {

    /**
     * Выбор пациента по полису ОМС
     */
    Patient getPatient(1:string omsSer, 2:string omsNum) throws (
            1: kmiacServer.KmiacServerException kse, 2: PatientNotFoundException pnfe);

    /**
     * Возвращает список всех поликлиник, для которых сгенерированны талоны
     */    
    list<classifier.IntegerClassifier> getPoliclinic() throws (1: kmiacServer.KmiacServerException kse,
            2: PoliclinicNotFoundException pnfe);

    /**
     * Возвращает список всех специальностей в выбранной поликлинике, для которых
     * сгенерированны талоны
     */
    list<classifier.StringClassifier> getSpec(1:i32 cpol) throws (1: kmiacServer.KmiacServerException kse,
            2: SpecNotFoundException snfe);

    /**
     * Возвращает список всех врачей выбранной поликлиники и специальности,
     * для которых сгенерированны талоны
     */
    list<classifier.IntegerClassifier> getVrach(1:i32 cpol, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse,
	    2: VrachNotFoundException vnfe);

    /**
     * Возвращает список всех видов приёма
     */
    list<Vidp> getVidp() throws (1: kmiacServer.KmiacServerException kse,
            2: VidpNotFoundException vnfe);

    /**
     * Возвращает список всех талонов для выбранного врача
     */
    list<Talon> getTalon(1:i32 cpol, 2:string cdol, 3:i32 pcod, 4:i32 patientId, 5:bool secOnly) throws (1: kmiacServer.KmiacServerException kse,
            2: TalonNotFoundException tnfe);

    /**
     * Возвращает список всех талонов, занятых данным пациентом 
     */
    list<Talon> getReservedTalons(1:i32 cpol, 2:string cdol, 3:i32 doctorId, 4:i32 patientId) throws (
            1:kmiacServer.KmiacServerException kse, 2: TalonNotFoundException tnfe);

    /*
     * Запись пациента на приём (изменение выбранного талона)
     */
    void reserveTalon(1: Patient pat, 2:Talon talon) throws (1: kmiacServer.KmiacServerException kse,
            2: ReserveTalonOperationFailedException rtofe, 3: PatientHasSomeReservedTalonsOnThisDay phsrtotd); 
    
    /*
     * Отменяет выбор талона (освобождает талон)
     */
    void releaseTalon(1:Talon talon) throws (1: kmiacServer.KmiacServerException kse,
            2: ReleaseTalonOperationFailedException rtofe);
}


namespace java ru.nkz.ivcgzo.thriftHospital

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct TSimplePatient{
	1:optional i32 patientId;
    2:optional i32 idGosp;
	3:optional string surname;
	4:optional string name;
	5:optional string middlename;
	6:optional i64 birthDate;
	7:optional i64 arrivalDate;
	8:optional i32 departmentCod;
	9:optional i32 npal;
	10:optional i32 nist;
}

struct TPatient{
	1:i32 patientId;
	2:i32 gospitalCod;
	3:i64 birthDate;
	4:string surname;
	5:string name;
	6:string middlename;
	7:string gender;
	8:i32 nist;
	9:i32 status;
	10:string oms;
	11:string dms;
	12:string job;
	13:i32 chamber;
	14:string registrationAddress;
	15:string realAddress;	
}

struct TPriemInfo {
	1:string pl_extr;
	2:i64 datap;
	3:i64 dataosm;
	4:string naprav;
	5:string nOrg;
	6:string diagN;
	7:string diagNtext;
	8:string diagP;
	9:string diagPtext;
	10:string t0c;
	11:string ad;
	12:bool nal_z;
	13:bool nal_p;
	14:i32 alkg;
	15:string vid_tran;
	16:string jalob;
}

struct TMedicalHistory{
    1:i32 id;
	2:i32 idGosp;
	3:optional string jalob;
	4:optional string morbi;
	5:optional string statusPraesense;
	6:optional string statusLocalis;
	7:optional string fisicalObs;
	8:i32 pcodVrach;
	9:i64 dataz;
	10:i64 timez;
}

struct TLifeHistory{
	1:i32 id;
	2:optional string allerg;
	3:optional string farmkol;
	4:optional string vitae;
}

struct TDiagnosis{
	1:i32 id
    2:i32 idGosp
    3:optional string cod;
	4:optional string medOp;
	5:optional i64 dateUstan;
    6:optional i32 prizn;
    7:optional i32 vrach;
	/* Поле для customTable в swinge - в таблице Бд его нет*/
	8:optional string diagName;
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
}

/**
 * Пациент с такими данными не найден.
 */
exception PatientNotFoundException {
}

/**
 * История жизни с такими данными не найдена.
 */
exception LifeHistoryNotFoundException {
}

/**
 * Медицинская история с такими данными не найдена.
 */
exception MedicalHistoryNotFoundException {
}

/**
 * Диагноз данными не найден.
 */
exception DiagnosisNotFoundException {
}

/*
 * Информация из приёмного отделения не найдена
 */
exception PriemInfoNotFoundException {
}

service ThriftHospital extends kmiacServer.KmiacServer{
	list<TSimplePatient> getAllPatientForDoctor(1:i32 doctorId, 2:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	list<TSimplePatient> getAllPatientFromOtd(1:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);	
	TPatient getPatientPersonalInfo(1:i32 patientId) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	TPriemInfo getPriemInfo(1:i32 idGosp) throws (1: PriemInfoNotFoundException pinfe,
		2:kmiacServer.KmiacServerException kse);
	void updatePatientChamberNumber(1:i32 gospId, 2:i32  chamberNum) throws (1:kmiacServer.KmiacServerException kse);
	
	TLifeHistory getLifeHistory(1:i32 patientId) throws (1:LifeHistoryNotFoundException lhnfe,
		2:kmiacServer.KmiacServerException kse);
	void updateLifeHistory(1:TLifeHistory lifeHist) throws (1:kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getShablonNames(1:i32 cspec, 2:i32 cslu, 3:string srcText)
		throws (1:kmiacServer.KmiacServerException kse);
	Shablon getShablon(1:i32 idSh) throws (1:kmiacServer.KmiacServerException kse);

	list<TMedicalHistory> getMedicalHistory(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse,
		2: MedicalHistoryNotFoundException mhnfe);
	i32 addMedicalHistory(1:TMedicalHistory medHist) throws (1:kmiacServer.KmiacServerException kse);
	void deleteMedicalHistory(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse);
	
	void addPatientToDoctor(1:i32 gospId, 2:i32 doctorId) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);

    list<TDiagnosis> getDiagnosis(1:i32 gospId) throws (1:DiagnosisNotFoundException dnfe
		2:kmiacServer.KmiacServerException kse);
    i32 addDiagnosis(1:TDiagnosis inDiagnos) throws (1:kmiacServer.KmiacServerException kse);
    void updateDiagnosis(1:TDiagnosis inDiagnos) throws (1:kmiacServer.KmiacServerException kse);
    void deleteDiagnosis(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);

	void disharge(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse);
	
/*Классификаторы*/
	
	/**
	* Классификатор социального статуса (N_azj(pcod))
	 */
	list<classifier.StringClassifier> getAzj();
	/**
	* Классификатор исхода заболевания (N_ap0(pcod))
	 */
	list<classifier.IntegerClassifier> getAp0();
	/**
	* Классификатор результата лечения (N_aj0(pcod))
	 */
	list<classifier.IntegerClassifier> getAj0();
}

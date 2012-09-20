namespace java ru.nkz.ivcgzo.thriftHospital

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct TSimplePatient{
	1:optional i32 patientId;
    2:optional i32 id_gosp;
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

struct TDoctor{
	1:i32 doctorId;
	2:string surname;
	3:string name;
	4:string middlename;	
}

struct TMedicalHistory{
    1:i32 id;
    2:i32 id_gosp;
    3:string text;
    4:i64 dataz;
}

struct TDiagnosis{
	1:i32 id
    2:i32 id_gosp
    3:string diag;
	4:i32 prizn;
	5:i32 ustan;
    6:string named;
    7:i64 dataz;
}

struct TAllDiagnosis{
	1:TDiagnosis mainDiagnosis;
	2:list<TDiagnosis> accompDiagnosis;
	3:list<TDiagnosis> complication;
}

struct TComplaint{
    1:i32 id;
    2:i32 id_gosp;
	3:i64 date;
    4:i64 time;
	5:string text;
}

struct TClassifier {
	1: i32 id;
	2: string name;
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
 * История болезни с такими данными не найдена.
 */
exception DesiaseHistoryNotFoundException {
}

/**
 * Объективный статус с такими данными не найден.
 */
exception ObjectiveStateNotFoundException {
}

/**
 * Специальный статус с такими данными не найден.
 */
exception SpecialStateNotFoundException {
}

/**
 * Жалоба с такими данными не найдена.
 */
exception ComplaintNotFoundException {
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
	void updatePatientChamberNumber(1:i32 gospId, 2:i32  chamberNum);
	list<classifier.IntegerClassifier> getShablonNames(1: i32 cspec, 2: i32 cslu, 3: string srcText)
		throws (1: kmiacServer.KmiacServerException kse);
	Shablon getShablon(1: i32 idSh) throws (1: kmiacServer.KmiacServerException kse);
		
	void addPatientToDoctor(1:i32 gospId, 2:i32 doctorId);	
	TMedicalHistory getLifeHistory(1:i32 gospId) throws (1:LifeHistoryNotFoundException lhnfe);
    TMedicalHistory getDesiaseHistory(1:i32 gospId) throws (1:DesiaseHistoryNotFoundException dhnfe);
    TMedicalHistory getObjectiveState(1:i32 gospId) throws (1:ObjectiveStateNotFoundException osnfe);
    TMedicalHistory getSpecialState(1:i32 gospId) throws (1:SpecialStateNotFoundException ssnfe);
	void updateLifeHistory(1:i32 gospId, 2:TMedicalHistory lifeHistory);
	void updateDesiaseHistory(1:i32 gospId, 2:TMedicalHistory desiaseHistory);
	void updateObjectiveState(1:i32 gospId, 2:TMedicalHistory objectiveState);
	void updateSpecialState(1:i32 gospId, 2:TMedicalHistory specialState);
    
    void deleteLifeHistory(1:i32 gospId);
    void deleteDesiaseHistory(1:i32 gospId);
    void deleteObjectiveState(1:i32 gospId);
    void deleteSpecialState(1:i32 gospId);
    
	list<TComplaint> getComplaints(1:i32 gospId) throws (1:ComplaintNotFoundException cnfe);
	i32 addComplaint(1:TComplaint complaint);
    void updateComplaint(1:i32 id,2:TComplaint complaint);
	void deleteComplaint(1:i32 id);

    TDiagnosis getMainDiagnosis(1:i32 gospId) throws (1:DiagnosisNotFoundException dnfe);
    i32 addMainDiagnosis(1: TDiagnosis inDiagnos);
    void updateMainDiagnosis(1:i32 id, 2: TDiagnosis inDiagnos);
    void deleteMainDiagnosis(1:i32 id);

    list<TDiagnosis> getAccompDiagnosis(1:i32 gospId) throws (1:DiagnosisNotFoundException dnfe);
    i32 addAccompDiagnosis(1: TDiagnosis inDiagnos);
    void updateAccompDiagnosis(1:i32 id, 2: TDiagnosis inDiagnos);
    void deleteAccompDiagnosis(1:i32 id);

    list<TDiagnosis> getCopmlication(1:i32 gospId) throws (1:DiagnosisNotFoundException dnfe);
    i32 addCopmlication(1: TDiagnosis inDiagnos);
    void updateCopmlication(1:i32 id, 2: TDiagnosis inDiagnos);
    void deleteCopmlication(1:i32 id);
	
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

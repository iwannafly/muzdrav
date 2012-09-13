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

struct TAddress{
	1:string city;
	2:string street;
	3:string house;
}

struct TPatient{
	1:i32 patientId;
	2:i32 gospitalCod;
	3:i64 birthDate;
	4:string surname;
	5:string name;
	6:string middlename;
	7:i32 gender;
	8:string ambulCardNumber;
	9:i32 liver;
	10:i32 status;
	11:string oms;
	12:string dms;
	13:string job;
	14:i32 chumber;
	15:TAddress registrationAddress;
	16:TAddress realAddress;	
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

service ThriftHospital extends kmiacServer.KmiacServer{
	list<TSimplePatient> getAllPatientForDoctor(1:i32 doctorId, 2:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	list<TSimplePatient> getAllPatientFromOtd(1:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);	
	TPatient getPatientPersonalInfo(1:i32 patientId) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	void updatePatientChamberNumber(1:i32 gospId, 2:i32  chamberNum);
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

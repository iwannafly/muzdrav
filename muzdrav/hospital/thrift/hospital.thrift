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
	14:string alkg;
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
	8:optional i32 pcodVrach;
	9:optional i64 dataz;
	10:optional i64 timez;
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
	4: i32 id;
}

struct DopShablon {
	1: i32 id;
	2: i32 nShablon;
	3: string name;
	4: string text;
}

struct Zakl {
	1: optional i32 ishod;
	2: optional i32 result;
	3: optional i64 datav;
	4: optional i64 vremv;
	5: optional string sostv;
	6: optional string recom;
	7: optional i32 idGosp;
	8: optional i32 newOtd;
	9: optional i32 vidOpl;
	10: optional i32 vidPom;
	11: optional i32 ukl;
}

struct TStage {
	1: optional i32 id;
	2: optional i32 idGosp;
	3: optional i32 stage;
	4: optional string mes;
	5: optional i64 dateStart;
	6: optional i64 dateEnd;
	7: optional i32 ukl;
	8: optional i32 ishod;
	9: optional i32 result;
	10: optional i64 timeStart;
	11: optional i64 timeEnd;
}
struct TRdIshod{
   1: optional i32 npasp;
   2: optional i32 ngosp;
   3: optional i32 id_berem;
   4: optional i32 id;
   5: optional double oj;
   6: optional i32 hdm;
   7: optional i32 polpl;
   8: optional i32 predpl;
   9: optional i32 vidpl;
  10: optional i32 serd;
  11: optional i32 serd1;
  12: optional i32 serdm;
  13: optional i32 chcc;
  14: optional i32 pozpl;
  15: optional i32 mesto;
  16: optional string deyat;
  17: optional string shvat;
  18: optional string vody;
  19: optional string kashetv;
  20: optional string poln;
  21: optional string potugi;
  22: optional i32 posled;
  23: optional string vremp;
  24: optional i32 obol;
  25: optional i32 pupov;
  26: optional i32 obvit;
  27: optional string osobp;
  28: optional i32 krov;
  29: optional bool psih;
  30: optional string obezb;
  31: optional i32 eff;
  32: optional string prr1;
  33: optional string prr2;
  34: optional string prr3;
  35: optional string prinyl;
  36: optional string osmposl;
  37: optional string vrash;
  38: optional string akush;
  39: optional i64 datarod;
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

/*
 * Код МЭС не сущестdует
 */
exception MesNotFoundException {
}
exception PrdIshodNotFoundException{
}

service ThriftHospital extends kmiacServer.KmiacServer{
	list<TSimplePatient> getAllPatientForDoctor(1:i32 doctorId, 2:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	list<TSimplePatient> getAllPatientFromOtd(1:i32 otdNum) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);	
	TPatient getPatientPersonalInfo(1:i32 patientId, 2:i32 idGosp) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	TPriemInfo getPriemInfo(1:i32 idGosp) throws (1: PriemInfoNotFoundException pinfe,
		2:kmiacServer.KmiacServerException kse);
	void updatePatientChamberNumber(1:i32 gospId, 2:i32 chamberNum, 3:i32 profPcod)
		throws (1:kmiacServer.KmiacServerException kse);
	
	TLifeHistory getLifeHistory(1:i32 patientId) throws (1:LifeHistoryNotFoundException lhnfe,
		2:kmiacServer.KmiacServerException kse);
	void updateLifeHistory(1:TLifeHistory lifeHist) throws (1:kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getShablonNames(1:i32 cspec, 2:i32 cslu, 3:string srcText)
		throws (1:kmiacServer.KmiacServerException kse);
	Shablon getShablon(1:i32 idSh) throws (1:kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getDopShablonNames(1:i32 nShablon, 2:string srcText)
		throws (1:kmiacServer.KmiacServerException kse);
	DopShablon getDopShablon(1:i32 idSh) throws (1:kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getShablonDiagnosis(1:i32 cspec, 2:i32 cslu, 3:string srcText)
		throws (1:kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getShablonBySelectedDiagnosis(
		1:i32 cspec, 2:i32 cslu, 3:string diag, 4:string srcText) throws (1:kmiacServer.KmiacServerException kse);

	list<TMedicalHistory> getMedicalHistory(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse,
		2: MedicalHistoryNotFoundException mhnfe);
	i32 addMedicalHistory(1:TMedicalHistory medHist) throws (1:kmiacServer.KmiacServerException kse);
	void updateMedicalHistory(1:TMedicalHistory medHist) throws (1:kmiacServer.KmiacServerException kse);
	void deleteMedicalHistory(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);
	
	void addPatientToDoctor(1:i32 gospId, 2:i32 doctorId, 3:i32 stationType) throws (1:PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);

    list<TDiagnosis> getDiagnosis(1:i32 gospId) throws (1:DiagnosisNotFoundException dnfe
		2:kmiacServer.KmiacServerException kse);
    i32 addDiagnosis(1:TDiagnosis inDiagnos) throws (1:kmiacServer.KmiacServerException kse);
    void updateDiagnosis(1:TDiagnosis inDiagnos) throws (1:kmiacServer.KmiacServerException kse);
    void deleteDiagnosis(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);

	void disharge(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse);
	void addZakl(1:Zakl zakl) throws (1:kmiacServer.KmiacServerException kse);

	list<TStage> getStage(1:i32 idGosp) throws (1:kmiacServer.KmiacServerException kse);
	i32 addStage(1:TStage stage) throws (1:kmiacServer.KmiacServerException kse,
		2: MesNotFoundException mnfe);
	void updateStage(1:TStage stage) throws (1:kmiacServer.KmiacServerException kse,
		2: MesNotFoundException mnfe);
	void deleteStage(1:i32 idStage) throws (1:kmiacServer.KmiacServerException kse);

	string printHospitalDiary(1: i32 idGosp, 2: i64 dateStart, 3: i64 dateEnd)
		throws (1:kmiacServer.KmiacServerException kse);	
	string printHospitalSummary(1: i32 idGosp, 2: string lpuInfo, 3: TPatient patient)
		throws (1:kmiacServer.KmiacServerException kse);
	
/*Классификаторы*/
	
	/**
	* Классификатор социального статуса (N_azj(pcod))
	*/
	list<classifier.StringClassifier> getAzj() throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор исхода заболевания (N_ap0(pcod))
	*/
	list<classifier.IntegerClassifier> getAp0() throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор результата лечения (N_aq0(pcod))
	*/
	list<classifier.IntegerClassifier> getAq0() throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор типа стационара (N_tip0(pcod))
	*/
	list<classifier.IntegerClassifier> getStationTypes(1: i32 cotd) throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор этапов лечения
	*/
	list<classifier.IntegerClassifier> getStagesClassifier(1: i32 idGosp)
		throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор отделений текущего лпу
	*/
	list<classifier.IntegerClassifier> getOtd(1: i32 lpu)
		throws (1:kmiacServer.KmiacServerException kse);
/* ���������������*/
	TRdIshod getRdIshodInfo(1:i32 npasp, 2:i32 ngosp) throws (1:kmiacServer.KmiacServerException kse);
    void addRdIshod(1:TRdIshod RdIs) throws (1:kmiacServer.KmiacServerException kse);
    void updateRdIshod(1:TRdIshod RdIs) throws (1:kmiacServer.KmiacServerException kse);
    void deleteRdIshod(1:i32 npasp, 2:i32 ngosp) throws (1:kmiacServer.KmiacServerException kse);
	
}

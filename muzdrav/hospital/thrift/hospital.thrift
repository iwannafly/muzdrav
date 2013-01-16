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
	11: optional double ukl;
}

struct TStage {
	1: optional i32 id;
	2: optional i32 idGosp;
	3: optional i32 stage;
	4: optional string mes;
	5: optional i64 dateStart;
	6: optional i64 dateEnd;
	7: optional double ukl;
	8: optional i32 ishod;
	9: optional i32 result;
	10: optional i64 timeStart;
	11: optional i64 timeEnd;
}

struct TRdIshod {
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
  15: optional string mesto;
  16: optional string deyat;
  17: optional string shvat;
  18: optional string vody;
  19: optional string kashetv;
  20: optional string poln;
  21: optional string potugi;
  22: optional i32 posled;
  23: optional string vremp;
  24: optional string obol;
  25: optional i32 pupov;
  26: optional string obvit;
  27: optional string osobp;
  28: optional i32 krov;
  29: optional bool psih;
  30: optional string obezb;
  31: optional i32 eff;
  32: optional string prr1;
  33: optional string prr2;
  34: optional string prr3;
  35: optional i32 prinyl;
  36: optional i32 osmposl;
  37: optional i32 vrash;
  38: optional i32 akush;
  39: optional i64 daterod;
  40: optional i32 srok;
  41: optional i32 ves;
  42: optional i32 vespl; 
  43: optional string detmesto;
}
struct RdSlStruct{
        1: optional  i32 id;
        2: optional i32 npasp;
        3: optional i64 datay;
	4: optional i64 dataosl;
	5: optional i32 abort;
	6: optional i32 shet;
	7: optional i64 dataM;
	8: optional i32 yavka1;
	9: optional i32 ishod;
	10: optional i64 Datasn; 
	11: optional i64 DataZs;
        12: optional i32 kolrod;
	13: optional i32 deti;
	14: optional bool kont;
	15: optional double vesd;
	16: optional i32 dsp;
	17: optional i32 dsr;
	18: optional i32 dTroch;
	19: optional i32 cext;
	20: optional i32 indsol;
	21: optional i32 prmen;
	22: optional i64 dataz;
	23: optional i64 datasert;
	24: optional string nsert;
	25: optional string ssert;
	26: optional string oslab;
	27: optional i32 plrod;
	28: optional string prrod;
	29: optional i32 vozmen;
	30: optional i32 oslrod;
	31: optional i32 polj;
	32: optional i64 dataab;
	33: optional i32 srokab;
	34: optional i32 cdiagt;
	35: optional i32 cvera;
	36: optional i32 id_pvizit;
        37: optional i32 rost;
        38: optional bool eko;
        39: optional bool rub;
        40: optional bool predp;
        41: optional i32 osp;
        42: optional i32 cmer;
}
struct RdDinStruct{
	1: optional i32 id_rd_sl;
	2: optional i32 id_pvizit;
	3: optional i32 npasp;
	4: optional i32 srok;
	5: optional i32 grr;
	6: optional i32 ball;
	7: optional i32 oj;
	8: optional i32 hdm;
	9: optional string dspos;
	10: optional i32 art1;
	11: optional i32 art2;
	12: optional i32 art3;
	13: optional i32 art4;
	14: optional i32 spl;
	15: optional i32 oteki;
	16: optional i32 chcc;
	17: optional i32 polpl;
	18: optional i32 predpl;
	19: optional i32 serd;
	20: optional i32 serd1;
	21: optional i32 id_pos;
	22: optional double ves;
        23: optional i32 ngosp;
        24: optional i32 pozpl;
        25: optional i32 vidpl;
}
/*. Rd_Inf*/
struct RdInfStruct{
	1: optional i32 npasp;
	2: optional i32 obr;
	3: optional i32 sem;
	4: optional i32 vOtec;
	5: optional string grotec;
	6: optional string phOtec;
	7: optional i64 dataz;
	8: optional string fioOtec;
	9: optional string mrOtec;
	10: optional string telOtec;
	11: optional i32 vredOtec;
	12: optional i32 osoco;
	13: optional i32 uslpr;
        14: optional string zotec;
}

struct TRd_Novor {
	 1: i32 npasp;
	 2: i32 nrod;
	 3: optional string timeon;
	 4: optional i32 kolchild;
	 5: optional i32 nreb;
	 6: i32 massa;
	 7: i32 rost;
	 8: optional i32 apgar1;
	 9: optional i32 apgar5;
	10: optional bool krit1;
	11: optional bool krit2;
	12: optional bool krit3;
	13: optional bool krit4;
	14: optional bool mert;
	15: optional bool donosh;
	16: i64 datazap;
}

struct TRd_Svid {
	1: i32 npasp;
	2: i32 ndoc;
	3: i64 dateoff;
	4: string famreb;
	5: i32 svidvrach;
}

struct TPatientCommonInfo {
	 1: i32 npasp;
	 2: string full_name;
	 3: i64 datar;
	 4: string pol;
	 5: string jitel;
	 6: string adp_obl;
	 7: string adp_gorod;
	 8: string adp_ul;
	 9: string adp_dom;
	10: string adp_kv;
}

struct TRd_SMPK {
   1: i32 npasp;
   2: i32 nrod;
   3: i32 nnov;
   4: optional i32 ndoc;
   5: optional i64 dateoff;
   6: optional i32 prvid;
   7: optional i32 sm1;
   8: optional i64 datas;
   9: optional string times;
  10: optional i32 kodmest;
  11: optional i32 kolchild;
  12: optional i32 b1;
  13: optional i32 b2;
  14: optional i32 b3;
  15: optional i32 b4;
  16: optional i32 b5;
  17: optional i32 nreb;
  18: optional string dsbasic;
  19: optional i32 sostp;
  20: optional i32 sm2;
  21: optional i32 sm3;
  22: optional string psm1;
  23: optional string psm2;
  24: optional string psm3;
  25: optional string psm4;
  26: optional string psm5;
  27: optional string fioreb;
  28: optional string fiozap;
  29: optional i32 sm4;
  30: optional i32 sm5;
  31: optional i64 datazap;
}

struct TRd_ACCOMP {
   1: i32 nnov;
   2: i32 naccomp;
   3: string accomp;
   4: optional i32 priz;
}

struct TRd_PAT {
   1: i32 nnov;
   2: i32 npat;
   3: string pat;
   4: optional i32 priz;
}

struct TRd_DIAGnr {
   1: i32 nnov;
   2: i32 ndiag;
   3: string diag;
   4: optional i32 priz;
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

/**
 * Информация из приёмного отделения не найдена
 */
exception PriemInfoNotFoundException {
}

/**
 * Код МЭС не сущестdует
 */
exception MesNotFoundException {
}

exception PrdIshodNotFoundException{
}

/**
 * Свидетельство о рождении/перинатальной смерти не найдено
 */
exception ChildDocNotFoundException{
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
	string printHospitalDeathSummary(1: i32 idGosp, 2: string lpuInfo, 3: TPatient patient)
		throws (1:kmiacServer.KmiacServerException kse);		
	string printHospitalSummary(1: i32 idGosp, 2: string lpuInfo, 3: TPatient patient)
		throws (1:kmiacServer.KmiacServerException kse);
	
/* Классификаторы */
	
	/**
	* Классификатор социального статуса (N_azj(pcod))
	*/
	list<classifier.StringClassifier> getAzj()
		throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор исхода заболевания (N_ap0(pcod))
	*/
	list<classifier.IntegerClassifier> getAp0()
		throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор результата лечения (N_aq0(pcod))
	*/
	list<classifier.IntegerClassifier> getAq0()
		throws (1:kmiacServer.KmiacServerException kse);
	/**
	* Классификатор типа стационара (N_tip0(pcod))
	*/
	list<classifier.IntegerClassifier> getStationTypes(1: i32 cotd)
		throws (1:kmiacServer.KmiacServerException kse);
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
		
/* Родовспоможение */
	list<classifier.IntegerClassifier> get_s_vrach() throws (1:kmiacServer.KmiacServerException kse);
	
/* Новорождённый */
	list<classifier.IntegerClassifier> getChildBirths(1:i64 BirthDate) throws (1:kmiacServer.KmiacServerException kse);
	void addChildInfo(1:TRd_Novor Child)
		throws (1:kmiacServer.KmiacServerException kse, 2:PatientNotFoundException pnfe);
	TRd_Novor getChildInfo(1:i32 npasp)
		throws (1:kmiacServer.KmiacServerException kse, 2:PatientNotFoundException pnfe);
    void updateChildInfo(1:TRd_Novor Child)
    	throws (1:kmiacServer.KmiacServerException kse, 2:PatientNotFoundException pnfe);
    
	/**
	 * Получение следующего доступного номера свидетельства о рождении/перинатальной смерти новорождённого
	 * @return Возвращает номер свидетельства о рождении/перинатальной смерти
	 */
    i32 getNextChildDocNum();
    /**
	 * Добавление в БД свидетельства о рождении/перинатальной смерти новорождённого
	 * @return Возвращает номер свидетельства
	 */
	i32 addChildDocument(1:TRd_Svid ChildDocument)
		throws (1:kmiacServer.KmiacServerException kse, 2:PatientNotFoundException pnfe);
    TRd_Svid getChildDocument(1:i32 npasp)
    	throws (1:kmiacServer.KmiacServerException kse, 2:ChildDocNotFoundException cdnfe);
    TRd_Svid getChildDocumentByDoc(1:i32 ndoc)
    	throws (1:kmiacServer.KmiacServerException kse, 2:ChildDocNotFoundException cdnfe);
    void updateChildDocument(1:TRd_Svid ChildDocument)
    	throws (1:kmiacServer.KmiacServerException kse, 2:ChildDocNotFoundException cdnfe);
    string printChildBirthDocument(1:i32 ndoc)
    	throws (1:kmiacServer.KmiacServerException kse, 2:ChildDocNotFoundException cdnfe);
    string printChildDeathDocument(1:i32 ndoc)
    	throws (1:kmiacServer.KmiacServerException kse, 2:ChildDocNotFoundException cdnfe);
    string printChildBlankDocument(1:bool isLiveChild)
    	throws (1:kmiacServer.KmiacServerException kse);
    TPatientCommonInfo getPatientCommonInfo(1:i32 npasp)
    	throws (1:kmiacServer.KmiacServerException kse, 2:PatientNotFoundException pnfe);

/*DispBer*/
	TRdIshod getRdIshodInfo(1:i32 npasp, 2:i32 ngosp) throws (1:kmiacServer.KmiacServerException kse);
    void addRdIshod(1:i32 npasp, 2:i32 ngosp) throws (1:kmiacServer.KmiacServerException kse);
    void updateRdIshod(1:TRdIshod RdIs) throws (1:kmiacServer.KmiacServerException kse);
    void deleteRdIshod(1:i32 npasp, 2:i32 ngosp) throws (1:kmiacServer.KmiacServerException kse);
	RdSlStruct getRdSlInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	RdDinStruct getRdDinInfo(1:i32 npasp,2: i32 ngosp) throws (1: kmiacServer.KmiacServerException kse);
	RdInfStruct getRdInfInfo (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddRdSl(1:RdSlStruct rdSl) throws (1: kmiacServer.KmiacServerException kse);
	void AddRdDin(1:i32 npasp,2: i32 ngosp) throws (1: kmiacServer.KmiacServerException kse);
 
	void DeleteRdSl(1:i32 id_pvizit,2:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	void DeleteRdDin(1:i32 ngosp) throws (1: kmiacServer.KmiacServerException kse);

	void UpdateRdSl(1: RdSlStruct Dispb) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdDin(1: RdDinStruct Din) throws (1: kmiacServer.KmiacServerException kse);
	void UpdateRdInf(1: RdInfStruct inf) throws (1: kmiacServer.KmiacServerException kse);

	void AddRdInf(1:RdInfStruct rdInf) throws (1: kmiacServer.KmiacServerException kse);

	void DeleteRdInf(1:i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
}

namespace java ru.nkz.ivcgzo.thriftOutputInfo

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct InputAuthInfo {
	1: optional i32 userId;
	2: optional string cpodr_name;
	3: optional string clpu_name;
    4: optional i32 cpodr;
    5: optional i32 clpu;
}

struct InputSvodVed {
    1: string dateb;
    2: string datef;
    3: i32 vozcat;
}

struct InputFacZd {
    1: string dateb;
    2: string datef;
    3: i32 vozcat;
    4: i32 kvar;
}

struct InputPasUch {
    1: string dateb;
    2: string datef;
    3: i32 uchnum;
}

struct VrachInfo {
	1: i32 pcod;
	2: string fam;
	3: string im;
	4: string ot;
	5: string cdol;
} 

struct VrachTabel {
	1: i32 pcod;
	2: string cdol;
	3: i64 datav;
	4: double timep;
	5: double timed;
	6: double timeda;
	7: double timeprf;
	8: double timepr;
	9: string nuch1;
	10: string nuch2;
	11: string nuch3;
	12: i32 id;
}

struct UchastokInfo {
	1: optional string fam;
	2: optional string im;
	3: optional string ot;
	4: optional i32 pcod;	
}

struct UchastokNum {
	1: optional i32 pcod;	
	2: optional i32 cpol;
	3: optional i32 uch;
	4: optional i32 id; 
}

struct InputPlanDisp {
    1: i32 kpolik;
    2: string namepol;
    3: string daten;
    4: string datek;
    5: optional string uchas;
    6: i32 clpu;
}

struct InputStructPosAuth {
	1: optional i32 userId;
	2: optional string cpodr_name;
	3: optional string clpu_name;
}

struct InputStructPos {
    1: string date1;
    2: string date2;
}


/**
 * No information
 */
exception VINotFoundException {
}

/**
 * Already exists
 */
exception VTDuplException {
}

/*
 * No information
 */
exception VTException {
}

/**
 * No information
 */
exception UchException {
}

service ThriftOutputInfo extends kmiacServer.KmiacServer {
    
    /**
     * Возвращает список врачей, ведущих прием
     * @param pcod - Уникальный код специалиста
     * @return список thrift-объектов, содержащих информацию о врачах
     */
    list<VrachInfo> getVrachTableInfo(1:i32 cpodr) throws (1: VINotFoundException vinfe,
		2:kmiacServer.KmiacServerException kse);
    

     /**
     * Возвращает время работы врачей
     */
    list<VrachTabel> getVrachTabel(1:i32 pcod) throws (1: VTException vte, 2:VTDuplException vtde,
		3:kmiacServer.KmiacServerException kse);
		
		
    /**
     * Добавляет информацию о враче
     */
    i32 addVT(1:VrachTabel vt) throws (1: VTException vte, 2:VTDuplException vtde,
		3:kmiacServer.KmiacServerException kse);

    /**
     * Обновляет информацию о враче
     */
	void updateVT(1:VrachTabel vt) throws (1:kmiacServer.KmiacServerException kse); 
    
	/**
     * Удаляет информацию о враче
     */
	void deleteVT(1:i32 vt);
		
	list<UchastokInfo> getUch(1:i32 cpol) throws (1: UchException uche, 2:kmiacServer.KmiacServerException kse);
	
	list<UchastokNum> getUchNum(1:i32 pcod) throws (1: UchException uche, 2:kmiacServer.KmiacServerException kse);

	i32 addUchNum(1:UchastokNum uchNum) throws (1: kmiacServer.KmiacServerException kse);
		
	void updateUchNum(1:UchastokNum uchNum) throws (1:kmiacServer.KmiacServerException kse);
	
	void deleteUchNum(1:i32 uchNum);
	
    string printPlanDisp(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printNoVipPlanDisp(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printSvedDispObs(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printOtDetPol(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printSvodVed(1: InputAuthInfo iaf 2: InputSvodVed isv) throws (1: kmiacServer.KmiacServerException kse);

    string printFacZd(1: InputAuthInfo iaf 2: InputFacZd ifz) throws (1: kmiacServer.KmiacServerException kse);

    string printPasUch(1: InputAuthInfo iaf 2: InputPasUch ipu) throws (1: kmiacServer.KmiacServerException kse);
   /**
    * Сводки по форме 39
    */
    string printDnevVr() throws (1: kmiacServer.KmiacServerException kse);

	string printStructPos(1: InputStructPosAuth ispa 2: InputStructPos isp) throws (1: kmiacServer.KmiacServerException kse);

    string nagrvr(1:i32 cpol)  throws (1: kmiacServer.KmiacServerException kse); 
}
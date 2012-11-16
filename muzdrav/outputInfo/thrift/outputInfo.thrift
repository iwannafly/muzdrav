namespace java ru.nkz.ivcgzo.thriftOutputInfo

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"

struct InputAuthInfo {
	1: optional i32 userId;
	2: optional string cpodr_name;
	3: optional string clpu_name;
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

struct InputPlanDisp{
    1: i32 kpolik;
    2: string namepol;
    3: string daten;
    4: string datek;
    5: optional string uchas;
    6: i32 clpu;
}


/**
 * РРЅС„РѕСЂРјР°С†РёСЏ РѕС‚СЃС‚СѓС‚СЃС‚РІСѓРµС‚
 */
exception VINotFoundException {
}

/**
 * РўР°РєР°СЏ СѓР¶Рµ Р·Р°РїРёСЃСЊ СЃСѓС‰РµСЃС‚РІСѓРµС‚
 */
exception VTDuplException {
}

/*
 * РРЅС„РѕСЂРјР°С†РёСЏ РѕС‚СЃСѓС‚СЃС‚РІСѓРµС‚
 */
exception VTException {
}

service ThriftOutputInfo extends kmiacServer.KmiacServer {
    
    /**
     * Р’РѕР·РІСЂР°С‰Р°РµС‚ СЃРїРёСЃРѕРє РІСЂР°С‡РµР№, РІРµРґСѓС‰РёС… РїСЂРёРµРј
     * @param pcod - РЈРЅРёРєР°Р»СЊРЅС‹Р№ РєРѕРґ СЃРїРµС†РёР°Р»РёСЃС‚Р°
     * @return СЃРїРёСЃРѕРє thrift-РѕР±СЉРµРєС‚РѕРІ, СЃРѕРґРµСЂР¶Р°С‰РёС… РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РІСЂР°С‡Р°С…
     */
    list<VrachInfo> getVrachTableInfo(1:i32 cpodr) throws (1: VINotFoundException vinfe,
		2:kmiacServer.KmiacServerException kse);
    

     /**
     * Р’РѕР·РІСЂР°С‰Р°РµС‚ РІСЂРµРјСЏ СЂР°Р±РѕС‚С‹ РІСЂР°С‡РµР№
     */
    list<VrachTabel> getVrachTabel(1:i32 pcod) throws (1: VTException vte, 2:VTDuplException vtde,
		3:kmiacServer.KmiacServerException kse);
		
		
    /**
     * Р”РѕР±Р°РІР»СЏРµС‚ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РІСЂР°С‡Рµ
     */
    i32 addVT(1:VrachTabel vt) throws (1: VTException vte, 2:VTDuplException vtde,
		3:kmiacServer.KmiacServerException kse);

    /**
     * РћР±РЅРѕРІР»СЏРµС‚ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РІСЂР°С‡Рµ
     */
	void updateVT(1:VrachTabel vt) throws (1:kmiacServer.KmiacServerException kse); 
    
	/**
     * РЈРґР°Р»СЏРµС‚ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РІСЂР°С‡Рµ
     */
	void deleteVT(1:i32 vt);

    string printPlanDisp(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printNoVipPlanDisp(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printSvedDispObs(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printOtDetPol(1:InputPlanDisp ipd) throws (1: kmiacServer.KmiacServerException kse);

    string printSvodVed(1: InputAuthInfo iaf 2: InputSvodVed isv) throws (1: kmiacServer.KmiacServerException kse);

    string printFacZd(1: InputAuthInfo iaf 2: InputFacZd ifz) throws (1: kmiacServer.KmiacServerException kse);

   /**
    * РЎРІРѕРґРєРё РїРѕ С„РѕСЂРјРµ 39
    */
    string printDnevVr() throws (1: kmiacServer.KmiacServerException kse);
}


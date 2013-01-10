namespace java ru.nkz.ivcgzo.thriftViewSelect

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift" 


struct PatientBriefInfo {
	1: i32 npasp;
	2: string fam;
	3: string im;
	4: string ot;
	5: i64 datar;
	6: string spolis;
	7: string npolis;
}

struct PatientSearchParams {
	1: bool manyPatients;
	2: bool illegibleSearch;
	3: string fam;
	4: string im;
	5: string ot;
	6: optional i64 datar;
	7: optional i64 datar2;
	8: string spolis;
	9: string npolis;
	10: optional i32 npasp;
}

struct mkb_2{
	1: string pcod;
	2: string name;
	3: list<classifier.StringClassifier> mlb3;
}

struct mkb_1{
	1: string pcod;
	2: string klass;
	3: string name;
	5: list<mkb_2> mkb2;
}

struct mkb_0{
	1: string pcod;
	2: string name;
	3: string kod_mkb;
	4: list<mkb_1> mlb1;
}

struct polp_1 {
	1: i32 kdlpu;
	2: string name;
	3: list<classifier.IntegerClassifier> polp2;
}

struct polp_0 {
	1: i32 kdate;
	2: string name;
	3: list<polp_1> polp1;
}

struct mrab_0 {
    1: i32 pGruppa;
    2: string name;
    3: list<classifier.IntegerClassifier> mrab1;
}

struct PatientCommonInfo {
	 1: optional i32 npasp;
	 2: optional string fam;
	 3: optional string im;
	 4: optional string ot;
	 5: optional i64 datar;
	 6: optional string poms_ser;
	 7: optional string poms_nom;
	 8: optional i32 pol;
	 9: optional i32 jitel;
	10: optional i32 sgrp;
	11: optional string adp_obl;
	12: optional string adp_gorod;
	13: optional string adp_ul;
	14: optional string adp_dom;
	15: optional string adp_korp;
	16: optional string adp_kv;
	17: optional string adm_obl;
	18: optional string adm_gorod;
	19: optional string adm_ul;
	20: optional string adm_dom;
	21: optional string adm_korp;
	22: optional string adm_kv;
	23: optional string name_mr;
	24: optional i32 ncex;
	25: optional i32 poms_strg;
	26: optional i32 poms_tdoc;
	27: optional string poms_ndog;
	28: optional i32 pdms_strg;
	29: optional string pdms_ser;
	30: optional string pdms_nom;
	31: optional string pdms_ndog;
	32: optional i32 cpol_pr;
	33: optional i32 terp;
	34: optional i64 datapr;
	35: optional i32 tdoc;
	36: optional string docser;
	37: optional string docnum;
	38: optional i64 datadoc;
	39: optional string odoc;
	40: optional string snils;
	41: optional i64 dataz;
	42: optional string prof;
	43: optional string tel;
	44: optional i64 dsv;
	45: optional i32 prizn;
	46: optional i32 ter_liv;
	47: optional i32 region_liv;
	48: optional i32 mrab;
}

struct PatientSignInfo {
	1: optional i32 npasp;
	2: optional string grup;
	3: optional string ph;
	4: optional string allerg;
	5: optional string farmkol;
	6: optional string vitae;
	7: optional string vred;
}

struct PatientVizitInfo {
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional i32 cpol;
	4: optional i64 datao;
	5: optional i32 ishod;
	6: optional i32 rezult;
	7: optional i32 talon;
	8: optional i32 cod_sp;
	9: optional string cdol;
	10: optional i32 cuser;
	11: optional string zakl;
	12: optional i64 dataz;
	13: optional string recomend;
	14: optional string lech;
	15: optional i32 cobr;
}

struct PatientDiagZInfo {
	 1: optional i32 id;
	 2: optional i32 id_diag_amb;
	 3: optional i32 npasp;
	 4: optional string diag;
	 5: optional i32 cpodr;
	 6: optional i64 d_vz;
	 7: optional i32 d_grup;
	 8: optional i32 ishod;
	 9: optional i64 dataish;
	10: optional i64 datag;
	11: optional i64 datad;
	12: optional i32 cod_sp;
	13: optional string cdol_ot;
	14: optional i32 nmvd;
	15: optional i32 xzab;
	16: optional i32 stady;
	17: optional i32 disp;
	18: optional i32 pat;
	19: optional i32 prizb;
	20: optional i32 prizi;
	21: optional string named;
	22: optional string fio_vr;
}

struct PatientVizitAmbInfo {
	1: optional i32 id;
	2: optional i32 id_obr;
	3: optional i32 npasp;
	4: optional i64 datap;
	5: optional i32 cod_sp;
	6: optional string cdol;
	7: optional string diag; 
	8: optional i32 mobs;
	9: optional i32 rezult;
	10: optional i32 opl;
	11: optional double stoim;
	12: optional i32 uet;
	13: optional i64 datak;
	14: optional i32 kod_rez;
	15: optional i32 k_lr;
	16: optional i32 n_sp;
	17: optional i32 pr_opl;
	18: optional i32 pl_extr;
	19: optional i32 vpom;
	20: optional string fio_vr;
	21: optional i64 dataz;
	22: optional i32 cpos;
}

struct RdSlInfo {
        1: optional i32 id;
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
}

struct PatientAnamZabInfo {
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional string t_ist_zab;
}

struct PatientIsslInfo{
	1: optional i32 nisl;
	2: optional i32 cisl;
	3: optional string name_cisl;
	4: optional string pokaz;
	5: optional string pokaz_name;
	6: optional string rez;
	7: optional i64 datav;
	8: optional string op_name;
	9: optional string rez_name;
	10: optional i32 gruppa;
}

struct PatientDiagAmbInfo {
	 1: optional i32 id;
	 2: optional i32 id_obr;
	 3: optional i32 npasp;
	 4: optional string diag;
	 5: optional string named;/*медицинское описание д-за*/
	 6: optional i32 diag_stat;
	 7: optional bool predv;
	 8: optional i64 datad;
	 9: optional i32 obstreg;
	10: optional i32 cod_sp;
	11: optional string cdol;
	12: optional i64 dataot;
	13: optional i32 obstot;
	14: optional i32 codsp_ot;
	15: optional string cdol_ot;
	16: optional i32 vid_tr;
}

struct PatientNaprInfo {
	1: optional i32 id;
	2: optional i32 idpvizit;
	3: optional i32 vid_doc;
	4: optional string text;
	5: optional i32 preds;
	6: optional i32 zaved;
	7: optional string name;
}

struct PatientPriemInfo {
	1: optional i32 id_pvizit;
	2: optional i32 npasp;
	3: optional i32 idpos;
	4: optional i32 sl_ob;
	5: optional i32 n_is;
	6: optional i32 n_kons;
	7: optional i32 n_proc;
	8: optional i32 n_lek;
	9: optional string t_chss;
	10: optional string t_temp;
	11: optional string t_ad;
	12: optional string t_rost;
	13: optional string t_ves;
	14: optional string t_st_localis;
	15: optional string t_ocenka;
	16: optional string t_jalob;
	17: optional string t_status_praesense;
	18: optional string t_fiz_obsl;
	19: optional string t_recom;
}

struct CgospInfo{
	1: optional i32 id;
	2: optional i32 ngosp;
	3: optional i32 npasp;
	4: optional i32 nist;
	5: optional i64 datap;
	6: optional i64 vremp;
	7: optional i32 pl_extr;
	8: optional string naprav;
	9: optional i32 n_org;
	10: optional i32 cotd;
	11: optional i32 sv_time;
	12: optional i32 sv_day;
	13: optional i32 ntalon;
	14: optional i32 vidtr;
	15: optional i32 pr_out;
	16: optional i32 alkg;
	17: optional bool meesr;
	18: optional i32 vid_tran;
	19: optional string diag_n;
	20: optional string diag_p;
	21: optional string named_n;
	22: optional string named_p;
	23: optional bool nal_z;
	24: optional bool nal_p;
	25: optional string t0c;
	26: optional string ad;
	27: optional i64 smp_data;
	28: optional i64 smp_time;
	29: optional i32 smp_num;
	30: optional i32 cotd_p;
	31: optional i64 datagos;
	32: optional i64 vremgos;
	33: optional i32 cuser;
	34: optional i64 dataosm;
	35: optional i64 vremosm;
	36: optional i32 kod_rez;
	37: optional i64 dataz;
	38: optional string jalob;
	39: optional i32 vid_st;
	40: optional i64 d_rez;
	41: optional bool pr_ber;
}

struct CdepicrisInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional i64 datas;
	4: optional i64 times;
	5: optional string dspat;
	7: optional i32 prizn;
	8: optional string named;
	9: optional i64 dataz;
}

struct CdiagInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional string cod;
	4: optional string med_op;
	5: optional i64 date_ustan;
	6: optional i32 prizn;
	7: optional i32 vrach;
}

struct C_etapInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional i32 stl;
	4: optional string mes;
	5: optional i64 date_start;
	6: optional i64 date_end;
	7: optional i32 ukl;
	8: optional i32 ishod;
	9: optional i32 result;
	10: optional i64 time_start;
	11: optional i64 time_end;
}

struct CizmerInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional string temp;
	4: optional string ad;
	5: optional string chss;
	6: optional string rost;
	7: optional string ves;
	8: optional i64 dataz;
	9: optional i64 timez;
}

struct ClekInfo{
	1: optional i32 nlek;
	2: optional i32 id_gosp;
	3: optional i32 vrach;
	4: optional i64 datan;
	5: optional i32 klek;
	6: optional string flek;
	7: optional i32 doza;
	8: optional i32 ed;
	9: optional i32 sposv;
	10: optional i32 spriem;
	11: optional i32 pereod;
	12: optional i32 dlitkl;
	13: optional string komm;
	14: optional i64 datao;
	15: optional i32 vracho;
	16: optional i64 dataz;
}

struct CosmotrInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional string jalob;
	4: optional string morbi;
	5: optional string status_praesense;
	6: optional string status_localis;
	7: optional string fisical_obs;
	8: optional i32 pcod_vrach;
	9: optional i64 dataz;
	10: optional i64 timez;
}

struct CotdInfo{
	1: optional i32 id;
	2: optional i32 id_gosp;
	3: optional i32 nist;
	4: optional bool sign;
	5: optional i32 cotd;
	6: optional i32 cprof;
	7: optional i32 stt;
	8: optional i64 dataol;
	9: optional i64 datazl;
	10: optional i32 vozrlbl;
	11: optional i32 pollbl;
	12: optional i32 ishod;
	13: optional i32 result;
	14: optional double ukl;
	15: optional i32 vrach;
	16: optional i32 npal;
	17: optional i64 datav;
	18: optional i64 vremv;
	19: optional string sostv;
	20: optional string recom;
	21: optional string mes;
	22: optional i64 dataz;
	23: optional i32 stat_type;
}

struct PaspErrorInfo {	
	1: optional i32 id;
	2: optional i32 npasp;
	3: optional string fam;
	4: optional string im;
	5: optional string ot;
	6: optional i64 datar;
	7: optional i32 kderr;
	8: optional string err_name;
	9: optional string err_comm;
}

struct MedPolErrorInfo {
	 1: optional i32 id;
	 2: optional i32 id_obr;
	 3: optional i32 id_pos;
	 4: optional i64 dat_obr;
	 5: optional i64 dat_pos;
	 6: optional i32 vr_pcod;
	 7: optional string vr_fio;
	 8: optional string vr_cdol;
	 9: optional string vr_cdol_name;
	10: optional i32 npasp;
	11: optional string pat_fio;
	12: optional i64 pat_datar;
	13: optional i32 kderr;
	14: optional string err_name;
	15: optional string err_comm;
}

struct PatientAnamnez {
	1: optional i32 npasp;
	2: optional i64 datap;
	3: optional i32 numstr;
	4: optional bool vybor;
	5: optional string comment;
	6: optional string name;
	7: optional string pranz;
}

struct Pbol{
	 1: optional i32 id;
	 2: optional i32 id_obr;
	 3: optional i32 id_gosp;
	 4: optional i32 npasp;
	 5: optional i32 bol_l;
	 6: optional i64 s_bl;
	 7: optional i64 po_bl;
	 8: optional i32 pol;
	 9: optional i32 vozr;
	10: optional string nombl;
	11: optional i32 cod_sp;
	12: optional string cdol;
	13: optional i32 pcod;
	14: optional i64 dataz;
}

exception TipPodrNotFoundException {
}

service ThriftViewSelect extends kmiacServer.KmiacServer {
	/**
	 * Информация из классификатора с pcod типа string
	 */
	list<classifier.StringClassifier> getVSStringClassifierView(1: string className),
	
	/**
	 * Информация из классификатора с pcod типа integer
	 */
	list<classifier.IntegerClassifier> getVSIntegerClassifierView(1: string className),

    /**
     * Является ли классификатор редактируемым
     */
    bool isClassifierEditable(1: string className),

    /**
     * Является ли классификатор с pcod типа integer
     */
    bool isClassifierPcodInteger(1: string className),


    
	list<PatientBriefInfo> searchPatient(1: PatientSearchParams prms) throws (1: kmiacServer.KmiacServerException kse);

	list<classifier.IntegerClassifier> getIntegerClassifier(1: classifier.IntegerClassifiers cls) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.IntegerClassifier> getIntegerClassifierSorted(1: classifier.IntegerClassifiers cls, 2: classifier.ClassifierSortOrder ord, 3: classifier.ClassifierSortFields fld) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getStringClassifier(1: classifier.StringClassifiers cls) throws (1: kmiacServer.KmiacServerException kse);
	list<classifier.StringClassifier> getStringClassifierSorted(1: classifier.StringClassifiers cls, 2: classifier.ClassifierSortOrder ord, 3: classifier.ClassifierSortFields fld) throws (1: kmiacServer.KmiacServerException kse);
	list<mkb_0> getMkb_0() throws (1: kmiacServer.KmiacServerException kse);
	list<polp_0> getPolp_0() throws (1: kmiacServer.KmiacServerException kse);
	list<mrab_0> getMrab_0() throws (1: kmiacServer.KmiacServerException kse);

	PatientCommonInfo getPatientCommonInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	PatientSignInfo getPatientSignInfo(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientVizitInfo> getPatientVizitInfoList(1: i32 npasp, 2: i64 datan, 3: i64 datak) throws (1: kmiacServer.KmiacServerException kse);
	list<RdSlInfo> getRdSlInfoList(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientDiagZInfo> getPatientDiagZInfoList(1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientVizitAmbInfo> getPatientVizitAmbInfoList(1: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	PatientPriemInfo getPatientPriemInfo(1: i32 npasp, 2: i32 pvizitAmbId) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientNaprInfo> getPatientNaprInfoList(1: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientDiagAmbInfo> getPatientDiagAmbInfoList(1: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientIsslInfo> getPatientIsslInfoList(1: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	PatientAnamZabInfo getPatientAnamZabInfo(1: i32 pvizitId, 2: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	list<CgospInfo> getCgospinfo (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	CdepicrisInfo getCdepicrisInfo(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<CdiagInfo> getCdiagInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<C_etapInfo> getCEtapInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<CizmerInfo> getCizmerInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<ClekInfo> getClekInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<CosmotrInfo> getCosmotrInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<CotdInfo> getCotdInfoList(1: i32 id_gosp) throws (1: kmiacServer.KmiacServerException kse);
	list<PaspErrorInfo> getPaspErrors(1: i32 cpodrz, 2: i64 datazf, 3: i64 datazt) throws (1: kmiacServer.KmiacServerException kse);
	list<MedPolErrorInfo> getMedPolErrors(1: i32 cpodrz, 2: i64 datazf, 3: i64 datazt) throws (1: kmiacServer.KmiacServerException kse);
	list<PatientAnamnez> getAnamnez(1:i32 npasp, 2:i32 cslu, 3:i32 cpodr) throws (1: TipPodrNotFoundException tpfe, 2:kmiacServer.KmiacServerException kse);
	void deleteAnam(1:i32 npasp, 2:i32 cslu, 3:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);
	void updateAnam(1: list<PatientAnamnez> patAnam) throws (1: kmiacServer.KmiacServerException kse);

	list<Pbol> getPbol (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPbol(1: Pbol pbol) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePbol(1: Pbol pbol) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePbol(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
}

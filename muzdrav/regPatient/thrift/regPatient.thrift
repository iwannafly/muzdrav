namespace java ru.nkz.ivcgzo.thriftRegPatient

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Address{
	1:string region,
	2:string city,
	3:string street,
	4:string house,
	5:string flat
}

struct PatientBrief{
	1:optional i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:optional i64 datar,
	6:string spolis,
	7:string npolis,
	8:Address adpAddress,
	9:Address admAddress
}

struct Polis{
	1:i32 strg,
	2:string ser,
	3:string nom,
	4:i32 tdoc
}

/*p_nambk*/
struct Nambk{
	1:i32 npasp,
	2:string nambk,
	3:i32 nuch,
	4:i32 cpol,
	5:optional i64 datapr,
	6:optional i64 dataot,
	7:i32 ishod
}

/*patient*/
struct PatientFullInfo{
	1:i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:optional i64 datar,
	6:optional i32 pol,
	7:optional i32 jitel,
	8:optional i32 sgrp,
	9:optional i32 mrab,
	10:optional string namemr,
	11:optional i32 ncex,
	12:optional i32 cpol_pr,
	13:optional i32 terp,
	14:optional i32 tdoc,
	15:optional string docser,
	16:optional string docnum,
	17:optional i64 datadoc,
	18:optional string odoc,
	19:optional string snils,
	20:optional i64 dataz,
	21:optional string prof,
	22:optional string tel,
	23:optional i64 dsv,
	24:optional i32 prizn,
	25:optional i32 ter_liv,
	26:optional i32 region_liv,
	27:optional Address adpAddress,
	28:optional Address admAddress,
	29:optional Polis polis_oms,
	30:optional Polis polis_dms,
	31:optional i32 v_sch,
	32:optional i32 str_org
}

/*сведени о представителе табл. p_preds*/
struct Agent{
	1:i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:optional i64 datar,
	6:i32 pol,
	7:string name_str,
	8:string ogrn_str,
	9:i32 vpolis,
	10:string spolis,
	11:string npolis,
	12:optional i32 tdoc,
	13:optional string docser,
	14:optional string docnum,
	15:string birthplace
}

/*pkov*/
struct Lgota{
	1:i32 id,
	2:i32 npasp,
	3:i32 lgota,
	4:optional i64 datau,
	5:string name
}

/*pkonti*/
struct Kontingent{
	1:i32 id,
	2:i32 npasp,
	3:i32 kateg,
	4:optional i64 datau,
	5:string name
}

/*psign*/
struct Sign{
	1:i32 npasp,
	2:string grup,
	3:string ph,
	4:string allerg,
	5:string farmkol,
	6:string vitae,
	7:string vred
}

/*c_gosp*/
struct AllGosp{
	1:i32 id,
	2:i32 ngosp,
	3:i32 npasp,
	4:i32 nist,
	5:optional i64 datap,
	6:i32 cotd,
	7:string diag_p,
	8:string named_p
}

/*c_gosp*/
struct Gosp{
	1:i32 id,
	2:i32 ngosp,
	3:i32 npasp,
	4:optional i32 nist,
	5:optional i64 datap,
	6:optional i64 vremp,
	7:optional i32 pl_extr,
	8:optional string naprav,
	9:optional i32 n_org,
	10:optional i32 cotd,
	11:optional i32 sv_time,
	12:optional i32 sv_day,
	13:optional i32 ntalon,
	14:optional i32 vidtr,
	15:optional i32 pr_out,
	16:optional i32 alkg,
	17:optional bool messr,
	18:optional i32 vid_trans,
	19:optional string diag_n,
	20:optional string diag_p,
	21:optional string named_n,
	22:optional string named_p,
	23:optional bool nal_z,
	24:optional bool nal_p,
	25:optional string toc,
	26:optional string ad,
	27:optional i64 smp_data,
	28:optional i64 smp_time,
	29:optional i32 smp_num,
	30:optional i32 cotd_p,
	31:optional i64 datagos,
	32:optional i64 vremgos,
	33:optional i32 cuser,
	34:optional i64 dataosm,
	35:optional i64 vremosm,
	36:optional i64 dataz,
	37:optional string jalob
}

/**
 * Объект, содержащий в первом поле - сгенированный ид записи
 * <ul>
 *     <li>id - сгенированный после добавления номер записи</l1>
 *     <li>name - текстовое описание из классификатора</l1>
 * </ul>
 * Используется для добавления льгот и контингента
 */
struct Info{
	1:i32 id,
	2:string name 
}

/**
 * Пациент с такими данными не найден.
 */
exception PatientNotFoundException {
}

/**
 * Представитель с такими данными не найден
 */
exception AgentNotFoundException {
}

/**
 * Запись льготы с такими данными не найдена
 */
exception LgotaNotFoundException {
}

/**
 * Запись категории с такими данными не найдена
 */
exception KontingentNotFoundException {
}

/**
 * Особа информаци о пациенте с такими данными не найдены
 */
exception SignNotFoundException {
}

/**
 * Запись госпитализации пациента с такими данными не найдена
 */
exception GospNotFoundException {
}

/**
 * Пациент с такими данными уже существует
 */
exception PatientAlreadyExistException {
}

/**
 * Пациент с такими данными уже существует
 */
exception LgotaAlreadyExistException {
}

/**
 * Пациент с такими данными уже существует
 */
exception KontingentAlreadyExistException {
}

/**
 * Пациент с такими данными уже существует
 */
exception GospAlreadyExistException {
}

/**
 * Пациент с такими данными уже существует
 */
exception NambkAlreadyExistException {
}

/**
 * Запись с такими данными не найдена
 */
exception SmorfNotFoundException {
}

exception OgrnNotFoundException {
}

exception RegionLiveNotFoundException {
}

exception TerLiveNotFoundException {
}

exception SmocodNotFoundException {
}
exception NambkNotFoundException{
}

service ThriftRegPatient extends kmiacServer.KmiacServer {
    
    /**
     * Возвращает краткие сведения
     * о всех пациентах, удовлетворяющих введенным данным.
     * @param patient - информация о пациентах, по которой производится поиск
     * @return список thrift-объектов, содержащих краткую информацию о пациентах
     * @throws PatientNotFoundException
     */
    list<PatientBrief> getAllPatientBrief(1:PatientBrief patient) throws (1: PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает полные сведения о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий полную информацию о пациенте
     * @throws PatientNotFoundException
     */
    PatientFullInfo getPatientFullInfo(1:i32 npasp) throws (1: PatientNotFoundException pnfe,
		2:kmiacServer.KmiacServerException kse);
	
	Nambk getNambk(1:i32 npasp, 2:i32 cpodr) throws (1:NambkNotFoundException nnfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает сведения о представителе пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий информацию о представителе пациента
     * @throws AgentNotFoundException
     */
    Agent getAgent(1:i32 npasp) throws (1: AgentNotFoundException anfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает сведения о льготах пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о льготах пациента
     * @throws LgotaNotFoundException
     */
    list<Lgota> getLgota(1:i32 npasp) throws (1: LgotaNotFoundException lnfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает сведения о категориях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о категориях пациента
     * @throws KontingentNotFoundException
     */
    list<Kontingent> getKontingent(1:i32 npasp) throws (1: KontingentNotFoundException knfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает особую информацию о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий особую информацию о пациенте
     * @throws SignNotFoundException
     */
    Sign getSign(1:i32 npasp) throws (1: SignNotFoundException snfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает записи о всех госпитализациях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о госпитализациях пациента
     * @throws GospNotFoundException
     */
    list<AllGosp> getAllGosp(1:i32 npasp) throws (1: GospNotFoundException gnfe,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Возвращает запись госпитализации пациента с указанным идентификатором
     * госпитализации
     * @param id - уникальный идентификатор госпитализации
     * @return thrift-объект, содержащий особую информацию о госпитализации пациента
     * @throws GospNotFoundException
     */
    Gosp getGosp(1:i32 id) throws (1: GospNotFoundException gnfe,
		2:kmiacServer.KmiacServerException kse);
	
	/**
	 * Печать медицинской карты приемного отделения
	 */
	string printMedCart(1: Nambk nambk, 2: PatientFullInfo pat, 3: kmiacServer.UserAuthInfo uai,
		4: string docInfo, 5: string oms_org, 6: string lgot) throws (1:kmiacServer.KmiacServerException kse);
	
	/**
	 * Печать амбулаторный карты
	 */
	string printAmbCart(1:PatientFullInfo pat) throws (1:kmiacServer.KmiacServerException kse);

    void deletePatient(1:i32 npasp) throws (1:kmiacServer.KmiacServerException kse);
    void deleteNambk(1:i32 npasp, 2:i32 cpol) throws (1:kmiacServer.KmiacServerException kse);
    void deleteLgota(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);
    void deleteKont(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);
    void deleteAgent(1:i32 npasp) throws (1:kmiacServer.KmiacServerException kse);
    void deleteSign(1:i32 npasp) throws (1:kmiacServer.KmiacServerException kse);
    void deleteGosp(1:i32 id) throws (1:kmiacServer.KmiacServerException kse);

    /**
     * Добавляет информацию о пациенте в БД
     * @param patinfo - thrift-объект с информацией о пациенте
     * @return целочисленный первичный ключ id, сгенерированный при добавлении
     * @throws PatientAlreadyExistException
     */
    i32 addPatient(1:PatientFullInfo patinfo) throws (1:PatientAlreadyExistException paee,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет сведения о льготах пациента
     * @param lgota - сведения о льготах пациента
     * @return объект класса Info, в котором:
	 * <ul>
	 *     <li>id - сгенированный после добавления номер записи</l1>
	 *     <li>name - текстовое описание льготы из классификатора n_lkn</l1>
	 * </ul>
     * @throws LgotaAlreadyExistException
     */
    Info addLgota(1:Lgota lgota) throws (1:LgotaAlreadyExistException laee,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет информацию о категории пациента в БД
     * @param kont - информация о категории пациента
     * @return объект класса Info, в котором:
	 * <ul>
	 *     <li>id - сгенированный после добавления номер записи</l1>
	 *     <li>name - текстовое описание категории из классификатора n_lkr</l1>
	 * </ul>
     * @throws KontingentAlreadyExistException
     */
    Info addKont(1:Kontingent kont) throws (1:KontingentAlreadyExistException kaee,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет или обновляет информацию о представителе пациента в БД,
     * в зависимости от наличия данного представителя пациента в базе.
     * @param agent - информация о представителе пациента
     */
    void addOrUpdateAgent(1:Agent agent) throws (1:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет или обновляет особую информацию о пациенте в БД,
     * в зависимости от наличия в базе.
     * @param sign - особая информация о пациенте
     */
    void addOrUpdateSign(1:Sign sign) throws (1:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет запись госпитализации в БД
     * @param gosp - особая информация о представителе пациента
     * @throws GospAlreadyExistException
     */
    i32 addGosp(1:Gosp gosp) throws (1:GospAlreadyExistException gaee,
		2:kmiacServer.KmiacServerException kse);
	
    /**
     * Добавляет запись об амбулаторной карте в БД
     * @param nambk - thrift-объект с информацией об амбулаторной карте
     * @throws NambkAlreadyExistException
     */
    void addNambk(1:Nambk nambk) throws (1:NambkAlreadyExistException naee,
		2:kmiacServer.KmiacServerException kse);

    void updatePatient(1:PatientFullInfo patinfo) throws (1:kmiacServer.KmiacServerException kse);
    void updateNambk(1:Nambk nambk) throws (1:kmiacServer.KmiacServerException kse);
    void updateLgota(1:Lgota lgota) throws (1:kmiacServer.KmiacServerException kse);
    void updateKont(1:Kontingent kont) throws (1:kmiacServer.KmiacServerException kse);
    void updateGosp(1:Gosp gosp) throws (1:kmiacServer.KmiacServerException kse);

    /**
     * Обновляет информацию в табл. preds поля name_str, ogrn_str если изменилось СМО и если есть запись в этой табл.
     * update preds set name_str=null, ogrn_str=null where npasp=?
     */
    void updateOgrn(1:i32 npasp) throws (1:kmiacServer.KmiacServerException kse);
	
	/**
	* Возвращает ОГРН
	* select ogrn from n_smorf where smocod=?
     	*/
	string getOgrn(1:string smocod) throws (1:OgrnNotFoundException onfe,
		2:kmiacServer.KmiacServerException kse);

	/**
	* Возвращает код СМО
	* select smocod from n_smorf where ogrn=?
     	*/
	string getSmocod(1:string ogrn, 2:i32 pcod) throws (1:SmocodNotFoundException snfe,
		2:kmiacServer.KmiacServerException kse);

	/**
	* Возвращает регион проживания
	* select c_ffomc from n_l02 where pcod=?
    */
	i32 getRegionLive(1:i32 pcod) throws (1:RegionLiveNotFoundException rlnfe,
		2:kmiacServer.KmiacServerException kse);

	/**
	* Возвращает территорию проживания
	* select ter from n_l00 where pcod=?
     	*/
	i32 getTerLive(1:i32 pcod) throws (1:TerLiveNotFoundException tlnfe,
		2:kmiacServer.KmiacServerException kse);

/*Классификаторы*/
	
	/**
	 * Классификатор социального статуса (N_az9(pcod))
	 */
	list<classifier.IntegerClassifier> getSgrp() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор типа документа, подтверждающего страхование (N_f008 (pcod))
	 */
	list<classifier.IntegerClassifier> getPomsTdoc() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор типа документа, удостоверющего личность (N_az0 (pcod))
	 */
	list<classifier.IntegerClassifier> getTdoc() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор кем направлен (N_K02 (pcod))
	 */
	list<classifier.StringClassifier> getNaprav() throws (1:kmiacServer.KmiacServerException kse);
	
	/**
	 * Классификатор ЛПУ (N_M00 (pcod))
	 */
	list<classifier.IntegerClassifier> getM00() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор поликлиник (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getN00() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор отделений ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getO00() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор отделений для текущего ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getOtdForCurrentLpu(1:i32 lpuId) 
		throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор ведомственное подчинение (N_AL0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAL0() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор военкоматы (N_W04 (pcod))
	 */
	list<classifier.IntegerClassifier> getW04() throws (1:kmiacServer.KmiacServerException kse);
	
	/**
	 * Классификатор вид травмы (N_AI0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAI0() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор отказов в госпитализации (N_AF0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAF0() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор состояние опьянения (N_ALK (pcod))
	 */
	list<classifier.IntegerClassifier> getALK() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор вид транспортировки (N_VTR (pcod))
	 */
	list<classifier.IntegerClassifier> getVTR() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор N_ABB (N_ABB(pcod))
	 */
	list<classifier.IntegerClassifier> getABB() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор N_SMORF (N_SMORF(pcod))
	 */
	list<classifier.StringClassifier> getSmorf(1:i32 kodsmo) throws (1: SmorfNotFoundException snfe,
		2:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор льгот N_LKN (N_LKN(pcod))
	 */
	list<classifier.IntegerClassifier> getLKN() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор категорий N_LKR (N_LKR(pcod))
	 */
	list<classifier.IntegerClassifier> getLKR() throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор городов N_L00 (N_L00(pcod))
	 * select ter, nam_kem  from n_l00 where c_ffomc=?
	 */
	list<classifier.IntegerClassifier> getL00(1:i32 pcod) throws (1:kmiacServer.KmiacServerException kse);

	/**
	 * Классификатор улиц N_U10 (N_U10(pcod))
	 * select ndom  from n_u10 where name1=?
	 */
	list<classifier.StringClassifier> getU10(1:string name) throws (1:kmiacServer.KmiacServerException kse);



}

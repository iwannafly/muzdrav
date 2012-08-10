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
	5:i64 datar,
	6:i32 pol,
	7:i32 jitel,
	8:i32 sgrp,
	9:string mrab,
	10:string namemr,
	11:i32 ncex,
	12:i32 cpol_pr,
	13:i32 terp,
	14:i32 tdoc,
	15:string docser,
	16:string docnum,
	17:i64 datadoc,
	18:string odoc,
	19:string snils,
	20:i64 dataz,
	21:string prof,
	22:string tel,
	23:i64 dsv,
	24:i32 prizn,
	25:i32 ter_liv,
	26:i32 region_liv,
	27:Nambk nambk,
	28:Address adpAddress,
	29:Address admAddress,
	30:Polis polis_oms,
	31:Polis polis_dms
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
	4:i32 nist,
	5:optional i64 datap,
	6:optional i64 vremp,
	7:i32 pl_extr,
	8:string naprav,
	9:i32 n_org,
	10:i32 cotd,
	11:i32 sv_time,
	12:i32 sv_day,
	13:i32 ntalon,
	14:i32 vidtr,
	15:i32 pr_out,
	16:i32 alkg,
	17:bool messr,
	18:i32 vid_trans,
	19:string diag_n,
	20:string diag_p,
	21:string named_n,
	22:string named_p,
	23:bool nal_z,
	24:bool nal_p,
	25:string toc,
	26:string ad,
	27:optional i64 smp_data,
	28:optional i64 smp_time,
	29:optional i32 smp_num,
	30:i32 cotd_p,
	31:optional i64 datagos,
	32:optional i64 vremgos,
	33:i32 cuser,
	34:optional i64 dataosm,
	35:optional i64 vremosm,
	36:i64 dataz,
	37:string jalob
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

service ThriftRegPatient extends kmiacServer.KmiacServer {
    
    /**
     * Возвращает краткие сведения
     * о всех пациентах, удовлетворяющих введенным данным.
     * @param patient - информация о пациентах, по которой производится поиск
     * @return список thrift-объектов, содержащих краткую информацию о пациентах
     * @throws PatientNotFoundException
     */
    list<PatientBrief> getAllPatientBrief(1:PatientBrief patient) throws (1: PatientNotFoundException pnfe),
	
    /**
     * Возвращает полные сведения о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий полную информацию о пациенте
     * @throws PatientNotFoundException
     */
    PatientFullInfo getPatientFullInfo(1:i32 npasp) throws (1: PatientNotFoundException pnfe),
	
    /**
     * Возвращает сведения о представителе пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий информацию о представителе пациента
     * @throws AgentNotFoundException
     */
    Agent getAgent(1:i32 npasp) throws (1: AgentNotFoundException anfe),
	
    /**
     * Возвращает сведения о льготах пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о льготах пациента
     * @throws LgotaNotFoundException
     */
    list<Lgota> getLgota(1:i32 npasp) throws (1: LgotaNotFoundException lnfe),
	
    /**
     * Возвращает сведения о категориях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о категориях пациента
     * @throws KontingentNotFoundException
     */
    list<Kontingent> getKontingent(1:i32 npasp) throws (1: KontingentNotFoundException knfe),
	
    /**
     * Возвращает особую информацию о пациенте с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return thrift-объект, содержащий особую информацию о пациенте
     * @throws SignNotFoundException
     */
    Sign getSign(1:i32 npasp) throws (1: SignNotFoundException snfe),
	
    /**
     * Возвращает записи о всех госпитализациях пациента с указанным персональным номером
     * @param npasp - персональный номер пациента
     * @return список thrift-объектов, содержащих информацию о госпитализациях пациента
     * @throws GospNotFoundException
     */
    list<AllGosp> getAllGosp(1:i32 npasp) throws (1: GospNotFoundException gnfe),
	
    /**
     * Возвращает запись госпитализации пациента с указанным идентификатором
     * госпитализации
     * @param id - уникальный идентификатор госпитализации
     * @return thrift-объект, содержащий особую информацию о госпитализации пациента
     * @throws GospNotFoundException
     */
    Gosp getGosp(1:i32 id) throws (1: GospNotFoundException gnfe),
	
	/**
	 * Печать медицинской карты приемного отделения
	 */
	string printMedCart(1: Gosp gosp, 2: PatientFullInfo pat),
	
	/**
	 * Печать амбулаторный карты
	 */
	string printAmbCart(1:PatientFullInfo pat),

    void deletePatient(1:i32 npasp),
    void deleteNambk(1:i32 npasp, 2:i32 cpol),
    void deleteLgota(1:i32 id),
    void deleteKont(1:i32 id),
    void deleteAgent(1:i32 npasp),
    void deleteSign(1:i32 npasp),
    void deleteGosp(1:i32 id),

    /**
     * Добавляет информацию о пациенте в БД
     * @param patinfo - thrift-объект с информацией о пациенте
     * @return целочисленный первичный ключ id, сгенерированный при добавлении
     * @throws PatientAlreadyExistException
     */
    i32 addPatient(1:PatientFullInfo patinfo) throws (1:PatientAlreadyExistException paee),
	
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
    Info addLgota(1:Lgota lgota) throws (1:LgotaAlreadyExistException laee),
	
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
    Info addKont(1:Kontingent kont) throws (1:KontingentAlreadyExistException kaee),
	
    /**
     * Добавляет или обновляет информацию о представителе пациента в БД,
     * в зависимости от наличия данного представителя пациента в базе.
     * @param agent - информация о представителе пациента
     */
    void addOrUpdateAgent(1:Agent agent),
	
    /**
     * Добавляет или обновляет особую информацию о пациенте в БД,
     * в зависимости от наличия в базе.
     * @param sign - особая информация о пациенте
     */
    void addOrUpdateSign(1:Sign sign),
	
    /**
     * Добавляет запись госпитализации в БД
     * @param gosp - особая информация о представителе пациента
     * @throws GospAlreadyExistException
     */
    i32 addGosp(1:Gosp gosp) throws (1:GospAlreadyExistException gaee),
	
    /**
     * Добавляет запись об амбулаторной карте в БД
     * @param nambk - thrift-объект с информацией об амбулаторной карте
     * @throws NambkAlreadyExistException
     */
    void addNambk(1:Nambk nambk) throws (1:NambkAlreadyExistException naee),

    void updatePatient(1:PatientFullInfo patinfo),
    void updateNambk(1:Nambk nambk),
    void updateLgota(1:Lgota lgota),
    void updateKont(1:Kontingent kont),
    void updateGosp(1:Gosp gosp),

    /**
     * Обновляет информацию в табл. preds поля name_str, ogrn_str если изменилось СМО и если есть запись в этой табл.
     * update preds set name_str=null, ogrn_str=null where npasp=?
     */
    void updateOgrn(1:i32 npasp),
	
	/**
	* Возвращает ОГРН
	* select q_ogrn from n_smorf where smocod=?
     	*/
	string getOgrn(1:string smocod) throws (1:OgrnNotFoundException onfe),

	/**
	* Возвращает регион проживания
	* select c_ffomc from n_l02 where pcod=?
     	*/
	i32 getRegionLive(1:i32 pcod) throws (1:RegionLiveNotFoundException rlnfe),

	/**
	* Возвращает территорию проживания
	* select ter from n_l00 where pcod=?
     	*/
	i32 getTerLive(1:i32 pcod) throws (1:TerLiveNotFoundException tlnfe),

/*Классификаторы*/
	
	/**
	 * Классификатор социального статуса (N_az9(pcod))
	 */
	list<classifier.IntegerClassifier> getSgrp(),

	/**
	 * Классификатор типа документа, подтверждающего страхование (N_f008 (pcod))
	 */
	list<classifier.IntegerClassifier> getPomsTdoc(),

	/**
	 * Классификатор типа документа, удостоверющего личность (N_az0 (pcod))
	 */
	list<classifier.IntegerClassifier> getTdoc(),

	/**
	 * Классификатор кем направлен (N_K02 (pcod))
	 */
	list<classifier.StringClassifier> getNaprav(),
	
	/**
	 * Классификатор ЛПУ (N_M00 (pcod))
	 */
	list<classifier.IntegerClassifier> getM00(),

	/**
	 * Классификатор поликлиник (N_N00 (pcod))
	 */
	list<classifier.IntegerClassifier> getN00(),

	/**
	 * Классификатор отделений ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getO00(),

	/**
	 * Классификатор отделений для текущего ЛПУ (N_O00 (pcod))
	 */
	list<classifier.IntegerClassifier> getOtdForCurrentLpu(1:i32 lpuId),

	/**
	 * Классификатор ведомственное подчинение (N_AL0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAL0(),

	/**
	 * Классификатор военкоматы (N_W04 (pcod))
	 */
	list<classifier.IntegerClassifier> getW04(),
	
	/**
	 * Классификатор вид травмы (N_AI0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAI0(),

	/**
	 * Классификатор отказов в госпитализации (N_AF0 (pcod))
	 */
	list<classifier.IntegerClassifier> getAF0(),

	/**
	 * Классификатор состояние опьянения (N_ALK (pcod))
	 */
	list<classifier.IntegerClassifier> getALK(),

	/**
	 * Классификатор вид транспортировки (N_VTR (pcod))
	 */
	list<classifier.IntegerClassifier> getVTR(),

	/**
	 * Классификатор N_ABB (N_ABB(pcod))
	 */
	list<classifier.IntegerClassifier> getABB(),

	/**
	 * Классификатор N_SMORF (N_SMORF(pcod))
	 */
	list<classifier.IntegerClassifier> getSmorf(1:i32 kodsmo) throws (1: SmorfNotFoundException snfe)

}

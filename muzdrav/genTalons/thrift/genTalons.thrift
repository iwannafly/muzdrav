namespace java ru.nkz.ivcgzo.thriftGenTalon

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

/*s_mrab, n_s00*/
struct Spec{
	1:string pcod,
	2:string name 
}

/*s_vrach*/
struct Vrach{
	1:i32 pcod,
	2:string fam,
	3:string im,
	4:string ot,
	5:string cdol
}

struct Calendar{
	1:i64 datacal,
	2:i32 dweek,
	3:i32 nweek,
	4:i32 cday,
	5:i32 cmonth,
	6:i32 cyear,
	7:bool pr_rab,
	8:i64 d_rab
}

struct Ndv{
	1:i32 pcod,
	2:i64 datan,
	3:i64 datak,
	4:string cdol,
	5:i32 cpol,
	6:i32 id
}

struct Norm{
	1:string cdol,
	2:i32 vidp,
	3:i32 dlit,
	4:i32 cpol,
	5:i32 id
}

struct Nrasp{
	1:i32 pcod,
	2:i32 denn,
	3:i32 vidp,
	4:i64 time_n,
	5:i64 time_k,
	6:i32 cxema,
	7:string cdol,
	8:i32 cpol,
	9:i32 id
}

struct Rasp{
	1:i32 nrasp,
	2:i32 pcod,
	3:i32 nned,
	4:i32 denn,
	5:i64 datap,
	6:i64 time_n,
	7:i64 time_k,
	8:i32 vidp,
	9:string cdol
}

struct Vidp{
	1:i32 pcod,
	2:string name,
	3:string vcolor
}

struct Talon{
	1:i32 id,
	2:i32 ntalon,
	3:i32 nrasp,
	4:i32 pcod_sp,
	5:string cdol,
	6:i32 cdol_kem,
	7:i32 vidp,
	8:i64 timepn,
	9:i64 timepk
	10:i64 datapt,
	11:i64 datap,
	12:i64 timep
}

service ThriftGenTalons extends kmiacServer.KmiacServer {
	/**
	*Возвращает информацию о врачебных специальностях (datau пусто)
	*/
	list<Spec> getAllSpecForPolikliniki(1:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает код и ФИО врачей по врачебной должности
	*/
	list<Vrach> getVrachForCurrentSpec(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает календарь на текущий год
	*/
	list<Calendar> getCalendar(1:i32 cyear) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает длительность приема специалистом в поликлинике
	*/
	list<Norm> getNorm(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает неприемные дни врача
	*/
	list<Ndv> getNdv(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает график приема врача в поликлинике
	* сортировать denn,vidp
	*/
	list<Nrasp> getNrasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol, 4:i32 cxema) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает расписание работы врача в поликлинике
	*/
	list<Rasp> getRasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает талоны на прием в поликлинике
	*/
	list<Talon> getTalon(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol, 4:i64 datap) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*Возвращает виды приема
	*/
	list<Vidp> getVidp() throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Добавляет записи в табл расписание работы врача
        * @param Nrasp - thrift-объект с информацией о расписании
        */
	void addNrasp(1: list<Nrasp> nrasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Добавляет записи в табл ndv
        * @param Ndv - thrift-объект с информацией о нерабочих днях
        */
	void addNdv(1: Ndv ndv) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Добавляет записи в табл norm
        * @param Norm - thrift-объект с информацией о нормах длительности приема
        */
	void addNorm(1: list<Norm> nrasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Удаляет расписание работы врача
        */
	void deleteNrasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Удаляет строку в табл ndv
        */
	void deleteNdv(1:i32 id) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Сохраняет изменения в табл расписание работы врача
        * @param Nrasp - thrift-объект с информацией о расписании
        */
	void updateNrasp(1: list<Nrasp> nrasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
        * Сохраняет изменения в табл norm
        * @param Norm - thrift-объект с информацией о расписании
        */
	void updateNorm(1: list<Norm> nrasp) throws (1: kmiacServer.KmiacServerException kse);

















}

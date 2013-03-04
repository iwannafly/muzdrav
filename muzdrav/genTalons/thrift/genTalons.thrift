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

struct Calend{
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
	5:i32 id,
	6:string name_vidp
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
	9:i32 id,
	10:bool pfd,
	11:i64 timep_n,
	12:i64 timep_k,
	13:i64 time_int_n,
	14:i64 time_int_k
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
	9:string cdol,
	10:i32 cpol,
	11:i32 id,
	12:bool pfd
}

struct Vidp{
	1:i32 pcod,
	2:string name,
	3:string vcolor
}

struct Talon{
	1:i32 id,
	2:optional i32 ntalon,
	3:optional i32 nrasp,
	4:i32 pcod_sp,
	5:string cdol,
	6:optional i32 cdol_kem,
	7:i32 vidp,
	8:optional i64 timepn,
	9:optional i64 timepk
	10:optional i64 datapt,
	11:optional i64 datap,
	12:optional i64 timep,
	13:i32 cpol,
	14:i32 prv
}

struct RepStruct{
	1:i32 cpol,
	2:optional i32 pcod,
	3:optional string cdol,
	4:i64 datan,
	5:i64 datak,
	6:i32 nsv
}


/**
 * 
 */
exception SpecNotFoundException {
}

/**
 *
 */
exception CalendNotFoundException {
}

/**
 * 
 */
exception VrachNotFoundException {
}

/**
 * 
 */
exception NormNotFoundException {
}

/**
 * 
 */
exception NdvNotFoundException {
}

/**
 * 
 */
exception NraspNotFoundException {
}
 
/**
 * 
 */
exception RaspNotFoundException {
}

/**
 * 
 */
exception TalonNotFoundException {
}

/**
 * 
 */
exception VidpNotFoundException {
}

/**
 * 
 */
exception AztNotFoundException {
}

service ThriftGenTalons extends kmiacServer.KmiacServer {
	/**
	*
	*/
	list<Spec> getAllSpecForPolikliniki(1:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse,
            2: SpecNotFoundException snfe);

	/**
	*
	*/
	list<Vrach> getVrachForCurrentSpec(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse,
            2: VrachNotFoundException vnfe);

	/**
	*
	*/
	list<Norm> getNorm(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse,
            2: NormNotFoundException nnfe);

	/**
	*
	*/
	list<Ndv> getNdv(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1:kmiacServer.KmiacServerException kse, 
            2: NdvNotFoundException nnfe);

	/**
	*
	*/
	list<Nrasp> getNrasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol, 4:i32 cxema) throws (1: kmiacServer.KmiacServerException kse,
            2: NraspNotFoundException nnfe);

	/**
	*
	*/
	list<Rasp> getRasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1:kmiacServer.KmiacServerException kse, 
            2:RaspNotFoundException rnfe);

	/**
	*
	*/
	list<Talon> getTalon(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol, 4:i64 datap) throws (1:kmiacServer.KmiacServerException kse,
            2: TalonNotFoundException tnfe);

	/**
	*
	*/
	list<classifier.IntegerClassifier> getVidp() throws (1: kmiacServer.KmiacServerException kse, 2: VidpNotFoundException vnfe);

	/**
	*
	*/
	list<classifier.IntegerClassifier> getAzt() throws (1: kmiacServer.KmiacServerException kse, 2: AztNotFoundException anfe);

	/**
	*
	*/
	Calend getCalendar(1:i64 datacal) throws (1: kmiacServer.KmiacServerException kse, 
            2: CalendNotFoundException cnfe);

	/**
	 */  
    	void addNrasp(1: list<Nrasp> nrasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	void addNdv(1: Ndv ndv) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void addNorm(1: list<Norm> norm) throws (1: kmiacServer.KmiacServerException kse);

 	/**
	*/
	void deleteNrasp(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	void deleteNdv(1:i32 id) throws (1: kmiacServer.KmiacServerException kse);
	void deleteNorm(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	void updateNrasp(1: list<Nrasp> nrasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	void updateNorm(1: list<Norm> norm) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	void updateNdv(1:list<Ndv> ndv) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	list<Nrasp> getNraspCpodr(1:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse, 
            2: NraspNotFoundException nnfe);

	/**
	*/
	list<Nrasp> getNraspCdol(1:i32 cpodr, 2:string cdol) throws (1: kmiacServer.KmiacServerException kse, 
            2: NraspNotFoundException nnfe);

	/**
	*/
	list<Nrasp> getNraspVrach(1:i32 cpodr, 2:i32 pcodvrach, 3:string cdol) throws (1: kmiacServer.KmiacServerException kse,
            2: NraspNotFoundException nnfe);

	/**
        */
	void addRasp(1: list<Rasp> rasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 addRaspReturnId(1: Rasp rasp) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteRaspCpodr(1:i64 datan, 2:i64 datak, 3:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteRaspCdol(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteRaspVrach(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:i32 pcodvrach, 5:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
	*/
	i32 getTalonCountCpodr(1:i64 datan, 2:i64 datak, 3:i32 cpodr) throws (1:
            kmiacServer.KmiacServerException kse)

	/**
	*/
	i32 getTalonCountCdol(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:string cdol) throws (1:
            kmiacServer.KmiacServerException kse)

	/**
	*/
	i32 getTalonCountVrach(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:i32 pcodvrach, 5:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteTalonCpodr(1:i64 datan, 2:i64 datak, 3:i32 cpodr) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteTalonCdol(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void deleteTalonVrach(1:i64 datan, 2:i64 datak, 3:i32 cpodr, 4:i32 pcodvrach, 5:string cdol) throws (1: kmiacServer.KmiacServerException kse);

	/**
        */
	void addTalons(1: list<Talon> talon) throws (1: kmiacServer.KmiacServerException kse);

	string printReport(1: RepStruct rep) throws (1: kmiacServer.KmiacServerException kse);

}

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
}


service ThriftViewSelect extends kmiacServer.KmiacServer {
	/**
	 * Информация из классификатора с pcod типа string
	 */
	list<classifier.StringClassifier> getVSStringClassifierView(),
	
	/**
	 * Информация из классификатора с pcod типа integer
	 */
	list<classifier.IntegerClassifier> getVSIntegerClassifierView()


	list<PatientBriefInfo> searchPatient(1: PatientSearchParams prms) throws (1: kmiacServer.KmiacServerException kse);
}
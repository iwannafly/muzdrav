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
}

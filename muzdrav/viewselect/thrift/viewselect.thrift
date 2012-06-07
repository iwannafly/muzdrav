namespace java ru.nkz.ivcgzo.thriftViewSelect

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift" 

service ThriftViewSelect extends kmiacServer.KmiacServer {
	/**
	 * Информация из классификатора с pcod типа string
	 */
	list<classifier.StringClassifier> getVSStringClassifierView(),
	
	/**
	 * Информация из классификатора с pcod типа integer
	 */
	list<classifier.IntegerClassifier> getVSIntegerClassifierView()
}
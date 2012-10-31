namespace java ru.nkz.ivcgzo.thriftMedication

include "../../../common/thrift/classifier.thrift"
include "../../../common/thrift/kmiacServer.thrift"

struct Patient {
	1: optional i32 id;
	2: optional string surname;
	3: optional string name;
	4: optional string middlename;
	5: optional i32 idGosp;
}

service ThriftMedication extends kmiacServer.KmiacServer {
	i32 getInt();
}


namespace java ru.nkz.ivcgzo.thriftOutputInfo

include "../../../common/thrift/kmiacServer.thrift"

struct Input_info {
    1: i32 kpolik;
    2: string namepol;
    3: string dateb;
    4: string datef;
    5: string vozcat;
}

struct SvodVed {
	1: optional i32 kodVidIssl;
	2: optional i32 userId;
	3: optional i32 npasp;
	4: optional string kodMetod;
	5: optional list<string> pokaz;
	6: optional string mesto;
	7: optional string kab;
	8: optional i32 pvizitId;
	9: optional string cpodr_name;
	10: optional string clpu_name;
}


service ThriftOutputInfo extends kmiacServer.KmiacServer {
    string printSvodVed(1: SvodVed sv) throws (1: kmiacServer.KmiacServerException kse);

}

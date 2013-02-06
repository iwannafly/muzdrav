namespace java ru.nkz.ivcgzo.thriftPrint

include "../../../common/thrift/kmiacServer.thrift"

struct Napr{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional string obosnov;
	4: optional string clpu;
	5: optional i32 pvizitId; 
	6: optional string cpodr_name;
	7: optional string clpu_name;
	8: optional string diag;
}

struct NaprKons{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional string obosnov;
	4: optional string cpol;
	5: optional string nazv;
	6: optional string cdol;
	7: optional i32 pvizitId;
	8: optional string cpodr_name;
	9: optional string clpu_name;
	10: optional string diag;
}

struct Protokol{
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional i32 pvizit_id;
	4: optional i32 pvizit_ambId;
	5: optional i32 cpol;
	6: optional string cpodr_name;
	7: optional string clpu_name;
	8: optional i32 nstr;
}

struct Vypis {
	1: optional i32 npasp;
	2: optional i32 userId;
	3: optional i32 pvizit_id;
	4: optional string cpodr_name;
	5: optional string clpu_name;
}

struct PNapr {
	1: i32 id;
	2: i32 idpvizit;
	3: i32 vid_doc;
	4: string text;
	5: i32 preds;
	6: i32 zaved;
	7: string name;
}

struct KartaBer {
	1: optional i32 npasp;
	2: optional i32 id_pvizit;
	3: optional i32 id_pos;
	4: optional i32 id_rd_sl;
}

struct SpravNetrud {
	1: optional string fam;
	2: optional string im;
	3: optional string oth;
	4: optional i64 datar;
	5: optional i32 npasp;
	6: optional string diag;
	7: optional i32 userId;
	8: optional string cpodr_name;
	9: optional string clpu_name;
}

service ThriftPrint extends kmiacServer.KmiacServer {


	string printNapr(1: Napr na) throws (1: kmiacServer.KmiacServerException kse);//госпитализация и обследование
	string printNaprKons(1: NaprKons nk) throws (1: kmiacServer.KmiacServerException kse);//консультация
	string printVypis(1: Vypis vp) throws (1: kmiacServer.KmiacServerException kse);//выписка.данные из бд по номеру посещения и по номеру обращения.возм...а возм и нет
	string printKek(1: i32 npasp, 2: i32 pvizitId) throws (1: kmiacServer.KmiacServerException kse);
	string printProtokol(1: Protokol pk) throws (1: kmiacServer.KmiacServerException kse);
	string printMSK(1: i32 npasp)  throws (1: kmiacServer.KmiacServerException kse);
	string printAnamZab(1: i32 id_pvizit) throws (1: kmiacServer.KmiacServerException kse);
	string printSpravNetrud(1: SpravNetrud sn) throws (1: kmiacServer.KmiacServerException kse);
	string printKartaBer(1:KartaBer kb) throws (1: kmiacServer.KmiacServerException kse);
}
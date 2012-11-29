namespace java ru.nkz.ivcgzo.thriftPbol

include "../../../common/thrift/kmiacServer.thrift"
struct Pbol{
	1: optional i32 id;
	2: optional i32 id_obr;
	3: optional i32 id_gosp;
	4: optional i32 npasp;
	5: optional i32 bol_l;
	6: optional i64 s_bl;
	7: optional i64 po_bl;
	8: optional i32 pol;

	9: optional i32 vozr;
	10: optional string nombl;
	11: optional i32 cod_sp;
	12: optional string cdol;
	13: optional i32 pcod;
	14: optional i64 dataz;
}

service ThriftPbol extends kmiacServer.KmiacServer {
	list<Pbol> getPbol (1: i32 npasp) throws (1: kmiacServer.KmiacServerException kse);
	i32 AddPbol(1: Pbol pbol) throws (1: kmiacServer.KmiacServerException kse);
	void UpdatePbol(1: Pbol pbol) throws (1: kmiacServer.KmiacServerException kse);
	void DeletePbol(1: i32 id) throws (1: kmiacServer.KmiacServerException kse);
}

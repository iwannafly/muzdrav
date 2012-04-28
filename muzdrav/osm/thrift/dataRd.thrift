namespace java ru.nkz.ivcgzo.thriftOsm

include "../../../common/thrift/kmiacServer.thrift"
include "../../../common/thrift/classifier.thrift"


/*�������� � ���������� �� ���� �� ������������ ����. Rd_Sl*/
struct RdSlStruct{
        1:i32 idDispb;
        2:i32 npasp;
        3:i64 datay;
        4:i32 kolpr;
        5:i32 abort;
        6:string oslAb;
        7:i64 dataosl;
        8:i64 dataM;
        9:i32 yavka1;
       10:string plrod;
       11:string prRod;
       12:i64 DataZs;
       13:i32 kolRod;
       14:i32 let,
       15:i32 prmen,
       16:i32 deti
       17:i32 polj,
       18:i32 kont,
       19:i32 oslrod,
       20:i32 rost,
       21:i32 vesd,
       22:i32 dsp,
       23:i32 dsr,
       24:i32 dTroch,
       25:i32 cext,
       26:i32 indSol,
       27:i64 Datasn 
}
/*�������� � �������� ���������� Rd_Din*/
struct RdDinStruct{
	1:i32 idDispb,
	2:i32 id,
	3:i64 datapos,
	4:i32 srok,
	5:i32 ves,
	6:i32 oj,
	7:i32 hdm,
	8:string dspos,
	9:i32 art1,
   10:i32 art2,
   11:i32 art3;
   12:i32 art4,
   13:i64 datasl,
   14:i32 spl,
   15:i32 oteki,
   16:i32 chcc,
   17:i32 polpl,
   18:i32 predpl,
   19:i32 serd,
   20:i32 serd1
}
service DataExchange {
	list<RdSlStruct> getRdSlInfo(1:i32 idDispb,2:i32 npasp),
	list<RdDinStruct> getRdDinInfo(1:i32 idDispb,2:i32 npasp),

	void AddRdSl(1:RdSlStruct rdSl),
	void AddRdDin(1:RdDinStruct RdDin),

	void DeleteRdSl(1:i32 idDispb,2:i32 npasp),
	void DeleteRdDin(1:i32 idDispb,2:i32 iD),

	void UpdateRdSl(1:i32 npasp, 2:i32 lgota),
	void UpdateRdDin(1:i32 idDispb,2:i32 iD),
}
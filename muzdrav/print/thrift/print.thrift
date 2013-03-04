namespace java ru.nkz.ivcgzo.thriftPrint

include "../../../common/thrift/kmiacServer.thrift"





service ThriftPrint extends kmiacServer.KmiacServer {


/**
	 * Печать вкладыша в амбулаторную карту
	 */
	string printProtokol(1: i32 npasp, 2: i32 userId, 3: i32 pvizit_id, 4: i32 pvizit_ambId, 5: i32 cpol, 6: i32 clpu, 7: i32 nstr) throws (1: kmiacServer.KmiacServerException kse);
/**
	 * Печать справки в бассейн
	 */
	string printSprBass(1: i32 npasp, 2: i32 pol) throws (1: kmiacServer.KmiacServerException kse);

/**
	 * Печать амбулаторный карты
	 */
	string printMedCart(1: i32 npasp, 2: i32 clpu, 3: i32 cpol) throws (1:kmiacServer.KmiacServerException kse);
	

}
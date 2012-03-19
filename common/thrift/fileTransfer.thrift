namespace java ru.nkz.ivcgzo.thriftCommon.fileTransfer

include "kmiacServer.thrift"

/**
 * Файла с таким именем, доступного для чтения, не существует.
 */
exception FileNotFoundException {
}

/**
 * Ошибка открытия файла на чтение или запись.
 */
exception OpenFileException {
}

/**
 * Размер буфера для передачи данных.
 */
const i32 bufSize = 65536

service FileTransfer extends kmiacServer.KmiacServer {
	/**
	 * Открывает сокет для передачи данных с сервера на клиент.
	 */
	i32 openReadServerSocket(1: string path) throws (1: FileNotFoundException fnf, 2: OpenFileException ofe);
	
	/**
	 * Открывает сокет для передачи данных с клиента на сервер.
	 */
	i32 openWriteServerSocket(1: string path) throws (1: OpenFileException ofe);
	
	/**
	 * Закрывает сокет, удаляя по необходимости ранее открытый файл.
	 */
	void closeServerSocket(1: i32 port, 2: bool delFile);
	
}
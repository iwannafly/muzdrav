namespace java ru.nkz.ivcgzo.thriftCommon.libraryUpdater

include "fileTransfer.thrift"

/**
 * Информация о модуле.
 */
struct LibraryInfo {
	1: i32 id;
	2: string name;
	3: string md5;
	4: i32 size;
}

/**
 * Модуль системы не найден на сервере.
 */
exception ModuleNotFound {
}

/**
 * Класс для поддержания на клиенте актуальных версий общих модулей системы.
 */
service LibraryUpdater extends fileTransfer.FileTransfer {
	/**
	 * Получает список всех модулей системы.
	 */
	list<LibraryInfo> getModulesList();
	
	/**
	 * Открывает сокет для загрузки модуля системы на клиент.
	 */
	i32 openModuleReadSocket(1: i32 id) throws (1: ModuleNotFound mnf, 2: fileTransfer.OpenFileException ofe);
	
	/**
	 * Закрывает загрузочный сокет.
	 */
	void closeReadSocket(1: i32 port);
	
}
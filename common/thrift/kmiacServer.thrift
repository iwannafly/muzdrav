namespace java ru.nkz.ivcgzo.thriftCommon.kmiacServer

service KmiacServer {
	/**
	* Получает версию сервера.
	* Так же может использоваться для проверки соединения.
	*/
	string getServerVersion();

	/**
	* Получает версию клиента (для обновления).
	*/
	string getClientVersion();
}
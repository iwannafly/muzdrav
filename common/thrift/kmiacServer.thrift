namespace java ru.nkz.ivcgzo.thriftCommon.kmiacServer

/**
 * Информация о вошедшем в систему пользователе.
 */
struct UserAuthInfo {
	1: i32 pcod;
	2: i32 clpu;
	3: i32 cpodr;
	4: string pdost;
	5: string name;
	6: i32 user_id;
}

/**
 * Исключение для проброса вместо TException.
 */
exception KmiacServerException {
	1:  string message;
}

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

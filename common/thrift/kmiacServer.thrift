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
	 7: string config;
	 8: string cdol;
	 9: string cdol_name;
	10: string name_short;
}

/**
 * Исключение для проброса вместо TException.
 */
exception KmiacServerException {
	1:  string message;
}

service KmiacServer {
	/**
	 * Проверка соединения клиента с сервером.
	 */
	void testConnection();
	
	/**
	 * Сохранение настроек.
	 */
	void saveUserConfig(1: i32 id, 2: string config);
}

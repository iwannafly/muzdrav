namespace java ru.nkz.ivcgzo.thriftServerStaticInputVrachInfoAdmin

include "vrachInfo.thrift"

service ThriftServerStaticInputVrachInfoAdmin extends vrachInfo.ThriftServerStaticInputVrachInfo {
	/**
	 * Получает логин пользователя.
	 */
	string getLogin(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Устанавливает пароль для пользователя, открывая ему доступ к системе.
	 */
	string setPassword(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod, 4: string login);

	/**
	 * Очищает пароль пользователя, закрывая ему доступ к системе.
	 */
	void remPassword(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Получает разрешения пользователя, то есть, к каким частям системы у него есть доступ.
	 */
	string getPermissions(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod);

	/**
	 * Устанавливает разрешения пользователя.
	 */
	void setPermissions(1: i32 vrachPcod, 2: i32 lpuPcod, 3: i32 podrPcod, 4: string pdost);

}

namespace java ru.nkz.ivcgzo.thriftServerAuth

include "../../../common/thrift/fileTransfer.thrift"

struct UserAuthInfo {
	1: i32 pcod;
	2: i32 clpu;
	3: i32 cpodr;
	4: string pdost;
	5: string name;
}

/**
 * Пользователя с таким логином и паролем не найдено.
 */
exception UserNotFoundException {
}

service ThriftServerAuth extends fileTransfer.FileTransfer {
	/**
	 * Процедура аутентификации.
	 */
	UserAuthInfo auth(1: string login, 2: string password) throws (1: UserNotFoundException unf);
	
}

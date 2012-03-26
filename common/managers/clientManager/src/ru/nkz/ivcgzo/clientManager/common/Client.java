package ru.nkz.ivcgzo.clientManager.common;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public abstract class Client implements IClient {
	public ConnectionManager conMan;
	public UserAuthInfo authInfo;
	public int lncPrm;
	
	public Client(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) {
		this.conMan = conMan;
		this.authInfo = authInfo;
		this.lncPrm = lncPrm;
	}
}

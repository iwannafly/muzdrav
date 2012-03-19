package ru.nkz.ivcgzo.clientManager.common;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;

public interface IClient {
	String getVersion();
	int getId();
	String getName();
	void onConnect(KmiacServer.Client conn);
}

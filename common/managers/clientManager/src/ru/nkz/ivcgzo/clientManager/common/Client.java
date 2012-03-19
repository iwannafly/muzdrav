package ru.nkz.ivcgzo.clientManager.common;

public abstract class Client implements IClient {
	public ConnectionManager conMan;
	public int lncPrm;
	
	public Client(ConnectionManager conMan, int lncPrm) {
		this.conMan = conMan;
		this.lncPrm = lncPrm;
	}
}

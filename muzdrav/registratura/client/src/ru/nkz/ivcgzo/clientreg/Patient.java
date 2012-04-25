package ru.nkz.ivcgzo.clientreg;

import java.util.Date;

public class Patient {
	private int npasp;
	private String fam;
	private String im;
	private String ot;
	private Date datar;
	private String spolis;
	private String npolis;
	private String adpAddress;
	private String admAddress;

	public Patient(int inNpasp, String inFam, String inIm, String inOt, Date inDatar,
			     String inSpolis, String inNpolis, String inAdrp, String inAdrm) {
	this.npasp = inNpasp;
	this.fam = inFam;
	this.im = inIm;
	this.ot = inOt;
	this.datar = inDatar;
	this.spolis = inSpolis;
	this.npolis = inNpolis;
	this.adpAddress = inAdrp;
	this.admAddress = inAdrm;
}

public int getNpasp(){
	return this.npasp;
}

public String getFam(){
	return this.fam;
}

public String getIm(){
	return this.im;
}

public String getOt(){
	return this.ot;
}

public Date getDatar(){
	return this.datar;
}

public String getSpolis(){
	return this.spolis;
}

public String getNpolis(){
	return this.npolis;
}

public String getAdpAddress(){
	return this.adpAddress;
}

public String getAdmAddress(){
	return this.admAddress;
}

}

package ru.nkz.ivcgzo.Infomat;

public class Patient {
    private int npasp;
    private String fam;
    private String im;
    private String ot;

    public Patient(int npasp, String fam, String im, String ot) {
        super();
        this.npasp = npasp;
        this.fam = fam;
        this.im = im;
        this.ot = ot;
    }

    public int getNpasp() {
        return npasp;
    }

    public String getFam() {
        return fam;
    }
    
    public String getIm() {
        return im;
    }
    
    public String getOt() {
        return ot;
    }
    
    public void setNpasp(int npasp) {
        this.npasp = npasp;
    }
    
    public void setFam(String fam) {
        this.fam = fam;
    }
    
    public void setIm(String im) {
        this.im = im;
    }
    
    public void setOt(String ot) {
        this.ot = ot;
    }
}

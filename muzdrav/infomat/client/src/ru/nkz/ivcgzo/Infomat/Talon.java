package ru.nkz.ivcgzo.Infomat;

import java.sql.Time;
import java.sql.Date;

public class Talon {
    private int id;
    private int vidp;
    private Time timep;
    private Date datap;
    private int npasp;
    private int pcodSp;
    private String lpuName;
    private String doctorSpec;
    private String doctorFio;

    public Talon(int inId, int inVidp, Time inTimep, Date inDatep, int inNpasp, int inPcodSp) {
        id = inId;
        vidp = inVidp;
        timep = inTimep;
        datap = inDatep;
        npasp = inNpasp;
        pcodSp = inPcodSp;
    }

    public Talon(int id, int vidp, Time timep, Date datap, int npasp,
            int pcodSp, String lpuName, String doctorSpec, String doctorFio) {
        super();
        this.id = id;
        this.vidp = vidp;
        this.timep = timep;
        this.datap = datap;
        this.npasp = npasp;
        this.pcodSp = pcodSp;
        this.setDoctorSpec(doctorSpec);
        this.doctorFio = doctorFio;
        this.setLpuName(lpuName);
    }

    public int getId() {
        return id;
    }

    public int getVidp() {
        return vidp;
    }

    public Time getTimep() {
        return timep;
    }

    public Date getDatap() {
        return datap;
    }

    public int getNpasp() {
        return npasp;
    }

    public int getPcodSp() {
        return pcodSp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVidp(int vidp) {
        this.vidp = vidp;
    }

    public void setTimep(Time timep) {
        this.timep = timep;
    }

    public void setDatap(Date datap) {
        this.datap = datap;
    }

    public void setNpasp(int npasp) {
        this.npasp = npasp;
    }

    public void setPcodSp(int pcodSp) {
        this.pcodSp = pcodSp;
    }

    public String getDoctorFio() {
        return doctorFio;
    }

    public void setDoctorFio(String doctorFio) {
        this.doctorFio = doctorFio;
    }

    public String getDoctorSpec() {
        return doctorSpec;
    }

    public void setDoctorSpec(String doctorSpec) {
        this.doctorSpec = doctorSpec;
    }

    public String getLpuName() {
        return lpuName;
    }

    public void setLpuName(String lpuName) {
        this.lpuName = lpuName;
    }

}

package ru.nkz.ivcgzo.Infomat;

import java.sql.Time;
import java.sql.Date;

public class Talon {
    public int id;
    public int vidp;
    public Time timep;
    public Date datap;
    public int npasp;
    public int pcodSp;

    public Talon(int inId, int inVidp, Time inTimep, Date inDatep, int inNpasp, int inPcodSp) {
        id = inId;
        vidp = inVidp;
        timep = inTimep;
        datap = inDatep;
        npasp = inNpasp;
        pcodSp = inPcodSp;
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

}

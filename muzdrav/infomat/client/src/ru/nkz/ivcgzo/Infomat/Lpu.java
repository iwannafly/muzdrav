package ru.nkz.ivcgzo.Infomat;

public class Lpu {
    private int pcod;
    private String name;

    public Lpu(int inPcod, String inName) {
        pcod = inPcod;
        name = inName;
    }

    public int getPcod() {
        return pcod;
    }

    public String getName() {
        return name;
    }

    public void setCdol(int inPcod) {
        pcod = inPcod;
    }

    public void setName(String inName) {
        name = inName;
    }

    public String toString() {
        return name;
    }
}

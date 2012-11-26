package ru.nkz.ivcgzo.Infomat;

public class Lpu {
    private String pcod;
    private String name;

    public Lpu(String inPcod, String inName) {
        pcod = inPcod;
        name = inName;
    }

    public String getPcod() {
        return pcod;
    }

    public String getName() {
        return name;
    }

    public void setCdol(String inPcod) {
        pcod = inPcod;
    }

    public void setName(String inName) {
        name = inName;
    }

    public String toString() {
        return name;
    }
}

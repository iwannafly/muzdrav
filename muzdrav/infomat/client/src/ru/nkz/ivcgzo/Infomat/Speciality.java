package ru.nkz.ivcgzo.Infomat;

public class Speciality {
    private String cdol;
    private String name;

    public Speciality(String inCdol, String inName) {
        cdol = inCdol;
        name = inName;
    }

    public String getCdol() {
        return cdol;
    }

    public String getName() {
        return name;
    }

    public void setCdol(String inCdol) {
        cdol = inCdol;
    }

    public void setName(String inName) {
        name = inName;
    }

    public String toString() {
        return name;
    }
}

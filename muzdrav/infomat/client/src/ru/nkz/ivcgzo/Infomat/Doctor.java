package ru.nkz.ivcgzo.Infomat;

public class Doctor {
    private int pcod;
    private String cdol;
    private String surname;
    private String name;
    private String middlename;

    public Doctor(int pcod, String cdol, String surname, String name,
            String middlename) {
        this.pcod = pcod;
        this.cdol = cdol;
        this.surname = surname;
        this.name = name;
        this.middlename = middlename;
    }

    public int getPcod() {
        return pcod;
    }

    public String getCdol() {
        return cdol;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setPcod(int pcod) {
        this.pcod = pcod;
    }

    public void setCdol(String cdol) {
        this.cdol = cdol;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    private String getShortName() {
        return String.format("%s %s. %s.", surname, name.charAt(0), middlename.charAt(0));
    }

    public String toString() {
        return getShortName();
    }
}

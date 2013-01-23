package ru.nkz.ivcgzo.clientOperation.model;

public class Patient {
    private int pcod;
    private String surname;
    private String name;
    private String middlename;
    private int idGosp;

    public Patient(int pcod, String surname, String name, String middlename, int idGosp) {
        super();
        this.pcod = pcod;
        this.surname = surname;
        this.name = name;
        this.middlename = middlename;
        this.idGosp = idGosp;
    }

    public int getPcod() {
        return pcod;
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
    public int getIdGosp() {
        return idGosp;
    }
    public void setPcod(int pcod) {
        this.pcod = pcod;
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
    public void setIdGosp(int idGosp) {
        this.idGosp = idGosp;
    }
}

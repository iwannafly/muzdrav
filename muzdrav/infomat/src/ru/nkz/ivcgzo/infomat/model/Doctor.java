package ru.nkz.ivcgzo.infomat.model;

import java.util.List;

/**
 * @version 1.0
 * @created 22-май-2012 14:02:44
 */
public class Doctor {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    /**
     * Персональный номер врача
     */
    private int pcod;
    /**
     * Фамилия врача
     */
    private String surname;
    /**
     * Имя врача
     */
    private String name;
    /**
     * Отчество врача
     */
    private String middlename;
    /**
     * Специальность врача
     */
    private String speciality;
    /**
     * Номер кабинета врача
     */
    private int cabinet;

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

    public Doctor() {
    }

    /**
     * @param pcod
     * @param surname
     * @param name
     * @param middlename
     * @param speciality
     * @param cabinet
     */
    public Doctor(final int inPcod, final String inSurname, final String inName,
            final String inMiddlename, final String inSpeciality, final int inCabinet) {
        this.pcod = inPcod;
        this.surname = inSurname;
        this.name = inName;
        this.middlename = inMiddlename;
        this.speciality = inSpeciality;
        this.cabinet = inCabinet;
    }

////////////////////////////////////////////////////////////////////////
//                           Getters                                  //
////////////////////////////////////////////////////////////////////////

    public final int getPcod() {
        return pcod;
    }

    public final String getSurname() {
        return this.surname;
    }

    public final String getName() {
        return this.name;
    }

    public final String getMiddlename() {
        return this.middlename;
    }

    public final String getSpeciality() {
        return this.speciality;
    }

    public final int getCabinet() {
        return this.cabinet;
    }

////////////////////////////////////////////////////////////////////////
//                           Setters                                  //
////////////////////////////////////////////////////////////////////////

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

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

    /**
     * Возвращает краткую запись Ф.И.О. врача в виде: Фамилия И. О.
     */
    public final String getShortName() {
        if  (surname != null) {
            if ((name != null) && (middlename != null)) {
                if ((!name.isEmpty()) && (!middlename.isEmpty())) {
                    return String.format("%s %S. %S.",
                            surname, name.charAt(0), middlename.charAt(0));
                }
                return String.format("%s", surname);
            }
        }
        return "";
    }

    public static List<Doctor> getAllDoctorsOfThisSpeciality() {
        return null;
    }

}

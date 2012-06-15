package ru.nkz.ivcgzo.infomat.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.nkz.ivcgzo.infomat.dbLevel.DbConnectionManager;

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

    public final void setPcod(final int inPcod) {
        this.pcod = inPcod;
    }

    public final void setSurname(final String inSurname) {
        this.surname = inSurname;
    }

    public final void setName(final String inName) {
        this.name = inName;
    }

    public final void setMiddlename(final String inMiddlename) {
        this.middlename = inMiddlename;
    }

    public final void setSpeciality(final String inSpeciality) {
        this.speciality = inSpeciality;
    }

    public final void setCabinet(final int inCabinet) {
        this.cabinet = inCabinet;
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

    public static List<Doctor> getAllDoctorsOfThisSpeciality(final int specialityCod) {
        String sqlQuery = "SELECT npasp FROM patient "
                + "WHERE ((poms_ser || poms_nom) = ?) OR (poms_nom = ?);";
        Connection connection = DbConnectionManager.getInstance().getConnection();
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = connection.prepareStatement(sqlQuery);
            prepStatement.setString(1, ,);
            prepStatement.setString(2, i,nputPomsFull);
            rs = prepStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (prepStatement != null) {
                prepStatement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

}

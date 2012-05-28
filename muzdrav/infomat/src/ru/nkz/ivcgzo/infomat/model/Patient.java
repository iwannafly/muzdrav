package ru.nkz.ivcgzo.infomat.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.nkz.ivcgzo.infomat.dbLevel.DbConnectionManager;

/**
 * @version 1.0
 * @created 22-май-2012 14:03:45
 */
public class Patient {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private static final String[] FIELDS = {"NPASP", "FAM", "IM", "OT",
        "POMS_SER", "POMS_NOM"};

    /**
     * Персональный код пациента
     */
    private int pcod;

    /**
     * Фамилия пациента
     */
    private String surname;

    /**
     * Имя пациента
     */
    private String name;

    /**
     * Отчество пациента
     */
    private String middlename;

    /**
     * Серия страхового полиса ОМС пациента
     */
    private String pomsSer;

    /**
     * Номер страхового полиса ОМС пациента
     */
    private String pomsNum;

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

    /**
     * Конструктор по умолчанию.
     * Создан для модульного тестирования. При необходимости можно удалить.
     */
    public Patient() {
    }

    /**
     * Открытый конструктор с параметром
     * @param polisOms - серия и номер полиса одной строкой
     * @throws SQLException
     */
    public Patient(final String polisOms) throws SQLException {
        selectFromDb(polisOms);
    }

////////////////////////////////////////////////////////////////////////
//                           Getters                                  //
////////////////////////////////////////////////////////////////////////

    public final int getPcod() {
        return pcod;
    }

    public final String getSurname() {
        return surname;
    }

    public final String getName() {
        return name;
    }

    public final String getMiddlename() {
        return middlename;
    }

    public final String getPomsSer() {
        return pomsSer;
    }

    public final String getPomsNum() {
        return pomsNum;
    }

////////////////////////////////////////////////////////////////////////
//                           Setters                                  //
////////////////////////////////////////////////////////////////////////

    public final void setPcod(final int inputPcod) {
        this.pcod = inputPcod;
    }

    public final void setSurname(final String inputSurname) {
        this.surname = inputSurname;
    }

    public final void setName(final String inputName) {
        this.name = inputName;
    }

    public final void setMiddlename(final String inputMiddlename) {
        this.middlename = inputMiddlename;
    }

    public final void setPomsSer(final String inputPomsSer) {
        this.pomsSer = inputPomsSer;
    }

    public final void setPomsNum(final String inputPomsNum) {
        this.pomsNum = inputPomsNum;
    }

////////////////////////////////////////////////////////////////////////
//                       Private Methods                              //
////////////////////////////////////////////////////////////////////////

    /**
     *
     * @param pomsFull - серия и номер полиса ОМС одной строкой
     * @throws SQLException
     */
    private void selectFromDb(final String inputPomsFull) throws SQLException {
        String sqlQuery = "SELECT npasp, fam, im, ot, poms_ser, poms_nom "
                + "FROM patient WHERE ((poms_ser || poms_nom) = ?) OR (poms_nom = ?);";
        Connection connection = DbConnectionManager.getInstance().getConnection();
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = connection.prepareStatement(sqlQuery);
            prepStatement.setString(1, inputPomsFull);
            prepStatement.setString(2, inputPomsFull);
            rs = prepStatement.executeQuery();
            int position = 0;
            if (rs.next()) {
                fillFields(rs.getInt(FIELDS[position]), rs.getString(FIELDS[++position]),
                        rs.getString(FIELDS[++position]), rs.getString(FIELDS[++position]),
                        rs.getString(FIELDS[++position]), rs.getString(FIELDS[++position]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null) {
                prepStatement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
    * Заполняет поля объекта
    * @throws SQLException
    */
    private void fillFields(final int inPcod, final String inSurname, final String inName,
           final String inMiddlename, final String inPomsSer,
           final String inPomsNum) {
        this.pcod = inPcod;
        this.surname = inSurname;
        this.name = inName;
        this.middlename = inMiddlename;
        this.pomsSer = inPomsSer;
        this.pomsNum = inPomsNum;
    }

////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

    /**
     * Преобразует ФИО пациента в краткую запись.
     * @return String, в котором:
     * <ul>
     *     <li>Если все поля заданы и не пустые - строка вида: Фамилия И. О.</l1>
     *     <li>Если имя или отчество пустое, либо не задано - Фамилия</l1>
     *     <li>Если фамилия не задана - пустая строку</l1>
     * </ul>
     *
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

    /**
     * Проверяет введенные серию и номер полиса на наличие в базе.
     * По сути является механизмом авторизации пациента на инфомате.
     * @param pomsFull - серия и номер полиса ОМС одной строкой
     * @throws SQLException
     */
    public static boolean isInsuranceNumberValid(final String inputPomsFull) throws SQLException {
        String sqlQuery = "SELECT npasp FROM patient "
                + "WHERE ((poms_ser || poms_nom) = ?) OR (poms_nom = ?);";
        Connection connection = DbConnectionManager.getInstance().getConnection();
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = connection.prepareStatement(sqlQuery);
            prepStatement.setString(1, inputPomsFull);
            prepStatement.setString(2, inputPomsFull);
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

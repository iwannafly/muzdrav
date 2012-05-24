package ru.nkz.ivcgzo.infomat.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.infomat.model.Patient;

/**
 * @author Avdeev Alexander
 */
public class TestPatient {
    private Patient  patient;

    @Before
    public final void setUp() throws Exception {
    }

    @Test
    public final void getShortName_isCorrectWithFullFields() {
        patient = new Patient();
        patient.setSurname("Петров");
        patient.setName("Василий");
        patient.setMiddlename("Иванович");
        assertEquals("Проверка с заполненными полями пациента",
                patient.getShortName(), "Петров В. И.");
        patient = null;
    }

    @Test
    public final void getShortName_isCorrectWithOneEmptyField() {
        patient = new Patient();
        patient.setSurname("Петров");
        patient.setName("Василий");
        patient.setMiddlename("");
        assertEquals("Проверка с одним пустым",
                patient.getShortName(), "Петров");
        patient = null;
    }

    @Test
    public final void getShortName_isCorrectWithBothEmptyFields() {
        patient = new Patient();
        patient.setSurname("");
        patient.setName("");
        patient.setMiddlename("");
        assertEquals("Проверка с пустыми полями",
                patient.getShortName(), "");
        patient = null;
    }

    @Test
    public final void getShortName_isCorrectWithNullFields() {
        patient = new Patient();
        assertEquals("Проверка с null полями",
                patient.getShortName(), "");
        patient = null;
    }

    @Test
    public final void selectFromDb_isCorrectWithExistedPatient() throws SQLException {
        patient = new Patient("43090551104");
        assertEquals("Персональный номер пациента",
                patient.getPcod(), 2);
        assertEquals("Фамилия пациента",
                patient.getSurname(), "БЕЛОГОРОДЦЕВА");
        assertEquals("Имя пациента",
                patient.getName(), "ДИАНА");
        assertEquals("Отчество пациента",
                patient.getMiddlename(), "АЛЕКСЕЕВНА");
        assertEquals("Серия полиса",
                patient.getPomsSer(), null);
        assertEquals("Номер полиса пациента",
                patient.getPomsNum(), "43090551104");
    }

    @Test
    public final void selectFromDb_isInvalidWithNonexistentPatient() throws SQLException {
        patient = new Patient("0454054");
        assertEquals("Проверка создания пациента с несуществующим номером полиса",
                patient.getName(), null);
    }

    @Test
    public final void isInsuranceNumberValid_isValidWithCorrectNumber() throws SQLException {
        assertEquals("Проверка существующего полиса на валидность",
                Patient.isInsuranceNumberValid("43090551104"), true);
    }

    @Test
    public final void isInsuranceNumberValid_isInvalidWithInvalidNumber() throws SQLException {
        assertEquals("Проверка существующего полиса на валидность",
                Patient.isInsuranceNumberValid("00099009090"), false);
    }
}

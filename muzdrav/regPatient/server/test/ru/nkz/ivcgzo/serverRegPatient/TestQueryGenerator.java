/**
 */
package ru.nkz.ivcgzo.serverRegPatient;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;

/**
 * @author Avdeev Alexander
 */
public class TestQueryGenerator {
    private String  sqlQuery;
    private static final String[] SQL_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
    };
    private QueryGenerator<PatientBrief> pqg =
            new QueryGenerator<PatientBrief>(PatientBrief.class, SQL_FIELD_NAMES);
    private PatientBrief testPatient2Field;
    private PatientBrief testPatientEmpty;

    @Rule
    public ExpectedException testException = ExpectedException.none();

    @Before
    public final void setUp() throws Exception {
        sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv FROM patient";

        testPatient2Field = new PatientBrief();
        testPatient2Field.setNpasp(1);
        testPatient2Field.setIm("Иван");

        testPatientEmpty = new PatientBrief();
    }

    @Test
    public final void genSelectQuery_isQueryTextCorrectOnFilledObject() {
        String testSqlQuery = pqg.genSelectQuery(testPatient2Field, sqlQuery);
        assertEquals("comparison of the query text",
                sqlQuery + " WHERE im = ? AND npasp = ?;", testSqlQuery);
    }

    @Test
    public final void genSelectQuery_isQueryTextCorrectOnEmptyObject() {
        String testSqlQuery = pqg.genSelectQuery(testPatientEmpty, sqlQuery);
        assertEquals("comparison of the query text",
                sqlQuery + ";", testSqlQuery);
    }

    @Test
    public final void genIndexes_isIndexesLengthCorrectOnFilledObject() {
        int[] testIndexes = pqg.genIndexes(testPatient2Field);
        assertEquals("length of the indexes array", 2, testIndexes.length);
    }

    @Test
    public final void genIndexes_isIndexesLengthCorrectOnEmptyObject() {
        int[] testIndexes = pqg.genIndexes(testPatient2Field);
        assertEquals("first element", 2, testIndexes[0]);
        assertEquals("second element", 0, testIndexes[1]);
    }

    @Test
    public final void genIndexes_isIndexesValueCorrectOnFilledObject() {
        int[] testIndexes = pqg.genIndexes(testPatientEmpty);
        assertEquals("length of the indexes array", 0, testIndexes.length);
    }

    @Test
    public final void genIndexes_isThrowExceptionOnEmptyObject() {
        int[] testIndexes = pqg.genIndexes(testPatientEmpty);
        testException.expect(IndexOutOfBoundsException.class);
        assertEquals("first element", 2, testIndexes[0]);
        assertEquals("second element", 0, testIndexes[1]);
    }

}

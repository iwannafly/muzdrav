/**
 */
package ru.nkz.ivcgzo.serverRegPatient;

import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;

/**
 * @author as
 *
 */
public class TestQueryGenerator {
    private String  sqlQuery;
    private static final String[] SQL_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
    };
    private QueryGenerator<PatientBrief> pqg =
            new QueryGenerator<PatientBrief>(PatientBrief.class, SQL_FIELD_NAMES);

    private PatientBrief patient;

    @Before
    public final void setUp() throws Exception {
        sqlQuery = "SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom, "
                + "adp_obl, adp_gorod, adp_ul, adp_dom, adp_kv, "
                + "adm_obl, adm_gorod, adm_ul, adm_dom, adm_kv FROM patient";
    }

    @Test
    public final void testGenerateQueryEmptyFields() {

        patient = new PatientBrief();
        patient.setNpasp(1);
        patient.setIm("Иван");
        System.out.println(pqg.genSelectQuery(patient, sqlQuery));
        if (pqg.genIndexes(patient).length != 0) {
            for (int i:pqg.genIndexes(patient)) {
                System.out.print(i + " ");
            }
        }

        System.out.println();

        patient.unsetNpasp();
        patient.unsetIm();
        System.out.println(pqg.genSelectQuery(patient, sqlQuery));
        if (pqg.genIndexes(patient).length != 0) {
            for (int i:pqg.genIndexes(patient)) {
                System.out.print(i + " ");
            }
        }
    }

}

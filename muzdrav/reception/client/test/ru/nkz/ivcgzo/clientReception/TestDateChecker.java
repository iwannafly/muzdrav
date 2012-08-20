package ru.nkz.ivcgzo.clientReception;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Avdeev Alexander
 */
public class TestDateChecker {
    private DateChecker testDateChecker;

    @Before
    public final void setUp() throws Exception {
        testDateChecker = new DateChecker();
    }

    @Test
    public final void getCurrentWeek() {
        Date[] wk = testDateChecker.getCurrentWeek();
        for (int i = 0; i < 7; i++) {
            System.out.println(wk[i]);
        }
        testDateChecker.nextWeek();
        for (Date day:testDateChecker.getCurrentWeek()) {
            System.out.println(day);
        }
    }
}

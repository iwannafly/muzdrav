package ru.nkz.ivcgzo.clientReception;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

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
        GregorianCalendar calend = new GregorianCalendar();
        System.out.println(calend.getMaximum(Calendar.DAY_OF_WEEK));
    }
}

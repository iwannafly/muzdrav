package ru.nkz.ivcgzo.clientReception;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateChecker {
    GregorianCalendar calend;
    private Date[] curWeek = new Date[7];

    public DateChecker() {
        setCalendarDefaults();
        fillCurrentWeekArray();
    }

    public final Date[] getCurrentWeek() {
        return curWeek;
    }

    private void setCalendarDefaults() {
        calend = new GregorianCalendar();
        calend.setFirstDayOfWeek(Calendar.MONDAY);
        //calend.setTime(new Date());
        //calend.set(Calendar.DAY_OF_WEEK, calend.getFirstDayOfWeek());
    }

    private void fillCurrentWeekArray() {
        calend.set(Calendar.DAY_OF_WEEK, calend.getFirstDayOfWeek());
        for (int i = 0; i < 7; i++) {
            curWeek[i] = calend.getTime();
            calend.set(Calendar.DATE, calend.get(Calendar.DATE) + 1);
        }
        calend.set(Calendar.DATE, calend.get(Calendar.DATE) - 7);
    }

    public final void nextWeek() {
        calend.set(Calendar.WEEK_OF_YEAR, calend.get(Calendar.WEEK_OF_YEAR) + 1);
        fillCurrentWeekArray();
    }

    public final void prevWeek() {
        calend.set(Calendar.WEEK_OF_YEAR, calend.get(Calendar.WEEK_OF_YEAR) - 1);
        fillCurrentWeekArray();
    }

}

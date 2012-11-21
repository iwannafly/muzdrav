package ru.nkz.ivcgzo.Infomat;

import java.sql.Time;

public class SheduleDay {
    private Time timeStart;
    private Time timeEnd;
    private int vidp;
    private int weekDay;

    public SheduleDay(Time timeStart, Time timeEnd, int vidp, int weekDay) {
        super();
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.vidp = vidp;
        this.weekDay = weekDay;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public int getVidp() {
        return vidp;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setVidp(int vidp) {
        this.vidp = vidp;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }


}

package ru.nkz.ivcgzo.clientInfomat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import ru.nkz.ivcgzo.thriftInfomat.TTalon;

public class TalonList {
    private DateChecker dateChecker;
    private List<TTalon> allTalonList;
    private List<TTalon> mondayTalonList;
    private List<TTalon> tuesdayTalonList;
    private List<TTalon> wednesdayTalonList;
    private List<TTalon> thursdayTalonList;
    private List<TTalon> fridayTalonList;
    private List<TTalon> saturdayTalonList;
    private List<TTalon> sundayTalonList;

    public TalonList() {
        dateChecker = new DateChecker();
        mondayTalonList = Collections.emptyList();
        tuesdayTalonList = Collections.emptyList();
        wednesdayTalonList = Collections.emptyList();
        thursdayTalonList = Collections.emptyList();
        fridayTalonList = Collections.emptyList();
        saturdayTalonList = Collections.emptyList();
        sundayTalonList = Collections.emptyList();
    }

    public TalonList(final List<TTalon> list) {
        allTalonList = list;
        dateChecker = new DateChecker();
        partitionWeekTalonList(allTalonList);
    }

    public final List<TTalon> getMondayTalonList() {
        return mondayTalonList;
    }

    public final List<TTalon> getTuesdayTalonList() {
        return tuesdayTalonList;
    }

    public final List<TTalon> getWednesdayTalonList() {
        return wednesdayTalonList;
    }

    public final List<TTalon> getThursdayTalonList() {
        return thursdayTalonList;
    }

    public final List<TTalon> getFridayTalonList() {
        return fridayTalonList;
    }

    public final List<TTalon> getSaturdayTalonList() {
        return saturdayTalonList;
    }

    public final List<TTalon> getSundayTalonList() {
        return sundayTalonList;
    }

    public final int getMaximumListSize() {
        int[] maxSizeArray = {
            mondayTalonList.size(), tuesdayTalonList.size(), wednesdayTalonList.size(),
            thursdayTalonList.size(), fridayTalonList.size(), saturdayTalonList.size(),
            sundayTalonList.size()
        };
        int max = maxSizeArray[0];
        for (int i = 1; i < maxSizeArray.length; i++) {
            if (maxSizeArray[i] > max) {
                max = maxSizeArray[i];
            }
        }
        return max;
    }

    public final Date[] getWeekDays() {
        return dateChecker.getCurrentWeek();
    }

    public final void setPrevWeek() {
        dateChecker.prevWeek();
        if (allTalonList != null) {
            partitionWeekTalonList(allTalonList);
        }
    }

    public final void setNextWeek() {
        dateChecker.nextWeek();
        if (allTalonList != null) {
            partitionWeekTalonList(allTalonList);
        }
    }

    private void partitionWeekTalonList(final List<TTalon> weekTalonList) {
        mondayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[0]);
        tuesdayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[1]);
        wednesdayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[2]);
        thursdayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[3]);
        fridayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[4]);
        saturdayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[5]);
        sundayTalonList = createDayTalonList(weekTalonList, dateChecker.getCurrentWeek()[6]);
    }

    private List<TTalon> createDayTalonList(final List<TTalon> weekTalonList, final Date curDate) {
        List<TTalon> dayTalonList = new ArrayList<TTalon>();
        for (TTalon curTalon: weekTalonList) {
            Date dateApp = new Date(curTalon.getDatap());
            if (dateApp.equals(curDate)) {
                dayTalonList.add(curTalon);
                //weekTalonList.remove(curTalon);
            }
        }

        return dayTalonList;
    }

    private Time safeGetTimeOfAppointment(final List<TTalon> dayTalons, final int index) {
        if (index < dayTalons.size()) {
            return new Time(dayTalons.get(index).getTimep());
        } else {
            return null;
        }
    }

    public final Time getTimeOfAppointmentByDay(final int index, final int dayOfWeek) {
        switch (dayOfWeek) {
            case 0:
                return safeGetTimeOfAppointment(mondayTalonList, index);
            case 1:
                return safeGetTimeOfAppointment(tuesdayTalonList, index);
            case 2:
                return safeGetTimeOfAppointment(wednesdayTalonList, index);
            case 3:
                return safeGetTimeOfAppointment(thursdayTalonList, index);
            case 4:
                return safeGetTimeOfAppointment(fridayTalonList, index);
            case 5:
                return safeGetTimeOfAppointment(saturdayTalonList, index);
            case 6:
                return safeGetTimeOfAppointment(sundayTalonList, index);
            default:
                return null;
        }
    }

    private int safeGetVidp(final List<TTalon> dayTalons, final int index) {
        if (index < dayTalons.size()) {
            return dayTalons.get(index).getVidp();
        } else {
            return 0;
        }
    }

    public final int getVidpByDay(final int index, final int dayOfWeek) {
        switch (dayOfWeek) {
            case 0:
                return safeGetVidp(mondayTalonList, index);
            case 1:
                return safeGetVidp(tuesdayTalonList, index);
            case 2:
                return safeGetVidp(wednesdayTalonList, index);
            case 3:
                return safeGetVidp(thursdayTalonList, index);
            case 4:
                return safeGetVidp(fridayTalonList, index);
            case 5:
                return safeGetVidp(saturdayTalonList, index);
            case 6:
                return safeGetVidp(sundayTalonList, index);
            default:
                return 0;
        }
    }

    private TTalon safeGetTalon(final List<TTalon> dayTalons, final int index) {
        if (index < dayTalons.size()) {
            return dayTalons.get(index);
        } else {
            return null;
        }
    }

    public final TTalon getTalonByDay(final int index, final int dayOfWeek) {
        switch (dayOfWeek) {
            case 0:
                return safeGetTalon(mondayTalonList, index);
            case 1:
                return safeGetTalon(tuesdayTalonList, index);
            case 2:
                return safeGetTalon(wednesdayTalonList, index);
            case 3:
                return safeGetTalon(thursdayTalonList, index);
            case 4:
                return safeGetTalon(fridayTalonList, index);
            case 5:
                return safeGetTalon(saturdayTalonList, index);
            case 6:
                return safeGetTalon(sundayTalonList, index);
            default:
                return null;
        }
    }

}

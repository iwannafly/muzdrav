package ru.nkz.ivcgzo.clientInfomat.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс для управления настройками времени (дней недели) при генерации таблиц талонов
 * @author Avdeev Alexander
 */
public class DateChecker {
    /**
     * Календарь для формирования таблиц талонов
     */
    private GregorianCalendar calend;
    /**
     * Массив дней недели, отображаемой в таблице талонов
     */
    private Date[] curWeek = new Date[7];

    /**
     * Конструктор по умолчанию - устанавливает начальные установки в календаре
     * и формирует массив дат текущей недели.
     */
    public DateChecker() {
        setCalendarDefaults();
        fillCurrentWeekArray();
    }

    /**
     * Получение массива дней текущей недели.
     * @return массив дат текущей недели
     */
    public final Date[] getCurrentWeek() {
        return curWeek;
    }

    /**
     * Установка настроек календаря по умолчанию.
     */
    private void setCalendarDefaults() {
        calend = new GregorianCalendar();
        // установка дня начала недели на понедельник (по умолчанию - воскресенье)
        calend.setFirstDayOfWeek(Calendar.MONDAY);
        calend.set(Calendar.HOUR_OF_DAY, 0);
        calend.set(Calendar.MINUTE, 0);
        calend.set(Calendar.SECOND, 0);
        calend.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Заполнение массива дней текущей недели
     */
    private void fillCurrentWeekArray() {
        // устанавливаем календарь на первый день текущей недели
        calend.set(Calendar.DAY_OF_WEEK, calend.getFirstDayOfWeek());
        for (int i = 0; i < calend.getMaximum(Calendar.DAY_OF_WEEK); i++) {
            curWeek[i] = calend.getTime();
            // перемещаем календарь на день вперед
            calend.set(Calendar.DATE, calend.get(Calendar.DATE) + 1);
        }
        /*
         * возвращаем календарь на первый день текущей недели (т.к. в предшествующем цикле)
         * он переместился на на неделю вперед (т.е. на понедельник следующей недели)
         */
        calend.set(Calendar.DATE,
            calend.get(Calendar.DATE) - calend.getMaximum(Calendar.DAY_OF_WEEK));
    }

    /**
     * Сдвиг календаря на неделю вперед
     */
    public final void nextWeek() {
        calend.set(Calendar.WEEK_OF_YEAR, calend.get(Calendar.WEEK_OF_YEAR) + 1);
        fillCurrentWeekArray();
    }

    /**
     * Сдвиг календаря на неделю назад
     */
    public final void prevWeek() {
        calend.set(Calendar.WEEK_OF_YEAR, calend.get(Calendar.WEEK_OF_YEAR) - 1);
        fillCurrentWeekArray();
    }

}

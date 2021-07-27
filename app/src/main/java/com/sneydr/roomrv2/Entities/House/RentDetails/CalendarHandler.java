package com.sneydr.roomrv2.Entities.House.RentDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarHandler {

    private Calendar calendar;

    public CalendarHandler() {
        calendar = Calendar.getInstance();
    }


    public String getNow() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        return dateFormatter.format(calendar.getTime());
    }

    private int getFirstDayOfMonth() {
        return calendar.getActualMinimum(Calendar.DATE);
    }

    private int getNextMonth() {
        int month = calendar.get(Calendar.MONTH) + 2;
        if (month >= 12){
            return 1;
        }
        return month;
    }

    private int getSecondDayOfMonth() {
        return calendar.getActualMinimum(Calendar.DATE) + 1;
    }

    private int getLastDayOfMonth() {
        return calendar.getActualMaximum(Calendar.DATE);
    }

    private int getCurrentYear() {
        return calendar.get(Calendar.YEAR);
    }

    private int getCurrentMonth() {
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 12){
            return 1;
        }
        return month;
    }

    private String getDueDateWeekday(String string) {
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateParser.parse(string);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE MMMM dd yyyy");
            return dateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFormattedDueDate(String rentDueDate) {
        StringBuilder builder = new StringBuilder();
        if (rentDueDate.equals("First")) {
            builder.append(getFirstDayOfMonth());
            builder.append("/");
            builder.append(getNextMonth());
            builder.append("/");
        }
        else if (rentDueDate.equals("Second")) {
            builder.append(getSecondDayOfMonth());
            builder.append("/");
            builder.append(getNextMonth());
            builder.append("/");
        }
        else if (rentDueDate.equals("Last")) {
            builder.append(getLastDayOfMonth());
            builder.append("/");
            builder.append(getCurrentMonth());
            builder.append("/");
        }
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            builder.append(getCurrentYear() + 1);
            return getDueDateWeekday(builder.toString());
        }
        builder.append(getCurrentYear());
        return getDueDateWeekday(builder.toString());

    }


    public int getDayInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDaysUntilDueDate(String rentDueDate) {
        switch (rentDueDate) {
            case "First":
                return getTotalDaysInMonth() + 1;
            case "Second":
                return getTotalDaysInMonth() + 2;
            case "Last":
                return getTotalDaysInMonth();
            default:
                return 0;
        }
    }

    public int getTotalDaysInMonth() {
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.MONTH)){
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                if (isLeapYear()) return 29;
                return 28;
            default:
                return 31;
        }
    }

    public boolean isLeapYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year % 4 == 0;
    }

}

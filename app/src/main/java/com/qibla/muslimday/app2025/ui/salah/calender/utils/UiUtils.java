package com.qibla.muslimday.app2025.ui.salah.calender.utils;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.FormatStyle;


public class UiUtils {

    public static final String GREGORIAN_MONTH_YEAR_FORMAT = "MMMM yyyy";

    public static String formatReadableGregorianDate(LocalDate localDate, FormatStyle dateStyle) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String month = localDate.getMonth().toString().substring(0, 1).toUpperCase() +
                    localDate.getMonth().toString().substring(1).toLowerCase();
            return month + " " + localDate.getDayOfMonth() + " " + localDate.getYear();
        }

        return null;
    }


    public static String formatHijriDate(int day, String monthName, int year) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        String date = monthName + " " + numberFormat.format(day) + " " + numberFormat.format(year);
        return date.replaceAll("[٬،.,]", "");
    }


}

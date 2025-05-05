package com.qibla.muslimday.app2025.ui.salah.calender.utils;

import android.os.Build;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class TimingUtils {

    public final static int DOHA_INTERVAL = 15;
    public static final String ADHAN_API_DEFAULT_FORMAT = "dd-MM-yyyy";
    public static final String LUT_API_DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String TIMING_DEFAULT_FORMAT = "HH:mm";
    public static final String DEFAULT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    public static String formatDateForAdhanAPI(LocalDate localDate) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern(ADHAN_API_DEFAULT_FORMAT, Locale.getDefault());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate.format(formatter);
        }
        return null;
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert, ZoneId zone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Instant.ofEpochMilli(dateToConvert.getTime())
                    .atZone(zone)
                    .toLocalDateTime();
        }
        return null;
    }

    public static long getTimestampsFromLocalDateTime(LocalDateTime localDateTime, ZoneId zone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDateTime.atZone(zone).toEpochSecond();
        }
        return 0;
    }

    public static long getTimestampsFromLocalDate(LocalDate localDate, ZoneId zone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getTimestampsFromLocalDateTime(LocalDateTime.of(localDate, LocalTime.of(0, 0)), zone);
        }
        return 0;
    }

    public static String getDefaultTimeZone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ZoneId.systemDefault().getId();
        }
        return null;
    }

    public static LocalDateTime getLastThirdOfTheNight(LocalDateTime fajrTime, LocalDateTime maghribTime) {
        long nightDurationInSeconds = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nightDurationInSeconds = maghribTime.until(fajrTime.plus(1, ChronoUnit.DAYS), ChronoUnit.SECONDS);
        }

        LocalDateTime lastThirdOfTheNight = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lastThirdOfTheNight = maghribTime.plus((long) (nightDurationInSeconds * (2.0 / 3.0)), ChronoUnit.SECONDS).withSecond(0).withNano(0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (lastThirdOfTheNight.toLocalDate().isAfter(maghribTime.toLocalDate())) {
                return lastThirdOfTheNight.minus(1, ChronoUnit.DAYS);
            }
        }

        return lastThirdOfTheNight;
    }
}
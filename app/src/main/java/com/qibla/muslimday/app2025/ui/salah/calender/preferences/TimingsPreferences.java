package com.qibla.muslimday.app2025.ui.salah.calender.preferences;


import com.qibla.muslimday.app2025.ui.salah.calender.calculations.CalculationMethodEnum;
import com.qibla.muslimday.app2025.ui.salah.calender.calculations.LatitudeAdjustmentMethod;
import com.qibla.muslimday.app2025.ui.salah.calender.calculations.MidnightModeAdjustmentMethod;
import com.qibla.muslimday.app2025.ui.salah.calender.calculations.SchoolAdjustmentMethod;

public class TimingsPreferences {

    private CalculationMethodEnum method;
    private String tune;
    private LatitudeAdjustmentMethod latitudeAdjustmentMethod;
    private SchoolAdjustmentMethod schoolAdjustmentMethod;
    private MidnightModeAdjustmentMethod midnightModeAdjustmentMethod;
    private int hijriAdjustment;

    public TimingsPreferences(CalculationMethodEnum method,
                              String tune,
                              LatitudeAdjustmentMethod latitudeAdjustmentMethod,
                              SchoolAdjustmentMethod schoolAdjustmentMethod,
                              MidnightModeAdjustmentMethod midnightModeAdjustmentMethod,
                              int hijriAdjustment) {
        this.method = method;
        this.tune = tune;
        this.latitudeAdjustmentMethod = latitudeAdjustmentMethod;
        this.schoolAdjustmentMethod = schoolAdjustmentMethod;
        this.midnightModeAdjustmentMethod = midnightModeAdjustmentMethod;
        this.hijriAdjustment = hijriAdjustment;
    }

    public CalculationMethodEnum getMethod() {
        return method;
    }

    public String getTune() {
        return tune;
    }

    public LatitudeAdjustmentMethod getLatitudeAdjustmentMethod() {
        return latitudeAdjustmentMethod;
    }

    public SchoolAdjustmentMethod getSchoolAdjustmentMethod() {
        return schoolAdjustmentMethod;
    }

    public MidnightModeAdjustmentMethod getMidnightModeAdjustmentMethod() {
        return midnightModeAdjustmentMethod;
    }

    public int getHijriAdjustment() {
        return hijriAdjustment;
    }
}

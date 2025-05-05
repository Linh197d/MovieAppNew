package com.qibla.muslimday.app2025.ui.salah.calender.calculations;

public enum SchoolAdjustmentMethod {

    SHAFII(0),
    HANAFI(1);

    private int value;

    SchoolAdjustmentMethod(int value) {
        this.value = value;
    }

    public static SchoolAdjustmentMethod getDefault() {
        return SHAFII;
    }

    public int getValue() {
        return value;
    }
}

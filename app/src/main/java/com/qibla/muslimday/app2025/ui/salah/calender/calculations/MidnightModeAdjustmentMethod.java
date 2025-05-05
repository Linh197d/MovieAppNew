package com.qibla.muslimday.app2025.ui.salah.calender.calculations;

public enum MidnightModeAdjustmentMethod {

    STANDARD(0),
    JAFARI(1);

    private int value;

    MidnightModeAdjustmentMethod(int value) {
        this.value = value;
    }

    public static MidnightModeAdjustmentMethod getDefault() {
        return STANDARD;
    }

    public int getValue() {
        return value;
    }
}

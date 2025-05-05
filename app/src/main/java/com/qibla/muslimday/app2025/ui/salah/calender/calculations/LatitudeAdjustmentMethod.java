package com.qibla.muslimday.app2025.ui.salah.calender.calculations;

public enum LatitudeAdjustmentMethod {

    MIDDLE_OF_THE_NIGHT(1),
    ONE_SEVENTH(2),
    ANGLE_BASED(3);

    private int value;

    LatitudeAdjustmentMethod(int value) {
        this.value = value;
    }

    public static LatitudeAdjustmentMethod getDefault() {
        return ANGLE_BASED;
    }

    public int getValue() {
        return value;
    }
}

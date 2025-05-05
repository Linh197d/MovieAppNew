package com.qibla.muslimday.app2025.customeview.seekbar;

public class SeekBarState {
    public String indicatorText;
    public boolean isMax;
    public boolean isMin;
    public float value;

    public String toString() {
        return "indicatorText: " + this.indicatorText + " ,isMin: " + this.isMin + " ,isMax: " + this.isMax;
    }
}

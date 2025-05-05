package com.qibla.muslimday.app2025.customeview.seekbar;

public interface OnRangeChangedListener {
    void onRangeChanged(RangeSeekBar rangeSeekBar, float f, float f2, boolean z);

    void onStartTrackingTouch(RangeSeekBar rangeSeekBar, boolean z);

    void onStopTrackingTouch(RangeSeekBar rangeSeekBar, boolean z);
}

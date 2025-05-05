package com.qibla.muslimday.app2025.customeview.seekbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class SavedState extends View.BaseSavedState {
    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
    public float currSelectedMax;
    public float currSelectedMin;
    public float maxValue;
    public float minValue;
    public float rangeInterval;
    public int tickNumber;

    public SavedState(Parcelable superState) {
        super(superState);
    }

    private SavedState(Parcel in) {
        super(in);
        this.minValue = in.readFloat();
        this.maxValue = in.readFloat();
        this.rangeInterval = in.readFloat();
        this.tickNumber = in.readInt();
        this.currSelectedMin = in.readFloat();
        this.currSelectedMax = in.readFloat();
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeFloat(this.minValue);
        out.writeFloat(this.maxValue);
        out.writeFloat(this.rangeInterval);
        out.writeInt(this.tickNumber);
        out.writeFloat(this.currSelectedMin);
        out.writeFloat(this.currSelectedMax);
    }
}

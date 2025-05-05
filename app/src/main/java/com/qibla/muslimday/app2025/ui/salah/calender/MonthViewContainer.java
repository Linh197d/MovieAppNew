package com.qibla.muslimday.app2025.ui.salah.calender;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.kizitonwose.calendarview.ui.ViewContainer;
import com.qibla.muslimday.app2025.R;

public class MonthViewContainer extends ViewContainer {

    private final LinearLayout legendLayout;

    public MonthViewContainer(@NonNull View view) {
        super(view);
        legendLayout = view.findViewById(R.id.legendLayout);
    }

    public LinearLayout getLegendLayout() {
        return legendLayout;
    }
}

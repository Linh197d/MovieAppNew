package com.qibla.muslimday.app2025.ui.salah.calender;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.ViewContainer;
import com.qibla.muslimday.app2025.R;
import com.qibla.muslimday.app2025.ui.salah.SalahFragment;

import org.greenrobot.eventbus.EventBus;


public class DayViewContainer extends ViewContainer {

    private final View calendarDayLayout;
    private TextView hijriDayTextView;
    private TextView dayTextView;
    private CalendarDay calendarDay;
    private LinearLayout llBackgroundRoot;
    private ImageView ivDot;

    public DayViewContainer(@NonNull View view) {
        super(view);

        calendarDayLayout = view.findViewById(R.id.calendarDayLayout);
        dayTextView = view.findViewById(R.id.calendarDayText);
        hijriDayTextView = view.findViewById(R.id.hijri_calendar_date_text_view);
        llBackgroundRoot = view.findViewById(R.id.llCalendarBackground);
        ivDot = view.findViewById(R.id.ivCalendarDot);
        view.setOnClickListener(v -> {
            if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                EventBus.getDefault().post(new SalahFragment.MessageEvent(calendarDay.getDate()));
            }
        });
    }

    public TextView getDayTextView() {
        return dayTextView;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public TextView getHijriDayTextView() {
        return hijriDayTextView;
    }

    public View getCalendarDayLayout() {
        return calendarDayLayout;
    }

    public ImageView getIvDot() {
        return ivDot;
    }

    public LinearLayout getLlBackgroundRoot() {
        return llBackgroundRoot;
    }
}

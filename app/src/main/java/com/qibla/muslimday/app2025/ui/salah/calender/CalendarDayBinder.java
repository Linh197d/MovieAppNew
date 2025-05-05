package com.qibla.muslimday.app2025.ui.salah.calender;

import static com.qibla.muslimday.app2025.util.Global.intDay;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.qibla.muslimday.app2025.R;
import com.qibla.muslimday.app2025.ui.salah.SalahFragment;
import com.qibla.muslimday.app2025.ui.salah.calender.common.HijriHoliday;
import com.qibla.muslimday.app2025.ui.salah.calender.model.AladhanDate;
import com.qibla.muslimday.app2025.ui.salah.calender.model.AladhanDateType;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class CalendarDayBinder implements DayBinder<DayViewContainer> {

    @Override
    public void bind(@NonNull DayViewContainer dayViewContainer, @NonNull CalendarDay calendarDay) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

        dayViewContainer.setCalendarDay(calendarDay);
        TextView dayTextView = dayViewContainer.getDayTextView();
        TextView hijriDayTextView = dayViewContainer.getHijriDayTextView();
        View calendarDayLayout = dayViewContainer.getCalendarDayLayout();
        LinearLayout llBackGround = dayViewContainer.getLlBackgroundRoot();
        ImageView ivDot = dayViewContainer.getIvDot();

        LocalDate selectedDate = SalahFragment.getSelectedDate();
        List<AladhanDate> aladhanDates = SalahFragment.getHijriDates();
        llBackGround.setBackground(null);
//        ivDot.setVisibility(View.INVISIBLE);
        LocalDate today = null;
        today = LocalDate.now().plusDays(intDay);
        if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
            hijriDayTextView.setVisibility(View.VISIBLE);
            System.out.println("day = " + calendarDay.getDate().getDayOfMonth() + " month = " + calendarDay.getDate().getMonthValue());
            if (HijriHoliday.checkDay(calendarDay.getDate())) {
                ivDot.setVisibility(View.VISIBLE);
            } else {
                ivDot.setVisibility(View.INVISIBLE);
            }
            ivDot.setImageResource(R.drawable.ic_dot_yellow);
            if (calendarDay.getDate().equals(today)) {
                ivDot.setImageResource(R.drawable.ic_dot_white);
                updateHijriDate(calendarDay, hijriDayTextView, aladhanDates);
                llBackGround.setBackgroundResource(R.drawable.shape_bg_color_eaae15_corner_16);
                dayTextView.setVisibility(View.VISIBLE);

            } else if (calendarDay.getDate().equals(selectedDate)) {
                updateHijriDate(calendarDay, hijriDayTextView, aladhanDates);
                llBackGround.setBackgroundResource(R.drawable.shape_stroke_color_white_corner_16);
                dayTextView.setVisibility(View.VISIBLE);
            } else {
                updateHijriDate(calendarDay, hijriDayTextView, aladhanDates);
                llBackGround.setBackground(null);
                dayTextView.setVisibility(View.VISIBLE);
            }
        } else {
            ivDot.setVisibility(View.INVISIBLE);
            dayTextView.setVisibility(View.INVISIBLE);
            hijriDayTextView.setVisibility(View.INVISIBLE);
            llBackGround.setBackground(null);
            calendarDayLayout.setBackground(null);
        }
        dayTextView.setText(numberFormat.format(calendarDay.getDay()));
    }

    private void updateHijriDate(@NonNull CalendarDay calendarDay, TextView hijriDayTextView, List<AladhanDate> aladhanDates) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

        if (aladhanDates != null) {
            String gregorianMonth = aladhanDates.get(0).getGregorian().getMonth().getNumber();

            if (Integer.valueOf(gregorianMonth).equals(calendarDay.getDate().getMonthValue())) {
                AladhanDateType hijriDate = aladhanDates.get(calendarDay.getDay() - 1).getHijri();
                String hijriDayNumber = hijriDate.getDay();
                hijriDayTextView.setText(numberFormat.format(Integer.parseInt(hijriDayNumber)));
                hijriDayTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public DayViewContainer create(@NonNull View view) {
        return new DayViewContainer(view);
    }
}

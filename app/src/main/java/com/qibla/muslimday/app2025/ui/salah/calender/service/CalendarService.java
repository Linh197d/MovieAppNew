package com.qibla.muslimday.app2025.ui.salah.calender.service;

import android.util.Log;

import com.qibla.muslimday.app2025.ui.salah.calender.model.AladhanDate;
import com.qibla.muslimday.app2025.ui.salah.calender.preferences.PreferencesHelper;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

@Singleton
public class CalendarService {

    private final CalendarAPIService calendarAPIService;
    private final OfflineTimingsService offlineTimingsService;
    private final PreferencesHelper preferencesHelper;

    private Single<List<AladhanDate>> single;
    private Disposable disposable; // Biến toàn cục để quản lý Single


    @Inject
    public CalendarService(CalendarAPIService calendarAPIService,
                           OfflineTimingsService offlineTimingsService,
                           PreferencesHelper preferencesHelper) {
        this.calendarAPIService = calendarAPIService;
        this.offlineTimingsService = offlineTimingsService;
        this.preferencesHelper = preferencesHelper;
    }

    public Single<List<AladhanDate>> getHijriCalendar(final int month, final int year) {
        int hijriAdjustment = preferencesHelper.getHijriAdjustment();
        single = null;
        single = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println("tunglvvvvvvv call calendar");
                    CalendarAPIResponse hijriCalendar = calendarAPIService.getHijriCalendar(month, year, hijriAdjustment);
                    Log.d("aaaaa", "nhay vao 1");

                    if (hijriCalendar != null) {
                        emitter.onSuccess(hijriCalendar.getData());
                    } else {
                        Log.d(CalendarService.class.getName(), "Offline calendar");
                        Log.d("aaaaa", "nhay vao 2");
                        emitter.onSuccess(offlineTimingsService.getHijriCalendar(month, year, hijriAdjustment));
                    }

                } catch (IOException e) {
                    System.out.println("tunglvvvvvvv call calendar error = " + e);
                    Log.d(CalendarService.class.getName(), "Offline calendar");
                    Log.d("aaaaa", "nhay vao 3");
                    emitter.onSuccess(offlineTimingsService.getHijriCalendar(month, year, hijriAdjustment));
                }
            });
            thread.start();
        });
        return single;
    }

    public void clearSingle() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

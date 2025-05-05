package com.qibla.muslimday.app2025.ui.salah.calender.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private final Context context;

    @Inject
    public PreferencesHelper(Context context) {
        this.context = context;
    }

    public Map<String, Integer> getTuneMap() {
        HashMap<String, Integer> map = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferencesConstants.TIMING_ADJUSTMENT, Context.MODE_PRIVATE);

        int fajrTimingAdjustment = sharedPreferences.getInt(PreferencesConstants.FAJR_TIMING_ADJUSTMENT, 0);
        int dohrTimingAdjustment = sharedPreferences.getInt(PreferencesConstants.DOHR_TIMING_ADJUSTMENT, 0);
        int asrTimingAdjustment = sharedPreferences.getInt(PreferencesConstants.ASR_TIMING_ADJUSTMENT, 0);
        int maghrebTimingAdjustment = sharedPreferences.getInt(PreferencesConstants.MAGHREB_TIMING_ADJUSTMENT, 0);
        int ichaTimingAdjustment = sharedPreferences.getInt(PreferencesConstants.ICHA_TIMING_ADJUSTMENT, 0);

        map.put(PreferencesConstants.FAJR_TIMING_ADJUSTMENT, fajrTimingAdjustment);
        map.put(PreferencesConstants.DOHR_TIMING_ADJUSTMENT, dohrTimingAdjustment);
        map.put(PreferencesConstants.ASR_TIMING_ADJUSTMENT, asrTimingAdjustment);
        map.put(PreferencesConstants.MAGHREB_TIMING_ADJUSTMENT, maghrebTimingAdjustment);
        map.put(PreferencesConstants.ICHA_TIMING_ADJUSTMENT, ichaTimingAdjustment);

        return map;
    }

    public int getHijriAdjustment() {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return defaultSharedPreferences.getInt(PreferencesConstants.HIJRI_DAY_ADJUSTMENT_PREFERENCE, 0);
    }
}

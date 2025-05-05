package com.qibla.muslimday.app2025.ui.salah.calender.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CalendarAPIResource {

    @GET("gToHCalendar/{month}/{year}")
    Call<CalendarAPIResponse> getHijriCalendar(
            @Path("month") int month,
            @Path("year") int year,
            @Query("adjustment") int adjustment
    );
}

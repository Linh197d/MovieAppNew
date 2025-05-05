package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.network.model.PrayTimeModel
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Named

interface PrayTimeService {
    @GET("prayer_times")
    @Named("retrofit_time_pray")
    suspend fun getPrayTime(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timezone: String,
        @Query("juristic") juristic: Int,
        @Query("method") method: Int,
        @Query("time_format") timeFormat: Int,
        @Query("date") date: String
    ): PrayTimeModel
}
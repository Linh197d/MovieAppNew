package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Named

interface PrayTimeNewService {
    @GET("{date}")
    @Named("retrofit_time_pray_new")
    suspend fun getPrayTime(
        @Path("date") date: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
        @Query("school") school: Int
    ): PrayerTimeBackup
}
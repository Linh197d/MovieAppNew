package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.network.model_mosque.NominatimResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NominatimService {
    @Headers("User-Agent: App450Muslim/1.0 (linhdq@vtn-global.com)")
    @GET("reverse")
    suspend fun getAddress(
        @Query("format") format: String = "json",
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): NominatimResponse
}
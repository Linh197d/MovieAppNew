package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.network.model_mosque.WikiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WikimediaApiService {
    @Headers("User-Agent: App450Muslim/1.0 (linhdq@vtn-global.com)")
    @GET("w/api.php")
    suspend fun getMosqueImage(
        @Query("action") action: String = "wbgetclaims",
        @Query("format") format: String = "json",
        @Query("entity") entityId: String,
        @Query("property") property: String = "P18"
    ): WikiResponse
}
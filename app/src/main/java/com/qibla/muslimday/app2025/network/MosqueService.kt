package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.network.model_mosque.OverpassResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MosqueService {
    @Headers("User-Agent: App450Muslim/1.0 (linhdq@vtn-global.com)")
    @GET("api/interpreter")
    suspend fun getMosques(
        @Query("data") query: String
    ): OverpassResponse
}
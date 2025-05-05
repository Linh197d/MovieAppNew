package com.qibla.muslimday.app2025.network

import com.qibla.muslimday.app2025.ui.quran.modelDetail.QuranDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Named

interface QuranService {

    @Named("retrofit_quran")
    @GET("verses/by_juz/{id}")
    suspend fun getJuzQuranDetailById(
        @Path("id") id: Int,
        @Query("language") language: String,
        @Query("words") words: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("audio") audio: Int,
        @Query("fields") fields: String,
    ): QuranDetail

    @Named("retrofit_quran")
    @GET("verses/by_chapter/{id}")
    suspend fun getSurahQuranDetailById(
        @Path("id") id: Int,
        @Query("language") language: String,
        @Query("words") words: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("audio") audio: Int,
        @Query("fields") fields: String,
    ): QuranDetail
}
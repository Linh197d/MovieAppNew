package com.qibla.muslimday.app2025.ui.quran.modelDetail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuranDetail(
    @Json(name = "pagination")
    val pagination: Pagination,
    @Json(name = "verses")
    val vers: List<Verse>,
)
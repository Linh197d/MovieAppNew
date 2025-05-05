package com.qibla.muslimday.app2025.ui.quran.modelDetail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "current_page")
    val current_page: Int,
    @Json(name = "next_page")
    val next_page: Int?,
    @Json(name = "per_page")
    val per_page: Int,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "total_records")
    val total_records: Int
)
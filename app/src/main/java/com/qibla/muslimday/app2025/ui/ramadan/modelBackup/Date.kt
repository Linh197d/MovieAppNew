package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Date(
    @Json(name = "gregorian")
    val gregorian: Gregorian,
    @Json(name = "hijri")
    val hijri: Hijri,
    @Json(name = "readable")
    val readable: String,
    @Json(name = "timestamp")
    val timestamp: String
)
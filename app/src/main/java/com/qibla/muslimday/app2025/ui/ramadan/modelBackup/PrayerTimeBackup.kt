package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrayerTimeBackup(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val `data`: Data?,
    @Json(name = "status") val status: String
)
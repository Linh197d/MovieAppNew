package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Params(
    @Json(name = "Fajr") val Fajr: Int,
    @Json(name = "Isha") val Isha: Int
)
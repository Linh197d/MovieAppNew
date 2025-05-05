package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Offset(
    @Json(name = "Asr") val Asr: Int,
    @Json(name = "Dhuhr") val Dhuhr: Int,
    @Json(name = "Fajr") val Fajr: Int,
    @Json(name = "Imsak") val Imsak: Int,
    @Json(name = "Isha") val Isha: Int,
    @Json(name = "Maghrib") val Maghrib: Int,
    @Json(name = "Midnight") val Midnight: Int,
    @Json(name = "Sunrise") val Sunrise: Int,
    @Json(name = "Sunset") val Sunset: Int
)
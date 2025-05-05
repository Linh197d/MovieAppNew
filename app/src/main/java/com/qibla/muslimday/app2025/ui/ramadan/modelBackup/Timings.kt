package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Timings(
    @Json(name = "Asr") var Asr: String,
    @Json(name = "Dhuhr") var Dhuhr: String,
    @Json(name = "Fajr") var Fajr: String,
    @Json(name = "Firstthird") val Firstthird: String,
    @Json(name = "Imsak") val Imsak: String,
    @Json(name = "Isha") var Isha: String,
    @Json(name = "Lastthird") val Lastthird: String,
    @Json(name = "Maghrib") var Maghrib: String,
    @Json(name = "Midnight") val Midnight: String,
    @Json(name = "Sunrise") var Sunrise: String,
    @Json(name = "Sunset") val Sunset: String
)
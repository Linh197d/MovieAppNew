package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gregorian(
    @Json(name = "date") val date: String,
    @Json(name = "day") val day: String,
    @Json(name = "designation") val designation: Designation,
    @Json(name = "format") val format: String,
    @Json(name = "month") val month: Month,
    @Json(name = "weekday") val weekday: Weekday,
    @Json(name = "year") val year: String
)
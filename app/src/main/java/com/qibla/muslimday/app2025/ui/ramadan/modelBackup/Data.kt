package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "date") val date: Date,
    @Json(name = "meta") val meta: Meta,
    @Json(name = "timings") val timings: Timings
)
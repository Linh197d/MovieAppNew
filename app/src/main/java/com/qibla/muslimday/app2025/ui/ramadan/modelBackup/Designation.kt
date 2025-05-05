package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Designation(
    @Json(name = "abbreviated") val abbreviated: String,
    @Json(name = "expanded") val expanded: String
)
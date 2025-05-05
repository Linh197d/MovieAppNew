package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Month(
    @Json(name = "en") val en: String,
    @Json(name = "number") val number: Int
)
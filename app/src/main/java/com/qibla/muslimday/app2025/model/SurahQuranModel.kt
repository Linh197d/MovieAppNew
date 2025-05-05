package com.qibla.muslimday.app2025.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurahQuranEntity(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val data: List<Data>,
    @Json(name = "status") val status: String
)
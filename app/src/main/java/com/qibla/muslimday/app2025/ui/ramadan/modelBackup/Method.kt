package com.qibla.muslimday.app2025.ui.ramadan.modelBackup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Method(
    @Json(name = "id") val id: Int,
    @Json(name = "location") val location: Location,
    @Json(name = "name") val name: String,
    @Json(name = "params") val params: Params
)
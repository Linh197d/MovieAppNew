package com.qibla.muslimday.app2025.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PrayTimeModel(
    @Json(name = "results") val results: Results?,
    @Json(name = "settings") val settings: Settings?,
    @Json(name = "success") val success: Boolean,
) : Serializable

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "Asr") var Asr: String,
    @Json(name = "Dhuhr") var Dhuhr: String,
    @Json(name = "Duha") var Duha: String,
    @Json(name = "Fajr") var Fajr: String,
    @Json(name = "Isha") var Isha: String,
    @Json(name = "Maghrib") var Maghrib: String,
)

@JsonClass(generateAdapter = true)
data class Settings(
    @Json(name = "fajir_rule") val fajir_rule: FajirRule,
    @Json(name = "high_latitude") val high_latitude: Double,
    @Json(name = "isha_rule") val isha_rule: IshaRule,
    @Json(name = "juristic") val juristic: Double,
    @Json(name = "latitude") val latitude: String,
    @Json(name = "location") val location: Location,
    @Json(name = "longitude") val longitude: String,
    @Json(name = "maghrib_rule") val maghrib_rule: MaghribRule,
    @Json(name = "method") val method: Double,
    @Json(name = "name") val name: String,
    @Json(name = "time_format") val time_format: Double,
    @Json(name = "timezone") val timezone: String,
)

@JsonClass(generateAdapter = true)
data class FajirRule(
    @Json(name = "type") val type: Double,
    @Json(name = "value") val value: Double,
)

@JsonClass(generateAdapter = true)
data class IshaRule(
    @Json(name = "type") val type: Double,
    @Json(name = "value") val value: Double,
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "city") val city: String,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
)

@JsonClass(generateAdapter = true)
data class MaghribRule(
    @Json(name = "type") val type: Double,
    @Json(name = "value") val value: Double,
)
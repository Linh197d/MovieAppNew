package com.qibla.muslimday.app2025.network.model_mosque

import com.squareup.moshi.Json

data class OverpassResponse(
    val elements: List<OverpassElement>
)

data class OverpassElement(
    val id: Long,
    val lat: Double,
    val lon: Double,
    val tags: MosqueTags?
)

data class MosqueTags(
    val name: String?,
    @Json(name = "name:en") val nameEn: String?,
    val religion: String?,
    @Json(name = "wikidata") val wikidata: String?,
    @Json(name = "wikipedia") val wikipedia: String?
)
package com.qibla.muslimday.app2025.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "englishName") val englishName: String = "",
    @Json(name = "englishNameTranslation") val englishNameTranslation: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "number") val number: Int = 0,
    @Json(name = "numberOfAyahs") val numberOfAyahs: Int = 0,
    @Json(name = "revelationType") val revelationType: String = "",
    var isBookmark: Boolean = false,
) : Serializable
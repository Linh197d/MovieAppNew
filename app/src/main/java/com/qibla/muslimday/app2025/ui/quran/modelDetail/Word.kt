package com.qibla.muslimday.app2025.ui.quran.modelDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "word")
data class Word(
    @Json(name = "audio_url")
    @Ignore
    var audio_url: String? = "",
    @Json(name = "char_type_name")
    @Ignore
    var char_type_name: String = "",
    @Json(name = "code_v1")
    @Ignore
    var code_v1: String = "",
    @Json(name = "id")
    @ColumnInfo(name = "_id")
    @PrimaryKey
    var id: Int = 0,
    @Json(name = "line_number")
    @Ignore
    var line_number: Int = 0,
    @Json(name = "page_number")
    @Ignore
    var page_number: Int = 0,
    @Json(name = "position")
    @ColumnInfo(name = "_position")
    var position: Int = 0,
    @Json(name = "text")
    @Ignore
    var text: String = "",
    @Json(name = "translation")
    @Ignore
    var translation: Translation? = null,
    @Json(name = "transliteration")
    @Ignore
    var transliteration: Transliteration? = null,
    @ColumnInfo(name = "_id_verse")
    var idVerse: Int = 0,
) : Serializable
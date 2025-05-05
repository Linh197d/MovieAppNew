package com.qibla.muslimday.app2025.ui.quran.modelDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "translation")
data class Translation(
    @Json(name = "language_name")
    @ColumnInfo(name = "_language_name")
    var language_name: String = "",
    @Json(name = "text")
    @ColumnInfo(name = "_text")
    var text: String = "",
    @ColumnInfo(name = "_id_words")
    @PrimaryKey
    var idWords: Int = 0,
    @ColumnInfo(name = "_id_verse")
    var idVerse: Int = 0
) : Serializable
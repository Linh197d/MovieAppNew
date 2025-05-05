package com.qibla.muslimday.app2025.ui.quran.modelDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "audio")
data class Audio(
    @Json(name = "segments")
    @Ignore
    var segments: List<List<Int>>? = null,
    @Json(name = "url")
    @ColumnInfo(name = "_url")
    var url: String = "",
    @ColumnInfo(name = "_verse_id")
    @PrimaryKey()
    var verseId: Int = 0,
) : Serializable
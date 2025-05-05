package com.qibla.muslimday.app2025.ui.quran.modelDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "verse")
data class Verse(
    @Json(name = "audio")
    @Ignore
    var audio: Audio? = null,
    @Json(name = "hizb_number")
    @Ignore
    var hizb_number: Int = 0,
    @Json(name = "id")
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Int = 0,
    @Json(name = "juz_number")
    @ColumnInfo(name = "_juz_number")
    var juz_number: Int = 0,
    @Json(name = "manzil_number")
    @Ignore
    var manzil_number: Int = 0,
    @Json(name = "page_number")
    @Ignore
    var page_number: Int = 0,
    @Json(name = "rub_el_hizb_number")
    @Ignore
    var rub_el_hizb_number: Int = 0,
    @Json(name = "ruku_number")
    @Ignore
    var ruku_number: Int = 0,
    @Json(name = "sajdah_number")
    @Ignore
    var sajdah_number: Any? = null,
    @Json(name = "text_uthmani")
    @ColumnInfo(name = "_text_uthmani")
    var text_uthmani: String = "",
    @Json(name = "verse_key")
    @Ignore
    var verse_key: String = "",
    @Json(name = "verse_number")
    @ColumnInfo(name = "_verse_number")
    var verse_number: Int = 0,
    @Json(name = "words")
    @Ignore
    var words: List<Word>? = null,
    @Ignore
    var isPlaySound: Boolean = false,
    @ColumnInfo(name = "_is_book_mark")
    var isBookmark: Boolean = false,
    @ColumnInfo(name = "_surah_number")
    var surahNumber: Int = 0,
    @Ignore
    var isShowTitle: Boolean = false,
) : Serializable
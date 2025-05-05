package com.qibla.muslimday.app2025.database.Quran

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Quran")
class QuranEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "num")
    var num: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "translation")
    var translation: String,
    @ColumnInfo(name = "isBookMark")
    var isBookMark: Boolean = false,
) : Serializable
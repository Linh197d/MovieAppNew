package com.qibla.muslimday.app2025.database.Duas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "DailyDuas")
class DuasEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "superId")
    var superId: Int,
    @ColumnInfo(name = "nameId")
    var nameId: Int,
    @ColumnInfo(name = "fontName")
    var fontName: String,
    @ColumnInfo(name = "textIndia")
    var textIndia: String,
    @ColumnInfo(name = "textEnglish")
    var textEnglish: Int,
    @ColumnInfo(name = "isCheck")
    var isCheck: Boolean = false
) : Serializable
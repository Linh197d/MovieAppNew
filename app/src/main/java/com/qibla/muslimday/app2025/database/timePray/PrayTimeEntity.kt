package com.qibla.muslimday.app2025.database.timePray

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PrayTime")
class PrayTimeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "time")
    var time: String
)
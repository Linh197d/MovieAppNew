package com.qibla.muslimday.app2025.database.timePray

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PrayTimeEntity::class], version = 1, exportSchema = false)
abstract class PrayTimeDatabase : RoomDatabase() {
    abstract val prayTimeDao: PrayTimeDao
}
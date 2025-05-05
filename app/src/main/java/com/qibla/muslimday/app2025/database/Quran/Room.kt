package com.qibla.muslimday.app2025.database.Quran

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuranEntity::class], version = 1, exportSchema = false)
abstract class QuranDatabase : RoomDatabase() {
    abstract val quranDao: QuranDao
}
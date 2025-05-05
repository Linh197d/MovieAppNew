package com.qibla.muslimday.app2025.database.Azkar

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AzkarEntity::class], version = 1, exportSchema = false)
abstract class AzkarDatabase : RoomDatabase() {
    abstract val azkarDao: AzkarDao
}
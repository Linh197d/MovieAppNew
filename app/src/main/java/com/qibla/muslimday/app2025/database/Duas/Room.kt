package com.qibla.muslimday.app2025.database.Duas

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DuasEntity::class], version = 1, exportSchema = false)
abstract class DuasDatabase : RoomDatabase() {
    abstract val duasDao: DuasDao
}
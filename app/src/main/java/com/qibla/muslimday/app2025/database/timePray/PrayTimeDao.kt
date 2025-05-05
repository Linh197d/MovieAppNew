package com.qibla.muslimday.app2025.database.timePray

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayTime(prayTimeEntity: PrayTimeEntity)

    @Query("SELECT * FROM PrayTime ")
    fun getPrayTime(): LiveData<List<PrayTimeEntity>>

    @Query("SELECT * FROM PrayTime ")
    fun getPrayTimeS(): List<PrayTimeEntity>

    @Query("UPDATE PrayTime SET time = :time WHERE id = :id")
    suspend fun updateIsCheck(id: Int, time: String)
}
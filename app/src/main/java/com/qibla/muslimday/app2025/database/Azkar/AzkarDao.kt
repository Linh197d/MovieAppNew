package com.qibla.muslimday.app2025.database.Azkar

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AzkarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAzkar(azkarEntity: AzkarEntity)

    @Query("SELECT * FROM Azkar WHERE nameId = :id")
    fun getListAzkarId(id: Int): List<AzkarEntity>

    @Query("SELECT * FROM Azkar WHERE nameId = :id")
    fun getAzkarId(id: Int): LiveData<List<AzkarEntity>>

    @Query("SELECT * FROM Azkar WHERE id = :id")
    fun getAzkarIdRandom(id: Int): LiveData<AzkarEntity>

    @Query("SELECT * FROM Azkar WHERE id = :id")
    fun getAzkarIdRandomS(id: Int): AzkarEntity

    @Query("SELECT * FROM Azkar WHERE id = :id")
    suspend fun getAzkarNameIdRandom(id: Int): AzkarEntity

    @Query("SELECT * FROM Azkar WHERE isCheck = 1")
    fun getItemChecked(): LiveData<List<AzkarEntity>>

    @Query("UPDATE Azkar SET isCheck = :isCheck WHERE id = :id")
    suspend fun updateIsCheck(id: Int, isCheck: Boolean)
}
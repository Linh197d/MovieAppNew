package com.qibla.muslimday.app2025.database.Quran

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuranDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuran(quranEntity: QuranEntity)

    @Query("SELECT * FROM Quran WHERE type = :type")
    fun getQuranType(type: String): List<QuranEntity>

    @Query("SELECT * FROM Quran WHERE type = :type AND num = :num")
    fun getNameByNum(type: String, num: Int): LiveData<QuranEntity>

    @Query("SELECT * FROM Quran WHERE isBookMark = 1 AND type = :type")
    fun getItemChecked(type: String): LiveData<List<QuranEntity>>

    @Query("UPDATE Quran SET isBookMark = :isCheck WHERE id = :id")
    suspend fun updateIsCheck(id: Int, isCheck: Boolean)

    @Query("DELETE FROM Quran")
    suspend fun deleteAllQuran()
}
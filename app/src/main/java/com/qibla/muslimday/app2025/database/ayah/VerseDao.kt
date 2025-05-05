package com.qibla.muslimday.app2025.database.ayah

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse

@Dao
interface VerseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerse(vers: Verse)

    @Query("SELECT * FROM verse WHERE _juz_number = :id")
    fun getVerseByIdJuz(id: Int): LiveData<List<Verse>>

    @Query("SELECT * FROM verse WHERE _surah_number = :id")
    fun getVerseByIdSurah(id: Int): LiveData<List<Verse>>

    @Query("SELECT * FROM verse WHERE _is_book_mark = 1")
    fun getAllItemVerseChecked(): LiveData<List<Verse>>

    @Query("UPDATE verse SET _is_book_mark = :isCheck WHERE _id = :id")
    suspend fun updateIsCheck(id: Int, isCheck: Boolean)
}
package com.qibla.muslimday.app2025.database.ayah

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Word

@Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(word: Word)

    @Query("SELECT * FROM word WHERE _id_verse= :idVerse")
    fun getWordsByIdVerse(idVerse: Int): List<Word>
}
package com.qibla.muslimday.app2025.database.ayah

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Transliteration

@Dao
interface TransliterationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransliteration(transliteration: Transliteration)

    @Query("SELECT * FROM transliteration WHERE _id_words = :idWords")
    fun getTransliterationByIdWords(idWords: Int): Transliteration
}
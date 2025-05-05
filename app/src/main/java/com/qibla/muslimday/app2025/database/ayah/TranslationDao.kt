package com.qibla.muslimday.app2025.database.ayah

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Translation

@Dao
interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: Translation)

    @Query("SELECT * FROM translation WHERE _id_words = :idWords")
    fun getTranslationByIdWords(idWords: Int): Translation

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTranslation(translation: Translation)
}
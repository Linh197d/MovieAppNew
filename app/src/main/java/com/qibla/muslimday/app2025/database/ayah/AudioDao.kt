package com.qibla.muslimday.app2025.database.ayah

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Audio

@Dao
interface AudioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudio(audio: Audio)

    @Query("SELECT * FROM audio WHERE _verse_id = :idVerse")
    fun getAudioByIdVerse(idVerse: Int): Audio

    @Query("UPDATE audio SET _url = :urlAudio WHERE _verse_id = :idVerse")
    suspend fun updateUrlAudioByIdVerse(idVerse: Int, urlAudio: String)
}
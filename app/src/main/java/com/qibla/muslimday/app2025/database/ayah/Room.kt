package com.qibla.muslimday.app2025.database.ayah

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Audio
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Translation
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Transliteration
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Word

@Database(
    entities = [Verse::class, Word::class, Audio::class, Translation::class, Transliteration::class],
    version = 1,
    exportSchema = false
)
abstract class VerseDatabase : RoomDatabase() {
    abstract val verseDao: VerseDao
    abstract val wordsDao: WordsDao
    abstract val audioDao: AudioDao
    abstract val translationDao: TranslationDao
    abstract val transliterationDao: TransliterationDao
}


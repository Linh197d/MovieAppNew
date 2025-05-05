package com.qibla.muslimday.app2025.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.qibla.muslimday.app2025.database.Quran.QuranDao
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.database.ayah.AudioDao
import com.qibla.muslimday.app2025.database.ayah.TranslationDao
import com.qibla.muslimday.app2025.database.ayah.TransliterationDao
import com.qibla.muslimday.app2025.database.ayah.VerseDao
import com.qibla.muslimday.app2025.database.ayah.WordsDao
import com.qibla.muslimday.app2025.network.QuranService
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Audio
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Pagination
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Translation
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Transliteration
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Word
import javax.inject.Inject

class QuranRepository @Inject constructor(
    private val quranService: QuranService,
    private val audioDao: AudioDao,
    private val translationDao: TranslationDao,
    private val transliterationDao: TransliterationDao,
    private val verseDao: VerseDao,
    private val worksDao: WordsDao,
    private val quranDao: QuranDao,
) {

    suspend fun insertVerse(verse: Verse) {
        verseDao.insertVerse(verse)
    }

    fun getVerseByIdJuz(id: Int): LiveData<List<Verse>> {
        return verseDao.getVerseByIdJuz(id)
    }

    fun getVerseByIdSurah(id: Int): LiveData<List<Verse>> {
        return verseDao.getVerseByIdSurah(id)
    }

    fun getItemVerseChecked(): LiveData<List<Verse>> {
        return verseDao.getAllItemVerseChecked()
    }

    suspend fun updateIsCheckVerse(id: Int, isCheck: Boolean) {
        verseDao.updateIsCheck(id, isCheck)
    }

    //

    suspend fun insertWord(word: Word) {
        worksDao.insertWords(word)
    }

    fun getWordByIdVerse(id: Int): List<Word> {
        return worksDao.getWordsByIdVerse(id)
    }

    suspend fun insertAudio(audio: Audio) {
        audioDao.insertAudio(audio)
    }

    fun getAudioByIdVerse(id: Int): Audio {
        return audioDao.getAudioByIdVerse(id)
    }

    suspend fun updateUrlAudioByIdVerse(idVerse: Int, urlAudio: String) {
        audioDao.updateUrlAudioByIdVerse(idVerse, urlAudio)
    }

    suspend fun insertTransliteration(transliteration: Transliteration) {
        transliterationDao.insertTransliteration(transliteration)
    }

    fun getTransliterationByIdWords(idWords: Int): Transliteration {
        return transliterationDao.getTransliterationByIdWords(idWords)
    }

    suspend fun insertTranslation(translation: Translation) {
        translationDao.insertTranslation(translation)
    }

    fun getTranslationByIdWords(idWords: Int): Translation {
        return translationDao.getTranslationByIdWords(idWords)
    }

    suspend fun updateTranslation(translation: Translation) {
        translationDao.updateTranslation(translation)
    }

    suspend fun getAllSurahById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ): List<Verse> {
        var listSurahById: List<Verse> = listOf()
        try {
            listSurahById = quranService.getSurahQuranDetailById(
                id, language, words, page, per_page, audio, fields
            ).vers

        } catch (e: Exception) {
            Log.d("ntt", "Exeption" + e)
        }
        return listSurahById
    }

    suspend fun insertQuran(quranEntity: QuranEntity) {
        quranDao.insertQuran(quranEntity)
    }

    suspend fun clearQuran() {
        quranDao.deleteAllQuran()
    }

    fun getQuranType(type: String): List<QuranEntity> {
        return quranDao.getQuranType(type)
    }

    fun getItemChecked(type: String): LiveData<List<QuranEntity>> {
        return quranDao.getItemChecked(type)
    }


    suspend fun updateIsCheck(id: Int, isCheck: Boolean) {
        quranDao.updateIsCheck(id, isCheck)
    }

    fun getNameByNum(type: String, num: Int): LiveData<QuranEntity> {
        return quranDao.getNameByNum(type, num)
    }


    suspend fun getAllJuzById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ): List<Verse> {
        var listSurahById: List<Verse> = listOf()
        try {
            listSurahById = quranService.getJuzQuranDetailById(
                id, language, words, page, per_page, audio, fields
            ).vers

        } catch (e: Exception) {
            Log.d("ntt", "Exeption" + e)
        }
        return listSurahById
    }

    suspend fun getPaginationJuzById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ): Pagination {
        var pagination: Pagination = Pagination(0, 0, 0, 0, 0)
        try {
            pagination = quranService.getJuzQuranDetailById(
                id, language, words, page, per_page, audio, fields
            ).pagination

        } catch (e: Exception) {
            Log.d("ntt", "Exeption" + e)
        }
        return pagination
    }

    suspend fun getPaginationSurahById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ): Pagination {
        var pagination: Pagination = Pagination(0, 0, 0, 0, 0)
        try {
            pagination = quranService.getSurahQuranDetailById(
                id, language, words, page, per_page, audio, fields
            ).pagination

        } catch (e: Exception) {
            Log.d("ntt", "Exeption" + e)
        }
        return pagination
    }
}
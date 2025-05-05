package com.qibla.muslimday.app2025.ui.quran.quranDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.repository.QuranRepository
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Audio
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Pagination
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Translation
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Transliteration
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranDetailViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
) : ViewModel() {

    val paginationSurahById: LiveData<Pagination>
        get() = _paginationSurahById
    private var _paginationSurahById = MutableLiveData<Pagination>()

    val paginationJuzById: LiveData<Pagination>
        get() = _paginationJuzById
    private var _paginationJuzById = MutableLiveData<Pagination>()

    fun getNameByNum(type: String, num: Int): LiveData<QuranEntity> {
        return quranRepository.getNameByNum(type, num)
    }


    suspend fun insertVerse(verse: Verse) {
        quranRepository.insertVerse(verse)
    }

    fun getVerseByIdJuz(id: Int): LiveData<List<Verse>> {
        return quranRepository.getVerseByIdJuz(id)
    }

    fun getVerseByIdSurah(id: Int): LiveData<List<Verse>> {
        return quranRepository.getVerseByIdSurah(id)
    }

    fun getItemVerseChecked(): LiveData<List<Verse>> {
        return quranRepository.getItemVerseChecked()
    }

    suspend fun updateIsCheckVerse(id: Int, isCheck: Boolean) {
        quranRepository.updateIsCheckVerse(id, isCheck)
    }

    suspend fun insertWord(word: Word) {
        quranRepository.insertWord(word)
    }

    fun getWordByIdVerse(id: Int): List<Word> {
        return quranRepository.getWordByIdVerse(id)
    }

    suspend fun insertAudio(audio: Audio) {
        quranRepository.insertAudio(audio)
    }

    fun getAudioByIdVerse(id: Int): Audio {
        return quranRepository.getAudioByIdVerse(id)
    }

    suspend fun updateUrlAudioByIdVerse(idVerse: Int, urlAudio: String) {
        quranRepository.updateUrlAudioByIdVerse(idVerse, urlAudio)
    }

    suspend fun insertTransliteration(transliteration: Transliteration) {
        quranRepository.insertTransliteration(transliteration)
    }

    fun getTransliterationByIdWords(idWords: Int): Transliteration {
        return quranRepository.getTransliterationByIdWords(idWords)
    }

    suspend fun insertTranslation(translation: Translation) {
        quranRepository.insertTranslation(translation)
    }

    fun getTranslationByIdWords(idWords: Int): Translation {
        return quranRepository.getTranslationByIdWords(idWords)
    }

    suspend fun updateTranslation(translation: Translation) {
        quranRepository.updateTranslation(translation)
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

        val listSurahById = viewModelScope.async(Dispatchers.IO) {

            quranRepository.getAllSurahById(
                id, language, words, page, per_page, audio, fields

            )
        }

        return listSurahById.await()
    }

    fun getPaginationSurahById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _paginationSurahById.postValue(
                quranRepository.getPaginationSurahById(
                    id, language, words, page, per_page, audio, fields
                )
            )
        }
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

        val listJuzById = viewModelScope.async(Dispatchers.IO) {
            quranRepository.getAllJuzById(
                id, language, words, page, per_page, audio, fields
            )
        }

        return listJuzById.await()
    }

    fun getPaginationJuzById(
        id: Int,
        language: String,
        words: String,
        page: Int,
        per_page: Int,
        audio: Int,
        fields: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _paginationJuzById.postValue(
                quranRepository.getPaginationJuzById(
                    id, language, words, page, per_page, audio, fields
                )
            )
        }
    }

}
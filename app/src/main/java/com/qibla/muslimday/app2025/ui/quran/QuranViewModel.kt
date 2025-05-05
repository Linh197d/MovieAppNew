package com.qibla.muslimday.app2025.ui.quran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.network.QuranService
import com.qibla.muslimday.app2025.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
    private val quranService: QuranService,
) : ViewModel() {
    private val _updateStatus = MutableLiveData<Pair<Int, Boolean>>() // Pair(id, isBookMark)
    val updateStatus: LiveData<Pair<Int, Boolean>> get() = _updateStatus
//    fun getJuzQuranDetail(
//        id: Int,
//        language: String,
//        words: String,
//        audio: Int,
//        word_fields: String,
//    ): Flow<PagingData<VerseJuz>> {
//        return Pager(PagingConfig(pageSize = 10)) {
//            JuzDataSource(quranService, id, language, words, audio, word_fields)
//        }.flow.cachedIn(viewModelScope)
//    }

    fun insertQuran(quranEntity: QuranEntity) {

            viewModelScope.launch(Dispatchers.IO) {
                quranRepository.insertQuran(quranEntity)
            }
    }

    fun clearAllQuran() {

            viewModelScope.launch(Dispatchers.IO) {
                quranRepository.clearQuran()
            }
    }

    fun getQuranType(type: String): LiveData<List<QuranEntity>> {
        return MutableLiveData(quranRepository.getQuranType(type))
    }

    fun getItemChecked(type: String): LiveData<List<QuranEntity>> {
        return quranRepository.getItemChecked(type)
    }


    fun updateIsCheck(id: Int, isBookMark: Boolean) {
        viewModelScope.launch {

            quranRepository.updateIsCheck(id, isBookMark)
            _updateStatus.postValue(Pair(id, isBookMark)) // Cập nhật trạng thái khi xong
        }
    }

}

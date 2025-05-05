package com.qibla.muslimday.app2025.ui.quran.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
) : ViewModel() {
    fun getItemChecked(type: String): LiveData<List<QuranEntity>> {
        return quranRepository.getItemChecked(type)
    }


    fun updateIsCheck(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            quranRepository.updateIsCheck(id, isCheck)
        }
    }

}
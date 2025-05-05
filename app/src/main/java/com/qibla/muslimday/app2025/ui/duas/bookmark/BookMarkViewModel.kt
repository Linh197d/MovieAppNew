package com.qibla.muslimday.app2025.ui.duas.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.repository.DuasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val dailyDuasRepository: DuasRepository
) : ViewModel() {

    fun getItemChecked(): LiveData<List<DuasEntity>> {
        return dailyDuasRepository.getItemChecked()
    }

    fun updateIsCheck(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            dailyDuasRepository.updateIsCheck(id, isCheck)
        }
    }

}
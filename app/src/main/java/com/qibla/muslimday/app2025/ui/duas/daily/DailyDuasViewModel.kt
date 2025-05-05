package com.qibla.muslimday.app2025.ui.duas.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.repository.DuasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyDuasViewModel @Inject constructor(
    private val dailyDuasRepository: DuasRepository
) : ViewModel() {

    fun getDailyDuasId(id: Int): LiveData<List<DuasEntity>> {
        return dailyDuasRepository.getDuasId(id)
    }

    fun insertDailyDuas(dailyDuasEntity: DuasEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyDuasRepository.insertDuas(dailyDuasEntity)
        }
    }

    fun updateIsCheck(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            dailyDuasRepository.updateIsCheck(id, isCheck)
        }
    }
}
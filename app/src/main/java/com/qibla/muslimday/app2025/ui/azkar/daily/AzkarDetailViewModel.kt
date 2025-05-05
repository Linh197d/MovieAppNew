package com.qibla.muslimday.app2025.ui.azkar.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.repository.AzkarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarDetailViewModel @Inject constructor(
    private val azkarRepository: AzkarRepository
) : ViewModel() {
    fun getAzkarId(id: Int): LiveData<List<AzkarEntity>> {
        return azkarRepository.getAzkarId(id)
    }

    fun updateIsCheck(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            azkarRepository.updateIsCheck(id, isCheck)
        }
    }

}
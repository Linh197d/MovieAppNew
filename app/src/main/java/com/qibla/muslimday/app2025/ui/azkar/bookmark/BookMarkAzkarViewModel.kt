package com.qibla.muslimday.app2025.ui.azkar.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.repository.AzkarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkAzkarViewModel @Inject constructor(
    private val azkarRepository: AzkarRepository
) : ViewModel() {
    fun getItemChecked(): LiveData<List<AzkarEntity>> {
        return azkarRepository.getItemChecked()
    }

    fun updateIsCheck(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            azkarRepository.updateIsCheck(id, isCheck)
        }
    }
}
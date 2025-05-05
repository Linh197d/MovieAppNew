package com.qibla.muslimday.app2025.ui.mosque

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.network.model_mosque.MosqueWithImage
import com.qibla.muslimday.app2025.repository.MosqueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MosqueViewModel @Inject constructor(
    private val mosqueRepository: MosqueRepository,
) : ViewModel() {

    private val _mosques = MutableLiveData<List<MosqueWithImage>>()
    val mosques: LiveData<List<MosqueWithImage>> = _mosques
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private var job: Job? = null

    fun getMosquesWithImages(context: Context, lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mosqueList = mosqueRepository.getMosques(context, lat, lng)
                println("mosqueList === $mosqueList")
                _mosques.postValue(mosqueList)
            } catch (e: Exception) {
                println("mosqueList === $e")
                Log.e("MosqueViewModel", "Lỗi khi lấy mosque: ${e.message}")
            }
        }
    }

    fun getDisplayNameFromLatLon(context: Context, lat: Double, lng: Double) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val nameAddress = mosqueRepository.getAddress(lat, lng)
                _name.postValue(nameAddress)
            } catch (e: Exception) {
                Log.e("MosqueViewModel", "Lỗi khi lấy name: ${e.message}")
            }
        }
    }
}

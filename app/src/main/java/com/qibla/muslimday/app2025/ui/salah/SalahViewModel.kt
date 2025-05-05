package com.qibla.muslimday.app2025.ui.salah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.network.model.PrayTimeModel
import com.qibla.muslimday.app2025.repository.PrayTimeNewRepository
import com.qibla.muslimday.app2025.repository.PrayTimeRepository
import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalahViewModel @Inject constructor(
    private val prayTimeRepository: PrayTimeRepository,
    private val prayTimeNewRepository: PrayTimeNewRepository
) : ViewModel() {
    val prayTime: LiveData<PrayTimeModel>
        get() = _prayTime
    private var _prayTime = MutableLiveData<PrayTimeModel>()
    var job: Job? = null
    val prayTimeNew: LiveData<PrayerTimeBackup>
        get() = _prayTimeNew
    private var _prayTimeNew = MutableLiveData<PrayerTimeBackup>()

    fun getPrayTimeNew(date: String, lat: Double, long: Double, method: Int, school: Int) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            println("tunglv1 ::: data API start")
            _prayTimeNew.postValue(
                prayTimeNewRepository.getPrayTime(date, lat, long, method, school)
            )
        }
    }

    fun getPrayTime(
        latitude: Double,
        longitude: Double,
        timezone: String,
        juristic: Int,
        method: Int,
        timeFormat: Int,
        date: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            _prayTime.postValue(
                prayTimeRepository.getPrayTime(
                    latitude,
                    longitude,
                    timezone,
                    juristic,
                    method,
                    timeFormat,
                    date
                )
            )
        }
    }
}
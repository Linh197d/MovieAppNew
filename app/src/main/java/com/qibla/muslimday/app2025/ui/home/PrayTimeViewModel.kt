package com.qibla.muslimday.app2025.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.database.timePray.PrayTimeEntity
import com.qibla.muslimday.app2025.network.model.PrayTimeModel
import com.qibla.muslimday.app2025.repository.AzkarRepository
import com.qibla.muslimday.app2025.repository.DuasRepository
import com.qibla.muslimday.app2025.repository.PrayTimeNewRepository
import com.qibla.muslimday.app2025.repository.PrayTimeRepository
import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayTimeViewModel @Inject constructor(
    private val prayTimeRepository: PrayTimeRepository,
    private val azkarRepository: AzkarRepository,
    private val duasRepository: DuasRepository,
    private val prayerTimeNewRepository: PrayTimeNewRepository
) : ViewModel() {

    val prayTime: LiveData<PrayTimeModel>
        get() = _prayTime
    private var _prayTime = MutableLiveData<PrayTimeModel>()

    val prayTimeNew: LiveData<PrayerTimeBackup>
        get() = _prayTimeNew
    private var _prayTimeNew = MutableLiveData<PrayerTimeBackup>()

    private var job: Job? = null

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

    fun getPrayTimeNew(date: String, lat: Double, long: Double, method: Int, school: Int) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            println("tunglv ::: start CallAPI")
            _prayTimeNew.postValue(
                prayerTimeNewRepository.getPrayTime(date, lat, long, method, school)
            )
        }
    }

    fun updateIsCheck(id: Int, time: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prayTimeRepository.updateIsCheck(id, time)
        }
    }

    fun insertPrayTime(prayTimeEntity: PrayTimeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            prayTimeRepository.insertPrayTime(prayTimeEntity)
        }
    }

    fun getPrayTimeEntity(): LiveData<List<PrayTimeEntity>> {
        return prayTimeRepository.getPrayTime()
    }

    fun getAzkarIdRandom(id: Int): LiveData<AzkarEntity> {
        return azkarRepository.getAzkarIdRandom(id)
    }

    fun getDuasIdRandom(id: Int): LiveData<DuasEntity> {
        return duasRepository.getDuasIdRandom(id)
    }

}
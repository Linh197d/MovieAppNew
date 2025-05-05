package com.qibla.muslimday.app2025.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.qibla.muslimday.app2025.database.timePray.PrayTimeDao
import com.qibla.muslimday.app2025.database.timePray.PrayTimeEntity
import com.qibla.muslimday.app2025.network.PrayTimeService
import com.qibla.muslimday.app2025.network.model.PrayTimeModel
import javax.inject.Inject

class PrayTimeRepository @Inject constructor(
    private val prayTimeService: PrayTimeService,
    private val prayTimeDao: PrayTimeDao
) {
    suspend fun getPrayTime(
        latitude: Double,
        longitude: Double,
        timezone: String,
        juristic: Int,
        method: Int,
        timeFormat: Int,
        date: String
    ): PrayTimeModel {
        var prayTime: PrayTimeModel = PrayTimeModel(null, null, false)
        try {
            prayTime = prayTimeService.getPrayTime(
                latitude,
                longitude,
                timezone,
                juristic,
                method,
                timeFormat,
                date
            )
            println("tunglv ::: data API = $prayTime")
        } catch (e: Exception) {
            Log.d("ntt", "Error Exeption" + e)
            println("tunglv ::: error data API = $e")
        }
        return prayTime
    }

    suspend fun insertPrayTime(prayTimeEntity: PrayTimeEntity) {
        prayTimeDao.insertPrayTime(prayTimeEntity)
    }

    fun getPrayTime(): LiveData<List<PrayTimeEntity>> {
        return prayTimeDao.getPrayTime()
    }

    fun getPrayTimes(): List<PrayTimeEntity> {
        return prayTimeDao.getPrayTimeS()
    }

    suspend fun updateIsCheck(id: Int, time: String) {
        prayTimeDao.updateIsCheck(id, time)
    }

}
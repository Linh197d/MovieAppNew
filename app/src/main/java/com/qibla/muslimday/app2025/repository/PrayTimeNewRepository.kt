package com.qibla.muslimday.app2025.repository

import com.qibla.muslimday.app2025.network.PrayTimeNewService
import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import javax.inject.Inject

class PrayTimeNewRepository @Inject constructor(
    private val prayTimeNewService: PrayTimeNewService,
) {
    suspend fun getPrayTime(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): PrayerTimeBackup {
        var prayTime: PrayerTimeBackup = PrayerTimeBackup(0, null, "")
        try {
            prayTime = prayTimeNewService.getPrayTime(
                date,
                latitude,
                longitude,
                method,
                school
            )
            println("tunglv1 ::: data API = $prayTime")

        } catch (e: Exception) {
            e.printStackTrace()
            println("tunglv1 ::: error data API = $e")
        }
        return prayTime
    }


}
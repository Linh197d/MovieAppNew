package com.qibla.muslimday.app2025.repository

import androidx.lifecycle.LiveData
import com.qibla.muslimday.app2025.database.Azkar.AzkarDao
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AzkarRepository @Inject constructor(private val azkarDao: AzkarDao) {

    suspend fun insertAzkar(azkarEntity: AzkarEntity) {
        azkarDao.insertAzkar(azkarEntity)
    }

    fun getAzkarId(id: Int): LiveData<List<AzkarEntity>> {
        return azkarDao.getAzkarId(id)
    }

    fun getAzkarListId(id: Int): List<AzkarEntity> {
        return azkarDao.getListAzkarId(id)
    }

    fun getAzkarIdRandom(id: Int): LiveData<AzkarEntity> {
        return azkarDao.getAzkarIdRandom(id)
    }

    fun getAzkarIdRandomS(id: Int): AzkarEntity {
        return azkarDao.getAzkarIdRandomS(id)
    }

    suspend fun getAzkarNameIdRandom(id: Int): AzkarEntity {
        return withContext(Dispatchers.IO) {
            azkarDao.getAzkarNameIdRandom(id)
        }
    }


    fun getItemChecked(): LiveData<List<AzkarEntity>> {
        return azkarDao.getItemChecked()
    }

    suspend fun updateIsCheck(id: Int, isCheck: Boolean) {
        azkarDao.updateIsCheck(id, isCheck)
    }
}
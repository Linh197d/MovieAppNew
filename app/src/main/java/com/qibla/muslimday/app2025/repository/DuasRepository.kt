package com.qibla.muslimday.app2025.repository

import androidx.lifecycle.LiveData
import com.qibla.muslimday.app2025.database.Duas.DuasDao
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import javax.inject.Inject

class DuasRepository @Inject constructor(private val duasDao: DuasDao) {

    suspend fun insertDuas(duasEntity: DuasEntity) {
        duasDao.insertDuas(duasEntity)
    }

    fun getDuasId(id: Int): LiveData<List<DuasEntity>> {
        return duasDao.getDuasId(id)
    }

    fun getDuasListId(id: Int): List<DuasEntity> {
        return duasDao.getListDuasId(id)
    }

    fun getDuasIdRandom(id: Int): LiveData<DuasEntity> {
        return duasDao.getDuasIdRandom(id)
    }

    fun getDuasIdRandomS(id: Int): DuasEntity? {
        return duasDao.getDuasIdRandomS(id)
    }

    fun getItemChecked(): LiveData<List<DuasEntity>> {
        return duasDao.getItemChecked()
    }

    suspend fun updateIsCheck(id: Int, isCheck: Boolean) {
        duasDao.updateIsCheck(id, isCheck)
    }
}
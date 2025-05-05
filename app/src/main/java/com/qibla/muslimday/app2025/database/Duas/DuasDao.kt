package com.qibla.muslimday.app2025.database.Duas

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DuasDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDuas(duasEntity: DuasEntity)

    @Query("SELECT * FROM DailyDuas WHERE nameId = :id")
    fun getDuasId(id: Int): LiveData<List<DuasEntity>>

    @Query("SELECT * FROM DailyDuas WHERE nameId = :id")
    fun getListDuasId(id: Int): List<DuasEntity>

    @Query("SELECT * FROM DailyDuas WHERE id = :id")
    fun getDuasIdRandom(id: Int): LiveData<DuasEntity>

    @Query("SELECT * FROM DailyDuas WHERE id = :id")
    fun getDuasIdRandomS(id: Int): DuasEntity?

    @Query("SELECT * FROM DailyDuas WHERE isCheck = 1")
    fun getItemChecked(): LiveData<List<DuasEntity>>

    @Query("UPDATE DailyDuas SET isCheck = :isCheck WHERE id = :id")
    suspend fun updateIsCheck(id: Int, isCheck: Boolean)
}
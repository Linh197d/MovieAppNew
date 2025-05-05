package com.qibla.muslimday.app2025.ui.duas.daily.adapter

import com.qibla.muslimday.app2025.database.Duas.DuasEntity

interface IonClickItemDailyDuas {
    fun onClickBookMarkItemDailyDuas(dailyDuasEntity: DuasEntity)
    fun onClickShareItemDailyDuas(dailyDuasEntity: DuasEntity)
}
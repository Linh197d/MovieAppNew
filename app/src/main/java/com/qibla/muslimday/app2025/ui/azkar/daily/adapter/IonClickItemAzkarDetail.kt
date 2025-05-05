package com.qibla.muslimday.app2025.ui.azkar.daily.adapter

import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity

interface IonClickItemAzkarDetail {
    fun onClickBookMarkItem(azkarEntity: AzkarEntity)
    fun onClickShareItem(azkarEntity: AzkarEntity)
}
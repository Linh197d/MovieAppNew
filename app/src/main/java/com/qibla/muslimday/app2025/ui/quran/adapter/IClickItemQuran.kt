package com.qibla.muslimday.app2025.ui.quran.adapter

import com.qibla.muslimday.app2025.database.Quran.QuranEntity

interface IClickItemQuran {
    fun onClickItemQuran(QuranEntity: QuranEntity)

    fun onClickBookMarkItemQuran(QuranEntity: QuranEntity, position: Int)
}
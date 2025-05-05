package com.qibla.muslimday.app2025.ui.quran.adapter

import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse


interface IOnClickItemQuranDetail {
    fun onClickBookMarkItemQuranDetail(verse: Verse)
    fun onClickShareItemQuranDetail(verse: Verse)
    fun onClickItemQuranDetail(verse: Verse, position: Int)
}
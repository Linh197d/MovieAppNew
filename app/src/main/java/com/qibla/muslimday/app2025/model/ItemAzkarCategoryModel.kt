package com.qibla.muslimday.app2025.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemAzkarCategoryModel(val title: String, val topicId: Int, val categoryId: Int) :
    Parcelable {
}
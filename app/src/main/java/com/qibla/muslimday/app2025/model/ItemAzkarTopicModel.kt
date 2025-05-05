package com.qibla.muslimday.app2025.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ItemAzkarTopicModel(
    var title: String,
    var icBackground: Int,
    var listAzkar: ArrayList<ItemAzkarCategoryModel>,
    var parentId: Int,
    var isChildVisible: Boolean = false
) : Parcelable {
}
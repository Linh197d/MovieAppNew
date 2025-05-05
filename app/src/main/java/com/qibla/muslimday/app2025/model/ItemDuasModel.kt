package com.qibla.muslimday.app2025.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ItemDuasModel(
    var title: String,
    var icBackground: Int,
    var listDuas: ArrayList<ItemDuasChildModel>,
) : Parcelable {
}
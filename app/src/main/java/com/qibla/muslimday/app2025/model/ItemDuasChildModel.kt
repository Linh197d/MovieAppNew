package com.qibla.muslimday.app2025.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ItemDuasChildModel(val id: Int, val title: String, var isShow: Boolean = true) : Parcelable {
}
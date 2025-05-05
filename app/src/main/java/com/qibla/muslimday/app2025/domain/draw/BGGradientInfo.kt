package com.qibla.muslimday.app2025.domain.draw

import android.graphics.drawable.GradientDrawable
import androidx.annotation.Keep

@Keep
class BGGradientInfo constructor() {
    var centerX: Float = 0.5f
    var centerY: Float = 0.5f
    lateinit var colors: IntArray
    var direction: String = GradientDrawable.Orientation.BL_TR.name
    var radius: Float = 1.5f
    var style: Int = 0
}
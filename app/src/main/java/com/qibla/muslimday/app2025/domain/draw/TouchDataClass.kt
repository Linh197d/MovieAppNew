package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
class TouchDataClass constructor() {
    var brushSmoothnessSize: Float = 0f
    var eraserSizePercentage: Float = 0f
    var isErasing: Boolean = false
    var isPainting: Boolean = false
    var paintColor: Int = 0
    var paintSizePercentage: Float = 0f
    var touchPoints: FloatArray? = null
    fun updateDimension(f: Float) {
        brushSmoothnessSize *= f
        if (touchPoints != null && touchPoints!!.isNotEmpty()) {
            for (i in touchPoints!!.indices) {
                val fArr: FloatArray? = touchPoints
                fArr!![i] = fArr[i] * f
            }
        }
    }
}
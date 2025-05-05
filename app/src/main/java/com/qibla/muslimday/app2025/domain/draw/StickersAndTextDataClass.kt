package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
open class StickersAndTextDataClass constructor() {
    var angle: Float = 0f
    var height: Float = 0f
    var isLocked: Boolean = false
    var isVisible: Boolean = false
    var opacity: Int = 0
    var position: Int = 0
    var size: Float = 0f
    var width: Float = 0f
    var xPos: Float = 0f
    var yPos: Float = 0f
    var shadowColor: Int = 0
    var shadowRadius: Float = 0f
    var shadownXvalue: Float = 25.0f
    var shadownYvalue: Float = 25.0f
    var x3dvalue: Float = 0f
    var y3dvalue: Float = 0f
    fun updateDimension(f: Float) {
        xPos *= f
        yPos *= f
        size *= f
        width *= f
        height *= f
    }
}
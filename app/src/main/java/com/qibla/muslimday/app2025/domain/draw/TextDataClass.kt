package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep
import androidx.core.view.ViewCompat
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel

@Keep
class TextDataClass constructor() : StickersAndTextDataClass() {
    var color: Int = ViewCompat.MEASURED_STATE_MASK
    var strokeColor: Int = ViewCompat.MEASURED_STATE_MASK
    var strokeWith: Float = 0.0f
    var letterSpacing: Float = 0.0f
    var fontIndex: Int = 0
    var fontName: String? = null
    var isFlipped: Int = 1
    var logoFontClass: FontModel? = null
    var text: String? = null
    var curvature: Float = 50.0f
    var isBold: Boolean = false
    var isItalic: Boolean = false
}
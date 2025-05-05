package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
class BGClassInfo constructor() {
    var color: Int = 0
    var gradient: BGGradientInfo? = null
    var f85id: Int = 0
    var path: String? = null
    var type: Int = 0
}
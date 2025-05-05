package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
class StickersDataClass constructor() : StickersAndTextDataClass() {
    var colorize: Int = 0
    var imagePath: String? = null
    var isFlipped: Int = 1
    var isFlippedVertical: Int = 1
    var isFromGallery: Boolean = false
    var stickerId: Int = 0
    var stickerModule: String? = null
    var stickerName: String? = null
}
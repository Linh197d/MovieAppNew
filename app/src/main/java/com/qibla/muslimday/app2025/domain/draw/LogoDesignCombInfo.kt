package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
class LogoDesignCombInfo constructor() {
    var logoArtInfo: LogoArtInfo? = null
    var f87bg: BGClassInfo? = null
    var filter: FilterClassInfo? = null
    var stickers: Array<StickersDataClass?>? = null
    var texts: Array<TextDataClass?>? = null
    var touchDataClasses: Array<TouchDataClass?>? = null
}
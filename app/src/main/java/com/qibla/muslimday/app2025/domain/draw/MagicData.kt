package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep
import com.qibla.muslimday.app2025.R

@Keep
object MagicData {
    var PATTERN_BRUSH_START_INDEX: Int = 14
    fun getBGIDForResID(i: Int): Int {
        return 1
    }

    fun getBGResIDForId(i: Int): Int {
        return R.drawable.bg_create_logo
    }

    fun getStickerIDForResID(i: Int): Int {
        return 0
    }

    fun getStickerResIdForId(i: Int): Int {
        return R.drawable.ic_after_prayers
    }

    fun updateDimensions(logoDesignCombInfo: LogoDesignCombInfo, f: Float) {
        if (logoDesignCombInfo.touchDataClasses != null && logoDesignCombInfo.touchDataClasses!!.isNotEmpty()) {
            for (updateDimension: TouchDataClass? in logoDesignCombInfo.touchDataClasses!!) {
                updateDimension!!.updateDimension(f)
            }
        }
        if (logoDesignCombInfo.stickers != null && logoDesignCombInfo.stickers!!.isNotEmpty()) {
            for (i in logoDesignCombInfo.stickers!!.indices) {
                if (logoDesignCombInfo.stickers!![i] != null) {
                    logoDesignCombInfo.stickers!![i]!!.updateDimension(f)
                }
            }
        }
        if (logoDesignCombInfo.texts != null && logoDesignCombInfo.texts!!.isNotEmpty()) {
            for (i2 in logoDesignCombInfo.texts!!.indices) {
                if (logoDesignCombInfo.texts!![i2] != null) {
                    logoDesignCombInfo.texts!![i2]!!.updateDimension(f)
                }
            }
        }
    }
}
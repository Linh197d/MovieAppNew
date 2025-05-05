package com.yakin.indicator.annotation

import androidx.annotation.IntDef
import com.yakin.indicator.enums.IndicatorOrientation

/**
 *
 * @author zhangpan
 * @date 2021/1/21
 */
@IntDef(
    IndicatorOrientation.INDICATOR_HORIZONTAL, IndicatorOrientation.INDICATOR_VERTICAL,
    IndicatorOrientation.INDICATOR_RTL
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class AIndicatorOrientation()

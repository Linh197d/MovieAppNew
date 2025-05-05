package com.yakin.indicator.annotation

import androidx.annotation.IntDef
import com.yakin.indicator.enums.IndicatorStyle

/**
 * <pre>
 * Created by zhangpan on 2019-10-18.
 * Description:
</pre> *
 */
@IntDef(IndicatorStyle.CIRCLE, IndicatorStyle.DASH, IndicatorStyle.ROUND_RECT)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class AIndicatorStyle

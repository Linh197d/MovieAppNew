package com.qibla.muslimday.app2025.ui.ramadan.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class ThreeLayoutCreateViewGrp : ViewGroup {
    private var aspectRatio: Float = 1.0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    )

    fun setAspectRatio(f: Float) {
        aspectRatio = f
        requestLayout()
        forceLayout()
        postInvalidate()
    }

    public override fun onMeasure(i: Int, i2: Int) {
        val f: Float
        super.onMeasure(i, i2)
        if (getChildCount() > 0) {
            measureChild(getChildAt(0), i, i2)
            measureChild(getChildAt(2), i, i2)
            val measuredHeight: Int =
                getMeasuredHeight() - (getChildAt(0).getMeasuredHeight() + getChildAt(2).getMeasuredHeight())
            val measuredWidth: Int = getMeasuredWidth()
            var f2: Float =
                if (measuredHeight < measuredWidth) measuredHeight.toFloat() else measuredWidth.toFloat()
            if (aspectRatio > 1.0f) {
                f = f2 / aspectRatio
            } else if (aspectRatio < 1.0f) {
                f = f2
                f2 = aspectRatio * f2
            } else {
                f = f2
            }
            measureChild(
                getChildAt(1),
                MeasureSpec.makeMeasureSpec(f2.toInt(), MeasureSpec.getMode(i)),
                MeasureSpec.makeMeasureSpec(f.toInt(), MeasureSpec.getMode(i2))
            )
        }
    }

    public override fun onLayout(z: Boolean, i: Int, i2: Int, i3: Int, i4: Int) {
        if (getChildCount() > 0) {
            val childAt: View = getChildAt(0)
            val childAt2: View = getChildAt(1)
            val childAt3: View = getChildAt(2)
            childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight())
            val measuredHeight: Int =
                getMeasuredHeight() - (getChildAt(0).getMeasuredHeight() + getChildAt(2).getMeasuredHeight())
            getMeasuredWidth()
            childAt2.layout(
                (getMeasuredWidth() - childAt2.getMeasuredWidth()) shr 1,
                childAt.getMeasuredHeight() + ((measuredHeight - childAt2.getMeasuredHeight()) shr 1),
                (getMeasuredWidth() + childAt2.getMeasuredWidth()) shr 1,
                childAt.getMeasuredHeight() + ((measuredHeight + childAt2.getMeasuredHeight()) shr 1)
            )
            if (childAt3.getMeasuredWidth() < getWidth()) {
                val width: Int = (getWidth() - childAt3.getMeasuredWidth()) / 2
                childAt3.layout(
                    width,
                    getMeasuredHeight() - childAt3.getMeasuredHeight(),
                    childAt3.getMeasuredWidth() + width,
                    (getMeasuredHeight() - childAt3.getMeasuredHeight()) + childAt3.getMeasuredHeight()
                )
                return
            }
            childAt3.layout(
                0,
                getMeasuredHeight() - childAt3.getMeasuredHeight(),
                getWidth(),
                (getMeasuredHeight() - childAt3.getMeasuredHeight()) + childAt3.getMeasuredHeight()
            )
        }
    }
}
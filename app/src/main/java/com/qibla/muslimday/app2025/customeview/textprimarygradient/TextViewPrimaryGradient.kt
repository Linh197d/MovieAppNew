package com.qibla.muslimday.app2025.customeview.textprimarygradient

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TextViewPrimaryGradient constructor(
    ctx: Context,
    attrs: AttributeSet
) : AppCompatTextView(ctx, attrs) {

    init {
        postDelayed({
            val textShader: Shader = LinearGradient(
                0f, 0f, width.toFloat(), this.textSize,
                intArrayOf(
                    Color.parseColor("#0E8C62"),
                    Color.parseColor("#008040"),
                ), null, Shader.TileMode.CLAMP
            )
            paint.setShader(textShader)
            setTextColor(Color.parseColor("#0E8C62"))
        }, 50)
    }

}
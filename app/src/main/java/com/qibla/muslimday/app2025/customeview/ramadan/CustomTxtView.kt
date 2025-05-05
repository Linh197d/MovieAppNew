package com.qibla.muslimday.app2025.customeview.ramadan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel


class CustomTxtView : AppCompatTextView {
    private val f95bg: BitmapDrawable? = null
    private var isSelected: Boolean = false
    var fontModelObject: FontModel? = null
        private set

    constructor(context: Context?) : super((context)!!)
    constructor(context: Context?, attributeSet: AttributeSet?) : super(
        (context)!!, attributeSet
    ) {
        init(attributeSet)
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        (context)!!, attributeSet, i
    ) {
        init(attributeSet)
    }

    @SuppressLint("ResourceAsColor")
    public override fun setSelected(z: Boolean) {
        if (z != isSelected) {
//            if (z) {
//                setBackgroundResource(R.drawable.bg_item_selected_text_font)
//                setTextColor(ContextCompat.getColor(context, R.color.white))
//            } else {
//                setBackgroundResource(R.drawable.bg_item_not_selected_text_font)
//                setTextColor(ContextCompat.getColor(context, R.color.gray_2))
//            }
            postInvalidate()
        }
        isSelected = z
    }

    public override fun isSelected(): Boolean {
        return isSelected
    }

    private fun init(attributeSet: AttributeSet?) {
        Log.d("LM", "init: starting initializer for Custom Text View")
//        val obtainStyledAttributes: TypedArray =
//            context.obtainStyledAttributes(attributeSet, R.styleable.MyCustomTextView)
//        val indexCount: Int = obtainStyledAttributes.indexCount
//        for (i in 0 until indexCount) {
//            obtainStyledAttributes.getIndex(i)
//        }
//        obtainStyledAttributes.recycle()
        Log.d("LM", "init: Ending initializer for Custom Text View")
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val z: Boolean = isSelected
    }

    fun checkForSelection(fontModel: FontModel) {
        setSelected((fontModel == fontModelObject))
    }

    fun setLogoFont(fontModel: FontModel?) {
        fontModelObject = fontModel
        typeface = if (fontModel != null) {
            Typeface.createFromAsset(
                context.assets,
                "fonts/${fontModel?.fontPath}"
            )
        } else {
            null as Typeface?
        }
    }
}
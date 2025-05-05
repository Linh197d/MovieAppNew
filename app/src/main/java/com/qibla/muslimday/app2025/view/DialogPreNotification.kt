package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.qibla.muslimday.app2025.R
import com.shawnlin.numberpicker.NumberPicker


class DialogPreNotification(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: DialogPreNotification.OnPress? = null
    private val tvPraying: TextView
    private val btnCancel: Button
    private val btnSave: Button
    private val numberPicker: NumberPicker
    private val imgReset: ImageButton

    private var time: Int = 0

    init {
        setContentView(R.layout.dialog_pre_notification)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvPraying = findViewById<View>(R.id.tv_praying) as TextView

        btnSave = findViewById<View>(R.id.btn_save) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button

        numberPicker = findViewById(R.id.time_pre_notification)

        imgReset = findViewById<View>(R.id.img_reset) as ImageButton

        numberPicker.maxValue = 60

        numberPicker.isFadingEdgeEnabled = true

        numberPicker.isScrollerEnabled = true

        numberPicker.wrapSelectorWheel = false

        changeNumberTime()

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun savePreNotification(time: Int)
    }

    fun init(context: Context, pray: String, timePre: Int, onPress: OnPress?) {
        this.onPress = onPress
        this.numberPicker.value = timePre
        this.tvPraying.text = pray
    }

    private fun changeNumberTime() {
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("ntt", "New value: $newVal")
            time = newVal
        }

    }

    private fun onclick() {
        btnSave.setOnClickListener {
            Log.d("ntt", "set onclick: $time")
            onPress?.savePreNotification(time)
        }

        imgReset.setOnClickListener {
            numberPicker.value = 0
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.shawnlin.numberpicker.NumberPicker


class DialogAdjustIslamicCalendar(
    private val context: Context,
    preferenceHelper: PreferenceHelper
) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val btnCancel: Button
    private val btnSave: Button
    private val numberPicker: NumberPicker
    private val tvDate: TextView
    private val imgReset: ImageButton

    private var date: Int = 0
    private lateinit var utils: Array<String>

    init {
        setContentView(R.layout.dialog_adjust_islamic_calendar)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tv_title) as TextView

        btnSave = findViewById<View>(R.id.btn_save) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button

        tvDate = findViewById<View>(R.id.tv_date) as TextView

        numberPicker = findViewById(R.id.time_adjust_islamic_calendar)

        imgReset = findViewById<View>(R.id.img_reset) as ImageButton

        tvTitle.isSelected = true

        numberPicker.minValue = -3
        numberPicker.maxValue = 3

        numberPicker.value = preferenceHelper.getInt(PreferenceHelper.ISLAMIC_CALENDAR)

        numberPicker.isFadingEdgeEnabled = true

        numberPicker.isScrollerEnabled = true

        numberPicker.wrapSelectorWheel = false

        changeNumberDate()

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun saveDate(day: Int)
    }

    fun init(context: Context?, onPress: OnPress?) {
        this.onPress = onPress
    }

    private fun changeNumberDate() {
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            date = newVal
        }

    }


    private fun onclick() {
        btnSave.setOnClickListener {
            onPress?.saveDate(date)
        }

        imgReset.setOnClickListener {
            numberPicker.value = 0
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
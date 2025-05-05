package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.util.Const
import com.shawnlin.numberpicker.NumberPicker


class DialogPrayingTimeAdjustment(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val tvPraying: TextView
    private val tvTime: TextView
    private val btnCancel: Button
    private val btnSave: Button
    private val numberPicker: NumberPicker
    private val imgReset: ImageButton
    private val layoutTimePray: ConstraintLayout
    private val layoutTimeAll: ConstraintLayout

    private var time: Int = 0

    init {
        setContentView(R.layout.dialog_praying_time_adjustment)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvPraying = findViewById<View>(R.id.tv_praying) as TextView
        tvTime = findViewById<View>(R.id.tv_time) as TextView

        layoutTimePray = findViewById<View>(R.id.layout_time_pray) as ConstraintLayout
        layoutTimeAll = findViewById<View>(R.id.layout_time_all) as ConstraintLayout

        btnSave = findViewById<View>(R.id.btn_save) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button

        numberPicker = findViewById(R.id.time_praying_adjustment)

        imgReset = findViewById<View>(R.id.img_reset) as ImageButton

        numberPicker.minValue = -120
        numberPicker.maxValue = 120

        numberPicker.value = 0

        numberPicker.isFadingEdgeEnabled = true

        numberPicker.isScrollerEnabled = true

        numberPicker.wrapSelectorWheel = false

        changeNumberTime()

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun saveTime(day: Int)
    }

    fun init(
        context: Context?,
        pray: String,
        preferenceHelper: PreferenceHelper,
        onPress: OnPress?
    ) {
        this.onPress = onPress
        if (pray == "All") {
            this.layoutTimeAll.visibility = View.VISIBLE
            this.layoutTimePray.visibility = View.GONE
            numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentAll()
        } else {
            this.layoutTimeAll.visibility = View.GONE
            this.layoutTimePray.visibility = View.VISIBLE

            this.tvPraying.text = pray

            when (pray) {
                "Fajr" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Fajr
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentFajr()

                }

                "Sunrise" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Sunrise
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentSunrise()
                }

                "Dhuhr" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Dhuhr
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentDhuhr()
                }

                "Asr" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Asr
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentAsr()
                }

                "Maghrib" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Maghrib
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentMaghrib()
                }

                "Isha" -> {
                    this.tvTime.text = Const.PrayTimeModel?.data?.timings?.Isha
                    numberPicker.value = preferenceHelper.getPrayingTimeAdjustmentIsha()
                }
            }
        }
    }

    private fun changeNumberTime() {
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            time = newVal
        }

    }

    private fun onclick() {
        btnSave.setOnClickListener {
            onPress?.saveTime(time)
            Const.isCheck = true
        }

        imgReset.setOnClickListener {
            numberPicker.value = 0
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
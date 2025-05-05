package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.util.Const

class DialogInformationPrayingTime(private var context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {

    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val imgFajr: ImageView
    private val imgSunrise: ImageView
    private val imgDhuhr: ImageView
    private val imgAsr: ImageView
    private val imgMaghrib: ImageView
    private val imgIsha: ImageView
    private val tvFajr: TextView
    private val tvSunrise: TextView
    private val tvDhuhr: TextView
    private val tvAsr: TextView
    private val tvMaghrib: TextView
    private val tvIsha: TextView
    private val tvTimeFajr: TextView
    private val tvTimeSunrise: TextView
    private val tvTimeDhuhr: TextView
    private val tvTimeAsr: TextView
    private val tvTimeMaghrib: TextView
    private val tvTimeIsha: TextView
    private val btnPrayingTimeAdj: Button

    init {
        setContentView(R.layout.dialog_information_praying_time)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        btnPrayingTimeAdj = findViewById<View>(R.id.btn_praying_time_adj) as Button

        imgFajr = findViewById<View>(R.id.img_fair) as ImageView
        imgSunrise = findViewById<View>(R.id.img_sunrise) as ImageView
        imgDhuhr = findViewById<View>(R.id.img_dhuhr) as ImageView
        imgAsr = findViewById<View>(R.id.img_asr) as ImageView
        imgMaghrib = findViewById<View>(R.id.img_maghrib) as ImageView
        imgIsha = findViewById<View>(R.id.img_isha) as ImageView
        tvFajr = findViewById<View>(R.id.tv_fair) as TextView
        tvSunrise = findViewById<View>(R.id.tv_sunrise) as TextView
        tvDhuhr = findViewById<View>(R.id.tv_dhuhr) as TextView
        tvAsr = findViewById<View>(R.id.tv_asr) as TextView
        tvMaghrib = findViewById<View>(R.id.tv_maghrib) as TextView
        tvIsha = findViewById<View>(R.id.tv_isha) as TextView
        tvTimeFajr = findViewById<View>(R.id.tv_time_fair) as TextView
        tvTimeSunrise = findViewById<View>(R.id.tv_time_sunrise) as TextView
        tvTimeDhuhr = findViewById<View>(R.id.tv_time_dhuhr) as TextView
        tvTimeAsr = findViewById<View>(R.id.tv_time_asr) as TextView
        tvTimeMaghrib = findViewById<View>(R.id.tv_time_maghrib) as TextView
        tvTimeIsha = findViewById<View>(R.id.tv_time_isha) as TextView


        tvTitle.isSelected = true
        onclick()

        tvTimeFajr.text = Const.PrayTimeModel!!.data?.timings?.Fajr ?: ""
        tvTimeSunrise.text = Const.PrayTimeModel!!.data?.timings?.Sunrise ?: ""
        tvTimeDhuhr.text = Const.PrayTimeModel!!.data?.timings?.Dhuhr ?: ""
        tvTimeAsr.text = Const.PrayTimeModel!!.data?.timings?.Asr ?: ""
        tvTimeMaghrib.text = Const.PrayTimeModel!!.data?.timings?.Maghrib ?: ""
        tvTimeIsha.text = Const.PrayTimeModel!!.data?.timings?.Isha ?: ""


    }


    interface OnPress {
        fun prayingTimeAdj()
    }

    fun init(context: Context, position: Int, onPress: OnPress?) {
        this.context = context
        this.onPress = onPress

        when (position) {
            0 -> {
                tvFajr.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeFajr.setTextColor(context.resources.getColor(R.color.color_bound))
                imgFajr.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }

            1 -> {
                tvSunrise.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeSunrise.setTextColor(context.resources.getColor(R.color.color_bound))
                imgSunrise.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }

            2 -> {
                tvDhuhr.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeDhuhr.setTextColor(context.resources.getColor(R.color.color_bound))
                imgDhuhr.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }

            3 -> {
                tvAsr.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeAsr.setTextColor(context.resources.getColor(R.color.color_bound))
                imgAsr.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }

            4 -> {
                tvMaghrib.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeMaghrib.setTextColor(context.resources.getColor(R.color.color_bound))
                imgMaghrib.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }

            5 -> {
                tvIsha.setTextColor(context.resources.getColor(R.color.color_bound))
                tvTimeIsha.setTextColor(context.resources.getColor(R.color.color_bound))
                imgIsha.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.color_bound
                    )
                )
            }
        }
    }


    private fun onclick() {
        btnPrayingTimeAdj.setOnClickListener {
            onPress?.prayingTimeAdj()
        }
    }
}
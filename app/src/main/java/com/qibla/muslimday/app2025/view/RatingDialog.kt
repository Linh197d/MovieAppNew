package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.qibla.muslimday.app2025.R
import com.willy.ratingbar.RotationRatingBar


class RatingDialog(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialogRate
) {
    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val tvContent: TextView
    private val imgIcon: ImageView
    private val rtb: RotationRatingBar
    private val btnRate: Button
    private val btnLater: Button

    init {
        setContentView(R.layout.dialog_rating_app)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        tvContent = findViewById<View>(R.id.tv_content) as TextView
        rtb = findViewById<View>(R.id.ratingView) as RotationRatingBar
        imgIcon = findViewById<View>(R.id.img_icon) as ImageView

        btnRate = findViewById<View>(R.id.btn_rate) as Button
        btnLater = findViewById<View>(R.id.btn_exit) as Button

        tvContent.isSelected = true
        tvTitle.isSelected = true

        onclick()
        changeRating()

        setCancelable(false)

        btnRate.text = context.resources.getString(R.string.string_rate)

        imgIcon.setImageResource(R.drawable.ic_rate_5)

        changeRating()

        rtb.rating == 5f

        tvTitle.text = context.getString(R.string.string_we_love_you_too)
        tvContent.text = context.getString(R.string.string_thanks_for_your_feedback)

    }

    interface OnPress {
        fun sendThank()
        fun rating()
        fun later()
    }

    fun init(context: Context?, onPress: OnPress?) {
        this.onPress = onPress
    }

    private fun changeRating() {

        rtb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            when (rating) {
                1f -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_1)
                    tvTitle.text = context.getString(R.string.string_oh_no)
                    tvContent.text =
                        context.getString(R.string.string_please_give_us_some_feedback)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }

                2f -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_2)
                    tvTitle.text = context.getString(R.string.string_oh_no)
                    tvContent.text =
                        context.getString(R.string.string_please_give_us_some_feedback)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }

                3f -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_3)
                    tvTitle.text = context.getString(R.string.string_oh_no)
                    tvContent.text =
                        context.getString(R.string.string_please_give_us_some_feedback)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }

                4f -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_4)
                    tvTitle.text = context.getString(R.string.string_we_love_you_too)
                    tvContent.text = context.getString(R.string.string_thanks_for_your_feedback)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }

                5f -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_5)
                    tvTitle.text = context.getString(R.string.string_we_love_you_too)
                    tvContent.text = context.getString(R.string.string_thanks_for_your_feedback)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }

                else -> {
                    imgIcon.setImageResource(R.drawable.ic_rate_default)
                    tvTitle.text = context.getString(R.string.string_do_you_like_the_app)
                    tvContent.text =
                        context.getString(R.string.string_let_us_know_your_experience)
                    btnRate.text = context.resources.getString(R.string.string_rate)
                }
            }
        }
    }

    private fun onclick() {
        btnRate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (rtb.rating == 0f) {
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.Please_feedback),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (rtb.rating <= 4.0) {
                    onPress!!.sendThank()
                } else {
                    onPress!!.rating()
                }
            }
        })
        btnLater.setOnClickListener { onPress!!.later() }
    }
}
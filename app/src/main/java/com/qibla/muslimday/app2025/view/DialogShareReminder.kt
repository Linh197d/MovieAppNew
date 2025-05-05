package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.qibla.muslimday.app2025.R


class DialogShareReminder(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val btnCancel: Button
    private val btnShare: Button
    private var tvNamePray: TextView
    private var tvTimePray: TextView
    private var tvNameLocation: TextView
    private var tvNameDate: TextView

    private var namePray: String = ""
    private var timePray: String = ""
    private var nameLocation: String = ""
    private var nameDate: String = ""


    init {
        setContentView(R.layout.dialog_share_reminder)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)

        btnShare = findViewById<View>(R.id.btn_share) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button

        tvNamePray = findViewById<View>(R.id.tv_name_pray) as TextView
        tvTimePray = findViewById<View>(R.id.tv_time_pray) as TextView
        tvNameLocation = findViewById<View>(R.id.tv_name_location) as TextView
        tvNameDate = findViewById<View>(R.id.tv_name_date) as TextView

        tvNameLocation.isSelected = true
        tvNameDate.isSelected = true

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun share(
            namePray: String,
            timePray: String,
            nameLocation: String,
            nameDate: String,
        )
    }

    fun init(
        context: Context,
        namePray: String,
        timePray: String,
        nameLocation: String,
        nameDate: String, onPress: OnPress?,
    ) {
        this.onPress = onPress
        this.namePray = namePray
        this.tvNamePray.text = namePray
        this.timePray = timePray
        this.tvTimePray.text = timePray
        this.nameLocation = nameLocation
        this.tvNameLocation.text = nameLocation
        this.nameDate = nameDate
        this.tvNameDate.text = nameDate
    }

    private fun onclick() {
        btnShare.setOnClickListener {
            onPress?.share(namePray, timePray, nameLocation, nameDate)
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
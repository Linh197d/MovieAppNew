package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qibla.muslimday.app2025.R

class DialogConnectionWifi(private val context: Context, isBoolean: Boolean) : Dialog(
    context, R.style.CustomAlertDialog
) {

    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val btnCancel: Button
    private val btnSave: Button
    private val btnWifi: Button
    private val constraintLayout: ConstraintLayout

    init {
        setContentView(R.layout.dialog_connection_wifi)
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
        btnWifi = findViewById<View>(R.id.btn_connect) as Button
        constraintLayout = findViewById<View>(R.id.ctr_1) as ConstraintLayout

        if (isBoolean) {
            btnWifi.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
        } else {
            constraintLayout.visibility = View.GONE
            btnWifi.visibility = View.VISIBLE
        }

        tvTitle.isSelected = true
        onclick()
        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun save()
    }

    fun init(context: Context?, onPress: OnPress?) {
        this.onPress = onPress

    }


    private fun onclick() {
        btnSave.setOnClickListener {
            onPress?.save()
        }
        btnWifi.setOnClickListener {
            onPress?.save()
        }
        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
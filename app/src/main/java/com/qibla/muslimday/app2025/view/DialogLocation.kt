package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.qibla.muslimday.app2025.R

class DialogLocation(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {

    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val btnConnect: Button

    init {
        setContentView(R.layout.dialog_location)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        btnConnect = findViewById<View>(R.id.btn_connect) as Button

        tvTitle.isSelected = true
        onclick()
        setCancelable(false)
    }


    interface OnPress {
        fun save()
    }

    fun init(context: Context?, onPress: OnPress?) {
        this.onPress = onPress

    }


    private fun onclick() {
        btnConnect.setOnClickListener {
            onPress?.save()
        }
    }
}
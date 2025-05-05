package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.qibla.muslimday.app2025.R


class DialogWarning(private var context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val btnCancel: Button
    private val btnDone: Button
    private var tvTitle: TextView

    init {
        setContentView(R.layout.dialog_warning)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)

        btnDone = findViewById<View>(R.id.btn_done) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button
        tvTitle = findViewById<View>(R.id.tv_title) as TextView

        tvTitle.isSelected = true

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun enable()
    }

    fun init(context: Context, open: String, onPress: OnPress?) {
        this.onPress = onPress
        this.context = context

        if (open == "tasbih") {
            tvTitle.text = context.getString(R.string.string_do_you_want_to_reset_the_counter)
            btnDone.text = context.getString(R.string.string_reset)
        }
    }


    private fun onclick() {
        btnDone.setOnClickListener {
            onPress?.enable()
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
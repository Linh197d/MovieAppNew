package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.qibla.muslimday.app2025.R
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class DialogCustomizeNumberOfRounds(
    private var context: Context,
) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val edtTotalTime: EditText
    private val btnCancel: Button
    private val btnDone: Button
    private var tvTitle: TextView

    init {
        setContentView(R.layout.dialog_customize_number_of_rounds)
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

        edtTotalTime = findViewById(R.id.number_of_round)

        tvTitle.isSelected = true

        onclick()

        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun saveNumberOfRoundCustomize(num: Int)
    }

    fun init(context: Context, onPress: OnPress?) {
        this.onPress = onPress
        this.context = context

        tvTitle.isSelected = true
    }


    private fun onclick() {
        btnDone.setOnClickListener {
            if (edtTotalTime.text.isEmpty()) {
                Toast.makeText(
                    context,
                    context.getString(R.string.string_please_enter_the_value_of_the_number_of_rounds),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (edtTotalTime.text.toString().toInt() == 33 || edtTotalTime.text.toString()
                    .toInt() == 99 || edtTotalTime.text.toString().toInt() == 100
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.string_value_of_the_number_of_rounds_that_exist),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (edtTotalTime.text.toString().toInt() <= 9) {
                Toast.makeText(
                    context,
                    context.getString(R.string.string_round_10),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                onPress?.saveNumberOfRoundCustomize(edtTotalTime.text.toString().toInt())
            }
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
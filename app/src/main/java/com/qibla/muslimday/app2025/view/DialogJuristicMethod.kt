package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.qibla.muslimday.app2025.R

class DialogJuristicMethod(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private var radioGroup: RadioGroup
    private val tvTitle: TextView
    private val btnCancel: Button
    private val btnSave: Button

    private val radioButtonShafi: RadioButton

    private val radioButtonHanafi: RadioButton

    init {
        setContentView(R.layout.dialog_juristic_method)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        radioGroup = findViewById<View>(R.id.radio_group) as RadioGroup
        radioButtonShafi = findViewById(R.id.rb_shafi)
        radioButtonHanafi = findViewById(R.id.rb_hanafi)

        btnSave = findViewById<View>(R.id.btn_save) as Button
        btnCancel = findViewById<View>(R.id.btn_cancel) as Button


        tvTitle.isSelected = true


        radioButtonShafi.isChecked = true

        onclick()
        changeSelectRadioGroup()

        setCancelable(false)
    }

    private fun changeSelectRadioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_shafi) {
                radioButtonShafi.isChecked = true
                radioButtonHanafi.isChecked = false
            } else if (checkedId == R.id.rb_hanafi) {
                radioButtonShafi.isChecked = false
                radioButtonHanafi.isChecked = true
            }
        }
    }

    interface OnPress {
        fun cancel()
        fun save(juristicMethod: Int)
    }

    fun init(context: Context?, juristicMethod: Int, onPress: OnPress?) {
        this.onPress = onPress
        if (juristicMethod == 0) {
            radioButtonShafi.isChecked = true
            radioButtonHanafi.isChecked = false
        } else if (juristicMethod == 1) {
            radioButtonShafi.isChecked = false
            radioButtonHanafi.isChecked = true
        }
    }


    private fun onclick() {
        btnSave.setOnClickListener {
            if (radioButtonShafi.isChecked) {
                onPress?.save(0)
            } else if (radioButtonHanafi.isChecked) {
                onPress?.save(1)
            }
        }
        btnCancel.setOnClickListener { onPress?.cancel() }
    }
}
package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class DialogAdjustTheNumberOfRounds(
    private var context: Context,
    private var preferenceHelper: PreferenceHelper,
) : Dialog(
    context, R.style.CustomAlertDialog
) {

    private var onPress: OnPress? = null
    private var btnNumberRound33: Button
    private var btnNumberRound99: Button
    private var btnNumberRound100: Button
    private var layoutCustomize: ConstraintLayout
    private var tvCustomize: TextView
    private var tvTitle: TextView
    private var imgEdit: ImageButton
    private val btnCancel: Button
    private val btnDone: Button
    private val llAdjustNumberRounds: LinearLayout
    private val tvCustomizeNew: TextView
    private val tvAdjustNumberRounds: TextView

    private var popupWindow: PopupWindow? = null

    var numSelected = 0

    init {
        setContentView(R.layout.dialog_adjust_the_number_of_rounds)
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

        btnNumberRound33 = findViewById<View>(R.id.btn_number_33) as Button
        btnNumberRound99 = findViewById<View>(R.id.btn_number_99) as Button
        btnNumberRound100 = findViewById<View>(R.id.btn_number_100) as Button
        layoutCustomize = findViewById<View>(R.id.ll_customize) as ConstraintLayout
        tvCustomize = findViewById<View>(R.id.tv_customize) as TextView
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        imgEdit = findViewById<View>(R.id.img_edit) as ImageButton
        llAdjustNumberRounds = findViewById(R.id.llAdjustNumberRounds)
        tvCustomizeNew = findViewById(R.id.tvAdjustNumberRoundsCustomize)
        tvAdjustNumberRounds = findViewById(R.id.tvAdjustNumberRounds)
        if (preferenceHelper.getNumberOfRoundCustomize() != -1) {
            tvCustomize.text = preferenceHelper.getNumberOfRoundCustomize().toString()
        }

        tvTitle.isSelected = true

        setUpClickNumber()

        onclick()
        setUpPopupWindow()
        setCancelable(false)
    }


    interface OnPress {
        fun cancel()
        fun saveNumberOfRound(num: Int)
        fun showAgainDialog()
        fun load()
    }

    fun init(context: Context, onPress: OnPress?) {
        this.onPress = onPress
        this.context = context

        if (preferenceHelper.getNumberOfRoundCustomize() != -1) {
            tvCustomize.text = preferenceHelper.getNumberOfRoundCustomize().toString()
        }

        tvTitle.isSelected = true

        setUpClickNumber()
    }

    private fun setUpPopupWindow() {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.adjust_number_popup_window, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        this.popupWindow = (popupWindow)
        val tv33 = popupView.findViewById<TextView>(R.id.tvAdjustNumber33)
        val tv66 = popupView.findViewById<TextView>(R.id.tvAdjustNumber66)
        val tv99 = popupView.findViewById<TextView>(R.id.tvAdjustNumber99)
        val tvCustomize = popupView.findViewById<TextView>(R.id.tvAdjustNumberCustomize)
        tv33.setTextColor(this.context.getColor(R.color.color_2D3640))
        tv66.setTextColor(this.context.getColor(R.color.color_2D3640))
        tv99.setTextColor(this.context.getColor(R.color.color_2D3640))
        tvCustomize.setTextColor(this.context.getColor(R.color.color_2D3640))

        tv33.setOnClickListener {
            numSelected = 1
            tvCustomizeNew.isVisible = false
            tvAdjustNumberRounds.text = "33"
            this.popupWindow?.dismiss()
        }

        tv66.setOnClickListener {
            numSelected = 2
            tvCustomizeNew.isVisible = false
            tvAdjustNumberRounds.text = "66"
            this.popupWindow?.dismiss()
        }

        tv99.setOnClickListener {
            numSelected = 3
            tvCustomizeNew.isVisible = false
            tvAdjustNumberRounds.text = "99"
            this.popupWindow?.dismiss()
        }

        tvCustomize.setOnClickListener {
            numSelected = 4
            showDialogAdjustNumberOfRoundsCustomize()
            tvCustomizeNew.isVisible = true
            this.popupWindow?.dismiss()
        }

        when (preferenceHelper.getNumberOfRounds()) {
            33 -> {
                tv33.setTextColor(this.context.getColor(R.color.color_008040))
            }

            66 -> {
                tv66.setTextColor(this.context.getColor(R.color.color_008040))
            }

            99 -> {
                tv99.setTextColor(this.context.getColor(R.color.color_008040))
            }

            else -> {
                tvCustomize.setTextColor(this.context.getColor(R.color.color_008040))
            }
        }
    }

    private fun setUpClickNumber() {
        val listRound = listOf(33, 66, 99)
        tvCustomizeNew.isVisible = false
        tvAdjustNumberRounds.text = preferenceHelper.getNumberOfRounds().toString()
        tvCustomizeNew.isVisible = !listRound.contains(preferenceHelper.getNumberOfRounds())
    }

    private fun onclick() {

        llAdjustNumberRounds.setOnClickListener {
            popupWindow?.showAsDropDown(llAdjustNumberRounds)
        }

        btnDone.setOnClickListener {
            when (numSelected) {
                1 -> {
                    preferenceHelper.setNumberOfRounds(33)
                    onPress?.saveNumberOfRound(33)
                }

                2 -> {
                    preferenceHelper.setNumberOfRounds(66)
                    onPress?.saveNumberOfRound(66)
                }

                3 -> {
                    preferenceHelper.setNumberOfRounds(99)
                    onPress?.saveNumberOfRound(99)
                }

                4 -> {
                    if (preferenceHelper.getNumberOfRoundCustomize() == -1) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.string_please_customize_the_number_of_round),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        preferenceHelper.setNumberOfRounds(preferenceHelper.getNumberOfRoundCustomize())
                        onPress?.saveNumberOfRound(preferenceHelper.getNumberOfRoundCustomize())
                    }
                }
            }
        }

        btnNumberRound33.setOnClickListener {
            numSelected = 1
            btnNumberRound33.setBackgroundResource(R.drawable.bg_btn_number_of_round_selected)
            btnNumberRound99.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound100.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            layoutCustomize.setBackgroundResource(R.drawable.bg_btn_number_of_round)
        }

        btnNumberRound99.setOnClickListener {
            numSelected = 2
            btnNumberRound33.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound99.setBackgroundResource(R.drawable.bg_btn_number_of_round_selected)
            btnNumberRound100.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            layoutCustomize.setBackgroundResource(R.drawable.bg_btn_number_of_round)
        }

        btnNumberRound100.setOnClickListener {
            numSelected = 3
            btnNumberRound33.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound99.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound100.setBackgroundResource(R.drawable.bg_btn_number_of_round_selected)
            layoutCustomize.setBackgroundResource(R.drawable.bg_btn_number_of_round)
        }

        layoutCustomize.setOnClickListener {
            numSelected = 4
            btnNumberRound33.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound99.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            btnNumberRound100.setBackgroundResource(R.drawable.bg_btn_number_of_round)
            layoutCustomize.setBackgroundResource(R.drawable.bg_btn_number_of_round_selected)
        }

        imgEdit.setOnClickListener {
            showDialogAdjustNumberOfRoundsCustomize()
        }

        btnCancel.setOnClickListener { onPress?.cancel() }
    }

    private fun showDialogAdjustNumberOfRoundsCustomize() {
        onPress?.cancel()
        val dialogCustomizeNumberOfRounds = DialogCustomizeNumberOfRounds(context)
        dialogCustomizeNumberOfRounds.init(
            context,

            object : DialogCustomizeNumberOfRounds.OnPress {
                override fun cancel() {
                    dialogCustomizeNumberOfRounds.dismiss()
                    onPress?.showAgainDialog()
                }

                override fun saveNumberOfRoundCustomize(num: Int) {
                    tvAdjustNumberRounds.text = num.toString()
                    tvCustomizeNew.isVisible = true
                    if (preferenceHelper.getNumberOfRounds() != 33 && preferenceHelper.getNumberOfRounds() != 99 && preferenceHelper.getNumberOfRounds() != 100) {
                        preferenceHelper.setNumberOfRounds(num)
                        preferenceHelper.setNumberOfRoundCustomize(num)
                        onPress?.load()
                    } else {
                        preferenceHelper.setNumberOfRounds(num)
                        preferenceHelper.setNumberOfRoundCustomize(num)
                    }
                    dialogCustomizeNumberOfRounds.dismiss()
                    onPress?.showAgainDialog()
                }

            })

        dialogCustomizeNumberOfRounds.show()
    }

}
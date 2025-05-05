package com.qibla.muslimday.app2025.ui.qibla

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.qibla.muslimday.app2025.databinding.RadarNotSupportDlgBinding
import com.qibla.muslimday.app2025.util.SystemUtil

class RadarDlg : DialogFragment() {

    private lateinit var binding: RadarNotSupportDlgBinding

    var action: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RadarNotSupportDlgBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext()) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCancelable(false)
            val window = this.window
            val decorView = window?.decorView
            val uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView?.systemUiVisibility = uiOptions
            window?.let { w ->
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val wlp = w.attributes
                wlp.width = LinearLayout.LayoutParams.MATCH_PARENT
                wlp.height = LinearLayout.LayoutParams.MATCH_PARENT
            }
        }
        return dialog
    }

    override fun onAttach(context: Context) {
        SystemUtil.setLanguage(context)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRadarCancel.setOnClickListener {
            action.invoke()
            dismiss()
        }

        binding.tvRadarDone.setOnClickListener {
            action.invoke()
            dismiss()
        }
    }


}
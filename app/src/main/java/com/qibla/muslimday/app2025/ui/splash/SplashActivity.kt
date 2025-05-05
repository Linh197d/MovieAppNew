package com.qibla.muslimday.app2025.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivitySplashBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private var progress = 0
    private val handler = Handler()

    override fun setBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Thread {
            while (progress <= 98) {
                Thread.sleep(50) // Tăng tiến trình mỗi 50ms
                progress++
                handler.post {
                    binding.progressBar.layoutDirection =
                        View.LAYOUT_DIRECTION_LTR // Đảm bảo chạy từ trái sang phải
                    binding.progressBar.progress = progress
                    binding.tvProgress.text =
                        getString(R.string.loading_splash) + " " + progress + "%..."
                }
            }
        }.start()

        preferenceHelper.setBoolean(PreferenceHelper.IS_SHOW_DIALOG_TARAWIH, false)

    }







    private fun isTimeAutomatic(c: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.Global.getInt(c.contentResolver, Settings.Global.AUTO_TIME, 0) == 1
        } else {
            Settings.System.getInt(c.contentResolver, Settings.System.AUTO_TIME, 0) == 1
        }
    }


}
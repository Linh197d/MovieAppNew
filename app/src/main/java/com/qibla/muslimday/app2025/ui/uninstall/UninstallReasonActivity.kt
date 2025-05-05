package com.qibla.muslimday.app2025.ui.uninstall

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityUninstallReasonBinding


class UninstallReasonActivity :
    BaseActivity<ActivityUninstallReasonBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityUninstallReasonBinding {
        return ActivityUninstallReasonBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnCancel.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
        binding.btnUninstall.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(Uri.parse("package:$packageName"))
            startActivity(intent)
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

}
package com.qibla.muslimday.app2025.ui.uninstall

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityUninstallBinding
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UninstallActivity : BaseActivity<ActivityUninstallBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityUninstallBinding {
        return ActivityUninstallBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.btnStart2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.btnDontUninstall.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.rlNative.isVisible = Admob.getInstance().isLoadFullAds
        loadNativeUninstall()
        binding.tvStillUninstall.setOnClickListener {
            AdsInter.loadInter(
                context = this@UninstallActivity,
                adsId = getString(R.string.inter_uninstall),
                isShow = AdsInter.isLoadInterUninstall && Admob.getInstance().isLoadFullAds,
                action = {
                    startActivity(
                        Intent(
                            this@UninstallActivity,
                            UninstallReasonActivity::class.java
                        )
                    )
                }
            )
        }
    }

    private fun loadNativeUninstall() {
        if (Admob.getInstance().isLoadFullAds && AdsInter.isLoadNativeUninstall) {
            Admob.getInstance().loadNativeAd(
                this@UninstallActivity,
                getString(R.string.native_uninstall),
                object : NativeCallback() {
                    override fun onNativeAdLoaded(nativeAd: NativeAd) {
                        val adView =
                            LayoutInflater.from(this@UninstallActivity)
                                .inflate(R.layout.layout_ads_native_update, null) as NativeAdView
                        binding.frNativeAds.removeAllViews()
                        binding.frNativeAds.addView(adView)
                        Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
                    }

                    override fun onAdFailedToLoad() {
                        binding.frNativeAds.removeAllViews()
                    }

                    override fun onAdClick() {
                        super.onAdClick()
                        loadNativeUninstall()
                    }
                }
            )
        } else
            binding.frNativeAds.removeAllViews()
    }

}
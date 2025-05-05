package com.qibla.muslimday.app2025.ui.settings.prayingTimeAdjustment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityPrayingTimeAdjustmentBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.view.DialogPrayingTimeAdjustment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrayingTimeAdjustment : BaseActivity<ActivityPrayingTimeAdjustmentBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private var prayingTimeAdjustmentAll = 0
    private var prayingTimeAdjustmentFajr = 0
    private var prayingTimeAdjustmentSunrise = 0
    private var prayingTimeAdjustmentDhuhr = 0
    private var prayingTimeAdjustmentAsr = 0
    private var prayingTimeAdjustmentMaghrib = 0
    private var prayingTimeAdjustmentIsha = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityPrayingTimeAdjustmentBinding {
        return ActivityPrayingTimeAdjustmentBinding.inflate(layoutInflater)
    }

    override fun initView() {

        setData()
        AdsInter.onAdsClick = {
            loadAdsNativePrayingTime()
        }
        if (ConsentHelper.getInstance(this).canRequestAds())
            loadAdsNativePrayingTime()

        binding.layoutTimeAll.setOnClickListener {
            showDialogPrayingTimeAdjustment("All")
        }

        binding.layoutFajrTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Fajr")
        }

        binding.layoutSunriseTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Sunrise")
        }

        binding.layoutDhuhrTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Dhuhr")
        }

        binding.layoutAsrTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Asr")
        }

        binding.layoutMaghribTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Maghrib")
        }

        binding.layoutIshaTime.setOnClickListener {
            showDialogPrayingTimeAdjustment("Isha")
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

//        if (isLoadNative) {
//            isLoadNative = false
//
//            if (ConsentHelper.getInstance(this).canRequestAds())
//                loadAdsNativePrayingTime()
//        }
    }

    private fun setData() {
        prayingTimeAdjustmentAll = preferenceHelper.getPrayingTimeAdjustmentAll()
        prayingTimeAdjustmentFajr = preferenceHelper.getPrayingTimeAdjustmentFajr()
        prayingTimeAdjustmentSunrise = preferenceHelper.getPrayingTimeAdjustmentSunrise()
        prayingTimeAdjustmentDhuhr = preferenceHelper.getPrayingTimeAdjustmentDhuhr()
        prayingTimeAdjustmentAsr = preferenceHelper.getPrayingTimeAdjustmentAsr()
        prayingTimeAdjustmentMaghrib = preferenceHelper.getPrayingTimeAdjustmentMaghrib()
        prayingTimeAdjustmentIsha = preferenceHelper.getPrayingTimeAdjustmentIsha()

        if (prayingTimeAdjustmentAll < 0) {
            binding.tvTimeAll.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentAll.toString()
            )
        } else if (prayingTimeAdjustmentAll == 0) {
            binding.tvTimeAll.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvTimeAll.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentAll.toString()
            )
        }


        if (prayingTimeAdjustmentFajr < 0) {
            binding.tvFajrTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentFajr.toString()
            )
        } else if (prayingTimeAdjustmentFajr == 0) {
            binding.tvFajrTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvFajrTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentFajr.toString()
            )
        }

        if (prayingTimeAdjustmentSunrise < 0) {
            binding.tvSunriseTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentSunrise.toString()
            )
        } else if (prayingTimeAdjustmentSunrise == 0) {
            binding.tvSunriseTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvSunriseTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentSunrise.toString()
            )
        }

        if (prayingTimeAdjustmentDhuhr < 0) {
            binding.tvDhuhrTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentDhuhr.toString()
            )
        } else if (prayingTimeAdjustmentDhuhr == 0) {
            binding.tvDhuhrTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvDhuhrTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentDhuhr.toString()
            )
        }

        if (prayingTimeAdjustmentAsr < 0) {
            binding.tvAsrTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentAsr.toString()
            )
        } else if (prayingTimeAdjustmentAsr == 0) {
            binding.tvAsrTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvAsrTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentAsr.toString()
            )
        }

        if (prayingTimeAdjustmentMaghrib < 0) {
            binding.tvMaghribTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentMaghrib.toString()
            )
        } else if (prayingTimeAdjustmentMaghrib == 0) {
            binding.tvMaghribTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvMaghribTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentMaghrib.toString()
            )
        }

        if (prayingTimeAdjustmentIsha < 0) {
            binding.tvIshaTime.text = getString(
                R.string.string_praying_time_adjustment,
                prayingTimeAdjustmentIsha.toString()
            )
        } else if (prayingTimeAdjustmentIsha == 0) {
            binding.tvIshaTime.text = getString(
                R.string.string_not_adjusted
            )
        } else {
            binding.tvIshaTime.text = getString(
                R.string.string_plus_praying_time_adjustment,
                prayingTimeAdjustmentIsha.toString()
            )
        }

    }

    private fun showDialogPrayingTimeAdjustment(praying: String) {
        val prayingTimeAdjustmentDialog = DialogPrayingTimeAdjustment(this)
        binding.frNativeAds.visibility = View.GONE
        prayingTimeAdjustmentDialog.init(
            this,
            praying,
            preferenceHelper,
            object : DialogPrayingTimeAdjustment.OnPress {
                override fun cancel() {
                    prayingTimeAdjustmentDialog.dismiss()
                    binding.frNativeAds.visibility = View.VISIBLE
                }

                override fun saveTime(time: Int) {
                    when (praying) {
                        "All" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(time)
                            preferenceHelper.setPrayingTimeAdjustmentFajr(time)
                            preferenceHelper.setPrayingTimeAdjustmentSunrise(time)
                            preferenceHelper.setPrayingTimeAdjustmentDhuhr(time)
                            preferenceHelper.setPrayingTimeAdjustmentAsr(time)
                            preferenceHelper.setPrayingTimeAdjustmentMaghrib(time)
                            preferenceHelper.setPrayingTimeAdjustmentIsha(time)
                        }

                        "Fajr" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentFajr(time)
                        }

                        "Sunrise" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentSunrise(time)
                        }

                        "Dhuhr" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentDhuhr(time)
                        }

                        "Asr" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentAsr(time)
                        }

                        "Maghrib" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentMaghrib(time)
                        }

                        "Isha" -> {
                            preferenceHelper.setPrayingTimeAdjustmentAll(0)
                            preferenceHelper.setPrayingTimeAdjustmentIsha(time)
                        }
                    }
                    prayingTimeAdjustmentDialog.dismiss()

                    binding.frNativeAds.visibility = View.VISIBLE
                    setData()
                }
            })

        try {
            prayingTimeAdjustmentDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    private fun loadAdsNativePrayingTime() {

        Log.d("ntt", "loadAdsNativePrayingTime: ${AdsInter.nativeAdsAll}")
        AdsInter.pushNativeAll(
            context = this@PrayingTimeAdjustment,
            view = binding.frNativeAds,
            keyCheck = AdsInter.isLoadNativePrayingtime,
            scope = lifecycleScope
        )

    }

    private fun pushAdsToView(nativeAd: NativeAd) {
        val adView = LayoutInflater.from(this@PrayingTimeAdjustment)
            .inflate(R.layout.layout_ads_native_update, null)
        val nativeAdView = adView as NativeAdView
        binding.frNativeAds.removeAllViews()
        binding.frNativeAds.addView(adView)

        Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)

//        lifecycleScope.launch {
//            AdsInter.nativeAdsAll = null
//        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PrayingTimeAdjustment::class.java)
        }
    }
}
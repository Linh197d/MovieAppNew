package com.qibla.muslimday.app2025.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.DialogExitNewBinding
import com.qibla.muslimday.app2025.util.AdsInter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExitDialogNew(context: Context) : Dialog(context) {
    private var onPress: OnPress? = null

    private var coroutineScope: CoroutineScope? = null

    @Override
    override fun onStart() {
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.setSoftInputMode(16)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.attributes = attributes
        setCancelable(false)
        super.onStart()
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogExitNewBinding.inflate(layoutInflater)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        registerEvent(binding)
    }

    private fun registerEvent(binding: DialogExitNewBinding) {

        binding.btnYes.setOnClickListener { onPress!!.yes() }
        binding.btnNo.setOnClickListener { onPress!!.no() }

        coroutineScope?.launch {
            if (Admob.getInstance().isLoadFullAds && ConsentHelper.getInstance(context)
                    .canRequestAds() && AdsInter.isLoadNativeExit
            ) {
                Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                    context,
                    context.getString(R.string.native_exit),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            val adView1 = LayoutInflater.from(context)
                                .inflate(
                                    R.layout.layout_ads_native_update,
                                    null
                                ) as NativeAdView

                            binding.frNativeAds.removeAllViews()
                            binding.frNativeAds.addView(adView1)

                            Admob.getInstance().pushAdsToViewCustom(nativeAd, adView1)

                        }

                        override fun onAdFailedToLoad() {
                            binding.frNativeAds.removeAllViews()
                        }

                    })

            } else {
                binding.frNativeAds.removeAllViews()
                binding.frNativeAds.visibility = View.GONE
            }
        }
    }


    fun init(onPress: OnPress?) {
        this.onPress = onPress
    }

    interface OnPress {
        fun yes()
        fun no()

    }
}
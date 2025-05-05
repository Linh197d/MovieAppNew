package com.qibla.muslimday.app2025.ui.intro.tab

import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.databinding.FieldNativeFullFragmentBinding
import com.qibla.muslimday.app2025.ui.intro.IntroViewModel
import com.qibla.muslimday.app2025.ui.salah.SalahFragment.hasNetworkConnection
import com.qibla.muslimday.app2025.util.AdsInter

class FieldNativeIntroFullFragmentIntro : BaseFragment<FieldNativeFullFragmentBinding>() {

    companion object {

        private const val VALUE_INTRO_KEY = "VALUE_INTRO_KEY"

        fun getInstance(value: Int): FieldNativeIntroFullFragmentIntro {
            val fra = FieldNativeIntroFullFragmentIntro()
            fra.arguments = bundleOf(VALUE_INTRO_KEY to value)
            return fra
        }
    }

    private var type = FIELD_NATIVE_FULL.INTRO_FULL_2

    private val viewModel by activityViewModels<IntroViewModel>()

    private var isLoadingIntro = false


    override fun getViewBinding(): FieldNativeFullFragmentBinding {
        return FieldNativeFullFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        type = FIELD_NATIVE_FULL.getValueType(arguments?.getInt(VALUE_INTRO_KEY))
        binding.root.setOnClickListener {
            viewModel.setPosition(viewModel.currentItem + 1)
        }

        loadAds()
    }

    private fun safeLoadAds(): Boolean {
        val isLoadByKey = when (type) {
            FIELD_NATIVE_FULL.INTRO_FULL_2 -> {
                AdsInter.isLoadNativeFullIntro2 && Admob.getInstance().isLoadFullAds
            }

            FIELD_NATIVE_FULL.INTRO_FULL_3 -> {
                AdsInter.isLoadNativeFullIntro3 && Admob.getInstance().isLoadFullAds
            }
        }

        return isLoadByKey && hasNetworkConnection(requireContext()) && ConsentHelper.getInstance(
            requireContext()
        ).canRequestAds()
    }

    private fun loadAds() {
        if (safeLoadAds()) {
            val adsId = if (type == FIELD_NATIVE_FULL.INTRO_FULL_2) {
                getString(R.string.native_full_intro2)
            } else {
                getString(R.string.native_full_intro3)
            }
            if (isLoadingIntro) return
            binding.flFieldIntroAds.visibility = View.VISIBLE
            isLoadingIntro = true
            Admob.getInstance().loadNativeAdFullScreen(
                requireActivity(),
                adsId,
                NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_ANY,
                object : NativeCallback() {
                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                        if (!isAdded) return
                        isLoadingIntro = false
                        val adView = LayoutInflater.from(requireActivity())
                            .inflate(R.layout.custom_native_full, null) as NativeAdView
                        binding.flFieldIntroAds.removeAllViews()
                        binding.flFieldIntroAds.addView(adView)
                        Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
                    }

                    override fun onAdClick() {
                        loadAds()
                    }

                    override fun onAdFailedToLoad() {
                        isLoadingIntro = false
                        binding.flFieldIntroAds.removeAllViews()
                        viewModel.setPosition(viewModel.currentItem + 1)
                    }
                }
            )
        } else {
            binding.flFieldIntroAds.removeAllViews()
            viewModel.setPosition(viewModel.currentItem + 1)
        }
    }
}
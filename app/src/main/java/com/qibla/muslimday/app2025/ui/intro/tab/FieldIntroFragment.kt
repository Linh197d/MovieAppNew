package com.qibla.muslimday.app2025.ui.intro.tab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.databinding.FieldIntroFragmentBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.intro.GuideModel
import com.qibla.muslimday.app2025.ui.intro.IntroViewModel
import com.qibla.muslimday.app2025.ui.permission.PermissionActivity
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FieldIntroFragment : BaseFragment<FieldIntroFragmentBinding>() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    companion object {

        private const val VALUE_INTRO_KEY = "VALUE_INTRO_KEY"

        fun getInstance(value: Int): FieldIntroFragment {
            val fra = FieldIntroFragment()
            fra.arguments = bundleOf(VALUE_INTRO_KEY to value)
            return fra
        }
    }

    private val viewModel by activityViewModels<IntroViewModel>()
    private var type = FIELD_INTRO_NAVIATE_TYPE.INTRO1
    private var isLoadingIntro = false
    private val introAdList = mutableListOf<String>()

    override fun getViewBinding(): FieldIntroFragmentBinding {
        return FieldIntroFragmentBinding.inflate(layoutInflater)
    }

    override fun initView()
    {
        type = FIELD_INTRO_NAVIATE_TYPE.getValueType(arguments?.getInt(VALUE_INTRO_KEY))
        println("type = $type")
        introAdList.clear()
        introAdList.add(getString(R.string.native_intro1))
        introAdList.add(getString(R.string.native_intro2))
        introAdList.add(getString(R.string.native_intro3))
        introAdList.add(getString(R.string.native_intro4))
        loadAds()
        setUpDot()

        binding.tvTitle.text = getData()[type.value].title
        binding.tvContent.text = getData()[type.value].content
        binding.imgGuide.setImageResource(getData()[type.value].img)

        binding.lottieView.isVisible =
            (type == FIELD_INTRO_NAVIATE_TYPE.INTRO2 || type == FIELD_INTRO_NAVIATE_TYPE.INTRO3) && Admob.getInstance().isLoadFullAds

        binding.tvFieldIntroNext.setOnClickListener {
            if (viewModel.currentItem == viewModel.maxSize) {
                loadAndShowInterIntro()
            } else {
                viewModel.setPosition(viewModel.currentItem + 1)
            }
        }
    }

    private fun loadAds() {
        if (safeLoadAds()) {
            if (isLoadingIntro) return
            isLoadingIntro = true
            binding.flFieldIntroAds.visibility = View.VISIBLE
            Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                requireActivity(),
                introAdList[type.value],
                object : NativeCallback() {
                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                        if (!isAdded) return
                        isLoadingIntro = false
                        val adView = if (Admob.getInstance().isLoadFullAds) {
                            LayoutInflater.from(requireActivity())
                                .inflate(R.layout.layout_native_language, null) as NativeAdView
                        } else {
                            LayoutInflater.from(requireActivity())
                                .inflate(R.layout.custom_native_ad, null) as NativeAdView
                        }
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
                    }
                })
        } else {
            binding.flFieldIntroAds.removeAllViews()
        }
    }

    private fun setUpDot() {
        when (type) {
            FIELD_INTRO_NAVIATE_TYPE.INTRO1 -> {
                binding.view1.setImageResource(R.drawable.ic_view_select)
                binding.view2.setImageResource(R.drawable.ic_view_un_select)
                binding.view3.setImageResource(R.drawable.ic_view_un_select)
                binding.view4.setImageResource(R.drawable.ic_view_un_select)
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO2 -> {
                binding.view1.setImageResource(R.drawable.ic_view_un_select)
                binding.view2.setImageResource(R.drawable.ic_view_select)
                binding.view3.setImageResource(R.drawable.ic_view_un_select)
                binding.view4.setImageResource(R.drawable.ic_view_un_select)
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO3 -> {
                binding.view1.setImageResource(R.drawable.ic_view_un_select)
                binding.view2.setImageResource(R.drawable.ic_view_un_select)
                binding.view3.setImageResource(R.drawable.ic_view_select)
                binding.view4.setImageResource(R.drawable.ic_view_un_select)
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO4 -> {
                binding.view1.setImageResource(R.drawable.ic_view_un_select)
                binding.view2.setImageResource(R.drawable.ic_view_un_select)
                binding.view3.setImageResource(R.drawable.ic_view_un_select)
                binding.view4.setImageResource(R.drawable.ic_view_select)
            }
        }
    }

    private fun safeLoadAds(): Boolean {
        val isLoadByKey = when (type) {
            FIELD_INTRO_NAVIATE_TYPE.INTRO1 -> {
                AdsInter.isLoadNativeIntro
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO2 -> {
                false
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO3 -> {
                AdsInter.isLoadNativeIntro3 && !Admob.getInstance().isLoadFullAds
            }

            FIELD_INTRO_NAVIATE_TYPE.INTRO4 -> {
                AdsInter.isLoadNativeIntro4 && Admob.getInstance().isLoadFullAds
            }
        }

        return isLoadByKey && requireActivity().hasNetworkConnection() && ConsentHelper.getInstance(
            requireContext()
        ).canRequestAds()
    }

    private fun getData(): ArrayList<GuideModel> {
        val mHelpGuide: ArrayList<GuideModel> = ArrayList()
        mHelpGuide.add(
            GuideModel(
                R.drawable.bg_intro_1,
                getString(R.string.title_intro_1),
                getString(R.string.content_intro_1)
            )
        )
        mHelpGuide.add(
            GuideModel(
                if (Admob.getInstance().isLoadFullAds) R.drawable.bg_intro_2_full else R.drawable.bg_intro_2,
                getString(R.string.title_intro_2),
                getString(R.string.content_intro_2)
            )
        )
        mHelpGuide.add(
            GuideModel(
                R.drawable.bg_intro_3,
                getString(R.string.title_intro_3),
                getString(R.string.content_intro_3)
            )
        )
        mHelpGuide.add(
            GuideModel(
                if (Admob.getInstance().isLoadFullAds) R.drawable.bg_intro_4 else R.drawable.bg_intro_4_normal,
                getString(R.string.title_intro_4),
                getString(R.string.content_intro_4)
            )
        )
        return mHelpGuide
    }

    private fun loadAndShowInterIntro() {
        AdsInter.loadInter(
            context = requireActivity(),
            adsId = getString(R.string.inter_intro),
            isShow = AdsInter.isLoadInterIntro && Admob.getInstance().isLoadFullAds,
            action = {
                gotoNextScreen()
            }
        )
    }

    private fun checkSystemAlertWindowPermission(): Boolean {
        return Settings.canDrawOverlays(requireContext())
    }

    private fun checkPermissionLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionNotification(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun gotoNextScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (preferenceHelper.showPermission() || (!checkPermissionLocation() || !checkSystemAlertWindowPermission() || !checkPermissionNotification())
            ) {
                startActivity(PermissionActivity.newIntent(requireContext()))
            } else if (!preferenceHelper.showPermission() && checkPermissionLocation() && checkSystemAlertWindowPermission() && checkPermissionNotification()
            ) {
                startActivity(MainActivity.newIntent(requireContext()))
            }

            requireActivity().finish()
        } else {
            if (preferenceHelper.showPermission() || (!checkPermissionLocation() || !checkSystemAlertWindowPermission())
            ) {
                startActivity(PermissionActivity.newIntent(requireContext()))
            } else if (!preferenceHelper.showPermission() && checkPermissionLocation() && checkSystemAlertWindowPermission()
            ) {
                startActivity(MainActivity.newIntent(requireContext()))
            }
        }
    }

}
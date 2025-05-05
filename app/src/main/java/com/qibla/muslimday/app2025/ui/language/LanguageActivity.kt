package com.qibla.muslimday.app2025.ui.language

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityLanguageBinding
import com.qibla.muslimday.app2025.extensions.setLocale
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.intro.IntroActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.SystemUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var languageAdapter: LanguageAdapter

    val languages = mutableListOf<LanguageModel>()

    var fromSplash = false
    override fun setBinding(layoutInflater: LayoutInflater): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initData()
        setupListLanguage()
        initEvent()
        setListeners()
        AdsInter.onAdsClick = {
            loadAdsNativeLanguageSetting()
        }
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        languages.add(LanguageModel("Español", "es"))
        languages.add(LanguageModel("中文（简体)", "zh-rCN"))
        languages.add(LanguageModel("中文（繁體）", "zh-rTW"))
        languages.add(LanguageModel("English", "en"))
        languages.add(LanguageModel("Français", "fr"))
        languages.add(LanguageModel("हिंदी भाषा", "hi"))
        languages.add(LanguageModel("Indonesian", "in"))
        languages.add(LanguageModel("Português (Portugal)", "pt"))
        languages.add(LanguageModel("Português (Brasil)", "pt-rBR"))
        languages.add(LanguageModel("عربي", "ar"))
        languages.add(LanguageModel("বাংলা", "bn"))
        languages.add(LanguageModel("Русский", "ru"))
        languages.add(LanguageModel("Deutsch", "de"))
        languages.add(LanguageModel("日本語", "ja"))
        languages.add(LanguageModel("Turkish", "tr"))
        languages.add(LanguageModel("한국인", "ko"))
    }

    private fun initListLanguage(selectedLanguage: String) {

        if (!fromSplash) {
            if (languages.any { it.code == selectedLanguage }) {
                languages.first { it.code == selectedLanguage }.active = true
            }
//            else {
//                languages.first { it.code == "en" }.active = true
//            }
        }

//        if (languages.any { it.code == selectedLanguage }) {
//            languages.first { it.code == selectedLanguage }.active = true
//        }
//        else {
//            languages.first { it.code == "en" }.active = true
//        }

        binding.lvLanguage.adapter = languageAdapter
        languageAdapter.submitList(languages)

        fromSplash = intent.getBooleanExtra(FROM_SPLASH, false)

        if (fromSplash) {
            languageAdapter.setOnClickListener {
                Log.d("ntt", "onClickItemLanguage: ")
                if (AdsInter.isLoadNativeLanguageSelect) {
                    loadAdsNativeLanguageSelect()
                }
            }
        }
    }

    private fun setupListLanguage() {
        fromSplash = intent.getBooleanExtra(FROM_SPLASH, false)
        if (fromSplash) {
            binding.btnBack.visibility = View.GONE
            val locale = Locale.getDefault()
            initListLanguage(locale.language)
            if (ConsentHelper.getInstance(this).canRequestAds())
                loadAdsNativeLanguageFirst()

        } else {
            binding.btnBack.visibility = View.VISIBLE
            val selectedLanguage =
                preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)
            initListLanguage(selectedLanguage ?: "")
            if (ConsentHelper.getInstance(this).canRequestAds()) {
                loadAdsNativeLanguageSetting()

            }

        }
    }

    private fun initEvent() {
        binding.btnDone.setOnClickListener {
            val selectedLanguage = languageAdapter.getSelectedLanguage()
            if (selectedLanguage == null) {
                Toast.makeText(
                    this,
                    getString(R.string.string_please_select_language),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                setLocale(selectedLanguage.code)
                preferenceHelper.setString(
                    PreferenceHelper.PREF_CURRENT_LANGUAGE,
                    selectedLanguage.code
                )
//                SystemUtil.setLanguage(this)
                SystemUtil.setPreLanguage(this, selectedLanguage.code)
                if (fromSplash) {
//                    preferenceHelper.setBoolean(PREF_SHOWED_START_LANGUAGE, true)
                    val intent = Intent(this, IntroActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                } else {
                    if (Admob.getInstance().isLoadFullAds) {
                        val intent = Intent(this, IntroActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        val intent = MainActivity.newIntent(this)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finishAffinity()
                    }
                }
            }
        }
    }

    private fun loadAdsNativeLanguageFirst() {
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                if (AdsInter.isLoadNativeLanguage) {
                    Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                        this@LanguageActivity,
                        getString(R.string.native_language),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                try {
//                                val inflater = LayoutInflater.from(this@LanguageActivity)
//                                val adView: View =
//                                    inflater.inflate(R.layout.custom_native_ad, null)

                                    val adView = if (Admob.getInstance().isLoadFullAds) {
                                        LayoutInflater.from(this@LanguageActivity)
                                            .inflate(
                                                R.layout.layout_native_language,
                                                null
                                            ) as NativeAdView
                                    } else {
                                        LayoutInflater.from(this@LanguageActivity)
                                            .inflate(
                                                R.layout.custom_native_ad,
                                                null
                                            ) as NativeAdView
                                    }

                                    binding.frAds.removeAllViews()
                                    binding.frAds.addView(adView)
                                    Admob.getInstance()
                                        .pushAdsToViewCustom(nativeAd, adView as NativeAdView)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    )
                } else {
                    binding.frAds.removeAllViews()
                    binding.frAds.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadAdsNativeLanguageSetting() {
        println("checkKey = ${AdsInter.isLoadNativeLanguage3}")
        AdsInter.pushNativeAll(
            context = this@LanguageActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeLanguage3,
            scope = lifecycleScope
        )
    }

    companion object {
        const val FROM_SPLASH = "from_splash"
        fun newIntent(context: Context, fromSplash: Boolean = false): Intent {
            val intent = Intent(context, LanguageActivity::class.java)
            intent.putExtra(FROM_SPLASH, fromSplash)
            if (fromSplash) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            return intent
        }

    }

    private fun loadAdsNativeLanguageSelect() {
        lifecycleScope.launch {
            if (AdsInter.nativeAdsLanguageSelect != null) {
                Log.d(
                    "ntt",
                    "Before loadAdsNativeLangInSplash: ${AdsInter.nativeAdsLanguageSelect}"
                )

                val adView = if (Admob.getInstance().isLoadFullAds) {
                    LayoutInflater.from(this@LanguageActivity)
                        .inflate(R.layout.layout_native_language, null) as NativeAdView
                } else {
                    LayoutInflater.from(this@LanguageActivity)
                        .inflate(R.layout.custom_native_ad, null) as NativeAdView
                }
//            val adView = LayoutInflater.from(this@LanguageActivity)
//                .inflate(R.layout.custom_native_ad, null)
                val nativeAdView = adView as NativeAdView
                binding.frAds.removeAllViews()
                binding.frAds.addView(adView)

                Admob.getInstance()
                    .pushAdsToViewCustom(AdsInter.nativeAdsLanguageSelect, nativeAdView)
                Log.d("ntt", "After loadAdsNativeLangInSplash: ")

            } else {
                Admob.getInstance().loadNativeAd(
                    this@LanguageActivity,
                    getString(R.string.native_language_select),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
//                        val adView = LayoutInflater.from(this@LanguageActivity)
//                            .inflate(R.layout.custom_native_ad, null)

                            val adView = if (Admob.getInstance().isLoadFullAds) {
                                LayoutInflater.from(this@LanguageActivity)
                                    .inflate(R.layout.layout_native_language, null) as NativeAdView
                            } else {
                                LayoutInflater.from(this@LanguageActivity)
                                    .inflate(R.layout.custom_native_ad, null) as NativeAdView
                            }

                            val nativeAdView = adView as NativeAdView
                            binding.frAds.removeAllViews()
                            binding.frAds.addView(adView)

                            Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)

                        }

                        override fun onAdClick() {
                            AdsInter.nativeAdsLanguageSelect = null
                            loadAdsNativeLanguageSelect()
                        }

                        override fun onAdFailedToLoad() {
                            binding.frAds.removeAllViews()
                        }

                    }
                )
            }
        }


//        } else {
//            binding.frAds.removeAllViews()
//            binding.frAds.visibility = View.GONE
//        }
    }


}
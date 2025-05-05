package com.qibla.muslimday.app2025.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nlbn.ads.banner.BannerPlugin
import com.nlbn.ads.callback.AdCallback
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.ui.salah.SalahFragment.hasNetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AdsInter : CoroutineScope {
    companion object {
        var isShowRate = false
        var onAdsClick: ((String) -> Unit)? = null
        var onAdsDataClick: ((String) -> Unit)? = null
        var historyInterQibla: Long = 0
        var historyInterDetail: Long = 0
        var historyInterTarawih: Long = 0
        var historyInterGreetings: Long = 0
        const val PAUSE_INTER_TIME: Long = 15
        const val CLICK_NATIVE_ALL: String = "click_native_all"
        const val CLICK_NATIVE_DATA1: String = "click_native_data1"
        const val CLICK_NATIVE_DATA2: String = "click_native_data2"
        const val CLICK_NATIVE_HOME: String = "click_native_home"

        var isLoadNativeLanguage = true
        var isLoadNativeLanguage3 = true
        var isLoadNativeLanguageSelect = true
        var isLoadNativeIntro = true
        var isLoadNativeIntro2 = true
        var isLoadNativeIntro3 = true
        var isLoadNativeIntro4 = true
        var isLoadInterIntro = true
        var isLoadNativePermissionOpen = true
        var isLoadBannerAll = true
        var isLoadInterQibla = true
        var isLoadNativePrayingtime = true
        var isLoadNativeHome = true
        var isLoadNativePermission = true
        var isLoadNativePermissionLocation = true
        var isLoadNativePermissionAppear = true
        var isLoadNativePermissionNotification = true


        var isLoadNativeExit = true
        var isLoadInterHome = true
        var isLoadInterTab = true
        var isLoadInterBack = true
        var isLoadInterSplash = true
        var isLoadInterDetail = true
        var isLoadNativeBookmark = true
        var isLoadNativeQibla = true
        var isLoadNativeTasbih = true
        var isLoadInterQuran = true


        var isLoadNativeData1 = true
        var isLoadNativeData2 = true
        var isLoadNativeDetail = true
        var isLoadNativeAllah = true
        var isLoadNativeZakat = true
        var isLoadNativePrayerTime = true
        var isLoadNativeMosque = true
        var isLoadNativeUninstall = true
        var isLoadNativeFullAll = true
        var isLoadNativeFullSplash = true
        var isLoadNativeFullIntro2 = true
        var isLoadNativeFullIntro3 = true
        var isLoadInterSplashFromUninstall = true
        var isLoadInterUninstall = true


        var nativeAdsHome: NativeAd? = null
        var nativeAdsData1: NativeAd? = null
        var nativeAdsData2: NativeAd? = null
        var nativeAdsPermission: NativeAd? = null
        var nativeAdsAll: NativeAd? = null

        var nativeAdsPermissionAppear: NativeAd? = null
        var nativeAdsPermissionNotification: NativeAd? = null
        var nativeAdsPermissionLocation: NativeAd? = null

        var nativeAdsLanguageSelect: NativeAd? = null

        private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)


        fun loadNativePermissionAppear(context: Context) {
            if (nativeAdsPermissionAppear == null) {
                Admob.getInstance().loadNativeAd(
                    context,
                    context.getString(R.string.native_permission_appear),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            nativeAdsPermissionAppear = nativeAd
                        }

                        override fun onAdFailedToLoad() {
                            super.onAdFailedToLoad()

                        }

                        override fun onAdClick() {
                            nativeAdsPermissionAppear = null
                            loadNativePermissionAppear(context)
                        }

                    }
                )
            }
        }

        fun loadNativeData1(context: Activity) {
            coroutineScope.launch {
                if (isLoadNativeData1) {
                    Admob.getInstance()
                        .loadNativeAd(
                            context,
                            context.getString(R.string.native_data1),
                            object : NativeCallback() {
                                override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                    AdsInter.nativeAdsData1 = nativeAd
                                }

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    nativeAdsData1 = null
                                    Log.d("onAdsClick", "tttt onAdImpression")
                                }

                                override fun onAdClick() {
                                    onAdsDataClick?.invoke(CLICK_NATIVE_DATA1)
                                    Log.d("onAdsClick", "tttt onAdsClick")
                                }
                            })
                }
            }
        }

        fun loadNativeData2(context: Activity) {
            coroutineScope.launch {
                if (AdsInter.isLoadNativeData2) {
                    Admob.getInstance()
                        .loadNativeAd(
                            context,
                            context.getString(R.string.native_data2),
                            object : NativeCallback() {
                                override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                    AdsInter.nativeAdsData2 = nativeAd
                                }

                                override fun onAdClick() {
                                    onAdsDataClick?.invoke(CLICK_NATIVE_DATA2)
                                }

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    nativeAdsData2 = null

                                }
                            })
                }
            }

        }

        fun loadNativeAll(context: Context) {
            coroutineScope.launch {
                if (AdsInter.nativeAdsAll == null) {
                    Admob.getInstance().loadNativeAd(
                        context,
                        context.getString(R.string.native_all),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                AdsInter.nativeAdsAll = nativeAd
                            }

                            override fun onAdFailedToLoad() {
//                            AdsInter.nativeAdsAll = null
                            }

                            override fun onAdImpression() {
                                super.onAdImpression()
                                AdsInter.nativeAdsAll = null

//                            loadNativeAll(context)
                            }

                            override fun onAdClick() {
                                onAdsClick?.invoke(CLICK_NATIVE_ALL)
                            }
                        }
                    )
                }
            }
        }

        fun pushNativeAll(
            context: Context,
            view: ViewGroup?,
            keyCheck: Boolean,
            resLayout: Int? = null,
            isRemoveBackground: Boolean = true,
            isLoadNormal: Boolean = true,
            isNotify: Boolean = false,
            scope: CoroutineScope = coroutineScope
        ) {
            scope.launch {
                if (!context.hasNetworkConnection() ||
//                !ConsentHelper.getInstance(context).canRequestAds() ||
                    !keyCheck
                ) {
                    view?.removeAllViews()
                    return@launch
                }
                if (!isLoadNormal && !Admob.getInstance().isLoadFullAds) {
                    view?.removeAllViews()
                    return@launch
                }
                if (nativeAdsAll != null) {
                    var layoutRes = resLayout ?: R.layout.layout_ads_native_update
                    if (isNotify) layoutRes = R.layout.layout_ads_native_update_black

                    val adView =
                        LayoutInflater.from(context)
                            .inflate(layoutRes, null) as NativeAdView
                    if (Admob.getInstance().isLoadFullAds && isRemoveBackground) {
                        adView.findViewById<RelativeLayout>(R.id.ad_unit_content).background = null
                    }
                    view?.removeAllViews()
                    view?.addView(adView)
                    Admob.getInstance().pushAdsToViewCustom(nativeAdsAll, adView)
                    return@launch
                }
                try {
                    val shimmer =
                        LayoutInflater.from(context).inflate(
                            R.layout.layout_shimer_native_new,
                            null
                        ) as ShimmerFrameLayout
                    shimmer.findViewById<RelativeLayout>(R.id.ad_unit_content).background = null
                    view?.removeAllViews()
                    view?.addView(shimmer)
                    Admob.getInstance().loadNativeAd(
                        context,
                        context.getString(R.string.native_all),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                var layoutRes = resLayout ?: R.layout.layout_ads_native_update
                                if (isNotify) layoutRes = R.layout.layout_ads_native_update_black
                                val adView =
                                    LayoutInflater.from(context).inflate(
                                        layoutRes,
                                        null
                                    ) as NativeAdView
                                if (Admob.getInstance().isLoadFullAds && isRemoveBackground) {
                                    adView.findViewById<RelativeLayout>(R.id.ad_unit_content).background =
                                        null
                                }
                                view?.removeAllViews()
                                view?.addView(adView)
                                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
                            }

                            override fun onAdClick() {
                                AdsInter.onAdsClick?.invoke(CLICK_NATIVE_ALL)
                            }

                            override fun onAdFailedToLoad() {
                                view?.removeAllViews()
                            }
                        })
                } catch (e: Exception) {
                    view?.removeAllViews()
                }
            }
        }

        fun loadInter(
            context: Activity,
            adsId: String,
            isShow: Boolean,
            action: () -> Unit
        ) {
            if (ConsentHelper.getInstance(context)
                    .canRequestAds() && context.hasNetworkConnection() && isShow
            ) {
                val callback = object : AdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        action.invoke()
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError?) {
                        super.onAdFailedToLoad(p0)
                        action.invoke()
                    }
                }

                if (Admob.getInstance().isLoadFullAds) {
                    Admob.getInstance().loadAndShowInterWithNativeFullScreen(
                        context,
                        adsId,
                        context.getString(R.string.native_full_all),
                        true,
                        callback
                    )
                } else {
                    Admob.getInstance().loadAndShowInter(
                        context,
                        adsId,
                        true,
                        callback
                    )
                }
            } else {
                action.invoke()
            }
        }

        fun loadNativePermissionLocation(context: Context) {
            if (nativeAdsPermissionLocation == null) {
                Admob.getInstance().loadNativeAd(
                    context,
                    context.getString(R.string.native_permission_location),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            nativeAdsPermissionLocation = nativeAd
                        }

                        override fun onAdFailedToLoad() {
                            super.onAdFailedToLoad()

                        }

                        override fun onAdClick() {
                            super.onAdClick()
                            nativeAdsPermissionLocation = null
                            loadNativePermissionLocation(context)
                        }

                    }
                )
            }
        }


        fun loadNativeHome(context: Context) {
            coroutineScope.launch {
                if (AdsInter.isLoadNativeHome && AdsInter.nativeAdsHome == null) {
                    Admob.getInstance()
                        .loadNativeAd(
                            context,
                            context.getString(R.string.native_home),
                            object : NativeCallback() {
                                override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                    AdsInter.nativeAdsHome = nativeAd
                                }

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    AdsInter.nativeAdsHome = null
                                    loadNativeHome(context)

                                }

                                override fun onAdClick() {
                                    onAdsDataClick?.invoke(CLICK_NATIVE_HOME)
                                }
                            })
                }
            }

        }

        fun loadNativeLanguageSelect(context: Context) {
            if (AdsInter.nativeAdsLanguageSelect == null) {
                Admob.getInstance()
                    .loadNativeAd(
                        context,
                        context.getString(R.string.native_language_select),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                AdsInter.nativeAdsLanguageSelect = nativeAd
                            }

                            override fun onAdImpression() {
                                super.onAdImpression()
                                AdsInter.nativeAdsLanguageSelect = null

                            }

                            override fun onAdClick() {
                                AdsInter.nativeAdsLanguageSelect = null
                            }
                        })
            }
        }


        fun loadBanner(context: Activity, frBanner: FrameLayout, view: ViewGroup) {
            if (hasNetworkConnection(context)) {
                if (AdsInter.isLoadBannerAll) {
                    frBanner.visibility = View.VISIBLE
                    val config = BannerPlugin.Config()
                    config.defaultAdUnitId = context.getString(R.string.banner_all)
                    config.defaultBannerType = BannerPlugin.BannerType.Adaptive
                    val cbFetchInterval =
                        FirebaseRemoteConfig.getInstance().getLong("cb_fetch_interval").toInt()
                    config.defaultRefreshRateSec = cbFetchInterval
                    config.defaultCBFetchIntervalSec = cbFetchInterval
                    Admob.getInstance().loadBannerPlugin(
                        context,
                        view.findViewById<ViewGroup>(R.id.fr_banner),
                        view.findViewById<ViewGroup>(R.id.include),
                        config
                    )
                } else {
                    frBanner.visibility = View.GONE
                }
            } else {
                frBanner.visibility = View.GONE
            }
        }

        fun loadNativePermission(context: Context) {
            Log.d("ntt", "loadNativePermission:")
            if (AdsInter.isLoadNativePermission && AdsInter.nativeAdsPermission == null) {
                Admob.getInstance()
                    .loadNativeAd(
                        context,
                        context.getString(R.string.native_permission),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                                AdsInter.nativeAdsPermission = nativeAd
                                Log.d("ntt", "onNativeAdLoaded: - ${AdsInter.nativeAdsPermission}")
                            }

//                            override fun onAdImpression() {
//                                super.onAdImpression()
//                                AdsInter.nativeAdsPermission = null
//
//                                Log.d("ntt", "onAdImpression:")
//
//                                loadNativePermission(context)
//
//                            }
                        })
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main
}
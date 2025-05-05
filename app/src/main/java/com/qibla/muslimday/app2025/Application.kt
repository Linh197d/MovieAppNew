package com.qibla.muslimday.app2025

import android.graphics.Color
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.nlbn.ads.util.Adjust
import com.nlbn.ads.util.AdsApplication
import com.nlbn.ads.util.AppOpenManager
import com.qibla.muslimday.app2025.helper.AppPreference
import com.qibla.muslimday.app2025.ui.notification.NotificationActivity
import com.qibla.muslimday.app2025.ui.ramadan.model.BackgroundRamadan
import com.qibla.muslimday.app2025.ui.ramadan.model.ColorModel
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel
import com.qibla.muslimday.app2025.ui.splash.SplashActivity
import com.qibla.muslimday.app2025.util.Const
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltAndroidApp
class Application : AdsApplication() {

    private var scope: CoroutineScope? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AppPreference.init(applicationContext)
        initRemoteConfig()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        scope = CoroutineScope(Job() + Dispatchers.Main)
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(NotificationActivity::class.java)
        scope?.launch {
            addData()
        }
    }

    private fun addData() {

        Const.listFont.add(FontModel(1, "Roboto", "roboto_regular.ttf"))
        Const.listFont.add(FontModel(2, "Suez One", "suezone_regular.ttf"))
        Const.listFont.add(FontModel(3, "Inika", "inika_regular.ttf"))
        Const.listFont.add(FontModel(4, "Oleo Script", "oleoscript_regular.ttf"))
        Const.listFont.add(FontModel(5, "Lexend", "lexend_regular.ttf"))
        Const.listFont.add(FontModel(6, "HeadlandOne", "headlandone_regular.ttf"))
        Const.listFont.add(FontModel(7, "Graduate", "graduate_regular.ttf"))
        Const.listFont.add(FontModel(8, "Fraunces", "fraunces_regular.ttf"))
        Const.listFont.add(FontModel(9, "Rochesster", "rochester_regular.ttf"))
        Const.listFont.add(FontModel(10, "The Nautigal", "thenautigal_regular.ttf"))
        Const.listFont.add(FontModel(11, "Spirax", "spirax_regular.ttf"))

        Const.listColor.add(
            ColorModel(
                0,
                Color.parseColor("#FCFFFFFF")
            )
        )
        Const.listColor.add(
            ColorModel(
                1,
                Color.parseColor("#000000")
            )
        )
        Const.listColor.add(
            ColorModel(
                2,
                Color.parseColor("#333333")
            )
        )
        Const.listColor.add(
            ColorModel(
                3,
                Color.parseColor("#666666")
            )
        )
        Const.listColor.add(
            ColorModel(
                4,
                Color.parseColor("#999999")
            )
        )

        Const.listColor.add(
            ColorModel(
                5,
                Color.parseColor("#AAAAAA")
            )
        )

        Const.listColor.add(
            ColorModel(
                6,
                Color.parseColor("#DDDDDD")
            )
        )

        Const.listColor.add(
            ColorModel(
                7,
                Color.parseColor("#8F1D21")
            )
        )

        Const.listColor.add(
            ColorModel(
                8,
                Color.parseColor("#C3272B")
            )
        )

        Const.listColor.add(ColorModel(9, Color.parseColor("#C91F37")))
        Const.listColor.add(ColorModel(10, Color.parseColor("#F22613")))
        Const.listColor.add(ColorModel(11, Color.parseColor("#DC3023")))
        Const.listColor.add(ColorModel(12, Color.parseColor("#CF000F")))
        Const.listColor.add(ColorModel(13, Color.parseColor("#F62459")))
        Const.listColor.add(ColorModel(14, Color.parseColor("#C93756")))
        Const.listColor.add(ColorModel(15, Color.parseColor("#D24D57")))
        Const.listColor.add(ColorModel(16, Color.parseColor("#DB5A6B")))
        Const.listColor.add(ColorModel(17, Color.parseColor("#F47983")))
        Const.listColor.add(ColorModel(18, Color.parseColor("#E68364")))
        Const.listColor.add(ColorModel(19, Color.parseColor("#F58F84")))
        Const.listColor.add(ColorModel(20, Color.parseColor("#FFB3A7")))
        Const.listColor.add(ColorModel(21, Color.parseColor("#FCC9B9")))
        Const.listColor.add(ColorModel(22, Color.parseColor("#763568")))
        Const.listColor.add(ColorModel(23, Color.parseColor("#5D3F6A")))
        Const.listColor.add(ColorModel(24, Color.parseColor("#8E44AD")))
        Const.listColor.add(ColorModel(25, Color.parseColor("#875F9A")))
        Const.listColor.add(ColorModel(26, Color.parseColor("#8254F3")))
        Const.listColor.add(ColorModel(27, Color.parseColor("#BF55EC")))
        Const.listColor.add(ColorModel(28, Color.parseColor("#B398F8")))
        Const.listColor.add(ColorModel(29, Color.parseColor("#003171")))
        Const.listColor.add(ColorModel(30, Color.parseColor("#1F4788")))
        Const.listColor.add(ColorModel(31, Color.parseColor("#4B77BE")))
        Const.listColor.add(ColorModel(32, Color.parseColor("#89C4F4")))
        Const.listColor.add(ColorModel(33, Color.parseColor("#317589")))
        Const.listColor.add(ColorModel(34, Color.parseColor("#59ABE3")))
        Const.listColor.add(ColorModel(35, Color.parseColor("#22A7F0")))
        Const.listColor.add(ColorModel(36, Color.parseColor("#006442")))
        Const.listColor.add(ColorModel(37, Color.parseColor("#5B8930")))
        Const.listColor.add(ColorModel(38, Color.parseColor("#407A52")))
        Const.listColor.add(ColorModel(39, Color.parseColor("#6B9362")))
        Const.listColor.add(ColorModel(40, Color.parseColor("#87D37C")))
        Const.listColor.add(ColorModel(41, Color.parseColor("#26A65B")))
        Const.listColor.add(ColorModel(42, Color.parseColor("#26C281")))
        Const.listColor.add(ColorModel(43, Color.parseColor("#03A678")))
        Const.listColor.add(ColorModel(44, Color.parseColor("#16A085")))
        Const.listColor.add(ColorModel(45, Color.parseColor("#36D7B7")))
        Const.listColor.add(ColorModel(46, Color.parseColor("#F9690E")))
        Const.listColor.add(ColorModel(47, Color.parseColor("#A17917")))
        Const.listColor.add(ColorModel(48, Color.parseColor("#E08A1E")))
        Const.listColor.add(ColorModel(49, Color.parseColor("#FFA400")))
        Const.listColor.add(ColorModel(50, Color.parseColor("#E2B13C")))
        Const.listColor.add(ColorModel(51, Color.parseColor("#F3C13A")))
        Const.listColor.add(ColorModel(52, Color.parseColor("#F7CA18")))
        Const.listColor.add(ColorModel(53, Color.parseColor("#4E342E")))
        Const.listColor.add(ColorModel(54, Color.parseColor("#5D4037")))
        Const.listColor.add(ColorModel(55, Color.parseColor("#6D4C41")))
        Const.listColor.add(ColorModel(56, Color.parseColor("#8D6E63")))
        Const.listColor.add(ColorModel(57, Color.parseColor("#A1887F")))

        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                0,
                "bg_ramadan_01.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_01.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                1,
                "bg_ramadan_02.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_02.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                2,
                "bg_ramadan_03.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_03.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                3,
                "bg_ramadan_04.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_04.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                4,
                "bg_ramadan_05.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_05.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                5,
                "bg_ramadan_06.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_06.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                6,
                "bg_ramadan_07.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_07.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                7,
                "bg_ramadan_08.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_08.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                8,
                "bg_ramadan_09.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_09.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                9,
                "bg_ramadan_10.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_10.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                10,
                "bg_ramadan_11.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_11.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                11,
                "bg_ramadan_12.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_12.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                12,
                "bg_ramadan_13.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_13.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                13,
                "bg_ramadan_14.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_14.png"
            )
        )
        Const.listBackgroundRamadan.add(
            BackgroundRamadan(
                14,
                "bg_ramadan_15.png",
                "file:///android_asset/backgroundramadan/bg_ramadan_15.png"
            )
        )
    }

    override fun enableAdsResume(): Boolean = true
    override fun getKeyRemoteIntervalShowInterstitial(): String {
        return "interval_show_interstitial"
    }

    override fun getListTestDeviceId(): MutableList<String>? = null

    override fun getResumeAdId(): String = getString(R.string.appopen_resume)

    override fun buildDebug(): Boolean =BuildConfig.DEBUG

    override fun enableAdjustTracking(): Boolean {
        return true
    }

    override fun logRevenueAdjustWithCustomEvent(p0: Double, p1: String?) {
        Adjust.getInstance().logRevenueWithCustomEvent("3jvugw", p0, p1)
    }

    override fun getAdjustToken(): String {
        return getString(R.string.app_token)
    }


    fun initRemoteConfig() {
        var index = BuildConfig.Minimum_Fetch
        Log.e("TAG", "initRemoteConfig: " + index)
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(index)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        mFirebaseRemoteConfig.fetchAndActivate()


    }
}
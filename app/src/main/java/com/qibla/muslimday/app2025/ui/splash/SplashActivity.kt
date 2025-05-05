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
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.os.bundleOf
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.nlbn.ads.callback.AdCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.BuildConfig
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivitySplashBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.intro.IntroActivity
import com.qibla.muslimday.app2025.ui.language.LanguageActivity
import com.qibla.muslimday.app2025.ui.uninstall.UninstallActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.InAppUpdate
import com.qibla.muslimday.app2025.util.InstallUpdatedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private lateinit var inAppUpdate: InAppUpdate

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private var interShortcutCallback: AdCallback? = null
    private var interCallback: AdCallback? = null
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
        getRemoteConfig()
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (Admob.getInstance().isLoadFullAds) {
                val shortcut = ShortcutInfoCompat.Builder(this, "shortcut_uninstall")
                    .setShortLabel("Uninstall")
                    .setLongLabel("Uninstall")
                    .setIcon(
                        IconCompat.createWithResource(
                            this@SplashActivity,
                            R.drawable.ic_uninstall
                        )
                    )
                    .setIntent(
                        Intent(
                            Intent.ACTION_VIEW,
                            null, this@SplashActivity, SplashActivity::class.java
                        ).putExtras(bundleOf("shortcut_value" to "uninstall"))
                    )
                    .build()

                ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
            }
        }, 3000)
        val remoteConfig = FirebaseRemoteConfig.getInstance();
        inAppUpdate = InAppUpdate(this, remoteConfig.getBoolean("force_update"), object :
            InstallUpdatedListener {
            override fun onUpdateNextAction() {
                if (hasNetworkConnection()) {
                    consentHelper.obtainConsentAndShow(this@SplashActivity) {
                        val shortcutValue = if (intent.hasExtra("shortcut_value")) {
                            intent.getStringExtra("shortcut_value")
                        } else {
                            null
                        }
                        if (shortcutValue != null) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                if (Admob.getInstance().isLoadFullAds) {
                                    loadInterAdsSplashFromShortCut()
                                } else {
                                    startNextAct()
                                }
                            }, 3000)
                        } else {
                            loadInterAdsSplash()
                        }

                    }
                } else {
                    Handler().postDelayed({
                        startNextAct()
                    }, 3000)
                }

            }

            override fun onUpdateCancel() {
                finish()
            }

        })
        preferenceHelper.setBoolean(PreferenceHelper.IS_SHOW_DIALOG_TARAWIH, false)

    }

    private fun loadInterAdsSplashFromShortCut() {
        if (AdsInter.isLoadInterSplashFromUninstall) {
            interShortcutCallback = object :
                AdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    startActivity(Intent(this@SplashActivity, UninstallActivity::class.java))
                    finish()
                }
            }
            if (Admob.getInstance().isLoadFullAds) {
                Admob.getInstance()
                    .loadInterSplashWithNativeFullScreen(
                        this,
                        getString(R.string.inter_splash_from_uninstall),
                        getString(R.string.native_full_all),
                        3000,
                        interShortcutCallback
                    )
            } else {
                Admob.getInstance()
                    .loadSplashInterAds2(
                        this,
                        getString(R.string.inter_splash_from_uninstall),
                        0,
                        interShortcutCallback
                    )
            }
        } else
            startActivity(Intent(this@SplashActivity, UninstallActivity::class.java))


    }

    private fun loadInterAdsSplash() {
        if (AdsInter.isLoadInterSplash) {
            interCallback = object : AdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    startNextAct()
                }
            }

            if (Admob.getInstance().isLoadFullAds && AdsInter.isLoadNativeFullSplash
            ) {
                Admob.getInstance()
                    .loadInterSplashWithNativeFullScreen(
                        this,
                        getString(R.string.inter_splash),
                        getString(R.string.native_full_splash),
                        3000,
                        interCallback
                    )

            } else {
                Admob.getInstance()
                    .loadSplashInterAds2(
                        this,
                        getString(R.string.inter_splash),
                        3000,
                        interCallback
                    )

            }
        } else
            startNextAct()
    }


    override fun onResume() {
        super.onResume()
        inAppUpdate.onResume()
        Admob.getInstance().onCheckShowSplashWhenFail(this, interCallback, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
        Admob.getInstance().dismissLoadingDialog()
    }

    override fun onStop() {
        super.onStop()
        Admob.getInstance().dismissLoadingDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode, resultCode, data)
    }

    private fun startNextAct() {
        if (preferenceHelper.getBoolean(PreferenceHelper.PREF_SHOWED_START_LANGUAGE) == true) {
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            startActivity(
                LanguageActivity.newIntent(this, true)
            )
        }
    }

    private fun setUpKeyActive(mFirebaseRemoteConfig: FirebaseRemoteConfig) {
        AdsInter.isLoadNativeLanguage =
            mFirebaseRemoteConfig.getBoolean("is_load_native_language")
        AdsInter.isLoadNativeLanguage3 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_language_setting")
        AdsInter.isLoadNativeLanguageSelect =
            mFirebaseRemoteConfig.getBoolean("is_load_native_language_select")
        AdsInter.isLoadInterIntro =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_intro")
        AdsInter.isLoadNativePermissionOpen =
            mFirebaseRemoteConfig.getBoolean("is_load_native_popup_permission")
        AdsInter.isLoadNativeIntro =
            mFirebaseRemoteConfig.getBoolean("is_load_native_intro1")
        AdsInter.isLoadNativeIntro2 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_intro2")
        AdsInter.isLoadNativeIntro3 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_intro3")
        AdsInter.isLoadNativeIntro4 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_intro4")
        AdsInter.isLoadBannerAll =
            mFirebaseRemoteConfig.getBoolean("is_load_banner_all")

        AdsInter.isLoadInterDetail =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_detail")
        AdsInter.isLoadInterQibla =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_qibla")
        AdsInter.isLoadNativePrayingtime =
            mFirebaseRemoteConfig.getBoolean("is_load_native_prayingtime")
        AdsInter.isLoadNativeHome =
            mFirebaseRemoteConfig.getBoolean("is_load_native_home")
        AdsInter.isLoadNativePermission =
            mFirebaseRemoteConfig.getBoolean("is_load_native_permission")
        AdsInter.isLoadNativeTasbih =
            mFirebaseRemoteConfig.getBoolean("is_load_native_tasbih")
        AdsInter.isLoadNativePermissionAppear =
            mFirebaseRemoteConfig.getBoolean("is_load_native_permission_appear")
        AdsInter.isLoadNativePermissionLocation =
            mFirebaseRemoteConfig.getBoolean("is_load_native_permission_location")
        AdsInter.isLoadNativePermissionNotification =
            mFirebaseRemoteConfig.getBoolean("is_load_native_permission_notification")
        AdsInter.isLoadNativeBookmark =
            mFirebaseRemoteConfig.getBoolean("is_load_native_bookmark")
        AdsInter.isLoadNativeQibla =
            mFirebaseRemoteConfig.getBoolean("is_load_native_qibla")
        AdsInter.isLoadNativeExit =
            mFirebaseRemoteConfig.getBoolean("is_load_native_exit")
        AdsInter.isLoadInterHome =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_home")
        AdsInter.isLoadInterTab =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_tab")
        AdsInter.isLoadInterBack =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_back")
        AdsInter.isLoadInterSplash =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_splash")
        AdsInter.isLoadNativeDetail =
            mFirebaseRemoteConfig.getBoolean("is_load_native_detail")
        AdsInter.isLoadNativeAllah =
            mFirebaseRemoteConfig.getBoolean("is_load_native_allah")
        AdsInter.isLoadNativeZakat =
            mFirebaseRemoteConfig.getBoolean("is_load_native_zakat")
        AdsInter.isLoadNativePrayerTime =
            mFirebaseRemoteConfig.getBoolean("is_load_native_prayer_time")
        AdsInter.isLoadNativeMosque =
            mFirebaseRemoteConfig.getBoolean("is_load_native_mosque")
        AdsInter.isLoadInterQuran =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_quran")
        AdsInter.isLoadNativeUninstall =
            mFirebaseRemoteConfig.getBoolean("is_load_native_uninstall")
        AdsInter.isLoadNativeFullAll =
            mFirebaseRemoteConfig.getBoolean("is_load_native_full_all")
        AdsInter.isLoadNativeFullSplash =
            mFirebaseRemoteConfig.getBoolean("is_load_native_full_splash")
        AdsInter.isLoadNativeFullIntro2 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_full_intro2")
        AdsInter.isLoadNativeFullIntro3 =
            mFirebaseRemoteConfig.getBoolean("is_load_native_full_intro3")
        AdsInter.isLoadInterSplashFromUninstall =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_splash_from_uninstall")
        AdsInter.isLoadInterUninstall =
            mFirebaseRemoteConfig.getBoolean("is_load_inter_uninstall")

    }

    private fun setUpKeyInActive() {
        AdsInter.isLoadNativeLanguage = false
        AdsInter.isLoadNativeLanguage3 = false
        AdsInter.isLoadNativeLanguageSelect = false
        AdsInter.isLoadInterIntro = false
        AdsInter.isLoadNativePermissionOpen = false
        AdsInter.isLoadNativeIntro = false
        AdsInter.isLoadNativeIntro2 = false
        AdsInter.isLoadNativeIntro3 = false
        AdsInter.isLoadNativeIntro4 = false
        AdsInter.isLoadBannerAll = false
        AdsInter.isLoadInterDetail = false
        AdsInter.isLoadInterQibla = false
        AdsInter.isLoadNativePrayingtime = false
        AdsInter.isLoadNativeHome = false
        AdsInter.isLoadNativePermission = false
        AdsInter.isLoadNativeTasbih = false
        AdsInter.isLoadNativePermissionAppear = false
        AdsInter.isLoadNativePermissionLocation = false
        AdsInter.isLoadNativePermissionNotification = false
        AdsInter.isLoadNativeBookmark = false
        AdsInter.isLoadNativeQibla = false
        AdsInter.isLoadNativeExit = false
        AdsInter.isLoadInterHome = false
        AdsInter.isLoadInterTab = false
        AdsInter.isLoadInterBack = false
        AdsInter.isLoadInterSplash = false
    }

    private fun getRemoteConfig() {
        var index = BuildConfig.Minimum_Fetch
        Log.e("TAG", "initRemoteConfig: " + index)
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(index)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        mFirebaseRemoteConfig.fetchAndActivate()

        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    setUpKeyInActive()
                    setUpKeyActive(mFirebaseRemoteConfig)
                }
            }

    }


    private fun getRemoteConfigBoolean(adUnitId: String): Boolean {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return mFirebaseRemoteConfig.getBoolean(adUnitId)
    }

    private fun isTimeAutomatic(c: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.Global.getInt(c.contentResolver, Settings.Global.AUTO_TIME, 0) == 1
        } else {
            Settings.System.getInt(c.contentResolver, Settings.System.AUTO_TIME, 0) == 1
        }
    }


}
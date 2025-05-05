package com.qibla.muslimday.app2025.ui.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.AppOpenManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityPermissionBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val REQUEST_CODE_SYSTEM_ALERT_WINDOW = 1
    private val REQUEST_CODE_LOCATION = 2
    private val REQUEST_CODE_NOTIFICATON = 30

    private var checkOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity::class.java)
        binding.btnAllowLocation.setOnClickListener {

            if (ConsentHelper.getInstance(this)
                    .canRequestAds() && AdsInter.isLoadNativePermissionLocation && Admob.getInstance().isLoadFullAds
            ) {
                binding.frAds.visibility = View.VISIBLE
                loadAdsNativePermissionLocation()
            } else {
                binding.frAds.visibility = View.GONE
            }

            requestPermissionLocation()
        }

        binding.btnAllowAppearOnTop.setOnClickListener {

            if (ConsentHelper.getInstance(this)
                    .canRequestAds() && AdsInter.isLoadNativePermissionAppear && Admob.getInstance().isLoadFullAds
            ) {
                binding.frAds.visibility = View.VISIBLE
                loadAdsNativePermissionAppear()
            } else {
                binding.frAds.visibility = View.GONE
            }


            requestPermissionAlertWindow()
        }

        binding.btnAllowNotification.setOnClickListener {

            if (ConsentHelper.getInstance(this)
                    .canRequestAds() && AdsInter.isLoadNativePermissionLocation && Admob.getInstance().isLoadFullAds
            ) {
                binding.frAds.visibility = View.VISIBLE
                loadAdsNativePermissionNotification()
            } else {
                binding.frAds.visibility = View.GONE
            }

            requestPermissionNotification()
        }

        binding.btnDone.setOnClickListener {
            if (Build.VERSION.SDK_INT > 32) {
                if (isPermissionSAW && isPermissionLocation && isPermissionNotification) {
                    val intent3 =
                        Intent(this@PermissionActivity, MainActivity::class.java)
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    preferenceHelper.hidePermission()
                    startActivity(intent3)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.string_you_need_to_accept_full_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (isPermissionSAW && isPermissionLocation) {
                    val intent3 =
                        Intent(this@PermissionActivity, MainActivity::class.java)
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    preferenceHelper.hidePermission()
                    startActivity(intent3)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.string_you_need_to_accept_full_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnDoneFullAds.setOnClickListener {
            val intent3 =
                Intent(this@PermissionActivity, MainActivity::class.java)
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            preferenceHelper.hidePermission()
            startActivity(intent3)
        }
        binding.btnSkip.setOnClickListener {
            val intent3 =
                Intent(this@PermissionActivity, MainActivity::class.java)
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            preferenceHelper.hidePermission()
            startActivity(intent3)
        }

        binding.llNotification.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    private fun requestPermissionNotification() {

        binding.frAds.visibility = View.GONE

        if (Build.VERSION.SDK_INT > 32) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_NOTIFICATON
            )
        }
    }

    private fun requestPermissionAlertWindow() {

        binding.frAds.visibility = View.GONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(applicationContext)) {

                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )

                AppOpenManager.getInstance()
                    .disableAppResumeWithActivity(PermissionActivity::class.java)
                checkOpen = true
                startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW)
            }
        }
    }

    private fun requestPermissionLocation() {

        binding.frAds.visibility = View.GONE

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CODE_LOCATION
        )
    }

    private fun checkPermissionNotification() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionNotification = true
            binding.btnAllowNotification.isEnabled = false
            binding.btnAllowNotification.setBackgroundResource(R.drawable.ic_switch_active)
        } else {
            isPermissionNotification = false
            binding.btnAllowNotification.isEnabled = true
            binding.btnAllowNotification.setBackgroundResource(R.drawable.ic_switch_inactive)
        }
    }

    private fun checkPermissionLocation() {
        if (
//            ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionLocation = true
            binding.btnAllowLocation.isEnabled = false
            binding.btnAllowLocation.setBackgroundResource(R.drawable.ic_switch_active)
        } else {
            isPermissionLocation = false
            binding.btnAllowLocation.isEnabled = true
            binding.btnAllowLocation.setBackgroundResource(R.drawable.ic_switch_inactive)
        }
    }

    private fun checkSystemAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                isPermissionSAW = true
                binding.btnAllowAppearOnTop.isEnabled = false
                binding.btnAllowAppearOnTop.setBackgroundResource(R.drawable.ic_switch_active)
            } else {
                isPermissionSAW = false
                binding.btnAllowAppearOnTop.isEnabled = true
                binding.btnAllowAppearOnTop.setBackgroundResource(R.drawable.ic_switch_inactive)
            }
        }
    }

    private fun loadAdsNativePermission() {
        try {
            if (AdsInter.isLoadNativePermission
//                && ConsentHelper.getInstance(this).canRequestAds()
                && Admob.getInstance().isLoadFullAds
            ) {
                Admob.getInstance().loadNativeAd(
                    this,
                    getString(R.string.native_permission),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            try {

                                val adView = LayoutInflater.from(this@PermissionActivity)
                                    .inflate(
                                        R.layout.layout_ads_native_update_no_bor,
                                        null
                                    ) as NativeAdView

                                binding.frAds.removeAllViews()
                                binding.frAds.addView(adView)
                                Admob.getInstance()
                                    .pushAdsToViewCustom(nativeAd, adView as NativeAdView)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onAdClick() {
                            loadAdsNativePermission()
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

    private fun loadAdsNativePermissionLocation() {
        try {
            if (AdsInter.isLoadNativePermissionLocation) {
//                if (AdsInter.nativeAdsPermissionLocation != null) {
//                    AdsInter.nativeAdsPermissionLocation?.let {
//                        val adView = LayoutInflater.from(this@PermissionActivity)
//                            .inflate(
//                                R.layout.layout_ads_native_update_no_bor,
//                                null
//                            ) as NativeAdView
//
//                        binding.frAds.removeAllViews()
//                        binding.frAds.addView(adView)
//                        Admob.getInstance()
//                            .pushAdsToViewCustom(it, adView as NativeAdView)
//                    }
//                } else {
                Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                    this@PermissionActivity,
                    getString(R.string.native_permission_location),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            val adView = LayoutInflater.from(this@PermissionActivity)
                                .inflate(
                                    R.layout.layout_ads_native_update_no_bor,
                                    null
                                ) as NativeAdView

                            binding.frAds.removeAllViews()
                            binding.frAds.addView(adView)
                            Admob.getInstance()
                                .pushAdsToViewCustom(nativeAd, adView as NativeAdView)
                        }

                        override fun onAdFailedToLoad() {
                            binding.frAds.removeAllViews()
                        }
                    }
                )
//                }

            } else {
                binding.frAds.removeAllViews()
                binding.frAds.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadAdsNativePermissionAppear() {
        try {
            if (AdsInter.isLoadNativePermissionAppear) {
//                if (AdsInter.nativeAdsPermissionAppear != null) {
//                    AdsInter.nativeAdsPermissionAppear?.let {
//                        val adView = LayoutInflater.from(this@PermissionActivity)
//                            .inflate(
//                                R.layout.layout_ads_native_update_no_bor,
//                                null
//                            ) as NativeAdView
//                        binding.frAds.removeAllViews()
//                        binding.frAds.addView(adView)
//                        Admob.getInstance()
//                            .pushAdsToViewCustom(it, adView as NativeAdView)
//                    }
//                } else {
                Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                    this@PermissionActivity,
                    getString(R.string.native_permission_appear),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            val adView = LayoutInflater.from(this@PermissionActivity)
                                .inflate(
                                    R.layout.layout_ads_native_update_no_bor,
                                    null
                                ) as NativeAdView

                            binding.frAds.removeAllViews()
                            binding.frAds.addView(adView)
                            Admob.getInstance()
                                .pushAdsToViewCustom(nativeAd, adView as NativeAdView)
                        }

                        override fun onAdFailedToLoad() {
                            binding.frAds.removeAllViews()
                        }

                    }
                )
//                }

            } else {
                binding.frAds.removeAllViews()
                binding.frAds.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadAdsNativePermissionNotification() {
        try {
            if (AdsInter.isLoadNativePermissionNotification) {
                if (AdsInter.nativeAdsPermissionNotification != null) {
                    AdsInter.nativeAdsPermissionNotification?.let {
                        val adView = LayoutInflater.from(this@PermissionActivity)
                            .inflate(
                                R.layout.layout_ads_native_update_no_bor,
                                null
                            ) as NativeAdView
                        binding.frAds.removeAllViews()
                        binding.frAds.addView(adView)
                        Admob.getInstance()
                            .pushAdsToViewCustom(it, adView as NativeAdView)
                    }
                } else {
                    Admob.getInstance().loadNativeAd(
                        this@PermissionActivity,
                        getString(R.string.native_permission_notification),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                val adView = LayoutInflater.from(this@PermissionActivity)
                                    .inflate(
                                        R.layout.layout_ads_native_update_no_bor,
                                        null
                                    ) as NativeAdView


                                binding.frAds.removeAllViews()
                                binding.frAds.addView(adView)
                                Admob.getInstance()
                                    .pushAdsToViewCustom(nativeAd, adView as NativeAdView)
                            }

                            override fun onAdFailedToLoad() {
                                binding.frAds.removeAllViews()
                            }

                        }
                    )
                }

            } else {
                binding.frAds.removeAllViews()
                binding.frAds.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkOpen) {
            binding.frAds.visibility = View.VISIBLE
            checkOpen = false
        }

        checkPermissionLocation()
        checkSystemAlertWindowPermission()
        checkPermissionNotification()

        if (isPermissionNotification || isPermissionLocation || isPermissionSAW) {
            binding.btnSkip.setText(R.string.string_continue)
        } else {
            binding.btnSkip.setText(R.string.string_skip)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_LOCATION) {
            if (
//                ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isPermissionLocation = false
                binding.frAds.visibility = View.GONE
                binding.btnAllowLocation.setBackgroundResource(R.drawable.ic_switch_active)

                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setCancelable(false)

                alertDialog.setMessage(getString(R.string.string_you_need_to_enable_permission_to_use_this_features))
                alertDialog.setButton(
                    -1,
                    getString(R.string.go_to_setting)
                ) { dialogInterface, i ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri

                    AppOpenManager.getInstance()
                        .disableAppResumeWithActivity(PermissionActivity::class.java)

                    startActivityForResult(intent, 12)

                    checkOpen = true

                    alertDialog.dismiss()
                }

                alertDialog.show()
            } else {
                binding.frAds.visibility = View.VISIBLE
                binding.btnAllowLocation.isEnabled = false
                isPermissionLocation = true
                binding.btnAllowLocation.setBackgroundResource(R.drawable.ic_switch_active)

            }
        } else if (requestCode == REQUEST_CODE_NOTIFICATON) {
            if (grantResults.isNotEmpty()) {
                val post = grantResults[0] == PackageManager.PERMISSION_GRANTED

                if (post) {
                    isPermissionNotification = true
                    binding.frAds.visibility = View.VISIBLE
                    binding.btnAllowNotification.setBackgroundResource(R.drawable.ic_switch_active)
                    binding.btnAllowNotification.isEnabled = false
                } else {
                    isPermissionNotification = false
                    binding.frAds.visibility = View.GONE
                    binding.btnAllowNotification.setBackgroundResource(R.drawable.ic_switch_inactive)
                    binding.btnAllowNotification.isEnabled = true

                    val alertDialog = AlertDialog.Builder(this).create()
                    alertDialog.setCancelable(false)

                    alertDialog.setMessage(getString(R.string.string_you_need_to_enable_permission_to_use_this_features))
                    alertDialog.setButton(
                        -1,
                        getString(R.string.go_to_setting)
                    ) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri

                        AppOpenManager.getInstance()
                            .disableAppResumeWithActivity(PermissionActivity::class.java)

                        startActivityForResult(intent, 123)

                        checkOpen = true
                        alertDialog.dismiss()
                    }

                    alertDialog.show()
                }
            }
        }

        if (isPermissionNotification || isPermissionLocation || isPermissionSAW) {
            binding.btnSkip.setText(R.string.string_continue)
        } else {
            binding.btnSkip.setText(R.string.string_skip)
        }

    }


    override fun setBinding(layoutInflater: LayoutInflater): ActivityPermissionBinding {
        return ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        loadAdsNativePermission()
//        if (AdsInter.isLoadNativePermissionLocation && ConsentHelper.getInstance(this)
//                .canRequestAds() && Admob.getInstance().isLoadFullAds
//        ) {
//            AdsInter.loadNativePermissionLocation(this)
//        }
//
//        if (AdsInter.isLoadNativePermissionAppear && ConsentHelper.getInstance(this)
//                .canRequestAds() && Admob.getInstance().isLoadFullAds
//        ) {
//            AdsInter.loadNativePermissionAppear(this)
//        }
//
//        if (AdsInter.isLoadNativePermissionLocation && ConsentHelper.getInstance(this)
//                .canRequestAds() && Admob.getInstance().isLoadFullAds
//        ) {
//            AdsInter.loadNativePermissionLocation(this)
//        }


        if (Admob.getInstance().isLoadFullAds) {
            binding.btnDoneFullAds.visibility = View.GONE
            binding.btnDone.visibility = View.GONE
//            binding.tvGrandPermissionLater.visibility = View.GONE
        } else {
            binding.btnDoneFullAds.visibility = View.GONE
            binding.btnDone.visibility = View.GONE
//            binding.tvGrandPermissionLater.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PermissionActivity::class.java)
        }

        private var isPermissionSAW = false
        private var isPermissionLocation = false
        private var isPermissionNotification = false
    }
}
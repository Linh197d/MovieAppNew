package com.qibla.muslimday.app2025.ui.tasbih

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
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
import com.qibla.muslimday.app2025.databinding.ActivityTasbihBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.view.DialogAdjustTheNumberOfRounds
import com.qibla.muslimday.app2025.view.DialogConnectionWifi
import com.qibla.muslimday.app2025.view.DialogWarning
import dagger.hilt.android.AndroidEntryPoint
import pl.droidsonroids.gif.GifDrawable
import javax.inject.Inject

@AndroidEntryPoint
class TasbihActivity : BaseActivity<ActivityTasbihBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private var isPlaySoundTasbih: Boolean = true

    private var numberOfRound = 0

    private lateinit var gifDrawable: GifDrawable

    private var numRound = 0

    private var num = 0

    private lateinit var dialogAdjustNumberOfRounds: DialogAdjustTheNumberOfRounds

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    private var lastClickTime = 0L
    private val clickDelay = 500L
    private var showBack = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBack = intent?.getBooleanExtra("showBack", true) == true
//        if(!hasNetworkConnection()){
//            showDialogWifi(true)
//        }
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        AdsInter.onAdsClick = {
            loadAdsNativeTasbih()
        }
        consentHelper.obtainConsentAndShow(this) {
            if (Admob.getInstance().isLoadFullAds) {
                binding.frBanner.visible(false)
                binding.layoutNative.visible(true)
                loadAdsNativeTasbih()
            } else {
                binding.frBanner.visible(true)
                binding.layoutNative.visible(false)
                AdsInter.loadBanner(this@TasbihActivity, binding.frBanner, binding.root)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSound.setOnClickListener {
            isPlaySoundTasbih = !isPlaySoundTasbih
            setIsSoundTasbih(isPlaySoundTasbih)
            preferenceHelper.setIsSoundTasbih(isPlaySoundTasbih)
        }

        binding.btnEdit.setOnClickListener {
            showDialogAdjustNumberOfRounds()
        }

        binding.btnReload.setOnClickListener {

            binding.frAds.visibility = View.GONE

            val dialogWarning = DialogWarning(this)
            dialogWarning.init(this, "tasbih", object : DialogWarning.OnPress {
                override fun cancel() {
                    dialogWarning.dismiss()
                }

                override fun enable() {
                    num = 0
                    numRound = 0
                    loadData()
                    dialogWarning.dismiss()
                }

            })

            dialogWarning.show()

            dialogWarning.setOnDismissListener {
                binding.frAds.visibility = View.VISIBLE
            }

        }
    }


    private fun showDialogWifi(b: Boolean) {
        val dialogWifi = DialogConnectionWifi(this, b)
        dialogWifi.init(
            this, object : DialogConnectionWifi.OnPress {
                override fun cancel() {
                    dialogWifi.dismiss()
                    if (!hasNetworkConnection()) {
                        showDialogWifi(true)
                    }
                }

                override fun save() {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivity(intent)
                    Const.isCheckWifi = 2
                    dialogWifi.dismiss()
                }
            }
        )
        try {
            dialogWifi.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    private fun loadAdsNativeTasbih() {
        AdsInter.pushNativeAll(
            context = this@TasbihActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeTasbih,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    private fun pushAdsToView(nativeAd: NativeAd) {
        val adView = LayoutInflater.from(this@TasbihActivity)
            .inflate(R.layout.layout_ads_native_update, null)
        val nativeAdView = adView as NativeAdView
        binding.frAds.removeAllViews()
        binding.frAds.addView(adView)

        Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)

    }


    private fun showDialogAdjustNumberOfRounds() {

        binding.frAds.visibility = View.GONE
        dialogAdjustNumberOfRounds = DialogAdjustTheNumberOfRounds(this, preferenceHelper)
        dialogAdjustNumberOfRounds.init(this, object : DialogAdjustTheNumberOfRounds.OnPress {
            override fun cancel() {
                dialogAdjustNumberOfRounds.dismiss()
            }

            override fun saveNumberOfRound(number: Int) {
                dialogAdjustNumberOfRounds.dismiss()

                preferenceHelper.setNumberOfRounds(number)

                num = 0

                loadData()
            }

            override fun showAgainDialog() {
                loadData()
            }

            override fun load() {
                num = 0

                loadData()
            }

        })

        dialogAdjustNumberOfRounds.show()

        dialogAdjustNumberOfRounds.setOnDismissListener {
            binding.frAds.visibility = View.VISIBLE
        }
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityTasbihBinding {
        return ActivityTasbihBinding.inflate(layoutInflater)
    }

    override fun initView() {

        loadData()

        gifDrawable = GifDrawable(resources, R.drawable.high_quality_output_white)

        binding.gifView.setImageDrawable(gifDrawable)

        gifDrawable.setSpeed(1.5f)

        gifDrawable.stop()

        binding.flTabihTab.setOnClickListener {

            val currentTime = System.currentTimeMillis()

            if (currentTime - lastClickTime >= clickDelay) {

                if (isPlaySoundTasbih) {
                    playSound()
                }

                // Kiểm tra nếu đối tượng GifDrawable đã được khởi tạo trước đó
                gifDrawable.seekTo(0)

                gifDrawable.start()

                num++

                if (num > numberOfRound) {
                    num = 0
                    numRound++
                }

                loadData()

                lastClickTime = currentTime
                binding.gifView.postDelayed({
                    gifDrawable.seekTo(0)
                    gifDrawable.stop()
                }, 300)
            }

        }
    }

    private fun loadData() {

        isPlaySoundTasbih = preferenceHelper.getIsSoundTasbih()

        setIsSoundTasbih(isPlaySoundTasbih)

        numberOfRound = preferenceHelper.getNumberOfRounds()

        binding.tvNumOfRounds.text = numberOfRound.toString()

        binding.tvNum.text = num.toString()
        binding.tvTasbihCount.text = num.toString()
        if (num == 0) {
            binding.tvTasbihCount.setTextColor(getColor(R.color.color_B8C2CC))
        } else {
            binding.tvTasbihCount.setTextColor(getColor(R.color.color_008040))
        }

        binding.tvRounds.text = numRound.toString()
    }

    private fun setIsSoundTasbih(isPlaySoundTasbih: Boolean) {
        if (isPlaySoundTasbih) {
            binding.btnSound.setImageResource(R.drawable.ic_sound_tasbih)
        } else {
            binding.btnSound.setImageResource(R.drawable.ic_not_sound_tasbih)
        }
    }

    private fun playSound() {

        mediaPlayer.stop()

        mediaPlayer.reset()

        val descriptor: AssetFileDescriptor =
            this.assets.openFd("bead_sound.wav")
        mediaPlayer.setDataSource(
            descriptor.fileDescriptor,
            descriptor.startOffset,
            descriptor.length
        )
        descriptor.close()

        mediaPlayer.prepare()

        mediaPlayer.start()

    }


    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer()

//        if (isLoadNative) {
//            isLoadNative = false
//
//            if (ConsentHelper.getInstance(this).canRequestAds())
//
//                loadAdsNativeTasbih()
//        }
//        if (!hasNetworkConnection() && Const.isCheckWifi ==2){
//            showDialogWifi(true)
//            Const.isCheckWifi = 1
//        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
        mediaPlayer.stop()
        mediaPlayer.release()

    }

    override fun onBackPressed() {
        if (showBack)
            AdsInter.loadInter(
                context = this@TasbihActivity,
                adsId = getString(R.string.inter_back),
                isShow = AdsInter.isLoadInterBack && Admob.getInstance().isLoadFullAds,
                action = {
                    finish()
                }
            )
        else
            finish()
    }


}